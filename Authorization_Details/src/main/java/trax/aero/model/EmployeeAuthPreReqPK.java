package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EMPLOYEE_AUTH_PRE_REQ database table.
 * 
 */
@Embeddable
public class EmployeeAuthPreReqPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="AUTHORIZATION_CODE", insertable=false, updatable=false)
	private String authorizationCode;

	@Column(name="TRANSACTION_TYPE")
	private String transactionType;

	private long item;

	public EmployeeAuthPreReqPK() {
	}
	public String getAuthorizationCode() {
		return this.authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public String getTransactionType() {
		return this.transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public long getItem() {
		return this.item;
	}
	public void setItem(long item) {
		this.item = item;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmployeeAuthPreReqPK)) {
			return false;
		}
		EmployeeAuthPreReqPK castOther = (EmployeeAuthPreReqPK)other;
		return 
			this.authorizationCode.equals(castOther.authorizationCode)
			&& this.transactionType.equals(castOther.transactionType)
			&& (this.item == castOther.item);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.authorizationCode.hashCode();
		hash = hash * prime + this.transactionType.hashCode();
		hash = hash * prime + ((int) (this.item ^ (this.item >>> 32)));
		
		return hash;
	}
}