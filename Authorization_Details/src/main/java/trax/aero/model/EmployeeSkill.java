package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the EMPLOYEE_SKILL database table.
 * 
 */
@Entity
@Table(name="EMPLOYEE_SKILL")
@NamedQuery(name="EmployeeSkill.findAll", query="SELECT e FROM EmployeeSkill e")
public class EmployeeSkill implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmployeeSkillPK id;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="COMPLETED_DATE")
	private Date completedDate;

	@Column(name="COMPLETED_FLAG")
	private String completedFlag;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="EXPIRATION_DATE")
	private Date expirationDate;

	@Column(name="EXPIRATION_OPTIONAL")
	private String expirationOptional;

	@Column(name="INTERFACE_TRANSFER_BY")
	private String interfaceTransferBy;

	@Column(name="INTERFACE_TRANSFER_DATE")
	private Date interfaceTransferDate;

	private String license;

	@Column(name="LICENSE_TYPE")
	private String licenseType;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	private String rating;

	@Column(name="REQUIRED_SKILL_CLASS")
	private String requiredSkillClass;

	private String school;

	@Column(name="SKILL_CATEGORY")
	private String skillCategory;

	@Column(name="SKILL_LEVEL")
	private String skillLevel;

	private String status;

	@Column(name="TOTAL_HOURS")
	private BigDecimal totalHours;

	public EmployeeSkill() {
	}

	public EmployeeSkillPK getId() {
		return this.id;
	}

	public void setId(EmployeeSkillPK id) {
		this.id = id;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
	}

	public Date getCompletedDate() {
		return this.completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getCompletedFlag() {
		return this.completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
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

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getExpirationOptional() {
		return this.expirationOptional;
	}

	public void setExpirationOptional(String expirationOptional) {
		this.expirationOptional = expirationOptional;
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

	public String getLicense() {
		return this.license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLicenseType() {
		return this.licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
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

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRequiredSkillClass() {
		return this.requiredSkillClass;
	}

	public void setRequiredSkillClass(String requiredSkillClass) {
		this.requiredSkillClass = requiredSkillClass;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSkillCategory() {
		return this.skillCategory;
	}

	public void setSkillCategory(String skillCategory) {
		this.skillCategory = skillCategory;
	}

	public String getSkillLevel() {
		return this.skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalHours() {
		return this.totalHours;
	}

	public void setTotalHours(BigDecimal totalHours) {
		this.totalHours = totalHours;
	}

}