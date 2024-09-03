package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the INTERFACE_DATA database table.
 * 
 */
@Embeddable
public class InterfaceDataPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"TRANSACTION\"", insertable=false, updatable=false)
	private long transaction;

	@Column(name="TRANSACTION_ITEM")
	private long transactionItem;

	public InterfaceDataPK() {
	}
	public long getTransaction() {
		return this.transaction;
	}
	public void setTransaction(long transaction) {
		this.transaction = transaction;
	}
	public long getTransactionItem() {
		return this.transactionItem;
	}
	public void setTransactionItem(long transactionItem) {
		this.transactionItem = transactionItem;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InterfaceDataPK)) {
			return false;
		}
		InterfaceDataPK castOther = (InterfaceDataPK)other;
		return 
			(this.transaction == castOther.transaction)
			&& (this.transactionItem == castOther.transactionItem);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.transaction ^ (this.transaction >>> 32)));
		hash = hash * prime + ((int) (this.transactionItem ^ (this.transactionItem >>> 32)));
		
		return hash;
	}
}