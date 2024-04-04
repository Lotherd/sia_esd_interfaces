package trax.aero.pojo;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MT_TRAX_I11_TRAX", namespace="http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class MaterialStatusImportMaster {
	

	@XmlElement(name = "RFO_NO")
	private String RFO_NO;
	    
	@XmlElement(name = "PICKLIST")
	private String PICKLIST;
		
	@XmlElement(name = "PICKLIST_LINE")
	private String PICKLIST_LINE;
	
	@XmlElement(name = "EXTERNAL_CUST_RES")
	private String EXTERNAL_CUST_RES;
	
	@XmlElement(name = "EXTERNAL_CUST_RES_ITEM")
	private String EXTERNAL_CUST_RES_ITEM;
	
	@XmlElement(name = "PN")
	private String PN;
	    
	@XmlElement(name = "TRASNFER_ORDER_NUMBER")
	private BigDecimal TRASNFER_ORDER_NUMBER;
		
	@XmlElement(name = "TRANSFER_ORDER_QUANTITY")
	private BigDecimal TRANSFER_ORDER_QUANTITY;
	
	@XmlElement(name = "LEGACY_BATCH")
	private String LEGACY_BATCH;

	@XmlElement(name = "ATTACHED_DOCUMENT_IDOC")
	private byte[] AttachedDocumentIDOC;
	
	@XmlElement(name = "ATTACHMENT_LINK_SHARE_POINT")
	private byte[] AttachmentLinkSharepointlink;

	

	public byte[] getAttachedDocumentIDOC() {
		return AttachedDocumentIDOC;
	}

	public void setAttachedDocumentIDOC(byte[] attachedDocumentIDOC) {
		AttachedDocumentIDOC = attachedDocumentIDOC;
	}

	public String getRFO_NO() {
		return RFO_NO;
	}

	public void setRFO_NO(String rFO_NO) {
		RFO_NO = rFO_NO;
	}

	public String getPICKLIST() {
		return PICKLIST;
	}

	public void setPICKLIST(String pICKLIST) {
		PICKLIST = pICKLIST;
	}

	public String getPICKLIST_LINE() {
		return PICKLIST_LINE;
	}

	public void setPICKLIST_LINE(String pICKLIST_LINE) {
		PICKLIST_LINE = pICKLIST_LINE;
	}

	public String getEXTERNAL_CUST_RES() {
		return EXTERNAL_CUST_RES;
	}

	public void setEXTERNAL_CUST_RES(String eXTERNAL_CUST_RES) {
		EXTERNAL_CUST_RES = eXTERNAL_CUST_RES;
	}

	public String getEXTERNAL_CUST_RES_ITEM() {
		return EXTERNAL_CUST_RES_ITEM;
	}

	public void setEXTERNAL_CUST_RES_ITEM(String eXTERNAL_CUST_RES_ITEM) {
		EXTERNAL_CUST_RES_ITEM = eXTERNAL_CUST_RES_ITEM;
	}

	public String getPN() {
		return PN;
	}

	public void setPN(String pN) {
		PN = pN;
	}

	public BigDecimal getTRASNFER_ORDER_NUMBER() {
		return TRASNFER_ORDER_NUMBER;
	}

	public void setTRASNFER_ORDER_NUMBER(BigDecimal tRASNFER_ORDER_NUMBER) {
		TRASNFER_ORDER_NUMBER = tRASNFER_ORDER_NUMBER;
	}

	public BigDecimal getTRANSFER_ORDER_QUANTITY() {
		return TRANSFER_ORDER_QUANTITY;
	}

	public void setTRANSFER_ORDER_QUANTITY(BigDecimal tRANSFER_ORDER_QUANTITY) {
		TRANSFER_ORDER_QUANTITY = tRANSFER_ORDER_QUANTITY;
	}

	public String getLEGACY_BATCH() {
		return LEGACY_BATCH;
	}

	public void setLEGACY_BATCH(String lEGACY_BATCH) {
		LEGACY_BATCH = lEGACY_BATCH;
	}

	public byte[] getAttachmentLinkSharepointlink() {
		return AttachmentLinkSharepointlink;
	}

	public void setAttachmentLinkSharepointlink(byte[] attachmentLinkSharepointlink) {
		AttachmentLinkSharepointlink = attachmentLinkSharepointlink;
	}

}
