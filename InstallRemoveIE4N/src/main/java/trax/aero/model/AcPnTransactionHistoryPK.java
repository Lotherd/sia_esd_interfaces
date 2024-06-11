package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AC_PN_TRANSACTION_HISTORY database table.
 * 
 */
@Embeddable
public class AcPnTransactionHistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"TRANSACTION\"")
	private String transaction;

	@Column(name="TRANSACTION_ITEM")
	private long transactionItem;

	public AcPnTransactionHistoryPK() {
	}
	public String getTransaction() {
		return this.transaction;
	}
	public void setTransaction(String transaction) {
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
		if (!(other instanceof AcPnTransactionHistoryPK)) {
			return false;
		}
		AcPnTransactionHistoryPK castOther = (AcPnTransactionHistoryPK)other;
		return 
			this.transaction.equals(castOther.transaction)
			&& (this.transactionItem == castOther.transactionItem);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.transaction.hashCode();
		hash = hash * prime + ((int) (this.transactionItem ^ (this.transactionItem >>> 32)));
		
		return hash;
	}
}