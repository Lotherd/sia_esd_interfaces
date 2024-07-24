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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import trax.aero.controller.InstallRemoveSVOController;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.I19_Request;
import trax.aero.pojo.I19_Response;
import trax.aero.utils.DataSourceClient;


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


public class InstallRemoveSvoData {
	EntityManagerFactory factory;
	EntityManager em;
	String exceuted;
	private Connection con;
	
	final String MaxRecord = System.getProperty("InstallRemoveSVO_MaxRecord");
	//public InterfaceLockMaster lock;
	Logger logger = LogManager.getLogger("InstallRemoveSVO_I19");

	
	public InstallRemoveSvoData(String mark)
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
			logger.info("An error occured getting the status of the connection");
			InstallRemoveSVOController.addError(e.toString());
			
		}
		catch (CustomizeHandledException e1) {
			
			InstallRemoveSVOController.addError(e1.toString());
			
		} catch (Exception e) {
			
			InstallRemoveSVOController.addError(e.toString());
			
		}
			
	}
	
	
	public InstallRemoveSvoData()
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
			logger.info("An error occured getting the status of the connection");
			InstallRemoveSVOController.addError(e.toString());
			
		}
		catch (CustomizeHandledException e1) {
			
			InstallRemoveSVOController.addError(e1.toString());
			
		} catch (Exception e) {
			
			InstallRemoveSVOController.addError(e.toString());
			
		}
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		em = factory.createEntityManager();		
	}
	
	public Connection getCon() {
		return con;
	}
	
	
	public String markTransaction(I19_Response request) throws Exception
	{
		//setting up variables
		exceuted = "OK";
		
		String sqlDate =
		"UPDATE PN_INVENTORY_HISTORY SET PN_INVENTORY_HISTORY.SVO_NO = ? WHERE PN_INVENTORY_HISTORY.TRANSACTION_NO = ?";
		
		PreparedStatement pstmt2 = null; 

		try 
		{
			
				pstmt2 = con.prepareStatement(sqlDate);
				
				pstmt2.setString(1, request.getTransaction());
				
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
		
		String sql= "SELECT \n" + 
			    "    A3.PN AS PN,\n" + 
			    "    A3.SN AS SN,\n" + 
			    "    A3.SN AS SN,\n" + 
			    "    A3.REMOVE_INSTALLED_DATE AS REMOVE_INSTALLED_DATE,\n" + 
			    "    A3.LOCATION AS LOCATION,\n" + 
			    "    'LICENCE_TYPE' AS LICENCE_TYPE,\n" + 
			    "    A3.REMOVE_AS_SERVICEABLE AS REMOVE_AS_SERVICEABLE,\n" + 
			    "    A3.INTERNAL_EXTERNAL AS INTERNAL_EXTERNAL,\n" + 
			    "    A3.TRANSACTION_TYPE AS TRANSACTION_TYPE,\n" + 
			    "    A3.REMOVAL_REASON AS REMOVAL_REASON,\n" + 
			    "    A3.NOTES AS NOTES,\n" + 
			    "    A1.CUSTOMER AS CUSTOMER,\n" + 
			    "    A3.RFO_NO AS RFO_NO,\n" + 
			    "    A2.LEGACY_BATCH AS LEGACY_BATCH,\n" + 
			    "    A3.QTY AS QTY,\n" + 
			    "    A3.WO AS WO,\n" + 
			    "    A3.TASK_CARD AS TASK_CARD,\n" + 
			    "    A3.TRANSACTION_NO AS TRANSACTION\n" + 
			    "FROM\n" + 
			    "    PN_INVENTORY_HISTORY A3,\n" + 
			    "    PN_INVENTORY_DETAIL A2,\n" + 
			    "    WO A1\n" + 
			    "WHERE\n" + 
			    "    A3.SVO_NO IS NULL\n" + 
			    "    AND A3.WO IS NOT NULL\n" + 
			    "    AND A3.TASK_CARD IS NOT NULL\n" + 
			    "    AND A3.TRANSACTION_TYPE LIKE '%A/C%'\n" + 
			    "    AND A3.BATCH = A2.BATCH\n" + 
			    "    AND A1.WO = A3.WO";

		
		if(MaxRecord != null && !MaxRecord.isEmpty()) {
			sql=  "SELECT *	FROM ( " + sql;
		}
		
		if(MaxRecord != null && !MaxRecord.isEmpty()) {
			sql= sql + "  )WHERE ROWNUM <= ?";		
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
						Inbound.setPnSn(rs1.getString(3));
					}
					else {
						Inbound.setPnSn("");
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
						Inbound.setLicenceType(rs1.getString(6));
					}
					else {
						Inbound.setLicenceType("");
					}
					
					if(rs1.getString(7) != null && !rs1.getString(7).isEmpty()) {
						Inbound.setRemoveAsServiceable(rs1.getString(7));
					}
					else {
						Inbound.setRemoveAsServiceable("");
					}
					
					if(rs1.getString(8) != null && !rs1.getString(8).isEmpty()) {
						Inbound.setInternalExternal(rs1.getString(8));
					}
					else {
						Inbound.setInternalExternal("");
					}
					
					if(rs1.getString(9) != null && !rs1.getString(9).isEmpty()) {
						Inbound.setTransactionType(rs1.getString(9));
					}
					else {
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
					else {
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
	
	
	public void lockTable(String notificationType)
	{
		em.getTransaction().begin();
		InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type", InterfaceLockMaster.class)
				.setParameter("type", notificationType)
				.getSingleResult();
		lock.setLocked(new BigDecimal(1));
		lock.setLockedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()) );
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
	
	public void unlockTable(String notificationType)
	{
		em.getTransaction().begin();
		InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type", InterfaceLockMaster.class)
				.setParameter("type", notificationType)
				.getSingleResult();
		lock.setLocked(new BigDecimal(0));
		lock.setUnlockedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()) );
	
		em.merge(lock);
		em.getTransaction().commit();
	}



}
