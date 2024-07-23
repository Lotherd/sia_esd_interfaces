package trax.aero.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_RCV_I6_4108_RES", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT6_TRAX {
	
	 @XmlElement(name = "ORDER")
	 private ArrayList<OrderTRAX> order = new ArrayList<>();

	    
	    public ArrayList<OrderTRAX> getOrder() {
	        return order;
	    }

	    public void setOrder(ArrayList<OrderTRAX> order) {
	        this.order = order;
	    }

}
