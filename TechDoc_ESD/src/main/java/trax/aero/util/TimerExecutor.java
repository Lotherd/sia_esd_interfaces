package trax.aero.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import trax.aero.data.ModelData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.I74_Request;

public class TimerExecutor implements Runnable {
	
	Logger logger = LogManager.getLogger("Techdoc_I9_I29");

	ModelData data = null;
	
	//Variables
		//final String ID = System.getProperty("JobConfirmation_ID");
		//final String Password = System.getProperty("JobConfirmation_Password");
		final String url = System.getProperty("Techdoc_URL");
		final int MAX_ATTEMPTS = 3;
	
	
	private void process() {
		TaskCardPoster poster = new TaskCardPoster();
		ArrayList<I74_Request> ArrayRequest = new ArrayList<I74_Request>();
		try 
			{
								
				// loop
				ArrayRequest = data.getTaskCards();
				boolean success = false;
				
				if(!ArrayRequest.isEmpty()) {
					for(I74_Request req : ArrayRequest) {
						success = false;
					
						JAXBContext jc = JAXBContext.newInstance(I74_Request.class);
						Marshaller marshaller = jc.createMarshaller();
						marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
						StringWriter sw = new StringWriter();
						marshaller.marshal(req, sw);
						
						logger.info("Ouput: " + sw.toString());
						
						for(int i = 0; i < MAX_ATTEMPTS; i++)
						{
							success = poster.postTaskCard(req, url);
							if(success)
							{								
						       break;
							}
						}			
						if(!success)
						{
							 logger.severe("Unable to send RFO: "+req.getOrderNumber() +" to URL " + url);
							 data.emailer.sendEmail("Unable to send RFO: "+req.getOrderNumber() +" to URL " + url,
									 req.getOrderNumber(),req.getReasonForTECO_reversal());							
						}else {														
							try {    
							    data.markTransaction(req);
							}
							catch(Exception e){
								data.emailer.sendEmail(e.toString());
							}
					         
							logger.info("POST status: " + String.valueOf(success) + " RFO: "+ req.getOrderNumber());
						}
					}
				}
				
			}
			catch(Throwable e)
			{
				logger.severe(e.toString());
				data.emailer.sendEmail(e.toString());
			}
	}
	
	
	public void run() 
	{
		try {
			if(data.lockAvailable("I9_29"))
			{
				data.lockTable("I9_29");
				process();
				data.unlockTable("I9_29");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	 }
}
