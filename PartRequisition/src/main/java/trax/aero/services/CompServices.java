package trax.aero.services;

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
import trax.aero.interfaces.IPartRequisitionData;

import trax.aero.logger.LogManager;
import trax.aero.outbound.MT_TRAX_RCV_I21_4121_RES;

@Path("/Services")
public class CompServices {

	Logger logger = LogManager.getLogger("PartRequisition_I41");

	@EJB IPartRequisitionData pr;
	
	@POST
	@Path("/recieveResponse")
	@Consumes(MediaType.APPLICATION_XML + ";charset=UTF-8" )
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8" )
	public Response recieveResponse(MT_TRAX_RCV_I21_4121_RES data)
	{
		try
		{
			JAXBContext jc = JAXBContext.newInstance(MT_TRAX_RCV_I21_4121_RES.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(data, sw);
			
			logger.info("Input: " + sw.toString());
			
			pr.acceptReq(data);
			
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