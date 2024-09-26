package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CONDITION_APPROVAL database table.
 * 
 */
@Entity
@Table(name="CONDITION_APPROVAL")
@NamedQuery(name="ConditionApproval.findAll", query="SELECT c FROM ConditionApproval c")
public class ConditionApproval implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConditionApprovalPK id;

	public ConditionApproval() {
	}

	public ConditionApprovalPK getId() {
		return this.id;
	}

	public void setId(ConditionApprovalPK id) {
		this.id = id;
	}

}