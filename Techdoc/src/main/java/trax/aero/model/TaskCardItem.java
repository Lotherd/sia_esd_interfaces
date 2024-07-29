package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TASK_CARD_ITEM database table.
 * 
 */
@Entity
@Table(name="TASK_CARD_ITEM")
@NamedQuery(name="TaskCardItem.findAll", query="SELECT t FROM TaskCardItem t")
public class TaskCardItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TaskCardItemPK id;

	@Column(name="APPLIC_ID")
	private String applicId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DUAL_INSP_MAN_HOURS_ALTERED")
	private String dualInspManHoursAltered;

	@Column(name="DUAL_INSPECTOR_MAN_HOURS")
	private BigDecimal dualInspectorManHours;

	@Column(name="DUAL_INSPECTOR_MAN_REQUIRE")
	private BigDecimal dualInspectorManRequire;

	@Column(name="DUAL_INSPECTOR_SKILL")
	private String dualInspectorSkill;

	@Column(name="DUPLICATE_INSPECTION")
	private String duplicateInspection;

	@Column(name="EXTERNAL_CUST_REF")
	private String externalCustRef;

	@Column(name="FILE_NAME")
	private String fileName;

	private String filename;

	@Column(name="INFORMATIONAL_ONLY")
	private String informationalOnly;

	@Column(name="INSP_MAN_HOURS_ALTERED")
	private String inspManHoursAltered;

	private String inspector;

	@Column(name="INSPECTOR_MAN_HOURS")
	private BigDecimal inspectorManHours;

	@Column(name="INSPECTOR_MAN_REQUIRE")
	private BigDecimal inspectorManRequire;

	@Column(name="INSPECTOR_SKILL")
	private String inspectorSkill;

	@Column(name="INSPECTOR_SKILL_LEVEL")
	private String inspectorSkillLevel;

	@Column(name="ITEM_TYPE")
	private String itemType;

	@Column(name="MAIN_SKILL")
	private String mainSkill;

	@Column(name="MAN_HOURS")
	private BigDecimal manHours;

	@Column(name="MAN_HOURS_ALTERED")
	private String manHoursAltered;

	@Column(name="MAN_REQUIRE")
	private BigDecimal manRequire;

	@Column(name="MANUAL_ITEM")
	private String manualItem;

	private String mechanic;

	@Column(name="MECHANIC_SKILL_LEVEL")
	private String mechanicSkillLevel;

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

	@Column(name="OP_HRS")
	private BigDecimal opHrs;

	@Column(name="PAGE_PDF_SIGNED")
	private BigDecimal pagePdfSigned;

	private String phase;

	private String revision;

	@Column(name="RTF_CONVERTED")
	private String rtfConverted;

	private String skill;

	@Column(name="SORT_ITEM")
	private BigDecimal sortItem;

	@Column(name="SUPPLEMENTAL_FLAG")
	private String supplementalFlag;

	@Lob
	@Column(name="TASK_CARD_TEXT")
	private String taskCardText;

	@Lob
	@Column(name="TASK_CARD_TEXT_BLOB")
	private byte[] taskCardTextBlob;

	@Column(name="TD_LINK_CONTENT_ID")
	private BigDecimal tdLinkContentId;

	@Column(name="TRAXDOC_NO")
	private BigDecimal traxdocNo;

	@Column(name="TRAXDOC_REVISION")
	private String traxdocRevision;

	@Column(name="OPS_NO")
	private String opsNo;
	
	@Lob
	@Column(name="TRAXDOC_XML")
	private byte[] traxdocXml;

	//bi-directional many-to-one association to TaskCard
	@ManyToOne
	@JoinColumn(name="TASK_CARD", insertable=false, updatable=false)
	private TaskCard taskCardBean;

	public TaskCardItem() {
	}

	public TaskCardItemPK getId() {
		return this.id;
	}

	public void setId(TaskCardItemPK id) {
		this.id = id;
	}

	public String getApplicId() {
		return this.applicId;
	}

	public void setApplicId(String applicId) {
		this.applicId = applicId;
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

	public String getDualInspManHoursAltered() {
		return this.dualInspManHoursAltered;
	}

	public void setDualInspManHoursAltered(String dualInspManHoursAltered) {
		this.dualInspManHoursAltered = dualInspManHoursAltered;
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

	public String getDuplicateInspection() {
		return this.duplicateInspection;
	}

	public void setDuplicateInspection(String duplicateInspection) {
		this.duplicateInspection = duplicateInspection;
	}

	public String getExternalCustRef() {
		return this.externalCustRef;
	}

	public void setExternalCustRef(String externalCustRef) {
		this.externalCustRef = externalCustRef;
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

	public String getInformationalOnly() {
		return this.informationalOnly;
	}

	public void setInformationalOnly(String informationalOnly) {
		this.informationalOnly = informationalOnly;
	}

	public String getInspManHoursAltered() {
		return this.inspManHoursAltered;
	}

	public void setInspManHoursAltered(String inspManHoursAltered) {
		this.inspManHoursAltered = inspManHoursAltered;
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

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
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

	public String getManHoursAltered() {
		return this.manHoursAltered;
	}

	public void setManHoursAltered(String manHoursAltered) {
		this.manHoursAltered = manHoursAltered;
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

	public String getMechanicSkillLevel() {
		return this.mechanicSkillLevel;
	}

	public void setMechanicSkillLevel(String mechanicSkillLevel) {
		this.mechanicSkillLevel = mechanicSkillLevel;
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

	public BigDecimal getOpHrs() {
		return this.opHrs;
	}

	public void setOpHrs(BigDecimal opHrs) {
		this.opHrs = opHrs;
	}

	public BigDecimal getPagePdfSigned() {
		return this.pagePdfSigned;
	}

	public void setPagePdfSigned(BigDecimal pagePdfSigned) {
		this.pagePdfSigned = pagePdfSigned;
	}

	public String getPhase() {
		return this.phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getRevision() {
		return this.revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getRtfConverted() {
		return this.rtfConverted;
	}

	public void setRtfConverted(String rtfConverted) {
		this.rtfConverted = rtfConverted;
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

	public String getSupplementalFlag() {
		return this.supplementalFlag;
	}

	public void setSupplementalFlag(String supplementalFlag) {
		this.supplementalFlag = supplementalFlag;
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

	public TaskCard getTaskCardBean() {
		return this.taskCardBean;
	}

	public void setTaskCardBean(TaskCard taskCardBean) {
		this.taskCardBean = taskCardBean;
	}

	public String getOpsNo() {
		return opsNo;
	}

	public void setOpsNo(String opsNo) {
		this.opsNo = opsNo;
	}

}