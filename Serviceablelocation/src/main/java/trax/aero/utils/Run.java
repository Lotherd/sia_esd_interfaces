package trax.aero.utils;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.ServiceablelocationController;
import trax.aero.data.ServiceablelocationData;
import trax.aero.interfaces.IServiceablelocationData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.MT_TRAX_RCV_I28_4134_RES;
import trax.aero.pojo.MT_TRAX_SND_I28_4134_REQ;




public class Run implements Runnable {
	
	//Variables
	IServiceablelocationData data = null;
	//final String ID = System.getProperty("JobConfirmation_ID");
	//final String Password = System.getProperty("JobConfirmation_Password");
	final String url = System.getProperty("Serviceablelocation_URL");
	final int MAX_ATTEMPTS = 3;
	Logger logger = LogManager.getLogger("Serviceablelocation_I28");
	
	public Run(IServiceablelocationData data) {
		this.data = data;
	}
	
	private void process() {
		LoopPoster poster = new LoopPoster();
		ArrayList<MT_TRAX_SND_I28_4134_REQ> requests = new ArrayList<MT_TRAX_SND_I28_4134_REQ>();
		String exceuted = "OK";
		try 
		{
			data.openCon();
			// loop
			requests = data.getRequests();
			boolean success = false;
			
			
			for(MT_TRAX_SND_I28_4134_REQ request : requests) {
				success = false;
				JAXBContext jc = JAXBContext.newInstance(MT_TRAX_SND_I28_4134_REQ.class);
				Marshaller marshaller = jc.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				marshaller.marshal(request, sw);
				
				logger.info("Output to post: " + sw.toString());
				
				for(int i = 0; i < MAX_ATTEMPTS; i++)
				{
					success = poster.post(request, url);
					if(success)
					{
						break;
					}
				}
				if(!success)
				{
					logger.severe("Unable to send XML with RFO: "+request.getRfoNo() +" to URL " + url);
					ServiceablelocationController.addError("Unable to send XML with RFO: "+request.getRfoNo() + ", Date: " + new Date().toString()  + ", SHOP WO: " + request.getWo()+" to URL " + url + " MAX_ATTEMPTS: "  +MAX_ATTEMPTS );
					ServiceablelocationController.sendEmail(request.getWo());	
				}else {
					MT_TRAX_RCV_I28_4134_RES input = null;
													
						try 
				        {    
							String body = poster.getBody();
							StringReader sr = new StringReader(body);				
							jc = JAXBContext.newInstance(MT_TRAX_RCV_I28_4134_RES.class);
					        Unmarshaller unmarshaller = jc.createUnmarshaller();
					        input = (MT_TRAX_RCV_I28_4134_RES) unmarshaller.unmarshal(sr);

					        marshaller = jc.createMarshaller();
					        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
					        sw = new StringWriter();
						    marshaller.marshal(input,sw);
						    logger.info("Input: " + sw.toString());
						    if(input.getExceptionId().equalsIgnoreCase("53")) {
								data.markTransaction(input);
								data.setInspLot(input);
								data.printLabel(input);
							}else {
								data.markTransaction(input);
								data.setComplete(input);
								exceuted = (  "RFO: " + input.getRfo() 
								+ ", Date: " + new Date().toString()  + ", SHOP WO: " +input.getWo() );
								
								logger.severe(exceuted);
								ServiceablelocationController.addError(exceuted);
								
								exceuted = ("Received acknowledgement with Error Code: " + input.getExceptionId() 
								+", Status Message: "+input.getExceptionDetail()) ;
								
								logger.severe(exceuted);
								ServiceablelocationController.addError(exceuted);
								
								exceuted = "Issue found";
								ServiceablelocationController.sendEmailACK(input.getWo() );
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
				       finally 
				       {
				    	   /*
				    	   try 
							{
								if(data.getCon() != null && !data.getCon().isClosed())
									data.getCon().close();
							} 
							catch (SQLException e) 
							{ 
								e.printStackTrace();
							}
							*/
				    	   logger.info("finishing");
				       }
						    
						    
						    
						    
						    
						 
					
					logger.info("POST status: " + String.valueOf(success) +" to URL: " + url);
				}
			}	
		}
		catch(Throwable e)
		{
			logger.severe(e.toString());				
		}finally {
			try 
			{
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace();
			}
    	   logger.info("finishing");
		}
	}
	
	
	
	
	public void run() 
	{
		try {
			if(data.lockAvailable("I28"))
			{
				data.lockTable("I28");
				process();
				data.unlockTable("I28");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}