package trax.aero.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.Unit_Price_RFO_Controller;
import trax.aero.data.Unit_Price_RFO_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT27_SND;
import trax.aero.pojo.INT27_TRAX;

public class Run implements Runnable {

    Unit_Price_RFO_Data data = null;
    final String url = System.getProperty("UnitPriceURL");
    final int MAX_ATTEMPTS = Integer.parseInt(System.getProperty("UnitPrice_MAX_ATTEMPTS", "-1"));
    final long RETRY_INTERVAL = Long.parseLong(System.getProperty("UnitPrice_RETRY_INTERVAL", "180")) * 1000;
    Logger logger = LogManager.getLogger("UnitPrice");

    public Run() {
        data = new Unit_Price_RFO_Data();
    }

    private void process() {
        Poster poster = new Poster();
        ArrayList<INT27_SND> ArrayReq = new ArrayList<>();
        String executed = "OK";

        try {
            ArrayReq = data.getPrice();
            boolean success = false;

            if (!ArrayReq.isEmpty()) {
                for (INT27_SND ArrayRequest : ArrayReq) {
                    if (!ArrayRequest.getWO().isEmpty()) {
                        logger.info("RUN INFO " + ArrayRequest.getOperation().get(0).getBatch());
                    } else {
                        logger.info("RUN INFO: Order list is empty");
                    }

                    int attempt = 1;

                    while (MAX_ATTEMPTS == -1 || attempt <= MAX_ATTEMPTS) {
                        try {
                            JAXBContext jc = JAXBContext.newInstance(INT27_SND.class);
                            Marshaller marshaller = jc.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                            StringWriter sw = new StringWriter();
                            marshaller.marshal(ArrayRequest, sw);
                            String xmlContent = sw.toString();

                            logger.info("Attempt " + attempt + " to send WO: " + ArrayRequest.getWO());
                            logger.info("XML Content: " + xmlContent);

                            success = poster.post(ArrayRequest, url);
                            String markSendResult = data.markSendData();

                            if ("OK".equals(markSendResult)) {
                                success = true;
                                break;
                            }

                            if (success) {
                                logger.info("POST successful for WO: " + ArrayRequest.getWO());

                                String body = poster.getBody();
                                StringReader sr = new StringReader(body);
                                jc = JAXBContext.newInstance(INT27_TRAX.class);
                                Unmarshaller unmarshaller = jc.createUnmarshaller();
                                INT27_TRAX input = (INT27_TRAX) unmarshaller.unmarshal(sr);

                                marshaller = jc.createMarshaller();
                                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                                sw = new StringWriter();
                                marshaller.marshal(input, sw);
                                logger.info("Input: " + sw.toString());

                                if (input.getError_code() != null && !input.getError_code().isEmpty() && input.getError_code().equalsIgnoreCase("53")) {
                                    executed = data.markTransaction(input);
                                } else {
                                    logger.severe("Received Response with Remarks: " + input.getRemarks() + ", WO: " + input.getWO() + ", Error Code: " + input.getError_code());
                                    Unit_Price_RFO_Controller.addError("Received Response with Remarks: " + input.getRemarks() + ", WO: " + input.getWO() + ", Error Code: " + input.getError_code());
                                    executed = data.markTransaction(input);
                                    executed = "Issue found";
                                }

                                if (executed == null || !executed.equalsIgnoreCase("OK")) {
                                    executed = "Issue found";
                                    throw new Exception("Issue found");
                                }
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
                        Unit_Price_RFO_Controller.addError("Unable to send XML to URL " + url + " after " + (MAX_ATTEMPTS == -1 ? "infinite" : MAX_ATTEMPTS) + " attempts.");
                    } else {
                        logger.info("POST status: true to URL: " + url);
                    }
                }
            }

            if (!Unit_Price_RFO_Controller.getError().isEmpty()) {
                throw new Exception("Issue found");
            }
        } catch (Throwable e) {
            logger.severe(e.toString());
            Unit_Price_RFO_Controller.addError(e.toString());
            Unit_Price_RFO_Controller.sendEmailRequest(ArrayReq);
        }
    }

    public void run() {
        try {
            if (data.lockAvailable("I27")) {
                data.lockTable("I27");
                process();
                data.unlockTable("I27");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
