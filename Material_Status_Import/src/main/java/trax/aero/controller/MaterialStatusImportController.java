package trax.aero.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import trax.aero.interfaces.IMaterialStatusImportData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.MaterialStatusImportMaster;


public class MaterialStatusImportController {
	
	static String errors = "";
	
	@EJB
	static IMaterialStatusImportData data;
	
	static Logger logger = LogManager.getLogger("MaterialStatusImport_I11&I12");
	
	public MaterialStatusImportController()
	{
		
	}
	
	public static void addError(String error) {
		errors=errors.concat(error + System.lineSeparator()+ System.lineSeparator());
	}
	
	public static void sendEmail(MaterialStatusImportMaster input)
	{
		try
		{
			
			
			String fromEmail = System.getProperty("fromEmail");
			String host = System.getProperty("fromHost");
			String port = System.getProperty("fromPort");
		
			final String toEmail = System.getProperty("MaterialStatusImport_toEmail");
			
			ArrayList<String>  emailsList = new ArrayList<String> (Arrays.asList(toEmail.split(",")));
			
			Email email = new SimpleEmail();
			email.setHostName(host);
			email.setSmtpPort(Integer.valueOf(port));
			email.setFrom(fromEmail);
			email.setSubject(" Material Status Import interface did not receive XML correctly.");
			
			for(String emails: emailsList)
	        {
				email.addTo(emails);
				
	        }
			
			
			email.setMsg("XML with RFO " 
					+ input.getRFO_NO()
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
			data.logError(errors);
			errors = "";
		}
	}
}
