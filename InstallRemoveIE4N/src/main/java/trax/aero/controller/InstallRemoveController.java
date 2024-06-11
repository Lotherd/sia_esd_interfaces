package trax.aero.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import trax.aero.logger.LogManager;
import trax.aero.pojo.Application_Log;
import trax.aero.pojo.IE4N;



public class InstallRemoveController {

	
	static String errors = "";
	static Logger logger = LogManager.getLogger("InstallRemove_I20");
	
	public InstallRemoveController()
	{
	}
	
	public static void addError(String error) {
		errors=errors.concat(error + System.lineSeparator()+ System.lineSeparator());
	}
	
	public static String getError() {
		return errors;
	}
	
	public static void sendEmailInbound(Application_Log log)
	{
		try
		{
			
			
			String fromEmail = System.getProperty("fromEmail");
			String host = System.getProperty("fromHost");
			String port = System.getProperty("fromPort");
		
			final String toEmail = System.getProperty("InstallRemove_toEmail");
			
			ArrayList<String>  emailsList = new ArrayList<String> (Arrays.asList(toEmail.split(",")));
			
			Email email = new SimpleEmail();
			email.setHostName(host);
			email.setSmtpPort(Integer.valueOf(port));
			email.setFrom(fromEmail);


			email.setSubject("Install Remove IE4N interface did not insert XML correctly.");
			for(String emails: emailsList)
	        {
				email.addTo(emails);
	        }
			email.setMsg("Install Remove IE4N " 
					+"has encountered an issue. "
					+ "Issues found at:"+System.lineSeparator()
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
		
		public static void sendEmailButton(IE4N message)
		{
			try
			{
				
				
				String fromEmail = System.getProperty("fromEmail");
				String host = System.getProperty("fromHost");
				String port = System.getProperty("fromPort");
			
				final String toEmail = System.getProperty("InstallRemove_toEmail");
				
				ArrayList<String>  emailsList = new ArrayList<String> (Arrays.asList(toEmail.split(",")));
				
				Email email = new SimpleEmail();
				email.setHostName(host);
				email.setSmtpPort(Integer.valueOf(port));
				email.setFrom(fromEmail);

				email.setSubject("Install Remove IE4N interface did not send XML correctly.");
				for(String emails: emailsList)
		        {
					email.addTo(emails);
		        }
				email.setMsg("Install Remove IE4N interface " 
						+"has encountered an issue. "
						+ "Issues found at:" +System.lineSeparator()  
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
