package trax.aero.utils;


import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.InstallRemoveSVOController;
import trax.aero.data.InstallRemoveSvoData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.I19_Request;
import trax.aero.pojo.I19_Response;




public class Run implements Runnable {
	
	//Variables
	InstallRemoveSvoData data = null;
	//final String ID = System.getProperty("JobConfirmation_ID");
	//final String Password = System.getProperty("JobConfirmation_Password");
	final String url = System.getProperty("InstallRemoveSVO_URL");
	final int MAX_ATTEMPTS = 3;
	Logger logger = LogManager.getLogger("InstallRemoveSVO_I19");
	
	public Run() {
		data = new InstallRemoveSvoData();
	}
	
	private void process() {
		Poster poster = new Poster();
		ArrayList<I19_Request> ArrayRequest = new ArrayList<I19_Request>();
		ArrayList<I19_Request> ArrayRequestError = new ArrayList<I19_Request>();
			String exceuted = "OK";
			try 
			{
								
				// loop
				ArrayRequest = data.getTransactions();
				boolean success = false;
				
				if(!ArrayRequest.isEmpty()) {
					for(I19_Request req : ArrayRequest) {
						success = false;
					
						JAXBContext jc = JAXBContext.newInstance(I19_Request.class);
						Marshaller marshaller = jc.createMarshaller();
						marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
						StringWriter sw = new StringWriter();
						marshaller.marshal(req, sw);
						
						logger.info("Ouput: " + sw.toString());
						
						for(int i = 0; i < MAX_ATTEMPTS; i++)
						{
							success = poster.post(req, url);
							if(success)
							{
								break;					 
							}
							
						}			

						if(!success)
						{
							 logger.severe("Unable to send Transaction: "+req.getTransaction() +" to URL " + url);
							InstallRemoveSVOController.addError("Unable to send Transaction: "+req.getTransaction() +" to URL " + url);
							ArrayRequestError.add(req);
							
						}else {
							I19_Response input = null;
														
							try 
					        {    
								String body = poster.getBody();
								StringReader sr = new StringReader(body);				
								jc = JAXBContext.newInstance(I19_Response.class);
						        Unmarshaller unmarshaller = jc.createUnmarshaller();
						        input = (I19_Response) unmarshaller.unmarshal(sr);

						        marshaller = jc.createMarshaller();
						        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
						        sw = new StringWriter();
							    marshaller.marshal(input,sw);
							    logger.info("Input: " + sw.toString());
							    if(input.getExceptionId() != null && !input.getExceptionDetail().isEmpty() && input.getExceptionId().equalsIgnoreCase("53")) {
							    	exceuted = data.markTransaction(input);
								}else {
																		
									logger.severe("Received Response with Exception: " + input.getExceptionDetail() +",Transaction: "+input.getTransaction() + ", Exception ID: " +input.getExceptionId());
									InstallRemoveSVOController.addError("Received Response with Exception: " + input.getExceptionDetail() +", Order Number: "+input.getTransaction() + ", Exception ID: " +input.getExceptionId());
									exceuted = data.markTransaction(input);
									exceuted = "Issue found";
								}
					        	if(exceuted == null || !exceuted.equalsIgnoreCase("OK")) {
					        		exceuted = "Issue found";
					        		throw new Exception("Issue found");
					        	}
							}
							catch(Exception e)
							{
								InstallRemoveSVOController.addError(e.toString());
								InstallRemoveSVOController.sendEmailResponse(input);
							}
					       finally 
					       {   
					    	   logger.info("finishing");
					       }   
							 logger.info("POST status: " + String.valueOf(success) + " Transaction: "+ req.getTransaction());
						}
					}
				}
				
				
				
				if(!InstallRemoveSVOController.getError().isEmpty()) {
					 throw new Exception("Issue found");
				}
			}
			catch(Throwable e)
			{
				e.printStackTrace();
				 logger.severe(e.toString());
				InstallRemoveSVOController.addError(e.toString());
				if(!ArrayRequestError.isEmpty()) {			
					InstallRemoveSVOController.sendEmailRequest(ArrayRequestError);
				}else{
					InstallRemoveSVOController.sendEmailRequest(ArrayRequest);
				}
			}
	}
	
	
	public void run() 
	{
		try {
			if(data.lockAvailable("I19"))
			{
				data.lockTable("I19");
				process();
				data.unlockTable("I19");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	 }
}