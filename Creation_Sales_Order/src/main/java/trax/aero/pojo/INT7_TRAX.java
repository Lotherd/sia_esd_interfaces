package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_RCV_I7_4114_RES", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT7_TRAX {
	
	@XmlElement(name = "RFO_NO")
    private String RfoNO;
    
    @XmlElement(name = "WO")
    private String WO;
    
    @XmlElement(name = "EXCEPTION_ID")
    private String exceptionId;
    
    @XmlElement(name = "EXCEPTION_DETAIL")
    private String exceptionDetail;

	public String getRfoNO() {
		return RfoNO;
	}

	public void setRfoNO(String rfoNO) {
		RfoNO = rfoNO;
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
    
    

}
