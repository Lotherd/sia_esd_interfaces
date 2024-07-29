package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PN_INVENTORY_DETAIL database table.
 * 
 */
@Entity
@Table(name="PN_INVENTORY_DETAIL")
@NamedQuery(name="PnInventoryDetail.findAll", query="SELECT p FROM PnInventoryDetail p")
public class PnInventoryDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long batch;

	@Column(name="ACCOUNTING_DOCUMENT")
	private String accountingDocument;

	@Column(name="ACCOUNTING_DOCUMENT_DATE")
	private Date accountingDocumentDate;

	@Column(name="ADJUSTMENT_CODE")
	private String adjustmentCode;

	@Column(name="APPROVED_CERTIFICATE")
	private String approvedCertificate;

	@Column(name="APPROVED_CERTIFICATE_NO")
	private String approvedCertificateNo;

	@Column(name="APPROVED_CERTIFICATE_ORIGINAL")
	private String approvedCertificateOriginal;

	@Column(name="AUTOMATED_WAREHOUSE_MEMO")
	private String automatedWarehouseMemo;

	private String bin;

	@Column(name="BIRTH_DATE")
	private Date birthDate;

	@Column(name="BLADE_AXIAL_WEIGHT")
	private BigDecimal bladeAxialWeight;

	@Column(name="BLADE_RADIAL_WEIGHT")
	private BigDecimal bladeRadialWeight;

	@Column(name="BLADE_TANGENTIAL_WEIGHT")
	private BigDecimal bladeTangentialWeight;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="BOX_PACKED_BY")
	private String boxPackedBy;

	@Column(name="BOX_PACKED_DATE")
	private Date boxPackedDate;

	@Column(name="BOX_PACKED_IN_BOX")
	private String boxPackedInBox;

	private String condition;

	@Column(name="COST_IN_KIT")
	private String costInKit;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	private String currency;

	@Column(name="CURRENCY_EXCHANGE_RATE")
	private BigDecimal currencyExchangeRate;

	@Column(name="CUSTOM_DUTY_DOCUMENT")
	private String customDutyDocument;

	@Column(name="CUTOM_DUTY_CODE")
	private String cutomDutyCode;

	@Column(name="DATE_BINNED")
	private Date dateBinned;

	@Column(name="DISTRIBUTED_LICENSE")
	private String distributedLicense;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	@Column(name="EXTERNAL_POSITION")
	private String externalPosition;

	@Column(name="EXTERNAL_REFERENCE")
	private String externalReference;

	@Column(name="FILING_SEQUENCE")
	private BigDecimal filingSequence;

	@Column(name="FORM_NO")
	private BigDecimal formNo;

	@Column(name="FUNCTIONAL_LOCATION")
	private String functionalLocation;

	private String gl;

	@Column(name="GL_COMPANY")
	private String glCompany;

	@Column(name="GL_COST_CENTER")
	private String glCostCenter;

	@Column(name="GL_EXPENDITURE")
	private String glExpenditure;

	@Column(name="GOODS_RCVD_BATCH")
	private BigDecimal goodsRcvdBatch;

	@Column(name="HAZARDOUS_MATERIAL_AT_REMOVAL")
	private String hazardousMaterialAtRemoval;

	private BigDecimal height;

	@Column(name="HEIGHT_UNITS")
	private String heightUnits;

	@Column(name="IN_USE")
	private String inUse;

	@Column(name="IN_USE_BY")
	private String inUseBy;

	@Column(name="INITIAL_TSI_CYCLES")
	private BigDecimal initialTsiCycles;

	@Column(name="INITIAL_TSI_DAYS")
	private BigDecimal initialTsiDays;

	@Column(name="INITIAL_TSI_HOURS")
	private BigDecimal initialTsiHours;

	@Column(name="INITIAL_TSI_MINUTES")
	private BigDecimal initialTsiMinutes;

	private String inop;

	@Column(name="INSTALLED_AC")
	private String installedAc;

	@Column(name="INSTALLED_DATE")
	private Date installedDate;

	@Column(name="INSTALLED_HOUR")
	private BigDecimal installedHour;

	@Column(name="INSTALLED_MINUTE")
	private BigDecimal installedMinute;

	@Column(name="INSTALLED_POSITION")
	private String installedPosition;

	@Column(name="INTERFACE_TRANSFERRED_DATE")
	private Date interfaceTransferredDate;

	@Column(name="INVENTORY_TYPE")
	private String inventoryType;

	@Column(name="KIT_NO")
	private BigDecimal kitNo;

	@Column(name="LEGACY_BATCH")
	private String legacyBatch;

	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	@Column(name="LENGTH_UNITS")
	private String lengthUnits;

	@Column(name="LICENSE_TYPE")
	private String licenseType;

	@Column(name="LOAN_CATEGORY")
	private String loanCategory;

	private String location;

	@Column(name="MANAGED_WO")
	private BigDecimal managedWo;

	private String missing;

	@Column(name="MOD_STATUS")
	private String modStatus;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="NHA_PN")
	private String nhaPn;

	@Column(name="NHA_SN")
	private String nhaSn;

	@Column(name="NLA_POSITION")
	private String nlaPosition;

	@Column(name="NO_OF_TAG_PRINT")
	private BigDecimal noOfTagPrint;

	private BigDecimal notes;

	@Column(name="ORGINAL_RECORDS_DATE")
	private Date orginalRecordsDate;

	@Column(name="ORIGINAL_AC")
	private String originalAc;

	@Column(name="ORIGINAL_AC_ISSUE_TO_ONLY")
	private String originalAcIssueToOnly;

	@Column(name="ORIGINAL_RECORDS_BY")
	private String originalRecordsBy;

	private String owner;

	@Column(name="OWNER_ORIGIN")
	private String ownerOrigin;

	private String pending;

	@Column(name="PENDING_DATE")
	private Date pendingDate;

	@Column(name="PHYSICAL_COUNT_BY")
	private String physicalCountBy;

	@Column(name="PHYSICAL_COUNT_BY_PREV")
	private String physicalCountByPrev;

	@Column(name="PHYSICAL_COUNT_DATE")
	private Date physicalCountDate;

	@Column(name="PHYSICAL_COUNT_DATE_PREV")
	private Date physicalCountDatePrev;

	private String pn;

	@Column(name="PN_MODEL")
	private String pnModel;

	@Column(name="PN_SOFTWARE")
	private String pnSoftware;

	@Column(name="PN_SW_CONFIG_TRANS")
	private BigDecimal pnSwConfigTrans;

	@Column(name="QTY_AVAILABLE")
	private BigDecimal qtyAvailable;

	@Column(name="QTY_IN_RENTAL")
	private BigDecimal qtyInRental;

	@Column(name="QTY_IN_REPAIR")
	private BigDecimal qtyInRepair;

	@Column(name="QTY_IN_TRANSFER")
	private BigDecimal qtyInTransfer;

	@Column(name="QTY_LICENSE")
	private BigDecimal qtyLicense;

	@Column(name="QTY_ON_HOLD")
	private BigDecimal qtyOnHold;

	@Column(name="QTY_PENDING_RI")
	private BigDecimal qtyPendingRi;

	@Column(name="QTY_RESERVED")
	private BigDecimal qtyReserved;

	@Column(name="QTY_US")
	private BigDecimal qtyUs;

	@Column(name="REPAIR_RETURN_DATE")
	private Date repairReturnDate;

	@Column(name="RFID_EPC")
	private String rfidEpc;

	@Column(name="RI_BY")
	private String riBy;

	@Column(name="RI_CONTROL")
	private String riControl;

	@Column(name="RI_DATE")
	private Date riDate;

	@Column(name="RO_FOLLOWING_ID_SN")
	private BigDecimal roFollowingIdSn;

	@Column(name="SCRAP_CODE")
	private String scrapCode;

	@Column(name="SECONDARY_COST")
	private BigDecimal secondaryCost;

	@Column(name="SECONDARY_CURRENCY_EXCHANGE")
	private BigDecimal secondaryCurrencyExchange;

	@Column(name="SHELF_LIFE_EXP_EOM")
	private Date shelfLifeExpEom;

	@Column(name="SHELF_LIFE_EXPIRATION")
	private Date shelfLifeExpiration;

	@Column(name="SHELF_LIFE_EXPIRATION_LAST")
	private Date shelfLifeExpirationLast;

	private BigDecimal slot;

	private String sn;

	@Column(name="SOS_DATE")
	private Date sosDate;

	@Column(name="SOS_HOUR")
	private BigDecimal sosHour;

	@Column(name="SOS_MINUTE")
	private BigDecimal sosMinute;

	@Column(name="SUPERIOR_EQUIPMENT")
	private String superiorEquipment;

	@Column(name="TAG_BY")
	private String tagBy;

	@Column(name="TAG_DATE")
	private Date tagDate;

	@Column(name="TAG_NO")
	private String tagNo;

	@Column(name="TOOL_CALIBRATION_NO")
	private String toolCalibrationNo;

	@Column(name="TOOL_LIFE_CONCESSION_DAYS")
	private BigDecimal toolLifeConcessionDays;

	@Column(name="TOOL_LIFE_EXP_EOM")
	private Date toolLifeExpEom;

	@Column(name="TOOL_LIFE_EXPIRATION")
	private Date toolLifeExpiration;

	@Column(name="TOOL_LIFE_EXPIRATION_LAST")
	private Date toolLifeExpirationLast;

	@Column(name="UM_AXIAL")
	private String umAxial;

	@Column(name="UM_RADIAL")
	private String umRadial;

	@Column(name="UM_TANGENTIAL")
	private String umTangential;

	@Column(name="UNIT_COST")
	private BigDecimal unitCost;

	@Column(name="US_CODE")
	private String usCode;

	@Column(name="US_NUMBER")
	private String usNumber;

	@Column(name="VENDOR_LOT")
	private String vendorLot;

	private BigDecimal weight;

	@Column(name="WEIGHT_UNIT")
	private String weightUnit;

	private BigDecimal width;

	@Column(name="WIDTH_UNITS")
	private String widthUnits;

	public PnInventoryDetail() {
	}

	public long getBatch() {
		return this.batch;
	}

	public void setBatch(long batch) {
		this.batch = batch;
	}

	public String getAccountingDocument() {
		return this.accountingDocument;
	}

	public void setAccountingDocument(String accountingDocument) {
		this.accountingDocument = accountingDocument;
	}

	public Date getAccountingDocumentDate() {
		return this.accountingDocumentDate;
	}

	public void setAccountingDocumentDate(Date accountingDocumentDate) {
		this.accountingDocumentDate = accountingDocumentDate;
	}

	public String getAdjustmentCode() {
		return this.adjustmentCode;
	}

	public void setAdjustmentCode(String adjustmentCode) {
		this.adjustmentCode = adjustmentCode;
	}

	public String getApprovedCertificate() {
		return this.approvedCertificate;
	}

	public void setApprovedCertificate(String approvedCertificate) {
		this.approvedCertificate = approvedCertificate;
	}

	public String getApprovedCertificateNo() {
		return this.approvedCertificateNo;
	}

	public void setApprovedCertificateNo(String approvedCertificateNo) {
		this.approvedCertificateNo = approvedCertificateNo;
	}

	public String getApprovedCertificateOriginal() {
		return this.approvedCertificateOriginal;
	}

	public void setApprovedCertificateOriginal(String approvedCertificateOriginal) {
		this.approvedCertificateOriginal = approvedCertificateOriginal;
	}

	public String getAutomatedWarehouseMemo() {
		return this.automatedWarehouseMemo;
	}

	public void setAutomatedWarehouseMemo(String automatedWarehouseMemo) {
		this.automatedWarehouseMemo = automatedWarehouseMemo;
	}

	public String getBin() {
		return this.bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public BigDecimal getBladeAxialWeight() {
		return this.bladeAxialWeight;
	}

	public void setBladeAxialWeight(BigDecimal bladeAxialWeight) {
		this.bladeAxialWeight = bladeAxialWeight;
	}

	public BigDecimal getBladeRadialWeight() {
		return this.bladeRadialWeight;
	}

	public void setBladeRadialWeight(BigDecimal bladeRadialWeight) {
		this.bladeRadialWeight = bladeRadialWeight;
	}

	public BigDecimal getBladeTangentialWeight() {
		return this.bladeTangentialWeight;
	}

	public void setBladeTangentialWeight(BigDecimal bladeTangentialWeight) {
		this.bladeTangentialWeight = bladeTangentialWeight;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public String getBoxPackedBy() {
		return this.boxPackedBy;
	}

	public void setBoxPackedBy(String boxPackedBy) {
		this.boxPackedBy = boxPackedBy;
	}

	public Date getBoxPackedDate() {
		return this.boxPackedDate;
	}

	public void setBoxPackedDate(Date boxPackedDate) {
		this.boxPackedDate = boxPackedDate;
	}

	public String getBoxPackedInBox() {
		return this.boxPackedInBox;
	}

	public void setBoxPackedInBox(String boxPackedInBox) {
		this.boxPackedInBox = boxPackedInBox;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCostInKit() {
		return this.costInKit;
	}

	public void setCostInKit(String costInKit) {
		this.costInKit = costInKit;
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

	public BigDecimal getCurrencyExchangeRate() {
		return this.currencyExchangeRate;
	}

	public void setCurrencyExchangeRate(BigDecimal currencyExchangeRate) {
		this.currencyExchangeRate = currencyExchangeRate;
	}

	public String getCustomDutyDocument() {
		return this.customDutyDocument;
	}

	public void setCustomDutyDocument(String customDutyDocument) {
		this.customDutyDocument = customDutyDocument;
	}

	public String getCutomDutyCode() {
		return this.cutomDutyCode;
	}

	public void setCutomDutyCode(String cutomDutyCode) {
		this.cutomDutyCode = cutomDutyCode;
	}

	public Date getDateBinned() {
		return this.dateBinned;
	}

	public void setDateBinned(Date dateBinned) {
		this.dateBinned = dateBinned;
	}

	public String getDistributedLicense() {
		return this.distributedLicense;
	}

	public void setDistributedLicense(String distributedLicense) {
		this.distributedLicense = distributedLicense;
	}

	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getExternalPosition() {
		return this.externalPosition;
	}

	public void setExternalPosition(String externalPosition) {
		this.externalPosition = externalPosition;
	}

	public String getExternalReference() {
		return this.externalReference;
	}

	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}

	public BigDecimal getFilingSequence() {
		return this.filingSequence;
	}

	public void setFilingSequence(BigDecimal filingSequence) {
		this.filingSequence = filingSequence;
	}

	public BigDecimal getFormNo() {
		return this.formNo;
	}

	public void setFormNo(BigDecimal formNo) {
		this.formNo = formNo;
	}

	public String getFunctionalLocation() {
		return this.functionalLocation;
	}

	public void setFunctionalLocation(String functionalLocation) {
		this.functionalLocation = functionalLocation;
	}

	public String getGl() {
		return this.gl;
	}

	public void setGl(String gl) {
		this.gl = gl;
	}

	public String getGlCompany() {
		return this.glCompany;
	}

	public void setGlCompany(String glCompany) {
		this.glCompany = glCompany;
	}

	public String getGlCostCenter() {
		return this.glCostCenter;
	}

	public void setGlCostCenter(String glCostCenter) {
		this.glCostCenter = glCostCenter;
	}

	public String getGlExpenditure() {
		return this.glExpenditure;
	}

	public void setGlExpenditure(String glExpenditure) {
		this.glExpenditure = glExpenditure;
	}

	public BigDecimal getGoodsRcvdBatch() {
		return this.goodsRcvdBatch;
	}

	public void setGoodsRcvdBatch(BigDecimal goodsRcvdBatch) {
		this.goodsRcvdBatch = goodsRcvdBatch;
	}

	public String getHazardousMaterialAtRemoval() {
		return this.hazardousMaterialAtRemoval;
	}

	public void setHazardousMaterialAtRemoval(String hazardousMaterialAtRemoval) {
		this.hazardousMaterialAtRemoval = hazardousMaterialAtRemoval;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getHeightUnits() {
		return this.heightUnits;
	}

	public void setHeightUnits(String heightUnits) {
		this.heightUnits = heightUnits;
	}

	public String getInUse() {
		return this.inUse;
	}

	public void setInUse(String inUse) {
		this.inUse = inUse;
	}

	public String getInUseBy() {
		return this.inUseBy;
	}

	public void setInUseBy(String inUseBy) {
		this.inUseBy = inUseBy;
	}

	public BigDecimal getInitialTsiCycles() {
		return this.initialTsiCycles;
	}

	public void setInitialTsiCycles(BigDecimal initialTsiCycles) {
		this.initialTsiCycles = initialTsiCycles;
	}

	public BigDecimal getInitialTsiDays() {
		return this.initialTsiDays;
	}

	public void setInitialTsiDays(BigDecimal initialTsiDays) {
		this.initialTsiDays = initialTsiDays;
	}

	public BigDecimal getInitialTsiHours() {
		return this.initialTsiHours;
	}

	public void setInitialTsiHours(BigDecimal initialTsiHours) {
		this.initialTsiHours = initialTsiHours;
	}

	public BigDecimal getInitialTsiMinutes() {
		return this.initialTsiMinutes;
	}

	public void setInitialTsiMinutes(BigDecimal initialTsiMinutes) {
		this.initialTsiMinutes = initialTsiMinutes;
	}

	public String getInop() {
		return this.inop;
	}

	public void setInop(String inop) {
		this.inop = inop;
	}

	public String getInstalledAc() {
		return this.installedAc;
	}

	public void setInstalledAc(String installedAc) {
		this.installedAc = installedAc;
	}

	public Date getInstalledDate() {
		return this.installedDate;
	}

	public void setInstalledDate(Date installedDate) {
		this.installedDate = installedDate;
	}

	public BigDecimal getInstalledHour() {
		return this.installedHour;
	}

	public void setInstalledHour(BigDecimal installedHour) {
		this.installedHour = installedHour;
	}

	public BigDecimal getInstalledMinute() {
		return this.installedMinute;
	}

	public void setInstalledMinute(BigDecimal installedMinute) {
		this.installedMinute = installedMinute;
	}

	public String getInstalledPosition() {
		return this.installedPosition;
	}

	public void setInstalledPosition(String installedPosition) {
		this.installedPosition = installedPosition;
	}

	public Date getInterfaceTransferredDate() {
		return this.interfaceTransferredDate;
	}

	public void setInterfaceTransferredDate(Date interfaceTransferredDate) {
		this.interfaceTransferredDate = interfaceTransferredDate;
	}

	public String getInventoryType() {
		return this.inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public BigDecimal getKitNo() {
		return this.kitNo;
	}

	public void setKitNo(BigDecimal kitNo) {
		this.kitNo = kitNo;
	}

	public String getLegacyBatch() {
		return this.legacyBatch;
	}

	public void setLegacyBatch(String legacyBatch) {
		this.legacyBatch = legacyBatch;
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getLengthUnits() {
		return this.lengthUnits;
	}

	public void setLengthUnits(String lengthUnits) {
		this.lengthUnits = lengthUnits;
	}

	public String getLicenseType() {
		return this.licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getLoanCategory() {
		return this.loanCategory;
	}

	public void setLoanCategory(String loanCategory) {
		this.loanCategory = loanCategory;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getManagedWo() {
		return this.managedWo;
	}

	public void setManagedWo(BigDecimal managedWo) {
		this.managedWo = managedWo;
	}

	public String getMissing() {
		return this.missing;
	}

	public void setMissing(String missing) {
		this.missing = missing;
	}

	public String getModStatus() {
		return this.modStatus;
	}

	public void setModStatus(String modStatus) {
		this.modStatus = modStatus;
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

	public String getNhaPn() {
		return this.nhaPn;
	}

	public void setNhaPn(String nhaPn) {
		this.nhaPn = nhaPn;
	}

	public String getNhaSn() {
		return this.nhaSn;
	}

	public void setNhaSn(String nhaSn) {
		this.nhaSn = nhaSn;
	}

	public String getNlaPosition() {
		return this.nlaPosition;
	}

	public void setNlaPosition(String nlaPosition) {
		this.nlaPosition = nlaPosition;
	}

	public BigDecimal getNoOfTagPrint() {
		return this.noOfTagPrint;
	}

	public void setNoOfTagPrint(BigDecimal noOfTagPrint) {
		this.noOfTagPrint = noOfTagPrint;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public Date getOrginalRecordsDate() {
		return this.orginalRecordsDate;
	}

	public void setOrginalRecordsDate(Date orginalRecordsDate) {
		this.orginalRecordsDate = orginalRecordsDate;
	}

	public String getOriginalAc() {
		return this.originalAc;
	}

	public void setOriginalAc(String originalAc) {
		this.originalAc = originalAc;
	}

	public String getOriginalAcIssueToOnly() {
		return this.originalAcIssueToOnly;
	}

	public void setOriginalAcIssueToOnly(String originalAcIssueToOnly) {
		this.originalAcIssueToOnly = originalAcIssueToOnly;
	}

	public String getOriginalRecordsBy() {
		return this.originalRecordsBy;
	}

	public void setOriginalRecordsBy(String originalRecordsBy) {
		this.originalRecordsBy = originalRecordsBy;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerOrigin() {
		return this.ownerOrigin;
	}

	public void setOwnerOrigin(String ownerOrigin) {
		this.ownerOrigin = ownerOrigin;
	}

	public String getPending() {
		return this.pending;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

	public Date getPendingDate() {
		return this.pendingDate;
	}

	public void setPendingDate(Date pendingDate) {
		this.pendingDate = pendingDate;
	}

	public String getPhysicalCountBy() {
		return this.physicalCountBy;
	}

	public void setPhysicalCountBy(String physicalCountBy) {
		this.physicalCountBy = physicalCountBy;
	}

	public String getPhysicalCountByPrev() {
		return this.physicalCountByPrev;
	}

	public void setPhysicalCountByPrev(String physicalCountByPrev) {
		this.physicalCountByPrev = physicalCountByPrev;
	}

	public Date getPhysicalCountDate() {
		return this.physicalCountDate;
	}

	public void setPhysicalCountDate(Date physicalCountDate) {
		this.physicalCountDate = physicalCountDate;
	}

	public Date getPhysicalCountDatePrev() {
		return this.physicalCountDatePrev;
	}

	public void setPhysicalCountDatePrev(Date physicalCountDatePrev) {
		this.physicalCountDatePrev = physicalCountDatePrev;
	}

	public String getPn() {
		return this.pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getPnModel() {
		return this.pnModel;
	}

	public void setPnModel(String pnModel) {
		this.pnModel = pnModel;
	}

	public String getPnSoftware() {
		return this.pnSoftware;
	}

	public void setPnSoftware(String pnSoftware) {
		this.pnSoftware = pnSoftware;
	}

	public BigDecimal getPnSwConfigTrans() {
		return this.pnSwConfigTrans;
	}

	public void setPnSwConfigTrans(BigDecimal pnSwConfigTrans) {
		this.pnSwConfigTrans = pnSwConfigTrans;
	}

	public BigDecimal getQtyAvailable() {
		return this.qtyAvailable;
	}

	public void setQtyAvailable(BigDecimal qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	public BigDecimal getQtyInRental() {
		return this.qtyInRental;
	}

	public void setQtyInRental(BigDecimal qtyInRental) {
		this.qtyInRental = qtyInRental;
	}

	public BigDecimal getQtyInRepair() {
		return this.qtyInRepair;
	}

	public void setQtyInRepair(BigDecimal qtyInRepair) {
		this.qtyInRepair = qtyInRepair;
	}

	public BigDecimal getQtyInTransfer() {
		return this.qtyInTransfer;
	}

	public void setQtyInTransfer(BigDecimal qtyInTransfer) {
		this.qtyInTransfer = qtyInTransfer;
	}

	public BigDecimal getQtyLicense() {
		return this.qtyLicense;
	}

	public void setQtyLicense(BigDecimal qtyLicense) {
		this.qtyLicense = qtyLicense;
	}

	public BigDecimal getQtyOnHold() {
		return this.qtyOnHold;
	}

	public void setQtyOnHold(BigDecimal qtyOnHold) {
		this.qtyOnHold = qtyOnHold;
	}

	public BigDecimal getQtyPendingRi() {
		return this.qtyPendingRi;
	}

	public void setQtyPendingRi(BigDecimal qtyPendingRi) {
		this.qtyPendingRi = qtyPendingRi;
	}

	public BigDecimal getQtyReserved() {
		return this.qtyReserved;
	}

	public void setQtyReserved(BigDecimal qtyReserved) {
		this.qtyReserved = qtyReserved;
	}

	public BigDecimal getQtyUs() {
		return this.qtyUs;
	}

	public void setQtyUs(BigDecimal qtyUs) {
		this.qtyUs = qtyUs;
	}

	public Date getRepairReturnDate() {
		return this.repairReturnDate;
	}

	public void setRepairReturnDate(Date repairReturnDate) {
		this.repairReturnDate = repairReturnDate;
	}

	public String getRfidEpc() {
		return this.rfidEpc;
	}

	public void setRfidEpc(String rfidEpc) {
		this.rfidEpc = rfidEpc;
	}

	public String getRiBy() {
		return this.riBy;
	}

	public void setRiBy(String riBy) {
		this.riBy = riBy;
	}

	public String getRiControl() {
		return this.riControl;
	}

	public void setRiControl(String riControl) {
		this.riControl = riControl;
	}

	public Date getRiDate() {
		return this.riDate;
	}

	public void setRiDate(Date riDate) {
		this.riDate = riDate;
	}

	public BigDecimal getRoFollowingIdSn() {
		return this.roFollowingIdSn;
	}

	public void setRoFollowingIdSn(BigDecimal roFollowingIdSn) {
		this.roFollowingIdSn = roFollowingIdSn;
	}

	public String getScrapCode() {
		return this.scrapCode;
	}

	public void setScrapCode(String scrapCode) {
		this.scrapCode = scrapCode;
	}

	public BigDecimal getSecondaryCost() {
		return this.secondaryCost;
	}

	public void setSecondaryCost(BigDecimal secondaryCost) {
		this.secondaryCost = secondaryCost;
	}

	public BigDecimal getSecondaryCurrencyExchange() {
		return this.secondaryCurrencyExchange;
	}

	public void setSecondaryCurrencyExchange(BigDecimal secondaryCurrencyExchange) {
		this.secondaryCurrencyExchange = secondaryCurrencyExchange;
	}

	public Date getShelfLifeExpEom() {
		return this.shelfLifeExpEom;
	}

	public void setShelfLifeExpEom(Date shelfLifeExpEom) {
		this.shelfLifeExpEom = shelfLifeExpEom;
	}

	public Date getShelfLifeExpiration() {
		return this.shelfLifeExpiration;
	}

	public void setShelfLifeExpiration(Date shelfLifeExpiration) {
		this.shelfLifeExpiration = shelfLifeExpiration;
	}

	public Date getShelfLifeExpirationLast() {
		return this.shelfLifeExpirationLast;
	}

	public void setShelfLifeExpirationLast(Date shelfLifeExpirationLast) {
		this.shelfLifeExpirationLast = shelfLifeExpirationLast;
	}

	public BigDecimal getSlot() {
		return this.slot;
	}

	public void setSlot(BigDecimal slot) {
		this.slot = slot;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Date getSosDate() {
		return this.sosDate;
	}

	public void setSosDate(Date sosDate) {
		this.sosDate = sosDate;
	}

	public BigDecimal getSosHour() {
		return this.sosHour;
	}

	public void setSosHour(BigDecimal sosHour) {
		this.sosHour = sosHour;
	}

	public BigDecimal getSosMinute() {
		return this.sosMinute;
	}

	public void setSosMinute(BigDecimal sosMinute) {
		this.sosMinute = sosMinute;
	}

	public String getSuperiorEquipment() {
		return this.superiorEquipment;
	}

	public void setSuperiorEquipment(String superiorEquipment) {
		this.superiorEquipment = superiorEquipment;
	}

	public String getTagBy() {
		return this.tagBy;
	}

	public void setTagBy(String tagBy) {
		this.tagBy = tagBy;
	}

	public Date getTagDate() {
		return this.tagDate;
	}

	public void setTagDate(Date tagDate) {
		this.tagDate = tagDate;
	}

	public String getTagNo() {
		return this.tagNo;
	}

	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}

	public String getToolCalibrationNo() {
		return this.toolCalibrationNo;
	}

	public void setToolCalibrationNo(String toolCalibrationNo) {
		this.toolCalibrationNo = toolCalibrationNo;
	}

	public BigDecimal getToolLifeConcessionDays() {
		return this.toolLifeConcessionDays;
	}

	public void setToolLifeConcessionDays(BigDecimal toolLifeConcessionDays) {
		this.toolLifeConcessionDays = toolLifeConcessionDays;
	}

	public Date getToolLifeExpEom() {
		return this.toolLifeExpEom;
	}

	public void setToolLifeExpEom(Date toolLifeExpEom) {
		this.toolLifeExpEom = toolLifeExpEom;
	}

	public Date getToolLifeExpiration() {
		return this.toolLifeExpiration;
	}

	public void setToolLifeExpiration(Date toolLifeExpiration) {
		this.toolLifeExpiration = toolLifeExpiration;
	}

	public Date getToolLifeExpirationLast() {
		return this.toolLifeExpirationLast;
	}

	public void setToolLifeExpirationLast(Date toolLifeExpirationLast) {
		this.toolLifeExpirationLast = toolLifeExpirationLast;
	}

	public String getUmAxial() {
		return this.umAxial;
	}

	public void setUmAxial(String umAxial) {
		this.umAxial = umAxial;
	}

	public String getUmRadial() {
		return this.umRadial;
	}

	public void setUmRadial(String umRadial) {
		this.umRadial = umRadial;
	}

	public String getUmTangential() {
		return this.umTangential;
	}

	public void setUmTangential(String umTangential) {
		this.umTangential = umTangential;
	}

	public BigDecimal getUnitCost() {
		return this.unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public String getUsCode() {
		return this.usCode;
	}

	public void setUsCode(String usCode) {
		this.usCode = usCode;
	}

	public String getUsNumber() {
		return this.usNumber;
	}

	public void setUsNumber(String usNumber) {
		this.usNumber = usNumber;
	}

	public String getVendorLot() {
		return this.vendorLot;
	}

	public void setVendorLot(String vendorLot) {
		this.vendorLot = vendorLot;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return this.weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public String getWidthUnits() {
		return this.widthUnits;
	}

	public void setWidthUnits(String widthUnits) {
		this.widthUnits = widthUnits;
	}

}