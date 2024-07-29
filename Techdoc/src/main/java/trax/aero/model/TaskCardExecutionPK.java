package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TASK_CARD_EXECUTION database table.
 * 
 */
@Embeddable
public class TaskCardExecutionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TASK_CARD", insertable=false, updatable=false)
	private String taskCard;

	private long item;

	public TaskCardExecutionPK() {
	}
	public String getTaskCard() {
		return this.taskCard;
	}
	public void setTaskCard(String taskCard) {
		this.taskCard = taskCard;
	}
	public long getItem() {
		return this.item;
	}
	public void setItem(long item) {
		this.item = item;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TaskCardExecutionPK)) {
			return false;
		}
		TaskCardExecutionPK castOther = (TaskCardExecutionPK)other;
		return 
			this.taskCard.equals(castOther.taskCard)
			&& (this.item == castOther.item);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.taskCard.hashCode();
		hash = hash * prime + ((int) (this.item ^ (this.item >>> 32)));
		
		return hash;
	}
}