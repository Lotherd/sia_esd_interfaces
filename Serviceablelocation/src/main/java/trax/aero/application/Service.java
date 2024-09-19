package trax.aero.application;



import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import trax.aero.controller.ServiceablelocationController;
import trax.aero.data.ServiceablelocationData;
import trax.aero.interfaces.IServiceablelocationData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.MT_TRAX_RCV_I28_4134_RES;
import trax.aero.pojo.MultipartBody;



@Path("/ServiceablelocationService")
public class Service {
		
	Logger logger = LogManager.getLogger("Serviceablelocation_I28");
	
	@EJB IServiceablelocationData data;
	
	@POST
	@Path("/markTransaction")
	@Consumes(MediaType.APPLICATION_XML + ";charset=UTF-8" )
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8")
	public Response markTransaction(MT_TRAX_RCV_I28_4134_RES response)
	{
		String exceuted = "OK";
		
		try 
        {
			
			JAXBContext jc = JAXBContext.newInstance(MT_TRAX_RCV_I28_4134_RES.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(response, sw);
			
			logger.info("Input: " + sw.toString());
			data.openCon();
			if(response.getExceptionId().equalsIgnoreCase("53")) {
				data.markTransaction(response);
				data.setInspLot(response);
				data.printLabel(response);
			}else {
				data.markTransaction(response);
				data.setComplete(response);
				exceuted = ("RFO: " +  response.getRfo()
				+ ", Date: " + new Date().toString()  + ", SHOP WO: " +response.getWo()    );
				
				logger.severe(exceuted);
				ServiceablelocationController.addError(exceuted);
				
				exceuted = ("Received acknowledgement with Error Code: " + response.getExceptionId() 
				+", Status Message: "+response.getExceptionDetail()) ;
				
				logger.severe(exceuted);
				ServiceablelocationController.addError(exceuted);
				
				exceuted = "Issue found";
				ServiceablelocationController.sendEmailACK(response.getWo());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
       finally 
       {
    	   try 
			{
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace();
			}
    	   logger.info("finishing");
       }
		return Response.ok(exceuted,MediaType.APPLICATION_XML + ";charset=UTF-8").build();
	}
	
	@GET
    @Path("/healthCheck")
    @Produces(MediaType.APPLICATION_XML + ";charset=UTF-8")
    public Response healthCheck() 
    {    	
		logger.info("Healthy");
    	return Response.ok("Healthy",MediaType.APPLICATION_XML + ";charset=UTF-8").build();
    }
	
	
	@POST
	@Path("/printFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response  CarryForwardPrint(
			@MultipartForm MultipartBody body)
	{
		String fianl = "{\n\"status\": \"OK\", \n\"statusCode\": \"200\"\n}";
		String exceuted = "OK";
		try 
        {   
			
	           	logger.info("Input: " + body.json.toString());
	          
	           	
	        	exceuted = data.print(body.json.getWo(), body.json.getTask_card(), IOUtils.toByteArray(body.file),
	        			body.json.getForm_No(), body.json.getForm_Line());
	        	
	        
        	
        	
        	if(exceuted == null || !exceuted.equalsIgnoreCase("OK")) {
        		exceuted = "Issue found";
        		throw new Exception("Issue found");
        	}else {
        		exceuted = fianl;
        	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
       finally 
       {   
    	   try 
			{
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			} 
			catch (SQLException e) 
			{ 
				exceuted = e.toString();
			}
    	   logger.info("finishing");
       }
        
	   return Response.ok(exceuted,MediaType.APPLICATION_JSON).build();
	}
	
}