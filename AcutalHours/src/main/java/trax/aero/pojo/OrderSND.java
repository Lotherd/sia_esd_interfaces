package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSND {
	
	@XmlElement(name = "Order_No")
    private String orderNo;

    @XmlElement(name = "Operation_Line")
    private String operationLine;

    @XmlElement(name = "Work_Center")
    private String workCenter;

    @XmlElement(name = "Plant")
    private String plant;

    @XmlElement(name = "Personnel_number")
    private String personnelNumber;

    @XmlElement(name = "Actual_work")
    private String actualWork;

    @XmlElement(name = "Unit_for_work")
    private String unitForWork;

    @XmlElement(name = "Activity_type")
    private String activityType;

    @XmlElement(name = "Posting_Date")
    private String postingDate;

    @XmlElement(name = "Final_Confirmation")
    private String finalConfirmation;

    @XmlElement(name = "Actual_Start_Date")
    private String actualStartDate;

    @XmlElement(name = "Actual_Start_Time")
    private String actualStartTime;

    @XmlElement(name = "Actual_Finish_Date")
    private String actualFinishDate;

    @XmlElement(name = "Actual_Finish_Time")
    private String actualFinishTime;

    @XmlElement(name = "General_Flag")
    private String generalFlag;

    @XmlElement(name = "WO_Actual_Transaction")
    private String woActualTransaction;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOperationLine() {
		return operationLine;
	}

	public void setOperationLine(String operationLine) {
		this.operationLine = operationLine;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getPersonnelNumber() {
		return personnelNumber;
	}

	public void setPersonnelNumber(String personnelNumber) {
		this.personnelNumber = personnelNumber;
	}

	public String getActualWork() {
		return actualWork;
	}

	public void setActualWork(String actualWork) {
		this.actualWork = actualWork;
	}

	public String getUnitForWork() {
		return unitForWork;
	}

	public void setUnitForWork(String unitForWork) {
		this.unitForWork = unitForWork;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}

	public String getFinalConfirmation() {
		return finalConfirmation;
	}

	public void setFinalConfirmation(String finalConfirmation) {
		this.finalConfirmation = finalConfirmation;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public String getActualFinishDate() {
		return actualFinishDate;
	}

	public void setActualFinishDate(String actualFinishDate) {
		this.actualFinishDate = actualFinishDate;
	}

	public String getActualFinishTime() {
		return actualFinishTime;
	}

	public void setActualFinishTime(String actualFinishTime) {
		this.actualFinishTime = actualFinishTime;
	}

	public String getGeneralFlag() {
		return generalFlag;
	}

	public void setGeneralFlag(String generalFlag) {
		this.generalFlag = generalFlag;
	}

	public String getWoActualTransaction() {
		return woActualTransaction;
	}

	public void setWoActualTransaction(String woActualTransaction) {
		this.woActualTransaction = woActualTransaction;
	}
	

}
