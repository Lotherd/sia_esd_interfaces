package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WO_TASK_CARD_EXECUTION database table.
 * 
 */
@Embeddable
public class WoTaskCardExecutionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long wo;

	@Column(name="TASK_CARD")
	private String taskCard;

	private String ac;

	private String pn;

	private String sn;

	@Column(name="PRE_TASK_CARD")
	private String preTaskCard;

	@Column(name="PRE_TASK_CARD_AC")
	private String preTaskCardAc;

	@Column(name="PRE_TASK_CARD_PN")
	private String preTaskCardPn;

	@Column(name="PRE_TASK_CARD_SN")
	private String preTaskCardSn;

	public WoTaskCardExecutionPK() {
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
	public String getSn() {
		return this.sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getPreTaskCard() {
		return this.preTaskCard;
	}
	public void setPreTaskCard(String preTaskCard) {
		this.preTaskCard = preTaskCard;
	}
	public String getPreTaskCardAc() {
		return this.preTaskCardAc;
	}
	public void setPreTaskCardAc(String preTaskCardAc) {
		this.preTaskCardAc = preTaskCardAc;
	}
	public String getPreTaskCardPn() {
		return this.preTaskCardPn;
	}
	public void setPreTaskCardPn(String preTaskCardPn) {
		this.preTaskCardPn = preTaskCardPn;
	}
	public String getPreTaskCardSn() {
		return this.preTaskCardSn;
	}
	public void setPreTaskCardSn(String preTaskCardSn) {
		this.preTaskCardSn = preTaskCardSn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WoTaskCardExecutionPK)) {
			return false;
		}
		WoTaskCardExecutionPK castOther = (WoTaskCardExecutionPK)other;
		return 
			(this.wo == castOther.wo)
			&& this.taskCard.equals(castOther.taskCard)
			&& this.ac.equals(castOther.ac)
			&& this.pn.equals(castOther.pn)
			&& this.sn.equals(castOther.sn)
			&& this.preTaskCard.equals(castOther.preTaskCard)
			&& this.preTaskCardAc.equals(castOther.preTaskCardAc)
			&& this.preTaskCardPn.equals(castOther.preTaskCardPn)
			&& this.preTaskCardSn.equals(castOther.preTaskCardSn);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.wo ^ (this.wo >>> 32)));
		hash = hash * prime + this.taskCard.hashCode();
		hash = hash * prime + this.ac.hashCode();
		hash = hash * prime + this.pn.hashCode();
		hash = hash * prime + this.sn.hashCode();
		hash = hash * prime + this.preTaskCard.hashCode();
		hash = hash * prime + this.preTaskCardAc.hashCode();
		hash = hash * prime + this.preTaskCardPn.hashCode();
		hash = hash * prime + this.preTaskCardSn.hashCode();
		
		return hash;
	}
}