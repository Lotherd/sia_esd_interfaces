package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WO_TASK_CARD_PN database table.
 * 
 */
@Embeddable
public class WoTaskCardPnPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long wo;

	@Column(name="TASK_CARD")
	private String taskCard;

	private String pn;

	private String ac;

	@Column(name="TASK_CARD_PN")
	private String taskCardPn;

	@Column(name="TASK_CARD_PN_SN")
	private String taskCardPnSn;

	private String reserve;

	public WoTaskCardPnPK() {
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
	public String getPn() {
		return this.pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getAc() {
		return this.ac;
	}
	public void setAc(String ac) {
		this.ac = ac;
	}
	public String getTaskCardPn() {
		return this.taskCardPn;
	}
	public void setTaskCardPn(String taskCardPn) {
		this.taskCardPn = taskCardPn;
	}
	public String getTaskCardPnSn() {
		return this.taskCardPnSn;
	}
	public void setTaskCardPnSn(String taskCardPnSn) {
		this.taskCardPnSn = taskCardPnSn;
	}
	public String getReserve() {
		return this.reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WoTaskCardPnPK)) {
			return false;
		}
		WoTaskCardPnPK castOther = (WoTaskCardPnPK)other;
		return 
			(this.wo == castOther.wo)
			&& this.taskCard.equals(castOther.taskCard)
			&& this.pn.equals(castOther.pn)
			&& this.ac.equals(castOther.ac)
			&& this.taskCardPn.equals(castOther.taskCardPn)
			&& this.taskCardPnSn.equals(castOther.taskCardPnSn)
			&& this.reserve.equals(castOther.reserve);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.wo ^ (this.wo >>> 32)));
		hash = hash * prime + this.taskCard.hashCode();
		hash = hash * prime + this.pn.hashCode();
		hash = hash * prime + this.ac.hashCode();
		hash = hash * prime + this.taskCardPn.hashCode();
		hash = hash * prime + this.taskCardPnSn.hashCode();
		hash = hash * prime + this.reserve.hashCode();
		
		return hash;
	}
}