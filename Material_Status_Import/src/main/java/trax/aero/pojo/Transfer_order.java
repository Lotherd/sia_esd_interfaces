package trax.aero.pojo;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Transfer_order {

	@XmlElement(name = "Transfer_order_number")
	private BigDecimal TRASNFER_ORDER_NUMBER;
		
	@XmlElement(name = "Transfer_order_quantity")
	private BigDecimal TRANSFER_ORDER_QUANTITY;
	
	@XmlElement(name = "Batch")
	private String LEGACY_BATCH;
	
	@XmlElement(name = "Serial_number")
	private String Serial_number;

	@XmlElement(name = "Attached_document_COC")
	private byte[] AttachedDocumentIDOC;
	
	@XmlElement(name = "Sharepoint_URL_link")
	private byte[] AttachmentLinkSharepointlink;

	public byte[] getAttachedDocumentIDOC() {
		return AttachedDocumentIDOC;
	}

	public void setAttachedDocumentIDOC(byte[] attachedDocumentIDOC) {
		AttachedDocumentIDOC = attachedDocumentIDOC;
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
	
	public String getSerial_number() {
		return Serial_number;
	}

	public void setSerial_number(String serial_number) {
		Serial_number = serial_number;
	}

}
