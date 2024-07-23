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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBException;

import trax.aero.controller.Actual_Hours_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.INT31_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.pojo.OrderSND;
import trax.aero.pojo.INT31_SND;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.ErrorType;

public class Actual_Hours_Data {
	
	EntityManagerFactory factory;
	EntityManager em;
	String executed;
	private Connection con;
	
	final String MaxRecord = System.getProperty("Actual_Hours_MaxRecords");
	Logger logger = LogManager.getLogger("ActualHours");
	
	public Actual_Hours_Data(String mark) {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " +String.valueOf(!this.con.isClosed()));
		}
	}catch(SQLException e) {
		logger.severe("An error ocurred getting the status of the connection");
		Actual_Hours_Controller.addError(e.toString());
	} catch (CustomizeHandledException e1) {
		Actual_Hours_Controller.addError(e1.toString());
	} catch (Exception e) {
		Actual_Hours_Controller.addError(e.toString());
	}
}	

	public Actual_Hours_Data() {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!this.con.isClosed()));
			}
		} catch (SQLException e) {
		      logger.severe("An error occured getting the status of the connection");
		      Actual_Hours_Controller.addError(e.toString());
		    } catch (CustomizeHandledException e1) {
		      Actual_Hours_Controller.addError(e1.toString());
		    } catch (Exception e) {
		      Actual_Hours_Controller.addError(e.toString());
		    }
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		em = factory.createEntityManager();
	}
	
	public Connection getCon() {
		return con;
	}
	
	/*public String markSendData() throws JAXBException
	{
	  INT31_TRAX request = new INT31_TRAX();
	  try {
	        markTransaction(request);
	        logger.info("markTransaction completed successfully.");
	        return "OK";
	    } catch (Exception e) {
	    	logger.log(Level.SEVERE, "Error executing markTransaction", e);
	    	e.printStackTrace();
	        return null; 
	    }
	}*/
	
	/*public String markTransaction(INT31_TRAX request) {
		executed = "OK";
		
		String sqlSelect = "select distinct wa.wo, wa.wo_actual_transaction from wo_actuals wa, wo w, wo_task_card_item wti where w.rfo_no= ? and wti.ops_no = ? and wa.wo = w.wo";
		String sqlUpdate = "UPDATE WO_ACTUALS SET INTERFACE_MODIFIED_DATE = SYSDATE WHERE WO = ? AND WO_ACTUAL_TRANSACTION = ?";
		
		try(PreparedStatement pstmt1 = con.prepareStatement(sqlSelect);
				PreparedStatement pstmt2 = con.prepareStatement(sqlUpdate)){
			if(request != null) {
				pstmt1.setString(1, request.getRfoNo());
				pstmt1.setString(2, request.getOpsNo());
				pstmt1.executeQuery();
				
				ResultSet rs1 = null;
				rs1 = pstmt1.executeQuery();
				
				String wo = rs1.getString(1);
				String transaction = rs1.getString(2);
				
				if (wo != null && transaction != null ) {
					pstmt2.setString(1, wo);
					pstmt2.setString(2, transaction);
					pstmt2.executeUpdate();
				}
				
				if (request.getExceptionId() != null && !request.getExceptionId().equalsIgnoreCase("53")) {
					executed = "Request Order number: " + request.getRfoNo() + ", Operation Line: " + request.getOpsNo() + ", Error Code: " + request.getExceptionId() + ", Remarks: " + request.getExceptionDetail();
					Actual_Hours_Controller.addError(executed);
				}
			}
		} catch (SQLException e) {
	        executed = e.toString();
	        Actual_Hours_Controller.addError(executed);
	        logger.severe(executed);
	    } 
		
		return executed;
	}*/
	
	
	public ArrayList<INT31_SND> getActualHours() throws Exception{
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
		
		ArrayList<INT31_SND> list = new ArrayList<INT31_SND>();
		ArrayList<OrderSND> orlist = new ArrayList<OrderSND>();
		
		String sqlActualHR = "SELECT DISTINCT W.RFO_NO, WTI.OPS_NO, WA.EMPLOYEE, ABS(WA.HOURS * 60 + WA.MINUTES) AS TOTAL_MINUTES, \r\n" +
							 "TO_CHAR(WA.TRANSACTION_DATE, 'DDMMYY') AS START_DATE, LPAD(TO_CHAR(WA.START_HOUR), 2, '0') || LPAD(TO_CHAR(WA.START_MINUTE), 2, '0') || '00' AS START_TIME, \r\n" +
							 "TO_CHAR(WA.TRANSACTION_DATE, 'DDMMYY') AS END_DATE, LPAD(TO_CHAR(WA.END_HOUR), 2, '0') || LPAD(TO_CHAR(WA.END_MINUTE), 2, '0') || '00' AS END_TIME, \r\n" +
							 "WA.WO_ACTUAL_TRANSACTION, WA.SKILL, CASE WHEN WT.STATUS = 'CLOSED' THEN 'X' ELSE '' END AS STATUS_INDICATOR, \r\n" +
							 "W.WO, WA.TASK_CARD FROM WO W INNER JOIN WO_ACTUALS WA ON W.WO = WA.WO INNER JOIN WO_TASK_CARD WT ON \r\n" + 
							 "WA.WO = WT.WO AND WA.TASK_CARD = WT.TASK_CARD INNER JOIN WO_TASK_CARD_ITEM WTI ON \r\n" +
							 "WA.WO = WTI.WO AND WA.TASK_CARD = WTI.TASK_CARD WHERE W.RFO_NO IS NOT NULL AND WTI.OPS_NO IS NOT NULL AND WA.INTERFACE_ESD_TRANSFERRED_FLAG IS NULL \r\n" +
							 "AND WA.INTERFACE_ESD_TRANSFERRED_DATE IS NULL AND WA.TRANSACTION_DATE IS NOT NULL";
		
		String sqlSkill = "SELECT WA.EMPLOYEE, CASE WHEN RM.MECHANIC_STAMP = 'INSPECTOR' THEN 'TECNT' ELSE 'ENGNT' END AS ACTIVITY_TYPE \r\n" +
						  "FROM WO_ACTUALS WA JOIN RELATION_MASTER RM ON WA.EMPLOYEE = RM.RELATION_CODE JOIN EMPLOYEE_CONTROL EC ON WA.EMPLOYEE = EC.EMPLOYEE \r\n" +
						  "WHERE WA.WO = ? AND WA.WO_ACTUAL_TRANSACTION = ?";
		
		String sqlMark = "UPDATE WO_ACTUALS SET INTERFACE_ESD_TRANSFERRED_FLAG = 'Y', INTERFACE_ESD_TRANSFERRED_DATE = SYSDATE WHERE WO = ? AND WO_ACTUAL_TRANSACTION = ?";
		
		String sqlMark2 = "UPDATE WO_TASK_CARD SET INTERFACE_SAP_TRANSFERRED_DATE = SYSDATE, INTERFACE_SAP_TRANSFERRED_FLAG = '3'  WHERE WO = ? AND TASK_CARD = ?";
		
		if (MaxRecord != null && !MaxRecord.isEmpty()) {
			sqlActualHR = "SELECT * FROM (" + sqlActualHR;
		}
		
		if (MaxRecord != null && !MaxRecord.isEmpty()) {
			sqlActualHR = sqlActualHR + " ) WHERE ROWNUM <= ?";
		}
		
		 PreparedStatement pstmt1 = null;
		 ResultSet rs1 = null;
		 PreparedStatement pstmt2 = null;
		 ResultSet rs2 = null;
		 PreparedStatement pstmt3 = null;
		 ResultSet rs3 = null;
		 PreparedStatement pstmt4 = null;
		 ResultSet rs4 = null;
		 try {
			pstmt1 = con.prepareStatement(sqlActualHR);
			pstmt2 = con.prepareStatement(sqlSkill);
			pstmt3 = con.prepareStatement(sqlMark);
			pstmt4 = con.prepareStatement(sqlMark2);
			
			if(MaxRecord != null && !MaxRecord.isEmpty()) {
				pstmt1.setString(1, MaxRecord);
			}
			
			rs1 = pstmt1.executeQuery();
			
			if(rs1 != null) {
				while(rs1.next()) {
					logger.info("Processing Order Number: " + rs1.getString(1) + ", Operation Number: " + rs1.getString(2));
					INT31_SND req = new INT31_SND();
					orlist = new ArrayList<OrderSND>();
					req.setOrder(orlist);
					OrderSND Inbound = new OrderSND();
					
					String wo = rs1.getString(12);
					String tc = rs1.getString(13);
					
					if(rs1.getString(1) != null) {
						Inbound.setOrderNo(rs1.getString(1));
					} else {
						Inbound.setOrderNo("");
					}
					
					if(rs1.getString(2) != null) {
						Inbound.setOperationLine(rs1.getNString(2));
					} else {
						Inbound.setOperationLine("");
					}
					
					Inbound.setWorkCenter("");
					
					Inbound.setPlant("");
					
					logger.info("Personel Number: " + rs1.getString(3) + ", Actual Hours: " + rs1.getString(4));
					
					if(rs1.getString(3) != null) {
						Inbound.setPersonnelNumber(rs1.getString(3));
					} else {
						Inbound.setPersonnelNumber("");
					}
					
					if(rs1.getString(4) != null) {
						Inbound.setActualWork(rs1.getString(4));
					} else {
						Inbound.setActualWork("00");
					}
					
					Inbound.setUnitForWork("MIN");
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
					
					Inbound.setPostingDate(LocalDate.now().format(formatter));
					
					
					if(rs1.getString(11) != null) {
					Inbound.setFinalConfirmation(rs1.getString(11));
					} else {
						Inbound.setFinalConfirmation("");
					}
					
					logger.info("Start Date: " + rs1.getString(5) + ", Time: " + rs1.getString(6) + ", Finish Date: " + rs1.getString(7) + ", Time: " +rs1.getString(8));
					
					if(rs1.getString(5) != null) {
						Inbound.setActualStartDate(rs1.getString(5));
					} else {
						Inbound.setActualStartDate("");
					}
					
					if(rs1.getString(6) != null) {
						Inbound.setActualStartTime(rs1.getString(6));
					} else {
						Inbound.setActualStartTime("");
					}
					
					if(rs1.getString(7) != null) {
						Inbound.setActualFinishDate(rs1.getString(7));
					}else {
						Inbound.setActualFinishDate("");
					}
					
					if(rs1.getString(8) != null) {
						Inbound.setActualFinishTime(rs1.getString(8));
					}else {
						Inbound.setActualFinishTime("");
					}
					
					logger.info("Transaction: " + rs1.getString(9));
					if(rs1.getString(9) != null) {
						Inbound.setWoActualTransaction(rs1.getString(9));
					}else {
						Inbound.setWoActualTransaction("");
					}
					
					pstmt2.setString(1, wo);
					pstmt2.setString(2, Inbound.getWoActualTransaction());
					rs2 = pstmt2.executeQuery();
					
					if(rs2 != null && rs2.next()) {
						logger.info("Employee : " + rs2.getString(1) + ", Activity Type: " + rs2.getString(2));
						
						if(rs2.getString(2) != null) {
							Inbound.setActivityType(rs2.getString(2));
						} else {
							Inbound.setActivityType("");
						}
						
					} else {
						Inbound.setActivityType("");
					}
					if (rs2 != null && !rs2.isClosed()) {
		        	    rs2.close();
		        	}
					
					req.getOrder().add(Inbound);
					list.add(req);
					
					pstmt3.setString(1, wo);
					pstmt3.setString(2, Inbound.getWoActualTransaction());
					pstmt3.executeQuery();
					
					pstmt4.setString(1, wo);
					pstmt4.setString(2, tc);
					pstmt4.executeQuery();
					
				}
			}
			if (rs1 != null && !rs1.isClosed()) rs1.close();
			
		 }catch (Exception e) {
		      e.printStackTrace();
		      executed = e.toString();
		      Actual_Hours_Controller.addError(e.toString());

		      logger.severe(executed);
		      throw new Exception("Issue found");
		}finally {
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
