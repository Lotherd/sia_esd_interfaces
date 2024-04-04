package trax.aero.start;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import trax.aero.interfaces.IMaterialData;
import trax.aero.util.TimerExecutor;

@Startup
@Singleton(name = "StartProcess", mappedName = "StartProcess")
public class StartProcess {
	
	private ScheduledExecutorService scheduledServ;
	TimerExecutor timer = null;
	
	@EJB IMaterialData data;
	
	
	@PostConstruct
	public void startTimer()
	{
		timer = new TimerExecutor(data);
		timer.setPostOn(true);
		scheduledServ = Executors.newScheduledThreadPool(2);
		
		scheduledServ.scheduleAtFixedRate(timer, 30, Integer.valueOf(System.getProperty("MD_interval")), TimeUnit.SECONDS);
		
		
		
	}
	
	
	@PreDestroy
	public void stopTimer()
	{
		if(scheduledServ != null)
			scheduledServ.shutdown();
	}

}
