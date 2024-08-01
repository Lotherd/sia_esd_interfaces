package trax.aero.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;



import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import trax.aero.logger.LogManager;

public class ImportZepartserController {

	
	static String errors = "";
	
	static Logger logger = LogManager.getLogger("ImportZepartser_I23");
	
	
	
	public static void addError(String error) {
		errors=errors.concat(error + System.lineSeparator()+ System.lineSeparator());
	}
	
	public static void sendEmail(File file)
	{
		try
		{
			String fromEmail = System.getProperty("fromEmail");
			String host = System.getProperty("fromHost");
			String port = System.getProperty("fromPort");
			String toEmail = System.getProperty("Zepartser_toEmail");
	        ArrayList<String>  emailsList = new ArrayList<String>(Arrays.asList(toEmail.split(",")));
			Email email = new SimpleEmail();
			email.setHostName(host);
			email.setSmtpPort(Integer.valueOf(port));
			email.setFrom(fromEmail);
			//email.setAuthentication("apikey", "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");
			email.setSubject("Interface failed to record material in ZEPARTSER_MASTER due to incorrect format");
			for(String emails: emailsList)
	        {
	        	email.addTo(emails);
	        }
			email.setMsg(
					"Date & Time of Transaction: " +new Date().toString()+",\n"
					+"XML File " + file.getName()  +",\n"
					+ "Error Message : " +errors +System.lineSeparator()
					+"**********************************************************"+System.lineSeparator()
					+"* NOTE: This is a system generated email. Do not reply *"+System.lineSeparator()
					+"**********************************************************"+System.lineSeparator()
					);
			email.send();
		}
		catch(Exception e)
		{
			logger.info(e.toString());
			logger.info("Email not found");
			
		}
		finally
		{
			errors = "";
		}
	}
}
