package trax.aero.interfaces;

import javax.xml.bind.JAXBException;

import trax.aero.outbound.MT_TRAX_RCV_I21_4121_RES;


public interface IPartRequisitionData {
	
	public String sendComponent() throws JAXBException;

	public void acceptReq(MT_TRAX_RCV_I21_4121_RES reqs);

	public boolean lockAvailable(String notificationType);
	
	
	public void lockTable(String notificationType);
	
	public void unlockTable(String notificationType);
	
}
