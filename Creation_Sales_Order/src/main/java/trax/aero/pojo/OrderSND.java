package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSND {
	
	@XmlElement(name = "TRAX_Location")
    private String LocationWO;
    
    @XmlElement(name = "TRAX_WO")
    private String TraxWo;
    
    @XmlElement(name = "TRAX_WO_Description")
    private String TcDescription;
    
    @XmlElement(name = "Material_Part_number")
    private String Pn;
    
    @XmlElement(name = "Material_Serial_number")
    private String PnSn;
    
    @XmlElement(name = "Customer_ID")
    private String CustomerID;
    
    @XmlElement(name = "Contract_ID")
    private String ContractID;
    
    @XmlElement(name = "Billing_Form")
    private String BillFormat;
    
    @XmlElement(name = "DIP_Profile")
    private String DIP_Profile;
    
    @XmlElement(name = "Notification_Type")
    private String Notification;

    @XmlElement(name = "Notification_Number")
    private String NotificationNO;
    
    @XmlElement(name = "Tech_CTL")
    private String TechControl;
    
    @XmlElement(name = "P_FLAG")
    private String PFlag;
    
    @XmlElement(name = "WBS_element")
    private String WBS;

	public String getLocationWO() {
		return LocationWO;
	}

	public void setLocationWO(String locationWO) {
		LocationWO = locationWO;
	}

	public String getTraxWo() {
		return TraxWo;
	}

	public void setTraxWo(String traxWo) {
		TraxWo = traxWo;
	}

	public String getTcDescription() {
		return TcDescription;
	}

	public void setTcDescription(String tcDescription) {
		TcDescription = tcDescription;
	}

	public String getPn() {
		return Pn;
	}

	public void setPn(String pn) {
		Pn = pn;
	}

	public String getPnSn() {
		return PnSn;
	}

	public void setPnSn(String pnSn) {
		PnSn = pnSn;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public String getContractID() {
		return ContractID;
	}

	public void setContractID(String contractID) {
		ContractID = contractID;
	}

	public String getBillFormat() {
		return BillFormat;
	}

	public void setBillFormat(String billFormat) {
		BillFormat = billFormat;
	}

	public String getDIP_Profile() {
		return DIP_Profile;
	}

	public void setDIP_Profile(String dIP_Profile) {
		DIP_Profile = dIP_Profile;
	}

	public String getNotification() {
		return Notification;
	}

	public void setNotification(String notification) {
		Notification = notification;
	}

	public String getNotificationNO() {
		return NotificationNO;
	}

	public void setNotificationNO(String notificationNO) {
		NotificationNO = notificationNO;
	}

	public String getTechControl() {
		return TechControl;
	}

	public void setTechControl(String techControl) {
		TechControl = techControl;
	}

	public String getPFlag() {
		return PFlag;
	}

	public void setPFlag(String pFlag) {
		PFlag = pFlag;
	}

	public String getWBS() {
		return WBS;
	}

	public void setWBS(String wBS) {
		WBS = wBS;
	}
    
    
}
