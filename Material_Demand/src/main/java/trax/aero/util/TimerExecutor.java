package trax.aero.util;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import trax.aero.inbound.MT_TRAX_SND_I10_4110;
import trax.aero.interfaces.IMaterialData;
import trax.aero.logger.LogManager;

public class TimerExecutor  implements Runnable{

	
	Logger logger = LogManager.getLogger("MaterialDemand_I10");
	boolean isInsertLockhOn =false;
	boolean isPostOn = false;
	
	
	
	@EJB IMaterialData processor;
	
	public TimerExecutor(IMaterialData processor )
	{
		this.processor = processor;
	}
	
	private void process() {
		// TODO Auto-generated method stub
		
				try
				{
					//logger.info("Searching for parts");
					JAXBContext jc = JAXBContext.newInstance(MT_TRAX_SND_I10_4110.class);
					Unmarshaller unmarshaller = jc.createUnmarshaller();
//					File folder = new File(System.getProperty("CM_fileLoc"));
		//
//					File[] files = folder.listFiles();
					
					String success = processor.sendComponent();
									
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.severe(e.toString());
				}
	}
	
	@Override
	public void run() {
		
		
		
		try {
			if(isPostOn()) {
				if(processor.lockAvailable("I10"))
				{
					processor.lockTable("I10");
					process();
					processor.unlockTable("I10");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isInsertLockhOn() {
		return isInsertLockhOn;
	}

	public void setInsertLockhOn(boolean isInsertLockhOn) {
		this.isInsertLockhOn = isInsertLockhOn;
	}

	public boolean isPostOn() {
		return isPostOn;
	}

	public void setPostOn(boolean isPostOn) {
		this.isPostOn = isPostOn;
	}
	
	
	
	
}
