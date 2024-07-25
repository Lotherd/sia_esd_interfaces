package trax.aero.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.ManHours_Item_Controller;
import trax.aero.data.ManHours_Item_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT14_18_SND;
import trax.aero.pojo.INT14_18_TRAX;

public class Run implements Runnable{

	ManHours_Item_Data data = null;
	final String url = System.getProperty("ManHR_URL");
	final int MAX_ATTEMPTS = 3;
	Logger logger = LogManager.getLogger("ManHourItem");
	
	public Run() {
		data = new ManHours_Item_Data();
	}
	
	private void process() {
		Poster poster = new Poster();
		ArrayList<INT14_18_SND> ArrayReq = new ArrayList<INT14_18_SND>();
		String executed = "OK";
		try {
			ArrayReq = data.getManHRIT();
			 String markSendResult;
		      boolean success = false;
			
			if(!ArrayReq.isEmpty()) {
				for (INT14_18_SND ArrayRequest : ArrayReq) {
					 if (!ArrayRequest.getWO().isEmpty()) {
	                        logger.info("RUN INFO " + ArrayRequest.getOperation().get(0).getOPS_NO());
	                    } else {
	                        logger.info("RUN INFO: Order list is empty");
	                    }
					JAXBContext jc = JAXBContext.newInstance(INT14_18_SND.class);
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
			        	 ManHours_Item_Controller.addError("Unable to send XML " + "to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
			         } else {
			        	 INT14_18_TRAX input = null;
			        	 try {
			        		 String body = poster.getBody();
			        		 StringReader sr = new StringReader(body);
			        		 jc = JAXBContext.newInstance(INT14_18_TRAX.class);
			        		 Unmarshaller unmarshaller = jc.createUnmarshaller();
			        		 input = (INT14_18_TRAX) unmarshaller.unmarshal(sr);
			        		 
			        		 marshaller = jc.createMarshaller();
						        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
						        sw = new StringWriter();
							    marshaller.marshal(input,sw);
							    logger.info("Input: " + sw.toString());
							    if(input.getError_code() != null && !input.getError_code().isEmpty() && input.getError_code().equalsIgnoreCase("53")) {
							    	executed = data.markTransaction(input);
							    } else {
							    	logger.severe("Received Response with Remarks: " + input.getRemarks() +", Order Number: "+input.getRFO() + ", Error Code: " +input.getError_code());
							    	ManHours_Item_Controller.addError("Received Response with Remarks: " + input.getRemarks() +", Order Number: "+input.getRFO() + ", Error Code: " +input.getError_code());
							    	executed = data.markTransaction(input);
							    	executed = "Issue found";
							    }
							    if(executed == null || !executed.equalsIgnoreCase("OK")) {
							    	executed = "Issue found";
					        		throw new Exception("Issue found");
							    }
			        		 
			        	 }catch(Exception e) {
			        		
			        		 ManHours_Item_Controller.addError(e.toString());
							 ManHours_Item_Controller.sendEmailRequest(ArrayReq);
							
			        	 }finally {
			        	 
			        		 logger.info("finishing");
			        	 }
			        	 
			        	 logger.info("POST status: " + String.valueOf(success) + " to URL: " + url);
			         }
				}
			}
			if (!ManHours_Item_Controller.getError().isEmpty()) {
				throw new Exception("Issue found");
			}
		}catch(Throwable e){
			logger.severe(e.toString());
			ManHours_Item_Controller.addError(e.toString());
			ManHours_Item_Controller.sendEmailRequest(ArrayReq);
		}
	}
	
	public void run() {
	    try {
	      if (data.lockAvailable("I1418")) {
	        data.lockTable("I1418");
	        process();
	        data.unlockTable("I1418");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	
}
