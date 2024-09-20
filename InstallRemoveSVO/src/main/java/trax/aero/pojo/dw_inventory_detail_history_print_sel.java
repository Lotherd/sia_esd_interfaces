package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class dw_inventory_detail_history_print_sel {

    @XmlElement(name = "row")
	private RowSel RowObject;

	 // Getter Methods 

	 public RowSel getRow() {
	  return RowObject;
	 }

	 // Setter Methods 

	 public void setRow(RowSel rowObject) {
	  this.RowObject = rowObject;
	 }
}
