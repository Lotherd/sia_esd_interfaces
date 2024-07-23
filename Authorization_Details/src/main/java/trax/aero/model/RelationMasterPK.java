package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the RELATION_MASTER database table.
 * 
 */
@Embeddable
public class RelationMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="RELATION_TRANSACTION")
	private String relationTransaction;

	@Column(name="RELATION_CODE")
	private String relationCode;

	public RelationMasterPK() {
	}
	public String getRelationTransaction() {
		return this.relationTransaction;
	}
	public void setRelationTransaction(String relationTransaction) {
		this.relationTransaction = relationTransaction;
	}
	public String getRelationCode() {
		return this.relationCode;
	}
	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelationMasterPK)) {
			return false;
		}
		RelationMasterPK castOther = (RelationMasterPK)other;
		return 
			this.relationTransaction.equals(castOther.relationTransaction)
			&& this.relationCode.equals(castOther.relationCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.relationTransaction.hashCode();
		hash = hash * prime + this.relationCode.hashCode();
		
		return hash;
	}
}