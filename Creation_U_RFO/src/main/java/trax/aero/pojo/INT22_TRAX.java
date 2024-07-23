package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_RCV_I22_4129_RES", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT22_TRAX {

    @XmlElement(name = "SAP_REPAIR_RFO")
    private String sapRepairRfo;

    @XmlElement(name = "SAP_SVO", required = true)
    private String sapSvo;

    @XmlElement(name = "Print_Status")
    private String printStatus;

    @XmlElement(name = "TRAX_WO_NUMBER", required = true)
    private String traxWoNumber;

    @XmlElement(name = "TC_NUMBER", required = true)
    private String tcNumber;

    @XmlElement(name = "TRANSACTION", required = true)
    private String transaction;

    @XmlElement(name = "ERROR_CODE", required = true)
    private String errorCode;

    @XmlElement(name = "REMARKS", required = true)
    private String remarks;

	public String getSapRepairRfo() {
		return sapRepairRfo;
	}

	public void setSapRepairRfo(String sapRepairRfo) {
		this.sapRepairRfo = sapRepairRfo;
	}

	public String getSapSvo() {
		return sapSvo;
	}

	public void setSapSvo(String sapSvo) {
		this.sapSvo = sapSvo;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getTraxWoNumber() {
		return traxWoNumber;
	}

	public void setTraxWoNumber(String traxWoNumber) {
		this.traxWoNumber = traxWoNumber;
	}

	public String getTcNumber() {
		return tcNumber;
	}

	public void setTcNumber(String tcNumber) {
		this.tcNumber = tcNumber;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
    
}