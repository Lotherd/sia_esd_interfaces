package trax.aero.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.TECO_Handling_Controller;
import trax.aero.data.TECO_Handling_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT15_SND;
import trax.aero.pojo.INT15_TRAX;

public class Run implements Runnable {

    TECO_Handling_Data data = null;
    final String url = System.getProperty("TECO_url");
    final int MAX_ATTEMPTS = 3;
    Logger logger = LogManager.getLogger("TECO_Handling");

    public Run() {
        data = new TECO_Handling_Data();
    }

    public void process() {
        Poster poster = new Poster();
        ArrayList<INT15_SND> ArrayReqSVO = new ArrayList<>();
        ArrayList<INT15_SND> ArrayReqRFO = new ArrayList<>();
        String executed = "OK";

        try {
            ArrayReqSVO = data.getSVO();
            ArrayReqRFO = data.getRFO();
            String markSendResult;
            boolean success = false;

            if (!ArrayReqSVO.isEmpty()) {
                for (INT15_SND ArrayRequest : ArrayReqSVO) {
                    logger.info("Processing SVO request with SAP number: " + ArrayRequest.getSAP_number());

                    if (!ArrayRequest.getSAP_number().isEmpty()) {
                        logger.info("RUN INFO " + ArrayRequest.getSAP_number());
                    } else {
                        logger.info("RUN INFO: List is empty");
                    }

                    JAXBContext jc = JAXBContext.newInstance(INT15_SND.class);
                    Marshaller marshaller = jc.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                    StringWriter sw = new StringWriter();
                    marshaller.marshal(ArrayRequest, sw);

                    logger.info("Output: " + sw.toString());

                    for (int i = 0; i < MAX_ATTEMPTS; i++) {
                        //logger.info("Attempt " + (i + 1) + " to post XML.");
                        success = poster.post(ArrayRequest, url);
                        markSendResult = data.markSendData();
                        //logger.info("Mark Send Data Result: " + markSendResult);
                        if ("OK".equals(markSendResult)) {
                            success = true;
                            break;
                        }
                    }

                    success = poster.post(ArrayRequest, url);

                    if (!success) {
                        logger.severe("Unable to send XML to URL " + url);
                        TECO_Handling_Controller.addError("Unable to send XML to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
                    } else {
                        INT15_TRAX input = null;
                        try {
                            String body = poster.getBody();
                            logger.info("Received body: " + body);
                            StringReader sr = new StringReader(body);
                            jc = JAXBContext.newInstance(INT15_TRAX.class);
                            Unmarshaller unmarshaller = jc.createUnmarshaller();
                            input = (INT15_TRAX) unmarshalWithLogging(unmarshaller, sr);

                            if (input != null) {
                                marshaller = jc.createMarshaller();
                                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                                sw = new StringWriter();
                                marshaller.marshal(input, sw);
                                logger.info("Input: " + sw.toString());
                            } else {
                                logger.severe("Unmarshalling resulted in null object.");
                            }

                            if (input != null && input.getExceptionId() != null && !input.getExceptionId().isEmpty() && input.getExceptionId().equalsIgnoreCase("53")) {
                                executed = data.markTransaction(input);
                                logger.info("Transaction marked successfully.");
                            } else {
                                //logger.severe("Received Response with Remarks: " + (input != null ? input.getExceptionDetail() : "null") + ", Order Number: " + (input != null ? input.getRFO_NO() : "null") + ", Error Code: " + (input != null ? input.getExceptionId() : "null"));
                                TECO_Handling_Controller.addError("Received Response with Remarks: " + (input != null ? input.getExceptionDetail() : "null") + ", Order Number: " + (input != null ? input.getRFO_NO() : "null") + ", Error Code: " + (input != null ? input.getExceptionId() : "null"));
                                executed = data.markTransaction(input);
                                executed = "Issue found";
                            }

                            if (executed == null || !executed.equalsIgnoreCase("OK")) {
                                executed = "Issue found";
                                throw new Exception("Issue found");
                            }
                        } catch (Exception e) {
                            //logger.severe("Exception during transaction processing 1: " + e.getMessage());
                            TECO_Handling_Controller.addError(e.toString());
                            TECO_Handling_Controller.sendEmailRequest(ArrayReqSVO);
                        } finally {
                            logger.info("finishing");
                        }
                    }
                }
            }

            if (!ArrayReqRFO.isEmpty()) {
                for (INT15_SND ArrayRequest : ArrayReqRFO) {
                    logger.info("Processing RFO request with SAP number: " + ArrayRequest.getSAP_number());

                    if (!ArrayRequest.getSAP_number().isEmpty()) {
                        logger.info("RUN INFO " + ArrayRequest.getSAP_number());
                    } else {
                        logger.info("RUN INFO: List is empty");
                    }

                    JAXBContext jc = JAXBContext.newInstance(INT15_SND.class);
                    Marshaller marshaller = jc.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                    StringWriter sw = new StringWriter();
                    marshaller.marshal(ArrayRequest, sw);

                    logger.info("Output: " + sw.toString());

                    for (int i = 0; i < MAX_ATTEMPTS; i++) {
                        logger.info("Attempt " + (i + 1) + " to post XML.");
                        success = poster.post(ArrayRequest, url);
                        markSendResult = data.markSendData();
                        logger.info("Mark Send Data Result: " + markSendResult);
                        if ("OK".equals(markSendResult)) {
                            success = true;
                            break;
                        }
                    }

                    success = poster.post(ArrayRequest, url);

                    if (!success) {
                        logger.severe("Unable to send XML to URL " + url);
                        TECO_Handling_Controller.addError("Unable to send XML to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
                    } else {
                        INT15_TRAX input = null;
                        try {
                            String body = poster.getBody();
                            logger.info("Received body: " + body);
                            StringReader sr = new StringReader(body);
                            jc = JAXBContext.newInstance(INT15_TRAX.class);
                            Unmarshaller unmarshaller = jc.createUnmarshaller();
                            input = (INT15_TRAX) unmarshalWithLogging(unmarshaller, sr);

                            if (input != null) {
                                marshaller = jc.createMarshaller();
                                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                                sw = new StringWriter();
                                marshaller.marshal(input, sw);
                               logger.info("Input: " + sw.toString());
                            } else {
                                logger.severe("Unmarshalling resulted in null object.");
                            }

                            if (input != null && input.getExceptionId() != null && !input.getExceptionId().isEmpty() && input.getExceptionId().equalsIgnoreCase("53")) {
                                executed = data.markTransaction(input);
                                logger.info("Transaction marked successfully.");
                            } else {
                               // logger.severe("Received Response with Remarks: " + (input != null ? input.getExceptionDetail() : "null") + ", Order Number: " + (input != null ? input.getRFO_NO() : "null") + ", Error Code: " + (input != null ? input.getExceptionId() : "null"));
                                TECO_Handling_Controller.addError("Received Response with Remarks: " + (input != null ? input.getExceptionDetail() : "null") + ", Order Number: " + (input != null ? input.getRFO_NO() : "null") + ", Error Code: " + (input != null ? input.getExceptionId() : "null"));
                                executed = data.markTransaction(input);
                                executed = "Issue found";
                            }

                            if (executed == null || !executed.equalsIgnoreCase("OK")) {
                                executed = "Issue found";
                                throw new Exception("Issue found");
                            }
                        } catch (Exception e) {
                           // logger.severe("Exception during transaction processing 2: " + e.getMessage());
                            TECO_Handling_Controller.addError(e.toString());
                            TECO_Handling_Controller.sendEmailRequest(ArrayReqRFO);
                        } finally {
                            logger.info("finishing");
                        }
                    }
                }
            }
            if (!TECO_Handling_Controller.getError().isEmpty()) {
                throw new Exception("Issue found");
            }

        } catch (Throwable e) {
            logger.severe("Throwable caught: " + e.toString());
            TECO_Handling_Controller.addError(e.toString());
            TECO_Handling_Controller.sendEmailRequest(ArrayReqSVO);
            TECO_Handling_Controller.sendEmailRequest(ArrayReqRFO);
        }
    }

    public void run() {
        try {
            //logger.info("Checking if lock is available for I15.");
            if (data.lockAvailable("I15")) {
               // logger.info("Lock is available, locking table I15.");
                data.lockTable("I15");
                process();
               // logger.info("Unlocking table I15.");
                data.unlockTable("I15");
            }
        } catch (Exception e) {
            logger.severe("Exception in run method: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Object unmarshalWithLogging(Unmarshaller unmarshaller, StringReader sr) throws Exception {
        try {
            return unmarshaller.unmarshal(sr);
        } catch (Exception e) {
            logger.severe("Error during unmarshalling: " + e.getMessage());
            throw new Exception("Unmarshalling error: " + e.getMessage(), e);
        }
    }
}
