package trax.aero.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="PN_AUTHORITY_ESD")
@NamedQuery(name="PartAuthorityESD.findAll", query="SELECT p FROM PartAuthorityESD p")
public class PartAuthorityESD  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private PartAuthorityESD_PK id;
	
	@Column(name="PN_TYPE")
    private String pnType;
	
    @Column(name="AUTHORITY_DATE")
    private String authorityDate;

    @Column(name="WORKSHOP")
    private String workshop;

    @Column(name="COMP_CAPABILITY")
    private String compCapability;

    @Column(name="QLTY_STATUS")
    private String qltyStatus;

    @Column(name="TECH_CONTROL")
    private String techControl;

    @Column(name="REV_NUMBER")
    private Integer revNumber;

    @Column(name="CREATED_BY")
    private String createdBy;

    @Column(name="CREATED_DATE")
    private Date createdDate;

    @Column(name="MODIFIED_BY")
    private String modifiedBy;

    @Column(name="MODIFIED_DATE")
    private Date modifiedDate;
    
    public PartAuthorityESD() {
    }

	public PartAuthorityESD_PK getId() {
		return id;
	}

	public void setId(PartAuthorityESD_PK id) {
		this.id = id;
	}

	public String getPnType() {
		return pnType;
	}

	public void setPnType(String pnType) {
		this.pnType = pnType;
	}

	public String getAuthorityDate() {
		return authorityDate;
	}

	public void setAuthorityDate(String authorityDate) {
		this.authorityDate = authorityDate;
	}

	public String getWorkshop() {
		return workshop;
	}

	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}

	public String getCompCapability() {
		return compCapability;
	}

	public void setCompCapability(String compCapability) {
		this.compCapability = compCapability;
	}

	public String getQltyStatus() {
		return qltyStatus;
	}

	public void setQltyStatus(String qltyStatus) {
		this.qltyStatus = qltyStatus;
	}

	public String getTechControl() {
		return techControl;
	}

	public void setTechControl(String techControl) {
		this.techControl = techControl;
	}

	public Integer getRevNumber() {
		return revNumber;
	}

	public void setRevNumber(Integer revNumber) {
		this.revNumber = revNumber;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    

}
