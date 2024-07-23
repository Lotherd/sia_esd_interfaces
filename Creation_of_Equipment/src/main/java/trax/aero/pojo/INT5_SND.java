package trax.aero.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "MT_TRAX_SND_I5_4112", namespace = "http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class INT5_SND {
    
	@XmlElement(name = "TRAXLocation")
    private String LocationWO;
    
    @XmlElement(name = "TraxWO")
    private String TraxWo;
    
    @XmlElement(name = "TraxWODescription")
    private String TcDescription;
    
    @XmlElement(name = "MaterialPartNumber")
    private String Pn;
    
    @XmlElement(name = "MaterialSerialNumber")
    private String PnSn;
    
    @XmlElement(name = "WOScheduledStartDate")
    private String StartDate;
    
    @XmlElement(name = "WOScheduledEndDate")
    private String EndDate;
    
    @XmlElement(name = "CustomerID")
    private String CustomerID;
    
    @XmlElement(name = "TechCTL")
    private String TechControl;
    
    @XmlElement(name = "PFlag")
    private String Pflag;

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

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public String getTechControl() {
		return TechControl;
	}

	public void setTechControl(String techControl) {
		TechControl = techControl;
	}

	public String getPflag() {
		return Pflag;
	}

	public void setPflag(String pflag) {
		Pflag = pflag;
	}
}
