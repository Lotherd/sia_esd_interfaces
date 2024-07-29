package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TASK_CARD database table.
 * 
 */
@Entity
@Table(name="TASK_CARD")
@NamedQuery(name="TaskCard.findAll", query="SELECT t FROM TaskCard t")
public class TaskCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TASK_CARD")
	private String taskCard;

	@Column(name="AC_REWEIGHT")
	private String acReweight;

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

	@Column(name="ALPHNUM_SECTION")
	private String alphnumSection;

	@Column(name="APU_REMOVAL")
	private String apuRemoval;

	private String area;

	@Column(name="ASSY_CODE")
	private String assyCode;

	@Column(name="\"AUTHORIZATION\"")
	private String authorization;

	@Column(name="AUTHORIZED_BY")
	private String authorizedBy;

	@Column(name="AUTHORIZED_DATE")
	private Date authorizedDate;

	@Column(name="BASIC_EMPTY_WEIGHT")
	private BigDecimal basicEmptyWeight;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CABIN_CEILING_PANELS_REM")
	private String cabinCeilingPanelsRem;

	@Column(name="CABIN_FLOOR_PANELS_REMOVAL")
	private String cabinFloorPanelsRemoval;

	@Column(name="CABIN_PRESSURIZATION")
	private String cabinPressurization;

	@Column(name="CARGO_PANELS_REMOVAL")
	private String cargoPanelsRemoval;

	@Column(name="CAT_RATING")
	private String catRating;

	private String cdccl;

	private BigDecimal chapter;

	private String clean;

	private String cmr;

	@Column(name="CMR_CHECK")
	private String cmrCheck;

	@Column(name="CONFLICT_TASK_CARD")
	private String conflictTaskCard;

	@Column(name="CONTROL_AREA")
	private String controlArea;

	private String corrosion;

	@Column(name="CREATE_NR")
	private String createNr;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CREW_CODE")
	private String crewCode;

	@Column(name="CRITICAL_TASK")
	private String criticalTask;

	@Column(name="CRITICAL_TASK_GROUP_NUMBER")
	private String criticalTaskGroupNumber;

	private String defer;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	private String dt;

	private String ectm;

	@Column(name="EDITOR_USED")
	private String editorUsed;

	@Column(name="ELECTRICAL_LOAD_CHANGE_NOTIF")
	private String electricalLoadChangeNotif;

	@Column(name="ELECTRICAL_LOAD_CHG")
	private String electricalLoadChg;

	@Column(name="ELECTRICAL_LOAD_CHG_NO")
	private BigDecimal electricalLoadChgNo;

	@Column(name="ELECTRICAL_POWER_REQ")
	private String electricalPowerReq;

	@Column(name="ENGINE_REMOVAL")
	private String engineRemoval;

	@Column(name="ENGINERUN_UP")
	private String enginerunUp;

	private String et;

	private String etops;

	@Column(name="EXPENDITURE_CODE")
	private String expenditureCode;

	@Column(name="FLAPS_POSITION")
	private String flapsPosition;

	@Column(name="FLIGHT_SURFACE_LIMITATION")
	private String flightSurfaceLimitation;

	@Column(name="FLIGHT_SURFACES_REMOVAL")
	private String flightSurfacesRemoval;

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

	@Column(name="GRAPH_LIST")
	private String graphList;

	@Column(name="GROUND_AIR_LEAK_CHECK")
	private String groundAirLeakCheck;

	@Column(name="GROUND_TIME_REQUIRE")
	private BigDecimal groundTimeRequire;

	@Column(name="HANGAR_SPACE_REQU")
	private String hangarSpaceRequ;

	@Column(name="HYD_GROUND_CHARTREQU")
	private String hydGroundChartrequ;

	@Column(name="HYDRAULIC_POWER_REQ")
	private String hydraulicPowerReq;

	@Column(name="INDEPENDENT_TASK")
	private String independentTask;

	private String ir;

	private String ishtmledited;

	@Column(name="LANDING_GEAR_REMOVAL")
	private String landingGearRemoval;

	@Column(name="LEAK_CHECK")
	private String leakCheck;

	private String lube;

	private String m;

	@Column(name="MANDATORY_CLASSIFICATION")
	private String mandatoryClassification;

	private BigDecimal mechanic;

	@Column(name="MEL_NUMBER")
	private String melNumber;

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

	@Column(name="NEXT_SITE")
	private String nextSite;

	private BigDecimal notes;

	private String ntd;

	@Column(name="ON_CONDITION_TASK_CARD")
	private String onConditionTaskCard;

	@Column(name="OUT_OF_HANGAR")
	private String outOfHangar;

	@Column(name="PAPER_REQUIRED")
	private String paperRequired;

	private BigDecimal paragraph;

	@Column(name="PARENT_TD_LINK_CONTENT_ID")
	private BigDecimal parentTdLinkContentId;

	private String phase;

	@Column(name="PLANNING_NOTES")
	private String planningNotes;

	@Column(name="PM_CATEGORY")
	private String pmCategory;

	@Column(name="PNEUMATIC_POWER")
	private String pneumaticPower;

	private String priority;

	private String pt;

	@Column(name="PYLON_REMOVAL")
	private String pylonRemoval;

	@Column(name="REFERENCE_INTERVAL")
	private String referenceInterval;

	@Column(name="REFERENCE_TASK_CARD")
	private String referenceTaskCard;

	@Column(name="RELEASE_FOR_AUTHORIZATION")
	private String releaseForAuthorization;

	@Column(name="RELEASE_FOR_AUTHORIZATION_ON")
	private Date releaseForAuthorizationOn;

	private String reseq;

	@Column(name="REVISED_BY")
	private String revisedBy;

	@Column(name="REVISED_DATE")
	private Date revisedDate;

	@Column(name="REVISION_ISSUED_BY")
	private String revisionIssuedBy;

	private String revison;

	private String rii;

	private String rvsm;

	@Column(name="\"SECTION\"")
	private BigDecimal section;

	@Column(name="SELL_LABOR_AMOUNT")
	private BigDecimal sellLaborAmount;

	@Column(name="SELL_LABOR_METHOD")
	private String sellLaborMethod;

	@Column(name="SELL_MATERIAL_AMOUNT")
	private BigDecimal sellMaterialAmount;

	@Column(name="SELL_MATERIAL_METHOD")
	private String sellMaterialMethod;

	@Column(name="SELL_OTHER_AMOUNT")
	private BigDecimal sellOtherAmount;

	@Column(name="SELL_OTHER_METHOD")
	private String sellOtherMethod;

	@Column(name="\"SERVICE\"")
	private String service;

	private String site;

	private String slats;

	@Column(name="SORT_ITEM")
	private BigDecimal sortItem;

	@Column(name="SPLIT_FROM")
	private String splitFrom;

	private String spoilers;

	private String sr;

	private String ssid;

	private String status;

	@Column(name="STOWAGE_BIN_REMOVAL")
	private String stowageBinRemoval;

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

	@Column(name="SYSTEM_CODE")
	private String systemCode;

	@Column(name="TASK_CARD_CATEGORY")
	private String taskCardCategory;

	@Column(name="TASK_CARD_DESCRIPTION")
	private String taskCardDescription;

	@Lob
	@Column(name="TASK_CARD_PDF")
	private byte[] taskCardPdf;

	@Column(name="TC_AC_TYPE")
	private String tcAcType;

	@Column(name="TC_COMPANY")
	private String tcCompany;

	@Column(name="TC_SUB")
	private String tcSub;

	@Lob
	private String tcxml;

	@Column(name="TECH_LEVEL")
	private String techLevel;

	@Column(name="TEST_FLIGHT")
	private String testFlight;

	@Column(name="TOTAL_MOMENT")
	private BigDecimal totalMoment;

	@Column(name="TRAXDOC_FILE_NAME")
	private String traxdocFileName;

	@Column(name="TRAXDOC_NO")
	private BigDecimal traxdocNo;

	@Column(name="\"TYPE\"")
	private String type;

	private String ut;

	@Column(name="VIBRATION_CHECK")
	private String vibrationCheck;

	private String warranty;

	private String weighing;

	@Column(name="WEIGHT_BALANCE")
	private String weightBalance;

	@Column(name="WEIGHT_BALANCE_NOTIFICATION")
	private String weightBalanceNotification;

	@Column(name="WEIGHT_CHG")
	private String weightChg;

	@Column(name="WEIGHT_CHG_NO")
	private BigDecimal weightChgNo;

	@Column(name="WEIGHT_OFF")
	private BigDecimal weightOff;

	@Column(name="WEIGHT_ON")
	private BigDecimal weightOn;

	@Column(name="WO_STATUS")
	private String woStatus;

	@Column(name="X_RAY")
	private String xRay;

	private String zonal;
	
	//ESD  TODO
	/*
	private String gate;

	@Column(name="SUB_PHASE")
	private String subPhase;
		
	@Column(name="BILLABLE_HOURS")
	private String billableHours;
*/

	//bi-directional many-to-one association to TaskCardItem
	@OneToMany(mappedBy="taskCardBean")
	private List<TaskCardItem> taskCardItems;

	//bi-directional many-to-one association to TaskCardKeyword
	@OneToMany(mappedBy="taskCardBean")
	private List<TaskCardKeyword> taskCardKeyword;

	//bi-directional many-to-one association to TaskCardKeyword
	@OneToMany(mappedBy="taskCardBean")
	private List<TaskCardExecution> taskCardExecution;

	
	//bi-directional many-to-one association to TaskCardPn
	@OneToMany(mappedBy="taskCardBean")
	private List<TaskCardPn> taskCardPns;

	public TaskCard() {
	}

	public String getTaskCard() {
		return this.taskCard;
	}

	public void setTaskCard(String taskCard) {
		this.taskCard = taskCard;
	}

	public String getAcReweight() {
		return this.acReweight;
	}

	public void setAcReweight(String acReweight) {
		this.acReweight = acReweight;
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

	public String getAlphnumSection() {
		return this.alphnumSection;
	}

	public void setAlphnumSection(String alphnumSection) {
		this.alphnumSection = alphnumSection;
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

	public String getAssyCode() {
		return this.assyCode;
	}

	public void setAssyCode(String assyCode) {
		this.assyCode = assyCode;
	}

	public String getAuthorization() {
		return this.authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getAuthorizedBy() {
		return this.authorizedBy;
	}

	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}

	public Date getAuthorizedDate() {
		return this.authorizedDate;
	}

	public void setAuthorizedDate(Date authorizedDate) {
		this.authorizedDate = authorizedDate;
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

	public String getConflictTaskCard() {
		return this.conflictTaskCard;
	}

	public void setConflictTaskCard(String conflictTaskCard) {
		this.conflictTaskCard = conflictTaskCard;
	}

	public String getControlArea() {
		return this.controlArea;
	}

	public void setControlArea(String controlArea) {
		this.controlArea = controlArea;
	}

	public String getCorrosion() {
		return this.corrosion;
	}

	public void setCorrosion(String corrosion) {
		this.corrosion = corrosion;
	}

	public String getCreateNr() {
		return this.createNr;
	}

	public void setCreateNr(String createNr) {
		this.createNr = createNr;
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

	public String getDefer() {
		return this.defer;
	}

	public void setDefer(String defer) {
		this.defer = defer;
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

	public String getElectricalLoadChangeNotif() {
		return this.electricalLoadChangeNotif;
	}

	public void setElectricalLoadChangeNotif(String electricalLoadChangeNotif) {
		this.electricalLoadChangeNotif = electricalLoadChangeNotif;
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

	public String getEngineRemoval() {
		return this.engineRemoval;
	}

	public void setEngineRemoval(String engineRemoval) {
		this.engineRemoval = engineRemoval;
	}

	public String getEnginerunUp() {
		return this.enginerunUp;
	}

	public void setEnginerunUp(String enginerunUp) {
		this.enginerunUp = enginerunUp;
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

	public String getExpenditureCode() {
		return this.expenditureCode;
	}

	public void setExpenditureCode(String expenditureCode) {
		this.expenditureCode = expenditureCode;
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

	public String getGraphList() {
		return this.graphList;
	}

	public void setGraphList(String graphList) {
		this.graphList = graphList;
	}

	public String getGroundAirLeakCheck() {
		return this.groundAirLeakCheck;
	}

	public void setGroundAirLeakCheck(String groundAirLeakCheck) {
		this.groundAirLeakCheck = groundAirLeakCheck;
	}

	public BigDecimal getGroundTimeRequire() {
		return this.groundTimeRequire;
	}

	public void setGroundTimeRequire(BigDecimal groundTimeRequire) {
		this.groundTimeRequire = groundTimeRequire;
	}

	public String getHangarSpaceRequ() {
		return this.hangarSpaceRequ;
	}

	public void setHangarSpaceRequ(String hangarSpaceRequ) {
		this.hangarSpaceRequ = hangarSpaceRequ;
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

	public String getLandingGearRemoval() {
		return this.landingGearRemoval;
	}

	public void setLandingGearRemoval(String landingGearRemoval) {
		this.landingGearRemoval = landingGearRemoval;
	}

	public String getLeakCheck() {
		return this.leakCheck;
	}

	public void setLeakCheck(String leakCheck) {
		this.leakCheck = leakCheck;
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

	public String getMelNumber() {
		return this.melNumber;
	}

	public void setMelNumber(String melNumber) {
		this.melNumber = melNumber;
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

	public String getNextSite() {
		return this.nextSite;
	}

	public void setNextSite(String nextSite) {
		this.nextSite = nextSite;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public String getNtd() {
		return this.ntd;
	}

	public void setNtd(String ntd) {
		this.ntd = ntd;
	}

	public String getOnConditionTaskCard() {
		return this.onConditionTaskCard;
	}

	public void setOnConditionTaskCard(String onConditionTaskCard) {
		this.onConditionTaskCard = onConditionTaskCard;
	}

	public String getOutOfHangar() {
		return this.outOfHangar;
	}

	public void setOutOfHangar(String outOfHangar) {
		this.outOfHangar = outOfHangar;
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

	public String getPlanningNotes() {
		return this.planningNotes;
	}

	public void setPlanningNotes(String planningNotes) {
		this.planningNotes = planningNotes;
	}

	public String getPmCategory() {
		return this.pmCategory;
	}

	public void setPmCategory(String pmCategory) {
		this.pmCategory = pmCategory;
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

	public String getReleaseForAuthorization() {
		return this.releaseForAuthorization;
	}

	public void setReleaseForAuthorization(String releaseForAuthorization) {
		this.releaseForAuthorization = releaseForAuthorization;
	}

	public Date getReleaseForAuthorizationOn() {
		return this.releaseForAuthorizationOn;
	}

	public void setReleaseForAuthorizationOn(Date releaseForAuthorizationOn) {
		this.releaseForAuthorizationOn = releaseForAuthorizationOn;
	}

	public String getReseq() {
		return this.reseq;
	}

	public void setReseq(String reseq) {
		this.reseq = reseq;
	}

	public String getRevisedBy() {
		return this.revisedBy;
	}

	public void setRevisedBy(String revisedBy) {
		this.revisedBy = revisedBy;
	}

	public Date getRevisedDate() {
		return this.revisedDate;
	}

	public void setRevisedDate(Date revisedDate) {
		this.revisedDate = revisedDate;
	}

	public String getRevisionIssuedBy() {
		return this.revisionIssuedBy;
	}

	public void setRevisionIssuedBy(String revisionIssuedBy) {
		this.revisionIssuedBy = revisionIssuedBy;
	}

	public String getRevison() {
		return this.revison;
	}

	public void setRevison(String revison) {
		this.revison = revison;
	}

	public String getRii() {
		return this.rii;
	}

	public void setRii(String rii) {
		this.rii = rii;
	}

	public String getRvsm() {
		return this.rvsm;
	}

	public void setRvsm(String rvsm) {
		this.rvsm = rvsm;
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

	public String getStowageBinRemoval() {
		return this.stowageBinRemoval;
	}

	public void setStowageBinRemoval(String stowageBinRemoval) {
		this.stowageBinRemoval = stowageBinRemoval;
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

	public byte[] getTaskCardPdf() {
		return this.taskCardPdf;
	}

	public void setTaskCardPdf(byte[] taskCardPdf) {
		this.taskCardPdf = taskCardPdf;
	}

	public String getTcAcType() {
		return this.tcAcType;
	}

	public void setTcAcType(String tcAcType) {
		this.tcAcType = tcAcType;
	}

	public String getTcCompany() {
		return this.tcCompany;
	}

	public void setTcCompany(String tcCompany) {
		this.tcCompany = tcCompany;
	}

	public String getTcSub() {
		return this.tcSub;
	}

	public void setTcSub(String tcSub) {
		this.tcSub = tcSub;
	}

	public String getTcxml() {
		return this.tcxml;
	}

	public void setTcxml(String tcxml) {
		this.tcxml = tcxml;
	}

	public String getTechLevel() {
		return this.techLevel;
	}

	public void setTechLevel(String techLevel) {
		this.techLevel = techLevel;
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

	public String getTraxdocFileName() {
		return this.traxdocFileName;
	}

	public void setTraxdocFileName(String traxdocFileName) {
		this.traxdocFileName = traxdocFileName;
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

	public String getWeightBalanceNotification() {
		return this.weightBalanceNotification;
	}

	public void setWeightBalanceNotification(String weightBalanceNotification) {
		this.weightBalanceNotification = weightBalanceNotification;
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

	public String getWoStatus() {
		return this.woStatus;
	}

	public void setWoStatus(String woStatus) {
		this.woStatus = woStatus;
	}

	public String getXRay() {
		return this.xRay;
	}

	public void setXRay(String xRay) {
		this.xRay = xRay;
	}

	public String getZonal() {
		return this.zonal;
	}

	public void setZonal(String zonal) {
		this.zonal = zonal;
	}

	public List<TaskCardItem> getTaskCardItems() {
		return this.taskCardItems;
	}

	public void setTaskCardItems(List<TaskCardItem> taskCardItems) {
		this.taskCardItems = taskCardItems;
	}

	public TaskCardItem addTaskCardItem(TaskCardItem taskCardItem) {
		getTaskCardItems().add(taskCardItem);
		taskCardItem.setTaskCardBean(this);

		return taskCardItem;
	}

	public TaskCardItem removeTaskCardItem(TaskCardItem taskCardItem) {
		getTaskCardItems().remove(taskCardItem);
		taskCardItem.setTaskCardBean(null);

		return taskCardItem;
	}

	public List<TaskCardPn> getTaskCardPns() {
		return this.taskCardPns;
	}

	public void setTaskCardPns(List<TaskCardPn> taskCardPns) {
		this.taskCardPns = taskCardPns;
	}

	public TaskCardPn addTaskCardPn(TaskCardPn taskCardPn) {
		getTaskCardPns().add(taskCardPn);
		taskCardPn.setTaskCardBean(this);

		return taskCardPn;
	}

	public TaskCardPn removeTaskCardPn(TaskCardPn taskCardPn) {
		getTaskCardPns().remove(taskCardPn);
		taskCardPn.setTaskCardBean(null);

		return taskCardPn;
	}

	public List<TaskCardKeyword> getTaskCardKeyword() {
		return taskCardKeyword;
	}

	public void setTaskCardKeyword(List<TaskCardKeyword> taskCardKeyword) {
		this.taskCardKeyword = taskCardKeyword;
	}

	public List<TaskCardExecution> getTaskCardExecution() {
		return taskCardExecution;
	}

	public void setTaskCardExecution(List<TaskCardExecution> taskCardExecution) {
		this.taskCardExecution = taskCardExecution;
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