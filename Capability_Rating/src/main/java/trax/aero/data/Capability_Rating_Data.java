package trax.aero.data;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import trax.aero.interfaces.ICapability_Rating_Data;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.model.PartAuthorityESD;
import trax.aero.model.PartAuthorityESD_PK;
import trax.aero.model.PnMaster;
import trax.aero.pojo.DATA;
import trax.aero.pojo.DATAMasterResponse;
import trax.aero.pojo.DATARequest;
import trax.aero.pojo.DATAResponse;

@Stateless(name = "Capability_Rating_Data", mappedName = "Capability_Rating_Data")
public class Capability_Rating_Data implements ICapability_Rating_Data {

    @PersistenceContext(unitName = "TraxStandaloneDS")
    private EntityManager em;

    Logger logger = LogManager.getLogger("CapabilityRat");

    public Capability_Rating_Data() {
    }

    public String translateCategory(String catCategory) {
        switch (catCategory) {
            case "B1":
                return "ENGINE";
            case "B3":
                return "APU";
            case "C7":
                return "MODULE";
            default:
                return null;
        }
    }

    public DATAMasterResponse importAuth(DATA input) {
        DATAMasterResponse output = new DATAMasterResponse();
        if (input.getData() != null) {
            DATAResponse dataResponse = importAuthPN(input.getData());
            output.setData(dataResponse);
        }
        return output;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private DATAResponse importAuthPN(DATARequest element) {
        DATAResponse item = new DATAResponse();

        if (!checkValueString(element.getPartNo()) || !checkValueString(element.getAuthorityType())) {
            System.out.println("Authority: " + element.getAuthorityType() + " or PN: " + element.getPartNo() + " is null or empty");
            return item;
        }
        
        String authType = element.getAuthorityType();
        try {
            String checkAuthMappingStr = "SELECT AUTHORITY_MODIFIED FROM ESD_AUTHORITY_MAPPING WHERE AUTHORITY_ORIGINAL = ?";
            Query checkAuthMapping = em.createNativeQuery(checkAuthMappingStr);
            checkAuthMapping.setParameter(1, authType);
            String modifiedAuth = (String) checkAuthMapping.getSingleResult();
            if (modifiedAuth != null) {
                authType = modifiedAuth;
            }
        } catch (NoResultException ex) {
            // No mapping found, use original value
        }
        try {
        	// **SCENARIO DETECTION**
            String originalPN = element.getPartNo();
            PnMaster originalPnMaster = em.find(PnMaster.class, originalPN);
            
            // Check for ALL matching PNs with ':' suffix
            String checkPnMasterStr = "SELECT PN FROM PN_MASTER WHERE PN LIKE ?";
            Query checkPnMasterQuery = em.createNativeQuery(checkPnMasterStr);
            checkPnMasterQuery.setParameter(1, originalPN + ":%");
            List<String> matchingPNs = checkPnMasterQuery.getResultList();
            
            System.out.println("Original PN: " + originalPN + " exists in PN_MASTER: " + (originalPnMaster != null));
            System.out.println("Found " + matchingPNs.size() + " matching PN(s) with ':' suffix: " + matchingPNs);
            
            // Determine which PNs to process
            List<String> pnsToProcess = new ArrayList<>();
            
            if (originalPnMaster != null && !matchingPNs.isEmpty()) {
                // **SCENARIO 1**: Both original and matching PN(s) exist - process all
                System.out.println("SCENARIO 1: Both original and " + matchingPNs.size() + " matching PN(s) exist. Processing all.");
                pnsToProcess.add(originalPN);
                pnsToProcess.addAll(matchingPNs);
            } else if (originalPnMaster == null && !matchingPNs.isEmpty()) {
                // **SCENARIO 2**: Only matching PN(s) exist - process all matching
                System.out.println("SCENARIO 2: Only " + matchingPNs.size() + " matching PN(s) exist. Processing matching PNs only.");
                pnsToProcess.addAll(matchingPNs);
            } else if (originalPnMaster != null && matchingPNs.isEmpty()) {
                // **SCENARIO 3**: Only original exists - process only original
                System.out.println("SCENARIO 3: Only original PN exists. Processing original PN only.");
                pnsToProcess.add(originalPN);
            } else {
                // Neither exists
                System.out.println("ERROR: Neither original PN nor matching PNs exist in PN_MASTER. Cannot process.");
                return item;
            }
            
            System.out.println("Total PNs to process: " + pnsToProcess.size() + " -> " + pnsToProcess);
            
            for(String pnToProcess : pnsToProcess) {
            	System.out.println("========================================");
                System.out.println("Processing PN: " + pnToProcess + " (" + (pnsToProcess.indexOf(pnToProcess) + 1) + " of " + pnsToProcess.size() + ")");
                System.out.println("========================================");
                
                PnMaster pnmaster = em.find(PnMaster.class, pnToProcess);
                
                if (pnmaster == null) {
                    System.out.println("ERROR: PN " + pnToProcess + " not found in PN_MASTER. Skipping.");
                    continue;
                }
                
                if (pnmaster.getStatus() != null && !pnmaster.getStatus().isEmpty() && pnmaster.getStatus().equalsIgnoreCase("INACTIVE")) {
                    System.out.println("PN " + pnToProcess + " is INACTIVE. Skipping.");
                    continue;
                }
                
                // **PART AUTHORITY ESD**
                PartAuthorityESD_PK primaryKey = new PartAuthorityESD_PK();
                primaryKey.setPn(pnToProcess);
                primaryKey.setAuthority(authType);
                
                PartAuthorityESD auth = em.find(PartAuthorityESD.class, primaryKey);
                if (auth == null) {
                    auth = new PartAuthorityESD();
                    auth.setId(primaryKey);
                    auth.setModifiedBy("TRAX_IFACE");
                    auth.setModifiedDate(new Date());
                    auth.setCreatedBy("TRAX_IFACE");
                    auth.setCreatedDate(new Date());
                    em.persist(auth);
                    System.out.println("Inserted new PartAuthorityESD record for PN: " + pnToProcess + ", Authority: " + authType);
                } else {
                    auth.setModifiedBy("TRAX_IFACE");
                    auth.setModifiedDate(new Date());
                    em.merge(auth);
                    System.out.println("Updated existing PartAuthorityESD record for PN: " + pnToProcess + ", Authority: " + authType);
                }
                
                if (element.getRevNo() != null) {
                    try {
                        Integer newRevNumber = Integer.parseInt(element.getRevNo());
                        if (auth.getRevNumber() != null && newRevNumber <= auth.getRevNumber()) {
                            System.out.println("Received RevNo " + newRevNumber + " is less than or equal to existing RevNo " + auth.getRevNumber() + ". No update performed for PN: " + pnToProcess);
                            continue;
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
                    if (element.getClcfNo() != null) auth.setPnType(element.getClcfNo());
                    if (element.getCompCapability() != null) auth.setCompCapability(element.getCompCapability());
                    if (element.getWorkshop() != null) auth.setWorkshop(element.getWorkshop());
                    if (element.getQltyStatus() != null) auth.setQltyStatus(element.getQltyStatus());
                    if (element.getDate() != null) {
                        auth.setAuthorityDate(element.getDate());
                    } else {
                        auth.setAuthorityDate("");
                    }
                    em.merge(auth);
                }
                
                // **TASK_CARD_PN_EFFECTIVITY**
                System.out.println("CHECKING TASK_CARD EFFECTIVITY FOR PN_TYPE: " + element.getClcfNo() + " and PN: " + pnToProcess);
                try {
                    String pnType = element.getClcfNo();
                    
                    String queryStr = "SELECT DISTINCT te.task_card " +
                                      "FROM TASK_CARD_PN_EFFECTIVITY te " +
                                      "JOIN pn_master pm ON te.pn = pm.pn " +
                                      "WHERE pm.pn_type = ?";
                    Query query = em.createNativeQuery(queryStr);
                    query.setParameter(1, pnType);
                    List<String> taskCards = query.getResultList();
                    System.out.println("Found " + taskCards.size() + " task card(s) for PN_TYPE: " + pnType);

                    if (taskCards.isEmpty()) {
                        System.out.println("No task cards found for PN_TYPE: " + pnType);
                    }

                    for (String taskCard : taskCards) {
                        System.out.println("Using MERGE for task_card: " + taskCard + " and PN: " + pnToProcess);
                        
                        String mergeQuery = "MERGE INTO TASK_CARD_PN_EFFECTIVITY target " +
                                           "USING (SELECT ? AS task_card, ? AS pn FROM DUAL) source " +
                                           "ON (target.TASK_CARD = source.task_card AND target.PN = source.pn) " +
                                           "WHEN NOT MATCHED THEN " +
                                           "INSERT (TASK_CARD, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, \"SELECT\", PN) " +
                                           "VALUES (?, 'TRAXIFACE', SYSDATE, 'TRAXIFACE', SYSDATE, 'Y', ?)";
                        
                        Query mergeQueryObj = em.createNativeQuery(mergeQuery);
                        mergeQueryObj.setParameter(1, taskCard);
                        mergeQueryObj.setParameter(2, pnToProcess);
                        mergeQueryObj.setParameter(3, taskCard);
                        mergeQueryObj.setParameter(4, pnToProcess);
                        
                        mergeQueryObj.executeUpdate();
                        System.out.println("Successfully merged record for task_card: " + taskCard + " and PN: " + pnToProcess);
                    } 
                } catch (Exception e) {
                    System.out.println("Error occurred while merging into TASK_CARD_PN_EFFECTIVITY for PN: " + pnToProcess);
                    e.printStackTrace();
                }
                
                // **ENGINEERING_CONTROL**
                System.out.println("CHECKING EC EFFECTIVITY FOR PN_TYPE: " + element.getClcfNo() + " and PN: " + pnToProcess);
                try {
                    String pnType = element.getClcfNo();
                    
                    String queryStr = "SELECT DISTINCT te.eo " +
                                      "FROM ENGINEERING_CONTROL te " +
                                      "JOIN pn_master pm ON te.pn = pm.pn " +
                                      "WHERE pm.pn_type = ?";
                    Query query = em.createNativeQuery(queryStr);
                    query.setParameter(1, pnType);
                    List<String> EC = query.getResultList();
                    System.out.println("Found " + EC.size() + " EC(s) for PN_TYPE: " + pnType);

                    if (EC.isEmpty()) {
                        System.out.println("No engineering controls found for PN_TYPE: " + pnType);
                    }

                    for (String EO : EC) {
                        String checkECorder = "SELECT COUNT(*) FROM ENGINEERING_ORDER WHERE eo = ? ";
                        Query checkOrder = em.createNativeQuery(checkECorder);
                        checkOrder.setParameter(1, EO);
                        long count_order = ((Number) checkOrder.getSingleResult()).longValue();
                        
                        if (count_order > 0) {
                            System.out.println("Using MERGE for EC: " + EO + " and PN: " + pnToProcess);
                            
                            String mergeQuery = "MERGE INTO ENGINEERING_CONTROL target " +
                                               "USING (SELECT ? AS eo, ? AS pn FROM DUAL) source " +
                                               "ON (target.EO = source.eo AND target.PN = source.pn) " +
                                               "WHEN NOT MATCHED THEN " +
                                               "INSERT (EO, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, PN, EO_EFFECTIVITY_CATEGORY, AC_TYPE, AC_SERIES, \"SELECT\", CONTROL_OVERRIDE) " +
                                               "VALUES (?, 'TRAXIFACE', SYSDATE, 'TRAXIFACE', SYSDATE, ?, 'PNSHOP', '          ', '          ', 'Y', 'N')";
                            
                            Query mergeQueryObj = em.createNativeQuery(mergeQuery);
                            mergeQueryObj.setParameter(1, EO);
                            mergeQueryObj.setParameter(2, pnToProcess);
                            mergeQueryObj.setParameter(3, EO);
                            mergeQueryObj.setParameter(4, pnToProcess);
                            
                            mergeQueryObj.executeUpdate();
                            System.out.println("Successfully merged record for EC: " + EO + " and PN: " + pnToProcess);
                        } else {
                            System.out.println("EC " + EO + " Skipping insertion. EC does not have Engineering Order.");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error occurred while merging into ENGINEERING_CONTROL for PN: " + pnToProcess);
                    e.printStackTrace();
                }
                
             // **SYSTEM_TRAN_CODE UPDATES** (only once, not per PN)
                if (pnToProcess.equals(pnsToProcess.get(0))) { // Execute only for first PN
                    if (element.getQltyStatus().equalsIgnoreCase("Current") || element.getQltyStatus().equalsIgnoreCase("Update")) {
                        System.out.println("Inserting SYSTEM_TRAN_CODE entries (executed only once for all PNs)");
                        insertSystemTranCodes(element, auth, authType);
                    }
                }
                
                // **UPDATE PN_MASTER**
                System.out.println("Updating PN_MASTER " + pnToProcess + " into the Trax DataBase");
                System.out.println("TECH_CONTROL: " + translateCategory(element.getCatCategory()) + " PN_TYPE: " + element.getClcfNo());
                if (!element.getQltyStatus().equalsIgnoreCase("Terminated")) {
                    String updatePnMaster = "UPDATE PN_MASTER SET ENGINE = ?, PN_TYPE = ?, MODIFIED_DATE = sysdate, MODIFIED_BY = 'IFACEESD' WHERE PN = ?";
                    Query updatePN = em.createNativeQuery(updatePnMaster);
                    updatePN.setParameter(1, translateCategory(element.getCatCategory()));
                    updatePN.setParameter(2, element.getClcfNo());
                    updatePN.setParameter(3, pnToProcess);
                    updatePN.executeUpdate();
                    System.out.println("Successfully updated PN_MASTER for PN: " + pnToProcess);
                    
                    insertPnMasterAudit(pnToProcess, translateCategory(element.getCatCategory()), element.getClcfNo());
                }
                
             // **PN_AUTHORITY_APPROVAL**
                System.out.println("Processing PN_AUTHORITY_APPROVAL for Authority: " + authType + " and PN: " + pnToProcess);
                insertData(auth);
                
                boolean isActive = (auth.getAuthorityDate() != null && 
                                    !auth.getAuthorityDate().trim().isEmpty() && 
                                    !auth.getAuthorityDate().equalsIgnoreCase("null") &&  
                                    !element.getQltyStatus().equalsIgnoreCase("Terminated"));
                
                String activeStatus = isActive ? "Y" : "N";
                System.out.println("Preparing to check and insert/update PN_AUTHORITY_APPROVAL with ACTIVE: " + activeStatus + " for PN: " + pnToProcess);
                
                try {
                    String checkQueryStr = "SELECT COUNT(*) FROM PN_AUTHORITY_APPROVAL WHERE PN = ? AND AUTHORITY = ?";
                    Query checkQuery = em.createNativeQuery(checkQueryStr);
                    checkQuery.setParameter(1, pnToProcess);
                    checkQuery.setParameter(2, authType);
                    long count = ((Number) checkQuery.getSingleResult()).longValue();
                    System.out.println("Count of records found in PN_AUTHORITY_APPROVAL: " + count);

                    if (count > 0) {
                        System.out.println("Record exists. Updating AUTH_STATUS field in PN_AUTHORITY_APPROVAL");
                        String updateQueryStr = "UPDATE PN_AUTHORITY_APPROVAL SET AUTH_STATUS = ?, MODIFIED_BY = 'TRAX_IFACE', MODIFIED_DATE = SYSDATE WHERE PN = ? AND AUTHORITY = ?";
                        Query updateQuery = em.createNativeQuery(updateQueryStr);
                        updateQuery.setParameter(1, activeStatus);
                        updateQuery.setParameter(2, pnToProcess);
                        updateQuery.setParameter(3, authType);
                        updateQuery.executeUpdate();
                        System.out.println("Successfully updated PN_AUTHORITY_APPROVAL for PN: " + pnToProcess);

                        if (activeStatus.equalsIgnoreCase("N")) {
                            String deleteAuth = "DELETE FROM PN_AUTHORITY_APPROVAL WHERE PN = ? AND AUTHORITY = ?";
                            Query deleteAuthObj = em.createNativeQuery(deleteAuth);
                            deleteAuthObj.setParameter(1, pnToProcess);
                            deleteAuthObj.setParameter(2, authType);
                            deleteAuthObj.executeUpdate();
                            System.out.println("Successfully deleted (inactivated) record in PN_AUTHORITY_APPROVAL for PN: " + pnToProcess);
                        }
                    } else {
                        System.out.println("Record does not exist. Inserting into PN_AUTHORITY_APPROVAL");
                        String insertQuery = "INSERT INTO PN_AUTHORITY_APPROVAL (PN, AUTHORITY, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, WO, PRINTED, REASON, PN_SN, AUTH_STATUS, EXTERNAL_SELECTED) " +
                                             "VALUES (?, ?, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?, NULL)";
                        Query insertQueryObj = em.createNativeQuery(insertQuery);
                        insertQueryObj.setParameter(1, pnToProcess);
                        insertQueryObj.setParameter(2, authType);
                        insertQueryObj.setParameter(3, "TRAX_IFACE");
                        insertQueryObj.setParameter(4, new Date());
                        insertQueryObj.setParameter(5, "TRAX_IFACE");
                        insertQueryObj.setParameter(6, new Date());
                        insertQueryObj.setParameter(7, activeStatus);
                        insertQueryObj.executeUpdate();
                        System.out.println("Successfully inserted record into PN_AUTHORITY_APPROVAL for PN: " + pnToProcess);

                        if (activeStatus.equalsIgnoreCase("N")) {
                            String deleteAuth = "DELETE FROM PN_AUTHORITY_APPROVAL WHERE PN = ? AND AUTHORITY = ?";
                            Query deleteAuthObj = em.createNativeQuery(deleteAuth);
                            deleteAuthObj.setParameter(1, pnToProcess);
                            deleteAuthObj.setParameter(2, authType);
                            deleteAuthObj.executeUpdate();
                            System.out.println("Successfully deleted (inactivated) newly inserted record in PN_AUTHORITY_APPROVAL for PN: " + pnToProcess);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error occurred while checking or inserting/updating PN_AUTHORITY_APPROVAL for PN: " + pnToProcess);
                    e.printStackTrace();
                }
                
                System.out.println("Completed processing for PN: " + pnToProcess);
                
            }
            
            System.out.println("========================================");
            System.out.println("COMPLETED: Processed " + pnsToProcess.size() + " PN(s) successfully");
            System.out.println("========================================");
                
            return item;
        	
        } catch (Exception e) {
            System.out.println("An error occurred during the import process.");
            e.printStackTrace();
            return item;
        }
        
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private <T> void insertData(T data) {
        try {
            em.merge(data);  // Use merge for both insert and update
        } catch (Exception e) {
            logger.severe(e.toString());
        }
    }

    private boolean checkValueString(String data) {
        return (data != null && !data.isEmpty());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean lockAvailable(String notificationType) {
        InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i WHERE i.interfaceType = :type", InterfaceLockMaster.class)
                .setParameter("type", notificationType).getSingleResult();
        em.refresh(lock);

        if (lock.getLocked().intValue() == 1) {                
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime locked = LocalDateTime.ofInstant(lock.getLockedDate().toInstant(), ZoneId.systemDefault());
            Duration diff = Duration.between(locked, today);

            if (diff.getSeconds() >= lock.getMaxLock().longValue()) {
               
                return true;
            }
            
            return false;
        } else {
            
            return true;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void lockTable(String notificationType) {
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
        em.flush();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void unlockTable(String notificationType) {
        try {
            InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i WHERE i.interfaceType = :type", InterfaceLockMaster.class)
                    .setParameter("type", notificationType).getSingleResult();
            
            lock.setLocked(new BigDecimal(0));
            lock.setUnlockedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            
            em.merge(lock);
            em.flush();
            
            logger.info("Successfully unlocked table for notification type: " + notificationType);
        } catch (Exception e) {
            logger.severe("Error unlocking table for notification type: " + notificationType + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void insertSystemTranCodes(DATARequest element, PartAuthorityESD auth, String authType) {
        System.out.println("CHECKING PN_TYPE: " + element.getClcfNo() + " into the Trax DataBase");
        try {
            String checkPnTypeStr = "SELECT COUNT(*) FROM SYSTEM_TRAN_CODE WHERE SYSTEM_TRANSACTION = 'PNTYPE' AND SYSTEM_CODE = ?";
            Query checkPnType = em.createNativeQuery(checkPnTypeStr);
            checkPnType.setParameter(1, auth.getPnType());
            long count = ((Number) checkPnType.getSingleResult()).longValue();
            System.out.println("Count of records found: " + count);
            
            if (count == 0) {
                System.out.println("Record does not exist. Inserting into SYSTEM_TRAN_CODE");
                String insertPnTypeQuery = "INSERT INTO SYSTEM_TRAN_CODE (SYSTEM_TRANSACTION, SYSTEM_CODE, SYSTEM_CODE_DESCRIPTION, PN_TRANSACTION, PN_COSTING_METHOD, CREATED_BY, CREATED_DATE, SYSTEM_TRAN_CODE_SUB, STATUS) " +
                                           "VALUES ('PNTYPE', ?, ?, 'C', 'A', 'TRAX_IFACE', SYSDATE, 'I25', 'ACTIVE' )";
                Query insertPnTypeQueryObj = em.createNativeQuery(insertPnTypeQuery);
                insertPnTypeQueryObj.setParameter(1, auth.getPnType());
                insertPnTypeQueryObj.setParameter(2, auth.getPnType());
                insertPnTypeQueryObj.executeUpdate();
                System.out.println("Successfully inserted into SYSTEM_TRAN_CODE");
            } else {
                System.out.println("Record already exists. No insertion needed.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while checking or inserting/updating SYSTEM_TRAN_CODE");
            e.printStackTrace();
        }
        
        System.out.println("CHECKING EMPLOYEE_TYPE: " + element.getClcfNo() + " into the Trax DataBase");
        try {
            String checkEmTypeStr = "SELECT COUNT(*) FROM SYSTEM_TRAN_CODE WHERE SYSTEM_TRANSACTION = 'EMPLICTYP' AND SYSTEM_CODE = ?";
            Query checkEmType = em.createNativeQuery(checkEmTypeStr);
            checkEmType.setParameter(1, auth.getPnType());
            long count = ((Number) checkEmType.getSingleResult()).longValue();
            System.out.println("Count of records found: " + count);
            
            if (count == 0) {
                System.out.println("Record does not exist. Inserting into SYSTEM_TRAN_CODE");
                String insertEmTypeQuery = "INSERT INTO SYSTEM_TRAN_CODE (SYSTEM_TRANSACTION, SYSTEM_CODE, SYSTEM_CODE_DESCRIPTION, PN_TRANSACTION, PN_COSTING_METHOD, CREATED_BY, CREATED_DATE, SYSTEM_TRAN_CODE_SUB, STATUS) " +
                                           "VALUES ('EMPLICTYP', ?, ?, 'C', 'A', 'TRAX_IFACE', SYSDATE, 'I25', 'ACTIVE' )";
                Query insertEmTypeQueryObj = em.createNativeQuery(insertEmTypeQuery);
                insertEmTypeQueryObj.setParameter(1, auth.getPnType());
                insertEmTypeQueryObj.setParameter(2, auth.getPnType());
                insertEmTypeQueryObj.executeUpdate();
                System.out.println("Successfully inserted into SYSTEM_TRAN_CODE");
            } else {
                System.out.println("Record already exists. No insertion needed.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while checking or inserting/updating SYSTEM_TRAN_CODE");
            e.printStackTrace();
        }
        
        System.out.println("CHECKING AUTHORITY TRANSCODE: " + authType + " into the Trax DataBase");
        try {
            String checkAuthStr = "SELECT COUNT(*) FROM SYSTEM_TRAN_CODE WHERE SYSTEM_TRANSACTION = 'AUTHAPPROVAL' AND SYSTEM_CODE = ?";
            Query checkAuth = em.createNativeQuery(checkAuthStr);
            checkAuth.setParameter(1, authType);
            long count = ((Number) checkAuth.getSingleResult()).longValue();
            System.out.println("Count of records found: " + count);
            
            if (count == 0) {
                System.out.println("Record does not exist. Inserting into SYSTEM_TRAN_CODE");
                String insertAuthQuery = "INSERT INTO SYSTEM_TRAN_CODE (SYSTEM_TRANSACTION, SYSTEM_CODE, SYSTEM_CODE_DESCRIPTION, PN_TRANSACTION, PN_COSTING_METHOD, CREATED_BY, CREATED_DATE, SYSTEM_TRAN_CODE_SUB, STATUS) " +
                                         "VALUES ('AUTHAPPROVAL', ?, ?, 'C', 'A', 'TRAX_IFACE', SYSDATE, 'I25', 'ACTIVE')";
                Query insertAuthQueryObj = em.createNativeQuery(insertAuthQuery);
                insertAuthQueryObj.setParameter(1, authType);
                insertAuthQueryObj.setParameter(2, authType);
                insertAuthQueryObj.executeUpdate();
                System.out.println("Successfully inserted into SYSTEM_TRAN_CODE");
            } else {
                System.out.println("Record already exists. No insertion needed.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while checking or inserting/updating SYSTEM_TRAN_CODE");
            e.printStackTrace();
        }
        
        System.out.println("CHECKING EMPLOYEE AUTHORITY TRANSCODE: " + authType + " into the Trax DataBase");
        try {
            String checkEAuthStr = "SELECT COUNT(*) FROM SYSTEM_TRAN_CODE WHERE SYSTEM_TRANSACTION = 'EMPLICAUT' AND SYSTEM_CODE = ?";
            Query checkEAuth = em.createNativeQuery(checkEAuthStr);
            checkEAuth.setParameter(1, authType);
            long count = ((Number) checkEAuth.getSingleResult()).longValue();
            System.out.println("Count of records found: " + count);
            
            if (count == 0) {
                System.out.println("Record does not exist. Inserting into SYSTEM_TRAN_CODE");
                String insertEAuthQuery = "INSERT INTO SYSTEM_TRAN_CODE (SYSTEM_TRANSACTION, SYSTEM_CODE, SYSTEM_CODE_DESCRIPTION, PN_TRANSACTION, PN_COSTING_METHOD, CREATED_BY, CREATED_DATE, SYSTEM_TRAN_CODE_SUB, STATUS) " +
                                         "VALUES ('EMPLICAUT', ?, ?, 'C', 'A', 'TRAX_IFACE', SYSDATE, 'I25', 'ACTIVE')";
                Query insertEAuthQueryObj = em.createNativeQuery(insertEAuthQuery);
                insertEAuthQueryObj.setParameter(1, authType);
                insertEAuthQueryObj.setParameter(2, authType);
                insertEAuthQueryObj.executeUpdate();
                System.out.println("Successfully inserted into SYSTEM_TRAN_CODE");
            } else {
                System.out.println("Record already exists. No insertion needed.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while checking or inserting/updating SYSTEM_TRAN_CODE");
            e.printStackTrace();
        }
    }
    
    
    public void insertPnMasterAudit(String pn, String engine, String pnType) {
        try {
            
            int randomDelay = (int)(Math.random() * 200);
            Thread.sleep(randomDelay);
            
            String selectPnMasterQuery = "SELECT PN_DESCRIPTION, CATEGORY FROM PN_MASTER WHERE PN = ?";
            Query selectPnMasterQueryObj = em.createNativeQuery(selectPnMasterQuery);
            selectPnMasterQueryObj.setParameter(1, pn);
            Object[] result = (Object[]) selectPnMasterQueryObj.getSingleResult();
            
            String pnDescription = (String) result[0];
            String pnCategory = (String) result[1];
            
            try {
                String disableConstraint = "ALTER TABLE PN_MASTER_AUDIT DISABLE CONSTRAINT P_PN_MASTER_AUDIT";
                Query disableQuery = em.createNativeQuery(disableConstraint);
                disableQuery.executeUpdate();
                System.out.println("Constraint P_PN_MASTER_AUDIT disabled for PN: " + pn);
                
                String insertAuditQuery = "INSERT INTO PN_MASTER_AUDIT (PN, PN_DESCRIPTION, CATEGORY, ENGINE, PN_TYPE, MODIFIED_DATE, MODIFIED_BY) " +
                                         "VALUES (?, ?, ?, ?, ?, SYSTIMESTAMP, 'IFACEESD')";
                Query insertAuditQueryObj = em.createNativeQuery(insertAuditQuery);
                insertAuditQueryObj.setParameter(1, pn);
                insertAuditQueryObj.setParameter(2, pnDescription);
                insertAuditQueryObj.setParameter(3, pnCategory);
                insertAuditQueryObj.setParameter(4, engine);
                insertAuditQueryObj.setParameter(5, pnType);
                insertAuditQueryObj.executeUpdate();
                System.out.println("Successfully inserted into PN_MASTER_AUDIT for PN: " + pn);
                
            } finally {
                try {
                    String enableConstraint = "ALTER TABLE PN_MASTER_AUDIT ENABLE CONSTRAINT P_PN_MASTER_AUDIT";
                    Query enableQuery = em.createNativeQuery(enableConstraint);
                    enableQuery.executeUpdate();
                    System.out.println("Constraint P_PN_MASTER_AUDIT re-enabled for PN: " + pn);
                } catch (Exception enableEx) {
                    System.out.println("CRITICAL: Failed to re-enable constraint P_PN_MASTER_AUDIT: " + enableEx.getMessage());
                    enableEx.printStackTrace();
                }
            }
            
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted for PN: " + pn);
        } catch (Exception e) {
            System.out.println("Error in insertPnMasterAudit for PN: " + pn);
            if (e.getMessage() != null && 
                (e.getMessage().contains("ORA-00054") ||
                 e.getMessage().contains("P_PN_MASTER_AUDIT") || 
                 e.getMessage().contains("unique constraint"))) {
                System.out.println("Non-critical: Lock timeout or constraint violation - skipping");
            } else {
                e.printStackTrace();
            }
        }
    }
}
