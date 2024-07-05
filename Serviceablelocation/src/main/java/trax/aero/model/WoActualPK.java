package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WO_ACTUALS database table.
 * 
 */
@Embeddable
public class WoActualPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="WO_ACTUAL_TRANSACTION")
	private String woActualTransaction;

	@Column(name="TRASACTION_CATEGORY")
	private String trasactionCategory;

	public WoActualPK() {
	}
	public String getWoActualTransaction() {
		return this.woActualTransaction;
	}
	public void setWoActualTransaction(String woActualTransaction) {
		this.woActualTransaction = woActualTransaction;
	}
	public String getTrasactionCategory() {
		return this.trasactionCategory;
	}
	public void setTrasactionCategory(String trasactionCategory) {
		this.trasactionCategory = trasactionCategory;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WoActualPK)) {
			return false;
		}
		WoActualPK castOther = (WoActualPK)other;
		return 
			this.woActualTransaction.equals(castOther.woActualTransaction)
			&& this.trasactionCategory.equals(castOther.trasactionCategory);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.woActualTransaction.hashCode();
		hash = hash * prime + this.trasactionCategory.hashCode();
		
		return hash;
	}
}