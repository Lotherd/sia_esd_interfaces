package trax.aero.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    private static Map<String, Integer> attemptCounts = new HashMap<>();
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
        Set<String> pendingWOs = new HashSet<>();  // Set to store WOs waiting for 53 code

        try {
            svoRequests.addAll(data.getSVO());

            if (!svoRequests.isEmpty()) {
                // Process SVO records
                for (INT15_SND svoRequest : svoRequests) {
                    logger.info("RUN INFO (SVO) " + svoRequest.getSAP_number());

                    pendingWOs.add(svoRequest.getWO());  // Mark WO as pending

                    try {
                        boolean success = sendSVORequest(svoRequest, poster);

                        if (success) {
                            INT15_TRAX input = receiveResponse(poster, INT15_TRAX.class);

                            if (input != null && "53".equalsIgnoreCase(input.getExceptionId())) {
                                pendingWOs.remove(svoRequest.getWO());  // Remove from pending on success
                                executed = data.markTransaction(input);
                                if (!"OK".equalsIgnoreCase(executed)) {
                                    throw new Exception("Error processing SVO with ExceptionId 53.");
                                }
                            } else if (input != null && "51".equalsIgnoreCase(input.getExceptionId())) {
                                logger.severe("SVO failed with ExceptionId 51 for WO: " + svoRequest.getWO());
                                // Handle the error without throwing exception to continue processing other SVOs
                                data.markTransaction(input);
                            }
                        } else {
                            throw new Exception("Failed to send SVO request.");
                        }
                    } catch (Exception e) {
                        logger.severe("Error processing SVO: " + svoRequest.getWO() + " - " + e.toString());
                        // Log the error but continue with the next SVO
                    }
                }
            }

            //logger.info("SVO processing completed, now processing RFO...");
            rfoRequests.addAll(data.getRFO());
            processRFORequests(rfoRequests, poster, pendingWOs);

        } catch (Exception e) {
            logger.severe(e.toString());
            TECO_Handling_Controller.addError(e.toString());
            TECO_Handling_Controller.sendEmailRequest(svoRequests.isEmpty() ? rfoRequests : svoRequests);
        } finally {
           // logger.info("Processing finished.");
        }
    }

    private void processRFORequests(ArrayList<INT15_SND> rfoRequests, Poster poster, Set<String> pendingWOs) throws Exception {
        for (INT15_SND rfoRequest : rfoRequests) {
            // Only process RFO if its WO is not pending confirmation
            if (!pendingWOs.contains(rfoRequest.getWO())) {
                logger.info("Processing RFO: " + rfoRequest.getSAP_number());

                boolean success = sendRFORequest(rfoRequest, poster);

                if (success) {
                    INT15_TRAX input = receiveResponse(poster, INT15_TRAX.class);

                    if (input != null && "53".equalsIgnoreCase(input.getExceptionId())) {
                        String executed = data.markTransaction(input);
                        if (!"OK".equalsIgnoreCase(executed)) {
                            throw new Exception("Error processing RFO with ExceptionId 53.");
                        }
                    } else if (input != null && "51".equalsIgnoreCase(input.getExceptionId())) {
                        logger.severe("RFO failed with ExceptionId 51 for WO: " + rfoRequest.getWO());
                       
                        data.markTransaction(input);
                    } else {
                        throw new Exception("Received RFO Response with Error or unexpected ExceptionId.");
                    }
                } else {
                    throw new Exception("Failed to send RFO request.");
                }
            } else {
                logger.info("Skipping RFO for WO: " + rfoRequest.getWO() + " as it is still waiting for SVO confirmation.");
            }
        }
    }

    private boolean sendSVORequest(INT15_SND svoRequest, Poster poster) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(INT15_SND.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(svoRequest, sw);

        logger.info("Output (SVO): " + sw.toString());

        boolean success = poster.post(svoRequest, url);
        if (!success) {
            throw new Exception("Failed to send SVO request.");
        }

        return success;
    }

    private boolean sendRFORequest(INT15_SND rfoRequest, Poster poster) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(INT15_SND.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(rfoRequest, sw);

        logger.info("Output (RFO): " + sw.toString());

        boolean success = poster.post(rfoRequest, url);
        if (!success) {
            throw new Exception("Failed to send RFO request.");
        }

        return success;
    }

    private <T> T receiveResponse(Poster poster, Class<T> clazz) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        String body = poster.getBody();

        // Check if the body has invalid characters or leading whitespace
        if (body != null && body.trim().startsWith("<?xml")) {
            StringReader sr = new StringReader(body.trim());
            T input = (T) unmarshaller.unmarshal(sr);

            StringWriter sw = new StringWriter();
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(input, sw);
            logger.info("Input: " + sw.toString());

            return input;
        } else {
            throw new Exception("Invalid XML format received.");
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
