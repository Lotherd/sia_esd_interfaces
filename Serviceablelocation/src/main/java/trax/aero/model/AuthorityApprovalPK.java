package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AUTHORITY_APPROVAL database table.
 * 
 */
@Embeddable
public class AuthorityApprovalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String authority;

	private String code;

	public AuthorityApprovalPK() {
	}
	public String getAuthority() {
		return this.authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
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
		if (!(other instanceof AuthorityApprovalPK)) {
			return false;
		}
		AuthorityApprovalPK castOther = (AuthorityApprovalPK)other;
		return 
			this.authority.equals(castOther.authority)
			&& this.code.equals(castOther.code);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.authority.hashCode();
		hash = hash * prime + this.code.hashCode();
		
		return hash;
	}
}