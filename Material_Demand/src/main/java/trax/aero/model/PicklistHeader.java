package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PICKLIST_HEADER database table.
 * 
 */
@Entity
@Table(name="PICKLIST_HEADER")
@NamedQuery(name="PicklistHeader.findAll", query="SELECT p FROM PicklistHeader p")
public class PicklistHeader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long picklist;

	private String ac;

	@Column(name="\"ACTION\"")
	private String action;

	private String bin;

	@Column(name="BIN_TRANSFER")
	private String binTransfer;

	@Column(name="BUILD_KIT")
	private String buildKit;

	@Column(name="BYPASS_ROUTING")
	private String bypassRouting;

	@Column(name="CANCEL_REVIEW_BY")
	private String cancelReviewBy;

	@Column(name="CANCEL_REVIEW_DATE")
	private Date cancelReviewDate;

	@Column(name="CANCEL_REVIEW_FLAG")
	private String cancelReviewFlag;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_BY_PRINT")
	private String createdByPrint;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CREATED_DATE_PRINT")
	private Date createdDatePrint;

	private String defect;

	@Column(name="DEFECT_ITEM")
	private BigDecimal defectItem;

	@Column(name="DEFECT_TYPE")
	private String defectType;

	@Column(name="DELIVERY_LOCATION")
	private String deliveryLocation;

	@Column(name="IN_USE")
	private String inUse;

	@Column(name="IN_USED_BY")
	private String inUsedBy;

	@Column(name="INVENTORY_TYPE")
	private String inventoryType;

	@Column(name="ISSUE_TO")
	private String issueTo;

	@Column(name="ISSUE_TO_EMPLOYEE")
	private String issueToEmployee;

	private String location;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_BY_PRINT")
	private String modifiedByPrint;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="MODIFIED_DATE_PRINT")
	private Date modifiedDatePrint;

	@Column(name="NO_OF_PRINT")
	private BigDecimal noOfPrint;

	private BigDecimal notes;

	@Column(name="ORDER_NUMBER")
	private BigDecimal orderNumber;

	@Column(name="ORDER_TYPE")
	private String orderType;

	@Column(name="ORIGINAL_LOCATION")
	private String originalLocation;

	@Column(name="PICKED_BY")
	private String pickedBy;

	@Column(name="PICKED_DATE")
	private Date pickedDate;

	@Column(name="PICKED_FLAG")
	private String pickedFlag;

	private String priority;

	@Column(name="REQUIRE_HOUR")
	private BigDecimal requireHour;

	@Column(name="REQUIRE_MINUTE")
	private BigDecimal requireMinute;

	@Column(name="REQUIRE_ON")
	private Date requireOn;

	private String site;

	private BigDecimal so;

	private String status;

	@Column(name="TASK_CARD")
	private String taskCard;

	@Column(name="TASK_CARD_PN")
	private String taskCardPn;

	@Column(name="TASK_CARD_SN")
	private String taskCardSn;

	private BigDecimal wo;

	//bi-directional many-to-one association to PicklistDistribution
	@OneToMany(mappedBy="picklistHeader")
	private List<PicklistDistribution> picklistDistributions;

	public PicklistHeader() {
	}

	public long getPicklist() {
		return this.picklist;
	}

	public void setPicklist(long picklist) {
		this.picklist = picklist;
	}

	public String getAc() {
		return this.ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getBin() {
		return this.bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getBinTransfer() {
		return this.binTransfer;
	}

	public void setBinTransfer(String binTransfer) {
		this.binTransfer = binTransfer;
	}

	public String getBuildKit() {
		return this.buildKit;
	}

	public void setBuildKit(String buildKit) {
		this.buildKit = buildKit;
	}

	public String getBypassRouting() {
		return this.bypassRouting;
	}

	public void setBypassRouting(String bypassRouting) {
		this.bypassRouting = bypassRouting;
	}

	public String getCancelReviewBy() {
		return this.cancelReviewBy;
	}

	public void setCancelReviewBy(String cancelReviewBy) {
		this.cancelReviewBy = cancelReviewBy;
	}

	public Date getCancelReviewDate() {
		return this.cancelReviewDate;
	}

	public void setCancelReviewDate(Date cancelReviewDate) {
		this.cancelReviewDate = cancelReviewDate;
	}

	public String getCancelReviewFlag() {
		return this.cancelReviewFlag;
	}

	public void setCancelReviewFlag(String cancelReviewFlag) {
		this.cancelReviewFlag = cancelReviewFlag;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByPrint() {
		return this.createdByPrint;
	}

	public void setCreatedByPrint(String createdByPrint) {
		this.createdByPrint = createdByPrint;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedDatePrint() {
		return this.createdDatePrint;
	}

	public void setCreatedDatePrint(Date createdDatePrint) {
		this.createdDatePrint = createdDatePrint;
	}

	public String getDefect() {
		return this.defect;
	}

	public void setDefect(String defect) {
		this.defect = defect;
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

	public String getDeliveryLocation() {
		return this.deliveryLocation;
	}

	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}

	public String getInUse() {
		return this.inUse;
	}

	public void setInUse(String inUse) {
		this.inUse = inUse;
	}

	public String getInUsedBy() {
		return this.inUsedBy;
	}

	public void setInUsedBy(String inUsedBy) {
		this.inUsedBy = inUsedBy;
	}

	public String getInventoryType() {
		return this.inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getIssueTo() {
		return this.issueTo;
	}

	public void setIssueTo(String issueTo) {
		this.issueTo = issueTo;
	}

	public String getIssueToEmployee() {
		return this.issueToEmployee;
	}

	public void setIssueToEmployee(String issueToEmployee) {
		this.issueToEmployee = issueToEmployee;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedByPrint() {
		return this.modifiedByPrint;
	}

	public void setModifiedByPrint(String modifiedByPrint) {
		this.modifiedByPrint = modifiedByPrint;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getModifiedDatePrint() {
		return this.modifiedDatePrint;
	}

	public void setModifiedDatePrint(Date modifiedDatePrint) {
		this.modifiedDatePrint = modifiedDatePrint;
	}

	public BigDecimal getNoOfPrint() {
		return this.noOfPrint;
	}

	public void setNoOfPrint(BigDecimal noOfPrint) {
		this.noOfPrint = noOfPrint;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public BigDecimal getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOriginalLocation() {
		return this.originalLocation;
	}

	public void setOriginalLocation(String originalLocation) {
		this.originalLocation = originalLocation;
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

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public BigDecimal getRequireHour() {
		return this.requireHour;
	}

	public void setRequireHour(BigDecimal requireHour) {
		this.requireHour = requireHour;
	}

	public BigDecimal getRequireMinute() {
		return this.requireMinute;
	}

	public void setRequireMinute(BigDecimal requireMinute) {
		this.requireMinute = requireMinute;
	}

	public Date getRequireOn() {
		return this.requireOn;
	}

	public void setRequireOn(Date requireOn) {
		this.requireOn = requireOn;
	}

	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public BigDecimal getSo() {
		return this.so;
	}

	public void setSo(BigDecimal so) {
		this.so = so;
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

	public String getTaskCardPn() {
		return this.taskCardPn;
	}

	public void setTaskCardPn(String taskCardPn) {
		this.taskCardPn = taskCardPn;
	}

	public String getTaskCardSn() {
		return this.taskCardSn;
	}

	public void setTaskCardSn(String taskCardSn) {
		this.taskCardSn = taskCardSn;
	}

	public BigDecimal getWo() {
		return this.wo;
	}

	public void setWo(BigDecimal wo) {
		this.wo = wo;
	}

	public List<PicklistDistribution> getPicklistDistributions() {
		return this.picklistDistributions;
	}

	public void setPicklistDistributions(List<PicklistDistribution> picklistDistributions) {
		this.picklistDistributions = picklistDistributions;
	}

	public PicklistDistribution addPicklistDistribution(PicklistDistribution picklistDistribution) {
		getPicklistDistributions().add(picklistDistribution);
		picklistDistribution.setPicklistHeader(this);

		return picklistDistribution;
	}

	public PicklistDistribution removePicklistDistribution(PicklistDistribution picklistDistribution) {
		getPicklistDistributions().remove(picklistDistribution);
		picklistDistribution.setPicklistHeader(null);

		return picklistDistribution;
	}

}