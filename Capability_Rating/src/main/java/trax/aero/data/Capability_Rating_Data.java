package trax.aero.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.ZoneId;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import trax.aero.controller.Capability_Rating_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.interfaces.ICapability_Rating_Data;
import trax.aero.logger.LogManager;
import trax.aero.model.PartAuthorityESD;
import trax.aero.model.PartAuthorityESD_PK;
import trax.aero.model.PnMaster;
import trax.aero.pojo.DATA;
import trax.aero.pojo.DATAMasterResponse;
import trax.aero.pojo.DATARequest;
import trax.aero.pojo.DATAResponse;
import trax.aero.utils.DataSourceClient;

@Stateless(name="Capability_Rating_Data" , mappedName="Capability_Rating_Data")
public class Capability_Rating_Data  implements ICapability_Rating_Data{
	
	

	@PersistenceContext(unitName = "TraxESD") private EntityManager em;
	
	
	public Capability_Rating_Data() {
		
	}
	
	public DATAMasterResponse insertAuth(DATA input) {
		DATAMasterResponse output = new DATAMasterResponse();
		if (input.getData() == null ) {
            DATAResponse dataResponse = insertAuthPN(input.getData());
            System.out.println("Hello: " + dataResponse);
            output.setData(dataResponse);
		}
		return output;
	}
	
	private DATAResponse insertAuthPN(DATARequest element) {
		DATAResponse item = new DATAResponse();
		
		if(!checkValueString(element.getPartNo()) || !checkValueString(element.getAuthorityType())){
			System.out.println("Authority: " + element.getAuthorityType() +" or PN: " + element.getPartNo() + " is null or empty");
			
			return item;
		}
		
		PartAuthorityESD_PK primaryKey = new PartAuthorityESD_PK();
		primaryKey.setPn(element.getPartNo());
		primaryKey.setAuthority(element.getAuthorityType());
		
		try {
			
			
			PnMaster pnmaster = em.find(PnMaster.class,primaryKey.getPn());
			
			if(pnmaster == null) {
				System.out.println("Authority: " + element.getAuthorityType() +" or PN: " + element.getPartNo() + " does not exist in the Trax DataBase");
				return item;
			}
			if(pnmaster.getStatus() !=null && !pnmaster.getStatus().isEmpty() && pnmaster.getStatus().equalsIgnoreCase("INACTIVE")) {
				throw new Exception("PN is not a active");
			}	
			
			PartAuthorityESD auth = em.find(PartAuthorityESD.class, primaryKey);
			
			if(auth == null) {
				auth = new PartAuthorityESD();
				
				auth.setId(primaryKey);
				auth.setModifiedBy("TRAX_IFACE");
				auth.setModifiedDate(new Date());
				auth.setCreatedBy("TRAX_IFACE");
				auth.setCreatedDate(new Date());
			}
			else {
				auth.setModifiedBy("TRAX_IFACE");
				auth.setModifiedDate(new Date());
			}
			
			
			
			
			if(element.getQltyStatus().equalsIgnoreCase("Current")) {
				if(element.getCatCategory() != null)
					auth.setTechControl((element.getCatCategory()));
				if(element.getClcfNo() != null)
					auth.setPnType((element.getClcfNo()));
				if(element.getCompCapability() != null)
					auth.setCompCapability((element.getCompCapability()));
				if(element.getWorkshop() != null)
					auth.setWorkshop((element.getWorkshop()));
				if(element.getQltyStatus() != null)
					auth.setQltyStatus((element.getQltyStatus()));
				if (element.getRevNo() != null) {
				    try {
				        Integer revNumber = Integer.parseInt(element.getRevNo());
				        auth.setRevNumber(revNumber);
				    } catch (NumberFormatException e) {
				        e.printStackTrace(); 
				    }
				}
				if(element.getDate() != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				try {
			        LocalDate localDate = LocalDate.parse(element.getDate(), formatter);
			        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			        auth.setAuthorityDate(date);
			    } catch (DateTimeParseException e) {
			        e.printStackTrace(); 
			    }
				}
				
			}
			
			System.out.println("INSERTING Authority: "+element.getAuthorityType()+" and Pn: "+element.getPartNo()+" into the Trax DataBase");
			insertData(auth);
			return item;
			
		} catch(Exception e) {
			return item;
		}
	}
	
	private boolean checkValueString(String data) {
		return (data != null && !data.isEmpty() ? true:false);
	}
	
	private <T> void insertData( T data) 
	{
			
		em.merge(data);
		em.flush();
		
	}

	@Override
	public DATAMasterResponse importAuth(DATA input) {
		// TODO Auto-generated method stub
		return null;
	}
}
