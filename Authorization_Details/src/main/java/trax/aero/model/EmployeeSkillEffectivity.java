package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the EMPLOYEE_SKILL_EFFECTIVITY database table.
 * 
 */
@Entity
@Table(name="EMPLOYEE_SKILL_EFFECTIVITY")
@NamedQuery(name="EmployeeSkillEffectivity.findAll", query="SELECT e FROM EmployeeSkillEffectivity e")
public class EmployeeSkillEffectivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmployeeSkillEffectivityPK id;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="\"SELECT\"")
	private String select;

	public EmployeeSkillEffectivity() {
	}

	public EmployeeSkillEffectivityPK getId() {
		return this.id;
	}

	public void setId(EmployeeSkillEffectivityPK id) {
		this.id = id;
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

	public String getSelect() {
		return this.select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

}