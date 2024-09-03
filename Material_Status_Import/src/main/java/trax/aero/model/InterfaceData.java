package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the INTERFACE_DATA database table.
 * 
 */
@Entity
@Table(name="INTERFACE_DATA")
@NamedQuery(name="InterfaceData.findAll", query="SELECT i FROM InterfaceData i")
public class InterfaceData implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InterfaceDataPK id;

	@Lob
	@Column(name="CLOB_DOCUMENT")
	private String clobDocument;

	@Column(name="XML_DOCUMENT")
	private String xmlDocument;

	//bi-directional many-to-one association to InterfaceAudit
	@ManyToOne
	@JoinColumns(
		@JoinColumn(name="\"TRANSACTION\"", referencedColumnName="\"TRANSACTION\"", insertable=false, updatable=false)
		)
	private InterfaceAudit interfaceAudit;

	public InterfaceData() {
	}

	public InterfaceDataPK getId() {
		return this.id;
	}

	public void setId(InterfaceDataPK id) {
		this.id = id;
	}

	public String getClobDocument() {
		return this.clobDocument;
	}

	public void setClobDocument(String clobDocument) {
		this.clobDocument = clobDocument;
	}

	public String getXmlDocument() {
		return this.xmlDocument;
	}

	public void setXmlDocument(String xmlDocument) {
		this.xmlDocument = xmlDocument;
	}

	public InterfaceAudit getInterfaceAudit() {
		return this.interfaceAudit;
	}

	public void setInterfaceAudit(InterfaceAudit interfaceAudit) {
		this.interfaceAudit = interfaceAudit;
	}

}