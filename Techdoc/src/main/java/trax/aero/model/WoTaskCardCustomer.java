package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the WO_TASK_CARD_CUSTOMER database table.
 * 
 */
@Entity
@Table(name="WO_TASK_CARD_CUSTOMER")
@NamedQuery(name="WoTaskCardCustomer.findAll", query="SELECT w FROM WoTaskCardCustomer w")
public class WoTaskCardCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoTaskCardCustomerPK id;

	//bi-directional many-to-one association to WoTaskCard
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="AC", referencedColumnName="AC" , insertable=false, updatable=false),
		@JoinColumn(name="PN", referencedColumnName="PN" , insertable=false, updatable=false),
		@JoinColumn(name="PN_SN", referencedColumnName="PN_SN" , insertable=false, updatable=false),
		@JoinColumn(name="TASK_CARD", referencedColumnName="TASK_CARD" , insertable=false, updatable=false),
		@JoinColumn(name="WO", referencedColumnName="WO" , insertable=false, updatable=false)
		})
	private WoTaskCard woTaskCard;

	public WoTaskCardCustomer() {
	}

	public WoTaskCardCustomerPK getId() {
		return this.id;
	}

	public void setId(WoTaskCardCustomerPK id) {
		this.id = id;
	}

	public WoTaskCard getWoTaskCard() {
		return this.woTaskCard;
	}

	public void setWoTaskCard(WoTaskCard woTaskCard) {
		this.woTaskCard = woTaskCard;
	}

}