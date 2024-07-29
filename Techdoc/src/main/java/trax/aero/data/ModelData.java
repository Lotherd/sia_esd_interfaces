package trax.aero.data;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.text.StringEscapeUtils;

import trax.aero.logger.LogManager;
import trax.aero.model.AcMaster;
import trax.aero.model.AcTypeSeriesMaster;
import trax.aero.model.BlobTable;
import trax.aero.model.BlobTablePK;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.model.PicklistDistribution;
import trax.aero.model.PicklistDistributionPK;
import trax.aero.model.PicklistHeader;
import trax.aero.model.PnMaster;
import trax.aero.model.TaskCard;

import trax.aero.model.TaskCardControlMaster;
import trax.aero.model.TaskCardControlMasterPK;

import trax.aero.model.TaskCardExecution;
import trax.aero.model.TaskCardItem;
import trax.aero.model.TaskCardKeyword;
import trax.aero.model.TaskCardPn;
import trax.aero.model.Wo;
import trax.aero.model.WoTaskCard;
import trax.aero.model.WoTaskCardControl;
import trax.aero.model.WoTaskCardControlPK;
import trax.aero.model.WoTaskCardCustomer;
import trax.aero.model.WoTaskCardCustomerPK;
import trax.aero.model.WoTaskCardExecution;
import trax.aero.model.WoTaskCardExecutionPK;
import trax.aero.model.WoTaskCardItem;
import trax.aero.model.WoTaskCardItemPK;
import trax.aero.model.WoTaskCardKeyword;
import trax.aero.model.WoTaskCardKeywordPK;
import trax.aero.model.WoTaskCardPK;
import trax.aero.model.WoTaskCardPn;
import trax.aero.model.WoTaskCardPnPK;
import trax.aero.pojo.ADDATTR;
import trax.aero.pojo.MATERIAL;
import trax.aero.pojo.MODEL;
import trax.aero.pojo.TOOLSLIST;
import trax.aero.util.EmailSender;


public class ModelData {
	
	long Cuslong = 0001;
	
	EntityManagerFactory factory = null;
	EntityManager em = null;
	public EmailSender emailer = null;
	public String error = "";
	Logger logger = LogManager.getLogger("Techdoc_I20_I26");
	String cus = "";
	private boolean existWoTaskCard = false;
	private boolean existWoTaskCardCUS = false;
	
	public String wo = "";
	public String ac = "";
	
	
	public ArrayList<WoTaskCard> cusList = new ArrayList<WoTaskCard>();
	
	
	public ModelData()
	{
		factory = Persistence.createEntityManagerFactory("ZprintDS");
		em = factory.createEntityManager();
		emailer = new EmailSender(System.getProperty("TECH_toEmail"));
		
		
	}
	
	
	
	public boolean processData(MODEL data, String pdf, byte[] pdfBytes)
	{
		try
		{
			cus = "";
			
			
			List<ADDATTR> attributes = data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getADDATTR();
			
			WoTaskCard taskCard = new WoTaskCard();
			String taskNBR = filterADDATTR(attributes, "TASK-NBR");
			
			String taskId = "";
			String groupNo = "";
			
			String refer = "";
			
			String wonbr = data.getEFFECTIVITY().getJOBCARD().getWONBR();
			if(wonbr != null)
			{
				if(wonbr.length() > 4)
				{
					refer = (wonbr.substring(wonbr.length()- 4));
				}
			}
			
			
			if(data.getEFFECTIVITY().getJOBCARD().getJCNBR() != null && data.getEFFECTIVITY().getJOBCARD().getJCNBR().length() > 8)
			{
				groupNo = data.getEFFECTIVITY().getJOBCARD().getJCNBR().substring(0, 8);
			}
			else if(data.getEFFECTIVITY().getJOBCARD().getJCNBR() != null)
			{
				groupNo = data.getEFFECTIVITY().getJOBCARD().getJCNBR();
			}
			
			
			if(taskNBR == null || taskNBR.length() == 0 || groupNo == null || groupNo.length() == 0 || 
					refer == null || refer.length() == 0)
			{
				error = error.concat("Error Inserting WoTaskCard " +  System.lineSeparator() + "Id was not provided" +
						 System.lineSeparator() +  System.lineSeparator());
				
				return false;
			}
			
			int length = (taskNBR + "_" + groupNo + "_" + refer).length();
			if(length > 35)
			{
				taskId = taskNBR.substring(0, taskNBR.length() - (length - 35)) +"_" + groupNo + "_" + refer;
			}
			else
			{
				taskId = taskNBR + "_" + groupNo + "_" + refer;
			}
			
			String woo = data.getEFFECTIVITY().getJOBCARD().getWPTITLE();
			wo = woo;
			
			if(woo == null || woo.length() == 0)
			{
				error = error.concat("TaskCard: " +taskId + " WO: " +woo+ " ,Error Inserting WoTaskCard " + System.lineSeparator() + "Wo was not provided" +
						 System.lineSeparator() +  System.lineSeparator());
				
				return false;
			}
			
			logger.info(taskId);
			if(taskCard.getId() == null)
				taskCard.setId(new WoTaskCardPK());
			
			taskCard.getId().setAc(data.getEFFECTIVITY().getREGNBR());
			String pn = filterADDATTR(attributes, "COMP");
			if(pn == null || pn.length() == 0)
			{
				taskCard.getId().setPn("                                   ");
			}
			else
			{
				taskCard.getId().setPn("                                   ");
			}
			
			String sn = filterADDATTR(attributes, "SN");
			if(sn == null || sn.length() == 0)
			{
				taskCard.getId().setPnSn("                                   ");
			}
			else
			{
				taskCard.getId().setPnSn("                                   ");
			}
			
			taskCard = assignValuesWO(taskId,taskCard,Long.parseLong(woo),taskCard.getId().getAc()
					,taskCard.getId().getPn(),taskCard.getId().getPnSn());
			
			
			
			logger.info(""+ taskCard.getWoTaskCardItems().size());
			
			taskCard = assignValues(taskId,taskCard,taskCard.getWoTaskCardItems().get(0));
			
			if(taskCard.getId() == null)
				taskCard.setId(new WoTaskCardPK());
			
			if(taskCard.getWoTaskCardItems() == null)
				taskCard.setWoTaskCardItems(new ArrayList<>());
			
			if(taskCard.getWoTaskCardItems().size() == 0)
				taskCard.addWoTaskCardItem(new WoTaskCardItem());
			
			if(taskCard.getWoTaskCardItems().get(0).getId() == null)
				taskCard.getWoTaskCardItems().get(0).setId(new WoTaskCardItemPK());
			
			
			String taskCardId = taskId;
			
			//= taskNBR + "_" + groupNo + "_" + refer;
			
			if(taskCardId != null && taskCardId.length() != 0)
			{
				taskCard.getId().setTaskCard(taskCardId);
				taskCard.getWoTaskCardItems().get(0).getId().setTaskCard(taskCard.getId().getTaskCard());
			}
			else
			{
				error = error.concat("TaskCard: " +taskId + " WO: " +woo+" ,Error Inserting WoTaskCard "+  System.lineSeparator() + "TaskCard was not provided" +
						 System.lineSeparator() +  System.lineSeparator());
				
				return false;
			}
			

			
			taskCard.getId().setAc(data.getEFFECTIVITY().getREGNBR());
			taskCard.getWoTaskCardItems().get(0).getId().setAc(taskCard.getId().getAc());
			
			ac = taskCard.getId().getAc();
			
			
			taskCard.getWoTaskCardItems().get(0).getId().setTaskCardPn(taskCard.getId().getPn());
			String equiNumber = filterADDATTR(attributes, "EQUI-NUMBER");
			if(equiNumber != null)
			{
				taskCard.setEqptNo(equiNumber);
			}
			
			taskCard.getWoTaskCardItems().get(0).getId().setTaskCardPnSn(taskCard.getId().getPnSn());
			
			String wo = data.getEFFECTIVITY().getJOBCARD().getWPTITLE();
			if(wo == null || wo.length() == 0)
			{
				error = error.concat("TaskCard: " +taskId + " WO: " +woo+ " Error Inserting WoTaskCard "+ "Wo was not provided" +
						 System.lineSeparator() +  System.lineSeparator());
				
				return false;
			}
			else
			{
				taskCard.getId().setWo(Long.parseLong(wo));
			}
			
			taskCard.getWoTaskCardItems().get(0).getId().setWo(taskCard.getId().getWo());
			
			
			
			
			
			
			if(groupNo != null)
			{
				taskCard.setEo(groupNo);
			}
			
			
			if(data.getEFFECTIVITY().getJOBCARD().getJCTITLE() != null)
			{
				taskCard.setTaskCardDescription(data.getEFFECTIVITY().getJOBCARD().getJCTITLE());
			}

			
			String revision = filterADDATTR(attributes, "LATEST-REVISION");
			if(revision != null)
				taskCard.setRevision(revision);

			
			
			
			
			if(data.getEFFECTIVITY().getJOBCARD().getENGINEPOS() != null) {
				taskCard.setFunctionalLocation(data.getEFFECTIVITY().getJOBCARD().getENGINEPOS());
			}
			
			
			
			
			if(data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getCHAPNBR() != null)
			{
				try
				{
					taskCard.setChapter(new BigDecimal(data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getCHAPNBR()));
				}
				catch(Exception e)
				{
					
				}
			}

			
			if(data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getSECTNBR() != null)
			{
				try
				{
					taskCard.setSection(new BigDecimal(data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getSECTNBR()));
				}
				catch(Exception e)
				{
					
				}
			}
				
			
			
			
			String category = filterADDATTR(attributes, "ORDER-TYPE");
			
			if(category != null)
			{
				taskCard.setTaskCardCategory(category);
			}
			else
			{
				error = error.concat("TaskCard: " +taskId + " WO: " +woo+" ,Error Inserting WoTaskCard "+ "Category was not provided" +
						 System.lineSeparator() +  System.lineSeparator());
				
				return false;
			}
			
			String type =data.getEFFECTIVITY().getJOBCARD().getTYPE();
			String priority = filterADDATTR(attributes, "PRIORITY");

			if(type != null)
				taskCard.setType(type);
			if(priority != null)
				taskCard.setPlanningPriority(priority);
			
			if(type != null && priority != null) {
				switch(type) {
					case "MJC":
						taskCard.setTaskCardCategory(type);
						break;
					case "SJC":
						taskCard.setTaskCardCategory(type);
						break;
					case "CCS":
						if(priority.equalsIgnoreCase("O/C"))
							taskCard.setTaskCardCategory(type);
						break;
					case "MCS":
						switch(priority) {
							case "A":
								taskCard.setTaskCardCategory("ADMAF");
								break;
							case "B":
								taskCard.setTaskCardCategory("ALMAF");
								break;
							case "C":
								taskCard.setTaskCardCategory("MAF");
								break;	
						}
					    break;
					case "SI":
						switch(priority) {
							case "1":
								taskCard.setTaskCardCategory("ADSI");
								break;
							case "2":
								taskCard.setTaskCardCategory("ALSI");
								break;
							case "3":
								taskCard.setTaskCardCategory("SI");
								break;	
						}
					    break;    
				}
			}
			
			String ref = data.getEFFECTIVITY().getJOBCARD().getWONBR();
			if(wonbr != null)
			{
				if(wonbr.length() > 4)
				{
					ref = (wonbr.substring(0, wonbr.length()- 4));
					taskCard.setReferenceTaskCard(ref);
				}
			}
			
			
			
			


			
			if(data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getPLITEXT() != null && !data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getPLITEXT().isEmpty())
			{
				String text = data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getPLITEXT();
				
				text=text.replaceAll("&re;", " ");
				text= StringEscapeUtils.unescapeHtml4(text);
				taskCard.getWoTaskCardItems().get(0).setTaskCardText(text);
			}

			
			if(refer != null)
			{
				taskCard.getWoTaskCardItems().get(0).setExternalCustRef(refer);
				taskCard.getWoTaskCardItems().get(0).setOpsNo(refer);
			}
			
			
			
			if(taskCard.getWoTaskCardItems().get(0).getId().getTaskCardItem() == 0)
				taskCard.getWoTaskCardItems().get(0).getId().setTaskCardItem(1);
			
			
			WoTaskCardItem itemComp = taskCard.getWoTaskCardItems().get(0);
			
			
			taskCard.setModifiedBy("TRAXIFACE");
			taskCard.setModifiedDate(new Date());
			
			
			
			
			itemComp.setModifiedBy("TRAXIFACE");
			itemComp.setModifiedDate(new Date());
			
			
			
			taskCard.getWoTaskCardItems().remove(0);
			
			
			if(taskCard.getStatus() !=null && !taskCard.getStatus().isEmpty() 
			&& !taskCard.getStatus().equalsIgnoreCase("OPEN")) {
				logger.info("TaskCard: " + taskCard.getId().getTaskCard() + 
						" Status is not OPEN " 
						+ " Status: " +taskCard.getStatus() );
				return true;
			}
			
			if(getWoThirdParty(taskCard.getId().getWo())  && !getWoShop(woo)) {
				processDataCUS(data, pdf, pdfBytes,taskCard);
			}else {
				logger.info("WO: " + wo + " is not a Third Party WO");
			}
			
			if(getWoShop(woo) && (pdf != null && pdfBytes != null)) {
			
					boolean existBlob = false;
					BlobTable blob = null;
				    	
						try 
						{
							blob = em.createQuery("SELECT b FROM BlobTable b where b.id.blobNo = :bl and b.id.blobLine = :des", BlobTable.class)
									.setParameter("bl", taskCard.getBlobNo().longValue())
									.setParameter("li",new Long(1))
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
							blob.setDocType("TASKCARD");
							blob.getId().setBlobLine(1);
						}
						
						
						
						blob.setModifiedBy("TRAX_IFACE");
						blob.setModifiedDate(new Date());
						blob.setBlobItem(pdfBytes);
						blob.setBlobDescription(pdf);
						blob.setCustomDescription(pdf);
						
						
						
						if(!existBlob && taskCard.getBlobNo() == null) {
							try {
								blob.getId().setBlobNo(((getTransactionNo("BLOB").longValue())));
								taskCard.setBlobNo(new BigDecimal(blob.getId().getBlobNo()));
							} catch (Exception e1) {
								
							}
						}else if(taskCard.getBlobNo() != null){
							blob.getId().setBlobNo(taskCard.getBlobNo().longValue());
						}
						
						logger.info("INSERTING blob: " + blob.getId().getBlobNo() + " Line: " + blob.getId().getBlobLine());
						if(!em.getTransaction().isActive())
							em.getTransaction().begin();
						
						em.merge(blob);
						em.getTransaction().commit();
						//em.clear();
			}
			
			if(!existWoTaskCard) {
				taskCard.setTaskCardNumberingSystem(new BigDecimal(getLine(new BigDecimal(wo),"task_card_numbering_system","wo_task_card","wo" )));
				setWoNumber(taskCard.getId().getWo(), taskCard.getTaskCardNumberingSystem());
			}
			
			
			try
			{
				
				
				logger.info("SIZE ITEM " + taskCard.getWoTaskCardItems().size());
				try {
					if(taskCard.getWoTaskCardItems().size() == 1) {
						taskCard.getWoTaskCardItems().remove(0);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				
				logger.info("INSERTING WO TASK CARD: " + taskCard.getId().getTaskCard());
				if(!em.getTransaction().isActive())
					em.getTransaction().begin();
				
				em.merge(taskCard);
				em.getTransaction().commit();
				
				
				logger.info("INSERTING WO TASK CARD ITEM: " + itemComp.getId().getTaskCardItem());
				if(!em.getTransaction().isActive())
					em.getTransaction().begin();
				
				em.merge(itemComp);
				em.getTransaction().commit();
				em.clear();
				

				if(cus != null && !cus.isEmpty()) {
					WoTaskCardCustomer customer = new WoTaskCardCustomer();
					customer.setId(new WoTaskCardCustomerPK());
					customer.getId().setAc(taskCard.getId().getAc());
					customer.getId().setCustomerTaskCard(cus);
					customer.getId().setTaskCard(taskCard.getId().getTaskCard());
					customer.getId().setPn(taskCard.getId().getPn());
					customer.getId().setPnSn(taskCard.getId().getPnSn());
					customer.getId().setWo(taskCard.getId().getWo());
					
					
					logger.info("INSERTING WO TASK CARD CUSTOMER: " + customer.getId().getCustomerTaskCard());
					if(!em.getTransaction().isActive())
						em.getTransaction().begin();
					
					em.merge(customer);
					em.getTransaction().commit();
					
				}
				
				
				
				if(data.getEFFECTIVITY().getJOBCARD().getJOBI().getZONE() !=null 
						&& !data.getEFFECTIVITY().getJOBCARD().getJOBI().getZONE().isEmpty()) {
					insertTaskCardControl(taskCard, data.getEFFECTIVITY().getJOBCARD().getJOBI().getZONE());
				}
				
				try
				{
					List<WoTaskCardPn> pns = getPns(taskCard);
					
					List<TOOLSLIST> tools = data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getTOOLSLIST();
					
					List<MATERIAL> mats = data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getMATERIAL();
					
					if(mats != null && mats.size() > 0) {
						for(MATERIAL mat : mats)
						{
							String partNumber_Tool = mat.getMPNNUMBER().replaceAll("\"", "IN");
							partNumber_Tool = partNumber_Tool.replaceAll("'", "FT");
							if(!partNumber_Tool.contains(":"))
							{
								partNumber_Tool = partNumber_Tool.concat(":UPLOAD");
							}
							mat.setMPNNUMBER(partNumber_Tool);
						}
					}
					
					if(tools != null && tools.size() > 0) {
						for(TOOLSLIST tool : tools)
						{
							String partNumber_Tool = tool.getPRTNUMBER().replaceAll("\"", "IN");
							partNumber_Tool = partNumber_Tool.replaceAll("'", "FT");
							if(!partNumber_Tool.contains(":"))
							{
								partNumber_Tool = partNumber_Tool.concat(":UPLOAD");
							}
							tool.setPRTNUMBER(partNumber_Tool);
						}
					}
					
					
					
					if(mats != null && mats.size() > 0) {
						for(MATERIAL mat : mats)
						{
							
							
							WoTaskCardPn cardPn = pns.stream().filter(woPn -> mat.getMPNNUMBER().equals(woPn.getId().getPn())).findFirst().orElse(null);
							if(cardPn == null || cardPn.getId() == null || cardPn.getId().getPn() == null) {
								continue;
							}
							
							if(cardPn != null)
							{
								cardPn.setQty(new BigDecimal(mat.getQTY().trim()));
								mats.remove(mat);
							}
							
							
						}
						
						if(mats.size() > 0)
						{
							for(MATERIAL mat : mats)
							{
								if(getPN(mat.getMPNNUMBER()) == null) {
									error = error.concat("TaskCard: " +taskCardId + " WO: " +woo +" ,PN " + mat.getMPNNUMBER() +  " does not exist in Pn Master" +
											 System.lineSeparator() +  System.lineSeparator());
									continue;
								}
								
								WoTaskCardPnPK pnKey = new WoTaskCardPnPK();
								
								pnKey.setAc(taskCard.getId().getAc());
								pnKey.setPn(mat.getMPNNUMBER());
								pnKey.setReserve("YES");
								pnKey.setTaskCard(taskCard.getId().getTaskCard());
								pnKey.setTaskCardPn(taskCard.getId().getPn());
								pnKey.setTaskCardPnSn(taskCard.getId().getPnSn());
								pnKey.setWo(taskCard.getId().getWo());
								
								
								
								
								
								WoTaskCardPn woPn = new WoTaskCardPn();
								
								//EMRO fields to create basic object
								woPn.setTagType("NONE");
								woPn.setSpare("SPARE");
								woPn.setTaskCardItem(new BigDecimal(1));
								woPn.setTaskCardXRef(taskCard.getId().getTaskCard());
								
								
								woPn.setId(pnKey);
								
								woPn.setQty(new BigDecimal((mat.getQTY().trim())));
								
								woPn.setTaskCardItem(new BigDecimal(1));
								woPn.setCreatedBy("TRAXIFACE");
								woPn.setCreatedDate(new Date());
								woPn.setModifiedBy("TRAXIFACE");
								woPn.setModifiedDate(new Date());
								
								
								pns.add(woPn);
								try{
									if(mat.getRsnum() != null && !mat.getRsnum().isEmpty() && mat.getRspos()!= null && !mat.getRspos().isEmpty()) 
									{
										insertComponent(mat, taskCard, true);
									}
								}catch(Exception e) {
									e.printStackTrace();
									error = error.concat("TaskCard: " +taskCardId + " WO: " +woo + " ,Error Inserting PickList " + mat.getMPNNUMBER() +  System.lineSeparator() + e.toString() +
											 System.lineSeparator() +  System.lineSeparator());
								
								}
								
							}
						}
					}
					for(TOOLSLIST tool : tools)
					{
						
						
						WoTaskCardPn cardPn = pns.stream().filter(woPn -> tool.getPRTNUMBER().equals(woPn.getId().getPn())).findFirst().orElse(null);
						if(cardPn == null || cardPn.getId() == null || cardPn.getId().getPn() == null) {
							continue;
						}
											
						if(cardPn != null)
						{
							cardPn.setQty(new BigDecimal(tool.getQTY().trim()));
							tools.remove(tool);
						}
						
						
						
					}
					
					if(tools.size() > 0)
					{
						for(TOOLSLIST tool : tools)
						{
							if(getPN(tool.getPRTNUMBER()) == null) {
								error = error.concat("TaskCard: " +taskCardId + " WO: " +woo +" ,PN " + tool.getPRTNUMBER() +  " does not exist in Pn Master" +
										 System.lineSeparator() +  System.lineSeparator());
								continue;
							}
							
							WoTaskCardPnPK pnKey = new WoTaskCardPnPK();
							
							pnKey.setAc(taskCard.getId().getAc());
							pnKey.setPn(tool.getPRTNUMBER());
							pnKey.setReserve("YES");
							pnKey.setTaskCard(taskCard.getId().getTaskCard());
							pnKey.setTaskCardPn(taskCard.getId().getPn());
							pnKey.setTaskCardPnSn(taskCard.getId().getPnSn());
							pnKey.setWo(taskCard.getId().getWo());
							
							
							
							
							
							WoTaskCardPn woPn = new WoTaskCardPn();
							
							//EMRO fields to create basic object
							woPn.setTagType("NONE");
							woPn.setSpare("SPARE");
							woPn.setTaskCardItem(new BigDecimal(1));
							woPn.setTaskCardXRef(taskCard.getId().getTaskCard());
							woPn.setCreatedBy("TRAXIFACE");
							woPn.setCreatedDate(new Date());
							woPn.setModifiedBy("TRAXIFACE");
							woPn.setModifiedDate(new Date());
							
							woPn.setId(pnKey);
							
							woPn.setQty(new BigDecimal(tool.getQTY().trim()));
							
							woPn.setTaskCardItem(new BigDecimal(1));
							
							pns.add(woPn);
						}
					}
					
					for(WoTaskCardPn woPn : pns)
					{
						try
						{
							logger.info("INSERTING WO TASK CARD PN: " + woPn.getId().getPn());
							
							
							if(!em.getTransaction().isActive())
								em.getTransaction().begin();
							
							em.merge(woPn);
							em.getTransaction().commit();
							//em.clear();
						}
						catch(Exception e)
						{
							error = error.concat("TaskCard: " +taskCardId + " WO: " +woo +" ,Error Inserting WoTaskCardPn " + woPn.getId().getPn() +  System.lineSeparator() + e.toString() +
									 System.lineSeparator() +  System.lineSeparator());
							
							return false;
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					error = error.concat("TaskCard: " +taskCardId + " WO: " +woo +" ,Error Inserting WoTaskCardPns " +  System.lineSeparator() + e.toString() +
							 System.lineSeparator() +  System.lineSeparator());
					
					return false;
				}
				
				exhancedData(taskCard,itemComp.getOpsNo());
				
				existWoTaskCard = false;
				
				return true;
			}
			catch(Exception e)
			{
				error = error.concat("TaskCard: " +taskCardId + " WO: " +woo + " Error Inserting WoTaskCard " +  System.lineSeparator() + e.toString() +
						 System.lineSeparator() +  System.lineSeparator());
				e.printStackTrace();
				return false;
				
			}
				
						
		}
		catch(Exception e)
		{
			logger.severe(e.toString());
			error = error.concat("Error Inserting WoTaskCard " +  System.lineSeparator() + e.toString() +
					 System.lineSeparator() +  System.lineSeparator());
			e.printStackTrace();
			
		}finally {
			em.clear();
		}
		return false;
		
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
	
	private WoTaskCard assignValuesWO(String taskCard , WoTaskCard woCard , long wo, String ac, String pn, String sn  )
	{
		try
		{				
			woCard =  em.createQuery("select w from WoTaskCard w where w.id.wo = :woo and w.id.taskCard = :card "
					+ "and w.id.ac = :acc and w.id.pn = :pnn and w.id.pnSn = :snn ", WoTaskCard.class)
					.setParameter("woo", wo)
					.setParameter("card", taskCard)
					.setParameter("acc", ac)
					.setParameter("pnn", pn)
					.setParameter("snn", sn)
					.getSingleResult();
			existWoTaskCard = true;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			WoTaskCardPK key = new WoTaskCardPK();
			
			key.setTaskCard(taskCard);
			
			woCard.setId(key);
			
			WoTaskCardItemPK itemId = new WoTaskCardItemPK();
			
			itemId.setTaskCard(taskCard);
			
			WoTaskCardItem item = new WoTaskCardItem();
			
			item.setId(itemId);
			
			woCard.setWoTaskCardItems(new ArrayList<>());
			
			
			woCard.getId().setAc(ac);
			woCard.getId().setPn(pn);
			woCard.getId().setPnSn(sn);
			
			woCard.setCreatedBy("TRAXIFACE");
			woCard.setCreatedDate(new Date());
			
			item.setCreatedBy("TRAXIFACE");
			item.setCreatedDate(new Date());
			
			woCard.setStatus("OPEN");
			
			item.setStatus("OPEN");
			
			//EMRO fields to create basic object
			item.setMechanic("N");
			item.setInspector("N");
			item.setDuplicateInspection("N");
			item.setInspectorManHours(new BigDecimal(0));
			item.setInspectorManRequire(new BigDecimal(1));
			item.setManHours(new BigDecimal(0));
			item.setManRequire(new BigDecimal(1));
			item.setDualInspectorManHours(new BigDecimal(0));
			item.setDualInspectorManRequire(new BigDecimal(1));			
			item.setInformationalOnly("N");
			item.setMainSkill("YES");
			
			
			
			//DEFAULT VALUES for EMRO
			//EMRO fields to create basic object
			woCard.setNdt("N");
			woCard.setSellMaterialMethod("STANDARD");
			woCard.setSellMaterialAmount(new BigDecimal(0));
			woCard.setSellLaborMethod("STANDARD");
			woCard.setSellLaborAmount(new BigDecimal(0));
			woCard.setSellOtherMethod("STANDARD");
			woCard.setSellOtherAmount(new BigDecimal(0));
			
			woCard.setAuthorization("AUTHORIZED");
			woCard.setAuthorizationBy("TRAX_IFACE");
			woCard.setAuthorizationDate(new Date());
			woCard.setWeightOn(new BigDecimal(0));
			woCard.setWeightOff(new BigDecimal(0));
			woCard.setScheduleStartHour(new BigDecimal(0));
			woCard.setScheduleStartMinute(new BigDecimal(0));
			
			
			
			woCard.setParagraph(new BigDecimal(0));
			woCard.setEditorUsed("NONE");
			woCard.setScheduleEndHour(new BigDecimal(0));
			woCard.setScheduleEndMinute(new BigDecimal(0));
			woCard.setNoOfPrint(new BigDecimal(0));
			woCard.setInsuranceClaim("NO");
			woCard.setFaultConfirm("PENDING");
			woCard.setPnRequired("NO");
			
			woCard.setRectifiedByEngineering("N");
			woCard.setAircraftJacked("NO");
			woCard.setElectricalPowerReq("OPTIONAL");
			woCard.setHydraulicPowerReq("OPTIONAL");
			woCard.setNonRoutine("N");
			
			woCard.addWoTaskCardItem(item);
		}
		
		
		return woCard;
		
	}
	
	
	private WoTaskCard assignValuesCUS(String taskCard , WoTaskCard woCard , WoTaskCard parentTaskCard, long wo  )
	{
		try
		{				
			woCard =  em.createQuery("select w from WoTaskCard w where w.id.wo = :woo and w.id.taskCard = :card "
					+ "and w.id.ac = :acc and w.id.pn = :pnn and w.id.pnSn = :snn ", WoTaskCard.class)
					.setParameter("woo", wo)
					.setParameter("card", taskCard)
					.setParameter("acc", parentTaskCard.getId().getAc())
					.setParameter("pnn", parentTaskCard.getId().getPn())
					.setParameter("snn", parentTaskCard.getId().getPnSn())
					.getSingleResult();
			existWoTaskCardCUS = true;
		}
		catch(Exception e)
		{
		
			WoTaskCardPK key = new WoTaskCardPK();
			
			key.setTaskCard(taskCard);
			
			woCard.setId(key);
			
			WoTaskCardItemPK itemId = new WoTaskCardItemPK();
			
			itemId.setTaskCard(taskCard);
			
			WoTaskCardItem item = new WoTaskCardItem();
			
			item.setId(itemId);
			
			woCard.setWoTaskCardItems(new ArrayList<>());
			
			woCard.getId().setAc(parentTaskCard.getId().getAc());
			woCard.getId().setPn(parentTaskCard.getId().getPn());
			woCard.getId().setPnSn(parentTaskCard.getId().getPnSn());
			
			woCard.setCreatedBy("TRAXIFACE");
			woCard.setCreatedDate(new Date());
			
			item.setCreatedBy("TRAXIFACE");
			item.setCreatedDate(new Date());
			
			item.setStatus("OPEN");
			woCard.setStatus("OPEN");
			//EMRO fields to create basic object
			item.setMechanic("N");
			item.setInspector("N");
			item.setDuplicateInspection("N");
			item.setInspectorManHours(new BigDecimal(0));
			item.setInspectorManRequire(new BigDecimal(1));
			item.setManHours(new BigDecimal(0));
			item.setManRequire(new BigDecimal(1));
			item.setDualInspectorManHours(new BigDecimal(0));
			item.setDualInspectorManRequire(new BigDecimal(1));			
			item.setInformationalOnly("N");
			item.setMainSkill("YES");
			
			//DEFAULT VALUES for EMRO
			//EMRO fields to create basic object
			woCard.setNdt("N");
			woCard.setSellMaterialMethod("STANDARD");
			woCard.setSellMaterialAmount(new BigDecimal(0));
			woCard.setSellLaborMethod("STANDARD");
			woCard.setSellLaborAmount(new BigDecimal(0));
			woCard.setSellOtherMethod("STANDARD");
			woCard.setSellOtherAmount(new BigDecimal(0));
			
			woCard.setAuthorization("AUTHORIZED");
			woCard.setAuthorizationBy("TRAX_IFACE");
			woCard.setAuthorizationDate(new Date());
			woCard.setWeightOn(new BigDecimal(0));
			woCard.setWeightOff(new BigDecimal(0));
			woCard.setScheduleStartHour(new BigDecimal(0));
			woCard.setScheduleStartMinute(new BigDecimal(0));
			
			
			
			woCard.setParagraph(new BigDecimal(0));
			woCard.setEditorUsed("NONE");
			woCard.setScheduleEndHour(new BigDecimal(0));
			woCard.setScheduleEndMinute(new BigDecimal(0));
			woCard.setNoOfPrint(new BigDecimal(0));
			woCard.setInsuranceClaim("NO");
			woCard.setFaultConfirm("PENDING");
			woCard.setPnRequired("NO");
			
			woCard.setRectifiedByEngineering("N");
			woCard.setAircraftJacked("NO");
			woCard.setElectricalPowerReq("OPTIONAL");
			woCard.setHydraulicPowerReq("OPTIONAL");
			woCard.setNonRoutine("S");
			
			woCard.addWoTaskCardItem(item);
		}
		
		
		return woCard;
		
	}
	
	
	
	private WoTaskCard assignValues(String taskCard , WoTaskCard woCard ,WoTaskCardItem item )
	{
		TaskCard card = null;
		
		try
		{
			card = (TaskCard) this.em.createQuery("select t from TaskCard t where t.taskCard = :card")
					.setParameter("card", taskCard)
					.getSingleResult();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			logger.info("NO ENG TASK CARD FOUND");
			return woCard;
		}
		
		woCard.setAuthorization(card.getAuthorization());
		
		woCard.setSellMaterialMethod(card.getSellMaterialMethod());
		
		woCard.setSellMaterialAmount(card.getSellLaborAmount());
		
		woCard.setSellLaborMethod(card.getSellLaborMethod());
		
		woCard.setSellLaborAmount(card.getSellLaborAmount());
		
		woCard.setSellOtherMethod(card.getSellOtherMethod());
		
		woCard.setSellOtherAmount(card.getSellOtherAmount());
		
		woCard.setStructureRepair(card.getStructureRepair());
		
		woCard.setStructureRepair(card.getStructureRepairClass());
		
		woCard.setBasicEmptyWeight(card.getBasicEmptyWeight());
		
		woCard.setTotalMoment(card.getTotalMoment());
		
		woCard.setWeightOff(card.getWeightOff());
		
		woCard.setAircraftJacked(card.getAircraftJacked());
		
		woCard.setElectricalPowerReq(card.getElectricalPowerReq());
		
		woCard.setHydraulicPowerReq(card.getHydraulicPowerReq());
		
		woCard.setWeightBalance(card.getWeightBalance());
		
		woCard.setEditorUsed(card.getEditorUsed());
		
		woCard.setWeightOn(card.getWeightOn());
		
		woCard.setPaperRequired(card.getPaperRequired());
		
		//woCard.setModNo(card.getModNo());
		
		woCard.setRevision(card.getRevison());
		
		woCard.setTaskCardCategory(card.getTaskCardCategory());
		
		woCard.setTaskCardDescription(card.getTaskCardDescription());
		
		if(card.getArea() != null && !card.getArea().isEmpty()) {
			woCard.setArea(card.getArea());
		}
		woCard.setPhase(card.getPhase());
		
		for(TaskCardItem i : card.getTaskCardItems()) {
			if(i.getId().getTaskCardItem() == 1) {
				woCard.getWoTaskCardItems().get(0).setMechanic(i.getMechanic());
				
				woCard.getWoTaskCardItems().get(0).setSkill(i.getSkill());
				
				woCard.getWoTaskCardItems().get(0).setInspectorSkill(i.getInspectorSkill());
				
				woCard.getWoTaskCardItems().get(0).setDualInspectorSkill((i.getDualInspectorSkill()));
				
				woCard.getWoTaskCardItems().get(0).setInspector(i.getInspector());
				
				woCard.getWoTaskCardItems().get(0).setInspectorManHours(i.getInspectorManHours());
				
				woCard.getWoTaskCardItems().get(0).setInspectorManRequire(i.getInspectorManRequire());
				
				woCard.getWoTaskCardItems().get(0).setManRequire(i.getManRequire());
				
				if(i.getManHours() != null) {
					woCard.getWoTaskCardItems().get(0).setManHours(i.getManHours());
				}
				woCard.getWoTaskCardItems().get(0).setDuplicateInspection(i.getDuplicateInspection());
				
				woCard.getWoTaskCardItems().get(0).setDualInspectorManHours(i.getDualInspectorManHours());
				
				woCard.getWoTaskCardItems().get(0).setDualInspectorManRequire(i.getDualInspectorManRequire());
				
				woCard.getWoTaskCardItems().get(0).setInformationalOnly(i.getInformationalOnly());
				
				woCard.getWoTaskCardItems().get(0).setExternalCustRef(i.getExternalCustRef());
				woCard.getWoTaskCardItems().get(0).setOpsNo(i.getOpsNo());
				
				woCard.getWoTaskCardItems().get(0).setTaskCardText(i.getTaskCardText());
				
			}	
		}
		
		
		
		//woCard.addWoTaskCardItem(item);
		
		
		
		
		return woCard;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<WoTaskCardPn> getPns(WoTaskCard card)
	{
		List<WoTaskCardPn> woPns = new ArrayList<>();
		
		List<TaskCardPn> pns;
		
		try
		{
			pns = this.em.createQuery("select w from TaskCardPn w where w.taskCard = :card and w.taskCard")
					.setParameter("card", card.getId().getTaskCard())
					.getResultList();
		}
		catch(Exception e)
		{
			return woPns;
		}
		
		for(TaskCardPn cardPn : pns)
		{
			WoTaskCardPn woPn = new WoTaskCardPn();
			
			WoTaskCardPnPK key = new WoTaskCardPnPK();
			
			key.setTaskCard(card.getId().getTaskCard());
			
			key.setAc(card.getId().getAc());
			
			key.setTaskCardPn(card.getId().getPn());
			
			key.setTaskCardPnSn(card.getId().getPnSn());
			
			key.setWo(card.getId().getWo());
			
			key.setPn(cardPn.getId().getPn());
			
			key.setReserve(cardPn.getReserve());
			
			woPn.setId(key);
			
			woPn.setTaskCardItem(new BigDecimal(cardPn.getId().getTaskCardItem()));
			
			woPn.setQty(cardPn.getQty());
			
			woPn.setSpare(cardPn.getSpare());
			
			woPn.setTagType(cardPn.getTagType());
			
			woPn.setFleetInvolvementBlock(cardPn.getFleetInvolvementBlock());
			
			woPn.setTagQty(cardPn.getTagQty());
			
			woPn.setRepairReturnShopAction(cardPn.getRepairReturnShopAction());
			
			woPn.setShop1(cardPn.getShop1());
			
			woPn.setShop2(cardPn.getShop2());
			
			woPn.setShop3(cardPn.getShop3());
			
			woPn.setShop4(cardPn.getShop4());
			
			woPn.setShop5(cardPn.getShop5());
			
			woPn.setShop6(cardPn.getShop6());
			
			woPn.setShop7(cardPn.getShop7());
			
			woPn.setShop8(cardPn.getShop8());
			
			woPn.setShop9(cardPn.getShop9());
			
			woPn.setShop10(cardPn.getShop10());
			
			woPn.setFinalRoutine(cardPn.getFinalRoutine());
			
			woPn.setOffAc(cardPn.getOffAc());
			
			woPn.setInShop(cardPn.getInShop());
			
			woPn.setTagMessage(cardPn.getTagMessage());
			
			woPn.setOriginator(cardPn.getOriginator());
			
			woPn.setOriginatorPhone(cardPn.getOriginatorPhone());
			
			woPn.setAuthority(cardPn.getAuthority());
			
			woPn.setAuthorityPhone(cardPn.getAuthorityPhone());
			
			woPn.setSurplusQty(cardPn.getSurplusQty());
			
			woPn.setUnitOfIssue(cardPn.getUnitOfIssue());
			
			woPn.setRepairReturnShopAction(cardPn.getReturnToDock());
			
			woPn.setTaskCardXRef(cardPn.getTaskCardXRef());
			
			woPn.setTaskCardXRefDescription(cardPn.getTaskCardXRefDescription());
			
			woPn.setRealPartNumber(cardPn.getRealPartNumber());
			
			woPn.setPnByReference(cardPn.getPnByReference());
			
			woPn.setCreatedBy("TRAXIFACE");
			woPn.setCreatedDate(new Date());
			woPn.setModifiedBy("TRAXIFACE");
			woPn.setModifiedDate(new Date());
			
			woPns.add(woPn);	
		}
		
		
		
		return woPns;
	}
	
	
	public String filterADDATTR(List<ADDATTR> attributes, String filter)
	{
		ADDATTR temp = attributes.stream().filter(att -> filter.equals(att.getATTRNAME())).findFirst().orElse(null);
		
		
		if(temp == null)
			return null;
		else
			return temp.getATTRVALUE();
	}
	
	// insert PICKLIST
	private void insertComponent(MATERIAL mat, WoTaskCard wotaskcard, Boolean prod) {
				
				//setting up variables
				PicklistHeader picklistheader = null;
				//Boolean exist = false;
				PicklistDistribution picklistdistdistribu = null;
				PicklistDistribution picklistdistrequire = null;
				
				//check if object has min values
				if(prod && mat.getRsnum() != null && !mat.getRsnum().isEmpty() && mat.getRspos()!= null && !mat.getRspos().isEmpty()) 
				{
					
					String pickNumber = findPicklistNumber(mat.getRsnum(),mat.getRspos());
					
					try 
					{
						picklistheader = em.createQuery("SELECT p FROM PicklistHeader p where p.id.picklist =:pick", PicklistHeader.class)
								.setParameter("pick", Long.valueOf(pickNumber))
								.getSingleResult();
					}
					catch(Exception e)
					{
						picklistheader = new PicklistHeader();
						picklistheader.setCreatedBy("TRAX_IFACE");
						picklistheader.setCreatedDate(new Date());
					
						
						//EMRO fields to create basic object
						picklistheader.setInventoryType("MAINTENANCE");
						picklistheader.setBypassRouting("Y");
						picklistheader.setBuildKit("NO");
						picklistheader.setNoOfPrint(new BigDecimal(1));
						picklistheader.setRequireHour(new BigDecimal(0));
						picklistheader.setRequireMinute(new BigDecimal(0));
						picklistheader.setBinTransfer("N");
						picklistheader.setStatus("OPEN");
						picklistheader.setLocation("SIN");
						picklistheader.setRequireOn(new Date());
						picklistheader.setRequireHour(new BigDecimal(0));
						picklistheader.setRequireMinute(new BigDecimal(0));
						
						try {
							picklistheader.setPicklist(getTransactionNo("PICKLIST").longValue());
						} catch (Exception e1) {
							logger.severe(e1.toString());
						}
					}
					picklistheader.setModifiedDate(new Date());
					picklistheader.setModifiedBy("TRAX_IFACE");
					
					
					picklistheader.setWo(new BigDecimal(wotaskcard.getId().getWo()));
					picklistheader.setTaskCard(wotaskcard.getId().getTaskCard());
					picklistheader.setTaskCardPn(wotaskcard.getId().getPn());
					picklistheader.setTaskCardSn(wotaskcard.getId().getPnSn());
					
					
					String line = mat.getRspos();
					line = line.replaceFirst("^0+(?!$)", "");
					Long l = Long.parseLong(line);
					logger.info("LINE "+ line);
					
					picklistdistdistribu = fillPicklistDistribution(picklistdistdistribu, mat, "DISTRIBU","2", picklistheader.getPicklist(),l.longValue(),false );
					
					picklistdistrequire = fillPicklistDistribution(picklistdistrequire, mat,"REQUIRE","0" ,picklistheader.getPicklist(),l.longValue(),false);
					
					picklistdistdistribu.setTaskCard(wotaskcard.getId().getTaskCard());
					picklistdistrequire.setTaskCard(wotaskcard.getId().getTaskCard());
					
					if(mat.getQTY() !=null && !mat.getQTY().isEmpty()) {
						try 
						{
							picklistdistdistribu.setQty(new BigDecimal(mat.getQTY().trim()));
							picklistdistrequire.setQty(new BigDecimal(mat.getQTY().trim()));
							picklistdistdistribu.setQtyPicked(new BigDecimal(mat.getQTY().trim()));
							picklistdistrequire.setQtyPicked(new BigDecimal(1));
						}
						catch(NumberFormatException e)
						{
							logger.severe("Can not insert/update Material Pi: "+ mat.getRsnum() +" Material: " +mat.getMPNNUMBER()+" ERROR: Quantity");
							error = error.concat("TaskCard: " +wotaskcard.getId().getTaskCard() + " WO: " +wotaskcard.getId().getTaskCard() +" ,Can not insert/update Material ReservationNumber: "+ mat.getRsnum() +" Material: " +mat.getMPNNUMBER()+ " ERROR: Quantity");
							return ;
						}
						
					}
					
					
					
					logger.info("INSERTING picklist header: " + picklistheader.getPicklist() );
					insertData(picklistheader,"picklist header",String.valueOf(picklistheader.getPicklist()) );
					
					logger.info("INSERTING picklist dist: " + picklistdistdistribu.getId().getTransaction()  );
					insertData(picklistdistdistribu,"picklist dist distribu",String.valueOf(picklistheader.getPicklist()));
					
					logger.info("INSERTING picklist require: " + picklistdistrequire.getId().getTransaction() );
					insertData(picklistdistrequire,"picklist dist require",String.valueOf(picklistheader.getPicklist()) );
					
					
				}else 
				{
					
					
					try 
					{
						picklistheader = em.createQuery("SELECT p FROM PicklistHeader p where p.wo =:woo and p.taskCard =:task", PicklistHeader.class)
								.setParameter("woo", wotaskcard.getId().getWo())
								.setParameter("task", wotaskcard.getId().getTaskCard())
								.getSingleResult();
					}
					catch(Exception e)
					{
						picklistheader = new PicklistHeader();
						picklistheader.setCreatedBy("TRAX_IFACE");
						picklistheader.setCreatedDate(new Date());
					
						
						//EMRO fields to create basic object
						picklistheader.setInventoryType("MAINTENANCE");
						picklistheader.setBypassRouting("Y");
						picklistheader.setBuildKit("NO");
						picklistheader.setNoOfPrint(new BigDecimal(1));
						picklistheader.setRequireHour(new BigDecimal(0));
						picklistheader.setRequireMinute(new BigDecimal(0));
						picklistheader.setBinTransfer("N");
						picklistheader.setStatus("OPEN");
						
						try {
							picklistheader.setPicklist(getTransactionNo("PICKLIST").longValue());
						} catch (Exception e1) {
							logger.severe(e1.toString());
						}
					}
					picklistheader.setModifiedDate(new Date());
					picklistheader.setModifiedBy("TRAX_IFACE");
					
					
					picklistheader.setWo(new BigDecimal(wotaskcard.getId().getWo()));
					picklistheader.setTaskCard(wotaskcard.getId().getTaskCard());
					picklistheader.setTaskCardPn(wotaskcard.getId().getPn());
					picklistheader.setTaskCardSn(wotaskcard.getId().getPnSn());
					
					
					String line = mat.getRspos();
					line = line.replaceFirst("^0+(?!$)", "");
					Long l = Long.parseLong(line);
					
					logger.info("LINE "+ line);
					picklistdistdistribu = fillPicklistDistribution(picklistdistdistribu, mat, "DISTRIBU","2", picklistheader.getPicklist(),l.longValue(),true );
					picklistdistrequire = fillPicklistDistribution(picklistdistrequire, mat,"REQUIRE","0" ,picklistheader.getPicklist(),l.longValue(),true);
					
					
					
					if(mat.getQTY() !=null && !mat.getQTY().isEmpty()) {
						try 
						{
							picklistdistdistribu.setQty(new BigDecimal(mat.getQTY().trim()));
							picklistdistrequire.setQty(new BigDecimal(mat.getQTY().trim()));
							picklistdistdistribu.setQtyPicked(new BigDecimal(mat.getQTY().trim()));
							picklistdistrequire.setQtyPicked(new BigDecimal(mat.getQTY().trim()));
						}
						catch(NumberFormatException e)
						{
							logger.severe("Can not insert/update Material ReservationNumber: "+ mat.getRsnum() +" Material: " +mat.getMPNNUMBER()+" ERROR: Quantity");
							error = error.concat("TaskCard: " +wotaskcard.getId().getTaskCard() + " WO: " +wotaskcard.getId().getTaskCard() +" Can not insert/update Material ReservationNumber: "+ mat.getRsnum() +" Material: " +mat.getMPNNUMBER()+ " ERROR: Quantity");
							return ;
						}
						
					}
					
					
					
					logger.info("INSERTING picklist header: " + picklistheader.getPicklist() );
					insertData(picklistheader,"picklist header",String.valueOf(picklistheader.getPicklist()) );
					
					logger.info("INSERTING picklist dist: " + picklistdistdistribu.getId().getTransaction()  );
					insertData(picklistdistdistribu,"picklist dist distribu",String.valueOf(picklistheader.getPicklist()));
					
					logger.info("INSERTING picklist require: " + picklistdistrequire.getId().getTransaction() );
					insertData(picklistdistrequire,"picklist dist require",String.valueOf(picklistheader.getPicklist()) );
					
				}
			}
			


			private String findPicklistNumber(String reservationNumber, String resrvationItem) {
				try
				{	
					@SuppressWarnings("unchecked")
					ArrayList<PicklistDistribution>picklistdist = (ArrayList<PicklistDistribution>) em.createQuery("SELECT p FROM PicklistDistribution p where p.externalCustRes =:pi AND p.id.transaction =:tra AND p.id.distributionLine =:dl")
							.setParameter("pi", reservationNumber)
							.setParameter("tra", "DISTRIBU")
							.setParameter("dl",new Long(2) )
							.getResultList();
					return String.valueOf(picklistdist.get(0).getId().getPicklist());
				}
				catch (Exception e)
				{	
					//e.printStackTrace();
					logger.info("PICKLIST NOT FOUND");
				}
				return null;
			}
			

			
			
			private PicklistDistribution fillPicklistDistribution(PicklistDistribution picklistdist , MATERIAL mat , String transaction, String DistributionLine, long l, long line, Boolean cus) {
				
				try 
				{
					picklistdist = em.createQuery("SELECT p FROM PicklistDistribution p where p.id.picklist =:pi AND p.id.picklistLine =:li AND p.id.transaction =:tra AND p.id.distributionLine =:dl", PicklistDistribution.class)
							.setParameter("pi", l)
							.setParameter("li", line)
							.setParameter("tra", transaction)
							.setParameter("dl", DistributionLine)
							.getSingleResult();
					//exist = true;
				}
				catch(Exception e)
				{
					//EMRO fields to create basic object
					PicklistDistributionPK pk = new PicklistDistributionPK();
					picklistdist = new PicklistDistribution();
					picklistdist.setId(pk);
					picklistdist.setCreatedBy("TRAX_IFACE");
					picklistdist.setCreatedDate(new Date());
					picklistdist.setQtyPicked(new BigDecimal(1));
					
					
				}
				
				
				if(picklistdist.getStatus() == null)
					picklistdist.setStatus("OPEN");
				picklistdist.setModifiedDate(new Date());
				picklistdist.setModifiedBy("TRAX_IFACE");
				
				if(!cus) {
					picklistdist.setExternalCustRes(mat.getRsnum());
					picklistdist.setExternalCustResItem(mat.getRspos());
				}
				
				picklistdist.getId().setPicklist(l );
				picklistdist.getId().setPicklistLine(line);
				picklistdist.getId().setDistributionLine(new Long(DistributionLine));
				picklistdist.getId().setTransaction(transaction);
				picklistdist.setPn(mat.getMPNNUMBER());
				
				return picklistdist;
				
			}
			
			private <T> void insertData( T data, String type, String name) 
			{
				try 
				{	
					if(!em.getTransaction().isActive())
						em.getTransaction().begin();
						em.merge(data);
					em.getTransaction().commit();
				}catch (Exception e)
				{
					String exceuted = "insertData has encountered an Exception with inserting type: " + type +" Name: "+name +" Exception: "+e.toString();
					logger.severe(exceuted);
					e.printStackTrace();
				}
			}
			
			private String getPN(String PN) {
				try
				{
					PnMaster pnMaster = em.createQuery("Select p From PnMaster p where p.id.pn = :partn", PnMaster.class)
					.setParameter("partn", PN)
					.getSingleResult();
					
					return pnMaster.getPn();
				}
				catch (Exception e)
				{
					
				}
				return null;
			}
			
			
		
			
			
			
			
			private boolean processDataCUS( MODEL data, String pdf, byte[] pdfBytes, WoTaskCard parentTaskCard) {
				
				try
				{
					
					List<ADDATTR> attributes = data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getADDATTR();
					
					WoTaskCard taskCard = new WoTaskCard();
					
					String taskNBR = filterADDATTR(attributes, "TASK-NBR");
					
					String taskId = "";
					String groupNo = "";
					
					String refer = "";
					
					String wonbr = data.getEFFECTIVITY().getJOBCARD().getWONBR();
					if(wonbr != null)
					{
						if(wonbr.length() > 4)
						{
							refer = (wonbr.substring(wonbr.length()- 4));
						}
					}
					
					
					if(data.getEFFECTIVITY().getJOBCARD().getJCNBR() != null && data.getEFFECTIVITY().getJOBCARD().getJCNBR().length() > 8)
					{
						groupNo = data.getEFFECTIVITY().getJOBCARD().getJCNBR().substring(0, 8);
					}
					else if(data.getEFFECTIVITY().getJOBCARD().getJCNBR() != null)
					{
						groupNo = data.getEFFECTIVITY().getJOBCARD().getJCNBR();
					}
					
					
					if(taskNBR == null || taskNBR.length() == 0 || groupNo == null || groupNo.length() == 0 || 
							refer == null || refer.length() == 0)
					{
						error = error.concat("Error Inserting WoTaskCard " +  System.lineSeparator() + "Id was not provided" +
								 System.lineSeparator() +  System.lineSeparator());
						
						return false;
					}
					
					
					if(data.getEFFECTIVITY().getJOBCARD().getWPTITLE() == null || data.getEFFECTIVITY().getJOBCARD().getWPTITLE().length() == 0)
					{
						error = error.concat("Error Inserting WoTaskCard " +  System.lineSeparator() + "Wo was not provided" +
								 System.lineSeparator() +  System.lineSeparator());
						
						return false;
					}
					
					
					taskId = getCusId(Cuslong ,parentTaskCard );
					
					cus = taskId;
					taskCard = assignValuesCUS(taskId,taskCard, parentTaskCard,Long.parseLong(data.getEFFECTIVITY().getJOBCARD().getWPTITLE()));
					
					taskCard = assignValues(parentTaskCard.getId().getTaskCard(),taskCard,taskCard.getWoTaskCardItems().get(0));
					
					if(taskCard.getId() == null)
						taskCard.setId(new WoTaskCardPK());
					
					if(taskCard.getWoTaskCardItems() == null)
						taskCard.setWoTaskCardItems(new ArrayList<>());
					
					if(taskCard.getWoTaskCardItems().size() == 0)
						taskCard.addWoTaskCardItem(new WoTaskCardItem());
					
					if(taskCard.getWoTaskCardItems().get(0).getId() == null)
						taskCard.getWoTaskCardItems().get(0).setId(new WoTaskCardItemPK());
					
					
					
					
					String taskCardId = taskId;
					
					//= taskNBR + "_" + groupNo + "_" + refer;
					
					if(taskCardId != null && taskCardId.length() != 0)
					{
						taskCard.getId().setTaskCard(taskCardId);
						taskCard.getWoTaskCardItems().get(0).getId().setTaskCard(taskCard.getId().getTaskCard());
					}
					else
					{
						error = error.concat("Error Inserting WoTaskCard " +  System.lineSeparator() + "TaskCard was not provided" +
								 System.lineSeparator() +  System.lineSeparator());
						
						return false;
					}
					
				
					
					taskCard.getId().setAc(data.getEFFECTIVITY().getREGNBR());
					taskCard.getWoTaskCardItems().get(0).getId().setAc(taskCard.getId().getAc());
					
					String pn = filterADDATTR(attributes, "COMP");
					
					if(pn == null || pn.length() == 0)
					{
						taskCard.getId().setPn("                                   ");
					}
					else
					{
						taskCard.getId().setPn("                                   ");
						
						
					}
					
					taskCard.getWoTaskCardItems().get(0).getId().setTaskCardPn(taskCard.getId().getPn());
					
					String sn = filterADDATTR(attributes, "SN");
					
					if(sn == null || sn.length() == 0)
					{
						taskCard.getId().setPnSn("                                   ");
					}
					else
					{
						taskCard.getId().setPnSn("                                   ");
					}
					
					taskCard.getWoTaskCardItems().get(0).getId().setTaskCardPnSn(taskCard.getId().getPnSn());
					
					String wo = data.getEFFECTIVITY().getJOBCARD().getWPTITLE();
					if(wo == null || wo.length() == 0)
					{
						error = error.concat("TaskCard: " +taskCardId + " WO: " +wo + " ,Error Inserting WoTaskCard " +  System.lineSeparator() + "Wo was not provided" +
								 System.lineSeparator() +  System.lineSeparator());
						
						return false;
					}
					else
					{
						taskCard.getId().setWo(Long.parseLong(wo));
					}
					
					taskCard.getWoTaskCardItems().get(0).getId().setWo(taskCard.getId().getWo());
					
					
					
					
					
					
					if(groupNo != null)
					{
						taskCard.setEo(groupNo);
					}
					
					
					if(data.getEFFECTIVITY().getJOBCARD().getJCTITLE() != null)
					{
						taskCard.setTaskCardDescription(data.getEFFECTIVITY().getJOBCARD().getJCTITLE());
					}

					
					String equiNumber = filterADDATTR(attributes, "EQUI-NUMBER");
					if(equiNumber != null)
					{
						taskCard.setEqptNo(equiNumber);
					}
					
					String revision = filterADDATTR(attributes, "LATEST-REVISION");
					if(revision != null)
						taskCard.setRevision(revision);

					
					
					
					
					if(data.getEFFECTIVITY().getJOBCARD().getENGINEPOS() != null) {
						taskCard.setFunctionalLocation(data.getEFFECTIVITY().getJOBCARD().getENGINEPOS());
					}
					
					if(pdf != null && pdfBytes != null) {
						
						boolean existBlob = false;
						BlobTable blob = null;
					    	
							try 
							{
								blob = em.createQuery("SELECT b FROM BlobTable b where b.id.blobNo = :bl and b.id.blobLine = :des", BlobTable.class)
										.setParameter("bl", taskCard.getBlobNo().longValue())
										.setParameter("li",new Long(1))
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
								blob.setDocType("CUSTCARD");
								blob.getId().setBlobLine(1);
							}
							
							
							
							blob.setModifiedBy("TRAX_IFACE");
							blob.setModifiedDate(new Date());
							blob.setBlobItem(pdfBytes);
							blob.setBlobDescription(pdf);
							blob.setCustomDescription(pdf);
							
							
							
							if(!existBlob && taskCard.getBlobNo() == null) {
								try {
									blob.getId().setBlobNo(((getTransactionNo("BLOB").longValue())));
									taskCard.setBlobNo(new BigDecimal(blob.getId().getBlobNo()));
								} catch (Exception e1) {
									
								}
							}else if(taskCard.getBlobNo() != null){
								blob.getId().setBlobNo(taskCard.getBlobNo().longValue());
							}
							
							logger.info("INSERTING blob: " + blob.getId().getBlobNo() + " Line: " + blob.getId().getBlobLine());
							if(!em.getTransaction().isActive())
								em.getTransaction().begin();
							
							em.merge(blob);
							em.getTransaction().commit();
							//em.clear();

					}
					
					
					if(data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getCHAPNBR() != null)
					{
						try
						{
							taskCard.setChapter(new BigDecimal(data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getCHAPNBR()));
						}
						catch(Exception e)
						{
							
						}
					}

					
					if(data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getSECTNBR() != null)
					{
						try
						{
							taskCard.setSection(new BigDecimal(data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getSECTNBR()));
						}
						catch(Exception e)
						{
							
						}
					}
						
					
					
					
					String category = filterADDATTR(attributes, "ORDER-TYPE");
					
					if(category != null)
					{
						taskCard.setTaskCardCategory(category);
					}
					else
					{
						error = error.concat("TaskCard: " +taskCardId + " WO: " +wo +" ,Error Inserting WoTaskCard " +  System.lineSeparator() + "Category was not provided" +
								 System.lineSeparator() +  System.lineSeparator());
						
						return false;
					}
					
					String type =data.getEFFECTIVITY().getJOBCARD().getTYPE();
					String priority = filterADDATTR(attributes, "PRIORITY");

					if(type != null)
						taskCard.setType(type);
					if(priority != null)
						taskCard.setPlanningPriority(priority);
					
					if(type != null && priority != null) {
						switch(type) {
							case "MJC":
								taskCard.setTaskCardCategory(type);
								break;
							case "SJC":
								taskCard.setTaskCardCategory(type);
								break;
							case "CCS":
								if(priority.equalsIgnoreCase("O/C"))
									taskCard.setTaskCardCategory(type);
								break;
							case "MCS":
								switch(priority) {
									case "A":
										taskCard.setTaskCardCategory("ADMAF");
										break;
									case "B":
										taskCard.setTaskCardCategory("ALMAF");
										break;
									case "C":
										taskCard.setTaskCardCategory("MAF");
										break;	
								}
							    break;
							case "SI":
								switch(priority) {
									case "1":
										taskCard.setTaskCardCategory("ADSI");
										break;
									case "2":
										taskCard.setTaskCardCategory("ALSI");
										break;
									case "3":
										taskCard.setTaskCardCategory("SI");
										break;	
								}
							    break;    
						}
					}

					
					
					
					String ref = data.getEFFECTIVITY().getJOBCARD().getWONBR();
					if(wonbr != null)
					{
						if(wonbr.length() > 4)
						{
							ref = (wonbr.substring(0, wonbr.length()- 4));
							taskCard.setReferenceTaskCard(ref);
						}
					}
					
					
					
					//item.setId(itemKey);
					
					

					
					if(data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getPLITEXT() != null)
					{
						String text = data.getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getPLITEXT();
						
						text=text.replaceAll("&re;", System.lineSeparator());
						
						taskCard.getWoTaskCardItems().get(0).setTaskCardText(text);
					}

					
					if(refer != null)
					{
						taskCard.getWoTaskCardItems().get(0).setExternalCustRef(refer);
						taskCard.getWoTaskCardItems().get(0).setOpsNo(refer);
					}
					
					
					
					if(taskCard.getWoTaskCardItems().get(0).getId().getTaskCardItem() == 0)
						taskCard.getWoTaskCardItems().get(0).getId().setTaskCardItem(1);
					
					
					WoTaskCardItem itemComp = taskCard.getWoTaskCardItems().get(0);
					
					
					taskCard.setModifiedBy("TRAXIFACE");
					taskCard.setModifiedDate(new Date());
					
					
					//taskCard.setScheduleTaskCard(taskcardSchl);
					
					itemComp.setModifiedBy("TRAXIFACE");
					itemComp.setModifiedDate(new Date());
					
					
					
					taskCard.getWoTaskCardItems().remove(0);
					
					
					
					if(!existWoTaskCardCUS) {
						
						cusList.add(taskCard);
						
						//taskCard.setTaskCardNumberingSystem(new BigDecimal(getLine(new BigDecimal(wo),"task_card_numbering_system","wo_task_card","wo" )));
					}
					
					
					
					
					try
					{
						logger.info("INSERTING WO TASK CARD: " + taskCard.getId().getTaskCard());
						if(!em.getTransaction().isActive())
							em.getTransaction().begin();
						
						em.merge(taskCard);
						em.getTransaction().commit();
						//em.clear();
						
						logger.info("INSERTING WO TASK CARD ITEM: " + itemComp.getId().getTaskCardItem());
						if(!em.getTransaction().isActive())
							em.getTransaction().begin();
						
						em.merge(itemComp);
						em.getTransaction().commit();
						//em.clear();
						
						
						
						
						//em.clear();
						
						
						existWoTaskCardCUS = false;
						
						return true;
					}
					catch(Exception e)
					{
						error = error.concat("Error Inserting WoTaskCard " +  System.lineSeparator() + e.toString() +
								 System.lineSeparator() +  System.lineSeparator());
						e.printStackTrace();
						
						return false;
					}
						
								
				}
				catch(Exception e)
				{
					logger.severe(e.toString());
					error = error.concat(" Error Inserting WoTaskCard " +  System.lineSeparator() + e.toString() +
							 System.lineSeparator() +  System.lineSeparator());
					e.printStackTrace();
					
				}
				return false;
				
			}

			private String getCusId( long cus , WoTaskCard parent) throws SQLException {
				logger.info("Finding  Customer ID");

				boolean notexist = false;
				String sqlCus = "SELECT R.CUSTOMER_TASK_CARD FROM WO_TASK_CARD_CUSTOMER R "
						+ "WHERE R.TASK_CARD = ? AND R.WO = ? AND R.AC = ? AND R.PN = ? AND R.PN_SN = ?";
				
		        
		        try {
		        	 return (String) em.createNativeQuery(sqlCus)
		        			.setParameter(1, parent.getId().getTaskCard())
		        			.setParameter(2, parent.getId().getWo())
		        			.setParameter(3, parent.getId().getAc())
		        			.setParameter(4, parent.getId().getPn())
		        			.setParameter(5, parent.getId().getPnSn())
		        			.getSingleResult();		              		            
		        } catch (NoResultException e) {
		        	e.printStackTrace();
		        	logger.info("Customer ID does not exist");
		        } catch(Exception e) {
		        	logger.severe("Error getting ID.");
		        	logger.severe(e.toString());
		            throw e;
		        }
		        notexist = true;
		        
		        if(notexist) {
			       String sql = "SELECT R.TASK_CARD FROM WO_TASK_CARD R WHERE R.TASK_CARD = ? AND R.WO = ? ";
			        
			    

			        long i = cus;
			       String c = "CUS-" +String.format("%04d", i);
			      
			       try {
			    	   
			    		
			            while (true)
			            {
			            	
			            	try {
			            	  @SuppressWarnings("unused")
			            	  String found = (String) em.createNativeQuery(sql)
			        			.setParameter(1, c)
			        			.setParameter(2, parent.getId().getWo() )
			        			.getSingleResult();	
			            	}  catch (NoResultException e) {
			            		return c;
			            	}   
				            	i++;
					            c = "CUS-" + String.format("%04d", i);
			                
			            }
			        } catch (Exception e) {
			        	logger.severe("Error getting PickList.");
			        	logger.severe(e.toString());
			            throw e;
			        } 

		        }
		        return "";
			}
			
			
			
			
			
			
			private Boolean getWoThirdParty(long w) {
				logger.info("Checking WO");
				try 
				{
					Wo wo = em.createQuery("Select w From Wo w where w.id.wo =:work", Wo.class)
							.setParameter("work", w)
							.getSingleResult();
					if(wo .getThirdPartyWo() != null && !wo.getThirdPartyWo().isEmpty() &&
							wo.getThirdPartyWo().equalsIgnoreCase("Y") ) {
						
						logger.info("ThirdParty: " + wo.getThirdPartyWo());
						
						return true;
					}else {
						return false;
					}
				}
				catch(Exception e)
				{
					logger.info("NO Production WO found");
					e.printStackTrace();		
				}
			    return false;
			}
			
			private void setWoNumber(long w, BigDecimal number ) {
				
				try 
				{
					Wo wo = em.createQuery("Select w From Wo w where w.id.wo =:work", Wo.class)
							.setParameter("work", w)
							.getSingleResult();
					
					wo.setTaskCardNumberingSystem(number);
					logger.info("INSERTING WO: " + wo.getWo() + " Number: " + number.toString() );
					insertData(wo, "wo", "wo");
				}
				catch(Exception e)
				{
					e.printStackTrace();		
				}
			}
			
			
			public void setNumber(WoTaskCard cus) {
				
				//cus.setTaskCardNumberingSystem(new BigDecimal(getLine(new BigDecimal(cus.getId().getWo()),"task_card_numbering_system","wo_task_card","wo" )));
				//setWoNumber(cus.getId().getWo(), cus.getTaskCardNumberingSystem());
				
				//insertData(cus, "WoTaskCard", "cus");
				
			}
			
			
			private AcTypeSeriesMaster getAcTypeSeries(String AC) {
				try
				{	
					AcMaster acMaster = em.createQuery("Select a From AcMaster a where a.id.ac = :airC", AcMaster.class)
					.setParameter("airC", AC)
					.getSingleResult();
							
					return acMaster.getAcTypeSeriesMaster();
				}
				catch (Exception e)
				{
					
				}
				return null;
			}
			
			@SuppressWarnings("unchecked")
			private void exhancedData(WoTaskCard woTaskCard, String ops){
				TaskCard card = null;
				boolean shopWo = false;
				try
				{
					card = (TaskCard) this.em.createQuery("select t from TaskCard t where t.taskCard = :card")
							.setParameter("card", woTaskCard.getId().getTaskCard())
							.getSingleResult();
				}
				catch(Exception e)
				{
					logger.info("NO ENG TASK CARD FOUND");
					return;
				}
				if(getWoShop(new BigDecimal( woTaskCard.getId().getWo()).toString())){
					shopWo = true;
				}
				
				//ITEM
				if(card.getTaskCardItems() != null && !card.getTaskCardItems().isEmpty()) {
					for(TaskCardItem item :card.getTaskCardItems()) {
						if(item.getId().getTaskCardItem() != 1 && item.getOpsNo() != null && !item.getOpsNo().isEmpty() && item.getOpsNo().equalsIgnoreCase(ops)) {
							
							WoTaskCardItem	i = null;
							
							try{
								i = (WoTaskCardItem) this.em.createQuery("select t from WoTaskCardItem t where t.id.wo = :woo and t.id.taskCard = :card and t.id.taskCardItem = :item and t.opsNo = :ops ")
										.setParameter("woo", woTaskCard.getId().getWo())
										.setParameter("card", woTaskCard.getId().getTaskCard())
										.setParameter("item", item.getId().getTaskCardItem())
										.setParameter("ops", ops)
										.getSingleResult();
								
							}catch(Exception e)
							{
								e.printStackTrace();
								
								i = new WoTaskCardItem();
								i.setId(new WoTaskCardItemPK());
								
								i.setCreatedBy("TRAXIFACE");
								i.setCreatedDate(new Date());
								
								
								i.setStatus("OPEN");
								
								//EMRO fields to create basic object
								i.setMechanic("N");
								i.setInspector("N");
								i.setDuplicateInspection("N");
								i.setInspectorManHours(new BigDecimal(0));
								i.setInspectorManRequire(new BigDecimal(1));
								i.setManHours(new BigDecimal(0));
								i.setManRequire(new BigDecimal(1));
								i.setDualInspectorManHours(new BigDecimal(0));
								i.setDualInspectorManRequire(new BigDecimal(1));			
								i.setInformationalOnly("N");
								//i.setMainSkill("YES");
								
								i.getId().setAc(woTaskCard.getId().getAc());
								i.getId().setTaskCard(woTaskCard.getId().getTaskCard());
								i.getId().setTaskCardItem(item.getId().getTaskCardItem());
								i.getId().setTaskCardPn(woTaskCard.getId().getPn());
								i.getId().setTaskCardPnSn(woTaskCard.getId().getPnSn());
								i.getId().setWo(woTaskCard.getId().getWo());
								i.setOpsNo(ops);
								
							}
							
							i.setModifiedBy("TRAX_IFACE");
							i.setModifiedDate(new Date());
							
							i.setMainSkill(item.getMainSkill());
							
							i.setMechanic(item.getMechanic());
							
							i.setSkill(item.getSkill());
							
							i.setInspectorSkill(item.getInspectorSkill());
							
							i.setDualInspectorSkill(item.getDualInspectorSkill());
							
							i.setInspector(item.getInspector());
							
							i.setInspectorManHours(item.getInspectorManHours());
							
							i.setInspectorManRequire(item.getInspectorManRequire());
							
							i.setManRequire(item.getManRequire());
							
							i.setManHours(item.getManHours());
							
							i.setDuplicateInspection(item.getDuplicateInspection());
							
							i.setDualInspectorManHours(item.getDualInspectorManHours());
							
							i.setDualInspectorManRequire(item.getDualInspectorManRequire());
							
							i.setInformationalOnly(item.getInformationalOnly());
							
							i.setExternalCustRef(item.getExternalCustRef());
							i.setOpsNo(item.getOpsNo());
							
							i.setTaskCardText(item.getTaskCardText());
							
							//TODO ESD
							//MAN_HOURS
							//SKILL
							//if(shopWo) {
								
							//}
							
							logger.info("INSERTING WoTaskCardItem: " + i.getId().getTaskCardItem() );
							
							insertData(i, "WoTaskCardItem", "WoTaskCardItem");
						}
					}
					
					
					
				}
				//KEYWORD
				if(card.getTaskCardKeyword() != null && !card.getTaskCardKeyword().isEmpty()) {
					for(TaskCardKeyword tck :card.getTaskCardKeyword()) {
						
						WoTaskCardKeyword  k = null;
							
						try{
							k = (WoTaskCardKeyword) this.em.createQuery("select t from WoTaskCardKeyword t where t.id.wo = :woo "
									+ "and t.id.taskCard = :card and t.id.pn = :pn and t.id.pnSn = :sn "
									+ "and t.id.ac = :acc and t.id.keywordItem = :item")
									.setParameter("woo", woTaskCard.getId().getWo())
									.setParameter("card", woTaskCard.getId().getTaskCard())
									.setParameter("pn", woTaskCard.getId().getPn())
									.setParameter("sn", woTaskCard.getId().getPnSn())
									.setParameter("acc", woTaskCard.getId().getAc())
									.setParameter("item", tck.getId().getKeywordItem())
									.getSingleResult();
							continue;
						}catch(Exception e)
						{
							e.printStackTrace();
							k = new WoTaskCardKeyword();
							k.setId(new WoTaskCardKeywordPK());
							k.getId().setAc(woTaskCard.getId().getAc());
							k.getId().setKeywordItem(tck.getId().getKeywordItem());
							k.getId().setPn(woTaskCard.getId().getPn());
							k.getId().setPnSn(woTaskCard.getId().getPnSn());	
							k.getId().setTaskCard(woTaskCard.getId().getTaskCard());	
							k.getId().setWo(woTaskCard.getId().getWo());
							
							
							k.setCreatedBy("TRAXIFACE");
							k.setModifiedBy("TRAXIFACE");
							k.setCreatedDate(new Date());
							k.setModifiedDate(new Date());
							k.setKeywordText(tck.getKeywordText());
							logger.info("INSERTING WoTaskCardKeyword: " + k.getKeywordText());
							
							insertData(k, "WoTaskCardKeyword", "WoTaskCardKeyword");
						}
					}
				}
				//ATTACHMENT
				
				if(card.getBlobNo() != null) {
					List<BlobTable> blobs = null;
					
					
					try 
					{
						blobs = em.createQuery("SELECT b FROM BlobTable b where b.id.blobNo = :bl")
								.setParameter("bl", card.getBlobNo().longValue())
								.getResultList();
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					
					if(blobs !=null && !blobs.isEmpty()) {
						for(BlobTable b : blobs) {
							BlobTable blob = null;
							
							
							try 
							{
								blob = em.createQuery("SELECT b FROM BlobTable b where b.id.blobNo = :bl and b.blobDescription = :des", BlobTable.class)
										.setParameter("bl", woTaskCard.getBlobNo().longValue())
										.setParameter("des",b.getBlobDescription() )
										.getSingleResult();
								continue;
							}
							catch(Exception e)
							{
								
								BlobTablePK pk = new BlobTablePK();
								blob = new BlobTable();
								blob.setCreatedDate(new Date());
								blob.setCreatedBy("TRAX_IFACE");
								blob.setId(pk);
								
								blob.setPrintFlag("YES");
								blob.setDocType(b.getDocType());
								blob.getId().setBlobLine(getLine(woTaskCard.getBlobNo(),"BLOB_LINE","BLOB_TABLE","BLOB_NO" ));
							
								blob.setModifiedBy("TRAX_IFACE");
								blob.setModifiedDate(new Date());
								blob.setBlobItem(b.getBlobItem());
								blob.setBlobDescription(b.getBlobDescription());
								blob.setCustomDescription(b.getCustomDescription());
								if(woTaskCard.getBlobNo() == null) {
									try {
										blob.getId().setBlobNo(((getTransactionNo("BLOB").longValue())));
										woTaskCard.setBlobNo(new BigDecimal(blob.getId().getBlobNo()));
									} catch (Exception e1) {
										
									}
								}else if(woTaskCard.getBlobNo() != null){
									blob.getId().setBlobNo(woTaskCard.getBlobNo().longValue());
									
								}
								
								
								logger.info("INSERTING blob: " + woTaskCard.getBlobNo());
								
								insertData(blob, "BlobTable", "blob");
								
								insertData(woTaskCard, "WoTaskCard", "woTaskCard");
							
							}	
						}			
					}
					
				}
				//pre
				if(card.getTaskCardExecution() != null && !card.getTaskCardExecution().isEmpty()) {
					for(TaskCardExecution execution :card.getTaskCardExecution()) {
						
						WoTaskCardExecution  e = null;
							
						try{
							e = (WoTaskCardExecution) this.em.createQuery("select t from WoTaskCardExecution t where t.id.wo = :woo "
									+ "and t.id.taskCard = :card and t.id.pn = :pn and t.id.sn = :sn "
									+ "and t.id.ac = :acc and t.id.preTaskCard = :pre")
									.setParameter("woo", woTaskCard.getId().getWo())
									.setParameter("card", woTaskCard.getId().getTaskCard())
									.setParameter("pn", woTaskCard.getId().getPn())
									.setParameter("sn", woTaskCard.getId().getPnSn())
									.setParameter("acc", woTaskCard.getId().getAc())
									.setParameter("pre", execution.getTaskCardPredecessors())
									.getSingleResult();
							continue;
						}catch(Exception e1)
						{
							//e1.printStackTrace();
							e = new WoTaskCardExecution();
							e.setId(new WoTaskCardExecutionPK());
							e.getId().setAc(woTaskCard.getId().getAc());
							e.getId().setPn(woTaskCard.getId().getPn());
							e.getId().setSn(woTaskCard.getId().getPnSn());	
							e.getId().setTaskCard(woTaskCard.getId().getTaskCard());	
							e.getId().setWo(woTaskCard.getId().getWo());
							
							
							e.getId().setPreTaskCard(execution.getTaskCardPredecessors());
							e.getId().setPreTaskCardAc(woTaskCard.getId().getAc());
							e.getId().setPreTaskCardPn(woTaskCard.getId().getPn());
							e.getId().setPreTaskCardSn(woTaskCard.getId().getPnSn());
							
							e.setCreatedBy("TRAXIFACE");
							e.setModifiedBy("TRAXIFACE");
							e.setCreatedDate(new Date());
							e.setModifiedDate(new Date());
							
							logger.info("INSERTING WoTaskCardExecution: " + e.getId().getPreTaskCard());
							
							insertData(e, "WoTaskCardExecution", "WoTaskCardExecution");
						}
					}
				}
				//TASK_CARD TODO ESD
				//SUB_PHASE, BILLABLE_HOURS ,GATE, PHASE
				if(shopWo) {
				/*	
					woTaskCard.setPhase(card.getPhase());
					woTaskCard.setSubPhase(card.getSubPhase());
					woTaskCard.setBillableHours(card.getBillableHours());
					woTaskCard.setGate(card.getGate());
					woTaskCard.setModifiedDate(new Date());
					woTaskCard.setModifiedBy("TRAXIFACE");
					insertData(woTaskCard, "WoTaskCard", "woTaskCard");
				 */
				}
				
			}
			
			
			
			
			//insert task card control 
			private boolean insertTaskCardControl(WoTaskCard taskCard, String zone) //, TaskCard taskCard, TaskCardItem taskCardItem
			{
					WoTaskCardControl taskCardControlZone = null;
					TaskCardControlMaster taskCardControlMasterZone = null;
					
					String acType= null;
					String acSeries = null;
					
					AcTypeSeriesMaster acTypeSeriesMaster = getAcTypeSeries(taskCard.getId().getAc());
					
					if(acTypeSeriesMaster == null) {
						return false;
					}
					acType = acTypeSeriesMaster.getId().getAcType();
					acSeries = acTypeSeriesMaster.getId().getAcSeries();
					
					if( zone != null && !zone.isEmpty()) {
							try 
							{
								taskCardControlZone = em.createQuery("Select t From WoTaskCardControl t where "
										+ "t.id.controlCategory = :controlcat and t.id.acType = :act and t.id.acSeries = :acs and t.id.code = :cod "
										+ "and t.id.taskCardItem = :item and t.id.taskCard = :taskc "
										+ "and t.id.wo =:woo and t.id.ac =:acc and t.id.taskCardPn =:pnn "
										+ "and t.id.taskCardPnSn =:snn", WoTaskCardControl.class)
										.setParameter("controlcat", "ZONE")
										.setParameter("act", acType)
										.setParameter("acs", acSeries)
										.setParameter("cod", zone)
										.setParameter("item", new Long(0).longValue())
										.setParameter("taskc", taskCard.getId().getTaskCard())
										.setParameter("woo", taskCard.getId().getTaskCard())
										.setParameter("pnn", taskCard.getId().getTaskCard())
										.setParameter("snn", taskCard.getId().getTaskCard())
										.setParameter("acc", taskCard.getId().getTaskCard())
										.getSingleResult();
							}
							catch(Exception e)
							{
								
								WoTaskCardControlPK pk = new WoTaskCardControlPK();
								taskCardControlZone = new WoTaskCardControl();
								taskCardControlZone.setId(pk);
								taskCardControlZone.setCreatedDate(new Date());
								taskCardControlZone.setCreatedBy("TRAX_IFACE");
							}
							taskCardControlZone.setModifiedDate(new Date());
							taskCardControlZone.setModifiedBy("TRAX_IFACE");
							try 
							{
								taskCardControlMasterZone = em.createQuery("Select t From TaskCardControlMaster t where "
										+ "t.id.controlCategory = :controlcat and t.id.acType = :act and t.id.acSeries = :acs and t.id.code = :cod "
										, TaskCardControlMaster.class)
										.setParameter("controlcat", "ZONE")
										.setParameter("act", acType)
										.setParameter("acs", acSeries)
										.setParameter("cod", zone)
										.getSingleResult();
							}
							catch(Exception e)
							{
								TaskCardControlMasterPK pk = new TaskCardControlMasterPK();
								taskCardControlMasterZone = new TaskCardControlMaster();
								taskCardControlMasterZone.setId(pk);
								taskCardControlMasterZone.setCreatedDate(new Date());
								taskCardControlMasterZone.setCreatedBy("TRAX_IFACE");
								
								taskCardControlMasterZone.setManHours(new BigDecimal(0));
								taskCardControlMasterZone.setManRequire(new BigDecimal(0));
							}
							taskCardControlMasterZone.setModifiedDate(new Date());
							taskCardControlMasterZone.setModifiedBy("TRAX_IFACE");
						
						taskCardControlMasterZone.getId().setCode(zone);
						taskCardControlMasterZone.getId().setAcType(acType);
						taskCardControlMasterZone.getId().setAcSeries(acSeries);
						taskCardControlMasterZone.getId().setControlCategory("ZONE");
						
						taskCardControlZone.getId().setTaskCard(taskCard.getId().getTaskCard());
						taskCardControlZone.getId().setTaskCardItem(0);
						taskCardControlZone.getId().setAc(taskCard.getId().getAc());
						taskCardControlZone.getId().setTaskCardPn(taskCard.getId().getPn());
						taskCardControlZone.getId().setTaskCardPnSn(taskCard.getId().getPnSn());
						taskCardControlZone.getId().setWo(taskCard.getId().getWo());
												
						taskCardControlZone.getId().setCode(zone);
						taskCardControlZone.getId().setAcType(acType);
						taskCardControlZone.getId().setAcSeries(acSeries);
						taskCardControlZone.getId().setControlCategory("ZONE");
						
						logger.info("INSERTING Zone: " + zone);
						insertData(taskCardControlMasterZone,"Zone",zone);
						insertData(taskCardControlZone,"Zone",zone);
						return true;
					}
				return false;
			}
			
			public boolean lockAvailable(String notificationType)
			{
				
				//em.getTransaction().begin();
				InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type", InterfaceLockMaster.class)
						.setParameter("type", notificationType).getSingleResult();
				em.refresh(lock);
				//logger.info("lock " + lock.getLocked());
				if(lock.getLocked() == 1)
				{				
					LocalDateTime today = LocalDateTime.now();
					LocalDateTime locked = LocalDateTime.ofInstant(lock.getLockedDate().toInstant(), ZoneId.systemDefault());
					Duration diff = Duration.between(locked, today);
					if(diff.getSeconds() >= lock.getMax())
					{
						lock.setLocked(1);
						insertData(lock, "InterfaceLockMaster", "lock");
						return true;
					}
					return false;
				}
				else
				{
					lock.setLocked(1);
					insertData(lock, "InterfaceLockMaster", "lock");
					return true;
				}
				
			}
			
			
			public void lockTable(String notificationType)
			{
				em.getTransaction().begin();
				InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type", InterfaceLockMaster.class)
						.setParameter("type", notificationType).getSingleResult();
				lock.setLocked(1);
				//logger.info("lock " + lock.getLocked());
				
				lock.setLockedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()) );
				InetAddress address = null;
				try {
					address = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					
					logger.info(e.getMessage());
					//e.printStackTrace();
				}
				lock.setServer(address.getHostName());
				//em.lock(lock, LockModeType.NONE);
				em.merge(lock);
				em.getTransaction().commit();
			}
			
			public void unlockTable(String notificationType)
			{
				em.getTransaction().begin();
				
				InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type", InterfaceLockMaster.class)
						.setParameter("type", notificationType).getSingleResult();
				lock.setLocked(0);
				//logger.info("lock " + lock.getLocked());
				
				lock.setUnlockedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()) );
				//em.lock(lock, LockModeType.NONE);
				em.merge(lock);
				em.getTransaction().commit();
			}
			
			private Boolean getWoShop(String woString) {
				logger.info("Checking WO");
				try 
				{
					Wo wo = em.createQuery("Select w From Wo w where w.id.wo =:work", Wo.class)
							.setParameter("work", Long.parseLong(woString))
							.getSingleResult();
					if(wo .getModule() != null && !wo.getModule().isEmpty() &&
							wo.getModule().equalsIgnoreCase("SHOP") ) {
						
						logger.info("SHOP: " + wo.getModule());
						
						return true;
					}else {
						return false;
					}
				}
				catch(Exception e)
				{
					logger.info("NO Production WO found");
					e.printStackTrace();		
				}
			    return false;
			}
			
			
		
}
