package trax.aero.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import trax.aero.controller.Unit_Price_RFO_Controller;
import trax.aero.data.Unit_Price_RFO_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT27_SND;
import trax.aero.pojo.INT27_TRAX;


public class Run implements Runnable{
	
	Unit_Price_RFO_Data data = null;
	final String url = System.getProperty("UnitPrice_URL");
	final int MAX_ATTEMPTS = 3;
	Logger logger = LogManager.getLogger("UnitPrice");
	
	public Run() {
		data = new Unit_Price_RFO_Data();
	}
	
	private void process() {
		Poster poster = new Poster();
		ArrayList<INT27_SND> ArrayReq = new ArrayList<INT27_SND>();
		String executed = "OK";
		try {
			ArrayReq = data.getManHRIT();
			 String markSendResult;
		      boolean success = false;
			
			if(!ArrayReq.isEmpty()) {
				for (INT27_SND ArrayRequest : ArrayReq) {
					 if (!ArrayRequest.getWO().isEmpty()) {
	                        logger.info("RUN INFO " + ArrayRequest.getOperation().get(0).getBatch());
	                    } else {
	                        logger.info("RUN INFO: Order list is empty");
	                    }
					JAXBContext jc = JAXBContext.newInstance(INT27_SND.class);
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
			        	 Unit_Price_RFO_Controller.addError("Unable to send XML " + "to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
			         } else {
			        	 INT27_TRAX input = null;
			        	 
			        	 
			        		 logger.info("finishing");
			        	 
			        	 logger.info("POST status: " + String.valueOf(success) + " to URL: " + url);
			         }
				}
			}
			if (!Unit_Price_RFO_Controller.getError().isEmpty()) {
				throw new Exception("Issue found");
			}
		}catch(Throwable e){
			logger.severe(e.toString());
			Unit_Price_RFO_Controller.addError(e.toString());
			Unit_Price_RFO_Controller.sendEmailRequest(ArrayReq);
		}
	}
	
	public void run() {
	    try {
	      if (data.lockAvailable("I27")) {
	        data.lockTable("I27");
	        process();
	        data.unlockTable("I27");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

}
