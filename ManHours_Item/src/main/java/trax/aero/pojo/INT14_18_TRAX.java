package trax.aero.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DT_TRAX_RCV_I14_I18_4126_RES", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT14_18_TRAX {

	@XmlElement(name = "RFO_NO")
	private String RFO;
	
	@XmlElement(name = "Error_code")
	private String Error_code;
	
	@XmlElement(name = "Remarks")
	private String Remarks;
	
	@XmlElement(name = "WO_number")
	private String WO_number;
	
	@XmlElement(name = "Operation")
    private List<Operation_TRAX> operation = new ArrayList<>();

	public String getRFO() {
		return RFO;
	}

	public void setRFO(String rFO) {
		RFO = rFO;
	}

	public String getError_code() {
		return Error_code;
	}

	public void setError_code(String error_code) {
		Error_code = error_code;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public String getWO_number() {
		return WO_number;
	}

	public void setWO_number(String wO_number) {
		WO_number = wO_number;
	}

	public List<Operation_TRAX> getOperation() {
		return operation;
	}

	public void setOperation(List<Operation_TRAX> operation) {
		this.operation = operation;
	}
    
    
	
}
