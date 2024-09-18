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



	public void setInspLot(MT_TRAX_RCV_I28_4134_RES response) throws Exception;

	public void openCon() throws SQLException, Exception;

	public void printLabel(MT_TRAX_RCV_I28_4134_RES response);
	
	public ArrayList<MT_TRAX_SND_I28_4134_REQ> getRequests() throws Exception;
	
	public boolean lockAvailable(String notificationType);
}
