package trax.aero.application;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

import trax.aero.controller.Creation_Sales_Controller;
import trax.aero.data.Creation_Sales_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.INT7_SND;
import trax.aero.pojo.INT7_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.OrderSND;

@Path("/CreationSales")
public class Service {
	
	Logger logger = LogManager.getLogger("CreationSales");
	private ScheduledExecutorService scheduler;

	@PostConstruct
    public void init() {
        logger.info("Initializing scheduler for fallback task.");
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                checkPendingTransactions();
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

    @PreDestroy
    public void shutdown() {
        if (scheduler != null && !scheduler.isShutdown()) {
            logger.info("Shutting down scheduler.");
            scheduler.shutdown();
        }
    }

    private void checkPendingTransactions() {
        logger.info("Checking for pending transactions exceeding 10 minutes.");
        Creation_Sales_Data data = new Creation_Sales_Data();
        
        try {
            String sqlPendingTransactions = "SELECT WO FROM WO WHERE STATUS = 'OPEN' AND INTERFACE_ESD_TRANSFERRED_FLAG IS NULL AND SYSDATE - INTERFACE_ESD_TRANSFERRED_DATE > 10 / (24 * 60)";
            
            try (Connection con = data.getCon();
                 PreparedStatement ps = con.prepareStatement(sqlPendingTransactions);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String wo = rs.getString("WO");
                    logger.warning("Fallback triggered for WO: " + wo);

                    String sqlInsertError = "INSERT INTO interface_audit (TRANSACTION, TRANSACTION_TYPE, ORDER_NUMBER, TRANSACTION_OBJECT, TRANSACTION_DATE, CREATED_BY, MODIFIED_BY, EXCEPTION_ID, EXCEPTION_BY_TRAX, EXCEPTION_DETAIL, EXCEPTION_CLASS_TRAX, CREATED_DATE, MODIFIED_DATE) "
                            + "SELECT seq_interface_audit.NEXTVAL, 'ERROR', ?, 'I07', sysdate, 'TRAX_IFACE', 'TRAX_IFACE', '51', 'Y', 'WO does not have Contract added', 'Creation_Sales I_07', sysdate, sysdate FROM dual";
                    
                   
                    try (PreparedStatement psError = con.prepareStatement(sqlInsertError)) {
                        psError.setString(1, wo);
                        psError.executeUpdate();
                    }

                    String sqlUpdateWO = "UPDATE WO SET STATUS = 'CONF SLOT', INTERFACE_ESD_TRANSFERRED_FLAG = 'D', INTERFACE_ESD_TRANSFERRED_DATE = NULL WHERE WO = ?";
                    
                  
                    try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdateWO)) {
                        psUpdate.setString(1, wo);
                        psUpdate.executeUpdate();
                    }

                    logger.info("Fallback completed for WO: " + wo);
                }
            }
        } catch (Exception e) {
            logger.severe("Error during fallback task: " + e.getMessage());
            Creation_Sales_Controller.addError(e.getMessage());
        }
    }
	
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
		
		Creation_Sales_Data data = new Creation_Sales_Data();
		
		String executed = "OK";
		
		try {
			executed = data.setOpsLine(opsLine, email);
		} catch(Exception e) {
			executed = e.toString();
			Creation_Sales_Controller.addError(e.toString());
			Creation_Sales_Controller.sendEmailService(executed);
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
		Creation_Sales_Data data = new Creation_Sales_Data();
		
		String executed = "OK";
		
		try {
			data.deleteOpsLine(opsline);
		} catch(Exception e) {
			executed = e.toString();
			logger.severe(e.toString());
			Creation_Sales_Controller.addError(e.toString());
			Creation_Sales_Controller.sendEmailService(executed);
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
		Creation_Sales_Data data = new Creation_Sales_Data();
		String executed = "OK";
		String group = null;
		
		try {
			group = data.getemailByOpsLine(opsline);
			if(group == null) {
				executed = "Issue found";
				throw new Exception("Issue found");
						
			}
		} catch(Exception e) {
			Creation_Sales_Controller.addError(e.toString());
			Creation_Sales_Controller.sendEmailService(executed);
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
	public Response markTransaction(INT7_TRAX input) {
String executed = "OK";
		
		Creation_Sales_Data data = new Creation_Sales_Data("mark");
		try {
			JAXBContext jc = JAXBContext.newInstance(INT7_TRAX.class);
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
			Creation_Sales_Controller.addError(e.toString());
				OpsLineEmail opsLineEmail = data.getOpsLineStaffName(input.getWO());
                
				Creation_Sales_Controller.sendEmailOpsLine(null, input, opsLineEmail);
			
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
