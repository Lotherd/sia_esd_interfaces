package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RowOther {

    @XmlElement(name = "date")
	private String date;
    
    @XmlElement(name = "accounting_document_dt_from")
	private String accounting_document_dt_from;

    @XmlElement(name = "accounting_document_dt_to")
	private String accounting_document_dt_to;

    @XmlElement(name = "custom_duty_document")
	private String custom_duty_document;

    @XmlElement(name = "ac")
	private String ac;

    @XmlElement(name = "issue")
	private String issue;

    @XmlElement(name = "accounting_document")
	private String accounting_document;

    @XmlElement(name = "date_to")
	private String date_to;

    @XmlElement(name = "created_by")
	private String created_by;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAccounting_document_dt_from() {
		return accounting_document_dt_from;
	}

	public void setAccounting_document_dt_from(String accounting_document_dt_from) {
		this.accounting_document_dt_from = accounting_document_dt_from;
	}

	public String getAccounting_document_dt_to() {
		return accounting_document_dt_to;
	}

	public void setAccounting_document_dt_to(String accounting_document_dt_to) {
		this.accounting_document_dt_to = accounting_document_dt_to;
	}

	public String getCustom_duty_document() {
		return custom_duty_document;
	}

	public void setCustom_duty_document(String custom_duty_document) {
		this.custom_duty_document = custom_duty_document;
	}

	public String getAc() {
		return ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getAccounting_document() {
		return accounting_document;
	}

	public void setAccounting_document(String accounting_document) {
		this.accounting_document = accounting_document;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getDate_to() {
		return date_to;
	}

	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}

}    