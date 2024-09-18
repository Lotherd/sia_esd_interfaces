package trax.aero.utils;

import trax.aero.pojo.PrintQueueJob;
import java.io.*;
import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


public class PrintPoster {

	
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
