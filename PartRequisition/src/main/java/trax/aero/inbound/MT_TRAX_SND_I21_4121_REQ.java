

package trax.aero.inbound;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;




@XmlRootElement(name="MT_TRAX_SND_I21_4121_REQ", namespace="http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class MT_TRAX_SND_I21_4121_REQ {

	@XmlElement(name = "Trax_repair_order")
	private String Trax_repair_order;

	public String getTrax_repair_order() {
		return Trax_repair_order;
	}

	public String getTrax_repair_order_line() {
		return Trax_repair_order_line;
	}

	public String getTrax_WO_location() {
		return Trax_WO_location;
	}

	public String getESN() {
		return ESN;
	}

	public String getWO() {
		return WO;
	}

	public String getMaterial() {
		return Material;
	}

	public String getQuantity() {
		return Quantity;
	}

	public String getDelivery_date() {
		return Delivery_date;
	}

	public String getItem_text() {
		return item_text;
	}

	public void setTrax_repair_order(String trax_repair_order) {
		Trax_repair_order = trax_repair_order;
	}

	public void setTrax_repair_order_line(String trax_repair_order_line) {
		Trax_repair_order_line = trax_repair_order_line;
	}

	public void setTrax_WO_location(String trax_WO_location) {
		Trax_WO_location = trax_WO_location;
	}

	public void setESN(String eSN) {
		ESN = eSN;
	}

	public void setWO(String wO) {
		WO = wO;
	}

	public void setMaterial(String material) {
		Material = material;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public void setDelivery_date(String delivery_date) {
		Delivery_date = delivery_date;
	}

	public void setItem_text(String item_text) {
		this.item_text = item_text;
	}

	@XmlElement(name = "Trax_repair_order_line")
	private String Trax_repair_order_line;
	
	@XmlElement(name = "Trax_WO_location")
	private String Trax_WO_location;
    
	@XmlElement(name = "ESN")
	private String ESN;

	@XmlElement(name = "WO")
	private String WO;

	@XmlElement(name = "Material")
	private String Material;
	
	@XmlElement(name = "Quantity")
	private String Quantity;
	
	//dd-mm-yyyy
	@XmlElement(name = "Delivery_date")
	private String Delivery_date;
	
	@XmlElement(name = "item_text")
	private String item_text;
	
	
	 @Override
	    public boolean equals(Object obj) {
	        // TODO Auto-generated method stub
	        if(obj instanceof MT_TRAX_SND_I21_4121_REQ)
	        {
	        	MT_TRAX_SND_I21_4121_REQ temp = (MT_TRAX_SND_I21_4121_REQ) obj;
	            if(this.Trax_repair_order.equals(temp.Trax_repair_order) &&
	            		this.Trax_repair_order_line.equals(temp.Trax_repair_order_line) &&	
	            		this.Trax_WO_location.equals(temp.Trax_WO_location) &&
	            		this.ESN.equals(temp.ESN) &&
	            		this.WO.equals(temp.WO) &&
	            		this.Material.equals(temp.Material) &&
	            		this.Quantity.equals(temp.Quantity) &&
	            		this.Delivery_date.equals(temp.Delivery_date) &&
	            		this.item_text.equals(temp.item_text))
	                return true;
	        }
	        return false;
	    }

	    @Override
	    public int hashCode() {
	        // TODO Auto-generated method stub
	        
	        return (this.Trax_repair_order.hashCode() +
	        		this.Trax_repair_order_line.hashCode() +	
	        		this.Trax_WO_location.hashCode() +
	        		this.ESN.hashCode() +
	        		this.WO.hashCode() +
	        		this.Material.hashCode() +
	        		this.Quantity.hashCode() +
	        		this.Delivery_date.hashCode() +
	        		this.item_text.hashCode());        
	    }
	
}
