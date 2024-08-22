package trax.aero.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MT_TRAX_SND_I9_29_REQ", namespace="http://singaporeair.com/mro/TRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class I9_I29_Request {
	
	
	@XmlElement(name = "RFO_NO")
	private  String RFO_NO;

	@XmlElement(name = "WO")
	private  String WO;
	
	@XmlElement(name = "PN")
	private  String PN;
	
	@XmlElement(name = "PN_SN")
	private  String PN_SN;

	public String getRFO_NO() {
		return RFO_NO;
	}

	public void setRFO_NO(String rFO_NO) {
		RFO_NO = rFO_NO;
	}

	public String getWO() {
		return WO;
	}

	public void setWO(String wO) {
		WO = wO;
	}

	public String getPN() {
		return PN;
	}

	public void setPN(String pN) {
		PN = pN;
	}

	public String getPN_SN() {
		return PN_SN;
	}

	public void setPN_SN(String pN_SN) {
		PN_SN = pN_SN;
	}
	
	
}
