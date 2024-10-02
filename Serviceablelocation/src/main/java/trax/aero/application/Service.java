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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.IOUtils;

import trax.aero.controller.ServiceablelocationController;
import trax.aero.data.ServiceablelocationData;
import trax.aero.interfaces.IServiceablelocationData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.MT_TRAX_RCV_I28_4134_RES;



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
				data.markTransaction(response, null);
				data.setInspLot(response, null);
				data.printLabel(response, null);
			}else {
				data.markTransaction(response, null);
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
	
	
	
	
	
	
	
	
	@GET
	@Path("/setCondition")
	@Produces(MediaType.APPLICATION_JSON)
	public Response setCondition(@QueryParam("condition") String condition, @QueryParam("status") String status,
	 @QueryParam("code") String code)
	{
		
		String exceuted = "OK";
		                              
		try 
        {    		 
        	exceuted = data.setCondition(condition,status,code);
		}
		catch(Exception e)
		{
			exceuted = e.toString();
			logger.severe(e.toString());
			
		}
       finally 
       {   
    	  
			
    	   logger.info("finishing");
       }
	   return Response.ok(exceuted,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/deleteCondition")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCondition(@QueryParam("condition") String condition, @QueryParam("status") String status,
			 @QueryParam("code") String code )
	{
		
		String exceuted = "OK";
		                              
		try 
        {    		 
			data.deleteCondition(condition);
		}
		catch(Exception e)
		{
			exceuted = e.toString();
			logger.severe(e.toString());
		
		}
       finally 
       {   
    	   
    	   logger.info("finishing");
       }
	   return Response.ok(exceuted,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/getCondition")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCondition(@QueryParam("condition") String site )
	{
		
		String exceuted = "OK";
		
		String group = null;
		                              
		try 
        {    		 
			group = data.getCondition(site);
        	if(group == null ) {
        		exceuted = "Issue found";
        		throw new Exception("Issue found");
        	}
		}
		catch(Exception e)
		{
			exceuted = e.toString();
			logger.severe(e.toString());
		
		}
       finally 
       {   
    	   
    	   logger.info("finishing");
       }
		
		
	   return Response.ok(group,MediaType.APPLICATION_JSON).build();
	}
	
	
	
	@GET
	@Path("/setAuthority")
	@Produces(MediaType.APPLICATION_JSON)
	public Response setAuthority(@QueryParam("authority") String authority
			, @QueryParam("code") String code)
	{
		
		String exceuted = "OK";
		                              
		try 
        {    		 
        	exceuted = data.setAuthority(authority, code);
		}
		catch(Exception e)
		{
			exceuted = e.toString();
			logger.severe(e.toString());
			
		}
       finally 
       {   
    	  
			
    	   logger.info("finishing");
       }
	   return Response.ok(exceuted,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/deleteAuthority")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAuthority(@QueryParam("authority") String authority
			, @QueryParam("code") String code )
	{
		
		String exceuted = "OK";
		                              
		try 
        {    		 
			data.deleteAuthority(authority, code);
		}
		catch(Exception e)
		{
			exceuted = e.toString();
			logger.severe(e.toString());
		
		}
       finally 
       {   
    	   
    	   logger.info("finishing");
       }
	   return Response.ok(exceuted,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/getAuthority")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthority(@QueryParam("authority") String site )
	{
		
		String exceuted = "OK";
		
		String group = null;
		                              
		try 
        {    		 
			group = data.getAuthority(site);
        	if(group == null ) {
        		exceuted = "Issue found";
        		throw new Exception("Issue found");
        	}
		}
		catch(Exception e)
		{
			exceuted = e.toString();
			logger.severe(e.toString());
		
		}
       finally 
       {   
    	   
    	   logger.info("finishing");
       }
		
		
	   return Response.ok(group,MediaType.APPLICATION_JSON).build();
	}
	
}