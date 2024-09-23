package trax.aero.data;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.LockModeType;


import trax.aero.controller.Authorization_Details_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.BlobTable;
import trax.aero.model.EmployeeAuthPreReq;
import trax.aero.model.EmployeeAuthPreReqPK;
import trax.aero.model.EmployeeAuthorization;
import trax.aero.model.EmployeeAuthorizationApv;
import trax.aero.model.EmployeeAuthorizationApvPK;
import trax.aero.model.EmployeeControl;
import trax.aero.model.EmployeeControlPK;
import trax.aero.model.EmployeeSkill;
import trax.aero.model.EmployeeSkillPK;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.model.RelationMaster;
import trax.aero.model.SystemTranCode;
import trax.aero.model.SystemTranCodePK;
import trax.aero.pojo.EmployeeLicense;
import trax.aero.utils.DataSourceClient;

public class Authorization_Controller_Data {

    private Connection con;
    private EntityManagerFactory factory;
    private EntityManager em;
    private String executed = "OK";
    
    private static final ArrayList<String> Inactive = new ArrayList<>(Arrays.asList(
        "Withdrawn", "Suspension", "Secondment", "Resignation", "Terminated", 
        "End of Contract Term", "Retirement"
    ));
    
    static Logger logger = LogManager.getLogger("AuthDetails");
    
    public Authorization_Controller_Data() {
        factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
        em = factory.createEntityManager();
        
        try {
            if (this.con == null || this.con.isClosed()) {
                this.con = DataSourceClient.getConnection();
                logger.info("The connection was established successfully with status: " + String.valueOf(!this.con.isClosed()));
            }        
        } catch (SQLException | CustomizeHandledException e) {
            Authorization_Details_Controller.addError(e.toString());
            logger.severe(e.toString());
        } catch (Exception e) {
            Authorization_Details_Controller.addError(e.toString());
            logger.severe(e.toString());
        }
    }
    
    public Connection getCon() {
        return con;
    }
    
    public String insertEmployeeLicense(EmployeeLicense e) {
        executed = "OK";
        
        if (e != null && checkMinValue(e)) {
            if (getEmployee(e.getStaffNumber()) == null) {
                return executed;
            }
            setEmployeeControl(e);
            setEmployeeSkillLicense(e);
        } else {
            executed = "Cannot insert/update Employee: " + e.getStaffNumber() + " due to missing or invalid minimum values.";
            logger.severe(executed);
            Authorization_Details_Controller.addError(executed);
        }
        
        return executed;
    }
    
    private void setEmployeeSkillLicense(EmployeeLicense e) {
        /*// Define skill mappings
        Map<String, String> skillMapping = new HashMap<>();
        skillMapping.put("4.3.1.13 Fan Blade Leading Edge Profiling", "FBLEP");
        skillMapping.put("4.3.1.16 Engine Borescope Inspection", "BSI");
        skillMapping.put("4.3.1.17 Engine Borescope Blending", "BB");
        skillMapping.put("APU", "APU");
        skillMapping.put("Avionics", "QTA");
        skillMapping.put("Engine Borescope Blade Blending", "BB");
        skillMapping.put("Engine Borescope Inspection", "BSI");
        skillMapping.put("Engine build up for avionics harness/components", "EBA");
        skillMapping.put("Engine build up for avionics harness/components and stagger check limited to ATA Chapters listed in APS 4.1 Appendix", "SCA");
        skillMapping.put("Engine build up including component, accessories", "EBM");
        skillMapping.put("Engine build up including component, accessories and stagger check limited to ATA Chapters listed in APS 4.1 Appendix", "SCM");
        skillMapping.put("Engine Test Authorisation", "TES");
        skillMapping.put("Fan Blade Leading Edge Profiling", "FBLEP");
        skillMapping.put("Mechanical", "QTM");
        skillMapping.put("Rigging/Duplicate Inspection", "DI");*/

    	// Use a Set to keep track of inserted skills to avoid duplicates
        Set<String> processedSkills = new HashSet<>();

        // Get the assigned skill from the document and trim any leading/trailing spaces
        String assignedSkill = e.getRecordItemParent().trim();
        logger.info("Assigned Skill from document: '" + assignedSkill + "' with length: " + assignedSkill.length());

        // Query the ESD_SKILL_MAPPING table to get the corresponding skill code (case-insensitive, trimmed, and space-handled)
        String skillCode = null;
        try {
            // Log the incoming assigned skill for debugging
            logger.info("Assigned Skill Name (before processing): " + assignedSkill);

            // Modified query to match what worked in SQL test (handling spaces and case-insensitive)
            Query query = em.createNativeQuery(
                "SELECT SKILL FROM ESD_SKILL_MAPPING WHERE REPLACE(LOWER(TRIM(skill_name)), ' ', '') = REPLACE(LOWER(TRIM(:skillName)), ' ', '')");

            query.setParameter("skillName", assignedSkill);

            @SuppressWarnings("unchecked")
            List<String> result = query.getResultList();  // Casting result to List<String>

            // Log the result for debugging purposes
            logger.info("RESULT Skill Code: " + result);  // This will print the result list to logs

            if (!result.isEmpty()) {
                skillCode = result.get(0);  // Get the first result since we're expecting a single match
                logger.info("Mapped Skill Code: " + skillCode);
            } else {
                logger.warning("No skill found for skill name: '" + assignedSkill + "'");
            }
        } catch (Exception ex) {
            logger.severe("Exception occurred while fetching skill code for skill name: '" + assignedSkill + "'");
            ex.printStackTrace();  // Print the full exception stack trace
        }

        // Only process the skill if it's found in the mapping and hasn't been processed before
        if (skillCode != null && !processedSkills.contains(skillCode)) {
            String techControl = null;
            try {
                // Execute your new SQL query to retrieve tech_control
                Query techControlQuery = em.createNativeQuery(
                    "SELECT DISTINCT pe.tech_control " +
                    "FROM pn_master pm, pn_authority_approval pa, pn_authority_esd pe " +
                    "WHERE pm.pn = pa.pn AND pm.pn_type = :pnType " +
                    "AND pe.pn_type = pm.pn_type AND pe.tech_control <> 'MODULE'");
                techControlQuery.setParameter("pnType", e.getRecordItemName().toUpperCase());

                @SuppressWarnings("unchecked")
                List<String> techControlResult = techControlQuery.getResultList();

                if (!techControlResult.isEmpty()) {
                    techControl = techControlResult.get(0);  // Assuming you only need the first result
                    logger.info("Tech Control retrieved: " + techControl);
                } else {
                    logger.warning("No tech_control found for pn_type: '" + e.getRecordItemName().toUpperCase() + "'");
                }
            } catch (Exception ex) {
                logger.severe("Exception occurred while fetching tech_control for pn_type: '" + e.getRecordItemName().toUpperCase() + "'");
                ex.printStackTrace();
            }

            // Here we handle multiple `e.getRecordItemName().toUpperCase()` for the same skill
            // Assuming e.getRecordItemName().toUpperCase() returns names separated by commas or some delimiter, we can split them
            String[] recordItemNames = e.getRecordItemName().toUpperCase().split(",");  // Splitting by comma or other delimiter
            for (String recordItemName : recordItemNames) {
                recordItemName = recordItemName.trim();  // Make sure to trim any extra spaces

                EmployeeSkill employeeSkill = null;
                try {
                    // Check if the skill already exists for the employee with the given record item name
                    employeeSkill = em.createQuery("SELECT e FROM EmployeeSkill e WHERE e.id.employee = :em AND e.id.skill = :sk AND e.id.acType = :acType", EmployeeSkill.class)
                                      .setParameter("em", e.getStaffNumber())
                                      .setParameter("sk", skillCode)
                                      .setParameter("acType", recordItemName)
                                      .getSingleResult();
                    
                    // Skill exists, so update it
                    employeeSkill.setModifiedBy("TRAX_IFACE");
                    employeeSkill.setModifiedDate(new Date());
                    employeeSkill.setLicense(e.getAuthorizationNumber());

                    try {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        employeeSkill.setExpirationDate(format.parse(e.getAuthorizationExpiryDate()));
                        employeeSkill.setExpirationOptional("YES");
                    } catch (ParseException e1) {
                        logger.severe("Error parsing expiration date for employee: " + e.getStaffNumber());
                    }
                    
                    logger.info("UPDATING SKILL: " + assignedSkill + " for Employee: " + e.getStaffNumber());
                } catch (NoResultException ex) {
                    // Skill does not exist, so create and insert it
                    employeeSkill = new EmployeeSkill();
                    EmployeeSkillPK pk = new EmployeeSkillPK();

                    pk.setEmployee(e.getStaffNumber());
                    pk.setSkill(skillCode);
                    pk.setAcType(recordItemName != null ? recordItemName : "N/A"); // Use a placeholder value instead of an empty string
                    pk.setAcSeries(techControl != null ? techControl : "N/A"); // Use techControl if found, otherwise "N/A"

                    employeeSkill.setId(pk);
                    employeeSkill.setStatus("ACTIVE");
                    employeeSkill.setCreatedBy("TRAX_IFACE");
                    employeeSkill.setCreatedDate(new Date());
                    employeeSkill.setModifiedBy("TRAX_IFACE");
                    employeeSkill.setModifiedDate(new Date());
                    employeeSkill.setLicense(e.getAuthorizationNumber());
                    employeeSkill.setPntype(recordItemName != null ? recordItemName : "N/A");
                    employeeSkill.setEngine(techControl != null ? techControl : "N/A");

                    try {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        employeeSkill.setExpirationDate(format.parse(e.getAuthorizationExpiryDate()));
                        employeeSkill.setExpirationOptional("YES");
                    } catch (ParseException e1) {
                        logger.severe("Error parsing expiration date for employee: " + e.getStaffNumber());
                    }

                    logger.info("INSERTING NEW SKILL: " + assignedSkill + " for Employee: " + e.getStaffNumber());
                }

                try {
                    // Insert or update the skill in the database
                    logger.info("Attempting to insert/update skill: " + skillCode + " for Employee: " + e.getStaffNumber());
                    insertData(employeeSkill);
                    logger.info("Successfully processed skill: " + assignedSkill + " for Employee: " + e.getStaffNumber());
                    
                    // Add the processed skill to the set to avoid duplicating it
                    processedSkills.add(skillCode);
                } catch (Exception ex) {
                    logger.severe("Error processing skill: " + assignedSkill + " for Employee: " + e.getStaffNumber() + " - " + ex.getMessage());
                }
            }
        } else {
            logger.info("Skill '" + assignedSkill + "' not found in mapping or already processed.");
        }
    }
    
    public void insertSkillsToSkillMaster() {
        EntityTransaction transaction = null;
        try {
            // Start the transaction
            transaction = em.getTransaction();
            transaction.begin();

            // First, execute the query to get skill and skill_name from ESD_SKILL_MAPPING
            List<Object[]> skillMappings = em.createNativeQuery("SELECT SKILL, SKILL_NAME FROM ESD_SKILL_MAPPING")
                                             .getResultList();

            // Iterate through the results of the SELECT query
            for (Object[] skillMapping : skillMappings) {
                String skill = (String) skillMapping[0];  // First column is the skill
                String skillName = (String) skillMapping[1];  // Second column is the skill_name

                // Check if the skill already exists in the skill_master table
                Long count = ((Number) em.createNativeQuery("SELECT COUNT(*) FROM skill_master WHERE skill = :skill")
                                         .setParameter("skill", skill)
                                         .getSingleResult()).longValue();

                if (count == 0) {
                    // The skill does not exist, proceed with the insert
                    Query insertQuery = em.createNativeQuery(
                        "INSERT INTO skill_master " +
                        "(SKILL, SKILL_DESCRIPTION, CREATED_BY, MODIFIED_BY, CREATED_DATE, MODIFIED_DATE, STATUS, MECHANIC, INSPECTOR, ETOPS, DEFECT) " +
                        "VALUES (:skill, :skillName, 'TRAX_IFACE', 'TRAX_IFACE', SYSDATE, SYSDATE, 'ACTIVE', 'Y', 'Y', 'N', 'N')"
                    );
                    insertQuery.setParameter("skill", skill);
                    insertQuery.setParameter("skillName", skillName);
                    insertQuery.executeUpdate();
                    logger.info("Inserted skill: " + skill + " with description: " + skillName);
                } else {
                    // The skill already exists, skip the insert
                    logger.info("Skill " + skill + " already exists in skill_master. Skipping insert.");
                }
            }

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If an error occurs, rollback the transaction
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.severe("Error inserting skills into skill_master: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
    public synchronized void deleteEmployeeControlRecords(String employeeId) {
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();  
            }

            Long count = em.createQuery("SELECT COUNT(e) FROM EmployeeControl e WHERE e.id.employee = :employee", Long.class)
                           .setParameter("employee", employeeId)
                           .getSingleResult();

            if (count > 0) {
                int deletedCount = em.createQuery("DELETE FROM EmployeeControl e WHERE e.id.employee = :employee")
                                     .setParameter("employee", employeeId)
                                     .executeUpdate();

                em.flush();  
                em.getTransaction().commit();
                logger.info("Deleted " + deletedCount + " EmployeeControl records for Employee: " + employeeId);
            } else {
                logger.info("No EmployeeControl records found for Employee: " + employeeId + ". Skipping deletion.");
                em.getTransaction().commit();
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.severe("Error while deleting EmployeeControl records for Employee: " + employeeId + " - " + e.getMessage());
        }finally {
        	em.clear();
        }
    } 

    
    private void setEmployeeControl(EmployeeLicense e) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        boolean expire = false;

       /* // Skill mapping
        Map<String, String> skillMapping = new HashMap<>();
        skillMapping.put("4.3.1.13 Fan Blade Leading Edge Profiling", "FBLEP");
        skillMapping.put("4.3.1.6 Engine Borescope Inspection", "BSI");
        skillMapping.put("4.3.1.7 Engine Borescope Blending", "BB");
        skillMapping.put("APU", "APU");
        skillMapping.put("Avionics", "QTA");
        skillMapping.put("Engine Borescope Blade Blending", "BB");
        skillMapping.put("Engine Borescope Inspection", "BSI");
        skillMapping.put("Engine build up for avionics harness/components", "EBA");
        skillMapping.put("Engine build up for avionics harness/components and stagger check limited to ATA Chapters listed in APS 4.1 Appendix", "SCA");
        skillMapping.put("Engine build up including component, accessories", "EBM");
        skillMapping.put("Engine build up including component, accessories and stagger check limited to ATA Chapters listed in APS 4.1 Appendix", "SCM");
        skillMapping.put("Engine Test Authorisation", "TES");
        skillMapping.put("Fan Blade Leading Edge Profiling", "FBLEP");
        skillMapping.put("Mechanical", "QTM");
        skillMapping.put("Rigging/Duplicate Inspection", "DI");*/

        // List to store authorities issued
        List<String> issuedAuthorities = new ArrayList<>();
        String techControl = "";
        try {
            if (e.getRecordItemAuthority() == null || e.getRecordItemAuthority().isEmpty()) {
                e.setRecordItemAuthority("-");
            } 
            if (e.getRecordItemAuthority() != null && !e.getRecordItemAuthority().isEmpty()) {
                String authority = e.getRecordItemAuthority();
                logger.info("AUTHORITY :" + e.getRecordItemAuthority());
                
                if (authority.contains("EASA-66")) {
                    issuedAuthorities.add("EASA");
                    
                  
                    @SuppressWarnings("unchecked")
                    List<String> queriedTechControls = em.createNativeQuery(
                        "SELECT distinct tech_control " +
                        "FROM pn_authority_esd " +
                        "WHERE pn_type = :pnType and authority = 'EASA' and tech_control <> 'MODULE' ")
                        .setParameter("pnType", e.getRecordItemName().toUpperCase())
                        .getResultList();
                    
                    
                    if (!queriedTechControls.isEmpty()) {
                        techControl = queriedTechControls.get(0); 
                    }
                } else if (authority.equals("-")) {
                    
                    @SuppressWarnings("unchecked")
                    List<Object[]> queriedResults = em.createNativeQuery(
                        "SELECT distinct pa.authority, pe.tech_control " +
                        "FROM pn_master pm, pn_authority_approval pa, pn_authority_esd pe " +
                        "WHERE pm.pn = pa.pn AND pm.pn_type = :pnType " +
                        "AND pa.authority <> 'EASA' AND pe.pn_type = pm.pn_type AND pe.tech_control <> 'MODULE' ")
                        .setParameter("pnType", e.getRecordItemName().toUpperCase())
                        .getResultList();

                    
                    for (Object[] result : queriedResults) {
                        String queriedAuthority = (String) result[0]; 
                        String queriedTechControl = (String) result[1]; 

                        issuedAuthorities.add(queriedAuthority);

                        
                        if (techControl.isEmpty()) {
                            techControl = queriedTechControl; 
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.severe("Error while filtering authorities: " + ex.getMessage());
        }

        for (String issuedAuthority : issuedAuthorities) {
            EmployeeControl license = null;

            try {
               


                try {
                    logger.info("Looking for existing EmployeeControl record for authority: " + issuedAuthority);
                    license = em.createQuery("SELECT e FROM EmployeeControl e WHERE e.id.employee = :em AND e.id.employeeControl = :tr AND issuedAuthority = :auth ", EmployeeControl.class)
                            .setParameter("em", e.getStaffNumber())
                            .setParameter("tr", "LICENCE")
                            .setParameter("auth", issuedAuthority)    
                            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                            .getSingleResult();
                } catch (Exception ex) {
                    logger.info("No existing record found, creating a new one.");
                    license = new EmployeeControl();
                    EmployeeControlPK employeepk = new EmployeeControlPK();
                    license.setId(employeepk);
                    license.setCreatedDate(new Date());
                    license.setCreatedBy("TRAX_IFACE");
                    license.getId().setEmployee(e.getStaffNumber());
                    license.getId().setEmployeeControl("LICENCE");
                    license.setDateIssued(new Date());
                    license.setExpirationOptional("Y");
                    license.getId().setControlItem(getLine(e.getStaffNumber(), "CONTROL_ITEM", "Employee_Control", "EMPLOYEE"));
                    license.setEngine(techControl);
                    
                }

                // Set the details of the EmployeeControl record
                license.setReference(e.getAuthorizationNumber());
                license.getId().setEmployee(e.getStaffNumber());
                license.setIssuedAuthority(issuedAuthority);
                license.setEngine(techControl);
                license.setLicenceType(e.getRecordItemName().toUpperCase());

                String skill = null;
                try {
                    skill = (String) em.createNativeQuery(
                    		"SELECT SKILL \r\n"
                                    + "FROM ESD_SKILL_MAPPING \r\n"
                                    + "WHERE LOWER(skill_name) = LOWER(:skillName)")
                        .setParameter("skillName", e.getRecordItemParent())
                        .getSingleResult();
                } catch (Exception ex) {
                    logger.warning("No skill found for the skill name: " + e.getRecordItemParent());
                }
                license.setSkillesd(skill);

                // Set the status of the license based on authorization status
                if (Inactive.contains(e.getAuthorizationStatus())) {
                    license.setStatus("INACTIVE");
                    expire = true;
                } else {
                    license.setStatus("ACTIVE");
                }

                // Parse and set the expiration date
                try {
                    license.setExpireDate(format.parse(e.getAuthorizationExpiryDate()));
                } catch (ParseException e1) {
                    logger.severe("Error parsing expiration date for license: " + license.getReference());
                }

                // Check if the license is expired
                if (license.getExpireDate().before(new Date())) {
                    expire = true;
                }

                // If expired, issue a warning and remove stamp signature
                if (expire) {
                    logger.warning("WARNING: Employee License is expired: " + license.getReference() + " Expiry Date: " + license.getExpireDate() + " Status: " + license.getStatus());
                    removeStampSign(e);
                }

                // Update modified details
                license.setModifiedBy("TRAX_IFACE");
                license.setModifiedDate(new Date());

                logger.info("Before inserting EmployeeControl for authority: " + issuedAuthority);
                insertData(license);
                logger.info("After inserting EmployeeControl");
            } catch (Exception ex) {
                logger.severe("Unexpected error during creation or insertion of EmployeeControl: " + ex.getMessage());
            }
        }
    }
    
    private long getLine(String no, String table_line, String table, String table_no)
	{		
		long line = 0;
		String query = " SELECT  MAX("+table_line+") FROM "+table+" WHERE "+table_no+" = ?";
		PreparedStatement ps = null;

		try
		{
			if(con == null || con.isClosed())
			{
				con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
			}
			ps = con.prepareStatement(query);
			ps.setString(1, no);

			ResultSet rs = ps.executeQuery();		
			if (rs != null) 
			{
				while (rs.next()) 
				{
					line = rs.getLong(1);
				}
			}
			rs.close();
			line++;
		}
		catch (Exception e) 
		{
			line = 1;
		}
		finally 
		{
			try 
			{
				if(ps != null && !ps.isClosed())
					ps.close();
			} 
			catch (SQLException e) 
			{ 
				logger.severe("An error ocurrer trying to close the statement");
			}
		}

		return line;
	}
    
    private void removeStampSign(EmployeeLicense e) {
        List<BlobTable> blobs = null;
        
        try {
            BigDecimal blobNo = getEmployeeBlob(e.getStaffNumber());
            blobs = em.createQuery("SELECT b FROM BlobTable b WHERE b.id.blobNo = :blo", BlobTable.class)
                    .setParameter("blo", blobNo.longValue())
                    .getResultList();
            
            for (BlobTable b : blobs) {
                if ("STAMP.png".equalsIgnoreCase(b.getBlobDescription()) || "ESIGN.png".equalsIgnoreCase(b.getBlobDescription())) {
                    logger.info("DELETE blob: " + b.getId().getBlobNo() + " Line: " + b.getId().getBlobLine() + " Description: " + b.getBlobDescription());
                    deleteData(b);
                }
            }
        } catch (Exception ex) {
            logger.info("No Stamp or Sign found for Employee: " + e.getStaffNumber());
        }
    }
    
    private String getEmployee(String e) {
        try {
            RelationMaster employee = em.createQuery("SELECT r FROM RelationMaster r WHERE r.id.relationCode = :em AND r.id.relationTransaction = :tr", RelationMaster.class)
                    .setParameter("em", e)
                    .setParameter("tr", "EMPLOYEE")
                    .getSingleResult();
            
            return employee.getId().getRelationCode();
        } catch (Exception ex) {
            logger.severe("Employee not found: " + e);
        }
        return null;
    }
    
    private BigDecimal getEmployeeBlob(String e) {
        try {
            RelationMaster employee = em.createQuery("SELECT r FROM RelationMaster r WHERE r.id.relationCode = :em AND r.id.relationTransaction = :tr", RelationMaster.class)
                    .setParameter("em", e)
                    .setParameter("tr", "EMPLOYEE")
                    .getSingleResult();

            return employee.getBlobNo();
        } catch (Exception ex) {
            logger.severe("Employee Blob not found: " + e);
        }
        return null;
    }

    private String getCustomer(String customer) {
        try {
            RelationMaster relationMaster = em.createQuery("SELECT r FROM RelationMaster r WHERE r.id.relationCode = :na AND r.id.relationTransaction = :tr", RelationMaster.class)
                    .setParameter("na", customer)
                    .setParameter("tr", "CUSTOMER")
                    .getSingleResult();

            return relationMaster.getId().getRelationCode();
        } catch (Exception e) {
            logger.severe("Customer not found: " + customer);
        }
        return null;
    }

    private EmployeeAuthPreReq setEmployeeAuthPreReq(String code) {
        EmployeeAuthPreReq employeeAuthPreReq = null;

        try {
            employeeAuthPreReq = em.createQuery("SELECT e FROM EmployeeAuthPreReq e WHERE e.id.authorizationCode = :co AND e.id.transactionType = :tr", EmployeeAuthPreReq.class)
                    .setParameter("co", code)
                    .setParameter("tr", "LICENSE")
                    .getSingleResult();
        } catch (Exception e) {
            employeeAuthPreReq = new EmployeeAuthPreReq();
            EmployeeAuthPreReqPK pk = new EmployeeAuthPreReqPK();
            employeeAuthPreReq.setId(pk);
            employeeAuthPreReq.setCreatedDate(new Date());
            employeeAuthPreReq.setCreatedBy("TRAX_IFACE");
            employeeAuthPreReq.getId().setItem(getLine(code, "AUTHORIZATION_CODE", "employee_Auth_Pre_Req", "ITEM"));
            employeeAuthPreReq.getId().setTransactionType("LICENSE");
        }
        employeeAuthPreReq.getId().setAuthorizationCode(code);
        employeeAuthPreReq.setTransactionCode("Part-66");
        employeeAuthPreReq.setModifiedBy("TRAX_IFACE");
        employeeAuthPreReq.setModifiedDate(new Date());

        logger.info("INSERTING EMPLOYEE AUTH PRE REQ AuthorizationCode: " + employeeAuthPreReq.getId().getAuthorizationCode() + " ITEM: " + employeeAuthPreReq.getId().getItem());
        insertData(employeeAuthPreReq);

        return employeeAuthPreReq;
    }

    // Insert generic data into the database
    private <T> void insertData(T data) {
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.merge(data);  // Utiliza merge para insertar o actualizar
            em.flush();  // Asegura que se aplique el cambio a la base de datos
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();  // Hace rollback en caso de error
            }
            logger.severe("Error during insert/update: " + e.getMessage());
        }
    }



    private boolean checkMinValue(EmployeeLicense e) {
        if (e.getStaffNumber() == null || e.getStaffNumber().isEmpty()) {
            Authorization_Details_Controller.addError("Cannot insert/update Employee: " + e.getStaffNumber() + " due to ERROR StaffNumber");
            return false;
        }

        if (e.getAuthorizationNumber() == null || e.getAuthorizationNumber().isEmpty()) {
            Authorization_Details_Controller.addError("Cannot insert/update Employee: " + e.getStaffNumber() + " due to ERROR AuthorizationNumber");
            return false;
        }

        if (e.getAuthorizationExpiryDate() == null || e.getAuthorizationExpiryDate().isEmpty()) {
            Authorization_Details_Controller.addError("Cannot insert/update Employee: " + e.getStaffNumber() + " due to ERROR AuthorizationExpiryDate");
            return false;
        }

        return true;
    }

    // Delete generic data from the database
    private <T> void deleteData(T data) {
        try {
            if (!em.getTransaction().isActive())
                em.getTransaction().begin();
            em.remove(data);
            em.getTransaction().commit();
        } catch (Exception e) {
            executed = "deleteData encountered an Exception: " + e.toString();
            Authorization_Details_Controller.addError(executed);
            logger.severe(e.toString());
        }
    }

    void insertDefaultVendorCategory(String cat) {
        SystemTranCode category = null;
        try {
            category = em.createQuery("SELECT s FROM SystemTranCode s WHERE s.id.systemTransaction = :tran AND s.id.systemCode = :code", SystemTranCode.class)
                    .setParameter("tran", "EMAUTCAT")
                    .setParameter("code", cat)
                    .getSingleResult();
            return;
        } catch (Exception e) {
            category = new SystemTranCode();
            SystemTranCodePK pk = new SystemTranCodePK();
            category.setId(pk);
            category.setCreatedBy("TRAX_IFACE");
            category.setCreatedDate(new Date());
            category.setModifiedBy("TRAX_IFACE");
            category.setModifiedDate(new Date());

            category.getId().setSystemCode(cat);
            category.getId().setSystemTransaction("EMAUTCAT");
            category.getId().setSystemTranCodeSub("          ");
            category.setPnTransaction("C");
            category.setPnCostingMethod("A");
            category.setConfigFlag("N");
            category.setTagType("FORMONE");
            category.setTemporaryRevision("NO");
            category.setChapterMandatory("NO");
            category.setSectionMandatory("NO");
            category.setDeferCatAutoMddrClosing("YES");
            category.setDefectReportPilot("YES");
            category.setVendorStatus("ACTIVE");
            category.setStatus("ACTIVE");
            category.setPnCategoryInventoryType("MAINTENANCE");
            category.setDoNotAllowReset("NO");
            category.setAutoResetOnRo("NO");
            category.setDoNotAllowResetProd("NO");
            category.setAcRestriction("P");
            category.setRadiocative("N");
            category.setCodeColor("8421376");

            category.setConfigNumber(BigDecimal.ZERO);
            category.setCurrencyExchange(BigDecimal.ZERO);

            category.setMelCycles(BigDecimal.ZERO);
            category.setMelHours(BigDecimal.ZERO);
            category.setRevenueFlight("NO");
            category.setEtops("NO");

            category.setAlertNoOfRemoval(BigDecimal.ZERO);
            category.setAlertCategoryNoOf(BigDecimal.ZERO);

            category.setAcMandatory("NO");
            category.setEquipmentRefDesignator("N");
            category.setRosClassificationCode("O");
            category.setIfrs("NO");
            category.setSupplier("NO");
            category.setSupplierMaintenance("NO");
            category.setSupplierGeneral("NO");
            category.setSupplierGse("NO");
            category.setRepair("NO");
            category.setRepairMaintenance("NO");
            category.setRepairGeneral("NO");
            category.setRepairGse("NO");
            category.setFreightForwarder("N");
            category.setService("NO");
            category.setAdCategory("N");
            category.setEcNameOverride("YES");
            category.setVbLifeLimit("NO");
            category.setVopBaseOnCondition(BigDecimal.ZERO);
            category.setCabin("N");
            category.setHighDollar("N");
            category.setBinCountFrequency("YEAR");
            category.setBinCountNoWeekend("NO");
            category.setFollowGrb("NO");
            category.setLoadAtRecv("N");
            category.setAllowBust("NO");
            category.setSwoRequisition("N");

            category.setOpsRestriction("NO");
            category.setPaperRequired("YES");
            category.setImage("bmp-ghs-01-bmp");
            category.setScrapReorder("YES");
            category.setEssentialityCodeLevel("NOGO");
            category.setMonthDepreciation(BigDecimal.ZERO);
            category.setThirdPartyWo("N");

            logger.info("INSERTING CATEGORY: " + cat);
            insertData(category);
        }
    }

   
    private <T> void insertData( T data, String s) 
	{
		try 
		{	
			if(!em.getTransaction().isActive())
				em.getTransaction().begin();
				em.merge(data);
			em.getTransaction().commit();
		}catch (Exception e)
		{
			logger.severe(e.toString());
		}
	}

    public boolean lockAvailable(String notificationType) {
        InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i WHERE i.interfaceType = :type", InterfaceLockMaster.class)
                .setParameter("type", notificationType).getSingleResult();
        em.refresh(lock);

        if (lock.getLocked().intValue() == 1) {                
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime locked = LocalDateTime.ofInstant(lock.getLockedDate().toInstant(), ZoneId.systemDefault());
            Duration diff = Duration.between(locked, today);

            if (diff.getSeconds() >= lock.getMaxLock().longValue()) {
                lock.setLocked(new BigDecimal(1));
                insertData(lock);
                return true;
            }
            return false;
        } else {
            lock.setLocked(new BigDecimal(1));
            insertData(lock);
            return true;
        }
    }

    public void lockTable(String notificationType) {
        em.getTransaction().begin();
        InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i WHERE i.interfaceType = :type", InterfaceLockMaster.class)
                .setParameter("type", notificationType).getSingleResult();
        lock.setLocked(new BigDecimal(1));
        
        lock.setLockedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            logger.info(e.getMessage());
        }
        lock.setCurrentServer(address.getHostName());
        em.merge(lock);
        em.getTransaction().commit();
    }

    public void unlockTable(String notificationType) {
        em.getTransaction().begin();

        InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i WHERE i.interfaceType = :type", InterfaceLockMaster.class)
                .setParameter("type", notificationType).getSingleResult();
        lock.setLocked(new BigDecimal(0));

        lock.setUnlockedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        em.merge(lock);
        em.getTransaction().commit();
    }
}