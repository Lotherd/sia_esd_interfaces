package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="MT_TRAX_SND_I43_4076_REQ", namespace="http://singaporeair.com/mro/TRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class MT_TRAX_SND_I43_4076_REQ {

	@XmlElement(name = "Dismantle_Install_Date")
	private String Dismantle_Install_Date;

	@XmlElement(name = "Position")
    private String Position;

	@XmlElement(name = "SAP_Batch")
    private String SAP_Batch;

	@XmlElement(name = "Quantity")
    private String Quantity;

	@XmlElement(name = "Material_Number")
    private String Material_Number;

	@XmlElement(name = "Storage_Location")
    private String Storage_Location;

	@XmlElement(name = "Force_Installation_Dismantling_Indicator")
    private String Force_Installation_Dismantling_Indicator;

	@XmlElement(name = "Dismantle_Install_Time")
    private String Dismantle_Install_Time;

	@XmlElement(name = "Equipment")
    private String Equipment;

	//@XmlElement(name = "Functional_Location")
    //private String Functional_Location;

	@XmlElement(name = "Serial_Number")
    private String Serial_Number;

	@XmlElement(name = "Scheduled_Removal_Indicator")
    private String Scheduled_Removal_Indicator;

	@XmlElement(name = "Unserviceable_Serviceable_Indicator")
    private String Unserviceable_Serviceable_Indicator;

	@XmlElement(name = "Reference_Order")
    private String Reference_Order;

	@XmlElement(name = "Superior_Order_Equipment")
    private String Superior_Order_Equipment;

	@XmlElement(name = "Install_Dismantle_Indicator")
    private String Install_Dismantle_Indicator;
	
	@XmlElement(name = "TRAXTRANS")
    private String TRAXTRANS;

	@XmlElement(name = "TRAXITEM")
    private String TRAXITEM;

	
    public String getDismantle_Install_Date ()
    {
        return Dismantle_Install_Date;
    }

    public void setDismantle_Install_Date (String Dismantle_Install_Date)
    {
        this.Dismantle_Install_Date = Dismantle_Install_Date;
    }

    public String getPosition ()
    {
        return Position;
    }

    public void setPosition (String Position)
    {
        this.Position = Position;
    }

    public String getSAP_Batch ()
    {
        return SAP_Batch;
    }

    public void setSAP_Batch (String SAP_Batch)
    {
        this.SAP_Batch = SAP_Batch;
    }

    public String getQuantity ()
    {
        return Quantity;
    }

    public void setQuantity (String Quantity)
    {
        this.Quantity = Quantity;
    }

    public String getMaterial_Number ()
    {
        return Material_Number;
    }

    public void setMaterial_Number (String Material_Number)
    {
        this.Material_Number = Material_Number;
    }

    public String getStorage_Location ()
    {
        return Storage_Location;
    }

    public void setStorage_Location (String Storage_Location)
    {
        this.Storage_Location = Storage_Location;
    }

    public String getForce_Installation_Dismantling_Indicator ()
    {
        return Force_Installation_Dismantling_Indicator;
    }

    public void setForce_Installation_Dismantling_Indicator (String Force_Installation_Dismantling_Indicator)
    {
        this.Force_Installation_Dismantling_Indicator = Force_Installation_Dismantling_Indicator;
    }


    public String getDismantle_Install_Time ()
    {
        return Dismantle_Install_Time;
    }

    public void setDismantle_Install_Time (String Dismantle_Install_Time)
    {
        this.Dismantle_Install_Time = Dismantle_Install_Time;
    }

    public String getEquipment ()
    {
        return Equipment;
    }

    public void setEquipment (String Equipment)
    {
        this.Equipment = Equipment;
    }
/*
    public String getFunctional_Location ()
    {
        return Functional_Location;
    }

    public void setFunctional_Location (String Functional_Location)
    {
        this.Functional_Location = Functional_Location;
    }
*/
    public String getSerial_Number ()
    {
        return Serial_Number;
    }

    public void setSerial_Number (String Serial_Number)
    {
        this.Serial_Number = Serial_Number;
    }

    public String getScheduled__Removal_Indicator ()
    {
        return Scheduled_Removal_Indicator;
    }

    public void setScheduled__Removal_Indicator (String Scheduled__Removal_Indicator)
    {
        this.Scheduled_Removal_Indicator = Scheduled__Removal_Indicator;
    }

    public String getUnserviceable_Serviceable_Indicator ()
    {
        return Unserviceable_Serviceable_Indicator;
    }

    public void setUnserviceable_Serviceable_Indicator (String Unserviceable_Serviceable_Indicator)
    {
        this.Unserviceable_Serviceable_Indicator = Unserviceable_Serviceable_Indicator;
    }

    public String getReference_Order ()
    {
        return Reference_Order;
    }

    public void setReference_Order (String Reference_Order)
    {
        this.Reference_Order = Reference_Order;
    }

    public String getSuperior_Order_Equipment ()
    {
        return Superior_Order_Equipment;
    }

    public void setSuperior_Order_Equipment (String Superior_Order_Equipment)
    {
        this.Superior_Order_Equipment = Superior_Order_Equipment;
    }

    public String getInstall_Dismantle_Indicator ()
    {
        return Install_Dismantle_Indicator;
    }

    public void setInstall_Dismantle_Indicator (String Install_Dismantle_Indicator)
    {
        this.Install_Dismantle_Indicator = Install_Dismantle_Indicator;
    }

	public String getTRAXTRANS() {
		return TRAXTRANS;
	}

	public void setTRAXTRANS(String tRAXTRANS) {
		TRAXTRANS = tRAXTRANS;
	}

	public String getTRAXITEM() {
		return TRAXITEM;
	}

	public void setTRAXITEM(String tRAXITEM) {
		TRAXITEM = tRAXITEM;
	}
	
}
