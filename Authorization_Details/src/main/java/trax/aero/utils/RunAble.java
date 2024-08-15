package trax.aero.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.FileUtils;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;

import trax.aero.controller.Authorization_Details_Controller;
import trax.aero.data.Authorization_Controller_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.EmployeeLicense;
import trax.aero.pojo.ThirdParty;

public class RunAble implements Runnable {
	
	Logger logger = LogManager.getLogger("AuthDetails");
	Authorization_Controller_Data data = null;
	private static File inputFiles[],inputFolder;
	private static FilenameFilter filter = new FilenameFilter() 
	{		 
		public boolean accept(File dir, String name) 
		{
			return (name.toLowerCase().endsWith(".csv"));
		}
	};
	
	public RunAble() {
		data = new Authorization_Controller_Data();
	}
	
	private String insertFile(File file, String outcome) 
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime  currentDateTime = LocalDateTime.now();
		
		File todayFolder = new File(System.getProperty("AuthorizationD_compFiles")+ File.separator + dtf.format(currentDateTime));
		if (!todayFolder.isDirectory())			
			todayFolder.mkdir();
		
		
		
		File output =  new File(todayFolder + File.separator
			+ outcome + Calendar.getInstance().getTimeInMillis() + "_" + file.getName());
		
		try {
			FileUtils.copyFile(file, output);
		} catch (IOException e) {
			
		}
		file.delete();
		
		
		logger.info("DONE processing file " + file.getName() );
		
		return output.getName();
	}
	
	private String insertFileFailed(ArrayList<EmployeeLicense> employeeFailure, String outcome, String fileName) throws JAXBException, IOException
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime currentDateTime = LocalDateTime.now();
        List<String[]> data = new ArrayList<String[]>();
        
        List<String> list = new ArrayList<String>();
        list.add("android");
        list.add("apple");
        String[] stringArray = list.toArray(new String[0]);
        
        String[] header = {"Resource First Name","Resource ID","Resource Authorisation No.","Organization / Customer Text","Record Item Name",
        		"Record Item Parent", "Record Item Authority", "Auth Expiry"};
        String[] arr = {"","","","","","","",""};
        data.add(header);
        
        for(EmployeeLicense e : employeeFailure) {
        	if(e.getStaffNumber() != null && !e.getStaffNumber().isEmpty()) {
        		arr[0] = e.getStaffNumber();
        	}
        	if(e.getTrade() != null && !e.getTrade().isEmpty()) {
        		arr[1] = e.getTrade();
        	}
        	if(e.getAuthorizationNumber() != null && !e.getAuthorizationNumber().isEmpty()) {
        		arr[2] = e.getAuthorizationNumber();
        	}
        	if(e.getAuthorizationExpiryDate() != null && !e.getAuthorizationExpiryDate().isEmpty()) {
        		arr[3] = e.getAuthorizationExpiryDate();
        	}
        	if(e.getAuthorizationStatus() != null && !e.getAuthorizationStatus().isEmpty()) {
        		arr[4] = e.getAuthorizationStatus();
        	}

        	       	   	
        	data.add(arr);
        }
        File compFolder = new File(System.getProperty("AuthorizationD_compFiles"));
		if (!compFolder.isDirectory())
		compFolder.mkdir();
		File todayFolder = new File(System.getProperty("AuthorizationD_compFiles")+ File.separator + dtf.format(currentDateTime));
		if (!todayFolder.isDirectory())
		todayFolder.mkdir();
	
		File output = new File(todayFolder + File.separator
		+ outcome + "_"+ Calendar.getInstance().getTimeInMillis() + "_" + fileName);
		
        FileWriter outputfile = new FileWriter(output);
  

        CSVWriter writer = new CSVWriter(outputfile, ';',
                                         CSVWriter.NO_QUOTE_CHARACTER,
                                         CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                         CSVWriter.DEFAULT_LINE_END);
		
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
			final String process = System.getProperty("AuthorizationD_locFiles");
			inputFolder = new File(process);
			String exectued = "OK",outcome = "PROCESSED_";
			ArrayList<EmployeeLicense> employees = new ArrayList<EmployeeLicense>();
			ArrayList<EmployeeLicense> employeesFailure = new ArrayList<EmployeeLicense>(); 
			int itr;
			EmployeeLicense employee = null;
			
			
			
				
				
			
			
			
			
			
			
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
				outcome = "PROCESSED_";
				logger.info("Checking file " + inputFiles[i].toString());
				File file = new File(inputFiles[i].toString());
				try
				{	
					
					
					
					//logger.info("Input: "+ FileUtils.readFileToString(file, StandardCharsets.UTF_8));
					
					FileReader filereader = new FileReader(file);
					
					RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().withSeparator(';').build();
					
					 CSVReader csvReader = new CSVReaderBuilder(filereader)
                             .withCSVParser(rfc4180Parser)
                             .withSkipLines(1)
                             .build();
					 
					 
					 try {
						 List<String[]> allData = csvReader.readAll();
						 
						 for (String[] row : allData) {
							 itr = 0;
							 
							 employee = new EmployeeLicense();
							 ArrayList<String> Strings = new ArrayList( Arrays.asList( row ) );
							 //logger.info(" SIZE " + Strings.size());
							 Strings.remove(Strings.size() - 1);
							 //logger.info(" SIZE AFTER REMOVE " + Strings.size());
							 employee.setThirdParty(new ArrayList<ThirdParty>());
					            for (String cell : Strings) {	            	
					            	 
					                if(itr == 0) {
					                	employee.setStaffNumber(cell);
					            	}
					            	if(itr == 1) {
					            		employee.setTrade(cell);
					            	}
					            	if(itr == 2) {
					            		employee.setAuthorizationNumber(cell);
					            	}
					            	if(itr == 3) {
					            		employee.setAuthorizationExpiryDate(cell);
					            	}
					            	if(itr == 4) {
					            		employee.setAuthorizationStatus(cell);
					            		
					            		int total = Strings.size();
					            		//logger.info("total" + total);
					            		if((total - 5)% 3 == 0) {
						            		int size = (total - 5)/3;
						            		int Jump = 0;
						            		//logger.info("size" + size);
						            		
						            		for(int P3 = 0; P3 < size; P3++) {
						            			ThirdParty thirdParty = new ThirdParty();
						            			//logger.info("j" + Jump);
						            			if(Strings.get(4 + 1 + Jump) != null && !Strings.get(4 + 1 + Jump).isEmpty()) {
						            				thirdParty.setCustomerIdentifier(Strings.get(4 + 1 + Jump));
						            				//logger.info("CustomerIdentifier "+Strings.get(4 + 1 + Jump) + " E " + employee.getStaffNumber() );
						            				
						            			}
						            			if(Strings.get(4 + 2 + Jump) != null && !Strings.get(4 + 2 + Jump).isEmpty()) {
						            				thirdParty.setCustomerAssignedNumber(Strings.get(4 + 2 + Jump));
						            			}
						            			
						            			
						            			thirdParty.setActiveCustIDIndcator(Strings.get(4 + 3 + Jump));
						            			//
						            			Jump += 3;
						            			if(thirdParty.getCustomerAssignedNumber() == null && thirdParty.getCustomerIdentifier() == null){
						            				continue;
						            			}else {
						            				employee.getThirdParty().add(thirdParty);
						            			}
						            		}
						            		break;
						            	}
					            		
					            	}
					            	
					                itr++;				                
					            }
					            
					            employees.add(employee);
					           
					        }   
						 
						 	 
					 }catch(Exception e){
						 Authorization_Details_Controller.addError(e.toString());
						 e.printStackTrace();
						 outcome = "FAILURE_";
						 throw new Exception("Failed to read file");
					 }finally {
						 csvReader.close();
						 filereader.close();
					 }
					
					 
				 
					 
			       for(EmployeeLicense e: employees) {
			    	   
			    	   exectued = "OK";
			    	   
			    	  
			    	   exectued = data.insertEmployeeLicense(e);
			    	   
			    	   if(!exectued.equalsIgnoreCase("OK")) {
			    		   employeesFailure.add(e);
			    	   }
			       }
			        			        			        
				   String fileName = file.getName(); 
				   
		   
				   if(!employeesFailure.isEmpty()){
				    	exectued = insertFileFailed(employeesFailure,"FAILURE_",fileName);
				    	employeesFailure = null;
				    	throw new Exception("Failed Employees are in File " + exectued);
				   }
				   
				}
				catch(Exception e)
				{
					Authorization_Details_Controller.addError(e.toString());
					Authorization_Details_Controller.sendEmailFile(file);
					//insertFile(file,"FAILURE_");
					
					logger.severe(e.toString());
				}finally {
					 
					insertFile(file,outcome);
					
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
			if(data.lockAvailable("I24"))
			{
				data.lockTable("I24");
				process();
				data.unlockTable("I24");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}

}
