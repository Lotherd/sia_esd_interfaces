package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WO_ACTUALS database table.
 * 
 */
@Entity
@Table(name="WO_ACTUALS")
@NamedQuery(name="WoActual.findAll", query="SELECT w FROM WoActual w")
public class WoActual implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WoActualPK id;

	@Column(name="ADD_BILL_CURR_AMOUNT")
	private BigDecimal addBillCurrAmount;

	@Column(name="ADD_BILL_CURRENCY")
	private String addBillCurrency;

	@Column(name="ADD_BILL_EXCHANGE_RATE")
	private BigDecimal addBillExchangeRate;

	private BigDecimal batch;

	@Column(name="BILLED_HOURS")
	private BigDecimal billedHours;

	@Column(name="BILLED_MINUTES")
	private BigDecimal billedMinutes;

	@Column(name="BILLED_PN_CATEGORY")
	private String billedPnCategory;

	@Column(name="BILLED_QTY")
	private BigDecimal billedQty;

	@Column(name="BILLED_SKILL")
	private String billedSkill;

	@Column(name="BOOK_TO_ORDER_LINE")
	private BigDecimal bookToOrderLine;

	@Column(name="BOOK_TO_ORDER_NUMBER")
	private BigDecimal bookToOrderNumber;

	@Column(name="BOOK_TO_ORDER_TYPE")
	private String bookToOrderType;

	@Column(name="CAP_AMOUNT")
	private BigDecimal capAmount;

	@Column(name="CAP_HRS")
	private BigDecimal capHrs;

	@Column(name="CAP_LIMIT_FLAG")
	private String capLimitFlag;

	private String category;

	private String checked;

	@Column(name="CHECKED_BY")
	private String checkedBy;

	@Column(name="CHECKED_DATE")
	private Date checkedDate;

	private String condition;

	@Column(name="CONTRACT_COST")
	private BigDecimal contractCost;

	@Column(name="COST_CENTER")
	private String costCenter;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CURRENCY_EXCHANGE_LIST")
	private BigDecimal currencyExchangeList;

	@Column(name="CURRENCY_LIST")
	private String currencyList;

	private String customer;

	@Column(name="CUSTOMER_QUOTED")
	private String customerQuoted;

	@Column(name="CUSTOMER_QUOTED_VALUE")
	private BigDecimal customerQuotedValue;

	private String description;

	@Column(name="DO_NOT_INVOICE")
	private String doNotInvoice;

	private String employee;

	@Column(name="EMPLOYEE_ISSUE_TO")
	private String employeeIssueTo;

	@Column(name="END_HOUR")
	private BigDecimal endHour;

	@Column(name="END_MINUTE")
	private BigDecimal endMinute;

	@Column(name="EXCLUDE_CONTRACT")
	private String excludeContract;

	@Column(name="EXCLUDE_TYPE")
	private String excludeType;

	@Column(name="FLAT_SELL")
	private String flatSell;

	@Column(name="GL_COMPANY_COS")
	private String glCompanyCos;

	@Column(name="GL_COS")
	private String glCos;

	@Column(name="GL_COST_CENTER_COS")
	private String glCostCenterCos;

	@Column(name="GL_EXPENDITURE_COS")
	private String glExpenditureCos;

	@Column(name="\"GROUP\"")
	private String group;

	@Column(name="GROUP_NO")
	private String groupNo;

	private BigDecimal handling;

	@Column(name="HANDLING_CURRENCY")
	private BigDecimal handlingCurrency;

	@Column(name="\"HOURS\"")
	private BigDecimal hours;

	@Column(name="INTERFACE_TRANSFER_BY")
	private String interfaceTransferBy;

	@Column(name="INTERFACE_TRANSFER_DATE")
	private Date interfaceTransferDate;

	private BigDecimal invoice;

	@Column(name="LABOR_MANUAL")
	private String laborManual;

	@Column(name="LIST_PRICE")
	private BigDecimal listPrice;

	@Column(name="LIST_PRICE_CURRENCY")
	private BigDecimal listPriceCurrency;

	private String location;

	private BigDecimal markup;

	@Column(name="MARKUP_AFTER_RATE")
	private String markupAfterRate;

	@Column(name="MARKUP_PERCENT")
	private BigDecimal markupPercent;

	@Column(name="\"MINUTES\"")
	private BigDecimal minutes;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="NON_ROUTINE")
	private String nonRoutine;

	private BigDecimal notes;

	@Column(name="ORDER_NUMBER")
	private BigDecimal orderNumber;

	@Column(name="ORDER_TYPE")
	private String orderType;

	private String owner;

	@Column(name="PARENT_TRANSACTION")
	private String parentTransaction;

	private String pn;

	@Column(name="PN_CATEGORY")
	private String pnCategory;

	@Column(name="PN_FLAT")
	private String pnFlat;

	@Column(name="PN_TRANSACTION_NO")
	private BigDecimal pnTransactionNo;

	@Column(name="PRE_PAID_REVERSE")
	private String prePaidReverse;

	private BigDecimal qty;

	@Column(name="QUOTE_RECORD")
	private String quoteRecord;

	@Column(name="RECORD_TYPE")
	private String recordType;

	private String rego;

	@Column(name="REGO_COMMENTS")
	private String regoComments;

	@Column(name="SCHED_EO")
	private String schedEo;

	@Column(name="SCHED_EO_CATEGORY")
	private String schedEoCategory;

	@Column(name="SCHED_TASK_CARD")
	private String schedTaskCard;

	@Column(name="SCHED_TASK_CARD_CAT")
	private String schedTaskCardCat;

	private String skill;

	private String sn;

	@Column(name="START_HOUR")
	private BigDecimal startHour;

	@Column(name="START_MINUTE")
	private BigDecimal startMinute;

	private String status;

	@Column(name="SUB_WO")
	private BigDecimal subWo;

	@Column(name="TASK_CARD")
	private String taskCard;

	@Column(name="TASK_CARD_ITEM")
	private BigDecimal taskCardItem;

	@Column(name="TASK_CARD_PN")
	private String taskCardPn;

	@Column(name="TASK_CARD_SN")
	private String taskCardSn;

	@Column(name="TOTAL_COST")
	private BigDecimal totalCost;

	@Column(name="TOTAL_HANDLING")
	private BigDecimal totalHandling;

	@Column(name="TOTAL_SELL")
	private BigDecimal totalSell;

	@Column(name="TOTAL_SELL_CURRENCY")
	private BigDecimal totalSellCurrency;

	@Column(name="TRANSACTION_DATE")
	private Date transactionDate;

	@Column(name="TRANSACTION_NO_INVENTORY")
	private BigDecimal transactionNoInventory;

	@Column(name="TRANSFER_FROM_INVOICE")
	private BigDecimal transferFromInvoice;

	@Column(name="UNIT_COST")
	private BigDecimal unitCost;

	@Column(name="UNIT_SELL")
	private BigDecimal unitSell;

	@Column(name="UNIT_SELL_B")
	private BigDecimal unitSellB;

	@Column(name="UNIT_SELL_B_CURRENCY")
	private BigDecimal unitSellBCurrency;

	@Column(name="UNIT_SELL_CURRENCY")
	private BigDecimal unitSellCurrency;

	@Column(name="UNIT_SELL_CURRENCY_LIST")
	private BigDecimal unitSellCurrencyList;

	@Column(name="UNIT_SELL_OVERRIDE")
	private String unitSellOverride;

	@Column(name="VAT_CODE")
	private String vatCode;

	private BigDecimal wo;

	@Column(name="WO_DISCOUNT")
	private BigDecimal woDiscount;

	@Column(name="WO_ITEM_SEQ")
	private BigDecimal woItemSeq;

	public WoActual() {
	}

	public WoActualPK getId() {
		return this.id;
	}

	public void setId(WoActualPK id) {
		this.id = id;
	}

	public BigDecimal getAddBillCurrAmount() {
		return this.addBillCurrAmount;
	}

	public void setAddBillCurrAmount(BigDecimal addBillCurrAmount) {
		this.addBillCurrAmount = addBillCurrAmount;
	}

	public String getAddBillCurrency() {
		return this.addBillCurrency;
	}

	public void setAddBillCurrency(String addBillCurrency) {
		this.addBillCurrency = addBillCurrency;
	}

	public BigDecimal getAddBillExchangeRate() {
		return this.addBillExchangeRate;
	}

	public void setAddBillExchangeRate(BigDecimal addBillExchangeRate) {
		this.addBillExchangeRate = addBillExchangeRate;
	}

	public BigDecimal getBatch() {
		return this.batch;
	}

	public void setBatch(BigDecimal batch) {
		this.batch = batch;
	}

	public BigDecimal getBilledHours() {
		return this.billedHours;
	}

	public void setBilledHours(BigDecimal billedHours) {
		this.billedHours = billedHours;
	}

	public BigDecimal getBilledMinutes() {
		return this.billedMinutes;
	}

	public void setBilledMinutes(BigDecimal billedMinutes) {
		this.billedMinutes = billedMinutes;
	}

	public String getBilledPnCategory() {
		return this.billedPnCategory;
	}

	public void setBilledPnCategory(String billedPnCategory) {
		this.billedPnCategory = billedPnCategory;
	}

	public BigDecimal getBilledQty() {
		return this.billedQty;
	}

	public void setBilledQty(BigDecimal billedQty) {
		this.billedQty = billedQty;
	}

	public String getBilledSkill() {
		return this.billedSkill;
	}

	public void setBilledSkill(String billedSkill) {
		this.billedSkill = billedSkill;
	}

	public BigDecimal getBookToOrderLine() {
		return this.bookToOrderLine;
	}

	public void setBookToOrderLine(BigDecimal bookToOrderLine) {
		this.bookToOrderLine = bookToOrderLine;
	}

	public BigDecimal getBookToOrderNumber() {
		return this.bookToOrderNumber;
	}

	public void setBookToOrderNumber(BigDecimal bookToOrderNumber) {
		this.bookToOrderNumber = bookToOrderNumber;
	}

	public String getBookToOrderType() {
		return this.bookToOrderType;
	}

	public void setBookToOrderType(String bookToOrderType) {
		this.bookToOrderType = bookToOrderType;
	}

	public BigDecimal getCapAmount() {
		return this.capAmount;
	}

	public void setCapAmount(BigDecimal capAmount) {
		this.capAmount = capAmount;
	}

	public BigDecimal getCapHrs() {
		return this.capHrs;
	}

	public void setCapHrs(BigDecimal capHrs) {
		this.capHrs = capHrs;
	}

	public String getCapLimitFlag() {
		return this.capLimitFlag;
	}

	public void setCapLimitFlag(String capLimitFlag) {
		this.capLimitFlag = capLimitFlag;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getChecked() {
		return this.checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getCheckedBy() {
		return this.checkedBy;
	}

	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}

	public Date getCheckedDate() {
		return this.checkedDate;
	}

	public void setCheckedDate(Date checkedDate) {
		this.checkedDate = checkedDate;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public BigDecimal getContractCost() {
		return this.contractCost;
	}

	public void setContractCost(BigDecimal contractCost) {
		this.contractCost = contractCost;
	}

	public String getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
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

	public BigDecimal getCurrencyExchangeList() {
		return this.currencyExchangeList;
	}

	public void setCurrencyExchangeList(BigDecimal currencyExchangeList) {
		this.currencyExchangeList = currencyExchangeList;
	}

	public String getCurrencyList() {
		return this.currencyList;
	}

	public void setCurrencyList(String currencyList) {
		this.currencyList = currencyList;
	}

	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCustomerQuoted() {
		return this.customerQuoted;
	}

	public void setCustomerQuoted(String customerQuoted) {
		this.customerQuoted = customerQuoted;
	}

	public BigDecimal getCustomerQuotedValue() {
		return this.customerQuotedValue;
	}

	public void setCustomerQuotedValue(BigDecimal customerQuotedValue) {
		this.customerQuotedValue = customerQuotedValue;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDoNotInvoice() {
		return this.doNotInvoice;
	}

	public void setDoNotInvoice(String doNotInvoice) {
		this.doNotInvoice = doNotInvoice;
	}

	public String getEmployee() {
		return this.employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getEmployeeIssueTo() {
		return this.employeeIssueTo;
	}

	public void setEmployeeIssueTo(String employeeIssueTo) {
		this.employeeIssueTo = employeeIssueTo;
	}

	public BigDecimal getEndHour() {
		return this.endHour;
	}

	public void setEndHour(BigDecimal endHour) {
		this.endHour = endHour;
	}

	public BigDecimal getEndMinute() {
		return this.endMinute;
	}

	public void setEndMinute(BigDecimal endMinute) {
		this.endMinute = endMinute;
	}

	public String getExcludeContract() {
		return this.excludeContract;
	}

	public void setExcludeContract(String excludeContract) {
		this.excludeContract = excludeContract;
	}

	public String getExcludeType() {
		return this.excludeType;
	}

	public void setExcludeType(String excludeType) {
		this.excludeType = excludeType;
	}

	public String getFlatSell() {
		return this.flatSell;
	}

	public void setFlatSell(String flatSell) {
		this.flatSell = flatSell;
	}

	public String getGlCompanyCos() {
		return this.glCompanyCos;
	}

	public void setGlCompanyCos(String glCompanyCos) {
		this.glCompanyCos = glCompanyCos;
	}

	public String getGlCos() {
		return this.glCos;
	}

	public void setGlCos(String glCos) {
		this.glCos = glCos;
	}

	public String getGlCostCenterCos() {
		return this.glCostCenterCos;
	}

	public void setGlCostCenterCos(String glCostCenterCos) {
		this.glCostCenterCos = glCostCenterCos;
	}

	public String getGlExpenditureCos() {
		return this.glExpenditureCos;
	}

	public void setGlExpenditureCos(String glExpenditureCos) {
		this.glExpenditureCos = glExpenditureCos;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public BigDecimal getHandling() {
		return this.handling;
	}

	public void setHandling(BigDecimal handling) {
		this.handling = handling;
	}

	public BigDecimal getHandlingCurrency() {
		return this.handlingCurrency;
	}

	public void setHandlingCurrency(BigDecimal handlingCurrency) {
		this.handlingCurrency = handlingCurrency;
	}

	public BigDecimal getHours() {
		return this.hours;
	}

	public void setHours(BigDecimal hours) {
		this.hours = hours;
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

	public BigDecimal getInvoice() {
		return this.invoice;
	}

	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}

	public String getLaborManual() {
		return this.laborManual;
	}

	public void setLaborManual(String laborManual) {
		this.laborManual = laborManual;
	}

	public BigDecimal getListPrice() {
		return this.listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public BigDecimal getListPriceCurrency() {
		return this.listPriceCurrency;
	}

	public void setListPriceCurrency(BigDecimal listPriceCurrency) {
		this.listPriceCurrency = listPriceCurrency;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getMarkup() {
		return this.markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public String getMarkupAfterRate() {
		return this.markupAfterRate;
	}

	public void setMarkupAfterRate(String markupAfterRate) {
		this.markupAfterRate = markupAfterRate;
	}

	public BigDecimal getMarkupPercent() {
		return this.markupPercent;
	}

	public void setMarkupPercent(BigDecimal markupPercent) {
		this.markupPercent = markupPercent;
	}

	public BigDecimal getMinutes() {
		return this.minutes;
	}

	public void setMinutes(BigDecimal minutes) {
		this.minutes = minutes;
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

	public String getNonRoutine() {
		return this.nonRoutine;
	}

	public void setNonRoutine(String nonRoutine) {
		this.nonRoutine = nonRoutine;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public BigDecimal getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
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

	public String getParentTransaction() {
		return this.parentTransaction;
	}

	public void setParentTransaction(String parentTransaction) {
		this.parentTransaction = parentTransaction;
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

	public String getPnFlat() {
		return this.pnFlat;
	}

	public void setPnFlat(String pnFlat) {
		this.pnFlat = pnFlat;
	}

	public BigDecimal getPnTransactionNo() {
		return this.pnTransactionNo;
	}

	public void setPnTransactionNo(BigDecimal pnTransactionNo) {
		this.pnTransactionNo = pnTransactionNo;
	}

	public String getPrePaidReverse() {
		return this.prePaidReverse;
	}

	public void setPrePaidReverse(String prePaidReverse) {
		this.prePaidReverse = prePaidReverse;
	}

	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getQuoteRecord() {
		return this.quoteRecord;
	}

	public void setQuoteRecord(String quoteRecord) {
		this.quoteRecord = quoteRecord;
	}

	public String getRecordType() {
		return this.recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getRego() {
		return this.rego;
	}

	public void setRego(String rego) {
		this.rego = rego;
	}

	public String getRegoComments() {
		return this.regoComments;
	}

	public void setRegoComments(String regoComments) {
		this.regoComments = regoComments;
	}

	public String getSchedEo() {
		return this.schedEo;
	}

	public void setSchedEo(String schedEo) {
		this.schedEo = schedEo;
	}

	public String getSchedEoCategory() {
		return this.schedEoCategory;
	}

	public void setSchedEoCategory(String schedEoCategory) {
		this.schedEoCategory = schedEoCategory;
	}

	public String getSchedTaskCard() {
		return this.schedTaskCard;
	}

	public void setSchedTaskCard(String schedTaskCard) {
		this.schedTaskCard = schedTaskCard;
	}

	public String getSchedTaskCardCat() {
		return this.schedTaskCardCat;
	}

	public void setSchedTaskCardCat(String schedTaskCardCat) {
		this.schedTaskCardCat = schedTaskCardCat;
	}

	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public BigDecimal getStartHour() {
		return this.startHour;
	}

	public void setStartHour(BigDecimal startHour) {
		this.startHour = startHour;
	}

	public BigDecimal getStartMinute() {
		return this.startMinute;
	}

	public void setStartMinute(BigDecimal startMinute) {
		this.startMinute = startMinute;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getSubWo() {
		return this.subWo;
	}

	public void setSubWo(BigDecimal subWo) {
		this.subWo = subWo;
	}

	public String getTaskCard() {
		return this.taskCard;
	}

	public void setTaskCard(String taskCard) {
		this.taskCard = taskCard;
	}

	public BigDecimal getTaskCardItem() {
		return this.taskCardItem;
	}

	public void setTaskCardItem(BigDecimal taskCardItem) {
		this.taskCardItem = taskCardItem;
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

	public BigDecimal getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getTotalHandling() {
		return this.totalHandling;
	}

	public void setTotalHandling(BigDecimal totalHandling) {
		this.totalHandling = totalHandling;
	}

	public BigDecimal getTotalSell() {
		return this.totalSell;
	}

	public void setTotalSell(BigDecimal totalSell) {
		this.totalSell = totalSell;
	}

	public BigDecimal getTotalSellCurrency() {
		return this.totalSellCurrency;
	}

	public void setTotalSellCurrency(BigDecimal totalSellCurrency) {
		this.totalSellCurrency = totalSellCurrency;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getTransactionNoInventory() {
		return this.transactionNoInventory;
	}

	public void setTransactionNoInventory(BigDecimal transactionNoInventory) {
		this.transactionNoInventory = transactionNoInventory;
	}

	public BigDecimal getTransferFromInvoice() {
		return this.transferFromInvoice;
	}

	public void setTransferFromInvoice(BigDecimal transferFromInvoice) {
		this.transferFromInvoice = transferFromInvoice;
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

	public BigDecimal getUnitSellB() {
		return this.unitSellB;
	}

	public void setUnitSellB(BigDecimal unitSellB) {
		this.unitSellB = unitSellB;
	}

	public BigDecimal getUnitSellBCurrency() {
		return this.unitSellBCurrency;
	}

	public void setUnitSellBCurrency(BigDecimal unitSellBCurrency) {
		this.unitSellBCurrency = unitSellBCurrency;
	}

	public BigDecimal getUnitSellCurrency() {
		return this.unitSellCurrency;
	}

	public void setUnitSellCurrency(BigDecimal unitSellCurrency) {
		this.unitSellCurrency = unitSellCurrency;
	}

	public BigDecimal getUnitSellCurrencyList() {
		return this.unitSellCurrencyList;
	}

	public void setUnitSellCurrencyList(BigDecimal unitSellCurrencyList) {
		this.unitSellCurrencyList = unitSellCurrencyList;
	}

	public String getUnitSellOverride() {
		return this.unitSellOverride;
	}

	public void setUnitSellOverride(String unitSellOverride) {
		this.unitSellOverride = unitSellOverride;
	}

	public String getVatCode() {
		return this.vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}

	public BigDecimal getWo() {
		return this.wo;
	}

	public void setWo(BigDecimal wo) {
		this.wo = wo;
	}

	public BigDecimal getWoDiscount() {
		return this.woDiscount;
	}

	public void setWoDiscount(BigDecimal woDiscount) {
		this.woDiscount = woDiscount;
	}

	public BigDecimal getWoItemSeq() {
		return this.woItemSeq;
	}

	public void setWoItemSeq(BigDecimal woItemSeq) {
		this.woItemSeq = woItemSeq;
	}

}