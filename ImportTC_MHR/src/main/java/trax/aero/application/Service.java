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

import trax.aero.controller.Import_TC_MHR_Controller;
import trax.aero.data.Import_TC_MHR_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT6_TRAX;
import trax.aero.pojo.OperationSND;
import trax.aero.pojo.OperationTRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.OrderSND;
import trax.aero.pojo.OrderTRAX;


@Path("/ImportTC_MHR")
public class Service {
	
	Logger logger = LogManager.getLogger("ImportTC_MHR");
	
	@POST
	@Path("/markTransaction")
	@Consumes(MediaType.APPLICATION_XML + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public Response markTransaction(INT6_TRAX input) {
		
		String executed = "OK";
		Import_TC_MHR_Data data = new Import_TC_MHR_Data("mark");
		try {
			JAXBContext jc = JAXBContext.newInstance(INT6_TRAX.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(input, sw);
			
			// Get the marshalled XML as a string
	        String xmlContent = sw.toString();
	        
	        // Remove any characters before the XML declaration
	        int xmlStart = xmlContent.indexOf("<?xml");
	        if (xmlStart > 0) {
	            xmlContent = xmlContent.substring(xmlStart);
	        }
	        
	        // Remove any non-printable characters
	        xmlContent = xmlContent.replaceAll("[^\\x20-\\x7e]", "");
	        
	        // Log the cleaned XML content
	        logger.info("Input: " + xmlContent);
			
			executed = data.markTransaction(input);
			if(!executed.equalsIgnoreCase("OK")){
					executed = "Issue found";
					throw new Exception("Issue found");
					}
			
		} catch(Exception e) {
			logger.severe(e.toString());
			Import_TC_MHR_Controller.addError(e.toString());
			for(OrderTRAX o : input.getOrder()) {
				if(o.getOperations() != null && !o.getOperations().isEmpty()) {
					for(OperationTRAX op : o.getOperations()) {
						
						OpsLineEmail opsLineEmail = data.getOpsLineStaffName(o.getWo(), op.getTaskCard());
						
						
	                    Import_TC_MHR_Controller.sendEmailOpsLine(op.getOpsNo(), o, op, opsLineEmail);
					}
				} 
			} 
		}finally {
				try {
					if(data.getCon() != null && !data.getCon().isClosed())
						data.getCon().close();
				} catch(SQLException e){
					e.printStackTrace();
				}
				logger.info("finishing");
			}
			
			return Response.ok(executed, MediaType.APPLICATION_XML + ";chartset=utf-8").build();
	}
	
	@GET
	@Path("/setOpsLine")
	@Produces(MediaType.APPLICATION_JSON)
	public Response setOpsLine(@QueryParam("opsLine") String opsLine, @QueryParam("email") String email) {
		
		Import_TC_MHR_Data data = new Import_TC_MHR_Data();
		
		String executed = "OK";
		
		try {
			executed = data.setOpsLine(opsLine, email);
		} catch(Exception e) {
			executed = e.toString();
			Import_TC_MHR_Controller.addError(e.toString());
			Import_TC_MHR_Controller.sendEmailService(executed);
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
		
		Import_TC_MHR_Data data = new Import_TC_MHR_Data();
		String executed = "OK";
		
		try {
			data.deleteOpsLine(opsLine);
		} catch(Exception e) {
			executed = e.toString();
			logger.severe(e.toString());
			Import_TC_MHR_Controller.addError(e.toString());
			Import_TC_MHR_Controller.sendEmailService(executed);
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
		Import_TC_MHR_Data data = new Import_TC_MHR_Data();
		String executed = "OK";
		String group = null;
		
		try {
			group = data.getemailByOpsLine(opsLine);
			if(group == null) {
				executed = "Issue found";
				throw new Exception("Issue found");
						
			}
		} catch(Exception e) {
			Import_TC_MHR_Controller.addError(e.toString());
			Import_TC_MHR_Controller.sendEmailService(executed);
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
	@Path("/healthCheck")
	@Produces(MediaType.TEXT_PLAIN)
	public Response healthCheck() {
		logger.info("Healthy");
    	return Response.ok("Healthy",MediaType.TEXT_PLAIN).build();
	}

}
