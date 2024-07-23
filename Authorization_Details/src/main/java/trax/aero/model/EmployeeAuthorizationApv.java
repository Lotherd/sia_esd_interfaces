package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;



/**
 * The persistent class for the EMPLOYEE_AUTHORIZATION_APV database table.
 * 
 */
@Entity
@Table(name="EMPLOYEE_AUTHORIZATION_APV")
@NamedQuery(name="EmployeeAuthorizationApv.findAll", query="SELECT e FROM EmployeeAuthorizationApv e")
public class EmployeeAuthorizationApv implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmployeeAuthorizationApvPK id;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DATE_EXPIRATION")
	private Date dateExpiration;

	@Column(name="DATE_ISSUED")
	private Date dateIssued;

	@Column(name="DATE_REVOKED")
	private Date dateRevoked;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	@Column(name="FIRST_LEVEL_APPROVER")
	private String firstLevelApprover;

	@Column(name="FIRST_LEVEL_DATE")
	private Date firstLevelDate;

	@Column(name="FIRST_LEVEL_STATUS")
	private String firstLevelStatus;

	private String limitation;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	private String override;

	@Column(name="SECOND_LEVEL_APPROVER")
	private String secondLevelApprover;

	@Column(name="SECOND_LEVEL_STATUS")
	private String secondLevelStatus;

	private String status;

	//bi-directional many-to-one association to EmployeeAuthorization
	@ManyToOne
	@JoinColumn(name="AUTHORIZATION_CODE" , insertable=false, updatable=false)
	private EmployeeAuthorization employeeAuthorization;

	public EmployeeAuthorizationApv() {
	}

	public EmployeeAuthorizationApvPK getId() {
		return this.id;
	}

	public void setId(EmployeeAuthorizationApvPK id) {
		this.id = id;
	}

	public BigDecimal getBlobNo() {
		return this.blobNo;
	}

	public void setBlobNo(BigDecimal blobNo) {
		this.blobNo = blobNo;
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

	public Date getDateExpiration() {
		return this.dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public Date getDateIssued() {
		return this.dateIssued;
	}

	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}

	public Date getDateRevoked() {
		return this.dateRevoked;
	}

	public void setDateRevoked(Date dateRevoked) {
		this.dateRevoked = dateRevoked;
	}

	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getFirstLevelApprover() {
		return this.firstLevelApprover;
	}

	public void setFirstLevelApprover(String firstLevelApprover) {
		this.firstLevelApprover = firstLevelApprover;
	}

	public Date getFirstLevelDate() {
		return this.firstLevelDate;
	}

	public void setFirstLevelDate(Date firstLevelDate) {
		this.firstLevelDate = firstLevelDate;
	}

	public String getFirstLevelStatus() {
		return this.firstLevelStatus;
	}

	public void setFirstLevelStatus(String firstLevelStatus) {
		this.firstLevelStatus = firstLevelStatus;
	}

	public String getLimitation() {
		return this.limitation;
	}

	public void setLimitation(String limitation) {
		this.limitation = limitation;
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

	public String getOverride() {
		return this.override;
	}

	public void setOverride(String override) {
		this.override = override;
	}

	public String getSecondLevelApprover() {
		return this.secondLevelApprover;
	}

	public void setSecondLevelApprover(String secondLevelApprover) {
		this.secondLevelApprover = secondLevelApprover;
	}

	public String getSecondLevelStatus() {
		return this.secondLevelStatus;
	}

	public void setSecondLevelStatus(String secondLevelStatus) {
		this.secondLevelStatus = secondLevelStatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EmployeeAuthorization getEmployeeAuthorization() {
		return this.employeeAuthorization;
	}

	public void setEmployeeAuthorization(EmployeeAuthorization employeeAuthorization) {
		this.employeeAuthorization = employeeAuthorization;
	}

}