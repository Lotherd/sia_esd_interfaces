package trax.aero.interfaces;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBException;

public interface Part_Data {
	
	public String setSite(String site, String recipient) throws Exception;
	
	public String deleteSite( String site) throws Exception;
	
	
	public String getSite( String site) throws Exception;

}
