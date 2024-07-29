package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the AC_TYPE_SERIES_MASTER database table.
 * 
 */
@Entity
@Table(name="AC_TYPE_SERIES_MASTER")
@NamedQuery(name="AcTypeSeriesMaster.findAll", query="SELECT a FROM AcTypeSeriesMaster a")
public class AcTypeSeriesMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AcTypeSeriesMasterPK id;

	@Column(name="AC_TYPE_SERIES_DESCRIPTION")
	private String acTypeSeriesDescription;

	@Column(name="ACT_CONFIGURATION")
	private String actConfiguration;

	private BigDecimal aft;

	private String amm;

	private String amp;

	@Column(name="AMP_REV_DATE")
	private Date ampRevDate;

	@Column(name="AMP_REVISION")
	private String ampRevision;

	@Column(name="AMP_TEMP_REV")
	private String ampTempRev;

	@Column(name="ARM_FORMULA")
	private String armFormula;

	@Column(name="ARM_FORMULA_NOTES")
	private BigDecimal armFormulaNotes;

	private BigDecimal bft;

	@Column(name="BOAWI_FORMULA")
	private String boawiFormula;

	@Column(name="BOAWI_FORMULA_NOTES")
	private BigDecimal boawiFormulaNotes;

	@Column(name="CODE_COLOR")
	private String codeColor;

	@Column(name="CONCESSION_LIMIT")
	private BigDecimal concessionLimit;

	@Column(name="CONCESSION_LIMIT_MAX_CYCLES")
	private BigDecimal concessionLimitMaxCycles;

	@Column(name="CONCESSION_LIMIT_MAX_DAYS")
	private BigDecimal concessionLimitMaxDays;

	@Column(name="CONCESSION_LIMIT_MAX_HOURS")
	private BigDecimal concessionLimitMaxHours;

	@Column(name="CONFIGURATION_1_CODE")
	private String configuration1Code;

	@Column(name="CONFIGURATION_1_NO_SEATS")
	private BigDecimal configuration1NoSeats;

	@Column(name="CONFIGURATION_2_CODE")
	private String configuration2Code;

	@Column(name="CONFIGURATION_2_NO_SEATS")
	private BigDecimal configuration2NoSeats;

	@Column(name="CONFIGURATION_3_CODE")
	private String configuration3Code;

	@Column(name="CONFIGURATION_3_NO_SEATS")
	private BigDecimal configuration3NoSeats;

	@Column(name="CONFIGURATION_4_CODE")
	private String configuration4Code;

	@Column(name="CONFIGURATION_4_NO_SEATS")
	private BigDecimal configuration4NoSeats;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DRTS_SYSTEM_APPLICABLE")
	private String drtsSystemApplicable;

	@Column(name="EQUIPMENT_ID")
	private String equipmentId;

	@Column(name="EQUIPMENT_REF_DESIGNATOR")
	private String equipmentRefDesignator;

	private String etops;

	private String fleet;

	@Column(name="FLEET_FAMILY")
	private String fleetFamily;

	@Column(name="FLIGHT_USE")
	private String flightUse;

	@Column(name="G_TOLERANCE")
	private BigDecimal gTolerance;

	private BigDecimal height;

	@Lob
	private byte[] image;

	@Column(name="JOURNEY_CHECKS")
	private String journeyChecks;

	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	@Column(name="MAC_FORMULA")
	private String macFormula;

	@Column(name="MAC_FORMULA_NOTES")
	private BigDecimal macFormulaNotes;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="NUMBER_OF_ENGINE")
	private BigDecimal numberOfEngine;

	@Column(name="OEW_FORMULA")
	private String oewFormula;

	@Column(name="OEW_FORMULA_NOTES")
	private BigDecimal oewFormulaNotes;

	@Column(name="R_TOLERANCE")
	private BigDecimal rTolerance;

	@Column(name="RON_HOURS")
	private BigDecimal ronHours;

	@Column(name="ROTOR_WING_AC")
	private String rotorWingAc;

	@Column(name="SEPARATOR_LABEL")
	private String separatorLabel;

	@Column(name="SGML_DESIGNATOR")
	private String sgmlDesignator;

	@Column(name="SGML_IDENTIFIER")
	private String sgmlIdentifier;

	@Column(name="SW_LOC_DESC_LABEL")
	private String swLocDescLabel;

	@Column(name="SW_LOC_LABEL")
	private String swLocLabel;

	@Column(name="SW_LOC_XML_TAG")
	private String swLocXmlTag;

	@Column(name="SW_PN_DESC_LABEL")
	private String swPnDescLabel;

	@Column(name="SW_PN_LABEL")
	private String swPnLabel;

	@Column(name="SW_PN_XML_TAG")
	private String swPnXmlTag;

	@Column(name="TANK_CONFIGURATION")
	private String tankConfiguration;

	private BigDecimal weight;

	private BigDecimal width;

	@Column(name="Y_TOLERANCE")
	private BigDecimal yTolerance;

	//bi-directional many-to-one association to AcMaster
	@OneToMany(mappedBy="acTypeSeriesMaster")
	private List<AcMaster> acMasters;

	public AcTypeSeriesMaster() {
	}

	public AcTypeSeriesMasterPK getId() {
		return this.id;
	}

	public void setId(AcTypeSeriesMasterPK id) {
		this.id = id;
	}

	public String getAcTypeSeriesDescription() {
		return this.acTypeSeriesDescription;
	}

	public void setAcTypeSeriesDescription(String acTypeSeriesDescription) {
		this.acTypeSeriesDescription = acTypeSeriesDescription;
	}

	public String getActConfiguration() {
		return this.actConfiguration;
	}

	public void setActConfiguration(String actConfiguration) {
		this.actConfiguration = actConfiguration;
	}

	public BigDecimal getAft() {
		return this.aft;
	}

	public void setAft(BigDecimal aft) {
		this.aft = aft;
	}

	public String getAmm() {
		return this.amm;
	}

	public void setAmm(String amm) {
		this.amm = amm;
	}

	public String getAmp() {
		return this.amp;
	}

	public void setAmp(String amp) {
		this.amp = amp;
	}

	public Date getAmpRevDate() {
		return this.ampRevDate;
	}

	public void setAmpRevDate(Date ampRevDate) {
		this.ampRevDate = ampRevDate;
	}

	public String getAmpRevision() {
		return this.ampRevision;
	}

	public void setAmpRevision(String ampRevision) {
		this.ampRevision = ampRevision;
	}

	public String getAmpTempRev() {
		return this.ampTempRev;
	}

	public void setAmpTempRev(String ampTempRev) {
		this.ampTempRev = ampTempRev;
	}

	public String getArmFormula() {
		return this.armFormula;
	}

	public void setArmFormula(String armFormula) {
		this.armFormula = armFormula;
	}

	public BigDecimal getArmFormulaNotes() {
		return this.armFormulaNotes;
	}

	public void setArmFormulaNotes(BigDecimal armFormulaNotes) {
		this.armFormulaNotes = armFormulaNotes;
	}

	public BigDecimal getBft() {
		return this.bft;
	}

	public void setBft(BigDecimal bft) {
		this.bft = bft;
	}

	public String getBoawiFormula() {
		return this.boawiFormula;
	}

	public void setBoawiFormula(String boawiFormula) {
		this.boawiFormula = boawiFormula;
	}

	public BigDecimal getBoawiFormulaNotes() {
		return this.boawiFormulaNotes;
	}

	public void setBoawiFormulaNotes(BigDecimal boawiFormulaNotes) {
		this.boawiFormulaNotes = boawiFormulaNotes;
	}

	public String getCodeColor() {
		return this.codeColor;
	}

	public void setCodeColor(String codeColor) {
		this.codeColor = codeColor;
	}

	public BigDecimal getConcessionLimit() {
		return this.concessionLimit;
	}

	public void setConcessionLimit(BigDecimal concessionLimit) {
		this.concessionLimit = concessionLimit;
	}

	public BigDecimal getConcessionLimitMaxCycles() {
		return this.concessionLimitMaxCycles;
	}

	public void setConcessionLimitMaxCycles(BigDecimal concessionLimitMaxCycles) {
		this.concessionLimitMaxCycles = concessionLimitMaxCycles;
	}

	public BigDecimal getConcessionLimitMaxDays() {
		return this.concessionLimitMaxDays;
	}

	public void setConcessionLimitMaxDays(BigDecimal concessionLimitMaxDays) {
		this.concessionLimitMaxDays = concessionLimitMaxDays;
	}

	public BigDecimal getConcessionLimitMaxHours() {
		return this.concessionLimitMaxHours;
	}

	public void setConcessionLimitMaxHours(BigDecimal concessionLimitMaxHours) {
		this.concessionLimitMaxHours = concessionLimitMaxHours;
	}

	public String getConfiguration1Code() {
		return this.configuration1Code;
	}

	public void setConfiguration1Code(String configuration1Code) {
		this.configuration1Code = configuration1Code;
	}

	public BigDecimal getConfiguration1NoSeats() {
		return this.configuration1NoSeats;
	}

	public void setConfiguration1NoSeats(BigDecimal configuration1NoSeats) {
		this.configuration1NoSeats = configuration1NoSeats;
	}

	public String getConfiguration2Code() {
		return this.configuration2Code;
	}

	public void setConfiguration2Code(String configuration2Code) {
		this.configuration2Code = configuration2Code;
	}

	public BigDecimal getConfiguration2NoSeats() {
		return this.configuration2NoSeats;
	}

	public void setConfiguration2NoSeats(BigDecimal configuration2NoSeats) {
		this.configuration2NoSeats = configuration2NoSeats;
	}

	public String getConfiguration3Code() {
		return this.configuration3Code;
	}

	public void setConfiguration3Code(String configuration3Code) {
		this.configuration3Code = configuration3Code;
	}

	public BigDecimal getConfiguration3NoSeats() {
		return this.configuration3NoSeats;
	}

	public void setConfiguration3NoSeats(BigDecimal configuration3NoSeats) {
		this.configuration3NoSeats = configuration3NoSeats;
	}

	public String getConfiguration4Code() {
		return this.configuration4Code;
	}

	public void setConfiguration4Code(String configuration4Code) {
		this.configuration4Code = configuration4Code;
	}

	public BigDecimal getConfiguration4NoSeats() {
		return this.configuration4NoSeats;
	}

	public void setConfiguration4NoSeats(BigDecimal configuration4NoSeats) {
		this.configuration4NoSeats = configuration4NoSeats;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDrtsSystemApplicable() {
		return this.drtsSystemApplicable;
	}

	public void setDrtsSystemApplicable(String drtsSystemApplicable) {
		this.drtsSystemApplicable = drtsSystemApplicable;
	}

	public String getEquipmentId() {
		return this.equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentRefDesignator() {
		return this.equipmentRefDesignator;
	}

	public void setEquipmentRefDesignator(String equipmentRefDesignator) {
		this.equipmentRefDesignator = equipmentRefDesignator;
	}

	public String getEtops() {
		return this.etops;
	}

	public void setEtops(String etops) {
		this.etops = etops;
	}

	public String getFleet() {
		return this.fleet;
	}

	public void setFleet(String fleet) {
		this.fleet = fleet;
	}

	public String getFleetFamily() {
		return this.fleetFamily;
	}

	public void setFleetFamily(String fleetFamily) {
		this.fleetFamily = fleetFamily;
	}

	public String getFlightUse() {
		return this.flightUse;
	}

	public void setFlightUse(String flightUse) {
		this.flightUse = flightUse;
	}

	public BigDecimal getGTolerance() {
		return this.gTolerance;
	}

	public void setGTolerance(BigDecimal gTolerance) {
		this.gTolerance = gTolerance;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getJourneyChecks() {
		return this.journeyChecks;
	}

	public void setJourneyChecks(String journeyChecks) {
		this.journeyChecks = journeyChecks;
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getMacFormula() {
		return this.macFormula;
	}

	public void setMacFormula(String macFormula) {
		this.macFormula = macFormula;
	}

	public BigDecimal getMacFormulaNotes() {
		return this.macFormulaNotes;
	}

	public void setMacFormulaNotes(BigDecimal macFormulaNotes) {
		this.macFormulaNotes = macFormulaNotes;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public BigDecimal getNumberOfEngine() {
		return this.numberOfEngine;
	}

	public void setNumberOfEngine(BigDecimal numberOfEngine) {
		this.numberOfEngine = numberOfEngine;
	}

	public String getOewFormula() {
		return this.oewFormula;
	}

	public void setOewFormula(String oewFormula) {
		this.oewFormula = oewFormula;
	}

	public BigDecimal getOewFormulaNotes() {
		return this.oewFormulaNotes;
	}

	public void setOewFormulaNotes(BigDecimal oewFormulaNotes) {
		this.oewFormulaNotes = oewFormulaNotes;
	}

	public BigDecimal getRTolerance() {
		return this.rTolerance;
	}

	public void setRTolerance(BigDecimal rTolerance) {
		this.rTolerance = rTolerance;
	}

	public BigDecimal getRonHours() {
		return this.ronHours;
	}

	public void setRonHours(BigDecimal ronHours) {
		this.ronHours = ronHours;
	}

	public String getRotorWingAc() {
		return this.rotorWingAc;
	}

	public void setRotorWingAc(String rotorWingAc) {
		this.rotorWingAc = rotorWingAc;
	}

	public String getSeparatorLabel() {
		return this.separatorLabel;
	}

	public void setSeparatorLabel(String separatorLabel) {
		this.separatorLabel = separatorLabel;
	}

	public String getSgmlDesignator() {
		return this.sgmlDesignator;
	}

	public void setSgmlDesignator(String sgmlDesignator) {
		this.sgmlDesignator = sgmlDesignator;
	}

	public String getSgmlIdentifier() {
		return this.sgmlIdentifier;
	}

	public void setSgmlIdentifier(String sgmlIdentifier) {
		this.sgmlIdentifier = sgmlIdentifier;
	}

	public String getSwLocDescLabel() {
		return this.swLocDescLabel;
	}

	public void setSwLocDescLabel(String swLocDescLabel) {
		this.swLocDescLabel = swLocDescLabel;
	}

	public String getSwLocLabel() {
		return this.swLocLabel;
	}

	public void setSwLocLabel(String swLocLabel) {
		this.swLocLabel = swLocLabel;
	}

	public String getSwLocXmlTag() {
		return this.swLocXmlTag;
	}

	public void setSwLocXmlTag(String swLocXmlTag) {
		this.swLocXmlTag = swLocXmlTag;
	}

	public String getSwPnDescLabel() {
		return this.swPnDescLabel;
	}

	public void setSwPnDescLabel(String swPnDescLabel) {
		this.swPnDescLabel = swPnDescLabel;
	}

	public String getSwPnLabel() {
		return this.swPnLabel;
	}

	public void setSwPnLabel(String swPnLabel) {
		this.swPnLabel = swPnLabel;
	}

	public String getSwPnXmlTag() {
		return this.swPnXmlTag;
	}

	public void setSwPnXmlTag(String swPnXmlTag) {
		this.swPnXmlTag = swPnXmlTag;
	}

	public String getTankConfiguration() {
		return this.tankConfiguration;
	}

	public void setTankConfiguration(String tankConfiguration) {
		this.tankConfiguration = tankConfiguration;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getYTolerance() {
		return this.yTolerance;
	}

	public void setYTolerance(BigDecimal yTolerance) {
		this.yTolerance = yTolerance;
	}

	public List<AcMaster> getAcMasters() {
		return this.acMasters;
	}

	public void setAcMasters(List<AcMaster> acMasters) {
		this.acMasters = acMasters;
	}

	public AcMaster addAcMaster(AcMaster acMaster) {
		getAcMasters().add(acMaster);
		acMaster.setAcTypeSeriesMaster(this);

		return acMaster;
	}

	public AcMaster removeAcMaster(AcMaster acMaster) {
		getAcMasters().remove(acMaster);
		acMaster.setAcTypeSeriesMaster(null);

		return acMaster;
	}

}