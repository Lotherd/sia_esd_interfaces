package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BLOB_TABLE database table.
 * 
 */
@Embeddable
public class BlobTablePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="BLOB_NO")
	private long blobNo;

	@Column(name="BLOB_LINE")
	private long blobLine;

	public BlobTablePK() {
	}
	public long getBlobNo() {
		return this.blobNo;
	}
	public void setBlobNo(long blobNo) {
		this.blobNo = blobNo;
	}
	public long getBlobLine() {
		return this.blobLine;
	}
	public void setBlobLine(long blobLine) {
		this.blobLine = blobLine;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BlobTablePK)) {
			return false;
		}
		BlobTablePK castOther = (BlobTablePK)other;
		return 
			(this.blobNo == castOther.blobNo)
			&& (this.blobLine == castOther.blobLine);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.blobNo ^ (this.blobNo >>> 32)));
		hash = hash * prime + ((int) (this.blobLine ^ (this.blobLine >>> 32)));
		
		return hash;
	}
}