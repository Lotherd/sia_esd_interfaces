package trax.aero.utils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.google.gson.Gson;

import trax.aero.pojo.PrintQueueJob;
import trax.aero.pojo.Root;


public class PrintPoster {

	
	public int sendPrintJob(String printWindow, Root dw) {
		int job = 0;
		String s_wo_print = "Inventory Detail Transaction History Print - All History Detail";

		try {

			StringWriter sw = new StringWriter();
			JAXBContext jc = JAXBContext.newInstance(Root.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.marshal(dw, sw);
			String xml = sw.toString();
					
			xml = xml.replaceAll("(<\\?xml.*?\\?>)","");
			xml = xml.replaceAll("<root>","");
			xml = xml.replaceAll("</root>","");
			 System.out.println("xml: "+xml);

			PrintQueueJob propJob = new PrintQueueJob();
			propJob.setDatasourceJNDI("java:/emroDS");
			propJob.setPrintWindow(printWindow);
			propJob.setSelectedPrint("All History Detail");
			propJob.setPrintTitle(s_wo_print);
			propJob.setPrintParameters(xml.getBytes());
			propJob.setUser("ADM");
			propJob.setComparisonOperator("=");
			addJobToJMSQueue(propJob, "N");

			 System.out.println("Print Job " +  dw.getDw_inventory_detail_history_print_sel().getRow().getBatch() + " Has been successfuly sent to the print queue");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return job;
	}
	
	private static void addJobToJMSQueue(PrintQueueJob propJob, String isWPP) throws Exception {
		propJob.setIsWPP(isWPP);

		

		String url = "Y".equals(isWPP) ? System.getProperty("Trax_Print_WPP_URL") + "rest/print/printwpp"
				: System.getProperty("Trax_Print_URL") + "rest/print/print";
		Client client = null;

		try {
			String jsonString="";
			
				Gson gson = new Gson();				
				jsonString = gson.toJson(propJob);
				//System.out.println(gson.toJson(jsonString));
							
			
			client = ClientBuilder.newClient();
			System.out.println("Calling Print web service : " + "'" + url + "'");

			client.target(url).request().post(Entity.entity(jsonString, MediaType.APPLICATION_JSON));

			System.out.println("After Print web service ");

		} catch (Exception e) {
			throw e;
		} finally {
			if (client != null)
				client.close();
		}
	}
	
	
	public void addJobToJMSQueueService(String jndiName , String printWindow , String printTitle, String user,BigDecimal seq , Object... parameters ) 
    {

         byte[] params = null;   
         
            
         try (ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    ObjectOutputStream oos = new ObjectOutputStream(baos);) {

                oos.writeObject(parameters);

                params = baos.toByteArray();

            } catch (Exception ioe) {
            	ioe.printStackTrace();
               return ; 

            }
        PrintQueueJob propJob = new PrintQueueJob(jndiName);

        propJob.setPrintWindow(printWindow);

        propJob.setPrintTitle(printTitle);

        propJob.setObjectParameters(params);

        propJob.setUser(user);        
        propJob.setJobId(seq.intValue());
        
  
        if("oux_form_response_print".equalsIgnoreCase(printWindow))
        	propJob.setUploadPdf(true);
		propJob.setIsWPP("N");

        System.out.println("addJobToJMSQueue: " + propJob.getJobId());
        String printUrl = "";
        printUrl = System.getProperty("Trax_Print_URL");
        

        if (printUrl == null) {
            System.out.println("Print URL null");
            return;
        }

        String url = printUrl + "rest/print/print";
        Client client = null;
        Response response = null;
        
        String jsonString = "" ; // 362471
        
        try
        {
            Gson gson = new Gson();
            jsonString = gson.toJson(propJob);
            System.out.println(jsonString);
        }
        catch(Exception e)
        {
            
        }
        try {
            client = ClientBuilder.newClient();
            System.out.println("Calling Print web service : " + "'" + url + "'");

            response = client.target(url).request().post(Entity.entity(jsonString, MediaType.APPLICATION_JSON)); // 362471

            System.out.println("Response from trax print server : " + response.getStatus() + " " + response.getStatusInfo().getReasonPhrase());
            
            System.out.println("After Print web service. Job " + propJob.getJobId() + " sent to Print queue.");

        } catch (Exception e) {
            System.out.println("Calling Print web service error " + e.getMessage());
        } finally {
            if (client != null)
                client.close();
        }
    
    }
	
}
