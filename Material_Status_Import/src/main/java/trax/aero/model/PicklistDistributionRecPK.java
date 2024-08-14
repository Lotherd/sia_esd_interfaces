package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PICKLIST_DISTRIBUTION_REC database table.
 * 
 */
@Embeddable
public class PicklistDistributionRecPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"TRANSACTION\"", insertable=false, updatable=false)
	private String transaction;

	@Column(insertable=false, updatable=false)
	private long picklist;

	@Column(name="PICKLIST_LINE", insertable=false, updatable=false)
	private long picklistLine;

	@Column(name="DISTRIBUTION_LINE", insertable=false, updatable=false)
	private long distributionLine;

	@Column(name="CUST_TO")
	private long custTo;

	public PicklistDistributionRecPK() {
	}
	public String getTransaction() {
		return this.transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	public long getPicklist() {
		return this.picklist;
	}
	public void setPicklist(long picklist) {
		this.picklist = picklist;
	}
	public long getPicklistLine() {
		return this.picklistLine;
	}
	public void setPicklistLine(long picklistLine) {
		this.picklistLine = picklistLine;
	}
	public long getDistributionLine() {
		return this.distributionLine;
	}
	public void setDistributionLine(long distributionLine) {
		this.distributionLine = distributionLine;
	}
	public long getCustTo() {
		return this.custTo;
	}
	public void setCustTo(long custTo) {
		this.custTo = custTo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PicklistDistributionRecPK)) {
			return false;
		}
		PicklistDistributionRecPK castOther = (PicklistDistributionRecPK)other;
		return 
			this.transaction.equals(castOther.transaction)
			&& (this.picklist == castOther.picklist)
			&& (this.picklistLine == castOther.picklistLine)
			&& (this.distributionLine == castOther.distributionLine)
			&& (this.custTo == castOther.custTo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.transaction.hashCode();
		hash = hash * prime + ((int) (this.picklist ^ (this.picklist >>> 32)));
		hash = hash * prime + ((int) (this.picklistLine ^ (this.picklistLine >>> 32)));
		hash = hash * prime + ((int) (this.distributionLine ^ (this.distributionLine >>> 32)));
		hash = hash * prime + ((int) (this.custTo ^ (this.custTo >>> 32)));
		
		return hash;
	}
}