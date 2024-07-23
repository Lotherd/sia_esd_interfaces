package trax.aero.start;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import trax.aero.interfaces.ICapability_Rating_Data;
import trax.aero.logger.LogManager;
import trax.aero.utils.RunAble;

@Startup
@Singleton
public class Start
{

	static Logger logger = LogManager.getLogger("CapabilityRat");
	private ScheduledExecutorService scheduledServ;
	RunAble timer = null;
	@EJB ICapability_Rating_Data data;

	
	@PostConstruct
	public void start()
	{
		timer = new RunAble(data);
		
		if (scheduledServ == null) {
			int scheduledPoolSize = 1;
			logger.info("Creating default Scheduled Executor Service [poolSize =" + String.valueOf(scheduledPoolSize) + "]");
			this.scheduledServ = Executors.newScheduledThreadPool(scheduledPoolSize);
		}
		scheduledServ.scheduleAtFixedRate(timer, 30, Long.parseLong(System.getProperty("CapabilityRat_Interval", "20")), TimeUnit.SECONDS);
	
	}
	@PreDestroy
	public void stop() 
	{
		if(!scheduledServ.isShutdown()) 
		{
			scheduledServ.shutdown();
		}
	}
	
}
