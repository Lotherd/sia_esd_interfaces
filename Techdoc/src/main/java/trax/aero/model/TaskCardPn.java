package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TASK_CARD_PN database table.
 * 
 */
@Entity
@Table(name="TASK_CARD_PN")
@NamedQuery(name="TaskCardPn.findAll", query="SELECT t FROM TaskCardPn t")
public class TaskCardPn implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TaskCardPnPK id;

	private String authority;

	@Column(name="AUTHORITY_PHONE")
	private String authorityPhone;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="FINAL_ROUTINE")
	private String finalRoutine;

	@Column(name="FLEET_INVOLVEMENT_BLOCK")
	private String fleetInvolvementBlock;

	@Column(name="IN_SHOP")
	private String inShop;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="OFF_AC")
	private String offAc;

	private String originator;

	@Column(name="ORIGINATOR_PHONE")
	private String originatorPhone;

	@Column(name="PN_BY_REFERENCE")
	private String pnByReference;

	private BigDecimal qty;

	@Column(name="REAL_PART_NUMBER")
	private String realPartNumber;

	@Column(name="REPAIR_RETURN_SHOP_ACTION")
	private String repairReturnShopAction;

	private String reserve;

	@Column(name="RETURN_TO_DOCK")
	private String returnToDock;

	@Column(name="SHOP_1")
	private String shop1;

	@Column(name="SHOP_10")
	private String shop10;

	@Column(name="SHOP_2")
	private String shop2;

	@Column(name="SHOP_3")
	private String shop3;

	@Column(name="SHOP_4")
	private String shop4;

	@Column(name="SHOP_5")
	private String shop5;

	@Column(name="SHOP_6")
	private String shop6;

	@Column(name="SHOP_7")
	private String shop7;

	@Column(name="SHOP_8")
	private String shop8;

	@Column(name="SHOP_9")
	private String shop9;

	private String spare;

	@Column(name="SURPLUS_QTY")
	private BigDecimal surplusQty;

	@Column(name="TAG_MESSAGE")
	private String tagMessage;

	@Column(name="TAG_QTY")
	private BigDecimal tagQty;

	@Column(name="TAG_TYPE")
	private String tagType;

	@Column(name="TASK_CARD_X_REF")
	private String taskCardXRef;

	@Column(name="TASK_CARD_X_REF_DESCRIPTION")
	private String taskCardXRefDescription;

	@Column(name="UNIT_OF_ISSUE")
	private String unitOfIssue;

	//bi-directional many-to-one association to TaskCard
	@ManyToOne
	@JoinColumn(name="TASK_CARD", insertable=false, updatable=false)
	private TaskCard taskCardBean;

	public TaskCardPn() {
	}

	public TaskCardPnPK getId() {
		return this.id;
	}

	public void setId(TaskCardPnPK id) {
		this.id = id;
	}

	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getAuthorityPhone() {
		return this.authorityPhone;
	}

	public void setAuthorityPhone(String authorityPhone) {
		this.authorityPhone = authorityPhone;
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

	public String getFinalRoutine() {
		return this.finalRoutine;
	}

	public void setFinalRoutine(String finalRoutine) {
		this.finalRoutine = finalRoutine;
	}

	public String getFleetInvolvementBlock() {
		return this.fleetInvolvementBlock;
	}

	public void setFleetInvolvementBlock(String fleetInvolvementBlock) {
		this.fleetInvolvementBlock = fleetInvolvementBlock;
	}

	public String getInShop() {
		return this.inShop;
	}

	public void setInShop(String inShop) {
		this.inShop = inShop;
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

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public String getOffAc() {
		return this.offAc;
	}

	public void setOffAc(String offAc) {
		this.offAc = offAc;
	}

	public String getOriginator() {
		return this.originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getOriginatorPhone() {
		return this.originatorPhone;
	}

	public void setOriginatorPhone(String originatorPhone) {
		this.originatorPhone = originatorPhone;
	}

	public String getPnByReference() {
		return this.pnByReference;
	}

	public void setPnByReference(String pnByReference) {
		this.pnByReference = pnByReference;
	}

	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getRealPartNumber() {
		return this.realPartNumber;
	}

	public void setRealPartNumber(String realPartNumber) {
		this.realPartNumber = realPartNumber;
	}

	public String getRepairReturnShopAction() {
		return this.repairReturnShopAction;
	}

	public void setRepairReturnShopAction(String repairReturnShopAction) {
		this.repairReturnShopAction = repairReturnShopAction;
	}

	public String getReserve() {
		return this.reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getReturnToDock() {
		return this.returnToDock;
	}

	public void setReturnToDock(String returnToDock) {
		this.returnToDock = returnToDock;
	}

	public String getShop1() {
		return this.shop1;
	}

	public void setShop1(String shop1) {
		this.shop1 = shop1;
	}

	public String getShop10() {
		return this.shop10;
	}

	public void setShop10(String shop10) {
		this.shop10 = shop10;
	}

	public String getShop2() {
		return this.shop2;
	}

	public void setShop2(String shop2) {
		this.shop2 = shop2;
	}

	public String getShop3() {
		return this.shop3;
	}

	public void setShop3(String shop3) {
		this.shop3 = shop3;
	}

	public String getShop4() {
		return this.shop4;
	}

	public void setShop4(String shop4) {
		this.shop4 = shop4;
	}

	public String getShop5() {
		return this.shop5;
	}

	public void setShop5(String shop5) {
		this.shop5 = shop5;
	}

	public String getShop6() {
		return this.shop6;
	}

	public void setShop6(String shop6) {
		this.shop6 = shop6;
	}

	public String getShop7() {
		return this.shop7;
	}

	public void setShop7(String shop7) {
		this.shop7 = shop7;
	}

	public String getShop8() {
		return this.shop8;
	}

	public void setShop8(String shop8) {
		this.shop8 = shop8;
	}

	public String getShop9() {
		return this.shop9;
	}

	public void setShop9(String shop9) {
		this.shop9 = shop9;
	}

	public String getSpare() {
		return this.spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	public BigDecimal getSurplusQty() {
		return this.surplusQty;
	}

	public void setSurplusQty(BigDecimal surplusQty) {
		this.surplusQty = surplusQty;
	}

	public String getTagMessage() {
		return this.tagMessage;
	}

	public void setTagMessage(String tagMessage) {
		this.tagMessage = tagMessage;
	}

	public BigDecimal getTagQty() {
		return this.tagQty;
	}

	public void setTagQty(BigDecimal tagQty) {
		this.tagQty = tagQty;
	}

	public String getTagType() {
		return this.tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public String getTaskCardXRef() {
		return this.taskCardXRef;
	}

	public void setTaskCardXRef(String taskCardXRef) {
		this.taskCardXRef = taskCardXRef;
	}

	public String getTaskCardXRefDescription() {
		return this.taskCardXRefDescription;
	}

	public void setTaskCardXRefDescription(String taskCardXRefDescription) {
		this.taskCardXRefDescription = taskCardXRefDescription;
	}

	public String getUnitOfIssue() {
		return this.unitOfIssue;
	}

	public void setUnitOfIssue(String unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}

	public TaskCard getTaskCardBean() {
		return this.taskCardBean;
	}

	public void setTaskCardBean(TaskCard taskCardBean) {
		this.taskCardBean = taskCardBean;
	}

}