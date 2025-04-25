package trax.aero.inbound;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "MT_TRAX_SND_I21_4121_REQ", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class MT_TRAX_SND_I21_4121_REQ {

    @XmlElement(name = "Trax_repair_order")
    private String Trax_repair_order;

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

    // dd-mm-yyyy
    @XmlElement(name = "Delivery_date")
    private String Delivery_date;

    @XmlElement(name = "item_text")
    private String item_text;

    // Getters y setters
    public String getTrax_repair_order() {
        return Trax_repair_order;
    }

    public void setTrax_repair_order(String trax_repair_order) {
        Trax_repair_order = trax_repair_order;
    }

    public String getTrax_repair_order_line() {
        return Trax_repair_order_line;
    }

    public void setTrax_repair_order_line(String trax_repair_order_line) {
        Trax_repair_order_line = trax_repair_order_line;
    }

    public String getTrax_WO_location() {
        return Trax_WO_location;
    }

    public void setTrax_WO_location(String trax_WO_location) {
        Trax_WO_location = trax_WO_location;
    }

    public String getESN() {
        return ESN;
    }

    public void setESN(String ESN) {
        this.ESN = ESN;
    }

    public String getWO() {
        return WO;
    }

    public void setWO(String WO) {
        this.WO = WO;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDelivery_date() {
        return Delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        Delivery_date = delivery_date;
    }

    public String getItem_text() {
        return item_text;
    }

    public void setItem_text(String item_text) {
        this.item_text = item_text;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        MT_TRAX_SND_I21_4121_REQ other = (MT_TRAX_SND_I21_4121_REQ) obj;
        
        return Objects.equals(Trax_repair_order, other.Trax_repair_order)
            && Objects.equals(Trax_repair_order_line, other.Trax_repair_order_line)
            && Objects.equals(Trax_WO_location, other.Trax_WO_location)
            && Objects.equals(ESN, other.ESN)
            && Objects.equals(WO, other.WO)
            && Objects.equals(Material, other.Material)
            && Objects.equals(Quantity, other.Quantity)
            && Objects.equals(Delivery_date, other.Delivery_date)
            && Objects.equals(item_text, other.item_text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            Trax_repair_order, 
            Trax_repair_order_line, 
            Trax_WO_location, 
            ESN, 
            WO, 
            Material, 
            Quantity, 
            Delivery_date, 
            item_text
        );
    }
}
