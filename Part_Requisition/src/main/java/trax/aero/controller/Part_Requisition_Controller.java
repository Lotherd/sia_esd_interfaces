package trax.aero.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import trax.aero.logger.LogManager;
import trax.aero.pojo.Component_TRAX;
import trax.aero.pojo.INT13_SND;
import trax.aero.pojo.INT13_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.OrderComponentSND;
import trax.aero.pojo.OrderSND;

public class Part_Requisition_Controller {
	EntityManagerFactory factory;
	static String errors = "";
	static Logger logger = LogManager.getLogger("Part_REQ");
	static String fromEmail = System.getProperty("fromEmail");
	static String host = System.getProperty("fromHost");
	static String port = System.getProperty("fromPort");
	static String toEmail = System.getProperty("Part_REQ_toEmail");
	
	public Part_Requisition_Controller() {
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		factory.createEntityManager();
	}
	
	public static void addError(String error) {
		errors = errors.concat(error + System.lineSeparator() + System.lineSeparator());
	}
	
	public static String getError() {
		return errors;
	}
	
	public static void sendEmailRequest(ArrayList <INT13_SND> arrayReq) {
		if(toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
            return;
		}
		try {
			String requests = "";
			for (INT13_SND or: arrayReq) {
				for(OrderSND r: or.getOrder()) {
					for(OrderComponentSND oc: r.getComponents()) {
						String OrderNO = r.getOrderNO();
						requests = requests + " (Order Number: " + OrderNO + ", Requisition: " + oc.getRequisition() + ", Requisition Line:" + oc.getRequisitionLine() + "),";
 					}
				}
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
	
	public static void sendEmailResponse(INT13_TRAX response) {
		if(toEmail == null || toEmail.trim().isEmpty()) {
			logger.severe("Email address (toEmail) is not configured. Please check the system properties.");
			return;
		}
		try {
			StringBuilder responses = new StringBuilder();
			for(Component_TRAX c : response.getComponent()) {
				responses.append("SAP Requisiton: ").append(c.getRequisition()).append(",");
			}
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
	
	public static void sendEmailOpsLine(String Operation, INT13_TRAX order, Component_TRAX component, OpsLineEmail opsLineEmails) {
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
				email.setSubject("Failure to update INT 13 Part Requisition: " + component.getRequisition() + " Requisition Line: " + component.getRequisitionLine());
				email.setMsg("Requisition: " + component.getRequisition() + ", Requisition Line: " + component.getRequisitionLine() + ", Date & Time of Transaction: " + date + ", SPA Order Number: " + order.getRFO() + ", Operation Number: " + component.getOPS() + ", Error Code: " + order.getExceptionId() + ", Remarks: " + order.getExceptionDetail());				
			} else {
				email.setSubject("Failure to update Requisition: " + component.getRequisition() + " Requisition Line: " + component.getRequisitionLine());
				email.setMsg("Requisition: " + component.getRequisition() + ", Requisition Line: " + component.getRequisitionLine() + ", Date & Time of Transaction: " + date + ", SPA Order Number: " + order.getRFO() + ", Operation Number: " + component.getOPS() + ", Error Code: " + order.getExceptionId() + ", Remarks: " + order.getExceptionDetail());				
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