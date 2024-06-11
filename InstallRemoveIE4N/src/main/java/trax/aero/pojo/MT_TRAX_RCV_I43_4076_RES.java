package trax.aero.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MT_TRAX_RCV_I43_4076_RES", namespace="http://singaporeair.com/mro/TRAX")
@XmlAccessorType(XmlAccessType.FIELD)
public class MT_TRAX_RCV_I43_4076_RES {

	@XmlElement(name = "Header")
	private Header Header;
	
	@XmlElement(name = "Application_Log")
	private ArrayList<Application_Log> Application_Log;

    public Header getHeader ()
    {
        return Header;
    }

    public void setHeader (Header Header)
    {
        this.Header = Header;
    }

    public ArrayList<Application_Log> getApplication_Log ()
    {
        return Application_Log;
    }

    public void setApplication_Log (ArrayList<Application_Log> Application_Log)
    {
        this.Application_Log = Application_Log;
    }
	
	
	
	
	
}
