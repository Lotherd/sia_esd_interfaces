package trax.aero.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PartAuthorityESD_PK implements Serializable {
	private static final long serialVersionUID = 1L;

    @Column(name="PN")
    private String pn;

    @Column(name="AUTHORITY")
    private String authority;

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PartAuthorityESD_PK)) {
            return false;
        }
        PartAuthorityESD_PK castOther = (PartAuthorityESD_PK) other;
        return this.pn.equals(castOther.pn) && this.authority.equals(castOther.authority);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.pn.hashCode();
        hash = hash * prime + this.authority.hashCode();
        
        return hash;
    }

}
