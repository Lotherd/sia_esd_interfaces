package trax.aero.data;

import java.io.StringReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.eclipse.yasson.internal.Unmarshaller;

import trax.aero.controller.Import_TC_MHR_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.INT6_SND;
import trax.aero.pojo.INT6_TRAX;
import trax.aero.pojo.OperationAudit;
import trax.aero.pojo.OperationSND;
import trax.aero.pojo.OrderTRAX;
import trax.aero.pojo.OperationTRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.OrderAudit;
import trax.aero.pojo.OrderSND;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.ErrorType;

public class Import_TC_MHR_Data {
	
	EntityManagerFactory factory;
	  EntityManager em;
	  String executed;
	  private Connection con;

	  final String MaxRecord = System.getProperty("Import_TC_MHR_MaxRecord");
	  Logger logger = LogManager.getLogger("ImportTC_MHR");

	  public Import_TC_MHR_Data(String mark) {
	    try {
	      if (this.con == null || this.con.isClosed()) {
	        this.con = DataSourceClient.getConnection();
	        logger.info(
	          "The connection was stabliched successfully with status: " +
	          String.valueOf(!this.con.isClosed())
	        );
	      }
	    } catch (SQLException e) {
	      logger.severe("An error occured getting the status of the connection");
	      Import_TC_MHR_Controller.addError(e.toString());
	    } catch (CustomizeHandledException e1) {
	      Import_TC_MHR_Controller.addError(e1.toString());
	    } catch (Exception e) {
	      Import_TC_MHR_Controller.addError(e.toString());
	    }
	  }

	  public Import_TC_MHR_Data() {
	    try {
	      if (this.con == null || this.con.isClosed()) {
	        this.con = DataSourceClient.getConnection();
	        logger.info(
	          "The connection was stablished successfully with status: " +
	          String.valueOf(!this.con.isClosed())
	        );
	      }
	    } catch (SQLException e) {
	      logger.severe("An error occured getting the status of the connection");
	      Import_TC_MHR_Controller.addError(e.toString());
	    } catch (CustomizeHandledException e1) {
	      Import_TC_MHR_Controller.addError(e1.toString());
	    } catch (Exception e) {
	      Import_TC_MHR_Controller.addError(e.toString());
	    }

	    factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
	    em = factory.createEntityManager();
	  }

	  public Connection getCon() {
	    return con;
	  }
	  
	  public String markSendData() throws JAXBException
		{
		  INT6_TRAX request = new INT6_TRAX();
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
	  
	  public String markTransaction(INT6_TRAX request) {
		    executed = "OK";

		    String sqlDate = "UPDATE WO_TASK_CARD SET INTERFACE_TRANSFERRED_DATE = sysdate, INTERFACE_FLAG = null WHERE TASK_CARD = ? AND WO = ?";
		    String sqlOPS = "UPDATE WO_TASK_CARD_ITEM SET OPS_NO = ? WHERE TASK_CARD = ? AND WO = ?";
		    String sqlOPS_audit = "UPDATE WO_TASK_CARD_ITEM_ADUIT SET OPS_NO = ? WHERE TASK_CARD = ? AND WO = ?";
		    
		    String sqlunMark = "UPDATE WO_TASK_CARD SET INTERFACE_TRANSFERRED_DATE = null WHERE TASK_CARD = ? AND WO = ?";
		    String sqlunMark2 = "UPDATE WO_TASK_CARD_ADUIT SET INTERFACE_SAP_TRANSFERRED_DATE = null WHERE TASK_CARD = ? AND WO = ? AND TRANSACTION_TYPE ='DELETE'";
		    
		    String sqlInsertError = "INSERT INTO interface_audit (TRANSACTION, TRANSACTION_TYPE, ORDER_NUMBER, EO, TRANSACTION_OBJECT, TRANSACTION_DATE, CREATED_BY, MODIFIED_BY, EXCEPTION_ID, EXCEPTION_BY_TRAX, EXCEPTION_DETAIL, EXCEPTION_CLASS_TRAX, CREATED_DATE, MODIFIED_DATE) "
		                          + "SELECT seq_interface_audit.NEXTVAL, 'ERROR', ?, ?, 'I06', sysdate, 'TRAX_IFACE', 'TRAX_IFACE', ?, 'Y', ?, 'Import_TC_MHR I_06', sysdate, sysdate FROM dual";
		    
		    String sqlDeleteError = "DELETE FROM interface_audit WHERE ORDER_NUMBER = ? AND EO = ?";
		    

		    try (PreparedStatement pstmt2 = con.prepareStatement(sqlDate);
		         PreparedStatement pstmt3 = con.prepareStatement(sqlOPS);
		         PreparedStatement pstmt4 = con.prepareStatement(sqlOPS_audit);    
		         PreparedStatement ps1 = con.prepareStatement(sqlunMark);
		         PreparedStatement ps2 = con.prepareStatement(sqlunMark2);
		         PreparedStatement psInsertError = con.prepareStatement(sqlInsertError);
		         PreparedStatement psDeleteError = con.prepareStatement(sqlDeleteError)) {

		        for (OrderTRAX r : request.getOrder()) {
		            for (OperationTRAX o : r.getOperations()) {
		                if (o != null) {
		                	if (r.getExceptionId().equalsIgnoreCase("53")){
		                    pstmt2.setString(1, o.getTaskCard());
		                    pstmt2.setString(2, r.getWo());
		                    pstmt2.executeUpdate();

		                    if (o.getOpsNo() != null && !o.getOpsNo().isEmpty()) {
		                        pstmt3.setString(1, o.getOpsNo());
		                        pstmt3.setString(2, o.getTaskCard());
		                        pstmt3.setString(3, r.getWo());
		                        pstmt3.executeUpdate();
		                    }

		                    if (o.getOpsNo() != null && !o.getOpsNo().isEmpty()) {
		                        pstmt4.setString(1, o.getOpsNo());
		                        pstmt4.setString(2, o.getTaskCard());
		                        pstmt4.setString(3, r.getWo());
		                        pstmt4.executeUpdate();
		                    }
		                    
		                    psDeleteError.setString(1, r.getWo());
	                        psDeleteError.setString(2, o.getTaskCard());
	                        psDeleteError.executeUpdate();
		                }
		                    if (!r.getExceptionId().equalsIgnoreCase("53") &&
		                    	    (r.getExceptionDetail().toLowerCase().contains("is locked".toLowerCase()) ||
		                    	     r.getExceptionDetail().toLowerCase().contains("already being processed".toLowerCase()))){
		                        executed = "Request SAP Order Number: " + r.getRfoNo() + ", Error Code: " + r.getExceptionId() + ", Remarks: " + r.getExceptionDetail() + ", Operation Number: " + o.getOpsNo();
		                        Import_TC_MHR_Controller.addError(executed);

		                        
		                       // psDeleteError.setString(1, r.getWo());
		                       // psDeleteError.executeUpdate();
		                        
		                        psInsertError.setString(1, r.getWo());
		                        psInsertError.setString(2, o.getTaskCard());
		                        psInsertError.setString(3, r.getExceptionId());
		                        psInsertError.setString(4, r.getExceptionDetail());
		                        psInsertError.executeUpdate();
		                        
		                        String key = r.getWo() + "-" + o.getTaskCard();
	                            int attempt = attemptCounts.getOrDefault(key, 0);

	                            if (attempt < 3) {
	                                attempt++;
	                                attemptCounts.put(key, attempt);

		                            try {
		                                Thread.sleep(300000); 

		                                ps1.setString(1, o.getTaskCard());
		                                ps1.setString(2, r.getWo());
		                                ps1.executeUpdate();

		                                ps2.setString(1, o.getTaskCard());
		                                ps2.setString(2, r.getWo());
		                                ps2.executeUpdate();

		                                if (attempt > 3) {
	                                        executed = "Failed after 3 attempts: Error Code: " + r.getExceptionId() + ", Remarks: " + r.getExceptionDetail();
	                                        Import_TC_MHR_Controller.addError(executed);
	                                        logger.severe(executed);
	                                    }
		                            } catch (InterruptedException ie) {
		                                Thread.currentThread().interrupt();
		                                executed = "Thread was interrupted: " + ie.toString();
		                                Import_TC_MHR_Controller.addError(executed);
		                                logger.severe(executed);
		                                return executed;
		                            } catch (SQLException e) {
		                                executed = e.toString();
		                                Import_TC_MHR_Controller.addError(executed);
		                                logger.severe(executed);
		                                return executed;
		                            }
		                        } else {
		                            executed = "Failed after 3 attempts: Error Code: " + r.getExceptionId() + ", Remarks: " + r.getExceptionDetail();
		                            Import_TC_MHR_Controller.addError(executed);
		                            logger.severe(executed);
		                        }
		                    } else {
		                        psDeleteError.setString(1, r.getWo());
		                        psDeleteError.setString(2, o.getTaskCard());
		                        psDeleteError.executeUpdate();
		                    }
		                }
		            }
		        }
		    } catch (SQLException e) {
		        executed = e.toString();
		        Import_TC_MHR_Controller.addError(e.toString());
		        logger.severe(e.toString());
		    }
		    return executed;
		}



	  public ArrayList<INT6_SND> getTaskCards() throws Exception{
	    executed = "OK";

	    ArrayList<INT6_SND> list = new ArrayList<INT6_SND>();
	    HashMap<String, OrderSND> orderMap = new HashMap<>();
	    ArrayList<OperationSND> oplist = new ArrayList<OperationSND>();
	    ArrayList<OrderSND> orlist = new ArrayList<OrderSND>();

	    String sqlTaskCard = "SELECT REFERENCE_TASK_CARD, TASK_CARD_DESCRIPTION, PRIORITY, WO, TASK_CARD, " +
	                    "(SELECT W.STATUS FROM WO W WHERE W.WO = WO_TASK_CARD.WO) AS STATUS, " +
	                    "(SELECT W.RFO_NO FROM WO W WHERE W.WO = WO_TASK_CARD.WO AND W.MODULE = 'SHOP' " +
	                    "AND (WO_TASK_CARD.non_routine = 'N' OR WO_TASK_CARD.non_routine = 'Y' OR WO_TASK_CARD.non_routine IS NULL) " +
	                    "AND W.rfo_no IS NOT NULL) AS ESD_RFO, TASK_CARD_CATEGORY, " +
	                    "(SELECT COALESCE(SUM(NVL(man_hours, 0) * NVL(man_require, 0)), 0) + " +
	                    "COALESCE(SUM(NVL(inspector_man_hours, 0) * NVL(inspector_man_require, 0)), 0) " +
	                    "FROM WO_task_card_item WHERE TASK_CARD = WO_TASK_CARD.TASK_CARD AND WO = WO_TASK_CARD.WO) AS Total_Hours, STATUS as TASK_CARD_STATUS " +
	                    "FROM WO_TASK_CARD " +
	                    "WHERE (INTERFACE_TRANSFERRED_DATE IS NULL " +
	                    "OR (MODIFIED_DATE > INTERFACE_TRANSFERRED_DATE AND " +
	                    "(TASK_CARD_DESCRIPTION <> TASK_CARD_DESCRIPTION_OLD OR TASK_CARD_CATEGORY <> TASK_CARD_CATEGORY_OLD OR (STATUS = 'CANCEL' AND STATUS <> STATUS_OLD) OR " +
	                    "(SELECT COALESCE(SUM(NVL(man_hours, 0) * NVL(man_require, 0)), 0) + " +
	                    "COALESCE(SUM(NVL(inspector_man_hours, 0) * NVL(inspector_man_require, 0)), 0) " +
	                    "FROM WO_task_card_item WHERE TASK_CARD = WO_TASK_CARD.TASK_CARD AND WO = WO_TASK_CARD.WO) <> TASK_CARD_HOURS_OLD))) " +
	                    "AND (1 = (SELECT COUNT(*) FROM WO W WHERE W.WO = WO_TASK_CARD.WO " +
	                    "AND W.MODULE = 'SHOP' AND W.RFO_NO IS NOT NULL " +
	                    "AND (WO_TASK_CARD.non_routine = 'N' OR WO_TASK_CARD.non_routine = 'Y' OR WO_TASK_CARD.non_routine IS NULL))) " +
	                    "AND (non_routine = 'N' OR non_routine = 'Y' OR non_routine IS NULL) AND STATUS != 'CLOSED' " +
	                    "AND EXISTS (" +
	                    "SELECT 1 " +
	                    "FROM WO W " +
	                    "WHERE W.WO = WO_TASK_CARD.WO " +
	                    "AND W.MODULE = 'SHOP' " +
	                    "AND W.RFO_NO IS NOT NULL " +
	                    "AND (W.STATUS = 'OPEN' OR (W.STATUS = 'COMPLETED' AND NOT EXISTS (SELECT 1 FROM WO_TASK_CARD TC WHERE TC.WO = W.WO AND TC.STATUS <> 'CANCEL'))) " +
	                    ") " +
	                    "UNION ALL " +
	                    "SELECT REFERENCE_TASK_CARD, TASK_CARD_DESCRIPTION, PRIORITY, WO, TASK_CARD, " +
	                    "(SELECT W.STATUS FROM WO W WHERE W.WO = WO_TASK_CARD_ADUIT.WO) AS STATUS, " +
	                    "(SELECT W.RFO_NO FROM WO W WHERE W.WO = WO_TASK_CARD_ADUIT.WO AND W.MODULE = 'SHOP' " +
	                    "AND (WO_TASK_CARD_ADUIT.non_routine = 'N' OR WO_TASK_CARD_ADUIT.non_routine = 'Y' OR WO_TASK_CARD_ADUIT.non_routine IS NULL) " +
	                    "AND W.rfo_no IS NOT NULL) AS ESD_RFO, TASK_CARD_CATEGORY, " +
	                    "(SELECT COALESCE(SUM(NVL(man_hours, 0) * NVL(man_require, 0)), 0) + " +
	                    "COALESCE(SUM(NVL(inspector_man_hours, 0) * NVL(inspector_man_require, 0)), 0) " +
	                    "FROM WO_task_card_item WHERE TASK_CARD = WO_TASK_CARD_ADUIT.TASK_CARD AND WO = WO_TASK_CARD_ADUIT.WO) AS Total_Hours, STATUS as TASK_CARD_STATUS " +
	                    "FROM WO_TASK_CARD_ADUIT " +
	                    "WHERE (INTERFACE_SAP_TRANSFERRED_DATE IS NULL " +
	                    "OR (MODIFIED_DATE > INTERFACE_SAP_TRANSFERRED_DATE AND " +
	                    "(TASK_CARD_DESCRIPTION <> TASK_CARD_DESCRIPTION_OLD OR TASK_CARD_CATEGORY <> TASK_CARD_CATEGORY_OLD OR (STATUS = 'CANCEL' AND STATUS <> STATUS_OLD) OR " +
	                    "(SELECT COALESCE(SUM(NVL(man_hours, 0) * NVL(man_require, 0)), 0) + " +
	                    "COALESCE(SUM(NVL(inspector_man_hours, 0) * NVL(inspector_man_require, 0)), 0) " +
	                    "FROM WO_task_card_item WHERE TASK_CARD = WO_TASK_CARD_ADUIT.TASK_CARD AND WO = WO_TASK_CARD_ADUIT.WO) <> TASK_CARD_HOURS_OLD))) " +
	                    "AND (1 = (SELECT COUNT(*) FROM WO W WHERE W.WO = WO_TASK_CARD_ADUIT.WO " +
	                    "AND W.MODULE = 'SHOP' AND W.RFO_NO IS NOT NULL " +
	                    "AND (WO_TASK_CARD_ADUIT.non_routine = 'N' OR WO_TASK_CARD_ADUIT.non_routine = 'Y' OR WO_TASK_CARD_ADUIT.non_routine IS NULL))) " +
	                    "AND (non_routine = 'N' OR non_routine = 'Y' OR non_routine IS NULL) AND STATUS != 'CLOSED' " +
	                    "AND EXISTS (" +
	                    "SELECT 1 " +
	                    "FROM WO W " +
	                    "WHERE W.WO = WO_TASK_CARD_ADUIT.WO " +
	                    "AND W.MODULE = 'SHOP' " +
	                    "AND W.RFO_NO IS NOT NULL " +
	                    "AND (W.STATUS = 'OPEN' OR (W.STATUS = 'COMPLETED' AND NOT EXISTS (SELECT 1 FROM WO_TASK_CARD_ADUIT TC WHERE TC.WO = W.WO AND TC.STATUS <> 'CANCEL'))) " +
	                    ") " +
	                    "AND WO_TASK_CARD_ADUIT.Transaction_type = 'DELETE'";


	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	      sqlTaskCard = "SELECT *	FROM (" + sqlTaskCard;
	    }

	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	      sqlTaskCard = sqlTaskCard + "  )WHERE ROWNUM <= ?";
	    }

	    String sqlItem =
	      "SELECT MAX(OPS_NO) AS OPS_NO FROM (SELECT WTI.OPS_NO FROM WO_TASK_CARD_ITEM WTI, WO_TASK_CARD WT WHERE WTI.WO = ? AND WTI.TASK_CARD = ? AND WT.WO = WTI.WO AND WT.TASK_CARD = WTI.TASK_CARD AND ROWNUM = 1 \r\n" +
	      "UNION ALL SELECT WTIA.OPS_NO FROM WO_TASK_CARD_ITEM_ADUIT WTIA, WO_TASK_CARD_ADUIT WTA WHERE WTIA.WO = ? AND WTIA.TASK_CARD = ? AND WTA.WO = WTIA.WO AND WTA.TASK_CARD = WTIA.TASK_CARD AND ROWNUM = 1) COMBINED_RESULT WHERE ROWNUM = 1 ";

	    String sqlWork =
	     "SELECT COALESCE(SUM(NVL(man_hours, 0)), 0) AS total_man_hours, COALESCE(SUM(NVL(inspector_man_hours, 0)), 0) AS total_inspector_man_hours, COALESCE(SUM(NVL(man_hours, 0) * NVL(man_require, 0)), 0) + COALESCE(SUM(NVL(inspector_man_hours, 0) * NVL(inspector_man_require, 0)), 0) AS Total_Hours\r\n"
	     + "FROM WO_task_card_item WHERE TASK_CARD = ? AND WO = ?";
	    
	   // String sqlStatus = 
	    //	" SELECT STATUS FROM WO_TASK_CARD WHERE WO =? and TASK_CARD = ?";
	    
	    String sqlStatusAudit = 
	    		"SELECT Transaction_type FROM wo_task_card_aduit WHERE WO = ? AND TASK_CARD = ? and rownum = 1 ORDER BY modified_Date DESC";
	    
	    String sqlCategory = 
		    	"SELECT TASK_CARD_CATEGORY FROM WO_TASK_CARD WHERE WO =? AND TASK_CARD =?";
	    
	    String sqlMark = "UPDATE WO_TASK_CARD SET INTERFACE_TRANSFERRED_DATE = SYSDATE, TASK_CARD_DESCRIPTION_OLD = ?, TASK_CARD_CATEGORY_OLD = ?, TASK_CARD_HOURS_OLD = ?, STATUS_OLD = ? WHERE TASK_CARD = ? AND WO = ?";
	    
	    String sqlMark2 = "UPDATE WO_TASK_CARD_ADUIT SET INTERFACE_SAP_TRANSFERRED_DATE = SYSDATE, TASK_CARD_DESCRIPTION_OLD = ?, TASK_CARD_CATEGORY_OLD = ?, TASK_CARD_HOURS_OLD = ?, STATUS_OLD = ? WHERE TASK_CARD = ? AND WO = ? AND TRANSACTION_TYPE ='DELETE'";

	    PreparedStatement pstmt1 = null;
	    ResultSet rs1 = null;

	    PreparedStatement pstmt2 = null;
	    ResultSet rs2 = null;

	    PreparedStatement pstmt3 = null;
	    ResultSet rs3 = null;
	    
	    //PreparedStatement pstmt4 = null;
	    //ResultSet rs4 = null;
	    
	    PreparedStatement pstmt5 = null;
	    ResultSet rs5 = null;
	    
	    PreparedStatement pstmt6 = null;
	    ResultSet rs6 = null;
	    
	    PreparedStatement pstmt7 = null;
	    ResultSet rs7 = null;
	    
	    PreparedStatement pstmt8 = null;
	    ResultSet rs8 = null;


	    try {
	      pstmt1 = con.prepareStatement(sqlTaskCard);
	      pstmt2 = con.prepareStatement(sqlItem);
	      pstmt3 = con.prepareStatement(sqlWork);
	     // pstmt4 = con.prepareStatement(sqlStatus);
	      pstmt5 = con.prepareStatement(sqlCategory);
	      pstmt6 = con.prepareStatement(sqlMark);
	      pstmt7 = con.prepareStatement(sqlMark2);
	      pstmt8 = con.prepareStatement(sqlStatusAudit);

	      if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        pstmt1.setString(1, MaxRecord);
	      }

	      rs1 = pstmt1.executeQuery();

	      if (rs1 != null) {
	            while (rs1.next()) {
	                logger.info("Processing WO Task Card: " + rs1.getString(5) + ", WO: " + rs1.getString(4));

	                String traxWO = rs1.getString(4);
	                String taskCard = rs1.getString(5);

	                OrderSND order;
	                if (orderMap.containsKey(traxWO)) {
	                    order = orderMap.get(traxWO);
	                } else {
	                    order = new OrderSND();
	                    order.setTraxWO(traxWO);
	                    order.setSapOrderNumber(rs1.getString(7) != null ? rs1.getString(7) : rs1.getString(1));
	                    oplist = new ArrayList<>();
	                    order.setOperations(oplist);
	                    orderMap.put(traxWO, order);
	                }

	                OperationSND operation = new OperationSND();
	                operation.setTcNumber(taskCard);
	                operation.setTcDescription(rs1.getString(2) != null ? rs1.getString(2) : "");
	                operation.setStandardManHours("00");
	                operation.setDeletionIndicator("");

	                pstmt5.setString(1, traxWO);
	                pstmt5.setString(2, taskCard);
	                rs5 = pstmt5.executeQuery();
	                if (rs5 != null && rs5.next()) {
	                    logger.info("Category of the WO: " + rs5.getString(1));
	                }
	                if (rs5 != null && !rs5.isClosed()) rs5.close();
	                
	                if(rs1.getString(8) != null) {
	                	operation.setTcCategory(rs1.getString(8));
	                }else {
	                	operation.setTcCategory("");
	                }

	                /*pstmt4.setString(1, traxWO);
	                pstmt4.setString(2, taskCard);
	                rs4 = pstmt4.executeQuery();*/
	                
	                String deletionIndicator = "";
	                String status = "";
	               
	                    logger.info("Status of the WO: " + rs1.getString(10));
	                    status = rs1.getString(10);
	                    if ("CANCEL".equals(rs1.getString(10))) {
	                        deletionIndicator = "X";
	                    }
	           

	                if (deletionIndicator.equals("")) {
	                    pstmt8.setString(1, traxWO);
	                    pstmt8.setString(2, taskCard);
	                    rs8 = pstmt8.executeQuery();
	                    if (rs8 != null && rs8.next()) {
	                        logger.info("Task Card deletion: " + rs8.getString(1));
	                        if ("DELETE".equals(rs8.getString(1))) {
	                            deletionIndicator = "X";
	                            operation.setTcCategory("");
	                        }
	                    }
	                    if (rs8 != null && !rs8.isClosed()) rs8.close();
	                }
	                
	                logger.info("Status of the WO: " + rs1.getString(10) + " Indicator: " + deletionIndicator);

	                operation.setDeletionIndicator(deletionIndicator);

	                pstmt2.setString(1, traxWO);
	                pstmt2.setString(2, taskCard);
	                pstmt2.setString(3, traxWO);
	                pstmt2.setString(4, taskCard);
	                rs2 = pstmt2.executeQuery();
	                if (rs2 != null) {
	                    while (rs2.next()) {
	                        logger.info("Processing WO Task Card Item Operation Number: " + rs2.getString(1));
	                        operation.setOperationNumber(rs2.getString(1) != null ? rs2.getString(1) : "");
	                    }
	                }
	                if (rs2 != null && !rs2.isClosed()) rs2.close();

	                pstmt3.setString(1, taskCard);
	                pstmt3.setString(2, traxWO);
	                rs3 = pstmt3.executeQuery();
	                if (rs3 != null) {
	                    while (rs3.next()) {
	                        logger.info("MECH HRS: " + rs3.getString(1) + " INSP HRS: " + rs3.getString(2) + " TOTAL HRS: " + rs3.getString(3));
	                        Integer hours = 0;
	                        if (rs3.getString(3) != null && !rs3.getString(3).isEmpty()) {
	                            hours = hours + new BigDecimal(rs3.getString(3)).intValue();
	                        }
	                        String manHours = "00";
	                        if (hours.intValue() != 0) {
	                            manHours = hours.toString();
	                        }
	                        operation.setStandardManHours(manHours);
	                    }
	                }
	                if (rs3 != null && !rs3.isClosed()) rs3.close();

	                order.getOperations().add(operation);

	                
	                pstmt6.setString(1, operation.getTcDescription());
	                pstmt6.setString(2, operation.getTcCategory());
	                pstmt6.setString(3, operation.getStandardManHours());
	                pstmt6.setString(4, status);
	                pstmt6.setString(5, taskCard);
	                pstmt6.setString(6, traxWO);
	                pstmt6.executeUpdate(); 

	                pstmt7.setString(1, operation.getTcDescription());
	                pstmt7.setString(2, operation.getTcCategory());
	                pstmt7.setString(3, operation.getStandardManHours());
	                pstmt7.setString(4, status);
	                pstmt7.setString(5, taskCard);
	                pstmt7.setString(6, traxWO);
	                pstmt7.executeQuery();
	                
	                
	   	         	
	                //if (rs4 != null && !rs4.isClosed()) rs4.close();
	                //if (rs5 != null && !rs5.isClosed()) rs5.close();
	            }

	            for (OrderSND order : orderMap.values()) {
	                INT6_SND req = new INT6_SND();
	                req.getOrder().add(order);
	                list.add(req);
	            }
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        executed = e.toString();
	        Import_TC_MHR_Controller.addError(e.toString());
	        logger.severe(executed);
	        throw new Exception("Issue found");
	    } finally {
	    	 if (rs1 != null) rs1.close();
	         if (pstmt1 != null) pstmt1.close();
	         if (rs2 != null) rs2.close();
	         if (pstmt2 != null) pstmt2.close();
	         if (rs3 != null) rs3.close();
	         if (pstmt3 != null) pstmt3.close();
	         //if (rs4 != null) rs4.close();
            // if (pstmt4 != null) pstmt4.close();
             if (rs5 != null) rs5.close();
             if (pstmt5 != null) pstmt5.close();
	         if (rs6 != null) rs6.close();
	         if (pstmt6 != null) pstmt6.close();
	         if (rs7 != null) rs7.close();
	         if (pstmt7 != null) pstmt7.close();
	         if (rs8 != null) rs8.close();
	         if (pstmt8 != null) pstmt8.close();
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

	  public String deleteOpsLine(String opsline) throws Exception {
	    String Executed = "OK";

	    String query = "DELETE OPS_LINE_EMAIL_MASTER where \"OPS_LINE\" = ?";

	    PreparedStatement ps = null;

	    try {
	      if (con == null || con.isClosed()) {
	        con = DataSourceClient.getConnection();
	        logger.info(
	          "The connection was stablished successfully with status: " +
	          String.valueOf(!con.isClosed())
	        );
	      }

	      ps = con.prepareStatement(query);
	      ps.setString(1, opsline);
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

	  public String getemailByOpsLine(String opsLine) throws Exception {
	    ArrayList<String> groups = new ArrayList<String>();

	    String query = "", group = "";
	    if (opsLine != null && !opsLine.isEmpty()) {
	      query =
	        "Select \\\"EMAIL\\\", OPS_LINE FROM OPS_LINE_EMAIL_MASTER where OPS_LINE = ?";
	    } else {
	      query = " Select \"EMAIL\", OPS_LINE FROM OPS_LINE_EMAIL_MASTER";
	    }
	    PreparedStatement ps = null;

	    try {
	      if (con == null || con.isClosed()) {
	        con = DataSourceClient.getConnection();
	        logger.info(
	          "The connection was stablished successfully with status: " +
	          String.valueOf(!con.isClosed())
	        );
	      }

	      ps = con.prepareStatement(query);
	      if (opsLine != null && !opsLine.isEmpty()) {
	        ps.setString(1, opsLine);
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
	        logger.info(
	          "The connection was stablished successfully with status: " +
	          String.valueOf(!con.isClosed())
	        );
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
	        logger.severe("An error ocurrer trying to close the statement");
	      }
	    }

	    return email;
	  }
	  public OpsLineEmail getOpsLineStaffName(String wo, String taskCard) {
			String query = "";
			OpsLineEmail OpsLineEmail = new OpsLineEmail();

			query =
			  "SELECT rm.RELATION_CODE,rm.NAME,w.ops_line,wtc.INTERFACE_FLAG\r\n" +
			  "FROM WO w, WO_TASK_CARD wtc, relation_master rm \r\n" +
			  "WHERE w.wo = ? AND wtc.task_card = ? AND w.wo = wtc.wo AND w.created_by = rm.relation_code";

			PreparedStatement ps = null;

			try {
			  if (con == null || con.isClosed()) {
				con = DataSourceClient.getConnection();
				logger.info(
				  "The connection was stablished successfully with status: " +
				  String.valueOf(!con.isClosed())
				);
			  }

			  ps = con.prepareStatement(query);

			  ps.setString(1, wo);
			  ps.setString(2, taskCard);

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
				logger.severe("An error ocurrer trying to close the statement");
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
