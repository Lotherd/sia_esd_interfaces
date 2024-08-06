package trax.aero.interfaces;

import trax.aero.inbound.MT_TRAX_SND_I10_4110;
import trax.aero.model.PicklistDistribution;
import trax.aero.model.Wo;
import trax.aero.outbound.Order;

public interface IEmailSender {
	
	public void sendEmail(String error, Wo w, String taskCard, PicklistDistribution  pick,String email  );

	public void sendEmail(String string, MT_TRAX_SND_I10_4110 requisition, String email);

	public void sendEmail(String string, Order reqs, String email);
	

}
