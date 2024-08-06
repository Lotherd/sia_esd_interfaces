package trax.aero.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.CreationBatch_Controller;
import trax.aero.data.CreationBatch_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT30_SND;
import trax.aero.pojo.INT30_TRAX;

public class Run implements Runnable{
	
	CreationBatch_Data data = null;
	final String url = System.getProperty("BatchCreation_URL");
	final int MAX_ATTEMPTS = 3;
	Logger logger = LogManager.getLogger("BatchCreation");
	
	public Run() {
		data = new CreationBatch_Data();
	}
	
	private void process() {
		Poster poster = new Poster();
		ArrayList<INT30_SND> ArrayReq = new ArrayList<INT30_SND>();
		String executed = "OK";
		
		try {
			ArrayReq = data.getPN();
			String markSendResult;
			boolean success = false;
			
			if(!ArrayReq.isEmpty()) {
				for (INT30_SND ArrayRequest : ArrayReq) {
					if (!ArrayRequest.getPN().isEmpty()) {
					logger.info("RUN INFO " + ArrayRequest.getPN());
				
				}else {
                    logger.info("RUN INFO: Order list is empty");
                }
				JAXBContext jc = JAXBContext.newInstance(INT30_SND.class);
				Marshaller marshaller = jc.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				
				StringWriter sw = new StringWriter();
				marshaller.marshal(ArrayRequest, sw);
				
				
				logger.info("Output: " + sw.toString());

		          for (int i = 0; i < MAX_ATTEMPTS; i++) {
		        	  success = poster.post(ArrayRequest, url);
		        	  markSendResult = data.markSendData();
		        	  if ("OK".equals(markSendResult)) {
		            success = true;
		            break;
		        	  }
		          }
		          if (!success) {
			        	 logger.severe("Unable to send XML "+ "to URL " + url);
			        	 CreationBatch_Controller.addError("Unable to send XML " + "to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
			         } else {
			        	 INT30_TRAX input = null;
			        	 
			        	 try {
			        		 String body = poster.getBody();
			        		 StringReader sr = new StringReader(body);
			        		 jc = JAXBContext.newInstance(INT30_TRAX.class);
			        		 Unmarshaller unmarshaller = jc.createUnmarshaller();
			        		 input = (INT30_TRAX) unmarshaller.unmarshal(sr);
			        		 
			        		 marshaller = jc.createMarshaller();
						        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
						        sw = new StringWriter();
							    marshaller.marshal(input,sw);
							    logger.info("Input: " + sw.toString());
							    if(input.getEXCEPTION_ID() != null && !input.getEXCEPTION_ID().isEmpty() && input.getEXCEPTION_ID().equalsIgnoreCase("53")) {
							    	executed = data.markTransaction(input);
							    } else {
							    	logger.severe("Received Response with Remarks: " + input.getEXCEPTION_DETAIL() +", Material: "+input.getPN() + ", Error Code: " +input.getEXCEPTION_ID());
							    	CreationBatch_Controller.addError("Received Response with Remarks: " + input.getEXCEPTION_DETAIL() +", Material: "+input.getPN() + ", Error Code: " +input.getEXCEPTION_ID());
							    	executed = data.markTransaction(input);
							    	executed = "Issue found";
							    }
							    if(executed == null || !executed.equalsIgnoreCase("OK")) {
							    	executed = "Issue found";
					        		throw new Exception("Issue found");
							    }
			        		 
			        	 }catch(Exception e) {
			        		
			        		 CreationBatch_Controller.addError(e.toString());
			        		 CreationBatch_Controller.sendEmailRequest(ArrayReq);
							
			        	 }finally {
			        	 
			        	 logger.info("finishing");
			        	 }
				        logger.info("POST status: " + String.valueOf(success) + " to URL: " + url);
			         }
			}
			}
			if (!CreationBatch_Controller.getError().isEmpty()) {
				throw new Exception("Issue found");
			}
		}catch(Throwable e){
			logger.severe(e.toString());
			CreationBatch_Controller.addError(e.toString());
			CreationBatch_Controller.sendEmailRequest(ArrayReq);
		}
	}
	
	public void run() {
	    try {
	      if (data.lockAvailable("I30")) {
	        data.lockTable("I30");
	        process();
	        data.unlockTable("I30");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

}
