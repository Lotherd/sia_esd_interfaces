package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CONDITION_APPROVAL database table.
 * 
 */
@Embeddable
public class ConditionApprovalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String condition;

	@Column(name="USER_STATUS")
	private String userStatus;

	private String code;

	public ConditionApprovalPK() {
	}
	public String getCondition() {
		return this.condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getUserStatus() {
		return this.userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConditionApprovalPK)) {
			return false;
		}
		ConditionApprovalPK castOther = (ConditionApprovalPK)other;
		return 
			this.condition.equals(castOther.condition)
			&& this.userStatus.equals(castOther.userStatus)
			&& this.code.equals(castOther.code);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.condition.hashCode();
		hash = hash * prime + this.userStatus.hashCode();
		hash = hash * prime + this.code.hashCode();
		
		return hash;
	}
}