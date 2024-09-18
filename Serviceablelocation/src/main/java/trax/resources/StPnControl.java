package trax.resources;

import java.io.Serializable;
import java.util.Date;

public class StPnControl implements Serializable {

	private static final long serialVersionUID = 1L;
	private String s_ac;
	private String s_pn;
	private String s_control;
	private String s_installed_position;
	private String s_nha_pn;
	private String s_repair_code;
	private String s_internal_capability;
	private String s_capability_area;
	private int l_notes;
	private String s_plan;
	private String s_eom;
	private String s_task_card;
	private int l_warning_at_percent_due;
	private String s_removal_type;
	private String s_nla_position;
	private Integer l_hours;
	private Integer l_cycles;
	private Integer l_days;
	private Boolean s_calendar_control;
	private Integer actualHours;
	private Integer actualMinutes;
	private Integer actualCycles;
	private Integer actualDays;
	private Integer l_schedule_removals;
	private Integer actualRemovals;
	private Boolean s_date_control;
	private Boolean s_hour_calendar_control;
	private Boolean s_shelf_limited_control;
	private String s_position;
	private Integer l_schedule_rin;
	private Integer actualRin;
	private Integer l_sch_hours;
    private Integer l_sch_cycles;
    private Integer l_sch_days;
    private Date resetDate;
    private Integer resetHour;
    private Integer resetMinute;
    private Boolean override;
    private String s_sn;
    private Date scheduleDate;
    private String authorization;
    private boolean displayHours;
    private boolean displayCycles;
    private boolean displayDays;
	private String id;
	
	public StPnControl() {
		super();
		this.id = String.valueOf(Math.random() * 10);
	}

	public StPnControl(String s_ac, String s_pn, String s_control, String s_installed_position, String s_nha_pn,
			String s_repair_code, String s_internal_capability, String s_capability_area, int l_notes, String s_plan,
			String s_eom, String s_task_card, int l_warning_at_percent_due, String s_removal_type,
			String s_nla_position, Integer l_hours, Integer l_cycles, Integer l_days, Boolean s_calendar_control,
			Integer actualHours, Integer actualCycles, Integer actualDays,
			Integer l_schedule_removals, String s_date_control, Boolean s_hour_calendar_control,
			Boolean s_shelf_limited_control, String s_position, Integer l_schedule_rin, Integer l_sch_hours, Integer l_sch_cycles,
			Integer l_sch_days, Date resetDate, Integer resetHour, Integer resetMinute, Boolean override, String s_sn, Date scheduleDate, 
			String authorization, boolean displayHours, boolean displayCycles, boolean displayDays, Integer actualRemovals) {
		
		super();
		this.id = String.valueOf(Math.random() * 10);
		
		this.s_ac = s_ac;
		this.s_pn = s_pn;
		this.s_control = s_control;
		this.s_installed_position = s_installed_position;
		this.s_nha_pn = s_nha_pn;
		this.s_repair_code = s_repair_code;
		this.s_internal_capability = s_internal_capability;
		this.s_capability_area = s_capability_area;
		this.l_notes = l_notes;
		this.s_plan = s_plan;
		this.s_eom = s_eom;
		this.s_task_card = s_task_card;
		this.l_warning_at_percent_due = l_warning_at_percent_due;
		this.s_removal_type = s_removal_type;
		this.s_nla_position = s_nla_position;
		this.l_hours = l_hours;
		this.l_cycles = l_cycles;
		this.l_days = l_days;
		this.s_calendar_control = s_calendar_control;
		this.actualHours = actualHours;
		this.actualCycles = actualCycles;
		this.actualDays = actualDays;
		this.l_schedule_removals = l_schedule_removals;
		this.s_date_control = "Y".equalsIgnoreCase(s_date_control);
		this.s_hour_calendar_control = s_hour_calendar_control;
		this.s_shelf_limited_control = s_shelf_limited_control;
		this.s_position = s_position;
		this.l_schedule_rin = l_schedule_rin;
		this.l_sch_hours = l_sch_hours;
		this.l_sch_cycles = l_sch_cycles;
		this.l_sch_days = l_sch_days;
		this.resetDate = resetDate;
		this.resetHour = resetHour;
		this.resetMinute = resetMinute;
		this.override = override;
		this.s_sn = s_sn;
		this.scheduleDate = scheduleDate;
		this.authorization = authorization;
		this.displayHours = displayHours;
		this.displayCycles = displayCycles;
		this.displayDays = displayDays;
		this.actualRemovals = actualRemovals;
	}

	public String getS_ac() {
		return s_ac;
	}

	public void setS_ac(String s_ac) {
		this.s_ac = s_ac;
	}

	public String getS_pn() {
		return s_pn;
	}

	public void setS_pn(String s_pn) {
		this.s_pn = s_pn;
	}

	public String getS_control() {
		return s_control;
	}

	public void setS_control(String s_control) {
		this.s_control = s_control;
	}

	public String getS_installed_position() {
		return s_installed_position;
	}

	public void setS_installed_position(String s_installed_position) {
		this.s_installed_position = s_installed_position;
	}

	public String getS_nha_pn() {
		return s_nha_pn;
	}

	public void setS_nha_pn(String s_nha_pn) {
		this.s_nha_pn = s_nha_pn;
	}

	public String getS_repair_code() {
		return s_repair_code;
	}

	public void setS_repair_code(String s_repair_code) {
		this.s_repair_code = s_repair_code;
	}

	public String getS_internal_capability() {
		return s_internal_capability;
	}

	public void setS_internal_capability(String s_internal_capability) {
		this.s_internal_capability = s_internal_capability;
	}

	public String getS_capability_area() {
		return s_capability_area;
	}

	public void setS_capability_area(String s_capability_area) {
		this.s_capability_area = s_capability_area;
	}

	public int getL_notes() {
		return l_notes;
	}

	public void setL_notes(int l_notes) {
		this.l_notes = l_notes;
	}

	public String getS_plan() {
		return s_plan;
	}

	public void setS_plan(String s_plan) {
		this.s_plan = s_plan;
	}

	public String getS_eom() {
		return s_eom;
	}

	public void setS_eom(String s_eom) {
		this.s_eom = s_eom;
	}

	public String getS_task_card() {
		return s_task_card;
	}

	public void setS_task_card(String s_task_card) {
		this.s_task_card = s_task_card;
	}

	public int getL_warning_at_percent_due() {
		return l_warning_at_percent_due;
	}

	public void setL_warning_at_percent_due(int l_warning_at_percent_due) {
		this.l_warning_at_percent_due = l_warning_at_percent_due;
	}

	public String getS_removal_type() {
		return s_removal_type;
	}

	public void setS_removal_type(String s_removal_type) {
		this.s_removal_type = s_removal_type;
	}

	public String getS_nla_position() {
		return s_nla_position;
	}

	public void setS_nla_position(String s_nla_position) {
		this.s_nla_position = s_nla_position;
	}

	public Integer getL_hours() {
		return l_hours;
	}

	public void setL_hours(Integer l_hours) {
		this.l_hours = l_hours;
	}

	public Integer getL_cycles() {
		return l_cycles;
	}

	public void setL_cycles(Integer l_cycles) {
		this.l_cycles = l_cycles;
	}

	public Integer getL_days() {
		return l_days;
	}

	public void setL_days(Integer l_days) {
		this.l_days = l_days;
	}

	public Boolean getS_calendar_control() {
		return s_calendar_control;
	}

	public void setS_calendar_control(Boolean s_calendar_control) {
		this.s_calendar_control = s_calendar_control;
	}

	public Integer getL_schedule_removals() {
		return l_schedule_removals;
	}

	public void setL_schedule_removals(Integer l_schedule_removals) {
		this.l_schedule_removals = l_schedule_removals;
	}

	public Boolean getS_date_control() {
		return s_date_control;
	}

	public void setS_date_control(Boolean s_date_control) {
		this.s_date_control = s_date_control;
	}

	public Boolean getS_hour_calendar_control() {
		return s_hour_calendar_control;
	}

	public void setS_hour_calendar_control(Boolean s_hour_calendar_control) {
		this.s_hour_calendar_control = s_hour_calendar_control;
	}

	public Boolean getS_shelf_limited_control() {
		return s_shelf_limited_control;
	}

	public void setS_shelf_limited_control(Boolean s_shelf_limited_control) {
		this.s_shelf_limited_control = s_shelf_limited_control;
	}

	public String getS_position() {
		return s_position;
	}

	public void setS_position(String s_position) {
		this.s_position = s_position;
	}

	public Integer getL_schedule_rin() {
		return l_schedule_rin;
	}

	public void setL_schedule_rin(Integer l_schedule_rin) {
		this.l_schedule_rin = l_schedule_rin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getActualHours() {
		return actualHours;
	}

	public void setActualHours(Integer actualHours) {
		this.actualHours = actualHours;
	}

	public Integer getActualMinutes() {
		return actualMinutes;
	}

	public void setActualMinutes(Integer actualMinutes) {
		this.actualMinutes = actualMinutes;
	}

	public Integer getActualCycles() {
		return actualCycles;
	}

	public void setActualCycles(Integer actualCycles) {
		this.actualCycles = actualCycles;
	}

	public Integer getActualDays() {
		return actualDays;
	}

	public void setActualDays(Integer actualDays) {
		this.actualDays = actualDays;
	}

	public Integer getL_sch_hours() {
		return l_sch_hours;
	}

	public void setL_sch_hours(Integer l_sch_hours) {
		this.l_sch_hours = l_sch_hours;
	}

	public Integer getL_sch_cycles() {
		return l_sch_cycles;
	}

	public void setL_sch_cycles(Integer l_sch_cycles) {
		this.l_sch_cycles = l_sch_cycles;
	}

	public Integer getL_sch_days() {
		return l_sch_days;
	}

	public void setL_sch_days(Integer l_sch_days) {
		this.l_sch_days = l_sch_days;
	}

	public Date getResetDate() {
		return resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	public Integer getResetHour() {
		return resetHour;
	}

	public void setResetHour(Integer resetHour) {
		this.resetHour = resetHour;
	}

	public Integer getResetMinute() {
		return resetMinute;
	}

	public void setResetMinute(Integer resetMinute) {
		this.resetMinute = resetMinute;
	}

	public Boolean getOverride() {
		return override;
	}

	public void setOverride(Boolean override) {
		this.override = override;
	}

	public String getS_sn() {
		return s_sn;
	}

	public void setS_sn(String s_sn) {
		this.s_sn = s_sn;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public boolean isDisplayHours() {
		return displayHours;
	}

	public void setDisplayHours(boolean displayHours) {
		this.displayHours = displayHours;
	}

	public boolean isDisplayCycles() {
		return displayCycles;
	}

	public void setDisplayCycles(boolean displayCycles) {
		this.displayCycles = displayCycles;
	}

	public boolean isDisplayDays() {
		return displayDays;
	}

	public void setDisplayDays(boolean displayDays) {
		this.displayDays = displayDays;
	}

	public Integer getActualRemovals() {
		return actualRemovals;
	}

	public void setActualRemovals(Integer actualRemovals) {
		this.actualRemovals = actualRemovals;
	}

	public Integer getActualRin() {
		return actualRin;
	}

	public void setActualRin(Integer actualRin) {
		this.actualRin = actualRin;
	}
	
}
