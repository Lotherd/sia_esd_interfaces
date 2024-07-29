package trax.aero.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import trax.aero.data.ModelData;
import trax.aero.logger.LogManager;
import trax.aero.model.WoTaskCard;
import trax.aero.pojo.ADDATTR;
import trax.aero.pojo.OUTPUT;

public class TimerExecutor implements Runnable {
	
	Logger logger = LogManager.getLogger("Techdoc_I20_I26");

	ModelData data = null;
	
	private static FilenameFilter filterTXT = new FilenameFilter() 
	{		 
		public boolean accept(File dir, String name) 
		{
			return (name.toLowerCase().endsWith(".txt"));
		}
	};
	
	private static FilenameFilter filterXML = new FilenameFilter() 
	{		 
		public boolean accept(File dir, String name) 
		{
			return (name.toLowerCase().endsWith(".xml"));
		}
	};
	
	private static FilenameFilter filterPDF = new FilenameFilter() 
	{		 
		public boolean accept(File dir, String name) 
		{
			return (name.toLowerCase().endsWith(".pdf"));
		}
	};
	
	
	public TimerExecutor()
	{
		data = new ModelData();
	}
	
	private void process() {
		try
		{
			JAXBContext jc = JAXBContext.newInstance(OUTPUT.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			File folder = new File(System.getProperty("TECH_fileLoc"));
			File[] files = folder.listFiles();
			
			String errors = "";
			
			
			if(files != null)
			{
				for(File file : files)
				{
					
					if(file.isDirectory()) {
						
						File[] txt = file.listFiles(filterTXT);
						
						if(txt.length > 0){
							
							logger.info("txt found in Directory: " + file.getName());
						
							File[] xmls = file.listFiles(filterXML);
							
							File[] pdfs = file.listFiles(filterPDF);
							
							
							for(File xml : xmls){
								if(xml.exists() && xml.getName().toLowerCase().endsWith(".xml"))
								{
									OUTPUT  out = null;
									try {
										out = (OUTPUT) unmarshaller.unmarshal(xml);
										
										Marshaller marshaller = jc.createMarshaller();
										marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
										StringWriter sw = new StringWriter();
										marshaller.marshal(out, sw);
										
										logger.info("Input: " + sw.toString());
									
									}catch(Exception e) {
										try {
											e.printStackTrace();
											String outcomeFile = insertFile(xml, "FAILURE");
											data.error = data.error.concat(" Error Message: "+e.toString() +
													" , Error File: " +outcomeFile  + System.lineSeparator() +  System.lineSeparator());
											if(data.error.length() > 0)
											{
												errors += data.error;
												data.error = "";
											}
											continue;
										}catch (Exception e1) {
											e1.printStackTrace();
											continue;
										}
									}
									byte[] pdfBytes = null;
									
									for(File pdf :pdfs) {
										if(pdf.exists() && pdf.getName().toLowerCase().endsWith(".pdf") && pdf.getName().equalsIgnoreCase(out.getFILENAME()))
										{
											pdfBytes = Files.readAllBytes(pdf.toPath());
											
											break;
										}
									}
									
									boolean success = data.processData(out.getMODEL(),out.getFILENAME(),pdfBytes);
									
									
									
									if(success)
									{
										List<ADDATTR> attributes = out.getMODEL().getEFFECTIVITY().getJOBCARD().getJOBI().getPLI().getADDATTR();
										String taskNBR = data.filterADDATTR(attributes, "TASK-NBR");
										String taskId = "" ,groupNo = "" ,refer = "";
										
										String wonbr = out.getMODEL().getEFFECTIVITY().getJOBCARD().getWONBR();
										if(wonbr != null){
											if(wonbr.length() > 4){
												refer = (wonbr.substring(wonbr.length()- 4));
											}
										}	
										
										if(out.getMODEL().getEFFECTIVITY().getJOBCARD().getJCNBR() != null && out.getMODEL().getEFFECTIVITY().getJOBCARD().getJCNBR().length() > 8)
										{
											groupNo = out.getMODEL().getEFFECTIVITY().getJOBCARD().getJCNBR().substring(0, 8);
										}
										else if(out.getMODEL().getEFFECTIVITY().getJOBCARD().getJCNBR() != null)
										{
											groupNo = out.getMODEL().getEFFECTIVITY().getJOBCARD().getJCNBR();
										}										
										int length = (taskNBR + "_" + groupNo + "_" + refer).length();
										if(length > 35){
											taskId = taskNBR.substring(0, taskNBR.length() - (length - 35)) +"_" + groupNo + "_" + refer;
										}else{
											taskId = taskNBR + "_" + groupNo + "_" + refer;
										}
										
										String ref = out.getMODEL().getEFFECTIVITY().getJOBCARD().getWONBR();
										if(wonbr != null)
										{
											if(wonbr.length() > 4)
											{
												ref = (wonbr.substring(0, wonbr.length()- 4));
											}
										}
										
										String outcomeFile = insertFile(xml, "SUCCESS");
										data.error = data.error.concat("SUCCESS INSERTED Task Card: " +taskId +" , SVO: "+ref+  " ,SUCCESS File: " +outcomeFile + 
												System.lineSeparator() +  System.lineSeparator() );
									}
									else
									{
										String errorFile = insertFile(xml, "FAILURE");
										data.error = data.error.concat("Error File: " +errorFile );
									}
									if(data.error.length() > 0)
									{
										errors += data.error;
										data.error = "";
									}
									
									
								}								
							}
							
							if(data.cusList != null && !data.cusList .isEmpty()) {
								for(WoTaskCard cus :data.cusList) {
									data.setNumber(cus);
								}
								data.cusList = new ArrayList<WoTaskCard>();
							}
							
							if(errors.length() > 0)
							{
								data.emailer.sendEmail(errors,data.wo, data.ac);
								errors = "";
							}
							try {
								File[] cleanup = file.listFiles();
								for(File clean : cleanup) {
									clean.delete();
								}
								file.delete();
							}catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						
					}else {
						/*
							if(file.exists() && file.getName().toLowerCase().endsWith(".xml"))
							{
								OUTPUT  out = (OUTPUT) unmarshaller.unmarshal(file);
								
								Marshaller marshaller = jc.createMarshaller();
								marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
								StringWriter sw = new StringWriter();
								marshaller.marshal(out, sw);
								
								logger.info("Input: " + sw.toString());
								
								boolean success = data.processData(out.getMODEL());
								
								
								
								if(success)
								{
									insertFile(file, "SUCCESS");
								}
								else
								{
									String errorFile = insertFile(file, "FAILURE");
									data.error = data.error.concat("Error File: " +errorFile );
								}
								
								if(data.error.length() > 0)
								{
									data.emailer.sendEmail(data.error);
									data.error = "";
								}
	
							}
							*/
					}
					
				}
			}
			
			

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		try 
		{
			if(data.lockAvailable("I20_I26"))
			{
				data.lockTable("I20_I26");
				process();
				data.unlockTable("I20_I26");
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	private String insertFile(File file, String outcome)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime currentDateTime = LocalDateTime.now();
		File compFolder = new File(System.getProperty("TECH_compFiles"));
		if (!compFolder.isDirectory())
			logger.info(String.valueOf(compFolder.mkdir()));
		File todayFolder = new File(System.getProperty("TECH_compFiles")+ File.separator + dtf.format(currentDateTime));
		if (!todayFolder.isDirectory())
			logger.info(String.valueOf(todayFolder.mkdir()));
		
		logger.info(todayFolder + File.separator
				+ outcome + "_"+ Calendar.getInstance().getTimeInMillis() + "_" + file.getName());
		boolean moved = file.renameTo(new File(todayFolder + File.separator
				+ outcome + "_"+ Calendar.getInstance().getTimeInMillis() + "_" + file.getName()));
		logger.info(String.valueOf(moved));
		
		logger.info("DONE processing file " + outcome + "_"+ Calendar.getInstance().getTimeInMillis() + "_" + file.getName());
		
		return outcome + "_"+ Calendar.getInstance().getTimeInMillis() + "_" + file.getName();
	}
}
