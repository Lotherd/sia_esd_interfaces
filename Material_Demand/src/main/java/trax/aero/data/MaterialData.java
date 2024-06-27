package trax.aero.data;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import trax.aero.client.ServiceClient;

import trax.aero.inbound.MT_TRAX_SND_I10_4110;
import trax.aero.inbound.Order;
import trax.aero.inbound.OrderComponent;
import trax.aero.interfaces.IMaterialData;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceAudit;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.model.PicklistDistribution;
import trax.aero.model.PicklistHeader;
import trax.aero.model.Wo;
import trax.aero.model.WoTaskCard;
import trax.aero.outbound.MT_TRAX_I10_TRAX;
import trax.aero.util.EmailSender;

@Stateless(name="MaterialData" , mappedName="MaterialData")
public class MaterialData implements IMaterialData {
	
	Logger logger = LogManager.getLogger("MaterialDemand_I10");
	
	private static final int X_MINUTES = new Integer(System.getProperty("MaterialStatusImport_Time")) * 60 * 1000;
	
	@PersistenceContext(unitName = "TraxESD") private EntityManager em;
	
	EmailSender emailer = null;
	String error = "";	
	
	ServiceClient client = null;
	public MaterialData()
	{
		emailer = new EmailSender(System.getProperty("MD_toEmail"));
		client = new ServiceClient();
				
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String sendComponent() throws JAXBException
	{
		Calendar cal = null;
		cal = Calendar.getInstance();
		
		cal.add(Calendar.SECOND, -Integer.parseInt(System.getProperty("MD_interval")));
		
		List<PicklistDistribution> updates = null;
		
		
		List<PicklistHeader> headers = null;
		try
		{
					
			headers = em.createQuery("SELECT p FROM PicklistHeader p where p.createdDate >= :date")
					.setParameter("date", cal.getTime())
					.getResultList();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.severe(e.toString());
		}
		
		ArrayList<MT_TRAX_SND_I10_4110> requisitions = new ArrayList<MT_TRAX_SND_I10_4110>();
		Order order = new Order();
		ArrayList<OrderComponent> component = new ArrayList<OrderComponent>();
		ArrayList<Order> orders = new ArrayList<Order>();
		
		
		if(headers != null && headers.size() != 0)
		{
			logger.info("new");
			for(PicklistHeader header :headers)
			{
			
				em.refresh(header);
				
				WoTaskCard card = null;
				Wo w = null;
				
				if(header.getWo() == null && header.getTaskCard() == null) {
					continue;
				}
				MT_TRAX_SND_I10_4110 requisition = new MT_TRAX_SND_I10_4110();
				orders = new ArrayList<Order>();
				requisition.setOrder(orders);
				component = new ArrayList<OrderComponent>();
				Order ord = new Order();
				String rfo = null;
								
				if(header.getWo() != null) {
					w = getWo(header.getWo());
					if(	w.getModule().equalsIgnoreCase("SHOP") && w.getRfoNo() != null) {
						rfo = w.getRfoNo();
					}
					if(rfo != null) {
						ord.setSAP_OrderNumber(rfo);
					}
				}	
				
				try
				{				
					card =  em.createQuery("select w from WoTaskCard w where w.id.wo = :wo and w.id.taskCard = :card", WoTaskCard.class)
							.setParameter("wo", header.getWo().longValue())
							.setParameter("card", header.getTaskCard())
							.getSingleResult();
					em.refresh(card);
				}
				catch(Exception e)
				{
					//e.printStackTrace();
					logger.severe(e.toString());
				}
				
				if(ord.getSAP_OrderNumber() == null ) {
					continue;
				}
				logger.info("CREATED DATE " + header.getCreatedDate() + " >=  CAL DATE " + cal.getTime());
				
				for(PicklistDistribution detail : header.getPicklistDistributions())
				{
					em.refresh(detail);
					
					if(detail.getId().getTransaction().equalsIgnoreCase("REQUIRE")) {
						
					
						if(detail.getInterfaceSyncFlag() != null ||  detail.getInterfaceSyncFlag().equalsIgnoreCase("S")) {
							continue;
						}
						OrderComponent c = new OrderComponent();
						
						
						try {
							if(card != null) {
								if(card.getWoTaskCardItems() != null && !card.getWoTaskCardItems().isEmpty()) {
									c.setOperationNumber(card.getWoTaskCardItems().get(0).getOpsNo());
								}
							}
						}catch(Exception e) {
							logger.info("No item found");
						}
						
						
												
						String pn = detail.getPn();
						
						pn = pn.replaceAll("IN", "\"");
						pn = pn.replaceAll("FT", "'");
						
						if(pn.contains(":UPLOAD"))
						{
							pn=  pn.substring(0, pn.indexOf(":"));
						}
						
						c.setMaterialNumber(pn);
						c.setsHOP_WO_SN(detail.getSn());
						if(c.getsHOP_WO_SN() == null) {
							c.setsHOP_WO_SN("");
						}
						c.setTaskCard(header.getTaskCard());
						if(card != null) {
							c.setTaskCard(card.getId().getTaskCard());
						}else {
							c.setTaskCard("");
						}
						
						c.setwO_Location(w.getLocation());
						if(c.getwO_Location() == null) {
							c.setwO_Location("");
						}
						
						c.setQuantity(detail.getQty().toString());
						c.setTrax_PicklistNumber(String.valueOf(header.getPicklist()));
						c.setTrax_PicklistLine(String.valueOf(detail.getId().getPicklistLine()));
						
						
						c.setReservationNumber(detail.getExternalCustRes());
						c.setReservationItem(detail.getExternalCustResItem());
						if(c.getReservationNumber() != null && !c.getReservationNumber().isEmpty() &&
						   c.getReservationItem() != null && !c.getReservationItem().isEmpty()) {
							continue;
						}
						
						
						c.setDeletionIndicator("");
						c.setReservationNumber("");
						c.setReservationItem("");
						
						boolean match = false;
						
						for(MT_TRAX_SND_I10_4110 r: requisitions) {
							for(Order o: r.getOrder()) {
								
								if(o.getSAP_OrderNumber().equalsIgnoreCase(ord.getSAP_OrderNumber())) {
									o.getOrderComponent().add(c);	
									match = true;
								}
								
							}
						}
						if(match) {
							
							continue;
						}
						component.add(c);
					}
				}
				
				if(component.isEmpty()) {
					
					
					continue;
				}
				logger.info("SIZE" +component.size() );
				ord.setOrderComponent(component);
				requisition.getOrder().add(ord);
				requisitions.add(requisition);
			}
		}
		
		try
		{
			updates = this.em.createQuery("SELECT p FROM PicklistDistribution p where ( p.interfaceSyncDate IS NOT NULL or p.createdDate > :date ) and  ( p.interfaceSyncFlag != :flag or p.interfaceSyncFlag is null)")
					.setParameter("date", cal.getTime())
					.setParameter("flag", "S")
					.getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.severe(e.toString());
		}
		
		if(updates != null && updates.size() > 0)
		{
			
			
			for(PicklistDistribution detail : updates)
			{
				if(detail.getId().getTransaction().equalsIgnoreCase("REQUIRE")) {
					if(detail.getPicklistHeader().getWo() == null || detail.getPicklistHeader().getWo() == null) {
						continue;
					}
					
					em.refresh(detail);
					
					
					if(detail.getInterfaceSyncFlag() != null &&  detail.getInterfaceSyncFlag().equalsIgnoreCase("S")) {
						continue;
					}
	
					OrderComponent c = new OrderComponent();
					Order ord = new Order();
					component = new ArrayList<OrderComponent>();
					orders = new ArrayList<Order>();
					
					MT_TRAX_SND_I10_4110 requisition = new MT_TRAX_SND_I10_4110();
					requisition.setOrder(orders);
					Wo w = null;
					
					
					if(detail.getPicklistHeader().getWo() != null) {
						String rfo = null;
						w = getWo(detail.getPicklistHeader().getWo());
						if(	w.getModule().equalsIgnoreCase("SHOP") && w.getRfoNo() != null) {
							rfo = w.getRfoNo();
						}
						if(rfo != null) {
							ord.setSAP_OrderNumber(rfo);
						}
					}
					
					WoTaskCard card = null;
					try
					{
						card = (WoTaskCard) this.em.createQuery("select w from WoTaskCard w where w.id.wo = :wo and w.id.taskCard = :card")
								.setParameter("wo", detail.getPicklistHeader().getWo().longValue())
								.setParameter("card",  detail.getPicklistHeader().getTaskCard())
								.getSingleResult();
						em.refresh(card);
					}
					catch(Exception e)
					{
						//e.printStackTrace();
						logger.warning("No Task card found for picklist: " + detail.getId().getPicklist()
								+ " " + e.getMessage());
					}
					
					if(ord.getSAP_OrderNumber() != null) {
												
						try {
							if(card != null) {
								if(card.getWoTaskCardItems() != null && !card.getWoTaskCardItems().isEmpty()) {
									c.setOperationNumber(card.getWoTaskCardItems().get(0).getOpsNo());
								}
							}
						}catch(Exception e) {
							logger.info("No item found");
						}
						
					}else {
						continue;
					}
					//logger.info("CREATED DATE " + detail.getCreatedDate() + " >  CAL DATE " + cal.getTime());
					logger.info("update " + detail.getId().getPicklist() + " " + detail.getId().getPicklistLine() + " " +  detail.getId().getTransaction());
					
					String pn = detail.getPn();
					
					pn = pn.replaceAll("IN", "\"");
					pn = pn.replaceAll("FT", "'");
					
					if(pn.contains(":UPLOAD"))
					{
						pn=  pn.substring(0, pn.indexOf(":"));
					}
					
					
					c.setMaterialNumber(pn);
					
					if(detail.getStatus() != null && !detail.getStatus().isEmpty() && 
							detail.getStatus().equalsIgnoreCase("CANCEL")) {
						c.setDeletionIndicator("X");
					}else {
						c.setDeletionIndicator("");
					}
					
					if(detail.getExternalCustToQty() != null 
							&& detail.getExternalCustToQty().equals(detail.getQtyPicked())) {
						c.setDeletionIndicator("F");
					}else {
						c.setDeletionIndicator("");
					}
					
					
					c.setQuantity(detail.getQty().toString());
					c.setTrax_PicklistNumber(String.valueOf(detail.getPicklistHeader().getPicklist()));
					c.setTrax_PicklistLine(String.valueOf(detail.getId().getPicklistLine()));
					
					c.setMaterialNumber(pn);
					c.setsHOP_WO_SN(detail.getSn());
					if(c.getsHOP_WO_SN() == null) {
						c.setsHOP_WO_SN("");
					}
					c.setTaskCard(detail.getPicklistHeader().getTaskCard());
					if(card != null) {
						c.setTaskCard(card.getId().getTaskCard());
					}else {
						c.setTaskCard("");
					}
					c.setwO_Location(w.getLocation());
					if(c.getwO_Location() == null) {
						c.setwO_Location("");
					}
					
					c.setReservationNumber(detail.getExternalCustRes());
					if(c.getReservationNumber() == null) {
						c.setReservationNumber("");
					}
					c.setReservationItem(detail.getExternalCustResItem());
					if(c.getReservationItem() == null) {
						c.setReservationItem("");
					}
					ord.setOrderComponent(component);
					
					//requisition.setOrder(order);
					//results.add(requisition);
					boolean match = false;
					for(MT_TRAX_SND_I10_4110 r: requisitions) {
						for(Order o: r.getOrder()) {
							for(OrderComponent com: o.getOrderComponent()){
								
								if(c.getMaterialNumber().equalsIgnoreCase(com.getMaterialNumber()) &&
								c.getTrax_PicklistLine().equalsIgnoreCase(com.getTrax_PicklistLine())  &&	
								c.getTrax_PicklistNumber().equalsIgnoreCase(com.getTrax_PicklistNumber())
								)
								{
									
									match = true;
								}
							}
	
						}
											
						
					}
					if(match) {
						continue;
					}
					match = false;
					for(MT_TRAX_SND_I10_4110 r: requisitions) {
						for(Order o: r.getOrder()) {
							if(o.getSAP_OrderNumber().equalsIgnoreCase(ord.getSAP_OrderNumber())) {
								o.getOrderComponent().add(c);
								match = true;
							}
							
						}
					}
					if(match) {
						continue;
					}
						
					
					component.add(c);
					logger.info("SIZE" +component.size() );
					ord.setOrderComponent(component);
					requisition.getOrder().add(ord);
					requisitions.add(requisition);
				}
			}
			
			
			
		}
		if(requisitions != null && requisitions.size() > 0) {
		
			for(MT_TRAX_SND_I10_4110 requisition : requisitions) {
				JAXBContext jc = JAXBContext.newInstance(MT_TRAX_SND_I10_4110.class);
				Marshaller marshaller = jc.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				marshaller.marshal(requisition, sw);
				
				logger.info("Ouput: " + sw.toString());
				
				if(!client.callSap(requisition))
				{
					String os = "";
					for(Order r : requisition.getOrder()) {
						os = os + "( OrderNumber: "+ r.getSAP_OrderNumber()+ "),";
					}
					markSentFailed(requisition);
					emailer.sendEmail("Trax was unable to call SAP Orders:\n" +os);
					logError("Trax was unable to call SAP Orders:\n" +os);
				}else {
					markSent(requisition);
				}
			}
		}
		
		
		checkMaterialStatusImport();
		return null;
		
	}
	
	
	

	private Wo getWo(BigDecimal wo) {
		
		try
		{	
			Wo work = em.createQuery("SELECT w FROM Wo w where w.wo = :param", Wo.class)
					.setParameter("param", wo.longValue()).getSingleResult();
			return work;
		}
		catch(NoResultException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private void markSent(MT_TRAX_SND_I10_4110 data) {
		try {
			
			for(Order o : data.getOrder()) {
				for(OrderComponent c: o.getOrderComponent()) {
					
					
					PicklistDistribution require = (PicklistDistribution) em.createQuery("SELECT p FROM PicklistDistribution p where p.id.picklist =:pick AND p.id.picklistLine =:line AND p.id.transaction =:tra")
							.setParameter("pick", Long.valueOf(c.getTrax_PicklistNumber()))
							.setParameter("line", Long.valueOf(c.getTrax_PicklistLine()))
							.setParameter("tra", "REQUIRE")
							.getSingleResult();
					require.setInterfaceSyncFlag("S");
					insertData(require);
					
					try {
						PicklistDistribution req = (PicklistDistribution) em.createQuery("SELECT p FROM PicklistDistribution p where p.id.picklist =:pick AND p.id.picklistLine =:line AND p.id.transaction =:tra")
								.setParameter("pick", Long.valueOf(c.getTrax_PicklistNumber()))
								.setParameter("line", Long.valueOf(c.getTrax_PicklistLine()))
								.setParameter("tra", "DISTRIBU")
								.getSingleResult();
						
						req.setInterfaceSyncFlag("S");
						insertData(req);
						
					}catch(Exception e) {
						logger.info(e.getMessage());
					}	
				}
			}	
			
		}catch(Exception e) {
			logger.info(e.getMessage());
		}		
	}
	
	
	private void markSentFailed(MT_TRAX_SND_I10_4110 data) {
		try {
			
			for(Order o : data.getOrder()) {
				for(OrderComponent c: o.getOrderComponent()) {
					
					
					PicklistDistribution require = (PicklistDistribution) em.createQuery("SELECT p FROM PicklistDistribution p where p.id.picklist =:pick AND p.id.picklistLine =:line AND p.id.transaction =:tra")
							.setParameter("pick", Long.valueOf(c.getTrax_PicklistNumber()))
							.setParameter("line", Long.valueOf(c.getTrax_PicklistLine()))
							.setParameter("tra", "REQUIRE")
							.getSingleResult();
					require.setInterfaceSyncDate(new Date());
					require.setInterfaceSyncFlag("Y");
					insertData(require);
					
					try {
						PicklistDistribution req = (PicklistDistribution) em.createQuery("SELECT p FROM PicklistDistribution p where p.id.picklist =:pick AND p.id.picklistLine =:line AND p.id.transaction =:tra")
								.setParameter("pick", Long.valueOf(c.getTrax_PicklistNumber()))
								.setParameter("line", Long.valueOf(c.getTrax_PicklistLine()))
								.setParameter("tra", "DISTRIBU")
								.getSingleResult();
						
						require.setInterfaceSyncDate(new Date());
						require.setInterfaceSyncFlag("Y");
						insertData(req);
						
					}catch(Exception e) {
						logger.info(e.getMessage());
					}	
				}
			}	
			
		}catch(Exception e) {
			logger.info(e.getMessage());
		}				
	}
	
	
	

	
	
	
	
	
	
	
	

	public void acceptReq(MT_TRAX_I10_TRAX r)
	
	{
		for(trax.aero.outbound.Order reqs : r.getOrder()) {

			for(trax.aero.outbound.OrderComponent oc : reqs.getOrderComponent()) {
				PicklistDistribution require = (PicklistDistribution) em.createQuery("SELECT p FROM PicklistDistribution p where p.id.picklist =:pick AND p.id.picklistLine =:line AND p.id.transaction =:tra")
				.setParameter("pick", Long.valueOf(oc.getPICKLIST()))
				.setParameter("line", Long.valueOf(oc.getPICKLIST_LINE()))
				.setParameter("tra", "REQUIRE")
				.getSingleResult();
				//require.setInterfaceSyncFlag(null);
				require.setInterfaceSyncDate(null);
				require.setInterfaceModifiedDate(new Date());
				require.setExternalCustRes(oc.getEXTERNAL_CUST_RES());
				require.setExternalCustResItem(oc.getEXTERNAL_CUST_RES_ITEM());
				insertData(require);
		
				try {
					PicklistDistribution req = (PicklistDistribution) em.createQuery("SELECT p FROM PicklistDistribution p where p.id.picklist =:pick AND p.id.picklistLine =:line AND p.id.transaction =:tra")
							.setParameter("pick", Long.valueOf(oc.getPICKLIST()))
							.setParameter("line", Long.valueOf(oc.getPICKLIST_LINE()))
							.setParameter("tra", "DISTRIBU")
							.getSingleResult();
					req.setExternalCustRes(oc.getEXTERNAL_CUST_RES());
					req.setExternalCustResItem(oc.getEXTERNAL_CUST_RES_ITEM());
					req.setInterfaceModifiedDate(new Date());
					insertData(require);
				
				}catch(Exception e) {
					logger.severe(e.toString());
				}
			}
			
			if(reqs.getEXCEPTION_ID() != null && reqs.getEXCEPTION_ID().equalsIgnoreCase("53"))
			{
				
				logger.info("IDOCStatus 53");
				
			}else if(reqs.getEXCEPTION_DETAIL() != null){
				logger.info("IDOCStatus 51");
				String orders = "";
						
				for(trax.aero.outbound.OrderComponent oc : reqs.getOrderComponent()) {
					orders = orders + "( RequistionNumber: "+ oc.getEXTERNAL_CUST_RES() 
					+ " Requistionline: "+ oc.getEXTERNAL_CUST_RES_ITEM() + "),";
				}	
			
				emailer.sendEmail("Received acknowledgement with EXCEPTION_ID: " + reqs.getEXCEPTION_ID() +", EXCEPTION_DETAIL: "+reqs.getEXCEPTION_DETAIL()+"\n"+ orders) ;
				logError("Received acknowledgement with EXCEPTION_ID: " + reqs.getEXCEPTION_ID() +", EXCEPTION_DETAIL: "+reqs.getEXCEPTION_DETAIL()+"\n"+ orders);
			}else {
				logger.info("IDOCStatus unkown");
				String orders = "";
				
						
				for(trax.aero.outbound.OrderComponent oc : reqs.getOrderComponent()) {
						orders = orders + "( RequistionNumber: "+ oc.getEXTERNAL_CUST_RES()
						+ " Requistionline: "+ oc.getEXTERNAL_CUST_RES_ITEM() + "),";
				}		
						
					
				emailer.sendEmail("Received acknowledgement with NULL Success Error Log\n" +orders ) ;
				logError("Received acknowledgement with NULL Success Error Log\n" +orders);
			}
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
			String exceuted = "insertData has encountered an Exception: "+e.toString();
			logger.severe(exceuted);
			e.printStackTrace();
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

	@Override
	public void logError(String error) {
		
		InterfaceAudit ia = null;
		ia = new InterfaceAudit();
		ia.setTransaction(getSeqNoInterfaceAudit().longValue());
		ia.setTransactionType("ERROR");
		ia.setTransactionObject("I10");
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
	
	
	private void checkMaterialStatusImport() {
		
		List<PicklistDistribution> list = null;
		try
		{
			list = this.em.createQuery("SELECT p FROM PicklistDistribution p where ( p.externalCustRes IS NOT NULL and p.externalCustRes IS NOT NULL ) and "
					+ " ( p.externalCustTo IS NULL)  AND p.id.transaction =:tra  "
					+ "AND p.interfaceModifiedDate IS NOT NULL")
					.setParameter("tra", "DISTRIBU")
					.getResultList();
					
			if(list != null && list.size() > 0){		
				long xAgo = System.currentTimeMillis() - X_MINUTES;
				for(PicklistDistribution p: list) {
					if (p.getInterfaceModifiedDate() != null  && 
						p.getInterfaceModifiedDate().getTime() < xAgo) {
						triggerInt46(p.getPn());
					}
				}
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.severe(e.toString());
		}
		
		
		
		
		
	}
	
	private void triggerInt46(String pn) {
		String sql = "UPDATE PN_MASTER SET PN_MASTER.INTERFACE_TRANSFERRED_DATE = sysdate "
				+ "WHERE PN_MASTER.PN = ?";
		try
		{
			em.createNativeQuery(sql).setParameter(1, pn).executeUpdate();	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.severe(e.toString());
		}
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


