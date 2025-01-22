package trax.aero.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import trax.aero.controller.Part_Requisition_Controller;
import trax.aero.data.Part_Requisition_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT13_SND;

public class Run implements Runnable {

    Part_Requisition_Data data = null;
    final String url = System.getProperty("PartREQ_URL");
    
    // Default to infinite attempts (-1) and 3 minutes in seconds
    final int MAX_ATTEMPTS = Integer.parseInt(System.getProperty("PartREQ_MAX_ATTEMPTS", "-1"));
    final long RETRY_INTERVAL = Long.parseLong(System.getProperty("PartREQ_RETRY_INTERVAL", "180")) * 1000; // convert seconds to milliseconds
    
    Logger logger = LogManager.getLogger("Part_REQ");

    public Run() {
        data = new Part_Requisition_Data();
    }

    private void process() {
        Poster poster = new Poster();
        ArrayList<INT13_SND> ArrayReq = new ArrayList<>();
        String executed = "OK";

        try {
            ArrayReq = data.getRequisiton();

            if (!ArrayReq.isEmpty()) {
                for (INT13_SND ArrayRequest : ArrayReq) {
                    if (!ArrayRequest.getOrder().isEmpty()) {
                        logger.info("RUN INFO " + ArrayRequest.getOrder().get(0).getOrderNO());
                    } else {
                        logger.info("RUN INFO: Order list is empty");
                    }

                    boolean success = false;
                    int attempt = 1;

                    while (MAX_ATTEMPTS == -1 || attempt <= MAX_ATTEMPTS) {
                        try {
                            // Generate XML content for each attempt
                            JAXBContext jc = JAXBContext.newInstance(INT13_SND.class);
                            Marshaller marshaller = jc.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                            StringWriter sw = new StringWriter();
                            marshaller.marshal(ArrayRequest, sw);
                            String xmlContent = sw.toString();

                            logger.info("Attempt " + attempt + " to send order: " + ArrayRequest.getOrder().get(0).getOrderNO());
                            logger.info("XML Content: " + xmlContent);

                            // Send the XML content
                            success = poster.post(ArrayRequest, url);

                            if (success) {
                                logger.info("POST successful for Order: " + ArrayRequest.getOrder().get(0).getOrderNO());
                                break;
                            }else {
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
                        Part_Requisition_Controller.addError("Unable to send XML to URL " + url + " after " + (MAX_ATTEMPTS == -1 ? "infinite" : MAX_ATTEMPTS) + " attempts.");
                    } else {
                        logger.info("POST status: true to URL: " + url);
                    }
                }
            }

            if (!Part_Requisition_Controller.getError().isEmpty()) {
                throw new Exception("Issue found");
            }
        } catch (Throwable e) {
            logger.severe(e.toString());
            Part_Requisition_Controller.addError(e.toString());
            Part_Requisition_Controller.sendEmailRequest(ArrayReq);
        }
    }

    public void run() {
        try {
            if (data.lockAvailable("I13")) {
                data.lockTable("I13");
                process();
                data.unlockTable("I13");
            }
        } catch (Exception e) {
            logger.severe("Error in run method: " + e.toString());
        }
    }
}

