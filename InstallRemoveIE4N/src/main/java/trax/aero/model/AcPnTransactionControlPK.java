package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AC_PN_TRANSACTION_CONTROL database table.
 * 
 */
@Embeddable
public class AcPnTransactionControlPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"TRANSACTION\"")
	private String transaction;

	@Column(name="TRANSACTION_ITEM")
	private long transactionItem;

	@Column(name="TRANSACTION_TYPE")
	private String transactionType;

	private String pn;

	private String sn;

	private String control;

	public AcPnTransactionControlPK() {
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
	public String getTransactionType() {
		return this.transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getPn() {
		return this.pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getSn() {
		return this.sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getControl() {
		return this.control;
	}
	public void setControl(String control) {
		this.control = control;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AcPnTransactionControlPK)) {
			return false;
		}
		AcPnTransactionControlPK castOther = (AcPnTransactionControlPK)other;
		return 
			this.transaction.equals(castOther.transaction)
			&& (this.transactionItem == castOther.transactionItem)
			&& this.transactionType.equals(castOther.transactionType)
			&& this.pn.equals(castOther.pn)
			&& this.sn.equals(castOther.sn)
			&& this.control.equals(castOther.control);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.transaction.hashCode();
		hash = hash * prime + ((int) (this.transactionItem ^ (this.transactionItem >>> 32)));
		hash = hash * prime + this.transactionType.hashCode();
		hash = hash * prime + this.pn.hashCode();
		hash = hash * prime + this.sn.hashCode();
		hash = hash * prime + this.control.hashCode();
		
		return hash;
	}
}