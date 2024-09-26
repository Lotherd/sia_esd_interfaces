package trax.aero.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import trax.aero.pojo.MT_TRAX_RCV_I28_4134_RES;
import trax.aero.pojo.MT_TRAX_SND_I28_4134_REQ;



public interface IServiceablelocationData {

	public String print(String wo,String task_card , byte[] bs, String formNo, String formLine) throws Exception ;
			
			
	 
	public Connection getCon();
	
	public void lockTable(String notificationType);
	
	public void unlockTable(String notificationType);



	public void markTransaction(MT_TRAX_RCV_I28_4134_RES response) throws Exception;

	public void setComplete(MT_TRAX_RCV_I28_4134_RES response) throws Exception;

	public void setInspLot(MT_TRAX_RCV_I28_4134_RES response) throws Exception;

	public void openCon() throws SQLException, Exception;

	public void printLabel(MT_TRAX_RCV_I28_4134_RES response);
	
	public ArrayList<MT_TRAX_SND_I28_4134_REQ> getRequests() throws Exception;
	
	public boolean lockAvailable(String notificationType);
	public void logError(String exceuted, MT_TRAX_RCV_I28_4134_RES input);



	public String setAuthority(String authority, String code);



	public void deleteCondition(String condition);



	public String setCondition(String condition, String status, String code);



	public String getCondition(String site);



	public void deleteAuthority(String authority, String code);



	public String getAuthority(String site);
}
