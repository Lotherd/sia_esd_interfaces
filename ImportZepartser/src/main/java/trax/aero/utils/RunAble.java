package trax.aero.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import trax.aero.controller.ImportZepartserController;
import trax.aero.data.ImportZepartserData;
import trax.aero.logger.LogManager;
import trax.aero.pojo.ZepartserMaster;
import trax.aero.pojo.ZEPARTSER;

public class RunAble implements Runnable {
	
	//Variables
	ImportZepartserData data = null;
	
	EntityManagerFactory factory;
	static Logger logger = LogManager.getLogger("ImportZepartser_I23");
	private static File inputFiles[],inputFolder;
	
	public static List<ZEPARTSER> zepArrayFailure  = null;
	
	private static FilenameFilter filter = new FilenameFilter() 
	{		 
		public boolean accept(File dir, String name) 
		{
			return (name.toLowerCase().endsWith(".csv"));
		}
	};
	
	public RunAble() {
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		data = new ImportZepartserData();
	}
	
	private void insertFile(File file, String outcome) 
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime  currentDateTime = LocalDateTime.now();
		
		File todayFolder = new File(System.getProperty("Zepartser_compFiles")+ File.separator + dtf.format(currentDateTime));
		if (!todayFolder.isDirectory())			
			todayFolder.mkdir();
		
		 boolean result = file.renameTo(new File(todayFolder + File.separator
			+ outcome + Calendar.getInstance().getTimeInMillis() + "_" + file.getName()));
		
		logger.info("DONE processing file " + file.getName() + " " + result);
	}
	
	private void insertFileFailed(File file, String outcome) 
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime  currentDateTime = LocalDateTime.now();
		
		File todayFolder = new File(System.getProperty("Zepartser_failedLoc")+ File.separator + dtf.format(currentDateTime));
		if (!todayFolder.isDirectory())			
			todayFolder.mkdir();
		
		 boolean result = file.renameTo(new File(todayFolder + File.separator
			+ outcome + Calendar.getInstance().getTimeInMillis() + "_" + file.getName()));
		
		logger.info("DONE processing file " + file.getName() + " " + result);
	}
	
	private String insertFileFailed(ZepartserMaster z, String outcome, String fileName) throws IOException 
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		
		List<String[]> data = new ArrayList<String[]>();
	    String[] header = {"Customer Number","Material Number"};
	    data.add(header);
	    for(ZEPARTSER e : z.getZepartser()) {
	    	String[] arr = {e.getCustomer(),e.getMaterialNumber()};
	    	data.add(arr);
	    }
		
		
		
		File compFolder = new File(System.getProperty("Zepartser_failedLoc"));
		if (!compFolder.isDirectory())
		compFolder.mkdir();
		File todayFolder = new File(System.getProperty("Zepartser_failedLoc")+ File.separator + dtf.format(currentDateTime));
		if (!todayFolder.isDirectory())
		todayFolder.mkdir();
	
		File output = new File(todayFolder + File.separator
		+ outcome + "_"+ Calendar.getInstance().getTimeInMillis() + "_" + fileName);
		
		FileWriter outputfile = new FileWriter(output);
		CSVWriter writer = new CSVWriter(outputfile, '|', 
				CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
			
	    writer.writeAll(data);
	    writer.close();
	    outputfile.close();
	        
		
		
		logger.info("DONE processing file " + output.getName());
		return output.getName();
	}
	
	
	private void process() {
		try 
		{
			//setting up variables
			final String process = System.getProperty("Zepartser_fileLoc");
			inputFolder = new File(process);
			String exectued = "";
			ZepartserMaster zepartserMaster = null;
			
			
			//logic taken from AIMS_Flight_Interface
			if (inputFolder.isDirectory())
			{
				inputFiles = inputFolder.listFiles(filter);
			}
			else
			{
				logger.severe("Path: " + inputFolder.toString() + " is not a directory or does not exist");
				throw new Exception("Path: " + inputFolder.toString() + " is not a directory or does not exist");
			}
					
			for (int i = 0; i < inputFiles.length; i++)
			{
				logger.info("Checking file " + inputFiles[i].toString());
				File file = new File(inputFiles[i].toString());
				zepartserMaster = new ZepartserMaster();
				zepartserMaster.setZepartser(new ArrayList<ZEPARTSER>());
				FileReader filereader = null;
				 CSVReader csvReader = null;
				try
				{	
					 filereader = new FileReader(file);
					
					 CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
					
					 csvReader = new CSVReaderBuilder(filereader)
							.withCSVParser(parser).withSkipLines(1).build();
					 
					List<String[]> allData = csvReader.readAll();
					filereader.close();
					csvReader.close();						 
					for (String[] row : allData) {
						ZEPARTSER z = new ZEPARTSER();
				        z.setCustomer(row[0]);
				        z.setMaterialNumber(row[1]);
				        zepartserMaster.getZepartser().add(z);					
					}		    
				    
					
					
					
				    ZepartserMaster zepartserMasterFailure = new ZepartserMaster();
				    zepArrayFailure = Collections.synchronizedList(new ArrayList<ZEPARTSER>());
									    
				    int scheduledPoolSize = 4;
					if(System.getProperty("Thread_Count") != null && !System.getProperty("Thread_Count").isEmpty()) {
						scheduledPoolSize = Integer.parseInt(System.getProperty("Thread_Count"));
					}
					logger.info("Creating default Scheduled Executor Service [poolSize =" + String.valueOf(scheduledPoolSize) + "]");
					ScheduledExecutorService scheduledServ = Executors.newScheduledThreadPool(scheduledPoolSize);
					
				    logger.info("SIZE " + zepartserMaster.getZepartser().size());
				    data.clearZepartserTable();
				    
				    for(ZEPARTSER z : zepartserMaster.getZepartser()) {
				    	   exectued = "OK";
				    	   Worker worker = new Worker(factory);
				    	   worker.setInput(z);
				    	   scheduledServ.execute(worker);
				    }
					
				   scheduledServ.shutdown();
				   scheduledServ.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);	  
				    
				   String fileName = file.getName();
				   
				   insertFile(file,"PROCESSED_");
				   
				   zepartserMasterFailure.setZepartser(new ArrayList<ZEPARTSER>(zepArrayFailure));
				   
				   if(!zepartserMasterFailure.getZepartser().isEmpty()){
				    	exectued = insertFileFailed(zepartserMasterFailure,"FAILURE_",fileName);
				    	zepartserMasterFailure = null;
				    	throw new Exception("Failed Zepartsers are in File " + exectued);
				   }
				}
				catch(Exception e)
				{
					e.printStackTrace();
					ImportZepartserController.addError(e.toString());
					ImportZepartserController.sendEmail(file);
					if(!e.getMessage().contains("Failed Zepartsers are in File")) {
						if(csvReader!=null) {
							csvReader.close();
						}
						if(filereader != null) {
							filereader.close();
						}
						insertFileFailed(file,"FAILURE_");
					}					
					logger.info(e.getMessage());
				}
			}
		}
		catch(Throwable e)
		{
			logger.severe(e.toString());
		}
	}
	
	
	public void run() 
	{
		try {
			if(data.lockAvailable("I23"))
			{
				data.lockTable("I23");
				process();
				data.unlockTable("I23");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}