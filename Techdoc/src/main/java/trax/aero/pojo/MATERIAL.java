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
 *       &lt;attribute name="MPNNUMBER" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DESC" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="QTY" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="UNIT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class MATERIAL {


    @XmlAttribute(name = "MPN-NUMBER")
    protected String mpnnumber;
    @XmlAttribute(name = "DESC")
    protected String desc;
    @XmlAttribute(name = "QTY")
    protected String qty;
    @XmlAttribute(name = "UNIT")
    protected String unit;
    
    @XmlAttribute(name = "RSNUM")
    protected String rsnum;
    @XmlAttribute(name = "RSPOS")
    protected String rspos;

    public String getRsnum() {
        return rsnum;
    }  
    public void setRsnum(String value) {
        this.rsnum = value;
    }
    
    public String getRspos() {
        return rspos;
    }  
    public void setRspos(String value) {
        this.rspos = value;
    }
    
    /**
     * Gets the value of the mpnnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMPNNUMBER() {
        return mpnnumber;
    }

    /**
     * Sets the value of the mpnnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMPNNUMBER(String value) {
        this.mpnnumber = value;
    }

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESC() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESC(String value) {
        this.desc = value;
    }

    /**
     * Gets the value of the qty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQTY() {
        return qty;
    }

    /**
     * Sets the value of the qty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQTY(String value) {
        this.qty = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNIT() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNIT(String value) {
        this.unit = value;
    }



}
