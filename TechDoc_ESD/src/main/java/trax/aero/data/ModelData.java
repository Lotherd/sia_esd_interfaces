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
import trax.aero.pojo.I9_I29_Request;
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
	
	
	public String markTransaction(I9_I29_Request req) throws Exception
	{
		//setting up variables
		String exceuted = "OK";
		
		

		try 
		{
			
				Wo wo = em.createQuery("Select w From Wo w where w.id.wo =:work", Wo.class)
						.setParameter("work",Long.valueOf( req.getWO()))
						.getSingleResult();
				logger.info("MARKING WO " +wo.getWo());

				wo.setInterfaceCreatedDate(new Date());
				insertData(wo);
				
		}
		catch (Exception e) 
        {            
            exceuted = e.toString();
            logger.severe(exceuted);
		}
		
		return exceuted;
		
	}
	
	public ArrayList<I9_I29_Request> getTaskCards() throws Exception
	{
		
		ArrayList<I9_I29_Request> list = new ArrayList<I9_I29_Request>();
		List<Wo> wos = null;

		
	
				
	
		try 
		{
			try
			{
				wos = this.em.createQuery("SELECT p FROM Wo p where "
						+ "( p.interfaceCreatedDate IS NULL  ) and p.module = :type and   "
						+ " p.rfoNo is not null and p.thirdPartyWo = :party and "
						+ "( p.sourceType = :so or p.sourceType = :sou )")
						.setParameter("type", "SHOP")
						.setParameter("party", "Y")
						.setParameter("so", "E8")
						.setParameter("sou", "X3")
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
					I9_I29_Request Inbound = new I9_I29_Request();
					em.refresh(wo);
					Inbound.setWO(String.valueOf(wo.getWo()));
					Inbound.setRFO_NO(wo.getRfoNo());
					
					String pn = "";
					String sn = "";
					
					if(wo.getWoShopDetails() != null && !wo.getWoShopDetails().isEmpty()) {
						sn = wo.getWoShopDetails().get(0).getPnSn();
						pn =wo.getWoShopDetails().get(0).getPn();
					}
					pn = pn.replaceAll("IN", "\"");
					pn = pn.replaceAll("FT", "'");
					
					if(pn.contains(":UPLOAD"))
					{
						pn=  pn.substring(0, pn.indexOf(":"));
					}
					Inbound.setPN(pn);
					Inbound.setPN_SN(sn);
					wo.setInterfaceCreatedDate(new Date());
					logger.info("MARKING WO " +wo.getWo());
					insertData(wo);
					list.add(Inbound);	
					
				}
			}
			
			
		}
		catch (Exception e) 
        {
			e.printStackTrace();
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
