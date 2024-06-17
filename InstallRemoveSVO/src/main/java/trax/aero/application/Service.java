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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import trax.aero.controller.InstallRemoveSVOController;
import trax.aero.data.InstallRemoveSvoData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.I19_Response;





@Path("/Service")
public class Service {
	
	Logger logger = LogManager.getLogger("InstallRemoveSVO_I19");
	
	@POST
	@Path("/markTransaction")
	@Consumes(MediaType.APPLICATION_XML + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public Response markTransaction(I19_Response input)
	{
		String exceuted = "OK";
		
		InstallRemoveSvoData data = new InstallRemoveSvoData("mark");
		
		try 
        {    
			
			JAXBContext jc = JAXBContext.newInstance(I19_Response.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(input, sw);
			
			logger.info("Input: " + sw.toString());
			
			 
			
			if(input.getExceptionDetail() != null && !input.getExceptionDetail().isEmpty()) {
				logger.severe("Received Response with Exception: " + input.getExceptionDetail() +",Transaction: "+input.getTransaction() + ", Exception ID: " +input.getExceptionId());
				InstallRemoveSVOController.addError("Received Response with Exception: " + input.getExceptionDetail() +", Order Number: "+input.getTransaction() + ", Exception ID: " +input.getExceptionId());
				exceuted = "Issue found";
			}else {
				exceuted = data.markTransaction(input);
			}
        	if(!exceuted.equalsIgnoreCase("OK")) {
        		exceuted = "Issue found";
        		throw new Exception("Issue found");
        	}
		}
		catch(Exception e)
		{
			InstallRemoveSVOController.addError(e.toString());
			InstallRemoveSVOController.sendEmailResponse(input);
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
	   return Response.ok(exceuted,MediaType.APPLICATION_XML + ";charset=utf-8").build();
	}
	
	
	@GET
    @Path("/healthCheck")
    @Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
    public Response healthCheck() 
    {    	
		logger.info("Healthy");
    	return Response.ok("Healthy",MediaType.APPLICATION_JSON).build();
    }
	
}