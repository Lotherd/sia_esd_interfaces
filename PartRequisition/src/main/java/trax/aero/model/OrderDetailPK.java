package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ORDER_DETAIL database table.
 * 
 */
@Embeddable
public class OrderDetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ORDER_TYPE", insertable=false, updatable=false)
	private String orderType;

	@Column(name="ORDER_NUMBER", insertable=false, updatable=false)
	private long orderNumber;

	@Column(name="ORDER_LINE")
	private long orderLine;

	public OrderDetailPK() {
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
	public long getOrderLine() {
		return this.orderLine;
	}
	public void setOrderLine(long orderLine) {
		this.orderLine = orderLine;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrderDetailPK)) {
			return false;
		}
		OrderDetailPK castOther = (OrderDetailPK)other;
		return 
			this.orderType.equals(castOther.orderType)
			&& (this.orderNumber == castOther.orderNumber)
			&& (this.orderLine == castOther.orderLine);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orderType.hashCode();
		hash = hash * prime + ((int) (this.orderNumber ^ (this.orderNumber >>> 32)));
		hash = hash * prime + ((int) (this.orderLine ^ (this.orderLine >>> 32)));
		
		return hash;
	}
}