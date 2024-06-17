package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MT_TRAX_RCV_I19_4130_RES", namespace="http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class I19_Response {
	
	
	@XmlElement(name = "ESD_SVO")
	private String esdSvo;
	
	@XmlElement(name = "PN")
	private String pn;
	
	@XmlElement(name = "SN")
	private String sn;
	
	@XmlElement(name = "LEGACY_BATCH")
	private String legacyBatch;
	
	@XmlElement(name = "EXCEPTION_ID")
	private String exceptionId;
	
	@XmlElement(name = "EXCEPTION_DETAIL")
	private String exceptionDetail;
	
	@XmlElement(name = "WO")
	private String wo;
	
	@XmlElement(name = "TC")
	private String tc;
	
	@XmlElement(name = "TRANSACTION")
	private String transaction;

	public String getEsdSvo() {
		return esdSvo;
	}

	public void setEsdSvo(String esdSvo) {
		this.esdSvo = esdSvo;
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

	public String getLegacyBatch() {
		return legacyBatch;
	}

	public void setLegacyBatch(String legacyBatch) {
		this.legacyBatch = legacyBatch;
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getExceptionDetail() {
		return exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}

	public String getWo() {
		return wo;
	}

	public void setWo(String wo) {
		this.wo = wo;
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
	
	
	
	

   
   
}
