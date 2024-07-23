package trax.aero.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import trax.aero.logger.LogManager;
import trax.aero.pojo.INT31_SND;
import trax.aero.pojo.INT31_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.OrderSND;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class Actual_Hours_Controller {
	
	EntityManagerFactory factory;
	static String errors = "";
	static Logger logger = LogManager.getLogger("ActualHours");
	static String fromEmail = System.getProperty("fromEmail");
	static String host = System.getProperty("fromHost");
	static String port = System.getProperty("fromPort");
	static String toEmail = System.getProperty("ActualHR_toEmail");
	
	public Actual_Hours_Controller() {
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		factory.createEntityManager();
	}
	
	public static void addError(String error) {
		errors = errors.concat(error + System.lineSeparator() + System.lineSeparator());
	}
	
	public static String getError() {
		return errors;
	}
	
	public static void sendEmailRequest(ArrayList <INT31_SND> arrayReq) {
		if(toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
            return;
		}
		try {
			String requests = "";
			for (INT31_SND or: arrayReq) {
				for(OrderSND r: or.getOrder()) {
						String OrderNO = r.getWoActualTransaction();
						requests = requests + " (Order Number: " + r.getOrderNo() + ", Transaction: " + OrderNO + "),";
 
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
	
	public static void sendEmailResponse(INT31_TRAX response) {
		if(toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
			return;
		}
		try {
			StringBuilder responses = new StringBuilder();
				responses.append("Order Number: ").append(response.getOpsNo()).append(",");
			
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
	
	public static void sendEmailOpsLine(String Operation, INT31_TRAX order, OpsLineEmail opsLineEmails) {
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
				email.setSubject("Failure to update INT 31 Order Number: " + order.getRfoNo() + " Operation: " + order.getOpsNo());
				email.setMsg("Order Number: " + order.getRfoNo() + " Operation: " + order.getOpsNo()+ ", Date & Time of Transaction: " + date  + ", Error Code: " + order.getExceptionId() + ", Remarks: " + order.getExceptionDetail());				
			} else {
				email.setSubject("Failure to update INT 31 Order Number: " + order.getRfoNo() + " Operation: " + order.getOpsNo());
				email.setMsg("Order Number: " + order.getRfoNo() + " Operation: " + order.getOpsNo()+ ", Date & Time of Transaction: " + date  + ", Error Code: " + order.getExceptionId() + ", Remarks: " + order.getExceptionDetail());				
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
