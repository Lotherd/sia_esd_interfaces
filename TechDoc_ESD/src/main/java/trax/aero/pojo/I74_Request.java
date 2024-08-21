package trax.aero.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MT_TRAX_SND_I74_4070_REQ", namespace="http://singaporeair.com/mro/TRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class I74_Request {
	
	
	@XmlElement(name = "OrderNumber")
	private  String OrderNumber;

	@XmlElement(name = "ReferenceDate")
	private  String ReferenceDate;
	
	@XmlElement(name = "ReferenceTime")
	private  String ReferenceTime;
	
	@XmlElement(name = "TRAXStatus")
	private  String TRAXStatus;
	
	@XmlElement(name = "TRAXStatusCategory")
	private  String TRAXStatusCategory;
	
	@XmlElement(name = "ReasonForTECO_reversal")
	private  String ReasonForTECO_reversal;
	
	public String getOrderNumber() {
		return OrderNumber;
	}
	public void setOrderNumber(String OrderNumber) {
		 this.OrderNumber = OrderNumber;
	}

	public String getReferenceDate() {
		return ReferenceDate;
	}
	public void setReferenceDate(String ReferenceDate) {
		 this.ReferenceDate = ReferenceDate;
	}
	
	public String getReferenceTime() {
		return ReferenceTime;
	}
	public void setReferenceTime(String ReferenceTime) {
		 this.ReferenceTime = ReferenceTime;
	}
	
	public String getTRAXStatus() {
		return TRAXStatus;
	}
	public void setTRAXStatus(String TRAXStatus) {
		 this.TRAXStatus = TRAXStatus;
	}
	
	public String getTRAXStatusCategory() {
		return TRAXStatusCategory;
	}
	public void setTRAXStatusCategory(String TRAXStatusCategory) {
		 this.TRAXStatusCategory = TRAXStatusCategory;
	}
	
	public String getReasonForTECO_reversal() {
		return ReasonForTECO_reversal;
	}
	public void setReasonForTECO_reversal(String ReasonForTECO_reversal) {
		 this.ReasonForTECO_reversal = ReasonForTECO_reversal;
	}
   
}
