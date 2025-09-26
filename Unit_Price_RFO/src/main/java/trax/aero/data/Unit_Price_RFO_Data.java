package trax.aero.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBException;

import trax.aero.controller.Unit_Price_RFO_Controller;
import trax.aero.exception.CustomizeHandledException;
import trax.aero.logger.LogManager;
import trax.aero.model.InterfaceLockMaster;
import trax.aero.pojo.INT27_SND;
import trax.aero.pojo.INT27_TRAX;
import trax.aero.pojo.Operation_SND;
import trax.aero.pojo.Operation_TRAX;
import trax.aero.pojo.OpsLineEmail;
import trax.aero.utils.DataSourceClient;
import trax.aero.utils.ErrorType;

public class Unit_Price_RFO_Data {
	
	EntityManagerFactory factory;
	EntityManager em;
	String executed;
	private Connection con;
	
	final String MaxRecord = System.getProperty("UnitPrice_MaxRecords");
	Logger logger = LogManager.getLogger("UnitPrice");
	
	public Unit_Price_RFO_Data(String mark) {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " +String.valueOf(!this.con.isClosed()));
		}
	}catch(SQLException e) {
		logger.severe("An error ocurred getting the status of the connection");
		Unit_Price_RFO_Controller.addError(e.toString());
	} catch (CustomizeHandledException e1) {
		Unit_Price_RFO_Controller.addError(e1.toString());
	} catch (Exception e) {
		Unit_Price_RFO_Controller.addError(e.toString());
	}
}
	
	public Unit_Price_RFO_Data() {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!this.con.isClosed()));
			}
		}catch (SQLException e) {
		      logger.severe("An error occured getting the status of the connection");
		      Unit_Price_RFO_Controller.addError(e.toString());
		    } catch (CustomizeHandledException e1) {
		    	Unit_Price_RFO_Controller.addError(e1.toString());
		    } catch (Exception e) {
		    	Unit_Price_RFO_Controller.addError(e.toString());
		    }
		factory = Persistence.createEntityManagerFactory("TraxStandaloneDS");
		em = factory.createEntityManager();
	}
	
	public Connection getCon() {
		return con;
	}
	
	/*public String markSendData() throws JAXBException
	{
	  INT27_TRAX request = new INT27_TRAX();
	  try {
		  if (request.getError_code() != null && !request.getError_code().isEmpty() && request.getError_code().equalsIgnoreCase("53")) {
	        markTransaction(request);
	        logger.info("markTransaction completed successfully.");
		  }else {
			  markTransactionForError(request);
			  logger.info("markTransaction completed successfully.");
		  }
	        return "OK";
	    } catch (Exception e) {
	    	logger.log(Level.SEVERE, "Error executing markTransaction", e);
	    	e.printStackTrace();
	        return null; 
	    }
	}*/
	private static Map<String, Integer> attemptCounts = new HashMap<>();
	
	public String markTransactionForError(INT27_TRAX request) {
	    executed = "OK";
	    
	    String resetFlag = "UPDATE WO_ACTUALS SET INTERFACE_ESD_UP_TRANSFERRED_FLAG = NULL, GET_PRICE = 'N' WHERE WO = ? AND PN = ?";
	    String resetTempFlag = "UPDATE wo_actuals_material_temp SET GET_PRICE = 'N' WHERE WO = ? AND wo_actual_transaction = ? AND trasaction_category = 'MATERIAL'";
	    String selectActuals = "SELECT wo_actual_transaction FROM wo_actuals WHERE wo = ? AND PN = ? AND trasaction_category = 'MATERIAL'";
	    String insertTempActuals = "INSERT INTO wo_actuals_material_temp (wo_actual_transaction, trasaction_category, wo, get_price, qty, unit_cost, total_cost, add_bill_currency, add_bill_curr_amount, unit_sell_b, variance_price) " +
	                              "VALUES (?, 'MATERIAL', ?, 'N', ?, ?, ?, ?, ?, ?, ?)";
	    String checkTempExists = "SELECT COUNT(*) FROM wo_actuals_material_temp WHERE wo = ? AND wo_actual_transaction = ? AND trasaction_category = 'MATERIAL'";
	    
	    try (PreparedStatement resetStmt = con.prepareStatement(resetFlag);
	         PreparedStatement resetTempStmt = con.prepareStatement(resetTempFlag);
	         PreparedStatement selectStmt = con.prepareStatement(selectActuals);
	         PreparedStatement insertTempStmt = con.prepareStatement(insertTempActuals);
	         PreparedStatement checkTempStmt = con.prepareStatement(checkTempExists)) {
	        
	        for (Operation_TRAX o : request.getOperation()) {
	            if (request.getWO() != null && !request.getWO().isEmpty()) {
	                
	                // Get transaction number for temp table operations
	                selectStmt.setString(1, request.getWO());
	                selectStmt.setString(2, o.getMaterial());
	                ResultSet rs = selectStmt.executeQuery();
	                
	                String transaction = null;
	                if (rs.next()) {
	                    transaction = rs.getString(1);
	                }
	                
	                // Reset the main WO_ACTUALS table flags
	                resetStmt.setString(1, request.getWO());
	                resetStmt.setString(2, o.getMaterial());
	                resetStmt.executeUpdate();
	                
	                // Handle temp table operations if transaction exists
	                if (transaction != null) {
	                    // Check if record exists in temp table
	                    checkTempStmt.setString(1, request.getWO());
	                    checkTempStmt.setString(2, transaction);
	                    ResultSet checkRs = checkTempStmt.executeQuery();
	                    
	                    boolean tempRecordExists = false;
	                    if (checkRs.next()) {
	                        tempRecordExists = checkRs.getInt(1) > 0;
	                    }
	                    
	                    if (tempRecordExists) {
	                        // Update existing temp record
	                        resetTempStmt.setString(1, request.getWO());
	                        resetTempStmt.setString(2, transaction);
	                        resetTempStmt.executeUpdate();
	                        logger.info("Updated existing temp record for WO: " + request.getWO() + ", Transaction: " + transaction);
	                    } else {
	                        // Insert new temp record with default/error values
	                        insertTempStmt.setString(1, transaction);
	                        insertTempStmt.setString(2, request.getWO());
	                        
	                        // Use available data from the response or set defaults for error cases
	                        String qty = (o.getQty() != null && !o.getQty().isEmpty()) ? o.getQty() : "0";
	                        String unitCost = "0"; // Default for error cases
	                        String totalCost = "0"; // Default for error cases
	                        String currency = (o.getCurrency() != null && !o.getCurrency().isEmpty()) ? o.getCurrency() : "";
	                        String sellTotalPrice = (o.getSell_Total_Price() != null && !o.getSell_Total_Price().isEmpty()) ? o.getSell_Total_Price() : "0";
	                        String unitSellB = "0"; // Default for error cases
	                        String variancePrice = "0"; // Default for error cases
	                        
	                        insertTempStmt.setString(3, qty);
	                        insertTempStmt.setString(4, unitCost);
	                        insertTempStmt.setString(5, totalCost);
	                        insertTempStmt.setString(6, currency);
	                        insertTempStmt.setString(7, sellTotalPrice);
	                        insertTempStmt.setString(8, unitSellB);
	                        insertTempStmt.setString(9, variancePrice);
	                        
	                        insertTempStmt.executeUpdate();
	                        logger.info("Inserted new temp record for WO: " + request.getWO() + ", Transaction: " + transaction + " with error defaults");
	                    }
	                }
	                
	                logger.info("Processed error response for WO: " + request.getWO() + ", Material: " + o.getMaterial() + " due to error code: " + request.getError_code());
	            }
	        }
	        
	    } catch (SQLException e) {
	        executed = e.toString();
	        Unit_Price_RFO_Controller.addError(executed);
	        logger.severe("Error processing error response: " + executed);
	    }
	    
	    return executed;
	}
	
	public String markTransaction(INT27_TRAX request) {
	    executed = "OK";

	    String pridedone = "UPDATE WO_ACTUALS SET INTERFACE_ESD_UP_TRANSFERRED_FLAG = NULL, GET_PRICE = 'N' WHERE WO = ? and pn = ? ";

		String pridedone2 = "UPDATE wo_actuals_material_temp SET GET_PRICE = 'N' WHERE WO = ? and wo_actual_transaction = ? and trasaction_category = 'MATERIAL' ";
	    
	    String getcurrency = "SELECT DISTINCT CCH.CURRENCY_MATERIAL FROM CUSTOMER_CONTRACT_HEADER CCH, CUSTOMER_ORDER_HEADER COH WHERE COH.ORDER_NUMBER = ? AND COH.CONTRACT_NUMBER = CCH.CONTRACT_NUMBER ";
	    
	    String setpriceUSD = "update wo_actuals set unit_cost = ?, qty = ?, total_cost = ?, unit_sell = ?, total_sell = ?  where wo = ? and pn = ? ";
	    
	    String setpriceSGD = "update wo_actuals set add_bill_currency = ?, add_bill_curr_amount = ? where wo = ? and pn = ? ";
	    
	    String exchamgerate = " select distinct exchange_rate from currency_exchange_history where currency = ? order by currency_date desc fetch first 1 row only ";
	    
	    String checkoldprice = "select distinct unit_sell_b from wo_actuals where wo = ? and pn = ? ";
	    
	    String oldprice = "update wo_actuals set unit_sell_b = ? where wo = ? and pn = ? ";
	    
	    String variance = "update wo_actuals set variance_price = ? where wo = ? and pn = ? ";
	    
	    String tempActuals = "insert into wo_actuals_material_temp  (wo_actual_transaction, trasaction_category,  wo, get_price, qty, unit_cost, total_cost, add_bill_currency, add_bill_curr_amount, unit_sell_b, variance_price) " +
	    					 "values ( ?, 'MATERIAL', ?, null , ?, ?, ?, ?, ?, ?, ? )";
	    
	    String updatetempActuals = "update wo_actuals_material_temp set get_price = null, qty = ?, unit_cost = ?, total_cost = ?, add_bill_currency = ?, add_bill_curr_amount = ?, unit_sell_b = ?, variance_price = ? " + 
	    							"where wo = ? and wo_actual_transaction = ? and trasaction_category = 'MATERIAL' ";
	    
	    String selectActuals = "select wo_actual_transaction from wo_actuals where wo = ? and pn = ? and trasaction_category = 'MATERIAL' ";
	    
	    try (PreparedStatement pstmt1 = con.prepareStatement(pridedone);
	             PreparedStatement ps1 = con.prepareStatement(getcurrency);
	             PreparedStatement usd = con.prepareStatement(setpriceUSD);
	             PreparedStatement sgd = con.prepareStatement(setpriceSGD);
	             PreparedStatement ex = con.prepareStatement(exchamgerate);
	             PreparedStatement chold = con.prepareStatement(checkoldprice);
	             PreparedStatement old = con.prepareStatement(oldprice);
	             PreparedStatement var = con.prepareStatement(variance);
	    		 PreparedStatement tempA = con.prepareStatement(tempActuals);
	    		 PreparedStatement UtempA = con.prepareStatement(updatetempActuals);
	    		 PreparedStatement actu = con.prepareStatement(selectActuals);
	    		 PreparedStatement tempA2 = con.prepareStatement(pridedone2);
				 ) {
	        
	        for (Operation_TRAX o : request.getOperation()) {
	            if (request.getWO() != null && !request.getWO().isEmpty()) {
	            	
	            	
	            	//Get transaction number 
	            	actu.setString(1, request.getWO());
	            	actu.setString(2, o.getMaterial());
	                ResultSet actus = actu.executeQuery();
	                
	                String Trnasction = null;
	                if (actus.next()) {
                       Trnasction = actus.getString(1);
                    }
	                
	                if (Trnasction == null) {
	                    System.out.println("No transaction found for WO: " + request.getWO() + " and Material: " + o.getMaterial());
	                    continue;
	                }

	                // Set the WO parameter in the query
	                ps1.setString(1, request.getWO());
	                ResultSet rs = ps1.executeQuery();

	                // Ensure that ResultSet contains at least one row
	                if (rs.next()) {
	                    // Get the currency from the ResultSet
	                    String Currency = rs.getString(1);

	                    // Get the TotalPrice and QTY from Operation_TRAX
	                    String TotalPrice = o.getSell_Total_Price().trim();
	                    String QTY = o.getQty().trim(); 
	                    BigDecimal qtyDecimal1 = new BigDecimal(QTY); 
	                    QTY = qtyDecimal1.stripTrailingZeros().toPlainString(); 

	                    
	                    if (QTY.contains(".")) {
	                        QTY = QTY.split("\\.")[0]; 
	                    }
	                    String UnitPrice = "";

	                    try {
	                        // Convert the values to BigDecimal for greater precision
	                        BigDecimal totalPriceDecimal = new BigDecimal(TotalPrice);
	                        BigDecimal qtyDecimal = new BigDecimal(QTY);

	                        // Calculate the UnitPrice and round to 2 decimal places
	                        BigDecimal unitPriceDecimal = totalPriceDecimal.divide(qtyDecimal, 4, RoundingMode.HALF_UP);
	                        unitPriceDecimal = unitPriceDecimal.setScale(2, RoundingMode.HALF_UP);

	                        // Convert the result to String
	                        UnitPrice = unitPriceDecimal.toString();
	                    } catch (NumberFormatException e) {
	                        executed = "Error converting the values of price and qty to number: " + e.getMessage();
	                        Unit_Price_RFO_Controller.addError(executed);
	                        logger.severe(executed);
	                    } catch (ArithmeticException e) {
	                        executed = "Error on the operation: " + e.getMessage();
	                        Unit_Price_RFO_Controller.addError(e.toString());
	                        logger.severe(e.toString());
	                    }

	                    // Get the currency from the operation
	                    String operationCurrency = o.getCurrency();

	                    // Compare the two currencies after calculating UnitPrice
	                    if (Currency != null && Currency.equals(operationCurrency)) {
	                        // The currencies are equal
	                        System.out.println("The currencies match: " + Currency);
	                        
	                        // Convert unit_price and qty from String to BigDecimal
	                        BigDecimal unitPriceBD = new BigDecimal(UnitPrice);
	                        BigDecimal qtyBD = new BigDecimal(QTY);
	                        
	                        // Calculate the total cost
	                        BigDecimal totalCostBD = unitPriceBD.multiply(qtyBD);
	                        
	                        // Convert total cost back to String
	                        String totalCost = totalCostBD.toString();
	                        
	                        if (Currency.equals(operationCurrency)) {
	                            usd.setString(1, UnitPrice);
	                            usd.setString(2, QTY);
	                            usd.setString(3, totalCost);
	                            usd.setString(4, UnitPrice);
	                            usd.setString(5, totalCost);
	                            usd.setString(6, request.getWO());
	                            usd.setString(7, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Updated with " + operationCurrency + " prices.");
	                        } 

	                    } else {
	                    	
	                    	
	                    	
	                    	sgd.setString(1, o.getCurrency());
                            sgd.setString(2, o.getSell_Total_Price().trim());
                            sgd.setString(3, request.getWO());
                            sgd.setString(4, o.getMaterial());
                            sgd.executeUpdate();
	                    	
	                        // The currencies do not match, handle the conversion using exchange rates
	                        BigDecimal exchangeRateDecimal = BigDecimal.ZERO;

	                        if (Currency.equals("GBP") && !(operationCurrency.equals("GBP"))) {
	                            // Set exchange query for SGD
	                            ex.setString(1, operationCurrency);
	                            ResultSet exRs = ex.executeQuery();

	                            if (exRs.next()) {
	                                exchangeRateDecimal = new BigDecimal(exRs.getString(1));
	                            }

	                            // Convert unit price to SGD: UnitPrice * exchange rate
	                            BigDecimal unitPriceConverted = new BigDecimal(UnitPrice).multiply(exchangeRateDecimal).setScale(2, RoundingMode.HALF_UP);
	                            UnitPrice = unitPriceConverted.toString();
	                            
	                            // Calculate total cost: unitPriceConverted * qty
	                            BigDecimal qtyBD = new BigDecimal(QTY);
	                            BigDecimal totalCostBD = unitPriceConverted.multiply(qtyBD).setScale(2, RoundingMode.HALF_UP);
	                            String totalCost = totalCostBD.toString();

	                            // Update with SGD
	                            usd.setString(1, UnitPrice);
	                            usd.setString(2, QTY);
	                            usd.setString(3, totalCost);
	                            usd.setString(4, UnitPrice);
	                            usd.setString(5, totalCost);
	                            usd.setString(6, request.getWO());
	                            usd.setString(7, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Converted and updated with " + operationCurrency + " prices.");

	                        } else if (Currency.equals("EUR") && !(operationCurrency.equals("GBP") || operationCurrency.equals("EUR"))) {
	                            // Set exchange query for SGD
	                            ex.setString(1, operationCurrency);
	                            ResultSet exRs = ex.executeQuery();

	                            if (exRs.next()) {
	                                exchangeRateDecimal = new BigDecimal(exRs.getString(1));
	                            }

	                            // Convert unit price to SGD: UnitPrice * exchange rate
	                            BigDecimal unitPriceConverted = new BigDecimal(UnitPrice).multiply(exchangeRateDecimal).setScale(2, RoundingMode.HALF_UP);
	                            UnitPrice = unitPriceConverted.toString();

	                            // Calculate total cost: unitPriceConverted * qty
	                            BigDecimal qtyBD = new BigDecimal(QTY);
	                            BigDecimal totalCostBD = unitPriceConverted.multiply(qtyBD).setScale(2, RoundingMode.HALF_UP);
	                            String totalCost = totalCostBD.toString();

	                            // Update with SGD
	                            usd.setString(1, UnitPrice);
	                            usd.setString(2, QTY);
	                            usd.setString(3, totalCost);
	                            usd.setString(4, UnitPrice);
	                            usd.setString(5, totalCost);
	                            usd.setString(6, request.getWO());
	                            usd.setString(7, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Converted and updated with " + operationCurrency + " prices.");

	                        } else if (Currency.equals("USD") && !(operationCurrency.equals("GBP") || operationCurrency.equals("EUR") || operationCurrency.equals("USD"))) {
	                            // Set exchange query for SGD
	                            ex.setString(1, operationCurrency);
	                            ResultSet exRs = ex.executeQuery();

	                            if (exRs.next()) {
	                                exchangeRateDecimal = new BigDecimal(exRs.getString(1));
	                            }

	                            // Convert unit price to SGD: UnitPrice * exchange rate
	                            BigDecimal unitPriceConverted = new BigDecimal(UnitPrice).multiply(exchangeRateDecimal).setScale(2, RoundingMode.HALF_UP);
	                            UnitPrice = unitPriceConverted.toString();

	                            // Calculate total cost: unitPriceConverted * qty
	                            BigDecimal qtyBD = new BigDecimal(QTY);
	                            BigDecimal totalCostBD = unitPriceConverted.multiply(qtyBD).setScale(2, RoundingMode.HALF_UP);
	                            String totalCost = totalCostBD.toString();

	                            // Update with SGD
	                            usd.setString(1, UnitPrice);
	                            usd.setString(2, QTY);
	                            usd.setString(3, totalCost);
	                            usd.setString(4, UnitPrice);
	                            usd.setString(5, totalCost);
	                            usd.setString(6, request.getWO());
	                            usd.setString(7, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Converted and updated with " + operationCurrency + " prices.");

	                        }  else if (Currency.equals("SGD") 
	                        		&& !(operationCurrency.equals("GBP") || operationCurrency.equals("EUR") || operationCurrency.equals("USD")|| operationCurrency.equals("SGD"))) {
	                            // Set exchange query for SGD
	                            ex.setString(1, operationCurrency);
	                            ResultSet exRs = ex.executeQuery();

	                            if (exRs.next()) {
	                                exchangeRateDecimal = new BigDecimal(exRs.getString(1));
	                            }

	                            // Convert unit price to SGD: UnitPrice * exchange rate
	                            BigDecimal unitPriceConverted = new BigDecimal(UnitPrice).multiply(exchangeRateDecimal).setScale(2, RoundingMode.HALF_UP);
	                            UnitPrice = unitPriceConverted.toString();

	                            // Calculate total cost: unitPriceConverted * qty
	                            BigDecimal qtyBD = new BigDecimal(QTY);
	                            BigDecimal totalCostBD = unitPriceConverted.multiply(qtyBD).setScale(2, RoundingMode.HALF_UP);
	                            String totalCost = totalCostBD.toString();

	                            // Update with SGD
	                            usd.setString(1, UnitPrice);
	                            usd.setString(2, QTY);
	                            usd.setString(3, totalCost);
	                            usd.setString(4, UnitPrice);
	                            usd.setString(5, totalCost);
	                            usd.setString(6, request.getWO());
	                            usd.setString(7, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Converted and updated with " + operationCurrency + " prices.");

	                        } else if (!(Currency.equals("GBP")) && operationCurrency.equals("GBP")) {
	                            // Set exchange query for USD
	                            ex.setString(1, Currency);
	                            ResultSet exRs = ex.executeQuery();

	                            if (exRs.next()) {
	                                exchangeRateDecimal = new BigDecimal(exRs.getString(1));
	                            }

	                            // Convert unit price to USD: UnitPrice / exchange rate
	                            BigDecimal unitPriceConverted = new BigDecimal(UnitPrice).divide(exchangeRateDecimal, 4, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
	                            UnitPrice = unitPriceConverted.toString();

	                         // Calculate total cost: unitPriceConverted * qty
	                            BigDecimal qtyBD = new BigDecimal(QTY);
	                            BigDecimal totalCostBD = unitPriceConverted.multiply(qtyBD).setScale(2, RoundingMode.HALF_UP);
	                            String totalCost = totalCostBD.toString();

	                            // Update with SGD
	                            usd.setString(1, UnitPrice);
	                            usd.setString(2, QTY);
	                            usd.setString(3, totalCost);
	                            usd.setString(4, UnitPrice);
	                            usd.setString(5, totalCost);
	                            usd.setString(6, request.getWO());
	                            usd.setString(7, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Converted and updated with " + operationCurrency + " prices.");
	                            
	                        } else if (!(Currency.equals("GBP") || Currency.equals("EUR") ) && operationCurrency.equals("EUR")) {
	                            // Set exchange query for USD
	                            ex.setString(1, Currency);
	                            ResultSet exRs = ex.executeQuery();

	                            if (exRs.next()) {
	                                exchangeRateDecimal = new BigDecimal(exRs.getString(1));
	                            }

	                            // Convert unit price to USD: UnitPrice / exchange rate
	                            BigDecimal unitPriceConverted = new BigDecimal(UnitPrice).divide(exchangeRateDecimal, 4, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
	                            UnitPrice = unitPriceConverted.toString();

	                         // Calculate total cost: unitPriceConverted * qty
	                            BigDecimal qtyBD = new BigDecimal(QTY);
	                            BigDecimal totalCostBD = unitPriceConverted.multiply(qtyBD).setScale(2, RoundingMode.HALF_UP);
	                            String totalCost = totalCostBD.toString();

	                            // Update with SGD
	                            usd.setString(1, UnitPrice);
	                            usd.setString(2, QTY);
	                            usd.setString(3, totalCost);
	                            usd.setString(4, UnitPrice);
	                            usd.setString(5, totalCost);
	                            usd.setString(6, request.getWO());
	                            usd.setString(7, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Converted and updated with " + operationCurrency + " prices.");
	                            
	                        } else if (!(Currency.equals("GBP") || Currency.equals("EUR") ) && operationCurrency.equals("EUR")) {
	                            // Set exchange query for USD
	                            ex.setString(1, Currency);
	                            ResultSet exRs = ex.executeQuery();

	                            if (exRs.next()) {
	                                exchangeRateDecimal = new BigDecimal(exRs.getString(1));
	                            }

	                            // Convert unit price to USD: UnitPrice / exchange rate
	                            BigDecimal unitPriceConverted = new BigDecimal(UnitPrice).divide(exchangeRateDecimal, 4, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
	                            UnitPrice = unitPriceConverted.toString();

	                         // Calculate total cost: unitPriceConverted * qty
	                            BigDecimal qtyBD = new BigDecimal(QTY);
	                            BigDecimal totalCostBD = unitPriceConverted.multiply(qtyBD).setScale(2, RoundingMode.HALF_UP);
	                            String totalCost = totalCostBD.toString();

	                            // Update with SGD
	                            usd.setString(1, UnitPrice);
	                            usd.setString(2, QTY);
	                            usd.setString(3, totalCost);
	                            usd.setString(4, UnitPrice);
	                            usd.setString(5, totalCost);
	                            usd.setString(6, request.getWO());
	                            usd.setString(7, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Converted and updated with " + operationCurrency + " prices.");
	                            
	                        } else if (!(Currency.equals("GBP") || Currency.equals("EUR") || Currency.equals("USD")) && operationCurrency.equals("USD")) {
	                            // Set exchange query for USD
	                            ex.setString(1, Currency);
	                            ResultSet exRs = ex.executeQuery();

	                            if (exRs.next()) {
	                                exchangeRateDecimal = new BigDecimal(exRs.getString(1));
	                            }

	                            // Convert unit price to USD: UnitPrice / exchange rate
	                            BigDecimal unitPriceConverted = new BigDecimal(UnitPrice).divide(exchangeRateDecimal, 4, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
	                            UnitPrice = unitPriceConverted.toString();

	                         // Calculate total cost: unitPriceConverted * qty
	                            BigDecimal qtyBD = new BigDecimal(QTY);
	                            BigDecimal totalCostBD = unitPriceConverted.multiply(qtyBD).setScale(2, RoundingMode.HALF_UP);
	                            String totalCost = totalCostBD.toString();

	                            // Update with SGD
	                            usd.setString(1, UnitPrice);
	                            usd.setString(2, QTY);
	                            usd.setString(3, totalCost);
	                            usd.setString(4, UnitPrice);
	                            usd.setString(5, totalCost);
	                            usd.setString(6, request.getWO());
	                            usd.setString(7, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Converted and updated with " + operationCurrency + " prices.");
	                        } else if (!(Currency.equals("GBP") || Currency.equals("EUR") || Currency.equals("USD") || Currency.equals("SGD")) && operationCurrency.equals("SGD")) {
	                            // Set exchange query for USD
	                            ex.setString(1, Currency);
	                            ResultSet exRs = ex.executeQuery();

	                            if (exRs.next()) {
	                                exchangeRateDecimal = new BigDecimal(exRs.getString(1));
	                            }

	                            // Convert unit price to USD: UnitPrice / exchange rate
	                            BigDecimal unitPriceConverted = new BigDecimal(UnitPrice).divide(exchangeRateDecimal, 4, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
	                            UnitPrice = unitPriceConverted.toString();

	                         // Calculate total cost: unitPriceConverted * qty
	                            BigDecimal qtyBD = new BigDecimal(QTY);
	                            BigDecimal totalCostBD = unitPriceConverted.multiply(qtyBD).setScale(2, RoundingMode.HALF_UP);
	                            String totalCost = totalCostBD.toString();

	                            // Update with SGD
	                            usd.setString(1, UnitPrice);
	                            usd.setString(2, QTY);
	                            usd.setString(3, totalCost);
	                            usd.setString(4, UnitPrice);
	                            usd.setString(5, totalCost);
	                            usd.setString(6, request.getWO());
	                            usd.setString(7, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Converted and updated with " + operationCurrency + " prices.");
	                        } else {
	                            System.out.println("The currencies do not match and cannot be converted.");
	                        }
	                        
	                    }
	                    

	                    // Check the old price
	                    chold.setString(1, request.getWO());
	                    chold.setString(2, o.getMaterial());
	                    ResultSet rs1 = chold.executeQuery();

	                    if (rs1 != null && rs1.next()) {
	                        String oldPrice = rs1.getString(1);
	                        
	                        BigDecimal oldPriceDecimal1 = (oldPrice == null || oldPrice.equals("0")) ? BigDecimal.ZERO : new BigDecimal(oldPrice);

	                        if (oldPrice == null || oldPrice.equals("0")) {
	                            // Old price is 0 or null, update variance to 0
	                            var.setString(1, "0");
	                            var.setString(2, request.getWO());
	                            var.setString(3, o.getMaterial());
	                            var.executeUpdate();
	                            System.out.println("Variance set to 0.");

	                            // Update old price with the new UnitPrice
	                            old.setString(1, UnitPrice);
	                            old.setString(2, request.getWO());
	                            old.setString(3, o.getMaterial());
	                            old.executeUpdate();
	                            System.out.println("Old price updated.");
	                            
	                            System.out.println("Inserting values on temp table for WO: " + request.getWO());

	                            insertOrUpdateTempActuals(con, Trnasction, request.getWO(), QTY, UnitPrice, o.getCurrency(), o.getSell_Total_Price().trim(), oldPriceDecimal1);
	                            
	                        } else {
	                            // Calculate variance as the difference between oldPrice and UnitPrice
	                            BigDecimal oldPriceDecimal = new BigDecimal(oldPrice);
	                            BigDecimal unitPriceDecimal = new BigDecimal(UnitPrice);
	                            BigDecimal varianceDecimal = unitPriceDecimal.subtract(oldPriceDecimal).setScale(2, RoundingMode.HALF_UP);

	                            // Update variance
	                            var.setString(1, varianceDecimal.toString());
	                            var.setString(2, request.getWO());
	                            var.setString(3, o.getMaterial());
	                            var.executeUpdate();
	                            System.out.println("Variance updated with value: " + varianceDecimal.toString());
	                            
	                            System.out.println("Inserting values on temp table for WO: " + request.getWO());

	                            insertOrUpdateTempActuals(con, Trnasction, request.getWO(), QTY, UnitPrice, o.getCurrency(), o.getSell_Total_Price().trim(), varianceDecimal);
	                            // Update old price with the new UnitPrice
	                            old.setString(1, UnitPrice);
	                            old.setString(2, request.getWO());
	                            old.setString(3, o.getMaterial());
	                            old.executeUpdate();
	                            System.out.println("Old price updated.");
	                        }
	                    } else {
	                        System.out.println("No old price found for WO: " + request.getWO() + " and Material: " + o.getMaterial());
	                    }
	                    
	                    
	                    System.out.println("Returning Get Price Flag to N for WO: " + request.getWO());
	                    pstmt1.setString(1, request.getWO());
	                    pstmt1.setString(2, o.getMaterial());
	                    pstmt1.executeUpdate();
	                    
	                    

	                    if (request.getError_code() != null && !request.getError_code().equalsIgnoreCase("53")) {
	                        executed = "WO: " + request.getWO() + ", PN: " + o.getMaterial() + ", Error Code: " + request.getError_code() + ", Remarks: " + request.getRemarks();

	                        Unit_Price_RFO_Controller.addError(executed);

	                        pstmt1.setString(1, request.getWO());
	                        pstmt1.setString(2, o.getMaterial());
	                        pstmt1.executeUpdate();

							tempA2.setString(1, request.getWO());
							tempA2.setString(2, Trnasction);
							tempA2.executeUpdate();
	                    }
	                } else {
	                    // Handle the case where no currency is found
	                    executed = "No currency found for WO: " + request.getWO();
	                    Unit_Price_RFO_Controller.addError(executed);
	                    logger.severe(executed);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        executed = e.toString();
	        Unit_Price_RFO_Controller.addError(executed);
	        logger.severe(executed);
	    }

	    return executed;
	}
	
	public void insertOrUpdateTempActuals(Connection con, String transaction, String WO, String QTY, String UnitPrice, String Currency, String sellTotalPrice, BigDecimal varianceDecimal) throws SQLException {
	    String tempActuals = "insert into wo_actuals_material_temp  (wo_actual_transaction, trasaction_category, wo, get_price, qty, unit_cost, total_cost, add_bill_currency, add_bill_curr_amount, unit_sell_b, variance_price) " +
	                         "values ( ?, 'MATERIAL', ?, 'N', ?, ?, ?, ?, ?, ?, ? )";
	    
	    String updateTempActuals = "update wo_actuals_material_temp set get_price = 'N', qty = ?, unit_cost = ?, total_cost = ?, add_bill_currency = ?, add_bill_curr_amount = ?, unit_sell_b = ?, variance_price = ? " + 
	                               "where wo = ? and wo_actual_transaction = ? and trasaction_category = 'MATERIAL'";

	    try (PreparedStatement tempA = con.prepareStatement(tempActuals);
	         PreparedStatement updateA = con.prepareStatement(updateTempActuals)) {

	        try {
	            // Calculate totalCost inside the SQL by multiplying qty and unit_cost
	            tempA.setString(1, transaction);
	            tempA.setString(2, WO);
	            tempA.setString(3, QTY);
	            tempA.setString(4, UnitPrice);
	            // Compute totalCost as qty * unit_cost
	            tempA.setBigDecimal(5, new BigDecimal(QTY).multiply(new BigDecimal(UnitPrice)));
	            tempA.setString(6, Currency);
	            tempA.setString(7, sellTotalPrice);
	            tempA.setString(8, UnitPrice);  // variance_price or other placeholder value
	            tempA.setString(9, varianceDecimal.toString());  // Setting variance_price from varianceDecimal
	            tempA.executeUpdate();
	            System.out.println("Insert into wo_actuals_material_temp completed successfully.");
	        } catch (SQLException e) {
	            // If a duplicate entry is found, we perform an update
	            if (e.getSQLState().startsWith("23")) {  // SQLState starting with "23" typically indicates constraint violations
	                System.out.println("Record already exists, updating wo_actuals_material_temp.");
	                updateA.setString(1, QTY);
	                updateA.setString(2, UnitPrice);
	                // Compute totalCost in the update as well
	                updateA.setBigDecimal(3, new BigDecimal(QTY).multiply(new BigDecimal(UnitPrice)));
	                updateA.setString(4, Currency);
	                updateA.setString(5, sellTotalPrice);
	                updateA.setString(6, UnitPrice);
	                updateA.setString(7, varianceDecimal.toString());  // Setting variance_price from varianceDecimal
	                updateA.setString(8, WO);
	                updateA.setString(9, transaction);
	                updateA.executeUpdate();
	                System.out.println("Update into wo_actuals_material_temp completed successfully.");
	            } else {
	                throw e;  // rethrow the exception if it's not a duplicate entry
	            }
	        }
	    }
	}

	
	public ArrayList<INT27_SND> getPrice() throws Exception {
	    executed = "OK";

	    if (this.con == null || this.con.isClosed()) {
	        try {
	            this.con = DataSourceClient.getConnection();
	            if (this.con == null || this.con.isClosed()) {
	                throw new IllegalStateException("Issues connecting to the database.");
	            }
	            logger.info("Established connection to the database.");
	        } catch (SQLException e) {
	            throw new IllegalStateException("Error trying to re-connect to the database.", e);
	        }
	    }

	    ArrayList<INT27_SND> list = new ArrayList<>();
	    ArrayList<Operation_SND> orlist = new ArrayList<>();
	    String lastWO = "";

	    String sqlPRICE = "SELECT DISTINCT " +
	            "    W.WO, " +
	            "    COALESCE(W.MOD_NO, ParentWO.MOD_NO) AS MOD_NO, " +
	            "    W.RFO_NO, " +
	            "    MAX(COALESCE(PDR.LEGACY_BATCH, RDR.LEGACY_BATCH)) AS LEGACY_BATCH, " +
	            "    WA.PN " +
	            "FROM WO W " +
	            "LEFT JOIN WO ParentWO ON W.NH_WO = ParentWO.WO AND ParentWO.MOD_NO IS NOT NULL " +
	            "LEFT JOIN WO_SHOP_DETAIL WSD ON W.WO = WSD.WO " +
	            "LEFT JOIN WO_ACTUALS WA ON W.WO = WA.WO " +
	            "LEFT JOIN PICKLIST_HEADER PH ON W.WO = PH.WO AND WA.TASK_CARD = PH.TASK_CARD " +
	            "LEFT JOIN PICKLIST_DISTRIBUTION PD ON PH.PICKLIST = PD.PICKLIST " +
	            "    AND WA.TASK_CARD = PD.TASK_CARD " +
	            "    AND WA.PN = PD.PN " +
	            "    AND PD.STATUS = 'CLOSED' " +
	            "    AND PD.TRANSACTION = 'DISTRIBU' " +
	            "LEFT JOIN PICKLIST_DISTRIBUTION_REC PDR ON PH.PICKLIST = PDR.PICKLIST AND PD.STATUS = 'CLOSED' " +
	            "LEFT JOIN REQUISITION_HEADER RH ON W.WO = RH.WO AND WA.TASK_CARD = RH.TASK_CARD " +
	            "LEFT JOIN REQUISITION_DETAIL RD ON RH.REQUISITION = RD.REQUISITION " +
	            "    AND WA.PN = RD.PN " +
	            "    AND RD.STATUS = 'CLOSED' " +
	            "LEFT JOIN REQUISITION_DETAIL_REC RDR ON RH.REQUISITION = RDR.REQUISITION AND RD.STATUS = 'CLOSED' " +
	            "WHERE W.RFO_NO IS NOT NULL " +
	            "    AND (W.MOD_NO IS NOT NULL OR ParentWO.MOD_NO IS NOT NULL) " +
	            "    AND WA.GET_PRICE = 'Y' " +
	            "    AND WA.INTERFACE_ESD_UP_TRANSFERRED_FLAG IS NULL " +
	            "    AND WA.TRASACTION_CATEGORY = 'MATERIAL' " +
	            "    AND (WA.PN = RD.PN OR WA.PN = PD.PN) " +
	            "    AND (WA.WO = RH.WO OR WA.WO = PH.WO) " +
	            "    AND (WA.TASK_CARD = RH.TASK_CARD OR WA.TASK_CARD = PH.TASK_CARD) " +
	            "    AND (PD.STATUS = 'CLOSED' OR RD.STATUS = 'CLOSED') " +
	            "    AND ((PH.PICKLIST IS NOT NULL " +
	            "          AND PD.PICKLIST IS NOT NULL " +
	            "          AND PDR.LEGACY_BATCH IS NOT NULL) " +
	            "         OR (RH.REQUISITION IS NOT NULL " +
	            "             AND RD.REQUISITION IS NOT NULL " +
	            "             AND RDR.LEGACY_BATCH IS NOT NULL)) " +
	            "GROUP BY " +
	            "    W.WO, " +
	            "    COALESCE(W.MOD_NO, ParentWO.MOD_NO), " +
	            "    W.RFO_NO, " +
	            "    WA.PN";


	    String markPrice = "UPDATE WO_ACTUALS SET INTERFACE_ESD_UP_TRANSFERRED_FLAG = 'Y' WHERE WO = ? AND PN = ?";

	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        sqlPRICE = "SELECT * FROM (" + sqlPRICE;
	    }

	    if (MaxRecord != null && !MaxRecord.isEmpty()) {
	        sqlPRICE = sqlPRICE + " ) WHERE ROWNUM <= ?";
	    }

	    PreparedStatement pstmt1 = null;
	    ResultSet rs1 = null;
	    PreparedStatement pstmt2 = null;
	    ResultSet rs2 = null;

	    try {
	        pstmt1 = con.prepareStatement(sqlPRICE);
	        pstmt2 = con.prepareStatement(markPrice);
	        if (MaxRecord != null && !MaxRecord.isEmpty()) {
	            pstmt1.setString(1, MaxRecord);
	        }

	        rs1 = pstmt1.executeQuery();

	        if (rs1 != null) {
	            INT27_SND req = null; // Define outside the loop to accumulate operations
	            while (rs1.next()) {
	                String currentWO = rs1.getString(1); // Current WO (Work Order)
	                String currentPN = rs1.getString(5);

	                if (req == null || !currentWO.equals(lastWO)) {
	                    // Add the previous req to the list (if it's not the first iteration)
	                    if (req != null) {
	                        list.add(req);
	                    }

	                    // Start a new INT27_SND object for the new WO
	                    req = new INT27_SND();
	                    orlist = new ArrayList<>(); // Reset the operations list
	                    req.setOperation(orlist);

	                    req.setWO(currentWO != null ? currentWO : "");
	                    req.setWBS(rs1.getString(2) != null ? rs1.getString(2) : "");
	                    req.setRFO(rs1.getString(3) != null ? rs1.getString(3) : "");

	                    lastWO = currentWO;
	                }

	                // Create a new Operation_SND and populate fields
	                Operation_SND inbound = new Operation_SND();
	                inbound.setBatch(rs1.getString(4) != null ? rs1.getString(4) : "");
	                inbound.setPN(rs1.getString(5) != null ? rs1.getString(5) : "");

	                // Add the operation to the list of operations
	                req.getOperation().add(inbound);

	                // Mark the price as processed
	                pstmt2.setString(1, currentWO);
	                pstmt2.setString(2, currentPN);
	                pstmt2.executeUpdate();
	            }

	            // Add the last req (if it exists) to the list
	            if (req != null) {
	                list.add(req);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        executed = e.toString();
	        Unit_Price_RFO_Controller.addError(e.toString());

	        logger.severe(executed);
	        throw new Exception("Issue found");
	    } finally {
	        if (rs1 != null && !rs1.isClosed()) rs1.close();
	        if (pstmt1 != null && !pstmt1.isClosed()) pstmt1.close();
	        if (pstmt2 != null && !pstmt2.isClosed()) pstmt2.close();
	    }
	    return list;
	}

	
	
	public String setOpsLine(String opsLine, String email) throws Exception {
	    String Executed = "OK";

	    String query =
	      "INSERT INTO OPS_LINE_EMAIL_MASTER (OPS_LINE, \"EMAIL\") VALUES (?, ?)";

	    PreparedStatement ps = null;

	    try {
	      if (con == null || con.isClosed()) {
	        con = DataSourceClient.getConnection();
	        logger.severe(
	          "The connection was stablished successfully with status: " +
	          String.valueOf(!con.isClosed())
	        );
	      }

	      ps = con.prepareStatement(query);

	      ps.setString(1, opsLine);
	      ps.setString(2, email);

	      ps.executeUpdate();
	    } catch (SQLException sqle) {
	      logger.severe(
	        "A SQLException" +
	        " occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.BAD_REQUEST +
	        "\nmessage: " +
	        sqle.getMessage()
	      );
	      throw new Exception(
	        "A SQLException" +
	        " occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.BAD_REQUEST +
	        "\nmessage: " +
	        sqle.getMessage()
	      );
	    } catch (NullPointerException npe) {
	      logger.severe(
	        "A NullPointerException occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.BAD_REQUEST +
	        "\nmessage: " +
	        npe.getMessage()
	      );
	      throw new Exception(
	        "A NullPointerException occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.BAD_REQUEST +
	        "\nmessage: " +
	        npe.getMessage()
	      );
	    } catch (Exception e) {
	      logger.severe(
	        "An Exception occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.INTERNAL_SERVER_ERROR +
	        "\nmessage: " +
	        e.getMessage()
	      );
	      throw new Exception(
	        "An Exception occurred executing the query to get the location site capacity. " +
	        "\n error: " +
	        ErrorType.INTERNAL_SERVER_ERROR +
	        "\nmessage: " +
	        e.getMessage()
	      );
	    } finally {
	      try {
	        if (ps != null && !ps.isClosed()) ps.close();
	      } catch (SQLException e) {
	        logger.severe("An error ocurrer trying to close the statement");
	      }
	    }

	    return Executed;
	  }
	
	public String deleteOpsLine(String opsline) throws Exception{
		String Executed = "OK";
		
		String query = "DELETE OPS_LINE_EMAIL_MASTER where \"OPS_LINE\" = ?";

	    PreparedStatement ps = null;
	    
	    try {
	    	if(con == null || con.isClosed()) {
	    		con = DataSourceClient.getConnection();
	    		logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
	    	}
	    	
	    	ps = con.prepareStatement(query);
	    	
		    ps.setString(1, opsline);
		    
		    ps.executeUpdate();
	    } catch(SQLException sqle) {
	    	logger.severe("A SQLException" + " occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + sqle.getMessage());
	    	throw new Exception("A SQLException" + " occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + sqle.getMessage());
	    } catch(NullPointerException npe) {
	    	logger.severe("A NullPointerException occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + npe.getMessage());
	    	throw new Exception("A NullPointerException occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + npe.getMessage());
	    }catch(Exception e) {
	    	logger.severe("An Exception occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.INTERNAL_SERVER_ERROR + "\nmessage: " + e.getMessage());
	    	throw new Exception("An Exception occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.INTERNAL_SERVER_ERROR + "\nmessage: " + e.getMessage());
	    } finally {
	    	try {
	    		if(ps != null && !ps.isClosed()) ps.close();
	    	} catch(SQLException e) {
	    		logger.severe("Error trying to close the statement");
	    	}
	    }
		
		return Executed;
	}
	
	public String getemailByOpsLine(String opsline) throws Exception{
		ArrayList<String> groups = new ArrayList<String>();
		 String query = "", group = "";
		 
		 if (opsline != null && !opsline.isEmpty()) {
		      query = "Select \\\"EMAIL\\\", OPS_LINE FROM OPS_LINE_EMAIL_MASTER where OPS_LINE = ?";
		    } else {
		      query = " Select \"EMAIL\", OPS_LINE FROM OPS_LINE_EMAIL_MASTER";
		    }
		 
		 PreparedStatement ps = null;
		 
		 try {
			 if(con == null || con.isClosed()) {
				 con = DataSourceClient.getConnection();
				 logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
			 }
			 ps = con.prepareStatement(query);
			 if (opsline != null && !opsline.isEmpty()) {
				 ps.setString(1, opsline);
			 }
			 
			 ResultSet rs = ps.executeQuery();
			 
			 if (rs != null) {
			        while (rs.next()) {
			          groups.add(
			            "OPS_LINE: " + rs.getString(2) + " EMAIL: " + rs.getString(1)
			          );
			        }
			      }
			 rs.close();
		 } catch(SQLException sqle) {
			 logger.severe("A SQLException" + " occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + sqle.getMessage());
			 throw new Exception("A SQLException" + " occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.BAD_REQUEST + "\n message: " + sqle.getMessage());
		 } catch(NullPointerException npe) {
			 logger.severe("A NullPointerException occurred executiong the query to get the location site capacity. " + "\n error:" + ErrorType.BAD_REQUEST + "\n message: " + npe.getMessage());
			 throw new Exception("A NullPointerException occurred executiong the query to get the location site capacity. " + "\n error:" + ErrorType.BAD_REQUEST + "\n message: " + npe.getMessage());
		 } catch(Exception e) {
		    	logger.severe("An Exception occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.INTERNAL_SERVER_ERROR + "\nmessage: " + e.getMessage());
		    	throw new Exception("An Exception occurred executing the query to get the location site capacity. " + "\n error: " + ErrorType.INTERNAL_SERVER_ERROR + "\nmessage: " + e.getMessage());
		 } finally {
			 try {
				 if(ps != null && !ps.isClosed()) ps.close();
			 } catch(SQLException e) {
				 logger.severe("Error trying to close the statement");
			 }
		 } 
		
		 for (String g : groups) {
		      group = group + g + "\n";
		    }
		
		return group;
	}
	
	public String getemailByOnlyOpsLine(String opsLine) {
	    String email = "ERROR";

	    String query =
	      " Select \"EMAIL\", cost_centre FROM OPS_LINE_EMAIL_MASTER where OPS_LINE = ?";

	    PreparedStatement ps = null;
	    
	    try {
		      if (con == null || con.isClosed()) {
		        con = DataSourceClient.getConnection();
		        logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
		      }

		      ps = con.prepareStatement(query);
		      if (opsLine != null && !opsLine.isEmpty()) {
		        ps.setString(1, opsLine);
		      }

		      ResultSet rs = ps.executeQuery();

		      if (rs != null) {
		        while (rs.next()) {
		          email = rs.getString(1);
		        }
		      }
		      rs.close();
		    } catch (Exception e) {
		      email = "ERROR";
		    } finally {
		      try {
		        if (ps != null && !ps.isClosed()) ps.close();
		      } catch (SQLException e) {
		        logger.severe("Error trying to close the statement");
		      }
		    }
	    
	    return email;
	}
	
	  public OpsLineEmail getOpsLineStaffName(String requisition) {
			String query = "";
			OpsLineEmail OpsLineEmail = new OpsLineEmail();

			query =
			  "SELECT rm.RELATION_CODE, rm.NAME,w.ops_line, r.INTERFACE_TRANSFERRED_DATE_ESD \r\n" +
			  "FROM WO w, REQUISITION_HEADER r, relation_master rm \r\n" +
			  "WHERE r.requisition = ? AND w.wo = r.wo AND r.created_by = rm.relation_code";

			PreparedStatement ps = null;

			try {
			  if (con == null || con.isClosed()) {
				con = DataSourceClient.getConnection();
				logger.info("The connection was stablished successfully with status: " + String.valueOf(!con.isClosed()));
			  }

			  ps = con.prepareStatement(query);

			  ps.setString(1, requisition);

			  ResultSet rs = ps.executeQuery();

			  if (rs != null) {
				while (rs.next()) {
				  if (rs.getString(1) != null && !rs.getString(1).isEmpty()) {
					OpsLineEmail.setRelationCode(rs.getString(1));
				  } else {
					OpsLineEmail.setRelationCode("");
				  }

				  if (rs.getString(2) != null && !rs.getString(2).isEmpty()) {
					OpsLineEmail.setName(rs.getString(2));
				  } else {
					OpsLineEmail.setName("");
				  }

				  if (rs.getString(3) != null && !rs.getString(3).isEmpty()) {
					OpsLineEmail.setOpsLine(rs.getString(3));
				  } else {
					OpsLineEmail.setOpsLine("");
				  }

				  OpsLineEmail.setEmail(
					getemailByOnlyOpsLine(OpsLineEmail.getOpsLine())
				  );

				  if (rs.getString(4) != null && !rs.getString(4).isEmpty()) {
					OpsLineEmail.setFlag(rs.getString(4));
				  } else {
					OpsLineEmail.setFlag("");
				  }
				}
			  }
			  rs.close();
			} catch (Exception e) {
			  logger.severe(e.toString());
			  OpsLineEmail.setOpsLine("");
			  OpsLineEmail.setName("");
			  OpsLineEmail.setRelationCode("");
			  OpsLineEmail.setFlag("");
			} finally {
			  try {
				if (ps != null && !ps.isClosed()) ps.close();
			  } catch (SQLException e) {
				logger.severe("Error trying to close the statement");
			  }
			}

			return OpsLineEmail;
		  }
	  
	  public boolean lockAvailable(String notificationType) {
			InterfaceLockMaster lock;
			try {
				lock = em
						.createQuery("SELECT i FROM InterfaceLockMaster i WHERE i.interfaceType = :type", InterfaceLockMaster.class)
						.setParameter("type", notificationType)
						.getSingleResult();
				em.refresh(lock);
			} catch (NoResultException e) {
				lock = new InterfaceLockMaster();
				lock.setInterfaceType(notificationType);
				lock.setLocked(new BigDecimal(0)); 
				insertData(lock);
				return true;
			}

			if (lock.getLocked().intValue() == 1) {
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime lockTime = LocalDateTime.ofInstant(lock.getLockedDate().toInstant(), ZoneId.systemDefault());
				Duration duration = Duration.between(lockTime, now);
				if (duration.getSeconds() >= lock.getMaxLock().longValue()) {
					lock.setLocked(new BigDecimal(0)); 
					insertData(lock);
					return true;
				}
				return false; 
			} else {
				lock.setLocked(new BigDecimal(1)); 
				insertData(lock);
				return true;
			}
		  }

		  private <T> void insertData(T data) {
			try {
			  if (!em.getTransaction().isActive()) em.getTransaction().begin();
			  em.merge(data);
			  em.getTransaction().commit();
			} catch (Exception e) {
			  logger.severe(e.toString());
			}
		  }

		  public void lockTable(String notificationType) {
			em.getTransaction().begin();
			InterfaceLockMaster lock = em.createQuery("SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type",InterfaceLockMaster.class)
			  .setParameter("type", notificationType)
			  .getSingleResult();
			lock.setLocked(new BigDecimal(1));    lock.setLockedDate(
			  Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
			);
			InetAddress address = null;

			try {
			  address = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
			  logger.info(e.getMessage());
			}

			lock.setCurrentServer(address.getHostName());
			em.merge(lock);
			em.getTransaction().commit();
		  }

		  public void unlockTable(String notificationType) {
			em.getTransaction().begin();
			InterfaceLockMaster lock = em
			  .createQuery(
				"SELECT i FROM InterfaceLockMaster i where i.interfaceType = :type",
				InterfaceLockMaster.class
			  )
			  .setParameter("type", notificationType)
			  .getSingleResult();
			lock.setLocked(new BigDecimal(0));
			lock.setUnlockedDate(
			  Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
			);

			em.merge(lock);
			em.getTransaction().commit();
		  }
}
