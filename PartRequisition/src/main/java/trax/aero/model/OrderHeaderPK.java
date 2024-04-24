package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ORDER_HEADER database table.
 * 
 */
@Embeddable
public class OrderHeaderPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ORDER_TYPE")
	private String orderType;

	@Column(name="ORDER_NUMBER")
	private long orderNumber;

	public OrderHeaderPK() {
	}
	public String getOrderType() {
		return this.orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public long getOrderNumber() {
		return this.orderNumber;
	}
	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrderHeaderPK)) {
			return false;
		}
		OrderHeaderPK castOther = (OrderHeaderPK)other;
		return 
			this.orderType.equals(castOther.orderType)
			&& (this.orderNumber == castOther.orderNumber);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orderType.hashCode();
		hash = hash * prime + ((int) (this.orderNumber ^ (this.orderNumber >>> 32)));
		
		return hash;
	}
}