package trax.aero.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import trax.aero.controller.Creation_Sales_Controller;
import trax.aero.data.Creation_Sales_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT7_SND;
import trax.aero.pojo.INT7_TRAX;

public class Run implements Runnable {

    Creation_Sales_Data data = null;
    final String url = System.getProperty("CreationSales_URL");
    final int MAX_ATTEMPTS = Integer.parseInt(System.getProperty("CreationSales_MAX_ATTEMPTS", "-1"));
    final long RETRY_INTERVAL = Long.parseLong(System.getProperty("CreationSales_RETRY_INTERVAL", "180")) * 1000; // convert seconds to milliseconds
    Logger logger = LogManager.getLogger("CreationSales");

    public Run() {
        data = new Creation_Sales_Data();
    }

    private void process() {
        Poster poster = new Poster();
        ArrayList<INT7_SND> ArrayReq = new ArrayList<INT7_SND>();
        String executed = "OK";

        try {
            ArrayReq = data.getWorkOrder();

            if (!ArrayReq.isEmpty()) {
                for (INT7_SND ArrayRequest : ArrayReq) {
                    if (!ArrayRequest.getTraxWo().isEmpty()) {
                        logger.info("RUN INFO " + ArrayRequest.getTraxWo());
                    } else {
                        logger.info("RUN INFO: Order list is empty");
                    }

                    boolean success = false;
                    int attempt = 1;

                    while (MAX_ATTEMPTS == -1 || attempt <= MAX_ATTEMPTS) {
                        try {
                            JAXBContext jc = JAXBContext.newInstance(INT7_SND.class);
                            Marshaller marshaller = jc.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                            StringWriter sw = new StringWriter();
                            marshaller.marshal(ArrayRequest, sw);
                            String xmlContent = sw.toString();

                            logger.info("Attempt " + attempt + " to send Work Order: " + ArrayRequest.getTraxWo());
                            logger.info("XML Content: " + xmlContent);

                            // Send the XML content
                            success = poster.post(ArrayRequest, url);
                            if (success) {
                                logger.info("POST successful for Work Order: " + ArrayRequest.getTraxWo());
                                break;
                            } else {
                                if (attempt == MAX_ATTEMPTS) {
                                    logger.warning("Attempt " + attempt + " failed. Maximum attempts reached. No more retries.");
                                } else {
                                    logger.warning("Attempt " + attempt + " failed. Retrying in " + (RETRY_INTERVAL / 1000) + " seconds...");
                                    Thread.sleep(RETRY_INTERVAL);
                                }
                                attempt++;
                            }
                        } catch (Exception e) {
                            logger.severe("Error during attempt " + attempt + ": " + e.getMessage());
                            if (attempt == MAX_ATTEMPTS) {
                                logger.severe("Maximum attempts reached. No more retries.");
                            }
                            attempt++;
                        }
                    }

                    if (!success) {
                        logger.severe("Unable to send XML to URL " + url);
                        Creation_Sales_Controller.addError("Unable to send XML to URL " + url + " after " + (MAX_ATTEMPTS == -1 ? "infinite" : MAX_ATTEMPTS) + " attempts.");
                    } else {
                        logger.info("POST status: true to URL: " + url);
                    }
                }
            }

            if (!Creation_Sales_Controller.getError().isEmpty()) {
                throw new Exception("Issue found");
            }
        } catch (Throwable e) {
            logger.severe(e.toString());
            Creation_Sales_Controller.addError(e.toString());
            Creation_Sales_Controller.sendEmailRequest(ArrayReq);
        }
    }

    public void run() {
        try {
            if (data.lockAvailable("I7")) {
                data.lockTable("I7");
                process();
                data.unlockTable("I7");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
