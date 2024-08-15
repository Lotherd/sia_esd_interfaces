package trax.aero.data;


import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import trax.aero.controller.MaterialStatusImportController;
import trax.aero.interfaces.IMaterialStatusImportData;
import trax.aero.logger.LogManager;
import trax.aero.model.BlobTable;
import trax.aero.model.BlobTablePK;
import trax.aero.model.InterfaceAudit;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.model.PicklistDistribution;
import trax.aero.model.PicklistDistributionPK;
import trax.aero.model.PicklistDistributionRec;
import trax.aero.model.PicklistDistributionRecPK;
import trax.aero.model.PicklistHeader;
import trax.aero.model.PnInterchangeable;
import trax.aero.model.PnInventoryDetail;
import trax.aero.model.PnInventoryHistory;
import trax.aero.model.PnInventoryHistoryPK;
import trax.aero.model.PnMaster;
import trax.aero.model.SystemTranCode;
import trax.aero.model.WoTaskCard;
import trax.aero.pojo.MaterialStatusImportMaster;
import trax.aero.pojo.Transfer_order;
import trax.aero.utils.SharePointPoster;


//TODO
//LOCATION TRANSFER



@Stateless(name="MaterialStatusImportData" , mappedName="MaterialStatusImportData")
public class MaterialStatusImportData implements IMaterialStatusImportData {
	
	
	@PersistenceContext(unitName = "TraxStandaloneDS") private EntityManager em;
	
	String exceuted;
	String location = "SIN-ESD";
	String bin = "BIN-ESD";
	
	
	private Logger logger = LogManager.getLogger("MaterialStatusImport_I11&I12");
	public InterfaceLockMaster lock;
	
	
	
	public MaterialStatusImportData()
	{			
	}
	
	
	
	public String updateMaterial(MaterialStatusImportMaster materialMovementMaster)
	{
		//setting up variables
		exceuted = "OK";
		
		try 
		{
			updateMaterials(materialMovementMaster);
		}
		catch (Exception e) 
        {
			e.printStackTrace();
			MaterialStatusImportController.addError(e.toString());
            logger.severe(e.toString());
            em.getTransaction().rollback();
            exceuted = e.toString();
		}
		finally
		{
			//clean up 
			em.clear();
		}
		return exceuted;
	}
	
	//update a material
	private void updateMaterials(MaterialStatusImportMaster input) throws Exception 
	{	
		logger.info("Inside updateMaterials");
		//setting up variables
		PnInventoryDetail pnInventoryDetail = null;
		PicklistHeader picklistHeader = null;
		PicklistDistribution picklistDistributionDIS = null;
		PicklistDistribution picklistDistributionREQ = null;
		
		WoTaskCard woTaskCard = null;
		
		
		//check if object has min values
		if(input != null  && checkMinValue(input)) 
		{
			logger.info("After checkMinValue");
			String partNumber_Tool ;
			partNumber_Tool = input.getPN().replaceAll("\"", "IN");
			partNumber_Tool = partNumber_Tool.replaceAll("'", "FT");
			if(!partNumber_Tool.contains(":"))
			{
				partNumber_Tool = partNumber_Tool.concat(":UPLOAD");
			}
			input.setPN(partNumber_Tool);
			
			
			woTaskCard= getWoTaskCard(input);		
			if(input.getPICKLIST() != null && input.getPICKLIST_LINE() != null &&
				!input.getPICKLIST().isEmpty() && !input.getPICKLIST_LINE().isEmpty()) {
					picklistHeader = getPicklistHeader(input);
					picklistDistributionDIS = getPicklistDistribution(picklistHeader, input,"DISTRIBU",null);
					picklistDistributionREQ = getPicklistDistribution(picklistHeader, input,"REQUIRE",null);
			}else if(!input.getPICKLIST().equalsIgnoreCase("0000000000") && !input.getPICKLIST_LINE().equalsIgnoreCase("0000")){
						
					picklistHeader = getPicklistHeaderRev(input);
					picklistDistributionREQ = getPicklistDistribution(picklistHeader, input,"REQUIRE",null );
					picklistDistributionDIS = getPicklistDistribution(picklistHeader, input,"DISTRIBU",picklistDistributionREQ);
						
			}else {
						
					picklistHeader = getPicklistHeaderTaskCard(woTaskCard,input);
					if(picklistHeader == null ) {
						picklistHeader= getPicklistHeaderTaskCardFirtOne(woTaskCard, input);
					}
						
					picklistDistributionREQ = getPicklistDistribution(picklistHeader, input,"REQUIRE",null );
					if(picklistDistributionREQ == null) {
						throw new Exception ("picklistDistribution REQ is null");
					}
					picklistDistributionDIS = getPicklistDistribution(picklistHeader, input,"DISTRIBU",picklistDistributionREQ);
						
			}					
				pnInventoryDetail = getPnInventoryDetail(input,picklistHeader);
						
			
				if( picklistDistributionDIS.getQty().doubleValue() > pnInventoryDetail.getQtyAvailable().doubleValue() ) {
					throw new Exception("QTY requested is more than QTY available");
				}
				
				pnInventoryDetail.setModifiedBy("TRAX_IFACE");
				pnInventoryDetail.setModifiedDate(new Date());
				
				picklistDistributionDIS.setModifiedBy("TRAX_IFACE");
				picklistDistributionDIS.setModifiedDate(new Date());
				
				//LOCATION TRANSFER
				setPnInevtoryHistory(pnInventoryDetail, input, picklistDistributionDIS, "ISSUED");
				for( Transfer_order to: input.getTransfer_order()) {
					setCustTo(picklistDistributionDIS,to);
					setCustTo(picklistDistributionREQ,to);
				}
				picklistDistributionDIS.setExternalCustTo(input.getTransfer_order().get(0).getTRASNFER_ORDER_NUMBER());
				picklistDistributionDIS.setExternalCustToQty(input.getTransfer_order().get(0).getTRANSFER_ORDER_QUANTITY());
				
				picklistDistributionREQ.setExternalCustTo(input.getTransfer_order().get(0).getTRASNFER_ORDER_NUMBER());
				picklistDistributionREQ.setExternalCustToQty(input.getTransfer_order().get(0).getTRANSFER_ORDER_QUANTITY());
				
				
				logger.info("UPDATING pnInventoryDetail: " + input.getPN());
				
				insertData(pnInventoryDetail);
				
				logger.info("UPDATING picklistDistribution: " + picklistHeader.getPicklist());
				insertData(picklistDistributionDIS);
				insertData(picklistDistributionREQ);
				
				insertData(picklistHeader);
				
				
				//
				for(Transfer_order to : input.getTransfer_order()) {
					if(to.getAttachedDocumentIDOC() != null) 
					{	
						woTaskCard = setAttachedDocument(woTaskCard,to);	
					}
					
					if(to.getAttachmentLinkSharepointlink() != null ) 
					{
						
						byte[] file = getsharePointfile(new String(to.getAttachmentLinkSharepointlink(), StandardCharsets.UTF_8));
						String fileName = new String(to.getAttachmentLinkSharepointlink(), StandardCharsets.UTF_8);
						try {
							URL url =new URL(new String(to.getAttachmentLinkSharepointlink(), StandardCharsets.UTF_8));
							fileName = url.getFile().substring(url.getFile().lastIndexOf("/")+1,url.getFile().length());
						} catch (MalformedURLException e) {
							
						}					
						woTaskCard = setAttachmentLink(woTaskCard,to,file,fileName);	
					}			
				}
				
							
		}else 
		{
			exceuted = "Can not update Material: "+ input.getPN() +" as ERROR: Material is null or does not have minimum values";
			logger.severe(exceuted);
			MaterialStatusImportController.addError(exceuted);
		}
			
				
	}
	

	private void setCustTo(PicklistDistribution picklistDistributionDIS, Transfer_order to) {
		PicklistDistributionRec rec = null;
		try {
			 rec = em.createQuery("SELECT p FROM PicklistDistributionRec p WHERE p.id.picklist = :pic AND p.id.picklistLine = :res AND p.id.transaction = :act and p.id.custTo = :cus", PicklistDistributionRec.class)
					.setParameter("pic", picklistDistributionDIS.getId().getPicklist())
					.setParameter("res",picklistDistributionDIS.getId().getPicklistLine())
					.setParameter("act",picklistDistributionDIS.getId().getTransaction())
					.setParameter("cus",to.getTRASNFER_ORDER_NUMBER().longValue())
					.getSingleResult();
		}catch (Exception e) {
			rec = new PicklistDistributionRec();
			rec.setId(new PicklistDistributionRecPK());
			rec.getId().setCustTo(to.getTRASNFER_ORDER_NUMBER().longValue());
			rec.getId().setDistributionLine(picklistDistributionDIS.getId().getDistributionLine());
			rec.getId().setPicklist(picklistDistributionDIS.getId().getPicklist());
			rec.getId().setPicklistLine(picklistDistributionDIS.getId().getPicklistLine());
			rec.getId().setTransaction(picklistDistributionDIS.getId().getTransaction());
			rec.setCreatedBy("TRAX_IFACE");
			rec.setCreatedDate(new Date());
			
		}
		rec.setModifiedBy("TRAX_IFACE");
		rec.setModifiedDate(new Date());
		rec.setCustToQty(to.getTRANSFER_ORDER_QUANTITY());
		rec.setLegacyBatch(new BigDecimal( to.getLEGACY_BATCH()));
		insertData(rec);
	}



	private PnInventoryDetail getPnInventoryDetail(MaterialStatusImportMaster input, PicklistHeader pick) {
		PnInventoryDetail pnInventoryDetail = em.createQuery("SELECT p FROM PnInventoryDetail p where p.pn = :par and p.sn is null and p.location = :loc"
				+ " and p.createdBy != :create ", PnInventoryDetail.class)
				.setParameter("par", input.getPN())
				.setParameter("loc", pick.getLocation())
				.setParameter("create", "ISSUEIFACE")
				.getSingleResult();
		logger.info("Found PnInventoryDetail");
		return pnInventoryDetail;
	}
	
	private WoTaskCard getWoTaskCard(MaterialStatusImportMaster input) {
		

		WoTaskCard woTaskCard = null;
		try
		{
			woTaskCard = (WoTaskCard) em.createQuery("Select w from WoTaskCard w, PicklistHeader p where w.id.wo = p.wo and "
					+ "w.id.taskCard = p.taskCard and w.id.pn = p.taskCardPn and w.id.pnSn = p.taskCardSn and "
					+ "p.picklist = :pick ")
					.setParameter("pick", Long.parseLong(input.getPICKLIST()))
					.getSingleResult();
			logger.info("Found WoTaskCard 1");
		}
		catch(Exception e)
		{
			try {
				woTaskCard = (WoTaskCard) em.createQuery("Select w from WoTaskCard w, PicklistHeader p, Wo wo, PicklistDistribution pd where w.id.wo = p.wo and "
						+ "w.id.taskCard = p.taskCard and w.id.pn = p.taskCardPn and w.id.pnSn = p.taskCardSn and "
						+ "wo.rfoNo = :order and pd.pn = :pn and p.picklist = pd.id.picklist and wo.wo = w.id.wo")
						.setParameter("order", input.getRFO_NO())
						.setParameter("pn", input.getPN())
						.setMaxResults(1)
						.getSingleResult();
				logger.info("Found WoTaskCard 2");
			}catch(Exception ex)
			{
				try {
					woTaskCard = (WoTaskCard) em.createQuery("Select w from WoTaskCard w,Wo woo where "
							+ "woo.rfoNo = :order and woo.wo = w.id.wo and woo.module = :mod and (w.nonRoutine is null or w.nonRoutine = :nr) ")
							.setParameter("order", input.getRFO_NO())
							.setParameter("mod", "SHOP")
							.setParameter("nr", "N")
							.setMaxResults(1)
							.getSingleResult();
					
					logger.info("Found WoTaskCard 3");
				
				}catch(Exception exc) {
					e.printStackTrace();
				}
			}
		}
		return woTaskCard;
	}
	
	private PicklistHeader getPicklistHeader(MaterialStatusImportMaster input) {
		
		PicklistHeader picklistHeader = em.createQuery("SELECT p FROM PicklistHeader p WHERE p.id.picklist = :woo", PicklistHeader.class)
					.setParameter("woo", new BigDecimal(input.getPICKLIST()).longValue())
					.getSingleResult();
		logger.info("Found PicklistHeader");
			return picklistHeader;
		}
	
	
	
	private PicklistDistribution getPicklistDistribution(PicklistHeader picklistHeader, MaterialStatusImportMaster input, String transaction, PicklistDistribution picklistDistributionREQ) {
		PicklistDistribution picklistDistribution = null;
		boolean newRecord = true;
		
		if(input.getPICKLIST_LINE() != null  && !input.getPICKLIST_LINE().isEmpty()) {
		
		
			try {
				picklistDistribution = em.createQuery("SELECT p FROM PicklistDistribution p WHERE p.id.picklist = :pic AND p.id.picklistLine = :res AND p.id.transaction = :act", PicklistDistribution.class)
						.setParameter("pic", picklistHeader.getPicklist())
						.setParameter("res",new BigDecimal(input.getPICKLIST_LINE()).longValue())
						.setParameter("act",transaction)
						.getSingleResult();
				newRecord= false;
			}catch(Exception e) {
				
				if(transaction.equalsIgnoreCase("DISTRIBU")) {
					//EMRO fields to create basic object
					PicklistDistributionPK pk = new PicklistDistributionPK();
					picklistDistribution = new PicklistDistribution();
					picklistDistribution.setId(pk);
					picklistDistribution.setCreatedBy("TRAX_IFACE");
					picklistDistribution.setCreatedDate(new Date());
					picklistDistribution.setQtyPicked(new BigDecimal(1));
					picklistDistribution.setModifiedDate(new Date());
					picklistDistribution.setModifiedBy("TRAX_IFACE");
					picklistDistribution.setStatus("OPEN");
					picklistDistribution.setExternalCustRes(input.getEXTERNAL_CUST_RES());
					picklistDistribution.setExternalCustResItem(input.getEXTERNAL_CUST_RES_ITEM());
					picklistDistribution.getId().setPicklist(picklistHeader.getPicklist() );
					
					
					picklistDistribution.getId().setPicklistLine(new BigDecimal(input.getPICKLIST_LINE()).longValue());
					
					picklistDistribution.getId().setDistributionLine(new Long(2));
					picklistDistribution.getId().setTransaction(transaction);
					picklistDistribution.setPn(input.getPN());
					picklistDistribution.setQty((input.getTransfer_order().get(0).getTRANSFER_ORDER_QUANTITY()));
					picklistDistribution.setPicklistHeader(picklistHeader);
				}
				
			}
			if(transaction.equalsIgnoreCase("DISTRIBU") && newRecord && picklistDistribution != null) {
				logger.info("INSERTING PICKLIST NEW DISTRIBUTION: " +picklistDistribution.getId().getPicklist()  );
				insertData(picklistDistribution);	
			}
		}else if(!input.getPICKLIST().equalsIgnoreCase("0000000000") && !input.getPICKLIST_LINE().equalsIgnoreCase("0000")){
			try {
				picklistDistribution = em.createQuery("SELECT p FROM PicklistDistribution p WHERE p.id.picklist = :pic AND p.externalCustRes = :req AND p.externalCustResItem = :reqit AND p.id.transaction = :act", PicklistDistribution.class)
						.setParameter("pic", picklistHeader.getPicklist())
						.setParameter("req",input.getEXTERNAL_CUST_RES())
						.setParameter("reqit",input.getEXTERNAL_CUST_RES_ITEM())
						.setParameter("act",transaction)
						.getSingleResult();
				newRecord= false;
			}catch(Exception e) {
				
				if(transaction.equalsIgnoreCase("DISTRIBU")) {
					//EMRO fields to create basic object
					PicklistDistributionPK pk = new PicklistDistributionPK();
					picklistDistribution = new PicklistDistribution();
					picklistDistribution.setId(pk);
					picklistDistribution.setCreatedBy("TRAX_IFACE");
					picklistDistribution.setCreatedDate(new Date());
					picklistDistribution.setQtyPicked(new BigDecimal(1));
					picklistDistribution.setModifiedDate(new Date());
					picklistDistribution.setModifiedBy("TRAX_IFACE");
					picklistDistribution.setStatus("OPEN");
					picklistDistribution.setExternalCustRes(input.getEXTERNAL_CUST_RES());
					picklistDistribution.setExternalCustResItem(input.getEXTERNAL_CUST_RES_ITEM());
					picklistDistribution.getId().setPicklist(picklistHeader.getPicklist() );
					
					if(picklistDistributionREQ != null ) {
						picklistDistribution.getId().setPicklistLine(picklistDistributionREQ.getId().getPicklistLine());
						
					}else {
						picklistDistribution.getId().setPicklistLine(new Long(1).longValue());
					}
					
					picklistDistribution.getId().setDistributionLine(new Long(2));
					picklistDistribution.getId().setTransaction(transaction);
					picklistDistribution.setPn(input.getPN());
					picklistDistribution.setQty((input.getTransfer_order().get(0).getTRANSFER_ORDER_QUANTITY()));
					picklistDistribution.setPicklistHeader(picklistHeader);
				}
			}
		}else {
				try {
					picklistDistribution = em.createQuery("SELECT p FROM PicklistDistribution p WHERE p.id.picklist = :pic AND p.pn = :pnn AND p.id.transaction = :act", PicklistDistribution.class)
							.setParameter("pic", picklistHeader.getPicklist())
							.setParameter("pnn",input.getPN())
							.setParameter("act",transaction)
							.getSingleResult();
					newRecord= false;
				}catch(Exception e) {
					newRecord = true;
					try {
						
						picklistDistribution = em.createQuery("SELECT p FROM PicklistDistribution p WHERE p.id.picklist = :pic AND p.id.transaction = :act", PicklistDistribution.class)
								.setParameter("pic", picklistHeader.getPicklist())
								.setParameter("act",transaction)
								.getSingleResult();
						newRecord= false;
					}catch(Exception e1) {
						if(transaction.equalsIgnoreCase("DISTRIBU")) {
							//EMRO fields to create basic object
							PicklistDistributionPK pk = new PicklistDistributionPK();
							picklistDistribution = new PicklistDistribution();
							picklistDistribution.setId(pk);
							picklistDistribution.setCreatedBy("TRAX_IFACE");
							picklistDistribution.setCreatedDate(new Date());
							picklistDistribution.setQtyPicked(new BigDecimal(1));
							picklistDistribution.setModifiedDate(new Date());
							picklistDistribution.setModifiedBy("TRAX_IFACE");
							picklistDistribution.setStatus("OPEN");
							picklistDistribution.setExternalCustRes(input.getEXTERNAL_CUST_RES());
							picklistDistribution.setExternalCustResItem(input.getEXTERNAL_CUST_RES_ITEM());
							picklistDistribution.getId().setPicklist(picklistHeader.getPicklist() );
							
							if(picklistDistributionREQ != null ) {
								picklistDistribution.getId().setPicklistLine(picklistDistributionREQ.getId().getPicklistLine());
								
							}else {
								picklistDistribution.getId().setPicklistLine(new Long(1).longValue());
							}
							
							picklistDistribution.getId().setDistributionLine(new Long(2));
							picklistDistribution.getId().setTransaction(transaction);
							if(picklistDistributionREQ != null ) {
								picklistDistribution.setPn(picklistDistributionREQ.getPn());
							}else {
								picklistDistribution.setPn(input.getPN());
							}
							picklistDistribution.setQty((input.getTransfer_order().get(0).getTRANSFER_ORDER_QUANTITY()));
							picklistDistribution.setPicklistHeader(picklistHeader);
					}
					
					}
			}
			if(transaction.equalsIgnoreCase("DISTRIBU") && newRecord && picklistDistribution != null) {
				logger.info("INSERTING PICKLIST NEW DISTRIBUTION: " +picklistDistribution.getId().getPicklist()  );
				insertData(picklistDistribution);	
			}
		}
			
		logger.info("Found picklistDistribution");	
		return picklistDistribution;
	}
	
	
	//****************** Helper functions ******************
	
	
	private PnMaster getUOMfromPnMaster(String pn)
	{
		try
		{
			PnMaster pnMast = (PnMaster) this.em.createQuery("SELECT p FROM PnMaster p where p.pn = :value").setParameter("value", pn).getSingleResult();
			return pnMast;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	private String getPnInterchangeable(String PN) {
		try
		{
			PnInterchangeable pninter = em.createQuery("SELECT p FROM PnInterchangeable p where p.id.pnInterchangeable = :partn", PnInterchangeable.class)
			.setParameter("partn", PN)
			.getSingleResult();
			
			return pninter.getPnMaster().getPn();
		}
		catch (Exception e)
		{
			
		}
		return null;
	}
	
	private String getPnTransaction(String Category) {
		try
		{	
			SystemTranCode systemTranCode = em.createQuery("Select s From SystemTranCode s where s.id.systemCode = :cat and s.id.systemTransaction = :systran", SystemTranCode.class)
			.setParameter("cat", Category)
			.setParameter("systran", "PNCATEGORY")
			.getSingleResult();
					
			return systemTranCode.getPnTransaction();
		}
		catch (Exception e)
		{
			
		}
		return null;
	}
	
	
	//insert generic data from model objects
	private <T> void insertData( T data) 
	{
		try 
		{	
			em.merge(data);
			em.flush();
						
		}catch (Exception e)
		{
			e.printStackTrace();
			exceuted = "insertData has encountered an Exception: "+e.toString();
			MaterialStatusImportController.addError(exceuted);
			logger.severe(e.toString());
		}
	}
	
	private <T> void deleteData( T data) 
	{
		try 
		{	
			em.remove(data);
			em.flush();
		}catch (Exception e)
		{
			e.printStackTrace();
			exceuted = "deleteData has encountered an Exception: "+e.toString();
			MaterialStatusImportController.addError(exceuted);
			logger.severe(e.toString());
		}
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
	
		private boolean checkMinValue(MaterialStatusImportMaster input) {
		
			if( input.getRFO_NO() == null || input.getRFO_NO().isEmpty()) {
				MaterialStatusImportController.addError("Can not update Material QTY: "+ input.getPN() +" as ERROR RFO_NO");
				return false;
			}
			if( input.getEXTERNAL_CUST_RES() == null || input.getEXTERNAL_CUST_RES().isEmpty()) {
				MaterialStatusImportController.addError("Can not update Material QTY: "+ input.getPN() +" as ERROR ReservationNumber");
				return false;
			}	
			if( input.getPN() == null || input.getPN().isEmpty()) {
				MaterialStatusImportController.addError("Can not update Material QTY: "+ input.getPN() +" as ERROR Material");
				return false;
			}
			
			if( input.getTransfer_order() == null ) {
				MaterialStatusImportController.addError("Can not update Material QTY: "+ input.getPN() +" as ERROR TRANSFER_ORDER");
				return false;
			}
				
			
			if( input.getPN() == null || input.getPN().isEmpty() || getPnInterchangeable(input.getPN()) == null) {
				MaterialStatusImportController.addError("Part number is empty or does not exist");
				return false;
			}
			
			PnMaster partMaster = getUOMfromPnMaster(getPnInterchangeable(input.getPN()));
			if(partMaster.getStatus() !=null && !partMaster.getStatus().isEmpty() && partMaster.getStatus().equalsIgnoreCase("INACTIVE")) {
				MaterialStatusImportController.addError("PN is not a active");
				return false;
			}			
			
			return true;
		}
	
	
	
	

	private WoTaskCard setAttachedDocument(WoTaskCard woTaskCard, Transfer_order to) {
		BlobTable blob = null;
		String filename = to.getLEGACY_BATCH()+ "_"+woTaskCard.getId().getWo() +"_IDOC.pdf";
		
		try 
		{
			blob = em.createQuery("SELECT b FROM BlobTable b where b.id.blobNo = :bl and b.blobDescription = :des", BlobTable.class)
					.setParameter("bl", woTaskCard.getBlobNo().longValue())
					.setParameter("des",filename )
					.getSingleResult();
			//existBlob = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BlobTablePK pk = new BlobTablePK();
			blob = new BlobTable();
			blob.setCreatedDate(new Date());
			blob.setCreatedBy("TRAX_IFACE");
			blob.setId(pk);
			
			blob.setPrintFlag("YES");
			
			blob.getId().setBlobLine(getLine(woTaskCard.getBlobNo(),"BLOB_LINE","BLOB_TABLE","BLOB_NO" ));
			
			if(woTaskCard.getBlobNo() == null) {
				try {
					blob.getId().setBlobNo(((getTransactionNo("BLOB").longValue())));
					woTaskCard.setBlobNo(new BigDecimal(blob.getId().getBlobNo()));
				} catch (Exception e1) {
				}
			}else {
				blob.getId().setBlobNo(woTaskCard.getBlobNo().longValue());
			}
		}
		
		
		blob.setDocType("IDOC");
		
		
		blob.setModifiedBy("TRAX_IFACE");
		blob.setModifiedDate(new Date());
		blob.setBlobItem(to.getAttachedDocumentIDOC());
		blob.setBlobDescription(filename);
		blob.setCustomDescription(filename);
		
		
		
		logger.info("UPDATING woTaskCard: TASK_CARD: " + woTaskCard.getId().getTaskCard() + " WO: " + woTaskCard.getId().getWo()  );
		insertData(woTaskCard);
		
		logger.info("UPDATING blob: " + blob.getId().getBlobNo());
		insertData(blob);
	
		return woTaskCard;
	
	}
	
	private WoTaskCard setAttachmentLink(WoTaskCard woTaskCard, Transfer_order input, byte[] file, String fileName) {
		BlobTable blob = null;
		if(file == null) {
			try 
			{
				blob = em.createQuery("SELECT b FROM BlobTable b where b.id.blobNo = :bl and b.webLink = :link", BlobTable.class)
						.setParameter("bl", woTaskCard.getBlobNo().longValue())
						.setParameter("link", new String(input.getAttachmentLinkSharepointlink(), StandardCharsets.UTF_8))
						.getSingleResult();
				
			}
			catch(Exception e)
			{
				//e.printStackTrace();
				BlobTablePK pk = new BlobTablePK();
				blob = new BlobTable();
				blob.setCreatedDate(new Date());
				blob.setCreatedBy("TRAX_IFACE");
				blob.setId(pk);
				
				blob.setPrintFlag("YES");
				blob.setBlobType("EXTLINK");
				if(woTaskCard.getBlobNo() == null) {
					try {
						blob.getId().setBlobNo(((getTransactionNo("BLOB").longValue())));
						woTaskCard.setBlobNo(new BigDecimal(blob.getId().getBlobNo()));
					} catch (Exception e1) {
					}
				}else {
					blob.getId().setBlobNo(woTaskCard.getBlobNo().longValue());
				}
				blob.getId().setBlobLine(getLine(woTaskCard.getBlobNo(),"BLOB_LINE","BLOB_TABLE","BLOB_NO" ));
				
			}	   
			
			blob.setModifiedBy("TRAX_IFACE");
			blob.setModifiedDate(new Date());
			blob.setWebLink(new String(input.getAttachmentLinkSharepointlink(), StandardCharsets.UTF_8));
			blob.setBlobDescription(fileName);
			blob.setCustomDescription("AttachmentLink");
			
			//blob.setDocType("LINK");
			
			
			
			logger.info("UPDATING woTaskCard: TASK_CARD: " + woTaskCard.getId().getTaskCard() + " WO: " + woTaskCard.getId().getWo()  );
			insertData(woTaskCard);
			
			logger.info("UPDATING blob: " + blob.getId().getBlobNo());
			insertData(blob);
		
			return woTaskCard;

		}else {
			try 
			{
				blob = em.createQuery("SELECT b FROM BlobTable b where b.id.blobNo = :bl and b.blobDescription = :des and b.customDescription = :cus", BlobTable.class)
						.setParameter("bl", woTaskCard.getBlobNo().longValue())
						.setParameter("des",fileName )
						.setParameter("cus","SHAREPOINT" )
						.getSingleResult();
				
			}
			catch(Exception e)
			{
				//e.printStackTrace();
				BlobTablePK pk = new BlobTablePK();
				blob = new BlobTable();
				blob.setCreatedDate(new Date());
				blob.setCreatedBy("TRAX_IFACE");
				blob.setId(pk);
				
				blob.setPrintFlag("YES");
				
				if(woTaskCard.getBlobNo() == null) {
					try {
						blob.getId().setBlobNo(((getTransactionNo("BLOB").longValue())));
						woTaskCard.setBlobNo(new BigDecimal(blob.getId().getBlobNo()));
					} catch (Exception e1) {
					}
				}else {
					blob.getId().setBlobNo(woTaskCard.getBlobNo().longValue());
				}
				
				blob.getId().setBlobLine(getLine(woTaskCard.getBlobNo(),"BLOB_LINE","BLOB_TABLE","BLOB_NO" ));
				
				
			}
			
			
			blob.setDocType("SHAREPOINT");
			
			
			blob.setModifiedBy("TRAX_IFACE");
			blob.setModifiedDate(new Date());
			blob.setBlobItem(file);
			blob.setBlobDescription(fileName);
			blob.setCustomDescription("SHAREPOINT");
			
			
			
			logger.info("UPDATING woTaskCard: TASK_CARD: " + woTaskCard.getId().getTaskCard() + " WO: " + woTaskCard.getId().getWo()  );
			insertData(woTaskCard);
			
			logger.info("UPDATING blob: " + blob.getId().getBlobNo());
			insertData(blob);
		
			return woTaskCard;
		}
	}

	
	private PnInventoryHistory setPnInevtoryHistory(PnInventoryDetail pnInventoryDetail, MaterialStatusImportMaster
			parameter, PicklistDistribution pick, String transactionType) throws Exception {
		
		PnInventoryHistory pnInventoryHistory = null;
			
		pnInventoryHistory = new PnInventoryHistory();
		PnInventoryHistoryPK pk = new PnInventoryHistoryPK();
				
		pnInventoryHistory.setCreatedDate(new Date());
		pnInventoryHistory.setCreatedBy("TRAX_IFACE");
		pnInventoryHistory.setId(pk);
				
		pnInventoryHistory.setModifiedBy("TRAX_IFACE");
		pnInventoryHistory.setModifiedDate(new Date());
			
		pnInventoryHistory.setPn(pnInventoryDetail.getPn());
		pnInventoryHistory.setSn(pnInventoryDetail.getSn());
		pnInventoryHistory.setGoodsRcvdBatch(pnInventoryDetail.getGoodsRcvdBatch());
		pnInventoryHistory.getId().setBatch(pnInventoryDetail.getBatch());
		
		pnInventoryHistory.setTransactionType(transactionType);
		pnInventoryHistory.setQty( (pick.getQtyPicked()));
		pnInventoryHistory.setToLocation(location);
		
		
		try {
			pnInventoryHistory.getId().setTransactionNo(getTransactionNo("PNINVHIS").longValue());
		} catch (Exception e) {
		}
		
		
		
		pnInventoryHistory.setUnitCost(pnInventoryDetail.getUnitCost());
		pnInventoryHistory.setSecondaryCost(pnInventoryDetail.getSecondaryCost());
		pnInventoryHistory.setSecondaryCurrencyExchange(pnInventoryDetail.getSecondaryCurrencyExchange());
		pnInventoryHistory.setCurrencyExchangeRate(pnInventoryDetail.getCurrencyExchangeRate());
		pnInventoryHistory.setToBin(pnInventoryDetail.getBin());
		pnInventoryHistory.setCondition(pnInventoryDetail.getCondition());
		pnInventoryHistory.setBin(pnInventoryDetail.getBin());
		pnInventoryHistory.setNla("Y");
		
		
		pnInventoryHistory.setCondition(pnInventoryDetail.getCondition());		
		
		pnInventoryHistory.setSn(pnInventoryDetail.getSn());
		pnInventoryHistory.setGl(pnInventoryDetail.getGl());
		pnInventoryHistory.setGlCompany(pnInventoryDetail.getGlCompany());
		pnInventoryHistory.setGlCostCenter(pnInventoryDetail.getGlCostCenter());
		pnInventoryHistory.setGlExpenditure(pnInventoryDetail.getGlExpenditure());
		
		pnInventoryHistory.setUnitCost(pnInventoryDetail.getUnitCost());
		pnInventoryHistory.setCurrency(pnInventoryDetail.getCurrency());
		pnInventoryHistory.setOwner(pnInventoryDetail.getOwner());
		pnInventoryHistory.setNotes(pnInventoryDetail.getNotes());
		pnInventoryHistory.setLocation(pnInventoryDetail.getLocation());
			
		if(pnInventoryDetail.getSn() == null || pnInventoryDetail.getSn().isEmpty() ||
					getPnTransaction(getUOMfromPnMaster(pnInventoryDetail.getPn()).getCategory()).equalsIgnoreCase("C")
					)  {
				PnInventoryDetail stockTransfer = setPnInevtoryDetail(pnInventoryDetail,parameter,pick);
				pnInventoryHistory.setGoodsRcvdBatch(stockTransfer.getGoodsRcvdBatch());
				pnInventoryHistory.getId().setBatch(stockTransfer.getBatch());
		}else {
				pnInventoryDetail	= setPnInevtoryDetailSN(pnInventoryDetail,parameter,pick);
				pnInventoryHistory.setGoodsRcvdBatch(pnInventoryDetail.getGoodsRcvdBatch());
				pnInventoryHistory.getId().setBatch(pnInventoryDetail.getBatch());
		}		
		
		System.out.println("INSERTING pnInventoryHistory: " + parameter.getPN());
		insertData(pnInventoryHistory);
		
		return pnInventoryHistory;
	}
	
	private PnInventoryDetail setPnInevtoryDetail(PnInventoryDetail pnInventoryDetail, MaterialStatusImportMaster
			parameter, PicklistDistribution pick) throws Exception {
		
		
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		MapperFacade mapper=mapperFactory.getMapperFacade();
		PnInventoryDetail stockTransfer = mapper.map(pnInventoryDetail, PnInventoryDetail.class);
		
		
		stockTransfer.setCreatedDate(pnInventoryDetail.getCreatedDate());
		stockTransfer.setCreatedBy(pnInventoryDetail.getCreatedBy());
			
		
		BigDecimal qtyAvail = pnInventoryDetail.getQtyAvailable();
		pnInventoryDetail.setQtyAvailable(qtyAvail.subtract( ( pick.getQty())));
		stockTransfer.setQtyReserved(( pick.getQty()));
			
		//stockTransfer.setQtyAvailable(new BigDecimal(0));
		stockTransfer.setQtyAvailable(new BigDecimal(0));
		stockTransfer.setQtyInTransfer(new BigDecimal(0));
		stockTransfer.setQtyPendingRi(new BigDecimal(0));
		stockTransfer.setQtyUs(new BigDecimal(0));
		stockTransfer.setQtyInRepair(new BigDecimal(0));
		stockTransfer.setCondition(pnInventoryDetail.getCondition());		
		stockTransfer.setPn(pnInventoryDetail.getPn());
		stockTransfer.setLocation(pnInventoryDetail.getLocation());
		stockTransfer.setSn(pnInventoryDetail.getSn());
		stockTransfer.setGl(pnInventoryDetail.getGl());
		stockTransfer.setGlCompany(pnInventoryDetail.getGlCompany());
		stockTransfer.setGlCostCenter(pnInventoryDetail.getGlCostCenter());
		stockTransfer.setGlExpenditure(pnInventoryDetail.getGlExpenditure());
		stockTransfer.setBin(pnInventoryDetail.getBin());
		stockTransfer.setLocation(location);
		stockTransfer.setRiBy(pnInventoryDetail.getRiBy());
		stockTransfer.setRiDate(pnInventoryDetail.getRiDate());
		stockTransfer.setUnitCost(pnInventoryDetail.getUnitCost());
		stockTransfer.setCurrency(pnInventoryDetail.getCurrency());
		stockTransfer.setRiControl(pnInventoryDetail.getRiControl());
		stockTransfer.setOwner(pnInventoryDetail.getOwner());
		stockTransfer.setOwnerOrigin(pnInventoryDetail.getOwnerOrigin());		
		
		long b = getTransactionNo("BATCH").longValue();
		stockTransfer.setBatch(b);
		
		
		//CONSIABLE then hold old batch
		
		stockTransfer.setGoodsRcvdBatch(new BigDecimal( pnInventoryDetail.getBatch()));
				
				
		pnInventoryDetail.setModifiedBy("TRAX_IFACE");
		pnInventoryDetail.setModifiedDate(new Date());
		stockTransfer.setModifiedBy("TRAX_IFACE");
		stockTransfer.setModifiedDate(new Date());
		
		System.out.println("INSERTING stockTransfer RECORD PN: "	+	
		stockTransfer.getPn() + ", BATCH: " + stockTransfer.getBatch() );
		
		
		insertData(stockTransfer);
		
		
		if((pnInventoryDetail.getQtyAvailable()!= null
				&&
				pnInventoryDetail.getQtyInRental()!= null
				&&
				pnInventoryDetail.getQtyInRepair()!= null
				&&
				pnInventoryDetail.getQtyInTransfer()!= null
				&&
				pnInventoryDetail.getQtyPendingRi()!= null
				&&
				pnInventoryDetail.getQtyReserved()!= null
				&&
				pnInventoryDetail.getQtyUs()!= null)
				&&
				(
				pnInventoryDetail.getQtyAvailable().intValue() == 0
				&&
				pnInventoryDetail.getQtyInRental().intValue() == 0
				&&
				pnInventoryDetail.getQtyInRepair().intValue() == 0
				&&
				pnInventoryDetail.getQtyInTransfer().intValue() == 0
				&&				
				pnInventoryDetail.getQtyPendingRi().intValue() == 0
				&&
				pnInventoryDetail.getQtyReserved().intValue() == 0
				&&
				pnInventoryDetail.getQtyUs().intValue() == 0
				)
				) {
			System.out.println("DELETE pnInventoryDetail RECORD PN: "	+	
					pnInventoryDetail.getPn() + ", BATCH: " + pnInventoryDetail.getBatch() );
				
			deleteData(pnInventoryDetail);
		}else {
			System.out.println("INSERTING pnInventoryDetail RECORD PN: "	+	
				pnInventoryDetail.getPn() + ", BATCH: " + pnInventoryDetail.getBatch() );
			
			insertData(pnInventoryDetail);
		}
		
		new BigDecimal(stockTransfer.getBatch());
		
		return stockTransfer;
		
	}
	
	
	private PnInventoryDetail setPnInevtoryDetailSN(PnInventoryDetail pnInventoryDetail, MaterialStatusImportMaster
			parameter,
			PicklistDistribution pick) throws Exception {
		
		pnInventoryDetail.setBin(pnInventoryDetail.getBin());
		pnInventoryDetail.setLocation(location);
		
		pnInventoryDetail.setQtyAvailable(  ( pick.getQtyPicked()));
		insertData(pnInventoryDetail);
		
		return pnInventoryDetail;
	}

	
	
	private PnInventoryDetail getPnInventoryDetailEmpty(PnInventoryDetail pnInventoryDetail,MaterialStatusImportMaster input , Transfer_order to ) {
		PnInventoryDetail pid = null;
		try {
			 pid = em.createQuery("SELECT p FROM PnInventoryDetail p where p.pn = :par and p.sn is null and p.location = :loc "
			 		+ " and p.legacyBatch = :bat and p.createdBy = :create", PnInventoryDetail.class)
					.setParameter("par", pnInventoryDetail.getPn())
					.setParameter("loc", pnInventoryDetail.getLocation())
					.setParameter("bat", to.getLEGACY_BATCH())
					.setParameter("create", "ISSUEIFACE")
					.getSingleResult();
			logger.info("Found PnInventoryDetail empty record");
		}catch (Exception e) {
				pid = new PnInventoryDetail();
				pid.setCreatedDate(new Date());
				pid.setCreatedBy("ISSUEIFACE");
				
				//EMRO fields to create basic object
				pid.setQtyAvailable(new BigDecimal(0));
				pid.setQtyReserved(new BigDecimal(0));
				pid.setQtyInTransfer(new BigDecimal(0));
				pid.setQtyPendingRi(new BigDecimal(0));
				pid.setQtyUs(new BigDecimal(0));
				pid.setUnitCost(new BigDecimal(0));
				pid.setCurrencyExchangeRate(new BigDecimal(1));
				pid.setQtyInRental(new BigDecimal(0));
				pid.setSecondaryCost(new BigDecimal(0));
				pid.setSecondaryCurrencyExchange(new BigDecimal(1));
				
				pid.setKitNo(new BigDecimal(0));
				pid.setBlobNo(new BigDecimal(0));
				pid.setDocumentNo(new BigDecimal(0));
				pid.setFilingSequence(new BigDecimal(0));
				pid.setNoOfTagPrint(new BigDecimal(0));
				pid.setSosHour(new BigDecimal(0));
				pid.setSlot(new BigDecimal(0));
				pid.setNlaPosition(" ");
				
				pid.setInventoryType("MAINTENANCE");	
				try
				{
					String company = (String) this.em.createQuery("select p.profile from ProfileMaster p")
							.getSingleResult();
					pid.setGlCompany(company);
				}
				catch(Exception e1) {
					pid.setGlCompany("SIAEC");	
				}
				pid.setCondition("NEW");
				
				pid.setModifiedBy("TRAX_IFACE");
				pid.setModifiedDate(new Date());
				
				pid.setPn(pnInventoryDetail.getPn());
				pid.setLocation(pnInventoryDetail.getLocation());
				long batch = getTransactionNo("BATCH").longValue();
				pid.setBatch(batch);
				pid.setGoodsRcvdBatch(new BigDecimal(batch));
				
				
		}
		pid.setModifiedBy("TRAX_IFACE");
		pid.setModifiedDate(new Date());
		pid.setLegacyBatch(to.getLEGACY_BATCH());
		
		logger.info("INSERTING Empty PID RECORD PN: "	+	
				pid.getPn() + " , PLANT: " + pid.getLocation()		
				+ ", BATCH: " + pid.getBatch() );
				
		insertData(pid);
		
		return pid;
	}



	public byte[] getsharePointfile(String spurl){
		
		logger.info("Getting SharePoint File");
		
		logger.info("setup variables");
		String clientID = System.getProperty("clientId");
		String clientSecret = System.getProperty("clientSecret");
		String tenantId = System.getProperty("tenantId");
		String tenant = System.getProperty("tenant");
		byte[]file = null;
		
		
		String resource = "00000003-0000-0ff1-ce00-000000000000/"+tenant+".sharepoint.com@"+tenantId;
		
		String spsite = "";
		String sppath = "";
		
		String spfile = "";
		
		String token = "";
		
		String urlToken = "https://accounts.accesscontrol.windows.net/"+tenantId+"/tokens/OAuth/2";
		
		clientID = clientID + "@" + tenantId;
		
		try {
			
			URL url =new URL(spurl);    
			
			if(spurl.toUpperCase().contains("SITES") ) {
				spsite = url.getProtocol() + "://" + url.getHost() 
				+ url.getPath().substring(0, ordinalIndexOf(url.getPath(),"/",3));
				
				sppath = url.getFile().substring(ordinalIndexOf(url.getFile(),"/",3)+1, url.getFile().lastIndexOf("/")+ 1);
				
			}else if(spurl.toUpperCase().contains("TEAM") ) {
				
				String p = url.getPath().substring(0, ordinalIndexOf(url.getPath(),"%20Documents",1)); 
		        String s = 		p.substring(0,p.lastIndexOf("/"));
		        spsite = url.getProtocol() + "://" + url.getHost() + s;
								
				String t = 	p.substring(p.lastIndexOf("/")+1,p.length());
			
				sppath = t + url.getFile().substring(ordinalIndexOf(url.getFile(),"%20Document",0) , url.getFile().lastIndexOf("/")+ 1);
				
			}else {
				
				spsite = url.getProtocol() + "://" + url.getHost();
				
				sppath = url.getFile().substring(0, url.getFile().lastIndexOf("/")+ 1);
				
			}
			
			
			
			spfile = url.getFile().substring(url.getFile().lastIndexOf("/")+1,url.getFile().length());
			
			String urlApi = spsite + "/_api/web/GetFolderByServerRelativeUrl('"+sppath+"')/Files('"+spfile+"')/$value";
				
					
						
	        SharePointPoster poster = new SharePointPoster();
	        //logger.info(resource);
	        
	        //logger.info(resource);
	        
	        token = poster.getToken(clientID,clientSecret,tenantId,resource,urlToken);
	        
	        if(token == null) {
	        	return null;
	        }
	        
	        logger.info("Token: "+token);
	        
	        
	        
	        poster.postSharePoint(urlApi,token);
	        
	        file = poster.getBody();
	        			 
			 return file;
		
		}catch(Exception e) {
			e.printStackTrace();
			file = null;
			logger.info(e.toString());			
		}	
		return file;	
	}
	
	public  int ordinalIndexOf(String str, String substr, int n) {
	    int pos = str.indexOf(substr);
	    while (--n > 0 && pos != -1)
	        pos = str.indexOf(substr, pos + 1);
	    return pos;
	}
	
	private PicklistHeader getPicklistHeaderRev(MaterialStatusImportMaster input) {
		try
		{	
			ArrayList<PicklistDistribution>picklistdist = (ArrayList<PicklistDistribution>) em.createQuery("SELECT p FROM PicklistDistribution p where p.externalCustRes =:pi AND p.externalCustResItem =:it AND p.id.transaction =:tra AND p.id.distributionLine =:dl")
					.setParameter("pi", input.getPICKLIST())
					.setParameter("it", input.getPICKLIST_LINE())
					.setParameter("tra", "REQUIRE")
					.setParameter("dl",new Long(0) )
					.getResultList();
			logger.info("Found PicklistHeader Rev");
			
			return picklistdist.get(0).getPicklistHeader();
		}
		catch (Exception e)
		{	
			logger.info("PICKLIST NOT FOUND");
		}
		return null;
	}
	
	private PicklistHeader getPicklistHeaderTaskCard(WoTaskCard woTaskCard, MaterialStatusImportMaster m) {
		try
		{	
			List<PicklistHeader> picklistHeader = em.createQuery("SELECT p FROM PicklistHeader p WHERE p.wo = :woo and p.taskCard = :tas ")
					.setParameter("woo", new BigDecimal(woTaskCard.getId().getWo()))
					.setParameter("tas", woTaskCard.getId().getTaskCard())
					.getResultList();
			
			for(PicklistHeader p : picklistHeader) {
				if(p.getPicklistDistributions() != null) {
					for(PicklistDistribution d : p.getPicklistDistributions()) {
						if(d.getId().getTransaction().equalsIgnoreCase("REQUIRE") 
								&& d.getPn().equalsIgnoreCase(m.getPN())) {
							logger.info("Found PicklistHeader TaskCard");	
							return d.getPicklistHeader();
						}
					}
				}	
			}
		}
		catch (Exception e)
		{	
			e.printStackTrace();
			logger.info("PICKLIST NOT FOUND");
		}
		return null;
	}
	
	private PicklistHeader getPicklistHeaderTaskCardFirtOne(WoTaskCard woTaskCard, MaterialStatusImportMaster m) {
		try
		{	
			List<PicklistHeader> picklistHeader = em.createQuery("SELECT p FROM PicklistHeader p WHERE p.wo = :woo and p.taskCard = :tas ")
					.setParameter("woo", new BigDecimal(woTaskCard.getId().getWo()))
					.setParameter("tas", woTaskCard.getId().getTaskCard())
					.getResultList();
			logger.info("PICKLIST HEADER SIZE " +picklistHeader.size());
			for(PicklistHeader p : picklistHeader) {
				if(p.getPicklistDistributions() != null) {
					for(PicklistDistribution d : p.getPicklistDistributions()) {
						if(d.getId().getTransaction().equalsIgnoreCase("REQUIRE")  && 
						(d.getStatus() != null && !d.getStatus().isEmpty() 
						&& !d.getStatus().equalsIgnoreCase("CANCEL"))) {
							logger.info("Found PicklistHeader TaskCard Firt One");	
							return d.getPicklistHeader();
						}
					}
				}	
			}
		}
		catch (Exception e)
		{	
			e.printStackTrace();
			logger.info("PICKLIST NOT FOUND");
		}
		return null;
	}	
	
	
	@Override
	public void logError(String error) {
		
		InterfaceAudit ia = null;
		ia = new InterfaceAudit();
		ia.setTransaction(getSeqNoInterfaceAudit().longValue());
		ia.setTransactionType("ERROR");
		ia.setTransactionObject("I11&I12");
		ia.setTransactionDate(new Date());
		ia.setCreatedBy("TRAX_IFACE");
		ia.setModifiedBy("TRAX_IFACE");
		ia.setCreatedDate(new Date());
		ia.setModifiedDate(new Date());
		ia.setExceptionId(new BigDecimal(-2000));
		ia.setExceptionByTrax("Y");
		ia.setExceptionDetail("Material Status Import interface did not receive XML correctly.");
		ia.setExceptionStackTrace(error);
		ia.setExceptionClassTrax("MaterialStatusImport_I11&I12");	
		
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
