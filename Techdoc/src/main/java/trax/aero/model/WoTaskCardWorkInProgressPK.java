package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WO_TASK_CARD_WORK_IN_PROGRESS database table.
 * 
 */
@Embeddable
public class WoTaskCardWorkInProgressPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private long wo;

	@Column(name="TASK_CARD", insertable=false, updatable=false)
	private String taskCard;

	private String employee;

	@Column(insertable=false, updatable=false)
	private String ac;

	@Column(name="TASK_CARD_PN", insertable=false, updatable=false)
	private String taskCardPn;

	@Column(name="TASK_CARD_PN_SN", insertable=false, updatable=false)
	private String taskCardPnSn;

	@Column(name="TASK_CARD_ITEM")
	private long taskCardItem;

	private String skill;

	public WoTaskCardWorkInProgressPK() {
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
	public String getEmployee() {
		return this.employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
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
	public String getSkill() {
		return this.skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WoTaskCardWorkInProgressPK)) {
			return false;
		}
		WoTaskCardWorkInProgressPK castOther = (WoTaskCardWorkInProgressPK)other;
		return 
			(this.wo == castOther.wo)
			&& this.taskCard.equals(castOther.taskCard)
			&& this.employee.equals(castOther.employee)
			&& this.ac.equals(castOther.ac)
			&& this.taskCardPn.equals(castOther.taskCardPn)
			&& this.taskCardPnSn.equals(castOther.taskCardPnSn)
			&& (this.taskCardItem == castOther.taskCardItem)
			&& this.skill.equals(castOther.skill);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.wo ^ (this.wo >>> 32)));
		hash = hash * prime + this.taskCard.hashCode();
		hash = hash * prime + this.employee.hashCode();
		hash = hash * prime + this.ac.hashCode();
		hash = hash * prime + this.taskCardPn.hashCode();
		hash = hash * prime + this.taskCardPnSn.hashCode();
		hash = hash * prime + ((int) (this.taskCardItem ^ (this.taskCardItem >>> 32)));
		hash = hash * prime + this.skill.hashCode();
		
		return hash;
	}
}