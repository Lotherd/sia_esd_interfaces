package trax.aero.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "root")
@XmlType(propOrder={"dw_inventory_detail_history_print_sel", "dw_inventory_detail_history_order_print"
		, "dw_inventory_detail_history_other_print"})
public class Root {

	private dw_inventory_detail_history_print_sel dw_inventory_detail_history_print_sel;
	
	private dw_inventory_detail_history_order_print dw_inventory_detail_history_order_print;
	
	private dw_inventory_detail_history_other_print dw_inventory_detail_history_other_print;

	public dw_inventory_detail_history_print_sel getDw_inventory_detail_history_print_sel() {
		return dw_inventory_detail_history_print_sel;
	}

	public void setDw_inventory_detail_history_print_sel(dw_inventory_detail_history_print_sel dw_inventory_detail_history_print_sel) {
		this.dw_inventory_detail_history_print_sel = dw_inventory_detail_history_print_sel;
	}

	public dw_inventory_detail_history_order_print getDw_inventory_detail_history_order_print() {
		return dw_inventory_detail_history_order_print;
	}

	public void setDw_inventory_detail_history_order_print(dw_inventory_detail_history_order_print dw_inventory_detail_history_order_print) {
		this.dw_inventory_detail_history_order_print = dw_inventory_detail_history_order_print;
	}

	public dw_inventory_detail_history_other_print getDw_inventory_detail_history_other_print() {
		return dw_inventory_detail_history_other_print;
	}

	public void setDw_inventory_detail_history_other_print(dw_inventory_detail_history_other_print dw_inventory_detail_history_other_print) {
		this.dw_inventory_detail_history_other_print = dw_inventory_detail_history_other_print;
	}

	

}
