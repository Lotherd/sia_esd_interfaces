package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EMPLOYEE_CONTROL database table.
 * 
 */
@Embeddable
public class EmployeeControlPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String employee;

	@Column(name="EMPLOYEE_CONTROL")
	private String employeeControl;

	@Column(name="CONTROL_ITEM")
	private long controlItem;

	public EmployeeControlPK() {
	}
	public String getEmployee() {
		return this.employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getEmployeeControl() {
		return this.employeeControl;
	}
	public void setEmployeeControl(String employeeControl) {
		this.employeeControl = employeeControl;
	}
	public long getControlItem() {
		return this.controlItem;
	}
	public void setControlItem(long controlItem) {
		this.controlItem = controlItem;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmployeeControlPK)) {
			return false;
		}
		EmployeeControlPK castOther = (EmployeeControlPK)other;
		return 
			this.employee.equals(castOther.employee)
			&& this.employeeControl.equals(castOther.employeeControl)
			&& (this.controlItem == castOther.controlItem);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.employee.hashCode();
		hash = hash * prime + this.employeeControl.hashCode();
		hash = hash * prime + ((int) (this.controlItem ^ (this.controlItem >>> 32)));
		
		return hash;
	}
}