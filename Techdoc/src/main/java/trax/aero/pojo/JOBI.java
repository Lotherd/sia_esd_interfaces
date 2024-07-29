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
 *         &lt;element name="PANEL" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PANELTEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="PANELNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="PANELSTATE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="PLI" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PLITEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SIMPLEREFERENCE" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="REFERENCENAME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="REFTEXT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="TOOLSLIST" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="PRTNUMBER" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="DESC" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="QTY" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="UNIT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="MATERIAL" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="MPNNUMBER" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="DESC" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="QTY" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="UNIT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ATTACHMENT" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="ATTTYPE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ADDATTR" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="ATTRNAME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="ATTRVALUE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="CHAPNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="SECTNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="SUBJNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="PLINBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="SKILL" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="JOBINBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="JOBITITLE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ZONE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="INTERVAL" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="HARDTIME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DIMENSION" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "panel",
    "pli"
})

public class JOBI {
	


    @XmlElement(name = "PANEL")
    protected List<PANEL> panel;
    @XmlElement(name = "PLI")
    protected PLI pli;
    @XmlAttribute(name = "JOBINBR")
    protected String jobinbr;
    @XmlAttribute(name = "JOBITITLE")
    protected String jobititle;
    @XmlAttribute(name = "LANGUAGE")
    protected String language;
    @XmlAttribute(name = "ZONE")
    protected String zone;
    @XmlAttribute(name = "INTERVAL")
    protected String interval;
    @XmlAttribute(name = "HARDTIME")
    protected String hardtime;
    @XmlAttribute(name = "DIMENSION")
    protected String dimension;

    /**
     * Gets the value of the panel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the panel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPANEL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PANEL }
     * 
     * 
     */
    public List<PANEL> getPANEL() {
        if (panel == null) {
            panel = new ArrayList<PANEL>();
        }
        return this.panel;
    }

    /**
     * Gets the value of the pli property.
     * 
     * @return
     *     possible object is
     *     {@link PLI }
     *     
     */
    public PLI getPLI() {
        return pli;
    }

    /**
     * Sets the value of the pli property.
     * 
     * @param value
     *     allowed object is
     *     {@link PLI }
     *     
     */
    public void setPLI(PLI value) {
        this.pli = value;
    }

    /**
     * Gets the value of the jobinbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJOBINBR() {
        return jobinbr;
    }

    /**
     * Sets the value of the jobinbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJOBINBR(String value) {
        this.jobinbr = value;
    }

    /**
     * Gets the value of the jobititle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJOBITITLE() {
        return jobititle;
    }

    /**
     * Sets the value of the jobititle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJOBITITLE(String value) {
        this.jobititle = value;
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
     * Gets the value of the zone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZONE() {
        return zone;
    }

    /**
     * Sets the value of the zone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZONE(String value) {
        this.zone = value;
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








}
