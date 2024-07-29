package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WO_TASK_CARD_CONTROL database table.
 * 
 */
@Embeddable
public class WoTaskCardControlPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long wo;

	@Column(name="TASK_CARD")
	private String taskCard;

	@Column(name="CONTROL_CATEGORY")
	private String controlCategory;

	private String code;

	private String ac;

	@Column(name="TASK_CARD_PN")
	private String taskCardPn;

	@Column(name="TASK_CARD_PN_SN")
	private String taskCardPnSn;

	@Column(name="TASK_CARD_ITEM")
	private long taskCardItem;

	@Column(name="AC_TYPE")
	private String acType;

	@Column(name="AC_SERIES")
	private String acSeries;

	public WoTaskCardControlPK() {
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
	public String getControlCategory() {
		return this.controlCategory;
	}
	public void setControlCategory(String controlCategory) {
		this.controlCategory = controlCategory;
	}
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public long getTaskCardItem() {
		return this.taskCardItem;
	}
	public void setTaskCardItem(long taskCardItem) {
		this.taskCardItem = taskCardItem;
	}
	public String getAcType() {
		return this.acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getAcSeries() {
		return this.acSeries;
	}
	public void setAcSeries(String acSeries) {
		this.acSeries = acSeries;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WoTaskCardControlPK)) {
			return false;
		}
		WoTaskCardControlPK castOther = (WoTaskCardControlPK)other;
		return 
			(this.wo == castOther.wo)
			&& this.taskCard.equals(castOther.taskCard)
			&& this.controlCategory.equals(castOther.controlCategory)
			&& this.code.equals(castOther.code)
			&& this.ac.equals(castOther.ac)
			&& this.taskCardPn.equals(castOther.taskCardPn)
			&& this.taskCardPnSn.equals(castOther.taskCardPnSn)
			&& (this.taskCardItem == castOther.taskCardItem)
			&& this.acType.equals(castOther.acType)
			&& this.acSeries.equals(castOther.acSeries);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.wo ^ (this.wo >>> 32)));
		hash = hash * prime + this.taskCard.hashCode();
		hash = hash * prime + this.controlCategory.hashCode();
		hash = hash * prime + this.code.hashCode();
		hash = hash * prime + this.ac.hashCode();
		hash = hash * prime + this.taskCardPn.hashCode();
		hash = hash * prime + this.taskCardPnSn.hashCode();
		hash = hash * prime + ((int) (this.taskCardItem ^ (this.taskCardItem >>> 32)));
		hash = hash * prime + this.acType.hashCode();
		hash = hash * prime + this.acSeries.hashCode();
		
		return hash;
	}
}