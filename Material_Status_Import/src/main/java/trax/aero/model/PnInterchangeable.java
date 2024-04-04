package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PN_INTERCHANGEABLE database table.
 * 
 */
@Entity
@Table(name="PN_INTERCHANGEABLE")
@NamedQuery(name="PnInterchangeable.findAll", query="SELECT p FROM PnInterchangeable p")
public class PnInterchangeable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PN_INTERCHANGEABLE")
	private String pnInterchangeable;

	@Column(name="ADDITIONAL_INFORMATION")
	private String additionalInformation;

	@Column(name="ALERT_CATEGORY")
	private String alertCategory;

	@Column(name="ALERT_CATEGORY_NO_OF")
	private BigDecimal alertCategoryNoOf;

	@Column(name="ALERT_NO_OF_REMOVAL")
	private BigDecimal alertNoOfRemoval;

	@Column(name="AUTHORIZATION_CODE")
	private String authorizationCode;

	@Column(name="CHECK_L")
	private String checkL;

	@Column(name="CLASS_DIVISION")
	private String classDivision;

	@Column(name="CONDITIONAL_NOTES")
	private BigDecimal conditionalNotes;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	private String eccn;

	private String ectm;

	private String etops;

	@Column(name="ETOPS_FLAG")
	private String etopsFlag;

	@Column(name="ETOPS_NOTE")
	private String etopsNote;

	@Column(name="HARMONIZED_CODE")
	private String harmonizedCode;

	@Column(name="HAZARDOUS_MATERIAL")
	private String hazardousMaterial;

	@Column(name="HAZARDOUS_MATERIAL_AT_REMOVAL")
	private String hazardousMaterialAtRemoval;

	@Column(name="HAZARDOUS_MATERIAL_HYPERLINK")
	private String hazardousMaterialHyperlink;

	@Column(name="HAZARDOUS_MATERIAL_NO")
	private String hazardousMaterialNo;

	@Column(name="HAZARDOUS_OVERIDE")
	private String hazardousOveride;

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

	private String iic;

	@Column(name="INTERCHANGEABLE_TYPE")
	private String interchangeableType;

	@Column(name="INTERFACE_TRNSFR_FINANCE_BRZL")
	private Date interfaceTrnsfrFinanceBrzl;

	@Column(name="ITAR_CONTROLLED")
	private String itarControlled;

	private String line;

	private String manufacturer;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="ORDER_NOTES")
	private BigDecimal orderNotes;

	@Column(name="OVER_NIGHT")
	private String overNight;

	@Column(name="PACKING_GROUP")
	private String packingGroup;

	private String pma;

	@Column(name="PN_CATEGORY_ACTION")
	private String pnCategoryAction;

	@Column(name="PN_OS_REFERENCE")
	private String pnOsReference;

	private String prefer;

	@Column(name="RELIABILITY_MONITORED")
	private String reliabilityMonitored;

	@Column(name="RMV_INST_ALERT_MESSAGE")
	private String rmvInstAlertMessage;

	@Column(name="RMV_INST_ALERT_MESSAGE_TO")
	private String rmvInstAlertMessageTo;

	@Column(name="RMV_INST_ALERT_NOTE")
	private BigDecimal rmvInstAlertNote;

	@Column(name="SOFTWARE_OVERRIDE_FROM_MAIN")
	private String softwareOverrideFromMain;

	private String status;

	@Column(name="T_PED")
	private String tPed;

	@Column(name="TRANSACTION_DOC_NUM")
	private BigDecimal transactionDocNum;

	@Column(name="TRAXDOC_CREATED_DATE")
	private Date traxdocCreatedDate;

	@Column(name="TRAXDOC_FILE_NAME_PATH")
	private String traxdocFileNamePath;

	@Column(name="TRAXDOC_ROW_ID")
	private BigDecimal traxdocRowId;

	private String turn;

	private String vendor;

	//bi-directional many-to-one association to PnMaster
	@ManyToOne
	@JoinColumn(name="PN")
	private PnMaster pnMaster;

	public PnInterchangeable() {
	}

	public String getPnInterchangeable() {
		return this.pnInterchangeable;
	}

	public void setPnInterchangeable(String pnInterchangeable) {
		this.pnInterchangeable = pnInterchangeable;
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

	public BigDecimal getAlertNoOfRemoval() {
		return this.alertNoOfRemoval;
	}

	public void setAlertNoOfRemoval(BigDecimal alertNoOfRemoval) {
		this.alertNoOfRemoval = alertNoOfRemoval;
	}

	public String getAuthorizationCode() {
		return this.authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getCheckL() {
		return this.checkL;
	}

	public void setCheckL(String checkL) {
		this.checkL = checkL;
	}

	public String getClassDivision() {
		return this.classDivision;
	}

	public void setClassDivision(String classDivision) {
		this.classDivision = classDivision;
	}

	public BigDecimal getConditionalNotes() {
		return this.conditionalNotes;
	}

	public void setConditionalNotes(BigDecimal conditionalNotes) {
		this.conditionalNotes = conditionalNotes;
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

	public String getEccn() {
		return this.eccn;
	}

	public void setEccn(String eccn) {
		this.eccn = eccn;
	}

	public String getEctm() {
		return this.ectm;
	}

	public void setEctm(String ectm) {
		this.ectm = ectm;
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

	public String getHarmonizedCode() {
		return this.harmonizedCode;
	}

	public void setHarmonizedCode(String harmonizedCode) {
		this.harmonizedCode = harmonizedCode;
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

	public String getHazardousOveride() {
		return this.hazardousOveride;
	}

	public void setHazardousOveride(String hazardousOveride) {
		this.hazardousOveride = hazardousOveride;
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

	public String getIic() {
		return this.iic;
	}

	public void setIic(String iic) {
		this.iic = iic;
	}

	public String getInterchangeableType() {
		return this.interchangeableType;
	}

	public void setInterchangeableType(String interchangeableType) {
		this.interchangeableType = interchangeableType;
	}

	public Date getInterfaceTrnsfrFinanceBrzl() {
		return this.interfaceTrnsfrFinanceBrzl;
	}

	public void setInterfaceTrnsfrFinanceBrzl(Date interfaceTrnsfrFinanceBrzl) {
		this.interfaceTrnsfrFinanceBrzl = interfaceTrnsfrFinanceBrzl;
	}

	public String getItarControlled() {
		return this.itarControlled;
	}

	public void setItarControlled(String itarControlled) {
		this.itarControlled = itarControlled;
	}

	public String getLine() {
		return this.line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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

	public String getPma() {
		return this.pma;
	}

	public void setPma(String pma) {
		this.pma = pma;
	}

	public String getPnCategoryAction() {
		return this.pnCategoryAction;
	}

	public void setPnCategoryAction(String pnCategoryAction) {
		this.pnCategoryAction = pnCategoryAction;
	}

	public String getPnOsReference() {
		return this.pnOsReference;
	}

	public void setPnOsReference(String pnOsReference) {
		this.pnOsReference = pnOsReference;
	}

	public String getPrefer() {
		return this.prefer;
	}

	public void setPrefer(String prefer) {
		this.prefer = prefer;
	}

	public String getReliabilityMonitored() {
		return this.reliabilityMonitored;
	}

	public void setReliabilityMonitored(String reliabilityMonitored) {
		this.reliabilityMonitored = reliabilityMonitored;
	}

	public String getRmvInstAlertMessage() {
		return this.rmvInstAlertMessage;
	}

	public void setRmvInstAlertMessage(String rmvInstAlertMessage) {
		this.rmvInstAlertMessage = rmvInstAlertMessage;
	}

	public String getRmvInstAlertMessageTo() {
		return this.rmvInstAlertMessageTo;
	}

	public void setRmvInstAlertMessageTo(String rmvInstAlertMessageTo) {
		this.rmvInstAlertMessageTo = rmvInstAlertMessageTo;
	}

	public BigDecimal getRmvInstAlertNote() {
		return this.rmvInstAlertNote;
	}

	public void setRmvInstAlertNote(BigDecimal rmvInstAlertNote) {
		this.rmvInstAlertNote = rmvInstAlertNote;
	}

	public String getSoftwareOverrideFromMain() {
		return this.softwareOverrideFromMain;
	}

	public void setSoftwareOverrideFromMain(String softwareOverrideFromMain) {
		this.softwareOverrideFromMain = softwareOverrideFromMain;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTPed() {
		return this.tPed;
	}

	public void setTPed(String tPed) {
		this.tPed = tPed;
	}

	public BigDecimal getTransactionDocNum() {
		return this.transactionDocNum;
	}

	public void setTransactionDocNum(BigDecimal transactionDocNum) {
		this.transactionDocNum = transactionDocNum;
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

	public String getTurn() {
		return this.turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public PnMaster getPnMaster() {
		return this.pnMaster;
	}

	public void setPnMaster(PnMaster pnMaster) {
		this.pnMaster = pnMaster;
	}

}