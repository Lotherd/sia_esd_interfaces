package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PICKLIST_DISTRIBUTION_REC database table.
 * 
 */
@Entity
@Table(name="PICKLIST_DISTRIBUTION_REC")
@NamedQuery(name="PicklistDistributionRec.findAll", query="SELECT p FROM PicklistDistributionRec p")
public class PicklistDistributionRec implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PicklistDistributionRecPK id;

	@Column(name="ASSIGN_TEAM")
	private String assignTeam;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CUST_RES")
	private BigDecimal custRes;

	@Column(name="CUST_RES_ITEM")
	private BigDecimal custResItem;

	@Column(name="CUST_TO_QTY")
	private BigDecimal custToQty;

	@Column(name="EMOBILITY_PICKED_BY")
	private String emobilityPickedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="EMOBILITY_PICKED_DATE")
	private Date emobilityPickedDate;

	@Column(name="EMOBILITY_PICKED_FLAG")
	private String emobilityPickedFlag;

	@Column(name="LEGACY_BATCH")
	private String legacyBatch;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="PN_RECEIVED")
	private String pnReceived;

	private String progress;

	@Column(name="QTY_ISSUED")
	private BigDecimal qtyIssued;

	@Column(name="QTY_RECEIVED")
	private BigDecimal qtyReceived;

	@Column(name="QTY_USED")
	private BigDecimal qtyUsed;

	@Column(name="REC_COMMENT")
	private String recComment;

	@Temporal(TemporalType.DATE)
	@Column(name="RECEIVED_DATE")
	private Date receivedDate;

	@Column(name="SN_RECEIVED")
	private String snReceived;

	@Column(name="TRANSFER_TO_BIN")
	private String transferToBin;
	
	private BigDecimal picklist_batch;

	//bi-directional many-to-one association to PicklistDistribution
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="DISTRIBUTION_LINE", referencedColumnName="DISTRIBUTION_LINE" , insertable=false, updatable=false),
		@JoinColumn(name="PICKLIST", referencedColumnName="PICKLIST", insertable=false, updatable=false),
		@JoinColumn(name="PICKLIST_LINE", referencedColumnName="PICKLIST_LINE" ,insertable=false, updatable=false),
		@JoinColumn(name="\"TRANSACTION\"", referencedColumnName="\"TRANSACTION\"" ,insertable=false, updatable=false)
		})
	private PicklistDistribution picklistDistribution;

	public PicklistDistributionRec() {
	}

	public PicklistDistributionRecPK getId() {
		return this.id;
	}

	public void setId(PicklistDistributionRecPK id) {
		this.id = id;
	}

	public String getAssignTeam() {
		return this.assignTeam;
	}

	public void setAssignTeam(String assignTeam) {
		this.assignTeam = assignTeam;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
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

	public BigDecimal getCustRes() {
		return this.custRes;
	}

	public void setCustRes(BigDecimal custRes) {
		this.custRes = custRes;
	}

	public BigDecimal getCustResItem() {
		return this.custResItem;
	}

	public void setCustResItem(BigDecimal custResItem) {
		this.custResItem = custResItem;
	}

	public BigDecimal getCustToQty() {
		return this.custToQty;
	}

	public void setCustToQty(BigDecimal custToQty) {
		this.custToQty = custToQty;
	}

	public String getEmobilityPickedBy() {
		return this.emobilityPickedBy;
	}

	public void setEmobilityPickedBy(String emobilityPickedBy) {
		this.emobilityPickedBy = emobilityPickedBy;
	}

	public Date getEmobilityPickedDate() {
		return this.emobilityPickedDate;
	}

	public void setEmobilityPickedDate(Date emobilityPickedDate) {
		this.emobilityPickedDate = emobilityPickedDate;
	}

	public String getEmobilityPickedFlag() {
		return this.emobilityPickedFlag;
	}

	public void setEmobilityPickedFlag(String emobilityPickedFlag) {
		this.emobilityPickedFlag = emobilityPickedFlag;
	}

	public String getLegacyBatch() {
		return this.legacyBatch;
	}

	public void setLegacyBatch(String legacyBatch) {
		this.legacyBatch = legacyBatch;
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

	public String getPnReceived() {
		return this.pnReceived;
	}

	public void setPnReceived(String pnReceived) {
		this.pnReceived = pnReceived;
	}

	public String getProgress() {
		return this.progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public BigDecimal getQtyIssued() {
		return this.qtyIssued;
	}

	public void setQtyIssued(BigDecimal qtyIssued) {
		this.qtyIssued = qtyIssued;
	}

	public BigDecimal getQtyReceived() {
		return this.qtyReceived;
	}

	public void setQtyReceived(BigDecimal qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	public BigDecimal getQtyUsed() {
		return this.qtyUsed;
	}

	public void setQtyUsed(BigDecimal qtyUsed) {
		this.qtyUsed = qtyUsed;
	}

	public String getRecComment() {
		return this.recComment;
	}

	public void setRecComment(String recComment) {
		this.recComment = recComment;
	}

	public Date getReceivedDate() {
		return this.receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getSnReceived() {
		return this.snReceived;
	}

	public void setSnReceived(String snReceived) {
		this.snReceived = snReceived;
	}

	public String getTransferToBin() {
		return this.transferToBin;
	}

	public void setTransferToBin(String transferToBin) {
		this.transferToBin = transferToBin;
	}


	public BigDecimal getPicklist_batch() {
		return picklist_batch;
	}

	public void setPicklist_batch(BigDecimal picklist_batch) {
		this.picklist_batch = picklist_batch;
	}

	public PicklistDistribution getPicklistDistribution() {
		return this.picklistDistribution;
	}

	public void setPicklistDistribution(PicklistDistribution picklistDistribution) {
		this.picklistDistribution = picklistDistribution;
	}

}