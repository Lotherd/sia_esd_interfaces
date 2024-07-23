package trax.aero.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="Operations")
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationAudit {


	@XmlElement(name = "TC_Category")
    private String tcCategory;

    @XmlElement(name = "TC_Number")
    private String tcNumber;

    @XmlElement(name = "TC_Description")
    private String tcDescription;

    @XmlElement(name = "StandardManHours")
    private String standardManHours;

    @XmlElement(name = "OperationNumber")
    private String operationNumber;

    @XmlElement(name = "DeletionIndicator")
    private String deletionIndicator;

	public String getTcCategory() {
		return tcCategory;
	}

	public void setTcCategory(String tcCategory) {
		this.tcCategory = tcCategory;
	}

	public String getTcNumber() {
		return tcNumber;
	}

	public void setTcNumber(String tcNumber) {
		this.tcNumber = tcNumber;
	}

	public String getTcDescription() {
		return tcDescription;
	}

	public void setTcDescription(String tcDescription) {
		this.tcDescription = tcDescription;
	}

	public String getStandardManHours() {
		return standardManHours;
	}

	public void setStandardManHours(String standardManHours) {
		this.standardManHours = standardManHours;
	}

	public String getOperationNumber() {
		return operationNumber;
	}

	public void setOperationNumber(String operationNumber) {
		this.operationNumber = operationNumber;
	}

	public String getDeletionIndicator() {
		return deletionIndicator;
	}

	public void setDeletionIndicator(String deletionIndicator) {
		this.deletionIndicator = deletionIndicator;
	}
}
