package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Operation_SND {
	
	@XmlElement(name = "OPS_NO")
	private String OPS_NO;
	
	@XmlElement(name = "TASK_CARD")
	private String TASK_CARD;
	
	@XmlElement(name = "TASK_CARD_CATEGORY")
	private String TASK_CARD_CATEGORY;
	
	@XmlElement(name = "TASK_CARD_DESCRIPTION")
	private String TASK_CARD_DESCRIPTION;
	
	@XmlElement(name = "WORK_ACCOMPLISHED")
	private String WORK_ACCOMPLISHED;
	
	@XmlElement(name = "INV_HRS")
	private String INV_HRS;

	public String getOPS_NO() {
		return OPS_NO;
	}

	public void setOPS_NO(String oPS_NO) {
		OPS_NO = oPS_NO;
	}

	public String getTASK_CARD() {
		return TASK_CARD;
	}

	public void setTASK_CARD(String tASK_CARD) {
		TASK_CARD = tASK_CARD;
	}

	public String getTASK_CARD_CATEGORY() {
		return TASK_CARD_CATEGORY;
	}

	public void setTASK_CARD_CATEGORY(String tASK_CARD_CATEGORY) {
		TASK_CARD_CATEGORY = tASK_CARD_CATEGORY;
	}

	public String getTASK_CARD_DESCRIPTION() {
		return TASK_CARD_DESCRIPTION;
	}

	public void setTASK_CARD_DESCRIPTION(String tASK_CARD_DESCRIPTION) {
		TASK_CARD_DESCRIPTION = tASK_CARD_DESCRIPTION;
	}

	public String getWORK_ACCOMPLISHED() {
		return WORK_ACCOMPLISHED;
	}

	public void setWORK_ACCOMPLISHED(String wORK_ACCOMPLISHED) {
		WORK_ACCOMPLISHED = wORK_ACCOMPLISHED;
	}

	public String getINV_HRS() {
		return INV_HRS;
	}

	public void setINV_HRS(String iNV_HRS) {
		INV_HRS = iNV_HRS;
	}
	
	

}
