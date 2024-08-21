package trax.aero.data;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.model.Wo;
import trax.aero.pojo.I74_Request;
import trax.aero.util.EmailSender;


public class ModelData {
	
	long Cuslong = 0001;
	
	EntityManagerFactory factory = null;
	EntityManager em = null;
	public EmailSender emailer = null;
	public String error = "";
	Logger logger = LogManager.getLogger("Techdoc_I20_I26");

	
	public String wo = "";
	public String ac = "";
	
	
	
	
	public ModelData()
	{
		factory = Persistence.createEntityManagerFactory("ZprintDS");
		em = factory.createEntityManager();
		emailer = new EmailSender(System.getProperty("TECH_toEmail"));
		
		
	}
	
	
	public String markTransaction(I74_Request req) throws Exception
	{
		//setting up variables
		String exceuted = "OK";
		
		

		try 
		{
			
				Wo wo = em.createQuery("Select w From Wo w where w.id.wo =:work", Wo.class)
						.setParameter("work",Long.valueOf( req.getReasonForTECO_reversal()))
						.getSingleResult();
				
				wo.setInterfaceModifiedDate(new Date());
				insertData(wo);
				
		}
		catch (Exception e) 
        {            
            exceuted = e.toString();
            logger.severe(exceuted);
		}
		
		return exceuted;
		
	}
	
	public ArrayList<I74_Request> getTaskCards() throws Exception
	{
		
		ArrayList<I74_Request> list = new ArrayList<I74_Request>();
		List<Wo> wos = null;

		
	
				
	
		try 
		{
			try
			{
				wos = this.em.createQuery("SELECT p FROM Wo p where "
						+ "( p.interfaceModifiedDate IS NULL  ) and p.module = :type and   "
						+ " p.rfo is not null")
						.setParameter("flag", "SHOP")
						.getResultList();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.severe(e.toString());
			}
			
			if(wos != null && wos.size() > 0)
			{
				for(Wo wo : wos)
				{
					
					logger.info("Processing WO : " + wo.getWo() + " RFO: " + wo.getRfoNo());
					I74_Request Inbound = new I74_Request();
						
					Inbound.setOrderNumber(String.valueOf(wo.getWo()));
					Inbound.setReasonForTECO_reversal(wo.getRfoNo());
					
					String pn = "";
					String sn = "";
					
					if(wo.getWoShopDetails() != null) {
						sn = wo.getWoShopDetails().get(0).getPnSn();
						pn =wo.getWoShopDetails().get(0).getPn();
					}
					pn = pn.replaceAll("IN", "\"");
					pn = pn.replaceAll("FT", "'");
					
					if(pn.contains(":UPLOAD"))
					{
						pn=  pn.substring(0, pn.indexOf(":"));
					}
					
					list.add(Inbound);	
					
				}
			}
			
			
		}
		catch (Exception e) 
        {
            logger.severe(e.toString());
            throw new Exception("Issue found");
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
			
		
}
