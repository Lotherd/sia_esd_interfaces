package trax.aero.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MT_TRAX_RCV_I74_4070_RES", namespace="http://singaporeair.com/mro/TRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class I74_Response {
	
	
	@XmlElement(name = "OrderNumber")
	private String OrderNumber;
	
	@XmlElement(name = "Remarks")
	private String Remarks;
	
	@XmlElement(name = "ErrorCode")
	private String ErrorCode;
	
	public String getOrderNumber() {
		return OrderNumber;
	}

	public void setOrderNumber(String OrderNumber) {
		this.OrderNumber = OrderNumber;
	}
	
	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String Remarks) {
		this.Remarks = Remarks;
	}

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

   
   
}
