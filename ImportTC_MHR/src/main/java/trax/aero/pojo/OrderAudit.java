package trax.aero.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="Order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderAudit {
	
	@XmlElement(name = "TRAX_WO")
    private String traxWO;

    @XmlElement(name = "SAP_OrderNumber")
    private String sapOrderNumber;

    @XmlElement(name = "Operation")
    private List<OperationSND> operations;

	public String getTraxWO() {
		return traxWO;
	}

	public void setTraxWO(String traxWO) {
		this.traxWO = traxWO;
	}

	public String getSapOrderNumber() {
		return sapOrderNumber;
	}

	public void setSapOrderNumber(String sapOrderNumber) {
		this.sapOrderNumber = sapOrderNumber;
	}

	public List<OperationSND> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationSND> operations) {
		this.operations = operations;
	}

}
