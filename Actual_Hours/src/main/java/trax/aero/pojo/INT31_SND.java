package trax.aero.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_I39_I40_4064", namespace = "http://singaporeair.com/mro/TRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT31_SND {
	
	@XmlElement(name = "Order_Header")
	private ArrayList<OrderSND> order;

	public ArrayList<OrderSND> getOrder() {
		return order;
	}

	public void setOrder(ArrayList<OrderSND> order) {
		this.order = order;
	}

}
