package trax.aero.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import trax.aero.controller.Creation_Sales_Controller;
import trax.aero.data.Creation_Sales_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT7_SND;
import trax.aero.pojo.INT7_TRAX;

public class Run implements Runnable{
	
	Creation_Sales_Data data = null;
	final String url = System.getProperty("CreationSales_URL");
	final int MAX_ATTEMPTS = 3;
	Logger logger = LogManager.getLogger("CreationSales");
	
	public Run() {
		data = new Creation_Sales_Data();
	}
	
	private void process() {
		Poster poster = new Poster();
		ArrayList<INT7_SND> ArrayReq = new ArrayList<INT7_SND>();
		String executed = "OK";
		
		try {
			ArrayReq = data.getWorkOrder();
			String markSendResult;
			boolean success = false;
			
			if(!ArrayReq.isEmpty()) {
				for(INT7_SND ArrayRequest : ArrayReq) {
					if(!ArrayRequest.getTraxWo().isEmpty()) {
						logger.info("RUN INFO " + ArrayRequest.getTraxWo());
					} else {
						 logger.info("RUN INFO: Order list is empty");
					}
					
					JAXBContext jc = JAXBContext.newInstance(INT7_SND.class);
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
			        	 Creation_Sales_Controller.addError("Unable to send XML " + "to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
			         } else {
			        	 INT7_TRAX input = null;
			        	 
			        	 logger.info("finishing");
			        	 
				        logger.info("POST status: " + String.valueOf(success) + " to URL: " + url);
			         }

					
				}
			}
			
			
		}catch(Throwable e){
			logger.severe(e.toString());
			Creation_Sales_Controller.addError(e.toString());
			Creation_Sales_Controller.sendEmailRequest(ArrayReq);
		}
	}
	
	public void run() {
	    try {
	      if (data.lockAvailable("I7")) {
	        data.lockTable("I7");
	        process();
	        data.unlockTable("I7");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

}
