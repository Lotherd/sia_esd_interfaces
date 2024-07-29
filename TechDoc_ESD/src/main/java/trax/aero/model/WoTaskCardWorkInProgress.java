package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WO_TASK_CARD_WORK_IN_PROGRESS database table.
 * 
 */
@Entity
@Table(name="WO_TASK_CARD_WORK_IN_PROGRESS")
@NamedQuery(name="WoTaskCardWorkInProgress.findAll", query="SELECT w FROM WoTaskCardWorkInProgress w")
public class WoTaskCardWorkInProgress implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoTaskCardWorkInProgressPK id;

	private String category;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	private String password;

	@Column(name="PASSWORD_CLOSED")
	private String passwordClosed;

	@Column(name="\"SELECT\"")
	private String select;

	@Column(name="SIGN_IN_GROUP")
	private BigDecimal signInGroup;

	@Column(name="SIGN_IN_TYPE")
	private String signInType;

	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="START_TIME")
	private Date startTime;

	private String status;

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

	public WoTaskCardWorkInProgress() {
	}

	public WoTaskCardWorkInProgressPK getId() {
		return this.id;
	}

	public void setId(WoTaskCardWorkInProgressPK id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordClosed() {
		return this.passwordClosed;
	}

	public void setPasswordClosed(String passwordClosed) {
		this.passwordClosed = passwordClosed;
	}

	public String getSelect() {
		return this.select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public BigDecimal getSignInGroup() {
		return this.signInGroup;
	}

	public void setSignInGroup(BigDecimal signInGroup) {
		this.signInGroup = signInGroup;
	}

	public String getSignInType() {
		return this.signInType;
	}

	public void setSignInType(String signInType) {
		this.signInType = signInType;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public WoTaskCard getWoTaskCard() {
		return this.woTaskCard;
	}

	public void setWoTaskCard(WoTaskCard woTaskCard) {
		this.woTaskCard = woTaskCard;
	}

}