package trax.aero.application;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Logger;

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

import trax.aero.controller.CreationBatch_Controller;
import trax.aero.data.CreationBatch_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT30_TRAX;

@Path("/CreationBacth")
public class Service {
	
	Logger logger = LogManager.getLogger("BatchCreation");
	
	@GET
	@Path("/healthCheck")
	@Produces(MediaType.TEXT_PLAIN)
	public Response healthCheck() {
		logger.info("Healthy");
		return Response.ok("Healthy", MediaType.TEXT_PLAIN).build();
	}
	
	@GET
	@Path("/setOpsLine")
	@Produces(MediaType.APPLICATION_JSON)
	public Response setOpsLine(@QueryParam("opsLine") String opsLine, @QueryParam("email") String email) {
		
		CreationBatch_Data data = new CreationBatch_Data();
		
		String executed = "OK";
		
		try {
			executed = data.setOpsLine(opsLine, email);
		} catch(Exception e) {
			executed = e.toString();
			CreationBatch_Controller.addError(e.toString());
			CreationBatch_Controller.sendEmailService(executed);
			logger.severe(executed);
		} finally {
			try {
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			}
			catch(SQLException e) {
				executed = e.toString();
			}
			logger.info("finishing");
		}
		
		return Response.ok(executed,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/deleteOpsLine")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteOpsLine(@QueryParam("opsLine") String opsLine) {
		
		CreationBatch_Data data = new CreationBatch_Data();
		String executed = "OK";
		
		try {
			data.deleteOpsLine(opsLine);
		} catch(Exception e) {
			executed = e.toString();
			logger.severe(e.toString());
			CreationBatch_Controller.addError(e.toString());
			CreationBatch_Controller.sendEmailService(executed);
		} finally {
			try {
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			} catch(SQLException e) {
				executed = e.toString();
			}
			logger.info("finishing");
		}
		
		return Response.ok(executed,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/getEmail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmail(@QueryParam("opsLine") String opsLine) {
		CreationBatch_Data data = new CreationBatch_Data();
		String executed = "OK";
		String group = null;
		
		try {
			group = data.getemailByOpsLine(opsLine);
			if(group == null) {
				executed = "Issue found";
				throw new Exception("Issue found");
						
			}
		} catch(Exception e) {
			CreationBatch_Controller.addError(e.toString());
			CreationBatch_Controller.sendEmailService(executed);
		} finally {
			try {
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			} catch(Exception e) {
				executed = e.toString();
			}
			logger.info("finishing");
		}
		
		return Response.ok(group, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/markTransaction")
	@Consumes(MediaType.APPLICATION_XML + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public Response markTransaction(INT30_TRAX input) {
String executed = "OK";
		
CreationBatch_Data data = new CreationBatch_Data("mark");
		try {
			JAXBContext jc = JAXBContext.newInstance(INT30_TRAX.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(input,  sw);
			
			logger.info("Input: " + sw.toString());
			
			if(input.getEXCEPTION_ID() != null && !input.getEXCEPTION_ID().isEmpty() && input.getEXCEPTION_ID().equalsIgnoreCase("53"))  {
		    	executed = data.markTransaction(input);
		    } else {
		    	logger.severe("Received Response with Remarks: " + input.getEXCEPTION_DETAIL() +", Material: "+input.getPN() + ", Error Code: " +input.getEXCEPTION_ID());
		    	CreationBatch_Controller.addError("Received Response with Remarks: " + input.getEXCEPTION_DETAIL() +", Material: "+input.getPN() + ", Error Code: " +input.getEXCEPTION_ID());
		    	executed = data.markTransaction(input);
		    	executed = data.markTransaction(input);
		    	executed = "Issue found";
		    }
			if(executed == null || !executed.equalsIgnoreCase("OK")) {
		    	executed = "Issue found";
        		throw new Exception("Issue found");
		    }
		}
		catch(Exception e)
		{
			
			CreationBatch_Controller.addError(e.toString());
			CreationBatch_Controller.sendEmailRequest(null);
			
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
	   return Response.ok(executed,MediaType.APPLICATION_XML + ";charset=utf-8").build();
	}

}
