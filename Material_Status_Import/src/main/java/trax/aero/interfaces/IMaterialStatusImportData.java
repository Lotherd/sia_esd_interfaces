package trax.aero.interfaces;

import trax.aero.pojo.MaterialStatusImportMaster;
import trax.aero.pojo.OpsLineEmail;

public interface IMaterialStatusImportData {

	public String updateMaterial(MaterialStatusImportMaster materialMovementMaster);
	
	public byte[] getsharePointfile(String spurl);
		
	public int ordinalIndexOf(String str, String substr, int n);

	void logError(String error);

	
}
