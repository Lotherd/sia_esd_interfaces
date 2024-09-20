package trax.aero.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import trax.aero.pojo.I19_Request;
import trax.aero.pojo.I19_Response;




public interface IInstallRemoveSvoData {

	public void setFailed(I19_Response response) throws Exception;
	public void logError(String error);
	public String print(String wo,String task_card , byte[] bs, String formNo, String formLine) throws Exception ;
			
	public ArrayList<I19_Request> getTransactions() throws Exception;
	
	public Connection getCon();
	
	public void lockTable(String notificationType);
	
	public void unlockTable(String notificationType);




	public String markTransaction(I19_Response request) throws Exception;


	public void openCon() throws SQLException, Exception;

	
	
	public boolean lockAvailable(String notificationType);

	public void printCCS(I19_Response input);
}
