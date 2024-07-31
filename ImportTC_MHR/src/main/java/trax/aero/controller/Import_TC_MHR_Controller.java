package trax.aero.controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT6_SND;
import trax.aero.pojo.INT6_TRAX;
import trax.aero.pojo.OperationSND;
import trax.aero.pojo.OperationTRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.OrderSND;
import trax.aero.pojo.OrderTRAX;
import trax.aero.utils.DataSourceClient;

public class Import_TC_MHR_Controller {
    EntityManagerFactory factory;
    static String errors = "";
    static Logger logger = LogManager.getLogger("ImportTC_MHR");
    static String fromEmail = System.getProperty("fromEmail");
    static String host = System.getProperty("fromHost");
    static String port = System.getProperty("fromPort");
    static String toEmail = System.getProperty("ImportTC_MHR_toEmail"); //System.getProperty("ImportTC_MHR_toEmail")
    public Import_TC_MHR_Controller() {
        factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
        factory.createEntityManager();
    }
    public static void addError(String error) {
        errors = errors.concat(error + System.lineSeparator() + System.lineSeparator());
    }
    public static String getError() {
        return errors;
    }
    /*public static void sendEMailRequest(ArrayList<INT6_SND> arrayReq) {
	    try {
	      String requests = "";

	      for (INT6_SND req : arrayReq) {
	        for (OrderSND r : req.getOrder()) {
	          for (OperationSND op : r.getOperations()) {
	            String tcNumber = op.getTcNumber();
	            requests =
	              requests +
	              " (Task Card: " +
	              tcNumber +
	              ", WO: " +
	              r.getTraxWO() +
	              "),";
	          }
	        }
	      }

	      ArrayList<String> emailsList = new ArrayList<String>(
	        Arrays.asList(toEmail.split(","))
	      );

	      Email email = new SimpleEmail();
	      email.setHostName(host);
	      email.setSmtpPort(Integer.valueOf(port));
	      email.setFrom(fromEmail);
	      email.setSubject("Import_TC_MHR Interface encountered a Error");

	      for (String emails : emailsList) {
	        email.addTo(emails);
	      }
	      email.setMsg(
	        "Request that failed: " +
	        requests +
	        " has encountered an issue. " +
	        "Issues found at:\n" +
	        errors
	      );
	      email.send();
	    } catch (Exception e) {
	      logger.severe(e.toString());
	      logger.severe("Email not found");
	    } finally {
	      errors = "";
	    }
	  }*/
    public static void sendEMailRequest(ArrayList<INT6_SND> arrayReq) {
        if (toEmail == null || toEmail.trim().isEmpty()) {
            logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
            return;
        }

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DataSourceClient.getConnection();
            logger.info("The connection was established successfully with status: " + !con.isClosed());

            String requests = "";
            for (INT6_SND req : arrayReq) {
                for (OrderSND r : req.getOrder()) {
                    for (OperationSND op : r.getOperations()) {
                        String tcNumber = op.getTcNumber();
                        requests = requests + " (Task Card: " + tcNumber + ", WO: " + r.getTraxWO() + "),";

                        String sql = "SELECT W.WO, W.WO_DESCRIPTION, W.RFO_NO, WS.PN, WS.PN_SN " +
                                "FROM WO W JOIN WO_SHOP_DETAIL WS ON W.WO = WS.WO " +
                                "WHERE W.WO = ?";

                        pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, r.getTraxWO());
                        rs = pstmt.executeQuery();

                        String wo = null;
                        String rfo = null;
                        String woDescription = null;
                        String pn = null;
                        String pnSn = null;

                        if (rs.next()) {
                            wo = rs.getString("WO");
                            rfo = rs.getString("RFO_NO");
                            woDescription = rs.getString("WO_DESCRIPTION");
                            pn = rs.getString("PN");
                            pnSn = rs.getString("PN_SN");
                        } else {
                            logger.severe("No data found for WO: " + r.getTraxWO());
                            return;
                        }

                        String date = new Date().toString();
                        ArrayList<String> emailsList = new ArrayList<>(Arrays.asList(toEmail.split(",")));
                        Email email = new SimpleEmail();
                        email.setHostName(host);
                        email.setSmtpPort(Integer.parseInt(port));
                        email.setAuthentication("apikey", "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");
                        email.setFrom(fromEmail);
                        email.setSubject("Interface failed to Update Order Details for WO: " + r.getTraxWO() + " Task Card: " + op.getTcNumber());

                        StringBuilder msgBuilder = new StringBuilder();
                        msgBuilder.append("WO: ").append(wo).append(",\n");
                        msgBuilder.append("WO Description: ").append(woDescription).append("\n");
                        msgBuilder.append("Task Card: ").append(tcNumber).append("\n");
                        msgBuilder.append("RFO: ").append(rfo).append("\n");
                        msgBuilder.append("PN: ").append(pn).append("\n");
                        msgBuilder.append("SN: ").append(pnSn).append("\n");
                        msgBuilder.append("Date & Time of Transaction: ").append(date).append(",\n\n");
                        msgBuilder.append("Error Message: ").append(errors).append("\n\n");
                        msgBuilder.append("**********************************************************\n");
                        msgBuilder.append("*NOTE: This is a system generated email. Do not reply*\n");
                        msgBuilder.append("**********************************************************");

                        email.setMsg(msgBuilder.toString());

                        for (String emailAddress : emailsList) {
                            email.addTo(emailAddress.trim());
                        }

                        email.send();
                        logger.info("Email sent successfully to: " + String.join(", ", emailsList));
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("Failed to send email due to: " + e.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.severe("Failed to close ResultSet: " + e.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.severe("Failed to close PreparedStatement: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    logger.severe("Failed to close Connection: " + e.getMessage());
                }
            }
            errors = "";
        }
    }

    public static void sendEmailResponse(INT6_TRAX response) {
    	  if (toEmail == null || toEmail.trim().isEmpty()) {
    	        logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
    	        return;
    	    }
        try {
            StringBuilder responses = new StringBuilder();
            for (OrderTRAX r: response.getOrder()) {
                responses.append("SAP Order Number: ").append(r.getRfoNo()).append(",");
            }
            ArrayList < String > emailsList = new ArrayList < String > (Arrays.asList(toEmail.split(",")));
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(Integer.valueOf(port));
            email.setAuthentication("apikey", "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");
            email.setFrom(fromEmail);
            email.setSubject("Import_TC_MHR Interface encountered a Error");
            for (String emails: emailsList) {
                email.addTo(emails);
            }
            email.setMsg("Responses that failed: " + responses + " has encountered an issue. " + "Enter records manually. " + "Issues found at:\n" + errors);
            email.send();
            logger.info("Email sent successfully to: " + String.join(", ", emailsList));
        } catch (Exception e) {
            logger.severe(e.toString());
            logger.severe("Email not found");
        } finally {
            errors = "";
        }
    }
    public static void sendEmailOpsLine(String OperationNumber, OrderTRAX order, OperationTRAX operation, OpsLineEmail opsLineEmails) {
        if (toEmail == null || toEmail.trim().isEmpty()) {
            logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
            return;
        }
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        
        
        try {
        	con = DataSourceClient.getConnection();
            logger.info("The connection was established successfully with status: " + !con.isClosed());
            
            String sql ="SELECT W.WO, W.WO_DESCRIPTION, W.RFO_NO, WS.PN, WS.PN_SN " +
            			"FROM WO W JOIN WO_SHOP_DETAIL WS ON W.WO = WS.WO " +
            			"WHERE W.WO = ?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, order.getWo());
            rs = pstmt.executeQuery();
            
            String wo = null;
            String rfo = null;
            String woDescription = null;
            String pn = null;
            String pnSn = null;
            
            if (rs.next()) {
                wo = rs.getString("WO");
                rfo = rs.getString("RFO_NO");
                woDescription = rs.getString("WO_DESCRIPTION");
                pn = rs.getString("PN");
                pnSn = rs.getString("PN_SN");

            }else {
                logger.severe("No data found for WO: " + order.getWo());
                return;
            }
        	
            String date = new Date().toString();
            ArrayList<String> emailsList = new ArrayList<String>(Arrays.asList(toEmail.split(",")));
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(Integer.valueOf(port));
            email.setAuthentication("apikey", "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");
            email.setFrom(fromEmail);
            
        

            if (!"53".equals(order.getExceptionId())) {
            email.setSubject("Interface failed to Update Order Details for WO: " + order.getWo() + " Task Card: " + operation.getTaskCard());

            
            StringBuilder msgBuilder = new StringBuilder();
            msgBuilder.append("WO: ").append(order.getWo()).append(",\n");
            msgBuilder.append("WO Description: ").append(woDescription).append("\n");
            msgBuilder.append("Task Card: ").append(operation.getTaskCard()).append("\n");
            msgBuilder.append("RFO: ").append(order.getRfoNo()).append("\n");
            msgBuilder.append("PN: ").append(pn).append("\n");
            msgBuilder.append("SN: ").append(pnSn).append("\n");
            msgBuilder.append("Date & Time of Transaction: ").append(date).append(",\n\n");
            msgBuilder.append("Error Message: ").append(order.getExceptionDetail()).append("\n\n");
            msgBuilder.append("**********************************************************\n");
            msgBuilder.append("*NOTE: This is a system generated email. Do not reply*\n");
            msgBuilder.append("**********************************************************");

            email.setMsg(msgBuilder.toString());
            }
            
            for (String emails : emailsList) {
                if (opsLineEmails.getEmail() == null || opsLineEmails.getEmail().isEmpty() || opsLineEmails.getEmail().equalsIgnoreCase("ERROR")) {
                    email.addTo(emails);
                } else {
                    email.addTo(opsLineEmails.getEmail());
                }
            }
            
            for (String emails : emailsList) {
                if (opsLineEmails.getEmail() == null || opsLineEmails.getEmail().isEmpty() || opsLineEmails.getEmail().equalsIgnoreCase("ERROR")) {
                    email.addTo(emails);
                } else {
                    email.addTo(opsLineEmails.getEmail());
                }
            }
            email.send();
            logger.info("Email sent successfully to: " + String.join(", ", emailsList));
        } catch (Exception e) {
            logger.severe(e.toString());
            logger.severe("Email not found");
        } finally {
            // Close resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.severe("Failed to close ResultSet: " + e.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.severe("Failed to close PreparedStatement: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    logger.severe("Failed to close Connection: " + e.getMessage());
                }
            }
            errors = "";
        }
    }

    public static void sendEmailService(String outcome) {
    	if (toEmail == null || toEmail.trim().isEmpty()) {
            logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
            return;
        }
        try {
            ArrayList < String > emailsList = new ArrayList < String > (Arrays.asList(toEmail.split(",")));
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(Integer.valueOf(port));
            email.setAuthentication("apikey", "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");
            email.setFrom(fromEmail);
            email.setSubject("Import_TC_MHR Interface encountered a Error");
            for (String emails: emailsList) {
                email.addTo(emails);
            }
            email.setMsg("Input" + " has encountered an issue. " + "Enter records manually. " + "Issues found at:\n" + errors);
            email.send();
        } catch (Exception e) {
            logger.severe(e.toString());
            logger.severe("Email not found");
        } finally {
            errors = "";
        }
    }
}