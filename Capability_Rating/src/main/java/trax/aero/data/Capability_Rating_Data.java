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
import javax.persistence.Query;

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
	
	

	@PersistenceContext(unitName = "TraxStandaloneDS") private EntityManager em;
	
	
	public Capability_Rating_Data() {
		
	}
	
	public String translateCategory(String catCategory) {
	    switch (catCategory) {
	        case "B1":
	            return "Engine";
	        case "B3":
	            return "APU";
	        case "C7":
	            return "Module";
	        default:
	            return null; 
	    }
	}
	 
	public DATAMasterResponse importAuth(DATA input) {
		DATAMasterResponse output = new DATAMasterResponse();
		if (input.getData() != null ) {
            DATAResponse dataResponse = importAuthPN(input.getData());
            output.setData(dataResponse);
		}
		return output;
	}
	
	private DATAResponse importAuthPN(DATARequest element) {
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
			
			if (element.getRevNo() != null) {
                try {
                    Integer newRevNumber = Integer.parseInt(element.getRevNo());
                    if (auth.getRevNumber() != null && newRevNumber <= auth.getRevNumber()) {
                      
                        System.out.println("Received RevNo " + newRevNumber + " is less than or equal to existing RevNo " + auth.getRevNumber() + ". No update performed.");
                        return item;
                    } else {
                        auth.setRevNumber(newRevNumber);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        
			
			System.out.println(element.getCatCategory() + " || " + element.getClcfNo() + " || " + element.getWorkshop() + " || " + element.getDate() + " || " + element.getQltyStatus() + " || " + element.getCompCapability());
	        
	        if (element.getQltyStatus().equalsIgnoreCase("Current") || element.getQltyStatus().equalsIgnoreCase("Update")) {
	            if (element.getCatCategory() != null) {
	                String translatedCategory = translateCategory(element.getCatCategory());
	                if (translatedCategory != null) {
	                    auth.setTechControl(translatedCategory);
	                }
	            }
	            if (element.getClcfNo() != null)
	                auth.setPnType(element.getClcfNo());
	            if (element.getCompCapability() != null)
	                auth.setCompCapability(element.getCompCapability());
	            if (element.getWorkshop() != null)
	                auth.setWorkshop(element.getWorkshop());
	            if (element.getQltyStatus() != null)
	                auth.setQltyStatus(element.getQltyStatus());
	            if (element.getRevNo() != null) {
	                try {
	                    Integer revNumber = Integer.parseInt(element.getRevNo());
	                    auth.setRevNumber(revNumber);
	                } catch (NumberFormatException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (element.getDate() != null) {
	            	auth.setAuthorityDate(element.getDate());
	            }else {
	            	auth.setAuthorityDate("");
	            }
	        }
	        
	      //PNTYPE TRANSCODE
	        
	        System.out.println("CHECKING PN_TYPE: "+element.getClcfNo() + " into the Trax DataBase");
	        
	        try {
	        	String checkPnTypeStr = "SELECT COUNT(*) FROM SYSTEM_TRAN_CODE WHERE SYSTEM_TRANSACTION = 'PNTYPE' AND SYSTEM_CODE = ? ";
	            Query checkPnType = em.createNativeQuery(checkPnTypeStr);
	            checkPnType.setParameter(1, auth.getPnType());
	            long count = ((Number) checkPnType.getSingleResult()).longValue();
	            System.out.println("Count of records found: " + count);
	            
	            if (count == 0) {
	            	  System.out.println("Record does not exist. Inserting into SYSTEM_TRAN_CODE");
		                String insertPnTypeQuery = "INSERT INTO SYSTEM_TRAN_CODE (SYSTEM_TRANSACTION, SYSTEM_CODE, SYSTEM_CODE_DESCRIPTION, PN_TRANSACTION, PN_COSTING_METHOD, CREATED_BY, CREATED_DATE, SYSTEM_TRAN_CODE_SUB) " +
		                                     "VALUES ('PNTYPE', ?, ?, 'C', 'A', 'TRAX_IFACE', SYSDATE, 'I25' )";
		                Query insertPnTypeQueryObj = em.createNativeQuery(insertPnTypeQuery);
		                insertPnTypeQueryObj.setParameter(1, auth.getPnType());
		                insertPnTypeQueryObj.setParameter(2, auth.getPnType());
		                insertPnTypeQueryObj.executeUpdate();
		                System.out.println("Successfully inserted into SYSTEM_TRAN_CODE");
	            } else {
	            	System.out.println("Record already exists. No insertion needed.");
	            }
	              
	        }catch (Exception e) {
	            System.out.println("Error occurred while checking or inserting/updating SYSTEM_TRAN_CODE");
	            e.printStackTrace();
	        }
	        
	        
	        //AUTHORITY TRANSCODE
	        
	        System.out.println("CHECKING AUTHORITY TRANSCODE: "+element.getClcfNo() + " into the Trax DataBase");
	        
	        try {
	        	String checkAuthStr = "SELECT COUNT(*) FROM SYSTEM_TRAN_CODE WHERE SYSTEM_TRANSACTION = 'AUTHAPPROVAL' AND SYSTEM_CODE = ? ";
	            Query checkAuth = em.createNativeQuery(checkAuthStr);
	            checkAuth.setParameter(1, auth.getId().getAuthority());
	            long count = ((Number) checkAuth.getSingleResult()).longValue();
	            System.out.println("Count of records found: " + count);
	            
	            if (count == 0) {
	            	  System.out.println("Record does not exist. Inserting into SYSTEM_TRAN_CODE");
		                String insertPnTypeQuery = "INSERT INTO SYSTEM_TRAN_CODE (SYSTEM_TRANSACTION, SYSTEM_CODE, SYSTEM_CODE_DESCRIPTION, PN_TRANSACTION, PN_COSTING_METHOD, CREATED_BY, CREATED_DATE, SYSTEM_TRAN_CODE_SUB) " +
		                                     "VALUES ('AUTHAPPROVAL', ?, ?, 'C', 'A', 'TRAX_IFACE', SYSDATE, 'I25')";
		                Query insertPnTypeQueryObj = em.createNativeQuery(insertPnTypeQuery);
		                insertPnTypeQueryObj.setParameter(1, auth.getId().getAuthority());
		                insertPnTypeQueryObj.setParameter(2, auth.getId().getAuthority());
		                insertPnTypeQueryObj.executeUpdate();
		                System.out.println("Successfully inserted into SYSTEM_TRAN_CODE");
	            } else {
	            	System.out.println("Record already exists. No insertion needed.");
	            }
	              
	        }catch (Exception e) {
	            System.out.println("Error occurred while checking or inserting/updating SYSTEM_TRAN_CODE");
	            e.printStackTrace();
	        }
	        
	        
	        // INSERTING PN_MASTER
	        System.out.println("Updating PN_MASTER "+element.getPartNo()+" into the Trax DataBase");
	        
	        String updatePnMaster = "UPDATE PN_MASTER SET ENGINE = ?, PN_TYPE = ? WHERE PN = ? ";
	        Query updatePN = em.createNativeQuery(updatePnMaster);
	        updatePN.setParameter(1, auth.getTechControl());
	        updatePN.setParameter(2, auth.getPnType());
	        updatePN.setParameter(3, auth.getId().getPn());
	        updatePN.executeUpdate();
            System.out.println("Successfully inserted into PN_MASTER");
	        
		
			System.out.println("INSERTING Authority: "+element.getAuthorityType()+" and Pn: "+element.getPartNo()+" into the Trax DataBase");
			insertData(auth);
			
			boolean isActive = (!auth.getAuthorityDate().isEmpty() && !element.getQltyStatus().equalsIgnoreCase("Terminated"));
	        String activeStatus = isActive ? "Y" : "N";
	        System.out.println("Preparing to check and insert/update PN_AUTHORITY_APPROVAL with ACTIVE: " + activeStatus);
	        
	        try {
	            // Check if the record exists in PN_AUTHORITY_APPROVAL using native SQL query
	            String checkQueryStr = "SELECT COUNT(*) FROM PN_AUTHORITY_APPROVAL WHERE PN = ? AND AUTHORITY = ?";
	            Query checkQuery = em.createNativeQuery(checkQueryStr);
	            checkQuery.setParameter(1, auth.getId().getPn());
	            checkQuery.setParameter(2, auth.getId().getAuthority());
	            long count = ((Number) checkQuery.getSingleResult()).longValue();
	            System.out.println("Count of records found: " + count);

	            if (count > 0) {
	                // Update the existing record
	                System.out.println("Record exists. Updating ACTIVE field in PN_AUTHORITY_APPROVAL");
	                String updateQueryStr = "UPDATE PN_AUTHORITY_APPROVAL SET AUTH_STATUS = ? WHERE PN = ? AND AUTHORITY = ?";
	                Query updateQuery = em.createNativeQuery(updateQueryStr);
	                updateQuery.setParameter(1, activeStatus);
	                updateQuery.setParameter(2, auth.getId().getPn());
	                updateQuery.setParameter(3, auth.getId().getAuthority());
	                updateQuery.executeUpdate();
	                System.out.println("Successfully updated PN_AUTHORITY_APPROVAL");
	                
	                if(activeStatus.equalsIgnoreCase("N")) {
	                	String deleteAuth = "DELETE FROM PN_AUTHORITY_APPROVAL WHERE PN = ? AND AUTHORITY = ? ";
	                	 Query deleteAuthObj = em.createNativeQuery(deleteAuth);
	                	 deleteAuthObj.setParameter(1, auth.getId().getPn());
	                	 deleteAuthObj.setParameter(2, auth.getId().getAuthority());
	                	 deleteAuthObj.executeUpdate();
	                	 System.out.println("Successfully inactive record into PN_AUTHORITY_APPROVAL");
	                }
	            } else {
	                // Insert the new record
	                System.out.println("Record does not exist. Inserting into PN_AUTHORITY_APPROVAL");
	                String insertQuery = "INSERT INTO PN_AUTHORITY_APPROVAL (PN, AUTHORITY, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, WO, PRINTED, REASON, PN_SN, AUTH_STATUS, EXTERNAL_SELECTED ) " +
	                                     "VALUES (?, ?, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?, NULL)";
	                Query insertQueryObj = em.createNativeQuery(insertQuery);
	                insertQueryObj.setParameter(1, auth.getId().getPn());
	                insertQueryObj.setParameter(2, auth.getId().getAuthority());
	                insertQueryObj.setParameter(3, "TRAX_IFACE");
	                insertQueryObj.setParameter(4, new Date());
	                insertQueryObj.setParameter(5, "TRAX_IFACE");
	                insertQueryObj.setParameter(6, new Date());
	                insertQueryObj.setParameter(7, activeStatus);
	                insertQueryObj.executeUpdate();
	                System.out.println("Successfully inserted into PN_AUTHORITY_APPROVAL");
	                
	                if(activeStatus.equalsIgnoreCase("N")) {
	                	String deleteAuth = "DELETE FROM PN_AUTHORITY_APPROVAL WHERE PN = ? AND AUTHORITY = ? ";
	                	 Query deleteAuthObj = em.createNativeQuery(deleteAuth);
	                	 deleteAuthObj.setParameter(1, auth.getId().getPn());
	                	 deleteAuthObj.setParameter(2, auth.getId().getAuthority());
	                	 deleteAuthObj.executeUpdate();
	                	 System.out.println("Successfully inactive record into PN_AUTHORITY_APPROVAL");
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Error occurred while checking or inserting/updating PN_AUTHORITY_APPROVAL");
	            e.printStackTrace();
	        }
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

}
