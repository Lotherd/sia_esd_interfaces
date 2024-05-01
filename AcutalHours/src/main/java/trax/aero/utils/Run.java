package trax.aero.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import trax.aero.controller.Actual_Hours_Controller;
import trax.aero.data.Actual_Hours_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT31_SND;
import trax.aero.pojo.INT31_TRAX;

public class Run implements Runnable{
	
	Actual_Hours_Data data = null;
	final String url = System.getProperty("ActualHR_URL");
	final int MAX_ATTEMPTS = 3;
	Logger logger = LogManager.getLogger("ActualHours");
	
	public Run() {
		data = new Actual_Hours_Data();
	}
	
	private void process() {
		Poster poster = new Poster();
		ArrayList<INT31_SND> ArrayReq = new ArrayList<INT31_SND>();
		String executed = "OK";
		try {
			ArrayReq = data.getActualHours();
			 String markSendResult;
		      boolean success = false;
			
			if(!ArrayReq.isEmpty()) {
				for (INT31_SND ArrayRequest : ArrayReq) {
					 if (!ArrayRequest.getOrder().isEmpty()) {
	                        logger.info("RUN INFO " + ArrayRequest.getOrder().get(0).getWoActualTransaction());
	                    } else {
	                        logger.info("RUN INFO: Order list is empty");
	                    }
					JAXBContext jc = JAXBContext.newInstance(INT31_SND.class);
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
			        	 Actual_Hours_Controller.addError("Unable to send XML " + "to URL " + url + " MAX_ATTEMPTS: " + MAX_ATTEMPTS);
			         } else {
			        	 INT31_TRAX input = null;
			        	 
			        	 
			        		 logger.info("finishing");
			        	 
			        	 logger.info("POST status: " + String.valueOf(success) + " to URL: " + url);
			         }
				}
			}
			if (!Actual_Hours_Controller.getError().isEmpty()) {
				throw new Exception("Issue found");
			}
		}catch(Throwable e){
			logger.severe(e.toString());
			Actual_Hours_Controller.addError(e.toString());
			Actual_Hours_Controller.sendEmailRequest(ArrayReq);
		}
	}

	public void run() {
    try {
      if (data.lockAvailable("I31")) {
        data.lockTable("I31");
        process();
        data.unlockTable("I31");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
