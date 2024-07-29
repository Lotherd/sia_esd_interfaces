package trax.aero.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="JOBI" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PANEL" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="PANELTEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="PANELNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="PANELSTATE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="PLI" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="PLITEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="SIMPLEREFERENCE" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="REFERENCENAME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="REFTEXT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="TOOLSLIST" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="PRTNUMBER" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="DESC" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="QTY" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="UNIT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="MATERIAL" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="MPNNUMBER" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="DESC" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="QTY" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="UNIT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="ATTACHMENT" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="ATTTYPE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="ADDATTR" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="ATTRNAME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="ATTRVALUE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="CHAPNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="SECTNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="SUBJNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="PLINBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="SKILL" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="JOBINBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="JOBITITLE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ZONE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="INTERVAL" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="HARDTIME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="DIMENSION" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="JCNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="JOBNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="JCTITLE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="CENTER" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ISSUENBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ISSUEDATE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="WPID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="WPNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="WPTITLE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="WPDATE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ENGINESN" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ENGINEPOS" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="APU" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="APUSN" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ORIGINATOR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="SEQNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="AREACODE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="AREA" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TRADECODE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TRADE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="POS" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="MANHRS" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="WONBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="CREWSIZE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DURATION" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="INTERVAL" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="HARDTIME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DIMENSION" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="OPPORTUNITY" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TYPE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "jobi"
})
public class JOBCARD {


    @XmlElement(name = "JOBI")
    protected JOBI jobi;
    @XmlAttribute(name = "JCNBR")
    protected String jcnbr;
    @XmlAttribute(name = "JOBNBR")
    protected String jobnbr;
    @XmlAttribute(name = "JC-TITLE")
    protected String jctitle;
    @XmlAttribute(name = "CENTER")
    protected String center;
    @XmlAttribute(name = "ISSUE-NBR")
    protected String issuenbr;
    @XmlAttribute(name = "ISSUE-DATE")
    protected String issuedate;
    @XmlAttribute(name = "WP-ID")
    protected String wpid;
    @XmlAttribute(name = "WP-NBR")
    protected String wpnbr;
    @XmlAttribute(name = "WP-TITLE")
    protected String wptitle;
    @XmlAttribute(name = "WP-DATE")
    protected String wpdate;
    @XmlAttribute(name = "ENGINE-SN")
    protected String enginesn;
    @XmlAttribute(name = "ENGINE-POS")
    protected String enginepos;
    @XmlAttribute(name = "APU")
    protected String apu;
    @XmlAttribute(name = "APU-SN")
    protected String apusn;
    @XmlAttribute(name = "ORIGINATOR")
    protected String originator;
    @XmlAttribute(name = "SEQ-NBR")
    protected String seqnbr;
    @XmlAttribute(name = "AREA-CODE")
    protected String areacode;
    @XmlAttribute(name = "AREA")
    protected String area;
    @XmlAttribute(name = "TRADE-CODE")
    protected String tradecode;
    @XmlAttribute(name = "TRADE")
    protected String trade;
    @XmlAttribute(name = "POS")
    protected String pos;
    @XmlAttribute(name = "MAN-HRS")
    protected String manhrs;
    @XmlAttribute(name = "WO-NBR")
    protected String wonbr;
    @XmlAttribute(name = "LANGUAGE")
    protected String language;
    @XmlAttribute(name = "CREWSIZE")
    protected String crewsize;
    @XmlAttribute(name = "DURATION")
    protected String duration;
    @XmlAttribute(name = "INTERVAL")
    protected String interval;
    @XmlAttribute(name = "HARDTIME")
    protected String hardtime;
    @XmlAttribute(name = "DIMENSION")
    protected String dimension;
    @XmlAttribute(name = "OPPORTUNITY")
    protected String opportunity;
    @XmlAttribute(name = "TYPE")
    protected String type;

    /**
     * Gets the value of the jobi property.
     * 
     * @return
     *     possible object is
     *     {@link JOBI }
     *     
     */
    public JOBI getJOBI() {
        return jobi;
    }

    /**
     * Sets the value of the jobi property.
     * 
     * @param value
     *     allowed object is
     *     {@link JOBI }
     *     
     */
    public void setJOBI(JOBI value) {
        this.jobi = value;
    }

    /**
     * Gets the value of the jcnbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJCNBR() {
        return jcnbr;
    }

    /**
     * Sets the value of the jcnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJCNBR(String value) {
        this.jcnbr = value;
    }

    /**
     * Gets the value of the jobnbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJOBNBR() {
        return jobnbr;
    }

    /**
     * Sets the value of the jobnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJOBNBR(String value) {
        this.jobnbr = value;
    }

    /**
     * Gets the value of the jctitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJCTITLE() {
        return jctitle;
    }

    /**
     * Sets the value of the jctitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJCTITLE(String value) {
        this.jctitle = value;
    }

    /**
     * Gets the value of the center property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCENTER() {
        return center;
    }

    /**
     * Sets the value of the center property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCENTER(String value) {
        this.center = value;
    }

    /**
     * Gets the value of the issuenbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISSUENBR() {
        return issuenbr;
    }

    /**
     * Sets the value of the issuenbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISSUENBR(String value) {
        this.issuenbr = value;
    }

    /**
     * Gets the value of the issuedate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISSUEDATE() {
        return issuedate;
    }

    /**
     * Sets the value of the issuedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISSUEDATE(String value) {
        this.issuedate = value;
    }

    /**
     * Gets the value of the wpid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWPID() {
        return wpid;
    }

    /**
     * Sets the value of the wpid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWPID(String value) {
        this.wpid = value;
    }

    /**
     * Gets the value of the wpnbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWPNBR() {
        return wpnbr;
    }

    /**
     * Sets the value of the wpnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWPNBR(String value) {
        this.wpnbr = value;
    }

    /**
     * Gets the value of the wptitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWPTITLE() {
        return wptitle;
    }

    /**
     * Sets the value of the wptitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWPTITLE(String value) {
        this.wptitle = value;
    }

    /**
     * Gets the value of the wpdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWPDATE() {
        return wpdate;
    }

    /**
     * Sets the value of the wpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWPDATE(String value) {
        this.wpdate = value;
    }

    /**
     * Gets the value of the enginesn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getENGINESN() {
        return enginesn;
    }

    /**
     * Sets the value of the enginesn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENGINESN(String value) {
        this.enginesn = value;
    }

    /**
     * Gets the value of the enginepos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getENGINEPOS() {
        return enginepos;
    }

    /**
     * Sets the value of the enginepos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENGINEPOS(String value) {
        this.enginepos = value;
    }

    /**
     * Gets the value of the apu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPU() {
        return apu;
    }

    /**
     * Sets the value of the apu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPU(String value) {
        this.apu = value;
    }

    /**
     * Gets the value of the apusn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPUSN() {
        return apusn;
    }

    /**
     * Sets the value of the apusn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPUSN(String value) {
        this.apusn = value;
    }

    /**
     * Gets the value of the originator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORIGINATOR() {
        return originator;
    }

    /**
     * Sets the value of the originator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORIGINATOR(String value) {
        this.originator = value;
    }

    /**
     * Gets the value of the seqnbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEQNBR() {
        return seqnbr;
    }

    /**
     * Sets the value of the seqnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEQNBR(String value) {
        this.seqnbr = value;
    }

    /**
     * Gets the value of the areacode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAREACODE() {
        return areacode;
    }

    /**
     * Sets the value of the areacode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAREACODE(String value) {
        this.areacode = value;
    }

    /**
     * Gets the value of the area property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAREA() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAREA(String value) {
        this.area = value;
    }

    /**
     * Gets the value of the tradecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRADECODE() {
        return tradecode;
    }

    /**
     * Sets the value of the tradecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRADECODE(String value) {
        this.tradecode = value;
    }

    /**
     * Gets the value of the trade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRADE() {
        return trade;
    }

    /**
     * Sets the value of the trade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRADE(String value) {
        this.trade = value;
    }

    /**
     * Gets the value of the pos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOS() {
        return pos;
    }

    /**
     * Sets the value of the pos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOS(String value) {
        this.pos = value;
    }

    /**
     * Gets the value of the manhrs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMANHRS() {
        return manhrs;
    }

    /**
     * Sets the value of the manhrs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMANHRS(String value) {
        this.manhrs = value;
    }

    /**
     * Gets the value of the wonbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWONBR() {
        return wonbr;
    }

    /**
     * Sets the value of the wonbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWONBR(String value) {
        this.wonbr = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLANGUAGE() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLANGUAGE(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the crewsize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCREWSIZE() {
        return crewsize;
    }

    /**
     * Sets the value of the crewsize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCREWSIZE(String value) {
        this.crewsize = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDURATION() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDURATION(String value) {
        this.duration = value;
    }

    /**
     * Gets the value of the interval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINTERVAL() {
        return interval;
    }

    /**
     * Sets the value of the interval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINTERVAL(String value) {
        this.interval = value;
    }

    /**
     * Gets the value of the hardtime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHARDTIME() {
        return hardtime;
    }

    /**
     * Sets the value of the hardtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHARDTIME(String value) {
        this.hardtime = value;
    }

    /**
     * Gets the value of the dimension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDIMENSION() {
        return dimension;
    }

    /**
     * Sets the value of the dimension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDIMENSION(String value) {
        this.dimension = value;
    }

    /**
     * Gets the value of the opportunity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOPPORTUNITY() {
        return opportunity;
    }

    /**
     * Sets the value of the opportunity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOPPORTUNITY(String value) {
        this.opportunity = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTYPE() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTYPE(String value) {
        this.type = value;
    }





}
