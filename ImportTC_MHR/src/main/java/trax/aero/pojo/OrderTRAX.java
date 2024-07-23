package trax.aero.pojo;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderTRAX {
	
	@XmlElement(name = "WO")
    private String wo;

    @XmlElement(name = "RFO_NO")
    private String rfoNo;

    @XmlElement(name = "EXCEPTION_ID")
    private String exceptionId;

    @XmlElement(name = "EXCEPTION_DETAIL")
    private String exceptionDetail;

    @XmlElement(name = "OPERATION")
    private List<OperationTRAX> operations;
    

	public String getWo() {
		return wo;
	}

	public void setWo(String wo) {
		this.wo = wo;
	}

	public String getRfoNo() {
		return rfoNo;
	}

	public void setRfoNo(String rfoNo) {
		this.rfoNo = rfoNo;
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

	public List<OperationTRAX> getOperations() {
		if(operations == null) {
			return Collections.emptyList();
		}
		return operations;
	}

	public void setOperations(List<OperationTRAX> operations) {
		this.operations = operations;
	}

}
