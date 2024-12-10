package trax.aero.data;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBException;

import trax.aero.controller.Creation_Sales_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.INT7_SND;
import trax.aero.pojo.INT7_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.OrderSND;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.ErrorType;

public class Creation_Sales_Data {
    EntityManagerFactory factory;
    EntityManager em;
    String executed;
    private Connection con;

    final String MaxRecord = System.getProperty("Creation_Sales_MaxRecord");
	Logger logger = LogManager.getLogger("CreationSales");

    public Creation_Sales_Data(String mark) {
        try {
            if (this.con == null || this.con.isClosed()) {
                this.con = DataSourceClient.getConnection();
                logger.info("The connection was established successfully with status: " + String.valueOf(!this.con.isClosed()));
            }
        } catch (SQLException e) {
        	logger.severe("An error ocurred getting the status of the connection");
            Creation_Sales_Controller.addError(e.toString());
        } catch (CustomizeHandledException e1) {
            Creation_Sales_Controller.addError(e1.toString());
        } catch (Exception e) {
            Creation_Sales_Controller.addError(e.toString());
        }
    }

    public Creation_Sales_Data() {
        try {
            if (this.con == null || this.con.isClosed()) {
                this.con = DataSourceClient.getConnection();
                logger.info("The connection was established successfully with status: " + String.valueOf(!this.con.isClosed()));
            }
        } catch (SQLException e) {
        	logger.severe("An error occurred getting the status of the connection");
            Creation_Sales_Controller.addError(e.toString());
        } catch (CustomizeHandledException e1) {
            Creation_Sales_Controller.addError(e1.toString());
        } catch (Exception e) {
            Creation_Sales_Controller.addError(e.toString());
        }
        factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
        em = factory.createEntityManager();
    }

    public Connection getCon() {
        return con;
    }
    
    private BigDecimal getTransactionNo(String code) {
        BigDecimal acctBal = null;
        String sql = "SELECT pkg_application_function.config_number (?) FROM DUAL";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    acctBal = rs.getBigDecimal(1);
                } else {
                    logger.severe("The sequence number returned from pkg_application_function.config_number is null for code: " + code);
                }
            }
        } catch (SQLException e) {
            logger.severe("An unexpected SQL error occurred getting the sequence for code: " + code + ". Exception details: " + e.getMessage());
        }
        return acctBal;
    }

    public String markSendData() throws JAXBException {
        INT7_TRAX request = new INT7_TRAX();
        try {
            markTransaction(request);
            logger.info("markTransaction completed successfully.");
            return "OK";
        } catch (Exception e) {
            logger.severe("Error executing markTransaction");
            return null;
        }
    }

	public String markTransaction(INT7_TRAX request) {
	    executed = "OK";
	    
	    String sqlUpdateWO = "UPDATE WO SET RFO_NO = ?, INTERFACE_ESD_TRANSFERRED_DATE = SYSDATE, INTERFACE_ESD_TRANSFERRED_FLAG = 'Y' WHERE WO = ?";
	    
	    String sqlReturn = "UPDATE WO SET STATUS = 'CONF SLOT', INTERFACE_ESD_TRANSFERRED_FLAG = CASE WHEN SOURCE_TYPE IN ('X3', 'E8') THEN 'D' ELSE '5' \r\n " +
	                        "END, INTERFACE_ESD_TRANSFERRED_DATE = CASE WHEN SOURCE_TYPE IN ('X3', 'E8') THEN NULL ELSE SYSDATE END WHERE WO = ?";
	    
	    String sqlInsertError = "INSERT INTO interface_audit (TRANSACTION, TRANSACTION_TYPE, ORDER_NUMBER, TRANSACTION_OBJECT, TRANSACTION_DATE, CREATED_BY, MODIFIED_BY, EXCEPTION_ID, EXCEPTION_BY_TRAX, EXCEPTION_DETAIL, EXCEPTION_CLASS_TRAX, CREATED_DATE, MODIFIED_DATE) "
	            + "SELECT seq_interface_audit.NEXTVAL, 'ERROR', ?, 'I07', sysdate, 'TRAX_IFACE', 'TRAX_IFACE', ?, 'Y', ?, 'Creation_Sales I_07', sysdate, sysdate FROM dual";

	    String sqlDeleteAllErrors = "DELETE interface_audit WHERE ORDER_NUMBER = ? "; 
	    
	    String sqlDeleteErrorWithContract = "DELETE interface_audit WHERE ORDER_NUMBER = ? AND EXCEPTION_DETAIL = 'WO does not have Contract added' ";

	    String sqlCheckContract = "SELECT CONTRACT_NUMBER FROM CUSTOMER_ORDER_HEADER WHERE ORDER_NUMBER = ? ";
	    
	    String sqlInitialLoad = "SELECT PN, PN_SN FROM WO_SHOP_DETAIL WHERE WO = ?";
        String sqlInitialLoad2 = "SELECT LOCATION FROM WO WHERE WO = ?";
        String initialLoadCheck = "SELECT COUNT(*) FROM PN_INVENTORY_HISTORY WHERE PN = ? AND SN = ? AND TRANSACTION_TYPE = 'INITIAL_LOAD' ";

	    
	    try (PreparedStatement pstmt1 = con.prepareStatement(sqlUpdateWO);
	         PreparedStatement pstmt2 = con.prepareStatement(sqlReturn);
	         PreparedStatement psInsertError = con.prepareStatement(sqlInsertError);
	         PreparedStatement psDeleteAllErrors = con.prepareStatement(sqlDeleteAllErrors); 
	         PreparedStatement psDeleteErrorWithContract = con.prepareStatement(sqlDeleteErrorWithContract);
	         PreparedStatement psCheckContract = con.prepareStatement(sqlCheckContract);
	    	 PreparedStatement psInitialLoad = con.prepareStatement(sqlInitialLoad);
	         PreparedStatement psInitialLoad2 = con.prepareStatement(sqlInitialLoad2);
	    	 PreparedStatement psInitialLoadcheck = con.prepareStatement(initialLoadCheck)	) {
	        
	        if (request != null) {
	            
	            // Check contract number
	            psCheckContract.setString(1, request.getWO());
	            ResultSet rs = psCheckContract.executeQuery();
	            boolean hasContract = false;

	            if (rs.next()) {
	                String contractNumber = rs.getString("CONTRACT_NUMBER");
	                hasContract = (contractNumber != null && !contractNumber.isEmpty());
	            }
	            
	            if (request.getRfoNO() != null && !request.getRfoNO().isEmpty() && request.getExceptionId() != null && request.getExceptionId().equalsIgnoreCase("53")) {
	                pstmt1.setString(2, request.getWO());
	                pstmt1.setString(1, request.getRfoNO());
	                pstmt1.executeUpdate();
	                
	                logger.info("Performing initial load for WO: " + request.getWO());
                    psInitialLoad.setString(1, request.getWO());
                    try (ResultSet rsInitialLoad = psInitialLoad.executeQuery()) {
                        if (rsInitialLoad.next()) {
                        	String pn = rsInitialLoad.getString("PN");
                            String sn = rsInitialLoad.getString("PN_SN");
                        	 // Execute the initial load check
                            psInitialLoadcheck.setString(1, pn);
                            psInitialLoadcheck.setString(2, sn);

                            try (ResultSet rsCheck = psInitialLoadcheck.executeQuery()) {
                                int count = 0;
                                if (rsCheck.next()) {
                                    count = rsCheck.getInt(1);
                                }

                                if (count == 1) {
                                    // Do nothing if record exists
                                    logger.info("Record already exists in PN_INVENTORY_HISTORY for PN: " + pn + ", SN: " + sn);
                                } else {
                                    // Proceed with the rest of the code
                                    psInitialLoad2.setString(1, request.getWO());
                                    try (ResultSet rsInitialLoad2 = psInitialLoad2.executeQuery()) {
                                        if (rsInitialLoad2.next()) {
                                            String location = rsInitialLoad2.getString("LOCATION");
                                            BigDecimal initialBatchVal = getTransactionNo("BATCH");
                                            if (initialBatchVal == null) {
                                                executed = "Failed to retrieve initial batch for WO: " + request.getWO();
                                                Creation_Sales_Controller.addError(executed);
                                                logger.severe("Batch retrieval failed for WO: " + request.getWO());
                                                return executed;
                                            }
                                            long initialBatch = initialBatchVal.longValue();
                                            long goodsReceivedBatch = initialBatch;
                                            long batch = initialBatch;

                                            logger.info("Inserting into pn_inventory_detail for PN: " + pn + ", SN: " + sn);
                                            String insertInventoryDetail = "INSERT INTO pn_inventory_detail (PN, SN, LOCATION, BATCH, GOODS_RCVD_BATCH, QTY_AVAILABLE, GL_COMPANY, CREATED_BY, CREATED_DATE, CONDITION) VALUES (?, ?, ?, ?, ?, ?, 'SIAEC', 'TRAXIFACE', sysdate, 'U/S')";
                                            try (PreparedStatement pstmtInsertDetail = con.prepareStatement(insertInventoryDetail)) {
                                                pstmtInsertDetail.setString(1, pn);
                                                pstmtInsertDetail.setString(2, sn);
                                                pstmtInsertDetail.setString(3, location);
                                                pstmtInsertDetail.setLong(4, batch);
                                                pstmtInsertDetail.setLong(5, goodsReceivedBatch);
                                                pstmtInsertDetail.setInt(6, 1); // Quantity available
                                                pstmtInsertDetail.executeUpdate();
                                            }

                                            BigDecimal transactionVal = getTransactionNo("PNINVHIS");
                                            if (transactionVal == null) {
                                                executed = "Failed to retrieve transaction number for WO: " + request.getWO();
                                                Creation_Sales_Controller.addError(executed);
                                                logger.severe("Transaction number retrieval failed for WO: " + request.getWO());
                                                return executed;
                                            }
                                            long transactionNo = transactionVal.longValue();

                                            logger.info("Inserting into pn_inventory_history for PN: " + pn + ", SN: " + sn);
                                            String insertInventoryHistory = "INSERT INTO pn_inventory_history (PN, SN, LOCATION, BATCH, GOODS_RCVD_BATCH, TRANSACTION_NO, WO, TRANSACTION_TYPE, TRANSACTION_DATE, GL_COMPANY, CREATED_BY, CREATED_DATE, CONDITION) VALUES (?, ?, ?, ?, ?, ?, ?, 'INITIAL_LOAD', SYSDATE, 'SIAEC', 'TRAXIFACE', sysdate, 'U/S')";
                                            try (PreparedStatement pstmtInsertHistory = con.prepareStatement(insertInventoryHistory)) {
                                                pstmtInsertHistory.setString(1, pn);
                                                pstmtInsertHistory.setString(2, sn);
                                                pstmtInsertHistory.setString(3, location);
                                                pstmtInsertHistory.setLong(4, batch);
                                                pstmtInsertHistory.setLong(5, goodsReceivedBatch);
                                                pstmtInsertHistory.setLong(6, transactionNo);
                                                pstmtInsertHistory.setString(7, request.getWO());
                                                pstmtInsertHistory.executeUpdate();
                                                
                                            
                                            }
                                            
                                            logger.info("Updating wo_shop_detail for WO: " + request.getWO() + ", PN: " + pn);
                                            String updateWOshop = "update wo_shop_detail set BATCH = ? where wo = ? and pn = ? and PN_SN = ? ";
                                            try (PreparedStatement pstmtupdateBatch = con.prepareStatement(updateWOshop)) {
                                            	pstmtupdateBatch.setLong(1, goodsReceivedBatch);
                                            	pstmtupdateBatch.setString(2, request.getWO());
                                            	pstmtupdateBatch.setString(3, pn);
                                            	pstmtupdateBatch.setString(4, sn);
                                            	pstmtupdateBatch.executeUpdate();
                                                
                                            
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
	            
	            logger.info("Deleting all errors for WO: " + request.getWO());
                psDeleteAllErrors.setString(1, request.getWO());
                int rowsDeleted = psDeleteAllErrors.executeUpdate();
                logger.info("Number of records deleted for WO " + request.getWO() + ": " + rowsDeleted);
	            
	         
	            if (!hasContract) {
	                executed = "WO: " + request.getWO() + ", Error Code: 51, Remarks: WO does not have Contract";
	                Creation_Sales_Controller.addError(executed);

	              
	                psInsertError.setString(1, request.getWO());
	                psInsertError.setString(2, "51");
	                psInsertError.setString(3, "WO does not have Contract added ");
	                psInsertError.executeUpdate();

	                pstmt2.setString(1, request.getWO());
	                pstmt2.executeUpdate();

	            } else if (request.getExceptionId() != null && !request.getExceptionId().equalsIgnoreCase("53")) {
	                executed = "WO: " + request.getWO() + ", Error Code: " + request.getExceptionId() + ", Remarks: " + request.getExceptionDetail();
	                Creation_Sales_Controller.addError(executed);
	                
	                String exceptionDetail = request.getExceptionDetail();
	                if (exceptionDetail == null || exceptionDetail.trim().isEmpty()) {
	                    exceptionDetail = "Interface error returned from SAP";
	                }

	              
	                psInsertError.setString(1, request.getWO());
	                psInsertError.setString(2, request.getExceptionId());
	                psInsertError.setString(3, exceptionDetail);
	                psInsertError.executeUpdate();

	                pstmt2.setString(1, request.getWO());
	                pstmt2.executeUpdate();
	            } else {
	               
	                if (hasContract) {
	                    psDeleteErrorWithContract.setString(1, request.getWO());
	                    psDeleteErrorWithContract.executeUpdate();
	                }
	            }
	        }
	        
	    } catch (SQLException e) {
            executed = "SQL Error in markTransaction: " + e.getMessage();
            Creation_Sales_Controller.addError(executed);
            logger.severe("SQL Error in markTransaction: " + e.getMessage());
        } catch (Exception e) {
            executed = "Unexpected Error in markTransaction: " + e.getMessage();
            Creation_Sales_Controller.addError(executed);
            logger.severe("Unexpected error occurred in markTransaction: " + e.getMessage());
        }
	    
	    return executed;
	}
	
	public ArrayList<INT7_SND> getWorkOrder() throws Exception {
	    executed = "OK";

	    if (this.con == null || this.con.isClosed()) {
	        try {
	            this.con = DataSourceClient.getConnection(); 
	            if (this.con == null || this.con.isClosed()) {
	                throw new IllegalStateException("Issues connecting to the database");
	            }
	            logger.info("Established connection to the database.");
	        } catch (SQLException e) {
	            throw new IllegalStateException("Error trying to re-connect to the database.", e);
	        }
	    }

	    ArrayList<INT7_SND> list = new ArrayList<INT7_SND>();

	    String sqlWO = "SELECT W.WO, W.LOCATION, W.WO_DESCRIPTION, WS.PN, WS.PN_SN, W.SCHEDULE_START_DATE, W.SCHEDULE_COMPLETION_DATE,CASE WHEN P.ENGINE = 'APU' THEN 'APU' WHEN P.ENGINE = 'ENGINE' THEN 'ENG' \r\n" +
	    			   "WHEN P.ENGINE = 'MODULE' THEN 'MOD'WHEN P.ENGINE = 'LRU' THEN 'LRU'  \r\n" +
	    			   "WHEN P.ENGINE = 'ENGINE STAND' THEN 'EST' ELSE SUBSTR(P.ENGINE, 1, 3) END AS ENGINE_TYPE, W.SOURCE_TYPE, W.SOURCE_REF, CASE WHEN W.SOURCE_TYPE IN ('E8', 'X3') THEN ( \r\n" +
	    			   "SELECT W2.MOD_NO FROM WO W2 WHERE W2.WO = W.NH_WO) ELSE W.MOD_NO END AS MOD_NO, \r\n" +
	                   "W.THIRD_PARTY_WO, S.PARTY, CASE WHEN S.PARTY = '1P' AND (W.THIRD_PARTY_WO = 'N' OR W.THIRD_PARTY_WO IS NULL ) THEN 'N' WHEN S.PARTY = '3P' AND W.THIRD_PARTY_WO = 'Y' THEN 'Y' \r\n" +
	                   "WHEN S.PARTY = '1P' THEN 'N' WHEN S.PARTY = '3P' THEN 'Y' ELSE NULL END AS THIRD_PARTY_FLAG FROM WO W JOIN SYSTEM_TRAN_CODE S ON W.SOURCE_TYPE = S.SYSTEM_CODE \r\n" +
	                   "JOIN WO_SHOP_DETAIL WS ON W.WO = WS.WO JOIN PN_MASTER P ON WS.PN = P.PN WHERE W.SOURCE_TYPE IS NOT NULL AND S.SYSTEM_TRANSACTION = 'SOURCETYPE' \r\n" +
	                   "AND W.STATUS = 'OPEN' AND ((W.SOURCE_TYPE IN ('X3', 'E8') AND W.INTERFACE_ESD_TRANSFERRED_FLAG IS NULL AND W.INTERFACE_ESD_TRANSFERRED_DATE IS NULL) \r\n" +
	                   "OR (W.SOURCE_TYPE NOT IN ('X3', 'E8') AND W.INTERFACE_ESD_TRANSFERRED_FLAG = '5' AND W.INTERFACE_ESD_TRANSFERRED_DATE IS NOT NULL)) ";

	    String sqlContract = "SELECT CC.CONTRACT_ID, CC.BILLING_FORM, CC.DIP_PROFILE, CC.CUSTOMER_CODE, CC.CUSTOMER FROM CUSTOMER_CONTRACT_ESD CC JOIN CUSTOMER_CONTRACT_HEADER CH \r\n" +
	                         "ON TO_CHAR(CC.CUSTOMER_CODE) = TO_CHAR(CH.CUSTOMER) JOIN CUSTOMER_ORDER_HEADER CO ON CH.CONTRACT_NUMBER = CO.CONTRACT_NUMBER WHERE CO.ORDER_NUMBER = ?";    

	    String sqlMark = "UPDATE WO SET INTERFACE_ESD_TRANSFERRED_DATE = SYSDATE, INTERFACE_ESD_TRANSFERRED_FLAG = 'D' WHERE WO = ?";

	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        sqlWO = "SELECT * FROM (" + sqlWO;
	    }
	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        sqlWO = sqlWO + " ) WHERE ROWNUM <= ?";
	    }

	    PreparedStatement pstmt1 = null;
	    ResultSet rs1 = null;
	    PreparedStatement pstmt2 = null;
	    ResultSet rs2 = null;
	    PreparedStatement pstmt3 = null;
	    ResultSet rs3 = null;

	    try {
	        pstmt1 = con.prepareStatement(sqlWO);
	        pstmt2 = con.prepareStatement(sqlContract);
	        pstmt3 = con.prepareStatement(sqlMark);

	        if (MaxRecord != null && !MaxRecord.isEmpty()) {
	            pstmt1.setString(1, MaxRecord);
	        }

	        rs1 = pstmt1.executeQuery();

	        if (rs1 != null) {
	            while (rs1.next()) {
	                logger.info("Processing WO: " + rs1.getString(1) + ", WO Description: " + rs1.getString(3) + ", Location: " + rs1.getString(2));
	                INT7_SND req = new INT7_SND();

	                if (rs1.getString(1) != null && !rs1.getNString(1).isEmpty()) {
	                    req.setTraxWo(rs1.getString(1));
	                    req.setLocationWO(rs1.getString(2));
	                }

	                req.setTcDescription(rs1.getString(3) != null ? rs1.getString(3) : "");

	                logger.info("PN: " + rs1.getString(4) + ", SN: " + rs1.getString(5));

	                req.setPn(rs1.getString(4) != null ? rs1.getString(4) : "");
	                req.setPnSn(rs1.getString(5) != null ? rs1.getString(5) : "");

	                logger.info("Notification Type: " + rs1.getString(9) + ", Notification Number: " + rs1.getString(10));

	                if (rs1.getString(9) != null && "N".equalsIgnoreCase(rs1.getString(14))) {
	                    req.setNotification(rs1.getString(9));
	                } else {
	                    req.setNotification("");
	                }

	                req.setNotificationNO(rs1.getString(10) != null && "N".equalsIgnoreCase(rs1.getString(14)) ? rs1.getString(10) : "");

	                logger.info("TECH CTL: " + rs1.getString(8) + ", 3P Flag: " + rs1.getString(14) + ", WBS: " + rs1.getString(11));

	                String techControl = rs1.getString(8);
	                req.setTechControl(techControl != null ? techControl : "");

	                req.setPFlag(rs1.getString(14));
	                req.setWBS(rs1.getString(11) != null ? rs1.getString(11) : "");

	                pstmt2.setString(1, req.getTraxWo());
	                rs2 = pstmt2.executeQuery();

	                if (rs2 != null && rs2.next()) {
	                    logger.info("Contract ID: " + rs2.getString(1) + ", Billing Form: " + rs2.getString(2));

	                    req.setContractID(rs2.getString(1) != null ? rs2.getString(1) : "");

	                    String billingForm = "";
	                    if ("FIXED RATE".equals(rs2.getString(2))) {
	                        billingForm = "01";
	                    } else if ("COST".equals(rs2.getString(2))) {
	                        billingForm = "02";
	                    }

	                    req.setBillFormat(billingForm);

	                    logger.info("Customer ID: " + rs2.getString(4) + ", DIP Profile: " + rs2.getString(3));

	                    req.setDIP_Profile(rs2.getString(3) != null ? rs2.getString(3) : "");
	                    req.setCustomerID(rs2.getString(4) != null ? rs2.getString(4) : "");

	                } else {
	                    req.setCustomerID("");
	                    req.setDIP_Profile("");
	                    req.setBillFormat("");
	                    req.setContractID("");
	                }
	                if (rs2 != null && !rs2.isClosed()) {
	                    rs2.close();
	                }

	                list.add(req);

	                pstmt3.setString(1, req.getTraxWo());
	                pstmt3.executeQuery();
	            }
	        }
	        if (rs1 != null && !rs1.isClosed()) rs1.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        executed = e.toString();
	        Creation_Sales_Controller.addError(e.toString());

	        logger.severe(executed);
	        throw new Exception("Issue found");
	    } finally {
	        if (rs1 != null && !rs1.isClosed()) rs1.close();
	        if (pstmt1 != null && !pstmt1.isClosed()) pstmt1.close();
	        if (pstmt2 != null && !pstmt2.isClosed()) pstmt2.close();
	        if (pstmt3 != null && !pstmt3.isClosed()) pstmt3.close();
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
