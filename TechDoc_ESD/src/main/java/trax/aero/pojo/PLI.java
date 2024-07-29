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
 *         &lt;element name="PLITEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SIMPLEREFERENCE" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="REFERENCENAME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="REFTEXT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TOOLSLIST" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="PRTNUMBER" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="DESC" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="QTY" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="UNIT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MATERIAL" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="MPNNUMBER" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="DESC" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="QTY" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="UNIT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ATTACHMENT" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="ATTTYPE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ADDATTR" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="ATTRNAME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ATTRVALUE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="CHAPNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="SECTNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="SUBJNBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PLINBR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="SKILL" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "plitext",
    "simplereference",
    "toolslist",
    "material",
    "attachment",
    "addattr"
})
public class PLI {
	


    @XmlElement(name = "PLITEXT")
    protected String plitext;
    @XmlElement(name = "SIMPLEREFERENCE")
    protected List<SIMPLEREFERENCE> simplereference;
    @XmlElement(name = "TOOLSLIST")
    protected List<TOOLSLIST> toolslist;
    @XmlElement(name = "MATERIAL")
    protected List<MATERIAL> material;
    @XmlElement(name = "ATTACHMENT")
    protected List<ATTACHMENT> attachment;
    @XmlElement(name = "ADDATTR")
    protected List<ADDATTR> addattr;
    @XmlAttribute(name = "CHAPNBR")
    protected String chapnbr;
    @XmlAttribute(name = "SECTNBR")
    protected String sectnbr;
    @XmlAttribute(name = "SUBJNBR")
    protected String subjnbr;
    @XmlAttribute(name = "PLINBR")
    protected String plinbr;
    @XmlAttribute(name = "SKILL")
    protected String skill;
    @XmlAttribute(name = "LANGUAGE")
    protected String language;

    /**
     * Gets the value of the plitext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPLITEXT() {
        return plitext;
    }

    /**
     * Sets the value of the plitext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPLITEXT(String value) {
        this.plitext = value;
    }

    /**
     * Gets the value of the simplereference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the simplereference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSIMPLEREFERENCE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SIMPLEREFERENCE }
     * 
     * 
     */
    public List<SIMPLEREFERENCE> getSIMPLEREFERENCE() {
        if (simplereference == null) {
            simplereference = new ArrayList<SIMPLEREFERENCE>();
        }
        return this.simplereference;
    }

    /**
     * Gets the value of the toolslist property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the toolslist property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTOOLSLIST().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TOOLSLIST }
     * 
     * 
     */
    public List<TOOLSLIST> getTOOLSLIST() {
        if (toolslist == null) {
            toolslist = new ArrayList<TOOLSLIST>();
        }
        return this.toolslist;
    }

    /**
     * Gets the value of the material property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the material property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMATERIAL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MATERIAL }
     * 
     * 
     */
    public List<MATERIAL> getMATERIAL() {
        if (material == null) {
            material = new ArrayList<MATERIAL>();
        }
        return this.material;
    }

    /**
     * Gets the value of the attachment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getATTACHMENT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ATTACHMENT }
     * 
     * 
     */
    public List<ATTACHMENT> getATTACHMENT() {
        if (attachment == null) {
            attachment = new ArrayList<ATTACHMENT>();
        }
        return this.attachment;
    }

    /**
     * Gets the value of the addattr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addattr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getADDATTR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ADDATTR }
     * 
     * 
     */
    public List<ADDATTR> getADDATTR() {
        if (addattr == null) {
            addattr = new ArrayList<ADDATTR>();
        }
        return this.addattr;
    }

    /**
     * Gets the value of the chapnbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCHAPNBR() {
        return chapnbr;
    }

    /**
     * Sets the value of the chapnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHAPNBR(String value) {
        this.chapnbr = value;
    }

    /**
     * Gets the value of the sectnbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSECTNBR() {
        return sectnbr;
    }

    /**
     * Sets the value of the sectnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSECTNBR(String value) {
        this.sectnbr = value;
    }

    /**
     * Gets the value of the subjnbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUBJNBR() {
        return subjnbr;
    }

    /**
     * Sets the value of the subjnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUBJNBR(String value) {
        this.subjnbr = value;
    }

    /**
     * Gets the value of the plinbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPLINBR() {
        return plinbr;
    }

    /**
     * Sets the value of the plinbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPLINBR(String value) {
        this.plinbr = value;
    }

    /**
     * Gets the value of the skill property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSKILL() {
        return skill;
    }

    /**
     * Sets the value of the skill property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSKILL(String value) {
        this.skill = value;
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


   





}
