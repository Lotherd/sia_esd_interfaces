package trax.aero.data;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.RandomStringUtils;

import trax.aero.controller.InstallRemoveSVOController;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.interfaces.IInstallRemoveSvoData;
import trax.aero.logger.LogManager;
import trax.aero.model.BlobTable;
import trax.aero.model.BlobTablePK;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.model.PnInventoryHistory;
import trax.aero.pojo.I19_Request;
import trax.aero.pojo.I19_Response;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.PrintPoster;
import trax.application_standard_structure.st_pn;


/*
SELECT
    "A3"."PN"                    "PN",
    "A3"."SN"                    "SN",
    "A3"."SN"                    "SN",
    "A3"."REMOVE_INSTALLED_DATE" "REMOVE_INSTALLED_DATE",
    "A3"."LOCATION"              "LOCATION",
    'LICENCE_TYPE'               "'LICENCE_TYPE'",
    "A3"."REMOVE_AS_SERVICEABLE" "REMOVE_AS_SERVICEABLE",
   "A3"."INTERNAL_EXTERNAL"     "INTERNAL_EXTERNAL",
    "A3"."TRANSACTION_TYPE"      "TRANSACTION_TYPE",
    "A3"."REMOVAL_REASON"        "REMOVAL_REASON",
    "A3"."NOTES"                 "NOTES",
    "A1"."CUSTOMER"              "CUSTOMER",
    "A3"."RFO_NO"                "RFO_NO",
    "A2"."LEGACY_BATCH"          "LEGACY_BATCH",
    "A3"."QTY"                   "QTY",
    "A3"."WO"                    "WO",
    "A3"."TASK_CARD"             "TASK_CARD",
    "A3"."TRANSACTION_NO"           "TRANSACTION"
FROM
    "PN_INVENTORY_HISTORY" "A3",
    "PN_INVENTORY_DETAIL"  "A2",
    "WO"                   "A1"
WHERE
    "A3"."SVO_NO" IS NULL
    AND "A3"."WO" IS NOT NULL
    AND "A3"."TASK_CARD" IS NOT NULL
    AND "A3"."TRANSACTION_TYPE" LIKE '%A/C%'
    AND "A3"."BATCH" = "A2"."BATCH"
    AND "A1"."WO" = "A3"."WO"
 
 */

@Stateless(name="InstallRemoveSvoData" , mappedName="InstallRemoveSvoData")
public class InstallRemoveSvoData implements IInstallRemoveSvoData {
	
	
	String exceuted;
	private Connection con;
	
	//public InterfaceLockMaster lock;
	Logger logger = LogManager.getLogger("InstallRemoveSVO_I19");

	@PersistenceContext(unitName = "TraxStandaloneDS") private EntityManager em;
	
	
	
	public InstallRemoveSvoData()
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
	
	
	public String markTransaction(I19_Response request) throws Exception
	{
		//setting up variables
		exceuted = "OK";
		
		String sqlDate =
		"UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_FLAG = 'Y', PN_INVENTORY_HISTORY.SVO_NO = ? WHERE PN_INVENTORY_HISTORY.TRANSACTION_NO = ?";
		
		PreparedStatement pstmt2 = null; 

		try 
		{
				openCon();
				pstmt2 = con.prepareStatement(sqlDate);
				
				pstmt2.setString(1, request.getEsdSvo());
				
				pstmt2.setString(2, request.getTransaction());
				
				pstmt2.executeQuery();
				
				
			
		}
		catch (Exception e) 
        {
			InstallRemoveSVOController.addError(e.toString());
            
            exceuted = e.toString();
            
            logger.severe(exceuted);
            
		}finally {
			if(pstmt2 != null && !pstmt2.isClosed())
				pstmt2.close();
		}
		
		return exceuted;
		
	}
	
	public ArrayList<I19_Request> getTransactions() throws Exception
	{
		//setting up variables
		Date Date;
		String currentDate;
		Format formatter = new SimpleDateFormat("dd-MM-yyyy");

		
		
		ArrayList<I19_Request> list = new ArrayList<I19_Request>();
		
		String sql= "SELECT DISTINCT A3.PN AS PN, " +
	            "A3.SN AS SN, " +
				"A4.PN_SN AS ESN, " +
	            "A3.REMOVE_INSTALLED_DATE AS REMOVE_INSTALLED_DATE, " +
	            "A1.LOCATION AS LOCATION, " +
	            "'TYPE' AS LICENCE_TYPE, " +
	            "A3.REMOVE_AS_SERVICEABLE AS REMOVE_AS_SERVICEABLE, " +
	            "A3.INTERNAL_EXTERNAL AS INTERNAL_EXTERNAL, " +
	            "A3.TRANSACTION_TYPE AS TRANSACTION_TYPE, " +
	            "A3.REASON_CATEGORY AS REMOVAL_REASON, " +
	            "A3.NOTES AS NOTES, " +
	            "A1.CUSTOMER AS CUSTOMER, " +
	            "A1.RFO_NO AS RFO_NO, " +
	            "A3.LEGACY_BATCH AS LEGACY_BATCH, " +
	            "A3.QTY AS QTY, " +
	            "A3.WO AS WO, " +
	            "A3.TASK_CARD AS TASK_CARD, " +
	            "A3.TRANSACTION_NO AS TRANSACTION " +
	            "FROM PN_INVENTORY_HISTORY A3 " +
	            "JOIN WO A1 ON A1.WO = A3.WO " +
	            "JOIN WO_SHOP_DETAIL A4 ON A4.WO = A1.WO " +
	            "LEFT JOIN PN_MASTER PM ON A3.PN = PM.PN " +
	            "WHERE A3.SVO_NO IS NULL " +
	            "AND A3.WO IS NOT NULL " +
	            "AND A3.TASK_CARD IS NOT NULL " +
	            "AND (A3.TRANSACTION_TYPE LIKE 'N/L/A%' ) " +
	            "AND A3.MADE_AS_CCS IS NOT NULL " +
	            "AND A1.MODULE = 'SHOP' " +
	            "AND A1.RFO_NO IS NOT NULL " +
	            "AND ( " +
	            "   (PM.CATEGORY IN ('B', 'C', 'D') " +
	            "    AND NOT EXISTS (SELECT 1 FROM ZEPARTSER_MASTER Z " +
	            "                   WHERE LTRIM(Z.CUSTOMER, '0') = LTRIM(A1.CUSTOMER, '0') " +
	            "                   AND Z.PN = A3.PN) " +
	            "    AND A3.INTERFACE_TRANSFER_FLAG = 'S') " +
	            "   OR " +
	            "   (PM.CATEGORY IN ('B', 'C', 'D') " +
	            "    AND EXISTS (SELECT 1 FROM ZEPARTSER_MASTER Z " +
	            "               WHERE LTRIM(Z.CUSTOMER, '0') = LTRIM(A1.CUSTOMER, '0') " +
	            "               AND Z.PN = A3.PN) " +
	            "    AND A3.INTERFACE_TRANSFER_FLAG IS NULL) " +
	            "   OR " +
	            "   (PM.CATEGORY = 'A' " +
	            "    AND A3.INTERFACE_TRANSFER_FLAG IS NULL) " +
	            ")";

		String sqlMark = "UPDATE PN_INVENTORY_HISTORY SET INTERFACE_TRANSFER_FLAG = 'D' WHERE WO = ? AND TASK_CARD = ? AND PN = ? ";
		
				
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		 PreparedStatement pstmt2 = null;
		 ResultSet rs2 = null;

		try 
		{
			openCon();
			pstmt1 = con.prepareStatement(sql);
			pstmt2 = con.prepareStatement(sqlMark);
			
			rs1 = pstmt1.executeQuery();

			if (rs1 != null) 
			{
				while (rs1.next()) // LOOP EACH INV LINE
				{
					 logger.info("Processing Transaction: " + rs1.getString(18) + " WO: " + rs1.getString(16) );
					 I19_Request Inbound = new I19_Request();
						
					if(rs1.getString(1) != null && !rs1.getString(1).isEmpty()) {
						Inbound.setPn(rs1.getString(1));
					}
					else {
						Inbound.setPn("");
					}
					
					if(rs1.getString(2) != null && !rs1.getString(2).isEmpty()) {
						Inbound.setPnSn(rs1.getString(2));
					}
					else {
						Inbound.setPnSn("");
					}
					
					if(rs1.getString(3) != null && !rs1.getString(3).isEmpty()) {
						Inbound.setEsnNo(rs1.getString(3));
					}
					else {
						Inbound.setEsnNo("");
					}
					
					if(rs1.getDate(4) != null) {
						Date = rs1.getDate(4);
						currentDate = formatter.format(Date);
						Inbound.setRemoveInstalledDate(currentDate);
					}else {
						Inbound.setRemoveInstalledDate("");
					}
					
					if(rs1.getString(5) != null && !rs1.getString(5).isEmpty()) {
						Inbound.setLocation(rs1.getString(5));
					}
					else {
						Inbound.setLocation("");
					}
					
					if(rs1.getString(6) != null && !rs1.getString(6).isEmpty()) {
						Inbound.setLicenceType("");
					}
					else {
						Inbound.setLicenceType("");
					}
					
					if(rs1.getString(7) != null && !rs1.getString(7).isEmpty()) {
					    String status = rs1.getString(7);
					    
					    if (status.equalsIgnoreCase("SERVICEABLE")) {
					        Inbound.setRemoveAsServiceable("X");
					    } else if (status.equalsIgnoreCase("UNSERVICEABLE")) {
					        // Do nothing (implicitly send nothing)
					        Inbound.setRemoveAsServiceable("");
					    } else {
					        // Handle other cases if necessary, or leave as it is
					        Inbound.setRemoveAsServiceable(status);
					    }
					} else {
					    Inbound.setRemoveAsServiceable("");
					}

					
					if(rs1.getString(8) != null && !rs1.getString(8).isEmpty()) {
					    String internalExternal = rs1.getString(8);
					    
					    if (internalExternal.equalsIgnoreCase("Internal")) {
					        Inbound.setInternalExternal("I");
					    } else if (internalExternal.equalsIgnoreCase("External")) {
					        Inbound.setInternalExternal("E");
					    } else {
					        // Handle other cases if necessary, or leave as it is
					        Inbound.setInternalExternal(internalExternal);
					    }
					} else {
					    Inbound.setInternalExternal("");
					}

					
					if(rs1.getString(9) != null && !rs1.getString(9).isEmpty()) {
					    String transactionType = rs1.getString(9);
					    
					    if (transactionType.contains("REMOV")) {
					        Inbound.setTransactionType("R");
					    } else if (transactionType.contains("INSTAL")) {
					        Inbound.setTransactionType("I");
					    } else {
					        // Handle other cases if necessary, or leave as it is
					        Inbound.setTransactionType(transactionType);
					    }
					} else {
					    Inbound.setTransactionType("");
					}

					
					if(rs1.getString(10) != null && !rs1.getString(10).isEmpty()) {
						Inbound.setRemovalReason(rs1.getString(10));
					}
					else {
						Inbound.setRemovalReason("");
					}
					
					if(rs1.getString(11) != null && !rs1.getString(11).isEmpty()) {
						Inbound.setNotes(rs1.getString(11));
					}
					else {
						Inbound.setNotes("");
					}
					
					if(rs1.getString(12) != null && !rs1.getString(12).isEmpty()) {
						Inbound.setCustomer(rs1.getString(12));
					}
					else {
						Inbound.setCustomer("");
					}
					
					if(rs1.getString(13) != null && !rs1.getString(13).isEmpty()) {
						Inbound.setRfoNo(rs1.getString(13));
					}
					else if(rs1.getString(19) != null && !rs1.getString(19).isEmpty()) {
						Inbound.setRfoNo(rs1.getString(19));
					}else {
						Inbound.setRfoNo("");
					}
					
					if(rs1.getString(14) != null && !rs1.getString(14).isEmpty()) {
						Inbound.setLegacyBatch(rs1.getString(14));
					}
					else {
						Inbound.setLegacyBatch("");
					}
					
					if(rs1.getString(15) != null && !rs1.getString(15).isEmpty()) {
						Inbound.setQty(rs1.getString(15));
					}
					else {
						Inbound.setQty("");
					}
					
					if(rs1.getString(16) != null && !rs1.getString(16).isEmpty()) {
						Inbound.setWo(rs1.getString(16));
					}
					else {
						Inbound.setWo("");
					}
					
					if(rs1.getString(17) != null && !rs1.getString(17).isEmpty()) {
						Inbound.setTc(rs1.getString(17));
					}
					else {
						Inbound.setTc("");
					}
					
					if(rs1.getString(18) != null && !rs1.getString(18).isEmpty()) {
						Inbound.setTransaction(rs1.getString(18));
					}
					else {
						Inbound.setTransaction("");
					}
										
					list.add(Inbound);	
					
					pstmt2.setString(1, Inbound.getWo());
					pstmt2.setString(2, Inbound.getTc());
					pstmt2.setString(3, Inbound.getPn());
					pstmt2.executeQuery();
					
				}
			}
			
			
		}
		catch (Exception e) 
        {
			InstallRemoveSVOController.addError(e.toString());
            logger.severe(e.toString());
            e.printStackTrace();
            throw e;
		}finally {
			if(rs1 != null && !rs1.isClosed())
				rs1.close();
			if(pstmt1 != null && !pstmt1.isClosed())
				pstmt1.close();
		}
		return list;
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

		PnInventoryHistory pih =  getPnInventoryHistory(woo);
		
		String fileName = random + ".pdf";
	    if(pih == null) {
	    	return;
	    }
		
		
			try 
			{
				blob = em.createQuery("SELECT b FROM BlobTable b where b.id.blobNo = :bl and b.blobDescription = :des", BlobTable.class)
						.setParameter("bl", pih.getBlobNo().longValue())
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
				
				blob.getId().setBlobLine(getLine(pih.getBlobNo(),"BLOB_LINE","BLOB_TABLE","BLOB_NO" ));
			}
			
			
			blob.setDocType(fileName.substring(0, 3));
			
				
			
			
			blob.setModifiedBy("TRAX_IFACE");
			blob.setModifiedDate(new Date());
			blob.setBlobItem(input);
			blob.setBlobDescription(fileName);
			blob.setCustomDescription(fileName);
			
			
			
			if(!existBlob && pih.getBlobNo() == null) {
				try {
					blob.getId().setBlobNo(((getTransactionNo("BLOB").longValue())));
					pih.setBlobNo(new BigDecimal(blob.getId().getBlobNo()));
				} catch (Exception e1) {
					
				}
			}else if(pih.getBlobNo() != null){
				blob.getId().setBlobNo(pih.getBlobNo().longValue());
			}
			
			logger.info("INSERTING pih: " + pih + " " );
			insertData(pih);
			
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
	
	
	private PnInventoryHistory getPnInventoryHistory(String formNo) {
		PnInventoryHistory wo = null;
		
		try {
			wo = em.createQuery("SELECT w FROM PnInventoryHistory w WHERE w.id.transactionNo = :formNo ", PnInventoryHistory.class)
						.setParameter("formNo", new BigDecimal(formNo).longValue())
						.getSingleResult();
				
				return wo;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void printCCS(I19_Response input) {
		logger.info("Setting ");
		
		logger.info("Calling Print server");
		PrintPoster poster = new PrintPoster();
		st_pn ms_pn = new st_pn();
		ms_pn.l_batch = getBatch(input);

		ms_pn.s_calling_window = "w_pn_identification_tag_print";

		
		ms_pn.s_employee = "ADM";
		
		ms_pn.s_message ="SERVICETAG";
	
		poster.addJobToJMSQueueService("emroDS", "w_pn_identification_tag_print"
				, "pn identification tag print"
				, "ADM", getSeqNo(), ms_pn);
		
	}

	private Integer getBatch(I19_Response response) {
		System.out.println("Finding next seq");
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			String sql = ("select batch from PN_INVENTORY_HISTORY  " + 
					"where TRANSACTION_NO = ?");
			
			pstmt1 = con.prepareStatement(sql);
			pstmt1.setString(1, response.getTransaction());
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


}
