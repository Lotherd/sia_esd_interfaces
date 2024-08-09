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
        ArrayList<INT15_SND> ArrayReq = new ArrayList<>();
        String executed = "OK";

        try {
            ArrayReq.addAll(data.getSVO());
            ArrayReq.addAll(data.getRFO());
            String markSendResult;
            boolean success = false;

            if (!ArrayReq.isEmpty()) {
                for (INT15_SND ArrayRequest : ArrayReq) {
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
                        success = poster.post(ArrayRequest, url);
                        markSendResult = data.markSendData();
                        if ("OK".equals(markSendResult)) {
                            success = true;
                            break;
                        }
                    }

                    if (!success) {
                        logger.severe("Unable to send XML to URL " + url);
                        TECO_Handling_Controller.addError("Unable to send XML to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
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
                            logger.info("Input: " + sw.toString());
                            if (input.getExceptionId() != null && !input.getExceptionId().isEmpty() && input.getExceptionId().equalsIgnoreCase("53")) {
                                executed = data.markTransaction(input);
                            } else {
                                logger.severe("Received Response with Remarks: " + input.getExceptionDetail() + ", Order Number: " + input.getRFO_NO() + ", Error Code: " + input.getExceptionId());
                                TECO_Handling_Controller.addError("Received Response with Remarks: " + input.getExceptionDetail() + ", Order Number: " + input.getRFO_NO() + ", Error Code: " + input.getExceptionId());
                                executed = data.markTransaction(input);
                                executed = "Issue found";
                            }
                            if (executed == null || !executed.equalsIgnoreCase("OK")) {
                                executed = "Issue found";
                                throw new Exception("Issue found");
                            }
                        } catch (Exception e) {
                            TECO_Handling_Controller.addError(e.toString());
                            TECO_Handling_Controller.sendEmailRequest(ArrayReq);
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
            logger.severe(e.toString());
            TECO_Handling_Controller.addError(e.toString());
            TECO_Handling_Controller.sendEmailRequest(ArrayReq);
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
