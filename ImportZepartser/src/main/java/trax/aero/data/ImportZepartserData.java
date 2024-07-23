package trax.aero.data;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.exception.ExceptionUtils;


import trax.aero.controller.ImportZepartserController;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.ZEPARTSER;


public class ImportZepartserData {

	EntityManagerFactory factory;
	//public InterfaceLockMaster lock;
	
	
	public EntityManager em;
	
			
	Logger logger = LogManager.getLogger("ImportZepartser_I23");
	
	public ImportZepartserData(EntityManagerFactory factory)
	{
		this.factory = factory;
		em = factory.createEntityManager();

	}
	
	public ImportZepartserData()
	{
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		em = factory.createEntityManager();		
	}
	
	@Transactional
	public boolean clearZepartserTable()  {
		
		String query = "DELETE ZEPARTSER_MASTER";		
		try
		{	
			logger.info("DELETING ZEPARTSER_MASTER");

			em.getTransaction().begin();
			em.createNativeQuery(query).executeUpdate();	
			em.getTransaction().commit();
			return true;
		}
		catch (Exception e) 
		{
			em.getTransaction().rollback();
			logger.severe("An Exception occurred executing the query to delete Zepartser Table . " + "\n error: " + ExceptionUtils.getStackTrace ( e ));
			ImportZepartserController.addError(ExceptionUtils.getStackTrace ( e ));	
			return false;
		}
	}
	
	
	
	@Transactional
	public boolean insertZepartser(ZEPARTSER z)
	{
		String query = "INSERT INTO ZEPARTSER_MASTER (CUSTOMER, PN) VALUES (?, ?)";
		
		try
		{
			
			String partNumber_Tool ;
			partNumber_Tool = z.getMaterialNumber().replaceAll("\"", "IN");
			partNumber_Tool = partNumber_Tool.replaceAll("'", "FT");
			partNumber_Tool = partNumber_Tool.replaceAll(",", "");

			if(!partNumber_Tool.contains(":"))
			{
				partNumber_Tool = partNumber_Tool.concat(":UPLOAD");
			}
			z.setMaterialNumber(partNumber_Tool);
			logger.info("INSERTING CUSTOMER: " + z.getCustomer() + " MATERIAL: " + z.getMaterialNumber());
			
			em.getTransaction().begin();
			em.createNativeQuery(query).setParameter(1, z.getCustomer())
			.setParameter(2, z.getMaterialNumber()).executeUpdate();
			em.getTransaction().commit();
			return true;
		}
		catch (Exception e) 
		{
			em.getTransaction().rollback();
			logger.severe("An Exception occurred executing the query to set ZEPARTSER_MASTER. " + "\n error: " + ExceptionUtils.getStackTrace ( e ) );
			ImportZepartserController.addError(ExceptionUtils.getStackTrace ( e ));	
			return false;
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
	
	
	
	
	
}
