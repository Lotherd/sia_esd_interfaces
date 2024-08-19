package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WO_SHOP_DETAIL database table.
 * 
 */
@Entity
@Table(name="WO_SHOP_DETAIL")
@NamedQuery(name="WoShopDetail.findAll", query="SELECT w FROM WoShopDetail w")
public class WoShopDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoShopDetailPK id;

	@Column(name="ADD_TO_MANUFACTURE_ORDER")
	private String addToManufactureOrder;

	@Column(name="APPROVED_CERTIFICATE")
	private String approvedCertificate;

	private BigDecimal batch;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CERTIFICATION_NO")
	private String certificationNo;

	@Column(name="CERTIFIED_BY")
	private String certifiedBy;

	@Column(name="CERTIFIED_DATE")
	private Date certifiedDate;

	@Column(name="CERTIFYING_STAFF")
	private String certifyingStaff;

	@Column(name="CLOSED_BY")
	private String closedBy;

	@Column(name="CLOSED_DATE")
	private Date closedDate;

	@Column(name="COMPLETED_BY")
	private String completedBy;

	@Column(name="COMPLETION_DATE")
	private Date completionDate;

	@Column(name="COMPLETION_TIME")
	private Date completionTime;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DAMAGE_INDICATOR")
	private String damageIndicator;

	@Column(name="DOCUMNET_NO")
	private BigDecimal documnetNo;

	@Column(name="INSURANCE_CLAIM")
	private String insuranceClaim;

	@Column(name="INSURANCE_CLAIM_NUMBER")
	private String insuranceClaimNumber;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="NON_INVENTORY_TC")
	private String nonInventoryTc;

	@Column(name="NON_INVENTORY_WO")
	private String nonInventoryWo;

	private BigDecimal notes;

	@Column(name="NOTES_FORM1")
	private BigDecimal notesForm1;

	private String pn;

	@Column(name="PN_CONDITION")
	private String pnCondition;

	@Column(name="PN_DESCRIPTION")
	private String pnDescription;

	@Column(name="PN_MODIFIED_TO")
	private String pnModifiedTo;

	@Column(name="PN_QTY")
	private BigDecimal pnQty;

	@Column(name="PN_SN")
	private String pnSn;

	private String priority;

	@Column(name="RECEIVED_DATE")
	private Date receivedDate;

	@Column(name="SCHEDULE_COMPLETION_DATE")
	private Date scheduleCompletionDate;

	@Column(name="SCHEDULE_COMPLETION_HOUR")
	private BigDecimal scheduleCompletionHour;

	@Column(name="SCHEDULE_COMPLETION_MINUTE")
	private BigDecimal scheduleCompletionMinute;

	@Column(name="SCHEDULE_START_DATE")
	private Date scheduleStartDate;

	@Column(name="SCHEDULE_START_HOUR")
	private BigDecimal scheduleStartHour;

	@Column(name="SCHEDULE_START_MINUTE")
	private BigDecimal scheduleStartMinute;

	@Column(name="SHELF_LIFE_EXPIRATION")
	private Date shelfLifeExpiration;

	@Lob
	@Column(name="SIGNED_PDF")
	private byte[] signedPdf;

	@Column(name="SN_MODIFIED_TO")
	private String snModifiedTo;

	private String status;

	@Column(name="TOOL_CALIBRATION_NO")
	private String toolCalibrationNo;

	@Column(name="WARRANTY_INDICATOR")
	private String warrantyIndicator;

	@Column(name="WO_DESCRIPTION")
	private String woDescription;

	//bi-directional many-to-one association to Wo
	@ManyToOne
	@JoinColumn(name="WO" , insertable=false, updatable=false)
	private Wo woBean;

	public WoShopDetail() {
	}

	public WoShopDetailPK getId() {
		return this.id;
	}

	public void setId(WoShopDetailPK id) {
		this.id = id;
	}

	public String getAddToManufactureOrder() {
		return this.addToManufactureOrder;
	}

	public void setAddToManufactureOrder(String addToManufactureOrder) {
		this.addToManufactureOrder = addToManufactureOrder;
	}

	public String getApprovedCertificate() {
		return this.approvedCertificate;
	}

	public void setApprovedCertificate(String approvedCertificate) {
		this.approvedCertificate = approvedCertificate;
	}

	public BigDecimal getBatch() {
		return this.batch;
	}

	public void setBatch(BigDecimal batch) {
		this.batch = batch;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public String getCertificationNo() {
		return this.certificationNo;
	}

	public void setCertificationNo(String certificationNo) {
		this.certificationNo = certificationNo;
	}

	public String getCertifiedBy() {
		return this.certifiedBy;
	}

	public void setCertifiedBy(String certifiedBy) {
		this.certifiedBy = certifiedBy;
	}

	public Date getCertifiedDate() {
		return this.certifiedDate;
	}

	public void setCertifiedDate(Date certifiedDate) {
		this.certifiedDate = certifiedDate;
	}

	public String getCertifyingStaff() {
		return this.certifyingStaff;
	}

	public void setCertifyingStaff(String certifyingStaff) {
		this.certifyingStaff = certifyingStaff;
	}

	public String getClosedBy() {
		return this.closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	public Date getClosedDate() {
		return this.closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getCompletedBy() {
		return this.completedBy;
	}

	public void setCompletedBy(String completedBy) {
		this.completedBy = completedBy;
	}

	public Date getCompletionDate() {
		return this.completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public Date getCompletionTime() {
		return this.completionTime;
	}

	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
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

	public String getDamageIndicator() {
		return this.damageIndicator;
	}

	public void setDamageIndicator(String damageIndicator) {
		this.damageIndicator = damageIndicator;
	}

	public BigDecimal getDocumnetNo() {
		return this.documnetNo;
	}

	public void setDocumnetNo(BigDecimal documnetNo) {
		this.documnetNo = documnetNo;
	}

	public String getInsuranceClaim() {
		return this.insuranceClaim;
	}

	public void setInsuranceClaim(String insuranceClaim) {
		this.insuranceClaim = insuranceClaim;
	}

	public String getInsuranceClaimNumber() {
		return this.insuranceClaimNumber;
	}

	public void setInsuranceClaimNumber(String insuranceClaimNumber) {
		this.insuranceClaimNumber = insuranceClaimNumber;
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

	public String getNonInventoryTc() {
		return this.nonInventoryTc;
	}

	public void setNonInventoryTc(String nonInventoryTc) {
		this.nonInventoryTc = nonInventoryTc;
	}

	public String getNonInventoryWo() {
		return this.nonInventoryWo;
	}

	public void setNonInventoryWo(String nonInventoryWo) {
		this.nonInventoryWo = nonInventoryWo;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public BigDecimal getNotesForm1() {
		return this.notesForm1;
	}

	public void setNotesForm1(BigDecimal notesForm1) {
		this.notesForm1 = notesForm1;
	}

	public String getPn() {
		return this.pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getPnCondition() {
		return this.pnCondition;
	}

	public void setPnCondition(String pnCondition) {
		this.pnCondition = pnCondition;
	}

	public String getPnDescription() {
		return this.pnDescription;
	}

	public void setPnDescription(String pnDescription) {
		this.pnDescription = pnDescription;
	}

	public String getPnModifiedTo() {
		return this.pnModifiedTo;
	}

	public void setPnModifiedTo(String pnModifiedTo) {
		this.pnModifiedTo = pnModifiedTo;
	}

	public BigDecimal getPnQty() {
		return this.pnQty;
	}

	public void setPnQty(BigDecimal pnQty) {
		this.pnQty = pnQty;
	}

	public String getPnSn() {
		return this.pnSn;
	}

	public void setPnSn(String pnSn) {
		this.pnSn = pnSn;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getReceivedDate() {
		return this.receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getScheduleCompletionDate() {
		return this.scheduleCompletionDate;
	}

	public void setScheduleCompletionDate(Date scheduleCompletionDate) {
		this.scheduleCompletionDate = scheduleCompletionDate;
	}

	public BigDecimal getScheduleCompletionHour() {
		return this.scheduleCompletionHour;
	}

	public void setScheduleCompletionHour(BigDecimal scheduleCompletionHour) {
		this.scheduleCompletionHour = scheduleCompletionHour;
	}

	public BigDecimal getScheduleCompletionMinute() {
		return this.scheduleCompletionMinute;
	}

	public void setScheduleCompletionMinute(BigDecimal scheduleCompletionMinute) {
		this.scheduleCompletionMinute = scheduleCompletionMinute;
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

	public Date getShelfLifeExpiration() {
		return this.shelfLifeExpiration;
	}

	public void setShelfLifeExpiration(Date shelfLifeExpiration) {
		this.shelfLifeExpiration = shelfLifeExpiration;
	}

	public byte[] getSignedPdf() {
		return this.signedPdf;
	}

	public void setSignedPdf(byte[] signedPdf) {
		this.signedPdf = signedPdf;
	}

	public String getSnModifiedTo() {
		return this.snModifiedTo;
	}

	public void setSnModifiedTo(String snModifiedTo) {
		this.snModifiedTo = snModifiedTo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToolCalibrationNo() {
		return this.toolCalibrationNo;
	}

	public void setToolCalibrationNo(String toolCalibrationNo) {
		this.toolCalibrationNo = toolCalibrationNo;
	}

	public String getWarrantyIndicator() {
		return this.warrantyIndicator;
	}

	public void setWarrantyIndicator(String warrantyIndicator) {
		this.warrantyIndicator = warrantyIndicator;
	}

	public String getWoDescription() {
		return this.woDescription;
	}

	public void setWoDescription(String woDescription) {
		this.woDescription = woDescription;
	}

	public Wo getWoBean() {
		return this.woBean;
	}

	public void setWoBean(Wo woBean) {
		this.woBean = woBean;
	}

}