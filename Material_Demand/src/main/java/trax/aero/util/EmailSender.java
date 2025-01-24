package trax.aero.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import trax.aero.inbound.MT_TRAX_SND_I10_4110;
import trax.aero.inbound.OrderComponent;
import trax.aero.interfaces.IEmailSender;
import trax.aero.logger.LogManager;
import trax.aero.model.PicklistDistribution;
import trax.aero.model.Wo;
import trax.aero.outbound.Order;

@Stateless(name="EmailSender" , mappedName="EmailSender")
public class EmailSender implements IEmailSender
{
	
	Logger logger = LogManager.getLogger("MaterialDemand_I10");
	
	@PersistenceContext(unitName = "TraxESD") private EntityManager em;

	
	private String toEmail;
	
	public EmailSender(String email)
	{
		toEmail = email;
	}
	
	public EmailSender()
	{
	}
	
	public void sendEmail(String error, Wo w, String taskCard, PicklistDistribution  pick, String toEmail  ) 
	{

		try {
			String fromEmail = System.getProperty("fromEmail");
			String host = System.getProperty("fromHost");
			String port = System.getProperty("fromPort");
			
			Email email = new SimpleEmail();
			email.setHostName(host);
			email.setSmtpPort(Integer.valueOf(port));
			email.setFrom(fromEmail);
			ArrayList<String> emailsList = new ArrayList<String>(Arrays.asList(toEmail.split(",")));
			for(String toEmails : emailsList)
			{
				email.addTo(toEmails);
			}
			
			
			email.setSubject("Interface failed to create Material Reservation in SAP for"
					+ " WO: " +w.getWo()
					+ " Task Card: " + taskCard
					+ " PN: " +pick.getPn()
					);
			
			email.setMsg("WO: " + w.getWo()+System.lineSeparator() +System.lineSeparator()
					+ "WO Description : "+w.getWoDescription()  +System.lineSeparator() +System.lineSeparator()
					+ "RFO :  " +w.getRfoNo() +System.lineSeparator() +System.lineSeparator()
					+ "Material : " +pick.getPn()+System.lineSeparator() +System.lineSeparator()
					+ "Date & Time of Transaction: " +new Date().toString()+System.lineSeparator() +System.lineSeparator()
					+ "Error Message : "+error+System.lineSeparator()  +System.lineSeparator()
					+"**********************************************************" +System.lineSeparator() +System.lineSeparator()
					+"* NOTE: This is a system generated email. Do not reply *"+System.lineSeparator() +System.lineSeparator()
					+"**********************************************************" +System.lineSeparator() +System.lineSeparator()
					);
			
			
			email.send();
			
		} 
		catch (EmailException e) 
		{
			logger.severe(e.toString());
		}

		
	}

	public void sendEmail(String string, MT_TRAX_SND_I10_4110 requisition , String toEmail ) {
		PicklistDistribution require = null;
		for(trax.aero.inbound.Order o : requisition.getOrder()) {
			for(OrderComponent c : o.getOrderComponent()) {
				require = (PicklistDistribution) em.createQuery("SELECT p FROM PicklistDistribution p where p.id.picklist =:pick AND p.id.picklistLine =:line AND p.id.transaction =:tra")
						.setParameter("pick", Long.valueOf(c.getTrax_PicklistNumber()))
						.setParameter("line", Long.valueOf(c.getTrax_PicklistLine()))
						.setParameter("tra", "REQUIRE")
						.getSingleResult();
			}
		}
		Wo w = getWo(require.getPicklistHeader().getWo());
		
		sendEmail(string, w, require.getPicklistHeader().getTaskCard(), require,toEmail);
	}

	private Wo getWo(BigDecimal wo) {
		
		try
		{	
			Wo work = em.createQuery("SELECT w FROM Wo w where w.wo = :param", Wo.class)
					.setParameter("param", wo.longValue()).getSingleResult();
			return work;
		}
		catch(NoResultException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void sendEmail(String string, Order reqs , String toEmail ) {
		PicklistDistribution require = null;
		for(trax.aero.outbound.OrderComponent c : reqs.getOrderComponent()) {
				require = (PicklistDistribution) em.createQuery("SELECT p FROM PicklistDistribution p where p.id.picklist =:pick AND p.id.picklistLine =:line AND p.id.transaction =:tra")
						.setParameter("pick", Long.valueOf(c.getPICKLIST()))
						.setParameter("line", Long.valueOf(c.getPICKLIST_LINE()))
						.setParameter("tra", "REQUIRE")
						.getSingleResult();
			
		}
		Wo w = getWo(require.getPicklistHeader().getWo());
		
		sendEmail(string, w, require.getPicklistHeader().getTaskCard(), require,toEmail);		
	}

}
