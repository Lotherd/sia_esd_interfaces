package trax.aero.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.InstallRemoveSVOController;
import trax.aero.interfaces.IInstallRemoveSvoData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.I19_Request;
import trax.aero.pojo.I19_Response;

public class Run implements Runnable {

    @EJB IInstallRemoveSvoData data;
    final String url = System.getProperty("InstallRemoveSVO_URL");
    final int MAX_ATTEMPTS = Integer.parseInt(System.getProperty("InstallRemoveSVO_MAX_ATTEMPTS", "-1"));
    final long RETRY_INTERVAL = Long.parseLong(System.getProperty("InstallRemoveSVO_RETRY_INTERVAL", "180")) * 1000;
    Logger logger = LogManager.getLogger("InstallRemoveSVO_I19");

    public Run(IInstallRemoveSvoData data) {
        this.data = data;
    }

    private void process() {
        Poster poster = new Poster();
        ArrayList<I19_Request> ArrayRequest = new ArrayList<>();
        ArrayList<I19_Request> ArrayRequestError = new ArrayList<>();
        String executed = "OK";

        try {
            data.openCon();
            ArrayRequest = data.getTransactions();

            if (!ArrayRequest.isEmpty()) {
                for (I19_Request req : ArrayRequest) {
                    boolean success = false;
                    int attempt = 1;

                    while (MAX_ATTEMPTS == -1 || attempt <= MAX_ATTEMPTS) {
                        try {
                            JAXBContext jc = JAXBContext.newInstance(I19_Request.class);
                            Marshaller marshaller = jc.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                            StringWriter sw = new StringWriter();
                            marshaller.marshal(req, sw);
                            String xmlContent = sw.toString();

                            logger.info("Attempt " + attempt + " to send Transaction: " + req.getTransaction());
                            logger.info("XML Content: " + xmlContent);

                            success = poster.post(req, url);

                            if (success) {
                                String body = poster.getBody();
                                StringReader sr = new StringReader(body);
                                jc = JAXBContext.newInstance(I19_Response.class);
                                Unmarshaller unmarshaller = jc.createUnmarshaller();
                                I19_Response input = (I19_Response) unmarshaller.unmarshal(sr);

                                marshaller = jc.createMarshaller();
                                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                                sw = new StringWriter();
                                marshaller.marshal(input, sw);
                                logger.info("Input: " + sw.toString());

                                if (input.getExceptionId() != null && !input.getExceptionDetail().isEmpty() && input.getExceptionId().equalsIgnoreCase("53")) {
                                    data.openCon();
                                    executed = data.markTransaction(input);
                                    data.printCCS(input);
                                } else {
                                    data.setFailed(input);
                                    logger.severe("Received Response with Exception: " + input.getExceptionDetail() + ", Transaction: " + input.getTransaction() + ", Exception ID: " + input.getExceptionId());
                                    data.logError("Received Response with Exception: " + input.getExceptionDetail() + ", Transaction: " + input.getTransaction() + ", Exception ID: " + input.getExceptionId());
                                    InstallRemoveSVOController.addError("Received Response with Exception: " + input.getExceptionDetail() + ", Order Number: " + input.getTransaction() + ", Exception ID: " + input.getExceptionId());
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
                        logger.severe("Unable to send Transaction: " + req.getTransaction() + " to URL " + url);
                        InstallRemoveSVOController.addError("Unable to send Transaction: " + req.getTransaction() + " to URL " + url + " after " + (MAX_ATTEMPTS == -1 ? "infinite" : MAX_ATTEMPTS) + " attempts.");
                        ArrayRequestError.add(req);
                    } else {
                        logger.info("POST status: true Transaction: " + req.getTransaction());
                    }
                }
            }

            if (!InstallRemoveSVOController.getError().isEmpty()) {
                throw new Exception("Issue found");
            }
        } catch (Throwable e) {
            logger.severe(e.toString());
            InstallRemoveSVOController.addError(e.toString());
            InstallRemoveSVOController.sendEmailRequest(ArrayRequestError.isEmpty() ? ArrayRequest : ArrayRequestError);
        } finally {
            try {
                if (data.getCon() != null && !data.getCon().isClosed()) {
                    data.getCon().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        try {
            if (data.lockAvailable("I19")) {
                data.lockTable("I19");
                process();
                data.unlockTable("I19");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
