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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.CSVWriter;

import trax.aero.controller.Authorization_Details_Controller;
import trax.aero.data.Authorization_Controller_Data;
import trax.aero.logger.LogManager;
import trax.aero.pojo.EmployeeLicense;

public class RunAble implements Runnable {

    Logger logger = LogManager.getLogger("AuthDetails");
    Authorization_Controller_Data data = null;
    private static File inputFiles[], inputFolder;
    private static FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return (name.toLowerCase().endsWith(".csv"));
        }
    };

    public RunAble() {
        data = new Authorization_Controller_Data();
    }

    private String insertFile(File file, String outcome) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime currentDateTime = LocalDateTime.now();

        File todayFolder = new File(System.getProperty("AuthorizationD_compFiles") + File.separator + dtf.format(currentDateTime));
        if (!todayFolder.isDirectory())
            todayFolder.mkdir();

        File output = new File(todayFolder + File.separator + outcome + Calendar.getInstance().getTimeInMillis() + "_" + file.getName());

        try {
            FileUtils.copyFile(file, output);
        } catch (IOException e) {
            logger.severe("Failed to copy file: " + e.getMessage());
        }
        file.delete();

        logger.info("DONE processing file " + file.getName());

        return output.getName();
    }

    private String insertFileFailed(List<EmployeeLicense> employeeFailure, String outcome, String fileName) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<String[]> data = new ArrayList<>();

        String[] header = {"Resource First Name", "Resource Id", "Resource Authorisation No.", "Organization / Customer Text", "Record Item Name",
                "Record Item parent", "Record Item Authority", "Auth Expiry"};
        data.add(header);

        for (EmployeeLicense e : employeeFailure) {
            String[] arr = new String[8];
            arr[0] = e.getStaffNumber();
            arr[1] = e.getTrade();
            arr[2] = e.getAuthorizationNumber();
            arr[3] = e.getOrganizationCustomerText();
            arr[4] = e.getRecordItemName();
            arr[5] = e.getRecordItemParent();
            arr[6] = e.getRecordItemAuthority();
            arr[7] = e.getAuthorizationExpiryDate();
            data.add(arr);
        }

        File compFolder = new File(System.getProperty("AuthorizationD_compFiles"));
        if (!compFolder.isDirectory())
            compFolder.mkdir();
        File todayFolder = new File(System.getProperty("AuthorizationD_compFiles") + File.separator + dtf.format(currentDateTime));
        if (!todayFolder.isDirectory())
            todayFolder.mkdir();

        File output = new File(todayFolder + File.separator + outcome + "_" + Calendar.getInstance().getTimeInMillis() + "_" + fileName);

        try (FileWriter outputfile = new FileWriter(output);
             CSVWriter writer = new CSVWriter(outputfile, ';', CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            writer.writeAll(data);
           writer.close();
        }

        logger.info("DONE processing file " + output.getName());
        return output.getName();
    }

    private void process() {
        try {
        	
        	
            Set<String> processedEmployees = new HashSet<>();
            // Setting up variables
            final String process = System.getProperty("AuthorizationD_locFiles");
            inputFolder = new File(process);
            String outcome = "PROCESSED_";
            List<EmployeeLicense> employees = new ArrayList<>();
            List<EmployeeLicense> employeesFailure = new ArrayList<>();
            EmployeeLicense employee;

            // Logic to check input directory and process files
            if (inputFolder.isDirectory()) {
                inputFiles = inputFolder.listFiles(filter);
            } else {
                logger.severe("Path: " + inputFolder.toString() + " is not a directory or does not exist");
                throw new Exception("Path: " + inputFolder.toString() + " is not a directory or does not exist");
            }

            for (File file : inputFiles) {
                outcome = "PROCESSED_";
                logger.info("Checking file " + file.toString());
                try (FileReader filereader = new FileReader(file);
                     CSVReader csvReader = new CSVReaderBuilder(filereader)
                             .withCSVParser(new RFC4180ParserBuilder().withSeparator(';').build())
                             .withSkipLines(1)
                             .build()) {

                    List<String[]> allData = csvReader.readAll();
                    csvReader.close();
                    filereader.close();
                    
                    for (String[] row : allData) {
                        employee = new EmployeeLicense();
                        employee.setStaffNumber(row[1]);
                        employee.setAuthorizationNumber(row[2]);
                        employee.setOrganizationCustomerText(row[3]);
                        employee.setRecordItemName(row[4]);
                        employee.setRecordItemParent(row[5]);
                        employee.setRecordItemAuthority(row[6]);
                        employee.setAuthorizationExpiryDate(row[7]);

                        String employeeId = employee.getStaffNumber();

                        
                        if (!processedEmployees.contains(employeeId)) {
                            
                            data.deleteEmployeeControlRecords(employeeId);
                            processedEmployees.add(employeeId);  
                        }
                        
                       
                    }
                    data.insertSkillsToSkillMaster();

                    for (String[] row : allData) {
                        employee = new EmployeeLicense();
                        employee.setStaffNumber(row[1]);
                        employee.setAuthorizationNumber(row[2]);
                        employee.setOrganizationCustomerText(row[3]);
                        employee.setRecordItemName(row[4]);
                        employee.setRecordItemParent(row[5]);
                        employee.setRecordItemAuthority(row[6]);
                        employee.setAuthorizationExpiryDate(row[7]);

                        String exectued = data.insertEmployeeLicense(employee);

                        if (!"OK".equalsIgnoreCase(exectued)) {
                            employeesFailure.add(employee);
                        }
                    }

                    if (!employeesFailure.isEmpty()) {
                        String fileName = file.getName();
                        outcome = "FAILURE_";
                        insertFileFailed(employeesFailure, outcome, fileName);
                        employeesFailure.clear();
                        throw new Exception("Failed Employees are in File " + outcome);
                    }
                } catch (Exception e) {
                    Authorization_Details_Controller.addError(e.toString());
                    Authorization_Details_Controller.sendEmailFile(file);
                    logger.severe(e.toString());
                } finally {
                    insertFile(file, outcome);
                }
            }
        } catch (Throwable e) {
        	e.printStackTrace();
            logger.severe(e.toString());
        }
    }

    public void run() {
        try {
            if (data.lockAvailable("I24")) {
                data.lockTable("I24");
                process();
                data.unlockTable("I24");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

