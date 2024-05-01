package trax.aero.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_RCV_I31_ACK_4113", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT31_TRAX {
	
	@XmlElement(name = "RFO_NO")
    private String rfoNo;

    @XmlElement(name = "OPS_NO")
    private String opsNo;

    @XmlElement(name = "ACTUAL_WORK")
    private String actualWork;

    @XmlElement(name = "UNIT_WORK")
    private String unitWork;

    @XmlElement(name = "Idoc_number")
    private String idocNumber;

    @XmlElement(name = "Message_number")
    private String messageNumber;

    @XmlElement(name = "EXCEPTION_ID")
    private String exceptionId;

    @XmlElement(name = "EXCEPTION_DETAIL")
    private String exceptionDetail;

	public String getRfoNo() {
		return rfoNo;
	}

	public void setRfoNo(String rfoNo) {
		this.rfoNo = rfoNo;
	}

	public String getOpsNo() {
		return opsNo;
	}

	public void setOpsNo(String opsNo) {
		this.opsNo = opsNo;
	}

	public String getActualWork() {
		return actualWork;
	}

	public void setActualWork(String actualWork) {
		this.actualWork = actualWork;
	}

	public String getUnitWork() {
		return unitWork;
	}

	public void setUnitWork(String unitWork) {
		this.unitWork = unitWork;
	}

	public String getIdocNumber() {
		return idocNumber;
	}

	public void setIdocNumber(String idocNumber) {
		this.idocNumber = idocNumber;
	}

	public String getMessageNumber() {
		return messageNumber;
	}

	public void setMessageNumber(String messageNumber) {
		this.messageNumber = messageNumber;
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

}
