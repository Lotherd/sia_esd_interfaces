package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ORDER_DETAIL database table.
 * 
 */
@Entity
@Table(name="ORDER_DETAIL")
@NamedQuery(name="OrderDetail.findAll", query="SELECT o FROM OrderDetail o")
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrderDetailPK id;

	private String ac;

	@Column(name="ACTUAL_REPAIR")
	private String actualRepair;

	@Column(name="ACTUAL_SHIP_FROM_DATE")
	private Date actualShipFromDate;

	@Column(name="ADD_COST_TYPE")
	private String addCostType;

	@Column(name="AMOUNT_AWARDED")
	private BigDecimal amountAwarded;

	@Column(name="AUTO_ISSUE")
	private String autoIssue;

	@Column(name="AUTO_ISSUE_QTY")
	private BigDecimal autoIssueQty;

	@Column(name="AUTO_ISSUE_QTY_RECEIVED")
	private BigDecimal autoIssueQtyReceived;

	private BigDecimal batch;

	private String ber;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CAPITAL_EXPENDITURE")
	private String capitalExpenditure;

	private String condition;

	@Column(name="CONSIGNED_GENERATION_FLAG")
	private String consignedGenerationFlag;

	@Column(name="CONSIGNED_GENERATION_LINE")
	private BigDecimal consignedGenerationLine;

	@Column(name="CONTRACT_ORDER_BY_MONTHS")
	private String contractOrderByMonths;

	@Column(name="CONVERTED_FROM_ORDER_LINE")
	private BigDecimal convertedFromOrderLine;

	@Column(name="CONVERTED_FROM_ORDER_TYPE")
	private String convertedFromOrderType;

	@Column(name="CONVERTED_TO_ORDER_LINE")
	private BigDecimal convertedToOrderLine;

	@Column(name="CONVERTED_TO_ORDER_TYPE")
	private String convertedToOrderType;

	@Column(name="COST_PER_DAY_FACTOR_1")
	private BigDecimal costPerDayFactor1;

	@Column(name="COST_PER_DAY_FACTOR_1_CURRENCY")
	private BigDecimal costPerDayFactor1Currency;

	@Column(name="COST_PER_DAY_FACTOR_2")
	private BigDecimal costPerDayFactor2;

	@Column(name="COST_PER_DAY_FACTOR_2_CURRENCY")
	private BigDecimal costPerDayFactor2Currency;

	@Column(name="COST_PER_DAY_FACTOR_3")
	private BigDecimal costPerDayFactor3;

	@Column(name="COST_PER_DAY_FACTOR_3_CURRENCY")
	private BigDecimal costPerDayFactor3Currency;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DATE_RDY_2B_PCKD_UP")
	private Date dateRdy2bPckdUp;

	private String defect;

	@Column(name="DEFECT_ITEM")
	private BigDecimal defectItem;

	@Column(name="DEFECT_TYPE")
	private String defectType;

	@Column(name="DELIVERY_DATE")
	private Date deliveryDate;

	@Column(name="DELIVERY_HOUR")
	private BigDecimal deliveryHour;

	@Column(name="DELIVERY_MINUTE")
	private BigDecimal deliveryMinute;

	private BigDecimal discount;

	@Column(name="DISTRIBUTION_LINE")
	private BigDecimal distributionLine;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	@Column(name="DONOTSHIPEARLY_CHANGE")
	private String donotshipearlyChange;

	@Column(name="DUTY_COST")
	private BigDecimal dutyCost;

	@Column(name="DUTY_COST_CURRENCY")
	private BigDecimal dutyCostCurrency;

	private String earliest;

	@Column(name="EST_SHIP_DATE")
	private Date estShipDate;

	@Column(name="EXCHANGE_BATCH")
	private BigDecimal exchangeBatch;

	@Column(name="EXCHANGE_PN")
	private String exchangePn;

	@Column(name="EXCHANGE_PN_DESCRIPTION")
	private String exchangePnDescription;

	@Column(name="EXCHANGE_REPAIR")
	private String exchangeRepair;

	@Column(name="EXCHANGE_REPAIR_COST")
	private BigDecimal exchangeRepairCost;

	@Column(name="EXCHANGE_REPAIR_COST_CURRENCY")
	private BigDecimal exchangeRepairCostCurrency;

	@Column(name="EXCHANGE_SN")
	private String exchangeSn;

	@Column(name="EXCHANGED_CORE_COST")
	private BigDecimal exchangedCoreCost;

	@Column(name="EXCHANGED_CORE_COST_SECONDARY")
	private BigDecimal exchangedCoreCostSecondary;

	@Column(name="EXPEDITE_DATE")
	private Date expediteDate;

	private String expedited;

	@Column(name="FAULT_FOUND")
	private String faultFound;

	@Column(name="FIX_LOAN_COST")
	private BigDecimal fixLoanCost;

	@Column(name="FIX_LOAN_COST_CURRENCY")
	private BigDecimal fixLoanCostCurrency;

	@Column(name="FIX_LOAN_COST_PERCENT")
	private BigDecimal fixLoanCostPercent;

	@Column(name="FORM_NO")
	private BigDecimal formNo;

	@Column(name="FREE_OF_CHARGE_FLAG")
	private String freeOfChargeFlag;

	@Column(name="FREIGHT_COST")
	private BigDecimal freightCost;

	@Column(name="FREIGHT_COST_CURRENCY")
	private BigDecimal freightCostCurrency;

	private String gl;

	@Column(name="GL_COMPANY")
	private String glCompany;

	@Column(name="GL_COST_CENTER")
	private String glCostCenter;

	@Column(name="GL_EXPENDITURE")
	private String glExpenditure;

	@Column(name="IFACE_BAXTER_MATSUP_XFER_DATE")
	private Date ifaceBaxterMatsupXferDate;

	@Column(name="IN_USE")
	private String inUse;

	@Column(name="IN_USE_BY")
	private String inUseBy;

	@Column(name="INSURANCE_CLAIM")
	private String insuranceClaim;

	@Column(name="INSURANCE_CLAIM_NUMBER")
	private String insuranceClaimNumber;

	@Column(name="INTERFACE_TRANSFER_BY")
	private String interfaceTransferBy;

	@Column(name="INTERFACE_TRANSFER_DATE")
	private Date interfaceTransferDate;

	@Column(name="INVOICE_RESERVED")
	private String invoiceReserved;

	@Column(name="INVOICE_STATUS")
	private String invoiceStatus;

	private String invoiced;

	@Column(name="INVOICEWORKS_PREV_TRANSACTION")
	private BigDecimal invoiceworksPrevTransaction;

	@Column(name="INVOICEWORKS_TRANSFER_BY")
	private String invoiceworksTransferBy;

	@Column(name="INVOICEWORKS_TRANSFER_DATE")
	private Date invoiceworksTransferDate;

	@Column(name="LABOR_COST")
	private BigDecimal laborCost;

	@Column(name="LAST_ACK_DATE")
	private Date lastAckDate;

	@Column(name="LAST_DATE_RECEIVED")
	private Date lastDateReceived;

	@Column(name="LATEST_RETURN_DATE")
	private Date latestReturnDate;

	@Column(name="LATEST_SHIP_DATE")
	private Date latestShipDate;

	@Column(name="LATEST_SHIP_HOUR")
	private BigDecimal latestShipHour;

	@Column(name="LATEST_SHIP_MINUTE")
	private BigDecimal latestShipMinute;

	@Column(name="LATEST_TAT")
	private BigDecimal latestTat;

	@Column(name="LEAD_DAYS")
	private BigDecimal leadDays;

	@Column(name="LHT_NUMBER")
	private String lhtNumber;

	@Column(name="LICENSE_TYPE")
	private String licenseType;

	@Column(name="LINE_IDENTIFIER")
	private BigDecimal lineIdentifier;

	@Column(name="LINE_ITEM_QTY")
	private BigDecimal lineItemQty;

	@Column(name="LINE_ITEM_REMARKS")
	private String lineItemRemarks;

	@Column(name="LINE_ITEM_SN")
	private String lineItemSn;

	@Column(name="LINE_ITEM_UOM")
	private String lineItemUom;

	@Column(name="LINKED_ORDER_LINE")
	private BigDecimal linkedOrderLine;

	@Column(name="LINKED_ORDER_NUMBER")
	private BigDecimal linkedOrderNumber;

	@Column(name="LINKED_ORDER_TYPE")
	private String linkedOrderType;

	@Column(name="LOAD_INITIAL_COST")
	private BigDecimal loadInitialCost;

	@Column(name="LOAD_INITIAL_COST_CURRENCY")
	private BigDecimal loadInitialCostCurrency;

	@Column(name="LOAN_CATEGORY")
	private String loanCategory;

	@Column(name="LOAN_DAYS")
	private BigDecimal loanDays;

	@Column(name="LOAN_DAYS_FACTOR_1_PERCENT")
	private BigDecimal loanDaysFactor1Percent;

	@Column(name="LOAN_DAYS_FACTOR_2")
	private BigDecimal loanDaysFactor2;

	@Column(name="LOAN_DAYS_FACTOR_2_PERCENT")
	private BigDecimal loanDaysFactor2Percent;

	@Column(name="LOAN_DAYS_FACTOR_3_PERCENT")
	private BigDecimal loanDaysFactor3Percent;

	@Column(name="LOAN_END_CHARGE_DATE")
	private Date loanEndChargeDate;

	@Column(name="LOAN_START_CHARGE_DATE")
	private Date loanStartChargeDate;

	private String location;

	@Column(name="LOCATION_CHANGE")
	private String locationChange;

	@Column(name="MISCELLANEOUS_REF")
	private String miscellaneousRef;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="NEGOTIATED_SHIP_DATE")
	private Date negotiatedShipDate;

	@Column(name="NEGOTIATED_SHIP_HOUR")
	private BigDecimal negotiatedShipHour;

	@Column(name="NEGOTIATED_SHIP_MINUTE")
	private BigDecimal negotiatedShipMinute;

	@Column(name="NON_INVENTORY_FLAG")
	private String nonInventoryFlag;

	private BigDecimal notes;

	@Column(name="NUMBER_OF_MONTHS")
	private BigDecimal numberOfMonths;

	@Column(name="NUMBER_OF_MONTHS_ISSUED")
	private BigDecimal numberOfMonthsIssued;

	@Column(name="OPERATIONAL_DAMAGE")
	private String operationalDamage;

	@Column(name="OPERATIONAL_DAMAGE_FLAG")
	private String operationalDamageFlag;

	@Column(name="ORDER_LOCK")
	private String orderLock;

	@Column(name="OS_MESSAGE_ACCEPTED")
	private String osMessageAccepted;

	@Column(name="OS_PREV_TRANSACTION")
	private BigDecimal osPrevTransaction;

	@Column(name="OS_SENT_STATUS")
	private String osSentStatus;

	@Column(name="OS_TRANSACTION")
	private BigDecimal osTransaction;

	@Column(name="OVERRIDE_RECEIVE_PN")
	private String overrideReceivePn;

	private String owner;

	@Column(name="PART_CHANGE")
	private String partChange;

	private BigDecimal picklist;

	@Column(name="PICKLIST_LINE")
	private BigDecimal picklistLine;

	private String pn;

	@Column(name="PN_DESCRIPTION")
	private String pnDescription;

	@Column(name="PN_TO_BE_RECEIVED")
	private String pnToBeReceived;

	@Column(name="POOL_LINE")
	private BigDecimal poolLine;

	@Column(name="POOL_NUMBER")
	private BigDecimal poolNumber;

	@Column(name="PRICE_CHANGE")
	private String priceChange;

	@Column(name="PRICECHANGE_UNIT_COST")
	private BigDecimal pricechangeUnitCost;

	@Column(name="QTY_AVAILABLE")
	private BigDecimal qtyAvailable;

	@Column(name="QTY_LICENSE")
	private BigDecimal qtyLicense;

	@Column(name="QTY_PENDING_RI")
	private BigDecimal qtyPendingRi;

	@Column(name="QTY_RECEIVED")
	private BigDecimal qtyReceived;

	@Column(name="QTY_REQUIRE")
	private BigDecimal qtyRequire;

	@Column(name="QTY_RESERVED")
	private BigDecimal qtyReserved;

	@Column(name="QTY_SHIPPED")
	private BigDecimal qtyShipped;

	@Column(name="QTY_US")
	private BigDecimal qtyUs;

	@Column(name="QUANTITY_CHANGE")
	private String quantityChange;

	private String quoted;

	@Column(name="QUOTED_ON_DATE")
	private Date quotedOnDate;

	@Column(name="RECEIPT_AT_VENDOR_DATE")
	private Date receiptAtVendorDate;

	private String remarks;

	@Column(name="RENTAL_ISSUE_DATE")
	private Date rentalIssueDate;

	@Column(name="RENTAL_LAST_INVOICE_DATE")
	private Date rentalLastInvoiceDate;

	@Column(name="REPAIR_STATUS_CODE")
	private String repairStatusCode;

	@Column(name="REPLACEMENT_PN")
	private String replacementPn;

	private BigDecimal requisition;

	@Column(name="REQUISITION_LINE")
	private BigDecimal requisitionLine;

	@Column(name="RESERVE_BATCH")
	private BigDecimal reserveBatch;

	@Column(name="RESERVE_SN")
	private String reserveSn;

	@Column(name="RETURN_PN_WITHIN_DAYS")
	private BigDecimal returnPnWithinDays;

	@Column(name="RI_RESET_AT_RECEIVING")
	private String riResetAtReceiving;

	@Column(name="RO_BIN")
	private String roBin;

	@Column(name="RO_GL")
	private String roGl;

	@Column(name="RO_GL_COMPANY")
	private String roGlCompany;

	@Column(name="RO_GL_COST_CENTER")
	private String roGlCostCenter;

	@Column(name="RO_GL_EXPENDITURE")
	private String roGlExpenditure;

	@Column(name="RO_LOCATION")
	private String roLocation;

	@Column(name="RO_PN_ON_AC")
	private String roPnOnAc;

	@Column(name="SCHEDULE_AC_DAYS")
	private BigDecimal scheduleAcDays;

	private String scrap;

	@Column(name="SHIP_COMPLETE")
	private String shipComplete;

	@Column(name="SHIP_DAYS")
	private BigDecimal shipDays;

	@Column(name="SHIPPING_ADDRESS1")
	private String shippingAddress1;

	@Column(name="SHIPPING_ADDRESS2")
	private String shippingAddress2;

	@Column(name="SHIPPING_CELL")
	private String shippingCell;

	@Column(name="SHIPPING_CITY")
	private String shippingCity;

	@Column(name="SHIPPING_COUNTRY")
	private String shippingCountry;

	@Column(name="SHIPPING_EMAIL")
	private String shippingEmail;

	@Column(name="SHIPPING_FAX")
	private String shippingFax;

	@Column(name="SHIPPING_MAIN_CONTACT")
	private String shippingMainContact;

	@Column(name="SHIPPING_PHONE")
	private String shippingPhone;

	@Column(name="SHIPPING_POST")
	private String shippingPost;

	@Column(name="SHIPPING_SITA")
	private String shippingSita;

	@Column(name="SHIPPING_STATE")
	private String shippingState;

	private String sn;

	@Column(name="SN_TO_BE_RECEIVED")
	private String snToBeReceived;

	@Column(name="SN_TO_BE_SEND")
	private String snToBeSend;

	@Column(name="SO_LINE")
	private BigDecimal soLine;

	@Column(name="SO_NUMBER")
	private BigDecimal soNumber;

	@Column(name="SO_TYPE")
	private String soType;

	@Column(name="SPEC2000_MESSAGE_ACCEPTED")
	private String spec2000MessageAccepted;

	@Column(name="SPEC2000_PREV_TRANSACTION")
	private BigDecimal spec2000PrevTransaction;

	@Column(name="SPEC2000_SENT_STATUS")
	private String spec2000SentStatus;

	private String status;

	@Column(name="SV_CONTROL")
	private String svControl;

	@Column(name="TASK_CARD")
	private String taskCard;

	@Column(name="TAX_COST")
	private BigDecimal taxCost;

	@Column(name="TAX_COST_CURRENCY")
	private BigDecimal taxCostCurrency;

	@Column(name="TAX_INCENTIVE")
	private String taxIncentive;

	@Column(name="TAX_INCENTIVE_NBR")
	private String taxIncentiveNbr;

	@Column(name="TO_BIN")
	private String toBin;

	@Column(name="TOTAL_COST")
	private BigDecimal totalCost;

	@Column(name="TRAX_TRANSACTION")
	private BigDecimal traxTransaction;

	@Column(name="UNIT_COST")
	private BigDecimal unitCost;

	@Column(name="UNIT_COST_CURRENCY")
	private BigDecimal unitCostCurrency;

	@Column(name="UNIT_LIST_PRICE")
	private BigDecimal unitListPrice;

	private String uom;

	@Column(name="US_CODE")
	private String usCode;

	@Column(name="VAT_CODE")
	private String vatCode;

	@Column(name="WARRANTY_CLAIM")
	private String warrantyClaim;

	@Column(name="WARRANTY_CLAIM_DATE")
	private Date warrantyClaimDate;

	@Column(name="WARRANTY_CLAIM_ORDER_ITEM_TYPE")
	private String warrantyClaimOrderItemType;

	@Column(name="WARRANTY_CLAIM_PRINT")
	private String warrantyClaimPrint;

	@Column(name="WARRANTY_CLAIM_STATUS")
	private String warrantyClaimStatus;

	@Column(name="WARRANTY_CLAIM_TRANSACTION")
	private String warrantyClaimTransaction;

	@Column(name="WARRANTY_CYCLES")
	private BigDecimal warrantyCycles;

	@Column(name="WARRANTY_DAYS")
	private BigDecimal warrantyDays;

	@Column(name="WARRANTY_GOODS_RCVD_BATCH")
	private BigDecimal warrantyGoodsRcvdBatch;

	@Column(name="WARRANTY_HOURS")
	private BigDecimal warrantyHours;

	@Column(name="WARRANTY_LINE")
	private BigDecimal warrantyLine;

	private BigDecimal wo;
	
	@Column(name="INTERFACE_SYNC_DATE")
	private Date interfaceSyncDate;
	
	@Column(name="INTERFACE_SYNC_FLAG")
	private String interfaceSyncFlag;
	
	@Column(name="EXTERNAL_KPR_NUMBER")
	private String externalKPRNumber;
	
	@Column(name="EXTERNAL_PR_ITEM")
	private String externalPRItem;
	
	@Column(name="EXTERNAL_RELEASE_STRATEGY")
	private String externalReleaseStrategy;

	//bi-directional many-to-one association to OrderHeader
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="ORDER_NUMBER", referencedColumnName="ORDER_NUMBER"),
		@JoinColumn(name="ORDER_TYPE", referencedColumnName="ORDER_TYPE")
		})
	private OrderHeader orderHeader;

	public OrderDetail() {
	}

	public OrderDetailPK getId() {
		return this.id;
	}

	public void setId(OrderDetailPK id) {
		this.id = id;
	}

	public String getAc() {
		return this.ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	public String getActualRepair() {
		return this.actualRepair;
	}

	public void setActualRepair(String actualRepair) {
		this.actualRepair = actualRepair;
	}

	public Date getActualShipFromDate() {
		return this.actualShipFromDate;
	}

	public void setActualShipFromDate(Date actualShipFromDate) {
		this.actualShipFromDate = actualShipFromDate;
	}

	public String getAddCostType() {
		return this.addCostType;
	}

	public void setAddCostType(String addCostType) {
		this.addCostType = addCostType;
	}

	public BigDecimal getAmountAwarded() {
		return this.amountAwarded;
	}

	public void setAmountAwarded(BigDecimal amountAwarded) {
		this.amountAwarded = amountAwarded;
	}

	public String getAutoIssue() {
		return this.autoIssue;
	}

	public void setAutoIssue(String autoIssue) {
		this.autoIssue = autoIssue;
	}

	public BigDecimal getAutoIssueQty() {
		return this.autoIssueQty;
	}

	public void setAutoIssueQty(BigDecimal autoIssueQty) {
		this.autoIssueQty = autoIssueQty;
	}

	public BigDecimal getAutoIssueQtyReceived() {
		return this.autoIssueQtyReceived;
	}

	public void setAutoIssueQtyReceived(BigDecimal autoIssueQtyReceived) {
		this.autoIssueQtyReceived = autoIssueQtyReceived;
	}

	public BigDecimal getBatch() {
		return this.batch;
	}

	public void setBatch(BigDecimal batch) {
		this.batch = batch;
	}

	public String getBer() {
		return this.ber;
	}

	public void setBer(String ber) {
		this.ber = ber;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public String getCapitalExpenditure() {
		return this.capitalExpenditure;
	}

	public void setCapitalExpenditure(String capitalExpenditure) {
		this.capitalExpenditure = capitalExpenditure;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getConsignedGenerationFlag() {
		return this.consignedGenerationFlag;
	}

	public void setConsignedGenerationFlag(String consignedGenerationFlag) {
		this.consignedGenerationFlag = consignedGenerationFlag;
	}

	public BigDecimal getConsignedGenerationLine() {
		return this.consignedGenerationLine;
	}

	public void setConsignedGenerationLine(BigDecimal consignedGenerationLine) {
		this.consignedGenerationLine = consignedGenerationLine;
	}

	public String getContractOrderByMonths() {
		return this.contractOrderByMonths;
	}

	public void setContractOrderByMonths(String contractOrderByMonths) {
		this.contractOrderByMonths = contractOrderByMonths;
	}

	public BigDecimal getConvertedFromOrderLine() {
		return this.convertedFromOrderLine;
	}

	public void setConvertedFromOrderLine(BigDecimal convertedFromOrderLine) {
		this.convertedFromOrderLine = convertedFromOrderLine;
	}

	public String getConvertedFromOrderType() {
		return this.convertedFromOrderType;
	}

	public void setConvertedFromOrderType(String convertedFromOrderType) {
		this.convertedFromOrderType = convertedFromOrderType;
	}

	public BigDecimal getConvertedToOrderLine() {
		return this.convertedToOrderLine;
	}

	public void setConvertedToOrderLine(BigDecimal convertedToOrderLine) {
		this.convertedToOrderLine = convertedToOrderLine;
	}

	public String getConvertedToOrderType() {
		return this.convertedToOrderType;
	}

	public void setConvertedToOrderType(String convertedToOrderType) {
		this.convertedToOrderType = convertedToOrderType;
	}

	public BigDecimal getCostPerDayFactor1() {
		return this.costPerDayFactor1;
	}

	public void setCostPerDayFactor1(BigDecimal costPerDayFactor1) {
		this.costPerDayFactor1 = costPerDayFactor1;
	}

	public BigDecimal getCostPerDayFactor1Currency() {
		return this.costPerDayFactor1Currency;
	}

	public void setCostPerDayFactor1Currency(BigDecimal costPerDayFactor1Currency) {
		this.costPerDayFactor1Currency = costPerDayFactor1Currency;
	}

	public BigDecimal getCostPerDayFactor2() {
		return this.costPerDayFactor2;
	}

	public void setCostPerDayFactor2(BigDecimal costPerDayFactor2) {
		this.costPerDayFactor2 = costPerDayFactor2;
	}

	public BigDecimal getCostPerDayFactor2Currency() {
		return this.costPerDayFactor2Currency;
	}

	public void setCostPerDayFactor2Currency(BigDecimal costPerDayFactor2Currency) {
		this.costPerDayFactor2Currency = costPerDayFactor2Currency;
	}

	public BigDecimal getCostPerDayFactor3() {
		return this.costPerDayFactor3;
	}

	public void setCostPerDayFactor3(BigDecimal costPerDayFactor3) {
		this.costPerDayFactor3 = costPerDayFactor3;
	}

	public BigDecimal getCostPerDayFactor3Currency() {
		return this.costPerDayFactor3Currency;
	}

	public void setCostPerDayFactor3Currency(BigDecimal costPerDayFactor3Currency) {
		this.costPerDayFactor3Currency = costPerDayFactor3Currency;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDateRdy2bPckdUp() {
		return this.dateRdy2bPckdUp;
	}

	public void setDateRdy2bPckdUp(Date dateRdy2bPckdUp) {
		this.dateRdy2bPckdUp = dateRdy2bPckdUp;
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

	public BigDecimal getDeliveryHour() {
		return this.deliveryHour;
	}

	public void setDeliveryHour(BigDecimal deliveryHour) {
		this.deliveryHour = deliveryHour;
	}

	public BigDecimal getDeliveryMinute() {
		return this.deliveryMinute;
	}

	public void setDeliveryMinute(BigDecimal deliveryMinute) {
		this.deliveryMinute = deliveryMinute;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getDistributionLine() {
		return this.distributionLine;
	}

	public void setDistributionLine(BigDecimal distributionLine) {
		this.distributionLine = distributionLine;
	}

	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getDonotshipearlyChange() {
		return this.donotshipearlyChange;
	}

	public void setDonotshipearlyChange(String donotshipearlyChange) {
		this.donotshipearlyChange = donotshipearlyChange;
	}

	public BigDecimal getDutyCost() {
		return this.dutyCost;
	}

	public void setDutyCost(BigDecimal dutyCost) {
		this.dutyCost = dutyCost;
	}

	public BigDecimal getDutyCostCurrency() {
		return this.dutyCostCurrency;
	}

	public void setDutyCostCurrency(BigDecimal dutyCostCurrency) {
		this.dutyCostCurrency = dutyCostCurrency;
	}

	public String getEarliest() {
		return this.earliest;
	}

	public void setEarliest(String earliest) {
		this.earliest = earliest;
	}

	public Date getEstShipDate() {
		return this.estShipDate;
	}

	public void setEstShipDate(Date estShipDate) {
		this.estShipDate = estShipDate;
	}

	public BigDecimal getExchangeBatch() {
		return this.exchangeBatch;
	}

	public void setExchangeBatch(BigDecimal exchangeBatch) {
		this.exchangeBatch = exchangeBatch;
	}

	public String getExchangePn() {
		return this.exchangePn;
	}

	public void setExchangePn(String exchangePn) {
		this.exchangePn = exchangePn;
	}

	public String getExchangePnDescription() {
		return this.exchangePnDescription;
	}

	public void setExchangePnDescription(String exchangePnDescription) {
		this.exchangePnDescription = exchangePnDescription;
	}

	public String getExchangeRepair() {
		return this.exchangeRepair;
	}

	public void setExchangeRepair(String exchangeRepair) {
		this.exchangeRepair = exchangeRepair;
	}

	public BigDecimal getExchangeRepairCost() {
		return this.exchangeRepairCost;
	}

	public void setExchangeRepairCost(BigDecimal exchangeRepairCost) {
		this.exchangeRepairCost = exchangeRepairCost;
	}

	public BigDecimal getExchangeRepairCostCurrency() {
		return this.exchangeRepairCostCurrency;
	}

	public void setExchangeRepairCostCurrency(BigDecimal exchangeRepairCostCurrency) {
		this.exchangeRepairCostCurrency = exchangeRepairCostCurrency;
	}

	public String getExchangeSn() {
		return this.exchangeSn;
	}

	public void setExchangeSn(String exchangeSn) {
		this.exchangeSn = exchangeSn;
	}

	public BigDecimal getExchangedCoreCost() {
		return this.exchangedCoreCost;
	}

	public void setExchangedCoreCost(BigDecimal exchangedCoreCost) {
		this.exchangedCoreCost = exchangedCoreCost;
	}

	public BigDecimal getExchangedCoreCostSecondary() {
		return this.exchangedCoreCostSecondary;
	}

	public void setExchangedCoreCostSecondary(BigDecimal exchangedCoreCostSecondary) {
		this.exchangedCoreCostSecondary = exchangedCoreCostSecondary;
	}

	public Date getExpediteDate() {
		return this.expediteDate;
	}

	public void setExpediteDate(Date expediteDate) {
		this.expediteDate = expediteDate;
	}

	public String getExpedited() {
		return this.expedited;
	}

	public void setExpedited(String expedited) {
		this.expedited = expedited;
	}

	public String getFaultFound() {
		return this.faultFound;
	}

	public void setFaultFound(String faultFound) {
		this.faultFound = faultFound;
	}

	public BigDecimal getFixLoanCost() {
		return this.fixLoanCost;
	}

	public void setFixLoanCost(BigDecimal fixLoanCost) {
		this.fixLoanCost = fixLoanCost;
	}

	public BigDecimal getFixLoanCostCurrency() {
		return this.fixLoanCostCurrency;
	}

	public void setFixLoanCostCurrency(BigDecimal fixLoanCostCurrency) {
		this.fixLoanCostCurrency = fixLoanCostCurrency;
	}

	public BigDecimal getFixLoanCostPercent() {
		return this.fixLoanCostPercent;
	}

	public void setFixLoanCostPercent(BigDecimal fixLoanCostPercent) {
		this.fixLoanCostPercent = fixLoanCostPercent;
	}

	public BigDecimal getFormNo() {
		return this.formNo;
	}

	public void setFormNo(BigDecimal formNo) {
		this.formNo = formNo;
	}

	public String getFreeOfChargeFlag() {
		return this.freeOfChargeFlag;
	}

	public void setFreeOfChargeFlag(String freeOfChargeFlag) {
		this.freeOfChargeFlag = freeOfChargeFlag;
	}

	public BigDecimal getFreightCost() {
		return this.freightCost;
	}

	public void setFreightCost(BigDecimal freightCost) {
		this.freightCost = freightCost;
	}

	public BigDecimal getFreightCostCurrency() {
		return this.freightCostCurrency;
	}

	public void setFreightCostCurrency(BigDecimal freightCostCurrency) {
		this.freightCostCurrency = freightCostCurrency;
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

	public Date getIfaceBaxterMatsupXferDate() {
		return this.ifaceBaxterMatsupXferDate;
	}

	public void setIfaceBaxterMatsupXferDate(Date ifaceBaxterMatsupXferDate) {
		this.ifaceBaxterMatsupXferDate = ifaceBaxterMatsupXferDate;
	}

	public String getInUse() {
		return this.inUse;
	}

	public void setInUse(String inUse) {
		this.inUse = inUse;
	}

	public String getInUseBy() {
		return this.inUseBy;
	}

	public void setInUseBy(String inUseBy) {
		this.inUseBy = inUseBy;
	}

	public String getInsuranceClaim() {
		return this.insuranceClaim;
	}

	public void setInsuranceClaim(String insuranceClaim) {
		this.insuranceClaim = insuranceClaim;
	}

	public String getInsuranceClaimNumber() {
		return this.insuranceClaimNumber;
	}

	public void setInsuranceClaimNumber(String insuranceClaimNumber) {
		this.insuranceClaimNumber = insuranceClaimNumber;
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

	public String getInvoiceReserved() {
		return this.invoiceReserved;
	}

	public void setInvoiceReserved(String invoiceReserved) {
		this.invoiceReserved = invoiceReserved;
	}

	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getInvoiced() {
		return this.invoiced;
	}

	public void setInvoiced(String invoiced) {
		this.invoiced = invoiced;
	}

	public BigDecimal getInvoiceworksPrevTransaction() {
		return this.invoiceworksPrevTransaction;
	}

	public void setInvoiceworksPrevTransaction(BigDecimal invoiceworksPrevTransaction) {
		this.invoiceworksPrevTransaction = invoiceworksPrevTransaction;
	}

	public String getInvoiceworksTransferBy() {
		return this.invoiceworksTransferBy;
	}

	public void setInvoiceworksTransferBy(String invoiceworksTransferBy) {
		this.invoiceworksTransferBy = invoiceworksTransferBy;
	}

	public Date getInvoiceworksTransferDate() {
		return this.invoiceworksTransferDate;
	}

	public void setInvoiceworksTransferDate(Date invoiceworksTransferDate) {
		this.invoiceworksTransferDate = invoiceworksTransferDate;
	}

	public BigDecimal getLaborCost() {
		return this.laborCost;
	}

	public void setLaborCost(BigDecimal laborCost) {
		this.laborCost = laborCost;
	}

	public Date getLastAckDate() {
		return this.lastAckDate;
	}

	public void setLastAckDate(Date lastAckDate) {
		this.lastAckDate = lastAckDate;
	}

	public Date getLastDateReceived() {
		return this.lastDateReceived;
	}

	public void setLastDateReceived(Date lastDateReceived) {
		this.lastDateReceived = lastDateReceived;
	}

	public Date getLatestReturnDate() {
		return this.latestReturnDate;
	}

	public void setLatestReturnDate(Date latestReturnDate) {
		this.latestReturnDate = latestReturnDate;
	}

	public Date getLatestShipDate() {
		return this.latestShipDate;
	}

	public void setLatestShipDate(Date latestShipDate) {
		this.latestShipDate = latestShipDate;
	}

	public BigDecimal getLatestShipHour() {
		return this.latestShipHour;
	}

	public void setLatestShipHour(BigDecimal latestShipHour) {
		this.latestShipHour = latestShipHour;
	}

	public BigDecimal getLatestShipMinute() {
		return this.latestShipMinute;
	}

	public void setLatestShipMinute(BigDecimal latestShipMinute) {
		this.latestShipMinute = latestShipMinute;
	}

	public BigDecimal getLatestTat() {
		return this.latestTat;
	}

	public void setLatestTat(BigDecimal latestTat) {
		this.latestTat = latestTat;
	}

	public BigDecimal getLeadDays() {
		return this.leadDays;
	}

	public void setLeadDays(BigDecimal leadDays) {
		this.leadDays = leadDays;
	}

	public String getLhtNumber() {
		return this.lhtNumber;
	}

	public void setLhtNumber(String lhtNumber) {
		this.lhtNumber = lhtNumber;
	}

	public String getLicenseType() {
		return this.licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public BigDecimal getLineIdentifier() {
		return this.lineIdentifier;
	}

	public void setLineIdentifier(BigDecimal lineIdentifier) {
		this.lineIdentifier = lineIdentifier;
	}

	public BigDecimal getLineItemQty() {
		return this.lineItemQty;
	}

	public void setLineItemQty(BigDecimal lineItemQty) {
		this.lineItemQty = lineItemQty;
	}

	public String getLineItemRemarks() {
		return this.lineItemRemarks;
	}

	public void setLineItemRemarks(String lineItemRemarks) {
		this.lineItemRemarks = lineItemRemarks;
	}

	public String getLineItemSn() {
		return this.lineItemSn;
	}

	public void setLineItemSn(String lineItemSn) {
		this.lineItemSn = lineItemSn;
	}

	public String getLineItemUom() {
		return this.lineItemUom;
	}

	public void setLineItemUom(String lineItemUom) {
		this.lineItemUom = lineItemUom;
	}

	public BigDecimal getLinkedOrderLine() {
		return this.linkedOrderLine;
	}

	public void setLinkedOrderLine(BigDecimal linkedOrderLine) {
		this.linkedOrderLine = linkedOrderLine;
	}

	public BigDecimal getLinkedOrderNumber() {
		return this.linkedOrderNumber;
	}

	public void setLinkedOrderNumber(BigDecimal linkedOrderNumber) {
		this.linkedOrderNumber = linkedOrderNumber;
	}

	public String getLinkedOrderType() {
		return this.linkedOrderType;
	}

	public void setLinkedOrderType(String linkedOrderType) {
		this.linkedOrderType = linkedOrderType;
	}

	public BigDecimal getLoadInitialCost() {
		return this.loadInitialCost;
	}

	public void setLoadInitialCost(BigDecimal loadInitialCost) {
		this.loadInitialCost = loadInitialCost;
	}

	public BigDecimal getLoadInitialCostCurrency() {
		return this.loadInitialCostCurrency;
	}

	public void setLoadInitialCostCurrency(BigDecimal loadInitialCostCurrency) {
		this.loadInitialCostCurrency = loadInitialCostCurrency;
	}

	public String getLoanCategory() {
		return this.loanCategory;
	}

	public void setLoanCategory(String loanCategory) {
		this.loanCategory = loanCategory;
	}

	public BigDecimal getLoanDays() {
		return this.loanDays;
	}

	public void setLoanDays(BigDecimal loanDays) {
		this.loanDays = loanDays;
	}

	public BigDecimal getLoanDaysFactor1Percent() {
		return this.loanDaysFactor1Percent;
	}

	public void setLoanDaysFactor1Percent(BigDecimal loanDaysFactor1Percent) {
		this.loanDaysFactor1Percent = loanDaysFactor1Percent;
	}

	public BigDecimal getLoanDaysFactor2() {
		return this.loanDaysFactor2;
	}

	public void setLoanDaysFactor2(BigDecimal loanDaysFactor2) {
		this.loanDaysFactor2 = loanDaysFactor2;
	}

	public BigDecimal getLoanDaysFactor2Percent() {
		return this.loanDaysFactor2Percent;
	}

	public void setLoanDaysFactor2Percent(BigDecimal loanDaysFactor2Percent) {
		this.loanDaysFactor2Percent = loanDaysFactor2Percent;
	}

	public BigDecimal getLoanDaysFactor3Percent() {
		return this.loanDaysFactor3Percent;
	}

	public void setLoanDaysFactor3Percent(BigDecimal loanDaysFactor3Percent) {
		this.loanDaysFactor3Percent = loanDaysFactor3Percent;
	}

	public Date getLoanEndChargeDate() {
		return this.loanEndChargeDate;
	}

	public void setLoanEndChargeDate(Date loanEndChargeDate) {
		this.loanEndChargeDate = loanEndChargeDate;
	}

	public Date getLoanStartChargeDate() {
		return this.loanStartChargeDate;
	}

	public void setLoanStartChargeDate(Date loanStartChargeDate) {
		this.loanStartChargeDate = loanStartChargeDate;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationChange() {
		return this.locationChange;
	}

	public void setLocationChange(String locationChange) {
		this.locationChange = locationChange;
	}

	public String getMiscellaneousRef() {
		return this.miscellaneousRef;
	}

	public void setMiscellaneousRef(String miscellaneousRef) {
		this.miscellaneousRef = miscellaneousRef;
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

	public Date getNegotiatedShipDate() {
		return this.negotiatedShipDate;
	}

	public void setNegotiatedShipDate(Date negotiatedShipDate) {
		this.negotiatedShipDate = negotiatedShipDate;
	}

	public BigDecimal getNegotiatedShipHour() {
		return this.negotiatedShipHour;
	}

	public void setNegotiatedShipHour(BigDecimal negotiatedShipHour) {
		this.negotiatedShipHour = negotiatedShipHour;
	}

	public BigDecimal getNegotiatedShipMinute() {
		return this.negotiatedShipMinute;
	}

	public void setNegotiatedShipMinute(BigDecimal negotiatedShipMinute) {
		this.negotiatedShipMinute = negotiatedShipMinute;
	}

	public String getNonInventoryFlag() {
		return this.nonInventoryFlag;
	}

	public void setNonInventoryFlag(String nonInventoryFlag) {
		this.nonInventoryFlag = nonInventoryFlag;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public BigDecimal getNumberOfMonths() {
		return this.numberOfMonths;
	}

	public void setNumberOfMonths(BigDecimal numberOfMonths) {
		this.numberOfMonths = numberOfMonths;
	}

	public BigDecimal getNumberOfMonthsIssued() {
		return this.numberOfMonthsIssued;
	}

	public void setNumberOfMonthsIssued(BigDecimal numberOfMonthsIssued) {
		this.numberOfMonthsIssued = numberOfMonthsIssued;
	}

	public String getOperationalDamage() {
		return this.operationalDamage;
	}

	public void setOperationalDamage(String operationalDamage) {
		this.operationalDamage = operationalDamage;
	}

	public String getOperationalDamageFlag() {
		return this.operationalDamageFlag;
	}

	public void setOperationalDamageFlag(String operationalDamageFlag) {
		this.operationalDamageFlag = operationalDamageFlag;
	}

	public String getOrderLock() {
		return this.orderLock;
	}

	public void setOrderLock(String orderLock) {
		this.orderLock = orderLock;
	}

	public String getOsMessageAccepted() {
		return this.osMessageAccepted;
	}

	public void setOsMessageAccepted(String osMessageAccepted) {
		this.osMessageAccepted = osMessageAccepted;
	}

	public BigDecimal getOsPrevTransaction() {
		return this.osPrevTransaction;
	}

	public void setOsPrevTransaction(BigDecimal osPrevTransaction) {
		this.osPrevTransaction = osPrevTransaction;
	}

	public String getOsSentStatus() {
		return this.osSentStatus;
	}

	public void setOsSentStatus(String osSentStatus) {
		this.osSentStatus = osSentStatus;
	}

	public BigDecimal getOsTransaction() {
		return this.osTransaction;
	}

	public void setOsTransaction(BigDecimal osTransaction) {
		this.osTransaction = osTransaction;
	}

	public String getOverrideReceivePn() {
		return this.overrideReceivePn;
	}

	public void setOverrideReceivePn(String overrideReceivePn) {
		this.overrideReceivePn = overrideReceivePn;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPartChange() {
		return this.partChange;
	}

	public void setPartChange(String partChange) {
		this.partChange = partChange;
	}

	public BigDecimal getPicklist() {
		return this.picklist;
	}

	public void setPicklist(BigDecimal picklist) {
		this.picklist = picklist;
	}

	public BigDecimal getPicklistLine() {
		return this.picklistLine;
	}

	public void setPicklistLine(BigDecimal picklistLine) {
		this.picklistLine = picklistLine;
	}

	public String getPn() {
		return this.pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getPnDescription() {
		return this.pnDescription;
	}

	public void setPnDescription(String pnDescription) {
		this.pnDescription = pnDescription;
	}

	public String getPnToBeReceived() {
		return this.pnToBeReceived;
	}

	public void setPnToBeReceived(String pnToBeReceived) {
		this.pnToBeReceived = pnToBeReceived;
	}

	public BigDecimal getPoolLine() {
		return this.poolLine;
	}

	public void setPoolLine(BigDecimal poolLine) {
		this.poolLine = poolLine;
	}

	public BigDecimal getPoolNumber() {
		return this.poolNumber;
	}

	public void setPoolNumber(BigDecimal poolNumber) {
		this.poolNumber = poolNumber;
	}

	public String getPriceChange() {
		return this.priceChange;
	}

	public void setPriceChange(String priceChange) {
		this.priceChange = priceChange;
	}

	public BigDecimal getPricechangeUnitCost() {
		return this.pricechangeUnitCost;
	}

	public void setPricechangeUnitCost(BigDecimal pricechangeUnitCost) {
		this.pricechangeUnitCost = pricechangeUnitCost;
	}

	public BigDecimal getQtyAvailable() {
		return this.qtyAvailable;
	}

	public void setQtyAvailable(BigDecimal qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	public BigDecimal getQtyLicense() {
		return this.qtyLicense;
	}

	public void setQtyLicense(BigDecimal qtyLicense) {
		this.qtyLicense = qtyLicense;
	}

	public BigDecimal getQtyPendingRi() {
		return this.qtyPendingRi;
	}

	public void setQtyPendingRi(BigDecimal qtyPendingRi) {
		this.qtyPendingRi = qtyPendingRi;
	}

	public BigDecimal getQtyReceived() {
		return this.qtyReceived;
	}

	public void setQtyReceived(BigDecimal qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	public BigDecimal getQtyRequire() {
		return this.qtyRequire;
	}

	public void setQtyRequire(BigDecimal qtyRequire) {
		this.qtyRequire = qtyRequire;
	}

	public BigDecimal getQtyReserved() {
		return this.qtyReserved;
	}

	public void setQtyReserved(BigDecimal qtyReserved) {
		this.qtyReserved = qtyReserved;
	}

	public BigDecimal getQtyShipped() {
		return this.qtyShipped;
	}

	public void setQtyShipped(BigDecimal qtyShipped) {
		this.qtyShipped = qtyShipped;
	}

	public BigDecimal getQtyUs() {
		return this.qtyUs;
	}

	public void setQtyUs(BigDecimal qtyUs) {
		this.qtyUs = qtyUs;
	}

	public String getQuantityChange() {
		return this.quantityChange;
	}

	public void setQuantityChange(String quantityChange) {
		this.quantityChange = quantityChange;
	}

	public String getQuoted() {
		return this.quoted;
	}

	public void setQuoted(String quoted) {
		this.quoted = quoted;
	}

	public Date getQuotedOnDate() {
		return this.quotedOnDate;
	}

	public void setQuotedOnDate(Date quotedOnDate) {
		this.quotedOnDate = quotedOnDate;
	}

	public Date getReceiptAtVendorDate() {
		return this.receiptAtVendorDate;
	}

	public void setReceiptAtVendorDate(Date receiptAtVendorDate) {
		this.receiptAtVendorDate = receiptAtVendorDate;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getRentalIssueDate() {
		return this.rentalIssueDate;
	}

	public void setRentalIssueDate(Date rentalIssueDate) {
		this.rentalIssueDate = rentalIssueDate;
	}

	public Date getRentalLastInvoiceDate() {
		return this.rentalLastInvoiceDate;
	}

	public void setRentalLastInvoiceDate(Date rentalLastInvoiceDate) {
		this.rentalLastInvoiceDate = rentalLastInvoiceDate;
	}

	public String getRepairStatusCode() {
		return this.repairStatusCode;
	}

	public void setRepairStatusCode(String repairStatusCode) {
		this.repairStatusCode = repairStatusCode;
	}

	public String getReplacementPn() {
		return this.replacementPn;
	}

	public void setReplacementPn(String replacementPn) {
		this.replacementPn = replacementPn;
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

	public BigDecimal getReserveBatch() {
		return this.reserveBatch;
	}

	public void setReserveBatch(BigDecimal reserveBatch) {
		this.reserveBatch = reserveBatch;
	}

	public String getReserveSn() {
		return this.reserveSn;
	}

	public void setReserveSn(String reserveSn) {
		this.reserveSn = reserveSn;
	}

	public BigDecimal getReturnPnWithinDays() {
		return this.returnPnWithinDays;
	}

	public void setReturnPnWithinDays(BigDecimal returnPnWithinDays) {
		this.returnPnWithinDays = returnPnWithinDays;
	}

	public String getRiResetAtReceiving() {
		return this.riResetAtReceiving;
	}

	public void setRiResetAtReceiving(String riResetAtReceiving) {
		this.riResetAtReceiving = riResetAtReceiving;
	}

	public String getRoBin() {
		return this.roBin;
	}

	public void setRoBin(String roBin) {
		this.roBin = roBin;
	}

	public String getRoGl() {
		return this.roGl;
	}

	public void setRoGl(String roGl) {
		this.roGl = roGl;
	}

	public String getRoGlCompany() {
		return this.roGlCompany;
	}

	public void setRoGlCompany(String roGlCompany) {
		this.roGlCompany = roGlCompany;
	}

	public String getRoGlCostCenter() {
		return this.roGlCostCenter;
	}

	public void setRoGlCostCenter(String roGlCostCenter) {
		this.roGlCostCenter = roGlCostCenter;
	}

	public String getRoGlExpenditure() {
		return this.roGlExpenditure;
	}

	public void setRoGlExpenditure(String roGlExpenditure) {
		this.roGlExpenditure = roGlExpenditure;
	}

	public String getRoLocation() {
		return this.roLocation;
	}

	public void setRoLocation(String roLocation) {
		this.roLocation = roLocation;
	}

	public String getRoPnOnAc() {
		return this.roPnOnAc;
	}

	public void setRoPnOnAc(String roPnOnAc) {
		this.roPnOnAc = roPnOnAc;
	}

	public BigDecimal getScheduleAcDays() {
		return this.scheduleAcDays;
	}

	public void setScheduleAcDays(BigDecimal scheduleAcDays) {
		this.scheduleAcDays = scheduleAcDays;
	}

	public String getScrap() {
		return this.scrap;
	}

	public void setScrap(String scrap) {
		this.scrap = scrap;
	}

	public String getShipComplete() {
		return this.shipComplete;
	}

	public void setShipComplete(String shipComplete) {
		this.shipComplete = shipComplete;
	}

	public BigDecimal getShipDays() {
		return this.shipDays;
	}

	public void setShipDays(BigDecimal shipDays) {
		this.shipDays = shipDays;
	}

	public String getShippingAddress1() {
		return this.shippingAddress1;
	}

	public void setShippingAddress1(String shippingAddress1) {
		this.shippingAddress1 = shippingAddress1;
	}

	public String getShippingAddress2() {
		return this.shippingAddress2;
	}

	public void setShippingAddress2(String shippingAddress2) {
		this.shippingAddress2 = shippingAddress2;
	}

	public String getShippingCell() {
		return this.shippingCell;
	}

	public void setShippingCell(String shippingCell) {
		this.shippingCell = shippingCell;
	}

	public String getShippingCity() {
		return this.shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingCountry() {
		return this.shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public String getShippingEmail() {
		return this.shippingEmail;
	}

	public void setShippingEmail(String shippingEmail) {
		this.shippingEmail = shippingEmail;
	}

	public String getShippingFax() {
		return this.shippingFax;
	}

	public void setShippingFax(String shippingFax) {
		this.shippingFax = shippingFax;
	}

	public String getShippingMainContact() {
		return this.shippingMainContact;
	}

	public void setShippingMainContact(String shippingMainContact) {
		this.shippingMainContact = shippingMainContact;
	}

	public String getShippingPhone() {
		return this.shippingPhone;
	}

	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}

	public String getShippingPost() {
		return this.shippingPost;
	}

	public void setShippingPost(String shippingPost) {
		this.shippingPost = shippingPost;
	}

	public String getShippingSita() {
		return this.shippingSita;
	}

	public void setShippingSita(String shippingSita) {
		this.shippingSita = shippingSita;
	}

	public String getShippingState() {
		return this.shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSnToBeReceived() {
		return this.snToBeReceived;
	}

	public void setSnToBeReceived(String snToBeReceived) {
		this.snToBeReceived = snToBeReceived;
	}

	public String getSnToBeSend() {
		return this.snToBeSend;
	}

	public void setSnToBeSend(String snToBeSend) {
		this.snToBeSend = snToBeSend;
	}

	public BigDecimal getSoLine() {
		return this.soLine;
	}

	public void setSoLine(BigDecimal soLine) {
		this.soLine = soLine;
	}

	public BigDecimal getSoNumber() {
		return this.soNumber;
	}

	public void setSoNumber(BigDecimal soNumber) {
		this.soNumber = soNumber;
	}

	public String getSoType() {
		return this.soType;
	}

	public void setSoType(String soType) {
		this.soType = soType;
	}

	public String getSpec2000MessageAccepted() {
		return this.spec2000MessageAccepted;
	}

	public void setSpec2000MessageAccepted(String spec2000MessageAccepted) {
		this.spec2000MessageAccepted = spec2000MessageAccepted;
	}

	public BigDecimal getSpec2000PrevTransaction() {
		return this.spec2000PrevTransaction;
	}

	public void setSpec2000PrevTransaction(BigDecimal spec2000PrevTransaction) {
		this.spec2000PrevTransaction = spec2000PrevTransaction;
	}

	public String getSpec2000SentStatus() {
		return this.spec2000SentStatus;
	}

	public void setSpec2000SentStatus(String spec2000SentStatus) {
		this.spec2000SentStatus = spec2000SentStatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSvControl() {
		return this.svControl;
	}

	public void setSvControl(String svControl) {
		this.svControl = svControl;
	}

	public String getTaskCard() {
		return this.taskCard;
	}

	public void setTaskCard(String taskCard) {
		this.taskCard = taskCard;
	}

	public BigDecimal getTaxCost() {
		return this.taxCost;
	}

	public void setTaxCost(BigDecimal taxCost) {
		this.taxCost = taxCost;
	}

	public BigDecimal getTaxCostCurrency() {
		return this.taxCostCurrency;
	}

	public void setTaxCostCurrency(BigDecimal taxCostCurrency) {
		this.taxCostCurrency = taxCostCurrency;
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

	public String getToBin() {
		return this.toBin;
	}

	public void setToBin(String toBin) {
		this.toBin = toBin;
	}

	public BigDecimal getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getTraxTransaction() {
		return this.traxTransaction;
	}

	public void setTraxTransaction(BigDecimal traxTransaction) {
		this.traxTransaction = traxTransaction;
	}

	public BigDecimal getUnitCost() {
		return this.unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public BigDecimal getUnitCostCurrency() {
		return this.unitCostCurrency;
	}

	public void setUnitCostCurrency(BigDecimal unitCostCurrency) {
		this.unitCostCurrency = unitCostCurrency;
	}

	public BigDecimal getUnitListPrice() {
		return this.unitListPrice;
	}

	public void setUnitListPrice(BigDecimal unitListPrice) {
		this.unitListPrice = unitListPrice;
	}

	public String getUom() {
		return this.uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getUsCode() {
		return this.usCode;
	}

	public void setUsCode(String usCode) {
		this.usCode = usCode;
	}

	public String getVatCode() {
		return this.vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}

	public String getWarrantyClaim() {
		return this.warrantyClaim;
	}

	public void setWarrantyClaim(String warrantyClaim) {
		this.warrantyClaim = warrantyClaim;
	}

	public Date getWarrantyClaimDate() {
		return this.warrantyClaimDate;
	}

	public void setWarrantyClaimDate(Date warrantyClaimDate) {
		this.warrantyClaimDate = warrantyClaimDate;
	}

	public String getWarrantyClaimOrderItemType() {
		return this.warrantyClaimOrderItemType;
	}

	public void setWarrantyClaimOrderItemType(String warrantyClaimOrderItemType) {
		this.warrantyClaimOrderItemType = warrantyClaimOrderItemType;
	}

	public String getWarrantyClaimPrint() {
		return this.warrantyClaimPrint;
	}

	public void setWarrantyClaimPrint(String warrantyClaimPrint) {
		this.warrantyClaimPrint = warrantyClaimPrint;
	}

	public String getWarrantyClaimStatus() {
		return this.warrantyClaimStatus;
	}

	public void setWarrantyClaimStatus(String warrantyClaimStatus) {
		this.warrantyClaimStatus = warrantyClaimStatus;
	}

	public String getWarrantyClaimTransaction() {
		return this.warrantyClaimTransaction;
	}

	public void setWarrantyClaimTransaction(String warrantyClaimTransaction) {
		this.warrantyClaimTransaction = warrantyClaimTransaction;
	}

	public BigDecimal getWarrantyCycles() {
		return this.warrantyCycles;
	}

	public void setWarrantyCycles(BigDecimal warrantyCycles) {
		this.warrantyCycles = warrantyCycles;
	}

	public BigDecimal getWarrantyDays() {
		return this.warrantyDays;
	}

	public void setWarrantyDays(BigDecimal warrantyDays) {
		this.warrantyDays = warrantyDays;
	}

	public BigDecimal getWarrantyGoodsRcvdBatch() {
		return this.warrantyGoodsRcvdBatch;
	}

	public void setWarrantyGoodsRcvdBatch(BigDecimal warrantyGoodsRcvdBatch) {
		this.warrantyGoodsRcvdBatch = warrantyGoodsRcvdBatch;
	}

	public BigDecimal getWarrantyHours() {
		return this.warrantyHours;
	}

	public void setWarrantyHours(BigDecimal warrantyHours) {
		this.warrantyHours = warrantyHours;
	}

	public BigDecimal getWarrantyLine() {
		return this.warrantyLine;
	}

	public void setWarrantyLine(BigDecimal warrantyLine) {
		this.warrantyLine = warrantyLine;
	}

	public BigDecimal getWo() {
		return this.wo;
	}

	public void setWo(BigDecimal wo) {
		this.wo = wo;
	}

	public OrderHeader getOrderHeader() {
		return this.orderHeader;
	}

	public void setOrderHeader(OrderHeader orderHeader) {
		this.orderHeader = orderHeader;
	}

	public Date getInterfaceSyncDate() {
		return interfaceSyncDate;
	}

	public void setInterfaceSyncDate(Date interfaceSyncDate) {
		this.interfaceSyncDate = interfaceSyncDate;
	}

	public String getInterfaceSyncFlag() {
		return interfaceSyncFlag;
	}

	public void setInterfaceSyncFlag(String interfaceSyncFlag) {
		this.interfaceSyncFlag = interfaceSyncFlag;
	}

	public String getExternalKPRNumber() {
		return externalKPRNumber;
	}

	public void setExternalKPRNumber(String externalKPRNumber) {
		this.externalKPRNumber = externalKPRNumber;
	}

	public String getExternalPRItem() {
		return externalPRItem;
	}

	public void setExternalPRItem(String externalPRItem) {
		this.externalPRItem = externalPRItem;
	}

	public String getExternalReleaseStrategy() {
		return externalReleaseStrategy;
	}

	public void setExternalReleaseStrategy(String externalReleaseStrategy) {
		this.externalReleaseStrategy = externalReleaseStrategy;
	}

}