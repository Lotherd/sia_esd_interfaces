package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Component_TRAX {
	
	@XmlElement(name = "PR_NUMBER")
	private String PRnumber;
	
	@XmlElement(name = "PR_ITEM")
	private String PRitem;
	
	@XmlElement(name = "OPS_NO")
	private String OPS;
	
	@XmlElement(name = "PN")
	private String pn;
	
	@XmlElement(name = "REQUISITION")
	private String requisition;
	
	@XmlElement(name = "REQUISITION_LINE")
	private String requisitionLine;
	
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
	
	public String getOPS() {
		return OPS;
	}

	public void setOPS(String oPS) {
		OPS = oPS;
	}
	
	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
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


}
