package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WO_TASK_CARD_ITEM database table.
 * 
 */
@Embeddable
public class WoTaskCardItemPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private long wo;

	@Column(name="TASK_CARD", insertable=false, updatable=false)
	private String taskCard;

	@Column(name="TASK_CARD_ITEM")
	private long taskCardItem;

	@Column(insertable=false, updatable=false)
	private String ac;

	@Column(name="TASK_CARD_PN", insertable=false, updatable=false)
	private String taskCardPn;

	@Column(name="TASK_CARD_PN_SN", insertable=false, updatable=false)
	private String taskCardPnSn;

	public WoTaskCardItemPK() {
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
	public long getTaskCardItem() {
		return this.taskCardItem;
	}
	public void setTaskCardItem(long taskCardItem) {
		this.taskCardItem = taskCardItem;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WoTaskCardItemPK)) {
			return false;
		}
		WoTaskCardItemPK castOther = (WoTaskCardItemPK)other;
		return 
			(this.wo == castOther.wo)
			&& this.taskCard.equals(castOther.taskCard)
			&& (this.taskCardItem == castOther.taskCardItem)
			&& this.ac.equals(castOther.ac)
			&& this.taskCardPn.equals(castOther.taskCardPn)
			&& this.taskCardPnSn.equals(castOther.taskCardPnSn);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.wo ^ (this.wo >>> 32)));
		hash = hash * prime + this.taskCard.hashCode();
		hash = hash * prime + ((int) (this.taskCardItem ^ (this.taskCardItem >>> 32)));
		hash = hash * prime + this.ac.hashCode();
		hash = hash * prime + this.taskCardPn.hashCode();
		hash = hash * prime + this.taskCardPnSn.hashCode();
		
		return hash;
	}
}