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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBException;

import trax.aero.controller.Part_Requisition_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.Component_TRAX;
import trax.aero.pojo.INT13_SND;
import trax.aero.pojo.OrderSND;
import trax.aero.pojo.OrderComponentSND;
import trax.aero.pojo.INT13_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.ErrorType;

public class Part_Requisition_Data {
	
	EntityManagerFactory factory;
	EntityManager em;
	String executed;
	private Connection con;
	
	final String MaxRecord = System.getProperty("Part_REQ_MaxRecord");
	Logger logger = LogManager.getLogger("Part_REQ");
	
	public Part_Requisition_Data(String mark) {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " +String.valueOf(!this.con.isClosed()));
			}
		} catch(SQLException e) {
			logger.severe("An error ocurred getting the status of the connection");
			Part_Requisition_Controller.addError(e.toString());
		} catch (CustomizeHandledException e1) {
			Part_Requisition_Controller.addError(e1.toString());
		} catch (Exception e) {
			Part_Requisition_Controller.addError(e.toString());
		}
	}
	
	public Part_Requisition_Data(){
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!this.con.isClosed()));
			}
		} catch (SQLException e) {
		      logger.severe("An error occured getting the status of the connection");
		      Part_Requisition_Controller.addError(e.toString());
		    } catch (CustomizeHandledException e1) {
		      Part_Requisition_Controller.addError(e1.toString());
		    } catch (Exception e) {
		     Part_Requisition_Controller.addError(e.toString());
		    }
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		em = factory.createEntityManager();
	}
	
	public Connection getCon() {
		return con;
	}
	
	public String markSendData() throws JAXBException
	{
	  INT13_TRAX request = new INT13_TRAX();
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
	
	public String markTransaction(INT13_TRAX request) {
		executed = "OK";
		
		String sqlDate = "UPDATE REQUISITION_HEADER SET INTERFACE_TRANSFERRED_DATE_ESD = sysdate WHERE INTERFACE_TRANSFERRED_DATE_ESD IS NULL AND REQUISITION = ?";
		String sqlDate2 = "UPDATE REQUISITION_DETAIL SET INTERFACE_TRANSFERRED_DATE_ESD = sysdate WHERE INTERFACE_TRANSFERRED_DATE_ESD IS NULL AND REQUISITION = ? AND REQUISITION_LINE = ?";
		String sqlPR = "UPDATE REQUISITION_DETAIL SET PR_NO = ?, PR_ITEM = ? WHERE REQUISITION = ? AND REQUISITION_LINE = ?";
		//String sqlUpdateREQD = "UPDATE REQUISITION_DETAIL SET STATUS = 'CLOSED' WHERE REQUISITION = ? AND REQUISITION_LINE = ? AND PR_NO IS NOT NULL AND PR_ITEM IS NOT NULL";
		//String sqlUpdateREQH = "UPDATE REQUISITION_HEADER RH SET RH.STATUS = 'CLOSED' WHERE RH.REQUISITION IN (SELECT RD.REQUISITION FROM REQUISITION_DETAIL RD WHERE RD.PR_NO IS NOT NULL AND RD.PR_ITEM IS NOT NULL GROUP BY RD.REQUISITION HAVING COUNT(*) = COUNT(CASE WHEN RD.STATUS = 'CLOSED' THEN 1 END))";
		String sqlCheckWOStatus = "SELECT W.STATUS FROM WO W, REQUISITION_HEADER R WHERE R.REQUISITION = ? and W.WO = R.WO";
		//String sqlUpdateReqStatus = "UPDATE REQUISITION_HEADER SET STATUS = 'CLOSED' WHERE REQUISITION = ?";
		String sqlInsertError = "INSERT INTO interface_audit (TRANSACTION, TRANSACTION_TYPE, ORDER_NUMBER, EO, TRANSACTION_OBJECT, TRANSACTION_DATE, CREATED_BY, MODIFIED_BY, EXCEPTION_ID, EXCEPTION_BY_TRAX, EXCEPTION_DETAIL, EXCEPTION_CLASS_TRAX, CREATED_DATE, MODIFIED_DATE) "
	            + "SELECT seq_interface_audit.NEXTVAL, 'ERROR', ?, ?, 'I13', sysdate, 'TRAX_IFACE', 'TRAX_IFACE', ?, 'Y', ?, 'Part_Requistion I_13', sysdate, sysdate FROM dual";
	    String sqlDeleteError = "DELETE FROM interface_audit WHERE ORDER_NUMBER = ? AND EO = ? ";
	    String sqlunMark = "UPDATE REQUISITION_DETAIL SET INTERFACE_TRANSFERRED_DATE_ESD = NULL WHERE REQUISITION = ? AND REQUISITION_LINE =?";
		
		try(PreparedStatement pstmt1 = con.prepareStatement(sqlDate);
			PreparedStatement pstmt2 = con.prepareStatement(sqlDate2);
			PreparedStatement pstmt3 = con.prepareStatement(sqlPR);
			PreparedStatement psInsertError = con.prepareStatement(sqlInsertError);
		    PreparedStatement psDeleteError = con.prepareStatement(sqlDeleteError);
		    PreparedStatement ps1 = con.prepareStatement(sqlunMark);
			//PreparedStatement pstmt4 = con.prepareStatement(sqlUpdateREQD);
		   // PreparedStatement pstmt5 = con.prepareStatement(sqlUpdateREQH);
			PreparedStatement pstmt6 = con.prepareStatement(sqlCheckWOStatus)
			/*PreparedStatement pstmt7 = con.prepareStatement(sqlUpdateReqStatus)*/){
			
			for(Component_TRAX c : request.getComponent()) {
				if(request != null) {
					pstmt1.setString(1, c.getRequisition());
					pstmt1.executeUpdate();
					
					pstmt2.setString(1, c.getRequisition());
					pstmt2.setString(2, c.getRequisitionLine());
					pstmt2.executeUpdate();
					
					if (c.getPRnumber() != null && !c.getPRnumber().isEmpty() && c.getPRitem() != null && !c.getPRitem().isEmpty()) {
						pstmt3.setString(1, c.getPRnumber());
						pstmt3.setString(2, c.getPRitem());
						pstmt3.setString(3, c.getRequisition());
						pstmt3.setString(4, c.getRequisitionLine());
						pstmt3.executeUpdate();
					}
					
					/*if (c.getPRnumber() != null && !c.getPRnumber().isEmpty() && c.getPRitem() != null && !c.getPRitem().isEmpty()) {
		                pstmt4.setString(1, c.getRequisition());
		                pstmt4.setString(2, c.getRequisitionLine());
		                pstmt4.executeUpdate();
		            }
					
					pstmt5.executeUpdate();
					
					pstmt6.setString(1, c.getRequisition());
					ResultSet rs = pstmt6.executeQuery();
					if(rs.next() && "CLOSED".equalsIgnoreCase(rs.getString(1))) {
						pstmt7.setString(1, c.getRequisition());
						pstmt7.executeUpdate();
					}*/
					
					String errorCode = request.getExceptionId();
					if (errorCode != null && !errorCode.equalsIgnoreCase("53") &&
		            	    (request.getExceptionDetail().toLowerCase().contains("is locked".toLowerCase()) ||
		            	     request.getExceptionDetail().toLowerCase().contains("already being processed".toLowerCase()))) {
						executed = "Request PR number: " + c.getPRnumber() + ", Error Code: " + c.getPRitem() + ", Error Code: " + request.getExceptionId() + ", Remarks: " + request.getExceptionDetail();
						Part_Requisition_Controller.addError(executed);
						
						psDeleteError.setString(1, c.getRequisition());
						psDeleteError.setString(2, request.getRFO());
		                psDeleteError.executeUpdate();
		                
		                psInsertError.setString(1, c.getRequisition());
		                psInsertError.setString(2, request.getRFO());
		                psInsertError.setString(3, errorCode);
		                psInsertError.setString(4, request.getExceptionDetail());
		                psInsertError.executeUpdate();
						
		                String key = request.getRFO() + "-" + c.getRequisition();
	                    int attempt = attemptCounts.getOrDefault(key, 0);
						
	                    if (attempt < 3) {
		                    attempt++;
		                    attemptCounts.put(key, attempt);

		                    try {
		                    	Thread.sleep(300000); 
		                        logger.info("REQUISTION: " + c.getRequisition() + " REQUISITION_LINE: " + c.getRequisitionLine());
		                        ps1.setString(1, c.getRequisition());
		                        ps1.setString(2, c.getRequisitionLine());
		                        ps1.executeUpdate();

		                        if (attempt > 3) {
		                            executed = "Failed after 3 attempts: Error Code: " + errorCode + ", Remarks: " + request.getExceptionDetail();
		                            Part_Requisition_Controller.addError(executed);
		                            logger.severe(executed);
		                        }
		                    } catch (InterruptedException ie) {
		                        Thread.currentThread().interrupt();
		                        executed = "Thread was interrupted: " + ie.toString();
		                        Part_Requisition_Controller.addError(executed);
		                        logger.severe(executed);
		                        return executed;
		                    } catch (SQLException e) {
		                        executed = e.toString();
		                        Part_Requisition_Controller.addError(executed);
		                        logger.severe(executed);
		                        return executed;
		                    }
		                } else {
		                    executed = "Failed after 3 attempts: Error Code: " + errorCode + ", Remarks: " + request.getExceptionDetail();
		                    Part_Requisition_Controller.addError(executed);
		                    logger.severe(executed);
		                }
		            } else {
		            	psDeleteError.setString(1, c.getRequisition());
						psDeleteError.setString(2, request.getRFO());
		                psDeleteError.executeUpdate();
		            }
					
				}
			}
			
		} catch (SQLException e) {
	        executed = e.toString();
	        Part_Requisition_Controller.addError(executed);
	        logger.severe(executed);
	    } 
		
		return executed;
	}
	
	
	public ArrayList<INT13_SND> getRequisiton() throws Exception {
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

	    ArrayList<INT13_SND> list = new ArrayList<INT13_SND>();
	    String sqlRequisition ="SELECT DISTINCT RD.REQUISITION, RD.REQUISITION_LINE, RD.PN, WS.PN_SN, RD.QTY_REQUIRE, R.WO, R.TASK_CARD, W.LOCATION, \r\n" +
	                           "W.RFO_NO, (SELECT WTI.OPS_NO FROM WO_TASK_CARD_ITEM WTI WHERE WTI.WO = R.WO AND WTI.TASK_CARD = R.TASK_CARD FETCH FIRST ROW ONLY) AS OPS_NO, \r\n" + 
	                           "RD.PR_NO, RD.PR_ITEM, W.SITE FROM REQUISITION_DETAIL RD INNER JOIN REQUISITION_HEADER R ON R.REQUISITION = RD.REQUISITION \r\n" +
	                           "INNER JOIN WO W ON W.WO = R.WO INNER JOIN WO_TASK_CARD WT ON WT.WO = R.WO AND WT.TASK_CARD = R.TASK_CARD \r\n" +
	                           "INNER JOIN WO_TASK_CARD_ITEM WTI ON WTI.WO = R.WO AND WT.TASK_CARD = R.TASK_CARD INNER JOIN WO_SHOP_DETAIL WS ON WS.WO = W.WO \r\n" + 
	                           "WHERE RD.STATUS = 'OPEN' AND RD.INTERFACE_TRANSFERRED_DATE_ESD IS NULL AND W.RFO_NO IS NOT NULL AND RAISE_PR ='Y'";

	    String sqlMark = "UPDATE REQUISITION_DETAIL SET INTERFACE_TRANSFERRED_DATE_ESD = SYSDATE WHERE REQUISITION = ? AND REQUISITION_LINE =?";

	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        sqlRequisition = "SELECT * FROM (" + sqlRequisition;
	    }

	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        sqlRequisition = sqlRequisition + " ) WHERE ROWNUM <= ?";
	    }

	    PreparedStatement pstmt1 = null;
	    ResultSet rs1 = null;
	    PreparedStatement pstmt2 = null;

	    try {
	        pstmt1 = con.prepareStatement(sqlRequisition);
	        pstmt2 = con.prepareStatement(sqlMark);

	        if (MaxRecord != null && !MaxRecord.isEmpty()) {
	            pstmt1.setString(1, MaxRecord);
	        }

	        rs1 = pstmt1.executeQuery();

	        INT13_SND req = null;
	        OrderSND Inbound = null;
	        ArrayList<OrderComponentSND> oclist = null;

	        if (rs1 != null) {
	            while (rs1.next()) {
	        
	                String currentOrderNo = rs1.getString(9); // RFO_NO
	                if (Inbound == null || !Inbound.getOrderNO().equals(currentOrderNo)) {
	                    Inbound = new OrderSND();
	                    oclist = new ArrayList<OrderComponentSND>();
	                    Inbound.setComponents(oclist);
	                    Inbound.setOrderNO(currentOrderNo != null ? currentOrderNo : ""); 

	                    if (req == null) {
	                        req = new INT13_SND();
	                        ArrayList<OrderSND> orlist = new ArrayList<OrderSND>();
	                        req.setOrder(orlist);
	                    }

	                    req.getOrder().add(Inbound); 
	                }

	             
	                OrderComponentSND InboundC = new OrderComponentSND();
	                InboundC.setWO_location(rs1.getString(8));

	                InboundC.setRequisition(rs1.getString(1) != null ? rs1.getString(1) : "");
	                InboundC.setRequisitionLine(rs1.getString(2) != null ? rs1.getString(2) : "");
	                InboundC.setACT(rs1.getString(10) != null ? rs1.getString(10) : "");
	                InboundC.setGoodsRecipient(rs1.getString(13));
	                InboundC.setTC_number(rs1.getString(7) != null ? rs1.getString(7) : "");
	                InboundC.setMaterialPartNumber(rs1.getString(3));
	                InboundC.setWoSN(rs1.getString(4) != null ? rs1.getString(4) : "");
	                InboundC.setQuantity(rs1.getString(5));
	                InboundC.setPRnumber(rs1.getString(11) != null ? rs1.getString(11) : "");
	                InboundC.setPRitem(rs1.getString(12) != null ? rs1.getString(12) : "");

	             
	                Inbound.getComponents().add(InboundC);

	           
	                pstmt2.setString(1, InboundC.getRequisition());
	                pstmt2.setString(2, InboundC.getRequisitionLine());
	                pstmt2.executeQuery();
	            }
	        }

	        if (req != null) {
	            list.add(req);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        executed = e.toString();
	        Part_Requisition_Controller.addError(e.toString());

	        logger.severe(executed);
	        throw new Exception("Issue found");
	    } finally {
	        if (rs1 != null && !rs1.isClosed()) rs1.close();
	        if (pstmt1 != null && !pstmt1.isClosed()) pstmt1.close();
	    }

	    return list;
	}

	
private String getRecepient(String site) {
		
		String query = "",group  = "";
		
		query = " Select recipient FROM SITE_RECIPIENT_MASTER where site = ?";
				
		try
		{
			group = (String) em.createNativeQuery(query).setParameter(1, site).getSingleResult();	
			return group;
		}
		catch (Exception e) 
		{
			//e.printStackTrace();
			logger.severe("An Exception occurred executing the query to get the recipient. " + "\n error: " + e.toString());
		}
		return group;
	}

	private String getSAPSite(String wo) {
		String site = " ";
		
		String query = " SELECT location FROM WO where wo = ?";	
		try
		{
			site = (String) em.createNativeQuery(query).setParameter(1, wo.toString()).getSingleResult();	
			
		}
		
		catch (Exception e) 
		{
			logger.severe("An Exception occurred executing the query to get the site. " + "\n error: " +  e.toString());
		}
		
		return site;
	}
	
	public String setSite(String site, String recipient) throws Exception{
		String Exceuted = "OK";
		String query = "INSERT INTO SITE_RECIPIENT_MASTER (SITE, \"RECIPIENT\") VALUES (?, ?)";
		
		PreparedStatement ps = null;
		try
		{
			if (con == null || con.isClosed()) {
		        con = DataSourceClient.getConnection();
		        logger.severe(
		          "The connection was stablished successfully with status: " +
		          String.valueOf(!con.isClosed())
		        );
		      }
			
			ps = con.prepareStatement(query);

		      ps.setString(1, site);
		      ps.setString(2, recipient);

		      ps.executeUpdate();
		}
		catch (Exception e) 
		{
			logger.severe("An Exception occurred executing the query to set the site recipient. " + "\n error: " + e.toString() );
			throw new Exception("An Exception occurred executing the query to set the site recipient. " + "\n error: " + e.toString());
		}
		
		return Exceuted;
	}
	
	public String deleteSite( String site) throws Exception{
		String Exceuted = "OK";
		String query = "DELETE SITE_RECIPIENT_MASTER where site = ?";		
		 PreparedStatement ps = null;
		    
		    try {
		    	if(con == null || con.isClosed()) {
		    		con = DataSourceClient.getConnection();
		    		logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
		    	}
		    	
		    	ps = con.prepareStatement(query);
		    	
			    ps.setString(1, site);
			    
			    ps.executeUpdate();
			    }
		catch (Exception e) 
		{
			logger.severe("An Exception occurred executing the query to delete the site . " + "\n error: " + e.toString());
			throw new Exception("An Exception occurred executing the query to delete the site. " + "\n error: " + e.toString());
		}
		
		return Exceuted;
	}
	
	
	public String getSite( String site) throws Exception{
		
		ArrayList<String> groups = new ArrayList<String>();
		
		String query = "", group = "";
		if(site != null && !site.isEmpty()) {
			query = " Select recipient, site FROM SITE_RECIPIENT_MASTER where site = ?";
		}else {
			query = " Select recipient, site FROM SITE_RECIPIENT_MASTER";
		}
		try
		{
			
			
			
			List<Object[]>	rs = null;
			
			if(site != null && !site.isEmpty()) {
				rs = em.createNativeQuery(query).setParameter(1, site).getResultList();	
			}else {
				rs = em.createNativeQuery(query).getResultList();	
			}
			
			
			if (rs != null) 
			{
				for(Object[] a : rs )
				{
					
				groups.add("Recipient: "+a[0] + " Site: " +a[1] );

				}
			}
			
			
			
		}
		catch (Exception e) 
		{
			logger.severe("An Exception occurred executing the query to get the site recipient. " + "\n error: " + e.toString());
			throw new Exception("An Exception occurred executing the query to get the site recipient. " + "\n error: " +  e.toString());
		}
		for(String g : groups) {
			group = group + g +"\n";
			
		}
		
		return group;
		
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
