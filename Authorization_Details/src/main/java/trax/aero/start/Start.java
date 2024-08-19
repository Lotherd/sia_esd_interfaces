package trax.aero.start;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import trax.aero.logger.LogManager;
import trax.aero.utils.RunAble;

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
	RunAble timer = null;
	Logger logger = LogManager.getLogger("AuthDetails");
	
	@PostConstruct
	public void start() {
	    timer = new RunAble();

	    if (scheduledServ == null) {
	        int scheduledPoolSize = 1;
	        logger.info("Creating default Scheduled Executor Service [poolSize =" + scheduledPoolSize + "]");
	        this.scheduledServ = Executors.newScheduledThreadPool(scheduledPoolSize);
	    }

	    String intervalProperty = System.getProperty("AuthorizationD_interval");
	    long interval;

	    try {
	        if (intervalProperty != null && !intervalProperty.isEmpty()) {
	            interval = Long.parseLong(intervalProperty);
	        } else {
	            throw new NumberFormatException("AuthorizationD_interval property is not set or is empty");
	        }
	    } catch (NumberFormatException e) {
	        logger.severe("Failed to parse AuthorizationD_interval property: " + e.getMessage());
	        interval = 60L; // Default interval in seconds
	        logger.info("Using default interval of " + interval + " seconds.");
	    }

	    scheduledServ.scheduleAtFixedRate(timer, 30, interval, TimeUnit.SECONDS);
	}

	@PreDestroy
	public void stop() {
	    if (scheduledServ != null && !scheduledServ.isShutdown()) {
	        scheduledServ.shutdown();
	    }
	}
	
}

