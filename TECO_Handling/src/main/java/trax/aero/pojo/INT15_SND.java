package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MT_TRAX_SND_I15_4125_REQ", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT15_SND {
	
	@XmlElement(name = "SAP_order_number")
	private String SAP_number;
	
	@XmlElement(name = "TRAX_WO_number")
	private String WO;
	
	@XmlElement(name = "WO_Close_Date")
	private String WO_Completion_date;
	
	@XmlElement(name = "WO_Close_time")
	private String WO_Completion_time;
	
	@XmlElement(name = "Task_card_status")
	private String status;
	
	@XmlElement(name = "Reason_for_TECO_reversal")
	private String Reason_teco;
	
	@XmlElement(name = "Notification_Number")
	private String Notification_number;
	
	@XmlElement(name = "TC_number")
	private String TC_number;
	
	@XmlElement(name = "Transaction")
	private String Transaction;
	
	@XmlElement(name = "Flag")
	private String Flag;

	public String getSAP_number() {
		return SAP_number;
	}

	public void setSAP_number(String sAP_number) {
		SAP_number = sAP_number;
	}

	public String getWO() {
		return WO;
	}

	public void setWO(String wO) {
		WO = wO;
	}

	public String getWO_Completion_date() {
		return WO_Completion_date;
	}

	public void setWO_Completion_date(String wO_Completion_date) {
		WO_Completion_date = wO_Completion_date;
	}

	public String getWO_Completion_time() {
		return WO_Completion_time;
	}

	public void setWO_Completion_time(String wO_Completion_time) {
		WO_Completion_time = wO_Completion_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason_teco() {
		return Reason_teco;
	}

	public void setReason_teco(String reason_teco) {
		Reason_teco = reason_teco;
	}

	public String getNotification_number() {
		return Notification_number;
	}

	public void setNotification_number(String notification_number) {
		Notification_number = notification_number;
	}

	public String getTC_number() {
		return TC_number;
	}

	public void setTC_number(String tC_number) {
		TC_number = tC_number;
	}

	public String getTransaction() {
		return Transaction;
	}

	public void setTransaction(String transaction) {
		Transaction = transaction;
	}

	public String getFlag() {
		return Flag;
	}

	public void setFlag(String flag) {
		Flag = flag;
	}
	
	

}
