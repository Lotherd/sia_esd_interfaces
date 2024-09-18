package trax.aero.pojo;

import java.io.Serializable;

public class PrintQueueJob implements Serializable {

	private static final long serialVersionUID = 1L;

	private int jobId;
	private String datasourceJNDI;
	private byte[] objectParameters;
	private byte[] printParameters;
	private String printWindow;
	private String selectedPrint;
	private String printTitle;
	private String comparisonOperator;
	private String user;
	private String schedulePrint;
	private String emailTo;
	private String notPrint;
	private String isWPP;
	private String printFilter;
	private boolean uploadPdf;

	public PrintQueueJob() {

	}

	public PrintQueueJob(String datasourceJNDI) {
		this.datasourceJNDI = datasourceJNDI;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getDatasourceJNDI() {
		return datasourceJNDI;
	}

	public void setDatasourceJNDI(String datasourceJNDI) {
		this.datasourceJNDI = datasourceJNDI;
	}

	public byte[] getObjectParameters() {
		return objectParameters;
	}

	public void setObjectParameters(byte[] objectParameters) {
		this.objectParameters = objectParameters;
	}

	

	public byte[] getPrintParameters() {
		return printParameters;
	}

	public void setPrintParameters(byte[] printParameters) {
		this.printParameters = printParameters;
	}

	public String getPrintWindow() {
		return printWindow;
	}

	public void setPrintWindow(String printWindow) {
		this.printWindow = printWindow;
	}

	public String getSelectedPrint() {
		return selectedPrint;
	}

	public void setSelectedPrint(String selectedPrint) {
		this.selectedPrint = selectedPrint;
	}

	public String getPrintTitle() {
		return printTitle;
	}

	public void setPrintTitle(String printTitle) {
		this.printTitle = printTitle;
	}

	public String getComparisonOperator() {
		return comparisonOperator;
	}

	public void setComparisonOperator(String comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSchedulePrint() {
		return schedulePrint;
	}

	public void setSchedulePrint(String schedulePrint) {
		this.schedulePrint = schedulePrint;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getNotPrint() {
		return notPrint;
	}

	public void setNotPrint(String notPrint) {
		this.notPrint = notPrint;
	}

	public String getIsWPP() {
		return isWPP;
	}

	public void setIsWPP(String isWPP) {
		this.isWPP = isWPP;
	}

	public boolean isUploadPdf() {
		return uploadPdf;
	}

	public void setUploadPdf(boolean uploadPdf) {
		this.uploadPdf = uploadPdf;
	}

	public String getPrintFilter() {
		return printFilter;
	}

	public void setPrintFilter(String printFilter) {
		this.printFilter = printFilter;
	}

	
	
}
