package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TASK_CARD_CONTROL_MASTER database table.
 * 
 */
@Embeddable
public class TaskCardControlMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CONTROL_CATEGORY")
	private String controlCategory;

	private String code;

	@Column(name="AC_TYPE")
	private String acType;

	@Column(name="AC_SERIES")
	private String acSeries;

	public TaskCardControlMasterPK() {
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
		if (!(other instanceof TaskCardControlMasterPK)) {
			return false;
		}
		TaskCardControlMasterPK castOther = (TaskCardControlMasterPK)other;
		return 
			this.controlCategory.equals(castOther.controlCategory)
			&& this.code.equals(castOther.code)
			&& this.acType.equals(castOther.acType)
			&& this.acSeries.equals(castOther.acSeries);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.controlCategory.hashCode();
		hash = hash * prime + this.code.hashCode();
		hash = hash * prime + this.acType.hashCode();
		hash = hash * prime + this.acSeries.hashCode();
		
		return hash;
	}
}