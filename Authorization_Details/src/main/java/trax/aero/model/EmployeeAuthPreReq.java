package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the EMPLOYEE_AUTH_PRE_REQ database table.
 * 
 */
@Entity
@Table(name="EMPLOYEE_AUTH_PRE_REQ")
@NamedQuery(name="EmployeeAuthPreReq.findAll", query="SELECT e FROM EmployeeAuthPreReq e")
public class EmployeeAuthPreReq implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmployeeAuthPreReqPK id;

	@Column(name="BLOB_NO")
	private BigDecimal blobNo;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="TRANSACTION_CODE")
	private String transactionCode;

	@Column(name="TRANSACTION_CODE_2")
	private String transactionCode2;

	@Column(name="TRANSACTION_CODE_3")
	private String transactionCode3;

	@Column(name="TRANSACTION_CODE_4")
	private String transactionCode4;

	@Column(name="TRANSACTION_CODE_5")
	private String transactionCode5;

	//bi-directional many-to-one association to EmployeeAuthorization
	@ManyToOne
	@JoinColumn(name="AUTHORIZATION_CODE" , insertable=false, updatable=false)
	private EmployeeAuthorization employeeAuthorization;

	public EmployeeAuthPreReq() {
	}

	public EmployeeAuthPreReqPK getId() {
		return this.id;
	}

	public void setId(EmployeeAuthPreReqPK id) {
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

	public String getTransactionCode() {
		return this.transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionCode2() {
		return this.transactionCode2;
	}

	public void setTransactionCode2(String transactionCode2) {
		this.transactionCode2 = transactionCode2;
	}

	public String getTransactionCode3() {
		return this.transactionCode3;
	}

	public void setTransactionCode3(String transactionCode3) {
		this.transactionCode3 = transactionCode3;
	}

	public String getTransactionCode4() {
		return this.transactionCode4;
	}

	public void setTransactionCode4(String transactionCode4) {
		this.transactionCode4 = transactionCode4;
	}

	public String getTransactionCode5() {
		return this.transactionCode5;
	}

	public void setTransactionCode5(String transactionCode5) {
		this.transactionCode5 = transactionCode5;
	}

	public EmployeeAuthorization getEmployeeAuthorization() {
		return this.employeeAuthorization;
	}

	public void setEmployeeAuthorization(EmployeeAuthorization employeeAuthorization) {
		this.employeeAuthorization = employeeAuthorization;
	}

}