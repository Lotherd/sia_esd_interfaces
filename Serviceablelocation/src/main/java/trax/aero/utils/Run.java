package trax.aero.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.ServiceablelocationController;
import trax.aero.data.ServiceablelocationData;
import trax.aero.interfaces.IServiceablelocationData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.MT_TRAX_RCV_I28_4134_RES;
import trax.aero.pojo.MT_TRAX_SND_I28_4134_REQ;

public class Run implements Runnable {

    IServiceablelocationData data = null;
    final String url = System.getProperty("Serviceablelocation_URL");
    final int MAX_ATTEMPTS = Integer.parseInt(System.getProperty("Serviceablelocation_MAX_ATTEMPTS", "-1"));
    final long RETRY_INTERVAL = Long.parseLong(System.getProperty("Serviceablelocation_RETRY_INTERVAL", "180")) * 1000;
    Logger logger = LogManager.getLogger("Serviceablelocation_I28");

    public Run(IServiceablelocationData data) {
        this.data = data;
    }

    private void process() {
        LoopPoster poster = new LoopPoster();
        ArrayList<MT_TRAX_SND_I28_4134_REQ> requests = new ArrayList<>();
        String executed = "OK";

        try {
            data.openCon();
            requests = data.getRequests();

            for (MT_TRAX_SND_I28_4134_REQ request : requests) {
                boolean success = false;
                int attempt = 1;

                while (MAX_ATTEMPTS == -1 || attempt <= MAX_ATTEMPTS) {
                    try {
                        JAXBContext jc = JAXBContext.newInstance(MT_TRAX_SND_I28_4134_REQ.class);
                        Marshaller marshaller = jc.createMarshaller();
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                        StringWriter sw = new StringWriter();
                        marshaller.marshal(request, sw);
                        String xmlContent = sw.toString();

                        logger.info("Attempt " + attempt + " to send RFO: " + request.getRfoNo());
                        logger.info("XML Content: " + xmlContent);

                        success = poster.post(request, url);
                        
                        if (success) {
                            String body = poster.getBody();
                            StringReader sr = new StringReader(body);
                            jc = JAXBContext.newInstance(MT_TRAX_RCV_I28_4134_RES.class);
                            Unmarshaller unmarshaller = jc.createUnmarshaller();
                            MT_TRAX_RCV_I28_4134_RES input = (MT_TRAX_RCV_I28_4134_RES) unmarshaller.unmarshal(sr);

                            marshaller = jc.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                            sw = new StringWriter();
                            marshaller.marshal(input, sw);
                            logger.info("Input: " + sw.toString());

                            if (input.getExceptionId().equalsIgnoreCase("53")) {
                                data.markTransaction(input, request);
                                data.setInspLot(input, request);
                                if (request.getInspLot() != null && !request.getInspLot().isEmpty()) {
                                    data.printLabel(input, request);
                                }
                            } else {
                                data.markTransaction(input, request);
                                data.setComplete(input);
                                executed = ("RFO: " + input.getRfo() + ", Date: " + new Date().toString() + ", SHOP WO: " + input.getWo());
                                logger.severe(executed);
                                ServiceablelocationController.addError(executed);
                                executed = ("Received acknowledgement with Error Code: " + input.getExceptionId() + ", Status Message: " + input.getExceptionDetail());
                                data.logError(executed, input);
                                logger.severe(executed);
                                ServiceablelocationController.addError(executed);
                                executed = "Issue found";
                                ServiceablelocationController.sendEmailACK(input.getWo());
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
                    logger.severe("Unable to send XML with RFO: " + request.getRfoNo() + " to URL " + url);
                    ServiceablelocationController.addError("Unable to send XML with RFO: " + request.getRfoNo() + " to URL " + url);
                    ServiceablelocationController.sendEmail(request.getWo());
                }
            }
        } catch (Throwable e) {
            logger.severe(e.toString());
        } finally {
            try {
                if (data.getCon() != null && !data.getCon().isClosed()) {
                    data.getCon().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            logger.info("finishing");
        }
    }

    public void run() {
        try {
            if (data.lockAvailable("I28")) {
                data.lockTable("I28");
                process();
                data.unlockTable("I28");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
