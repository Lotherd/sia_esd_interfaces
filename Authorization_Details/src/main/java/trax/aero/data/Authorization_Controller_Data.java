package trax.aero.data;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import trax.aero.controller.Authorization_Details_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.BlobTable;
import trax.aero.model.EmployeeAuthPreReq;
import trax.aero.model.EmployeeAuthPreReqPK;
import trax.aero.model.EmployeeAuthorization;
import trax.aero.model.EmployeeAuthorizationApv;
import trax.aero.model.EmployeeAuthorizationApvPK;
import trax.aero.model.EmployeeControl;
import trax.aero.model.EmployeeControlPK;
import trax.aero.model.EmployeeSkill;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.model.RelationMaster;
import trax.aero.model.SystemTranCode;
import trax.aero.model.SystemTranCodePK;
import trax.aero.pojo.EmployeeLicense;
import trax.aero.pojo.ThirdParty;
import trax.aero.utils.DataSourceClient;

public class Authorization_Controller_Data {

	private Connection con;
	EmployeeLicense in;
	
	EntityManagerFactory factory;
	EntityManager em;
	String executed = "OK";
	
	ArrayList<String> Inactive = new ArrayList<>(Arrays.asList("Withdrawn","Suspension","Secondment","Resignation","Terminated","End of Contract Term","Retirement"));
	
	static Logger logger = LogManager.getLogger("AuthDetails");
	
	public Authorization_Controller_Data() {
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		em = factory.createEntityManager();
		
		try {
			if(this.con == null || this.con.isClosed())
			{
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!this.con.isClosed()));
			}		
		}catch (SQLException e) 
		{
			logger.severe("An error occured getting the status of the connection");
			Authorization_Details_Controller.addError(e.toString());
			logger.severe(e.toString());
		}
		catch (CustomizeHandledException e1) {
			Authorization_Details_Controller.addError(e1.toString());
			logger.severe(e1.toString());
		} catch (Exception e) {
			Authorization_Details_Controller.addError(e.toString());
			logger.severe(e.toString());
		}
		
	}
	
	public Connection getCon() {
		return con;
	}
	
	public String insertEmployeeLicense(EmployeeLicense e) {
		executed = "OK";
		
		if(e != null && checkMinValue(e)) {
			if(getEmployee(e.getStaffNumber()) == null) {
				return executed;
			}
			setEmployeeControl(e);
			setEmployeeSkillLicense(e);
			
			if(e.getThirdParty() != null || !e.getThirdParty().isEmpty()) {
				for(ThirdParty tp: e.getThirdParty()) {
					
					setEmployeeAuthorization("DEFAULT",tp.getCustomerAssignedNumber());
					setEmployeeAuthPreReq(tp.getCustomerAssignedNumber());
					setEmployeeAuthorizationApv(e,tp.getCustomerAssignedNumber(),tp.getCustomerIdentifier(),tp.getActiveCustIDIndcator());
				}
			}
		} else {
			executed = "Can not insert/update Employee: " + e.getStaffNumber() + " as Error: inout is null or does not have minimun values";
			logger.severe(executed);
			Authorization_Details_Controller.addError(executed);
		}
		
		return executed;
		
	}
	
	private void setEmployeeSkillLicense(EmployeeLicense e) {
		List<EmployeeSkill> employeeSkills = null;
		
		try {
			employeeSkills = em.createQuery("SELECT e FROM EmployeeSkill e WHERE e.id.employee =: em ")
					.setParameter("em", e.getStaffNumber())
					.getResultList();
		}catch(Exception e1){
			logger.info("Employee: " +e.getStaffNumber() + " Does not contain any skills" );
			return;
		}
		if(employeeSkills == null && !employeeSkills.isEmpty()) {
			return;
		}
		
		for(EmployeeSkill employeeSkill : employeeSkills) {
			if(employeeSkill.getId().getSkill().contains("AH") || 
					employeeSkill.getId().getSkill().contains("LAE")) {
				DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
				
				employeeSkill.setModifiedBy("TRAX_IFACE");
				employeeSkill.setModifiedDate(new Date());
				
				employeeSkill.setLicense(e.getAuthorizationNumber());
				try {
					employeeSkill.setExpirationDate(format.parse(e.getAuthorizationExpiryDate()));
					employeeSkill.setExpirationOptional("YES");
				}catch (ParseException e1) {
					
				}
				logger.info("UPDATING SKILL Skill: " + employeeSkill.getId().getSkill() + " Employee: " + employeeSkill.getId().getEmployee() );
				insertData(employeeSkill);
			}
		}
	}
	
	private EmployeeAuthorization setEmployeeAuthorization( String cat, String code ) {
		EmployeeAuthorization employeeAuthorization = null;
		
		try {
			employeeAuthorization = em.createQuery("SELECT e FROM EmployeeAuthorization e WHERE e.id.authorizationCode = :auth AND  e.id.authorizationCategory = :cat", EmployeeAuthorization.class)
					.setParameter("auth", code)
					.setParameter("cat", cat)
					.getSingleResult();
		}
		catch(Exception ex)
		{
			employeeAuthorization = new EmployeeAuthorization();
			employeeAuthorization.setCreatedDate(new Date());
			employeeAuthorization.setCreatedBy("TRAX_IFACE");
			employeeAuthorization.setStatus("ACTIVE");
			employeeAuthorization.setMinimumAge(new BigDecimal(0));
		}
		employeeAuthorization.setModifiedBy("TRAX_IFACE");
		employeeAuthorization.setModifiedDate(new Date());
		employeeAuthorization.setAuthorizationCode(code);
		//employeeAuthorization.setStatus(status);
		
		
		insertDefaultVendorCategory(cat.toUpperCase());
		employeeAuthorization.setAuthorizationCategory(cat.toUpperCase());
		
		logger.info("INSERTING EMPLOYEE AUTHORIZATION AuthorizationCode: " + employeeAuthorization.getAuthorizationCode() + " AuthorizationCategory: " + cat.toUpperCase());
		insertData(employeeAuthorization);
		
		return employeeAuthorization;
		
	}
	
	private EmployeeAuthorizationApv setEmployeeAuthorizationApv(EmployeeLicense e, String code, String customer, String status ) {
		EmployeeAuthorizationApv employeeAuthorizationApv = null;
		boolean insert = true;
		
		customer = customer.toUpperCase();
		
		String cus =  getCustomer(customer);
		
		try {
			employeeAuthorizationApv = em.createQuery("SELECT e FROM EmployeeAuthorizationApv e WHERE e.id.authorizationCode = :auth AND  e.id.employee = :em AND e.id.customer = :cus AND e.id.company = :com", EmployeeAuthorizationApv.class)
					.setParameter("auth", code)
					.setParameter("em",e.getStaffNumber())
					.setParameter("cus",cus)
					.setParameter("com","SIAEC")
					.getSingleResult();
		}catch (Exception ex) {
			logger.info(ex.toString());
			EmployeeAuthorizationApvPK Pk = new EmployeeAuthorizationApvPK();
			employeeAuthorizationApv = new EmployeeAuthorizationApv();
			employeeAuthorizationApv.setId(Pk);
			
			employeeAuthorizationApv.setCreatedDate(new Date());
			employeeAuthorizationApv.setCreatedBy("TRAX_IFACE");
			employeeAuthorizationApv.setDateIssued(new Date());
			employeeAuthorizationApv.setStatus("LEVEL3");
			employeeAuthorizationApv.setFirstLevelApprover("TRAX_IFACE");
			employeeAuthorizationApv.setFirstLevelDate(new Date());
			employeeAuthorizationApv.setFirstLevelStatus("ACCEPT");
			employeeAuthorizationApv.setSecondLevelApprover("TRAX_IFACE");
			employeeAuthorizationApv.setSecondLevelStatus("ACCEPT");
		}
		employeeAuthorizationApv.setModifiedBy("TRAX_IFACE");
		employeeAuthorizationApv.setModifiedDate(new Date());
		employeeAuthorizationApv.getId().setEmployee(e.getStaffNumber());
		employeeAuthorizationApv.getId().setAuthorizationCode(code);
		employeeAuthorizationApv.getId().setCompany("SIAEC");
		if(getCustomer(customer) != null) {
			employeeAuthorizationApv.getId().setCustomer(getCustomer(customer));
		} else {
			employeeAuthorizationApv.getId().setCustomer("          ");
		}
		
		if(status != null && !status.isEmpty() && status.equalsIgnoreCase("NO") && getCustomer(customer) == null){
			insert = false;
		}
		
		
		if(status != null && !status.isEmpty() && status.equalsIgnoreCase("NO") && getCustomer(customer) != null){
			logger.info("DELETE EMPLOYEE AUTHORIZATION APV: " + employeeAuthorizationApv.getId().getAuthorizationCode() + " Employee: " +employeeAuthorizationApv.getId().getEmployee()
					+" Customer: "+ employeeAuthorizationApv.getId().getCustomer() + " Company: " + employeeAuthorizationApv.getId().getCompany());
			deleteData(employeeAuthorizationApv);
			return employeeAuthorizationApv;
		}
		
		
		if(status != null && !status.isEmpty() && status.equalsIgnoreCase("YES")) {
			
		}
		
		
		if(insert) {
			logger.info("INSERTING EMPLOYEE AUTHORIZATION APV AuthorizationCode: " + employeeAuthorizationApv.getId().getAuthorizationCode() + " Employee: " +employeeAuthorizationApv.getId().getEmployee()
					+" Customer: "+ employeeAuthorizationApv.getId().getCustomer() + " Company: " + employeeAuthorizationApv.getId().getCompany());
			insertData(employeeAuthorizationApv);
		}
		
		return employeeAuthorizationApv;
	}
	
	private EmployeeControl setEmployeeControl(EmployeeLicense e) {
		
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
		EmployeeControl license = null;
		boolean expire = false;
		
		try 
		{
			license = em.createQuery("SELECT e FROM EmployeeControl e where e.id.employee = :em and e.id.employeeControl = :tr and e.reference = :ref", EmployeeControl.class)
					.setParameter("em", e.getStaffNumber())
					.setParameter("ref", e.getAuthorizationNumber())
					.setParameter("tr", "LICENCE")
					.getSingleResult();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			license = new EmployeeControl();
			EmployeeControlPK employeepk = new EmployeeControlPK();
			license.setId(employeepk);
			license.setCreatedDate(new Date());
			license.setCreatedBy("TRAX_IFACE");
			
			license.getId().setEmployee(e.getStaffNumber());
			
			//EMRO fields to create basic object
			license.getId().setEmployeeControl("LICENCE");
			license.getId().setControlItem(getLine(e.getStaffNumber(), "CONTROL_ITEM", "Employee_Control", "EMPLOYEE"));
			license.setDateIssued(new Date());
			license.setExpirationOptional("Y");
			
						
		}
		license.setReference(e.getAuthorizationNumber());
		license.getId().setEmployee(e.getStaffNumber());
		license.setIssuedAuthority("CAAS");
		license.setLicenceType("Part-66");
		 
		
		if(Inactive.contains(e.getAuthorizationStatus())) {
			license.setStatus("INACTIVE");
			expire = true;
		}else {
			license.setStatus("ACTIVE");
		}
		try {
			license.setExpireDate(format.parse(e.getAuthorizationExpiryDate()));
		} catch (ParseException e1) {
			
		}
		
		if(license.getExpireDate().before(new Date())) {
			expire = true;
		}
		
		
		if(expire) {
			logger.warning("WARNING Employee License is expired: " + license.getReference() + " Expire Date: " + license.getExpireDate() + " Status:" + license.getStatus());
			removeStampSign(e);
		}
		
		
		license.setModifiedBy("TRAX_IFACE");
		license.setModifiedDate(new Date());
		
		logger.info("INSERTING EMPLOYEE CONTROL Reference: " + license.getReference() + " Employee:" + license.getId().getEmployee()+  " ITEM: " + license.getId().getControlItem());
		insertData(license);
		return license;
	}
	
private void removeStampSign(EmployeeLicense e) {
		
		List<BlobTable> blobs = null;
		try
		{
			BigDecimal blobNo = getEmployeeBlob(e.getStaffNumber());
			blobs = em.createQuery("SELECT b FROM BlobTable b where b.id.blobNo = :blo")
					.setParameter("blo", blobNo.longValue())
					.getResultList();
			
			for(BlobTable b : blobs) {
				if(b.getBlobDescription().equalsIgnoreCase("STAMP.png") || b.getBlobDescription().equalsIgnoreCase("ESIGN.png")) {
					logger.info("DELETE blob: " + b.getId().getBlobNo() + " Line: " + b.getId().getBlobLine() + " Description:" +b.getBlobDescription() );
					deleteData(b);
				}
			}
			
		
		}catch (Exception ex)
		{
			
			logger.info("No Stamp or Sign found for Employee:" + e.getStaffNumber());
		}
	}

private String getEmployee(String e) {
	try
	{
		RelationMaster employee = em.createQuery("SELECT r FROM RelationMaster r where r.id.relationCode = :em and r.id.relationTransaction = :tr", RelationMaster.class)
				.setParameter("em", e)
				.setParameter("tr", "EMPLOYEE")
				.getSingleResult();
		
		return employee.getId().getRelationCode();
	}
	catch (Exception ex)
	{
		
	}
	return null;
}

private BigDecimal getEmployeeBlob(String e) {
	try
	{
		RelationMaster employee = em.createQuery("SELECT r FROM RelationMaster r where r.id.relationCode = :em and r.id.relationTransaction = :tr", RelationMaster.class)
				.setParameter("em", e)
				.setParameter("tr", "EMPLOYEE")
				.getSingleResult();
		
		return employee.getBlobNo();
	}
	catch (Exception ex)
	{
		
	}
	return null;
}

private String getCustomer(String customer) {
	try
	{
		RelationMaster relationMaster = em.createQuery("SELECT r FROM RelationMaster r where r.id.relationCode = :na and r.id.relationTransaction = :tr", RelationMaster.class)
		.setParameter("na", customer)
		.setParameter("tr", "CUSTOMER")
		.getSingleResult();
		
		
		return relationMaster.getId().getRelationCode();
	}
	catch (Exception e)
	{
	
	}
	return null;
}

private EmployeeAuthPreReq setEmployeeAuthPreReq(String code) {
	
	EmployeeAuthPreReq employeeAuthPreReq = null;
	
	try
	{
		employeeAuthPreReq = em.createQuery("SELECT e FROM EmployeeAuthPreReq e WHERE e.id.authorizationCode = :co AND  e.id.transactionType = :tr", EmployeeAuthPreReq.class)
		.setParameter("co", code)
		.setParameter("tr", "LICENSE")
		.getSingleResult();
	}
	catch (Exception e)
	{
		employeeAuthPreReq = new EmployeeAuthPreReq();
		EmployeeAuthPreReqPK pk = new EmployeeAuthPreReqPK();
		employeeAuthPreReq.setId(pk);
		employeeAuthPreReq.setCreatedDate(new Date());
		employeeAuthPreReq.setCreatedBy("TRAX_IFACE");
		employeeAuthPreReq.getId().setItem(getLine(code, "AUTHORIZATION_CODE", "employee_Auth_Pre_Req", "ITEM"));
		employeeAuthPreReq.getId().setTransactionType("LICENSE");;
		
	}
	employeeAuthPreReq.getId().setAuthorizationCode(code);
	employeeAuthPreReq.setTransactionCode("Part-66");
	employeeAuthPreReq.setModifiedBy("TRAX_IFACE");
	employeeAuthPreReq.setModifiedDate(new Date());
	
	logger.info("INSERTING EMPLOYEE AUTH PRE REQ AuthorizationCode: " + employeeAuthPreReq.getId().getAuthorizationCode() + "ITEM: " + employeeAuthPreReq.getId().getItem());
	insertData(employeeAuthPreReq);
	
	return employeeAuthPreReq;
}


//insert generic data from model objects
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
		executed = "insertData has encountered an Exception: "+e.getMessage();
		Authorization_Details_Controller.addError(executed);
		logger.severe(e.toString());
	}
}

private boolean checkMinValue( EmployeeLicense e) {
	
	if( e.getStaffNumber() == null || e.getStaffNumber().isEmpty()) {
		Authorization_Details_Controller.addError("Can not insert/update Employee: "+ e.getStaffNumber() +" as ERROR StaffNumber");
		return false;
	}
		
	if( e.getAuthorizationNumber() == null || e.getAuthorizationNumber().isEmpty()) {
		Authorization_Details_Controller.addError("Can not insert/update Employee: "+ e.getStaffNumber() +" as ERROR AuthorizationNumber");
		return false;
	}
		
	if( e.getAuthorizationExpiryDate() == null || e.getAuthorizationExpiryDate().isEmpty()) {
		Authorization_Details_Controller.addError("Can not insert/update Employee: "+ e.getStaffNumber() +" as ERROR AuthorizationExpiryDate");
		return false;
	}
	if( e.getAuthorizationStatus() == null || e.getAuthorizationStatus().isEmpty() ) {
		Authorization_Details_Controller.addError("Can not insert/update Employee: "+ e.getStaffNumber() +" as ERROR AuthorisationStatusOfPersonnel");
		
		return false;
	}
	return true;
}
	


//delete generic data from model objects
private <T> void deleteData( T data) 
{
	try 
	{	
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
			em.remove(data);
			em.getTransaction().commit();
	}catch (Exception e)
	{
		executed = "insertData has encountered an Exception: "+e.toString();
		Authorization_Details_Controller.addError(executed);
		logger.severe(e.toString());
		}
}

void insertDefaultVendorCategory(String cat){
	SystemTranCode category = null;
	try {
		 category = (SystemTranCode) em.createQuery("select s from SystemTranCode s where s.id.systemTransaction = :tran and s.id.systemCode = :code")
				.setParameter("tran", "EMAUTCAT")
				.setParameter("code",  cat)
				.getSingleResult();
		 
		 return;
	}catch(Exception e) {
		category = new SystemTranCode();
		SystemTranCodePK pk = new SystemTranCodePK();
		category.setId(pk);
		category.setCreatedBy("TRAX_IFACE");
		category.setCreatedDate(new Date());
		category.setModifiedBy("TRAX_IFACE");
		category.setModifiedDate(new Date());
		
		category.getId().setSystemCode(cat);
		category.getId().setSystemTransaction("EMAUTCAT");
		category.getId().setSystemTranCodeSub("          ");
		category.setPnTransaction("C");
		category.setPnCostingMethod("A");
		category.setConfigFlag("N");
		category.setTagType("FORMONE");
		category.setTemporaryRevision("NO");
		category.setChapterMandatory("NO");
		category.setSectionMandatory("NO");
		category.setDeferCatAutoMddrClosing("YES");
		category.setDefectReportPilot("YES");
		category.setVendorStatus("ACTIVE");
		category.setStatus("ACTIVE");
		category.setPnCategoryInventoryType("MAINTENANCE");
		category.setDoNotAllowReset("NO");
		category.setAutoResetOnRo("NO");
		category.setDoNotAllowResetProd("NO");
		category.setAcRestriction("P");
		category.setRadiocative("N");
		category.setCodeColor("8421376");
		
		category.setConfigNumber(new BigDecimal(0));
		category.setCurrencyExchange(new BigDecimal(0));
		
		category.setMelCycles(new BigDecimal(0));
		category.setMelHours(new BigDecimal(0));
		category.setRevenueFlight("NO");
		category.setEtops("NO");
		
		
		
		category.setAlertNoOfRemoval(new BigDecimal(0));
		category.setAlertCategoryNoOf(new BigDecimal(0));
		
		category.setAcMandatory("NO");
		category.setEquipmentRefDesignator("N");
		category.setRosClassificationCode("O");
		category.setIfrs("NO");
		category.setSupplier("NO");
		category.setSupplierMaintenance("NO");
		category.setSupplierGeneral("NO");
		category.setSupplierGse("NO");
		category.setRepair("NO");
		category.setRepairMaintenance("NO");
		category.setRepairGeneral("NO");
		category.setRepairGse("NO");
		category.setFreightForwarder("N");
		category.setService("NO");
		category.setAdCategory("N");
		category.setEcNameOverride("YES");
		category.setVbLifeLimit("NO");
		category.setVopBaseOnCondition(new BigDecimal(0));
		category.setCabin("N");
		category.setHighDollar("N");
		category.setBinCountFrequency("YEAR");
		category.setBinCountNoWeekend("NO");
		category.setFollowGrb("NO");
		category.setLoadAtRecv("N");
		category.setAllowBust("NO");
		category.setSwoRequisition("N");
		
		category.setOpsRestriction("NO");
		category.setPaperRequired("YES");
		category.setImage("bmp-ghs-01-bmp");
		category.setScrapReorder("YES");
		category.setEssentialityCodeLevel("NOGO");
		category.setMonthDepreciation(new BigDecimal(0));
		category.setThirdPartyWo("N");
		
		logger.info("INSERTING CATEGORY: " + cat);
		insertData(category);
	}
}	

private long getLine(String no, String table_line, String table, String table_no)
{		
	long line = 0;
	String query = " SELECT  MAX("+table_line+") FROM "+table+" WHERE "+table_no+" = ?";
	
	PreparedStatement ps = null;
		
	
	try
	{
		if(con == null || con.isClosed())
		{
			con = DataSourceClient.getConnection();
			logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
		}
		
		ps = con.prepareStatement(query);
		
		ps.setString(1, no);
		
		
		ResultSet rs = ps.executeQuery();		
		
		if (rs != null) 
		{
			while (rs.next()) 
			{
				line = rs.getLong(1);
			}
		}
		rs.close();
		
		line++;
	}
	catch (Exception e) 
	{
		line = 1;
	}
	finally 
	{
		try 
		{
			if(ps != null && !ps.isClosed())
				ps.close();
		} 
		catch (SQLException e) 
		{ 
			logger.severe("An error ocurrer trying to close the statement");
		}
	}
	
	
	
	return line;
}

private <T> void insertData( T data, String s) 
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
			insertData(lock,"");
			return true;
		}
		return false;
	}
	else
	{
		lock.setLocked(new BigDecimal(1));
		insertData(lock,"");
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
