package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the WO_TASK_CARD database table.
 * 
 */
@Entity
@Table(name="WO_TASK_CARD")
@NamedQuery(name="WoTaskCard.findAll", query="SELECT w FROM WoTaskCard w")
public class WoTaskCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoTaskCardPK id;

	@Column(name="AC_REWEIGHT")
	private String acReweight;

	@Column(name="ADDITION_WORK")
	private String additionWork;

	@Column(name="AIR_AVIONICS")
	private String airAvionics;

	@Column(name="AIR_ENGINES")
	private String airEngines;

	@Column(name="AIR_FUEL")
	private String airFuel;

	@Column(name="AIR_GEAR")
	private String airGear;

	@Column(name="AIR_GROUND_MODE")
	private String airGroundMode;

	@Column(name="AIR_GROUND_MODE_YES")
	private String airGroundModeYes;

	@Column(name="AIR_HYDRAULICS")
	private String airHydraulics;

	@Column(name="AIRCRAFT_JACKED")
	private String aircraftJacked;

	@Column(name="ALLOW_DEFER")
	private String allowDefer;

	@Column(name="APU_REMOVAL")
	private String apuRemoval;

	private String area;

	@Column(name="\"ASC\"")
	private String asc;

	@Column(name="ASSY_CODE")
	private String assyCode;

	@Column(name="AUDIT_DATE")
	private Date auditDate;

	@Column(name="AUDIT_NOTES")
	private BigDecimal auditNotes;

	@Column(name="AUDIT_RESULT")
	private String auditResult;

	private String audited;

	@Column(name="AUDITED_BY")
	private String auditedBy;

	@Column(name="\"AUTHORIZATION\"")
	private String authorization;

	@Column(name="AUTHORIZATION_BY")
	private String authorizationBy;

	@Column(name="AUTHORIZATION_CONTROL")
	private BigDecimal authorizationControl;

	@Column(name="AUTHORIZATION_DATE")
	private Date authorizationDate;

	@Column(name="BASIC_EMPTY_WEIGHT")
	private BigDecimal basicEmptyWeight;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	private String bust;

	@Column(name="BUY_BACK")
	private String buyBack;
	
	@Column(name="EQPT_NO")
	private String eqptNo;
	
	@Column(name="BUY_BACK_RII")
	private String buyBackRii;

	@Column(name="CABIN_CEILING_PANELS_REM")
	private String cabinCeilingPanelsRem;

	@Column(name="CABIN_FLOOR_PANELS_REMOVAL")
	private String cabinFloorPanelsRemoval;

	@Column(name="CABIN_PRESSURIZATION")
	private String cabinPressurization;

	@Column(name="CARGO_PANELS_REMOVAL")
	private String cargoPanelsRemoval;

	@Column(name="CAT_III")
	private String catIii;

	@Column(name="CAT_RATING")
	private String catRating;

	private String cdccl;

	private BigDecimal chapter;

	private String clean;

	private String cmr;

	@Column(name="CMR_CHECK")
	private String cmrCheck;

	@Column(name="COMMON_REPAIR")
	private String commonRepair;

	@Column(name="COMPLETED_BY")
	private String completedBy;

	@Column(name="COMPLETED_ON")
	private Date completedOn;

	@Column(name="COMPLETED_ON_HOUR")
	private BigDecimal completedOnHour;

	@Column(name="COMPLETED_ON_MINUTE")
	private BigDecimal completedOnMinute;

	@Column(name="COMPLETED_STATION")
	private String completedStation;

	@Column(name="CONFLICT_TASK_CARD")
	private String conflictTaskCard;

	@Column(name="CORRECTIVE_ACTION")
	private String correctiveAction;

	private String corrosion;

	@Column(name="CORROSION_FINDINGS_NOTES")
	private BigDecimal corrosionFindingsNotes;

	@Column(name="CORROSION_LEVEL")
	private String corrosionLevel;

	private BigDecimal cost;

	@Column(name="COST_MATERIAL")
	private BigDecimal costMaterial;

	@Column(name="CPCP_ID")
	private BigDecimal cpcpId;

	private String crack;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CREATOR_AUTHORIZED_BY")
	private String creatorAuthorizedBy;

	@Column(name="CREATOR_AUTHORIZED_DATE")
	private Date creatorAuthorizedDate;

	@Column(name="CREW_CODE")
	private String crewCode;

	@Column(name="CRITICAL_TASK")
	private String criticalTask;

	@Column(name="CRITICAL_TASK_GROUP_NUMBER")
	private String criticalTaskGroupNumber;

	@Column(name="CUSTOMER_VISIBLE")
	private String customerVisible;

	private String defect;

	@Column(name="DEFECT_DESCRIPTION")
	private String defectDescription;

	@Column(name="DEFECT_ITEM")
	private BigDecimal defectItem;

	@Column(name="DEFECT_TYPE")
	private String defectType;

	@Column(name="DEFER_EO")
	private String deferEo;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	private String dt;

	@Column(name="DUAL_INSPECTED_BY")
	private String dualInspectedBy;

	@Column(name="DUAL_INSPECTED_DATE")
	private Date dualInspectedDate;

	@Column(name="DUAL_INSPECTOR_STATUS")
	private String dualInspectorStatus;

	private String ectm;

	@Column(name="EDITOR_USED")
	private String editorUsed;

	@Column(name="ELECTRICAL_LOAD_CHG")
	private String electricalLoadChg;

	@Column(name="ELECTRICAL_LOAD_CHG_NO")
	private BigDecimal electricalLoadChgNo;

	@Column(name="ELECTRICAL_POWER_REQ")
	private String electricalPowerReq;

	@Column(name="EMOB_REFERENCE")
	private String emobReference;

	@Column(name="ENGINE_REMOVAL")
	private String engineRemoval;

	private String engineering;

	@Column(name="ENGINERUN_UP")
	private String enginerunUp;

	private String eo;

	@Column(name="ESTIMATED_HOURS")
	private BigDecimal estimatedHours;

	private String et;

	private String etops;

	@Column(name="EXCLUDE_CONTRACT")
	private String excludeContract;

	@Column(name="EXCLUDE_TYPE")
	private String excludeType;

	@Column(name="EXPENDITURE_CODE")
	private String expenditureCode;

	@Column(name="FAULT_CONFIRM")
	private String faultConfirm;

	@Column(name="FLAPS_POSITION")
	private String flapsPosition;

	@Column(name="FLIGHT_SURFACE_LIMITATION")
	private String flightSurfaceLimitation;

	@Column(name="FLIGHT_SURFACES_REMOVAL")
	private String flightSurfacesRemoval;

	private String flowday;

	@Column(name="FORM_NO")
	private BigDecimal formNo;

	@Column(name="FUEL_LIMITATION")
	private String fuelLimitation;

	@Column(name="FUEL_TANKS")
	private String fuelTanks;

	@Column(name="FUNCTIONAL_CHECK")
	private String functionalCheck;

	@Column(name="GALLEY_TOILET_REMOVAL")
	private String galleyToiletRemoval;

	@Column(name="GE_DUE_DATE")
	private Date geDueDate;

	@Column(name="GE_MODIFIED_DATE")
	private Date geModifiedDate;

	@Column(name="GE_TAC")
	private BigDecimal geTac;

	@Column(name="GE_TAD")
	private Date geTad;

	@Column(name="GE_TAT")
	private BigDecimal geTat;

	@Column(name="GROUND_AIR_LEAK_CHECK")
	private String groundAirLeakCheck;

	@Column(name="HANGAR_SPACE_REQU")
	private String hangarSpaceRequ;

	@Column(name="HIDE_FLAG")
	private String hideFlag;

	@Column(name="\"HOURS\"")
	private BigDecimal hours;

	@Column(name="HYD_GROUND_CHARTREQU")
	private String hydGroundChartrequ;

	@Column(name="HYDRAULIC_POWER_REQ")
	private String hydraulicPowerReq;

	@Column(name="INDEPENDENT_TASK")
	private String independentTask;

	@Column(name="INSP_REJECTED_BY")
	private String inspRejectedBy;

	@Column(name="INSP_REJECTED_DATE")
	private Date inspRejectedDate;

	@Column(name="INSP_REJECTED_REASON")
	private String inspRejectedReason;

	@Column(name="INSPECTED_BY")
	private String inspectedBy;

	@Column(name="INSPECTED_DATE")
	private Date inspectedDate;

	@Column(name="INSPECTION_REQUIRED")
	private String inspectionRequired;

	@Column(name="INSPECTOR_STATUS")
	private String inspectorStatus;

	@Column(name="INSURANCE_CLAIM")
	private String insuranceClaim;

	@Column(name="INSURANCE_NUMBER")
	private String insuranceNumber;

	@Column(name="INTERFACE_CREATED_DATE")
	private Date interfaceCreatedDate;

	@Column(name="INTERFACE_ECTM_TRANSFER_BY")
	private String interfaceEctmTransferBy;

	@Column(name="INTERFACE_ECTM_TRANSFER_DATE")
	private Date interfaceEctmTransferDate;

	@Column(name="INTERFACE_MODIFIED_DATE")
	private Date interfaceModifiedDate;

	@Column(name="INTERFACE_TRANSFER_BY")
	private String interfaceTransferBy;

	@Column(name="INTERFACE_TRANSFER_DATE")
	private Date interfaceTransferDate;

	private String invoiced;

	private String ir;

	private String ishtmledited;

	@Column(name="ISSUED_AS_EXCHANGE")
	private String issuedAsExchange;

	@Column(name="JOB_NAME")
	private String jobName;

	@Column(name="LANDING_GEAR_REMOVAL")
	private String landingGearRemoval;

	@Column(name="LEAD_MECH_AUTH_BY")
	private String leadMechAuthBy;

	@Column(name="LEAD_MECH_AUTH_DATE")
	private Date leadMechAuthDate;

	@Column(name="LEAK_CHECK")
	private String leakCheck;

	@Column(name="LOG_ITEM")
	private BigDecimal logItem;

	@Column(name="LOG_PAGE")
	private String logPage;

	private String lube;

	private String m;

	@Column(name="MANDATORY_CLASSIFICATION")
	private String mandatoryClassification;

	private BigDecimal mechanic;

	@Column(name="MOD_NO")
	private String modNo;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="MOMENT_CHG")
	private String momentChg;

	@Column(name="MOMENT_CHG_NO")
	private BigDecimal momentChgNo;

	private String mpd;

	private String mrb;

	private String mt;

	@Column(name="N_R_FINDING_TENDENCY")
	private String nRFindingTendency;

	private String ndt;

	@Column(name="NEXT_SITE")
	private String nextSite;

	@Column(name="NO_OF_PRINT")
	private BigDecimal noOfPrint;

	@Column(name="NON_ROUTINE")
	private String nonRoutine;

	private BigDecimal notes;

	@Column(name="NOTIFY_APPEARANCE")
	private String notifyAppearance;

	@Column(name="NOTIFY_ENGINEERING")
	private String notifyEngineering;

	@Column(name="NOTIFY_INSPECTOR")
	private String notifyInspector;

	@Column(name="NOTIFY_PLANNING")
	private String notifyPlanning;

	@Column(name="NR_CLASSIFICATION")
	private String nrClassification;

	@Column(name="NR_ENGINEERING_ID")
	private BigDecimal nrEngineeringId;

	@Column(name="NR_FROM_TC")
	private String nrFromTc;

	@Column(name="NR_NOTES")
	private BigDecimal nrNotes;

	@Column(name="NR_REPORTED_BY")
	private String nrReportedBy;
	
	@Column(name="FUNCTIONAL_LOCATION")
	private String functionalLocation;

	@Column(name="ON_CONDITION_TASK_CARD")
	private String onConditionTaskCard;

	@Column(name="OPERATING_EMPTY_WEIGHT_UPDATE")
	private String operatingEmptyWeightUpdate;

	@Column(name="OUT_OF_HANGAR")
	private String outOfHangar;

	@Column(name="PANEL_TC")
	private String panelTc;

	@Column(name="PAPER_REQUIRED")
	private String paperRequired;

	private BigDecimal paragraph;

	@Column(name="PARENT_TD_LINK_CONTENT_ID")
	private BigDecimal parentTdLinkContentId;

	private String phase;

	@Column(name="PLANNING_PRIORITY")
	private String planningPriority;

	@Column(name="PM_CATEGORY")
	private String pmCategory;

	@Column(name="PN_REQUIRED")
	private String pnRequired;

	@Column(name="PNEUMATIC_POWER")
	private String pneumaticPower;

	private String priority;

	@Column(name="PRIORITY_OVERRIDE")
	private String priorityOverride;

	private String pt;

	@Column(name="PYLON_REMOVAL")
	private String pylonRemoval;

	@Column(name="QUOTATION_NO")
	private String quotationNo;

	private String reason;

	private String recomendation;

	@Column(name="RECTIFIED_BY_ENGINEERING")
	private String rectifiedByEngineering;

	@Column(name="REFERENCE_INTERVAL")
	private String referenceInterval;

	@Column(name="REFERENCE_TASK_CARD")
	private String referenceTaskCard;

	@Column(name="REFERERANCE_TO_AD")
	private String refereranceToAd;

	@Column(name="REJECT_TEAM")
	private String rejectTeam;

	@Column(name="REJECTED_BY")
	private String rejectedBy;

	@Column(name="REJECTED_CATEGORY")
	private String rejectedCategory;

	@Column(name="REJECTED_DATE")
	private Date rejectedDate;

	@Column(name="REJECTED_REASON")
	private String rejectedReason;

	private String remarks;

	@Column(name="REOPEN_BY")
	private String reopenBy;

	@Column(name="REOPEN_DATE")
	private Date reopenDate;

	@Column(name="REOPEN_REASON")
	private String reopenReason;

	@Column(name="REQUIRED_INSPECTION_BY")
	private String requiredInspectionBy;

	@Column(name="REQUIRED_INSPECTION_ITEM")
	private String requiredInspectionItem;

	@Column(name="\"RESET\"")
	private String reset;

	@Column(name="RESOLUTION_CATEGORY")
	private String resolutionCategory;

	@Column(name="REVISED_DATE")
	private Date revisedDate;

	private String revision;

	private String rii;

	@Column(name="RII_DATE")
	private Date riiDate;

	private String rvsm;

	@Column(name="SABRE_MESSAGE_NUMBER")
	private BigDecimal sabreMessageNumber;

	@Column(name="\"SCHEDULE\"")
	private String schedule;

	@Column(name="SCHEDULE_END_DATE")
	private Date scheduleEndDate;

	@Column(name="SCHEDULE_END_HOUR")
	private BigDecimal scheduleEndHour;

	@Column(name="SCHEDULE_END_MINUTE")
	private BigDecimal scheduleEndMinute;

	@Column(name="SCHEDULE_START_DATE")
	private Date scheduleStartDate;

	@Column(name="SCHEDULE_START_HOUR")
	private BigDecimal scheduleStartHour;

	@Column(name="SCHEDULE_START_MINUTE")
	private BigDecimal scheduleStartMinute;

	@Column(name="SCHEDULE_TASK_CARD")
	private String scheduleTaskCard;

	@Column(name="SCHEDULE_TASK_CARD_ITEM")
	private BigDecimal scheduleTaskCardItem;

	private String sdr;

	@Column(name="\"SECTION\"")
	private BigDecimal section;

	@Column(name="SELL_LABOR_AMOUNT")
	private BigDecimal sellLaborAmount;

	@Column(name="SELL_LABOR_CURRENCY")
	private BigDecimal sellLaborCurrency;

	@Column(name="SELL_LABOR_METHOD")
	private String sellLaborMethod;

	@Column(name="SELL_MATERIAL_AMOUNT")
	private BigDecimal sellMaterialAmount;

	@Column(name="SELL_MATERIAL_CURRENCY")
	private BigDecimal sellMaterialCurrency;

	@Column(name="SELL_MATERIAL_METHOD")
	private String sellMaterialMethod;

	@Column(name="SELL_OTHER_AMOUNT")
	private BigDecimal sellOtherAmount;

	@Column(name="SELL_OTHER_METHOD")
	private String sellOtherMethod;

	@Column(name="SENT_UTA")
	private String sentUta;

	@Column(name="\"SERVICE\"")
	private String service;

	private String site;

	private String skill;

	private String slats;

	@Column(name="SORT_ITEM")
	private BigDecimal sortItem;

	@Column(name="SORTING_ZONE")
	private String sortingZone;

	@Column(name="SOURCE_CODE")
	private String sourceCode;

	@Column(name="SPLIT_FROM")
	private String splitFrom;

	private String spoilers;

	private String sr;

	private String ssid;

	private String status;

	@Column(name="STATUS_CATEGORY")
	private String statusCategory;

	@Column(name="STATUS_REASON")
	private String statusReason;

	@Column(name="STATUS_REASON_PN")
	private String statusReasonPn;

	@Column(name="STOWAGE_BIN_REMOVAL")
	private String stowageBinRemoval;

	@Column(name="\"STRUCTURE\"")
	private String structure;

	@Column(name="STRUCTURE_REPAIR")
	private String structureRepair;

	@Column(name="STRUCTURE_REPAIR_CLASS")
	private String structureRepairClass;

	@Column(name="STYLESHEET_MODIFIED_DATE")
	private Date stylesheetModifiedDate;

	@Column(name="SUB_SUB_SYS_CODE")
	private String subSubSysCode;

	@Column(name="SUB_SYS_CODE")
	private String subSysCode;

	@Column(name="SUPERVISOR_AUTH_BY")
	private String supervisorAuthBy;

	@Column(name="SUPERVISOR_AUTH_DATE")
	private Date supervisorAuthDate;

	@Column(name="SYSTEM_CODE")
	private String systemCode;

	@Column(name="TASK_CARD_CATEGORY")
	private String taskCardCategory;

	@Column(name="TASK_CARD_DESCRIPTION")
	private String taskCardDescription;

	@Column(name="TASK_CARD_NUMBERING_SYSTEM")
	private BigDecimal taskCardNumberingSystem;

	@Lob
	@Column(name="TASK_CARD_PDF")
	private byte[] taskCardPdf;

	@Column(name="TECH_LEVEL")
	private String techLevel;

	@Column(name="TEMPORARY_ID")
	private String temporaryId;

	@Column(name="TEST_FLIGHT")
	private String testFlight;

	@Column(name="TOTAL_MOMENT")
	private BigDecimal totalMoment;

	@Column(name="TRAXDOC_NO")
	private BigDecimal traxdocNo;

	@Column(name="\"TYPE\"")
	private String type;

	private String ut;

	private String verified;

	@Column(name="VERIFIED_BY")
	private String verifiedBy;

	@Column(name="VERIFIED_DATE")
	private Date verifiedDate;

	@Column(name="VIBRATION_CHECK")
	private String vibrationCheck;

	private String warranty;

	private String weighing;

	@Column(name="WEIGHT_BALANCE")
	private String weightBalance;

	@Column(name="WEIGHT_CHG")
	private String weightChg;

	@Column(name="WEIGHT_CHG_NO")
	private BigDecimal weightChgNo;

	@Column(name="WEIGHT_OFF")
	private BigDecimal weightOff;

	@Column(name="WEIGHT_ON")
	private BigDecimal weightOn;

	@Lob
	@Column(name="WO_TASK_CARD_XML")
	private String woTaskCardXml;

	@Column(name="WORK_ACCOMPLISHED")
	private String workAccomplished;

	@Column(name="WORK_ACCOMPLISHED_STATUS")
	private String workAccomplishedStatus;

	@Column(name="WORK_AREA")
	private String workArea;

	@Column(name="X_RAY")
	private String xRay;

	@Column(name="XML_STATUS")
	private String xmlStatus;

	private String zonal;

	//ESD  TODO
	/*
	private String gate;

	@Column(name="SUB_PHASE")
	private String subPhase;
	
	@Column(name="BILLABLE_HOURS")
	private String billableHours;
	*/
	
	//bi-directional many-to-one association to Wo
	@ManyToOne
	@JoinColumn(name="WO" , insertable=false, updatable=false)
	private Wo woBean;
	
	//bi-directional many-to-one association to WoTaskCardAcModPlan
	@OneToMany(mappedBy="woTaskCard")
	private List<WoTaskCardAcModPlan> woTaskCardAcModPlans;

	//bi-directional many-to-one association to WoTaskCardBust
	@OneToMany(mappedBy="woTaskCard")
	private List<WoTaskCardBust> woTaskCardBusts;

	//bi-directional many-to-one association to WoTaskCardItem
	@OneToMany(mappedBy="woTaskCard")
	private List<WoTaskCardItem> woTaskCardItems;

	//bi-directional many-to-one association to WoTaskCardKeyword
	@OneToMany(mappedBy="woTaskCard")
	private List<WoTaskCardKeyword> woTaskCardKeywords;

	//bi-directional many-to-one association to WoTaskCardSignedPdf
	@OneToMany(mappedBy="woTaskCard")
	private List<WoTaskCardSignedPdf> woTaskCardSignedPdfs;

	//bi-directional many-to-one association to WoTaskCardSurvey
	@OneToMany(mappedBy="woTaskCard")
	private List<WoTaskCardSurvey> woTaskCardSurveys;

	//bi-directional many-to-one association to WoTaskCardWorkInProgress
	@OneToMany(mappedBy="woTaskCard")
	private List<WoTaskCardWorkInProgress> woTaskCardWorkInProgresses;
	
	//bi-directional many-to-one association to WoTaskCardCustomer
		@OneToMany(mappedBy="woTaskCard")
		private List<WoTaskCardCustomer> woTaskCardCustomers;

	public WoTaskCard() {
	}
	
	public Wo getWoBean() {
		return this.woBean;
	}

	public void setWoBean(Wo woBean) {
		this.woBean = woBean;
	}

	public WoTaskCardPK getId() {
		return this.id;
	}

	public void setId(WoTaskCardPK id) {
		this.id = id;
	}

	public String getAcReweight() {
		return this.acReweight;
	}

	public void setAcReweight(String acReweight) {
		this.acReweight = acReweight;
	}

	public String getAdditionWork() {
		return this.additionWork;
	}

	public void setAdditionWork(String additionWork) {
		this.additionWork = additionWork;
	}

	public String getAirAvionics() {
		return this.airAvionics;
	}

	public void setAirAvionics(String airAvionics) {
		this.airAvionics = airAvionics;
	}

	public String getAirEngines() {
		return this.airEngines;
	}

	public void setAirEngines(String airEngines) {
		this.airEngines = airEngines;
	}

	public String getAirFuel() {
		return this.airFuel;
	}

	public void setAirFuel(String airFuel) {
		this.airFuel = airFuel;
	}

	public String getAirGear() {
		return this.airGear;
	}

	public void setAirGear(String airGear) {
		this.airGear = airGear;
	}

	public String getAirGroundMode() {
		return this.airGroundMode;
	}

	public void setAirGroundMode(String airGroundMode) {
		this.airGroundMode = airGroundMode;
	}

	public String getAirGroundModeYes() {
		return this.airGroundModeYes;
	}

	public void setAirGroundModeYes(String airGroundModeYes) {
		this.airGroundModeYes = airGroundModeYes;
	}

	public String getAirHydraulics() {
		return this.airHydraulics;
	}

	public void setAirHydraulics(String airHydraulics) {
		this.airHydraulics = airHydraulics;
	}

	public String getAircraftJacked() {
		return this.aircraftJacked;
	}

	public void setAircraftJacked(String aircraftJacked) {
		this.aircraftJacked = aircraftJacked;
	}

	public String getAllowDefer() {
		return this.allowDefer;
	}

	public void setAllowDefer(String allowDefer) {
		this.allowDefer = allowDefer;
	}

	public String getApuRemoval() {
		return this.apuRemoval;
	}

	public void setApuRemoval(String apuRemoval) {
		this.apuRemoval = apuRemoval;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAsc() {
		return this.asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}

	public String getAssyCode() {
		return this.assyCode;
	}

	public void setAssyCode(String assyCode) {
		this.assyCode = assyCode;
	}

	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public BigDecimal getAuditNotes() {
		return this.auditNotes;
	}

	public void setAuditNotes(BigDecimal auditNotes) {
		this.auditNotes = auditNotes;
	}

	public String getAuditResult() {
		return this.auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getAudited() {
		return this.audited;
	}

	public void setAudited(String audited) {
		this.audited = audited;
	}

	public String getAuditedBy() {
		return this.auditedBy;
	}

	public void setAuditedBy(String auditedBy) {
		this.auditedBy = auditedBy;
	}

	public String getAuthorization() {
		return this.authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getAuthorizationBy() {
		return this.authorizationBy;
	}

	public void setAuthorizationBy(String authorizationBy) {
		this.authorizationBy = authorizationBy;
	}

	public BigDecimal getAuthorizationControl() {
		return this.authorizationControl;
	}

	public void setAuthorizationControl(BigDecimal authorizationControl) {
		this.authorizationControl = authorizationControl;
	}

	public Date getAuthorizationDate() {
		return this.authorizationDate;
	}

	public void setAuthorizationDate(Date authorizationDate) {
		this.authorizationDate = authorizationDate;
	}

	public BigDecimal getBasicEmptyWeight() {
		return this.basicEmptyWeight;
	}

	public void setBasicEmptyWeight(BigDecimal basicEmptyWeight) {
		this.basicEmptyWeight = basicEmptyWeight;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public String getBust() {
		return this.bust;
	}

	public void setBust(String bust) {
		this.bust = bust;
	}

	public String getBuyBack() {
		return this.buyBack;
	}

	public void setBuyBack(String buyBack) {
		this.buyBack = buyBack;
	}

	public String getBuyBackRii() {
		return this.buyBackRii;
	}

	public void setBuyBackRii(String buyBackRii) {
		this.buyBackRii = buyBackRii;
	}

	public String getCabinCeilingPanelsRem() {
		return this.cabinCeilingPanelsRem;
	}

	public void setCabinCeilingPanelsRem(String cabinCeilingPanelsRem) {
		this.cabinCeilingPanelsRem = cabinCeilingPanelsRem;
	}

	public String getCabinFloorPanelsRemoval() {
		return this.cabinFloorPanelsRemoval;
	}

	public void setCabinFloorPanelsRemoval(String cabinFloorPanelsRemoval) {
		this.cabinFloorPanelsRemoval = cabinFloorPanelsRemoval;
	}

	public String getCabinPressurization() {
		return this.cabinPressurization;
	}

	public void setCabinPressurization(String cabinPressurization) {
		this.cabinPressurization = cabinPressurization;
	}

	public String getCargoPanelsRemoval() {
		return this.cargoPanelsRemoval;
	}

	public void setCargoPanelsRemoval(String cargoPanelsRemoval) {
		this.cargoPanelsRemoval = cargoPanelsRemoval;
	}

	public String getCatIii() {
		return this.catIii;
	}

	public void setCatIii(String catIii) {
		this.catIii = catIii;
	}

	public String getCatRating() {
		return this.catRating;
	}

	public void setCatRating(String catRating) {
		this.catRating = catRating;
	}

	public String getCdccl() {
		return this.cdccl;
	}

	public void setCdccl(String cdccl) {
		this.cdccl = cdccl;
	}

	public BigDecimal getChapter() {
		return this.chapter;
	}

	public void setChapter(BigDecimal chapter) {
		this.chapter = chapter;
	}

	public String getClean() {
		return this.clean;
	}

	public void setClean(String clean) {
		this.clean = clean;
	}

	public String getCmr() {
		return this.cmr;
	}

	public void setCmr(String cmr) {
		this.cmr = cmr;
	}

	public String getCmrCheck() {
		return this.cmrCheck;
	}

	public void setCmrCheck(String cmrCheck) {
		this.cmrCheck = cmrCheck;
	}

	public String getCommonRepair() {
		return this.commonRepair;
	}

	public void setCommonRepair(String commonRepair) {
		this.commonRepair = commonRepair;
	}

	public String getCompletedBy() {
		return this.completedBy;
	}

	public void setCompletedBy(String completedBy) {
		this.completedBy = completedBy;
	}

	public Date getCompletedOn() {
		return this.completedOn;
	}

	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}

	public BigDecimal getCompletedOnHour() {
		return this.completedOnHour;
	}

	public void setCompletedOnHour(BigDecimal completedOnHour) {
		this.completedOnHour = completedOnHour;
	}

	public BigDecimal getCompletedOnMinute() {
		return this.completedOnMinute;
	}

	public void setCompletedOnMinute(BigDecimal completedOnMinute) {
		this.completedOnMinute = completedOnMinute;
	}

	public String getCompletedStation() {
		return this.completedStation;
	}

	public void setCompletedStation(String completedStation) {
		this.completedStation = completedStation;
	}

	public String getConflictTaskCard() {
		return this.conflictTaskCard;
	}

	public void setConflictTaskCard(String conflictTaskCard) {
		this.conflictTaskCard = conflictTaskCard;
	}

	public String getCorrectiveAction() {
		return this.correctiveAction;
	}

	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}

	public String getCorrosion() {
		return this.corrosion;
	}

	public void setCorrosion(String corrosion) {
		this.corrosion = corrosion;
	}

	public BigDecimal getCorrosionFindingsNotes() {
		return this.corrosionFindingsNotes;
	}

	public void setCorrosionFindingsNotes(BigDecimal corrosionFindingsNotes) {
		this.corrosionFindingsNotes = corrosionFindingsNotes;
	}

	public String getCorrosionLevel() {
		return this.corrosionLevel;
	}

	public void setCorrosionLevel(String corrosionLevel) {
		this.corrosionLevel = corrosionLevel;
	}

	public BigDecimal getCost() {
		return this.cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getCostMaterial() {
		return this.costMaterial;
	}

	public void setCostMaterial(BigDecimal costMaterial) {
		this.costMaterial = costMaterial;
	}

	public BigDecimal getCpcpId() {
		return this.cpcpId;
	}

	public void setCpcpId(BigDecimal cpcpId) {
		this.cpcpId = cpcpId;
	}

	public String getCrack() {
		return this.crack;
	}

	public void setCrack(String crack) {
		this.crack = crack;
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

	public String getCreatorAuthorizedBy() {
		return this.creatorAuthorizedBy;
	}

	public void setCreatorAuthorizedBy(String creatorAuthorizedBy) {
		this.creatorAuthorizedBy = creatorAuthorizedBy;
	}

	public Date getCreatorAuthorizedDate() {
		return this.creatorAuthorizedDate;
	}

	public void setCreatorAuthorizedDate(Date creatorAuthorizedDate) {
		this.creatorAuthorizedDate = creatorAuthorizedDate;
	}

	public String getCrewCode() {
		return this.crewCode;
	}

	public void setCrewCode(String crewCode) {
		this.crewCode = crewCode;
	}

	public String getCriticalTask() {
		return this.criticalTask;
	}

	public void setCriticalTask(String criticalTask) {
		this.criticalTask = criticalTask;
	}

	public String getCriticalTaskGroupNumber() {
		return this.criticalTaskGroupNumber;
	}

	public void setCriticalTaskGroupNumber(String criticalTaskGroupNumber) {
		this.criticalTaskGroupNumber = criticalTaskGroupNumber;
	}

	public String getCustomerVisible() {
		return this.customerVisible;
	}

	public void setCustomerVisible(String customerVisible) {
		this.customerVisible = customerVisible;
	}

	public String getDefect() {
		return this.defect;
	}

	public void setDefect(String defect) {
		this.defect = defect;
	}

	public String getDefectDescription() {
		return this.defectDescription;
	}

	public void setDefectDescription(String defectDescription) {
		this.defectDescription = defectDescription;
	}

	public BigDecimal getDefectItem() {
		return this.defectItem;
	}

	public void setDefectItem(BigDecimal defectItem) {
		this.defectItem = defectItem;
	}

	public String getDefectType() {
		return this.defectType;
	}

	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}

	public String getDeferEo() {
		return this.deferEo;
	}

	public void setDeferEo(String deferEo) {
		this.deferEo = deferEo;
	}

	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getDt() {
		return this.dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getDualInspectedBy() {
		return this.dualInspectedBy;
	}

	public void setDualInspectedBy(String dualInspectedBy) {
		this.dualInspectedBy = dualInspectedBy;
	}

	public Date getDualInspectedDate() {
		return this.dualInspectedDate;
	}

	public void setDualInspectedDate(Date dualInspectedDate) {
		this.dualInspectedDate = dualInspectedDate;
	}

	public String getDualInspectorStatus() {
		return this.dualInspectorStatus;
	}

	public void setDualInspectorStatus(String dualInspectorStatus) {
		this.dualInspectorStatus = dualInspectorStatus;
	}

	public String getEctm() {
		return this.ectm;
	}

	public void setEctm(String ectm) {
		this.ectm = ectm;
	}

	public String getEditorUsed() {
		return this.editorUsed;
	}

	public void setEditorUsed(String editorUsed) {
		this.editorUsed = editorUsed;
	}

	public String getElectricalLoadChg() {
		return this.electricalLoadChg;
	}

	public void setElectricalLoadChg(String electricalLoadChg) {
		this.electricalLoadChg = electricalLoadChg;
	}

	public BigDecimal getElectricalLoadChgNo() {
		return this.electricalLoadChgNo;
	}

	public void setElectricalLoadChgNo(BigDecimal electricalLoadChgNo) {
		this.electricalLoadChgNo = electricalLoadChgNo;
	}

	public String getElectricalPowerReq() {
		return this.electricalPowerReq;
	}

	public void setElectricalPowerReq(String electricalPowerReq) {
		this.electricalPowerReq = electricalPowerReq;
	}

	public String getEmobReference() {
		return this.emobReference;
	}

	public void setEmobReference(String emobReference) {
		this.emobReference = emobReference;
	}

	public String getEngineRemoval() {
		return this.engineRemoval;
	}

	public void setEngineRemoval(String engineRemoval) {
		this.engineRemoval = engineRemoval;
	}

	public String getEngineering() {
		return this.engineering;
	}

	public void setEngineering(String engineering) {
		this.engineering = engineering;
	}

	public String getEnginerunUp() {
		return this.enginerunUp;
	}

	public void setEnginerunUp(String enginerunUp) {
		this.enginerunUp = enginerunUp;
	}

	public String getEo() {
		return this.eo;
	}

	public void setEo(String eo) {
		this.eo = eo;
	}

	public BigDecimal getEstimatedHours() {
		return this.estimatedHours;
	}

	public void setEstimatedHours(BigDecimal estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public String getEt() {
		return this.et;
	}

	public void setEt(String et) {
		this.et = et;
	}

	public String getEtops() {
		return this.etops;
	}

	public void setEtops(String etops) {
		this.etops = etops;
	}

	public String getExcludeContract() {
		return this.excludeContract;
	}

	public void setExcludeContract(String excludeContract) {
		this.excludeContract = excludeContract;
	}

	public String getExcludeType() {
		return this.excludeType;
	}

	public void setExcludeType(String excludeType) {
		this.excludeType = excludeType;
	}

	public String getExpenditureCode() {
		return this.expenditureCode;
	}

	public void setExpenditureCode(String expenditureCode) {
		this.expenditureCode = expenditureCode;
	}

	public String getFaultConfirm() {
		return this.faultConfirm;
	}

	public void setFaultConfirm(String faultConfirm) {
		this.faultConfirm = faultConfirm;
	}

	public String getFlapsPosition() {
		return this.flapsPosition;
	}

	public void setFlapsPosition(String flapsPosition) {
		this.flapsPosition = flapsPosition;
	}

	public String getFlightSurfaceLimitation() {
		return this.flightSurfaceLimitation;
	}

	public void setFlightSurfaceLimitation(String flightSurfaceLimitation) {
		this.flightSurfaceLimitation = flightSurfaceLimitation;
	}

	public String getFlightSurfacesRemoval() {
		return this.flightSurfacesRemoval;
	}

	public void setFlightSurfacesRemoval(String flightSurfacesRemoval) {
		this.flightSurfacesRemoval = flightSurfacesRemoval;
	}

	public String getFlowday() {
		return this.flowday;
	}

	public void setFlowday(String flowday) {
		this.flowday = flowday;
	}

	public BigDecimal getFormNo() {
		return this.formNo;
	}

	public void setFormNo(BigDecimal formNo) {
		this.formNo = formNo;
	}

	public String getFuelLimitation() {
		return this.fuelLimitation;
	}

	public void setFuelLimitation(String fuelLimitation) {
		this.fuelLimitation = fuelLimitation;
	}

	public String getFuelTanks() {
		return this.fuelTanks;
	}

	public void setFuelTanks(String fuelTanks) {
		this.fuelTanks = fuelTanks;
	}

	public String getFunctionalCheck() {
		return this.functionalCheck;
	}

	public void setFunctionalCheck(String functionalCheck) {
		this.functionalCheck = functionalCheck;
	}

	public String getGalleyToiletRemoval() {
		return this.galleyToiletRemoval;
	}

	public void setGalleyToiletRemoval(String galleyToiletRemoval) {
		this.galleyToiletRemoval = galleyToiletRemoval;
	}

	public Date getGeDueDate() {
		return this.geDueDate;
	}

	public void setGeDueDate(Date geDueDate) {
		this.geDueDate = geDueDate;
	}

	public Date getGeModifiedDate() {
		return this.geModifiedDate;
	}

	public void setGeModifiedDate(Date geModifiedDate) {
		this.geModifiedDate = geModifiedDate;
	}

	public BigDecimal getGeTac() {
		return this.geTac;
	}

	public void setGeTac(BigDecimal geTac) {
		this.geTac = geTac;
	}

	public Date getGeTad() {
		return this.geTad;
	}

	public void setGeTad(Date geTad) {
		this.geTad = geTad;
	}

	public BigDecimal getGeTat() {
		return this.geTat;
	}

	public void setGeTat(BigDecimal geTat) {
		this.geTat = geTat;
	}

	public String getGroundAirLeakCheck() {
		return this.groundAirLeakCheck;
	}

	public void setGroundAirLeakCheck(String groundAirLeakCheck) {
		this.groundAirLeakCheck = groundAirLeakCheck;
	}

	public String getHangarSpaceRequ() {
		return this.hangarSpaceRequ;
	}

	public void setHangarSpaceRequ(String hangarSpaceRequ) {
		this.hangarSpaceRequ = hangarSpaceRequ;
	}

	public String getHideFlag() {
		return this.hideFlag;
	}

	public void setHideFlag(String hideFlag) {
		this.hideFlag = hideFlag;
	}

	public BigDecimal getHours() {
		return this.hours;
	}

	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}

	public String getHydGroundChartrequ() {
		return this.hydGroundChartrequ;
	}

	public void setHydGroundChartrequ(String hydGroundChartrequ) {
		this.hydGroundChartrequ = hydGroundChartrequ;
	}

	public String getHydraulicPowerReq() {
		return this.hydraulicPowerReq;
	}

	public void setHydraulicPowerReq(String hydraulicPowerReq) {
		this.hydraulicPowerReq = hydraulicPowerReq;
	}

	public String getIndependentTask() {
		return this.independentTask;
	}

	public void setIndependentTask(String independentTask) {
		this.independentTask = independentTask;
	}

	public String getInspRejectedBy() {
		return this.inspRejectedBy;
	}

	public void setInspRejectedBy(String inspRejectedBy) {
		this.inspRejectedBy = inspRejectedBy;
	}

	public Date getInspRejectedDate() {
		return this.inspRejectedDate;
	}

	public void setInspRejectedDate(Date inspRejectedDate) {
		this.inspRejectedDate = inspRejectedDate;
	}

	public String getInspRejectedReason() {
		return this.inspRejectedReason;
	}

	public void setInspRejectedReason(String inspRejectedReason) {
		this.inspRejectedReason = inspRejectedReason;
	}

	public String getInspectedBy() {
		return this.inspectedBy;
	}

	public void setInspectedBy(String inspectedBy) {
		this.inspectedBy = inspectedBy;
	}

	public Date getInspectedDate() {
		return this.inspectedDate;
	}

	public void setInspectedDate(Date inspectedDate) {
		this.inspectedDate = inspectedDate;
	}

	public String getInspectionRequired() {
		return this.inspectionRequired;
	}

	public void setInspectionRequired(String inspectionRequired) {
		this.inspectionRequired = inspectionRequired;
	}

	public String getInspectorStatus() {
		return this.inspectorStatus;
	}

	public void setInspectorStatus(String inspectorStatus) {
		this.inspectorStatus = inspectorStatus;
	}

	public String getInsuranceClaim() {
		return this.insuranceClaim;
	}

	public void setInsuranceClaim(String insuranceClaim) {
		this.insuranceClaim = insuranceClaim;
	}

	public String getInsuranceNumber() {
		return this.insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public Date getInterfaceCreatedDate() {
		return this.interfaceCreatedDate;
	}

	public void setInterfaceCreatedDate(Date interfaceCreatedDate) {
		this.interfaceCreatedDate = interfaceCreatedDate;
	}

	public String getInterfaceEctmTransferBy() {
		return this.interfaceEctmTransferBy;
	}

	public void setInterfaceEctmTransferBy(String interfaceEctmTransferBy) {
		this.interfaceEctmTransferBy = interfaceEctmTransferBy;
	}

	public Date getInterfaceEctmTransferDate() {
		return this.interfaceEctmTransferDate;
	}

	public void setInterfaceEctmTransferDate(Date interfaceEctmTransferDate) {
		this.interfaceEctmTransferDate = interfaceEctmTransferDate;
	}

	public Date getInterfaceModifiedDate() {
		return this.interfaceModifiedDate;
	}

	public void setInterfaceModifiedDate(Date interfaceModifiedDate) {
		this.interfaceModifiedDate = interfaceModifiedDate;
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

	public String getInvoiced() {
		return this.invoiced;
	}

	public void setInvoiced(String invoiced) {
		this.invoiced = invoiced;
	}

	public String getIr() {
		return this.ir;
	}

	public void setIr(String ir) {
		this.ir = ir;
	}

	public String getIshtmledited() {
		return this.ishtmledited;
	}

	public void setIshtmledited(String ishtmledited) {
		this.ishtmledited = ishtmledited;
	}

	public String getIssuedAsExchange() {
		return this.issuedAsExchange;
	}

	public void setIssuedAsExchange(String issuedAsExchange) {
		this.issuedAsExchange = issuedAsExchange;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getLandingGearRemoval() {
		return this.landingGearRemoval;
	}

	public void setLandingGearRemoval(String landingGearRemoval) {
		this.landingGearRemoval = landingGearRemoval;
	}

	public String getLeadMechAuthBy() {
		return this.leadMechAuthBy;
	}

	public void setLeadMechAuthBy(String leadMechAuthBy) {
		this.leadMechAuthBy = leadMechAuthBy;
	}

	public Date getLeadMechAuthDate() {
		return this.leadMechAuthDate;
	}

	public void setLeadMechAuthDate(Date leadMechAuthDate) {
		this.leadMechAuthDate = leadMechAuthDate;
	}

	public String getLeakCheck() {
		return this.leakCheck;
	}

	public void setLeakCheck(String leakCheck) {
		this.leakCheck = leakCheck;
	}

	public BigDecimal getLogItem() {
		return this.logItem;
	}

	public void setLogItem(BigDecimal logItem) {
		this.logItem = logItem;
	}

	public String getLogPage() {
		return this.logPage;
	}

	public void setLogPage(String logPage) {
		this.logPage = logPage;
	}

	public String getLube() {
		return this.lube;
	}

	public void setLube(String lube) {
		this.lube = lube;
	}

	public String getM() {
		return this.m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getMandatoryClassification() {
		return this.mandatoryClassification;
	}

	public void setMandatoryClassification(String mandatoryClassification) {
		this.mandatoryClassification = mandatoryClassification;
	}

	public BigDecimal getMechanic() {
		return this.mechanic;
	}

	public void setMechanic(BigDecimal mechanic) {
		this.mechanic = mechanic;
	}

	public String getModNo() {
		return this.modNo;
	}

	public void setModNo(String modNo) {
		this.modNo = modNo;
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

	public String getMomentChg() {
		return this.momentChg;
	}

	public void setMomentChg(String momentChg) {
		this.momentChg = momentChg;
	}

	public BigDecimal getMomentChgNo() {
		return this.momentChgNo;
	}

	public void setMomentChgNo(BigDecimal momentChgNo) {
		this.momentChgNo = momentChgNo;
	}

	public String getMpd() {
		return this.mpd;
	}

	public void setMpd(String mpd) {
		this.mpd = mpd;
	}

	public String getMrb() {
		return this.mrb;
	}

	public void setMrb(String mrb) {
		this.mrb = mrb;
	}

	public String getMt() {
		return this.mt;
	}

	public void setMt(String mt) {
		this.mt = mt;
	}

	public String getNRFindingTendency() {
		return this.nRFindingTendency;
	}

	public void setNRFindingTendency(String nRFindingTendency) {
		this.nRFindingTendency = nRFindingTendency;
	}

	public String getNdt() {
		return this.ndt;
	}

	public void setNdt(String ndt) {
		this.ndt = ndt;
	}

	public String getNextSite() {
		return this.nextSite;
	}

	public void setNextSite(String nextSite) {
		this.nextSite = nextSite;
	}

	public BigDecimal getNoOfPrint() {
		return this.noOfPrint;
	}

	public void setNoOfPrint(BigDecimal noOfPrint) {
		this.noOfPrint = noOfPrint;
	}

	public String getNonRoutine() {
		return this.nonRoutine;
	}

	public void setNonRoutine(String nonRoutine) {
		this.nonRoutine = nonRoutine;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public String getNotifyAppearance() {
		return this.notifyAppearance;
	}

	public void setNotifyAppearance(String notifyAppearance) {
		this.notifyAppearance = notifyAppearance;
	}

	public String getNotifyEngineering() {
		return this.notifyEngineering;
	}

	public void setNotifyEngineering(String notifyEngineering) {
		this.notifyEngineering = notifyEngineering;
	}

	public String getNotifyInspector() {
		return this.notifyInspector;
	}

	public void setNotifyInspector(String notifyInspector) {
		this.notifyInspector = notifyInspector;
	}

	public String getNotifyPlanning() {
		return this.notifyPlanning;
	}

	public void setNotifyPlanning(String notifyPlanning) {
		this.notifyPlanning = notifyPlanning;
	}

	public String getNrClassification() {
		return this.nrClassification;
	}

	public void setNrClassification(String nrClassification) {
		this.nrClassification = nrClassification;
	}

	public BigDecimal getNrEngineeringId() {
		return this.nrEngineeringId;
	}

	public void setNrEngineeringId(BigDecimal nrEngineeringId) {
		this.nrEngineeringId = nrEngineeringId;
	}

	public String getNrFromTc() {
		return this.nrFromTc;
	}

	public void setNrFromTc(String nrFromTc) {
		this.nrFromTc = nrFromTc;
	}

	public BigDecimal getNrNotes() {
		return this.nrNotes;
	}

	public void setNrNotes(BigDecimal nrNotes) {
		this.nrNotes = nrNotes;
	}

	public String getNrReportedBy() {
		return this.nrReportedBy;
	}

	public void setNrReportedBy(String nrReportedBy) {
		this.nrReportedBy = nrReportedBy;
	}

	public String getOnConditionTaskCard() {
		return this.onConditionTaskCard;
	}

	public void setOnConditionTaskCard(String onConditionTaskCard) {
		this.onConditionTaskCard = onConditionTaskCard;
	}

	public String getOperatingEmptyWeightUpdate() {
		return this.operatingEmptyWeightUpdate;
	}

	public void setOperatingEmptyWeightUpdate(String operatingEmptyWeightUpdate) {
		this.operatingEmptyWeightUpdate = operatingEmptyWeightUpdate;
	}

	public String getOutOfHangar() {
		return this.outOfHangar;
	}

	public void setOutOfHangar(String outOfHangar) {
		this.outOfHangar = outOfHangar;
	}

	public String getPanelTc() {
		return this.panelTc;
	}

	public void setPanelTc(String panelTc) {
		this.panelTc = panelTc;
	}

	public String getPaperRequired() {
		return this.paperRequired;
	}

	public void setPaperRequired(String paperRequired) {
		this.paperRequired = paperRequired;
	}

	public BigDecimal getParagraph() {
		return this.paragraph;
	}

	public void setParagraph(BigDecimal paragraph) {
		this.paragraph = paragraph;
	}

	public BigDecimal getParentTdLinkContentId() {
		return this.parentTdLinkContentId;
	}

	public void setParentTdLinkContentId(BigDecimal parentTdLinkContentId) {
		this.parentTdLinkContentId = parentTdLinkContentId;
	}

	public String getPhase() {
		return this.phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getPlanningPriority() {
		return this.planningPriority;
	}

	public void setPlanningPriority(String planningPriority) {
		this.planningPriority = planningPriority;
	}

	public String getPmCategory() {
		return this.pmCategory;
	}

	public void setPmCategory(String pmCategory) {
		this.pmCategory = pmCategory;
	}

	public String getPnRequired() {
		return this.pnRequired;
	}

	public void setPnRequired(String pnRequired) {
		this.pnRequired = pnRequired;
	}

	public String getPneumaticPower() {
		return this.pneumaticPower;
	}

	public void setPneumaticPower(String pneumaticPower) {
		this.pneumaticPower = pneumaticPower;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPriorityOverride() {
		return this.priorityOverride;
	}

	public void setPriorityOverride(String priorityOverride) {
		this.priorityOverride = priorityOverride;
	}

	public String getPt() {
		return this.pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	public String getPylonRemoval() {
		return this.pylonRemoval;
	}

	public void setPylonRemoval(String pylonRemoval) {
		this.pylonRemoval = pylonRemoval;
	}

	public String getQuotationNo() {
		return this.quotationNo;
	}

	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRecomendation() {
		return this.recomendation;
	}

	public void setRecomendation(String recomendation) {
		this.recomendation = recomendation;
	}

	public String getRectifiedByEngineering() {
		return this.rectifiedByEngineering;
	}

	public void setRectifiedByEngineering(String rectifiedByEngineering) {
		this.rectifiedByEngineering = rectifiedByEngineering;
	}

	public String getReferenceInterval() {
		return this.referenceInterval;
	}

	public void setReferenceInterval(String referenceInterval) {
		this.referenceInterval = referenceInterval;
	}

	public String getReferenceTaskCard() {
		return this.referenceTaskCard;
	}

	public void setReferenceTaskCard(String referenceTaskCard) {
		this.referenceTaskCard = referenceTaskCard;
	}

	public String getRefereranceToAd() {
		return this.refereranceToAd;
	}

	public void setRefereranceToAd(String refereranceToAd) {
		this.refereranceToAd = refereranceToAd;
	}

	public String getRejectTeam() {
		return this.rejectTeam;
	}

	public void setRejectTeam(String rejectTeam) {
		this.rejectTeam = rejectTeam;
	}

	public String getRejectedBy() {
		return this.rejectedBy;
	}

	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public String getRejectedCategory() {
		return this.rejectedCategory;
	}

	public void setRejectedCategory(String rejectedCategory) {
		this.rejectedCategory = rejectedCategory;
	}

	public Date getRejectedDate() {
		return this.rejectedDate;
	}

	public void setRejectedDate(Date rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	public String getRejectedReason() {
		return this.rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReopenBy() {
		return this.reopenBy;
	}

	public void setReopenBy(String reopenBy) {
		this.reopenBy = reopenBy;
	}

	public Date getReopenDate() {
		return this.reopenDate;
	}

	public void setReopenDate(Date reopenDate) {
		this.reopenDate = reopenDate;
	}

	public String getReopenReason() {
		return this.reopenReason;
	}

	public void setReopenReason(String reopenReason) {
		this.reopenReason = reopenReason;
	}

	public String getRequiredInspectionBy() {
		return this.requiredInspectionBy;
	}

	public void setRequiredInspectionBy(String requiredInspectionBy) {
		this.requiredInspectionBy = requiredInspectionBy;
	}

	public String getRequiredInspectionItem() {
		return this.requiredInspectionItem;
	}

	public void setRequiredInspectionItem(String requiredInspectionItem) {
		this.requiredInspectionItem = requiredInspectionItem;
	}

	public String getReset() {
		return this.reset;
	}

	public void setReset(String reset) {
		this.reset = reset;
	}

	public String getResolutionCategory() {
		return this.resolutionCategory;
	}

	public void setResolutionCategory(String resolutionCategory) {
		this.resolutionCategory = resolutionCategory;
	}

	public Date getRevisedDate() {
		return this.revisedDate;
	}

	public void setRevisedDate(Date revisedDate) {
		this.revisedDate = revisedDate;
	}

	public String getRevision() {
		return this.revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getRii() {
		return this.rii;
	}

	public void setRii(String rii) {
		this.rii = rii;
	}

	public Date getRiiDate() {
		return this.riiDate;
	}

	public void setRiiDate(Date riiDate) {
		this.riiDate = riiDate;
	}

	public String getRvsm() {
		return this.rvsm;
	}

	public void setRvsm(String rvsm) {
		this.rvsm = rvsm;
	}

	public BigDecimal getSabreMessageNumber() {
		return this.sabreMessageNumber;
	}

	public void setSabreMessageNumber(BigDecimal sabreMessageNumber) {
		this.sabreMessageNumber = sabreMessageNumber;
	}

	public String getSchedule() {
		return this.schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Date getScheduleEndDate() {
		return this.scheduleEndDate;
	}

	public void setScheduleEndDate(Date scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
	}

	public BigDecimal getScheduleEndHour() {
		return this.scheduleEndHour;
	}

	public void setScheduleEndHour(BigDecimal scheduleEndHour) {
		this.scheduleEndHour = scheduleEndHour;
	}

	public BigDecimal getScheduleEndMinute() {
		return this.scheduleEndMinute;
	}

	public void setScheduleEndMinute(BigDecimal scheduleEndMinute) {
		this.scheduleEndMinute = scheduleEndMinute;
	}

	public Date getScheduleStartDate() {
		return this.scheduleStartDate;
	}

	public void setScheduleStartDate(Date scheduleStartDate) {
		this.scheduleStartDate = scheduleStartDate;
	}

	public BigDecimal getScheduleStartHour() {
		return this.scheduleStartHour;
	}

	public void setScheduleStartHour(BigDecimal scheduleStartHour) {
		this.scheduleStartHour = scheduleStartHour;
	}

	public BigDecimal getScheduleStartMinute() {
		return this.scheduleStartMinute;
	}

	public void setScheduleStartMinute(BigDecimal scheduleStartMinute) {
		this.scheduleStartMinute = scheduleStartMinute;
	}

	public String getScheduleTaskCard() {
		return this.scheduleTaskCard;
	}

	public void setScheduleTaskCard(String scheduleTaskCard) {
		this.scheduleTaskCard = scheduleTaskCard;
	}

	public BigDecimal getScheduleTaskCardItem() {
		return this.scheduleTaskCardItem;
	}

	public void setScheduleTaskCardItem(BigDecimal scheduleTaskCardItem) {
		this.scheduleTaskCardItem = scheduleTaskCardItem;
	}

	public String getSdr() {
		return this.sdr;
	}

	public void setSdr(String sdr) {
		this.sdr = sdr;
	}

	public BigDecimal getSection() {
		return this.section;
	}

	public void setSection(BigDecimal section) {
		this.section = section;
	}

	public BigDecimal getSellLaborAmount() {
		return this.sellLaborAmount;
	}

	public void setSellLaborAmount(BigDecimal sellLaborAmount) {
		this.sellLaborAmount = sellLaborAmount;
	}

	public BigDecimal getSellLaborCurrency() {
		return this.sellLaborCurrency;
	}

	public void setSellLaborCurrency(BigDecimal sellLaborCurrency) {
		this.sellLaborCurrency = sellLaborCurrency;
	}

	public String getSellLaborMethod() {
		return this.sellLaborMethod;
	}

	public void setSellLaborMethod(String sellLaborMethod) {
		this.sellLaborMethod = sellLaborMethod;
	}

	public BigDecimal getSellMaterialAmount() {
		return this.sellMaterialAmount;
	}

	public void setSellMaterialAmount(BigDecimal sellMaterialAmount) {
		this.sellMaterialAmount = sellMaterialAmount;
	}

	public BigDecimal getSellMaterialCurrency() {
		return this.sellMaterialCurrency;
	}

	public void setSellMaterialCurrency(BigDecimal sellMaterialCurrency) {
		this.sellMaterialCurrency = sellMaterialCurrency;
	}

	public String getSellMaterialMethod() {
		return this.sellMaterialMethod;
	}

	public void setSellMaterialMethod(String sellMaterialMethod) {
		this.sellMaterialMethod = sellMaterialMethod;
	}

	public BigDecimal getSellOtherAmount() {
		return this.sellOtherAmount;
	}

	public void setSellOtherAmount(BigDecimal sellOtherAmount) {
		this.sellOtherAmount = sellOtherAmount;
	}

	public String getSellOtherMethod() {
		return this.sellOtherMethod;
	}

	public void setSellOtherMethod(String sellOtherMethod) {
		this.sellOtherMethod = sellOtherMethod;
	}

	public String getSentUta() {
		return this.sentUta;
	}

	public void setSentUta(String sentUta) {
		this.sentUta = sentUta;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getSlats() {
		return this.slats;
	}

	public void setSlats(String slats) {
		this.slats = slats;
	}

	public BigDecimal getSortItem() {
		return this.sortItem;
	}

	public void setSortItem(BigDecimal sortItem) {
		this.sortItem = sortItem;
	}

	public String getSortingZone() {
		return this.sortingZone;
	}

	public void setSortingZone(String sortingZone) {
		this.sortingZone = sortingZone;
	}

	public String getSourceCode() {
		return this.sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getSplitFrom() {
		return this.splitFrom;
	}

	public void setSplitFrom(String splitFrom) {
		this.splitFrom = splitFrom;
	}

	public String getSpoilers() {
		return this.spoilers;
	}

	public void setSpoilers(String spoilers) {
		this.spoilers = spoilers;
	}

	public String getSr() {
		return this.sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}

	public String getSsid() {
		return this.ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCategory() {
		return this.statusCategory;
	}

	public void setStatusCategory(String statusCategory) {
		this.statusCategory = statusCategory;
	}

	public String getStatusReason() {
		return this.statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public String getStatusReasonPn() {
		return this.statusReasonPn;
	}

	public void setStatusReasonPn(String statusReasonPn) {
		this.statusReasonPn = statusReasonPn;
	}

	public String getStowageBinRemoval() {
		return this.stowageBinRemoval;
	}

	public void setStowageBinRemoval(String stowageBinRemoval) {
		this.stowageBinRemoval = stowageBinRemoval;
	}

	public String getStructure() {
		return this.structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getStructureRepair() {
		return this.structureRepair;
	}

	public void setStructureRepair(String structureRepair) {
		this.structureRepair = structureRepair;
	}

	public String getStructureRepairClass() {
		return this.structureRepairClass;
	}

	public void setStructureRepairClass(String structureRepairClass) {
		this.structureRepairClass = structureRepairClass;
	}

	public Date getStylesheetModifiedDate() {
		return this.stylesheetModifiedDate;
	}

	public void setStylesheetModifiedDate(Date stylesheetModifiedDate) {
		this.stylesheetModifiedDate = stylesheetModifiedDate;
	}

	public String getSubSubSysCode() {
		return this.subSubSysCode;
	}

	public void setSubSubSysCode(String subSubSysCode) {
		this.subSubSysCode = subSubSysCode;
	}

	public String getSubSysCode() {
		return this.subSysCode;
	}

	public void setSubSysCode(String subSysCode) {
		this.subSysCode = subSysCode;
	}

	public String getSupervisorAuthBy() {
		return this.supervisorAuthBy;
	}

	public void setSupervisorAuthBy(String supervisorAuthBy) {
		this.supervisorAuthBy = supervisorAuthBy;
	}

	public Date getSupervisorAuthDate() {
		return this.supervisorAuthDate;
	}

	public void setSupervisorAuthDate(Date supervisorAuthDate) {
		this.supervisorAuthDate = supervisorAuthDate;
	}

	public String getSystemCode() {
		return this.systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getTaskCardCategory() {
		return this.taskCardCategory;
	}

	public void setTaskCardCategory(String taskCardCategory) {
		this.taskCardCategory = taskCardCategory;
	}

	public String getTaskCardDescription() {
		return this.taskCardDescription;
	}

	public void setTaskCardDescription(String taskCardDescription) {
		this.taskCardDescription = taskCardDescription;
	}

	public BigDecimal getTaskCardNumberingSystem() {
		return this.taskCardNumberingSystem;
	}

	public void setTaskCardNumberingSystem(BigDecimal taskCardNumberingSystem) {
		this.taskCardNumberingSystem = taskCardNumberingSystem;
	}

	public byte[] getTaskCardPdf() {
		return this.taskCardPdf;
	}

	public void setTaskCardPdf(byte[] taskCardPdf) {
		this.taskCardPdf = taskCardPdf;
	}

	public String getTechLevel() {
		return this.techLevel;
	}

	public void setTechLevel(String techLevel) {
		this.techLevel = techLevel;
	}

	public String getTemporaryId() {
		return this.temporaryId;
	}

	public void setTemporaryId(String temporaryId) {
		this.temporaryId = temporaryId;
	}

	public String getTestFlight() {
		return this.testFlight;
	}

	public void setTestFlight(String testFlight) {
		this.testFlight = testFlight;
	}

	public BigDecimal getTotalMoment() {
		return this.totalMoment;
	}

	public void setTotalMoment(BigDecimal totalMoment) {
		this.totalMoment = totalMoment;
	}

	public BigDecimal getTraxdocNo() {
		return this.traxdocNo;
	}

	public void setTraxdocNo(BigDecimal traxdocNo) {
		this.traxdocNo = traxdocNo;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUt() {
		return this.ut;
	}

	public void setUt(String ut) {
		this.ut = ut;
	}

	public String getVerified() {
		return this.verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public String getVerifiedBy() {
		return this.verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public Date getVerifiedDate() {
		return this.verifiedDate;
	}

	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
	}

	public String getVibrationCheck() {
		return this.vibrationCheck;
	}

	public void setVibrationCheck(String vibrationCheck) {
		this.vibrationCheck = vibrationCheck;
	}

	public String getWarranty() {
		return this.warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getWeighing() {
		return this.weighing;
	}

	public void setWeighing(String weighing) {
		this.weighing = weighing;
	}

	public String getWeightBalance() {
		return this.weightBalance;
	}

	public void setWeightBalance(String weightBalance) {
		this.weightBalance = weightBalance;
	}

	public String getWeightChg() {
		return this.weightChg;
	}

	public void setWeightChg(String weightChg) {
		this.weightChg = weightChg;
	}

	public BigDecimal getWeightChgNo() {
		return this.weightChgNo;
	}

	public void setWeightChgNo(BigDecimal weightChgNo) {
		this.weightChgNo = weightChgNo;
	}

	public BigDecimal getWeightOff() {
		return this.weightOff;
	}

	public void setWeightOff(BigDecimal weightOff) {
		this.weightOff = weightOff;
	}

	public BigDecimal getWeightOn() {
		return this.weightOn;
	}

	public void setWeightOn(BigDecimal weightOn) {
		this.weightOn = weightOn;
	}

	public String getWoTaskCardXml() {
		return this.woTaskCardXml;
	}

	public void setWoTaskCardXml(String woTaskCardXml) {
		this.woTaskCardXml = woTaskCardXml;
	}

	public String getWorkAccomplished() {
		return this.workAccomplished;
	}

	public void setWorkAccomplished(String workAccomplished) {
		this.workAccomplished = workAccomplished;
	}

	public String getWorkAccomplishedStatus() {
		return this.workAccomplishedStatus;
	}

	public void setWorkAccomplishedStatus(String workAccomplishedStatus) {
		this.workAccomplishedStatus = workAccomplishedStatus;
	}

	public String getWorkArea() {
		return this.workArea;
	}

	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}

	public String getXRay() {
		return this.xRay;
	}

	public void setXRay(String xRay) {
		this.xRay = xRay;
	}

	public String getXmlStatus() {
		return this.xmlStatus;
	}

	public void setXmlStatus(String xmlStatus) {
		this.xmlStatus = xmlStatus;
	}

	public String getZonal() {
		return this.zonal;
	}

	public void setZonal(String zonal) {
		this.zonal = zonal;
	}

	public List<WoTaskCardAcModPlan> getWoTaskCardAcModPlans() {
		return this.woTaskCardAcModPlans;
	}

	public void setWoTaskCardAcModPlans(List<WoTaskCardAcModPlan> woTaskCardAcModPlans) {
		this.woTaskCardAcModPlans = woTaskCardAcModPlans;
	}

	public WoTaskCardAcModPlan addWoTaskCardAcModPlan(WoTaskCardAcModPlan woTaskCardAcModPlan) {
		getWoTaskCardAcModPlans().add(woTaskCardAcModPlan);
		woTaskCardAcModPlan.setWoTaskCard(this);

		return woTaskCardAcModPlan;
	}

	public WoTaskCardAcModPlan removeWoTaskCardAcModPlan(WoTaskCardAcModPlan woTaskCardAcModPlan) {
		getWoTaskCardAcModPlans().remove(woTaskCardAcModPlan);
		woTaskCardAcModPlan.setWoTaskCard(null);

		return woTaskCardAcModPlan;
	}

	public List<WoTaskCardBust> getWoTaskCardBusts() {
		return this.woTaskCardBusts;
	}

	public void setWoTaskCardBusts(List<WoTaskCardBust> woTaskCardBusts) {
		this.woTaskCardBusts = woTaskCardBusts;
	}

	public WoTaskCardBust addWoTaskCardBust(WoTaskCardBust woTaskCardBust) {
		getWoTaskCardBusts().add(woTaskCardBust);
		woTaskCardBust.setWoTaskCard(this);

		return woTaskCardBust;
	}

	public WoTaskCardBust removeWoTaskCardBust(WoTaskCardBust woTaskCardBust) {
		getWoTaskCardBusts().remove(woTaskCardBust);
		woTaskCardBust.setWoTaskCard(null);

		return woTaskCardBust;
	}

	public List<WoTaskCardItem> getWoTaskCardItems() {
		return this.woTaskCardItems;
	}

	public void setWoTaskCardItems(List<WoTaskCardItem> woTaskCardItems) {
		this.woTaskCardItems = woTaskCardItems;
	}

	public WoTaskCardItem addWoTaskCardItem(WoTaskCardItem woTaskCardItem) {
		getWoTaskCardItems().add(woTaskCardItem);
		woTaskCardItem.setWoTaskCard(this);

		return woTaskCardItem;
	}

	public WoTaskCardItem removeWoTaskCardItem(WoTaskCardItem woTaskCardItem) {
		getWoTaskCardItems().remove(woTaskCardItem);
		woTaskCardItem.setWoTaskCard(null);

		return woTaskCardItem;
	}

	public List<WoTaskCardKeyword> getWoTaskCardKeywords() {
		return this.woTaskCardKeywords;
	}

	public void setWoTaskCardKeywords(List<WoTaskCardKeyword> woTaskCardKeywords) {
		this.woTaskCardKeywords = woTaskCardKeywords;
	}

	public WoTaskCardKeyword addWoTaskCardKeyword(WoTaskCardKeyword woTaskCardKeyword) {
		getWoTaskCardKeywords().add(woTaskCardKeyword);
		woTaskCardKeyword.setWoTaskCard(this);

		return woTaskCardKeyword;
	}

	public WoTaskCardKeyword removeWoTaskCardKeyword(WoTaskCardKeyword woTaskCardKeyword) {
		getWoTaskCardKeywords().remove(woTaskCardKeyword);
		woTaskCardKeyword.setWoTaskCard(null);

		return woTaskCardKeyword;
	}

	public List<WoTaskCardSignedPdf> getWoTaskCardSignedPdfs() {
		return this.woTaskCardSignedPdfs;
	}

	public void setWoTaskCardSignedPdfs(List<WoTaskCardSignedPdf> woTaskCardSignedPdfs) {
		this.woTaskCardSignedPdfs = woTaskCardSignedPdfs;
	}

	public WoTaskCardSignedPdf addWoTaskCardSignedPdf(WoTaskCardSignedPdf woTaskCardSignedPdf) {
		getWoTaskCardSignedPdfs().add(woTaskCardSignedPdf);
		woTaskCardSignedPdf.setWoTaskCard(this);

		return woTaskCardSignedPdf;
	}

	public WoTaskCardSignedPdf removeWoTaskCardSignedPdf(WoTaskCardSignedPdf woTaskCardSignedPdf) {
		getWoTaskCardSignedPdfs().remove(woTaskCardSignedPdf);
		woTaskCardSignedPdf.setWoTaskCard(null);

		return woTaskCardSignedPdf;
	}

	public List<WoTaskCardSurvey> getWoTaskCardSurveys() {
		return this.woTaskCardSurveys;
	}

	public void setWoTaskCardSurveys(List<WoTaskCardSurvey> woTaskCardSurveys) {
		this.woTaskCardSurveys = woTaskCardSurveys;
	}

	public WoTaskCardSurvey addWoTaskCardSurvey(WoTaskCardSurvey woTaskCardSurvey) {
		getWoTaskCardSurveys().add(woTaskCardSurvey);
		woTaskCardSurvey.setWoTaskCard(this);

		return woTaskCardSurvey;
	}

	public WoTaskCardSurvey removeWoTaskCardSurvey(WoTaskCardSurvey woTaskCardSurvey) {
		getWoTaskCardSurveys().remove(woTaskCardSurvey);
		woTaskCardSurvey.setWoTaskCard(null);

		return woTaskCardSurvey;
	}

	public List<WoTaskCardWorkInProgress> getWoTaskCardWorkInProgresses() {
		return this.woTaskCardWorkInProgresses;
	}

	public void setWoTaskCardWorkInProgresses(List<WoTaskCardWorkInProgress> woTaskCardWorkInProgresses) {
		this.woTaskCardWorkInProgresses = woTaskCardWorkInProgresses;
	}

	public WoTaskCardWorkInProgress addWoTaskCardWorkInProgress(WoTaskCardWorkInProgress woTaskCardWorkInProgress) {
		getWoTaskCardWorkInProgresses().add(woTaskCardWorkInProgress);
		woTaskCardWorkInProgress.setWoTaskCard(this);

		return woTaskCardWorkInProgress;
	}

	public WoTaskCardWorkInProgress removeWoTaskCardWorkInProgress(WoTaskCardWorkInProgress woTaskCardWorkInProgress) {
		getWoTaskCardWorkInProgresses().remove(woTaskCardWorkInProgress);
		woTaskCardWorkInProgress.setWoTaskCard(null);

		return woTaskCardWorkInProgress;
	}

	public String getFunctionalLocation() {
		return functionalLocation;
	}

	public void setFunctionalLocation(String functionalLocation) {
		this.functionalLocation = functionalLocation;
	}

	public String getEqptNo() {
		return eqptNo;
	}

	public void setEqptNo(String eqptNo) {
		this.eqptNo = eqptNo;
	}
	
	public List<WoTaskCardCustomer> getWoTaskCardCustomers() {
		return this.woTaskCardCustomers;
	}

	public void setWoTaskCardCustomers(List<WoTaskCardCustomer> woTaskCardCustomers) {
		this.woTaskCardCustomers = woTaskCardCustomers;
	}

	public WoTaskCardCustomer addWoTaskCardCustomer(WoTaskCardCustomer woTaskCardCustomer) {
		getWoTaskCardCustomers().add(woTaskCardCustomer);
		woTaskCardCustomer.setWoTaskCard(this);

		return woTaskCardCustomer;
	}

	public WoTaskCardCustomer removeWoTaskCardCustomer(WoTaskCardCustomer woTaskCardCustomer) {
		getWoTaskCardCustomers().remove(woTaskCardCustomer);
		woTaskCardCustomer.setWoTaskCard(null);

		return woTaskCardCustomer;
	}

	/*
	public String getGate() {
		return gate;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}

	public String getSubPhase() {
		return subPhase;
	}

	public void setSubPhase(String subPhase) {
		this.subPhase = subPhase;
	}

	public String getBillableHours() {
		return billableHours;
	}

	public void setBillableHours(String billableHours) {
		this.billableHours = billableHours;
	}
	*/

}