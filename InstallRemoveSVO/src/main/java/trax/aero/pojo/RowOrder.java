package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RowOrder {

	@XmlElement(name = "bin")
	private String bin;

    @XmlElement(name = "order_number")
	private String order_number;

    @XmlElement(name = "custom_status")
	private String custom_status;

    @XmlElement(name = "transaction_type")
	private String transaction_type;

    @XmlElement(name = "awb")
	private String awb;

    @XmlElement(name = "goods_rcvd_batch")
	private String goods_rcvd_batch;

    @XmlElement(name = "reference")
	private String reference;

    @XmlElement(name = "condition")
	private String condition;

    @XmlElement(name = "order_type")
	private String order_type;

    @XmlElement(name = "tag_no")
	private String tag_no;

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getCustom_status() {
		return custom_status;
	}

	public void setCustom_status(String custom_status) {
		this.custom_status = custom_status;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getAwb() {
		return awb;
	}

	public void setAwb(String awb) {
		this.awb = awb;
	}

	public String getGoods_rcvd_batch() {
		return goods_rcvd_batch;
	}

	public void setGoods_rcvd_batch(String goods_rcvd_batch) {
		this.goods_rcvd_batch = goods_rcvd_batch;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getTag_no() {
		return tag_no;
	}

	public void setTag_no(String tag_no) {
		this.tag_no = tag_no;
	}

}
