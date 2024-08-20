package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the INTERFACE_AUDIT database table.
 * 
 */
@Entity
@Table(name="INTERFACE_AUDIT")
@NamedQuery(name="InterfaceAudit.findAll", query="SELECT i FROM InterfaceAudit i")
public class InterfaceAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"TRANSACTION\"")
	private long transaction;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	private String defect;

	@Column(name="DEFECT_ITEM")
	private BigDecimal defectItem;

	@Column(name="DEFECT_TYPE")
	private String defectType;

	private String eo;

	@Column(name="EXCEPTION_BY_TRAX")
	private String exceptionByTrax;

	@Column(name="EXCEPTION_CLASS")
	private String exceptionClass;

	@Column(name="EXCEPTION_CLASS_TRAX")
	private String exceptionClassTrax;

	@Column(name="EXCEPTION_DETAIL")
	private String exceptionDetail;

	@Column(name="EXCEPTION_ID")
	private BigDecimal exceptionId;

	@Column(name="EXCEPTION_NEEDS_TO_B_PROCESSED")
	private String exceptionNeedsToBProcessed;

	@Column(name="EXCEPTION_STACK_TRACE")
	private String exceptionStackTrace;

	@Column(name="EXCEPTION_WAS_PROCESSED")
	private String exceptionWasProcessed;

	@Column(name="MESSAGE_DESCRIPTION")
	private String messageDescription;

	@Column(name="MESSAGE_EXCEPTION_DETAIL")
	private String messageExceptionDetail;

	@Column(name="MESSAGE_EXCEPTION_ID")
	private BigDecimal messageExceptionId;

	@Column(name="MESSAGE_ID")
	private BigDecimal messageId;

	@Column(name="MESSAGE_NEEDS_TO_BE_SENT")
	private String messageNeedsToBeSent;

	@Column(name="MESSAGE_PROCESSED_BY")
	private String messageProcessedBy;

	@Column(name="MESSAGE_PROCESSED_DATE")
	private Date messageProcessedDate;

	@Column(name="MESSAGE_WAS_SENT")
	private String messageWasSent;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="ORDER_LINE")
	private BigDecimal orderLine;

	@Column(name="ORDER_NUMBER")
	private BigDecimal orderNumber;

	@Column(name="ORDER_NUMBER_REFERENCE")
	private String orderNumberReference;

	@Column(name="ORDER_TYPE")
	private String orderType;

	@Column(name="TRANSACTION_DATE")
	private Date transactionDate;

	@Column(name="TRANSACTION_IN_OUT")
	private String transactionInOut;

	@Column(name="TRANSACTION_METHOD")
	private String transactionMethod;

	@Column(name="TRANSACTION_OBJECT")
	private String transactionObject;

	@Column(name="TRANSACTION_QUEUE")
	private String transactionQueue;

	@Column(name="TRANSACTION_TYPE")
	private String transactionType;

	@Column(name="TRANSACTION_USER")
	private String transactionUser;

	public InterfaceAudit() {
	}

	public long getTransaction() {
		return this.transaction;
	}

	public void setTransaction(long transaction) {
		this.transaction = transaction;
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

	public String getEo() {
		return this.eo;
	}

	public void setEo(String eo) {
		this.eo = eo;
	}

	public String getExceptionByTrax() {
		return this.exceptionByTrax;
	}

	public void setExceptionByTrax(String exceptionByTrax) {
		this.exceptionByTrax = exceptionByTrax;
	}

	public String getExceptionClass() {
		return this.exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public String getExceptionClassTrax() {
		return this.exceptionClassTrax;
	}

	public void setExceptionClassTrax(String exceptionClassTrax) {
		this.exceptionClassTrax = exceptionClassTrax;
	}

	public String getExceptionDetail() {
		return this.exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}

	public BigDecimal getExceptionId() {
		return this.exceptionId;
	}

	public void setExceptionId(BigDecimal exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getExceptionNeedsToBProcessed() {
		return this.exceptionNeedsToBProcessed;
	}

	public void setExceptionNeedsToBProcessed(String exceptionNeedsToBProcessed) {
		this.exceptionNeedsToBProcessed = exceptionNeedsToBProcessed;
	}

	public String getExceptionStackTrace() {
		return this.exceptionStackTrace;
	}

	public void setExceptionStackTrace(String exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}

	public String getExceptionWasProcessed() {
		return this.exceptionWasProcessed;
	}

	public void setExceptionWasProcessed(String exceptionWasProcessed) {
		this.exceptionWasProcessed = exceptionWasProcessed;
	}

	public String getMessageDescription() {
		return this.messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

	public String getMessageExceptionDetail() {
		return this.messageExceptionDetail;
	}

	public void setMessageExceptionDetail(String messageExceptionDetail) {
		this.messageExceptionDetail = messageExceptionDetail;
	}

	public BigDecimal getMessageExceptionId() {
		return this.messageExceptionId;
	}

	public void setMessageExceptionId(BigDecimal messageExceptionId) {
		this.messageExceptionId = messageExceptionId;
	}

	public BigDecimal getMessageId() {
		return this.messageId;
	}

	public void setMessageId(BigDecimal messageId) {
		this.messageId = messageId;
	}

	public String getMessageNeedsToBeSent() {
		return this.messageNeedsToBeSent;
	}

	public void setMessageNeedsToBeSent(String messageNeedsToBeSent) {
		this.messageNeedsToBeSent = messageNeedsToBeSent;
	}

	public String getMessageProcessedBy() {
		return this.messageProcessedBy;
	}

	public void setMessageProcessedBy(String messageProcessedBy) {
		this.messageProcessedBy = messageProcessedBy;
	}

	public Date getMessageProcessedDate() {
		return this.messageProcessedDate;
	}

	public void setMessageProcessedDate(Date messageProcessedDate) {
		this.messageProcessedDate = messageProcessedDate;
	}

	public String getMessageWasSent() {
		return this.messageWasSent;
	}

	public void setMessageWasSent(String messageWasSent) {
		this.messageWasSent = messageWasSent;
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

	public BigDecimal getOrderLine() {
		return this.orderLine;
	}

	public void setOrderLine(BigDecimal orderLine) {
		this.orderLine = orderLine;
	}

	public BigDecimal getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderNumberReference() {
		return this.orderNumberReference;
	}

	public void setOrderNumberReference(String orderNumberReference) {
		this.orderNumberReference = orderNumberReference;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionInOut() {
		return this.transactionInOut;
	}

	public void setTransactionInOut(String transactionInOut) {
		this.transactionInOut = transactionInOut;
	}

	public String getTransactionMethod() {
		return this.transactionMethod;
	}

	public void setTransactionMethod(String transactionMethod) {
		this.transactionMethod = transactionMethod;
	}

	public String getTransactionObject() {
		return this.transactionObject;
	}

	public void setTransactionObject(String transactionObject) {
		this.transactionObject = transactionObject;
	}

	public String getTransactionQueue() {
		return this.transactionQueue;
	}

	public void setTransactionQueue(String transactionQueue) {
		this.transactionQueue = transactionQueue;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionUser() {
		return this.transactionUser;
	}

	public void setTransactionUser(String transactionUser) {
		this.transactionUser = transactionUser;
	}

}