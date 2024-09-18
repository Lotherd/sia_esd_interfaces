package trax.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import trax.application_standard_structure.st_security;
//import trax.controller.TraxController;
//import trax.datawindow.DataWindow;
import trax.types.TraxArrayList;

public class StPn implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String s_pn;
	private String s_dsc;
	private String s_ac_type;
	private String s_ac_series;
	private String s_sn;
	private String s_position;
	private String s_installed_ac;
	private String s_transaction;
	private Integer l_transaction;
	private Integer l_batch;
	private Integer l_wo;
	private Integer l_hours;
	private Integer l_minutes;
	private Double l_cycles;
	private Integer l_days;
	private String s_ac;
	private Double l_qty;
	private String s_sos_flag;
	private Integer l_no_of_copies;
	private String s_location;
	private Double d_qty_available;
	private String s_condition;
	private String s_order_type;
	private List<String> s_pn_nla = new TraxArrayList<String>();
	private List<String> s_sn_nla = new TraxArrayList<String>();
	private Integer l_index;
	private Integer l_control_count;
	private String s_ri;
	private String s_ri_by;
	private Date dt_ri_date;
	private String s_bin;
	private String s_ri_control;
	private Double d_qty_us;
	private Double d_qty_pending_ri;
	private String s_us_code;
	private String s_modified_by;
	private Integer l_notes;
	private Double d_qty_reserved;
	private Date dt_removal_date;
	private Integer l_picklist;
	private Integer l_picklist_line;
	private String s_pn_old;
	private String s_sn_old;
	private Integer l_box_no;
	private Double d_value;
	private String s_pack_ship_flag;
	private Integer l_ship_pack;
	private String s_pack_ship_express;
	private String s_ship_from_location;
	private String s_ship_to_category;
	private String s_ship_to;
	private String s_qty_type;
	private String s_nha_pn;
	private String s_nha_sn;
	private String s_pn_category;
	private Integer l_goods_rcvd_batch;
	private Integer l_order_number;
	private Integer l_order_line;
	private List<Integer> l_picklist_control = new TraxArrayList<Integer>(Integer.class);
	private String s_window_supervisor;
	private String s_message;
	private String s_password_pass;
	private String s_wall_password;
	private String s_category;
	private String s_pn_interchng;
	private String s_vendor;
	private String s_position_description;
	private String s_pn_description;
	private String s_dd_field;
	private String s_dd_value;
	private String s_pn_nha;
	private String s_old_pn;
	private Date dt_modified_date;
	private Date dt_reset_date;
	private boolean b_nla;
	private String s_interchangeable_flag;
	private String s_error_flag;
	private String s_build_kit;
	private String s_auto_issued;
	private st_security ms_security;
	private String s_transaction_cat;
	private String s_security;
	private String s_inventory_type;
	private String s_issue_to_employee;
	private String s_issue_to;
	private String s_where;
	private String s_report;
	private String s_ipc;
	private String s_pn_transaction_type;
	private String s_nla_pn;
	private String s_nla_position;
	private String s_owner_omit_flag;
	private String s_nla_import_file;
	private String s_task_card;
	private Date dt_installed_date;
	private Integer l_installed_hour;
	private Integer l_installed_minute;
	private String s_owner;
	private String s_loan_category;
	private String s_zone;
	private Integer l_chap;
	private Integer l_sec;
	private Integer l_par;
	private String s_compare;
	private Double l_qty_picked;
	private String s_calling_window;
	private String s_position_group;
	private String s_remove_as_serviceable;
	private String s_original_ac;
	private String s_prorated_flag;
	private String s_control;
	private String s_status;
	private String s_effectivity;
	private String s_pn_change;
	private Integer l_requisition;
	private Integer l_requisition_line;
	private String s_uom;
	private Date dt_created_date;
	private String s_created_by;
	private Date dt_expire_date;
	private Integer l_do_not_update_pn_inv_control;
	private String s_trn;
	private List<String> s_trn_array = new TraxArrayList<String>();
	private boolean b_mobile_picklist_issue;
	private Integer l_picklist_distribution_line;
	private String s_task_card_pn;
	private String s_task_card_sn;
	private String s_to_bin;
	private String s_employee;
	private String s_skill;
	private Integer l_traxdoc_row_id;
	private String s_file_name_path;
	private List<String> s_pn_multi = new TraxArrayList<String>();
	private List<String> s_sn_multi = new TraxArrayList<String>();
	//private DataWindow dw;
	private String s_form;
	private boolean b_auto_receiving;
	private String s_pn_restricted;
	private String s_gl_company;
	private String s_gl_expenditure;
	private String s_gl;
	private String s_gl_cost_center;
	private Integer l_so_number;
	private Integer l_reset_actual_days;
	private String s_eo_filter;
	private String s_eo_revision;
	private String s_message_error1;
	private String s_message_error2;
	private String s_message_type;
	private String s_message_answer;
	private String s_etops_check;
	private String s_override;
	private Date dt_birth_date;
	private Date dt_received_date;
	private Integer l_pool_number;
	private String s_pool_type;
	//private TraxController w_win;
	private Date birthDate;
	private Date shelfLifeExpiration;
	private Date toolLifeExpiration;
	private List<StPnControl> pnControlList = new ArrayList<>();
	private List<StPn> nlaList = new ArrayList<>();
	
	private StPn parent;
	
	public StPn() {
		super();
		this.id = String.valueOf(Math.random() * 10);
	}

	public StPn(String s_pn, String s_dsc, String s_ac_type, String s_ac_series, String s_sn, String s_position,
			String s_installed_ac, String s_transaction, Integer l_transaction, Integer l_batch, Integer l_wo,
			Integer l_hours, Integer l_minutes, Double l_cycles, Integer l_days, String s_ac, Double l_qty,
			String s_sos_flag, Integer l_no_of_copies, String s_location, double d_qty_available, String s_condition,
			String s_order_type, List<String> s_pn_nla, List<String> s_sn_nla, Integer l_index, Integer l_control_count,
			String s_ri, String s_ri_by, Date dt_ri_date, String s_bin, String s_ri_control, Double d_qty_us,
			Double d_qty_pending_ri, String s_us_code, String s_modified_by, Integer l_notes, Double d_qty_reserved,
			Date dt_removal_date, Integer l_picklist, Integer l_picklist_line, String s_pn_old, String s_sn_old,
			Integer l_box_no, Double d_value, String s_pack_ship_flag, Integer l_ship_pack, String s_pack_ship_express,
			String s_ship_from_location, String s_ship_to_category, String s_ship_to, String s_qty_type,
			String s_nha_pn, String s_nha_sn, String s_pn_category, Integer l_goods_rcvd_batch, Integer l_order_number,
			Integer l_order_line, List<Integer> l_picklist_control, String s_window_supervisor, String s_message,
			String s_password_pass, String s_wall_password, String s_category, String s_pn_interchng, String s_vendor,
			String s_position_description, String s_dd_field, String s_dd_value, String s_pn_nha, String s_old_pn,
			Date dt_modified_date, Date dt_reset_date, boolean b_nla, String s_interchangeable_flag,
			String s_error_flag, String s_build_kit, String s_auto_issued, st_security ms_security,
			String s_transaction_cat, String s_security, String s_inventory_type, String s_issue_to_employee,
			String s_issue_to, String s_where, String s_report, String s_ipc, String s_pn_transaction_type,
			String s_nla_pn, String s_nla_position, String s_owner_omit_flag, String s_nla_import_file,
			String s_task_card, Date dt_installed_date, Integer l_installed_hour, Integer l_installed_minute,
			String s_owner, String s_loan_category, String s_zone, Integer l_chap, Integer l_sec, Integer l_par,
			String s_compare, Double l_qty_picked, String s_calling_window, String s_position_group,
			String s_remove_as_serviceable, String s_original_ac, String s_prorated_flag, String s_control,
			String s_status, String s_effectivity, String s_pn_change, Integer l_requisition,
			Integer l_requisition_line, String s_uom, Date dt_created_date, String s_created_by, Date dt_expire_date,
			Integer l_do_not_update_pn_inv_control, String s_trn, List<String> s_trn_array,
			boolean b_mobile_picklist_issue, Integer l_picklist_distribution_line, String s_task_card_pn,
			String s_task_card_sn, String s_to_bin, String s_employee, String s_skill, Integer l_traxdoc_row_id,
			//String s_file_name_path, List<String> s_pn_multi, List<String> s_sn_multi, DataWindow dw, String s_form,
			String s_file_name_path, List<String> s_pn_multi, List<String> s_sn_multi, String s_form,
			boolean b_auto_receiving, String s_pn_restricted, String s_gl_company, String s_gl_expenditure, String s_gl,
			String s_gl_cost_center, Integer l_so_number, Integer l_reset_actual_days, String s_eo_filter,
			String s_eo_revision, String s_message_error1, String s_message_error2, String s_message_type,
			String s_message_answer, String s_etops_check, String s_override, Date dt_birth_date, Date dt_received_date,
			//Integer l_pool_number, String s_pool_type, TraxController w_win, Date shelfLifeExpiration, Date toolLifeExpiration, List<StPnControl> pnControlList) {
		Integer l_pool_number, String s_pool_type, Date shelfLifeExpiration, Date toolLifeExpiration, List<StPnControl> pnControlList) {
		super();
		this.id = String.valueOf(Math.random() * 10);
		parent = null;
		this.s_pn = s_pn;
		this.s_dsc = s_dsc;
		this.s_ac_type = s_ac_type;
		this.s_ac_series = s_ac_series;
		this.s_sn = s_sn;
		this.s_position = s_position;
		this.s_installed_ac = s_installed_ac;
		this.s_transaction = s_transaction;
		this.l_transaction = l_transaction;
		this.l_batch = l_batch;
		this.l_wo = l_wo;
		this.l_hours = l_hours;
		this.l_minutes = l_minutes;
		this.l_cycles = l_cycles;
		this.l_days = l_days;
		this.s_ac = s_ac;
		this.l_qty = l_qty;
		this.s_sos_flag = s_sos_flag;
		this.l_no_of_copies = l_no_of_copies;
		this.s_location = s_location;
		this.d_qty_available = d_qty_available;
		this.s_condition = s_condition;
		this.s_order_type = s_order_type;
		this.s_pn_nla = s_pn_nla;
		this.s_sn_nla = s_sn_nla;
		this.l_index = l_index;
		this.l_control_count = l_control_count;
		this.s_ri = s_ri;
		this.s_ri_by = s_ri_by;
		this.dt_ri_date = dt_ri_date;
		this.s_bin = s_bin;
		this.s_ri_control = s_ri_control;
		this.d_qty_us = d_qty_us;
		this.d_qty_pending_ri = d_qty_pending_ri;
		this.s_us_code = s_us_code;
		this.s_modified_by = s_modified_by;
		this.l_notes = l_notes;
		this.d_qty_reserved = d_qty_reserved;
		this.dt_removal_date = dt_removal_date;
		this.l_picklist = l_picklist;
		this.l_picklist_line = l_picklist_line;
		this.s_pn_old = s_pn_old;
		this.s_sn_old = s_sn_old;
		this.l_box_no = l_box_no;
		this.d_value = d_value;
		this.s_pack_ship_flag = s_pack_ship_flag;
		this.l_ship_pack = l_ship_pack;
		this.s_pack_ship_express = s_pack_ship_express;
		this.s_ship_from_location = s_ship_from_location;
		this.s_ship_to_category = s_ship_to_category;
		this.s_ship_to = s_ship_to;
		this.s_qty_type = s_qty_type;
		this.s_nha_pn = s_nha_pn;
		this.s_nha_sn = s_nha_sn;
		this.s_pn_category = s_pn_category;
		this.l_goods_rcvd_batch = l_goods_rcvd_batch;
		this.l_order_number = l_order_number;
		this.l_order_line = l_order_line;
		this.l_picklist_control = l_picklist_control;
		this.s_window_supervisor = s_window_supervisor;
		this.s_message = s_message;
		this.s_password_pass = s_password_pass;
		this.s_wall_password = s_wall_password;
		this.s_category = s_category;
		this.s_pn_interchng = s_pn_interchng;
		this.s_vendor = s_vendor;
		this.s_position_description = s_position_description;
		this.s_dd_field = s_dd_field;
		this.s_dd_value = s_dd_value;
		this.s_pn_nha = s_pn_nha;
		this.s_old_pn = s_old_pn;
		this.dt_modified_date = dt_modified_date;
		this.dt_reset_date = dt_reset_date;
		this.b_nla = b_nla;
		this.s_interchangeable_flag = s_interchangeable_flag;
		this.s_error_flag = s_error_flag;
		this.s_build_kit = s_build_kit;
		this.s_auto_issued = s_auto_issued;
		this.ms_security = ms_security;
		this.s_transaction_cat = s_transaction_cat;
		this.s_security = s_security;
		this.s_inventory_type = s_inventory_type;
		this.s_issue_to_employee = s_issue_to_employee;
		this.s_issue_to = s_issue_to;
		this.s_where = s_where;
		this.s_report = s_report;
		this.s_ipc = s_ipc;
		this.s_pn_transaction_type = s_pn_transaction_type;
		this.s_nla_pn = s_nla_pn;
		this.s_nla_position = s_nla_position;
		this.s_owner_omit_flag = s_owner_omit_flag;
		this.s_nla_import_file = s_nla_import_file;
		this.s_task_card = s_task_card;
		this.dt_installed_date = dt_installed_date;
		this.l_installed_hour = l_installed_hour;
		this.l_installed_minute = l_installed_minute;
		this.s_owner = s_owner;
		this.s_loan_category = s_loan_category;
		this.s_zone = s_zone;
		this.l_chap = l_chap;
		this.l_sec = l_sec;
		this.l_par = l_par;
		this.s_compare = s_compare;
		this.l_qty_picked = l_qty_picked;
		this.s_calling_window = s_calling_window;
		this.s_position_group = s_position_group;
		this.s_remove_as_serviceable = s_remove_as_serviceable;
		this.s_original_ac = s_original_ac;
		this.s_prorated_flag = s_prorated_flag;
		this.s_control = s_control;
		this.s_status = s_status;
		this.s_effectivity = s_effectivity;
		this.s_pn_change = s_pn_change;
		this.l_requisition = l_requisition;
		this.l_requisition_line = l_requisition_line;
		this.s_uom = s_uom;
		this.dt_created_date = dt_created_date;
		this.s_created_by = s_created_by;
		this.dt_expire_date = dt_expire_date;
		this.l_do_not_update_pn_inv_control = l_do_not_update_pn_inv_control;
		this.s_trn = s_trn;
		this.s_trn_array = s_trn_array;
		this.b_mobile_picklist_issue = b_mobile_picklist_issue;
		this.l_picklist_distribution_line = l_picklist_distribution_line;
		this.s_task_card_pn = s_task_card_pn;
		this.s_task_card_sn = s_task_card_sn;
		this.s_to_bin = s_to_bin;
		this.s_employee = s_employee;
		this.s_skill = s_skill;
		this.l_traxdoc_row_id = l_traxdoc_row_id;
		this.s_file_name_path = s_file_name_path;
		this.s_pn_multi = s_pn_multi;
		this.s_sn_multi = s_sn_multi;
		//this.dw = dw;
		this.s_form = s_form;
		this.b_auto_receiving = b_auto_receiving;
		this.s_pn_restricted = s_pn_restricted;
		this.s_gl_company = s_gl_company;
		this.s_gl_expenditure = s_gl_expenditure;
		this.s_gl = s_gl;
		this.s_gl_cost_center = s_gl_cost_center;
		this.l_so_number = l_so_number;
		this.l_reset_actual_days = l_reset_actual_days;
		this.s_eo_filter = s_eo_filter;
		this.s_eo_revision = s_eo_revision;
		this.s_message_error1 = s_message_error1;
		this.s_message_error2 = s_message_error2;
		this.s_message_type = s_message_type;
		this.s_message_answer = s_message_answer;
		this.s_etops_check = s_etops_check;
		this.s_override = s_override;
		this.dt_birth_date = dt_birth_date;
		this.dt_received_date = dt_received_date;
		this.l_pool_number = l_pool_number;
		this.s_pool_type = s_pool_type;
		//this.w_win = w_win;
		this.shelfLifeExpiration = shelfLifeExpiration;
		this.toolLifeExpiration = toolLifeExpiration;
		this.pnControlList = pnControlList;
	}

	
	public void setStPn(StPn pn) {
		parent = pn.getParent();
		this.s_pn = pn.getS_pn();
		this.s_dsc = pn.getS_dsc();
		this.s_ac_type = pn.getS_ac_type();
		this.s_ac_series = pn.getS_ac_series();
		this.s_sn = pn.getS_sn();
		this.s_position = pn.getS_position();
		this.s_installed_ac = pn.getS_installed_ac();
		this.s_transaction = pn.getS_transaction();
		this.l_transaction = pn.getL_transaction();
		this.l_batch = pn.getL_batch();
		this.l_wo = pn.getL_wo();
		this.l_hours = pn.getL_hours();
		this.l_minutes = pn.getL_minutes();
		this.l_cycles = pn.getL_cycles();
		this.l_days = pn.getL_days();
		this.s_ac = pn.getS_ac();
		this.l_qty = pn.getL_qty();
		this.s_sos_flag = pn.getS_sos_flag();
		this.l_no_of_copies = pn.getL_no_of_copies();
		this.s_location = pn.getS_location();
		this.d_qty_available = pn.getD_qty_available();
		this.s_condition = pn.getS_condition();
		this.s_order_type = pn.getS_order_type();
		this.s_pn_nla = pn.getS_pn_nla();
		this.s_sn_nla = pn.getS_sn_nla();
		this.l_index = pn.getL_index();
		this.l_control_count = pn.getL_control_count();
		this.s_ri = pn.getS_ri();
		this.s_ri_by = pn.getS_ri_by();
		this.dt_ri_date = pn.getDt_ri_date();
		this.s_bin = pn.getS_bin();
		this.s_ri_control = pn.getS_ri_control();
		this.d_qty_us = pn.getD_qty_us();
		this.d_qty_pending_ri = pn.getD_qty_pending_ri();
		this.s_us_code = pn.getS_us_code();
		this.s_modified_by = pn.getS_modified_by();
		this.l_notes = pn.getL_notes();
		this.d_qty_reserved = pn.getD_qty_reserved();
		this.dt_removal_date = pn.getDt_removal_date();
		this.l_picklist = pn.getL_picklist();
		this.l_picklist_line = pn.getL_picklist_line();
		this.s_pn_old = pn.getS_pn_old();
		this.s_sn_old = pn.getS_sn_old();
		this.l_box_no = pn.getL_box_no();
		this.d_value = pn.getD_value();
		this.s_pack_ship_flag = pn.getS_pack_ship_flag();
		this.l_ship_pack = pn.getL_ship_pack();
		this.s_pack_ship_express = pn.getS_pack_ship_express();
		this.s_ship_from_location = pn.getS_ship_from_location();
		this.s_ship_to_category = pn.getS_ship_to_category();
		this.s_ship_to = pn.getS_ship_to();
		this.s_qty_type = pn.getS_qty_type();
		this.s_nha_pn = pn.getS_nha_pn();
		this.s_nha_sn = pn.getS_nha_sn();
		this.s_pn_category = pn.getS_pn_category();
		this.l_goods_rcvd_batch = pn.getL_goods_rcvd_batch();
		this.l_order_number = pn.getL_order_number();
		this.l_order_line = pn.getL_order_line();
		this.l_picklist_control = pn.getL_picklist_control();
		this.s_window_supervisor = pn.getS_window_supervisor();
		this.s_message = pn.getS_message();
		this.s_password_pass = pn.getS_password_pass();
		this.s_wall_password = pn.getS_wall_password();
		this.s_category = pn.getS_category();
		this.s_pn_interchng = pn.getS_pn_interchng();
		this.s_vendor = pn.getS_vendor();
		this.s_position_description = pn.getS_position_description();
		this.s_dd_field = pn.getS_dd_field();
		this.s_dd_value = pn.getS_dd_value();
		this.s_pn_nha = pn.getS_pn_nha();
		this.s_old_pn = pn.getS_old_pn();
		this.dt_modified_date = pn.getDt_modified_date();
		this.dt_reset_date = pn.getDt_reset_date();
		this.b_nla = pn.isB_nla();
		this.s_interchangeable_flag = pn.getS_interchangeable_flag();
		this.s_error_flag = pn.getS_error_flag();
		this.s_build_kit = pn.getS_build_kit();
		this.s_auto_issued = pn.getS_auto_issued();
		this.ms_security = pn.getMs_security();
		this.s_transaction_cat = pn.getS_transaction_cat();
		this.s_security = pn.getS_security();
		this.s_inventory_type = pn.getS_inventory_type();
		this.s_issue_to_employee = pn.getS_issue_to_employee();
		this.s_issue_to = pn.getS_issue_to();
		this.s_where = pn.getS_where();
		this.s_report = pn.getS_report();
		this.s_ipc = pn.getS_ipc();
		this.s_pn_transaction_type = pn.getS_pn_transaction_type();
		this.s_nla_pn = pn.getS_nla_pn();
		this.s_nla_position = pn.getS_nla_position();
		this.s_owner_omit_flag = pn.getS_owner_omit_flag();
		this.s_nla_import_file = pn.getS_nla_import_file();
		this.s_task_card = pn.getS_task_card();
		this.dt_installed_date = pn.getDt_installed_date();
		this.l_installed_hour = pn.getL_installed_hour();
		this.l_installed_minute = pn.getL_installed_minute();
		this.s_owner = pn.getS_owner();
		this.s_loan_category = pn.getS_loan_category();
		this.s_zone = pn.getS_zone();
		this.l_chap = pn.getL_chap();
		this.l_sec = pn.getL_sec();
		this.l_par = pn.getL_par();
		this.s_compare = pn.getS_compare();
		this.l_qty_picked = pn.getL_qty_picked();
		this.s_calling_window = pn.getS_calling_window();
		this.s_position_group = pn.getS_position_group();
		this.s_remove_as_serviceable = pn.getS_remove_as_serviceable();
		this.s_original_ac = pn.getS_original_ac();
		this.s_prorated_flag = pn.getS_prorated_flag();
		this.s_control = pn.getS_control();
		this.s_status = pn.getS_status();
		this.s_effectivity = pn.getS_effectivity();
		this.s_pn_change = pn.getS_pn_change();
		this.l_requisition = pn.getL_requisition();
		this.l_requisition_line = pn.getL_requisition_line();
		this.s_uom = pn.getS_uom();
		this.dt_created_date = pn.getDt_created_date();
		this.s_created_by = pn.getS_created_by();
		this.dt_expire_date = pn.getDt_expire_date();
		this.l_do_not_update_pn_inv_control = pn.getL_do_not_update_pn_inv_control();
		this.s_trn = pn.getS_trn();
		this.s_trn_array = pn.getS_trn_array();
		this.b_mobile_picklist_issue = pn.isB_mobile_picklist_issue();
		this.l_picklist_distribution_line = pn.getL_picklist_distribution_line();
		this.s_task_card_pn = pn.getS_task_card_pn();
		this.s_task_card_sn = pn.getS_task_card_sn();
		this.s_to_bin = pn.getS_to_bin();
		this.s_employee = pn.getS_employee();
		this.s_skill = pn.getS_skill();
		this.l_traxdoc_row_id = pn.getL_traxdoc_row_id();
		this.s_file_name_path = pn.getS_file_name_path();
		this.s_pn_multi = pn.getS_pn_multi();
		this.s_sn_multi = pn.getS_sn_multi();
		//this.dw = pn.getDw();
		this.s_form = pn.getS_form();
		this.b_auto_receiving = pn.isB_auto_receiving();
		this.s_pn_restricted = pn.getS_pn_restricted();
		this.s_gl_company = pn.getS_gl_company();
		this.s_gl_expenditure = pn.getS_gl_expenditure();
		this.s_gl = pn.getS_gl();
		this.s_gl_cost_center = pn.getS_gl_cost_center();
		this.l_so_number = pn.getL_so_number();
		this.l_reset_actual_days = pn.getL_reset_actual_days();
		this.s_eo_filter = pn.getS_eo_filter();
		this.s_eo_revision = pn.getS_eo_revision();
		this.s_message_error1 = pn.getS_message_error1();
		this.s_message_error2 = pn.getS_message_error2();
		this.s_message_type = pn.getS_message_type();
		this.s_message_answer = pn.getS_message_answer();
		this.s_etops_check = pn.getS_etops_check();
		this.s_override = pn.getS_override();
		this.dt_birth_date = pn.getDt_birth_date();
		this.dt_received_date = pn.getDt_received_date();
		this.l_pool_number = pn.getL_pool_number();
		this.s_pool_type = pn.getS_pool_type();
		//this.w_win = pn.getW_win();
		this.shelfLifeExpiration = pn.getShelfLifeExpiration();
		this.toolLifeExpiration = pn.getToolLifeExpiration();
		this.pnControlList = pn.getPnControlList();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

	public StPn getParent() {
		return parent;
	}

	public void setParent(StPn parent) {
		this.parent = parent;
	}

	public String getS_pn() {
		return s_pn;
	}

	public void setS_pn(String s_pn) {
		this.s_pn = s_pn;
	}

	public String getS_dsc() {
		return s_dsc;
	}

	public void setS_dsc(String s_dsc) {
		this.s_dsc = s_dsc;
	}

	public String getS_ac_type() {
		return s_ac_type;
	}

	public void setS_ac_type(String s_ac_type) {
		this.s_ac_type = s_ac_type;
	}

	public String getS_ac_series() {
		return s_ac_series;
	}

	public void setS_ac_series(String s_ac_series) {
		this.s_ac_series = s_ac_series;
	}

	public String getS_sn() {
		return s_sn;
	}

	public void setS_sn(String s_sn) {
		this.s_sn = s_sn;
	}

	public String getS_position() {
		return s_position;
	}

	public void setS_position(String s_position) {
		this.s_position = s_position;
	}

	public String getS_installed_ac() {
		return s_installed_ac;
	}

	public void setS_installed_ac(String s_installed_ac) {
		this.s_installed_ac = s_installed_ac;
	}

	public String getS_transaction() {
		return s_transaction;
	}

	public void setS_transaction(String s_transaction) {
		this.s_transaction = s_transaction;
	}

	public Integer getL_transaction() {
		return l_transaction;
	}

	public void setL_transaction(Integer l_transaction) {
		this.l_transaction = l_transaction;
	}

	public Integer getL_batch() {
		return l_batch;
	}

	public void setL_batch(Integer l_batch) {
		this.l_batch = l_batch;
	}

	public Integer getL_wo() {
		return l_wo;
	}

	public void setL_wo(Integer l_wo) {
		this.l_wo = l_wo;
	}

	public Integer getL_hours() {
		return l_hours;
	}

	public void setL_hours(Integer l_hours) {
		this.l_hours = l_hours;
	}

	public Integer getL_minutes() {
		return l_minutes;
	}

	public void setL_minutes(Integer l_minutes) {
		this.l_minutes = l_minutes;
	}

	public Double getL_cycles() {
		return l_cycles;
	}

	public void setL_cycles(Double l_cycles) {
		this.l_cycles = l_cycles;
	}

	public Integer getL_days() {
		return l_days;
	}

	public void setL_days(Integer l_days) {
		this.l_days = l_days;
	}

	public String getS_ac() {
		return s_ac;
	}

	public void setS_ac(String s_ac) {
		this.s_ac = s_ac;
	}

	public Double getL_qty() {
		return l_qty;
	}

	public void setL_qty(Double l_qty) {
		this.l_qty = l_qty;
	}

	public String getS_sos_flag() {
		return s_sos_flag;
	}

	public void setS_sos_flag(String s_sos_flag) {
		this.s_sos_flag = s_sos_flag;
	}

	public Integer getL_no_of_copies() {
		return l_no_of_copies;
	}

	public void setL_no_of_copies(Integer l_no_of_copies) {
		this.l_no_of_copies = l_no_of_copies;
	}

	public String getS_location() {
		return s_location;
	}

	public void setS_location(String s_location) {
		this.s_location = s_location;
	}

	public Double getD_qty_available() {
		return d_qty_available;
	}

	public void setD_qty_available(Double d_qty_available) {
		this.d_qty_available = d_qty_available;
	}

	public String getS_condition() {
		return s_condition;
	}

	public void setS_condition(String s_condition) {
		this.s_condition = s_condition;
	}

	public String getS_order_type() {
		return s_order_type;
	}

	public void setS_order_type(String s_order_type) {
		this.s_order_type = s_order_type;
	}

	public List<String> getS_pn_nla() {
		return s_pn_nla;
	}

	public void setS_pn_nla(List<String> s_pn_nla) {
		this.s_pn_nla = s_pn_nla;
	}

	public List<String> getS_sn_nla() {
		return s_sn_nla;
	}

	public void setS_sn_nla(List<String> s_sn_nla) {
		this.s_sn_nla = s_sn_nla;
	}

	public Integer getL_index() {
		return l_index;
	}

	public void setL_index(Integer l_index) {
		this.l_index = l_index;
	}

	public Integer getL_control_count() {
		return l_control_count;
	}

	public void setL_control_count(Integer l_control_count) {
		this.l_control_count = l_control_count;
	}

	public String getS_ri() {
		return s_ri;
	}

	public void setS_ri(String s_ri) {
		this.s_ri = s_ri;
	}

	public String getS_ri_by() {
		return s_ri_by;
	}

	public void setS_ri_by(String s_ri_by) {
		this.s_ri_by = s_ri_by;
	}

	public Date getDt_ri_date() {
		return dt_ri_date;
	}

	public void setDt_ri_date(Date dt_ri_date) {
		this.dt_ri_date = dt_ri_date;
	}

	public String getS_bin() {
		return s_bin;
	}

	public void setS_bin(String s_bin) {
		this.s_bin = s_bin;
	}

	public String getS_ri_control() {
		return s_ri_control;
	}

	public void setS_ri_control(String s_ri_control) {
		this.s_ri_control = s_ri_control;
	}

	public Double getD_qty_us() {
		return d_qty_us;
	}

	public void setD_qty_us(Double d_qty_us) {
		this.d_qty_us = d_qty_us;
	}

	public Double getD_qty_pending_ri() {
		return d_qty_pending_ri;
	}

	public void setD_qty_pending_ri(Double d_qty_pending_ri) {
		this.d_qty_pending_ri = d_qty_pending_ri;
	}

	public String getS_us_code() {
		return s_us_code;
	}

	public void setS_us_code(String s_us_code) {
		this.s_us_code = s_us_code;
	}

	public String getS_modified_by() {
		return s_modified_by;
	}

	public void setS_modified_by(String s_modified_by) {
		this.s_modified_by = s_modified_by;
	}

	public Integer getL_notes() {
		return l_notes;
	}

	public void setL_notes(Integer l_notes) {
		this.l_notes = l_notes;
	}

	public Double getD_qty_reserved() {
		return d_qty_reserved;
	}

	public void setD_qty_reserved(Double d_qty_reserved) {
		this.d_qty_reserved = d_qty_reserved;
	}

	public Date getDt_removal_date() {
		return dt_removal_date;
	}

	public void setDt_removal_date(Date dt_removal_date) {
		this.dt_removal_date = dt_removal_date;
	}

	public Integer getL_picklist() {
		return l_picklist;
	}

	public void setL_picklist(Integer l_picklist) {
		this.l_picklist = l_picklist;
	}

	public Integer getL_picklist_line() {
		return l_picklist_line;
	}

	public void setL_picklist_line(Integer l_picklist_line) {
		this.l_picklist_line = l_picklist_line;
	}

	public String getS_pn_old() {
		return s_pn_old;
	}

	public void setS_pn_old(String s_pn_old) {
		this.s_pn_old = s_pn_old;
	}

	public String getS_sn_old() {
		return s_sn_old;
	}

	public void setS_sn_old(String s_sn_old) {
		this.s_sn_old = s_sn_old;
	}

	public Integer getL_box_no() {
		return l_box_no;
	}

	public void setL_box_no(Integer l_box_no) {
		this.l_box_no = l_box_no;
	}

	public Double getD_value() {
		return d_value;
	}

	public void setD_value(Double d_value) {
		this.d_value = d_value;
	}

	public String getS_pack_ship_flag() {
		return s_pack_ship_flag;
	}

	public void setS_pack_ship_flag(String s_pack_ship_flag) {
		this.s_pack_ship_flag = s_pack_ship_flag;
	}

	public Integer getL_ship_pack() {
		return l_ship_pack;
	}

	public void setL_ship_pack(Integer l_ship_pack) {
		this.l_ship_pack = l_ship_pack;
	}

	public String getS_pack_ship_express() {
		return s_pack_ship_express;
	}

	public void setS_pack_ship_express(String s_pack_ship_express) {
		this.s_pack_ship_express = s_pack_ship_express;
	}

	public String getS_ship_from_location() {
		return s_ship_from_location;
	}

	public void setS_ship_from_location(String s_ship_from_location) {
		this.s_ship_from_location = s_ship_from_location;
	}

	public String getS_ship_to_category() {
		return s_ship_to_category;
	}

	public void setS_ship_to_category(String s_ship_to_category) {
		this.s_ship_to_category = s_ship_to_category;
	}

	public String getS_ship_to() {
		return s_ship_to;
	}

	public void setS_ship_to(String s_ship_to) {
		this.s_ship_to = s_ship_to;
	}

	public String getS_qty_type() {
		return s_qty_type;
	}

	public void setS_qty_type(String s_qty_type) {
		this.s_qty_type = s_qty_type;
	}

	public String getS_nha_pn() {
		return s_nha_pn;
	}

	public void setS_nha_pn(String s_nha_pn) {
		this.s_nha_pn = s_nha_pn;
	}

	public String getS_nha_sn() {
		return s_nha_sn;
	}

	public void setS_nha_sn(String s_nha_sn) {
		this.s_nha_sn = s_nha_sn;
	}

	public String getS_pn_category() {
		return s_pn_category;
	}

	public void setS_pn_category(String s_pn_category) {
		this.s_pn_category = s_pn_category;
	}

	public Integer getL_goods_rcvd_batch() {
		return l_goods_rcvd_batch;
	}

	public void setL_goods_rcvd_batch(Integer l_goods_rcvd_batch) {
		this.l_goods_rcvd_batch = l_goods_rcvd_batch;
	}

	public Integer getL_order_number() {
		return l_order_number;
	}

	public void setL_order_number(Integer l_order_number) {
		this.l_order_number = l_order_number;
	}

	public Integer getL_order_line() {
		return l_order_line;
	}

	public void setL_order_line(Integer l_order_line) {
		this.l_order_line = l_order_line;
	}

	public List<Integer> getL_picklist_control() {
		return l_picklist_control;
	}

	public void setL_picklist_control(List<Integer> l_picklist_control) {
		this.l_picklist_control = l_picklist_control;
	}

	public String getS_window_supervisor() {
		return s_window_supervisor;
	}

	public void setS_window_supervisor(String s_window_supervisor) {
		this.s_window_supervisor = s_window_supervisor;
	}

	public String getS_message() {
		return s_message;
	}

	public void setS_message(String s_message) {
		this.s_message = s_message;
	}

	public String getS_password_pass() {
		return s_password_pass;
	}

	public void setS_password_pass(String s_password_pass) {
		this.s_password_pass = s_password_pass;
	}

	public String getS_wall_password() {
		return s_wall_password;
	}

	public void setS_wall_password(String s_wall_password) {
		this.s_wall_password = s_wall_password;
	}

	public String getS_category() {
		return s_category;
	}

	public void setS_category(String s_category) {
		this.s_category = s_category;
	}

	public String getS_pn_interchng() {
		return s_pn_interchng;
	}

	public void setS_pn_interchng(String s_pn_interchng) {
		this.s_pn_interchng = s_pn_interchng;
	}

	public String getS_vendor() {
		return s_vendor;
	}

	public void setS_vendor(String s_vendor) {
		this.s_vendor = s_vendor;
	}

	public String getS_position_description() {
		return s_position_description;
	}

	public void setS_position_description(String s_position_description) {
		this.s_position_description = s_position_description;
	}

	public String getS_dd_field() {
		return s_dd_field;
	}

	public void setS_dd_field(String s_dd_field) {
		this.s_dd_field = s_dd_field;
	}

	public String getS_dd_value() {
		return s_dd_value;
	}

	public void setS_dd_value(String s_dd_value) {
		this.s_dd_value = s_dd_value;
	}

	public String getS_pn_nha() {
		return s_pn_nha;
	}

	public void setS_pn_nha(String s_pn_nha) {
		this.s_pn_nha = s_pn_nha;
	}

	public String getS_old_pn() {
		return s_old_pn;
	}

	public void setS_old_pn(String s_old_pn) {
		this.s_old_pn = s_old_pn;
	}

	public Date getDt_modified_date() {
		return dt_modified_date;
	}

	public void setDt_modified_date(Date dt_modified_date) {
		this.dt_modified_date = dt_modified_date;
	}

	public Date getDt_reset_date() {
		return dt_reset_date;
	}

	public void setDt_reset_date(Date dt_reset_date) {
		this.dt_reset_date = dt_reset_date;
	}

	public boolean isB_nla() {
		return b_nla;
	}

	public void setB_nla(boolean b_nla) {
		this.b_nla = b_nla;
	}

	public String getS_interchangeable_flag() {
		return s_interchangeable_flag;
	}

	public void setS_interchangeable_flag(String s_interchangeable_flag) {
		this.s_interchangeable_flag = s_interchangeable_flag;
	}

	public String getS_error_flag() {
		return s_error_flag;
	}

	public void setS_error_flag(String s_error_flag) {
		this.s_error_flag = s_error_flag;
	}

	public String getS_build_kit() {
		return s_build_kit;
	}

	public void setS_build_kit(String s_build_kit) {
		this.s_build_kit = s_build_kit;
	}

	public String getS_auto_issued() {
		return s_auto_issued;
	}

	public void setS_auto_issued(String s_auto_issued) {
		this.s_auto_issued = s_auto_issued;
	}

	public st_security getMs_security() {
		return ms_security;
	}

	public void setMs_security(st_security ms_security) {
		this.ms_security = ms_security;
	}

	public String getS_transaction_cat() {
		return s_transaction_cat;
	}

	public void setS_transaction_cat(String s_transaction_cat) {
		this.s_transaction_cat = s_transaction_cat;
	}

	public String getS_security() {
		return s_security;
	}

	public void setS_security(String s_security) {
		this.s_security = s_security;
	}

	public String getS_inventory_type() {
		return s_inventory_type;
	}

	public void setS_inventory_type(String s_inventory_type) {
		this.s_inventory_type = s_inventory_type;
	}

	public String getS_issue_to_employee() {
		return s_issue_to_employee;
	}

	public void setS_issue_to_employee(String s_issue_to_employee) {
		this.s_issue_to_employee = s_issue_to_employee;
	}

	public String getS_issue_to() {
		return s_issue_to;
	}

	public void setS_issue_to(String s_issue_to) {
		this.s_issue_to = s_issue_to;
	}

	public String getS_where() {
		return s_where;
	}

	public void setS_where(String s_where) {
		this.s_where = s_where;
	}

	public String getS_report() {
		return s_report;
	}

	public void setS_report(String s_report) {
		this.s_report = s_report;
	}

	public String getS_ipc() {
		return s_ipc;
	}

	public void setS_ipc(String s_ipc) {
		this.s_ipc = s_ipc;
	}

	public String getS_pn_transaction_type() {
		return s_pn_transaction_type;
	}

	public void setS_pn_transaction_type(String s_pn_transaction_type) {
		this.s_pn_transaction_type = s_pn_transaction_type;
	}

	public String getS_nla_pn() {
		return s_nla_pn;
	}

	public void setS_nla_pn(String s_nla_pn) {
		this.s_nla_pn = s_nla_pn;
	}

	public String getS_nla_position() {
		return s_nla_position;
	}

	public void setS_nla_position(String s_nla_position) {
		this.s_nla_position = s_nla_position;
	}

	public String getS_owner_omit_flag() {
		return s_owner_omit_flag;
	}

	public void setS_owner_omit_flag(String s_owner_omit_flag) {
		this.s_owner_omit_flag = s_owner_omit_flag;
	}

	public String getS_nla_import_file() {
		return s_nla_import_file;
	}

	public void setS_nla_import_file(String s_nla_import_file) {
		this.s_nla_import_file = s_nla_import_file;
	}

	public String getS_task_card() {
		return s_task_card;
	}

	public void setS_task_card(String s_task_card) {
		this.s_task_card = s_task_card;
	}

	public Date getDt_installed_date() {
		return dt_installed_date;
	}

	public void setDt_installed_date(Date dt_installed_date) {
		this.dt_installed_date = dt_installed_date;
	}

	public Integer getL_installed_hour() {
		return l_installed_hour;
	}

	public void setL_installed_hour(Integer l_installed_hour) {
		this.l_installed_hour = l_installed_hour;
	}

	public Integer getL_installed_minute() {
		return l_installed_minute;
	}

	public void setL_installed_minute(Integer l_installed_minute) {
		this.l_installed_minute = l_installed_minute;
	}

	public String getS_owner() {
		return s_owner;
	}

	public void setS_owner(String s_owner) {
		this.s_owner = s_owner;
	}

	public String getS_loan_category() {
		return s_loan_category;
	}

	public void setS_loan_category(String s_loan_category) {
		this.s_loan_category = s_loan_category;
	}

	public String getS_zone() {
		return s_zone;
	}

	public void setS_zone(String s_zone) {
		this.s_zone = s_zone;
	}

	public Integer getL_chap() {
		return l_chap;
	}

	public void setL_chap(Integer l_chap) {
		this.l_chap = l_chap;
	}

	public Integer getL_sec() {
		return l_sec;
	}

	public void setL_sec(Integer l_sec) {
		this.l_sec = l_sec;
	}

	public Integer getL_par() {
		return l_par;
	}

	public void setL_par(Integer l_par) {
		this.l_par = l_par;
	}

	public String getS_compare() {
		return s_compare;
	}

	public void setS_compare(String s_compare) {
		this.s_compare = s_compare;
	}

	public Double getL_qty_picked() {
		return l_qty_picked;
	}

	public void setL_qty_picked(Double l_qty_picked) {
		this.l_qty_picked = l_qty_picked;
	}

	public String getS_calling_window() {
		return s_calling_window;
	}

	public void setS_calling_window(String s_calling_window) {
		this.s_calling_window = s_calling_window;
	}

	public String getS_position_group() {
		return s_position_group;
	}

	public void setS_position_group(String s_position_group) {
		this.s_position_group = s_position_group;
	}

	public String getS_remove_as_serviceable() {
		return s_remove_as_serviceable;
	}

	public void setS_remove_as_serviceable(String s_remove_as_serviceable) {
		this.s_remove_as_serviceable = s_remove_as_serviceable;
	}

	public String getS_original_ac() {
		return s_original_ac;
	}

	public void setS_original_ac(String s_original_ac) {
		this.s_original_ac = s_original_ac;
	}

	public String getS_prorated_flag() {
		return s_prorated_flag;
	}

	public void setS_prorated_flag(String s_prorated_flag) {
		this.s_prorated_flag = s_prorated_flag;
	}

	public String getS_control() {
		return s_control;
	}

	public void setS_control(String s_control) {
		this.s_control = s_control;
	}

	public String getS_status() {
		return s_status;
	}

	public void setS_status(String s_status) {
		this.s_status = s_status;
	}

	public String getS_effectivity() {
		return s_effectivity;
	}

	public void setS_effectivity(String s_effectivity) {
		this.s_effectivity = s_effectivity;
	}

	public String getS_pn_change() {
		return s_pn_change;
	}

	public void setS_pn_change(String s_pn_change) {
		this.s_pn_change = s_pn_change;
	}

	public Integer getL_requisition() {
		return l_requisition;
	}

	public void setL_requisition(Integer l_requisition) {
		this.l_requisition = l_requisition;
	}

	public Integer getL_requisition_line() {
		return l_requisition_line;
	}

	public void setL_requisition_line(Integer l_requisition_line) {
		this.l_requisition_line = l_requisition_line;
	}

	public String getS_uom() {
		return s_uom;
	}

	public void setS_uom(String s_uom) {
		this.s_uom = s_uom;
	}

	public Date getDt_created_date() {
		return dt_created_date;
	}

	public void setDt_created_date(Date dt_created_date) {
		this.dt_created_date = dt_created_date;
	}

	public String getS_created_by() {
		return s_created_by;
	}

	public void setS_created_by(String s_created_by) {
		this.s_created_by = s_created_by;
	}

	public Date getDt_expire_date() {
		return dt_expire_date;
	}

	public void setDt_expire_date(Date dt_expire_date) {
		this.dt_expire_date = dt_expire_date;
	}

	public Integer getL_do_not_update_pn_inv_control() {
		return l_do_not_update_pn_inv_control;
	}

	public void setL_do_not_update_pn_inv_control(Integer l_do_not_update_pn_inv_control) {
		this.l_do_not_update_pn_inv_control = l_do_not_update_pn_inv_control;
	}

	public String getS_trn() {
		return s_trn;
	}

	public void setS_trn(String s_trn) {
		this.s_trn = s_trn;
	}

	public List<String> getS_trn_array() {
		return s_trn_array;
	}

	public void setS_trn_array(List<String> s_trn_array) {
		this.s_trn_array = s_trn_array;
	}

	public boolean isB_mobile_picklist_issue() {
		return b_mobile_picklist_issue;
	}

	public void setB_mobile_picklist_issue(boolean b_mobile_picklist_issue) {
		this.b_mobile_picklist_issue = b_mobile_picklist_issue;
	}

	public Integer getL_picklist_distribution_line() {
		return l_picklist_distribution_line;
	}

	public void setL_picklist_distribution_line(Integer l_picklist_distribution_line) {
		this.l_picklist_distribution_line = l_picklist_distribution_line;
	}

	public String getS_task_card_pn() {
		return s_task_card_pn;
	}

	public void setS_task_card_pn(String s_task_card_pn) {
		this.s_task_card_pn = s_task_card_pn;
	}

	public String getS_task_card_sn() {
		return s_task_card_sn;
	}

	public void setS_task_card_sn(String s_task_card_sn) {
		this.s_task_card_sn = s_task_card_sn;
	}

	public String getS_to_bin() {
		return s_to_bin;
	}

	public void setS_to_bin(String s_to_bin) {
		this.s_to_bin = s_to_bin;
	}

	public String getS_employee() {
		return s_employee;
	}

	public void setS_employee(String s_employee) {
		this.s_employee = s_employee;
	}

	public String getS_skill() {
		return s_skill;
	}

	public void setS_skill(String s_skill) {
		this.s_skill = s_skill;
	}

	public Integer getL_traxdoc_row_id() {
		return l_traxdoc_row_id;
	}

	public void setL_traxdoc_row_id(Integer l_traxdoc_row_id) {
		this.l_traxdoc_row_id = l_traxdoc_row_id;
	}

	public String getS_file_name_path() {
		return s_file_name_path;
	}

	public void setS_file_name_path(String s_file_name_path) {
		this.s_file_name_path = s_file_name_path;
	}

	public List<String> getS_pn_multi() {
		return s_pn_multi;
	}

	public void setS_pn_multi(List<String> s_pn_multi) {
		this.s_pn_multi = s_pn_multi;
	}

	public List<String> getS_sn_multi() {
		return s_sn_multi;
	}

	public void setS_sn_multi(List<String> s_sn_multi) {
		this.s_sn_multi = s_sn_multi;
	}

//	public DataWindow getDw() {
//		return dw;
//	}
//
//	public void setDw(DataWindow dw) {
//		this.dw = dw;
//	}

	public String getS_form() {
		return s_form;
	}

	public void setS_form(String s_form) {
		this.s_form = s_form;
	}

	public boolean isB_auto_receiving() {
		return b_auto_receiving;
	}

	public void setB_auto_receiving(boolean b_auto_receiving) {
		this.b_auto_receiving = b_auto_receiving;
	}

	public String getS_pn_restricted() {
		return s_pn_restricted;
	}

	public void setS_pn_restricted(String s_pn_restricted) {
		this.s_pn_restricted = s_pn_restricted;
	}

	public String getS_gl_company() {
		return s_gl_company;
	}

	public void setS_gl_company(String s_gl_company) {
		this.s_gl_company = s_gl_company;
	}

	public String getS_gl_expenditure() {
		return s_gl_expenditure;
	}

	public void setS_gl_expenditure(String s_gl_expenditure) {
		this.s_gl_expenditure = s_gl_expenditure;
	}

	public String getS_gl() {
		return s_gl;
	}

	public void setS_gl(String s_gl) {
		this.s_gl = s_gl;
	}

	public String getS_gl_cost_center() {
		return s_gl_cost_center;
	}

	public void setS_gl_cost_center(String s_gl_cost_center) {
		this.s_gl_cost_center = s_gl_cost_center;
	}

	public Integer getL_so_number() {
		return l_so_number;
	}

	public void setL_so_number(Integer l_so_number) {
		this.l_so_number = l_so_number;
	}

	public Integer getL_reset_actual_days() {
		return l_reset_actual_days;
	}

	public void setL_reset_actual_days(Integer l_reset_actual_days) {
		this.l_reset_actual_days = l_reset_actual_days;
	}

	public String getS_eo_filter() {
		return s_eo_filter;
	}

	public void setS_eo_filter(String s_eo_filter) {
		this.s_eo_filter = s_eo_filter;
	}

	public String getS_eo_revision() {
		return s_eo_revision;
	}

	public void setS_eo_revision(String s_eo_revision) {
		this.s_eo_revision = s_eo_revision;
	}

	public String getS_message_error1() {
		return s_message_error1;
	}

	public void setS_message_error1(String s_message_error1) {
		this.s_message_error1 = s_message_error1;
	}

	public String getS_message_error2() {
		return s_message_error2;
	}

	public void setS_message_error2(String s_message_error2) {
		this.s_message_error2 = s_message_error2;
	}

	public String getS_message_type() {
		return s_message_type;
	}

	public void setS_message_type(String s_message_type) {
		this.s_message_type = s_message_type;
	}

	public String getS_message_answer() {
		return s_message_answer;
	}

	public void setS_message_answer(String s_message_answer) {
		this.s_message_answer = s_message_answer;
	}

	public String getS_etops_check() {
		return s_etops_check;
	}

	public void setS_etops_check(String s_etops_check) {
		this.s_etops_check = s_etops_check;
	}

	public String getS_override() {
		return s_override;
	}

	public void setS_override(String s_override) {
		this.s_override = s_override;
	}

	public Date getDt_birth_date() {
		return dt_birth_date;
	}

	public void setDt_birth_date(Date dt_birth_date) {
		this.dt_birth_date = dt_birth_date;
	}

	public Date getDt_received_date() {
		return dt_received_date;
	}

	public void setDt_received_date(Date dt_received_date) {
		this.dt_received_date = dt_received_date;
	}

	public Integer getL_pool_number() {
		return l_pool_number;
	}

	public void setL_pool_number(Integer l_pool_number) {
		this.l_pool_number = l_pool_number;
	}

	public String getS_pool_type() {
		return s_pool_type;
	}

	public void setS_pool_type(String s_pool_type) {
		this.s_pool_type = s_pool_type;
	}

//	public TraxController getW_win() {
//		return w_win;
//	}
//
//	public void setW_win(TraxController w_win) {
//		this.w_win = w_win;
//	}

	public List<StPnControl> getPnControlList() {
		return pnControlList;
	}

	public void setPnControlList(List<StPnControl> pnControlList) {
		this.pnControlList = pnControlList;
	}

	public String getS_pn_description() {
		return s_pn_description;
	}

	public void setS_pn_description(String s_pn_description) {
		this.s_pn_description = s_pn_description;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getShelfLifeExpiration() {
		return shelfLifeExpiration;
	}

	public void setShelfLifeExpiration(Date shelfLifeExpiration) {
		this.shelfLifeExpiration = shelfLifeExpiration;
	}

	public Date getToolLifeExpiration() {
		return toolLifeExpiration;
	}

	public void setToolLifeExpiration(Date toolLifeExpiration) {
		this.toolLifeExpiration = toolLifeExpiration;
	}

	public List<StPn> getNlaList() {
		return nlaList;
	}

	public void setNlaList(List<StPn> nlaList) {
		this.nlaList = nlaList;
	}
	
	
}
