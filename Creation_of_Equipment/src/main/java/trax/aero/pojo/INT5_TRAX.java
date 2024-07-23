package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_RCV_I5_ACK_4113", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT5_TRAX {
    
    @XmlElement(name = "MOD_NO")
    private String ModNO;
    
    @XmlElement(name = "WO")
    private String WO;
    
    @XmlElement(name = "EQUIPMENT")
    private String Equipment;
    
    @XmlElement(name = "EXCEPTION_ID")
    private String exceptionId;
    
    @XmlElement(name = "EXCEPTION_DETAIL")
    private String exceptionDetail;

    // Getters and setters
    public String getModNO() {
        return ModNO;
    }

    public void setModNO(String modNO) {
        ModNO = modNO;
    }

    public String getWO() {
        return WO;
    }

    public void setWO(String wO) {
        WO = wO;
    }

    public String getEquipment() {
		return Equipment;
	}

	public void setEquipment(String equipment) {
		Equipment = equipment;
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
