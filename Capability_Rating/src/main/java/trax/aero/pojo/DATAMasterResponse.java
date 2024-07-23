package trax.aero.pojo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DATAMasterResponse {
	
	@JsonProperty("data")
	private DATAResponse data;

	public DATAResponse getData() {
		return data;
	}

	public void setData(DATAResponse data) {
		this.data = data;
	}
	
	

}
