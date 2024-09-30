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

import trax.aero.controller.CreationU_RFO_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.INT22_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.INT22_SND;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.ErrorType;

public class CreationU_RFO_Data {

	EntityManagerFactory factory;
	EntityManager em;
	String executed;
	private Connection con;
	
	final String MaxRecord = System.getProperty("CreationRFO_MaxRecord");
	Logger logger = LogManager.getLogger("RFOCreation");
	
	public CreationU_RFO_Data(String mark) {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " +String.valueOf(!this.con.isClosed()));
			}
		}catch(SQLException e) {
			logger.severe("An error ocurred getting the status of the connection");
			CreationU_RFO_Controller.addError(e.toString());
		} catch (CustomizeHandledException e1) {
			CreationU_RFO_Controller.addError(e1.toString());
		} catch (Exception e) {
			CreationU_RFO_Controller.addError(e.toString());
		}
	}
	
	public CreationU_RFO_Data() {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!this.con.isClosed()));
			}
		} catch (SQLException e) {
		      logger.severe("An error occured getting the status of the connection");
		      CreationU_RFO_Controller.addError(e.toString());
		    } catch (CustomizeHandledException e1) {
		    	CreationU_RFO_Controller.addError(e1.toString());
		    } catch (Exception e) {
		    	CreationU_RFO_Controller.addError(e.toString());
		    }
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		em = factory.createEntityManager();
	}
	
	public Connection getCon() {
		return con;
	}
	
	public String markSendData() throws JAXBException
	{
	  INT22_TRAX request = new INT22_TRAX();
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
	
	public String markTransaction(INT22_TRAX request) {
	    executed = "OK";

	    String update = "UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_DATE = SYSDATE, SVO_NO = ?, RFO_NO = ?, INTERFACE_TRANSFER_FLAG = 'Y', Z_PRINTSTATUS = ? WHERE TRANSACTION_NO = ? AND  WO = ? AND TASK_CARD = ? ";
	    
	    String errorunmark = " UPDATE PN_INVENTORY_HISTORY SET MADE_AS_CCS = NULL, interface_transfer_flag = 'X' WHERE TRANSACTION_NO = ? AND  WO = ? AND TASK_CARD = ? ";
	    
	    String selectpn = "SELECT PN FROM PN_INVENTORY_HISTORY WHERE TRANSACTION_NO = ? AND  WO = ? AND TASK_CARD = ? ";
	    
	    String sqlInsertError = "INSERT INTO interface_audit (TRANSACTION, TRANSACTION_TYPE, ORDER_NUMBER, EO, TRANSACTION_OBJECT, TRANSACTION_DATE, CREATED_BY, MODIFIED_BY, EXCEPTION_ID, EXCEPTION_BY_TRAX, EXCEPTION_DETAIL, EXCEPTION_CLASS_TRAX, CREATED_DATE, MODIFIED_DATE, ORDER_NUMBER_REFERENCE) "
	                + "SELECT seq_interface_audit.NEXTVAL, 'ERROR', ?, ?, 'I22', sysdate, 'TRAX_IFACE', 'TRAX_IFACE', ?, 'Y', ?, 'CreationU_RFO I_22', sysdate, sysdate, ? FROM dual";
	    
	    String sqlDeleteError = "DELETE FROM interface_audit WHERE ORDER_NUMBER = ? AND EO = ?";

	    try (PreparedStatement pstmt2 = con.prepareStatement(update);
	         PreparedStatement pstmt3 = con.prepareStatement(errorunmark);
	    	PreparedStatement pn = con.prepareStatement(selectpn);
	    	 PreparedStatement psInsertError = con.prepareStatement(sqlInsertError);
	         PreparedStatement psDeleteError = con.prepareStatement(sqlDeleteError)) {

	        if (request != null) {
	            String errorCode = request.getErrorCode();
	            String traxWoNumber = request.getTraxWoNumber();
	            String sapSvo = request.getSapSvo();
	            String transaction = request.getTransaction();
	            String tcNumber = request.getTcNumber();
	            String remarks = request.getRemarks();

	            // Log the request object to inspect its state
	            logger.info("Request Object: " + request.toString());

	            if (errorCode != null && errorCode.equalsIgnoreCase("53")) {

	                // Check if all necessary values are non-null before proceeding
	                if (transaction != null && sapSvo != null && traxWoNumber != null && tcNumber != null) {
	                	pstmt2.setString(1, sapSvo);
	                	pstmt2.setString(2, request.getSapRepairRfo());
	                	pstmt2.setString(3, request.getPrintStatus()); 
	                	pstmt2.setString(4, transaction);
	                	pstmt2.setString(5, traxWoNumber);
	                	pstmt2.setString(6, tcNumber);

	                	// Use executeUpdate for update queries
	                	pstmt2.executeUpdate();
	                    
	                    // Log success of the update
	                    logger.info("Successfully executed update for WO: " + traxWoNumber);

	                    psDeleteError.setString(1, traxWoNumber);
	                    psDeleteError.setString(2, sapSvo);
	                    psDeleteError.executeUpdate();

	                    // Log deletion of previous errors
	                    logger.info("Deleted previous errors for WO: " + traxWoNumber);
	                }
	            } else if (errorCode != null) {

	                if (traxWoNumber != null && sapSvo != null && errorCode != null && remarks != null) {
	                    executed = "Request WO: " + traxWoNumber + ", Error Code: " + errorCode + ", Remarks: " + remarks + ", SVO: " + sapSvo;
	                    CreationU_RFO_Controller.addError(executed);
	                    
	                    pn.setString(1, request.getTransaction());
	                    pn.setString(2, request.getTraxWoNumber());
	                    pn.setString(3, request.getTcNumber());
		    			ResultSet rs = pn.executeQuery();
		    			
		    			// Check if a result is returned
		    			String PN = null;
		    			if (rs.next()) {
		    			    PN = rs.getString(1); // Get the first column (PN)
		    			} else {
		    			    // Handle the case where no PN is found
		    			    logger.warning("No PN found for transaction: " + transaction + ", WO: " + traxWoNumber);
		    			}
	                    
	                    psInsertError.setString(1, request.getTraxWoNumber());
	                    psInsertError.setString(2, PN);
	                    psInsertError.setString(3, errorCode);
	                    psInsertError.setString(4, remarks);
	                    psInsertError.setString(5, transaction);
	                    psInsertError.executeUpdate();

	                    // Log the insertion of the error into the audit table
	                    logger.info("Inserted error for WO: " + traxWoNumber + ", Error Code: " + errorCode);
	                }

	                if (transaction != null && traxWoNumber != null && tcNumber != null) {
	                	pstmt3.setString(1, transaction);
	                	pstmt3.setString(2, traxWoNumber);
	                	pstmt3.setString(3, tcNumber);

	                	// Use executeUpdate here as well
	                	pstmt3.executeUpdate();

	                    // Log the execution of the error unmarking
	                    logger.info("Executed error unmark for WO: " + traxWoNumber);
	                }
	            } else {
	                if (traxWoNumber != null && sapSvo != null) {
	                    psDeleteError.setString(1, traxWoNumber);
	                    psDeleteError.setString(2, sapSvo);
	                    psDeleteError.executeUpdate();

	                    // Log the deletion of the error
	                    logger.info("Deleted error for WO: " + traxWoNumber);
	                }
	            }
	        }

	    } catch (SQLException e) {
	        executed = e.toString();
	        CreationU_RFO_Controller.addError(e.toString());
	        logger.severe("SQL Exception: " + e.toString());
	    }

	    return executed;
	}
	
	
	public ArrayList<INT22_SND> getPN() throws Exception{
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
		
		ArrayList<INT22_SND> list = new ArrayList<INT22_SND>();
		
		String sqlPN = "SELECT " +
                "w.location, " +
                "h.wo, " +
                "h.task_card, " +
                "w.rfo_no, " +
                "w.customer, " +
                "h.pn, " +
                "h.sn, " +
                "h.created_by, " +
                "h.legacy_batch, " +
                "h.transaction_no, " +
                "h.svo_no, " +
                "h.INTERNAL_EXTERNAL, " + 
                "CASE " +
                "    WHEN H.STATE_OF_PART = 'SERVICEABLE' THEN hd.qty_available " +
                "    WHEN H.STATE_OF_PART = 'UNSERVICEABLE' THEN hd.QTY_US " +
                "    ELSE NULL " +
                "END AS QTY " +
                "FROM wo w " +
                "JOIN wo_task_card wt ON wt.wo = w.wo " +
                "JOIN pn_inventory_history h ON wt.wo = h.wo " +
                "JOIN PN_INVENTORY_DETAIL hd ON hd.BATCH = h.BATCH " +
                "AND wt.task_card = h.task_card " +
                "JOIN pn_master pm ON h.pn = pm.pn " +
                "JOIN system_tran_code s ON w.source_type = s.system_code " +
                "WHERE h.interface_transfer_flag = 'X' " +
                "AND (pm.category = 'A' " +
                "OR (pm.category IN ('B', 'D') " +
                "AND NOT EXISTS ( " +
                "SELECT 1 FROM zepartser_master z " +
                "WHERE z.customer = w.customer " +
                "AND z.pn = wt.pn)) " +
                "OR (pm.category IN ('B', 'D') " +
                "AND EXISTS ( " +
                "SELECT 1 FROM zepartser_master z " +
                "WHERE z.customer = w.customer " +
                "AND z.pn = wt.pn))) " +
                "AND s.system_transaction = 'SOURCETYPE' " +
                "AND (s.party = '1P' OR s.party = '3P')" +
                "AND (h.TRANSACTION_TYPE = 'N/L/A REMOVED' OR h.TRANSACTION_TYPE = 'N/L/A INSPECTED') " +
                "AND h.STATUS = 'CLOSED' " +
                "AND h.STATE_OF_PART = 'UNSERVICEABLE' " +
                "AND h.made_as_ccs IS NOT NULL";
		
		String sqlMark = "UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_FLAG = 'Y' WHERE WO = ? AND TASK_CARD = ? AND PN = ? AND TRANSACTION_NO = ?";
		
		
		PreparedStatement pstmt1 = null;
		 ResultSet rs1 = null;
		 PreparedStatement pstmt2 = null;
		 ResultSet rs2 = null;
		 
		 try {
			pstmt1 = con.prepareStatement(sqlPN);
			pstmt2 = con.prepareStatement(sqlMark);
			

		      rs1 = pstmt1.executeQuery();
		      
		      if(rs1 != null) {
		    	  while(rs1.next()) {
		    		  logger.info("Porcessing PN: " +rs1.getString(6) + "Customer: " +rs1.getString(5));
		    		  INT22_SND req = new INT22_SND();
		    		  
		    		  if(rs1.getString(1) != null) {
		    			  req.setLocation(rs1.getString(1));
		    		  }else {
		    			  req.setLocation("");
		    		  }
		    		  
		    		  if(rs1.getString(2) != null) {
		    			  req.setWo(rs1.getString(2));
		    		  }else {
		    			  req.setWo("");
		    		  }
		    		  
		    		  if (rs1.getString(6) != null) {
		    			  req.setPn(rs1.getString(6));
		    		  } else {
		    			  req.setPn("");
		    		  }
		    		  
		    		  if(rs1.getString(7) != null) {
		    			  req.setPnSn(rs1.getString(7));
		    		  } else {
		    			  req.setPnSn("");
		    		  }
		    		  
		    		  if(rs1.getString(11) != null) {
		    			  req.setSvoNo(rs1.getString(11));
		    		  } else {
		    			  req.setSvoNo("");
		    		  }
		    		  
		    		  if(rs1.getString(5) != null) {
		    			  req.setRelationCode(rs1.getString(5));
		    		  } else {
		    			  req.setRelationCode("");
		    		  }
		    		  
		    		  if(rs1.getString(13) != null && !rs1.getString(12).isEmpty()) {
						    String internalExternal = rs1.getString(12);
						    
						    if (internalExternal.equalsIgnoreCase("Internal")) {
						    	req.setInternalExternal("I");
						    } else if (internalExternal.equalsIgnoreCase("External")) {
						    	req.setInternalExternal("E");
						    } else {
						        // Handle other cases if necessary, or leave as it is
						    	req.setInternalExternal(internalExternal);
						    }
						} else {
							req.setInternalExternal("");
						}
		    		  
		    		  if(rs1.getString(3) != null) {
		    			  req.setTc(rs1.getString(3));
		    		  } else {
		    			  req.setTc("");
		    		  }
		    		  
		    		  if(rs1.getString(10) != null) {
		    			  req.setTransaction(rs1.getString(10));
		    		  } else {
		    			  req.setTransaction("");
		    		  }
		    		  
		    		  if(rs1.getString(9) != null) {
		    			  req.setLegacyBatch(rs1.getString(9));
		    		  } else {
		    			  req.setLegacyBatch("");
		    		  }
		    		  
		    		  req.setRfoNo(rs1.getString(4));
		    		  
		    		  if(rs1.getString(13) != null) {
		    			  req.setQty(rs1.getString(13));
		    		  } else {
		    			  req.setQty("");
		    		  }

		    		  list.add(req);

		    		  
						pstmt2.setString(1, req.getWo());
						pstmt2.setString(2, req.getTc());
						pstmt2.setString(3, req.getPn());
						pstmt2.setString(4, req.getTransaction());
						pstmt2.executeQuery();
		    	  }
		      }
		      if (rs1 != null && !rs1.isClosed()) rs1.close();
		      
		 }catch (Exception e) {
		      e.printStackTrace();
		      executed = e.toString();
		      CreationU_RFO_Controller.addError(e.toString());

		      logger.severe(executed);
		      throw new Exception("Issue found");
		}finally {
		      if (rs1 != null && !rs1.isClosed()) rs1.close();
		      if (pstmt1 != null && !pstmt1.isClosed()) pstmt1.close();
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
