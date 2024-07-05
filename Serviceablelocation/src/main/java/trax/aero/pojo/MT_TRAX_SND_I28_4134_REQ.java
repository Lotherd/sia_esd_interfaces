package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MT_TRAX_SND_I28_4134_REQ", namespace="http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class MT_TRAX_SND_I28_4134_REQ {

	@XmlElement(name = "RFO_NO")
	private String rfoNo;

	@XmlElement(name = "PN")
	private String pn;

	@XmlElement(name = "SN")
	private String sn;

	@XmlElement(name = "INSP_LOT")
	private String inspLot;

	@XmlElement(name = "CODE")
	private String code;

	@XmlElement(name = "WO")
	private String wo;

	@XmlElement(name = "RELATION_CODE")
	private String relationCode;

	public String getRfoNo() {
		return rfoNo;
	}

	public void setRfoNo(String rfoNo) {
		this.rfoNo = rfoNo;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getInspLot() {
		return inspLot;
	}

	public void setInspLot(String inspLot) {
		this.inspLot = inspLot;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWo() {
		return wo;
	}

	public void setWo(String wo) {
		this.wo = wo;
	}

	public String getRelationCode() {
		return relationCode;
	}

	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	
	
	
	

	
	
}
