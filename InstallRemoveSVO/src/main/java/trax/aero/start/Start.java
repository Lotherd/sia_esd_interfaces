package trax.aero.start;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import trax.aero.logger.LogManager;
import trax.aero.utils.Run;

import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Startup
@Singleton
public class Start
{

	private ScheduledExecutorService scheduledServ;
	Run timer = null;
	Logger logger = LogManager.getLogger("InstallRemoveSVO_I19");
	
	@PostConstruct
	public void start()
	{
		timer = new Run();
		
		if (scheduledServ == null) {
			int scheduledPoolSize = 1;
			 logger.info("Creating default Scheduled Executor Service [poolSize =" + String.valueOf(scheduledPoolSize) + "]");
			this.scheduledServ = Executors.newScheduledThreadPool(scheduledPoolSize);
		}
		scheduledServ.scheduleWithFixedDelay(timer, 30, Long.parseLong(System.getProperty("InstallRemoveSVO_interval")), TimeUnit.SECONDS);
	
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
