package trax.aero.application;




import java.io.StringWriter;
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

import trax.aero.controller.MaterialStatusImportController;
import trax.aero.interfaces.IMaterialStatusImportData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.MaterialStatusImportMaster;




@Path("/MaterialStatusImportService")
public class Service {
	
	Logger logger = LogManager.getLogger("MaterialStatusImport_I11&I12");
	
	@EJB IMaterialStatusImportData data;
	
	
	
	@POST
	@Path("/moveMaterial")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8" )
	public Response moveMaterial(MaterialStatusImportMaster input)
	{
		String exceuted = "OK";
		try 
        {    
			 
			JAXBContext jc = JAXBContext.newInstance(MaterialStatusImportMaster.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(input, sw);
			logger.info("Input: " + sw.toString());
			exceuted = data.updateMaterial(input);
						
        	if(!exceuted.equalsIgnoreCase("OK")) {
        		exceuted = "Issue found";
        		throw new Exception("Issue found");
        	}
		}
		catch(Exception e)
		{
			logger.severe(e.toString());
			MaterialStatusImportController.addError(e.toString());
					  		
			MaterialStatusImportController.sendEmail(input);
		}
       finally 
       {   
    	   logger.info("finishing");
       }
	   return Response.ok(exceuted,MediaType.APPLICATION_JSON).build();
	}
		
	
	@GET
    @Path("/healthCheck")
    @Produces(MediaType.APPLICATION_XML + ";charset=UTF-8" )
    public Response healthCheck() 
    {    	
		logger.info("Healthy");
    	return Response.ok("Healthy",MediaType.APPLICATION_XML).build();
    }
	
		
	
	
	
	
}