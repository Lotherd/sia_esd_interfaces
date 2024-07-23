package trax.aero.application;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import trax.aero.controller.Capability_Rating_Controller;
import trax.aero.interfaces.ICapability_Rating_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.DATA;
import trax.aero.pojo.DATAMasterResponse;

@Path("/CapabilityRating")
public class Service {
	
	
	@EJB ICapability_Rating_Data data;
	
	@POST
	@Path("/insertAuth")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertAuthPN(String inputString) {
		
		DATAMasterResponse output = null;
		DATA input = null;
		String json = "";
		
		
		System.out.println("Input: " + inputString);
		
		try 
        {   
				 
			
			
			String id = inputString.substring(inputString.indexOf("\"")+1);
			id = id.substring(0,id.indexOf("\""));
			System.out.println("Id: " + id);
			
			inputString = inputString.replaceAll(id,"data" );
			System.out.println(inputString);
			
			ObjectMapper mapper = new ObjectMapper();
			
			input = mapper.readValue(inputString, DATA.class);
			System.out.println(input.getData().getPartNo());
			/*
			output.setTransactionId( new ArrayList<ItemResponse>());
			for(ItemRequest i  :input.getTransactionId()) {
				ItemResponse item = new ItemResponse();
				item.setCode(new Long(200));
				item.setMessage("accepted");
				item.setRowId(i.getRowId());
				output.getTransactionId().add(item);
			}
			*/
			output = data.importAuth(input);
			
			
			json = mapper.writeValueAsString(output);
			json = json.replaceAll("data", id);	
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
       finally 
       {   
    	
    	   System.out.println("finishing");
       }
		
		 return Response.ok(json,MediaType.APPLICATION_JSON ).build();
	}
	
	
	@GET
    @Path("/healthCheck")
    @Produces(MediaType.APPLICATION_JSON )
    public Response healthCheck() 
    {    	
		System.out.println("Healthy");
    	return Response.ok("Healthy",MediaType.APPLICATION_JSON).build();
    }
	

}
