package com.cv.jdbc.scripts;

import com.cv.model.CurrencyTicker;
import com.cv.model.Market;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.SystemPropertyUtils;

import com.cv.cryptowatch.CryptoWatchApi;
import com.cv.cryptowatch.CryptoWatchRest;
import com.cv.jdbc.ConnectToDB;
import com.cv.jdbc.get.GetCurrencyInformation;
import com.cv.jdbc.get.ValidateUser;

public class AddToFromExchangeInCurrencyInfo {
	
	private CryptoWatchRest rest;
	//CryptoWatchApi API;
	
	
	public AddToFromExchangeInCurrencyInfo(){
		this.rest = new CryptoWatchRest();
		//API = new CryptoWatchApi(rest);
	}
	
	public void addTickersOtherThanThreeChars() {
		
	}
	
	public void AddAllMarketsWhereToIsThreeChar() {
		GetCurrencyInformation validTicker = new GetCurrencyInformation();
		List<Market> myMarkets = getMarketsWhereToIsThreeChar();
		
		Connection conn = ConnectToDB.getDBConnection();
		PreparedStatement ps = null;
		
		
		for(int i=0; i<myMarkets.size(); i++) {
			
			if((myMarkets.get(i).getFromTicker() != null)&&(myMarkets.get(i).getToTicker() != null)&&(myMarkets.get(i).getExchange() != null)){
				if((validTicker.isValidCurrencyTicker((myMarkets.get(i).getFromTicker()).toUpperCase()))&&(validTicker.isValidCurrencyTicker((myMarkets.get(i).getToTicker()).toUpperCase()))) {
				
					try {
						System.out.println("Making a new prepared statement and adding to batch...");
						String addData = "INSERT INTO CurrencyToFromExchange" 
														+"(fromTicker, toTicker, exchangeName) VALUES"
														+"(?, ?, ?)";
							
						ps = conn.prepareStatement(addData);
						ps.setString(1, myMarkets.get(i).getFromTicker());
						ps.setString(2, myMarkets.get(i).getToTicker());
						ps.setString(3, myMarkets.get(i).getExchange());
						ps.execute();	
					} 
					catch (SQLException sqle) {
						System.out.println ("SQLException: " + sqle.getMessage());
							
					} 
				}
			}
		}
		try {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		}
		
	}
	
	
	
	public void AddAllThreeCharTickers() {
		GetCurrencyInformation validTicker = new GetCurrencyInformation();
		List<Market> myMarkets = getMarketsThreeCharTickers();
		
		Connection conn = ConnectToDB.getDBConnection();
		PreparedStatement ps = null;
		
		
		for(int i=0; i<myMarkets.size(); i++) {
			
			if((myMarkets.get(i).getFromTicker() != null)&&(myMarkets.get(i).getToTicker() != null)&&(myMarkets.get(i).getExchange() != null)){
				if((validTicker.isValidCurrencyTicker((myMarkets.get(i).getFromTicker()).toUpperCase()))&&(validTicker.isValidCurrencyTicker((myMarkets.get(i).getToTicker()).toUpperCase()))) {
				
					try {
						System.out.println("Making a new prepared statement and adding to batch...");
						String addData = "INSERT INTO CurrencyToFromExchange" 
														+"(fromTicker, toTicker, exchangeName) VALUES"
														+"(?, ?, ?)";
							
						ps = conn.prepareStatement(addData);
						ps.setString(1, myMarkets.get(i).getFromTicker());
						ps.setString(2, myMarkets.get(i).getToTicker());
						ps.setString(3, myMarkets.get(i).getExchange());
						ps.execute();	
					} 
					catch (SQLException sqle) {
						System.out.println ("SQLException: " + sqle.getMessage());
							
					} 
				}
			}
		}
		try {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		}
		
	}
	
	public List<Market> getMarketsWhereToIsThreeChar() {
	    String endpoint = "/markets";
	    JsonObject result = rest.get(endpoint);
	    
	  
	    
	    if (result == null) {
	      return null;
	    }

	    JsonArray exchanges = result.get("result").getAsJsonArray();

	    List<Market> markets = new ArrayList<Market>();
	    for (JsonElement exchange : exchanges) {
	      JsonObject exchangeObj = exchange.getAsJsonObject();
	      boolean active = exchangeObj.get("active").getAsBoolean();
	      String exchangeStr = exchangeObj.get("exchange").getAsString();
	      String pair = exchangeObj.get("pair").getAsString();

	      if (!active) {
	        continue;
	      }

	      if(pair.length() != 6) {
	    	  
	    	  Market tempMarket = getMarketsNotThreeCharTickersHelper(pair, exchangeStr);
	    	  if((tempMarket.getFromCur() != null) && (tempMarket.getToCur()!=null)) {
	    		  markets.add(tempMarket);
	    		  System.out.println("Added to LIST");
	    	  }
	    	 
	      }
	    }

	    printAPIRateLimitations(result);
	    return markets;
	  }
	
	private Market getMarketsNotThreeCharTickersHelper(String pair, String exchangeStr){
		GetCurrencyInformation isValidTicker = new GetCurrencyInformation();
		
		Market myMarket = new Market();
		String lastThree = pair.substring(pair.length()-3, pair.length());
		String firstPart = pair.substring(0, pair.length()-3);
		
		if((isValidTicker.isValidCurrencyTicker(lastThree.toUpperCase()))&&(isValidTicker.isValidCurrencyTicker(firstPart.toUpperCase()))) {
			myMarket = new Market(new CurrencyTicker(-1, firstPart), new CurrencyTicker(-1, lastThree), exchangeStr);
		}
		
		
		System.out.println(pair);
		System.out.println(firstPart);
		System.out.println(lastThree);
		
		return myMarket;
	}
	
	public List<Market> getMarketsThreeCharTickers() {
	    String endpoint = "/markets";
	    JsonObject result = rest.get(endpoint);
	    
	    GetCurrencyInformation isValidTicker = new GetCurrencyInformation();
	    
	    if (result == null) {
	      return null;
	    }

	    JsonArray exchanges = result.get("result").getAsJsonArray();

	    List<Market> markets = new ArrayList<Market>();
	    for (JsonElement exchange : exchanges) {
	      JsonObject exchangeObj = exchange.getAsJsonObject();
	      boolean active = exchangeObj.get("active").getAsBoolean();
	      String exchangeStr = exchangeObj.get("exchange").getAsString();
	      String pair = exchangeObj.get("pair").getAsString();

	      if (!active) {
	        continue;
	      }
	      
	      String fromStr = null;
	      String toStr = null;
	      
	   
	      
	      if(pair.length() == 6) {
	    	  fromStr = pair.substring(0, 3);
	          toStr = pair.substring(3, 6);
	          CurrencyTicker from = new CurrencyTicker(-1, fromStr);
		      CurrencyTicker to = new CurrencyTicker(-1, toStr);
		      markets.add(new Market(from, to, exchangeStr)); 
	      } 
	      
	    }

	    printAPIRateLimitations(result);
	    return markets;
	  }
	
	private void printAPIRateLimitations(JsonObject result) {
		JsonObject rateInfo = result.get("allowance").getAsJsonObject();
		String cost = rateInfo.get("cost").getAsString();
		String allowance = rateInfo.get("remaining").getAsString();
		System.out.println("Cost: " + cost + " Allowance remaining: " + allowance);
	}
	
	
	
	
}
