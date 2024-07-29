package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WO_TASK_CARD_CONTROL database table.
 * 
 */
@Entity
@Table(name="WO_TASK_CARD_CONTROL")
@NamedQuery(name="WoTaskCardControl.findAll", query="SELECT w FROM WoTaskCardControl w")
public class WoTaskCardControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoTaskCardControlPK id;

	@Column(name="CODE_DESCRIPTION")
	private String codeDescription;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="SORT_ITEM")
	private BigDecimal sortItem;

	@Column(name="TRAXDOC_NO")
	private BigDecimal traxdocNo;

	@Column(name="TRAXDOC_REVISION")
	private String traxdocRevision;

	public WoTaskCardControl() {
	}

	public WoTaskCardControlPK getId() {
		return this.id;
	}

	public void setId(WoTaskCardControlPK id) {
		this.id = id;
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

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public BigDecimal getSortItem() {
		return this.sortItem;
	}

	public void setSortItem(BigDecimal sortItem) {
		this.sortItem = sortItem;
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

}