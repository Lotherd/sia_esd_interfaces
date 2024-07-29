package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AC_TYPE_SERIES_MASTER database table.
 * 
 */
@Embeddable
public class AcTypeSeriesMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="AC_TYPE")
	private String acType;

	@Column(name="AC_SERIES")
	private String acSeries;

	public AcTypeSeriesMasterPK() {
	}
	public String getAcType() {
		return this.acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getAcSeries() {
		return this.acSeries;
	}
	public void setAcSeries(String acSeries) {
		this.acSeries = acSeries;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AcTypeSeriesMasterPK)) {
			return false;
		}
		AcTypeSeriesMasterPK castOther = (AcTypeSeriesMasterPK)other;
		return 
			this.acType.equals(castOther.acType)
			&& this.acSeries.equals(castOther.acSeries);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.acType.hashCode();
		hash = hash * prime + this.acSeries.hashCode();
		
		return hash;
	}
}