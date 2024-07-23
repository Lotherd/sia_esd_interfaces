package trax.aero.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import trax.aero.logger.LogManager;
import trax.aero.pojo.INT30_SND;
import trax.aero.pojo.INT30_TRAX;
import trax.aero.pojo.OpsLineEmail;

public class CreationBatch_Controller {
	
	EntityManagerFactory factory;
	static String errors = "";
	static Logger logger = LogManager.getLogger("BatchCreation");
	static String fromEmail = System.getProperty("fromEmail");
	static String host = System.getProperty("fromHost");
	static String port = System.getProperty("fromPort");
	static String toEmail = System.getProperty("BatchCreation_toEmail");
	
	public CreationBatch_Controller() {
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		factory.createEntityManager();
	}
	
	public static void addError(String error) {
		errors = errors.concat(error + System.lineSeparator() + System.lineSeparator());
	}
	
	public static String getError() {
		return errors;
	}
	
	public static void sendEmailRequest(ArrayList <INT30_SND> arrayReq) {
		if(toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
            return;
		}
		
		try {
			String requests = "";
			for (INT30_SND or: arrayReq) {
					String PN = or.getPN();
				requests = requests + " (Part Number : " + PN  + "),";
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
	
	public static void sendEmailResponse(INT30_TRAX response) {
		if(toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
			return;
		}
		try {
			StringBuilder responses = new StringBuilder();
				responses.append("WO: ").append(response.getLEGACY_BATCH()).append(",");
			
			ArrayList < String > emailsList = new ArrayList < String > (Arrays.asList(toEmail.split(",")));
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(Integer.valueOf(port));
            email.setAuthentication("apikey", "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");
            email.setFrom(fromEmail);
            email.setSubject("Batch Creationg INT30 encountered an Error");
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
	
	public static void sendEmailOpsLine(String Operation, INT30_TRAX order, OpsLineEmail opsLineEmails) {
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
				email.setSubject("Failure to update INT 30 PN: " + order.getPN() + " BATCH: " + order.getLEGACY_BATCH());
				email.setMsg("PN: " + order.getPN() + " BACTH: " + order.getLEGACY_BATCH() + ", Date & Time of Transaction: " + date +  ", Error Code: " + order.getEXCEPTION_ID() + ", Remarks: " + order.getEXCEPTION_DETAIL());				
			} else {
				email.setSubject("Failure to update PN: " + order.getPN() + " BATCH: " + order.getLEGACY_BATCH());
				email.setMsg("PN: " + order.getPN() + " BATCH: " + order.getLEGACY_BATCH() + ", Date & Time of Transaction: " + date +  ", Error Code: " + order.getEXCEPTION_ID() + ", Remarks: " + order.getEXCEPTION_DETAIL());				
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
