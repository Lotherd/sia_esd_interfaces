package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PROFILE_MASTER database table.
 * 
 */
@Entity
@Table(name="PROFILE_MASTER")
@NamedQuery(name="ProfileMaster.findAll", query="SELECT p FROM ProfileMaster p")
public class ProfileMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"PROFILE\"")
	private String profile;

	@Column(name="APPROVAL_REF_NO")
	private String approvalRefNo;

	@Lob
	@Column(name="BLOB_ICON")
	private byte[] blobIcon;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="COMPANY_NAME")
	private String companyName;

	@Lob
	@Column(name="CONFIG_RCVNG_BY_BATCH")
	private byte[] configRcvngByBatch;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	private String currency;

	@Column(name="\"DATABASE\"")
	private String database;

	@Lob
	@Column(name="DRTS_SYSTEM_CONFIG")
	private byte[] drtsSystemConfig;

	@Column(name="EOS_CONFIG")
	private Date eosConfig;

	@Column(name="EOS_FILENAME")
	private String eosFilename;

	@Column(name="FAA_CERTIFICATE_NO")
	private String faaCertificateNo;

	@Lob
	@Column(name="FILE_GLOBAL_SIGNATURE_BLOB")
	private byte[] fileGlobalSignatureBlob;

	@Column(name="FILE_GLOBAL_SIGNATURE_FILENAME")
	private String fileGlobalSignatureFilename;

	@Column(name="FILE_SERVER_ADDRESS")
	private String fileServerAddress;

	@Column(name="FUR_CONFIG")
	private Date furConfig;

	@Lob
	@Column(name="IMAGE_CONFIG")
	private byte[] imageConfig;

	@Column(name="INTER_COMP_EXPENDITURE_SO")
	private String interCompExpenditureSo;

	@Column(name="INTER_COMP_EXPENDITURE_XO")
	private String interCompExpenditureXo;

	@Column(name="INTER_COMP_PO_PRIORITY")
	private String interCompPoPriority;

	@Column(name="INTER_COMPANY_BILLING")
	private String interCompanyBilling;

	@Column(name="INTER_COMPANY_CUSTOMER")
	private String interCompanyCustomer;

	@Column(name="INTER_COMPANY_VENDOR")
	private String interCompanyVendor;

	@Column(name="INVOICE_AUTO_ONESTEPPOST")
	private String invoiceAutoOnesteppost;

	@Column(name="INVOICEWORKS_BUS_UNIT_ID")
	private String invoiceworksBusUnitId;

	@Column(name="INVOICEWORKS_CUSTOMER_ID")
	private String invoiceworksCustomerId;

	@Column(name="INVOICEWORKS_ORDER_PREFIX")
	private String invoiceworksOrderPrefix;

	@Lob
	@Column(name="LDAP_BLOB")
	private byte[] ldapBlob;

	@Column(name="LDAP_FILENAME")
	private String ldapFilename;

	@Column(name="LINK_AP_INVOICE")
	private String linkApInvoice;

	private String logo;

	@Column(name="MAIL_ADDRESS_1")
	private String mailAddress1;

	@Column(name="MAIL_ADDRESS_2")
	private String mailAddress2;

	@Column(name="MAIL_ADDRESS_3")
	private String mailAddress3;

	@Column(name="MAIL_ADDRESS_4")
	private String mailAddress4;

	@Column(name="MAIL_CELL")
	private String mailCell;

	@Column(name="MAIL_CELL_2")
	private String mailCell2;

	@Column(name="MAIL_CITY")
	private String mailCity;

	@Column(name="MAIL_CITY_2")
	private String mailCity2;

	@Column(name="MAIL_COUNTRY")
	private String mailCountry;

	@Column(name="MAIL_COUNTRY_2")
	private String mailCountry2;

	@Column(name="MAIL_EMAIL")
	private String mailEmail;

	@Column(name="MAIL_EMAIL_2")
	private String mailEmail2;

	@Column(name="MAIL_FAX")
	private String mailFax;

	@Column(name="MAIL_FAX_2")
	private String mailFax2;

	@Column(name="MAIL_PHONE")
	private String mailPhone;

	@Column(name="MAIL_PHONE_2")
	private String mailPhone2;

	@Column(name="MAIL_POST")
	private String mailPost;

	@Column(name="MAIL_POST_2")
	private String mailPost2;

	@Column(name="MAIL_STATE")
	private String mailState;

	@Column(name="MAIL_STATE_2")
	private String mailState2;

	@Column(name="MATERIAL_REQUEST_CONFIG")
	private Date materialRequestConfig;

	@Column(name="MATERIAL_REQUEST_FILENAME")
	private String materialRequestFilename;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="MURATA_CONFIG")
	private Date murataConfig;

	@Lob
	@Column(name="MURATA_TEMP")
	private String murataTemp;

	private BigDecimal notes;

	@Column(name="OPERATOR_DESIGNATOR")
	private String operatorDesignator;

	@Column(name="PDSC_CONFIG")
	private Date pdscConfig;

	@Column(name="PM_CHECK_CONFIG")
	private Date pmCheckConfig;

	@Column(name="POP3_SERVER_ADDRESS")
	private String pop3ServerAddress;

	@Column(name="QUEUE_MESSAGING")
	private String queueMessaging;

	@Column(name="ROS_CONFIG")
	private Date rosConfig;

	@Column(name="ROS_FILENAME")
	private String rosFilename;

	@Column(name="ROS_OVERRIDE")
	private String rosOverride;

	@Lob
	@Column(name="RTF_TEMPLATE")
	private String rtfTemplate;

	@Column(name="SCHEMA_OWNER")
	private String schemaOwner;

	@Column(name="SHIPPING_ADDRESS_1")
	private String shippingAddress1;

	@Column(name="SHIPPING_ADDRESS_2")
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

	@Column(name="SHIPPING_PHONE")
	private String shippingPhone;

	@Column(name="SHIPPING_POST")
	private String shippingPost;

	@Column(name="SHIPPING_STATE")
	private String shippingState;

	@Lob
	@Column(name="SMS_XML_BLOB")
	private byte[] smsXmlBlob;

	@Column(name="SMS_XML_FILENAME")
	private String smsXmlFilename;

	@Lob
	@Column(name="SMTP_AUTH_BLOB")
	private byte[] smtpAuthBlob;

	@Column(name="SMTP_AUTH_FILENAME")
	private String smtpAuthFilename;

	@Column(name="SMTP_SERVER_ADDRESS")
	private String smtpServerAddress;

	@Column(name="SPEC200_NOTIFICATION_SOURCE")
	private String spec200NotificationSource;

	@Column(name="SPEC2000_AIRINC")
	private String spec2000Airinc;

	@Column(name="SPEC2000_CIC")
	private String spec2000Cic;

	@Column(name="SPEC2000_NOTIFICATION_SOURCE")
	private String spec2000NotificationSource;

	@Column(name="SPEC2000_OTHER")
	private String spec2000Other;

	@Column(name="SPEC2000_PASSWORD")
	private String spec2000Password;

	@Column(name="SPEC2000_PROXY_HOST")
	private String spec2000ProxyHost;

	@Column(name="SPEC2000_PROXY_PASSWORD")
	private String spec2000ProxyPassword;

	@Column(name="SPEC2000_PROXY_PORT")
	private BigDecimal spec2000ProxyPort;

	@Column(name="SPEC2000_PROXY_SET")
	private String spec2000ProxySet;

	@Column(name="SPEC2000_PROXY_USERNAME")
	private String spec2000ProxyUsername;

	@Column(name="SPEC2000_S1CPOXMT_REMOVE_MFR")
	private String spec2000S1cpoxmtRemoveMfr;

	@Column(name="SPEC2000_S1CPOXMT_TNC_AOG")
	private String spec2000S1cpoxmtTncAog;

	@Column(name="SPEC2000_S1CPOXMT_TNC_USR")
	private String spec2000S1cpoxmtTncUsr;

	@Column(name="SPEC2000_S1CPOXMT_TNC_WSP")
	private String spec2000S1cpoxmtTncWsp;

	@Column(name="SPEC2000_S1ORDEXC_REMOVE_AUTH")
	private String spec2000S1ordexcRemoveAuth;

	@Column(name="SPEC2000_TRAXDOC_NO")
	private BigDecimal spec2000TraxdocNo;

	@Column(name="SPEC2000_URL")
	private String spec2000Url;

	@Column(name="SPEC2000_USERNAME")
	private String spec2000Username;

	@Column(name="SPEC2000_WEB_SERVER_ADDRESS")
	private String spec2000WebServerAddress;

	@Lob
	@Column(name="STYLESHEET_BLOB")
	private byte[] stylesheetBlob;

	@Column(name="STYLESHEET_NAME")
	private String stylesheetName;

	@Column(name="SUPPLIER_EOS_CONFIG")
	private Date supplierEosConfig;

	@Column(name="SUPPLIER_EOS_FILENAME")
	private String supplierEosFilename;

	@Lob
	@Column(name="SYS_NC00068$")
	private String sysNc00068$;

	@Lob
	@Column(name="TRAXDOC_ECTC_CONFIG")
	private byte[] traxdocEctcConfig;

	@Column(name="USE_MAILING_ADDRESS2_CHECKBOX")
	private String useMailingAddress2Checkbox;

	private String vat;

	@Column(name="WEB_SERVER_ADDRESS")
	private String webServerAddress;

	@Column(name="WEB_SERVER_QUEUE")
	private String webServerQueue;

	@Column(name="WO_SWEEP_CONFIG")
	private Date woSweepConfig;

	@Column(name="WO_UPDATE_CONFIG")
	private Date woUpdateConfig;

	@Lob
	@Column(name="XML_BOEING_IMM_BLOB")
	private byte[] xmlBoeingImmBlob;

	@Column(name="XML_BOEING_IMM_FILENAME")
	private String xmlBoeingImmFilename;

	public ProfileMaster() {
	}

	public String getProfile() {
		return this.profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getApprovalRefNo() {
		return this.approvalRefNo;
	}

	public void setApprovalRefNo(String approvalRefNo) {
		this.approvalRefNo = approvalRefNo;
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

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public byte[] getConfigRcvngByBatch() {
		return this.configRcvngByBatch;
	}

	public void setConfigRcvngByBatch(byte[] configRcvngByBatch) {
		this.configRcvngByBatch = configRcvngByBatch;
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

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDatabase() {
		return this.database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public byte[] getDrtsSystemConfig() {
		return this.drtsSystemConfig;
	}

	public void setDrtsSystemConfig(byte[] drtsSystemConfig) {
		this.drtsSystemConfig = drtsSystemConfig;
	}

	public Date getEosConfig() {
		return this.eosConfig;
	}

	public void setEosConfig(Date eosConfig) {
		this.eosConfig = eosConfig;
	}

	public String getEosFilename() {
		return this.eosFilename;
	}

	public void setEosFilename(String eosFilename) {
		this.eosFilename = eosFilename;
	}

	public String getFaaCertificateNo() {
		return this.faaCertificateNo;
	}

	public void setFaaCertificateNo(String faaCertificateNo) {
		this.faaCertificateNo = faaCertificateNo;
	}

	public byte[] getFileGlobalSignatureBlob() {
		return this.fileGlobalSignatureBlob;
	}

	public void setFileGlobalSignatureBlob(byte[] fileGlobalSignatureBlob) {
		this.fileGlobalSignatureBlob = fileGlobalSignatureBlob;
	}

	public String getFileGlobalSignatureFilename() {
		return this.fileGlobalSignatureFilename;
	}

	public void setFileGlobalSignatureFilename(String fileGlobalSignatureFilename) {
		this.fileGlobalSignatureFilename = fileGlobalSignatureFilename;
	}

	public String getFileServerAddress() {
		return this.fileServerAddress;
	}

	public void setFileServerAddress(String fileServerAddress) {
		this.fileServerAddress = fileServerAddress;
	}

	public Date getFurConfig() {
		return this.furConfig;
	}

	public void setFurConfig(Date furConfig) {
		this.furConfig = furConfig;
	}

	public byte[] getImageConfig() {
		return this.imageConfig;
	}

	public void setImageConfig(byte[] imageConfig) {
		this.imageConfig = imageConfig;
	}

	public String getInterCompExpenditureSo() {
		return this.interCompExpenditureSo;
	}

	public void setInterCompExpenditureSo(String interCompExpenditureSo) {
		this.interCompExpenditureSo = interCompExpenditureSo;
	}

	public String getInterCompExpenditureXo() {
		return this.interCompExpenditureXo;
	}

	public void setInterCompExpenditureXo(String interCompExpenditureXo) {
		this.interCompExpenditureXo = interCompExpenditureXo;
	}

	public String getInterCompPoPriority() {
		return this.interCompPoPriority;
	}

	public void setInterCompPoPriority(String interCompPoPriority) {
		this.interCompPoPriority = interCompPoPriority;
	}

	public String getInterCompanyBilling() {
		return this.interCompanyBilling;
	}

	public void setInterCompanyBilling(String interCompanyBilling) {
		this.interCompanyBilling = interCompanyBilling;
	}

	public String getInterCompanyCustomer() {
		return this.interCompanyCustomer;
	}

	public void setInterCompanyCustomer(String interCompanyCustomer) {
		this.interCompanyCustomer = interCompanyCustomer;
	}

	public String getInterCompanyVendor() {
		return this.interCompanyVendor;
	}

	public void setInterCompanyVendor(String interCompanyVendor) {
		this.interCompanyVendor = interCompanyVendor;
	}

	public String getInvoiceAutoOnesteppost() {
		return this.invoiceAutoOnesteppost;
	}

	public void setInvoiceAutoOnesteppost(String invoiceAutoOnesteppost) {
		this.invoiceAutoOnesteppost = invoiceAutoOnesteppost;
	}

	public String getInvoiceworksBusUnitId() {
		return this.invoiceworksBusUnitId;
	}

	public void setInvoiceworksBusUnitId(String invoiceworksBusUnitId) {
		this.invoiceworksBusUnitId = invoiceworksBusUnitId;
	}

	public String getInvoiceworksCustomerId() {
		return this.invoiceworksCustomerId;
	}

	public void setInvoiceworksCustomerId(String invoiceworksCustomerId) {
		this.invoiceworksCustomerId = invoiceworksCustomerId;
	}

	public String getInvoiceworksOrderPrefix() {
		return this.invoiceworksOrderPrefix;
	}

	public void setInvoiceworksOrderPrefix(String invoiceworksOrderPrefix) {
		this.invoiceworksOrderPrefix = invoiceworksOrderPrefix;
	}

	public byte[] getLdapBlob() {
		return this.ldapBlob;
	}

	public void setLdapBlob(byte[] ldapBlob) {
		this.ldapBlob = ldapBlob;
	}

	public String getLdapFilename() {
		return this.ldapFilename;
	}

	public void setLdapFilename(String ldapFilename) {
		this.ldapFilename = ldapFilename;
	}

	public String getLinkApInvoice() {
		return this.linkApInvoice;
	}

	public void setLinkApInvoice(String linkApInvoice) {
		this.linkApInvoice = linkApInvoice;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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

	public String getMailAddress3() {
		return this.mailAddress3;
	}

	public void setMailAddress3(String mailAddress3) {
		this.mailAddress3 = mailAddress3;
	}

	public String getMailAddress4() {
		return this.mailAddress4;
	}

	public void setMailAddress4(String mailAddress4) {
		this.mailAddress4 = mailAddress4;
	}

	public String getMailCell() {
		return this.mailCell;
	}

	public void setMailCell(String mailCell) {
		this.mailCell = mailCell;
	}

	public String getMailCell2() {
		return this.mailCell2;
	}

	public void setMailCell2(String mailCell2) {
		this.mailCell2 = mailCell2;
	}

	public String getMailCity() {
		return this.mailCity;
	}

	public void setMailCity(String mailCity) {
		this.mailCity = mailCity;
	}

	public String getMailCity2() {
		return this.mailCity2;
	}

	public void setMailCity2(String mailCity2) {
		this.mailCity2 = mailCity2;
	}

	public String getMailCountry() {
		return this.mailCountry;
	}

	public void setMailCountry(String mailCountry) {
		this.mailCountry = mailCountry;
	}

	public String getMailCountry2() {
		return this.mailCountry2;
	}

	public void setMailCountry2(String mailCountry2) {
		this.mailCountry2 = mailCountry2;
	}

	public String getMailEmail() {
		return this.mailEmail;
	}

	public void setMailEmail(String mailEmail) {
		this.mailEmail = mailEmail;
	}

	public String getMailEmail2() {
		return this.mailEmail2;
	}

	public void setMailEmail2(String mailEmail2) {
		this.mailEmail2 = mailEmail2;
	}

	public String getMailFax() {
		return this.mailFax;
	}

	public void setMailFax(String mailFax) {
		this.mailFax = mailFax;
	}

	public String getMailFax2() {
		return this.mailFax2;
	}

	public void setMailFax2(String mailFax2) {
		this.mailFax2 = mailFax2;
	}

	public String getMailPhone() {
		return this.mailPhone;
	}

	public void setMailPhone(String mailPhone) {
		this.mailPhone = mailPhone;
	}

	public String getMailPhone2() {
		return this.mailPhone2;
	}

	public void setMailPhone2(String mailPhone2) {
		this.mailPhone2 = mailPhone2;
	}

	public String getMailPost() {
		return this.mailPost;
	}

	public void setMailPost(String mailPost) {
		this.mailPost = mailPost;
	}

	public String getMailPost2() {
		return this.mailPost2;
	}

	public void setMailPost2(String mailPost2) {
		this.mailPost2 = mailPost2;
	}

	public String getMailState() {
		return this.mailState;
	}

	public void setMailState(String mailState) {
		this.mailState = mailState;
	}

	public String getMailState2() {
		return this.mailState2;
	}

	public void setMailState2(String mailState2) {
		this.mailState2 = mailState2;
	}

	public Date getMaterialRequestConfig() {
		return this.materialRequestConfig;
	}

	public void setMaterialRequestConfig(Date materialRequestConfig) {
		this.materialRequestConfig = materialRequestConfig;
	}

	public String getMaterialRequestFilename() {
		return this.materialRequestFilename;
	}

	public void setMaterialRequestFilename(String materialRequestFilename) {
		this.materialRequestFilename = materialRequestFilename;
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

	public Date getMurataConfig() {
		return this.murataConfig;
	}

	public void setMurataConfig(Date murataConfig) {
		this.murataConfig = murataConfig;
	}

	public String getMurataTemp() {
		return this.murataTemp;
	}

	public void setMurataTemp(String murataTemp) {
		this.murataTemp = murataTemp;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public String getOperatorDesignator() {
		return this.operatorDesignator;
	}

	public void setOperatorDesignator(String operatorDesignator) {
		this.operatorDesignator = operatorDesignator;
	}

	public Date getPdscConfig() {
		return this.pdscConfig;
	}

	public void setPdscConfig(Date pdscConfig) {
		this.pdscConfig = pdscConfig;
	}

	public Date getPmCheckConfig() {
		return this.pmCheckConfig;
	}

	public void setPmCheckConfig(Date pmCheckConfig) {
		this.pmCheckConfig = pmCheckConfig;
	}

	public String getPop3ServerAddress() {
		return this.pop3ServerAddress;
	}

	public void setPop3ServerAddress(String pop3ServerAddress) {
		this.pop3ServerAddress = pop3ServerAddress;
	}

	public String getQueueMessaging() {
		return this.queueMessaging;
	}

	public void setQueueMessaging(String queueMessaging) {
		this.queueMessaging = queueMessaging;
	}

	public Date getRosConfig() {
		return this.rosConfig;
	}

	public void setRosConfig(Date rosConfig) {
		this.rosConfig = rosConfig;
	}

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

	public String getRtfTemplate() {
		return this.rtfTemplate;
	}

	public void setRtfTemplate(String rtfTemplate) {
		this.rtfTemplate = rtfTemplate;
	}

	public String getSchemaOwner() {
		return this.schemaOwner;
	}

	public void setSchemaOwner(String schemaOwner) {
		this.schemaOwner = schemaOwner;
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

	public String getShippingState() {
		return this.shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public byte[] getSmsXmlBlob() {
		return this.smsXmlBlob;
	}

	public void setSmsXmlBlob(byte[] smsXmlBlob) {
		this.smsXmlBlob = smsXmlBlob;
	}

	public String getSmsXmlFilename() {
		return this.smsXmlFilename;
	}

	public void setSmsXmlFilename(String smsXmlFilename) {
		this.smsXmlFilename = smsXmlFilename;
	}

	public byte[] getSmtpAuthBlob() {
		return this.smtpAuthBlob;
	}

	public void setSmtpAuthBlob(byte[] smtpAuthBlob) {
		this.smtpAuthBlob = smtpAuthBlob;
	}

	public String getSmtpAuthFilename() {
		return this.smtpAuthFilename;
	}

	public void setSmtpAuthFilename(String smtpAuthFilename) {
		this.smtpAuthFilename = smtpAuthFilename;
	}

	public String getSmtpServerAddress() {
		return this.smtpServerAddress;
	}

	public void setSmtpServerAddress(String smtpServerAddress) {
		this.smtpServerAddress = smtpServerAddress;
	}

	public String getSpec200NotificationSource() {
		return this.spec200NotificationSource;
	}

	public void setSpec200NotificationSource(String spec200NotificationSource) {
		this.spec200NotificationSource = spec200NotificationSource;
	}

	public String getSpec2000Airinc() {
		return this.spec2000Airinc;
	}

	public void setSpec2000Airinc(String spec2000Airinc) {
		this.spec2000Airinc = spec2000Airinc;
	}

	public String getSpec2000Cic() {
		return this.spec2000Cic;
	}

	public void setSpec2000Cic(String spec2000Cic) {
		this.spec2000Cic = spec2000Cic;
	}

	public String getSpec2000NotificationSource() {
		return this.spec2000NotificationSource;
	}

	public void setSpec2000NotificationSource(String spec2000NotificationSource) {
		this.spec2000NotificationSource = spec2000NotificationSource;
	}

	public String getSpec2000Other() {
		return this.spec2000Other;
	}

	public void setSpec2000Other(String spec2000Other) {
		this.spec2000Other = spec2000Other;
	}

	public String getSpec2000Password() {
		return this.spec2000Password;
	}

	public void setSpec2000Password(String spec2000Password) {
		this.spec2000Password = spec2000Password;
	}

	public String getSpec2000ProxyHost() {
		return this.spec2000ProxyHost;
	}

	public void setSpec2000ProxyHost(String spec2000ProxyHost) {
		this.spec2000ProxyHost = spec2000ProxyHost;
	}

	public String getSpec2000ProxyPassword() {
		return this.spec2000ProxyPassword;
	}

	public void setSpec2000ProxyPassword(String spec2000ProxyPassword) {
		this.spec2000ProxyPassword = spec2000ProxyPassword;
	}

	public BigDecimal getSpec2000ProxyPort() {
		return this.spec2000ProxyPort;
	}

	public void setSpec2000ProxyPort(BigDecimal spec2000ProxyPort) {
		this.spec2000ProxyPort = spec2000ProxyPort;
	}

	public String getSpec2000ProxySet() {
		return this.spec2000ProxySet;
	}

	public void setSpec2000ProxySet(String spec2000ProxySet) {
		this.spec2000ProxySet = spec2000ProxySet;
	}

	public String getSpec2000ProxyUsername() {
		return this.spec2000ProxyUsername;
	}

	public void setSpec2000ProxyUsername(String spec2000ProxyUsername) {
		this.spec2000ProxyUsername = spec2000ProxyUsername;
	}

	public String getSpec2000S1cpoxmtRemoveMfr() {
		return this.spec2000S1cpoxmtRemoveMfr;
	}

	public void setSpec2000S1cpoxmtRemoveMfr(String spec2000S1cpoxmtRemoveMfr) {
		this.spec2000S1cpoxmtRemoveMfr = spec2000S1cpoxmtRemoveMfr;
	}

	public String getSpec2000S1cpoxmtTncAog() {
		return this.spec2000S1cpoxmtTncAog;
	}

	public void setSpec2000S1cpoxmtTncAog(String spec2000S1cpoxmtTncAog) {
		this.spec2000S1cpoxmtTncAog = spec2000S1cpoxmtTncAog;
	}

	public String getSpec2000S1cpoxmtTncUsr() {
		return this.spec2000S1cpoxmtTncUsr;
	}

	public void setSpec2000S1cpoxmtTncUsr(String spec2000S1cpoxmtTncUsr) {
		this.spec2000S1cpoxmtTncUsr = spec2000S1cpoxmtTncUsr;
	}

	public String getSpec2000S1cpoxmtTncWsp() {
		return this.spec2000S1cpoxmtTncWsp;
	}

	public void setSpec2000S1cpoxmtTncWsp(String spec2000S1cpoxmtTncWsp) {
		this.spec2000S1cpoxmtTncWsp = spec2000S1cpoxmtTncWsp;
	}

	public String getSpec2000S1ordexcRemoveAuth() {
		return this.spec2000S1ordexcRemoveAuth;
	}

	public void setSpec2000S1ordexcRemoveAuth(String spec2000S1ordexcRemoveAuth) {
		this.spec2000S1ordexcRemoveAuth = spec2000S1ordexcRemoveAuth;
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

	public String getSpec2000WebServerAddress() {
		return this.spec2000WebServerAddress;
	}

	public void setSpec2000WebServerAddress(String spec2000WebServerAddress) {
		this.spec2000WebServerAddress = spec2000WebServerAddress;
	}

	public byte[] getStylesheetBlob() {
		return this.stylesheetBlob;
	}

	public void setStylesheetBlob(byte[] stylesheetBlob) {
		this.stylesheetBlob = stylesheetBlob;
	}

	public String getStylesheetName() {
		return this.stylesheetName;
	}

	public void setStylesheetName(String stylesheetName) {
		this.stylesheetName = stylesheetName;
	}

	public Date getSupplierEosConfig() {
		return this.supplierEosConfig;
	}

	public void setSupplierEosConfig(Date supplierEosConfig) {
		this.supplierEosConfig = supplierEosConfig;
	}

	public String getSupplierEosFilename() {
		return this.supplierEosFilename;
	}

	public void setSupplierEosFilename(String supplierEosFilename) {
		this.supplierEosFilename = supplierEosFilename;
	}

	public String getSysNc00068$() {
		return this.sysNc00068$;
	}

	public void setSysNc00068$(String sysNc00068$) {
		this.sysNc00068$ = sysNc00068$;
	}

	public byte[] getTraxdocEctcConfig() {
		return this.traxdocEctcConfig;
	}

	public void setTraxdocEctcConfig(byte[] traxdocEctcConfig) {
		this.traxdocEctcConfig = traxdocEctcConfig;
	}

	public String getUseMailingAddress2Checkbox() {
		return this.useMailingAddress2Checkbox;
	}

	public void setUseMailingAddress2Checkbox(String useMailingAddress2Checkbox) {
		this.useMailingAddress2Checkbox = useMailingAddress2Checkbox;
	}

	public String getVat() {
		return this.vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getWebServerAddress() {
		return this.webServerAddress;
	}

	public void setWebServerAddress(String webServerAddress) {
		this.webServerAddress = webServerAddress;
	}

	public String getWebServerQueue() {
		return this.webServerQueue;
	}

	public void setWebServerQueue(String webServerQueue) {
		this.webServerQueue = webServerQueue;
	}

	public Date getWoSweepConfig() {
		return this.woSweepConfig;
	}

	public void setWoSweepConfig(Date woSweepConfig) {
		this.woSweepConfig = woSweepConfig;
	}

	public Date getWoUpdateConfig() {
		return this.woUpdateConfig;
	}

	public void setWoUpdateConfig(Date woUpdateConfig) {
		this.woUpdateConfig = woUpdateConfig;
	}

	public byte[] getXmlBoeingImmBlob() {
		return this.xmlBoeingImmBlob;
	}

	public void setXmlBoeingImmBlob(byte[] xmlBoeingImmBlob) {
		this.xmlBoeingImmBlob = xmlBoeingImmBlob;
	}

	public String getXmlBoeingImmFilename() {
		return this.xmlBoeingImmFilename;
	}

	public void setXmlBoeingImmFilename(String xmlBoeingImmFilename) {
		this.xmlBoeingImmFilename = xmlBoeingImmFilename;
	}

}