package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TASK_CARD_CONTROL_MASTER database table.
 * 
 */
@Entity
@Table(name="TASK_CARD_CONTROL_MASTER")
@NamedQuery(name="TaskCardControlMaster.findAll", query="SELECT t FROM TaskCardControlMaster t")
public class TaskCardControlMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TaskCardControlMasterPK id;

	@Column(name="AREA_ASSOCIATED")
	private String areaAssociated;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CODE_DESCRIPTION")
	private String codeDescription;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	@Column(name="MAN_HOURS")
	private BigDecimal manHours;

	@Column(name="MAN_HOURS_CLEARED")
	private BigDecimal manHoursCleared;

	@Column(name="MAN_HOURS_CLOSED")
	private BigDecimal manHoursClosed;

	@Column(name="MAN_REQ_CLEARED")
	private BigDecimal manReqCleared;

	@Column(name="MAN_REQ_CLOSED")
	private BigDecimal manReqClosed;

	@Column(name="MAN_REQUIRE")
	private BigDecimal manRequire;

	@Column(name="MANUAL_REF_CLOSED")
	private String manualRefClosed;

	@Column(name="MANUAL_REFERENCES")
	private String manualReferences;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="NH_PANEL")
	private String nhPanel;

	private BigDecimal notes;

	@Column(name="OP_HRS")
	private BigDecimal opHrs;

	@Column(name="PANEL_CLOSE_TASK_CARD")
	private String panelCloseTaskCard;

	@Column(name="PANEL_OPEN_TASK_CARD")
	private String panelOpenTaskCard;

	@Column(name="PARENT_CODE")
	private String parentCode;

	private String rii;

	@Column(name="SEALANT_REQ")
	private String sealantReq;

	private String skill;

	@Column(name="SKILL_CLEARED")
	private String skillCleared;

	@Column(name="SKILL_CLOSED")
	private String skillClosed;

	@Column(name="SPLIT_ZONE")
	private String splitZone;

	@Column(name="ZONE_ASSOCIATED")
	private String zoneAssociated;

	public TaskCardControlMaster() {
	}

	public TaskCardControlMasterPK getId() {
		return this.id;
	}

	public void setId(TaskCardControlMasterPK id) {
		this.id = id;
	}

	public String getAreaAssociated() {
		return this.areaAssociated;
	}

	public void setAreaAssociated(String areaAssociated) {
		this.areaAssociated = areaAssociated;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public String getCodeDescription() {
		return this.codeDescription;
	}

	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
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

	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public BigDecimal getManHours() {
		return this.manHours;
	}

	public void setManHours(BigDecimal manHours) {
		this.manHours = manHours;
	}

	public BigDecimal getManHoursCleared() {
		return this.manHoursCleared;
	}

	public void setManHoursCleared(BigDecimal manHoursCleared) {
		this.manHoursCleared = manHoursCleared;
	}

	public BigDecimal getManHoursClosed() {
		return this.manHoursClosed;
	}

	public void setManHoursClosed(BigDecimal manHoursClosed) {
		this.manHoursClosed = manHoursClosed;
	}

	public BigDecimal getManReqCleared() {
		return this.manReqCleared;
	}

	public void setManReqCleared(BigDecimal manReqCleared) {
		this.manReqCleared = manReqCleared;
	}

	public BigDecimal getManReqClosed() {
		return this.manReqClosed;
	}

	public void setManReqClosed(BigDecimal manReqClosed) {
		this.manReqClosed = manReqClosed;
	}

	public BigDecimal getManRequire() {
		return this.manRequire;
	}

	public void setManRequire(BigDecimal manRequire) {
		this.manRequire = manRequire;
	}

	public String getManualRefClosed() {
		return this.manualRefClosed;
	}

	public void setManualRefClosed(String manualRefClosed) {
		this.manualRefClosed = manualRefClosed;
	}

	public String getManualReferences() {
		return this.manualReferences;
	}

	public void setManualReferences(String manualReferences) {
		this.manualReferences = manualReferences;
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

	public String getNhPanel() {
		return this.nhPanel;
	}

	public void setNhPanel(String nhPanel) {
		this.nhPanel = nhPanel;
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

	public String getPanelCloseTaskCard() {
		return this.panelCloseTaskCard;
	}

	public void setPanelCloseTaskCard(String panelCloseTaskCard) {
		this.panelCloseTaskCard = panelCloseTaskCard;
	}

	public String getPanelOpenTaskCard() {
		return this.panelOpenTaskCard;
	}

	public void setPanelOpenTaskCard(String panelOpenTaskCard) {
		this.panelOpenTaskCard = panelOpenTaskCard;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getRii() {
		return this.rii;
	}

	public void setRii(String rii) {
		this.rii = rii;
	}

	public String getSealantReq() {
		return this.sealantReq;
	}

	public void setSealantReq(String sealantReq) {
		this.sealantReq = sealantReq;
	}

	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getSkillCleared() {
		return this.skillCleared;
	}

	public void setSkillCleared(String skillCleared) {
		this.skillCleared = skillCleared;
	}

	public String getSkillClosed() {
		return this.skillClosed;
	}

	public void setSkillClosed(String skillClosed) {
		this.skillClosed = skillClosed;
	}

	public String getSplitZone() {
		return this.splitZone;
	}

	public void setSplitZone(String splitZone) {
		this.splitZone = splitZone;
	}

	public String getZoneAssociated() {
		return this.zoneAssociated;
	}

	public void setZoneAssociated(String zoneAssociated) {
		this.zoneAssociated = zoneAssociated;
	}

}