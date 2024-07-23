package trax.aero.application;

import trax.aero.controller.Actual_Hours_Controller;
import trax.aero.data.Actual_Hours_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT31_TRAX;
import trax.aero.pojo.OpsLineEmail;

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

@Path("/ActualHours")
public class Service {
	
	Logger logger = LogManager.getLogger("ActualHours");
	
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
		
		Actual_Hours_Data data = new Actual_Hours_Data();
		
		String executed = "OK";
		
		try {
			executed = data.setOpsLine(opsLine, email);
		} catch(Exception e) {
			executed = e.toString();
			Actual_Hours_Controller.addError(e.toString());
			Actual_Hours_Controller.sendEmailService(executed);
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
		
		Actual_Hours_Data data = new Actual_Hours_Data();
		String executed = "OK";
		
		try {
			data.deleteOpsLine(opsLine);
		} catch(Exception e) {
			executed = e.toString();
			logger.severe(e.toString());
			Actual_Hours_Controller.addError(e.toString());
			Actual_Hours_Controller.sendEmailService(executed);
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
		Actual_Hours_Data data = new Actual_Hours_Data();
		String executed = "OK";
		String group = null;
		
		try {
			group = data.getemailByOpsLine(opsLine);
			if(group == null) {
				executed = "Issue found";
				throw new Exception("Issue found");
						
			}
		} catch(Exception e) {
			Actual_Hours_Controller.addError(e.toString());
			Actual_Hours_Controller.sendEmailService(executed);
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
	
	/*@POST
	@Path("/markTransaction")
	@Consumes(MediaType.APPLICATION_XML + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public Response markTransaction(INT31_TRAX input) {
		String executed = "OK";
		Actual_Hours_Data data = new Actual_Hours_Data("mark");
		try {
			JAXBContext jc = JAXBContext.newInstance(INT31_TRAX.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(input, sw);
			
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
			Actual_Hours_Controller.addError(e.toString());
				OpsLineEmail opsLineEmail = data.getOpsLineStaffName(input.getRfoNo());
				Actual_Hours_Controller.sendEmailOpsLine(null, input, opsLineEmail);
		} finally {
			try {
				if(data.getCon() != null && !data.getCon().isClosed())
					data.getCon().close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			logger.info("finishing");
		}
		
		return Response.ok(executed, MediaType.APPLICATION_XML + ";chartset=utf-8").build();
	}*/
}
