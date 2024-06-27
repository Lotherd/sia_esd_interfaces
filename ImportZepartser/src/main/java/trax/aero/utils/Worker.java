package trax.aero.utils;

import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;

import trax.aero.data.ImportZepartserData;
import trax.aero.logger.LogManager;

import trax.aero.pojo.ZEPARTSER;

public class Worker implements Runnable {

	ImportZepartserData data = null;
	Logger logger = LogManager.getLogger("ImportZepartser_I23");
	public Worker(EntityManagerFactory factory) {
		data = new ImportZepartserData(factory);
	}
	
	private ZEPARTSER input =null;
		
	public void run() 
	{
		
			try {
				boolean ouput = data.insertZepartser(input);
	    	if(!ouput) {
	    		RunAble.zepArrayFailure.add(input); 
	    	}
		}
		catch(Exception e)
		{	
			e.printStackTrace();
			logger.severe(e.toString());
		}finally {
			if(data.em != null && data.em .isOpen())
				data.em .close();
		}
	}

	public ZEPARTSER getInput() {
		return input;
	}

	public void setInput(ZEPARTSER inputs) {
		this.input = inputs;
	}
	
	

}
