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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.RandomStringUtils;

import trax.aero.controller.ServiceablelocationController;
import trax.aero.interfaces.IServiceablelocationData;
import trax.aero.logger.LogManager;
import trax.aero.model.BlobTable;
import trax.aero.model.BlobTablePK;
import trax.aero.model.InterfaceAudit;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.model.Wo;
import trax.aero.pojo.MT_TRAX_RCV_I28_4134_RES;
import trax.aero.pojo.MT_TRAX_SND_I28_4134_REQ;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.PrintPoster;
import trax.application_standard_structure.st_pn;


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


@Stateless(name="ServiceablelocationData" , mappedName="ServiceablelocationData")
public class ServiceablelocationData implements IServiceablelocationData {

		Logger logger = LogManager.getLogger("Serviceablelocation_I28");
		
		@PersistenceContext(unitName = "TraxStandaloneDS") private EntityManager em;
		
		String exceuted;
		private Connection con;
		public InterfaceLockMaster lock;

		final String MaxRecord = System.getProperty("Serviceablelocation_MaxRecord");
		//public InterfaceLockMaster lock;
		
		public ServiceablelocationData()
		{
			
		}
		
		public void openCon() throws SQLException, Exception{
			if(this.con == null || this.con.isClosed())
			{
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!this.con.isClosed()));
			}
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
			"where w.rfo_no is not null  and w.wo = wsd.wo and w.interface_esd_date is null\r\n" + 
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
		
		public void printLabel(MT_TRAX_RCV_I28_4134_RES response) {
			
					logger.info("Setting ");
						
					logger.info("Calling Print server");
					PrintPoster poster = new PrintPoster();
					st_pn ms_pn = new st_pn();
					ms_pn.l_batch = getBatch(response);

					ms_pn.s_calling_window = "w_pn_identification_tag_print";

					
					ms_pn.s_employee = "ADM";
					
					ms_pn.s_message ="SERVICETAG";
				
					poster.addJobToJMSQueueService("emroDS", "w_pn_identification_tag_print"
							, "pn identification tag print"
							, "ADM", getSeqNo(), ms_pn);
					
		}
		
		private Integer getBatch(MT_TRAX_RCV_I28_4134_RES response) {
			System.out.println("Finding next seq");
			PreparedStatement pstmt1 = null;
			ResultSet rs1 = null;
			try
			{
				String sql = ("select wsd.batch from wo w, wo_shop_detail wsd " + 
						"where w.rfo_no is not null  and w.wo = wsd.wo " + 
						"and w.rfo_no = ?");
				
				pstmt1 = con.prepareStatement(sql);
				pstmt1.setString(1, response.getRfo());
				rs1 = pstmt1.executeQuery();

				if (rs1 != null) 
				{
					while (rs1.next()) 
					{
						if(rs1.getString(1) != null && !rs1.getString(1).isEmpty()) {
							return Integer.parseInt(rs1.getString(1));
						}else {
							return 0;
						}
					}
				}	
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return 0;
			}finally {
				try {
					if(pstmt1 != null && !pstmt1.isClosed())
						pstmt1.close();
					if(rs1 != null && !rs1.isClosed())
						rs1.close();
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			return 0;
		}

		private BigDecimal getSeqNo() 
		{		
			System.out.println("Finding next seq");
			PreparedStatement pstmt1 = null;
			ResultSet rs1 = null;
			try
			{
				String sql = ("select SEQ_W_PRINT_JOBS.NextVal FROM DUAL");	
				pstmt1 = con.prepareStatement(sql);
				rs1 = pstmt1.executeQuery();

				if (rs1 != null) 
				{
					rs1.next(); 
					return new BigDecimal(rs1.getString(1));
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return null;
			}finally {
				try {
			
				if(pstmt1 != null && !pstmt1.isClosed())
					pstmt1.close();
				if(rs1 != null && !rs1.isClosed())
					rs1.close();
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			return null;
			
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

		
		public void setComplete(MT_TRAX_RCV_I28_4134_RES response) throws Exception
		{
					
			String sqlDate ="UPDATE WO w1 SET w1.STATUS =  'COMPLETED', POSTCOMPLETED_BY = null , POSTCOMPLETED_DATE = null , MODIFIED_BY = 'TRAX_IFACE' , MODIFIED_DATE = sysdate  WHERE w1.rfo_no = ? AND w1.MODULE = 'SHOP' and EXISTS (  SELECT 1 FROM WO w2 WHERE w2.wo = w1.wo  and status = 'POSTCOMPLT') ";
			
			PreparedStatement pstmt2 = null; 
			pstmt2 = con.prepareStatement(sqlDate);
			try 
			{	
				logger.info("Seting COMPLETED RFO: " + response.getRfo());
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
		
		
		private <T> void insertData( T data) 
		{
			try 
			{	
				em.merge(data);
				em.flush();
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
			insertData(lock);
		}
		
		public void unlockTable(String notificationType)
		{
			
			InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type", InterfaceLockMaster.class)
					.setParameter("type", notificationType).getSingleResult();
			lock.setLocked(new BigDecimal(0));
			//logger.info("lock " + lock.getLocked());
			
			lock.setUnlockedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()) );
			//em.lock(lock, LockModeType.NONE);
			insertData(lock);
		}
		
		public void logError(String error, String wo) {
			
			InterfaceAudit ia = null;
			ia = new InterfaceAudit();
			ia.setTransaction(getSeqNoInterfaceAudit().longValue());
			ia.setTransactionType("ERROR");
			ia.setTransactionObject("I28");
			ia.setTransactionDate(new Date());
			ia.setCreatedBy("TRAX_IFACE");
			ia.setModifiedBy("TRAX_IFACE");
			ia.setOrderLine(new BigDecimal(wo));
			ia.setCreatedDate(new Date());
			ia.setModifiedDate(new Date());
			ia.setExceptionId(new BigDecimal(-2000));
			ia.setExceptionByTrax("Y");
			ia.setExceptionDetail("Serviceable location interface ran into an error");
			ia.setExceptionStackTrace(error);
			ia.setExceptionClassTrax("Serviceablelocation_I28");	
			
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
		
		
		public String print(String wo,String task_card , byte[] bs, String formNo, String formLine) throws Exception {
			//setting up variables
			exceuted = "OK";
			
			try 
			{			
				if(this.con == null || this.con.isClosed())
				{
					this.con = DataSourceClient.getConnection();
					logger.info("The connection was stablished successfully with status: " + String.valueOf(!this.con.isClosed()));
				}
				setAttachmentLink(bs,wo, task_card);
				
			}
			catch (Exception e) 
	        {
				exceuted = e.toString();
				logger.severe(exceuted);
			}
			return exceuted;
			
			
		}
		
		private void setAttachmentLink( byte[] input,String woo, String path) {
			boolean existBlob = false;
			BlobTable blob = null;
			
			
		    String random = RandomStringUtils.random(19, false, true);

			Wo wo =  getWo(woo);
			
			String fileName = random + ".pdf";
		    if(wo == null) {
		    	return;
		    }
			
			
				try 
				{
					blob = em.createQuery("SELECT b FROM BlobTable b where b.id.blobNo = :bl and b.blobDescription = :des", BlobTable.class)
							.setParameter("bl", wo.getBlobNo().longValue())
							.setParameter("des",fileName )
							.getSingleResult();
					existBlob = true;
				}
				catch(Exception e)
				{
					
					BlobTablePK pk = new BlobTablePK();
					blob = new BlobTable();
					blob.setCreatedDate(new Date());
					blob.setCreatedBy("TRAX_IFACE");
					blob.setId(pk);
					
					blob.setPrintFlag("YES");
					
					blob.getId().setBlobLine(getLine(wo.getBlobNo(),"BLOB_LINE","BLOB_TABLE","BLOB_NO" ));
				}
				
				
				blob.setDocType(fileName.substring(0, 3));
				
					
				
				
				blob.setModifiedBy("TRAX_IFACE");
				blob.setModifiedDate(new Date());
				blob.setBlobItem(input);
				blob.setBlobDescription(fileName);
				blob.setCustomDescription(fileName);
				
				
				
				if(!existBlob && wo.getBlobNo() == null) {
					try {
						blob.getId().setBlobNo(((getTransactionNo("BLOB").longValue())));
						wo.setBlobNo(new BigDecimal(blob.getId().getBlobNo()));
					} catch (Exception e1) {
						
					}
				}else if(wo.getBlobNo() != null){
					blob.getId().setBlobNo(wo.getBlobNo().longValue());
				}
				
				logger.info("INSERTING WO: " + wo + " " );
				insertData(wo);
				
				logger.info("INSERTING blob: " + blob.getId().getBlobNo() + " Line: " + blob.getId().getBlobLine());
				insertData(blob);
				
				return;
		}
		
		
		private long getLine(BigDecimal no, String table_line, String table, String table_no)
		{		
			long line = 0;
			String sql = " SELECT  MAX("+table_line+") FROM "+table+" WHERE "+table_no+" = ?";
			try
			{
				logger.info(no.toString());
				Query query = em.createNativeQuery(sql);
				query.setParameter(1, no);  
			
				BigDecimal dec = (BigDecimal) query.getSingleResult(); 
				line = dec.longValue();
				line++;
			}
			catch (Exception e) 
			{
				line = 1;
			}
			
			return line;
		}
		private BigDecimal getTransactionNo(String code)
		{		
			try
			{
				BigDecimal acctBal = (BigDecimal) em.createNativeQuery("SELECT pkg_application_function.config_number ( ? ) "
						+ " FROM DUAL ").setParameter(1, code).getSingleResult();
							
				return acctBal;			
			}
			catch (Exception e) 
			{
				logger.severe("An unexpected error occurred getting the sequence. " + "\nmessage: " + e.toString());
			}
			
			return null;
			
		}
		
		
		private Wo getWo(String formNo) {
			Wo wo = null;
			
			try {
				wo = em.createQuery("SELECT w FROM Wo w WHERE w.wo = :formNo ", Wo.class)
							.setParameter("formNo", new BigDecimal(formNo).longValue())
							.getSingleResult();
					
					return wo;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		
}
