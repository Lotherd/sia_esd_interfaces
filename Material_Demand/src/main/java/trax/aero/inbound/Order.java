package trax.aero.inbound;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

	@XmlElement(name = "SAP_OrderNumber", required = true)
	private String SAP_OrderNumber;
	
	@XmlElement(name = "OrderComponent", required = true)
	 private ArrayList<OrderComponent> OrderComponent;

	public String getSAP_OrderNumber() {
		return SAP_OrderNumber;
	}

	public void setSAP_OrderNumber(String sAP_OrderNumber) {
		SAP_OrderNumber = sAP_OrderNumber;
	}

	public ArrayList<OrderComponent> getOrderComponent() {
		return OrderComponent;
	}

	public void setOrderComponent(ArrayList<OrderComponent> orderComponent) {
		OrderComponent = orderComponent;
	}

    

    
}
