package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WO_TASK_CARD_SURVEY database table.
 * 
 */
@Entity
@Table(name="WO_TASK_CARD_SURVEY")
@NamedQuery(name="WoTaskCardSurvey.findAll", query="SELECT w FROM WoTaskCardSurvey w")
public class WoTaskCardSurvey implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoTaskCardSurveyPK id;

	@Column(name="ANSWER_CODE")
	private String answerCode;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="BLOB_NO_FINDINGS")
	private BigDecimal blobNoFindings;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	@Column(name="DOCUMENT_NO_FINDINGS")
	private BigDecimal documentNoFindings;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="\"PRECISION\"")
	private BigDecimal precision;

	private String question;

	@Column(name="RESPONSE_MANDATORY")
	private String responseMandatory;

	@Column(name="RESPONSE_NUMERIC")
	private BigDecimal responseNumeric;

	@Column(name="RESPONSE_NUMERIC_FROM")
	private BigDecimal responseNumericFrom;

	@Column(name="RESPONSE_NUMERIC_TO")
	private BigDecimal responseNumericTo;

	@Column(name="RESPONSE_STRING")
	private String responseString;

	@Column(name="RESPONSE_STRING_FROM")
	private String responseStringFrom;

	@Column(name="RESPONSE_STRING_TO")
	private String responseStringTo;

	@Column(name="RESPONSE_TYPE")
	private String responseType;

	private BigDecimal scale;

	@Column(name="TASK_CARD_ITEM")
	private BigDecimal taskCardItem;

	//bi-directional many-to-one association to WoTaskCard
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="AC", referencedColumnName="AC", insertable=false, updatable=false),
		@JoinColumn(name="PN", referencedColumnName="PN", insertable=false, updatable=false),
		@JoinColumn(name="PN_SN", referencedColumnName="PN_SN", insertable=false, updatable=false),
		@JoinColumn(name="TASK_CARD", referencedColumnName="TASK_CARD", insertable=false, updatable=false),
		@JoinColumn(name="WO", referencedColumnName="WO", insertable=false, updatable=false)
		})
	private WoTaskCard woTaskCard;

	public WoTaskCardSurvey() {
	}

	public WoTaskCardSurveyPK getId() {
		return this.id;
	}

	public void setId(WoTaskCardSurveyPK id) {
		this.id = id;
	}

	public String getAnswerCode() {
		return this.answerCode;
	}

	public void setAnswerCode(String answerCode) {
		this.answerCode = answerCode;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public BigDecimal getBlobNoFindings() {
		return this.blobNoFindings;
	}

	public void setBlobNoFindings(BigDecimal blobNoFindings) {
		this.blobNoFindings = blobNoFindings;
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

	public BigDecimal getDocumentNoFindings() {
		return this.documentNoFindings;
	}

	public void setDocumentNoFindings(BigDecimal documentNoFindings) {
		this.documentNoFindings = documentNoFindings;
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

	public BigDecimal getPrecision() {
		return this.precision;
	}

	public void setPrecision(BigDecimal precision) {
		this.precision = precision;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getResponseMandatory() {
		return this.responseMandatory;
	}

	public void setResponseMandatory(String responseMandatory) {
		this.responseMandatory = responseMandatory;
	}

	public BigDecimal getResponseNumeric() {
		return this.responseNumeric;
	}

	public void setResponseNumeric(BigDecimal responseNumeric) {
		this.responseNumeric = responseNumeric;
	}

	public BigDecimal getResponseNumericFrom() {
		return this.responseNumericFrom;
	}

	public void setResponseNumericFrom(BigDecimal responseNumericFrom) {
		this.responseNumericFrom = responseNumericFrom;
	}

	public BigDecimal getResponseNumericTo() {
		return this.responseNumericTo;
	}

	public void setResponseNumericTo(BigDecimal responseNumericTo) {
		this.responseNumericTo = responseNumericTo;
	}

	public String getResponseString() {
		return this.responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public String getResponseStringFrom() {
		return this.responseStringFrom;
	}

	public void setResponseStringFrom(String responseStringFrom) {
		this.responseStringFrom = responseStringFrom;
	}

	public String getResponseStringTo() {
		return this.responseStringTo;
	}

	public void setResponseStringTo(String responseStringTo) {
		this.responseStringTo = responseStringTo;
	}

	public String getResponseType() {
		return this.responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public BigDecimal getScale() {
		return this.scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}

	public BigDecimal getTaskCardItem() {
		return this.taskCardItem;
	}

	public void setTaskCardItem(BigDecimal taskCardItem) {
		this.taskCardItem = taskCardItem;
	}

	public WoTaskCard getWoTaskCard() {
		return this.woTaskCard;
	}

	public void setWoTaskCard(WoTaskCard woTaskCard) {
		this.woTaskCard = woTaskCard;
	}

}