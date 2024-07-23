package trax.aero.controller;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import trax.aero.data.Creation_Sales_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT7_SND;
import trax.aero.pojo.INT7_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.OrderSND;
import trax.aero.utils.DataSourceClient;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class Creation_Sales_Controller {
	EntityManagerFactory factory;
	static String errors = "";
	static Logger logger = LogManager.getLogger("CreationSales");
	static String fromEmail = System.getProperty("fromEmail");
	static String host = System.getProperty("fromHost");
	static String port = System.getProperty("fromPort");
	static String toEmail = System.getProperty("Sales_Creation_toEmail");

	public Creation_Sales_Controller() {
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		factory.createEntityManager();
	}
	
	public static void addError(String error) {
		errors = errors.concat(error + System.lineSeparator() + System.lineSeparator());
	}
	
	public static String getError() {
		return errors;
	}
	
	public static void sendEmailRequest(ArrayList <INT7_SND> arrayReq) {
		if(toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
            return;
		}
		
		try {
			String requests = "";
			for (INT7_SND or: arrayReq) {
					String WO = or.getTraxWo();
				requests = requests + " (WO Number: " + WO + ", Location: " + or.getLocationWO() + ", WO Description: " + or.getTcDescription() + "),";
			}
		
			ArrayList <String> emailsList = new ArrayList <> (Arrays.asList(toEmail.split(",")));
			Email email = new SimpleEmail();
			email.setHostName(host);
            email.setSmtpPort(Integer.parseInt(port));
            email.setAuthentication("apikey", "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");
            email.setFrom(fromEmail);
            email.setSubject("Part Requisition Interface encountered an Error");
            for (String emailAddress: emailsList) {
            	email.addTo(emailAddress.trim());
            }
            email.setMsg("Request that failed: " + requests + " has encountered an issue. Issues found at:\n" + errors);
            email.send();
            logger.info("Email sent successfully to: " + String.join(", ", emailsList));
			
		} catch (Exception e) {
            logger.severe("Failed to send email due to: " + e.toString());
        } finally {
            errors = "";
        }
	}

	
	public static void sendEmailResponse(INT7_TRAX response) {
		if(toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
			return;
		}
		try {
			StringBuilder responses = new StringBuilder();
				responses.append("WO: ").append(response.getWO()).append(",");
			
			ArrayList < String > emailsList = new ArrayList < String > (Arrays.asList(toEmail.split(",")));
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(Integer.valueOf(port));
            email.setAuthentication("apikey", "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");
            email.setFrom(fromEmail);
            email.setSubject("Part Requisition Interface encountered an Error");
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
	
	public static void sendEmailOpsLine(String Operation, INT7_TRAX order, OpsLineEmail opsLineEmails) {
        if (toEmail == null || toEmail.trim().isEmpty()) {
            logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
            return;
        }

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Establish the database connection
            con = DataSourceClient.getConnection();
            logger.info("The connection was established successfully with status: " + !con.isClosed());

            // SQL query to fetch the required data
            String sql = "SELECT W.WO, W.LOCATION, W.WO_DESCRIPTION, WS.PN, WS.PN_SN " +
                         "FROM WO W JOIN WO_SHOP_DETAIL WS ON W.WO = WS.WO " +
                         "WHERE W.WO = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, order.getWO());
            rs = pstmt.executeQuery();

            String wo = null;
            String location = null;
            String woDescription = null;
            String pn = null;
            String pnSn = null;

            if (rs.next()) {
                wo = rs.getString("WO");
                location = rs.getString("LOCATION");
                woDescription = rs.getString("WO_DESCRIPTION");
                pn = rs.getString("PN");
                pnSn = rs.getString("PN_SN");

            } else {
                logger.severe("No data found for WO: " + order.getWO());
                return;
            }

            // Preparing to send email
            String date = new Date().toString();
            ArrayList<String> emailsList = new ArrayList<>(Arrays.asList(toEmail.split(",")));
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(Integer.parseInt(port));
            email.setAuthentication("apikey", "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");
            email.setFrom(fromEmail);

  

            if (!order.getExceptionId().equals("53")) {
                email.setSubject("Interface failed to Create RFO for WO: " + order.getWO());

                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append("WO: ").append(order.getWO()).append(",\n");
                msgBuilder.append("WO Description: ").append(woDescription).append("\n");
                msgBuilder.append("PN: ").append(pn).append("\n");
                msgBuilder.append("SN: ").append(pnSn).append("\n");
                msgBuilder.append("Date & Time of Transaction: ").append(date).append(",\n\n");
                msgBuilder.append("Error Message: ").append(order.getExceptionDetail()).append("\n\n");
                msgBuilder.append("**********************************************************\n");
                msgBuilder.append("* NOTE: This is a system generated email. Do not reply *\n");
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
