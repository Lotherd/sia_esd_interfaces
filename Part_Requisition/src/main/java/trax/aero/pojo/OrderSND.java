package trax.aero.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSND {
	
	@XmlElement(name = "Order_No")
	private String orderNO;
	
	@XmlElement(name = "Order_Component")
	private List<OrderComponentSND> components;

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public List<OrderComponentSND> getComponents() {
		return components;
	}

	public void setComponents(List<OrderComponentSND> components) {
		this.components = components;
	}

}
