package trax.aero.data;


import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBException;

import trax.aero.controller.ManHours_Item_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.INT14_18_SND;
import trax.aero.pojo.INT14_18_TRAX;
import trax.aero.pojo.Operation_SND;
import trax.aero.pojo.Operation_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.ErrorType;

public class ManHours_Item_Data {
	
	EntityManagerFactory factory;
	EntityManager em;
	String executed;
	private Connection con;
	
	final String MaxRecord = System.getProperty("ManHourItem_MaxRecords");
	Logger logger = LogManager.getLogger("ManHourItem");
	
	public ManHours_Item_Data(String mark) {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " +String.valueOf(!this.con.isClosed()));
		}
	}catch(SQLException e) {
		logger.severe("An error ocurred getting the status of the connection");
		ManHours_Item_Controller.addError(e.toString());
	} catch (CustomizeHandledException e1) {
		ManHours_Item_Controller.addError(e1.toString());
	} catch (Exception e) {
		ManHours_Item_Controller.addError(e.toString());
	}
}
	
	public ManHours_Item_Data() {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!this.con.isClosed()));
			}
		}catch (SQLException e) {
		      logger.severe("An error occured getting the status of the connection");
		      ManHours_Item_Controller.addError(e.toString());
		    } catch (CustomizeHandledException e1) {
		    	ManHours_Item_Controller.addError(e1.toString());
		    } catch (Exception e) {
		    	ManHours_Item_Controller.addError(e.toString());
		    }
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		em = factory.createEntityManager();
	}
	
	public Connection getCon() {
		return con;
	}
	
	public String markSendData() throws JAXBException
	{
	  INT14_18_TRAX request = new INT14_18_TRAX();
	  try {
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
	
	public String markTransaction(INT14_18_TRAX request) {
	    executed = "OK";
	    
	    String sqlDate = "UPDATE WO SET INTERFACE_ESD_TRANSFERRED_DATE = SYSDATE WHERE WO = ?";
	    
	    String sqlInsertError = "INSERT INTO interface_audit (TRANSACTION, TRANSACTION_TYPE, TRANSACTION_OBJECT, TRANSACTION_DATE, CREATED_BY, MODIFIED_BY, EXCEPTION_ID, EXCEPTION_BY_TRAX, EXCEPTION_DETAIL, EXCEPTION_CLASS_TRAX, CREATED_DATE, MODIFIED_DATE) "
	            + "VALUES (?, 'ERROR', 'I31', sysdate, 'TRAX_IFACE', 'TRAX_IFACE', ?, 'Y', ?, 'ManHours_Item I_31', sysdate, sysdate)";
	    
	    String sqlDeleteError = "DELETE FROM interface_audit WHERE TRANSACTION = ? ";
	    
	    String sqlunMark = "UPDATE WO_TASK_CARD SET INTERFACE_SAP_TRANSFERRED_FLAG = null WHERE WO = ? and TASK_CARD = ?";
	    
	    
	    
	    try (PreparedStatement pstmt1 = con.prepareStatement(sqlDate);
	         PreparedStatement psInsertError = con.prepareStatement(sqlInsertError);
	         PreparedStatement psDeleteError = con.prepareStatement(sqlDeleteError);
	         PreparedStatement ps1 = con.prepareStatement(sqlunMark)){
	        
	    	for(Operation_TRAX o : request.getOperation()) {
	        if (request != null) {
	            if(request.getRFO() != null && !request.getRFO().isEmpty()) {
	                pstmt1.setString(1, request.getWO_number());
	                pstmt1.executeUpdate();
	            }
	            
	            String errorCode = request.getError_code();
	            if (errorCode != null && !errorCode.equalsIgnoreCase("53") &&
	            	    (request.getRemarks().toLowerCase().contains("is locked by".toLowerCase()) ||
	            	     request.getRemarks().toLowerCase().contains("already being processed".toLowerCase()))) {
	                executed = "Request SAP Order Number: " + request.getRFO() + ", Error Code: " + errorCode + ", Remarks: " + request.getRemarks() + ", WO: " + request.getWO_number();
	                ManHours_Item_Controller.addError(executed);
	                
	                psDeleteError.setString(1, request.getWO_number());
	                psDeleteError.executeUpdate();
	                
	                psInsertError.setString(1, request.getWO_number());
	                psInsertError.setString(2, errorCode);
	                psInsertError.setString(3, request.getRemarks());
	                psInsertError.executeUpdate();
	                
	                String key = request.getWO_number() + "-" + request.getRFO();
	                int attempt = attemptCounts.getOrDefault(key, 0);
	                
	                if (attempt < 3) {
	                    attempt++;
	                    attemptCounts.put(key, attempt);

	                    try {
	                        Thread.sleep(30000); 
	                        logger.info("TASK_CARD: " + o.getTASK_CARD() + " WO: " + request.getWO_number());
	                        ps1.setString(1, request.getWO_number());
	                        ps1.setString(2, o.getTASK_CARD());
	                        ps1.executeUpdate();

	                        if (attempt > 3) {
	                            executed = "Failed after 3 attempts: Error Code: " + errorCode + ", Remarks: " + request.getRemarks();
	                            ManHours_Item_Controller.addError(executed);
	                            logger.severe(executed);
	                        }
	                    } catch (InterruptedException ie) {
	                        Thread.currentThread().interrupt();
	                        executed = "Thread was interrupted: " + ie.toString();
	                        ManHours_Item_Controller.addError(executed);
	                        logger.severe(executed);
	                        return executed;
	                    } catch (SQLException e) {
	                        executed = e.toString();
	                        ManHours_Item_Controller.addError(executed);
	                        logger.severe(executed);
	                        return executed;
	                    }
	                } else {
	                    executed = "Failed after 3 attempts: Error Code: " + errorCode + ", Remarks: " + request.getRemarks();
	                    ManHours_Item_Controller.addError(executed);
	                    logger.severe(executed);
	                }
	            } else {
	                psDeleteError.setString(1, request.getWO_number());
	                psDeleteError.executeUpdate();
	            }
	        }
	    	}
	    } catch (SQLException e) {
	        executed = e.toString();
	        ManHours_Item_Controller.addError(executed);
	        logger.severe(executed);
	    } 
	    
	    return executed;
	}
	
	public ArrayList<INT14_18_SND> getManHRIT() throws Exception {
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

	    ArrayList<INT14_18_SND> list = new ArrayList<>();
	    Set<String> processedCombinations = new HashSet<>();
	    String sqlManHRIT = "SELECT * FROM (SELECT DISTINCT W.WO, W.RFO_NO, WTI.OPS_NO, WT.TASK_CARD, WT.TASK_CARD_CATEGORY, WT.TASK_CARD_DESCRIPTION, WT.NON_ROUTINE, WT.BILLABLE_HOURS, WA.WO_ACTUAL_TRANSACTION, " +
	                        "RANK() OVER (ORDER BY WA.WO_ACTUAL_TRANSACTION DESC) AS rnk FROM WO W, WO_TASK_CARD WT, WO_TASK_CARD_ITEM WTI, WO_ACTUALS WA " +
	                        "WHERE W.SOURCE_TYPE NOT IN ('E8', 'X3') AND W.RFO_NO IS NOT NULL AND WTI.OPS_NO IS NOT NULL AND W.WO = WT.WO AND W.WO = WTI.WO AND W.WO = WA.WO AND WT.TASK_CARD = WTI.TASK_CARD " +
	                        "AND WT.STATUS = 'CLOSED' AND ( WT.INTERFACE_SAP_TRANSFERRED_FLAG IS NULL OR WT.INTERFACE_SAP_TRANSFERRED_FLAG = '3')) WHERE rnk = 1";

	    String sqlAction = "SELECT CASE " +
                "WHEN wt.non_routine = 'Y' THEN " +
                "(SELECT DISTINCT " +
                "LISTAGG(single_record, ' | ') WITHIN GROUP(ORDER BY rn) AS single_string " +
                "FROM (" +
                "SELECT DISTINCT " +
                "CASE " +
                "WHEN wti.dual_inspected_by IS NOT NULL THEN " +
                "'ITEM ' || wti.task_card_item || ': ' || wka1.work_accomplished || ' BY: ' || ec1.reference || ' DATE: ' || " +
                "to_char(wka1.created_date, 'DD-MON-YY HH24:MI') || ' DUALINSP: ' || wka2.work_accomplished || ' BY: ' || " +
                "ec2.reference || ' DATE: ' || to_char(wka2.created_date, 'DD-MON-YY HH24:MI') " +
                "ELSE " +
                "'ITEM ' || wti.task_card_item || ': ' || wka1.work_accomplished || ' BY: ' || ec1.reference || ' DATE: ' || " +
                "to_char(wka1.created_date, 'DD-MON-YY HH24:MI') " +
                "END AS single_record, " +
                "ROW_NUMBER() OVER(PARTITION BY wti.task_card_item ORDER BY wa.transaction_date DESC) AS rn " +
                "FROM wo_task_card_item wti " +
                "JOIN wo_task_card wt ON wti.wo = wt.wo AND wti.task_card = wt.task_card " +
                "JOIN (SELECT wo, task_card, task_card_item, work_accomplished_line, work_accomplished, created_by, created_date " +
                "FROM (SELECT wo, task_card, task_card_item, work_accomplished_line, work_accomplished, created_by, created_date, " +
                "ROW_NUMBER() OVER(PARTITION BY wo, task_card, task_card_item, created_by ORDER BY work_accomplished_line DESC) AS rn " +
                "FROM wo_task_card_item_wrk_acmplshd) WHERE rn = 1) wka1 " +
                "ON wti.wo = wka1.wo AND wti.task_card = wka1.task_card AND wti.task_card_item = wka1.task_card_item " +
                "AND wti.inspected_by = wka1.created_by " +
                "JOIN (SELECT wo, task_card, task_card_item, work_accomplished_line, work_accomplished, created_by, created_date " +
                "FROM (SELECT wo, task_card, task_card_item, work_accomplished_line, work_accomplished, created_by, created_date, " +
                "ROW_NUMBER() OVER(PARTITION BY wo, task_card, task_card_item, created_by ORDER BY work_accomplished_line DESC) AS rn " +
                "FROM wo_task_card_item_wrk_acmplshd) WHERE rn = 1) wka2 " +
                "ON wti.wo = wka2.wo AND wti.task_card = wka2.task_card AND wti.task_card_item = wka2.task_card_item " +
                "AND wti.dual_inspected_by = wka2.created_by " +
                "LEFT JOIN employee_control ec1 ON ec1.employee = wka1.created_by " +
                "LEFT JOIN employee_control ec2 ON ec2.employee = wka2.created_by " +
                "JOIN wo_actuals wa ON wa.wo = wti.wo " +
                "WHERE wti.wo = ? AND wti.task_card = ? AND wt.non_routine = 'Y' AND wa.employee IS NOT NULL) a " +
                "WHERE rn = 1) " +
                "WHEN wt.non_routine = 'N' THEN " +
                "(SELECT 'Refer to TRAXWO: ' || wt.wo || ' TASK CARD: ' || wt.task_card || ' For Details LICENSE: ' || " +
                "ec.reference || ' DATE: ' || to_char(wa.modified_date, 'DD-MON-YY HH24:MI') AS single_string " +
                "FROM wo_task_card_item wti " +
                "JOIN wo_task_card wt ON wti.wo = wt.wo AND wti.task_card = wt.task_card " +
                "JOIN wo_actuals wa ON wa.wo = wti.wo " +
                "LEFT JOIN (SELECT wo, task_card, task_card_item, work_accomplished_line, work_accomplished, created_by, created_date " +
                "FROM (SELECT wo, task_card, task_card_item, work_accomplished_line, work_accomplished, created_by, created_date, " +
                "ROW_NUMBER() OVER(PARTITION BY wo, task_card, task_card_item, created_by ORDER BY work_accomplished_line DESC) AS rn " +
                "FROM wo_task_card_item_wrk_acmplshd) WHERE rn = 1) wka " +
                "ON wti.wo = wka.wo AND wti.task_card = wka.task_card AND wti.task_card_item = wka.task_card_item " +
                "AND wti.inspected_by = wka.created_by " +
                "LEFT JOIN employee_control ec ON ec.employee = wka.created_by " +
                "WHERE wti.wo = ? AND wti.task_card = ? AND wt.non_routine = 'N' AND wa.employee IS NOT NULL " +
                "AND wa.transaction_date = (SELECT MAX(transaction_date) FROM wo_actuals wa2 WHERE wa2.wo = wti.wo) " +
                "FETCH FIRST 1 ROW ONLY) " +
                "END AS single_string " +
                "FROM wo_task_card wt " +
                "WHERE wt.wo = ? AND wt.task_card = ? AND (wt.non_routine = 'Y' OR wt.non_routine = 'N')";
   


	    String sqlmarking = "SELECT INVOICED_FLAG FROM WO_ACTUALS WHERE WO = ? AND TASK_CARD = ?"; 

	    String sqlMark = "UPDATE WO_TASK_CARD SET INTERFACE_SAP_TRANSFERRED_FLAG = 'Y' WHERE WO = ? AND TASK_CARD = ?";
	    
	   // String sqlMark2 = "UPDATE WO_ACTUALS SET INTERFACE_ESD_TRANSFERRED_FLAG = 'Y' WHERE WO = ?";

	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        sqlManHRIT = "SELECT * FROM (" + sqlManHRIT;
	    }

	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        sqlManHRIT = sqlManHRIT + " ) WHERE ROWNUM <= ?";
	    }

	    try (
	        PreparedStatement pstmt1 = con.prepareStatement(sqlManHRIT);
	        PreparedStatement pstmt2 = con.prepareStatement(sqlAction);
	        PreparedStatement pstmt3 = con.prepareStatement(sqlMark);
	        PreparedStatement pstmt4 = con.prepareStatement(sqlmarking);
	    	//PreparedStatement pstmt5 = con.prepareStatement(sqlMark2);
	    ) {
	        if (MaxRecord != null && !MaxRecord.isEmpty()) {
	            pstmt1.setString(1, MaxRecord);
	        }

	        try (ResultSet rs1 = pstmt1.executeQuery()) {
	            String currentWO = null;
	            INT14_18_SND currentReq = null;

	            while (rs1.next()) {
	                String wo = rs1.getString(1);
	                String taskCard = rs1.getString(4);
	                String combinationKey = wo + "-" + taskCard;

	                // Skip if this combination has already been processed
	                if (processedCombinations.contains(combinationKey)) {
	                    continue;
	                }

	                // If we're starting a new work order, save the previous one
	                if (currentWO == null || !currentWO.equals(wo)) {
	                    if (currentReq != null) {
	                        list.add(currentReq);
	                    }
	                    currentWO = wo;
	                    currentReq = new INT14_18_SND();
	                    currentReq.setWO(wo != null ? wo : "");
	                    currentReq.setRFO(rs1.getString(2) != null ? rs1.getString(2) : "");
	                }

	                Operation_SND Inbound = new Operation_SND();
	                Inbound.setOPS_NO(rs1.getString(3) != null ? rs1.getString(3) : "");
	                Inbound.setTASK_CARD(taskCard != null ? taskCard : "");
	                Inbound.setTASK_CARD_CATEGORY(rs1.getString(5) != null ? rs1.getString(5) : "");
	                Inbound.setTASK_CARD_DESCRIPTION(rs1.getString(6) != null ? rs1.getString(6) : "");

	               
	                pstmt4.setString(1, currentReq.getWO());
	                pstmt4.setString(2, Inbound.getTASK_CARD());
	                
	                boolean shouldExecutePstmt2 = true;
	                
	                try (ResultSet rs4 = pstmt4.executeQuery()) {
	                    if (rs4.next() && "Y".equals(rs4.getString(1))) {
	                        Inbound.setINV_HRS(rs1.getString(8) != null ? rs1.getString(8) : "");
	                        shouldExecutePstmt2 = false;
	                        
	                    }
	                }

	                if (shouldExecutePstmt2) {
	                pstmt2.setString(1, currentReq.getWO());
	                pstmt2.setString(2, Inbound.getTASK_CARD());
	                pstmt2.setString(3, currentReq.getWO());
	                pstmt2.setString(4, Inbound.getTASK_CARD());
	                pstmt2.setString(5, currentReq.getWO());
	                pstmt2.setString(6, Inbound.getTASK_CARD());
	                try (ResultSet rs2 = pstmt2.executeQuery()) {
	                    if (rs2.next()) {
	                        Inbound.setWORK_ACCOMPLISHED(rs2.getString(1) != null ? rs2.getString(1) : "");
	                    }
	                }
	                }
	                currentReq.getOperation().add(Inbound);

	                pstmt3.setString(1, currentReq.getWO());
	                pstmt3.setString(2, rs1.getString(4));
	                pstmt3.executeUpdate();
	                
	               // pstmt5.setString(1, currentReq.getWO());
	               // pstmt5.executeUpdate();

	                // Mark this combination as processed
	                processedCombinations.add(combinationKey);
	            }

	            // Add the last work order
	            if (currentReq != null) {
	                list.add(currentReq);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        executed = e.toString();
	        ManHours_Item_Controller.addError(e.toString());
	        logger.severe(executed);
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
