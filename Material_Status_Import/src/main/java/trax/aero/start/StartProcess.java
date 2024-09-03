package trax.aero.start;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import trax.aero.interfaces.IMaterialStatusImportData;
import trax.aero.utils.TimerExecutor;

@Startup
@Singleton(name = "StartProcess", mappedName = "StartProcess")
public class StartProcess {
	
	private ScheduledExecutorService scheduledServ;
	TimerExecutor timer = null;
	
	@EJB IMaterialStatusImportData data;
	
	
	@PostConstruct
	public void startTimer()
	{
		timer = new TimerExecutor(data);
		scheduledServ = Executors.newScheduledThreadPool(1);
		
		scheduledServ.scheduleAtFixedRate(timer, 30, Integer.valueOf(System.getProperty("MaterialStatusImport_interval")), TimeUnit.SECONDS);
		
		
		
	}
	
	
	@PreDestroy
	public void stopTimer()
	{
		if(scheduledServ != null)
			scheduledServ.shutdown();
	}

}
