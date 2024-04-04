package trax.aero.interfaces;

import javax.xml.bind.JAXBException;

import trax.aero.outbound.MT_TRAX_I10_TRAX;

public interface IMaterialData {

	public String sendComponent() throws JAXBException;

	public void acceptReq(MT_TRAX_I10_TRAX reqs);

	public boolean lockAvailable(String notificationType);
	
	
	public void lockTable(String notificationType);
	
	public void unlockTable(String notificationType);

	public void logError(String error);
	
}
