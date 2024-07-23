package trax.aero.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import trax.aero.controller.CreationU_RFO_Controller;
import trax.aero.data.CreationU_RFO_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT22_SND;
import trax.aero.pojo.INT22_TRAX;

public class Run implements Runnable{

	CreationU_RFO_Data data = null;
	final String url = System.getProperty("BatchCreation_URL");
	final int MAX_ATTEMPTS = 3;
	Logger logger = LogManager.getLogger("RFOCreation");
	
	public Run() {
		data = new CreationU_RFO_Data();
	}
	
	private void process() {
		Poster poster = new Poster();
		ArrayList<INT22_SND> ArrayReq = new ArrayList<INT22_SND>();
		String executed = "OK";
		
		try {
			ArrayReq = data.getPN();
			String markSendResult;
			boolean success = false;
			
			if(!ArrayReq.isEmpty()) {
				for (INT22_SND ArrayRequest : ArrayReq) {
					if (!ArrayRequest.getPn().isEmpty()) {
					logger.info("RUN INFO " + ArrayRequest.getPn());
				
				}else {
                    logger.info("RUN INFO: Order list is empty");
                }
				JAXBContext jc = JAXBContext.newInstance(INT22_SND.class);
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
			        	 CreationU_RFO_Controller.addError("Unable to send XML " + "to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
			         } else {
			        	 INT22_TRAX input = null;
			        	 
			        	 logger.info("finishing");
			        	 
				        logger.info("POST status: " + String.valueOf(success) + " to URL: " + url);
			         }
			}
			}
			if (!CreationU_RFO_Controller.getError().isEmpty()) {
				throw new Exception("Issue found");
			}
		}catch(Throwable e){
			logger.severe(e.toString());
			CreationU_RFO_Controller.addError(e.toString());
			CreationU_RFO_Controller.sendEmailRequest(ArrayReq);
		}
	}
	
	public void run() {
	    try {
	      if (data.lockAvailable("I22")) {
	        data.lockTable("I22");
	        process();
	        data.unlockTable("I22");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	
}
