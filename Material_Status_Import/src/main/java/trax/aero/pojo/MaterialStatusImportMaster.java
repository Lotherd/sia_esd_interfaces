package trax.aero.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MT_TRAX_RCV_I11_I12_4119", namespace="http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class MaterialStatusImportMaster {
	

	@XmlElement(name = "RFO_number_order_number")
	private String RFO_NO;
	    
	@XmlElement(name = "Trax_Requisition_Number")
	private String PICKLIST;
		
	@XmlElement(name = "Trax_Requisition_line")
	private String PICKLIST_LINE;
	
	@XmlElement(name = "SAP_Reservation_number")
	private String EXTERNAL_CUST_RES;
	
	@XmlElement(name = "SAP_Reservation_line_item")
	private String EXTERNAL_CUST_RES_ITEM;
	
	@XmlElement(name = "Material_number")
	private String PN;
	    
	@XmlElement(name = "Transfer_order")
	private ArrayList<Transfer_order > Transfer_order;
	
	
	

	
	public String getRFO_NO() {
		return RFO_NO;
	}

	public void setRFO_NO(String rFO_NO) {
		RFO_NO = rFO_NO;
	}

	public String getPICKLIST() {
		return PICKLIST;
	}

	public void setPICKLIST(String pICKLIST) {
		PICKLIST = pICKLIST;
	}

	public String getPICKLIST_LINE() {
		return PICKLIST_LINE;
	}

	public void setPICKLIST_LINE(String pICKLIST_LINE) {
		PICKLIST_LINE = pICKLIST_LINE;
	}

	public String getEXTERNAL_CUST_RES() {
		return EXTERNAL_CUST_RES;
	}

	public void setEXTERNAL_CUST_RES(String eXTERNAL_CUST_RES) {
		EXTERNAL_CUST_RES = eXTERNAL_CUST_RES;
	}

	public String getEXTERNAL_CUST_RES_ITEM() {
		return EXTERNAL_CUST_RES_ITEM;
	}

	public void setEXTERNAL_CUST_RES_ITEM(String eXTERNAL_CUST_RES_ITEM) {
		EXTERNAL_CUST_RES_ITEM = eXTERNAL_CUST_RES_ITEM;
	}

	public String getPN() {
		return PN;
	}

	public void setPN(String pN) {
		PN = pN;
	}

	

	public ArrayList<Transfer_order > getTransfer_order() {
		return Transfer_order;
	}

	public void setTransfer_order(ArrayList<Transfer_order > transfer_order) {
		Transfer_order = transfer_order;
	}

	
}
