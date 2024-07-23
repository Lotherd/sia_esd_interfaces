package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Operation_TRAX {
	
	@XmlElement(name = "Posting_date")
	private String Posting_date;
	
	@XmlElement(name = "Plant")
	private String Plant;
	
	@XmlElement(name = "Batch")
	private String Batch;
	
	@XmlElement(name = "Material")
	private String Material;
	
	@XmlElement(name = "Qty")
	private String Qty;
	
	@XmlElement(name = "IUOM")
	private String IUOM;
	
	@XmlElement(name = "Sell_Total_Price")
	private String Sell_Total_Price;
	
	@XmlElement(name = "Currency")
	private String Currency;
	
	@XmlElement(name = "Storage_location")
	private String Storage_location;

	public String getPosting_date() {
		return Posting_date;
	}

	public void setPosting_date(String posting_date) {
		Posting_date = posting_date;
	}

	public String getPlant() {
		return Plant;
	}

	public void setPlant(String plant) {
		Plant = plant;
	}

	public String getBatch() {
		return Batch;
	}

	public void setBatch(String batch) {
		Batch = batch;
	}

	public String getMaterial() {
		return Material;
	}

	public void setMaterial(String material) {
		Material = material;
	}

	public String getQty() {
		return Qty;
	}

	public void setQty(String qty) {
		Qty = qty;
	}

	public String getIUOM() {
		return IUOM;
	}

	public void setIUOM(String iUOM) {
		IUOM = iUOM;
	}

	public String getSell_Total_Price() {
		return Sell_Total_Price;
	}

	public void setSell_Total_Price(String sell_Total_Price) {
		Sell_Total_Price = sell_Total_Price;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getStorage_location() {
		return Storage_location;
	}

	public void setStorage_location(String storage_location) {
		Storage_location = storage_location;
	}
	
	

}
