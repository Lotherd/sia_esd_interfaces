package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WO_TASK_CARD_ITEM database table.
 * 
 */
@Entity
@Table(name="WO_TASK_CARD_ITEM")
@NamedQuery(name="WoTaskCardItem.findAll", query="SELECT w FROM WoTaskCardItem w")
public class WoTaskCardItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoTaskCardItemPK id;

	@Column(name="ADDITIONAL_WORK")
	private String additionalWork;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="\"CURRENT_USER\"")
	private String currentUser;

	@Column(name="DUAL_INSP_MAN_HOURS_RESERVED")
	private BigDecimal dualInspManHoursReserved;

	@Column(name="DUAL_INSPECTED_BY")
	private String dualInspectedBy;

	@Column(name="DUAL_INSPECTED_DATE")
	private Date dualInspectedDate;

	@Column(name="DUAL_INSPECTOR_MAN_HOURS")
	private BigDecimal dualInspectorManHours;

	@Column(name="DUAL_INSPECTOR_MAN_REQUIRE")
	private BigDecimal dualInspectorManRequire;

	@Column(name="DUAL_INSPECTOR_SKILL")
	private String dualInspectorSkill;

	@Column(name="DUAL_INSPECTOR_STATUS")
	private String dualInspectorStatus;

	@Column(name="DUPLICATE_INSPECTION")
	private String duplicateInspection;

	@Column(name="ENTERED_ERROR_BY")
	private String enteredErrorBy;

	@Column(name="ENTERED_ERROR_DATE")
	private Date enteredErrorDate;

	@Column(name="ENTERED_IN_ERROR")
	private String enteredInError;

	@Column(name="FILE_NAME")
	private String fileName;

	private String filename;

	@Column(name="IN_USE")
	private String inUse;

	@Column(name="INFORMATIONAL_ONLY")
	private String informationalOnly;

	@Column(name="INSPECTED_BY")
	private String inspectedBy;

	@Column(name="INSPECTED_DATE")
	private Date inspectedDate;

	@Column(name="INSPECTION_ONLY_ITEM")
	private String inspectionOnlyItem;

	private String inspector;

	@Column(name="INSPECTOR_MAN_HOURS")
	private BigDecimal inspectorManHours;

	@Column(name="INSPECTOR_MAN_HOURS_RESERVED")
	private BigDecimal inspectorManHoursReserved;

	@Column(name="INSPECTOR_MAN_REQUIRE")
	private BigDecimal inspectorManRequire;

	@Column(name="INSPECTOR_SKILL")
	private String inspectorSkill;

	@Column(name="INSPECTOR_SKILL_LEVEL")
	private String inspectorSkillLevel;

	@Column(name="INSPECTOR_STATUS")
	private String inspectorStatus;

	@Column(name="ITEM_BY_REJECTION")
	private BigDecimal itemByRejection;

	@Column(name="ITEM_NOT_EFFECTIVE")
	private String itemNotEffective;

	@Column(name="ITEM_TYPE")
	private String itemType;

	@Column(name="LOG_IN_USERS")
	private BigDecimal logInUsers;

	@Column(name="MAIN_SKILL")
	private String mainSkill;

	@Column(name="MAN_HOURS")
	private BigDecimal manHours;

	@Column(name="MAN_HOURS_RESERVED")
	private BigDecimal manHoursReserved;

	@Column(name="MAN_REQUIRE")
	private BigDecimal manRequire;

	@Column(name="MANUAL_ITEM")
	private String manualItem;

	private String mechanic;

	@Column(name="MECHANIC_COMP_BY")
	private String mechanicCompBy;

	@Column(name="MECHANIC_COMP_DATE")
	private Date mechanicCompDate;

	@Column(name="MECHANIC_SKILL_LEVEL")
	private String mechanicSkillLevel;

	@Column(name="MECHANIC_STATUS")
	private String mechanicStatus;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private String mpd;

	@Column(name="MPD_MAN_HOURS")
	private BigDecimal mpdManHours;

	@Column(name="MRS_REQUIRED")
	private String mrsRequired;

	@Column(name="NOT_APPLICABLE")
	private String notApplicable;

	private BigDecimal notes;

	@Column(name="NOTIFY_APPEARANCE")
	private String notifyAppearance;

	@Column(name="NOTIFY_ENGINEERING")
	private String notifyEngineering;

	@Column(name="NOTIFY_INSPECTOR")
	private String notifyInspector;

	@Column(name="NOTIFY_PLANNING")
	private String notifyPlanning;

	@Column(name="NR_TC_REFERENCE")
	private String nrTcReference;

	@Column(name="OP_HRS")
	private BigDecimal opHrs;

	@Column(name="PANEL_REF")
	private String panelRef;

	@Column(name="PARTIAL_WORK")
	private String partialWork;

	@Column(name="PARTIAL_WORK_BY")
	private String partialWorkBy;

	@Column(name="PARTIAL_WORK_DATE")
	private Date partialWorkDate;

	private String revision;

	private String rii;

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

	private String skill;

	@Column(name="SORT_ITEM")
	private BigDecimal sortItem;

	private String status;

	@Lob
	@Column(name="TASK_CARD_TEXT")
	private String taskCardText;

	@Lob
	@Column(name="TASK_CARD_TEXT_BLOB")
	private byte[] taskCardTextBlob;

	@Lob
	@Column(name="TASK_CARD_TEXT_PRINT")
	private String taskCardTextPrint;

	@Column(name="TD_LINK_CONTENT_ID")
	private BigDecimal tdLinkContentId;

	@Column(name="TRAXDOC_NO")
	private BigDecimal traxdocNo;

	@Column(name="TRAXDOC_REVISION")
	private String traxdocRevision;

	@Lob
	@Column(name="TRAXDOC_XML")
	private byte[] traxdocXml;

	@Column(name="WORK_ACCOMPLISHED")
	private String workAccomplished;
	
	@Column(name = "EXTERNAL_CUST_REF")
	private String externalCustRef;
	
	@Column(name="OPS_NO")
	private String opsNo;

	//bi-directional many-to-one association to WoTaskCard
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="AC", referencedColumnName="AC", insertable=false, updatable=false),
		@JoinColumn(name="TASK_CARD", referencedColumnName="TASK_CARD", insertable=false, updatable=false),
		@JoinColumn(name="TASK_CARD_PN", referencedColumnName="PN", insertable=false, updatable=false),
		@JoinColumn(name="TASK_CARD_PN_SN", referencedColumnName="PN_SN", insertable=false, updatable=false),
		@JoinColumn(name="WO", referencedColumnName="WO", insertable=false, updatable=false)
		})
	private WoTaskCard woTaskCard;

	public WoTaskCardItem() {
	}

	public WoTaskCardItemPK getId() {
		return this.id;
	}

	public void setId(WoTaskCardItemPK id) {
		this.id = id;
	}

	public String getAdditionalWork() {
		return this.additionalWork;
	}

	public void setAdditionalWork(String additionalWork) {
		this.additionalWork = additionalWork;
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

	public String getCurrentUser() {
		return this.currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public BigDecimal getDualInspManHoursReserved() {
		return this.dualInspManHoursReserved;
	}

	public void setDualInspManHoursReserved(BigDecimal dualInspManHoursReserved) {
		this.dualInspManHoursReserved = dualInspManHoursReserved;
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

	public BigDecimal getDualInspectorManHours() {
		return this.dualInspectorManHours;
	}

	public void setDualInspectorManHours(BigDecimal dualInspectorManHours) {
		this.dualInspectorManHours = dualInspectorManHours;
	}

	public BigDecimal getDualInspectorManRequire() {
		return this.dualInspectorManRequire;
	}

	public void setDualInspectorManRequire(BigDecimal dualInspectorManRequire) {
		this.dualInspectorManRequire = dualInspectorManRequire;
	}

	public String getDualInspectorSkill() {
		return this.dualInspectorSkill;
	}

	public void setDualInspectorSkill(String dualInspectorSkill) {
		this.dualInspectorSkill = dualInspectorSkill;
	}

	public String getDualInspectorStatus() {
		return this.dualInspectorStatus;
	}

	public void setDualInspectorStatus(String dualInspectorStatus) {
		this.dualInspectorStatus = dualInspectorStatus;
	}

	public String getDuplicateInspection() {
		return this.duplicateInspection;
	}

	public void setDuplicateInspection(String duplicateInspection) {
		this.duplicateInspection = duplicateInspection;
	}

	public String getEnteredErrorBy() {
		return this.enteredErrorBy;
	}

	public void setEnteredErrorBy(String enteredErrorBy) {
		this.enteredErrorBy = enteredErrorBy;
	}

	public Date getEnteredErrorDate() {
		return this.enteredErrorDate;
	}

	public void setEnteredErrorDate(Date enteredErrorDate) {
		this.enteredErrorDate = enteredErrorDate;
	}

	public String getEnteredInError() {
		return this.enteredInError;
	}

	public void setEnteredInError(String enteredInError) {
		this.enteredInError = enteredInError;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getInUse() {
		return this.inUse;
	}

	public void setInUse(String inUse) {
		this.inUse = inUse;
	}

	public String getInformationalOnly() {
		return this.informationalOnly;
	}

	public void setInformationalOnly(String informationalOnly) {
		this.informationalOnly = informationalOnly;
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

	public String getInspectionOnlyItem() {
		return this.inspectionOnlyItem;
	}

	public void setInspectionOnlyItem(String inspectionOnlyItem) {
		this.inspectionOnlyItem = inspectionOnlyItem;
	}

	public String getInspector() {
		return this.inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public BigDecimal getInspectorManHours() {
		return this.inspectorManHours;
	}

	public void setInspectorManHours(BigDecimal inspectorManHours) {
		this.inspectorManHours = inspectorManHours;
	}

	public BigDecimal getInspectorManHoursReserved() {
		return this.inspectorManHoursReserved;
	}

	public void setInspectorManHoursReserved(BigDecimal inspectorManHoursReserved) {
		this.inspectorManHoursReserved = inspectorManHoursReserved;
	}

	public BigDecimal getInspectorManRequire() {
		return this.inspectorManRequire;
	}

	public void setInspectorManRequire(BigDecimal inspectorManRequire) {
		this.inspectorManRequire = inspectorManRequire;
	}

	public String getInspectorSkill() {
		return this.inspectorSkill;
	}

	public void setInspectorSkill(String inspectorSkill) {
		this.inspectorSkill = inspectorSkill;
	}

	public String getInspectorSkillLevel() {
		return this.inspectorSkillLevel;
	}

	public void setInspectorSkillLevel(String inspectorSkillLevel) {
		this.inspectorSkillLevel = inspectorSkillLevel;
	}

	public String getInspectorStatus() {
		return this.inspectorStatus;
	}

	public void setInspectorStatus(String inspectorStatus) {
		this.inspectorStatus = inspectorStatus;
	}

	public BigDecimal getItemByRejection() {
		return this.itemByRejection;
	}

	public void setItemByRejection(BigDecimal itemByRejection) {
		this.itemByRejection = itemByRejection;
	}

	public String getItemNotEffective() {
		return this.itemNotEffective;
	}

	public void setItemNotEffective(String itemNotEffective) {
		this.itemNotEffective = itemNotEffective;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getLogInUsers() {
		return this.logInUsers;
	}

	public void setLogInUsers(BigDecimal logInUsers) {
		this.logInUsers = logInUsers;
	}

	public String getMainSkill() {
		return this.mainSkill;
	}

	public void setMainSkill(String mainSkill) {
		this.mainSkill = mainSkill;
	}

	public BigDecimal getManHours() {
		return this.manHours;
	}

	public void setManHours(BigDecimal manHours) {
		this.manHours = manHours;
	}

	public BigDecimal getManHoursReserved() {
		return this.manHoursReserved;
	}

	public void setManHoursReserved(BigDecimal manHoursReserved) {
		this.manHoursReserved = manHoursReserved;
	}

	public BigDecimal getManRequire() {
		return this.manRequire;
	}

	public void setManRequire(BigDecimal manRequire) {
		this.manRequire = manRequire;
	}

	public String getManualItem() {
		return this.manualItem;
	}

	public void setManualItem(String manualItem) {
		this.manualItem = manualItem;
	}

	public String getMechanic() {
		return this.mechanic;
	}

	public void setMechanic(String mechanic) {
		this.mechanic = mechanic;
	}

	public String getMechanicCompBy() {
		return this.mechanicCompBy;
	}

	public void setMechanicCompBy(String mechanicCompBy) {
		this.mechanicCompBy = mechanicCompBy;
	}

	public Date getMechanicCompDate() {
		return this.mechanicCompDate;
	}

	public void setMechanicCompDate(Date mechanicCompDate) {
		this.mechanicCompDate = mechanicCompDate;
	}

	public String getMechanicSkillLevel() {
		return this.mechanicSkillLevel;
	}

	public void setMechanicSkillLevel(String mechanicSkillLevel) {
		this.mechanicSkillLevel = mechanicSkillLevel;
	}

	public String getMechanicStatus() {
		return this.mechanicStatus;
	}

	public void setMechanicStatus(String mechanicStatus) {
		this.mechanicStatus = mechanicStatus;
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

	public String getMpd() {
		return this.mpd;
	}

	public void setMpd(String mpd) {
		this.mpd = mpd;
	}

	public BigDecimal getMpdManHours() {
		return this.mpdManHours;
	}

	public void setMpdManHours(BigDecimal mpdManHours) {
		this.mpdManHours = mpdManHours;
	}

	public String getMrsRequired() {
		return this.mrsRequired;
	}

	public void setMrsRequired(String mrsRequired) {
		this.mrsRequired = mrsRequired;
	}

	public String getNotApplicable() {
		return this.notApplicable;
	}

	public void setNotApplicable(String notApplicable) {
		this.notApplicable = notApplicable;
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

	public String getNrTcReference() {
		return this.nrTcReference;
	}

	public void setNrTcReference(String nrTcReference) {
		this.nrTcReference = nrTcReference;
	}

	public BigDecimal getOpHrs() {
		return this.opHrs;
	}

	public void setOpHrs(BigDecimal opHrs) {
		this.opHrs = opHrs;
	}

	public String getPanelRef() {
		return this.panelRef;
	}

	public void setPanelRef(String panelRef) {
		this.panelRef = panelRef;
	}

	public String getPartialWork() {
		return this.partialWork;
	}

	public void setPartialWork(String partialWork) {
		this.partialWork = partialWork;
	}

	public String getPartialWorkBy() {
		return this.partialWorkBy;
	}

	public void setPartialWorkBy(String partialWorkBy) {
		this.partialWorkBy = partialWorkBy;
	}

	public Date getPartialWorkDate() {
		return this.partialWorkDate;
	}

	public void setPartialWorkDate(Date partialWorkDate) {
		this.partialWorkDate = partialWorkDate;
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

	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public BigDecimal getSortItem() {
		return this.sortItem;
	}

	public void setSortItem(BigDecimal sortItem) {
		this.sortItem = sortItem;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskCardText() {
		return this.taskCardText;
	}

	public void setTaskCardText(String taskCardText) {
		this.taskCardText = taskCardText;
	}

	public byte[] getTaskCardTextBlob() {
		return this.taskCardTextBlob;
	}

	public void setTaskCardTextBlob(byte[] taskCardTextBlob) {
		this.taskCardTextBlob = taskCardTextBlob;
	}

	public String getTaskCardTextPrint() {
		return this.taskCardTextPrint;
	}

	public void setTaskCardTextPrint(String taskCardTextPrint) {
		this.taskCardTextPrint = taskCardTextPrint;
	}

	public BigDecimal getTdLinkContentId() {
		return this.tdLinkContentId;
	}

	public void setTdLinkContentId(BigDecimal tdLinkContentId) {
		this.tdLinkContentId = tdLinkContentId;
	}

	public BigDecimal getTraxdocNo() {
		return this.traxdocNo;
	}

	public void setTraxdocNo(BigDecimal traxdocNo) {
		this.traxdocNo = traxdocNo;
	}

	public String getTraxdocRevision() {
		return this.traxdocRevision;
	}

	public void setTraxdocRevision(String traxdocRevision) {
		this.traxdocRevision = traxdocRevision;
	}

	public byte[] getTraxdocXml() {
		return this.traxdocXml;
	}

	public void setTraxdocXml(byte[] traxdocXml) {
		this.traxdocXml = traxdocXml;
	}

	public String getWorkAccomplished() {
		return this.workAccomplished;
	}

	public void setWorkAccomplished(String workAccomplished) {
		this.workAccomplished = workAccomplished;
	}

	public WoTaskCard getWoTaskCard() {
		return this.woTaskCard;
	}

	public void setWoTaskCard(WoTaskCard woTaskCard) {
		this.woTaskCard = woTaskCard;
	}

	public String getExternalCustRef() {
		return externalCustRef;
	}

	public void setExternalCustRef(String externalCustRef) {
		this.externalCustRef = externalCustRef;
	}

	public String getOpsNo() {
		return opsNo;
	}

	public void setOpsNo(String opsNo) {
		this.opsNo = opsNo;
	}
	
	
}