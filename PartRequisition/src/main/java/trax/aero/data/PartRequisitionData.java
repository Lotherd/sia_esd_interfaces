package trax.aero.data;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.client.ServiceClient;
import trax.aero.inbound.MT_TRAX_SND_I21_4121_REQ;
import trax.aero.interfaces.IPartRequisitionData;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.model.NotePad;
import trax.aero.model.OrderDetail;
import trax.aero.model.OrderHeader;

import trax.aero.model.Wo;
import trax.aero.model.WoShopDetail;
import trax.aero.outbound.MT_TRAX_RCV_I21_4121_RES;
import trax.aero.util.EmailSender;

@Stateless(name="PartRequisitionData" , mappedName="PartRequisitionData")
public class PartRequisitionData implements IPartRequisitionData {
	
	Logger logger = LogManager.getLogger("PartRequisition_I21");
	
	@PersistenceContext(unitName = "TraxStandaloneDS") private EntityManager em;
	
	EmailSender emailer = null;
	String error = "";
	
	
	
	
	
	
	ServiceClient client = null;
	public PartRequisitionData()
	{
		emailer = new EmailSender(System.getProperty("PR_toEmail"));
		client = new ServiceClient();
				
	}
	
	@SuppressWarnings("unchecked")
	public String sendComponent() throws JAXBException
	{
		Calendar cal = null;
		//List<DTTRAXI414066> results = new ArrayList<>();
		 cal = Calendar.getInstance();
		
		cal.add(Calendar.SECOND, -Integer.parseInt(System.getProperty("PR_interval")));
		
		List<OrderDetail> details = null;
		ArrayList<MT_TRAX_SND_I21_4121_REQ> requisitions = new ArrayList<MT_TRAX_SND_I21_4121_REQ>();
				
		try
		{
			details = this.em.createQuery("SELECT p FROM OrderDetail p where "
					+ "( p.interfaceSyncDate IS NULL  ) and p.id.orderType = :type and   "
					+ "( p.interfaceSyncFlag != :flag or p.interfaceSyncFlag is null) and "
					+ " p.wo is not null")
					.setParameter("flag", "S")
					.setParameter("type", "RO")
					.getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.severe(e.toString());
		}
		
		if(details != null && details.size() > 0)
		{
			for(OrderDetail detail : details)
			{
					if(detail.getOrderHeader().getWo() == null 
							|| !detail.getOrderHeader().getId().getOrderType().equalsIgnoreCase("RO")) {
						continue;
					}
					
					em.refresh(detail);
					
					
					if(detail.getInterfaceSyncFlag() != null &&  detail.getInterfaceSyncFlag().equalsIgnoreCase("S")) {
						continue;
					}
					
					
					
					MT_TRAX_SND_I21_4121_REQ requisition = new MT_TRAX_SND_I21_4121_REQ();
					Wo w = null;
					if(detail.getOrderHeader().getWo() != null) {
						String rfo = null;
						w = getWo(detail.getOrderHeader().getWo());
						if(	w.getModule().equalsIgnoreCase("SHOP") && w.getRfoNo() != null) {
							rfo = w.getRfoNo();
						}
						if(rfo == null) {
							continue;
						}
					}
								
					
					String pn = detail.getPn();
					String sn = "";
					pn = pn.replaceAll("IN", "\"");
					pn = pn.replaceAll("FT", "'");
					
					if(pn.contains(":UPLOAD"))
					{
						pn=  pn.substring(0, pn.indexOf(":"));
					}
					if(w.getWoShopDetails() != null) {
						sn = w.getWoShopDetails().get(0).getPnSn();
					}
					
					requisition.setMaterial(pn);
					requisition.setESN(sn);
					requisition.setQuantity(detail.getQtyRequire().toString());
					requisition.setTrax_repair_order(String.valueOf(detail.getId().getOrderNumber()));
					requisition.setTrax_repair_order_line(String.valueOf(detail.getId().getOrderLine()));
					requisition.setWO(new Long( w.getWo()).toString());
					requisition.setTrax_WO_location(w.getLocation());
					requisition.setDelivery_date(convertDateToString(detail.getDeliveryDate()));	
					requisition.setItem_text(detail.getRemarks());
					
				
					requisitions.add(requisition);
				}
		}
		
		   
		
		if(requisitions != null && requisitions.size() > 0) {
		
			Set<MT_TRAX_SND_I21_4121_REQ> s= new HashSet<MT_TRAX_SND_I21_4121_REQ>();
		    s.addAll(requisitions);         
		    requisitions = new ArrayList<MT_TRAX_SND_I21_4121_REQ>();
		    requisitions.addAll(s);  
			
			for(MT_TRAX_SND_I21_4121_REQ requisition : requisitions) {
				JAXBContext jc = JAXBContext.newInstance(MT_TRAX_SND_I21_4121_REQ.class);
				Marshaller marshaller = jc.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				marshaller.marshal(requisition, sw);
				
				logger.info("Ouput: " + sw.toString());
				
				if(!client.callSap(requisition))
				{
					
					markSentFailed(requisition);
					emailer.sendEmail("Trax was unable to call SAP Order:" +requisition.getTrax_repair_order() 
					+" Line:"+ requisition.getTrax_repair_order_line(), "Part Requisition Interface Message");
				}else {
					markSent(requisition);
					
					MT_TRAX_RCV_I21_4121_RES input = null;
					
					try 
			        {    
						String body = client.getBody();
						StringReader sr = new StringReader(body);				
						jc = JAXBContext.newInstance(MT_TRAX_RCV_I21_4121_RES.class);
				        Unmarshaller unmarshaller = jc.createUnmarshaller();
				        input = (MT_TRAX_RCV_I21_4121_RES) unmarshaller.unmarshal(sr);

				        marshaller = jc.createMarshaller();
				        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
				        sw = new StringWriter();
					    marshaller.marshal(input,sw);
					    logger.info("Input: " + sw.toString());
					    
					    acceptReq(input);
					    
			        }catch (Exception e) {
						e.printStackTrace();
					}    
					
				}
			}
		}	
		return null;
		
	}
	
	
	private String getNote(BigDecimal notes) {
		try 
		{
			NotePad notepad = em.createQuery("Select n from NotePad n where n.id.notes = :not and n.id.notesLine = :text", NotePad.class)
					.setParameter("not",notes)
					.setParameter("text", 1l)
					.getSingleResult();
			return notepad.getNotesText();
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return "";
		}
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

	private void markSent(MT_TRAX_SND_I21_4121_REQ data) {
		try {
			OrderDetail require = (OrderDetail) em.createQuery("SELECT p FROM OrderDetail"
					+ " p where p.id.orderNumber =:pick AND p.id.orderLine =:line AND p.id.orderType =:tra")
					.setParameter("pick", Long.valueOf(data.getTrax_repair_order()))
					.setParameter("line", Long.valueOf(data.getTrax_repair_order_line()))
					.setParameter("tra", "RO")
					.getSingleResult();
					require.setInterfaceSyncFlag("S");
					insertData(require);
		}catch(Exception e) {
			logger.info(e.getMessage());
		}		
	}
	
	
	private void markSentFailed(MT_TRAX_SND_I21_4121_REQ data) {
		try {	
					
				OrderDetail require = (OrderDetail) em.createQuery("SELECT p FROM OrderDetail"
						+ " p where p.id.orderNumber =:pick AND p.id.orderLine =:line AND p.id.orderType =:tra")
						.setParameter("pick", Long.valueOf(data.getTrax_repair_order()))
						.setParameter("line", Long.valueOf(data.getTrax_repair_order_line()))
						.setParameter("tra", "RO")
						.getSingleResult();
				require.setInterfaceSyncDate(new Date());
				require.setInterfaceSyncFlag("Y");
				insertData(require);
					
		}catch(Exception e) {
			logger.info(e.getMessage());
		}				
	}
	
	
	
	
	public void acceptReq(MT_TRAX_RCV_I21_4121_RES reqs)
	
	{
		OrderDetail require = (OrderDetail) em.createQuery("SELECT p FROM OrderDetail"
				+ " p where p.id.orderNumber =:pick AND p.id.orderLine =:line AND p.id.orderType =:tra")
				.setParameter("pick", Long.valueOf(reqs.getTrax_repair_order()))
				.setParameter("line", Long.valueOf(reqs.getTrax_repair_order_line()))
				.setParameter("tra", "RO")
				.getSingleResult();
		
		require.setExternalKPRNumber(reqs.getKPR_number());
		require.setExternalPRItem(reqs.getPR_item());
		require.setExternalReleaseStrategy(reqs.getRelease_Strategy());
		
		
			if(reqs.getMessage_code() != null && reqs.getMessage_code().equalsIgnoreCase("53"))
			{
				logger.info("IDOCStatus 53");
				
				if(require.getExternalKPRNumber() !=null) {
					OrderHeader header = (OrderHeader) em.createQuery("SELECT p FROM OrderHeader"
							+ " p where p.id.orderNumber =:pick and p.id.orderType =:tra")
							.setParameter("pick",  require.getOrderHeader().getId().getOrderNumber())
							.setParameter("tra", require.getOrderHeader().getId().getOrderType())
							.getSingleResult();
					header.setStatus("CLOSED");
					require.setStatus("CLOSED");
					insertData(header);
				}
				//require.setInterfaceSyncFlag(null);
				require.setInterfaceSyncDate(null);
				insertData(require);
				//TODO
				Wo w = getWo(require.getOrderHeader().getWo());
				String esn = "";
				if(w.getWoShopDetails() != null) {
					esn = w.getWoShopDetails().get(0).getPnSn();
				}
				
				emailer.sendEmail("Kindly get the PR approved and released in SAP: "+ System.lineSeparator() + System.lineSeparator() 
				+"WO # "+require.getOrderHeader().getWo()+ System.lineSeparator() + System.lineSeparator() 
				+"PR # "+reqs.getKPR_number()+" and Strategic code "+reqs.getRelease_Strategy()+ System.lineSeparator() + System.lineSeparator() 
				+"ESN # "+esn + System.lineSeparator() + System.lineSeparator() 
				+"TC# "+require.getTaskCard() + System.lineSeparator() + System.lineSeparator() 
				+"REMOVAL PN : "+require.getPn() +", SN : "+require.getSn() + System.lineSeparator(),
				 "Purchase Requisition Successful for WO:" + require.getOrderHeader().getWo() + " PN :" +require.getPn());
				
			}else if(reqs.getMessage_code() != null){
				logger.info("IDOCStatus 51");
				String orders = "";		
				require.setInterfaceSyncDate(new Date());
				require.setInterfaceSyncFlag("Y");
				
				insertData(require);
				orders = orders + "( OrderNumber: "+ reqs.getTrax_repair_order() + " line: "+ reqs.getTrax_repair_order_line() + "),";
				emailer.sendEmail("Received acknowledgement with Status: " + reqs.getMessage_code() +", Error: "+reqs.getSuccess_Error_message() +"\n"+ orders,
						"Part Requisition Interface Message") ;
			}else {
				logger.info("IDOCStatus unkown");
				String orders = "";			
				require.setInterfaceSyncFlag("Y");
				require.setInterfaceSyncDate(new Date());
				
						
				insertData(require);
						
				orders = orders + "( OrderNumber: "+ reqs.getTrax_repair_order() + " line: "+ reqs.getTrax_repair_order_line() + "),";
					
				emailer.sendEmail("Received acknowledgement with NULL Success Error Log\n" +orders,
						"Part Requisition Interface Message") ;
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
	
	private String convertDateToString( Date date) {
		try {
			//dd-mm-yyyy
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			return df.format(date); 
		}catch(Exception e) {
			throw e;
		}
	}
	
	
}


