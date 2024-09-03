package trax.aero.utils;

import java.util.logging.Logger;

import javax.ejb.EJB;


import trax.aero.interfaces.IMaterialStatusImportData;
import trax.aero.logger.LogManager;

public class TimerExecutor  implements Runnable{

	
	Logger logger = LogManager.getLogger("MaterialStatusImport_I11&I12");
	boolean isInsertLockhOn =false;
	boolean isPostOn = false;
	
	
	
	@EJB IMaterialStatusImportData processor;
	
	public TimerExecutor(IMaterialStatusImportData processor )
	{
		this.processor = processor;
	}

	
	@Override
	public void run() {
		
		
		
		try {
				if(processor.lockAvailable("I11_I12"))
				{
					processor.lockTable("I11_I12");
					processor.processMaterialMovementMasterQueue();
					processor.unlockTable("I11_I12");
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
