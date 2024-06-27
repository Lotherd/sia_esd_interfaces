package trax.aero.outbound;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

	@XmlElement(name = "RFO_NO")
    private String RFO_NO;
		
	@XmlElement(name = "EXCEPTION_ID")
	private String EXCEPTION_ID;
	
	@XmlElement(name = "EXCEPTION_DETAIL")
	private String EXCEPTION_DETAIL;

	
	
	
	@XmlElement(name = "ORDER_COMPONENT", required = true)
	 private ArrayList<OrderComponent> OrderComponent;

	public String getRFO_NO() {
		return RFO_NO;
	}

	public void setRFO_NO(String RFO_NO) {
		this.RFO_NO = RFO_NO;
	}

	public String getEXCEPTION_ID() {
		return EXCEPTION_ID;
	}

	public void setEXCEPTION_ID(String EXCEPTION_ID) {
		this.EXCEPTION_ID = EXCEPTION_ID;
	}

	public String getEXCEPTION_DETAIL() {
		return EXCEPTION_DETAIL;
	}

	public void setEXCEPTION_DETAIL(String EXCEPTION_DETAIL) {
		this.EXCEPTION_DETAIL = EXCEPTION_DETAIL;
	}

	public ArrayList<OrderComponent> getOrderComponent() {
		return OrderComponent;
	}

	public void setOrderComponent(ArrayList<OrderComponent> orderComponent) {
		OrderComponent = orderComponent;
	}

    
}
