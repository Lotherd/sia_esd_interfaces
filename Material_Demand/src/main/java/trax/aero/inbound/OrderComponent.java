package trax.aero.inbound;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;


@XmlAccessorType(XmlAccessType.FIELD)
public class OrderComponent {

	@XmlElement(name = "TC_OperationNumber")
	private String operationNumber;
	@XmlElement(name = "MaterialNumber")
	private String materialNumber;
	@XmlElement(name = "Quantity")
	private String quantity;
	
	@XmlElement(name = "Trax_PicklistNumber")
	private String trax_PicklistNumber;
	@XmlElement(name = "Trax_PicklistLine")
	private String trax_PicklistLine;
	@XmlElement(name = "WO_Location")
	private String wO_Location;
	@XmlElement(name = "TaskCard")
	private String taskCard;
	@XmlElement(name = "SHOP_WO_SN")
	private String sHOP_WO_SN;
	@XmlElement(name = "ReservationNumber")
	private String reservationNumber;
	@XmlElement(name = "ReservationItem")
	private String reservationItem;
	
	@XmlElement(name = "DeletionIndicator")
	private String deletionIndicator;
   
       
    /**
     * Gets the value of the operationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationNumber() {
        return operationNumber;
    }

    /**
     * Sets the value of the operationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationNumber(String value) {
        this.operationNumber = value;
    }

    /**
     * Gets the value of the materialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialNumber() {
        return materialNumber;
    }

    /**
     * Sets the value of the materialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialNumber(String value) {
        this.materialNumber = value;
    }

    

    

    

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

    

    

    

   

    /**
     * Gets the value of the reservationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationNumber() {
        return reservationNumber;
    }

    /**
     * Sets the value of the reservationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationNumber(String value) {
        this.reservationNumber = value;
    }

    /**
     * Gets the value of the reservationItem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationItem() {
        return reservationItem;
    }

    /**
     * Sets the value of the reservationItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationItem(String value) {
        this.reservationItem = value;
    }

	public String getTrax_PicklistNumber() {
		return trax_PicklistNumber;
	}

	public void setTrax_PicklistNumber(String trax_PicklistNumber) {
		this.trax_PicklistNumber = trax_PicklistNumber;
	}

	public String getTrax_PicklistLine() {
		return trax_PicklistLine;
	}

	public void setTrax_PicklistLine(String trax_PicklistLine) {
		this.trax_PicklistLine = trax_PicklistLine;
	}

	public String getwO_Location() {
		return wO_Location;
	}

	public void setwO_Location(String wO_Location) {
		this.wO_Location = wO_Location;
	}

	public String getTaskCard() {
		return taskCard;
	}

	public void setTaskCard(String taskCard) {
		this.taskCard = taskCard;
	}

	public String getsHOP_WO_SN() {
		return sHOP_WO_SN;
	}

	public void setsHOP_WO_SN(String sHOP_WO_SN) {
		this.sHOP_WO_SN = sHOP_WO_SN;
	}

	public String getDeletionIndicator() {
		return deletionIndicator;
	}

	public void setDeletionIndicator(String deletionIndicator) {
		this.deletionIndicator = deletionIndicator;
	}

    

    

}
