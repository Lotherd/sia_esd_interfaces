package trax.aero.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.Import_TC_MHR_Controller;
import trax.aero.data.Import_TC_MHR_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT6_SND;
import trax.aero.pojo.INT6_TRAX;
import trax.aero.pojo.OperationTRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.OrderTRAX;

public class Run implements Runnable{

	// Variables
	  Import_TC_MHR_Data data = null;
	  final String url = System.getProperty("ImportTcMhr_URL");
	  final int MAX_ATTEMPTS = 1;
	  Logger logger = LogManager.getLogger("ImportTC_MHR");

	  public Run() {
	    data = new Import_TC_MHR_Data();
	  }

	  private void process() {
	    Poster poster = new Poster();
	    ArrayList<INT6_SND> ArrayReq = new ArrayList<INT6_SND>();
	    String executed = "OK";
	    try {
	      //loop
	      ArrayReq = data.getTaskCards();
	    //null test
	      if (ArrayReq == null) {
	          logger.severe("Task cards are null");
	          return;
	      }
	      String markSendResult;
	      boolean success = false;

	      if (!ArrayReq.isEmpty()) {
	        for (INT6_SND ArrayRequest : ArrayReq) {
	        	//null test
	        	 if (ArrayRequest == null || ArrayRequest.getOrder() == null || ArrayRequest.getOrder().get(0) == null) {
	                 logger.severe("ArrayRequest or its order is null");
	                 continue;
	             }
	        	logger.info("RUN INFO " + ArrayRequest.getOrder().get(0).getTraxWO());
	          JAXBContext jc = JAXBContext.newInstance(INT6_SND.class);
	          Marshaller marshaller = jc.createMarshaller();
	          marshaller.setProperty(
	            Marshaller.JAXB_FORMATTED_OUTPUT,
	            Boolean.TRUE
	          );
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
	            logger.severe("Unable to send XML " + " to URL " + url);
	            Import_TC_MHR_Controller.addError(
	              "Unable to send XML " +
	              " to URL " +
	              url +
	              " MAX_ATTEMPTS: " +
	              MAX_ATTEMPTS
	            );
	          } else {
	            INT6_TRAX input = null;

	            logger.info("finishing");
	            logger.info(
	              "POST status: " + String.valueOf(success) + " to URL: " + url
	            );
	          }
	        }
	      }
	      if (!Import_TC_MHR_Controller.getError().isEmpty()) {
	        throw new Exception("Issue found");
	      }
	    } catch (Throwable e) {
	      logger.severe(e.toString());
	      Import_TC_MHR_Controller.addError(e.toString());
	      Import_TC_MHR_Controller.sendEMailRequest(ArrayReq);
	    }
	  }

	  public void run() {
	    try {
	      if (data.lockAvailable("I06")) {
	        data.lockTable("I06");
	        process();
	        data.unlockTable("I06");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	
}
