package trax.aero.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DATARequest {
	
	@JsonProperty("clcfNo")
	private String clcfNo;
	
	@JsonProperty("compCapability")
	private String compCapability;
	
	@JsonProperty("workshop")
	private String workshop;
	
	@JsonProperty("partNo")
	private String partNo;
	
	@JsonProperty("qltyStatus")
	private String qltyStatus;
	
	@JsonProperty("authorityType")
	private String authorityType;
	
	@JsonProperty("catCCategory")
	private String catCategory;
	
	@JsonProperty("revNo")
	private String revNo;
	
	@JsonProperty("date")
	private String date;

	public String getClcfNo() {
		return clcfNo;
	}

	public void setClcfNo(String clcfNo) {
		this.clcfNo = clcfNo;
	}

	public String getCompCapability() {
		return compCapability;
	}

	public void setCompCapability(String compCapability) {
		this.compCapability = compCapability;
	}

	public String getWorkshop() {
		return workshop;
	}

	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getQltyStatus() {
		return qltyStatus;
	}

	public void setQltyStatus(String qltyStatus) {
		this.qltyStatus = qltyStatus;
	}

	public String getAuthorityType() {
		return authorityType;
	}

	public void setAuthorityType(String authorityType) {
		this.authorityType = authorityType;
	}

	public String getCatCategory() {
		return catCategory;
	}

	public void setCatCategory(String catCategory) {
		this.catCategory = catCategory;
	}

	public String getRevNo() {
		return revNo;
	}

	public void setRevNo(String revNo) {
		this.revNo = revNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	

}
