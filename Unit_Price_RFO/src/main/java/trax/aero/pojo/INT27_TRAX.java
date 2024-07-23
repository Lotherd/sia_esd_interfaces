package trax.aero.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_RCV_I27_4135_RES", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT27_TRAX {
	
	@XmlElement(name = "SAP_order_number")
	private String order_number;
	
	@XmlElement(name = "TRAX_WO_number")
	private String WO;
	
	@XmlElement(name = "Error_code")
	private String error_code;
	
	@XmlElement(name = "Remarks")
	private String Remarks;
	
	@XmlElement(name = "Operation")
    private List<Operation_TRAX> operation = new ArrayList<>();

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getWO() {
		return WO;
	}

	public void setWO(String wO) {
		WO = wO;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public List<Operation_TRAX> getOperation() {
		return operation;
	}

	public void setOperation(List<Operation_TRAX> operation) {
		this.operation = operation;
	}
    
    

}
