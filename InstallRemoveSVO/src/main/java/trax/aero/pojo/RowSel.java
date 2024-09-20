package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RowSel {

    @XmlElement(name = "ac_series")
	private String ac_series;
    
	@XmlElement(name = "owner")
	private String owner;
		
	@XmlElement(name = "batch")
	private String batch;

	@XmlElement(name = "ac_type")
	private String ac_type;

	@XmlElement(name = "wo")
	private String wo;

	@XmlElement(name = "location")
	private String location;

	@XmlElement(name = "inventory_type")
	private String inventory_type;

	@XmlElement(name = "pn_category")
	private String pn_category;

	@XmlElement(name = "sn")
	private String sn;

	@XmlElement(name = "pn_sub_category")
	private String pn_sub_category;

	@XmlElement(name = "pn")
	private String pn;

	public String getAc_series() {
		return ac_series;
	}

	public void setAc_series(String ac_series) {
		this.ac_series = ac_series;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public String getWo() {
		return wo;
	}

	public void setWo(String wo) {
		this.wo = wo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInventory_type() {
		return inventory_type;
	}

	public void setInventory_type(String inventory_type) {
		this.inventory_type = inventory_type;
	}

	public String getPn_category() {
		return pn_category;
	}

	public void setPn_category(String pn_category) {
		this.pn_category = pn_category;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPn_sub_category() {
		return pn_sub_category;
	}

	public void setPn_sub_category(String pn_sub_category) {
		this.pn_sub_category = pn_sub_category;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	
}
