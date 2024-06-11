package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the AC_PN_TRANSACTION_HISTORY database table.
 * 
 */
@Entity
@Table(name="AC_PN_TRANSACTION_HISTORY")
@NamedQuery(name="AcPnTransactionHistory.findAll", query="SELECT a FROM AcPnTransactionHistory a")
public class AcPnTransactionHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AcPnTransactionHistoryPK id;

	private String ac;

	private BigDecimal batch;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CCI_NUMBER")
	private BigDecimal cciNumber;

	private BigDecimal chapter;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CUST_REQ_ACT_DATE")
	private Date custReqActDate;

	@Column(name="CUST_REQ_DATE")
	private Date custReqDate;

	@Column(name="CYCLES_INSTALLED")
	private BigDecimal cyclesInstalled;

	@Column(name="DAYS_INSTALLED")
	private BigDecimal daysInstalled;

	private String defect;

	@Column(name="DEFECT_ITEM")
	private BigDecimal defectItem;

	@Column(name="DEFECT_TYPE")
	private String defectType;

	@Column(name="DELIVERY_DATE")
	private Date deliveryDate;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	@Column(name="FOLLOWUP_REMARKS")
	private String followupRemarks;

	@Column(name="FORM_ONE")
	private String formOne;

	@Column(name="GOODS_RCVD_BATCH")
	private BigDecimal goodsRcvdBatch;

	@Column(name="HOURS_INSTALLED")
	private BigDecimal hoursInstalled;

	@Column(name="INTERFACE_ECTM_TRANSFER_BY")
	private String interfaceEctmTransferBy;

	@Column(name="INTERFACE_ECTM_TRANSFER_DATE")
	private Date interfaceEctmTransferDate;

	@Column(name="LEAD_TIME")
	private BigDecimal leadTime;

	private String locked;

	@Column(name="MINUTES_INSTALLED")
	private BigDecimal minutesInstalled;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="NHA_PN")
	private String nhaPn;

	@Column(name="NHA_PN_PRORATED")
	private String nhaPnProrated;

	@Column(name="NHA_SN")
	private String nhaSn;

	private String nla;

	@Column(name="NLA_POSITION")
	private String nlaPosition;

	@Column(name="NON_SN")
	private String nonSn;

	private BigDecimal notes;

	@Column(name="ORDER_NO")
	private BigDecimal orderNo;

	@Column(name="ORDER_TYPE")
	private String orderType;

	private BigDecimal paragraph;

	private String pn;

	@Column(name="\"POSITION\"")
	private String position;

	private BigDecimal qty;

	@Column(name="REASON_CATEGORY")
	private String reasonCategory;

	private BigDecimal recurrent;

	@Column(name="REMOVAL_REASON")
	private String removalReason;

	@Column(name="REMOVE_AS_SERVICEABLE")
	private String removeAsServiceable;

	@Column(name="ROB_FROM_AC")
	private String robFromAc;

	@Column(name="SCHEDULE_CATEGORY")
	private String scheduleCategory;

	@Column(name="\"SECTION\"")
	private BigDecimal section;

	@Column(name="SHIPPING_DATE")
	private Date shippingDate;

	@Column(name="SHIPPING_REMARKS")
	private String shippingRemarks;

	private String sn;

	private String station;

	private String status;

	@Column(name="TAG_NO")
	private String tagNo;

	@Column(name="TASK_CARD")
	private String taskCard;

	@Column(name="TASK_CARD_PN")
	private String taskCardPn;

	@Column(name="TASK_CARD_SN")
	private String taskCardSn;

	@Column(name="TRANSACTION_DATE")
	private Date transactionDate;

	@Column(name="TRANSACTION_HOUR")
	private BigDecimal transactionHour;

	@Column(name="TRANSACTION_MINUTE")
	private BigDecimal transactionMinute;

	@Column(name="TRANSACTION_TYPE")
	private String transactionType;

	@Column(name="TRANSACTION_TYPE_CONTROL")
	private String transactionTypeControl;

	private BigDecimal wo;

	public AcPnTransactionHistory() {
	}

	public AcPnTransactionHistoryPK getId() {
		return this.id;
	}

	public void setId(AcPnTransactionHistoryPK id) {
		this.id = id;
	}

	public String getAc() {
		return this.ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
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

	public BigDecimal getCciNumber() {
		return this.cciNumber;
	}

	public void setCciNumber(BigDecimal cciNumber) {
		this.cciNumber = cciNumber;
	}

	public BigDecimal getChapter() {
		return this.chapter;
	}

	public void setChapter(BigDecimal chapter) {
		this.chapter = chapter;
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

	public Date getCustReqActDate() {
		return this.custReqActDate;
	}

	public void setCustReqActDate(Date custReqActDate) {
		this.custReqActDate = custReqActDate;
	}

	public Date getCustReqDate() {
		return this.custReqDate;
	}

	public void setCustReqDate(Date custReqDate) {
		this.custReqDate = custReqDate;
	}

	public BigDecimal getCyclesInstalled() {
		return this.cyclesInstalled;
	}

	public void setCyclesInstalled(BigDecimal cyclesInstalled) {
		this.cyclesInstalled = cyclesInstalled;
	}

	public BigDecimal getDaysInstalled() {
		return this.daysInstalled;
	}

	public void setDaysInstalled(BigDecimal daysInstalled) {
		this.daysInstalled = daysInstalled;
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

	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getFollowupRemarks() {
		return this.followupRemarks;
	}

	public void setFollowupRemarks(String followupRemarks) {
		this.followupRemarks = followupRemarks;
	}

	public String getFormOne() {
		return this.formOne;
	}

	public void setFormOne(String formOne) {
		this.formOne = formOne;
	}

	public BigDecimal getGoodsRcvdBatch() {
		return this.goodsRcvdBatch;
	}

	public void setGoodsRcvdBatch(BigDecimal goodsRcvdBatch) {
		this.goodsRcvdBatch = goodsRcvdBatch;
	}

	public BigDecimal getHoursInstalled() {
		return this.hoursInstalled;
	}

	public void setHoursInstalled(BigDecimal hoursInstalled) {
		this.hoursInstalled = hoursInstalled;
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

	public BigDecimal getLeadTime() {
		return this.leadTime;
	}

	public void setLeadTime(BigDecimal leadTime) {
		this.leadTime = leadTime;
	}

	public String getLocked() {
		return this.locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public BigDecimal getMinutesInstalled() {
		return this.minutesInstalled;
	}

	public void setMinutesInstalled(BigDecimal minutesInstalled) {
		this.minutesInstalled = minutesInstalled;
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

	public String getNhaPn() {
		return this.nhaPn;
	}

	public void setNhaPn(String nhaPn) {
		this.nhaPn = nhaPn;
	}

	public String getNhaPnProrated() {
		return this.nhaPnProrated;
	}

	public void setNhaPnProrated(String nhaPnProrated) {
		this.nhaPnProrated = nhaPnProrated;
	}

	public String getNhaSn() {
		return this.nhaSn;
	}

	public void setNhaSn(String nhaSn) {
		this.nhaSn = nhaSn;
	}

	public String getNla() {
		return this.nla;
	}

	public void setNla(String nla) {
		this.nla = nla;
	}

	public String getNlaPosition() {
		return this.nlaPosition;
	}

	public void setNlaPosition(String nlaPosition) {
		this.nlaPosition = nlaPosition;
	}

	public String getNonSn() {
		return this.nonSn;
	}

	public void setNonSn(String nonSn) {
		this.nonSn = nonSn;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public BigDecimal getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getParagraph() {
		return this.paragraph;
	}

	public void setParagraph(BigDecimal paragraph) {
		this.paragraph = paragraph;
	}

	public String getPn() {
		return this.pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getReasonCategory() {
		return this.reasonCategory;
	}

	public void setReasonCategory(String reasonCategory) {
		this.reasonCategory = reasonCategory;
	}

	public BigDecimal getRecurrent() {
		return this.recurrent;
	}

	public void setRecurrent(BigDecimal recurrent) {
		this.recurrent = recurrent;
	}

	public String getRemovalReason() {
		return this.removalReason;
	}

	public void setRemovalReason(String removalReason) {
		this.removalReason = removalReason;
	}

	public String getRemoveAsServiceable() {
		return this.removeAsServiceable;
	}

	public void setRemoveAsServiceable(String removeAsServiceable) {
		this.removeAsServiceable = removeAsServiceable;
	}

	public String getRobFromAc() {
		return this.robFromAc;
	}

	public void setRobFromAc(String robFromAc) {
		this.robFromAc = robFromAc;
	}

	public String getScheduleCategory() {
		return this.scheduleCategory;
	}

	public void setScheduleCategory(String scheduleCategory) {
		this.scheduleCategory = scheduleCategory;
	}

	public BigDecimal getSection() {
		return this.section;
	}

	public void setSection(BigDecimal section) {
		this.section = section;
	}

	public Date getShippingDate() {
		return this.shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getShippingRemarks() {
		return this.shippingRemarks;
	}

	public void setShippingRemarks(String shippingRemarks) {
		this.shippingRemarks = shippingRemarks;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTagNo() {
		return this.tagNo;
	}

	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
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

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getTransactionHour() {
		return this.transactionHour;
	}

	public void setTransactionHour(BigDecimal transactionHour) {
		this.transactionHour = transactionHour;
	}

	public BigDecimal getTransactionMinute() {
		return this.transactionMinute;
	}

	public void setTransactionMinute(BigDecimal transactionMinute) {
		this.transactionMinute = transactionMinute;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionTypeControl() {
		return this.transactionTypeControl;
	}

	public void setTransactionTypeControl(String transactionTypeControl) {
		this.transactionTypeControl = transactionTypeControl;
	}

	public BigDecimal getWo() {
		return this.wo;
	}

	public void setWo(BigDecimal wo) {
		this.wo = wo;
	}

}