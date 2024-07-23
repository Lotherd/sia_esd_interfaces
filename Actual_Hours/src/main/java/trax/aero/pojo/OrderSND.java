package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSND {
	
	@XmlElement(name = "Order_number")
    private String orderNo;

    @XmlElement(name = "Operation_number")
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

    @XmlElement(name = "Activity_type_for_confirmation")
    private String activityType;

    @XmlElement(name = "Posting_date")
    private String postingDate;

    @XmlElement(name = "Final_confirmation")
    private String finalConfirmation;
    
    @XmlElement(name = "Indicator_No_remaining_work")
    private String indicatorNRW;
    
    @XmlElement(name = "Accounting_indicator")
    private String indicatorAccount;

    @XmlElement(name = "Actual_start_date")
    private String actualStartDate;

    @XmlElement(name = "Actual_start_time")
    private String actualStartTime;

    @XmlElement(name = "Actual_finish_date")
    private String actualFinishDate;

    @XmlElement(name = "Actual_finish_time")
    private String actualFinishTime;

    @XmlElement(name = "General_flag")
    private String generalFlag;
    
    @XmlElement(name = "Confirmation_text")
    private String confirmationtext;
    
    @XmlElement(name = "Defect_text")
    private String defect_text;

    @XmlElement(name = "WO_ActualTransaction")
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

	public String getIndicatorNRW() {
		return indicatorNRW;
	}

	public void setIndicatorNRW(String indicatorNRW) {
		this.indicatorNRW = indicatorNRW;
	}

	public String getIndicatorAccount() {
		return indicatorAccount;
	}

	public void setIndicatorAccount(String indicatorAccount) {
		this.indicatorAccount = indicatorAccount;
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

	public String getConfirmationtext() {
		return confirmationtext;
	}

	public void setConfirmationtext(String confirmationtext) {
		this.confirmationtext = confirmationtext;
	}

	public String getDefect_text() {
		return defect_text;
	}

	public void setDefect_text(String defect_text) {
		this.defect_text = defect_text;
	}

	public String getWoActualTransaction() {
		return woActualTransaction;
	}

	public void setWoActualTransaction(String woActualTransaction) {
		this.woActualTransaction = woActualTransaction;
	}

}
