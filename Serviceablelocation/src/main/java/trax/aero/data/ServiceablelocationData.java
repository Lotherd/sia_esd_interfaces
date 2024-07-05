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
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import trax.aero.controller.ServiceablelocationController;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceAudit;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.MT_TRAX_RCV_I28_4134_RES;
import trax.aero.pojo.MT_TRAX_SND_I28_4134_REQ;
import trax.aero.utils.DataSourceClient;


/*
ALTER TABLE "WO"
ADD ("INTERFACE_ESD_DATE" DATE);
 
UPDATE 
WO      
SET   
WO.INTERFACE_SAP_TRANSFER_DATE = sysdate
WHERE
WO.refurbishment_order = ? AND w.MODULE = 'SHOP'
  
  
  
SELECT w.refurbishment_order FROM WO w where w.INTERFACE_SAP_TRANSFER_DATE IS NULL AND w.MODULE = 'SHOP'
  
*/



public class ServiceablelocationData {

		Logger logger = LogManager.getLogger("Serviceablelocation_I94");
		EntityManagerFactory factory;
		EntityManager em;
		String exceuted;
		private Connection con;
		
		final String MaxRecord = System.getProperty("Serviceablelocation_MaxRecord");
		//public InterfaceLockMaster lock;
		
		public ServiceablelocationData(String mark)
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
				ServiceablelocationController.addError(e.toString());
				
			}
			catch (CustomizeHandledException e1) {
				ServiceablelocationController.addError(e1.toString());
				logger.severe(e1.toString());
			} catch (Exception e) {
				ServiceablelocationController.addError(e.toString());
				logger.severe(e.toString());
			}
			
		}
		
		public ServiceablelocationData()
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
				ServiceablelocationController.addError(e.toString());
				
			}
			catch (CustomizeHandledException e1) {
				ServiceablelocationController.addError(e1.toString());
				logger.severe(e1.toString());
			} catch (Exception e) {
				ServiceablelocationController.addError(e.toString());
				logger.severe(e.toString());
			}
			factory = Persistence.createEntityManagerFactory("TraxESD");
			em = factory.createEntityManager();
		}
		
		public Connection getCon() {
			return con;
		}
		
		
		public ArrayList<MT_TRAX_SND_I28_4134_REQ> getRequests() throws Exception
		{
			//setting up variables
			exceuted = "OK";
			
			ArrayList<MT_TRAX_SND_I28_4134_REQ> requests = new ArrayList<MT_TRAX_SND_I28_4134_REQ>();
			
			
			
			
			String sql = 
			"select w.rfo_no, wsd.pn,wsd.pn_sn,w.wo,w.created_by  from wo w, wo_shop_detail wsd \r\n" + 
			"where w.rfo_no is not null  and w.wo = wsd.wo and w.interface_esd_date is not null\r\n" + 
			"and w.status = 'POSTCOMPLT'";

			if((MaxRecord != null && !MaxRecord.isEmpty())) {
				sql= sql + " AND ROWNUM <= ?";		
				}
			
			
			PreparedStatement pstmt1 = null;
			ResultSet rs1 = null;
			try 
			{
				pstmt1 = con.prepareStatement(sql);
				if((MaxRecord != null && !MaxRecord.isEmpty())) {
					pstmt1.setString(1, MaxRecord);
				}
				
				rs1 = pstmt1.executeQuery();

				if (rs1 != null) 
				{
					while (rs1.next()) 
					{
						logger.info("Processing RFO: " + rs1.getString(1));
						MT_TRAX_SND_I28_4134_REQ request = new MT_TRAX_SND_I28_4134_REQ();
						
						if(rs1.getString(1) != null && !rs1.getString(1).isEmpty()) {
							request.setRfoNo(rs1.getString(1));
						}
						else {
							request.setRfoNo("");
						}
						if(rs1.getString(2) != null && !rs1.getString(2).isEmpty()) {
							request.setPn(rs1.getString(2));
						}
						else {
							request.setPn("");
						}
						if(rs1.getString(3) != null && !rs1.getString(3).isEmpty()) {
							request.setSn(rs1.getString(3));
						}
						else {
							request.setSn("");
						}
						if(rs1.getString(4) != null && !rs1.getString(4).isEmpty()) {
							request.setWo(rs1.getString(4));
						}
						else {
							request.setWo("");
						}
						
						if(rs1.getString(5) != null && !rs1.getString(5).isEmpty()) {
							request.setRelationCode(rs1.getString(5));
						}
						else {
							request.setRelationCode("");
						}
						
						request.setInspLot("");
						request.setCode("");
					
						requests.add(request);	
						
					}
				}
				
				
			}
			catch (Exception e) 
	        {
				ServiceablelocationController.addError(e.toString());
				logger.severe(e.toString());
	            exceuted = e.toString();
	            throw new Exception("Issue found");
			}finally {
				if(rs1 != null && !rs1.isClosed())
					rs1.close();
				if(pstmt1 != null && !pstmt1.isClosed())
					pstmt1.close();
			}
			
			return requests;
		}
		
		
		public void markTransaction(MT_TRAX_RCV_I28_4134_RES response) throws Exception
		{
			/*
			  <UD_SUCCESS xmlns=""></UD_SUCCESS>
			  <EQUIPMENT xmlns="">string</EQUIPMENT>
			  <WORKCENTER xmlns="">string</WORKCENTER>
			  <LEGACY_BATCH xmlns="">string</LEGACY_BATCH>
			 */
			
			
			//setting up variables
			exceuted = "OK";
			
			String sqlDate ="UPDATE WO SET WO.interface_esd_date = sysdate WHERE WO.rfo_no = ? AND WO.MODULE = 'SHOP'";
			
			PreparedStatement pstmt2 = null; 
			pstmt2 = con.prepareStatement(sqlDate);
			try 
			{	
				logger.info("Marking RFO: " + response.getRfo());
				pstmt2.setString(1, response.getRfo());
				pstmt2.executeQuery();
			}
			catch (Exception e) 
	        {
				ServiceablelocationController.addError(e.toString());
				logger.severe(e.toString());
	            exceuted = e.toString();
	            throw new Exception("Issue found");
			}finally {
				
				if(pstmt2 != null && !pstmt2.isClosed())
					pstmt2.close();
				
			}
			
		}
		
		
		public void setInspLot(MT_TRAX_RCV_I28_4134_RES response) throws Exception
		{
			/*
			  <UD_SUCCESS xmlns=""></UD_SUCCESS>
			  <EQUIPMENT xmlns="">string</EQUIPMENT>
			  <WORKCENTER xmlns="">string</WORKCENTER>
			  <LEGACY_BATCH xmlns="">string</LEGACY_BATCH>
			 */
			
			
			//setting up variables
			exceuted = "OK";
			
			String sqlDate ="UPDATE WO SET INSPECTION_LOT_NUMBER = ?   WHERE WO.rfo_no = ? AND WO.MODULE = 'SHOP'";
			
			PreparedStatement pstmt2 = null; 
			pstmt2 = con.prepareStatement(sqlDate);
			try 
			{	
				logger.info("Marking RFO: " + response.getRfo());
				pstmt2.setString(1, response.getInspLot());
				pstmt2.setString(2, response.getRfo());
				pstmt2.executeQuery();
			}
			catch (Exception e) 
	        {
				ServiceablelocationController.addError(e.toString());
				logger.severe(e.toString());
	            exceuted = e.toString();
	            throw new Exception("Issue found");
			}finally {
				
				if(pstmt2 != null && !pstmt2.isClosed())
					pstmt2.close();
				
			}
			
		}

		
		
		
		private <T> void insertData( T data) 
		{
			try 
			{	
				if(!em.getTransaction().isActive())
					em.getTransaction().begin();
					em.merge(data);
				em.getTransaction().commit();
			}catch (Exception e)
			{
				logger.severe(e.toString());
			}
		}
		
		public boolean lockAvailable(String notificationType)
		{
			
			//em.getTransaction().begin();
			InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type", InterfaceLockMaster.class)
					.setParameter("type", notificationType).getSingleResult();
			em.refresh(lock);
			//logger.info("lock " + lock.getLocked());
			if(lock.getLocked().intValue() == 1)
			{				
				LocalDateTime today = LocalDateTime.now();
				LocalDateTime locked = LocalDateTime.ofInstant(lock.getLockedDate().toInstant(), ZoneId.systemDefault());
				Duration diff = Duration.between(locked, today);
				if(diff.getSeconds() >= lock.getMaxLock().longValue())
				{
					lock.setLocked(new BigDecimal(1));
					insertData(lock);
					return true;
				}
				return false;
			}
			else
			{
				lock.setLocked(new BigDecimal(1));
				insertData(lock);
				return true;
			}
			
		}
		
		
		public void lockTable(String notificationType)
		{
			em.getTransaction().begin();
			InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type", InterfaceLockMaster.class)
					.setParameter("type", notificationType).getSingleResult();
			lock.setLocked(new BigDecimal(1));
			//logger.info("lock " + lock.getLocked());
			
			lock.setLockedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()) );
			InetAddress address = null;
			try {
				address = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				
				logger.info(e.getMessage());
				//e.printStackTrace();
			}
			lock.setCurrentServer(address.getHostName());
			//em.lock(lock, LockModeType.NONE);
			em.merge(lock);
			em.getTransaction().commit();
		}
		
		public void unlockTable(String notificationType)
		{
			em.getTransaction().begin();
			
			InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type", InterfaceLockMaster.class)
					.setParameter("type", notificationType).getSingleResult();
			lock.setLocked(new BigDecimal(0));
			//logger.info("lock " + lock.getLocked());
			
			lock.setUnlockedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()) );
			//em.lock(lock, LockModeType.NONE);
			em.merge(lock);
			em.getTransaction().commit();
		}
		
		public void logError(String error) {
			
			InterfaceAudit ia = null;
			ia = new InterfaceAudit();
			ia.setTransaction(getSeqNoInterfaceAudit().longValue());
			ia.setTransactionType("ERROR");
			ia.setTransactionObject("I28");
			ia.setTransactionDate(new Date());
			ia.setCreatedBy("TRAX_IFACE");
			ia.setModifiedBy("TRAX_IFACE");
			ia.setCreatedDate(new Date());
			ia.setModifiedDate(new Date());
			ia.setExceptionId(new BigDecimal(-2000));
			ia.setExceptionByTrax("Y");
			ia.setExceptionDetail("Material Demand interface ran into an error");
			ia.setExceptionStackTrace(error);
			ia.setExceptionClassTrax("MaterialDemand_I10");	
			
			insertData(ia);
		}
		
		private BigDecimal getSeqNoInterfaceAudit()
		{		
			logger.info("Finding next seq");
			try
			{
				BigDecimal transaction = (BigDecimal)this.em.createNativeQuery("select seq_interface_audit.NextVal "
						+ "FROM DUAL").getSingleResult();		
				return transaction;			
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			}
			
		}
		
}
