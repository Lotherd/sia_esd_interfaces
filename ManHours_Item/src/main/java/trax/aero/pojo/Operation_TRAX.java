package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Operation_TRAX {
	
	@XmlElement(name = "Operation_number")
	private String Operation_number;
	
	@XmlElement(name = "TASK_CARD")
	private String TASK_CARD;

	public String getOperation_number() {
		return Operation_number;
	}

	public void setOperation_number(String operation_number) {
		Operation_number = operation_number;
	}

	public String getTASK_CARD() {
		return TASK_CARD;
	}

	public void setTASK_CARD(String tASK_CARD) {
		TASK_CARD = tASK_CARD;
	}
	
	

}
