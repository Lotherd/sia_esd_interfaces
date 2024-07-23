package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the EMPLOYEE_AUTHORIZATION database table.
 * 
 */
@Entity
@Table(name="EMPLOYEE_AUTHORIZATION")
@NamedQuery(name="EmployeeAuthorization.findAll", query="SELECT e FROM EmployeeAuthorization e")
public class EmployeeAuthorization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="AUTHORIZATION_CODE")
	private String authorizationCode;

	@Column(name="AUTHORIZATION_CATEGORY")
	private String authorizationCategory;

	@Column(name="AUTHORIZATION_DESCRIPTION")
	private String authorizationDescription;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	@Column(name="EMPLOYEE_POSITION")
	private String employeePosition;

	@Column(name="EMPLOYEE_POSITION_2")
	private String employeePosition2;

	@Column(name="EMPLOYEE_POSITION_3")
	private String employeePosition3;

	@Column(name="EMPLOYEE_POSITION_4")
	private String employeePosition4;

	@Column(name="EMPLOYEE_POSITION_5")
	private String employeePosition5;

	private String limitation;

	@Column(name="MINIMUM_AGE")
	private BigDecimal minimumAge;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	private String status;

	//bi-directional many-to-one association to EmployeeAuthorizationApv
	@OneToMany(mappedBy="employeeAuthorization")
	private List<EmployeeAuthorizationApv> employeeAuthorizationApvs;

	//bi-directional many-to-one association to EmployeeAuthPreReq
	@OneToMany(mappedBy="employeeAuthorization")
	private List<EmployeeAuthPreReq> employeeAuthPreReqs;

	public EmployeeAuthorization() {
	}

	public String getAuthorizationCode() {
		return this.authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getAuthorizationCategory() {
		return this.authorizationCategory;
	}

	public void setAuthorizationCategory(String authorizationCategory) {
		this.authorizationCategory = authorizationCategory;
	}

	public String getAuthorizationDescription() {
		return this.authorizationDescription;
	}

	public void setAuthorizationDescription(String authorizationDescription) {
		this.authorizationDescription = authorizationDescription;
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

	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getEmployeePosition() {
		return this.employeePosition;
	}

	public void setEmployeePosition(String employeePosition) {
		this.employeePosition = employeePosition;
	}

	public String getEmployeePosition2() {
		return this.employeePosition2;
	}

	public void setEmployeePosition2(String employeePosition2) {
		this.employeePosition2 = employeePosition2;
	}

	public String getEmployeePosition3() {
		return this.employeePosition3;
	}

	public void setEmployeePosition3(String employeePosition3) {
		this.employeePosition3 = employeePosition3;
	}

	public String getEmployeePosition4() {
		return this.employeePosition4;
	}

	public void setEmployeePosition4(String employeePosition4) {
		this.employeePosition4 = employeePosition4;
	}

	public String getEmployeePosition5() {
		return this.employeePosition5;
	}

	public void setEmployeePosition5(String employeePosition5) {
		this.employeePosition5 = employeePosition5;
	}

	public String getLimitation() {
		return this.limitation;
	}

	public void setLimitation(String limitation) {
		this.limitation = limitation;
	}

	public BigDecimal getMinimumAge() {
		return this.minimumAge;
	}

	public void setMinimumAge(BigDecimal minimumAge) {
		this.minimumAge = minimumAge;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<EmployeeAuthorizationApv> getEmployeeAuthorizationApvs() {
		return this.employeeAuthorizationApvs;
	}

	public void setEmployeeAuthorizationApvs(List<EmployeeAuthorizationApv> employeeAuthorizationApvs) {
		this.employeeAuthorizationApvs = employeeAuthorizationApvs;
	}

	public EmployeeAuthorizationApv addEmployeeAuthorizationApv(EmployeeAuthorizationApv employeeAuthorizationApv) {
		getEmployeeAuthorizationApvs().add(employeeAuthorizationApv);
		employeeAuthorizationApv.setEmployeeAuthorization(this);

		return employeeAuthorizationApv;
	}

	public EmployeeAuthorizationApv removeEmployeeAuthorizationApv(EmployeeAuthorizationApv employeeAuthorizationApv) {
		getEmployeeAuthorizationApvs().remove(employeeAuthorizationApv);
		employeeAuthorizationApv.setEmployeeAuthorization(null);

		return employeeAuthorizationApv;
	}

	public List<EmployeeAuthPreReq> getEmployeeAuthPreReqs() {
		return this.employeeAuthPreReqs;
	}

	public void setEmployeeAuthPreReqs(List<EmployeeAuthPreReq> employeeAuthPreReqs) {
		this.employeeAuthPreReqs = employeeAuthPreReqs;
	}

	public EmployeeAuthPreReq addEmployeeAuthPreReq(EmployeeAuthPreReq employeeAuthPreReq) {
		getEmployeeAuthPreReqs().add(employeeAuthPreReq);
		employeeAuthPreReq.setEmployeeAuthorization(this);

		return employeeAuthPreReq;
	}

	public EmployeeAuthPreReq removeEmployeeAuthPreReq(EmployeeAuthPreReq employeeAuthPreReq) {
		getEmployeeAuthPreReqs().remove(employeeAuthPreReq);
		employeeAuthPreReq.setEmployeeAuthorization(null);

		return employeeAuthPreReq;
	}

}