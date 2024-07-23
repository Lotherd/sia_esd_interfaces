package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_SND_I22_4129_REQ", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT22_SND {

    @XmlElement(name = "LOCATION", required = true)
    private String location;

    @XmlElement(name = "WO", required = true)
    private String wo;

    @XmlElement(name = "PN", required = true)
    private String pn;

    @XmlElement(name = "PN_SN")
    private String pnSn;

    @XmlElement(name = "SVO_NO", required = true)
    private String svoNo;

    @XmlElement(name = "RELATION_CODE", required = true)
    private String relationCode;

    @XmlElement(name = "INTERNAL_EXTERNAL", required = true)
    private String internalExternal;

    @XmlElement(name = "TC", required = true)
    private String tc;

    @XmlElement(name = "TRANSACTION", required = true)
    private String transaction;

    @XmlElement(name = "LEGACY_BATCH")
    private String legacyBatch;

    @XmlElement(name = "RFO_NO", required = true)
    private String rfoNo;

    @XmlElement(name = "QTY")
    private String qty;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWo() {
		return wo;
	}

	public void setWo(String wo) {
		this.wo = wo;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getPnSn() {
		return pnSn;
	}

	public void setPnSn(String pnSn) {
		this.pnSn = pnSn;
	}

	public String getSvoNo() {
		return svoNo;
	}

	public void setSvoNo(String svoNo) {
		this.svoNo = svoNo;
	}

	public String getRelationCode() {
		return relationCode;
	}

	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	public String getInternalExternal() {
		return internalExternal;
	}

	public void setInternalExternal(String internalExternal) {
		this.internalExternal = internalExternal;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getLegacyBatch() {
		return legacyBatch;
	}

	public void setLegacyBatch(String legacyBatch) {
		this.legacyBatch = legacyBatch;
	}

	public String getRfoNo() {
		return rfoNo;
	}

	public void setRfoNo(String rfoNo) {
		this.rfoNo = rfoNo;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}
    
    
}