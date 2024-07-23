package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RELATION_MASTER database table.
 * 
 */
@Entity
@Table(name="RELATION_MASTER")
@NamedQuery(name="RelationMaster.findAll", query="SELECT r FROM RelationMaster r")
public class RelationMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RelationMasterPK id;

	@Column(name="ALLOW_ASSIGN_TO")
	private String allowAssignTo;

	@Column(name="ALLOW_ISSUE_TO")
	private String allowIssueTo;

	@Column(name="APPROVAL_DESCRIPTION")
	private String approvalDescription;

	@Column(name="APPROVAL_NO")
	private String approvalNo;

	@Column(name="APPROVED_01")
	private String approved01;

	@Column(name="APPROVED_02")
	private String approved02;

	@Column(name="APPROVED_03")
	private String approved03;

	@Column(name="APPROVED_04")
	private String approved04;

	@Column(name="APPROVED_05")
	private String approved05;

	@Column(name="AUTO_EMAIL_RO")
	private String autoEmailRo;

	@Column(name="AWB_FIELD_LENGTH")
	private BigDecimal awbFieldLength;

	@Column(name="AWB_WEB_SITE")
	private String awbWebSite;

	@Column(name="B2B_ID")
	private String b2bId;

	@Column(name="BADGE_CREATED_BY")
	private String badgeCreatedBy;

	@Column(name="BADGE_CREATED_DATE")
	private Date badgeCreatedDate;

	@Column(name="BADGE_ID")
	private String badgeId;

	@Column(name="BADGE_MODIFIED_BY")
	private String badgeModifiedBy;

	@Column(name="BADGE_MODIFIED_DATE")
	private Date badgeModifiedDate;

	@Column(name="BARCODE_ID")
	private String barcodeId;

	@Lob
	@Column(name="BLOB_ICON")
	private byte[] blobIcon;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CAGE_CODE")
	private String cageCode;

	private String calibrated;

	@Column(name="CASE_NUMBER")
	private String caseNumber;

	private String cass;

	@Column(name="CASS_FLAG")
	private String cassFlag;

	@Column(name="CERTIFYING_STAFF")
	private String certifyingStaff;

	@Column(name="CERTIFYING_STAFF_EXPIRATION")
	private Date certifyingStaffExpiration;

	private BigDecimal contact;

	@Column(name="CONTRACT_NUMBER")
	private BigDecimal contractNumber;

	@Column(name="COST_CENTER")
	private String costCenter;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CREDIT_LIMIT")
	private BigDecimal creditLimit;

	private String currency;

	@Column(name="CUST_CONTACT")
	private String custContact;

	@Column(name="CUSTOMER_LOGO")
	private String customerLogo;

	@Column(name="DATE_HIRED")
	private Date dateHired;

	@Column(name="DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Column(name="DATE_TERMINATED")
	private Date dateTerminated;

	private String department;

	private String dispatcher;

	private String division;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	@Column(name="DRUG_TESTED_LAST_TESTED_DATE")
	private Date drugTestedLastTestedDate;

	@Column(name="DRUG_TESTING")
	private String drugTesting;

	@Column(name="DRUG_TESTING_MAX_DAYS")
	private BigDecimal drugTestingMaxDays;

	@Column(name="DRUG_TESTING_STATUS")
	private String drugTestingStatus;

	private String earliest;

	@Column(name="EMP_VENDOR")
	private String empVendor;

	@Column(name="EMPLOYEE_CONTRACTOR")
	private String employeeContractor;

	@Column(name="EMPLOYEE_ID")
	private String employeeId;

	@Column(name="EVALUATION_DATE2")
	private Date evaluationDate2;

	@Column(name="EVALUATION_DAY")
	private BigDecimal evaluationDay;

	@Column(name="EVALUATION_DAY2")
	private BigDecimal evaluationDay2;

	@Column(name="EVALUCATION_CONTACT")
	private String evalucationContact;

	@Column(name="EVALUTION_DATE")
	private Date evalutionDate;

	@Column(name="EVALUTION_NO")
	private String evalutionNo;

	@Column(name="EX_DEFAULT_LEAD_DAYS")
	private BigDecimal exDefaultLeadDays;

	@Column(name="EXTERNAL_COMPANY")
	private String externalCompany;

	@Column(name="EXTERNAL_EMPLOYEE")
	private String externalEmployee;

	@Column(name="EXTERNAL_RECEIVING_VENDOR")
	private String externalReceivingVendor;

	@Column(name="FINANCIAL_SYSTEM_COUNTRY")
	private String financialSystemCountry;

	@Column(name="FINANCIAL_SYSTEM_SITE_CODE")
	private String financialSystemSiteCode;

	@Column(name="FINANCIAL_SYSTEM_VENDOR")
	private String financialSystemVendor;

	@Column(name="FIR_DATA")
	private String firData;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="FIRST_TIME_WALL_CONTROL")
	private String firstTimeWallControl;

	@Column(name="FIX_LOAN_COST_PERCENT")
	private BigDecimal fixLoanCostPercent;

	private String fob;

	@Column(name="FORCE_PIN_CHANGE")
	private String forcePinChange;

	@Column(name="FREIGHT_FORWARDER")
	private String freightForwarder;

	@Column(name="GL_CUSTOMER")
	private String glCustomer;

	private String gst;

	@Column(name="GST_GL")
	private String gstGl;

	@Column(name="GST_GL_COMPANY")
	private String gstGlCompany;

	@Column(name="GST_GL_COST_CENTER")
	private String gstGlCostCenter;

	@Column(name="GST_GL_EXPENDITURE")
	private String gstGlExpenditure;

	@Column(name="GST_TAX_PERCENT")
	private BigDecimal gstTaxPercent;

	@Column(name="IATA_ADDRESS")
	private String iataAddress;

	@Column(name="IFACE_BAXTER_SUP_TRANSFER_DATE")
	private Date ifaceBaxterSupTransferDate;

	@Column(name="INTERFACE_TRANSFER_BY")
	private String interfaceTransferBy;

	@Column(name="INTERFACE_TRANSFER_DATE")
	private Date interfaceTransferDate;

	@Column(name="INVOICEWORKS_CAPABLE")
	private String invoiceworksCapable;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="LDAP_USERNAME")
	private String ldapUsername;

	@Column(name="LETTER_CODE_3")
	private String letterCode3;

	@Column(name="LETTER_CODE_4")
	private String letterCode4;

	@Column(name="LHT_VENDOR")
	private String lhtVendor;

	@Column(name="LO_DEFAULT_LEAD_DAYS")
	private BigDecimal loDefaultLeadDays;

	@Column(name="LOAN_FACTOR_1_DAYS")
	private BigDecimal loanFactor1Days;

	@Column(name="LOAN_FACTOR_1_PERCENT")
	private BigDecimal loanFactor1Percent;

	@Column(name="LOAN_FACTOR_2_DAYS")
	private BigDecimal loanFactor2Days;

	@Column(name="LOAN_FACTOR_2_PERCENT")
	private BigDecimal loanFactor2Percent;

	@Column(name="LOAN_FACTOR_3_PERCENT")
	private BigDecimal loanFactor3Percent;

	@Column(name="LOAN_INITIAL_FEE")
	private BigDecimal loanInitialFee;

	@Column(name="MAIL_ADDRESS_1")
	private String mailAddress1;

	@Column(name="MAIL_ADDRESS_2")
	private String mailAddress2;

	@Column(name="MAIL_CELL")
	private String mailCell;

	@Column(name="MAIL_CITY")
	private String mailCity;

	@Column(name="MAIL_CONTACT")
	private String mailContact;

	@Column(name="MAIL_COUNTRY")
	private String mailCountry;

	@Column(name="MAIL_EMAIL")
	private String mailEmail;

	@Column(name="MAIL_FAX")
	private String mailFax;

	@Column(name="MAIL_PHONE")
	private String mailPhone;

	@Column(name="MAIL_POST")
	private String mailPost;

	@Column(name="MAIL_SITA")
	private String mailSita;

	@Column(name="MAIL_STATE")
	private String mailState;

	private String manufacture;

	@Column(name="MECHANIC_STAMP")
	private String mechanicStamp;

	@Column(name="MECHANIC_STAMP_2")
	private String mechanicStamp2;

	@Column(name="MECHANIC_STAMP_3")
	private String mechanicStamp3;

	@Column(name="MECHANIC_STAMP_4")
	private String mechanicStamp4;

	@Column(name="MECHANIC_STAMP_5")
	private String mechanicStamp5;

	@Column(name="MFA_QR")
	private String mfaQr;

	@Column(name="MIDDLE_NAME")
	private String middleName;

	@Column(name="MILITARY_FLAG")
	private String militaryFlag;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private String name;

	private BigDecimal notes;

	@Column(name="ON_CALL_MX")
	private String onCallMx;

	@Column(name="OS_CAPABLE")
	private String osCapable;

	@Column(name="OTHER_DESCRIPTION")
	private String otherDescription;

	@Column(name="OTHER_PROG")
	private String otherProg;

	private String oxygen;

	@Column(name="PERCENT_MARKUP")
	private BigDecimal percentMarkup;

	@Column(name="PERCENT_MARKUP_XO")
	private BigDecimal percentMarkupXo;

	@Column(name="PHOTO_BLOB_NO")
	private BigDecimal photoBlobNo;

	private String pin;

	@Column(name="PLACE_OF_BIRTH")
	private String placeOfBirth;

	@Column(name="PO_DEFAULT_LEAD_DAYS")
	private BigDecimal poDefaultLeadDays;

	@Column(name="PO_DISCOUNT")
	private BigDecimal poDiscount;

	@Column(name="\"POSITION\"")
	private String position;

	@Column(name="QA_AUTHORIZER")
	private String qaAuthorizer;

	@Column(name="RELATED_LOCATION")
	private String relatedLocation;

	@Column(name="RELATION_ASSOCIATION")
	private String relationAssociation;

	@Column(name="RELATION_CATEGORY")
	private String relationCategory;

	private String repair;

	@Column(name="REPAIR_GENERAL")
	private String repairGeneral;

	@Column(name="REPAIR_GSE")
	private String repairGse;

	@Column(name="REPAIR_MAINTENANCE")
	private String repairMaintenance;

	@Column(name="REPAIR_STATION_NUMBER")
	private String repairStationNumber;

	@Column(name="RFID_PANEL_CONFIG")
	private String rfidPanelConfig;

	private String rii;

	@Column(name="RO_DEFAULT_LEAD_DAYS")
	private BigDecimal roDefaultLeadDays;

	@Column(name="ROS_ACTION_CODE")
	private String rosActionCode;

	@Column(name="ROS_AUTO_MESSAGE")
	private String rosAutoMessage;

	//@Column(name="ROS_CONFIG")
	//private Date rosConfig;

	@Column(name="ROS_FILENAME")
	private String rosFilename;

	@Column(name="ROS_OVERRIDE")
	private String rosOverride;

	@Column(name="ROS_SHIPMENT_CARRIER")
	private String rosShipmentCarrier;

	@Column(name="RQRS_FRGN_SHIP_DECL")
	private String rqrsFrgnShipDecl;

	@Column(name="RQRS_FRGN_SHIP_DECL_NOTES")
	private BigDecimal rqrsFrgnShipDeclNotes;

	@Column(name="RTV_REFERENCE")
	private String rtvReference;

	@Column(name="SCHEDULE_AC_DAYS")
	private BigDecimal scheduleAcDays;

	@Column(name="SCHEDULE_AC_DAYS_REPAIR")
	private BigDecimal scheduleAcDaysRepair;

	@Column(name="\"SECTION\"")
	private String section;

	@Column(name="SEND_VIA")
	private String sendVia;

	@Column(name="SENIORITY_DATE")
	private Date seniorityDate;

	@Column(name="\"SERVICE\"")
	private String service;

	@Column(name="SESSION_DOMAIN")
	private String sessionDomain;

	@Column(name="SESSION_GROUP")
	private String sessionGroup;

	@Column(name="SESSION_USERNAME")
	private String sessionUsername;

	@Column(name="SHIP_DAYS")
	private BigDecimal shipDays;

	@Column(name="SHIP_VIA")
	private String shipVia;

	@Column(name="SHIP_VIA_ACCOUNT")
	private String shipViaAccount;

	@Column(name="SHIP_VIA_ACCOUNT_EXPORT")
	private String shipViaAccountExport;

	@Column(name="SHIP_VIA_EXPORT")
	private String shipViaExport;

	@Column(name="SHIP_VIA_REMARKS")
	private String shipViaRemarks;

	@Column(name="SHIP_VIA_REMARKS_EXPORT")
	private String shipViaRemarksExport;

	@Column(name="SHIPPING_ADDRESS1")
	private String shippingAddress1;

	@Column(name="SHIPPING_ADDRESS2")
	private String shippingAddress2;

	@Column(name="SHIPPING_CELL")
	private String shippingCell;

	@Column(name="SHIPPING_CITY")
	private String shippingCity;

	@Column(name="SHIPPING_COUNTRY")
	private String shippingCountry;

	@Column(name="SHIPPING_EMAIL")
	private String shippingEmail;

	@Column(name="SHIPPING_FAX")
	private String shippingFax;

	@Column(name="SHIPPING_MAIN_CONTACT")
	private String shippingMainContact;

	@Column(name="SHIPPING_PHONE")
	private String shippingPhone;

	@Column(name="SHIPPING_POST")
	private String shippingPost;

	@Column(name="SHIPPING_SITA")
	private String shippingSita;

	@Column(name="SHIPPING_STATE")
	private String shippingState;

	@Column(name="SHIPPING_TERMS")
	private String shippingTerms;

	@Column(name="SHOP_OWNERSHIP_CONTROL")
	private String shopOwnershipControl;

	private String site;

	@Column(name="SKILL_CATEGORY")
	private String skillCategory;

	@Column(name="SPEC2000_OTHER")
	private String spec2000Other;

	@Column(name="SPEC2000_OVERRIDE")
	private String spec2000Override;

	@Column(name="SPEC2000_PASSWORD")
	private String spec2000Password;

	@Column(name="SPEC2000_TRAXDOC_NO")
	private BigDecimal spec2000TraxdocNo;

	@Column(name="SPEC2000_URL")
	private String spec2000Url;

	@Column(name="SPEC2000_USERNAME")
	private String spec2000Username;

	@Column(name="SPEC2K_ROS_TRANSMISSION_CODE")
	private String spec2kRosTransmissionCode;

	private String status;

	private String supplier;

	@Column(name="SUPPLIER_GENERAL")
	private String supplierGeneral;

	@Column(name="SUPPLIER_GSE")
	private String supplierGse;

	@Column(name="SUPPLIER_MAINTENANCE")
	private String supplierMaintenance;

	@Column(name="SV_DISCOUNT")
	private BigDecimal svDiscount;

	@Lob
	@Column(name="SYS_NC00160$")
	private String sysNc00160$;

	@Column(name="TAX_OFFICE_NAME")
	private String taxOfficeName;

	@Column(name="TAX_OFFICE_NUMBER")
	private String taxOfficeNumber;

	@Column(name="TECH_PUB_CONTACT")
	private String techPubContact;

	@Column(name="TECH_PUB_MAIL_ADDRESS_1")
	private String techPubMailAddress1;

	@Column(name="TECH_PUB_MAIL_ADDRESS_2")
	private String techPubMailAddress2;

	@Column(name="TECH_PUB_MAIL_CELL")
	private String techPubMailCell;

	@Column(name="TECH_PUB_MAIL_CITY")
	private String techPubMailCity;

	@Column(name="TECH_PUB_MAIL_COUNTRY")
	private String techPubMailCountry;

	@Column(name="TECH_PUB_MAIL_EMAIL")
	private String techPubMailEmail;

	@Column(name="TECH_PUB_MAIL_FAX")
	private String techPubMailFax;

	@Column(name="TECH_PUB_MAIL_PHONE")
	private String techPubMailPhone;

	@Column(name="TECH_PUB_MAIL_POST")
	private String techPubMailPost;

	@Column(name="TECH_PUB_MAIL_STATE")
	private String techPubMailState;

	private String terms;

	@Column(name="TRAINING_INSTRUCTOR")
	private String trainingInstructor;

	@Column(name="TRAXDOC_CREATED_DATE")
	private Date traxdocCreatedDate;

	@Column(name="TRAXDOC_FILE_NAME_PATH")
	private String traxdocFileNamePath;

	@Column(name="TRAXDOC_ROW_ID")
	private BigDecimal traxdocRowId;

	private String vat;

	@Column(name="VENDOR_CONTRACT")
	private String vendorContract;

	@Column(name="WARRANTY_CYCLES")
	private BigDecimal warrantyCycles;

	@Column(name="WARRANTY_CYCLES_REPAIR")
	private BigDecimal warrantyCyclesRepair;

	@Column(name="WARRANTY_DAYS")
	private BigDecimal warrantyDays;

	@Column(name="WARRANTY_DAYS_REPAIR")
	private BigDecimal warrantyDaysRepair;

	@Column(name="WARRANTY_HOURS")
	private BigDecimal warrantyHours;

	@Column(name="WARRANTY_HOURS_REPAIR")
	private BigDecimal warrantyHoursRepair;

	@Column(name="WEB_SITE")
	private String webSite;

	private String weight;

	@Column(name="WO_TASK_CARD_FOOTER")
	private BigDecimal woTaskCardFooter;

	public RelationMaster() {
	}

	public RelationMasterPK getId() {
		return this.id;
	}

	public void setId(RelationMasterPK id) {
		this.id = id;
	}

	public String getAllowAssignTo() {
		return this.allowAssignTo;
	}

	public void setAllowAssignTo(String allowAssignTo) {
		this.allowAssignTo = allowAssignTo;
	}

	public String getAllowIssueTo() {
		return this.allowIssueTo;
	}

	public void setAllowIssueTo(String allowIssueTo) {
		this.allowIssueTo = allowIssueTo;
	}

	public String getApprovalDescription() {
		return this.approvalDescription;
	}

	public void setApprovalDescription(String approvalDescription) {
		this.approvalDescription = approvalDescription;
	}

	public String getApprovalNo() {
		return this.approvalNo;
	}

	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}

	public String getApproved01() {
		return this.approved01;
	}

	public void setApproved01(String approved01) {
		this.approved01 = approved01;
	}

	public String getApproved02() {
		return this.approved02;
	}

	public void setApproved02(String approved02) {
		this.approved02 = approved02;
	}

	public String getApproved03() {
		return this.approved03;
	}

	public void setApproved03(String approved03) {
		this.approved03 = approved03;
	}

	public String getApproved04() {
		return this.approved04;
	}

	public void setApproved04(String approved04) {
		this.approved04 = approved04;
	}

	public String getApproved05() {
		return this.approved05;
	}

	public void setApproved05(String approved05) {
		this.approved05 = approved05;
	}

	public String getAutoEmailRo() {
		return this.autoEmailRo;
	}

	public void setAutoEmailRo(String autoEmailRo) {
		this.autoEmailRo = autoEmailRo;
	}

	public BigDecimal getAwbFieldLength() {
		return this.awbFieldLength;
	}

	public void setAwbFieldLength(BigDecimal awbFieldLength) {
		this.awbFieldLength = awbFieldLength;
	}

	public String getAwbWebSite() {
		return this.awbWebSite;
	}

	public void setAwbWebSite(String awbWebSite) {
		this.awbWebSite = awbWebSite;
	}

	public String getB2bId() {
		return this.b2bId;
	}

	public void setB2bId(String b2bId) {
		this.b2bId = b2bId;
	}

	public String getBadgeCreatedBy() {
		return this.badgeCreatedBy;
	}

	public void setBadgeCreatedBy(String badgeCreatedBy) {
		this.badgeCreatedBy = badgeCreatedBy;
	}

	public Date getBadgeCreatedDate() {
		return this.badgeCreatedDate;
	}

	public void setBadgeCreatedDate(Date badgeCreatedDate) {
		this.badgeCreatedDate = badgeCreatedDate;
	}

	public String getBadgeId() {
		return this.badgeId;
	}

	public void setBadgeId(String badgeId) {
		this.badgeId = badgeId;
	}

	public String getBadgeModifiedBy() {
		return this.badgeModifiedBy;
	}

	public void setBadgeModifiedBy(String badgeModifiedBy) {
		this.badgeModifiedBy = badgeModifiedBy;
	}

	public Date getBadgeModifiedDate() {
		return this.badgeModifiedDate;
	}

	public void setBadgeModifiedDate(Date badgeModifiedDate) {
		this.badgeModifiedDate = badgeModifiedDate;
	}

	public String getBarcodeId() {
		return this.barcodeId;
	}

	public void setBarcodeId(String barcodeId) {
		this.barcodeId = barcodeId;
	}

	public byte[] getBlobIcon() {
		return this.blobIcon;
	}

	public void setBlobIcon(byte[] blobIcon) {
		this.blobIcon = blobIcon;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public String getCageCode() {
		return this.cageCode;
	}

	public void setCageCode(String cageCode) {
		this.cageCode = cageCode;
	}

	public String getCalibrated() {
		return this.calibrated;
	}

	public void setCalibrated(String calibrated) {
		this.calibrated = calibrated;
	}

	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getCass() {
		return this.cass;
	}

	public void setCass(String cass) {
		this.cass = cass;
	}

	public String getCassFlag() {
		return this.cassFlag;
	}

	public void setCassFlag(String cassFlag) {
		this.cassFlag = cassFlag;
	}

	public String getCertifyingStaff() {
		return this.certifyingStaff;
	}

	public void setCertifyingStaff(String certifyingStaff) {
		this.certifyingStaff = certifyingStaff;
	}

	public Date getCertifyingStaffExpiration() {
		return this.certifyingStaffExpiration;
	}

	public void setCertifyingStaffExpiration(Date certifyingStaffExpiration) {
		this.certifyingStaffExpiration = certifyingStaffExpiration;
	}

	public BigDecimal getContact() {
		return this.contact;
	}

	public void setContact(BigDecimal contact) {
		this.contact = contact;
	}

	public BigDecimal getContractNumber() {
		return this.contractNumber;
	}

	public void setContractNumber(BigDecimal contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getCreditLimit() {
		return this.creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustContact() {
		return this.custContact;
	}

	public void setCustContact(String custContact) {
		this.custContact = custContact;
	}

	public String getCustomerLogo() {
		return this.customerLogo;
	}

	public void setCustomerLogo(String customerLogo) {
		this.customerLogo = customerLogo;
	}

	public Date getDateHired() {
		return this.dateHired;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateTerminated() {
		return this.dateTerminated;
	}

	public void setDateTerminated(Date dateTerminated) {
		this.dateTerminated = dateTerminated;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDispatcher() {
		return this.dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public Date getDrugTestedLastTestedDate() {
		return this.drugTestedLastTestedDate;
	}

	public void setDrugTestedLastTestedDate(Date drugTestedLastTestedDate) {
		this.drugTestedLastTestedDate = drugTestedLastTestedDate;
	}

	public String getDrugTesting() {
		return this.drugTesting;
	}

	public void setDrugTesting(String drugTesting) {
		this.drugTesting = drugTesting;
	}

	public BigDecimal getDrugTestingMaxDays() {
		return this.drugTestingMaxDays;
	}

	public void setDrugTestingMaxDays(BigDecimal drugTestingMaxDays) {
		this.drugTestingMaxDays = drugTestingMaxDays;
	}

	public String getDrugTestingStatus() {
		return this.drugTestingStatus;
	}

	public void setDrugTestingStatus(String drugTestingStatus) {
		this.drugTestingStatus = drugTestingStatus;
	}

	public String getEarliest() {
		return this.earliest;
	}

	public void setEarliest(String earliest) {
		this.earliest = earliest;
	}

	public String getEmpVendor() {
		return this.empVendor;
	}

	public void setEmpVendor(String empVendor) {
		this.empVendor = empVendor;
	}

	public String getEmployeeContractor() {
		return this.employeeContractor;
	}

	public void setEmployeeContractor(String employeeContractor) {
		this.employeeContractor = employeeContractor;
	}

	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Date getEvaluationDate2() {
		return this.evaluationDate2;
	}

	public void setEvaluationDate2(Date evaluationDate2) {
		this.evaluationDate2 = evaluationDate2;
	}

	public BigDecimal getEvaluationDay() {
		return this.evaluationDay;
	}

	public void setEvaluationDay(BigDecimal evaluationDay) {
		this.evaluationDay = evaluationDay;
	}

	public BigDecimal getEvaluationDay2() {
		return this.evaluationDay2;
	}

	public void setEvaluationDay2(BigDecimal evaluationDay2) {
		this.evaluationDay2 = evaluationDay2;
	}

	public String getEvalucationContact() {
		return this.evalucationContact;
	}

	public void setEvalucationContact(String evalucationContact) {
		this.evalucationContact = evalucationContact;
	}

	public Date getEvalutionDate() {
		return this.evalutionDate;
	}

	public void setEvalutionDate(Date evalutionDate) {
		this.evalutionDate = evalutionDate;
	}

	public String getEvalutionNo() {
		return this.evalutionNo;
	}

	public void setEvalutionNo(String evalutionNo) {
		this.evalutionNo = evalutionNo;
	}

	public BigDecimal getExDefaultLeadDays() {
		return this.exDefaultLeadDays;
	}

	public void setExDefaultLeadDays(BigDecimal exDefaultLeadDays) {
		this.exDefaultLeadDays = exDefaultLeadDays;
	}

	public String getExternalCompany() {
		return this.externalCompany;
	}

	public void setExternalCompany(String externalCompany) {
		this.externalCompany = externalCompany;
	}

	public String getExternalEmployee() {
		return this.externalEmployee;
	}

	public void setExternalEmployee(String externalEmployee) {
		this.externalEmployee = externalEmployee;
	}

	public String getExternalReceivingVendor() {
		return this.externalReceivingVendor;
	}

	public void setExternalReceivingVendor(String externalReceivingVendor) {
		this.externalReceivingVendor = externalReceivingVendor;
	}

	public String getFinancialSystemCountry() {
		return this.financialSystemCountry;
	}

	public void setFinancialSystemCountry(String financialSystemCountry) {
		this.financialSystemCountry = financialSystemCountry;
	}

	public String getFinancialSystemSiteCode() {
		return this.financialSystemSiteCode;
	}

	public void setFinancialSystemSiteCode(String financialSystemSiteCode) {
		this.financialSystemSiteCode = financialSystemSiteCode;
	}

	public String getFinancialSystemVendor() {
		return this.financialSystemVendor;
	}

	public void setFinancialSystemVendor(String financialSystemVendor) {
		this.financialSystemVendor = financialSystemVendor;
	}

	public String getFirData() {
		return this.firData;
	}

	public void setFirData(String firData) {
		this.firData = firData;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstTimeWallControl() {
		return this.firstTimeWallControl;
	}

	public void setFirstTimeWallControl(String firstTimeWallControl) {
		this.firstTimeWallControl = firstTimeWallControl;
	}

	public BigDecimal getFixLoanCostPercent() {
		return this.fixLoanCostPercent;
	}

	public void setFixLoanCostPercent(BigDecimal fixLoanCostPercent) {
		this.fixLoanCostPercent = fixLoanCostPercent;
	}

	public String getFob() {
		return this.fob;
	}

	public void setFob(String fob) {
		this.fob = fob;
	}

	public String getForcePinChange() {
		return this.forcePinChange;
	}

	public void setForcePinChange(String forcePinChange) {
		this.forcePinChange = forcePinChange;
	}

	public String getFreightForwarder() {
		return this.freightForwarder;
	}

	public void setFreightForwarder(String freightForwarder) {
		this.freightForwarder = freightForwarder;
	}

	public String getGlCustomer() {
		return this.glCustomer;
	}

	public void setGlCustomer(String glCustomer) {
		this.glCustomer = glCustomer;
	}

	public String getGst() {
		return this.gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getGstGl() {
		return this.gstGl;
	}

	public void setGstGl(String gstGl) {
		this.gstGl = gstGl;
	}

	public String getGstGlCompany() {
		return this.gstGlCompany;
	}

	public void setGstGlCompany(String gstGlCompany) {
		this.gstGlCompany = gstGlCompany;
	}

	public String getGstGlCostCenter() {
		return this.gstGlCostCenter;
	}

	public void setGstGlCostCenter(String gstGlCostCenter) {
		this.gstGlCostCenter = gstGlCostCenter;
	}

	public String getGstGlExpenditure() {
		return this.gstGlExpenditure;
	}

	public void setGstGlExpenditure(String gstGlExpenditure) {
		this.gstGlExpenditure = gstGlExpenditure;
	}

	public BigDecimal getGstTaxPercent() {
		return this.gstTaxPercent;
	}

	public void setGstTaxPercent(BigDecimal gstTaxPercent) {
		this.gstTaxPercent = gstTaxPercent;
	}

	public String getIataAddress() {
		return this.iataAddress;
	}

	public void setIataAddress(String iataAddress) {
		this.iataAddress = iataAddress;
	}

	public Date getIfaceBaxterSupTransferDate() {
		return this.ifaceBaxterSupTransferDate;
	}

	public void setIfaceBaxterSupTransferDate(Date ifaceBaxterSupTransferDate) {
		this.ifaceBaxterSupTransferDate = ifaceBaxterSupTransferDate;
	}

	public String getInterfaceTransferBy() {
		return this.interfaceTransferBy;
	}

	public void setInterfaceTransferBy(String interfaceTransferBy) {
		this.interfaceTransferBy = interfaceTransferBy;
	}

	public Date getInterfaceTransferDate() {
		return this.interfaceTransferDate;
	}

	public void setInterfaceTransferDate(Date interfaceTransferDate) {
		this.interfaceTransferDate = interfaceTransferDate;
	}

	public String getInvoiceworksCapable() {
		return this.invoiceworksCapable;
	}

	public void setInvoiceworksCapable(String invoiceworksCapable) {
		this.invoiceworksCapable = invoiceworksCapable;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLdapUsername() {
		return this.ldapUsername;
	}

	public void setLdapUsername(String ldapUsername) {
		this.ldapUsername = ldapUsername;
	}

	public String getLetterCode3() {
		return this.letterCode3;
	}

	public void setLetterCode3(String letterCode3) {
		this.letterCode3 = letterCode3;
	}

	public String getLetterCode4() {
		return this.letterCode4;
	}

	public void setLetterCode4(String letterCode4) {
		this.letterCode4 = letterCode4;
	}

	public String getLhtVendor() {
		return this.lhtVendor;
	}

	public void setLhtVendor(String lhtVendor) {
		this.lhtVendor = lhtVendor;
	}

	public BigDecimal getLoDefaultLeadDays() {
		return this.loDefaultLeadDays;
	}

	public void setLoDefaultLeadDays(BigDecimal loDefaultLeadDays) {
		this.loDefaultLeadDays = loDefaultLeadDays;
	}

	public BigDecimal getLoanFactor1Days() {
		return this.loanFactor1Days;
	}

	public void setLoanFactor1Days(BigDecimal loanFactor1Days) {
		this.loanFactor1Days = loanFactor1Days;
	}

	public BigDecimal getLoanFactor1Percent() {
		return this.loanFactor1Percent;
	}

	public void setLoanFactor1Percent(BigDecimal loanFactor1Percent) {
		this.loanFactor1Percent = loanFactor1Percent;
	}

	public BigDecimal getLoanFactor2Days() {
		return this.loanFactor2Days;
	}

	public void setLoanFactor2Days(BigDecimal loanFactor2Days) {
		this.loanFactor2Days = loanFactor2Days;
	}

	public BigDecimal getLoanFactor2Percent() {
		return this.loanFactor2Percent;
	}

	public void setLoanFactor2Percent(BigDecimal loanFactor2Percent) {
		this.loanFactor2Percent = loanFactor2Percent;
	}

	public BigDecimal getLoanFactor3Percent() {
		return this.loanFactor3Percent;
	}

	public void setLoanFactor3Percent(BigDecimal loanFactor3Percent) {
		this.loanFactor3Percent = loanFactor3Percent;
	}

	public BigDecimal getLoanInitialFee() {
		return this.loanInitialFee;
	}

	public void setLoanInitialFee(BigDecimal loanInitialFee) {
		this.loanInitialFee = loanInitialFee;
	}

	public String getMailAddress1() {
		return this.mailAddress1;
	}

	public void setMailAddress1(String mailAddress1) {
		this.mailAddress1 = mailAddress1;
	}

	public String getMailAddress2() {
		return this.mailAddress2;
	}

	public void setMailAddress2(String mailAddress2) {
		this.mailAddress2 = mailAddress2;
	}

	public String getMailCell() {
		return this.mailCell;
	}

	public void setMailCell(String mailCell) {
		this.mailCell = mailCell;
	}

	public String getMailCity() {
		return this.mailCity;
	}

	public void setMailCity(String mailCity) {
		this.mailCity = mailCity;
	}

	public String getMailContact() {
		return this.mailContact;
	}

	public void setMailContact(String mailContact) {
		this.mailContact = mailContact;
	}

	public String getMailCountry() {
		return this.mailCountry;
	}

	public void setMailCountry(String mailCountry) {
		this.mailCountry = mailCountry;
	}

	public String getMailEmail() {
		return this.mailEmail;
	}

	public void setMailEmail(String mailEmail) {
		this.mailEmail = mailEmail;
	}

	public String getMailFax() {
		return this.mailFax;
	}

	public void setMailFax(String mailFax) {
		this.mailFax = mailFax;
	}

	public String getMailPhone() {
		return this.mailPhone;
	}

	public void setMailPhone(String mailPhone) {
		this.mailPhone = mailPhone;
	}

	public String getMailPost() {
		return this.mailPost;
	}

	public void setMailPost(String mailPost) {
		this.mailPost = mailPost;
	}

	public String getMailSita() {
		return this.mailSita;
	}

	public void setMailSita(String mailSita) {
		this.mailSita = mailSita;
	}

	public String getMailState() {
		return this.mailState;
	}

	public void setMailState(String mailState) {
		this.mailState = mailState;
	}

	public String getManufacture() {
		return this.manufacture;
	}

	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}

	public String getMechanicStamp() {
		return this.mechanicStamp;
	}

	public void setMechanicStamp(String mechanicStamp) {
		this.mechanicStamp = mechanicStamp;
	}

	public String getMechanicStamp2() {
		return this.mechanicStamp2;
	}

	public void setMechanicStamp2(String mechanicStamp2) {
		this.mechanicStamp2 = mechanicStamp2;
	}

	public String getMechanicStamp3() {
		return this.mechanicStamp3;
	}

	public void setMechanicStamp3(String mechanicStamp3) {
		this.mechanicStamp3 = mechanicStamp3;
	}

	public String getMechanicStamp4() {
		return this.mechanicStamp4;
	}

	public void setMechanicStamp4(String mechanicStamp4) {
		this.mechanicStamp4 = mechanicStamp4;
	}

	public String getMechanicStamp5() {
		return this.mechanicStamp5;
	}

	public void setMechanicStamp5(String mechanicStamp5) {
		this.mechanicStamp5 = mechanicStamp5;
	}

	public String getMfaQr() {
		return this.mfaQr;
	}

	public void setMfaQr(String mfaQr) {
		this.mfaQr = mfaQr;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMilitaryFlag() {
		return this.militaryFlag;
	}

	public void setMilitaryFlag(String militaryFlag) {
		this.militaryFlag = militaryFlag;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public String getOnCallMx() {
		return this.onCallMx;
	}

	public void setOnCallMx(String onCallMx) {
		this.onCallMx = onCallMx;
	}

	public String getOsCapable() {
		return this.osCapable;
	}

	public void setOsCapable(String osCapable) {
		this.osCapable = osCapable;
	}

	public String getOtherDescription() {
		return this.otherDescription;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	public String getOtherProg() {
		return this.otherProg;
	}

	public void setOtherProg(String otherProg) {
		this.otherProg = otherProg;
	}

	public String getOxygen() {
		return this.oxygen;
	}

	public void setOxygen(String oxygen) {
		this.oxygen = oxygen;
	}

	public BigDecimal getPercentMarkup() {
		return this.percentMarkup;
	}

	public void setPercentMarkup(BigDecimal percentMarkup) {
		this.percentMarkup = percentMarkup;
	}

	public BigDecimal getPercentMarkupXo() {
		return this.percentMarkupXo;
	}

	public void setPercentMarkupXo(BigDecimal percentMarkupXo) {
		this.percentMarkupXo = percentMarkupXo;
	}

	public BigDecimal getPhotoBlobNo() {
		return this.photoBlobNo;
	}

	public void setPhotoBlobNo(BigDecimal photoBlobNo) {
		this.photoBlobNo = photoBlobNo;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPlaceOfBirth() {
		return this.placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public BigDecimal getPoDefaultLeadDays() {
		return this.poDefaultLeadDays;
	}

	public void setPoDefaultLeadDays(BigDecimal poDefaultLeadDays) {
		this.poDefaultLeadDays = poDefaultLeadDays;
	}

	public BigDecimal getPoDiscount() {
		return this.poDiscount;
	}

	public void setPoDiscount(BigDecimal poDiscount) {
		this.poDiscount = poDiscount;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getQaAuthorizer() {
		return this.qaAuthorizer;
	}

	public void setQaAuthorizer(String qaAuthorizer) {
		this.qaAuthorizer = qaAuthorizer;
	}

	public String getRelatedLocation() {
		return this.relatedLocation;
	}

	public void setRelatedLocation(String relatedLocation) {
		this.relatedLocation = relatedLocation;
	}

	public String getRelationAssociation() {
		return this.relationAssociation;
	}

	public void setRelationAssociation(String relationAssociation) {
		this.relationAssociation = relationAssociation;
	}

	public String getRelationCategory() {
		return this.relationCategory;
	}

	public void setRelationCategory(String relationCategory) {
		this.relationCategory = relationCategory;
	}

	public String getRepair() {
		return this.repair;
	}

	public void setRepair(String repair) {
		this.repair = repair;
	}

	public String getRepairGeneral() {
		return this.repairGeneral;
	}

	public void setRepairGeneral(String repairGeneral) {
		this.repairGeneral = repairGeneral;
	}

	public String getRepairGse() {
		return this.repairGse;
	}

	public void setRepairGse(String repairGse) {
		this.repairGse = repairGse;
	}

	public String getRepairMaintenance() {
		return this.repairMaintenance;
	}

	public void setRepairMaintenance(String repairMaintenance) {
		this.repairMaintenance = repairMaintenance;
	}

	public String getRepairStationNumber() {
		return this.repairStationNumber;
	}

	public void setRepairStationNumber(String repairStationNumber) {
		this.repairStationNumber = repairStationNumber;
	}

	public String getRfidPanelConfig() {
		return this.rfidPanelConfig;
	}

	public void setRfidPanelConfig(String rfidPanelConfig) {
		this.rfidPanelConfig = rfidPanelConfig;
	}

	public String getRii() {
		return this.rii;
	}

	public void setRii(String rii) {
		this.rii = rii;
	}

	public BigDecimal getRoDefaultLeadDays() {
		return this.roDefaultLeadDays;
	}

	public void setRoDefaultLeadDays(BigDecimal roDefaultLeadDays) {
		this.roDefaultLeadDays = roDefaultLeadDays;
	}

	public String getRosActionCode() {
		return this.rosActionCode;
	}

	public void setRosActionCode(String rosActionCode) {
		this.rosActionCode = rosActionCode;
	}

	public String getRosAutoMessage() {
		return this.rosAutoMessage;
	}

	public void setRosAutoMessage(String rosAutoMessage) {
		this.rosAutoMessage = rosAutoMessage;
	}

	//public Date getRosConfig() {
	//	return this.rosConfig;
	//}

	//public void setRosConfig(Date rosConfig) {
	//	this.rosConfig = rosConfig;
	//}

	public String getRosFilename() {
		return this.rosFilename;
	}

	public void setRosFilename(String rosFilename) {
		this.rosFilename = rosFilename;
	}

	public String getRosOverride() {
		return this.rosOverride;
	}

	public void setRosOverride(String rosOverride) {
		this.rosOverride = rosOverride;
	}

	public String getRosShipmentCarrier() {
		return this.rosShipmentCarrier;
	}

	public void setRosShipmentCarrier(String rosShipmentCarrier) {
		this.rosShipmentCarrier = rosShipmentCarrier;
	}

	public String getRqrsFrgnShipDecl() {
		return this.rqrsFrgnShipDecl;
	}

	public void setRqrsFrgnShipDecl(String rqrsFrgnShipDecl) {
		this.rqrsFrgnShipDecl = rqrsFrgnShipDecl;
	}

	public BigDecimal getRqrsFrgnShipDeclNotes() {
		return this.rqrsFrgnShipDeclNotes;
	}

	public void setRqrsFrgnShipDeclNotes(BigDecimal rqrsFrgnShipDeclNotes) {
		this.rqrsFrgnShipDeclNotes = rqrsFrgnShipDeclNotes;
	}

	public String getRtvReference() {
		return this.rtvReference;
	}

	public void setRtvReference(String rtvReference) {
		this.rtvReference = rtvReference;
	}

	public BigDecimal getScheduleAcDays() {
		return this.scheduleAcDays;
	}

	public void setScheduleAcDays(BigDecimal scheduleAcDays) {
		this.scheduleAcDays = scheduleAcDays;
	}

	public BigDecimal getScheduleAcDaysRepair() {
		return this.scheduleAcDaysRepair;
	}

	public void setScheduleAcDaysRepair(BigDecimal scheduleAcDaysRepair) {
		this.scheduleAcDaysRepair = scheduleAcDaysRepair;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSendVia() {
		return this.sendVia;
	}

	public void setSendVia(String sendVia) {
		this.sendVia = sendVia;
	}

	public Date getSeniorityDate() {
		return this.seniorityDate;
	}

	public void setSeniorityDate(Date seniorityDate) {
		this.seniorityDate = seniorityDate;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSessionDomain() {
		return this.sessionDomain;
	}

	public void setSessionDomain(String sessionDomain) {
		this.sessionDomain = sessionDomain;
	}

	public String getSessionGroup() {
		return this.sessionGroup;
	}

	public void setSessionGroup(String sessionGroup) {
		this.sessionGroup = sessionGroup;
	}

	public String getSessionUsername() {
		return this.sessionUsername;
	}

	public void setSessionUsername(String sessionUsername) {
		this.sessionUsername = sessionUsername;
	}

	public BigDecimal getShipDays() {
		return this.shipDays;
	}

	public void setShipDays(BigDecimal shipDays) {
		this.shipDays = shipDays;
	}

	public String getShipVia() {
		return this.shipVia;
	}

	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}

	public String getShipViaAccount() {
		return this.shipViaAccount;
	}

	public void setShipViaAccount(String shipViaAccount) {
		this.shipViaAccount = shipViaAccount;
	}

	public String getShipViaAccountExport() {
		return this.shipViaAccountExport;
	}

	public void setShipViaAccountExport(String shipViaAccountExport) {
		this.shipViaAccountExport = shipViaAccountExport;
	}

	public String getShipViaExport() {
		return this.shipViaExport;
	}

	public void setShipViaExport(String shipViaExport) {
		this.shipViaExport = shipViaExport;
	}

	public String getShipViaRemarks() {
		return this.shipViaRemarks;
	}

	public void setShipViaRemarks(String shipViaRemarks) {
		this.shipViaRemarks = shipViaRemarks;
	}

	public String getShipViaRemarksExport() {
		return this.shipViaRemarksExport;
	}

	public void setShipViaRemarksExport(String shipViaRemarksExport) {
		this.shipViaRemarksExport = shipViaRemarksExport;
	}

	public String getShippingAddress1() {
		return this.shippingAddress1;
	}

	public void setShippingAddress1(String shippingAddress1) {
		this.shippingAddress1 = shippingAddress1;
	}

	public String getShippingAddress2() {
		return this.shippingAddress2;
	}

	public void setShippingAddress2(String shippingAddress2) {
		this.shippingAddress2 = shippingAddress2;
	}

	public String getShippingCell() {
		return this.shippingCell;
	}

	public void setShippingCell(String shippingCell) {
		this.shippingCell = shippingCell;
	}

	public String getShippingCity() {
		return this.shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingCountry() {
		return this.shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public String getShippingEmail() {
		return this.shippingEmail;
	}

	public void setShippingEmail(String shippingEmail) {
		this.shippingEmail = shippingEmail;
	}

	public String getShippingFax() {
		return this.shippingFax;
	}

	public void setShippingFax(String shippingFax) {
		this.shippingFax = shippingFax;
	}

	public String getShippingMainContact() {
		return this.shippingMainContact;
	}

	public void setShippingMainContact(String shippingMainContact) {
		this.shippingMainContact = shippingMainContact;
	}

	public String getShippingPhone() {
		return this.shippingPhone;
	}

	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}

	public String getShippingPost() {
		return this.shippingPost;
	}

	public void setShippingPost(String shippingPost) {
		this.shippingPost = shippingPost;
	}

	public String getShippingSita() {
		return this.shippingSita;
	}

	public void setShippingSita(String shippingSita) {
		this.shippingSita = shippingSita;
	}

	public String getShippingState() {
		return this.shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingTerms() {
		return this.shippingTerms;
	}

	public void setShippingTerms(String shippingTerms) {
		this.shippingTerms = shippingTerms;
	}

	public String getShopOwnershipControl() {
		return this.shopOwnershipControl;
	}

	public void setShopOwnershipControl(String shopOwnershipControl) {
		this.shopOwnershipControl = shopOwnershipControl;
	}

	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSkillCategory() {
		return this.skillCategory;
	}

	public void setSkillCategory(String skillCategory) {
		this.skillCategory = skillCategory;
	}

	public String getSpec2000Other() {
		return this.spec2000Other;
	}

	public void setSpec2000Other(String spec2000Other) {
		this.spec2000Other = spec2000Other;
	}

	public String getSpec2000Override() {
		return this.spec2000Override;
	}

	public void setSpec2000Override(String spec2000Override) {
		this.spec2000Override = spec2000Override;
	}

	public String getSpec2000Password() {
		return this.spec2000Password;
	}

	public void setSpec2000Password(String spec2000Password) {
		this.spec2000Password = spec2000Password;
	}

	public BigDecimal getSpec2000TraxdocNo() {
		return this.spec2000TraxdocNo;
	}

	public void setSpec2000TraxdocNo(BigDecimal spec2000TraxdocNo) {
		this.spec2000TraxdocNo = spec2000TraxdocNo;
	}

	public String getSpec2000Url() {
		return this.spec2000Url;
	}

	public void setSpec2000Url(String spec2000Url) {
		this.spec2000Url = spec2000Url;
	}

	public String getSpec2000Username() {
		return this.spec2000Username;
	}

	public void setSpec2000Username(String spec2000Username) {
		this.spec2000Username = spec2000Username;
	}

	public String getSpec2kRosTransmissionCode() {
		return this.spec2kRosTransmissionCode;
	}

	public void setSpec2kRosTransmissionCode(String spec2kRosTransmissionCode) {
		this.spec2kRosTransmissionCode = spec2kRosTransmissionCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierGeneral() {
		return this.supplierGeneral;
	}

	public void setSupplierGeneral(String supplierGeneral) {
		this.supplierGeneral = supplierGeneral;
	}

	public String getSupplierGse() {
		return this.supplierGse;
	}

	public void setSupplierGse(String supplierGse) {
		this.supplierGse = supplierGse;
	}

	public String getSupplierMaintenance() {
		return this.supplierMaintenance;
	}

	public void setSupplierMaintenance(String supplierMaintenance) {
		this.supplierMaintenance = supplierMaintenance;
	}

	public BigDecimal getSvDiscount() {
		return this.svDiscount;
	}

	public void setSvDiscount(BigDecimal svDiscount) {
		this.svDiscount = svDiscount;
	}

	public String getSysNc00160$() {
		return this.sysNc00160$;
	}

	public void setSysNc00160$(String sysNc00160$) {
		this.sysNc00160$ = sysNc00160$;
	}

	public String getTaxOfficeName() {
		return this.taxOfficeName;
	}

	public void setTaxOfficeName(String taxOfficeName) {
		this.taxOfficeName = taxOfficeName;
	}

	public String getTaxOfficeNumber() {
		return this.taxOfficeNumber;
	}

	public void setTaxOfficeNumber(String taxOfficeNumber) {
		this.taxOfficeNumber = taxOfficeNumber;
	}

	public String getTechPubContact() {
		return this.techPubContact;
	}

	public void setTechPubContact(String techPubContact) {
		this.techPubContact = techPubContact;
	}

	public String getTechPubMailAddress1() {
		return this.techPubMailAddress1;
	}

	public void setTechPubMailAddress1(String techPubMailAddress1) {
		this.techPubMailAddress1 = techPubMailAddress1;
	}

	public String getTechPubMailAddress2() {
		return this.techPubMailAddress2;
	}

	public void setTechPubMailAddress2(String techPubMailAddress2) {
		this.techPubMailAddress2 = techPubMailAddress2;
	}

	public String getTechPubMailCell() {
		return this.techPubMailCell;
	}

	public void setTechPubMailCell(String techPubMailCell) {
		this.techPubMailCell = techPubMailCell;
	}

	public String getTechPubMailCity() {
		return this.techPubMailCity;
	}

	public void setTechPubMailCity(String techPubMailCity) {
		this.techPubMailCity = techPubMailCity;
	}

	public String getTechPubMailCountry() {
		return this.techPubMailCountry;
	}

	public void setTechPubMailCountry(String techPubMailCountry) {
		this.techPubMailCountry = techPubMailCountry;
	}

	public String getTechPubMailEmail() {
		return this.techPubMailEmail;
	}

	public void setTechPubMailEmail(String techPubMailEmail) {
		this.techPubMailEmail = techPubMailEmail;
	}

	public String getTechPubMailFax() {
		return this.techPubMailFax;
	}

	public void setTechPubMailFax(String techPubMailFax) {
		this.techPubMailFax = techPubMailFax;
	}

	public String getTechPubMailPhone() {
		return this.techPubMailPhone;
	}

	public void setTechPubMailPhone(String techPubMailPhone) {
		this.techPubMailPhone = techPubMailPhone;
	}

	public String getTechPubMailPost() {
		return this.techPubMailPost;
	}

	public void setTechPubMailPost(String techPubMailPost) {
		this.techPubMailPost = techPubMailPost;
	}

	public String getTechPubMailState() {
		return this.techPubMailState;
	}

	public void setTechPubMailState(String techPubMailState) {
		this.techPubMailState = techPubMailState;
	}

	public String getTerms() {
		return this.terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getTrainingInstructor() {
		return this.trainingInstructor;
	}

	public void setTrainingInstructor(String trainingInstructor) {
		this.trainingInstructor = trainingInstructor;
	}

	public Date getTraxdocCreatedDate() {
		return this.traxdocCreatedDate;
	}

	public void setTraxdocCreatedDate(Date traxdocCreatedDate) {
		this.traxdocCreatedDate = traxdocCreatedDate;
	}

	public String getTraxdocFileNamePath() {
		return this.traxdocFileNamePath;
	}

	public void setTraxdocFileNamePath(String traxdocFileNamePath) {
		this.traxdocFileNamePath = traxdocFileNamePath;
	}

	public BigDecimal getTraxdocRowId() {
		return this.traxdocRowId;
	}

	public void setTraxdocRowId(BigDecimal traxdocRowId) {
		this.traxdocRowId = traxdocRowId;
	}

	public String getVat() {
		return this.vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getVendorContract() {
		return this.vendorContract;
	}

	public void setVendorContract(String vendorContract) {
		this.vendorContract = vendorContract;
	}

	public BigDecimal getWarrantyCycles() {
		return this.warrantyCycles;
	}

	public void setWarrantyCycles(BigDecimal warrantyCycles) {
		this.warrantyCycles = warrantyCycles;
	}

	public BigDecimal getWarrantyCyclesRepair() {
		return this.warrantyCyclesRepair;
	}

	public void setWarrantyCyclesRepair(BigDecimal warrantyCyclesRepair) {
		this.warrantyCyclesRepair = warrantyCyclesRepair;
	}

	public BigDecimal getWarrantyDays() {
		return this.warrantyDays;
	}

	public void setWarrantyDays(BigDecimal warrantyDays) {
		this.warrantyDays = warrantyDays;
	}

	public BigDecimal getWarrantyDaysRepair() {
		return this.warrantyDaysRepair;
	}

	public void setWarrantyDaysRepair(BigDecimal warrantyDaysRepair) {
		this.warrantyDaysRepair = warrantyDaysRepair;
	}

	public BigDecimal getWarrantyHours() {
		return this.warrantyHours;
	}

	public void setWarrantyHours(BigDecimal warrantyHours) {
		this.warrantyHours = warrantyHours;
	}

	public BigDecimal getWarrantyHoursRepair() {
		return this.warrantyHoursRepair;
	}

	public void setWarrantyHoursRepair(BigDecimal warrantyHoursRepair) {
		this.warrantyHoursRepair = warrantyHoursRepair;
	}

	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public BigDecimal getWoTaskCardFooter() {
		return this.woTaskCardFooter;
	}

	public void setWoTaskCardFooter(BigDecimal woTaskCardFooter) {
		this.woTaskCardFooter = woTaskCardFooter;
	}

}