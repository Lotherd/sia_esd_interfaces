package trax.aero.services;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import trax.aero.data.MaterialData;
import trax.aero.interfaces.IMaterialData;

import trax.aero.logger.LogManager;
import trax.aero.outbound.MT_TRAX_I10_TRAX;
import trax.aero.util.EmailSender;




@Path("/MatServices")
public class MatServices {

	Logger logger = LogManager.getLogger("MaterialDemand_I10");

	@EJB IMaterialData md;
	
	@POST
	@Path("/recieveResponse")
	@Consumes(MediaType.APPLICATION_XML + ";charset=UTF-8" )
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8" )
	public Response recieveResponse(MT_TRAX_I10_TRAX data)
	{
		try
		{
			JAXBContext jc = JAXBContext.newInstance(MT_TRAX_I10_TRAX.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(data, sw);
			
			logger.info("Input: " + sw.toString());
			
			md.acceptReq(data);
			
		}
		catch(Exception e)
		{
			logger.severe(e.toString());
			e.printStackTrace();
		}
		logger.info("finishing");
		   
		
		return Response.ok("Ok",MediaType.APPLICATION_XML + ";charset=UTF-8").build();
		
	}
	
	
	
	@GET
    @Path("/healthCheck")
    @Produces(MediaType.APPLICATION_XML )
    public Response healthCheck() 
    {    	
		logger.info("Healthy");
    	return Response.ok("Healthy",MediaType.APPLICATION_XML).build();
    }


}