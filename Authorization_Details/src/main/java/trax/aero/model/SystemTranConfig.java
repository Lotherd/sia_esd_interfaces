package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SYSTEM_TRAN_CONFIG database table.
 * 
 */
@Entity
@Table(name="SYSTEM_TRAN_CONFIG")
@NamedQuery(name="SystemTranConfig.findAll", query="SELECT s FROM SystemTranConfig s")
public class SystemTranConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SystemTranConfigPK id;

	@Column(name="BY_TRANSACTION")
	private BigDecimal byTransaction;

	@Column(name="CONFIG_DATE")
	private Date configDate;

	@Column(name="CONFIG_FLAG")
	private String configFlag;

	@Column(name="CONFIG_NUMBER")
	private BigDecimal configNumber;

	@Column(name="CONFIG_NUMBER_DECIMAL")
	private BigDecimal configNumberDecimal;

	@Column(name="CONFIG_NUMBER_PARM1")
	private BigDecimal configNumberParm1;

	@Column(name="CONFIG_NUMBER_PARM2")
	private BigDecimal configNumberParm2;

	@Column(name="CONFIG_NUMBER_PARM3")
	private BigDecimal configNumberParm3;

	@Column(name="CONFIG_NUMBER_PARM4")
	private BigDecimal configNumberParm4;

	@Column(name="CONFIG_NUMBER_PARM5")
	private BigDecimal configNumberParm5;

	@Column(name="CONFIG_NUMBER_PARM6")
	private BigDecimal configNumberParm6;

	@Column(name="CONFIG_OTHER")
	private String configOther;

	@Column(name="CONFIG_OTHER_PARM1")
	private String configOtherParm1;

	@Column(name="CONFIG_OTHER_PARM2")
	private String configOtherParm2;

	@Column(name="CONFIG_OTHER_PARM3")
	private String configOtherParm3;

	@Column(name="CONFIG_OTHER_PARM4")
	private String configOtherParm4;

	@Column(name="CONFIG_OTHER_PARM5")
	private String configOtherParm5;

	@Column(name="CONFIG_OTHER_PARM6")
	private String configOtherParm6;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CURRENCY_EXCHANGE")
	private BigDecimal currencyExchange;

	@Column(name="CYCLE_COUNT_DAYS")
	private BigDecimal cycleCountDays;

	@Column(name="DISPLAY_FLAG")
	private String displayFlag;

	private String gl;

	@Column(name="GL_COMPANY")
	private String glCompany;

	@Column(name="GL_COST_CENTER")
	private String glCostCenter;

	@Column(name="GL_EXPENDITURE")
	private String glExpenditure;

	@Column(name="INTERFACE_FLAG")
	private String interfaceFlag;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="\"MODULE\"")
	private String module;

	@Column(name="MODULE_ICON")
	private String moduleIcon;

	private BigDecimal notes;

	@Column(name="PN_COSTING_METHOD")
	private String pnCostingMethod;

	@Column(name="PN_TRANSACTION")
	private String pnTransaction;

	@Column(name="PRIORITY_LEVEL")
	private String priorityLevel;

	@Column(name="SYSTEM_CODE_DESCRIPTION")
	private String systemCodeDescription;

	@Column(name="SYSTEM_CODE_DESCRIPTON_LONG")
	private String systemCodeDescriptonLong;

	@Column(name="TRANSACTION_TYPE")
	private String transactionType;

	@Column(name="TRAX_SYSTEM")
	private String traxSystem;

	@Column(name="USE_SEQ")
	private String useSeq;

	@Column(name="WINDOW_CODE_USE")
	private String windowCodeUse;

	@Column(name="WINDOW_CODE_USE_NAME")
	private String windowCodeUseName;

	public SystemTranConfig() {
	}

	public SystemTranConfigPK getId() {
		return this.id;
	}

	public void setId(SystemTranConfigPK id) {
		this.id = id;
	}

	public BigDecimal getByTransaction() {
		return this.byTransaction;
	}

	public void setByTransaction(BigDecimal byTransaction) {
		this.byTransaction = byTransaction;
	}

	public Date getConfigDate() {
		return this.configDate;
	}

	public void setConfigDate(Date configDate) {
		this.configDate = configDate;
	}

	public String getConfigFlag() {
		return this.configFlag;
	}

	public void setConfigFlag(String configFlag) {
		this.configFlag = configFlag;
	}

	public BigDecimal getConfigNumber() {
		return this.configNumber;
	}

	public void setConfigNumber(BigDecimal configNumber) {
		this.configNumber = configNumber;
	}

	public BigDecimal getConfigNumberDecimal() {
		return this.configNumberDecimal;
	}

	public void setConfigNumberDecimal(BigDecimal configNumberDecimal) {
		this.configNumberDecimal = configNumberDecimal;
	}

	public BigDecimal getConfigNumberParm1() {
		return this.configNumberParm1;
	}

	public void setConfigNumberParm1(BigDecimal configNumberParm1) {
		this.configNumberParm1 = configNumberParm1;
	}

	public BigDecimal getConfigNumberParm2() {
		return this.configNumberParm2;
	}

	public void setConfigNumberParm2(BigDecimal configNumberParm2) {
		this.configNumberParm2 = configNumberParm2;
	}

	public BigDecimal getConfigNumberParm3() {
		return this.configNumberParm3;
	}

	public void setConfigNumberParm3(BigDecimal configNumberParm3) {
		this.configNumberParm3 = configNumberParm3;
	}

	public BigDecimal getConfigNumberParm4() {
		return this.configNumberParm4;
	}

	public void setConfigNumberParm4(BigDecimal configNumberParm4) {
		this.configNumberParm4 = configNumberParm4;
	}

	public BigDecimal getConfigNumberParm5() {
		return this.configNumberParm5;
	}

	public void setConfigNumberParm5(BigDecimal configNumberParm5) {
		this.configNumberParm5 = configNumberParm5;
	}

	public BigDecimal getConfigNumberParm6() {
		return this.configNumberParm6;
	}

	public void setConfigNumberParm6(BigDecimal configNumberParm6) {
		this.configNumberParm6 = configNumberParm6;
	}

	public String getConfigOther() {
		return this.configOther;
	}

	public void setConfigOther(String configOther) {
		this.configOther = configOther;
	}

	public String getConfigOtherParm1() {
		return this.configOtherParm1;
	}

	public void setConfigOtherParm1(String configOtherParm1) {
		this.configOtherParm1 = configOtherParm1;
	}

	public String getConfigOtherParm2() {
		return this.configOtherParm2;
	}

	public void setConfigOtherParm2(String configOtherParm2) {
		this.configOtherParm2 = configOtherParm2;
	}

	public String getConfigOtherParm3() {
		return this.configOtherParm3;
	}

	public void setConfigOtherParm3(String configOtherParm3) {
		this.configOtherParm3 = configOtherParm3;
	}

	public String getConfigOtherParm4() {
		return this.configOtherParm4;
	}

	public void setConfigOtherParm4(String configOtherParm4) {
		this.configOtherParm4 = configOtherParm4;
	}

	public String getConfigOtherParm5() {
		return this.configOtherParm5;
	}

	public void setConfigOtherParm5(String configOtherParm5) {
		this.configOtherParm5 = configOtherParm5;
	}

	public String getConfigOtherParm6() {
		return this.configOtherParm6;
	}

	public void setConfigOtherParm6(String configOtherParm6) {
		this.configOtherParm6 = configOtherParm6;
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

	public BigDecimal getCurrencyExchange() {
		return this.currencyExchange;
	}

	public void setCurrencyExchange(BigDecimal currencyExchange) {
		this.currencyExchange = currencyExchange;
	}

	public BigDecimal getCycleCountDays() {
		return this.cycleCountDays;
	}

	public void setCycleCountDays(BigDecimal cycleCountDays) {
		this.cycleCountDays = cycleCountDays;
	}

	public String getDisplayFlag() {
		return this.displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
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

	public String getInterfaceFlag() {
		return this.interfaceFlag;
	}

	public void setInterfaceFlag(String interfaceFlag) {
		this.interfaceFlag = interfaceFlag;
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

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getModuleIcon() {
		return this.moduleIcon;
	}

	public void setModuleIcon(String moduleIcon) {
		this.moduleIcon = moduleIcon;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public String getPnCostingMethod() {
		return this.pnCostingMethod;
	}

	public void setPnCostingMethod(String pnCostingMethod) {
		this.pnCostingMethod = pnCostingMethod;
	}

	public String getPnTransaction() {
		return this.pnTransaction;
	}

	public void setPnTransaction(String pnTransaction) {
		this.pnTransaction = pnTransaction;
	}

	public String getPriorityLevel() {
		return this.priorityLevel;
	}

	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public String getSystemCodeDescription() {
		return this.systemCodeDescription;
	}

	public void setSystemCodeDescription(String systemCodeDescription) {
		this.systemCodeDescription = systemCodeDescription;
	}

	public String getSystemCodeDescriptonLong() {
		return this.systemCodeDescriptonLong;
	}

	public void setSystemCodeDescriptonLong(String systemCodeDescriptonLong) {
		this.systemCodeDescriptonLong = systemCodeDescriptonLong;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTraxSystem() {
		return this.traxSystem;
	}

	public void setTraxSystem(String traxSystem) {
		this.traxSystem = traxSystem;
	}

	public String getUseSeq() {
		return this.useSeq;
	}

	public void setUseSeq(String useSeq) {
		this.useSeq = useSeq;
	}

	public String getWindowCodeUse() {
		return this.windowCodeUse;
	}

	public void setWindowCodeUse(String windowCodeUse) {
		this.windowCodeUse = windowCodeUse;
	}

	public String getWindowCodeUseName() {
		return this.windowCodeUseName;
	}

	public void setWindowCodeUseName(String windowCodeUseName) {
		this.windowCodeUseName = windowCodeUseName;
	}

}