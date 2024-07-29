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
 *       &lt;attribute name="ATTRNAME" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ATTRVALUE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class ADDATTR {
	


    @XmlAttribute(name = "ATTR-NAME")
    protected String attrname;
    @XmlAttribute(name = "ATTR-VALUE")
    protected String attrvalue;

    /**
     * Gets the value of the attrname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRNAME() {
        return attrname;
    }

    /**
     * Sets the value of the attrname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRNAME(String value) {
        this.attrname = value;
    }

    /**
     * Gets the value of the attrvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRVALUE() {
        return attrvalue;
    }

    /**
     * Sets the value of the attrvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRVALUE(String value) {
        this.attrvalue = value;
    }



}
