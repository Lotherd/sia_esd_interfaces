package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BLOB_TABLE database table.
 * 
 */
@Entity
@Table(name="BLOB_TABLE")
@NamedQuery(name="BlobTable.findAll", query="SELECT b FROM BlobTable b")
public class BlobTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BlobTablePK id;

	@Column(name="BLOB_DESCRIPTION")
	private String blobDescription;

	@Lob
	@Column(name="BLOB_ITEM")
	private byte[] blobItem;

	@Column(name="BLOB_TYPE")
	private String blobType;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CUSTOM_DESCRIPTION")
	private String customDescription;

	@Column(name="DOC_TYPE")
	private String docType;

	@Column(name="EXPIRE_DATE")
	private Date expireDate;

	@Column(name="FROM_TABLE")
	private String fromTable;

	private BigDecimal height;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="PRINT_FLAG")
	private String printFlag;

	@Column(name="SIZE_REDUCED")
	private String sizeReduced;

	@Column(name="SIZE_REDUCTION_PROCESS_DATE")
	private Date sizeReductionProcessDate;

	@Column(name="STAMP_HEIGHT")
	private BigDecimal stampHeight;

	@Column(name="STAMP_WIDTH")
	private BigDecimal stampWidth;

	@Column(name="TRAXDOC_LINE")
	private BigDecimal traxdocLine;

	@Column(name="TRAXDOC_NO")
	private BigDecimal traxdocNo;

	@Column(name="TRAXDOC_ROW_ID")
	private BigDecimal traxdocRowId;

	@Column(name="WEB_LINK")
	private String webLink;

	private BigDecimal width;

	public BlobTable() {
	}

	public BlobTablePK getId() {
		return this.id;
	}

	public void setId(BlobTablePK id) {
		this.id = id;
	}

	public String getBlobDescription() {
		return this.blobDescription;
	}

	public void setBlobDescription(String blobDescription) {
		this.blobDescription = blobDescription;
	}

	public byte[] getBlobItem() {
		return this.blobItem;
	}

	public void setBlobItem(byte[] blobItem) {
		this.blobItem = blobItem;
	}

	public String getBlobType() {
		return this.blobType;
	}

	public void setBlobType(String blobType) {
		this.blobType = blobType;
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

	public String getCustomDescription() {
		return this.customDescription;
	}

	public void setCustomDescription(String customDescription) {
		this.customDescription = customDescription;
	}

	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getFromTable() {
		return this.fromTable;
	}

	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
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

	public String getPrintFlag() {
		return this.printFlag;
	}

	public void setPrintFlag(String printFlag) {
		this.printFlag = printFlag;
	}

	public String getSizeReduced() {
		return this.sizeReduced;
	}

	public void setSizeReduced(String sizeReduced) {
		this.sizeReduced = sizeReduced;
	}

	public Date getSizeReductionProcessDate() {
		return this.sizeReductionProcessDate;
	}

	public void setSizeReductionProcessDate(Date sizeReductionProcessDate) {
		this.sizeReductionProcessDate = sizeReductionProcessDate;
	}

	public BigDecimal getStampHeight() {
		return this.stampHeight;
	}

	public void setStampHeight(BigDecimal stampHeight) {
		this.stampHeight = stampHeight;
	}

	public BigDecimal getStampWidth() {
		return this.stampWidth;
	}

	public void setStampWidth(BigDecimal stampWidth) {
		this.stampWidth = stampWidth;
	}

	public BigDecimal getTraxdocLine() {
		return this.traxdocLine;
	}

	public void setTraxdocLine(BigDecimal traxdocLine) {
		this.traxdocLine = traxdocLine;
	}

	public BigDecimal getTraxdocNo() {
		return this.traxdocNo;
	}

	public void setTraxdocNo(BigDecimal traxdocNo) {
		this.traxdocNo = traxdocNo;
	}

	public BigDecimal getTraxdocRowId() {
		return this.traxdocRowId;
	}

	public void setTraxdocRowId(BigDecimal traxdocRowId) {
		this.traxdocRowId = traxdocRowId;
	}

	public String getWebLink() {
		return this.webLink;
	}

	public void setWebLink(String webLink) {
		this.webLink = webLink;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

}