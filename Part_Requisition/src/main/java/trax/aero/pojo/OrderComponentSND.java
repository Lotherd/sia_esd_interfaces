package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderComponentSND implements Comparable<OrderComponentSND>{
	
	@SuppressWarnings("removal")
	@Override
	public int compareTo(OrderComponentSND e) {
		return Long.compare(new Long(this.requisition),(new Long(e.getRequisition())));
	}
	
	@XmlElement(name = "Material_Part_number")
	private String MaterialPartNumber;
	
	@XmlElement(name = "Quantity")
	private String Quantity;
	
	@XmlElement(name = "OPS_NO")
	private String ACT;
	
	@XmlElement(name = "Goods_Recipient")
	private String goodsRecipient;
	
	@XmlElement(name = "Requisition")
	private String requisition;
	
	@XmlElement(name = "Requisition_Line")
	private String requisitionLine;
	
	@XmlElement(name = "TRAX_WO_Location")
	private String WO_location;
	
	@XmlElement(name = "Task_Card_number")
	private String TC_number;
	
	@XmlElement(name = "Shop_WO_SN")
	private String woSN;
	
	@XmlElement(name = "PR_Number")
	private String PRnumber;
	
	@XmlElement(name = "PR_item")
	private String PRitem;

	public String getMaterialPartNumber() {
		return MaterialPartNumber;
	}

	public void setMaterialPartNumber(String materialPartNumber) {
		MaterialPartNumber = materialPartNumber;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getACT() {
		return ACT;
	}

	public void setACT(String aCT) {
		ACT = aCT;
	}

	public String getGoodsRecipient() {
		return goodsRecipient;
	}

	public void setGoodsRecipient(String goodsRecipient) {
		this.goodsRecipient = goodsRecipient;
	}

	public String getRequisition() {
		return requisition;
	}

	public void setRequisition(String requisition) {
		this.requisition = requisition;
	}

	public String getRequisitionLine() {
		return requisitionLine;
	}

	public void setRequisitionLine(String requisitionLine) {
		this.requisitionLine = requisitionLine;
	}

	public String getWO_location() {
		return WO_location;
	}

	public void setWO_location(String wO_location) {
		WO_location = wO_location;
	}

	public String getTC_number() {
		return TC_number;
	}

	public void setTC_number(String tC_number) {
		TC_number = tC_number;
	}

	public String getWoSN() {
		return woSN;
	}

	public void setWoSN(String woSN) {
		this.woSN = woSN;
	}

	public String getPRnumber() {
		return PRnumber;
	}

	public void setPRnumber(String pRnumber) {
		PRnumber = pRnumber;
	}

	public String getPRitem() {
		return PRitem;
	}

	public void setPRitem(String pRitem) {
		PRitem = pRitem;
	}
	
}
