package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PN_INVENTORY_HISTORY database table.
 * 
 */
@Entity
@Table(name="PN_INVENTORY_HISTORY")
@NamedQuery(name="PnInventoryHistory.findAll", query="SELECT p FROM PnInventoryHistory p")
public class PnInventoryHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PnInventoryHistoryPK id;

	private String ac;

	@Column(name="ACCOUNTING_DOCUMENT")
	private String accountingDocument;

	@Temporal(TemporalType.DATE)
	@Column(name="ACCOUNTING_DOCUMENT_DATE")
	private Date accountingDocumentDate;

	@Column(name="\"ACTION\"")
	private String action;

	@Column(name="ADJUSTMENT_CODE")
	private String adjustmentCode;

	@Column(name="ASSIGN_TEAM")
	private String assignTeam;

	private String awb;

	private String bin;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	private BigDecimal chapter;

	@Column(name="COMPONENT_STATUS")
	private String componentStatus;

	private String condition;

	private String control;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_BY_COSTING_ADJUST")
	private String createdByCostingAdjust;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	private String currency;

	@Column(name="CURRENCY_EXCHANGE_RATE")
	private BigDecimal currencyExchangeRate;

	@Temporal(TemporalType.DATE)
	@Column(name="CUST_REQ_ACT_DATE")
	private Date custReqActDate;

	@Temporal(TemporalType.DATE)
	@Column(name="CUST_REQ_DATE")
	private Date custReqDate;

	@Column(name="CUSTOM_DUTY_CODE")
	private String customDutyCode;

	@Column(name="CUSTOM_DUTY_DOCUMENT")
	private String customDutyDocument;

	private String defect;

	@Column(name="DEFECT_ITEM")
	private BigDecimal defectItem;

	@Column(name="DEFECT_TYPE")
	private String defectType;

	@Temporal(TemporalType.DATE)
	@Column(name="DELIVERY_DATE")
	private Date deliveryDate;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	@Column(name="EMPLOYEE_COST_CENTER")
	private String employeeCostCenter;

	@Column(name="FOLLOWUP_REMARKS")
	private String followupRemarks;

	@Column(name="FORM_1_NOTES")
	private BigDecimal form1Notes;

	@Column(name="FORM_NO")
	private BigDecimal formNo;

	@Lob
	@Column(name="GENERAL_PDF")
	private byte[] generalPdf;

	private String gl;

	@Column(name="GL_COMPANY")
	private String glCompany;

	@Column(name="GL_COST_CENTER")
	private String glCostCenter;

	@Column(name="GL_EXPENDITURE")
	private String glExpenditure;

	@Column(name="GOODS_RCVD_BATCH")
	private BigDecimal goodsRcvdBatch;

	private String ie4n;

	@Column(name="IE4N_FORCE_INSTALL")
	private String ie4nForceInstall;

	@Column(name="IE4N_MESSAGE_TEXT")
	private String ie4nMessageText;

	@Temporal(TemporalType.DATE)
	@Column(name="IFACE_BAXTER_DINST_XFER_DATE")
	private Date ifaceBaxterDinstXferDate;

	@Temporal(TemporalType.DATE)
	@Column(name="IFACE_BAXTER_DISSUE_XFER_DATE")
	private Date ifaceBaxterDissueXferDate;

	@Temporal(TemporalType.DATE)
	@Column(name="IFACE_BAXTER_DREMOVE_XFER_DATE")
	private Date ifaceBaxterDremoveXferDate;

	@Temporal(TemporalType.DATE)
	@Column(name="IFACE_BAXTER_STMTD_XFER_DATE")
	private Date ifaceBaxterStmtdXferDate;

	@Column(name="INTERFACE_TECO_FLAG")
	private String interfaceTecoFlag;

	@Column(name="INTERFACE_TRANSFER_BY")
	private String interfaceTransferBy;

	@Temporal(TemporalType.DATE)
	@Column(name="INTERFACE_TRANSFER_DATE")
	private Date interfaceTransferDate;

	@Column(name="INTERFACE_TRANSFER_FLAG")
	private String interfaceTransferFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="INTERFACE_TRNSFR_FINANCE_BRZL")
	private Date interfaceTrnsfrFinanceBrzl;

	@Column(name="INTERNAL_EXTERNAL")
	private String internalExternal;

	private String invoice;

	@Column(name="ISSUE_TO_EMPLOYEE")
	private String issueToEmployee;

	@Column(name="ISSUED_TO")
	private String issuedTo;

	@Column(name="ISSUED_TO_EMPLOYEE")
	private String issuedToEmployee;

	@Column(name="LEAD_TIME")
	private BigDecimal leadTime;

	@Column(name="LEGACY_BATCH")
	private BigDecimal legacyBatch;

	private String location;

	private String locked;

	@Column(name="MADE_AS_CCS")
	private String madeAsCcs;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="NHA_PN")
	private String nhaPn;

	@Column(name="NHA_SN")
	private String nhaSn;

	private String nla;

	@Column(name="NLA_POSITION")
	private String nlaPosition;

	private BigDecimal notes;

	@Column(name="ORDER_LINE")
	private BigDecimal orderLine;

	@Column(name="ORDER_NO")
	private BigDecimal orderNo;

	@Column(name="ORDER_TYPE")
	private String orderType;

	private String owner;

	@Column(name="PASS_FINANCIAL_CLOSING")
	private String passFinancialClosing;

	private String pn;

	@Column(name="PN_CATEGORY")
	private String pnCategory;

	@Column(name="\"POSITION\"")
	private String position;

	@Column(name="PRE_RECEIPT")
	private String preReceipt;

	private BigDecimal qty;

	@Column(name="QTY_RETURN_STOCK")
	private BigDecimal qtyReturnStock;

	@Column(name="QTY_USED")
	private BigDecimal qtyUsed;

	@Column(name="REASON_CATEGORY")
	private String reasonCategory;

	private String reference;

	@Column(name="REMOVAL_INSTALLED_DESCRIPTION")
	private String removalInstalledDescription;

	@Column(name="REMOVAL_REASON")
	private String removalReason;

	@Column(name="REMOVE_AS_SERVICEABLE")
	private String removeAsServiceable;

	@Temporal(TemporalType.DATE)
	@Column(name="REMOVE_INSTALLED_DATE")
	private Date removeInstalledDate;

	@Column(name="REMOVE_INSTALLED_POSITION")
	private String removeInstalledPosition;

	@Column(name="REOPEN_ORDER")
	private String reopenOrder;

	@Column(name="REPAIR_SHOPS")
	private String repairShops;

	@Column(name="REPAIR_STATION_VENDOR")
	private String repairStationVendor;

	@Column(name="REQ_ACKNOWLEDGEMENT")
	private String reqAcknowledgement;

	@Column(name="REQ_ACKNOWLEDGEMENT_BY")
	private String reqAcknowledgementBy;

	@Temporal(TemporalType.DATE)
	@Column(name="REQ_ACKNOWLEDGEMENT_DATE")
	private Date reqAcknowledgementDate;

	private BigDecimal requisition;

	@Column(name="REQUISITION_LINE")
	private BigDecimal requisitionLine;

	@Column(name="RFO_NO")
	private BigDecimal rfoNo;

	@Column(name="RI_INTERFACE_FLAG")
	private String riInterfaceFlag;

	@Column(name="RI_PN_CONTROL_RESET")
	private String riPnControlReset;

	@Column(name="RMV_INS_PN")
	private String rmvInsPn;

	@Column(name="RMV_INS_SN")
	private String rmvInsSn;

	@Column(name="RTV_REOPEN_INV_INTERFACE_FLAG")
	private String rtvReopenInvInterfaceFlag;

	@Column(name="SCHEDULE_CATEGORY")
	private String scheduleCategory;

	@Column(name="SCRAP_CODE")
	private String scrapCode;

	@Column(name="SECONDARY_COST")
	private BigDecimal secondaryCost;

	@Column(name="SECONDARY_CURRENCY_EXCHANGE")
	private BigDecimal secondaryCurrencyExchange;

	@Column(name="\"SECTION\"")
	private BigDecimal section;

	@Column(name="SHIP_VIA")
	private String shipVia;

	@Column(name="SHIP_VIA_ACCOUNT")
	private String shipViaAccount;

	@Column(name="SHIP_VIA_REMARKS")
	private String shipViaRemarks;

	@Temporal(TemporalType.DATE)
	@Column(name="SHIPPING_DATE")
	private Date shippingDate;

	@Column(name="SHIPPING_REMARKS")
	private String shippingRemarks;

	@Lob
	@Column(name="SIGNED_PDF")
	private byte[] signedPdf;

	private String sn;

	@Column(name="STATE_OF_PART")
	private String stateOfPart;

	private String status;

	@Column(name="SVO_NO")
	private String svoNo;

	@Column(name="SVO_SENT")
	private String svoSent;

	@Column(name="TAG_BY")
	private String tagBy;

	@Temporal(TemporalType.DATE)
	@Column(name="TAG_DATE")
	private Date tagDate;

	@Column(name="TAG_NO")
	private String tagNo;

	@Column(name="TASK_CARD")
	private String taskCard;

	@Column(name="TASK_CARD_PN")
	private String taskCardPn;

	@Column(name="TASK_CARD_SN")
	private String taskCardSn;

	@Column(name="TAX_INCENTIVE")
	private String taxIncentive;

	@Column(name="TAX_INCENTIVE_NBR")
	private String taxIncentiveNbr;

	@Temporal(TemporalType.DATE)
	@Column(name="TECHNICAL_RECORD_DATE")
	private Date technicalRecordDate;

	@Column(name="TO_BIN")
	private String toBin;

	@Column(name="TO_LOCATION")
	private String toLocation;

	@Column(name="TOOL_CALIBRATION_NO")
	private String toolCalibrationNo;

	@Column(name="\"TRANSACTION\"")
	private String transaction;

	@Temporal(TemporalType.DATE)
	@Column(name="TRANSACTION_DATE")
	private Date transactionDate;

	@Column(name="TRANSACTION_HOUR")
	private BigDecimal transactionHour;

	@Column(name="TRANSACTION_ITEM")
	private BigDecimal transactionItem;

	@Column(name="TRANSACTION_MINUTE")
	private BigDecimal transactionMinute;

	@Column(name="TRANSACTION_TYPE")
	private String transactionType;

	@Column(name="TRANSACTION_TYPE_CONTROL")
	private String transactionTypeControl;

	@Column(name="UNIT_COST")
	private BigDecimal unitCost;

	@Column(name="UNIT_SELL")
	private BigDecimal unitSell;

	@Column(name="UNIT_SELL_OVERRIDE")
	private String unitSellOverride;

	@Column(name="US_NUMBER")
	private String usNumber;

	private String vendor;

	@Column(name="VENDOR_LOT")
	private String vendorLot;

	private BigDecimal wo;

	public PnInventoryHistory() {
	}

	public PnInventoryHistoryPK getId() {
		return this.id;
	}

	public void setId(PnInventoryHistoryPK id) {
		this.id = id;
	}

	public String getAc() {
		return this.ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	public String getAccountingDocument() {
		return this.accountingDocument;
	}

	public void setAccountingDocument(String accountingDocument) {
		this.accountingDocument = accountingDocument;
	}

	public Date getAccountingDocumentDate() {
		return this.accountingDocumentDate;
	}

	public void setAccountingDocumentDate(Date accountingDocumentDate) {
		this.accountingDocumentDate = accountingDocumentDate;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAdjustmentCode() {
		return this.adjustmentCode;
	}

	public void setAdjustmentCode(String adjustmentCode) {
		this.adjustmentCode = adjustmentCode;
	}

	public String getAssignTeam() {
		return this.assignTeam;
	}

	public void setAssignTeam(String assignTeam) {
		this.assignTeam = assignTeam;
	}

	public String getAwb() {
		return this.awb;
	}

	public void setAwb(String awb) {
		this.awb = awb;
	}

	public String getBin() {
		return this.bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public BigDecimal getChapter() {
		return this.chapter;
	}

	public void setChapter(BigDecimal chapter) {
		this.chapter = chapter;
	}

	public String getComponentStatus() {
		return this.componentStatus;
	}

	public void setComponentStatus(String componentStatus) {
		this.componentStatus = componentStatus;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getControl() {
		return this.control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByCostingAdjust() {
		return this.createdByCostingAdjust;
	}

	public void setCreatedByCostingAdjust(String createdByCostingAdjust) {
		this.createdByCostingAdjust = createdByCostingAdjust;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getCurrencyExchangeRate() {
		return this.currencyExchangeRate;
	}

	public void setCurrencyExchangeRate(BigDecimal currencyExchangeRate) {
		this.currencyExchangeRate = currencyExchangeRate;
	}

	public Date getCustReqActDate() {
		return this.custReqActDate;
	}

	public void setCustReqActDate(Date custReqActDate) {
		this.custReqActDate = custReqActDate;
	}

	public Date getCustReqDate() {
		return this.custReqDate;
	}

	public void setCustReqDate(Date custReqDate) {
		this.custReqDate = custReqDate;
	}

	public String getCustomDutyCode() {
		return this.customDutyCode;
	}

	public void setCustomDutyCode(String customDutyCode) {
		this.customDutyCode = customDutyCode;
	}

	public String getCustomDutyDocument() {
		return this.customDutyDocument;
	}

	public void setCustomDutyDocument(String customDutyDocument) {
		this.customDutyDocument = customDutyDocument;
	}

	public String getDefect() {
		return this.defect;
	}

	public void setDefect(String defect) {
		this.defect = defect;
	}

	public BigDecimal getDefectItem() {
		return this.defectItem;
	}

	public void setDefectItem(BigDecimal defectItem) {
		this.defectItem = defectItem;
	}

	public String getDefectType() {
		return this.defectType;
	}

	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}

	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getEmployeeCostCenter() {
		return this.employeeCostCenter;
	}

	public void setEmployeeCostCenter(String employeeCostCenter) {
		this.employeeCostCenter = employeeCostCenter;
	}

	public String getFollowupRemarks() {
		return this.followupRemarks;
	}

	public void setFollowupRemarks(String followupRemarks) {
		this.followupRemarks = followupRemarks;
	}

	public BigDecimal getForm1Notes() {
		return this.form1Notes;
	}

	public void setForm1Notes(BigDecimal form1Notes) {
		this.form1Notes = form1Notes;
	}

	public BigDecimal getFormNo() {
		return this.formNo;
	}

	public void setFormNo(BigDecimal formNo) {
		this.formNo = formNo;
	}

	public byte[] getGeneralPdf() {
		return this.generalPdf;
	}

	public void setGeneralPdf(byte[] generalPdf) {
		this.generalPdf = generalPdf;
	}

	public String getGl() {
		return this.gl;
	}

	public void setGl(String gl) {
		this.gl = gl;
	}

	public String getGlCompany() {
		return this.glCompany;
	}

	public void setGlCompany(String glCompany) {
		this.glCompany = glCompany;
	}

	public String getGlCostCenter() {
		return this.glCostCenter;
	}

	public void setGlCostCenter(String glCostCenter) {
		this.glCostCenter = glCostCenter;
	}

	public String getGlExpenditure() {
		return this.glExpenditure;
	}

	public void setGlExpenditure(String glExpenditure) {
		this.glExpenditure = glExpenditure;
	}

	public BigDecimal getGoodsRcvdBatch() {
		return this.goodsRcvdBatch;
	}

	public void setGoodsRcvdBatch(BigDecimal goodsRcvdBatch) {
		this.goodsRcvdBatch = goodsRcvdBatch;
	}

	public String getIe4n() {
		return this.ie4n;
	}

	public void setIe4n(String ie4n) {
		this.ie4n = ie4n;
	}

	public String getIe4nForceInstall() {
		return this.ie4nForceInstall;
	}

	public void setIe4nForceInstall(String ie4nForceInstall) {
		this.ie4nForceInstall = ie4nForceInstall;
	}

	public String getIe4nMessageText() {
		return this.ie4nMessageText;
	}

	public void setIe4nMessageText(String ie4nMessageText) {
		this.ie4nMessageText = ie4nMessageText;
	}

	public Date getIfaceBaxterDinstXferDate() {
		return this.ifaceBaxterDinstXferDate;
	}

	public void setIfaceBaxterDinstXferDate(Date ifaceBaxterDinstXferDate) {
		this.ifaceBaxterDinstXferDate = ifaceBaxterDinstXferDate;
	}

	public Date getIfaceBaxterDissueXferDate() {
		return this.ifaceBaxterDissueXferDate;
	}

	public void setIfaceBaxterDissueXferDate(Date ifaceBaxterDissueXferDate) {
		this.ifaceBaxterDissueXferDate = ifaceBaxterDissueXferDate;
	}

	public Date getIfaceBaxterDremoveXferDate() {
		return this.ifaceBaxterDremoveXferDate;
	}

	public void setIfaceBaxterDremoveXferDate(Date ifaceBaxterDremoveXferDate) {
		this.ifaceBaxterDremoveXferDate = ifaceBaxterDremoveXferDate;
	}

	public Date getIfaceBaxterStmtdXferDate() {
		return this.ifaceBaxterStmtdXferDate;
	}

	public void setIfaceBaxterStmtdXferDate(Date ifaceBaxterStmtdXferDate) {
		this.ifaceBaxterStmtdXferDate = ifaceBaxterStmtdXferDate;
	}

	public String getInterfaceTecoFlag() {
		return this.interfaceTecoFlag;
	}

	public void setInterfaceTecoFlag(String interfaceTecoFlag) {
		this.interfaceTecoFlag = interfaceTecoFlag;
	}

	public String getInterfaceTransferBy() {
		return this.interfaceTransferBy;
	}

	public void setInterfaceTransferBy(String interfaceTransferBy) {
		this.interfaceTransferBy = interfaceTransferBy;
	}

	public Date getInterfaceTransferDate() {
		return this.interfaceTransferDate;
	}

	public void setInterfaceTransferDate(Date interfaceTransferDate) {
		this.interfaceTransferDate = interfaceTransferDate;
	}

	public String getInterfaceTransferFlag() {
		return this.interfaceTransferFlag;
	}

	public void setInterfaceTransferFlag(String interfaceTransferFlag) {
		this.interfaceTransferFlag = interfaceTransferFlag;
	}

	public Date getInterfaceTrnsfrFinanceBrzl() {
		return this.interfaceTrnsfrFinanceBrzl;
	}

	public void setInterfaceTrnsfrFinanceBrzl(Date interfaceTrnsfrFinanceBrzl) {
		this.interfaceTrnsfrFinanceBrzl = interfaceTrnsfrFinanceBrzl;
	}

	public String getInternalExternal() {
		return this.internalExternal;
	}

	public void setInternalExternal(String internalExternal) {
		this.internalExternal = internalExternal;
	}

	public String getInvoice() {
		return this.invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getIssueToEmployee() {
		return this.issueToEmployee;
	}

	public void setIssueToEmployee(String issueToEmployee) {
		this.issueToEmployee = issueToEmployee;
	}

	public String getIssuedTo() {
		return this.issuedTo;
	}

	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}

	public String getIssuedToEmployee() {
		return this.issuedToEmployee;
	}

	public void setIssuedToEmployee(String issuedToEmployee) {
		this.issuedToEmployee = issuedToEmployee;
	}

	public BigDecimal getLeadTime() {
		return this.leadTime;
	}

	public void setLeadTime(BigDecimal leadTime) {
		this.leadTime = leadTime;
	}

	public BigDecimal getLegacyBatch() {
		return this.legacyBatch;
	}

	public void setLegacyBatch(BigDecimal legacyBatch) {
		this.legacyBatch = legacyBatch;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocked() {
		return this.locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getMadeAsCcs() {
		return this.madeAsCcs;
	}

	public void setMadeAsCcs(String madeAsCcs) {
		this.madeAsCcs = madeAsCcs;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getNhaPn() {
		return this.nhaPn;
	}

	public void setNhaPn(String nhaPn) {
		this.nhaPn = nhaPn;
	}

	public String getNhaSn() {
		return this.nhaSn;
	}

	public void setNhaSn(String nhaSn) {
		this.nhaSn = nhaSn;
	}

	public String getNla() {
		return this.nla;
	}

	public void setNla(String nla) {
		this.nla = nla;
	}

	public String getNlaPosition() {
		return this.nlaPosition;
	}

	public void setNlaPosition(String nlaPosition) {
		this.nlaPosition = nlaPosition;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public BigDecimal getOrderLine() {
		return this.orderLine;
	}

	public void setOrderLine(BigDecimal orderLine) {
		this.orderLine = orderLine;
	}

	public BigDecimal getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPassFinancialClosing() {
		return this.passFinancialClosing;
	}

	public void setPassFinancialClosing(String passFinancialClosing) {
		this.passFinancialClosing = passFinancialClosing;
	}

	public String getPn() {
		return this.pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getPnCategory() {
		return this.pnCategory;
	}

	public void setPnCategory(String pnCategory) {
		this.pnCategory = pnCategory;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPreReceipt() {
		return this.preReceipt;
	}

	public void setPreReceipt(String preReceipt) {
		this.preReceipt = preReceipt;
	}

	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getQtyReturnStock() {
		return this.qtyReturnStock;
	}

	public void setQtyReturnStock(BigDecimal qtyReturnStock) {
		this.qtyReturnStock = qtyReturnStock;
	}

	public BigDecimal getQtyUsed() {
		return this.qtyUsed;
	}

	public void setQtyUsed(BigDecimal qtyUsed) {
		this.qtyUsed = qtyUsed;
	}

	public String getReasonCategory() {
		return this.reasonCategory;
	}

	public void setReasonCategory(String reasonCategory) {
		this.reasonCategory = reasonCategory;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getRemovalInstalledDescription() {
		return this.removalInstalledDescription;
	}

	public void setRemovalInstalledDescription(String removalInstalledDescription) {
		this.removalInstalledDescription = removalInstalledDescription;
	}

	public String getRemovalReason() {
		return this.removalReason;
	}

	public void setRemovalReason(String removalReason) {
		this.removalReason = removalReason;
	}

	public String getRemoveAsServiceable() {
		return this.removeAsServiceable;
	}

	public void setRemoveAsServiceable(String removeAsServiceable) {
		this.removeAsServiceable = removeAsServiceable;
	}

	public Date getRemoveInstalledDate() {
		return this.removeInstalledDate;
	}

	public void setRemoveInstalledDate(Date removeInstalledDate) {
		this.removeInstalledDate = removeInstalledDate;
	}

	public String getRemoveInstalledPosition() {
		return this.removeInstalledPosition;
	}

	public void setRemoveInstalledPosition(String removeInstalledPosition) {
		this.removeInstalledPosition = removeInstalledPosition;
	}

	public String getReopenOrder() {
		return this.reopenOrder;
	}

	public void setReopenOrder(String reopenOrder) {
		this.reopenOrder = reopenOrder;
	}

	public String getRepairShops() {
		return this.repairShops;
	}

	public void setRepairShops(String repairShops) {
		this.repairShops = repairShops;
	}

	public String getRepairStationVendor() {
		return this.repairStationVendor;
	}

	public void setRepairStationVendor(String repairStationVendor) {
		this.repairStationVendor = repairStationVendor;
	}

	public String getReqAcknowledgement() {
		return this.reqAcknowledgement;
	}

	public void setReqAcknowledgement(String reqAcknowledgement) {
		this.reqAcknowledgement = reqAcknowledgement;
	}

	public String getReqAcknowledgementBy() {
		return this.reqAcknowledgementBy;
	}

	public void setReqAcknowledgementBy(String reqAcknowledgementBy) {
		this.reqAcknowledgementBy = reqAcknowledgementBy;
	}

	public Date getReqAcknowledgementDate() {
		return this.reqAcknowledgementDate;
	}

	public void setReqAcknowledgementDate(Date reqAcknowledgementDate) {
		this.reqAcknowledgementDate = reqAcknowledgementDate;
	}

	public BigDecimal getRequisition() {
		return this.requisition;
	}

	public void setRequisition(BigDecimal requisition) {
		this.requisition = requisition;
	}

	public BigDecimal getRequisitionLine() {
		return this.requisitionLine;
	}

	public void setRequisitionLine(BigDecimal requisitionLine) {
		this.requisitionLine = requisitionLine;
	}

	public BigDecimal getRfoNo() {
		return this.rfoNo;
	}

	public void setRfoNo(BigDecimal rfoNo) {
		this.rfoNo = rfoNo;
	}

	public String getRiInterfaceFlag() {
		return this.riInterfaceFlag;
	}

	public void setRiInterfaceFlag(String riInterfaceFlag) {
		this.riInterfaceFlag = riInterfaceFlag;
	}

	public String getRiPnControlReset() {
		return this.riPnControlReset;
	}

	public void setRiPnControlReset(String riPnControlReset) {
		this.riPnControlReset = riPnControlReset;
	}

	public String getRmvInsPn() {
		return this.rmvInsPn;
	}

	public void setRmvInsPn(String rmvInsPn) {
		this.rmvInsPn = rmvInsPn;
	}

	public String getRmvInsSn() {
		return this.rmvInsSn;
	}

	public void setRmvInsSn(String rmvInsSn) {
		this.rmvInsSn = rmvInsSn;
	}

	public String getRtvReopenInvInterfaceFlag() {
		return this.rtvReopenInvInterfaceFlag;
	}

	public void setRtvReopenInvInterfaceFlag(String rtvReopenInvInterfaceFlag) {
		this.rtvReopenInvInterfaceFlag = rtvReopenInvInterfaceFlag;
	}

	public String getScheduleCategory() {
		return this.scheduleCategory;
	}

	public void setScheduleCategory(String scheduleCategory) {
		this.scheduleCategory = scheduleCategory;
	}

	public String getScrapCode() {
		return this.scrapCode;
	}

	public void setScrapCode(String scrapCode) {
		this.scrapCode = scrapCode;
	}

	public BigDecimal getSecondaryCost() {
		return this.secondaryCost;
	}

	public void setSecondaryCost(BigDecimal secondaryCost) {
		this.secondaryCost = secondaryCost;
	}

	public BigDecimal getSecondaryCurrencyExchange() {
		return this.secondaryCurrencyExchange;
	}

	public void setSecondaryCurrencyExchange(BigDecimal secondaryCurrencyExchange) {
		this.secondaryCurrencyExchange = secondaryCurrencyExchange;
	}

	public BigDecimal getSection() {
		return this.section;
	}

	public void setSection(BigDecimal section) {
		this.section = section;
	}

	public String getShipVia() {
		return this.shipVia;
	}

	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}

	public String getShipViaAccount() {
		return this.shipViaAccount;
	}

	public void setShipViaAccount(String shipViaAccount) {
		this.shipViaAccount = shipViaAccount;
	}

	public String getShipViaRemarks() {
		return this.shipViaRemarks;
	}

	public void setShipViaRemarks(String shipViaRemarks) {
		this.shipViaRemarks = shipViaRemarks;
	}

	public Date getShippingDate() {
		return this.shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getShippingRemarks() {
		return this.shippingRemarks;
	}

	public void setShippingRemarks(String shippingRemarks) {
		this.shippingRemarks = shippingRemarks;
	}

	public byte[] getSignedPdf() {
		return this.signedPdf;
	}

	public void setSignedPdf(byte[] signedPdf) {
		this.signedPdf = signedPdf;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getStateOfPart() {
		return this.stateOfPart;
	}

	public void setStateOfPart(String stateOfPart) {
		this.stateOfPart = stateOfPart;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSvoNo() {
		return this.svoNo;
	}

	public void setSvoNo(String svoNo) {
		this.svoNo = svoNo;
	}

	public String getSvoSent() {
		return this.svoSent;
	}

	public void setSvoSent(String svoSent) {
		this.svoSent = svoSent;
	}

	public String getTagBy() {
		return this.tagBy;
	}

	public void setTagBy(String tagBy) {
		this.tagBy = tagBy;
	}

	public Date getTagDate() {
		return this.tagDate;
	}

	public void setTagDate(Date tagDate) {
		this.tagDate = tagDate;
	}

	public String getTagNo() {
		return this.tagNo;
	}

	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}

	public String getTaskCard() {
		return this.taskCard;
	}

	public void setTaskCard(String taskCard) {
		this.taskCard = taskCard;
	}

	public String getTaskCardPn() {
		return this.taskCardPn;
	}

	public void setTaskCardPn(String taskCardPn) {
		this.taskCardPn = taskCardPn;
	}

	public String getTaskCardSn() {
		return this.taskCardSn;
	}

	public void setTaskCardSn(String taskCardSn) {
		this.taskCardSn = taskCardSn;
	}

	public String getTaxIncentive() {
		return this.taxIncentive;
	}

	public void setTaxIncentive(String taxIncentive) {
		this.taxIncentive = taxIncentive;
	}

	public String getTaxIncentiveNbr() {
		return this.taxIncentiveNbr;
	}

	public void setTaxIncentiveNbr(String taxIncentiveNbr) {
		this.taxIncentiveNbr = taxIncentiveNbr;
	}

	public Date getTechnicalRecordDate() {
		return this.technicalRecordDate;
	}

	public void setTechnicalRecordDate(Date technicalRecordDate) {
		this.technicalRecordDate = technicalRecordDate;
	}

	public String getToBin() {
		return this.toBin;
	}

	public void setToBin(String toBin) {
		this.toBin = toBin;
	}

	public String getToLocation() {
		return this.toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public String getToolCalibrationNo() {
		return this.toolCalibrationNo;
	}

	public void setToolCalibrationNo(String toolCalibrationNo) {
		this.toolCalibrationNo = toolCalibrationNo;
	}

	public String getTransaction() {
		return this.transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getTransactionHour() {
		return this.transactionHour;
	}

	public void setTransactionHour(BigDecimal transactionHour) {
		this.transactionHour = transactionHour;
	}

	public BigDecimal getTransactionItem() {
		return this.transactionItem;
	}

	public void setTransactionItem(BigDecimal transactionItem) {
		this.transactionItem = transactionItem;
	}

	public BigDecimal getTransactionMinute() {
		return this.transactionMinute;
	}

	public void setTransactionMinute(BigDecimal transactionMinute) {
		this.transactionMinute = transactionMinute;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionTypeControl() {
		return this.transactionTypeControl;
	}

	public void setTransactionTypeControl(String transactionTypeControl) {
		this.transactionTypeControl = transactionTypeControl;
	}

	public BigDecimal getUnitCost() {
		return this.unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public BigDecimal getUnitSell() {
		return this.unitSell;
	}

	public void setUnitSell(BigDecimal unitSell) {
		this.unitSell = unitSell;
	}

	public String getUnitSellOverride() {
		return this.unitSellOverride;
	}

	public void setUnitSellOverride(String unitSellOverride) {
		this.unitSellOverride = unitSellOverride;
	}

	public String getUsNumber() {
		return this.usNumber;
	}

	public void setUsNumber(String usNumber) {
		this.usNumber = usNumber;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVendorLot() {
		return this.vendorLot;
	}

	public void setVendorLot(String vendorLot) {
		this.vendorLot = vendorLot;
	}

	public BigDecimal getWo() {
		return this.wo;
	}

	public void setWo(BigDecimal wo) {
		this.wo = wo;
	}

}