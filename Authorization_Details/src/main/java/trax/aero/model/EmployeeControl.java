package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the EMPLOYEE_CONTROL database table.
 * 
 */
@Entity
@Table(name="EMPLOYEE_CONTROL")
@NamedQuery(name="EmployeeControl.findAll", query="SELECT e FROM EmployeeControl e")
public class EmployeeControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmployeeControlPK id;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	private String country;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DATE_ISSUED")
	private Date dateIssued;

	@Column(name="EXPIRATION_OPTIONAL")
	private String expirationOptional;

	@Column(name="EXPIRE_DATE")
	private Date expireDate;

	@Column(name="ISSUED_AUTHORITY")
	private String issuedAuthority;

	@Column(name="LICENCE_TYPE")
	private String licenceType;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	private String reference;

	private String status;

	@Column(name="VERIFY_DATE")
	private Date verifyDate;
	
	@Column(name="SKILL_ESD")
	private String skillesd;
	
	@Column(name="ENGINE")
	private String engine;

	public EmployeeControl() {
	}

	public EmployeeControlPK getId() {
		return this.id;
	}

	public void setId(EmployeeControlPK id) {
		this.id = id;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public Date getDateIssued() {
		return this.dateIssued;
	}

	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}

	public String getExpirationOptional() {
		return this.expirationOptional;
	}

	public void setExpirationOptional(String expirationOptional) {
		this.expirationOptional = expirationOptional;
	}

	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getIssuedAuthority() {
		return this.issuedAuthority;
	}

	public void setIssuedAuthority(String issuedAuthority) {
		this.issuedAuthority = issuedAuthority;
	}

	public String getLicenceType() {
		return this.licenceType;
	}

	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
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

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getVerifyDate() {
		return this.verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getSkillesd() {
		return skillesd;
	}

	public void setSkillesd(String skillesd) {
		this.skillesd = skillesd;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}
	
	

}