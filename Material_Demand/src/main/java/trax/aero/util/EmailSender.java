package trax.aero.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import trax.aero.interfaces.IMaterialData;
import trax.aero.logger.LogManager;

public class EmailSender 
{
	
	Logger logger = LogManager.getLogger("MaterialDemand_I10");
	
	@EJB IMaterialData md;
	
	private String toEmail;
	
	public EmailSender(String email)
	{
		toEmail = email;
	}
	
	public void sendEmail(String error) 
	{

		try {
			String fromEmail = System.getProperty("fromEmail");
			String host = System.getProperty("fromHost");
			String port = System.getProperty("fromPort");
			
			Email email = new SimpleEmail();
			email.setHostName(host);
			email.setSmtpPort(Integer.valueOf(port));
			email.setFrom(fromEmail);
			
			ArrayList<String> emailsList = new ArrayList<String>(Arrays.asList(toEmail.split(",")));
			for(String toEmails : emailsList)
			{
				email.addTo(toEmails);
			}
			
			
			email.setSubject("Material Demand interface ran into an error");
			
			email.setMsg(error);
			
			email.send();
		} 
		catch (EmailException e) 
		{
			logger.severe(e.toString());
		}finally {
			md.logError(error);
		}

		
	}

}
