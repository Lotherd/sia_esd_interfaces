package trax.aero.application;


import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import trax.aero.controller.InstallRemoveController;
import trax.aero.data.InstallRemoveData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.Application_Log;
import trax.aero.pojo.IE4N;
import trax.aero.pojo.MT_TRAX_RCV_I43_4076_RES;










@Path("/InstallRemoveService")
public class Service {
	
	Logger logger = LogManager.getLogger("InstallRemove_I20");
	
	@POST
	@Path("/IE4NButton")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response IE4NButton(IE4N b)
	{
		InstallRemoveData data = new InstallRemoveData();
		String fianl = "{\n\"status\": \"OK\", \n\"statusCode\": \"200\"\n}";
		String exceuted = "OK";
		
		try 
        {   
			
			ObjectMapper Obj = new ObjectMapper();
			String json;
			try {
				json = Obj.writeValueAsString(b);
			} catch (JsonProcessingException e) {
				json = "JsonProcessingException";
			}
			
			if(b.getTrans() != null && !b.getTrans().isEmpty() &&
				(b.getTransaction() == null || b.getTransaction().isEmpty()) 
				) {
				b.setTransaction(b.getTrans());
				
							
			}
			
			
			
			logger.info("Input: " + json);
			exceuted = data.Button(b);
			
        	
        	if(exceuted == null || !exceuted.equalsIgnoreCase("OK")) {
        		throw new Exception("Issue found");
        	}else {
        		exceuted = fianl;
        	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.severe(e.toString());
			ResponseBuilder R = Response.serverError().status(Response.Status.BAD_REQUEST);
		    R.entity((exceuted));
		    R.type(MediaType.TEXT_PLAIN_TYPE);
		    InstallRemoveController.addError(e.toString());
			InstallRemoveController.sendEmailButton(b);
			 return R.build();
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
				ResponseBuilder R = Response.serverError().status(Response.Status.BAD_REQUEST);
			    R.entity((e.toString()));
			    R.type(MediaType.TEXT_PLAIN_TYPE);
			    return R.build();
			}
    	   logger.info("finishing");
       }
	   return Response.ok(exceuted,MediaType.APPLICATION_JSON).build();
	}
	
	
	@POST
	@Path("/markTransaction")
	@Consumes(MediaType.APPLICATION_XML + ";charset=UTF-8" )
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8")
	public Response markTransaction(MT_TRAX_RCV_I43_4076_RES request)
	{
		String exceuted = "OK";
		InstallRemoveData data = new InstallRemoveData();
		try 
        {   
			JAXBContext jc = JAXBContext.newInstance(MT_TRAX_RCV_I43_4076_RES.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(request, sw);
			
			logger.info("Input: " + sw.toString());
			
			String message = "";
		    
		    for(Application_Log l: request.getApplication_Log()) {
			    if(l.getMessage_Type().equalsIgnoreCase("I")) {
			    	logger.info("SETTING MESSAGE TYPE: " +request.getApplication_Log().get(0).getMessage_Type() +" TO W");
			    	l.setMessage_Type("W");
			    }
			    message = message +l.getMessage_Type()+ "-" +l.getMessage_Text()+ "\n";	 
		    }
		    
		   
		    data.markTransaction(request);
		    
		   	
			if(data.containsType(request.getApplication_Log(), "E")){
				
				exceuted = "Received acknowledgement:\n" 
				+ message 
				+"Reference order: "+request.getHeader().getReference_Order() 
				+ " Transaction: " + request.getHeader().getTRAXTRANS() ;
				
				InstallRemoveController.addError(exceuted ) ;
				InstallRemoveController.sendEmailInbound(request.getApplication_Log().get(0));
				
				
				
			}else if(data.containsType(request.getApplication_Log(), "W")) {
				

				exceuted = "Received acknowledgement:\n" 
						+ message 
						+"Reference order: "+request.getHeader().getReference_Order() 
						+ " Transaction: " + request.getHeader().getTRAXTRANS() ;						
						InstallRemoveController.addError(exceuted ) ;
						InstallRemoveController.sendEmailInbound(request.getApplication_Log().get(0));

			}
			
		}
		catch(Exception e)
		{
			logger.severe(e.toString());
			exceuted = 	e.toString();
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
				logger.severe(e.toString());
			}
    	   logger.info("finishing");
       }
		return Response.ok(exceuted,MediaType.APPLICATION_XML + ";charset=UTF-8").build();
	}
	
	@GET
    @Path("/healthCheck")
    @Produces(MediaType.APPLICATION_JSON)
    public Response healthCheck() 
    {    	
		logger.info("Healthy");
    	return Response.ok("Healthy",MediaType.APPLICATION_JSON).build();
    }
	
}