package trax.aero.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the PN_INVENTORY_LEVEL database table.
 * 
 */
@Entity
@Table(name="PN_INVENTORY_LEVEL")
@NamedQuery(name="PnInventoryLevel.findAll", query="SELECT p FROM PnInventoryLevel p")
public class PnInventoryLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PnInventoryLevelPK id;



	private String buyer;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="EOQ_LEVEL")
	private BigDecimal eoqLevel;

	@Column(name="\"GROUP\"")
	private String group;

	@Column(name="MAXIMUM_ORDER")
	private BigDecimal maximumOrder;

	@Column(name="MAXIMUM_STOCK")
	private BigDecimal maximumStock;

	@Column(name="MINIMUM_ORDER")
	private BigDecimal minimumOrder;

	@Column(name="MINIMUM_STOCK")
	private BigDecimal minimumStock;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;
	
	@Column(name="COMPANY")
	private String company;

	private BigDecimal notes;

	private String planner;

	private String pou;

	@Column(name="REORDER_LEVEL")
	private BigDecimal reorderLevel;

	@Column(name="REPLENISHMENT_LEAD_TIME")
	private BigDecimal replenishmentLeadTime;



	@Column(name="SERVICE_LEVEL")
	private BigDecimal serviceLevel;

	@Column(name="TRIGGER_REQUISITION")
	private String triggerRequisition;

	//bi-directional many-to-one association to LocationMaster
	@ManyToOne
	@JoinColumn(name="LOCATION", insertable=false, updatable=false)
	private LocationMaster locationMaster;

	//bi-directional many-to-one association to PnMaster
	@ManyToOne
	@JoinColumn(name="PN", insertable=false, updatable=false)
	private PnMaster pnMaster;

	public PnInventoryLevel() {
	}

	public PnInventoryLevelPK getId() {
		return this.id;
	}

	public void setId(PnInventoryLevelPK id) {
		this.id = id;
	}



	public String getBuyer() {
		return this.buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
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

	public BigDecimal getEoqLevel() {
		return this.eoqLevel;
	}

	public void setEoqLevel(BigDecimal eoqLevel) {
		this.eoqLevel = eoqLevel;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public BigDecimal getMaximumOrder() {
		return this.maximumOrder;
	}

	public void setMaximumOrder(BigDecimal maximumOrder) {
		this.maximumOrder = maximumOrder;
	}

	public BigDecimal getMaximumStock() {
		return this.maximumStock;
	}

	public void setMaximumStock(BigDecimal maximumStock) {
		this.maximumStock = maximumStock;
	}

	public BigDecimal getMinimumOrder() {
		return this.minimumOrder;
	}

	public void setMinimumOrder(BigDecimal minimumOrder) {
		this.minimumOrder = minimumOrder;
	}

	public BigDecimal getMinimumStock() {
		return this.minimumStock;
	}

	public void setMinimumStock(BigDecimal minimumStock) {
		this.minimumStock = minimumStock;
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

	public String getPlanner() {
		return this.planner;
	}

	public void setPlanner(String planner) {
		this.planner = planner;
	}

	public String getPou() {
		return this.pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}

	public BigDecimal getReorderLevel() {
		return this.reorderLevel;
	}

	public void setReorderLevel(BigDecimal reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public BigDecimal getReplenishmentLeadTime() {
		return this.replenishmentLeadTime;
	}

	public void setReplenishmentLeadTime(BigDecimal replenishmentLeadTime) {
		this.replenishmentLeadTime = replenishmentLeadTime;
	}



	public BigDecimal getServiceLevel() {
		return this.serviceLevel;
	}

	public void setServiceLevel(BigDecimal serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getTriggerRequisition() {
		return this.triggerRequisition;
	}

	public void setTriggerRequisition(String triggerRequisition) {
		this.triggerRequisition = triggerRequisition;
	}

	public LocationMaster getLocationMaster() {
		return this.locationMaster;
	}

	public void setLocationMaster(LocationMaster locationMaster) {
		this.locationMaster = locationMaster;
	}

	public PnMaster getPnMaster() {
		return this.pnMaster;
	}

	public void setPnMaster(PnMaster pnMaster) {
		this.pnMaster = pnMaster;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	
}