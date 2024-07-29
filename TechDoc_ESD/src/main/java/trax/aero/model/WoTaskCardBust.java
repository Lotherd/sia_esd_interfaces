package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WO_TASK_CARD_BUST database table.
 * 
 */
@Entity
@Table(name="WO_TASK_CARD_BUST")
@NamedQuery(name="WoTaskCardBust.findAll", query="SELECT w FROM WoTaskCardBust w")
public class WoTaskCardBust implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoTaskCardBustPK id;

	@Column(name="\"AUTHORIZATION\"")
	private String authorization;

	@Column(name="AUTHORIZATION_BY")
	private String authorizationBy;

	@Column(name="AUTHORIZATION_DATE")
	private Date authorizationDate;

	@Column(name="BUST_CATEGORY")
	private String bustCategory;

	@Column(name="BUST_DESCRIPTION")
	private String bustDescription;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	private String reason;

	private String status;

	@Column(name="SUB_CATEGORY")
	private String subCategory;

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

	public WoTaskCardBust() {
	}

	public WoTaskCardBustPK getId() {
		return this.id;
	}

	public void setId(WoTaskCardBustPK id) {
		this.id = id;
	}

	public String getAuthorization() {
		return this.authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getAuthorizationBy() {
		return this.authorizationBy;
	}

	public void setAuthorizationBy(String authorizationBy) {
		this.authorizationBy = authorizationBy;
	}

	public Date getAuthorizationDate() {
		return this.authorizationDate;
	}

	public void setAuthorizationDate(Date authorizationDate) {
		this.authorizationDate = authorizationDate;
	}

	public String getBustCategory() {
		return this.bustCategory;
	}

	public void setBustCategory(String bustCategory) {
		this.bustCategory = bustCategory;
	}

	public String getBustDescription() {
		return this.bustDescription;
	}

	public void setBustDescription(String bustDescription) {
		this.bustDescription = bustDescription;
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

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubCategory() {
		return this.subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public WoTaskCard getWoTaskCard() {
		return this.woTaskCard;
	}

	public void setWoTaskCard(WoTaskCard woTaskCard) {
		this.woTaskCard = woTaskCard;
	}

}