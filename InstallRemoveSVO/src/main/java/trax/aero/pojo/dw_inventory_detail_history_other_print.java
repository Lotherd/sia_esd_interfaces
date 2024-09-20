package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class dw_inventory_detail_history_other_print {

    @XmlElement(name = "row")
	private RowOther RowObject;

	 // Getter Methods 

	 public RowOther getRow() {
	  return RowObject;
	 }

	 // Setter Methods 

	 public void setRow(RowOther rowObject) {
	  this.RowObject = rowObject;
	 }
}
