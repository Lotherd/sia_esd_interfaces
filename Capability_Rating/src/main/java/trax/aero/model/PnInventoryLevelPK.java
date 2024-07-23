package trax.aero.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the PN_INVENTORY_LEVEL database table.
 * 
 */
@Embeddable
public class PnInventoryLevelPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String pn;

	@Column(insertable=false, updatable=false)
	private String location;

	

	public PnInventoryLevelPK() {
	}
	public String getPn() {
		return this.pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getLocation() {
		return this.location;
	}
	public void setLocation(String location) {
		this.location = location;
	}


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PnInventoryLevelPK)) {
			return false;
		}
		PnInventoryLevelPK castOther = (PnInventoryLevelPK)other;
		return 
			this.pn.equals(castOther.pn)
			&& this.location.equals(castOther.location);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pn.hashCode();
		hash = hash * prime + this.location.hashCode();
		
		return hash;
	}
}