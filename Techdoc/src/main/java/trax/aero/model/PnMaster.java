package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PN_MASTER database table.
 * 
 */
@Entity
@Table(name="PN_MASTER")
@NamedQuery(name="PnMaster.findAll", query="SELECT p FROM PnMaster p")
public class PnMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String pn;

	@Column(name="ADDITIONAL_INFORMATION")
	private String additionalInformation;

	@Column(name="ALERT_CATEGORY")
	private String alertCategory;

	@Column(name="ALERT_CATEGORY_NO_OF")
	private BigDecimal alertCategoryNoOf;

	@Column(name="ALERT_LEVEL")
	private BigDecimal alertLevel;

	@Column(name="ALERT_LEVEL_SECOND")
	private BigDecimal alertLevelSecond;

	@Column(name="ALERT_NO_OF_REMOVAL")
	private BigDecimal alertNoOfRemoval;

	@Column(name="ALT_PN_DESCRIPTION")
	private String altPnDescription;

	private String apu;

	@Column(name="APU_READING")
	private String apuReading;

	@Column(name="AUDIT_PROCESSED")
	private String auditProcessed;

	@Column(name="AUTO_INSTALL_POSITION")
	private String autoInstallPosition;

	@Column(name="AUTO_INSTALLED")
	private String autoInstalled;

	@Column(name="AUTO_SN_CREATE")
	private String autoSnCreate;

	@Column(name="AUTO_SN_PREFIX")
	private String autoSnPrefix;

	@Column(name="AUTOMATED_WAREHOUSE_CAPABLE")
	private String automatedWarehouseCapable;

	@Column(name="AVERAGE_COST")
	private BigDecimal averageCost;

	@Column(name="AVIONIC_PART")
	private String avionicPart;

	@Column(name="AXIAL_WEIGHT")
	private String axialWeight;

	@Column(name="BIN_CAT")
	private String binCat;

	@Column(name="BIN_CAT2")
	private String binCat2;

	@Column(name="BIN_ORIENTATION")
	private String binOrientation;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CARRYING_COST_FACTOR")
	private BigDecimal carryingCostFactor;

	@Column(name="CAT_RATING")
	private String catRating;

	private String category;

	@Column(name="CC_REFERENCE")
	private String ccReference;

	private BigDecimal chapter;

	@Column(name="CHECK_L")
	private String checkL;

	@Column(name="CHEMICAL_GUIDE_BLOB")
	private BigDecimal chemicalGuideBlob;

	@Column(name="CHEMICAL_GUIDE_DOC_NO")
	private BigDecimal chemicalGuideDocNo;

	@Column(name="CHEMICAL_GUIDE_SHEET")
	private String chemicalGuideSheet;

	@Column(name="CLASS_DIVISION")
	private String classDivision;

	@Column(name="CLIMATE_CONTROLLED")
	private String climateControlled;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CREATED_FROM_TRI")
	private BigDecimal createdFromTri;

	@Column(name="CRITICAL_LEVEL")
	private String criticalLevel;

	@Column(name="CUSTOM_DUTY")
	private String customDuty;

	@Column(name="CUSTOM_DUTY_DOCUMENT")
	private String customDutyDocument;

	@Column(name="CYCLE_COUNT_CODE")
	private String cycleCountCode;

	@Column(name="DAY_SPEC_REQUIRED")
	private String daySpecRequired;

	@Column(name="DIM_CONTROL_AT_BATCH_LEVEL")
	private String dimControlAtBatchLevel;

	@Column(name="\"DISK\"")
	private String disk;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	private String ectm;

	@Column(name="EFFECTIVITY_PN_INTERCHANGEABLE")
	private String effectivityPnInterchangeable;

	@Lob
	private String efftext;

	private String engine;

	@Column(name="ESSENTIALITY_CODE")
	private String essentialityCode;

	private String etops;

	@Column(name="ETOPS_FLAG")
	private String etopsFlag;

	@Column(name="ETOPS_NOTE")
	private String etopsNote;

	@Column(name="EX_GL")
	private String exGl;

	@Column(name="EX_GL_2")
	private String exGl2;

	@Column(name="EX_GL_COMPANY")
	private String exGlCompany;

	@Column(name="EX_GL_COMPANY_2")
	private String exGlCompany2;

	@Column(name="EX_GL_COST_CENTER")
	private String exGlCostCenter;

	@Column(name="EX_GL_COST_CENTER_2")
	private String exGlCostCenter2;

	@Column(name="EX_GL_EXPENDITURE")
	private String exGlExpenditure;

	@Column(name="EX_GL_EXPENDITURE_2")
	private String exGlExpenditure2;

	@Column(name="EXCHANGE_ORDER_NOTES")
	private BigDecimal exchangeOrderNotes;

	private String expenditure;

	@Column(name="FA_DEPRECIATION_CODE")
	private String faDepreciationCode;

	@Column(name="FA_NO_MONTH_DEPRECIATION")
	private BigDecimal faNoMonthDepreciation;

	@Column(name="FA_RESIDUAL_VALUE_PERCENT")
	private BigDecimal faResidualValuePercent;

	@Column(name="FACTOR_CONTROL")
	private BigDecimal factorControl;

	@Column(name="FACTOR_CONTROL_CYCLES")
	private BigDecimal factorControlCycles;

	@Column(name="FCC_FORM")
	private String fccForm;

	private String gl;

	@Column(name="GL_2")
	private String gl2;

	@Column(name="GL_COMPANY")
	private String glCompany;

	@Column(name="GL_COMPANY_2")
	private String glCompany2;

	@Column(name="GL_COST_CENTER")
	private String glCostCenter;

	@Column(name="GL_COST_CENTER_2")
	private String glCostCenter2;

	@Column(name="GL_EXPENDITURE")
	private String glExpenditure;

	@Column(name="GL_EXPENDITURE_2")
	private String glExpenditure2;

	@Column(name="HANDLING_NOTES")
	private BigDecimal handlingNotes;

	@Column(name="HAZARDOUS_MATERIAL")
	private String hazardousMaterial;

	@Column(name="HAZARDOUS_MATERIAL_AT_REMOVAL")
	private String hazardousMaterialAtRemoval;

	@Column(name="HAZARDOUS_MATERIAL_HYPERLINK")
	private String hazardousMaterialHyperlink;

	@Column(name="HAZARDOUS_MATERIAL_NO")
	private String hazardousMaterialNo;

	@Column(name="HAZARDOUS_SUBSTANCE")
	private String hazardousSubstance;

	@Column(name="HAZARDOUS_SUBSTANCE_AT_REMOVAL")
	private String hazardousSubstanceAtRemoval;

	@Column(name="HAZARDOUS_SUBSTANCE_HYPERLINK")
	private String hazardousSubstanceHyperlink;

	@Column(name="HEALTH_SAFETY")
	private String healthSafety;

	@Column(name="HEALTH_SAFETY_CLASS")
	private String healthSafetyClass;

	@Column(name="HEALTH_SAFETY_HYPERLINK")
	private String healthSafetyHyperlink;

	private BigDecimal height;

	@Column(name="HEIGHT_UNITS")
	private String heightUnits;

	@Column(name="IFACE_BAXTER_MAT_TRANSFER_DATE")
	private Date ifaceBaxterMatTransferDate;

	@Column(name="IFACE_BAXTER_MATFLD_XFER_DATE")
	private Date ifaceBaxterMatfldXferDate;

	@Column(name="INTERCHANGEABILITY_CLASS")
	private String interchangeabilityClass;

	@Column(name="INTERNAL_SHOW_CAPABILITY")
	private String internalShowCapability;

	@Column(name="INVENTORY_MONITORED")
	private String inventoryMonitored;

	@Column(name="INVENTORY_TYPE")
	private String inventoryType;

	private String ipc;

	@Column(name="ISSUED_NOTES")
	private BigDecimal issuedNotes;

	@Column(name="ITAR_CONTROLLED")
	private String itarControlled;

	@Column(name="K1_FACTOR")
	private BigDecimal k1Factor;

	@Column(name="K2_FACTOR")
	private BigDecimal k2Factor;

	@Column(name="K3_FACTOR")
	private BigDecimal k3Factor;

	private BigDecimal lenght;

	@Column(name="LENGHT_UNITS")
	private String lenghtUnits;

	private String line;

	@Column(name="LOT_CONTROL")
	private String lotControl;

	private String lru;

	@Column(name="MAINTENANCE_CONTROL")
	private String maintenanceControl;

	@Column(name="MAINTENANCE_PROGRAM")
	private String maintenanceProgram;

	@Column(name="MANUF_AFFIDAVIT")
	private String manufAffidavit;

	@Column(name="MARKET_VALUE_UNIT_COST")
	private BigDecimal marketValueUnitCost;

	private String mel;

	@Column(name="MINIMUM_SHELF_LIFE")
	private BigDecimal minimumShelfLife;

	@Column(name="MOD_STATUS")
	private String modStatus;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="\"MODULE\"")
	private String module;

	@Column(name="MRO_SUPPLIED_PART")
	private String mroSuppliedPart;

	@Column(name="NON_EXCHANGEABLE_PART")
	private String nonExchangeablePart;

	@Column(name="NON_REPAIRABLE_PART")
	private String nonRepairablePart;

	@Column(name="NOT_ALLOW_ON_CUST_ORDER_FLAG")
	private String notAllowOnCustOrderFlag;

	private BigDecimal notes;
	
	@Column(name="INTERFACE_TRANSFERRED_DATE")
	private Date interfaceTransferredDate;

	@Column(name="ORDER_NOTES")
	private BigDecimal orderNotes;

	@Column(name="ORDER_RCV_NOTES")
	private BigDecimal orderRcvNotes;

	@Column(name="OVER_NIGHT")
	private String overNight;

	@Column(name="PACKING_GROUP")
	private String packingGroup;

	private BigDecimal paragraph;

	@Column(name="PHYSICAL_COUNT_BY")
	private String physicalCountBy;

	@Column(name="PHYSICAL_COUNT_DATE")
	private String physicalCountDate;

	@Column(name="PN_DESCRIPTION")
	private String pnDescription;

	@Column(name="PN_OS_REFERENCE")
	private String pnOsReference;

	@Column(name="PN_SUPERSEDE")
	private String pnSupersede;

	@Column(name="PN_SUPERSEDE_NOTES")
	private BigDecimal pnSupersedeNotes;

	@Column(name="PN_TOTAL_TIMES_FLAG")
	private String pnTotalTimesFlag;

	@Column(name="POISSON_FACTOR")
	private String poissonFactor;

	private String preventupdatebytraxdoc;

	@Column(name="PRINT_NOTE_RECEIVING_FLAG")
	private String printNoteReceivingFlag;

	private String processed;

	@Column(name="PRORATED_FLAG")
	private String proratedFlag;

	@Column(name="PS_DOCUMENT_NO")
	private BigDecimal psDocumentNo;

	@Column(name="QTY_ASRS")
	private BigDecimal qtyAsrs;

	@Column(name="RADIAL_WEIGHT")
	private String radialWeight;

	@Column(name="REFERENCE_DOCUMENT")
	private String referenceDocument;

	@Column(name="REFERENCE_DOCUMENT_REVISION")
	private String referenceDocumentRevision;

	@Column(name="RELIABILITY_MONITORED")
	private String reliabilityMonitored;

	@Column(name="REMOVAL_INSTALLATION_AUDIT")
	private String removalInstallationAudit;

	@Column(name="REPAIR_ORDER_NOTES")
	private BigDecimal repairOrderNotes;

	@Column(name="REPLENISHMENT_COST_FACTOR")
	private BigDecimal replenishmentCostFactor;

	@Column(name="REQUIRE_TOOL_CALIBRATION_NO")
	private String requireToolCalibrationNo;

	@Column(name="REQUISITION_NOTES")
	private BigDecimal requisitionNotes;

	@Column(name="RI_FLAG")
	private String riFlag;

	@Column(name="RI_NOTES")
	private BigDecimal riNotes;

	@Column(name="RO_GL")
	private String roGl;

	@Column(name="RO_GL_2")
	private String roGl2;

	@Column(name="RO_GL_COMPANY")
	private String roGlCompany;

	@Column(name="RO_GL_COMPANY_2")
	private String roGlCompany2;

	@Column(name="RO_GL_COST_CENTER")
	private String roGlCostCenter;

	@Column(name="RO_GL_COST_CENTER_2")
	private String roGlCostCenter2;

	@Column(name="RO_GL_EXPENDITURE")
	private String roGlExpenditure;

	@Column(name="RO_GL_EXPENDITURE_2")
	private String roGlExpenditure2;

	@Column(name="RVSM_CODE")
	private String rvsmCode;

	@Column(name="RVSM_FLAG")
	private String rvsmFlag;

	@Column(name="SECONDARY_COST")
	private BigDecimal secondaryCost;

	@Column(name="\"SECTION\"")
	private BigDecimal section;

	@Column(name="SHELF_LIFE_DAYS")
	private BigDecimal shelfLifeDays;

	@Column(name="SHELF_LIFE_EOM")
	private String shelfLifeEom;

	@Column(name="SHELF_LIFE_FLAG")
	private String shelfLifeFlag;

	@Column(name="SHELF_LIFE_NOTES")
	private BigDecimal shelfLifeNotes;

	@Column(name="SHIPPING_NOTES")
	private BigDecimal shippingNotes;

	@Column(name="SHOCK_WATCH")
	private String shockWatch;

	private String software;

	@Column(name="SOS_ELIGIBLE_PART")
	private String sosEligiblePart;

	@Column(name="SPEC_NUMBER")
	private String specNumber;

	@Column(name="STANDARD_COST")
	private BigDecimal standardCost;

	@Column(name="STATIC_SENSITIVE_PART")
	private String staticSensitivePart;

	private String status;

	@Column(name="STOCK_UOM")
	private String stockUom;

	@Column(name="STORE_EXCEPTION")
	private String storeException;

	@Column(name="STORE_EXCEPTION_SHC_1")
	private String storeExceptionShc1;

	@Column(name="STORE_EXCEPTION_SHC_2")
	private String storeExceptionShc2;

	@Column(name="STORE_EXCEPTION_SHC_ID")
	private String storeExceptionShcId;

	@Column(name="SUB_CATEGORY")
	private String subCategory;

	@Column(name="T_PED")
	private String tPed;

	@Column(name="TANGENTIAL_WEIGHT")
	private String tangentialWeight;

	private String taxable;

	@Column(name="TOOL_CALIBRATION_DESCRIPTION")
	private String toolCalibrationDescription;

	@Column(name="TOOL_CALIBRATION_FLAG")
	private String toolCalibrationFlag;

	@Column(name="TOOL_CONTROL_ITEM")
	private String toolControlItem;

	@Column(name="TOOL_LIFE_DAYS")
	private BigDecimal toolLifeDays;

	@Column(name="TOOL_LIFE_EOM")
	private String toolLifeEom;

	@Column(name="TRANSACTION_DOCUMENT_NO")
	private BigDecimal transactionDocumentNo;

	@Column(name="TRAXDOC_CREATED_DATE")
	private Date traxdocCreatedDate;

	@Column(name="TRAXDOC_FILE_NAME_PATH")
	private String traxdocFileNamePath;

	@Column(name="TRAXDOC_ROW_ID")
	private BigDecimal traxdocRowId;

	@Column(name="TRAXDOC_ROW_ID_FATHER")
	private BigDecimal traxdocRowIdFather;

	@Column(name="TRAY_TYPE")
	private String trayType;

	private String turn;

	@Column(name="UNLIMITED_SHELF_LIFE_FLAG")
	private String unlimitedShelfLifeFlag;

	private String vendor;

	private BigDecimal weight;

	@Column(name="WEIGHT_CONTROL_AT_BATCH_LEVEL")
	private String weightControlAtBatchLevel;

	@Column(name="WEIGHT_UNIT")
	private String weightUnit;

	private BigDecimal width;

	@Column(name="WIDTH_UNITS")
	private String widthUnits;

	public PnMaster() {
	}

	public String getPn() {
		return this.pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getAdditionalInformation() {
		return this.additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getAlertCategory() {
		return this.alertCategory;
	}

	public void setAlertCategory(String alertCategory) {
		this.alertCategory = alertCategory;
	}

	public BigDecimal getAlertCategoryNoOf() {
		return this.alertCategoryNoOf;
	}

	public void setAlertCategoryNoOf(BigDecimal alertCategoryNoOf) {
		this.alertCategoryNoOf = alertCategoryNoOf;
	}

	public BigDecimal getAlertLevel() {
		return this.alertLevel;
	}

	public void setAlertLevel(BigDecimal alertLevel) {
		this.alertLevel = alertLevel;
	}

	public BigDecimal getAlertLevelSecond() {
		return this.alertLevelSecond;
	}

	public void setAlertLevelSecond(BigDecimal alertLevelSecond) {
		this.alertLevelSecond = alertLevelSecond;
	}

	public BigDecimal getAlertNoOfRemoval() {
		return this.alertNoOfRemoval;
	}

	public void setAlertNoOfRemoval(BigDecimal alertNoOfRemoval) {
		this.alertNoOfRemoval = alertNoOfRemoval;
	}

	public String getAltPnDescription() {
		return this.altPnDescription;
	}

	public void setAltPnDescription(String altPnDescription) {
		this.altPnDescription = altPnDescription;
	}

	public String getApu() {
		return this.apu;
	}

	public void setApu(String apu) {
		this.apu = apu;
	}

	public String getApuReading() {
		return this.apuReading;
	}

	public void setApuReading(String apuReading) {
		this.apuReading = apuReading;
	}

	public String getAuditProcessed() {
		return this.auditProcessed;
	}

	public void setAuditProcessed(String auditProcessed) {
		this.auditProcessed = auditProcessed;
	}

	public String getAutoInstallPosition() {
		return this.autoInstallPosition;
	}

	public void setAutoInstallPosition(String autoInstallPosition) {
		this.autoInstallPosition = autoInstallPosition;
	}

	public String getAutoInstalled() {
		return this.autoInstalled;
	}

	public void setAutoInstalled(String autoInstalled) {
		this.autoInstalled = autoInstalled;
	}

	public String getAutoSnCreate() {
		return this.autoSnCreate;
	}

	public void setAutoSnCreate(String autoSnCreate) {
		this.autoSnCreate = autoSnCreate;
	}

	public String getAutoSnPrefix() {
		return this.autoSnPrefix;
	}

	public void setAutoSnPrefix(String autoSnPrefix) {
		this.autoSnPrefix = autoSnPrefix;
	}

	public String getAutomatedWarehouseCapable() {
		return this.automatedWarehouseCapable;
	}

	public void setAutomatedWarehouseCapable(String automatedWarehouseCapable) {
		this.automatedWarehouseCapable = automatedWarehouseCapable;
	}

	public BigDecimal getAverageCost() {
		return this.averageCost;
	}

	public void setAverageCost(BigDecimal averageCost) {
		this.averageCost = averageCost;
	}

	public String getAvionicPart() {
		return this.avionicPart;
	}

	public void setAvionicPart(String avionicPart) {
		this.avionicPart = avionicPart;
	}

	public String getAxialWeight() {
		return this.axialWeight;
	}

	public void setAxialWeight(String axialWeight) {
		this.axialWeight = axialWeight;
	}

	public String getBinCat() {
		return this.binCat;
	}

	public void setBinCat(String binCat) {
		this.binCat = binCat;
	}

	public String getBinCat2() {
		return this.binCat2;
	}

	public void setBinCat2(String binCat2) {
		this.binCat2 = binCat2;
	}

	public String getBinOrientation() {
		return this.binOrientation;
	}

	public void setBinOrientation(String binOrientation) {
		this.binOrientation = binOrientation;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public BigDecimal getCarryingCostFactor() {
		return this.carryingCostFactor;
	}

	public void setCarryingCostFactor(BigDecimal carryingCostFactor) {
		this.carryingCostFactor = carryingCostFactor;
	}

	public String getCatRating() {
		return this.catRating;
	}

	public void setCatRating(String catRating) {
		this.catRating = catRating;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCcReference() {
		return this.ccReference;
	}

	public void setCcReference(String ccReference) {
		this.ccReference = ccReference;
	}

	public BigDecimal getChapter() {
		return this.chapter;
	}

	public void setChapter(BigDecimal chapter) {
		this.chapter = chapter;
	}

	public String getCheckL() {
		return this.checkL;
	}

	public void setCheckL(String checkL) {
		this.checkL = checkL;
	}

	public BigDecimal getChemicalGuideBlob() {
		return this.chemicalGuideBlob;
	}

	public void setChemicalGuideBlob(BigDecimal chemicalGuideBlob) {
		this.chemicalGuideBlob = chemicalGuideBlob;
	}

	public BigDecimal getChemicalGuideDocNo() {
		return this.chemicalGuideDocNo;
	}

	public void setChemicalGuideDocNo(BigDecimal chemicalGuideDocNo) {
		this.chemicalGuideDocNo = chemicalGuideDocNo;
	}

	public String getChemicalGuideSheet() {
		return this.chemicalGuideSheet;
	}

	public void setChemicalGuideSheet(String chemicalGuideSheet) {
		this.chemicalGuideSheet = chemicalGuideSheet;
	}

	public String getClassDivision() {
		return this.classDivision;
	}

	public void setClassDivision(String classDivision) {
		this.classDivision = classDivision;
	}

	public String getClimateControlled() {
		return this.climateControlled;
	}

	public void setClimateControlled(String climateControlled) {
		this.climateControlled = climateControlled;
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

	public BigDecimal getCreatedFromTri() {
		return this.createdFromTri;
	}

	public void setCreatedFromTri(BigDecimal createdFromTri) {
		this.createdFromTri = createdFromTri;
	}

	public String getCriticalLevel() {
		return this.criticalLevel;
	}

	public void setCriticalLevel(String criticalLevel) {
		this.criticalLevel = criticalLevel;
	}

	public String getCustomDuty() {
		return this.customDuty;
	}

	public void setCustomDuty(String customDuty) {
		this.customDuty = customDuty;
	}

	public String getCustomDutyDocument() {
		return this.customDutyDocument;
	}

	public void setCustomDutyDocument(String customDutyDocument) {
		this.customDutyDocument = customDutyDocument;
	}

	public String getCycleCountCode() {
		return this.cycleCountCode;
	}

	public void setCycleCountCode(String cycleCountCode) {
		this.cycleCountCode = cycleCountCode;
	}

	public String getDaySpecRequired() {
		return this.daySpecRequired;
	}

	public void setDaySpecRequired(String daySpecRequired) {
		this.daySpecRequired = daySpecRequired;
	}

	public String getDimControlAtBatchLevel() {
		return this.dimControlAtBatchLevel;
	}

	public void setDimControlAtBatchLevel(String dimControlAtBatchLevel) {
		this.dimControlAtBatchLevel = dimControlAtBatchLevel;
	}

	public String getDisk() {
		return this.disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getEctm() {
		return this.ectm;
	}

	public void setEctm(String ectm) {
		this.ectm = ectm;
	}

	public String getEffectivityPnInterchangeable() {
		return this.effectivityPnInterchangeable;
	}

	public void setEffectivityPnInterchangeable(String effectivityPnInterchangeable) {
		this.effectivityPnInterchangeable = effectivityPnInterchangeable;
	}

	public String getEfftext() {
		return this.efftext;
	}

	public void setEfftext(String efftext) {
		this.efftext = efftext;
	}

	public String getEngine() {
		return this.engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getEssentialityCode() {
		return this.essentialityCode;
	}

	public void setEssentialityCode(String essentialityCode) {
		this.essentialityCode = essentialityCode;
	}

	public String getEtops() {
		return this.etops;
	}

	public void setEtops(String etops) {
		this.etops = etops;
	}

	public String getEtopsFlag() {
		return this.etopsFlag;
	}

	public void setEtopsFlag(String etopsFlag) {
		this.etopsFlag = etopsFlag;
	}

	public String getEtopsNote() {
		return this.etopsNote;
	}

	public void setEtopsNote(String etopsNote) {
		this.etopsNote = etopsNote;
	}

	public String getExGl() {
		return this.exGl;
	}

	public void setExGl(String exGl) {
		this.exGl = exGl;
	}

	public String getExGl2() {
		return this.exGl2;
	}

	public void setExGl2(String exGl2) {
		this.exGl2 = exGl2;
	}

	public String getExGlCompany() {
		return this.exGlCompany;
	}

	public void setExGlCompany(String exGlCompany) {
		this.exGlCompany = exGlCompany;
	}

	public String getExGlCompany2() {
		return this.exGlCompany2;
	}

	public void setExGlCompany2(String exGlCompany2) {
		this.exGlCompany2 = exGlCompany2;
	}

	public String getExGlCostCenter() {
		return this.exGlCostCenter;
	}

	public void setExGlCostCenter(String exGlCostCenter) {
		this.exGlCostCenter = exGlCostCenter;
	}

	public String getExGlCostCenter2() {
		return this.exGlCostCenter2;
	}

	public void setExGlCostCenter2(String exGlCostCenter2) {
		this.exGlCostCenter2 = exGlCostCenter2;
	}

	public String getExGlExpenditure() {
		return this.exGlExpenditure;
	}

	public void setExGlExpenditure(String exGlExpenditure) {
		this.exGlExpenditure = exGlExpenditure;
	}

	public String getExGlExpenditure2() {
		return this.exGlExpenditure2;
	}

	public void setExGlExpenditure2(String exGlExpenditure2) {
		this.exGlExpenditure2 = exGlExpenditure2;
	}

	public BigDecimal getExchangeOrderNotes() {
		return this.exchangeOrderNotes;
	}

	public void setExchangeOrderNotes(BigDecimal exchangeOrderNotes) {
		this.exchangeOrderNotes = exchangeOrderNotes;
	}

	public String getExpenditure() {
		return this.expenditure;
	}

	public void setExpenditure(String expenditure) {
		this.expenditure = expenditure;
	}

	public String getFaDepreciationCode() {
		return this.faDepreciationCode;
	}

	public void setFaDepreciationCode(String faDepreciationCode) {
		this.faDepreciationCode = faDepreciationCode;
	}

	public BigDecimal getFaNoMonthDepreciation() {
		return this.faNoMonthDepreciation;
	}

	public void setFaNoMonthDepreciation(BigDecimal faNoMonthDepreciation) {
		this.faNoMonthDepreciation = faNoMonthDepreciation;
	}

	public BigDecimal getFaResidualValuePercent() {
		return this.faResidualValuePercent;
	}

	public void setFaResidualValuePercent(BigDecimal faResidualValuePercent) {
		this.faResidualValuePercent = faResidualValuePercent;
	}

	public BigDecimal getFactorControl() {
		return this.factorControl;
	}

	public void setFactorControl(BigDecimal factorControl) {
		this.factorControl = factorControl;
	}

	public BigDecimal getFactorControlCycles() {
		return this.factorControlCycles;
	}

	public void setFactorControlCycles(BigDecimal factorControlCycles) {
		this.factorControlCycles = factorControlCycles;
	}

	public String getFccForm() {
		return this.fccForm;
	}

	public void setFccForm(String fccForm) {
		this.fccForm = fccForm;
	}

	public String getGl() {
		return this.gl;
	}

	public void setGl(String gl) {
		this.gl = gl;
	}

	public String getGl2() {
		return this.gl2;
	}

	public void setGl2(String gl2) {
		this.gl2 = gl2;
	}

	public String getGlCompany() {
		return this.glCompany;
	}

	public void setGlCompany(String glCompany) {
		this.glCompany = glCompany;
	}

	public String getGlCompany2() {
		return this.glCompany2;
	}

	public void setGlCompany2(String glCompany2) {
		this.glCompany2 = glCompany2;
	}

	public String getGlCostCenter() {
		return this.glCostCenter;
	}

	public void setGlCostCenter(String glCostCenter) {
		this.glCostCenter = glCostCenter;
	}

	public String getGlCostCenter2() {
		return this.glCostCenter2;
	}

	public void setGlCostCenter2(String glCostCenter2) {
		this.glCostCenter2 = glCostCenter2;
	}

	public String getGlExpenditure() {
		return this.glExpenditure;
	}

	public void setGlExpenditure(String glExpenditure) {
		this.glExpenditure = glExpenditure;
	}

	public String getGlExpenditure2() {
		return this.glExpenditure2;
	}

	public void setGlExpenditure2(String glExpenditure2) {
		this.glExpenditure2 = glExpenditure2;
	}

	public BigDecimal getHandlingNotes() {
		return this.handlingNotes;
	}

	public void setHandlingNotes(BigDecimal handlingNotes) {
		this.handlingNotes = handlingNotes;
	}

	public String getHazardousMaterial() {
		return this.hazardousMaterial;
	}

	public void setHazardousMaterial(String hazardousMaterial) {
		this.hazardousMaterial = hazardousMaterial;
	}

	public String getHazardousMaterialAtRemoval() {
		return this.hazardousMaterialAtRemoval;
	}

	public void setHazardousMaterialAtRemoval(String hazardousMaterialAtRemoval) {
		this.hazardousMaterialAtRemoval = hazardousMaterialAtRemoval;
	}

	public String getHazardousMaterialHyperlink() {
		return this.hazardousMaterialHyperlink;
	}

	public void setHazardousMaterialHyperlink(String hazardousMaterialHyperlink) {
		this.hazardousMaterialHyperlink = hazardousMaterialHyperlink;
	}

	public String getHazardousMaterialNo() {
		return this.hazardousMaterialNo;
	}

	public void setHazardousMaterialNo(String hazardousMaterialNo) {
		this.hazardousMaterialNo = hazardousMaterialNo;
	}

	public String getHazardousSubstance() {
		return this.hazardousSubstance;
	}

	public void setHazardousSubstance(String hazardousSubstance) {
		this.hazardousSubstance = hazardousSubstance;
	}

	public String getHazardousSubstanceAtRemoval() {
		return this.hazardousSubstanceAtRemoval;
	}

	public void setHazardousSubstanceAtRemoval(String hazardousSubstanceAtRemoval) {
		this.hazardousSubstanceAtRemoval = hazardousSubstanceAtRemoval;
	}

	public String getHazardousSubstanceHyperlink() {
		return this.hazardousSubstanceHyperlink;
	}

	public void setHazardousSubstanceHyperlink(String hazardousSubstanceHyperlink) {
		this.hazardousSubstanceHyperlink = hazardousSubstanceHyperlink;
	}

	public String getHealthSafety() {
		return this.healthSafety;
	}

	public void setHealthSafety(String healthSafety) {
		this.healthSafety = healthSafety;
	}

	public String getHealthSafetyClass() {
		return this.healthSafetyClass;
	}

	public void setHealthSafetyClass(String healthSafetyClass) {
		this.healthSafetyClass = healthSafetyClass;
	}

	public String getHealthSafetyHyperlink() {
		return this.healthSafetyHyperlink;
	}

	public void setHealthSafetyHyperlink(String healthSafetyHyperlink) {
		this.healthSafetyHyperlink = healthSafetyHyperlink;
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

	public Date getIfaceBaxterMatTransferDate() {
		return this.ifaceBaxterMatTransferDate;
	}

	public void setIfaceBaxterMatTransferDate(Date ifaceBaxterMatTransferDate) {
		this.ifaceBaxterMatTransferDate = ifaceBaxterMatTransferDate;
	}

	public Date getIfaceBaxterMatfldXferDate() {
		return this.ifaceBaxterMatfldXferDate;
	}

	public void setIfaceBaxterMatfldXferDate(Date ifaceBaxterMatfldXferDate) {
		this.ifaceBaxterMatfldXferDate = ifaceBaxterMatfldXferDate;
	}

	public String getInterchangeabilityClass() {
		return this.interchangeabilityClass;
	}

	public void setInterchangeabilityClass(String interchangeabilityClass) {
		this.interchangeabilityClass = interchangeabilityClass;
	}

	public String getInternalShowCapability() {
		return this.internalShowCapability;
	}

	public void setInternalShowCapability(String internalShowCapability) {
		this.internalShowCapability = internalShowCapability;
	}

	public String getInventoryMonitored() {
		return this.inventoryMonitored;
	}

	public void setInventoryMonitored(String inventoryMonitored) {
		this.inventoryMonitored = inventoryMonitored;
	}

	public String getInventoryType() {
		return this.inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getIpc() {
		return this.ipc;
	}

	public void setIpc(String ipc) {
		this.ipc = ipc;
	}

	public BigDecimal getIssuedNotes() {
		return this.issuedNotes;
	}

	public void setIssuedNotes(BigDecimal issuedNotes) {
		this.issuedNotes = issuedNotes;
	}

	public String getItarControlled() {
		return this.itarControlled;
	}

	public void setItarControlled(String itarControlled) {
		this.itarControlled = itarControlled;
	}

	public BigDecimal getK1Factor() {
		return this.k1Factor;
	}

	public void setK1Factor(BigDecimal k1Factor) {
		this.k1Factor = k1Factor;
	}

	public BigDecimal getK2Factor() {
		return this.k2Factor;
	}

	public void setK2Factor(BigDecimal k2Factor) {
		this.k2Factor = k2Factor;
	}

	public BigDecimal getK3Factor() {
		return this.k3Factor;
	}

	public void setK3Factor(BigDecimal k3Factor) {
		this.k3Factor = k3Factor;
	}

	public BigDecimal getLenght() {
		return this.lenght;
	}

	public void setLenght(BigDecimal lenght) {
		this.lenght = lenght;
	}

	public String getLenghtUnits() {
		return this.lenghtUnits;
	}

	public void setLenghtUnits(String lenghtUnits) {
		this.lenghtUnits = lenghtUnits;
	}

	public String getLine() {
		return this.line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getLotControl() {
		return this.lotControl;
	}

	public void setLotControl(String lotControl) {
		this.lotControl = lotControl;
	}

	public String getLru() {
		return this.lru;
	}

	public void setLru(String lru) {
		this.lru = lru;
	}

	public String getMaintenanceControl() {
		return this.maintenanceControl;
	}

	public void setMaintenanceControl(String maintenanceControl) {
		this.maintenanceControl = maintenanceControl;
	}

	public String getMaintenanceProgram() {
		return this.maintenanceProgram;
	}

	public void setMaintenanceProgram(String maintenanceProgram) {
		this.maintenanceProgram = maintenanceProgram;
	}

	public String getManufAffidavit() {
		return this.manufAffidavit;
	}

	public void setManufAffidavit(String manufAffidavit) {
		this.manufAffidavit = manufAffidavit;
	}

	public BigDecimal getMarketValueUnitCost() {
		return this.marketValueUnitCost;
	}

	public void setMarketValueUnitCost(BigDecimal marketValueUnitCost) {
		this.marketValueUnitCost = marketValueUnitCost;
	}

	public String getMel() {
		return this.mel;
	}

	public void setMel(String mel) {
		this.mel = mel;
	}

	public BigDecimal getMinimumShelfLife() {
		return this.minimumShelfLife;
	}

	public void setMinimumShelfLife(BigDecimal minimumShelfLife) {
		this.minimumShelfLife = minimumShelfLife;
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

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getMroSuppliedPart() {
		return this.mroSuppliedPart;
	}

	public void setMroSuppliedPart(String mroSuppliedPart) {
		this.mroSuppliedPart = mroSuppliedPart;
	}

	public String getNonExchangeablePart() {
		return this.nonExchangeablePart;
	}

	public void setNonExchangeablePart(String nonExchangeablePart) {
		this.nonExchangeablePart = nonExchangeablePart;
	}

	public String getNonRepairablePart() {
		return this.nonRepairablePart;
	}

	public void setNonRepairablePart(String nonRepairablePart) {
		this.nonRepairablePart = nonRepairablePart;
	}

	public String getNotAllowOnCustOrderFlag() {
		return this.notAllowOnCustOrderFlag;
	}

	public void setNotAllowOnCustOrderFlag(String notAllowOnCustOrderFlag) {
		this.notAllowOnCustOrderFlag = notAllowOnCustOrderFlag;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public BigDecimal getOrderNotes() {
		return this.orderNotes;
	}

	public void setOrderNotes(BigDecimal orderNotes) {
		this.orderNotes = orderNotes;
	}

	public BigDecimal getOrderRcvNotes() {
		return this.orderRcvNotes;
	}

	public void setOrderRcvNotes(BigDecimal orderRcvNotes) {
		this.orderRcvNotes = orderRcvNotes;
	}

	public String getOverNight() {
		return this.overNight;
	}

	public void setOverNight(String overNight) {
		this.overNight = overNight;
	}

	public String getPackingGroup() {
		return this.packingGroup;
	}

	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}

	public BigDecimal getParagraph() {
		return this.paragraph;
	}

	public void setParagraph(BigDecimal paragraph) {
		this.paragraph = paragraph;
	}

	public String getPhysicalCountBy() {
		return this.physicalCountBy;
	}

	public void setPhysicalCountBy(String physicalCountBy) {
		this.physicalCountBy = physicalCountBy;
	}

	public String getPhysicalCountDate() {
		return this.physicalCountDate;
	}

	public void setPhysicalCountDate(String physicalCountDate) {
		this.physicalCountDate = physicalCountDate;
	}

	public String getPnDescription() {
		return this.pnDescription;
	}

	public void setPnDescription(String pnDescription) {
		this.pnDescription = pnDescription;
	}

	public String getPnOsReference() {
		return this.pnOsReference;
	}

	public void setPnOsReference(String pnOsReference) {
		this.pnOsReference = pnOsReference;
	}

	public String getPnSupersede() {
		return this.pnSupersede;
	}

	public void setPnSupersede(String pnSupersede) {
		this.pnSupersede = pnSupersede;
	}

	public BigDecimal getPnSupersedeNotes() {
		return this.pnSupersedeNotes;
	}

	public void setPnSupersedeNotes(BigDecimal pnSupersedeNotes) {
		this.pnSupersedeNotes = pnSupersedeNotes;
	}

	public String getPnTotalTimesFlag() {
		return this.pnTotalTimesFlag;
	}

	public void setPnTotalTimesFlag(String pnTotalTimesFlag) {
		this.pnTotalTimesFlag = pnTotalTimesFlag;
	}

	public String getPoissonFactor() {
		return this.poissonFactor;
	}

	public void setPoissonFactor(String poissonFactor) {
		this.poissonFactor = poissonFactor;
	}

	public String getPreventupdatebytraxdoc() {
		return this.preventupdatebytraxdoc;
	}

	public void setPreventupdatebytraxdoc(String preventupdatebytraxdoc) {
		this.preventupdatebytraxdoc = preventupdatebytraxdoc;
	}

	public String getPrintNoteReceivingFlag() {
		return this.printNoteReceivingFlag;
	}

	public void setPrintNoteReceivingFlag(String printNoteReceivingFlag) {
		this.printNoteReceivingFlag = printNoteReceivingFlag;
	}

	public String getProcessed() {
		return this.processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getProratedFlag() {
		return this.proratedFlag;
	}

	public void setProratedFlag(String proratedFlag) {
		this.proratedFlag = proratedFlag;
	}

	public BigDecimal getPsDocumentNo() {
		return this.psDocumentNo;
	}

	public void setPsDocumentNo(BigDecimal psDocumentNo) {
		this.psDocumentNo = psDocumentNo;
	}

	public BigDecimal getQtyAsrs() {
		return this.qtyAsrs;
	}

	public void setQtyAsrs(BigDecimal qtyAsrs) {
		this.qtyAsrs = qtyAsrs;
	}

	public String getRadialWeight() {
		return this.radialWeight;
	}

	public void setRadialWeight(String radialWeight) {
		this.radialWeight = radialWeight;
	}

	public String getReferenceDocument() {
		return this.referenceDocument;
	}

	public void setReferenceDocument(String referenceDocument) {
		this.referenceDocument = referenceDocument;
	}

	public String getReferenceDocumentRevision() {
		return this.referenceDocumentRevision;
	}

	public void setReferenceDocumentRevision(String referenceDocumentRevision) {
		this.referenceDocumentRevision = referenceDocumentRevision;
	}

	public String getReliabilityMonitored() {
		return this.reliabilityMonitored;
	}

	public void setReliabilityMonitored(String reliabilityMonitored) {
		this.reliabilityMonitored = reliabilityMonitored;
	}

	public String getRemovalInstallationAudit() {
		return this.removalInstallationAudit;
	}

	public void setRemovalInstallationAudit(String removalInstallationAudit) {
		this.removalInstallationAudit = removalInstallationAudit;
	}

	public BigDecimal getRepairOrderNotes() {
		return this.repairOrderNotes;
	}

	public void setRepairOrderNotes(BigDecimal repairOrderNotes) {
		this.repairOrderNotes = repairOrderNotes;
	}

	public BigDecimal getReplenishmentCostFactor() {
		return this.replenishmentCostFactor;
	}

	public void setReplenishmentCostFactor(BigDecimal replenishmentCostFactor) {
		this.replenishmentCostFactor = replenishmentCostFactor;
	}

	public String getRequireToolCalibrationNo() {
		return this.requireToolCalibrationNo;
	}

	public void setRequireToolCalibrationNo(String requireToolCalibrationNo) {
		this.requireToolCalibrationNo = requireToolCalibrationNo;
	}

	public BigDecimal getRequisitionNotes() {
		return this.requisitionNotes;
	}

	public void setRequisitionNotes(BigDecimal requisitionNotes) {
		this.requisitionNotes = requisitionNotes;
	}

	public String getRiFlag() {
		return this.riFlag;
	}

	public void setRiFlag(String riFlag) {
		this.riFlag = riFlag;
	}

	public BigDecimal getRiNotes() {
		return this.riNotes;
	}

	public void setRiNotes(BigDecimal riNotes) {
		this.riNotes = riNotes;
	}

	public String getRoGl() {
		return this.roGl;
	}

	public void setRoGl(String roGl) {
		this.roGl = roGl;
	}

	public String getRoGl2() {
		return this.roGl2;
	}

	public void setRoGl2(String roGl2) {
		this.roGl2 = roGl2;
	}

	public String getRoGlCompany() {
		return this.roGlCompany;
	}

	public void setRoGlCompany(String roGlCompany) {
		this.roGlCompany = roGlCompany;
	}

	public String getRoGlCompany2() {
		return this.roGlCompany2;
	}

	public void setRoGlCompany2(String roGlCompany2) {
		this.roGlCompany2 = roGlCompany2;
	}

	public String getRoGlCostCenter() {
		return this.roGlCostCenter;
	}

	public void setRoGlCostCenter(String roGlCostCenter) {
		this.roGlCostCenter = roGlCostCenter;
	}

	public String getRoGlCostCenter2() {
		return this.roGlCostCenter2;
	}

	public void setRoGlCostCenter2(String roGlCostCenter2) {
		this.roGlCostCenter2 = roGlCostCenter2;
	}

	public String getRoGlExpenditure() {
		return this.roGlExpenditure;
	}

	public void setRoGlExpenditure(String roGlExpenditure) {
		this.roGlExpenditure = roGlExpenditure;
	}

	public String getRoGlExpenditure2() {
		return this.roGlExpenditure2;
	}

	public void setRoGlExpenditure2(String roGlExpenditure2) {
		this.roGlExpenditure2 = roGlExpenditure2;
	}

	public String getRvsmCode() {
		return this.rvsmCode;
	}

	public void setRvsmCode(String rvsmCode) {
		this.rvsmCode = rvsmCode;
	}

	public String getRvsmFlag() {
		return this.rvsmFlag;
	}

	public void setRvsmFlag(String rvsmFlag) {
		this.rvsmFlag = rvsmFlag;
	}

	public BigDecimal getSecondaryCost() {
		return this.secondaryCost;
	}

	public void setSecondaryCost(BigDecimal secondaryCost) {
		this.secondaryCost = secondaryCost;
	}

	public BigDecimal getSection() {
		return this.section;
	}

	public void setSection(BigDecimal section) {
		this.section = section;
	}

	public BigDecimal getShelfLifeDays() {
		return this.shelfLifeDays;
	}

	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	public String getShelfLifeEom() {
		return this.shelfLifeEom;
	}

	public void setShelfLifeEom(String shelfLifeEom) {
		this.shelfLifeEom = shelfLifeEom;
	}

	public String getShelfLifeFlag() {
		return this.shelfLifeFlag;
	}

	public void setShelfLifeFlag(String shelfLifeFlag) {
		this.shelfLifeFlag = shelfLifeFlag;
	}

	public BigDecimal getShelfLifeNotes() {
		return this.shelfLifeNotes;
	}

	public void setShelfLifeNotes(BigDecimal shelfLifeNotes) {
		this.shelfLifeNotes = shelfLifeNotes;
	}

	public BigDecimal getShippingNotes() {
		return this.shippingNotes;
	}

	public void setShippingNotes(BigDecimal shippingNotes) {
		this.shippingNotes = shippingNotes;
	}

	public String getShockWatch() {
		return this.shockWatch;
	}

	public void setShockWatch(String shockWatch) {
		this.shockWatch = shockWatch;
	}

	public String getSoftware() {
		return this.software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getSosEligiblePart() {
		return this.sosEligiblePart;
	}

	public void setSosEligiblePart(String sosEligiblePart) {
		this.sosEligiblePart = sosEligiblePart;
	}

	public String getSpecNumber() {
		return this.specNumber;
	}

	public void setSpecNumber(String specNumber) {
		this.specNumber = specNumber;
	}

	public BigDecimal getStandardCost() {
		return this.standardCost;
	}

	public void setStandardCost(BigDecimal standardCost) {
		this.standardCost = standardCost;
	}

	public String getStaticSensitivePart() {
		return this.staticSensitivePart;
	}

	public void setStaticSensitivePart(String staticSensitivePart) {
		this.staticSensitivePart = staticSensitivePart;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStockUom() {
		return this.stockUom;
	}

	public void setStockUom(String stockUom) {
		this.stockUom = stockUom;
	}

	public String getStoreException() {
		return this.storeException;
	}

	public void setStoreException(String storeException) {
		this.storeException = storeException;
	}

	public String getStoreExceptionShc1() {
		return this.storeExceptionShc1;
	}

	public void setStoreExceptionShc1(String storeExceptionShc1) {
		this.storeExceptionShc1 = storeExceptionShc1;
	}

	public String getStoreExceptionShc2() {
		return this.storeExceptionShc2;
	}

	public void setStoreExceptionShc2(String storeExceptionShc2) {
		this.storeExceptionShc2 = storeExceptionShc2;
	}

	public String getStoreExceptionShcId() {
		return this.storeExceptionShcId;
	}

	public void setStoreExceptionShcId(String storeExceptionShcId) {
		this.storeExceptionShcId = storeExceptionShcId;
	}

	public String getSubCategory() {
		return this.subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getTPed() {
		return this.tPed;
	}

	public void setTPed(String tPed) {
		this.tPed = tPed;
	}

	public String getTangentialWeight() {
		return this.tangentialWeight;
	}

	public void setTangentialWeight(String tangentialWeight) {
		this.tangentialWeight = tangentialWeight;
	}

	public String getTaxable() {
		return this.taxable;
	}

	public void setTaxable(String taxable) {
		this.taxable = taxable;
	}

	public String getToolCalibrationDescription() {
		return this.toolCalibrationDescription;
	}

	public void setToolCalibrationDescription(String toolCalibrationDescription) {
		this.toolCalibrationDescription = toolCalibrationDescription;
	}

	public String getToolCalibrationFlag() {
		return this.toolCalibrationFlag;
	}

	public void setToolCalibrationFlag(String toolCalibrationFlag) {
		this.toolCalibrationFlag = toolCalibrationFlag;
	}

	public String getToolControlItem() {
		return this.toolControlItem;
	}

	public void setToolControlItem(String toolControlItem) {
		this.toolControlItem = toolControlItem;
	}

	public BigDecimal getToolLifeDays() {
		return this.toolLifeDays;
	}

	public void setToolLifeDays(BigDecimal toolLifeDays) {
		this.toolLifeDays = toolLifeDays;
	}

	public String getToolLifeEom() {
		return this.toolLifeEom;
	}

	public void setToolLifeEom(String toolLifeEom) {
		this.toolLifeEom = toolLifeEom;
	}

	public BigDecimal getTransactionDocumentNo() {
		return this.transactionDocumentNo;
	}

	public void setTransactionDocumentNo(BigDecimal transactionDocumentNo) {
		this.transactionDocumentNo = transactionDocumentNo;
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

	public BigDecimal getTraxdocRowIdFather() {
		return this.traxdocRowIdFather;
	}

	public void setTraxdocRowIdFather(BigDecimal traxdocRowIdFather) {
		this.traxdocRowIdFather = traxdocRowIdFather;
	}

	public String getTrayType() {
		return this.trayType;
	}

	public void setTrayType(String trayType) {
		this.trayType = trayType;
	}

	public String getTurn() {
		return this.turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public String getUnlimitedShelfLifeFlag() {
		return this.unlimitedShelfLifeFlag;
	}

	public void setUnlimitedShelfLifeFlag(String unlimitedShelfLifeFlag) {
		this.unlimitedShelfLifeFlag = unlimitedShelfLifeFlag;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getWeightControlAtBatchLevel() {
		return this.weightControlAtBatchLevel;
	}

	public void setWeightControlAtBatchLevel(String weightControlAtBatchLevel) {
		this.weightControlAtBatchLevel = weightControlAtBatchLevel;
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

	public Date getInterfaceTransferredDate() {
		return interfaceTransferredDate;
	}

	public void setInterfaceTransferredDate(Date interfaceTransferredDate) {
		this.interfaceTransferredDate = interfaceTransferredDate;
	}

}