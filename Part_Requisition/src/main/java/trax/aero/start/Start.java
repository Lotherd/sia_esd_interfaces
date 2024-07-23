package trax.aero.start;

import java.util.logging.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import trax.aero.logger.LogManager;
import trax.aero.utils.Run;

@Startup
@Singleton
public class Start {
	
	private ScheduledExecutorService scheduledServ;
		Run timer = null;
		Logger logger = LogManager.getLogger("Part_REQ");
		
		@PostConstruct
		public void start() {
			timer = new Run();
			
			if(scheduledServ == null) {
				int scheduledPoolSize = 1;
				scheduledServ = Executors.newScheduledThreadPool(scheduledPoolSize);
				logger.info("Creating default Scheduled Executor Service [poolSize=" + scheduledPoolSize + "]");
			}
			scheduledServ.scheduleAtFixedRate(timer, 30, Long.parseLong(System.getProperty("REQ_Interval", "60")), TimeUnit.SECONDS);
		}
		
		@PreDestroy
		public void stop() {
			if(!scheduledServ.isShutdown()) {
				scheduledServ.shutdown();
			}
		}
}
