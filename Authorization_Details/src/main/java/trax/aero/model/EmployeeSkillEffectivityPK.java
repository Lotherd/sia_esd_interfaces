package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EMPLOYEE_SKILL_EFFECTIVITY database table.
 * 
 */
@Embeddable
public class EmployeeSkillEffectivityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String employee;

	private String skill;

	private String ac;

	public EmployeeSkillEffectivityPK() {
	}
	public String getEmployee() {
		return this.employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getSkill() {
		return this.skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getAc() {
		return this.ac;
	}
	public void setAc(String ac) {
		this.ac = ac;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmployeeSkillEffectivityPK)) {
			return false;
		}
		EmployeeSkillEffectivityPK castOther = (EmployeeSkillEffectivityPK)other;
		return 
			this.employee.equals(castOther.employee)
			&& this.skill.equals(castOther.skill)
			&& this.ac.equals(castOther.ac);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.employee.hashCode();
		hash = hash * prime + this.skill.hashCode();
		hash = hash * prime + this.ac.hashCode();
		
		return hash;
	}
}