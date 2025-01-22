package trax.aero.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.ManHours_Item_Controller;
import trax.aero.data.ManHours_Item_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT14_18_SND;
import trax.aero.pojo.INT14_18_TRAX;

public class Run implements Runnable {

    ManHours_Item_Data data = null;
    final String url = System.getProperty("ManHR_URL");
    final int MAX_ATTEMPTS = Integer.parseInt(System.getProperty("ManHR_MAX_ATTEMPTS", "-1"));
    final long RETRY_INTERVAL = Long.parseLong(System.getProperty("ManHR_RETRY_INTERVAL", "180")) * 1000; // convert seconds to milliseconds
    Logger logger = LogManager.getLogger("ManHourItem");

    public Run() {
        data = new ManHours_Item_Data();
    }

    private void process() {
        Poster poster = new Poster();
        ArrayList<INT14_18_SND> ArrayReq = new ArrayList<INT14_18_SND>();
        String executed = "OK";

        try {
            ArrayReq = data.getManHRIT();

            if (!ArrayReq.isEmpty()) {
                for (INT14_18_SND ArrayRequest : ArrayReq) {
                    if (!ArrayRequest.getWO().isEmpty()) {
                        logger.info("RUN INFO " + ArrayRequest.getOperation().get(0).getOPS_NO());
                    } else {
                        logger.info("RUN INFO: Order list is empty");
                    }

                    boolean success = false;
                    int attempt = 1;

                    while (MAX_ATTEMPTS == -1 || attempt <= MAX_ATTEMPTS) {
                        try {
                            JAXBContext jc = JAXBContext.newInstance(INT14_18_SND.class);
                            Marshaller marshaller = jc.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                            StringWriter sw = new StringWriter();
                            marshaller.marshal(ArrayRequest, sw);
                            String xmlContent = sw.toString();

                            logger.info("Attempt " + attempt + " to send Work Order: " + ArrayRequest.getWO());
                            logger.info("XML Content: " + xmlContent);

                            // Send the XML content
                            success = poster.post(ArrayRequest, url);
                            String markSendResult;
                        
  			        	  markSendResult = data.markSendData();
                            if ("OK".equals(markSendResult)) {
        			            success = true;
        			            break;
        			        	  }
                            if (success) {
                                logger.info("POST successful for Work Order: " + ArrayRequest.getWO());

                                // Handle server response
                                String body = poster.getBody();
                                StringReader sr = new StringReader(body);
                                jc = JAXBContext.newInstance(INT14_18_TRAX.class);
                                Unmarshaller unmarshaller = jc.createUnmarshaller();
                                INT14_18_TRAX input = (INT14_18_TRAX) unmarshaller.unmarshal(sr);

                                marshaller = jc.createMarshaller();
                                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                                sw = new StringWriter();
                                marshaller.marshal(input, sw);
                                logger.info("Input: " + sw.toString());

                                if (input.getError_code() != null && !input.getError_code().isEmpty() && input.getError_code().equalsIgnoreCase("53")) {
                                    executed = data.markTransaction(input);
                                } else {
                                    logger.severe("Received Response with Remarks: " + input.getRemarks() + ", Order Number: " + input.getRFO() + ", Error Code: " + input.getError_code());
                                    ManHours_Item_Controller.addError("Received Response with Remarks: " + input.getRemarks() + ", Order Number: " + input.getRFO() + ", Error Code: " + input.getError_code());
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
                        ManHours_Item_Controller.addError("Unable to send XML to URL " + url + " after " + (MAX_ATTEMPTS == -1 ? "infinite" : MAX_ATTEMPTS) + " attempts.");
                    } else {
                        logger.info("POST status: true to URL: " + url);
                    }
                }
            }

            if (!ManHours_Item_Controller.getError().isEmpty()) {
                throw new Exception("Issue found");
            }
        } catch (Throwable e) {
            logger.severe(e.toString());
            ManHours_Item_Controller.addError(e.toString());
            ManHours_Item_Controller.sendEmailRequest(ArrayReq);
        }
    }

    public void run() {
        try {
            if (data.lockAvailable("I1418")) {
                data.lockTable("I1418");
                process();
                data.unlockTable("I1418");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
