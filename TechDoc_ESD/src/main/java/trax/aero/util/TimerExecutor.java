package trax.aero.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import trax.aero.data.ModelData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.I9_I29_Request;

public class TimerExecutor implements Runnable {
	
	Logger logger = LogManager.getLogger("Techdoc_I9_I29");

	ModelData data = null;
	
	public TimerExecutor()
	{
		data = new ModelData();
	}
	
	//Variables
		//final String ID = System.getProperty("JobConfirmation_ID");
		//final String Password = System.getProperty("JobConfirmation_Password");
		final String url = System.getProperty("Techdoc_URL");
		final int MAX_ATTEMPTS = 3;
	
	
	private void process() {
		TaskCardPoster poster = new TaskCardPoster();
		ArrayList<I9_I29_Request> ArrayRequest = new ArrayList<I9_I29_Request>();
		try 
			{
								
				// loop
				ArrayRequest = data.getTaskCards();
				boolean success = false;
				
				if(!ArrayRequest.isEmpty()) {
					for(I9_I29_Request req : ArrayRequest) {
						success = false;
					
						JAXBContext jc = JAXBContext.newInstance(I9_I29_Request.class);
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
							 logger.severe("Unable to send RFO: "+req.getRFO_NO() +" to URL " + url);
							 data.emailer.sendEmail("Unable to send RFO: "+req.getRFO_NO() +" to URL " + url,
									 req.getRFO_NO(),req.getWO());							
						}else {														
							try {    
							    data.markTransaction(req);
							}
							catch(Exception e){
								data.emailer.sendEmail(e.toString());
							}
					         
							logger.info("POST status: " + String.valueOf(success) + " RFO: "+ req.getRFO_NO());
						}
					}
				}
				
			}
			catch(Throwable e)
			{
				e.printStackTrace();
				logger.severe(e.toString());
				data.emailer.sendEmail(e.toString());
			}
	}
	
	
	public void run() 
	{
		try {
			if(data.lockAvailable("I9_I29"))
			{
				data.lockTable("I9_I29");
				process();
				data.unlockTable("I9_I29");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	 }
}
