package trax.application_standard_structure;
import trax.TraxObject;
import java.util.*;
public class st_security extends TraxObject
{
   private static final long serialVersionUID = 1L;

   public String s_user;
   public int i_transaction;
   public String s_title;
   public String s_transaction;
   public String s_category;
   public String s_application;
   public int i_handle;
   public String s_wall_require;
   public String s_wall_password = "";
   public String s_user_profile;
   public String s_dictionary[];
   public String s_password;
   public String s_database_environment;
   public String s_conect_status;
   public int l_width;
   public int l_height;
   public String s_database_enviroment_name;
   public String s_shortcut;
   public Date dt_override_date;
   public String s_override_date_flag;
   public String s_tabposition;
   public int x_pos;
   public int y_pos;
   public String s_password_pass;
   public boolean  b_running_on_webform;
   public boolean  b_trax_corp_running;
   public boolean  b_prox_card;
   public String s_turn_off_transparency;
   public boolean  b_running_trax_mobile;
   public String s_machine_name;
   public boolean  b_timer_on;
   public String s_gradient_color;
   public String s_gradient_off;
   public String s_gradient_inverse;
   public int l_session_id;
   public int l_version;
   public String s_default_login;
   public String s_transaction_type;
   public String s_authorization;
   public int l_rowfocusindicator;
   public int l_field_type;
   public int l_view_color;
   public int l_view_size;
   public String s_field_hightlight;
   public String s_schema_owner;
   
	public String getS_database_enviroment_name() {
		return s_database_enviroment_name;
	}
	public void setS_database_enviroment_name(String s_database_enviroment_name) {
		this.s_database_enviroment_name = s_database_enviroment_name;
	}
}

