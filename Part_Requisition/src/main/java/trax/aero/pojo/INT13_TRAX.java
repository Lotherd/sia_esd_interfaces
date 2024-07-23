package trax.aero.pojo;



import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_RCV_I13_4109_RES", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT13_TRAX {

	@XmlElement(name = "RFO_NO")
	private String RFO;
	
	@XmlElement(name = "EXCEPTION_ID")
	private String exceptionId;
	
	@XmlElement(name = "EXCEPTION_DETAIL")
	private String exceptionDetail;
	
	@XmlElement(name = "component")
    private List<Component_TRAX> component = new ArrayList<>();

	public String getRFO() {
		return RFO;
	}

	public void setRFO(String rFO) {
		RFO = rFO;
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

	public List<Component_TRAX> getComponent() {
		return component;
	}

	public void setComponent(List<Component_TRAX> component) {
		this.component = component;
	}

	
	
}
