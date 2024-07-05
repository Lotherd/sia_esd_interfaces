package trax.aero.pojo;





import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MT_TRAX_RCV_I28_4134_RES", namespace="http://singaporeair.com/mro/ESDTRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class MT_TRAX_RCV_I28_4134_RES  {
	
	
	@XmlElement(name = "RFO")
	private String rfo;
	
	@XmlElement(name = "PN")
	private String pn;
	
	@XmlElement(name = "SN")	
	private String sn;	
	
	@XmlElement(name = "INSP_LOT")
	private String inspLot;
	
	@XmlElement(name = "WO")
	private String wo;
	
	@XmlElement(name = "RELATION_CODE")
	private String relationCode;
	
	@XmlElement(name = "UD_SUCCESS")
	private String udSuccess;
	
	@XmlElement(name = "EQUIPMENT")
	private String equipment;
	
	@XmlElement(name = "WORKCENTER")
	private String workCenter;
	
	@XmlElement(name = "LEGACY_BATCH")
	private String legacyBatch;
	
	@XmlElement(name = "EXCEPTION_ID")
	private String exceptionId;
	
	@XmlElement(name = "EXCEPTON_DETAIL")
	private String exceptionDetail;

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getInspLot() {
		return inspLot;
	}

	public void setInspLot(String inspLot) {
		this.inspLot = inspLot;
	}

	public String getWo() {
		return wo;
	}

	public void setWo(String wo) {
		this.wo = wo;
	}

	public String getRelationCode() {
		return relationCode;
	}

	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	public String getUdSuccess() {
		return udSuccess;
	}

	public void setUdSuccess(String udSuccess) {
		this.udSuccess = udSuccess;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	public String getLegacyBatch() {
		return legacyBatch;
	}

	public void setLegacyBatch(String legacyBatch) {
		this.legacyBatch = legacyBatch;
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getExceptionDetail() {
		return exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}

	public String getRfo() {
		return rfo;
	}

	public void setRfo(String rfo) {
		this.rfo = rfo;
	}

	
}
