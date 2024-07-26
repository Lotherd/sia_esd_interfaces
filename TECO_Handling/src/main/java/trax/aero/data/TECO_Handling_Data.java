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
	
	final String MaxRecord = System.getProperty("TECO_MaxRecord");
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
			markTransaction(request);
	        logger.info("markTransaction completed successfully.");
	        return "OK";
		}catch (Exception e) {
	    	logger.log(Level.SEVERE, "Error executing markTransaction", e);
	    	e.printStackTrace();
	        return null; 
	    }
	}
	
	public String markTransaction(INT15_TRAX request) {
		executed = "OK";
		
		String sqlUpdate = "UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_DATE = SYSDATE WHERE WO = ?";
		
		try {
			PreparedStatement pstmt1 = con.prepareStatement(sqlUpdate);
			
			if (request != null) {
				
				if(request.getWO() != null && !request.getWO().isEmpty()) {
				pstmt1.setString(1, request.getWO());
				pstmt1.executeUpdate();
				}
				
				if (request.getExceptionId() != null && !request.getExceptionId().equalsIgnoreCase("53")) {
					executed = "WO: " + request.getWO() + ", SVO/RFO: " + request.getRFO_NO() + ", Error Code: " + request.getExceptionId() + ", Remarks: " + request.getExceptionDetail();
				}
			}
			
			
		}catch (SQLException e) {
	        executed = e.toString();
	        TECO_Handling_Controller.addError(executed);
	        logger.severe(executed);
	    } 
		
		return executed;
	}
	
	
	
	public ArrayList<INT15_SND> getSVO() throws Exception {
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

	    String sqlSVO = "SELECT DISTINCT ATH.SVO_NO, W.WO, TO_CHAR(W.COMPLETION_DATE, 'DD-MM-YYYY') AS COMPLETION_DATE, "
	            + "TO_CHAR(W.COMPLETION_TIME, 'HH24:MI:SS') AS COMPLETION_TIME, W.STATUS, W.REOPEN_REASON, W.SOURCE_REF, "
	            + "WT.TASK_CARD, ATH.TRANSACTION_NO, W.SOURCE_TYPE "
	            + "FROM WO W, WO_TASK_CARD WT, PN_INVENTORY_HISTORY ATH "
	            + "WHERE W.WO = WT.WO AND W.WO = ATH.WO AND WT.TASK_CARD = ATH.TASK_CARD AND W.RFO_NO IS NOT NULL "
	            + "AND (W.STATUS = 'CLOSED' OR W.STATUS = 'CANCEL' OR (W.STATUS = 'OPEN' AND W.REOPEN_REASON IS NOT NULL)) "
	            + "AND ATH.INTERFACE_TRANSFER_FLAG IS NULL AND ATH.TRANSACTION_TYPE = 'REMOVE'";

	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        sqlSVO = "SELECT * FROM (" + sqlSVO + ") WHERE ROWNUM <= ?";
	    }

	    String sqlMark = "UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_FLAG = 'D' WHERE SVO_NO = ? AND TRANSACTION_NO = ?";

	    try (PreparedStatement pstmt1 = con.prepareStatement(sqlSVO);
	         PreparedStatement pstmt2 = con.prepareStatement(sqlMark)) {

	        if (MaxRecord != null && !MaxRecord.isEmpty()) {
	            pstmt1.setString(1, MaxRecord);
	        }

	        try (ResultSet rs1 = pstmt1.executeQuery()) {
	            while (rs1.next()) {
	                logger.info("Processing SVO: " + rs1.getString(1));
	                INT15_SND req = new INT15_SND();

	                req.setSAP_number(rs1.getString(1));
	                req.setWO(rs1.getString(2));
	                req.setWO_Completion_date(rs1.getString(3) != null ? rs1.getString(3) : "");
	                req.setWO_Completion_time(rs1.getString(4) != null ? rs1.getString(4) : "");
	                req.setStatus(rs1.getString(5));
	                req.setReason_teco(rs1.getString(6));
	                req.setNotification_number(rs1.getString(7) != null && rs1.getString(5).equals("CLOSED") && rs1.getString(10).equals("E4") ? rs1.getString(7) : "");
	                req.setTC_number(rs1.getString(8) != null ? rs1.getString(8) : "");
	                req.setTransaction(rs1.getString(9) != null ? rs1.getString(9) : "");
	                req.setFlag("Y");

	                list.add(req);

	                pstmt2.setString(1, rs1.getString(1));
	                pstmt2.setString(2, rs1.getString(9));
	                pstmt2.executeUpdate();
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        executed = e.toString();
	        TECO_Handling_Controller.addError(e.toString());
	        logger.severe(executed);
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

	    String sqlRFO = "SELECT DISTINCT " +
                "    w.rfo_no, " +
                "    w.wo, " +
                "    TO_CHAR(w.completion_date, 'DD-MM-YYYY') AS completion_date, " +
                "    TO_CHAR(w.completion_date, 'HH24:MI:SS') AS completion_time, " +
                "    w.status, " +
                "    w.reopen_reason, " +
                "    w.source_ref, " +
                "    wt.task_card, " +
                "    w.source_type " +
                "FROM " +
                "    wo w " +
                "    JOIN wo_task_card wt ON w.wo = wt.wo " +
                "    JOIN pn_inventory_history ath ON w.wo = ath.wo AND wt.task_card = ath.task_card " +
                "WHERE " +
                "    w.rfo_no IS NOT NULL " +
                "    AND (w.status = 'CLOSED' " +
                "         OR w.status = 'CANCEL' " +
                "         OR (w.status = 'OPEN' " +
                "             AND w.reopen_reason IS NOT NULL)) " +
                "    AND ath.svo_no IS NOT NULL " +
                "    AND ath.interface_transfer_flag = 'D' " +
                "    AND ath.transaction_type = 'REMOVE' " +
                "    AND NOT EXISTS ( " +
                "        SELECT 1 " +
                "        FROM pn_inventory_history ath_inner " +
                "        WHERE ath_inner.wo = w.wo " +
                "            AND ath_inner.interface_transfer_flag = 'N') " +
                "UNION ALL " +
                "SELECT DISTINCT " +
                "    w.rfo_no, " +
                "    w.wo, " +
                "    TO_CHAR(w.completion_date, 'DD-MM-YYYY') AS completion_date, " +
                "    TO_CHAR(w.completion_date, 'HH24:MI:SS') AS completion_time, " +
                "    w.status, " +
                "    NULL AS reopen_reason, " +
                "    w.source_ref, " +
                "    wt.task_card, " +
                "    w.source_type " +
                "FROM " +
                "    wo w " +
                "    JOIN wo_task_card wt ON w.wo = wt.wo " +
                "WHERE " +
                "    (w.status = 'CLOSED' " +
                "         OR w.status = 'CANCEL' " +
                "         OR (w.status = 'OPEN' " +
                "             AND w.reopen_reason IS NOT NULL)) " +
                "    AND w.rfo_no IS NOT NULL " +
                "    AND wt.task_card IN ( " +
                "        SELECT task_card " +
                "        FROM wo_task_card wt_inner " +
                "        WHERE wt_inner.wo = w.wo " +
                "            AND wt_inner.status = 'CLOSED')";


	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        sqlRFO = "SELECT * FROM (" + sqlRFO + ") WHERE ROWNUM <= ?";
	    }

	    String sqlMark = "UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_FLAG = 'Y' WHERE WO = ? AND TASK_CARD = ?";

	    try (PreparedStatement pstmt1 = con.prepareStatement(sqlRFO);
	         PreparedStatement pstmt2 = con.prepareStatement(sqlMark)) {

	        if (MaxRecord != null && !MaxRecord.isEmpty()) {
	            pstmt1.setString(1, MaxRecord);
	        }

	        try (ResultSet rs1 = pstmt1.executeQuery()) {
	            while (rs1.next()) {
	                logger.info("Processing RFO: " + rs1.getString(1));
	                INT15_SND req = new INT15_SND();

	                req.setSAP_number(rs1.getString(1));
	                req.setWO(rs1.getString(2));
	                req.setWO_Completion_date(rs1.getString(3) != null ? rs1.getString(3) : "");
	                req.setWO_Completion_time(rs1.getString(4) != null ? rs1.getString(4) : "");
	                req.setStatus(rs1.getString(5));
	                req.setReason_teco(rs1.getString(6));
	                req.setNotification_number(rs1.getString(7) != null && rs1.getString(5).equals("CLOSED") && rs1.getString(10).equals("E4") ? rs1.getString(7) : "");
	                req.setFlag("N");

	                list.add(req);

	                pstmt2.setString(1, rs1.getString(2));
	                pstmt2.setString(2, rs1.getString(8));
	                pstmt2.executeUpdate();
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        executed = e.toString();
	        TECO_Handling_Controller.addError(e.toString());
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
