package trax.aero.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Application_Log {

	@XmlElement(name = "Message_Text")
	private String Message_Text;

	@XmlElement(name = "Message_Type")
    private String Message_Type;

    public String getMessage_Text ()
    {
        return Message_Text;
    }

    public void setMessage_Text (String Message_Text)
    {
        this.Message_Text = Message_Text;
    }

    public String getMessage_Type ()
    {
        return Message_Type;
    }

    public void setMessage_Type (String Message_Type)
    {
        this.Message_Type = Message_Type;
    }
	
	
}
