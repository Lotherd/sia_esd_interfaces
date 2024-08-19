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
    final int MAX_ATTEMPTS = 1;
    Logger logger = LogManager.getLogger("TECO_Handling");

    public Run() {
        data = new TECO_Handling_Data();
    }

    public void process() {
        Poster poster = new Poster();
        ArrayList<INT15_SND> svoRequests = new ArrayList<>();
        ArrayList<INT15_SND> rfoRequests = new ArrayList<>();
        String executed = "OK";

        try {
            svoRequests.addAll(data.getSVO());

            boolean svoSuccess = true;
            boolean rfoProcessed = false;

            // Procesar getSVO primero
            if (!svoRequests.isEmpty()) {
                for (INT15_SND svoRequest : svoRequests) {
                    if (!svoRequest.getSAP_number().isEmpty()) {
                        logger.info("RUN INFO (SVO) " + svoRequest.getSAP_number());
                    } else {
                        logger.info("RUN INFO: SVO List is empty");
                    }

                    JAXBContext jc = JAXBContext.newInstance(INT15_SND.class);
                    Marshaller marshaller = jc.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                    StringWriter sw = new StringWriter();
                    marshaller.marshal(svoRequest, sw);

                    logger.info("Output (SVO): " + sw.toString());

                    boolean success = false;
                    for (int i = 0; i < MAX_ATTEMPTS; i++) {
                        success = poster.post(svoRequest, url);
                        String markSendResult = data.markSendData();
                        if ("OK".equals(markSendResult)) {
                            success = true;
                            break;
                        }
                    }

                    if (!success) {
                        logger.severe("Unable to send SVO XML to URL " + url);
                        TECO_Handling_Controller.addError("Unable to send SVO XML to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
                        svoSuccess = false;
                        break;
                    } else {
                        INT15_TRAX input = null;
                        try {
                            String body = poster.getBody();
                            StringReader sr = new StringReader(body);
                            jc = JAXBContext.newInstance(INT15_TRAX.class);
                            Unmarshaller unmarshaller = jc.createUnmarshaller();
                            input = (INT15_TRAX) unmarshaller.unmarshal(sr);

                            marshaller = jc.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                            sw = new StringWriter();
                            marshaller.marshal(input, sw);
                            logger.info("Input (SVO): " + sw.toString());

                            if (input.getExceptionId() != null && !input.getExceptionId().isEmpty() && input.getExceptionId().equalsIgnoreCase("53")) {
                                executed = data.markTransaction(input);
                            } else {
                                logger.severe("Received SVO Response with Remarks: " + input.getExceptionDetail() + ", Order Number: " + input.getRFO_NO() + ", Error Code: " + input.getExceptionId());
                                TECO_Handling_Controller.addError("Received SVO Response with Remarks: " + input.getExceptionDetail() + ", Order Number: " + input.getRFO_NO() + ", Error Code: " + input.getExceptionId());
                                executed = data.markTransaction(input);
                                executed = "Issue found";
                                svoSuccess = false;
                                break;
                            }

                            if (executed == null || !executed.equalsIgnoreCase("OK")) {
                                executed = "Issue found";
                                throw new Exception("Issue found");
                            }
                        } catch (Exception e) {
                            TECO_Handling_Controller.addError(e.toString());
                            TECO_Handling_Controller.sendEmailRequest(svoRequests);
                            svoSuccess = false;
                            break;
                        } finally {
                            logger.info("SVO processing finished");
                        }
                    }
                }
            }

            // Si getSVO fue exitoso o no tenía elementos, procesar getRFO
            if (svoSuccess) {
                rfoRequests.addAll(data.getRFO());
                if (!rfoRequests.isEmpty()) {
                    for (INT15_SND rfoRequest : rfoRequests) {
                        if (!rfoRequest.getSAP_number().isEmpty()) {
                            logger.info("RUN INFO (RFO) " + rfoRequest.getSAP_number());
                        } else {
                            logger.info("RUN INFO: RFO List is empty");
                        }

                        JAXBContext jc = JAXBContext.newInstance(INT15_SND.class);
                        Marshaller marshaller = jc.createMarshaller();
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                        StringWriter sw = new StringWriter();
                        marshaller.marshal(rfoRequest, sw);

                        logger.info("Output (RFO): " + sw.toString());

                        boolean success = false;
                        for (int i = 0; i < MAX_ATTEMPTS; i++) {
                            success = poster.post(rfoRequest, url);
                            String markSendResult = data.markSendData();
                            if ("OK".equals(markSendResult)) {
                                success = true;
                                break;
                            }
                        }

                        if (!success) {
                            logger.severe("Unable to send RFO XML to URL " + url);
                            TECO_Handling_Controller.addError("Unable to send RFO XML to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
                            rfoProcessed = false;
                        } else {
                            INT15_TRAX input = null;
                            try {
                                String body = poster.getBody();
                                StringReader sr = new StringReader(body);
                                jc = JAXBContext.newInstance(INT15_TRAX.class);
                                Unmarshaller unmarshaller = jc.createUnmarshaller();
                                input = (INT15_TRAX) unmarshaller.unmarshal(sr);

                                marshaller = jc.createMarshaller();
                                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                                sw = new StringWriter();
                                marshaller.marshal(input, sw);
                                logger.info("Input (RFO): " + sw.toString());

                                if (input.getExceptionId() != null && !input.getExceptionId().isEmpty() && input.getExceptionId().equalsIgnoreCase("53")) {
                                    executed = data.markTransaction(input);
                                } else {
                                    logger.severe("Received RFO Response with Remarks: " + input.getExceptionDetail() + ", Order Number: " + input.getRFO_NO() + ", Error Code: " + input.getExceptionId());
                                    TECO_Handling_Controller.addError("Received RFO Response with Remarks: " + input.getExceptionDetail() + ", Order Number: " + input.getRFO_NO() + ", Error Code: " + input.getExceptionId());
                                    executed = data.markTransaction(input);
                                    executed = "Issue found";
                                }

                                if (executed == null || !executed.equalsIgnoreCase("OK")) {
                                    executed = "Issue found";
                                    throw new Exception("Issue found");
                                }
                            } catch (Exception e) {
                                TECO_Handling_Controller.addError(e.toString());
                                TECO_Handling_Controller.sendEmailRequest(rfoRequests);
                                rfoProcessed = false;
                            } finally {
                                logger.info("RFO processing finished");
                            }
                        }
                    }
                }
            }

            if (!TECO_Handling_Controller.getError().isEmpty()) {
                throw new Exception("Issue found");
            }

        } catch (Throwable e) {
            logger.severe(e.toString());
            TECO_Handling_Controller.addError(e.toString());
            TECO_Handling_Controller.sendEmailRequest(svoRequests.isEmpty() ? rfoRequests : svoRequests);
        }
    }


    public void run() {
        try {
            if (data.lockAvailable("I15")) {
                data.lockTable("I15");
                process();
                data.unlockTable("I15");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}