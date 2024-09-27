package trax.aero.data;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBException;


import trax.aero.controller.TECO_Handling_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.INT15_SND;
import trax.aero.pojo.INT15_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.ErrorType;

public class TECO_Handling_Data {
	
	EntityManagerFactory factory;
	EntityManager em;
	String executed;
	private Connection con;
	
	//final String MaxRecord = System.getProperty("TECO_MaxRecord");
	Logger logger = LogManager.getLogger("TECO_Handling");
	
	public TECO_Handling_Data(String mark) {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " +String.valueOf(!this.con.isClosed()));
			}
		} catch(SQLException e) {
			logger.severe("An error ocurred getting the status of the connection");
			TECO_Handling_Controller.addError(e.toString());
		} catch (CustomizeHandledException e1) {
			TECO_Handling_Controller.addError(e1.toString());
		} catch (Exception e) {
			TECO_Handling_Controller.addError(e.toString());
		}
	}
	
	public TECO_Handling_Data() {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!this.con.isClosed()));
			}
		}catch(SQLException e) {
			logger.severe("An error ocurred getting the status of the connection");
			TECO_Handling_Controller.addError(e.toString());
		} catch (CustomizeHandledException e1) {
			TECO_Handling_Controller.addError(e1.toString());
		} catch (Exception e) {
			TECO_Handling_Controller.addError(e.toString());
		}
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		em = factory.createEntityManager();
	}
	
	public Connection getCon() {
		return con;
	}
	
	public String markSendData() throws JAXBException {
	    INT15_TRAX request = new INT15_TRAX();
	    try {
	        if (request == null) {
	            logger.severe("Request object is null");
	            return null;
	        }
	        markTransaction(request);
	        logger.info("markTransaction completed successfully.");
	        return "OK";
	    } catch (Exception e) {
	        logger.log(Level.SEVERE, "Error executing markTransaction", e);
	        e.printStackTrace();
	        return null;
	    }
	}
	
private static Map<String, Integer> attemptCounts = new HashMap<>();
	
	public String markTransaction(INT15_TRAX request) {
	    String executed = "OK";
	    
	    
	    
	    String sqlUpdate = "UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_DATE = SYSDATE WHERE WO = ?";
	    String revert = "UPDATE WO SET STATUS = ? WHERE wo = ? ";
	    String STATUS = "SELECT STATUS FROM WO WHERE WO = ? ";
	    String sqlPrevStatus  = "SELECT WA1.STATUS " +
                "FROM WO_AUDIT WA1 " +
                "WHERE WA1.WO = ? " +
                "AND WA1.MODIFIED_DATE < ( " +
                "    SELECT MAX(WA2.MODIFIED_DATE) " +
                "    FROM WO_AUDIT WA2 " +
                "    WHERE WA2.WO = ? " +
                ") " +
                "AND WA1.STATUS != ( " +
                "    SELECT WA3.STATUS " +
                "    FROM WO_AUDIT WA3 " +
                "    WHERE WA3.WO = ? " +
                "    ORDER BY WA3.MODIFIED_DATE DESC " +
                "    FETCH FIRST 1 ROWS ONLY " +
                ") " +
                "ORDER BY WA1.MODIFIED_DATE DESC " +
                "FETCH FIRST 1 ROWS ONLY";
	    
	    String sqlInsertError = "INSERT INTO interface_audit (TRANSACTION, TRANSACTION_TYPE, ORDER_NUMBER, EO, TRANSACTION_OBJECT, TRANSACTION_DATE, CREATED_BY, MODIFIED_BY, EXCEPTION_ID, EXCEPTION_BY_TRAX, EXCEPTION_DETAIL, EXCEPTION_CLASS_TRAX, CREATED_DATE, MODIFIED_DATE) "
	            + "SELECT seq_interface_audit.NEXTVAL, 'ERROR', ?, ?, 'I15', sysdate, 'TRAX_IFACE', 'TRAX_IFACE', ?, 'Y', ?, 'TECO_Handling I_15', sysdate, sysdate FROM dual";
	    
	    String sqlDeleteError = "DELETE FROM interface_audit WHERE ORDER_NUMBER = ? AND EO = ? ";
	     
	    String sqlMark2 = "UPDATE WO SET INTERFACE_TECO_FLAG = CASE " +
	            "WHEN STATUS IN ('CLOSED') THEN 'D' " +
	            "WHEN STATUS = 'OPEN' AND REOPEN_REASON IS NOT NULL THEN 'Y' " +
	            "END " +
	            "WHERE WO = ? " +
	            "AND ((STATUS IN ('CLOSED', 'CANCEL')) OR (STATUS = 'OPEN' AND REOPEN_REASON IS NOT NULL)) ";
	    
	   String sqlMARK2 = "UPDATE PN_INVENTORY_HISTORY ATH " +
	             "SET ATH.INTERFACE_TECO_FLAG = (" +
	             "    SELECT CASE " +
	             "        WHEN W.STATUS IN ('CLOSED') THEN 'D' " +
	             "        WHEN W.STATUS = 'OPEN' AND W.REOPEN_REASON IS NOT NULL THEN 'Y' " +
	             "    END " +
	             "    FROM WO W " +
	             "    WHERE W.WO = ATH.WO " +
	             "    AND W.WO = ? " +
	             "    AND ((W.STATUS IN ('CLOSED', 'CANCEL')) OR (W.STATUS = 'OPEN' AND W.REOPEN_REASON IS NOT NULL))" +
	             ") " +
	             "WHERE EXISTS (" +
	             "    SELECT 1 " +
	             "    FROM WO W " +
	             "    WHERE W.WO = ATH.WO " +
	             "    AND W.WO = ? " +
	             "    AND ((W.STATUS IN ('CLOSED', 'CANCEL')) OR (W.STATUS = 'OPEN' AND W.REOPEN_REASON IS NOT NULL))" +
	             ")";
	    
	    String sqlMark3 = "UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_FLAG = NULL WHERE WO = ? AND TASK_CARD = ? AND SVO_NO = ? ";
	    
	    
	    String svocheck = "SELECT SVO_NO FROM PN_INVENTORY_HISTORY WHERE WO = ? AND TASK_CARD = ? "; 
	    
	    String sqlHistoryMark = "UPDATE PN_INVENTORY_HISTORY SET SVO_SENT = 'Y' WHERE WO = ? AND TASK_CARD = ? AND SVO_NO = ? ";
	    
	    String sqlMArk2 = "UPDATE WO_TASK_CARD " +
	    	    "SET SVO_SENT = 'Y' " +
	    	    "WHERE WO = ? " +
	    	    "AND TASK_CARD = ? " +
	    	    "AND NOT EXISTS ( " +
	    	    "    SELECT 1 " +
	    	    "    FROM PN_INVENTORY_HISTORY " +
	    	    "    WHERE WO = WO_TASK_CARD.WO " +
	    	    "    AND TASK_CARD = WO_TASK_CARD.TASK_CARD " +
	    	    "    AND (SVO_SENT IS NULL OR SVO_SENT <> 'Y') " +
	    	    ")";
	    
	    String sqlmarksvo = "UPDATE WO SET SVO_USED = NULL WHERE WO = ? AND NOT EXISTS (SELECT 1 FROM WO_TASK_CARD WHERE WO = WO.WO AND SVO_SENT <> 'Y') ";

	    try {
	        if (con == null) {
	            logger.severe("Database connection is null");
	            return "Database connection is null";
	        }
	        
	        PreparedStatement pstmt1 = con.prepareStatement(sqlUpdate);
	        PreparedStatement pstmt2 = con.prepareStatement(revert);
	        PreparedStatement Status = con.prepareStatement(STATUS);
	        PreparedStatement psStatus = con.prepareStatement(sqlPrevStatus);
	        PreparedStatement psInsertError = con.prepareStatement(sqlInsertError);
	        PreparedStatement psDeleteError = con.prepareStatement(sqlDeleteError);
	        PreparedStatement pstmt4 = con.prepareStatement(sqlMark2);
	        PreparedStatement pstmt41 = con.prepareStatement(sqlMARK2);
	        PreparedStatement pstmt5 = con.prepareStatement(sqlMark3);
	        PreparedStatement svoch = con.prepareStatement(svocheck);
	        PreparedStatement svomark = con.prepareStatement(sqlMArk2);
	        PreparedStatement svomark2 = con.prepareStatement(sqlmarksvo);
	        PreparedStatement svosent = con.prepareStatement(sqlHistoryMark);

	        if (request != null) {
	            String exceptionId = request.getExceptionId();
	            String exceptionDetail = request.getExceptionDetail() != null ? request.getExceptionDetail().trim().toLowerCase() : "";

	            logger.info("Checking condition for exceptionId: " + exceptionId + " and exceptionDetail: " + request.getExceptionDetail());
	            
	            if ("51".equalsIgnoreCase(exceptionId) && exceptionDetail.toLowerCase().contains("order is already in teco status, notification is completed")) {
	                logger.info("Skipping processing for exceptionId 51 with detail: " + exceptionDetail);
	                return executed;  
	            }
	            if (exceptionId != null && exceptionId.equalsIgnoreCase("53")) {
	                String wo = request.getWO();

	                if (wo != null && !wo.isEmpty()) {
	                    pstmt1.setString(1, wo);
	                    pstmt1.executeUpdate();
	                    
	                    svoch.setString(1, request.getWO());
	                    svoch.setString(2, request.getTC_number());
		                ResultSet rs = svoch.executeQuery();
		                
		                String SVO = null;
		                if (rs.next()) {
		                	SVO = rs.getString("SVO_NO");
		                }else {
		                	SVO = null;
		                }
		                
		                if(SVO != null && !SVO.isEmpty()){
		                	
		                	svosent.setString(1, wo);
		                	svosent.setString(2, request.getTC_number());
		                	svosent.setString(3, request.getRFO_NO());
		                	svosent.executeUpdate();
		                	
		                	svomark.setString(1, wo);
		                	svomark.setString(2, request.getTC_number());
		                	svomark.executeUpdate();
		                	
		                	
		                	svomark2.setString(1, wo);
		                	svomark2.executeUpdate();
		                	
		                }
	                    
	                }
	                
	                psDeleteError.setString(1, request.getWO());
	                psDeleteError.setString(2, request.getRFO_NO());
	                psDeleteError.executeUpdate();
	            } else if (exceptionId != null && !exceptionId.equalsIgnoreCase("53")) {
	                if (exceptionDetail.contains("is locked") || exceptionDetail.contains("already being processed")) {
	                    logger.info("Handling locked or already being processed condition for exceptionId: " + exceptionId);
	                    executed = "WO: " + request.getWO() + ", SVO/RFO: " + request.getRFO_NO() + ", Error Code: " + exceptionId + ", Remarks: " + exceptionDetail;

	                    psInsertError.setString(1, request.getWO());
	                    psInsertError.setString(2, request.getRFO_NO());
	                    psInsertError.setString(3, request.getExceptionId());
	                    psInsertError.setString(4, request.getExceptionDetail());
	                    psInsertError.executeUpdate();
	                    
	                    String key = request.getWO() + "-" + request.getRFO_NO();
	                    int attempt = attemptCounts.getOrDefault(key, 0);

	                    if (attempt < 3) {
	                        attempt++;
	                        attemptCounts.put(key, attempt);

	                        try {
	                            Thread.sleep(300000);
	                            
	                            String Flag = request.getFlag();
	                            
	                            if(Flag.equalsIgnoreCase("N")) {
	                            pstmt4.setString(1, request.getWO());
	                            pstmt4.executeUpdate();
	                           
	                            } else if (Flag.equalsIgnoreCase("Y")) {
	                            	pstmt5.setString(1, request.getWO());
	                            	pstmt5.setString(2, request.getTC_number());
	                            	pstmt5.setString(3, request.getRFO_NO());
		                            pstmt5.executeUpdate();
	                            }
	                            
	                            if (attempt >= 3) {
	                                executed = "Failed after 3 attempts: Error Code: " + request.getExceptionId() + ", Remarks: " + request.getExceptionDetail();
	                                TECO_Handling_Controller.addError(executed);
	                                logger.severe(executed);
	                            }
	                        } catch (InterruptedException ie) {
	                            Thread.currentThread().interrupt();
	                            executed = "Thread was interrupted: " + ie.toString();
	                            TECO_Handling_Controller.addError(executed);
	                            logger.severe(executed);
	                            return executed;
	                        } catch (SQLException e) {
	                            executed = "SQL Exception: " + e.toString();
	                            TECO_Handling_Controller.addError(executed);
	                            logger.severe(executed);
	                            return executed;
	                        }
	                    } else {
	                        executed = "Failed after 3 attempts: Error Code: " + request.getExceptionId() + ", Remarks: " + request.getExceptionDetail();
	                        TECO_Handling_Controller.addError(executed);
	                        logger.severe(executed);
	                    }
	                } else {
	                    logger.info("Handling general error condition for exceptionId: " + exceptionId);
	                    executed = "WO: " + request.getWO() + ", SVO/RFO: " + request.getRFO_NO() + ", Error Code: " + exceptionId + ", Remarks: " + exceptionDetail;
	                    
	                    psInsertError.setString(1, request.getWO());
	                    psInsertError.setString(2, request.getRFO_NO());
	                    psInsertError.setString(3, request.getExceptionId());
	                    psInsertError.setString(4, request.getExceptionDetail());
	                    psInsertError.executeUpdate();
	                    
	                 
	                    
	                    String Flag = request.getFlag();
	                    
	                    
	                    if(Flag.equalsIgnoreCase("N")) {
                            pstmt4.setString(1, request.getWO());
                            pstmt4.executeUpdate();
                           
                            } else if (Flag.equalsIgnoreCase("Y")) {
                            	pstmt5.setString(1, request.getWO());
                            	pstmt5.setString(2, request.getTC_number());
                            	pstmt5.setString(3, request.getRFO_NO());
	                            pstmt5.executeUpdate();
                            }
	                    
	                    if(Flag.equalsIgnoreCase("N")) {
	                    pstmt4.setString(1, request.getWO());
                        pstmt4.executeUpdate();
	                    }else if (Flag.equalsIgnoreCase("Y")) {
	                    	pstmt41.setString(1, request.getWO());
	                    	pstmt41.setString(2, request.getWO());
	                        pstmt41.executeUpdate();
	                    }
	                    
	                    Status.setString(1, request.getWO());
	                    ResultSet rs1 = Status.executeQuery();
	                    
	                    psStatus.setString(1, request.getWO());
	                    psStatus.setString(2, request.getWO());
	                    psStatus.setString(3, request.getWO());
	                    ResultSet rs = psStatus.executeQuery();
	                    
	                    
	                    String currentStatus = "";
	                    String prevStatus = "GENERATION";
	                  
	                    if (rs1.next()) {
	                        currentStatus = rs1.getString("STATUS");
	                    }
	                    
	                    if (rs.next()) {
	                        String statusFromAudit = rs.getString("STATUS");
	                        
	                        if (!currentStatus.equals(statusFromAudit)) {
	                            prevStatus = statusFromAudit;
	                        }
	                    }
	                    
	                    pstmt2.setString(1, prevStatus);
	                    pstmt2.setString(2, request.getWO());
	                    pstmt2.executeUpdate();
	                    
	                   
	                }
	            } else {
	                psDeleteError.setString(1, request.getWO());
	                psDeleteError.setString(2, request.getRFO_NO());
	                psDeleteError.executeUpdate();
	            }
	        } else {
	            logger.severe("Request object is null");
	            executed = "Request object is null";
	        }

	    } catch (SQLException e) {
	        executed = "SQL Exception: " + e.toString();
	        TECO_Handling_Controller.addError(executed);
	        logger.severe(executed);
	    } catch (Exception e) {
	        executed = "General Exception: " + e.toString();
	        TECO_Handling_Controller.addError(executed);
	        logger.severe(executed);
	    }

	    return executed;
	}

	
	public ArrayList<INT15_SND> getSVO() throws Exception {
	    executed = "OK";

	    
	    if (this.con == null || this.con.isClosed()) {
	        try {
	            this.con = DataSourceClient.getConnection();
	            if (this.con == null || this.con.isClosed()) {
	                throw new IllegalStateException("Issues connecting to the database.");
	            }
	            logger.info("Established connection to the database.");
	        } catch (SQLException e) {
	            throw new IllegalStateException("Error trying to re-connect to the database.", e);
	        }
	    }

	    ArrayList<INT15_SND> list = new ArrayList<>();

	    String sqlSVO = "SELECT DISTINCT " +
	            "ATH.SVO_NO, " +
	            "W.WO, " +
	            "TO_CHAR(W.COMPLETION_DATE, 'DD-MM-YYYY') AS COMPLETION_DATE, " +
	            "TO_CHAR(W.COMPLETION_DATE, 'HH24:MI:SS') AS COMPLETION_TIME, " +
	            "W.STATUS, " +
	            "W.REOPEN_REASON, " +
	            "W.SOURCE_REF, " +
	            "WT.TASK_CARD, " +
	            "ATH.TRANSACTION_NO, " +
	            "W.SOURCE_TYPE " +
	            "FROM WO W " +
	            "JOIN WO_TASK_CARD WT ON W.WO = WT.WO  JOIN WO_ACTUALS WA ON W.WO = WA.WO " +
	            "JOIN PN_INVENTORY_HISTORY ATH ON W.WO = ATH.WO AND WT.TASK_CARD = ATH.TASK_CARD " +
	            "WHERE W.RFO_NO IS NOT NULL " +
	            "AND WA.INVOICED_FLAG = 'Y' " +
	            "AND ( " +
	            "    (W.STATUS = 'CLOSED' AND (ATH.INTERFACE_TECO_FLAG = 'D' OR ATH.INTERFACE_TECO_FLAG IS NULL)) " +
	            "    OR " +
	            "    (W.STATUS = 'CANCEL' AND (ATH.INTERFACE_TECO_FLAG = 'D' OR ATH.INTERFACE_TECO_FLAG IS NULL)) " +
	            "    OR " +
	            "    (W.STATUS = 'OPEN' AND W.REOPEN_REASON IS NOT NULL AND (ATH.INTERFACE_TECO_FLAG = 'Y' OR ATH.INTERFACE_TECO_FLAG IS NULL)) " +
	            ") " +
	            "AND ATH.INTERFACE_TRANSFER_FLAG IS NULL " +
	            "AND (ATH.TRANSACTION_TYPE = 'REMOVE' OR ATH.TRANSACTION_TYPE = 'INSTALL')";

	    String sqlMark = "UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_FLAG = 'D' WHERE WO = ? AND TASK_CARD = ? AND SVO_NO = ?";
	    String sqlMark2 = "UPDATE WO_TASK_CARD SET SVO_SENT = 'S' WHERE WO = ? AND TASK_CARD = ?";
	    String sqlmarksvo = "UPDATE WO SET SVO_USED = 'Y' WHERE WO = ?";

	    try (PreparedStatement pstmt1 = con.prepareStatement(sqlSVO);
	         PreparedStatement pstmt2 = con.prepareStatement(sqlMark);
	         PreparedStatement pstmt3 = con.prepareStatement(sqlMark2);
	         PreparedStatement pstmt4 = con.prepareStatement(sqlmarksvo);
	         ResultSet rs1 = pstmt1.executeQuery()) {

	        while (rs1.next()) {
	            logger.info("Processing SVO: " + rs1.getString("SVO_NO"));

	            INT15_SND req = new INT15_SND();
	            req.setSAP_number(rs1.getString("SVO_NO"));
	            req.setWO(rs1.getString("WO"));
	            req.setWO_Completion_date(rs1.getString("COMPLETION_DATE") != null ? rs1.getString("COMPLETION_DATE") : "");
	            req.setWO_Completion_time(rs1.getString("COMPLETION_TIME") != null ? rs1.getString("COMPLETION_TIME") : "00:00:00");
	            req.setStatus(rs1.getString("STATUS"));
	            req.setReason_teco(rs1.getString("REOPEN_REASON"));
	            req.setNotification_number(rs1.getString("SOURCE_REF") != null && rs1.getString("SOURCE_TYPE").equals("E4") ? rs1.getString("SOURCE_REF") : "");
	            req.setTC_number(rs1.getString("TASK_CARD") != null ? rs1.getString("TASK_CARD") : "");
	            req.setTransaction(rs1.getString("TRANSACTION_NO") != null ? rs1.getString("TRANSACTION_NO") : "");
	            req.setFlag("Y");

	            list.add(req);

	            // Log parameter values
	            logger.info("Marking SVO_NO: " + rs1.getString("SVO_NO") + " Transaction NO: " + rs1.getString("TRANSACTION_NO"));
	            
	           
	            pstmt2.setString(1, rs1.getString("WO"));
	            pstmt2.setString(2, rs1.getString("TASK_CARD"));
	            pstmt2.setString(3, rs1.getString("SVO_NO"));
	            pstmt2.executeUpdate();
	            
	            pstmt3.setString(1, rs1.getString("WO"));
	            pstmt3.setString(2, rs1.getString("TASK_CARD"));
	            pstmt3.executeUpdate();
	            
	            pstmt4.setString(1, rs1.getString("WO"));
	            pstmt4.executeUpdate();
	        }
	    } catch (SQLException e) {
	        logger.severe("SQLException occurred: " + e.getMessage());
	        e.printStackTrace();
	        executed = e.toString();
	        TECO_Handling_Controller.addError(e.toString());
	        throw new Exception("Issue found", e);
	    }

	    return list;
	}


	
	
	public ArrayList<INT15_SND> getRFO() throws Exception {
	    executed = "OK";

	    // Ensure connection is available
	    if (this.con == null || this.con.isClosed()) {
	        try {
	            this.con = DataSourceClient.getConnection();
	            if (this.con == null || this.con.isClosed()) {
	                throw new IllegalStateException("Issues connecting to the database.");
	            }
	            logger.info("Established connection to the database.");
	        } catch (SQLException e) {
	            throw new IllegalStateException("Error trying to re-connect to the database.", e);
	        }
	    }

	    ArrayList<INT15_SND> list = new ArrayList<>();

	    String sqlRFO ="SELECT DISTINCT " +
                "w.rfo_no, " +
                "w.wo, " +
                "TO_CHAR(w.completion_date, 'DD-MM-YYYY') AS completion_date, " +
                "TO_CHAR(w.completion_date, 'HH24:MI:SS') AS completion_time, " +
                "CASE " +
                "    WHEN w.status = 'CLOSED' " +
                "         AND NOT EXISTS ( " +
                "        SELECT 1 FROM wo_task_card wt_inner WHERE wt_inner.wo = w.wo AND wt_inner.status NOT IN ('CLOSED', 'CANCEL') " +
                "    ) " +
                "         AND ( " +
                "             NOT EXISTS (SELECT 1 FROM wo_task_card wt_inner WHERE wt_inner.wo = w.wo AND wt_inner.svo_sent IS NULL) " +
                "             OR " +
                "             NOT EXISTS (SELECT 1 FROM wo_task_card wt_inner WHERE wt_inner.wo = w.wo AND wt_inner.svo_sent = 'Y') " +
                "         ) THEN 'CLOSED' " +
                "    WHEN w.status = 'CLOSED' " +
                "         AND NOT EXISTS ( " +
                "        SELECT 1 FROM wo_task_card wt_inner WHERE wt_inner.wo = w.wo AND wt_inner.status != 'CANCEL' " +
                "    ) " +
                "         AND ( " +
                "             NOT EXISTS (SELECT 1 FROM wo_task_card wt_inner WHERE wt_inner.wo = w.wo AND wt_inner.svo_sent IS NULL) " +
                "             OR " +
                "             NOT EXISTS (SELECT 1 FROM wo_task_card wt_inner WHERE wt_inner.wo = w.wo AND wt_inner.svo_sent = 'Y') " +
                "         ) THEN 'CANCEL' " +
                "    ELSE w.status " +
                "END AS status, " +
                "w.reopen_reason, " +
                "w.source_ref, " +
                "w.source_type " +
                "FROM wo w " +
                "JOIN wo_task_card wt ON w.wo = wt.wo " +
                "JOIN wo_Actuals wa ON w.wo = wa.wo " +
                "LEFT JOIN pn_inventory_history ath ON w.wo = ath.wo AND wt.task_card = ath.task_card " +
                "WHERE w.rfo_no IS NOT NULL AND SVO_USED IS NULL " +
                "AND ( " +
                "    (w.status = 'CLOSED' AND (w.interface_teco_flag = 'D' OR w.interface_teco_flag IS NULL) AND WA.INVOICED_FLAG = 'Y' " +
                "    AND NOT EXISTS (SELECT 1 FROM wo_task_card wt_inner WHERE wt_inner.wo = w.wo AND wt_inner.status NOT IN ('CLOSED', 'CANCEL')) " +
                "    AND ((ath.svo_no IS NULL OR ath.transaction_type IS NOT NULL) " +
                "    OR (ath.svo_no IS NOT NULL AND ath.interface_transfer_flag = 'D' AND (ATH.TRANSACTION_TYPE = 'REMOVE' OR ATH.TRANSACTION_TYPE = 'INSTALL' ) " +
                "    AND NOT EXISTS (SELECT 1 FROM pn_inventory_history ath_inner WHERE ath_inner.wo = w.wo AND ath_inner.interface_transfer_flag = 'N')))) " +
                ") " +
                "OR " +
                "(w.status = 'CLOSED' AND (w.interface_teco_flag = 'D' OR w.interface_teco_flag IS NULL) AND WA.INVOICED_FLAG IS NULL " +
                "AND NOT EXISTS (SELECT 1 FROM wo_task_card wt_inner WHERE wt_inner.wo = w.wo AND wt_inner.status != 'CANCEL') " +
                "AND ((ath.svo_no IS NULL OR ath.transaction_type IS NOT NULL) " +
                "OR (ath.svo_no IS NOT NULL AND ath.interface_transfer_flag = 'D' AND (ATH.TRANSACTION_TYPE = 'REMOVE' OR ATH.TRANSACTION_TYPE = 'INSTALL' ) " +
                "AND NOT EXISTS (SELECT 1 FROM pn_inventory_history ath_inner WHERE ath_inner.wo = w.wo AND ath_inner.interface_transfer_flag = 'N')))) " +
                "OR " +
                "(w.status = 'OPEN' AND w.reopen_reason IS NOT NULL AND (w.interface_teco_flag = 'Y' OR w.interface_teco_flag IS NULL) " +
                "AND (NOT EXISTS (SELECT 1 FROM wo_task_card wt_inner WHERE wt_inner.wo = w.wo AND wt_inner.status NOT IN ('CLOSED', 'CANCEL'))) " +
                "AND ((ath.svo_no IS NULL OR ath.transaction_type IS NOT NULL) " +
                "OR (ath.svo_no IS NOT NULL AND ath.interface_transfer_flag = 'D' AND (ATH.TRANSACTION_TYPE = 'REMOVE' OR ATH.TRANSACTION_TYPE = 'INSTALL' ) " +
                "AND NOT EXISTS (SELECT 1 FROM pn_inventory_history ath_inner WHERE ath_inner.wo = w.wo AND ath_inner.interface_transfer_flag = 'N'))))";

	    String sqlMark = "UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_FLAG = 'Y' WHERE WO = ?";
	    String sqlMark2 = "UPDATE WO SET INTERFACE_TECO_FLAG = CASE " +
                "WHEN STATUS IN ('CLOSED') THEN 'Y' " +
                "WHEN STATUS = 'OPEN' AND REOPEN_REASON IS NOT NULL THEN 'D' " +
                "END " +
                "WHERE WO = ? " +
                "AND ((STATUS IN ('CLOSED', 'CANCEL')) OR (STATUS = 'OPEN' AND REOPEN_REASON IS NOT NULL)) ";

	    try (PreparedStatement pstmt1 = con.prepareStatement(sqlRFO);
	         PreparedStatement pstmt2 = con.prepareStatement(sqlMark);
	         PreparedStatement pstmt3 = con.prepareStatement(sqlMark2)) {

	        //logger.info("Executing SQL query: " + sqlRFO);
	        
	        try (ResultSet rs1 = pstmt1.executeQuery()) {
	            while (rs1.next()) {
	                logger.info("Processing RFO: " + rs1.getString("RFO_NO"));

	                INT15_SND req = new INT15_SND();

	                req.setSAP_number(rs1.getString("RFO_NO"));
	                req.setWO(rs1.getString("WO"));
	                req.setWO_Completion_date(rs1.getString("COMPLETION_DATE") != null ? rs1.getString("COMPLETION_DATE") : "");
	                req.setWO_Completion_time(rs1.getString("COMPLETION_TIME") != null ? rs1.getString("COMPLETION_TIME") : "");
	                req.setStatus(rs1.getString("STATUS"));
	                if ("OPEN".equals(rs1.getString("STATUS"))) {
	                    req.setReason_teco(rs1.getString("REOPEN_REASON"));
	                } else {
	                    req.setReason_teco("");
	                }
	                req.setNotification_number(rs1.getString("SOURCE_REF") != null &&  rs1.getString("SOURCE_TYPE").equals("E4") ? rs1.getString("SOURCE_REF") : "");
	                req.setFlag("N");

	                list.add(req);

	                // Log parameter values
	                logger.info("Marking WO: " + rs1.getString("WO"));
	                
	                pstmt2.setString(1, rs1.getString("WO"));
	                pstmt2.executeUpdate();
	                
	                pstmt3.setString(1, rs1.getString("WO"));
	                pstmt3.executeUpdate();
	            }
	        }
	    } catch (SQLException e) {
	        logger.severe("SQLException occurred: " + e.getMessage());
	        e.printStackTrace();
	        executed = e.toString();
	        TECO_Handling_Controller.addError(e.toString());
	        throw new Exception("Issue found", e);
	    }

	    return list;
	}
	public String setOpsLine(String opsLine, String email) throws Exception {
	    String Executed = "OK";

	    String query =
	      "INSERT INTO OPS_LINE_EMAIL_MASTER (OPS_LINE, \"EMAIL\") VALUES (?, ?)";

	    PreparedStatement ps = null;

	    try {
	      if (con == null || con.isClosed()) {
	        con = DataSourceClient.getConnection();
	        logger.severe(
	          "The connection was stablished successfully with status: " +
	          String.valueOf(!con.isClosed())
	        );
	      }

	      ps = con.prepareStatement(query);

	      ps.setString(1, opsLine);
	      ps.setString(2, email);

	      ps.executeUpdate();
	    } catch (SQLException sqle) {
	      logger.severe(
	        "A SQLException" +
	        " occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.BAD_REQUEST +
	        "\nmessage: " +
	        sqle.getMessage()
	      );
	      throw new Exception(
	        "A SQLException" +
	        " occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.BAD_REQUEST +
	        "\nmessage: " +
	        sqle.getMessage()
	      );
	    } catch (NullPointerException npe) {
	      logger.severe(
	        "A NullPointerException occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.BAD_REQUEST +
	        "\nmessage: " +
	        npe.getMessage()
	      );
	      throw new Exception(
	        "A NullPointerException occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.BAD_REQUEST +
	        "\nmessage: " +
	        npe.getMessage()
	      );
	    } catch (Exception e) {
	      logger.severe(
	        "An Exception occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.INTERNAL_SERVER_ERROR +
	        "\nmessage: " +
	        e.getMessage()
	      );
	      throw new Exception(
	        "An Exception occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.INTERNAL_SERVER_ERROR +
	        "\nmessage: " +
	        e.getMessage()
	      );
	    } finally {
	      try {
	        if (ps != null && !ps.isClosed()) ps.close();
	      } catch (SQLException e) {
	        logger.severe("An error ocurrer trying to close the statement");
	      }
	    }

	    return Executed;
	  }
	
	public String deleteOpsLine(String opsline) throws Exception{
		String Executed = "OK";
		
		String query = "DELETE OPS_LINE_EMAIL_MASTER where \"OPS_LINE\" = ?";

	    PreparedStatement ps = null;
	    
	    try {
	    	if(con == null || con.isClosed()) {
	    		con = DataSourceClient.getConnection();
	    		logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
	    	}
	    	
	    	ps = con.prepareStatement(query);
	    	
		    ps.setString(1, opsline);
		    
		    ps.executeUpdate();
	    } catch(SQLException sqle) {
	    	logger.severe("A SQLException" + " occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + sqle.getMessage());
	    	throw new Exception("A SQLException" + " occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + sqle.getMessage());
	    } catch(NullPointerException npe) {
	    	logger.severe("A NullPointerException occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + npe.getMessage());
	    	throw new Exception("A NullPointerException occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + npe.getMessage());
	    }catch(Exception e) {
	    	logger.severe("An Exception occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.INTERNAL_SERVER_ERROR + "\nmessage: " + e.getMessage());
	    	throw new Exception("An Exception occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.INTERNAL_SERVER_ERROR + "\nmessage: " + e.getMessage());
	    } finally {
	    	try {
	    		if(ps != null && !ps.isClosed()) ps.close();
	    	} catch(SQLException e) {
	    		logger.severe("Error trying to close the statement");
	    	}
	    }
		
		return Executed;
	}
	
	public String getemailByOpsLine(String opsline) throws Exception{
		ArrayList<String> groups = new ArrayList<String>();
		 String query = "", group = "";
		 
		 if (opsline != null && !opsline.isEmpty()) {
		      query = "Select \\\"EMAIL\\\", OPS_LINE FROM OPS_LINE_EMAIL_MASTER where OPS_LINE = ?";
		    } else {
		      query = " Select \"EMAIL\", OPS_LINE FROM OPS_LINE_EMAIL_MASTER";
		    }
		 
		 PreparedStatement ps = null;
		 
		 try {
			 if(con == null || con.isClosed()) {
				 con = DataSourceClient.getConnection();
				 logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
			 }
			 ps = con.prepareStatement(query);
			 if (opsline != null && !opsline.isEmpty()) {
				 ps.setString(1, opsline);
			 }
			 
			 ResultSet rs = ps.executeQuery();
			 
			 if (rs != null) {
			        while (rs.next()) {
			          groups.add(
			            "OPS_LINE: " + rs.getString(2) + " EMAIL: " + rs.getString(1)
			          );
			        }
			      }
			 rs.close();
		 } catch(SQLException sqle) {
			 logger.severe("A SQLException" + " occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + sqle.getMessage());
			 throw new Exception("A SQLException" + " occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + sqle.getMessage());
		 } catch(NullPointerException npe) {
			 logger.severe("A NullPointerException occurred executiong the query to get the location site capacity. " + "\n error:" + ErrorType.BAD_REQUEST + "\n message: " + npe.getMessage());
			 throw new Exception("A NullPointerException occurred executiong the query to get the location site capacity. " + "\n error:" + ErrorType.BAD_REQUEST + "\n message: " + npe.getMessage());
		 } catch(Exception e) {
		    	logger.severe("An Exception occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.INTERNAL_SERVER_ERROR + "\nmessage: " + e.getMessage());
		    	throw new Exception("An Exception occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.INTERNAL_SERVER_ERROR + "\nmessage: " + e.getMessage());
		 } finally {
			 try {
				 if(ps != null && !ps.isClosed()) ps.close();
			 } catch(SQLException e) {
				 logger.severe("Error trying to close the statement");
			 }
		 } 
		
		 for (String g : groups) {
		      group = group + g + "\n";
		    }
		
		return group;
	}
	
	public String getemailByOnlyOpsLine(String opsLine) {
	    String email = "ERROR";

	    String query =
	      " Select \"EMAIL\", cost_centre FROM OPS_LINE_EMAIL_MASTER where OPS_LINE = ?";

	    PreparedStatement ps = null;
	    
	    try {
		      if (con == null || con.isClosed()) {
		        con = DataSourceClient.getConnection();
		        logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
		      }

		      ps = con.prepareStatement(query);
		      if (opsLine != null && !opsLine.isEmpty()) {
		        ps.setString(1, opsLine);
		      }

		      ResultSet rs = ps.executeQuery();

		      if (rs != null) {
		        while (rs.next()) {
		          email = rs.getString(1);
		        }
		      }
		      rs.close();
		    } catch (Exception e) {
		      email = "ERROR";
		    } finally {
		      try {
		        if (ps != null && !ps.isClosed()) ps.close();
		      } catch (SQLException e) {
		        logger.severe("Error trying to close the statement");
		      }
		    }
	    
	    return email;
	}
	
	  public OpsLineEmail getOpsLineStaffName(String requisition) {
			String query = "";
			OpsLineEmail OpsLineEmail = new OpsLineEmail();

			query =
			  "SELECT rm.RELATION_CODE, rm.NAME,w.ops_line, r.INTERFACE_TRANSFERRED_DATE_ESD \r\n" +
			  "FROM WO w, REQUISITION_HEADER r, relation_master rm \r\n" +
			  "WHERE r.requisition = ? AND w.wo = r.wo AND r.created_by = rm.relation_code";

			PreparedStatement ps = null;

			try {
			  if (con == null || con.isClosed()) {
				con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
			  }

			  ps = con.prepareStatement(query);

			  ps.setString(1, requisition);

			  ResultSet rs = ps.executeQuery();

			  if (rs != null) {
				while (rs.next()) {
				  if (rs.getString(1) != null && !rs.getString(1).isEmpty()) {
					OpsLineEmail.setRelationCode(rs.getString(1));
				  } else {
					OpsLineEmail.setRelationCode("");
				  }

				  if (rs.getString(2) != null && !rs.getString(2).isEmpty()) {
					OpsLineEmail.setName(rs.getString(2));
				  } else {
					OpsLineEmail.setName("");
				  }

				  if (rs.getString(3) != null && !rs.getString(3).isEmpty()) {
					OpsLineEmail.setOpsLine(rs.getString(3));
				  } else {
					OpsLineEmail.setOpsLine("");
				  }

				  OpsLineEmail.setEmail(
					getemailByOnlyOpsLine(OpsLineEmail.getOpsLine())
				  );

				  if (rs.getString(4) != null && !rs.getString(4).isEmpty()) {
					OpsLineEmail.setFlag(rs.getString(4));
				  } else {
					OpsLineEmail.setFlag("");
				  }
				}
			  }
			  rs.close();
			} catch (Exception e) {
			  logger.severe(e.toString());
			  OpsLineEmail.setOpsLine("");
			  OpsLineEmail.setName("");
			  OpsLineEmail.setRelationCode("");
			  OpsLineEmail.setFlag("");
			} finally {
			  try {
				if (ps != null && !ps.isClosed()) ps.close();
			  } catch (SQLException e) {
				logger.severe("Error trying to close the statement");
			  }
			}

			return OpsLineEmail;
		  }
	  
	  public boolean lockAvailable(String notificationType) {
			InterfaceLockMaster lock;
			try {
				lock = em
						.createQuery("SELECT i FROM InterfaceLockMaster i WHERE i.interfaceType = :type", InterfaceLockMaster.class)
						.setParameter("type", notificationType)
						.getSingleResult();
				em.refresh(lock);
			} catch (NoResultException e) {
				lock = new InterfaceLockMaster();
				lock.setInterfaceType(notificationType);
				lock.setLocked(new BigDecimal(0)); 
				insertData(lock);
				return true;
			}

			if (lock.getLocked().intValue() == 1) {
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime lockTime = LocalDateTime.ofInstant(lock.getLockedDate().toInstant(), ZoneId.systemDefault());
				Duration duration = Duration.between(lockTime, now);
				if (duration.getSeconds() >= lock.getMaxLock().longValue()) {
					lock.setLocked(new BigDecimal(0)); 
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

		  private <T> void insertData(T data) {
			try {
			  if (!em.getTransaction().isActive()) em.getTransaction().begin();
			  em.merge(data);
			  em.getTransaction().commit();
			} catch (Exception e) {
			  logger.severe(e.toString());
			}
		  }

		  public void lockTable(String notificationType) {
			em.getTransaction().begin();
			InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type",InterfaceLockMaster.class)
			  .setParameter("type", notificationType)
			  .getSingleResult();
			lock.setLocked(new BigDecimal(1));    lock.setLockedDate(
			  Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
			);
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
			InterfaceLockMaster lock = em
			  .createQuery(
				"SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type",
				InterfaceLockMaster.class
			  )
			  .setParameter("type", notificationType)
			  .getSingleResult();
			lock.setLocked(new BigDecimal(0));
			lock.setUnlockedDate(
			  Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
			);

			em.merge(lock);
			em.getTransaction().commit();
		  }

}
