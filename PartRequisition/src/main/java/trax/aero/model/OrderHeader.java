package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ORDER_HEADER database table.
 * 
 */
@Entity
@Table(name="ORDER_HEADER")
@NamedQuery(name="OrderHeader.findAll", query="SELECT o FROM OrderHeader o")
public class OrderHeader implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrderHeaderPK id;

	@Column(name="ACCESS_CONTROL")
	private String accessControl;

	private String amendment;

	private String attention;

	@Column(name="\"AUTHORIZATION\"")
	private String authorization;

	@Column(name="AUTHORIZATION_BY")
	private String authorizationBy;

	@Column(name="AUTHORIZATION_DATE")
	private Date authorizationDate;

	@Column(name="AUTHORIZED_EMAIL")
	private String authorizedEmail;

	@Column(name="B2B_ID")
	private String b2bId;

	@Column(name="BILL_TO_LOCATION")
	private String billToLocation;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	private String category;

	private String comfirmation;

	@Column(name="CONTRACT_ORDER_NUMBER")
	private BigDecimal contractOrderNumber;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	private String currency;

	@Column(name="CURRENCY_EXCHANGE")
	private BigDecimal currencyExchange;

	@Column(name="CUSTOMER_BILL_TO")
	private String customerBillTo;

	@Column(name="CUSTOMER_PO")
	private String customerPo;

	@Column(name="CUSTOMER_QUOTE")
	private String customerQuote;

	private String expenditure;

	private String fob;

	@Column(name="IN_USE")
	private String inUse;

	@Column(name="IN_USED_BY")
	private String inUsedBy;

	@Column(name="INTER_COMPANY")
	private String interCompany;

	@Column(name="INTERFACE_CREATED_DATE")
	private Date interfaceCreatedDate;

	@Column(name="INTERFACE_MODIFIED_DATE")
	private Date interfaceModifiedDate;

	@Column(name="INTERFACE_TRNSFR_FINANCE_BRZL")
	private Date interfaceTrnsfrFinanceBrzl;

	@Column(name="INVENTORY_TYPE")
	private String inventoryType;

	@Column(name="INVOICEWORKS_TRANSACTION")
	private BigDecimal invoiceworksTransaction;

	@Column(name="ISSUED_BY")
	private String issuedBy;

	@Column(name="LEGACY_SYSTEM_ORDER_NUMBER")
	private String legacySystemOrderNumber;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="NO_OF_PRINT")
	private BigDecimal noOfPrint;

	private BigDecimal notes;

	@Column(name="ORDER_ADDRESS_1")
	private String orderAddress1;

	@Column(name="ORDER_ADDRESS_2")
	private String orderAddress2;

	@Column(name="ORDER_CATEGORY")
	private String orderCategory;

	@Column(name="ORDER_CELL")
	private String orderCell;

	@Column(name="ORDER_CITY")
	private String orderCity;

	@Column(name="ORDER_COUNTRY")
	private String orderCountry;

	@Column(name="ORDER_EMAIL")
	private String orderEmail;

	@Column(name="ORDER_FAX")
	private String orderFax;

	@Column(name="ORDER_OPTION1")
	private String orderOption1;

	@Column(name="ORDER_OPTION10")
	private String orderOption10;

	@Column(name="ORDER_OPTION2")
	private String orderOption2;

	@Column(name="ORDER_OPTION3")
	private String orderOption3;

	@Column(name="ORDER_OPTION4")
	private String orderOption4;

	@Column(name="ORDER_OPTION5")
	private String orderOption5;

	@Column(name="ORDER_OPTION6")
	private String orderOption6;

	@Column(name="ORDER_OPTION7")
	private String orderOption7;

	@Column(name="ORDER_OPTION8")
	private String orderOption8;

	@Column(name="ORDER_OPTION9")
	private String orderOption9;

	@Column(name="ORDER_PHONE")
	private String orderPhone;

	@Column(name="ORDER_POST")
	private String orderPost;

	@Column(name="ORDER_SENT_TO_VENDOR")
	private String orderSentToVendor;

	@Column(name="ORDER_SITA")
	private String orderSita;

	@Column(name="ORDER_STATE")
	private String orderState;

	@Column(name="ORIG_CREATED_DATE")
	private Date origCreatedDate;

	@Column(name="OS_SENT_STATUS")
	private String osSentStatus;

	@Column(name="OVERRIDE_ADDRESS")
	private String overrideAddress;

	private String owner;

	@Column(name="PICKLIST_FLAG")
	private String picklistFlag;

	private String priority;

	@Column(name="RELATION_CODE")
	private String relationCode;

	@Column(name="RELEASE_FOR_AUTHORIZATION")
	private String releaseForAuthorization;

	@Column(name="RELEASE_FOR_AUTHORIZATION_ON")
	private Date releaseForAuthorizationOn;

	@Column(name="REQUESTER_LOCATION")
	private String requesterLocation;

	@Column(name="SHIP_VIA")
	private String shipVia;

	@Column(name="SHIP_VIA_ACCOUNT")
	private String shipViaAccount;

	@Column(name="SHIP_VIA_DATE")
	private Date shipViaDate;

	@Column(name="SHIP_VIA_REMARKS")
	private String shipViaRemarks;

	@Column(name="SHIPPED_FROM_LOCATION")
	private String shippedFromLocation;

	@Column(name="SPEC2000_PREV_TRANSACTION")
	private BigDecimal spec2000PrevTransaction;

	@Column(name="SPEC2000_SENT_STATUS")
	private String spec2000SentStatus;

	@Column(name="SPEC2K_TRANS")
	private String spec2kTrans;

	private String status;

	@Column(name="SUPPLIED_SN")
	private String suppliedSn;

	private String terms;

	@Column(name="TOTAL_COST")
	private BigDecimal totalCost;

	@Column(name="TRAX_TRANSACTION")
	private BigDecimal traxTransaction;

	@Column(name="\"TYPE\"")
	private String type;

	private BigDecimal wo;

	//bi-directional many-to-one association to OrderDetail
	@OneToMany(mappedBy="orderHeader")
	private List<OrderDetail> orderDetails;

	public OrderHeader() {
	}

	public OrderHeaderPK getId() {
		return this.id;
	}

	public void setId(OrderHeaderPK id) {
		this.id = id;
	}

	public String getAccessControl() {
		return this.accessControl;
	}

	public void setAccessControl(String accessControl) {
		this.accessControl = accessControl;
	}

	public String getAmendment() {
		return this.amendment;
	}

	public void setAmendment(String amendment) {
		this.amendment = amendment;
	}

	public String getAttention() {
		return this.attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public String getAuthorization() {
		return this.authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getAuthorizationBy() {
		return this.authorizationBy;
	}

	public void setAuthorizationBy(String authorizationBy) {
		this.authorizationBy = authorizationBy;
	}

	public Date getAuthorizationDate() {
		return this.authorizationDate;
	}

	public void setAuthorizationDate(Date authorizationDate) {
		this.authorizationDate = authorizationDate;
	}

	public String getAuthorizedEmail() {
		return this.authorizedEmail;
	}

	public void setAuthorizedEmail(String authorizedEmail) {
		this.authorizedEmail = authorizedEmail;
	}

	public String getB2bId() {
		return this.b2bId;
	}

	public void setB2bId(String b2bId) {
		this.b2bId = b2bId;
	}

	public String getBillToLocation() {
		return this.billToLocation;
	}

	public void setBillToLocation(String billToLocation) {
		this.billToLocation = billToLocation;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getComfirmation() {
		return this.comfirmation;
	}

	public void setComfirmation(String comfirmation) {
		this.comfirmation = comfirmation;
	}

	public BigDecimal getContractOrderNumber() {
		return this.contractOrderNumber;
	}

	public void setContractOrderNumber(BigDecimal contractOrderNumber) {
		this.contractOrderNumber = contractOrderNumber;
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

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getCurrencyExchange() {
		return this.currencyExchange;
	}

	public void setCurrencyExchange(BigDecimal currencyExchange) {
		this.currencyExchange = currencyExchange;
	}

	public String getCustomerBillTo() {
		return this.customerBillTo;
	}

	public void setCustomerBillTo(String customerBillTo) {
		this.customerBillTo = customerBillTo;
	}

	public String getCustomerPo() {
		return this.customerPo;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}

	public String getCustomerQuote() {
		return this.customerQuote;
	}

	public void setCustomerQuote(String customerQuote) {
		this.customerQuote = customerQuote;
	}

	public String getExpenditure() {
		return this.expenditure;
	}

	public void setExpenditure(String expenditure) {
		this.expenditure = expenditure;
	}

	public String getFob() {
		return this.fob;
	}

	public void setFob(String fob) {
		this.fob = fob;
	}

	public String getInUse() {
		return this.inUse;
	}

	public void setInUse(String inUse) {
		this.inUse = inUse;
	}

	public String getInUsedBy() {
		return this.inUsedBy;
	}

	public void setInUsedBy(String inUsedBy) {
		this.inUsedBy = inUsedBy;
	}

	public String getInterCompany() {
		return this.interCompany;
	}

	public void setInterCompany(String interCompany) {
		this.interCompany = interCompany;
	}

	public Date getInterfaceCreatedDate() {
		return this.interfaceCreatedDate;
	}

	public void setInterfaceCreatedDate(Date interfaceCreatedDate) {
		this.interfaceCreatedDate = interfaceCreatedDate;
	}

	public Date getInterfaceModifiedDate() {
		return this.interfaceModifiedDate;
	}

	public void setInterfaceModifiedDate(Date interfaceModifiedDate) {
		this.interfaceModifiedDate = interfaceModifiedDate;
	}

	public Date getInterfaceTrnsfrFinanceBrzl() {
		return this.interfaceTrnsfrFinanceBrzl;
	}

	public void setInterfaceTrnsfrFinanceBrzl(Date interfaceTrnsfrFinanceBrzl) {
		this.interfaceTrnsfrFinanceBrzl = interfaceTrnsfrFinanceBrzl;
	}

	public String getInventoryType() {
		return this.inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public BigDecimal getInvoiceworksTransaction() {
		return this.invoiceworksTransaction;
	}

	public void setInvoiceworksTransaction(BigDecimal invoiceworksTransaction) {
		this.invoiceworksTransaction = invoiceworksTransaction;
	}

	public String getIssuedBy() {
		return this.issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getLegacySystemOrderNumber() {
		return this.legacySystemOrderNumber;
	}

	public void setLegacySystemOrderNumber(String legacySystemOrderNumber) {
		this.legacySystemOrderNumber = legacySystemOrderNumber;
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

	public BigDecimal getNoOfPrint() {
		return this.noOfPrint;
	}

	public void setNoOfPrint(BigDecimal noOfPrint) {
		this.noOfPrint = noOfPrint;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public String getOrderAddress1() {
		return this.orderAddress1;
	}

	public void setOrderAddress1(String orderAddress1) {
		this.orderAddress1 = orderAddress1;
	}

	public String getOrderAddress2() {
		return this.orderAddress2;
	}

	public void setOrderAddress2(String orderAddress2) {
		this.orderAddress2 = orderAddress2;
	}

	public String getOrderCategory() {
		return this.orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

	public String getOrderCell() {
		return this.orderCell;
	}

	public void setOrderCell(String orderCell) {
		this.orderCell = orderCell;
	}

	public String getOrderCity() {
		return this.orderCity;
	}

	public void setOrderCity(String orderCity) {
		this.orderCity = orderCity;
	}

	public String getOrderCountry() {
		return this.orderCountry;
	}

	public void setOrderCountry(String orderCountry) {
		this.orderCountry = orderCountry;
	}

	public String getOrderEmail() {
		return this.orderEmail;
	}

	public void setOrderEmail(String orderEmail) {
		this.orderEmail = orderEmail;
	}

	public String getOrderFax() {
		return this.orderFax;
	}

	public void setOrderFax(String orderFax) {
		this.orderFax = orderFax;
	}

	public String getOrderOption1() {
		return this.orderOption1;
	}

	public void setOrderOption1(String orderOption1) {
		this.orderOption1 = orderOption1;
	}

	public String getOrderOption10() {
		return this.orderOption10;
	}

	public void setOrderOption10(String orderOption10) {
		this.orderOption10 = orderOption10;
	}

	public String getOrderOption2() {
		return this.orderOption2;
	}

	public void setOrderOption2(String orderOption2) {
		this.orderOption2 = orderOption2;
	}

	public String getOrderOption3() {
		return this.orderOption3;
	}

	public void setOrderOption3(String orderOption3) {
		this.orderOption3 = orderOption3;
	}

	public String getOrderOption4() {
		return this.orderOption4;
	}

	public void setOrderOption4(String orderOption4) {
		this.orderOption4 = orderOption4;
	}

	public String getOrderOption5() {
		return this.orderOption5;
	}

	public void setOrderOption5(String orderOption5) {
		this.orderOption5 = orderOption5;
	}

	public String getOrderOption6() {
		return this.orderOption6;
	}

	public void setOrderOption6(String orderOption6) {
		this.orderOption6 = orderOption6;
	}

	public String getOrderOption7() {
		return this.orderOption7;
	}

	public void setOrderOption7(String orderOption7) {
		this.orderOption7 = orderOption7;
	}

	public String getOrderOption8() {
		return this.orderOption8;
	}

	public void setOrderOption8(String orderOption8) {
		this.orderOption8 = orderOption8;
	}

	public String getOrderOption9() {
		return this.orderOption9;
	}

	public void setOrderOption9(String orderOption9) {
		this.orderOption9 = orderOption9;
	}

	public String getOrderPhone() {
		return this.orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public String getOrderPost() {
		return this.orderPost;
	}

	public void setOrderPost(String orderPost) {
		this.orderPost = orderPost;
	}

	public String getOrderSentToVendor() {
		return this.orderSentToVendor;
	}

	public void setOrderSentToVendor(String orderSentToVendor) {
		this.orderSentToVendor = orderSentToVendor;
	}

	public String getOrderSita() {
		return this.orderSita;
	}

	public void setOrderSita(String orderSita) {
		this.orderSita = orderSita;
	}

	public String getOrderState() {
		return this.orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Date getOrigCreatedDate() {
		return this.origCreatedDate;
	}

	public void setOrigCreatedDate(Date origCreatedDate) {
		this.origCreatedDate = origCreatedDate;
	}

	public String getOsSentStatus() {
		return this.osSentStatus;
	}

	public void setOsSentStatus(String osSentStatus) {
		this.osSentStatus = osSentStatus;
	}

	public String getOverrideAddress() {
		return this.overrideAddress;
	}

	public void setOverrideAddress(String overrideAddress) {
		this.overrideAddress = overrideAddress;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPicklistFlag() {
		return this.picklistFlag;
	}

	public void setPicklistFlag(String picklistFlag) {
		this.picklistFlag = picklistFlag;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getRelationCode() {
		return this.relationCode;
	}

	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	public String getReleaseForAuthorization() {
		return this.releaseForAuthorization;
	}

	public void setReleaseForAuthorization(String releaseForAuthorization) {
		this.releaseForAuthorization = releaseForAuthorization;
	}

	public Date getReleaseForAuthorizationOn() {
		return this.releaseForAuthorizationOn;
	}

	public void setReleaseForAuthorizationOn(Date releaseForAuthorizationOn) {
		this.releaseForAuthorizationOn = releaseForAuthorizationOn;
	}

	public String getRequesterLocation() {
		return this.requesterLocation;
	}

	public void setRequesterLocation(String requesterLocation) {
		this.requesterLocation = requesterLocation;
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

	public Date getShipViaDate() {
		return this.shipViaDate;
	}

	public void setShipViaDate(Date shipViaDate) {
		this.shipViaDate = shipViaDate;
	}

	public String getShipViaRemarks() {
		return this.shipViaRemarks;
	}

	public void setShipViaRemarks(String shipViaRemarks) {
		this.shipViaRemarks = shipViaRemarks;
	}

	public String getShippedFromLocation() {
		return this.shippedFromLocation;
	}

	public void setShippedFromLocation(String shippedFromLocation) {
		this.shippedFromLocation = shippedFromLocation;
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

	public String getSpec2kTrans() {
		return this.spec2kTrans;
	}

	public void setSpec2kTrans(String spec2kTrans) {
		this.spec2kTrans = spec2kTrans;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSuppliedSn() {
		return this.suppliedSn;
	}

	public void setSuppliedSn(String suppliedSn) {
		this.suppliedSn = suppliedSn;
	}

	public String getTerms() {
		return this.terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getWo() {
		return this.wo;
	}

	public void setWo(BigDecimal wo) {
		this.wo = wo;
	}

	public List<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetail addOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().add(orderDetail);
		orderDetail.setOrderHeader(this);

		return orderDetail;
	}

	public OrderDetail removeOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().remove(orderDetail);
		orderDetail.setOrderHeader(null);

		return orderDetail;
	}

}