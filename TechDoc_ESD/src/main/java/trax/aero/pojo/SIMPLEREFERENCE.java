package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="REFERENCENAME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="REFTEXT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class SIMPLEREFERENCE {


    @XmlAttribute(name = "REFERENCENAME")
    protected String referencename;
    @XmlAttribute(name = "REF-TEXT")
    protected String reftext;

    /**
     * Gets the value of the referencename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREFERENCENAME() {
        return referencename;
    }

    /**
     * Sets the value of the referencename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREFERENCENAME(String value) {
        this.referencename = value;
    }

    /**
     * Gets the value of the reftext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREFTEXT() {
        return reftext;
    }

    /**
     * Sets the value of the reftext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREFTEXT(String value) {
        this.reftext = value;
    }



}
