package trax.aero.outbound;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;


@XmlAccessorType(XmlAccessType.FIELD)
public class OrderComponent {

	@XmlElement(name = "EXTERNAL_CUST_RES")
	private String EXTERNAL_CUST_RES;
	
	@XmlElement(name = "EXTERNAL_CUST_RES_ITEM")
	private String EXTERNAL_CUST_RES_ITEM;
	
	@XmlElement(name = "PICKLIST")
	private String PICKLIST;
	
	@XmlElement(name = "PICKLIST_LINE")
	private String PICKLIST_LINE;
	
	public String getEXTERNAL_CUST_RES() {
		return EXTERNAL_CUST_RES;
	}

	public void setEXTERNAL_CUST_RES(String EXTERNAL_CUST_RES) {
		this.EXTERNAL_CUST_RES = EXTERNAL_CUST_RES;
	}

	public String getEXTERNAL_CUST_RES_ITEM() {
		return EXTERNAL_CUST_RES_ITEM;
	}

	public void setEXTERNAL_CUST_RES_ITEM(String EXTERNAL_CUST_RES_ITEM) {
		this.EXTERNAL_CUST_RES_ITEM = EXTERNAL_CUST_RES_ITEM;
	}

	public String getPICKLIST() {
		return PICKLIST;
	}

	public void setPICKLIST(String PICKLIST) {
		this.PICKLIST = PICKLIST;
	}

	public String getPICKLIST_LINE() {
		return PICKLIST_LINE;
	}

	public void setPICKLIST_LINE(String PICKLIST_LINE) {
		this.PICKLIST_LINE = PICKLIST_LINE;
	}

    

}
