package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class dw_inventory_detail_history_order_print {

    @XmlElement(name = "row")
	private RowOrder RowObject;

	 // Getter Methods 

	 public RowOrder getRow() {
	  return RowObject;
	 }

	 // Setter Methods 

	 public void setRow(RowOrder rowObject) {
	  this.RowObject = rowObject;
	 }
}
