package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MT_TRAX_SND_I19_4130_REQ", namespace="http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class I19_Request {
	
	@XmlElement(name = "PN")
	private String pn;
	
	@XmlElement(name = "PN_SN")
    private String pnSn;
    
	@XmlElement(name = "ESN_NO")
    private String esnNo;
    
	@XmlElement(name = "REMOVE_INSTALLED_DATE")
    private String removeInstalledDate;
    
	@XmlElement(name = "LOCATION")
    private String location;
    
	@XmlElement(name = "LICENCE_TYPE")
    private String licenceType;
    
	@XmlElement(name = "REMOVE_AS_SERVICEABLE")
    private String removeAsServiceable;
    
	@XmlElement(name = "INTERNAL_EXTERNAL")
    private String internalExternal;
    
	@XmlElement(name = "TRANSACTION_TYPE")
    private String transactionType;
    
	@XmlElement(name = "REMOVAL_REASON")
    private String removalReason;
    
	@XmlElement(name = "NOTES")
    private String notes;
    
	@XmlElement(name = "CUSTOMER")
    private String customer;
    
	@XmlElement(name = "RFO_NO")
    private String rfoNo;
    
	@XmlElement(name = "LEGACY_BATCH")
    private String legacyBatch;
    
	@XmlElement(name = "QTY")
    private String qty;
    
	@XmlElement(name = "WO")
    private String wo;
    
	@XmlElement(name = "TC")
    private String tc;
    
	@XmlElement(name = "TRANSACTION")
    private String transaction;

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getPnSn() {
		return pnSn;
	}

	public void setPnSn(String pnSn) {
		this.pnSn = pnSn;
	}

	public String getEsnNo() {
		return esnNo;
	}

	public void setEsnNo(String esnNo) {
		this.esnNo = esnNo;
	}

	public String getRemoveInstalledDate() {
		return removeInstalledDate;
	}

	public void setRemoveInstalledDate(String removeInstalledDate) {
		this.removeInstalledDate = removeInstalledDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLicenceType() {
		return licenceType;
	}

	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
	}

	public String getRemoveAsServiceable() {
		return removeAsServiceable;
	}

	public void setRemoveAsServiceable(String removeAsServiceable) {
		this.removeAsServiceable = removeAsServiceable;
	}

	public String getInternalExternal() {
		return internalExternal;
	}

	public void setInternalExternal(String internalExternal) {
		this.internalExternal = internalExternal;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getRemovalReason() {
		return removalReason;
	}

	public void setRemovalReason(String removalReason) {
		this.removalReason = removalReason;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getRfoNo() {
		return rfoNo;
	}

	public void setRfoNo(String rfoNo) {
		this.rfoNo = rfoNo;
	}

	public String getLegacyBatch() {
		return legacyBatch;
	}

	public void setLegacyBatch(String legacyBatch) {
		this.legacyBatch = legacyBatch;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getWo() {
		return wo;
	}

	public void setWo(String wo) {
		this.wo = wo;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
 
   
}
