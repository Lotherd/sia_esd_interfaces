package trax.aero.interfaces;

import trax.aero.pojo.MaterialStatusImportMaster;
import trax.aero.pojo.OpsLineEmail;

public interface IMaterialStatusImportData {

	public String updateMaterial(MaterialStatusImportMaster materialMovementMaster);
	
	public byte[] getsharePointfile(String spurl);
		
	public int ordinalIndexOf(String str, String substr, int n);

	void logError(String error);

	public void logMaterialMovementMaster(String string);

	public void processMaterialMovementMasterQueue();

	public void unlockTable(String string);

	public void lockTable(String string);

	public boolean lockAvailable(String string);

	
}
