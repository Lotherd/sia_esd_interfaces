package trax.aero.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_SND_I27_4135_REQ", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT27_SND {

	@XmlElement(name = "TRAX_WO_number")
	private String WO;
	
	@XmlElement(name = "WBS_element")
	private String WBS;
	
	@XmlElement(name = "RFO")
	private String RFO;
	
	@XmlElement(name = "Operation")
    private List<Operation_SND> operation = new ArrayList<>();

	public String getWO() {
		return WO;
	}

	public void setWO(String wO) {
		WO = wO;
	}

	public String getWBS() {
		return WBS;
	}

	public void setWBS(String wBS) {
		WBS = wBS;
	}

	public String getRFO() {
		return RFO;
	}

	public void setRFO(String rFO) {
		RFO = rFO;
	}

	public List<Operation_SND> getOperation() {
		return operation;
	}

	public void setOperation(List<Operation_SND> operation) {
		this.operation = operation;
	}
    
    
	 
}
