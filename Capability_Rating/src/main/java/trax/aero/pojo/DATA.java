package trax.aero.pojo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DATA {

	@JsonProperty("data")
	private DATARequest data;

	public DATARequest getData() {
		return data;
	}

	public void setData(DATARequest data) {
		this.data = data;
	}
	
	
}
