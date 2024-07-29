package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WO_TASK_CARD_SIGNED_PDF database table.
 * 
 */
@Entity
@Table(name="WO_TASK_CARD_SIGNED_PDF")
@NamedQuery(name="WoTaskCardSignedPdf.findAll", query="SELECT w FROM WoTaskCardSignedPdf w")
public class WoTaskCardSignedPdf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoTaskCardSignedPdfPK id;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Lob
	private byte[] pdf;

	@Column(name="PRINT_COUNT")
	private BigDecimal printCount;

	//bi-directional many-to-one association to WoTaskCard
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="AC", referencedColumnName="AC", insertable=false, updatable=false),
		@JoinColumn(name="TASK_CARD", referencedColumnName="TASK_CARD", insertable=false, updatable=false),
		@JoinColumn(name="TASK_CARD_PN", referencedColumnName="PN", insertable=false, updatable=false),
		@JoinColumn(name="TASK_CARD_PN_SN", referencedColumnName="PN_SN", insertable=false, updatable=false),
		@JoinColumn(name="WO", referencedColumnName="WO", insertable=false, updatable=false)
		})
	private WoTaskCard woTaskCard;

	public WoTaskCardSignedPdf() {
	}

	public WoTaskCardSignedPdfPK getId() {
		return this.id;
	}

	public void setId(WoTaskCardSignedPdfPK id) {
		this.id = id;
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

	public byte[] getPdf() {
		return this.pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	public BigDecimal getPrintCount() {
		return this.printCount;
	}

	public void setPrintCount(BigDecimal printCount) {
		this.printCount = printCount;
	}

	public WoTaskCard getWoTaskCard() {
		return this.woTaskCard;
	}

	public void setWoTaskCard(WoTaskCard woTaskCard) {
		this.woTaskCard = woTaskCard;
	}

}