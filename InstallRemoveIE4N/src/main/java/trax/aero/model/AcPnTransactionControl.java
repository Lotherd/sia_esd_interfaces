package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the AC_PN_TRANSACTION_CONTROL database table.
 * 
 */
@Entity
@Table(name="AC_PN_TRANSACTION_CONTROL")
@NamedQuery(name="AcPnTransactionControl.findAll", query="SELECT a FROM AcPnTransactionControl a")
public class AcPnTransactionControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AcPnTransactionControlPK id;

	@Column(name="ACTUAL_CYCLES")
	private BigDecimal actualCycles;

	@Column(name="ACTUAL_DAYS")
	private BigDecimal actualDays;

	@Column(name="ACTUAL_HOURS")
	private BigDecimal actualHours;

	@Column(name="ACTUAL_MINUTES")
	private BigDecimal actualMinutes;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="PLAN_AC_BRAKE_CYCLES")
	private BigDecimal planAcBrakeCycles;

	@Column(name="PLAN_AC_CYCLES")
	private BigDecimal planAcCycles;

	@Column(name="PLAN_AC_HOURS")
	private BigDecimal planAcHours;

	@Column(name="PLAN_AC_MINUTES")
	private BigDecimal planAcMinutes;

	@Column(name="PLAN_DATE")
	private Date planDate;

	@Column(name="PLAN_HOUR")
	private BigDecimal planHour;

	@Column(name="PLAN_MINUTE")
	private BigDecimal planMinute;

	@Column(name="RESET_DATE")
	private Date resetDate;

	@Column(name="SCHEDULE_CYCLES")
	private BigDecimal scheduleCycles;

	@Column(name="SCHEDULE_DATE")
	private Date scheduleDate;

	@Column(name="SCHEDULE_DAYS")
	private BigDecimal scheduleDays;

	@Column(name="SCHEDULE_HOURS")
	private BigDecimal scheduleHours;

	public AcPnTransactionControl() {
	}

	public AcPnTransactionControlPK getId() {
		return this.id;
	}

	public void setId(AcPnTransactionControlPK id) {
		this.id = id;
	}

	public BigDecimal getActualCycles() {
		return this.actualCycles;
	}

	public void setActualCycles(BigDecimal actualCycles) {
		this.actualCycles = actualCycles;
	}

	public BigDecimal getActualDays() {
		return this.actualDays;
	}

	public void setActualDays(BigDecimal actualDays) {
		this.actualDays = actualDays;
	}

	public BigDecimal getActualHours() {
		return this.actualHours;
	}

	public void setActualHours(BigDecimal actualHours) {
		this.actualHours = actualHours;
	}

	public BigDecimal getActualMinutes() {
		return this.actualMinutes;
	}

	public void setActualMinutes(BigDecimal actualMinutes) {
		this.actualMinutes = actualMinutes;
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

	public BigDecimal getPlanAcBrakeCycles() {
		return this.planAcBrakeCycles;
	}

	public void setPlanAcBrakeCycles(BigDecimal planAcBrakeCycles) {
		this.planAcBrakeCycles = planAcBrakeCycles;
	}

	public BigDecimal getPlanAcCycles() {
		return this.planAcCycles;
	}

	public void setPlanAcCycles(BigDecimal planAcCycles) {
		this.planAcCycles = planAcCycles;
	}

	public BigDecimal getPlanAcHours() {
		return this.planAcHours;
	}

	public void setPlanAcHours(BigDecimal planAcHours) {
		this.planAcHours = planAcHours;
	}

	public BigDecimal getPlanAcMinutes() {
		return this.planAcMinutes;
	}

	public void setPlanAcMinutes(BigDecimal planAcMinutes) {
		this.planAcMinutes = planAcMinutes;
	}

	public Date getPlanDate() {
		return this.planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public BigDecimal getPlanHour() {
		return this.planHour;
	}

	public void setPlanHour(BigDecimal planHour) {
		this.planHour = planHour;
	}

	public BigDecimal getPlanMinute() {
		return this.planMinute;
	}

	public void setPlanMinute(BigDecimal planMinute) {
		this.planMinute = planMinute;
	}

	public Date getResetDate() {
		return this.resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	public BigDecimal getScheduleCycles() {
		return this.scheduleCycles;
	}

	public void setScheduleCycles(BigDecimal scheduleCycles) {
		this.scheduleCycles = scheduleCycles;
	}

	public Date getScheduleDate() {
		return this.scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public BigDecimal getScheduleDays() {
		return this.scheduleDays;
	}

	public void setScheduleDays(BigDecimal scheduleDays) {
		this.scheduleDays = scheduleDays;
	}

	public BigDecimal getScheduleHours() {
		return this.scheduleHours;
	}

	public void setScheduleHours(BigDecimal scheduleHours) {
		this.scheduleHours = scheduleHours;
	}

}