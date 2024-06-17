package trax.aero.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import trax.aero.logger.LogManager;
import trax.aero.pojo.I19_Request;
import trax.aero.pojo.I19_Response;


public class InstallRemoveSVOController {
	EntityManagerFactory factory;
	private EntityManager em;
	static String errors = "";
	static Logger logger = LogManager.getLogger("InstallRemoveSVO_I19");
	
	public InstallRemoveSVOController()
	{
		
	}
	
	public static void addError(String error) {
		errors=errors.concat(error + System.lineSeparator()+ System.lineSeparator());
	}
	
	public static String getError() {
		return errors;
	}
	
	public static void sendEmailRequest(ArrayList<I19_Request> request)
	{
		try
		{
			
			String requests = "";
			
			for(I19_Request r : request) {
				
				requests = requests + " Transaction: "  + r.getTransaction() + " Rfo: "  + r.getRfoNo() + " Transaction Type: "  + r.getTransactionType() +",";
			}
			
			String fromEmail = System.getProperty("fromEmail");
			String host = System.getProperty("fromHost");
			String port = System.getProperty("fromPort");
			final String toEmail = System.getProperty("InstallRemoveSVO_toEmail");
			
			ArrayList<String>  emailsList = new ArrayList<String> (Arrays.asList(toEmail.split(",")));
			
			Email email = new SimpleEmail();
			email.setHostName(host);
			email.setSmtpPort(Integer.valueOf(port));
			email.setFrom(fromEmail);
			email.setSubject("Interface error encountered in Install Remove SVO");
			for(String emails: emailsList)
	        {
				email.addTo(emails);
	        }
			email.setMsg("Requests that failed: " +
					requests
					+" has encountered an issue. "
					+ "Issues found at:\n"  
					+errors);
			email.send();
		}
		catch(Exception e)
		{
			logger.severe(e.toString());
			logger.severe("Email not found");
			
		}
		finally
		{
			errors = "";
		}
	}
	
	public static void sendEmailResponse(I19_Response response)
	{
		try
		{
			
			String fromEmail = System.getProperty("fromEmail");
			String host = System.getProperty("fromHost");
			String port = System.getProperty("fromPort");
			final String toEmail = System.getProperty("InstallRemoveSVO_toEmail");
			
			ArrayList<String>  emailsList = new ArrayList<String> (Arrays.asList(toEmail.split(",")));
			
			Email email = new SimpleEmail();
			email.setHostName(host);
			email.setSmtpPort(Integer.valueOf(port));
			email.setFrom(fromEmail);
			email.setSubject("Interface error encountered in Install Remove SVO");
			for(String emails: emailsList)
	        {
				email.addTo(emails);
	        }
			email.setMsg("Transaction: " 
					+ response.getTransaction() + " Wo: "  +response.getWo()  + " Task Card: "  + response.getTc() 
					+" has encountered an issue. "			
					+ "Enter records manually. "
					+ "Issues found at:\n"  
					+errors);
			email.send();
			
			
		}
		catch(Exception e)
		{
			logger.severe(e.toString());
			logger.severe("Email not found");
			
		}
		finally
		{
			errors = "";
		}
	}
}
