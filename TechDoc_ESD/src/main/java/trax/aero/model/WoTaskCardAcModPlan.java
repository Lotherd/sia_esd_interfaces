package trax.aero.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the WO_TASK_CARD_AC_MOD_PLANS database table.
 * 
 */
@Entity
@Table(name="WO_TASK_CARD_AC_MOD_PLANS")
@NamedQuery(name="WoTaskCardAcModPlan.findAll", query="SELECT w FROM WoTaskCardAcModPlan w")
public class WoTaskCardAcModPlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoTaskCardAcModPlanPK id;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

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

	public WoTaskCardAcModPlan() {
	}

	public WoTaskCardAcModPlanPK getId() {
		return this.id;
	}

	public void setId(WoTaskCardAcModPlanPK id) {
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

	public WoTaskCard getWoTaskCard() {
		return this.woTaskCard;
	}

	public void setWoTaskCard(WoTaskCard woTaskCard) {
		this.woTaskCard = woTaskCard;
	}

}