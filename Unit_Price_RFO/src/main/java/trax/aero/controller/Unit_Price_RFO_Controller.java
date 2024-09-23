package trax.aero.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import trax.aero.logger.LogManager;
import trax.aero.pojo.INT27_SND;
import trax.aero.pojo.INT27_TRAX;
import trax.aero.pojo.Operation_SND;
import trax.aero.pojo.OpsLineEmail;

public class Unit_Price_RFO_Controller {
	
	EntityManagerFactory factory;
	static String errors = "";
	static Logger logger = LogManager.getLogger("UnitPrice");
	static String fromEmail = System.getProperty("fromEmail");
	static String host = System.getProperty("fromHost");
	static String port = System.getProperty("fromPort");
	static String toEmail = System.getProperty("UnitPrice_toEmail");
	
	public Unit_Price_RFO_Controller() {
		factory = Persistence.createEntityManagerFactory("TraxESD");
		factory.createEntityManager();
	}
	
	public static void addError(String error) {
		errors = errors.concat(error + System.lineSeparator() + System.lineSeparator());
	}
	
	public static String getError() {
		return errors;
	}
	
	public static void sendEmailRequest(ArrayList <INT27_SND> arrayReq) {
		if(toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
            return;
		}
		try {
			String requests = "";
			for (INT27_SND or: arrayReq) {
				for(Operation_SND r: or.getOperation()) {
						String OrderNO = r.getPN();
						requests = requests + " (Order Number: " + or.getRFO() + ", Operation: " + OrderNO + "),";
 
				}
			}
			ArrayList <String> emailsList = new ArrayList <> (Arrays.asList(toEmail.split(",")));
			Email email = new SimpleEmail();
			email.setHostName(host);
            email.setSmtpPort(Integer.parseInt(port));
            email.setAuthentication("apikey", "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");
            email.setFrom(fromEmail);
            email.setSubject("Actual Hours Interface encountered an Error");
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
	
	public static void sendEmailResponse(INT27_TRAX response) {
		if(toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
			return;
		}
		try {
			StringBuilder responses = new StringBuilder();
				responses.append("Order Number: ").append(response.getOrder_number()).append(",");
			
			ArrayList < String > emailsList = new ArrayList < String > (Arrays.asList(toEmail.split(",")));
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(Integer.valueOf(port));
            email.setAuthentication("apikey", "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");
            email.setFrom(fromEmail);
            email.setSubject("Actual Hours Interface encountered an Error");
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
	
	public static void sendEmailOpsLine(String Operation, INT27_TRAX order, OpsLineEmail opsLineEmails) {
		if (toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Emails address (toEmail) is not configured. Please check the system properties.");
			return;
		}
		try {
			String date = new Date().toString();
			ArrayList<String>emailsList = new ArrayList<String>(Arrays.asList(toEmail.split(",")));
			Email email = new SimpleEmail();
			email.setHostName(host);
			email.setSmtpPort(Integer.valueOf(port));
			email.setAuthentication("apikey", "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");
			email.setFrom(fromEmail);
			if(opsLineEmails.getFlag() != null && !opsLineEmails.getFlag().isEmpty() && (opsLineEmails.getFlag().equalsIgnoreCase("Y") || opsLineEmails.getFlag().equalsIgnoreCase("I"))) {
				email.setSubject("Failure to update INT 27  Order Number: " + order.getOrder_number() + " WO: " + order.getWO());
				email.setMsg("Order Number: " + order.getOrder_number() + " WO: " + order.getWO() + ", Date & Time of Transaction: " + date  + ", Error Code: " + order.getError_code() + ", Remarks: " + order.getRemarks());				
			} else {
				email.setSubject("Failure to update INT 27  Order Number: " + order.getOrder_number() + " WO: " + order.getWO());
				email.setMsg("Order Number: " + order.getOrder_number() + " WO: " + order.getWO() + ", Date & Time of Transaction: " + date  + ", Error Code: " + order.getError_code() + ", Remarks: " + order.getRemarks());				
			}
			for (String emails: emailsList) {
				if(opsLineEmails.getEmail() == null || opsLineEmails.getEmail().isEmpty() || opsLineEmails.getEmail().equalsIgnoreCase("ERROR")) {
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
	            email.setSubject("Actual Hours Interface encountered a Error");
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
