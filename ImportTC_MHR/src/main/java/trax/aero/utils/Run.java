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
	  final int MAX_ATTEMPTS = Integer.parseInt(System.getProperty("ImportTcMhr_MAX_ATTEMPTS", "-1"));
	    final long RETRY_INTERVAL = Long.parseLong(System.getProperty("ImportTcMhr_RETRY_INTERVAL", "180")) * 1000; // convert seconds to milliseconds
	  Logger logger = LogManager.getLogger("ImportTC_MHR");


	  public Run() {
			data = new Import_TC_MHR_Data();
		}
		
		private void process() {
			Poster poster = new Poster();
			ArrayList<INT6_SND> ArrayReq = new ArrayList<INT6_SND>();
			String executed = "OK";
			
			try {
				ArrayReq = data.getTaskCards();
				
				
				if(!ArrayReq.isEmpty()) {
					for (INT6_SND ArrayRequest : ArrayReq) {
						if (!ArrayRequest.getOrder().isEmpty()) {
						logger.info("RUN INFO " + ArrayRequest.getOrder());
					
					}else {
	                    logger.info("RUN INFO: Order list is empty");
	                }
					
						boolean success = false;
	                    int attempt = 1;

	                    while (MAX_ATTEMPTS == -1 || attempt <= MAX_ATTEMPTS) {
	                    	try {
					JAXBContext jc = JAXBContext.newInstance(INT6_SND.class);
					Marshaller marshaller = jc.createMarshaller();
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					StringWriter sw = new StringWriter();
	                marshaller.marshal(ArrayRequest, sw);
	                String xmlContent = sw.toString();
					
	                logger.info("Attempt " + attempt + " to send Work Order: " + ArrayRequest.getOrder());
	                logger.info("XML Content: " + xmlContent);
	                
	                // Send the XML content
	                success = poster.post(ArrayRequest, url);
	                if (success) {
	                    logger.info("POST successful for Work Order: " + ArrayRequest.getOrder());
	                    break;
	                }else {
	                    if (attempt == MAX_ATTEMPTS) {
	                        logger.warning("Attempt " + attempt + " failed. Maximum attempts reached. No more retries.");
	                    } else {
	                        logger.warning("Attempt " + attempt + " failed. Retrying in " + (RETRY_INTERVAL / 1000) + " seconds...");
	                        Thread.sleep(RETRY_INTERVAL);
	                    }
	                    attempt++;
	                }
	            } catch (Exception e) {
	                logger.severe("Error during attempt " + attempt + ": " + e.getMessage());
	                if (attempt == MAX_ATTEMPTS) {
	                    logger.severe("Maximum attempts reached. No more retries.");
	                }
	                attempt++;
	            }
	        }
	                    if (!success) {
	                        logger.severe("Unable to send XML to URL " + url);
	                        Import_TC_MHR_Controller.addError("Unable to send XML to URL " + url + " after " + (MAX_ATTEMPTS == -1 ? "infinite" : MAX_ATTEMPTS) + " attempts.");
	                    } else {
	                        logger.info("POST status: true to URL: " + url);
	                    }
	                    
					  }
	            }
					
				if (!Import_TC_MHR_Controller.getError().isEmpty()) {
					throw new Exception("Issue found");
				}
			}catch(Throwable e){
				logger.severe(e.toString());
				Import_TC_MHR_Controller.addError(e.toString());
				Import_TC_MHR_Controller.sendEMailRequest(ArrayReq);
			}
		}

		public void run() {
	    try {
	      if (data.lockAvailable("I6")) {
	        data.lockTable("I6");
	        process();
	        data.unlockTable("I6");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	
}
