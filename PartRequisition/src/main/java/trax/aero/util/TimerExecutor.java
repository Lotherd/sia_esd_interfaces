package trax.aero.util;

import java.util.logging.Logger;

import javax.ejb.EJB;
import trax.aero.interfaces.IPartRequisitionData;
import trax.aero.logger.LogManager;

public class TimerExecutor  implements Runnable{

	
	Logger logger = LogManager.getLogger("PartRequisition_I21");
	
	
	
	
	@EJB IPartRequisitionData processor;
	
	public TimerExecutor(IPartRequisitionData processor )
	{
		this.processor = processor;
	}
	
	
	
	@Override
	public void run() {
		try {
				if(processor.lockAvailable("I21"))
				{
					processor.lockTable("I21");
					
					processor.sendComponent();	
					
					processor.unlockTable("I21");
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	
}
