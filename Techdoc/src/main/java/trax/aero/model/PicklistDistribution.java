package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PICKLIST_DISTRIBUTION database table.
 * 
 */
@Entity
@Table(name="PICKLIST_DISTRIBUTION")
@NamedQuery(name="PicklistDistribution.findAll", query="SELECT p FROM PicklistDistribution p")
public class PicklistDistribution implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PicklistDistributionPK id;

	@Column(name="AUTOMATED_WAREHOUSE_ISSUED")
	private String automatedWarehouseIssued;

	private BigDecimal batch;

	private String condition;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DATE_PICKED")
	private Date datePicked;

	@Column(name="EMOBILITY_PICKED_BY")
	private String emobilityPickedBy;

	@Column(name="EMOBILITY_PICKED_DATE")
	private Date emobilityPickedDate;

	@Column(name="EMOBILITY_PICKED_FLAG")
	private String emobilityPickedFlag;

	@Column(name="INTERFACE_MODIFIED_DATE")
	private Date interfaceModifiedDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="ORDER_LINE")
	private BigDecimal orderLine;

	@Column(name="ORIGINAL_PICKLIST")
	private BigDecimal originalPicklist;

	@Column(name="PICKED_BY")
	private String pickedBy;

	@Column(name="PICKED_DATE")
	private Date pickedDate;

	@Column(name="PICKED_FLAG")
	private String pickedFlag;

	private String pn;

	@Column(name="PRINT_TAG")
	private String printTag;

	private BigDecimal qty;

	@Column(name="QTY_PICKED")
	private BigDecimal qtyPicked;

	@Column(name="READY_FLAG")
	private String readyFlag;

	private BigDecimal requisition;

	@Column(name="REQUISITION_LINE")
	private BigDecimal requisitionLine;

	@Column(name="RO_FOLLOWING_ID")
	private BigDecimal roFollowingId;

	@Column(name="SCAN_BATCH")
	private BigDecimal scanBatch;
	
	@Column(name="EXTERNAL_CUST_RES")
	private String externalCustRes;
	
	@Column(name="EXTERNAL_CUST_RES_ITEM")
	private String externalCustResItem;

	private String sn;

	private String status;

	@Column(name="TASK_CARD")
	private String taskCard;

	//bi-directional many-to-one association to PicklistHeader
	@ManyToOne
	@JoinColumn(name="PICKLIST" , insertable=false, updatable=false)
	private PicklistHeader picklistHeader;

	public PicklistDistribution() {
	}

	public PicklistDistributionPK getId() {
		return this.id;
	}

	public void setId(PicklistDistributionPK id) {
		this.id = id;
	}

	public String getAutomatedWarehouseIssued() {
		return this.automatedWarehouseIssued;
	}

	public void setAutomatedWarehouseIssued(String automatedWarehouseIssued) {
		this.automatedWarehouseIssued = automatedWarehouseIssued;
	}

	public BigDecimal getBatch() {
		return this.batch;
	}

	public void setBatch(BigDecimal batch) {
		this.batch = batch;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
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

	public Date getDatePicked() {
		return this.datePicked;
	}

	public void setDatePicked(Date datePicked) {
		this.datePicked = datePicked;
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

	public Date getInterfaceModifiedDate() {
		return this.interfaceModifiedDate;
	}

	public void setInterfaceModifiedDate(Date interfaceModifiedDate) {
		this.interfaceModifiedDate = interfaceModifiedDate;
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

	public BigDecimal getOrderLine() {
		return this.orderLine;
	}

	public void setOrderLine(BigDecimal orderLine) {
		this.orderLine = orderLine;
	}

	public BigDecimal getOriginalPicklist() {
		return this.originalPicklist;
	}

	public void setOriginalPicklist(BigDecimal originalPicklist) {
		this.originalPicklist = originalPicklist;
	}

	public String getPickedBy() {
		return this.pickedBy;
	}

	public void setPickedBy(String pickedBy) {
		this.pickedBy = pickedBy;
	}

	public Date getPickedDate() {
		return this.pickedDate;
	}

	public void setPickedDate(Date pickedDate) {
		this.pickedDate = pickedDate;
	}

	public String getPickedFlag() {
		return this.pickedFlag;
	}

	public void setPickedFlag(String pickedFlag) {
		this.pickedFlag = pickedFlag;
	}

	public String getPn() {
		return this.pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getPrintTag() {
		return this.printTag;
	}

	public void setPrintTag(String printTag) {
		this.printTag = printTag;
	}

	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getQtyPicked() {
		return this.qtyPicked;
	}

	public void setQtyPicked(BigDecimal qtyPicked) {
		this.qtyPicked = qtyPicked;
	}

	public String getReadyFlag() {
		return this.readyFlag;
	}

	public void setReadyFlag(String readyFlag) {
		this.readyFlag = readyFlag;
	}

	public BigDecimal getRequisition() {
		return this.requisition;
	}

	public void setRequisition(BigDecimal requisition) {
		this.requisition = requisition;
	}

	public BigDecimal getRequisitionLine() {
		return this.requisitionLine;
	}

	public void setRequisitionLine(BigDecimal requisitionLine) {
		this.requisitionLine = requisitionLine;
	}

	public BigDecimal getRoFollowingId() {
		return this.roFollowingId;
	}

	public void setRoFollowingId(BigDecimal roFollowingId) {
		this.roFollowingId = roFollowingId;
	}

	public BigDecimal getScanBatch() {
		return this.scanBatch;
	}

	public void setScanBatch(BigDecimal scanBatch) {
		this.scanBatch = scanBatch;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskCard() {
		return this.taskCard;
	}

	public void setTaskCard(String taskCard) {
		this.taskCard = taskCard;
	}

	public PicklistHeader getPicklistHeader() {
		return this.picklistHeader;
	}

	public void setPicklistHeader(PicklistHeader picklistHeader) {
		this.picklistHeader = picklistHeader;
	}

	public String getExternalCustRes() {
		return externalCustRes;
	}

	public void setExternalCustRes(String externalCustRes) {
		this.externalCustRes = externalCustRes;
	}

	public String getExternalCustResItem() {
		return externalCustResItem;
	}

	public void setExternalCustResItem(String externalCustResItem) {
		this.externalCustResItem = externalCustResItem;
	}

}