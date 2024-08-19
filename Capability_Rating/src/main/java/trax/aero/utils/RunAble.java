package trax.aero.utils;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;

import com.fasterxml.jackson.databind.ObjectMapper;

import trax.aero.interfaces.ICapability_Rating_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.DATA;

public class RunAble implements Runnable {
    
    Logger logger = LogManager.getLogger("CapabilityRat");
    
    @EJB ICapability_Rating_Data data;
    
    public RunAble(ICapability_Rating_Data data) {
        this.data = data;
    }
    
    private void process() throws Exception {
        String message = null;
        ObjectMapper objectMapper = new ObjectMapper();
        
        message = MqUtilities.receiveMqText();
        
        if (message != null) {
            logger.info(message);
            try {
                DATA root = objectMapper.readValue(message, DATA.class);
                
                if (root != null) {
                    data.importAuth(root);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void run() 
	{
		try {
			if(data.lockAvailable("I25"))
			{
				data.lockTable("I25");
				process();
				data.unlockTable("I25");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
}
