package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_SND_I30_4128_REQ", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT30_TRAX {
	
	@XmlElement(name = "LEGACY_BATCH")
	private String LEGACY_BATCH;
	
	@XmlElement(name = "PN")
	private String PN;
	
	@XmlElement(name = "EXCEPTION_ID")
	private String EXCEPTION_ID;
	
	@XmlElement(name = "EXCEPTION_DETAIL")
	private String EXCEPTION_DETAIL;

	public String getLEGACY_BATCH() {
		return LEGACY_BATCH;
	}

	public void setLEGACY_BATCH(String lEGACY_BATCH) {
		LEGACY_BATCH = lEGACY_BATCH;
	}

	public String getPN() {
		return PN;
	}

	public void setPN(String pN) {
		PN = pN;
	}

	public String getEXCEPTION_ID() {
		return EXCEPTION_ID;
	}

	public void setEXCEPTION_ID(String eXCEPTION_ID) {
		EXCEPTION_ID = eXCEPTION_ID;
	}

	public String getEXCEPTION_DETAIL() {
		return EXCEPTION_DETAIL;
	}

	public void setEXCEPTION_DETAIL(String eXCEPTION_DETAIL) {
		EXCEPTION_DETAIL = eXCEPTION_DETAIL;
	}
	
	

}
