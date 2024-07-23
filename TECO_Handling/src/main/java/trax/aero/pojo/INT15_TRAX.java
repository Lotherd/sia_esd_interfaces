package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_RCV_I15_4125_RES", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT15_TRAX {
	
	@XmlElement(name = "RFO_NO")
	private String RFO_NO;
	
	@XmlElement(name = "TRAX_WO_number")
	private String WO;
	
	@XmlElement(name = "Error_code")
	private String exceptionId;
	
	@XmlElement(name = "Remarks")
	private String exceptionDetail;
	
	@XmlElement(name = "TC_number")
	private String TC_number;
	
	@XmlElement(name = "Transaction")
	private String Transaction;
	
	@XmlElement(name = "Flag")
	private String Flag;

	public String getRFO_NO() {
		return RFO_NO;
	}

	public void setRFO_NO(String rFO_NO) {
		RFO_NO = rFO_NO;
	}

	public String getWO() {
		return WO;
	}

	public void setWO(String wO) {
		WO = wO;
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

	public String getTC_number() {
		return TC_number;
	}

	public void setTC_number(String tC_number) {
		TC_number = tC_number;
	}

	public String getTransaction() {
		return Transaction;
	}

	public void setTransaction(String transaction) {
		Transaction = transaction;
	}

	public String getFlag() {
		return Flag;
	}

	public void setFlag(String flag) {
		Flag = flag;
	}

	
}
