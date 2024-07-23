package trax.aero.application;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
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


import trax.aero.controller.Part_Requisition_Controller;
import trax.aero.data.Part_Requisition_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.Component_TRAX;
import trax.aero.pojo.INT13_TRAX;
import trax.aero.pojo.OpsLineEmail;

@Path("/PartRequisition")
public class Service {
	
	Logger logger = LogManager.getLogger("PartREQ");
	
	
	
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
		
		Part_Requisition_Data data = new Part_Requisition_Data();
		
		String executed = "OK";
		
		try {
			executed = data.setOpsLine(opsLine, email);
		} catch(Exception e) {
			executed = e.toString();
			Part_Requisition_Controller.addError(e.toString());
			Part_Requisition_Controller.sendEmailService(executed);
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
	public Response deleteOpsdLine(@QueryParam("opsLine") String opsline) {
		Part_Requisition_Data data = new Part_Requisition_Data();
		
		String executed = "OK";
		
		try {
			data.deleteOpsLine(opsline);
		} catch(Exception e) {
			executed = e.toString();
			logger.severe(e.toString());
			Part_Requisition_Controller.addError(e.toString());
			Part_Requisition_Controller.sendEmailService(executed);
		} finally {
			try {
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			}catch(SQLException e) {
				executed = e.toString();
			}
			logger.info("finishing");
		}
		
		return Response.ok(executed,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/getEmail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmail(@QueryParam("opsLine") String opsline) {
		Part_Requisition_Data data = new Part_Requisition_Data();
		String executed = "OK";
		String group = null;
		
		try {
			group = data.getemailByOpsLine(opsline);
			if(group == null) {
				executed = "Issue found";
				throw new Exception("Issue found");
						
			}
		} catch(Exception e) {
			Part_Requisition_Controller.addError(e.toString());
			Part_Requisition_Controller.sendEmailService(executed);
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
	
	
	@GET
	@Path("/setSite")
	@Produces(MediaType.APPLICATION_JSON)
	public Response setSite(@QueryParam("site") String site, @QueryParam("recipient") String recipient )
	{
		Part_Requisition_Data data = new Part_Requisition_Data();
		String executed = "OK";
		                              
		try 
        {    		 
        	executed = data.setSite(site,recipient);
		}
		catch(Exception e)
		{
			Part_Requisition_Controller.addError(e.toString());
			Part_Requisition_Controller.sendEmailService(executed);
		} finally {
			try {
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			} catch(Exception e) {
				executed = e.toString();
			}
			logger.info("finishing");
		}
	   return Response.ok(executed,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/deleteSite")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSite(@QueryParam("site") String site )
	{
		Part_Requisition_Data data = new Part_Requisition_Data();
		String executed  = "OK";
		                              
		try 
        {    		 
			data.deleteSite(site);
		}
		catch(Exception e)
		{
			Part_Requisition_Controller.addError(e.toString());
			Part_Requisition_Controller.sendEmailService(executed);
		} finally {
			try {
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			} catch(Exception e) {
				executed = e.toString();
			}
			logger.info("finishing");
		}
	   return Response.ok(executed ,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/getSite")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSite(@QueryParam("site") String site )
	{
		Part_Requisition_Data data = new Part_Requisition_Data();
		String executed = "OK";
		
		String group = null;
		                              
		try 
        {    		 
			group = data.getSite(site);
        	if(group == null ) {
        		executed = "Issue found";
        		throw new Exception("Issue found");
        	}
		}
		catch(Exception e)
		{
			Part_Requisition_Controller.addError(e.toString());
			Part_Requisition_Controller.sendEmailService(executed);
		} finally {
			try {
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			} catch(Exception e) {
				executed = e.toString();
			}
			logger.info("finishing");
		}
		
		
	   return Response.ok(group,MediaType.APPLICATION_JSON).build();
	}
	
	
	
	@POST
	@Path("/markTransaction")
	@Consumes(MediaType.APPLICATION_XML + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public Response markTransaction(INT13_TRAX input) {
		String executed = "OK";
		
		Part_Requisition_Data data = new Part_Requisition_Data("mark");
		try {
			JAXBContext jc = JAXBContext.newInstance(INT13_TRAX.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(input,  sw);
			
			String xmlContent = sw.toString();
			
			int xmlStart = xmlContent.indexOf("<?xml");
			if(xmlStart > 0) {
				xmlContent = xmlContent.substring(xmlStart);
			}
			
			 xmlContent = xmlContent.replaceAll("[^\\x20-\\x7e]", "");
		     
			 logger.info("Input: " + xmlContent);
			 
			 executed = data.markTransaction(input);
			 if(!executed.equalsIgnoreCase("OK")) {
				 executed = "Issue Found";
				 throw new Exception("Issue found");
			 }
			
		}catch(Exception e) {
			logger.severe(e.toString());
			Part_Requisition_Controller.addError(e.toString());
			for(Component_TRAX c : input.getComponent()) {
				OpsLineEmail opsLineEmail = data.getOpsLineStaffName(c.getRequisition());
				Part_Requisition_Controller.sendEmailOpsLine(null, input, c, opsLineEmail);
			}
		}finally {
			try {
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			logger.info("finishing");
		}
		
		
		return Response.ok(executed, MediaType.APPLICATION_XML + ";chartset=utf-8").build();
	}
	
}
