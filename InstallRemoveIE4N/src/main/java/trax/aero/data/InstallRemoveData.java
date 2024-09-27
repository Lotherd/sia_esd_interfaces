package trax.aero.data;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.controller.InstallRemoveController;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.Application_Log;
import trax.aero.pojo.Header;
import trax.aero.pojo.IE4N;
import trax.aero.pojo.MT_TRAX_RCV_I43_4076_RES;
import trax.aero.pojo.MT_TRAX_SND_I43_4076_REQ;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.IE4NPoster;

public class InstallRemoveData {

			
			String exceuted;
			Logger logger = LogManager.getLogger("InstallRemove_I20");
			private Connection con;
			final String url = System.getProperty("InstallRemove_URL");
			final String party3 = System.getProperty("TaskCardsIngestion_3PValue");
			EntityManagerFactory factory;
			EntityManager em;
			public InterfaceLockMaster lock;
			
			public InstallRemoveData()
			{
				try 
				{
					if(this.con == null || this.con.isClosed())
					{
						this.con = DataSourceClient.getConnection();
						logger.info("The connection was stablished successfully with status: " + String.valueOf(!this.con.isClosed()));
					}			
				} 
				catch (SQLException e) 
				{
					logger.severe("An error occured getting the status of the connection");
					InstallRemoveController.addError(e.toString());
					
				}
				catch (CustomizeHandledException e) {
					
					InstallRemoveController.addError(e.toString());
					
				} catch (Exception e) {
					
					InstallRemoveController.addError(e.toString());
					
				}
				
			}
			
			public Connection getCon() {
				return con;
			}
	
			public String Button(IE4N b) throws Exception
			{
				//setting up variables
				exceuted = "OK";
				boolean isOkay = true;
				boolean success = true;
				
				IE4NPoster poster = new IE4NPoster();

				try 
				{
					MT_TRAX_SND_I43_4076_REQ out = new MT_TRAX_SND_I43_4076_REQ();
					try {
						
						throwValidate(b);
					}
					catch (Exception e)
					{
						InstallRemoveController.addError(e.toString());
						
						exceuted = e.toString();
						logger.severe(exceuted);
						isOkay = false;
					}

					if(check3P(b)) {
						
						MT_TRAX_RCV_I43_4076_RES input = new MT_TRAX_RCV_I43_4076_RES();
						
						input.setHeader(new Header());
						input.setApplication_Log(new ArrayList<Application_Log>());
						Application_Log log = new Application_Log();
						log.setMessage_Text("IE4N Not Applicable due to Third Party.");
						log.setMessage_Type("E");
						input.getApplication_Log().add(log);
						input.getHeader().setTRAXTRANS(b.getTransaction());
						input.getHeader().setReference_Order(getSVO(b));
						
						String message = "";
					    
					    for(Application_Log l: input.getApplication_Log()) {
						   
						    message = message +l.getMessage_Type()+ "-" +l.getMessage_Text()+ System.lineSeparator() +System.lineSeparator() ;	 
					    }
						
						markTransaction(input);	
						exceuted = "Received acknowledgement:" +System.lineSeparator() 
								+ message +System.lineSeparator() 
								+" Reference order: "+input.getHeader().getReference_Order() 
								+ " Transaction: " + input.getHeader().getTRAXTRANS() ;								
						InstallRemoveController.addError(exceuted ) ;
								
						logger.severe(exceuted);
						isOkay = false;
					}
					
					if(isOkay) {
						try {
							out = convertButtonToOutbound(b);
						}catch (Exception e) {
								InstallRemoveController.addError(e.toString());
								logger.severe(exceuted);
								exceuted = e.toString();
								isOkay = false;
							}
					}
					
					if(isOkay) {
						
						JAXBContext jc = JAXBContext.newInstance(MT_TRAX_SND_I43_4076_REQ.class);
						Marshaller marshaller = jc.createMarshaller();
						marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
						StringWriter sw = new StringWriter();
						marshaller.marshal(out, sw);
						
						logger.info("Ouput: " + sw.toString());
						
						success = poster.postIE4N(out, url);
						
						if(!success)
						{
							exceuted = "Unable to send Outbound with Reference Order: " + out.getReference_Order() + " to URL" + url;
							logger.severe(exceuted);
							InstallRemoveController.addError(exceuted); 
						}else {
							logger.info("POST status: " + String.valueOf(success) + " Reference Order: " + out.getReference_Order());
													
							MT_TRAX_RCV_I43_4076_RES input = null;
							
							try 
					        {    
								String body = poster.getBody();
								StringReader sr = new StringReader(body);				
								jc = JAXBContext.newInstance(MT_TRAX_RCV_I43_4076_RES.class);
						        Unmarshaller unmarshaller = jc.createUnmarshaller();
						        input = (MT_TRAX_RCV_I43_4076_RES) unmarshaller.unmarshal(sr);

						        marshaller = jc.createMarshaller();
						        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
						        sw = new StringWriter();
							    marshaller.marshal(input,sw);
							    logger.info("Input: " + sw.toString());
							    
							   
							    String message = "";
							    
							    for(Application_Log l: input.getApplication_Log()) {
								    if(l.getMessage_Type().equalsIgnoreCase("I")) {
								    	logger.info("SETTING MESSAGE TYPE: " +input.getApplication_Log().get(0).getMessage_Type() +" TO W");
								    	l.setMessage_Type("W");
								    }
								    message = message +l.getMessage_Type()+ "-" +l.getMessage_Text()+ System.lineSeparator() +System.lineSeparator() ;	 
							    }
							    
							   
							    markTransaction(input);
							    
							   	
								if(containsType(input.getApplication_Log(), "E")){
									
									exceuted = "Received acknowledgement:"+System.lineSeparator() 
									+ message +System.lineSeparator() 
									+" Reference order: "+input.getHeader().getReference_Order() 
									+" Transaction: " + input.getHeader().getTRAXTRANS() 
									;
									
									InstallRemoveController.addError(exceuted ) ;
																		
								}
								
							}
							catch(Exception e)
							{
								logger.severe(e.toString());
								exceuted = 	e.toString();
							}
							logger.info("finishing");
						}	
					}	
				}
				catch (Exception e) 
		        {
					InstallRemoveController.addError(e.toString()); 
		            exceuted = e.toString();
		            
		            logger.severe(exceuted);
				}finally {
					if(!con.isClosed())
						con.close();
				}
				return exceuted;
			}
			
			private void throwValidate(IE4N b)throws Exception {
				
				if(b.getTransaction() != null && b.getTransaction().isEmpty()) {
					throw new Exception("Error: Transaction is null or empty");
				}
				
			}

			private MT_TRAX_SND_I43_4076_REQ convertButtonToOutbound(IE4N item) throws Exception
			{
				
				String date,hour,minute, time = "hhmm00";
				Date Date;
				logger.info("Getting  PN infromation from Transaction " + item.getTransaction() );
				
				String sql = "SELECT\r\n" + 
						"APTH.PN,\r\n" + 
						"APTH.SN,\r\n" + 
						"APTH.POSITION,\r\n" + 
						"PKG_INTERFACE.GETXMLNUMBERSTRING(APTH.QTY) AS QTY,\r\n" + 
						"(SELECT PID.LEGACY_BATCH FROM PN_INVENTORY_DETAIL PID WHERE APTH.PN = PID.PN AND APTH.SN = PID.SN AND APTH.BATCH =  PID.BATCH ) as legacy_batch," + 
						"(SELECT w.EQUIPMENT FROM WO w WHERE APTH.WO = w.WO  ) ,\r\n" + 
						"APTH.schedule_category,\r\n" + 
						"APTH.STATE_OF_PART,\r\n" + 
						"APTH.MODIFIED_DATE,\r\n" + 
						"PKG_INTERFACE.GETXMLNUMBERSTRING(APTH.TRANSACTION_HOUR) AS HOUR,\r\n" + 
						"PKG_INTERFACE.GETXMLNUMBERSTRING(APTH.TRANSACTION_MINUTE) AS MINUTE,\r\n" + 
						"APTH.TRANSACTION_TYPE,\r\n" +
						"APTH.SVO_NO,\r\n"+
						"(SELECT PID.FUNCTIONAL_LOCATION FROM PN_INVENTORY_DETAIL PID WHERE APTH.PN = PID.PN AND APTH.SN = PID.SN AND APTH.BATCH =  PID.BATCH ) as fun_loc,"+
						"APTH.IE4N_FORCE_INSTALL , APTH.STATE_OF_PART\r\n"+
						"FROM\r\n" + 
						"PN_INVENTORY_HISTORY APTH\r\n" + 
						"WHERE \r\n" + 
						"APTH.TRANSACTION_NO = ?";
				
				
				MT_TRAX_SND_I43_4076_REQ out = new MT_TRAX_SND_I43_4076_REQ();
				
				
				PreparedStatement pstmt1 = null; 
				ResultSet rs1 = null;
				pstmt1 = con.prepareStatement(sql);
				
				try 
				{
					pstmt1.setString(1, item.getTransaction());
					rs1 = pstmt1.executeQuery();

					if (rs1 != null) 
					{
						while (rs1.next())
						{
							logger.info("Processing " + rs1.getString(1));
							out.setMaterial_Number(rs1.getString(1));
																				
							String pn = out.getMaterial_Number();
							
							pn = pn.replaceAll("IN", "\"");
							pn = pn.replaceAll("FT", "'");
							
							if(pn.contains(":UPLOAD"))
							{
								pn=  pn.substring(0, pn.indexOf(":"));
							}
							out.setMaterial_Number(pn);
							
							
							out.setSerial_Number(rs1.getString(2));
							
							if(rs1.getString(3) != null && !rs1.getString(3).isEmpty()) {
								out.setPosition(rs1.getString(3));
							}else {
								out.setPosition("");
							}
							if(out.getPosition() != null && !out.getPosition().isEmpty() && 
									out.getPosition().length() > 4) {
								out.setPosition(out.getPosition().substring(0,4)) ;
								overRidePoistion(item, out.getPosition());
							}
							
							if(rs1.getString(4) != null && !rs1.getString(4).isEmpty()) {
								out.setQuantity(rs1.getString(4));
							}else {
								out.setQuantity("");
							}
							
							if(rs1.getString(5) != null && !rs1.getString(5).isEmpty()) {
								out.setSAP_Batch(rs1.getString(5));
							}else {
								out.setSAP_Batch("");
							}
							
							if(rs1.getString(6) != null && !rs1.getString(6).isEmpty()) {
								if(rs1.getString(6).length() < 18) {
									out.setSuperior_Order_Equipment(rs1.getString(6));
								}else {
									String NHA_PN = rs1.getString(6);
									NHA_PN = NHA_PN.replaceAll("IN", "\"");
									NHA_PN = NHA_PN.replaceAll("FT", "'");
									
									if(NHA_PN.contains(":UPLOAD"))
									{
										NHA_PN=  NHA_PN.substring(0, NHA_PN.indexOf(":"));
									}								
									NHA_PN = NHA_PN.substring(0,18);
									out.setSuperior_Order_Equipment(NHA_PN);
								}
							}else {
								out.setSuperior_Order_Equipment("");
							}
							if(rs1.getString(13) != null && !rs1.getString(13).isEmpty()) {
								out.setReference_Order(rs1.getString(13));
							}else {
								out.setReference_Order("");
							}
							
							
							
							
							//schedule_category
							if(rs1.getString(7) == null || rs1.getString(7).isEmpty() || rs1.getString(7).equalsIgnoreCase("SCHEDULE")) {
								out.setScheduled__Removal_Indicator("");
							}else if(rs1.getString(7) != null && !rs1.getString(7).isEmpty() && rs1.getString(7).equalsIgnoreCase("UN/SCHEDULE")) {
								out.setScheduled__Removal_Indicator("X");
							}
							
							//REMOVE_AS_SERVICEABLE
							if((rs1.getString(8) == null || rs1.getString(8).isEmpty() || rs1.getString(8).equalsIgnoreCase("NO"))
							|| (rs1.getString(16) == null || rs1.getString(16).isEmpty() || rs1.getString(8).equalsIgnoreCase("UNSERVICEABLE"))) {
								out.setUnserviceable_Serviceable_Indicator("");
							}else if(rs1.getString(8).equalsIgnoreCase("YES") ||
									rs1.getString(16).equalsIgnoreCase("SERVICEABLE")) {
								out.setUnserviceable_Serviceable_Indicator("X");
							}
							
							if(rs1.getDate(9) !=null ) {
								Date = rs1.getDate(9);
								Format formatter = new SimpleDateFormat("yyyyMMdd");
								date = formatter.format(Date);		
								out.setDismantle_Install_Date(date);
								
								
								formatter = new SimpleDateFormat("HHmmss");
								date = formatter.format(Date);		
								out.setDismantle_Install_Time(date);
								
							}
						
							if(rs1.getString(10) != null && !rs1.getString(10).isEmpty()) 
							{
								hour = String.format("%02d", Integer.parseInt(rs1.getString(10)));
							}
							else 
							{
								hour = "00";
							}
							if(rs1.getString(11) != null && !rs1.getString(11).isEmpty()) 
							{
								minute = String.format("%02d", Integer.parseInt(rs1.getString(11)));	
							}
							else 
							{
								minute = "00";
							}
							time=time.replaceAll("hh", hour);
							time=time.replaceAll("mm", minute);
							//out.setDismantle_Install_Time(time);
							
							
							if(rs1.getString(12) != null && !rs1.getString(12).isEmpty() && 
									( rs1.getString(12).contains("INSTAL") 
									|| rs1.getString(12).contains("INT/INST") )) {
								
								out.setInstall_Dismantle_Indicator("I");
							}else if( rs1.getString(12) != null && !rs1.getString(12).isEmpty() 
									&& rs1.getString(12).contains("REMOV")) {
								out.setInstall_Dismantle_Indicator("D");
							}else if( rs1.getString(12) != null && !rs1.getString(12).isEmpty() 
									&& rs1.getString(12).contains("INSPECT")) {
								out.setInstall_Dismantle_Indicator("D");
							}	
							
							if(rs1.getString(15) != null && !rs1.getString(15).isEmpty() && rs1.getString(15).equalsIgnoreCase("Y") ) {
								out.setForce_Installation_Dismantling_Indicator("X");
							}else  {
								out.setForce_Installation_Dismantling_Indicator("");
							}
							
							if(rs1.getString(14) != null && !rs1.getString(14).isEmpty()) {
								out.setFunctional_Location(rs1.getString(14));
							}else  {
								out.setFunctional_Location("");
							}							
							
							out.setTRAXTRANS(item.getTransaction());
						}
					}
							
					
					
				}
				catch (Exception e) 
		        {
					e.printStackTrace();
					InstallRemoveController.addError(e.toString());
					logger.severe(e.toString());
		            exceuted = e.toString();
		            throw new Exception("Issue found");
				}finally {
					
					if(pstmt1 != null && !pstmt1.isClosed())
						pstmt1.close();
					if(rs1 != null && !rs1.isClosed())
						rs1.close();	
				}
				return out;
			
			}
			
			

	
		 
		 
		 
		 private void overRidePoistion(IE4N item, String position) throws Exception {
			 String sqlPosition =
						"UPDATE pn_inventory_history SET POSITION  = ? WHERE TRANSACTION_NO = ? ";
			 PreparedStatement pstmt2 = null; 
			 
			
			 try {
				pstmt2 = con.prepareStatement(sqlPosition);
				pstmt2.setString(1, position);
				pstmt2.setString(2, item.getTransaction());
				pstmt2.executeQuery();
			}catch (Exception e) {
				InstallRemoveController.addError(e.toString());
				logger.severe(e.toString());
		        exceuted = e.toString();
		        throw new Exception("Issue found");
			}finally {
				if(pstmt2 != null && !pstmt2.isClosed())
					pstmt2.close();
			}
		}

		public void markTransaction(MT_TRAX_RCV_I43_4076_RES Inbound) throws Exception
			{
				//setting up variables
				exceuted = "OK";
				
				
			    String message = "";
			    
			    for(Application_Log l: Inbound.getApplication_Log()) {
			    	message = message +l.getMessage_Type()+ "-" +l.getMessage_Text()+ System.lineSeparator();				    				    
			    }
				
				String sqlDate =
				"UPDATE pn_inventory_history SET IE4N  = ? WHERE TRANSACTION_NO = ? ";

				String sqlText =
						"UPDATE pn_inventory_history SET IE4N_MESSAGE_TEXT  = ? WHERE TRANSACTION_NO = ? ";

				String text = "Received acknowledgement:" +System.lineSeparator()  +message 
				+"Reference order: "+Inbound.getHeader().getReference_Order() + " Transaction: " 
				+ Inbound.getHeader().getTRAXTRANS()  ;
				
				
				PreparedStatement pstmt2 = null; 
				PreparedStatement pstmt3 = null; 
				pstmt2 = con.prepareStatement(sqlDate);
				pstmt3 = con.prepareStatement(sqlText);
				try 
				{
				
					if(containsType(Inbound.getApplication_Log(), "E")){
						logger.info("1");
						logger.info(text);
						pstmt2.setString(1, "E");
						pstmt2.setString(2, Inbound.getHeader().getTRAXTRANS());
						pstmt2.executeQuery();
						
						pstmt3.setString(1, text);
						pstmt3.setString(2, Inbound.getHeader().getTRAXTRANS());
						pstmt3.executeQuery();
						
					}else if(containsType(Inbound.getApplication_Log(), "W")) {
						logger.info("2");	
						logger.info(text);
						pstmt2.setString(1, "W");
						pstmt2.setString(2, Inbound.getHeader().getTRAXTRANS());
						pstmt2.executeQuery();
						
						pstmt3.setString(1, text);
						pstmt3.setString(2, Inbound.getHeader().getTRAXTRANS());
						pstmt3.executeQuery();
							
					}else {
						logger.info("3");
						pstmt2.setString(1, "S");
						pstmt2.setString(2, Inbound.getHeader().getTRAXTRANS());
						pstmt2.executeQuery();
	
						pstmt3.setString(1, null);
						pstmt3.setString(2, Inbound.getHeader().getTRAXTRANS());
						pstmt3.executeQuery();
						
					}
					
				}
				catch (Exception e) 
		        {
					InstallRemoveController.addError(e.toString());
					logger.severe(e.toString());
		            exceuted = e.toString();
		            throw new Exception("Issue found");
				}finally {
					
					if(pstmt2 != null && !pstmt2.isClosed())
						pstmt2.close();
					if(pstmt3 != null && !pstmt3.isClosed())
						pstmt3.close();
					
				}
				
			}
		 
		 public boolean containsType(Collection<Application_Log> c, String type) {
			    for(Application_Log o : c) {
			        if(o != null && o.getMessage_Type().equals(type)) {
			            return true;
			        }
			    }
			    return false;
			}
			
		 public boolean check3P(IE4N b) {
			 String sql3p =
					 "select w.wo from wo w, pn_inventory_history apth where apth.TRANSACTION_NO = ? " + 
					 "and w.wo = apth.wo and w.wo_category like '%" +party3 +"' ";

			 	PreparedStatement pstmt = null; 
				ResultSet rs1 = null;
				try {
					pstmt = con.prepareStatement(sql3p);
					pstmt.setString(1, b.getTransaction());
					rs1 = pstmt.executeQuery();

					if (rs1 != null) 
					{
						while (rs1.next())
						{
							if(rs1.getString(1) != null && !rs1.getString(1).isEmpty()) {
								logger.info("IE4N Not Applicable due to Third Party.");
								return true;
							}
						}
					}	
				} catch (SQLException e) {
					e.printStackTrace();
				}
			    return false;
			}
		 
		 public String getSVO(IE4N b) {
			 String sql3p =
					 "select w.SVO_NO from wo_task_card w, pn_inventory_history apth where apth.TRANSACTION_NO = ? \r\n" + 
					 "and w.wo = apth.wo and w.task_card = apth.task_card";
								
				//logger.info(sql3p);		
				PreparedStatement pstmt = null; 
				ResultSet rs1 = null;
				try {
					pstmt = con.prepareStatement(sql3p);
					pstmt.setString(1, b.getTransaction());
					rs1 = pstmt.executeQuery();

					if (rs1 != null) 
					{
						while (rs1.next())
						{
							if(rs1.getString(1) != null && !rs1.getString(1).isEmpty()) {
								
								return rs1.getString(1);
							}
						}
					}	
				} catch (SQLException e) {
					e.printStackTrace();
				}
			    return "";
			}	
}
