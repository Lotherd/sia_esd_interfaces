package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PN_INVENTORY_HISTORY database table.
 * 
 */
@Embeddable
public class PnInventoryHistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TRANSACTION_NO")
	private long transactionNo;

	private long batch;

	public PnInventoryHistoryPK() {
	}
	public long getTransactionNo() {
		return this.transactionNo;
	}
	public void setTransactionNo(long transactionNo) {
		this.transactionNo = transactionNo;
	}
	public long getBatch() {
		return this.batch;
	}
	public void setBatch(long batch) {
		this.batch = batch;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PnInventoryHistoryPK)) {
			return false;
		}
		PnInventoryHistoryPK castOther = (PnInventoryHistoryPK)other;
		return 
			(this.transactionNo == castOther.transactionNo)
			&& (this.batch == castOther.batch);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.transactionNo ^ (this.transactionNo >>> 32)));
		hash = hash * prime + ((int) (this.batch ^ (this.batch >>> 32)));
		
		return hash;
	}
}