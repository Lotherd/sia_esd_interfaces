package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WO_TASK_CARD database table.
 * 
 */
@Embeddable
public class WoTaskCardPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private long wo;

	@Column(name="TASK_CARD")
	private String taskCard;

	private String ac;

	private String pn;

	@Column(name="PN_SN")
	private String pnSn;

	public WoTaskCardPK() {
	}
	public long getWo() {
		return this.wo;
	}
	public void setWo(long wo) {
		this.wo = wo;
	}
	public String getTaskCard() {
		return this.taskCard;
	}
	public void setTaskCard(String taskCard) {
		this.taskCard = taskCard;
	}
	public String getAc() {
		return this.ac;
	}
	public void setAc(String ac) {
		this.ac = ac;
	}
	public String getPn() {
		return this.pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getPnSn() {
		return this.pnSn;
	}
	public void setPnSn(String pnSn) {
		this.pnSn = pnSn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WoTaskCardPK)) {
			return false;
		}
		WoTaskCardPK castOther = (WoTaskCardPK)other;
		return 
			(this.wo == castOther.wo)
			&& this.taskCard.equals(castOther.taskCard)
			&& this.ac.equals(castOther.ac)
			&& this.pn.equals(castOther.pn)
			&& this.pnSn.equals(castOther.pnSn);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.wo ^ (this.wo >>> 32)));
		hash = hash * prime + this.taskCard.hashCode();
		hash = hash * prime + this.ac.hashCode();
		hash = hash * prime + this.pn.hashCode();
		hash = hash * prime + this.pnSn.hashCode();
		
		return hash;
	}
}