package trax.aero.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_SND_I14_I18_4126_REQ", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT14_18_SND {
	
	@XmlElement(name = "RFO_NO")
	private String RFO;
	
	@XmlElement(name = "WO")
	private String WO;
	
	@XmlElement(name = "Operation")
    private List<Operation_SND> operation = new ArrayList<>();

	public String getRFO() {
		return RFO;
	}

	public void setRFO(String rFO) {
		RFO = rFO;
	}

	public String getWO() {
		return WO;
	}

	public void setWO(String wO) {
		WO = wO;
	}

	public List<Operation_SND> getOperation() {
		return operation;
	}

	public void setOperation(List<Operation_SND> operation) {
		this.operation = operation;
	}
    
    

}
