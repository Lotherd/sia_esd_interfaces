package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AUTHORITY_APPROVAL database table.
 * 
 */
@Entity
@Table(name="AUTHORITY_APPROVAL")
@NamedQuery(name="AuthorityApproval.findAll", query="SELECT a FROM AuthorityApproval a")
public class AuthorityApproval implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AuthorityApprovalPK id;

	public AuthorityApproval() {
	}

	public AuthorityApprovalPK getId() {
		return this.id;
	}

	public void setId(AuthorityApprovalPK id) {
		this.id = id;
	}

}