package trax.aero.pojo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"Transaction",
"transaction",

})
@Generated("IE4N")
public class IE4N {

	@JsonProperty("Transaction")
	private String Transaction;
	
	@JsonProperty("transaction")
	private String trans;
	
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Transaction")
    public String getTransaction ()
    {
        return Transaction;
    }

    @JsonProperty("Transaction")
    public void setTransaction (String Transaction)
    {
        this.Transaction = Transaction;
    }

   
    
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    }

    @JsonProperty("transaction")
	public String getTrans() {
		return trans;
	}

    @JsonProperty("transaction")
	public void setTrans(String trans) {
		this.trans = trans;
	}

  
	
}
