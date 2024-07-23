package trax.aero.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


public class EmployeeLicense {

	private String StaffNumber;
	
	private String Trade;

	private String AuthorizationNumber ;
	
	private String AuthorizationExpiryDate;
	
	private String AuthorizationStatus;
	
	//private ArrayList<String> ThirdPartyAirlinesLicense ;

	
	
	private ArrayList<ThirdParty> ThirdParty;
	


	public String getStaffNumber() {
		return StaffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		StaffNumber = staffNumber;
	}

	public String getTrade() {
		return Trade;
	}

	public void setTrade(String trade) {
		Trade = trade;
	}

	public String getAuthorizationNumber() {
		return AuthorizationNumber;
	}

	public void setAuthorizationNumber(String authorizationNumber) {
		AuthorizationNumber = authorizationNumber;
	}

	public String getAuthorizationExpiryDate() {
		return AuthorizationExpiryDate;
	}

	public void setAuthorizationExpiryDate(String authorizationExpiryDate) {
		AuthorizationExpiryDate = authorizationExpiryDate;
	}

	
	//public ArrayList<String> getThirdPartyAirlinesLicense() {
	//	return ThirdPartyAirlinesLicense;
	//}

	//public void setThirdPartyAirlinesLicense(ArrayList<String> thirdPartyAirlinesLicense) {
	//	ThirdPartyAirlinesLicense = thirdPartyAirlinesLicense;
	//}

	

	

	

	

	public String getAuthorizationStatus() {
		return AuthorizationStatus;
	}

	public void setAuthorizationStatus(String authorizationStatus) {
		AuthorizationStatus = authorizationStatus;
	}

	

	public ArrayList<ThirdParty> getThirdParty() {
		return ThirdParty;
	}

	public void setThirdParty(ArrayList<ThirdParty> thirdParty) {
		ThirdParty = thirdParty;
	}

	
	
	

	
	
}
