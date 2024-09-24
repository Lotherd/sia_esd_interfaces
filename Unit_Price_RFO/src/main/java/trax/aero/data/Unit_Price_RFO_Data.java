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
	
	public String markSendData() throws JAXBException
	{
	  INT27_TRAX request = new INT27_TRAX();
	  try {
	        markTransaction(request);
	        logger.info("markTransaction completed successfully.");
	        return "OK";
	    } catch (Exception e) {
	    	logger.log(Level.SEVERE, "Error executing markTransaction", e);
	    	e.printStackTrace();
	        return null; 
	    }
	}
	private static Map<String, Integer> attemptCounts = new HashMap<>();
	
	public String markTransaction(INT27_TRAX request) {
	    executed = "OK";

	    String pridedone = "UPDATE WO_ACTUALS SET INTERFACE_ESD_UP_TRANSFERRED_FLAG = NULL, GET_PRICE = NULL WHERE WO = ? and task_card_pn = ? ";
	    
	    String getcurrency = "select distinct currency from CUSTOMER_ORDER_HEADER where order_number = ? ";
	    
	    String setpriceUSD = "update wo_actuals set unit_cost = ?, qty = ?, total_cost = ? where wo = ? and task_card_pn = ? ";
	    
	    String setpriceSGD = "update wo_actuals set add_bill_currency = ?, add_bill_curr_amount = ? where wo = ? and task_card_pn = ? ";
	    
	    String exchamgerate = " select distinct exchange_rate from currency_exchange_history where currency = ? ";
	    
	    String checkoldprice = "select distinct unit_sell_b from wo_actuals where wo = ? and task_card_pn = ? ";
	    
	    String oldprice = "update wo_actuals set unit_sell_b = ? where wo = ? and task_card_pn = ? ";
	    
	    String variance = "update wo_actuals set variance_price = ? where wo = ? and task_card_pn = ? ";
	    
	    try (PreparedStatement pstmt1 = con.prepareStatement(pridedone);
	             PreparedStatement ps1 = con.prepareStatement(getcurrency);
	             PreparedStatement usd = con.prepareStatement(setpriceUSD);
	             PreparedStatement sgd = con.prepareStatement(setpriceSGD);
	             PreparedStatement ex = con.prepareStatement(exchamgerate);
	             PreparedStatement chold = con.prepareStatement(checkoldprice);
	             PreparedStatement old = con.prepareStatement(oldprice);
	             PreparedStatement var = con.prepareStatement(variance)) {
	        
	        for (Operation_TRAX o : request.getOperation()) {
	            if (request.getWO() != null && !request.getWO().isEmpty()) {

	                // Set the WO parameter in the query
	                ps1.setString(1, request.getWO());
	                ResultSet rs = ps1.executeQuery();

	                // Ensure that ResultSet contains at least one row
	                if (rs.next()) {
	                    // Get the currency from the ResultSet
	                    String Currency = rs.getString(1);

	                    // Get the TotalPrice and QTY from Operation_TRAX
	                    String TotalPrice = o.getSell_Total_Price();
	                    String QTY = o.getQty();
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
	                        executed = "Error al convertir los valores de precio/cantidad a número: " + e.getMessage();
	                        Unit_Price_RFO_Controller.addError(executed);
	                        logger.severe(executed);
	                    } catch (ArithmeticException e) {
	                        executed = "Error en la operación aritmética: " + e.getMessage();
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
	                            usd.setString(4, request.getWO());
	                            usd.setString(5, o.getMaterial());
	                            usd.executeUpdate();
	                            System.out.println("Updated with " + operationCurrency + " prices.");
	                        } 

	                    } else {
	                    	
	                    	
	                    	
	                    	sgd.setString(1, o.getCurrency());
                            sgd.setString(2, o.getSell_Total_Price());
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
	                            usd.setString(4, request.getWO());
	                            usd.setString(5, o.getMaterial());
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
	                            usd.setString(4, request.getWO());
	                            usd.setString(5, o.getMaterial());
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
	                            usd.setString(4, request.getWO());
	                            usd.setString(5, o.getMaterial());
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
	                            usd.setString(4, request.getWO());
	                            usd.setString(5, o.getMaterial());
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
	                            usd.setString(4, request.getWO());
	                            usd.setString(5, o.getMaterial());
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
	                            usd.setString(4, request.getWO());
	                            usd.setString(5, o.getMaterial());
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
	                            usd.setString(4, request.getWO());
	                            usd.setString(5, o.getMaterial());
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
	                            usd.setString(4, request.getWO());
	                            usd.setString(5, o.getMaterial());
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
	                            usd.setString(4, request.getWO());
	                            usd.setString(5, o.getMaterial());
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

	                    if (rs1.next()) {
	                        String oldPrice = rs1.getString(1);

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

	                    pstmt1.setString(1, request.getWO());
	                    pstmt1.setString(2, o.getMaterial());
	                    pstmt1.executeUpdate();

	                    if (request.getError_code() != null && !request.getError_code().equalsIgnoreCase("53")) {
	                        executed = "WO: " + request.getWO() + ", PN: " + o.getMaterial() + ", Error Code: " + request.getError_code() + ", Remarks: " + request.getRemarks();

	                        Unit_Price_RFO_Controller.addError(executed);

	                        pstmt1.setString(1, request.getWO());
	                        pstmt1.setString(2, o.getMaterial());
	                        pstmt1.executeUpdate();
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

	    ArrayList<INT27_SND> list = new ArrayList<INT27_SND>();
		ArrayList<Operation_SND> orlist = new ArrayList<Operation_SND>();
	    
	    String sqlPRICE = "SELECT DISTINCT W.WO, W.MOD_NO, W.RFO_NO, PDR.LEGACY_BATCH, WSD.PN " +
                "FROM WO W " +
                "INNER JOIN WO_SHOP_DETAIL WSD ON W.WO = WSD.WO " +
                "INNER JOIN WO_ACTUALS WA ON W.WO = WA.WO " +
                "INNER JOIN PICKLIST_HEADER PH ON W.WO = PH.WO " +
                "INNER JOIN PICKLIST_DISTRIBUTION_REC PDR ON PH.PICKLIST = PDR.PICKLIST " +
                "WHERE W.RFO_NO IS NOT NULL " +
                "AND W.MOD_NO IS NOT NULL " +
                "AND WA.GET_PRICE IS NOT NULL "+ 
                "AND WA.INTERFACE_ESD_UP_TRANSFERRED_FLAG IS NULL ";
	    
	    String markPrice = "UPDATE WO_ACTUALS SET INTERFACE_ESD_UP_TRANSFERRED_FLAG = 'Y' WHERE WO = ? ";
	    
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
				if(MaxRecord != null && !MaxRecord.isEmpty()) {
					pstmt1.setString(1, MaxRecord);
				}
				
				rs1 = pstmt1.executeQuery();
				
				if(rs1 != null) {
					while(rs1.next()) {
						logger.info("Processing WO: " + rs1.getString(1) + ", RFO: " + rs1.getString(3));
						INT27_SND req = new INT27_SND();
						orlist = new ArrayList<Operation_SND>();
						req.setOperation(orlist);
						Operation_SND Inbound = new Operation_SND();
						
						if(rs1.getString(1) != null) {
							req.setWO(rs1.getString(1));
						} else {
							req.setWO("");
						}
						
						if(rs1.getString(2) != null) {
							req.setWBS(rs1.getString(2));
						} else {
							req.setWBS("");
						}
						
						if(rs1.getString(3) != null) {
							req.setRFO(rs1.getString(3));
						} else {
							req.setRFO("");
						}
						
						if(rs1.getString(4) != null) {
							Inbound.setBatch(rs1.getString(4));
						}else {
							Inbound.setBatch("");
						}
						
						if(rs1.getString(5) != null) {
							Inbound.setPN(rs1.getString(3));;
						}else {
							Inbound.setPN("");
						}

						req.getOperation().add(Inbound);
						list.add(req);
						
						
						pstmt2.setString(1, req.getWO());
						rs2 = pstmt2.executeQuery();
						
					}
				}
				
				if (rs1 != null && !rs1.isClosed()) rs1.close();
		 }catch (Exception e) {
		      e.printStackTrace();
		      executed = e.toString();
		      Unit_Price_RFO_Controller.addError(e.toString());

		      logger.severe(executed);
		      throw new Exception("Issue found");
		}finally {
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
