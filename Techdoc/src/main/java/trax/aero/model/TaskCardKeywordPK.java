package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TASK_CARD_KEYWORD database table.
 * 
 */
@Embeddable
public class TaskCardKeywordPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TASK_CARD", insertable=false, updatable=false)
	private String taskCard;

	@Column(name="KEYWORD_ITEM")
	private long keywordItem;

	public TaskCardKeywordPK() {
	}
	public String getTaskCard() {
		return this.taskCard;
	}
	public void setTaskCard(String taskCard) {
		this.taskCard = taskCard;
	}
	public long getKeywordItem() {
		return this.keywordItem;
	}
	public void setKeywordItem(long keywordItem) {
		this.keywordItem = keywordItem;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TaskCardKeywordPK)) {
			return false;
		}
		TaskCardKeywordPK castOther = (TaskCardKeywordPK)other;
		return 
			this.taskCard.equals(castOther.taskCard)
			&& (this.keywordItem == castOther.keywordItem);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.taskCard.hashCode();
		hash = hash * prime + ((int) (this.keywordItem ^ (this.keywordItem >>> 32)));
		
		return hash;
	}
}