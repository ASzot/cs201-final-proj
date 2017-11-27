package com.cv.jdbc.set;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cv.jdbc.get.GetCurrencyInformation;
import com.cv.jdbc.get.ValidateUser;

import com.cv.jdbc.get.*;

import com.cv.jdbc.ConnectToDB;

public class WatchListSetters {
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public WatchListSetters(){
		
	}
	
	public boolean addTickerStringToWatchList(String username, String ticker) {
		ValidateUser myUserValidation = new ValidateUser();
		GetCurrencyInformation myCurrencyInfo = new GetCurrencyInformation();
		
		int userID = myUserValidation.getUserID(username);
		int tickerID = myCurrencyInfo.getIDFromTicker(ticker);
		
		return addTickerIDWatchList(userID, tickerID);
	}
	
	
	public boolean addTickerIDWatchList(int userID, int currencyID) {
		Connection conn = ConnectToDB.getDBConnection();
		boolean added = false;
		//ValidateUser validateUserID = new ValidateUser();
		//GetCurrencyInformation validateCurrencyID = new GetCurrencyInformation();
		WatchListGetters myWatchListData = new WatchListGetters();
		
		//Code to check user and currency are valid -> slowed down operaton too much so out for now!!
		//if((validateUserID.isValidUserID(userID))&&(validateCurrencyID.isValidCurrencyID(currencyID))&&(!myWatchListData.isAlreadyInWatchList(userID, currencyID))) {
		if((!myWatchListData.isAlreadyInWatchList(userID, currencyID))) {
			try {
				String insertStatement = "INSERT INTO UserWatchList" 
														+"(userID, currencyID) VALUES"
														+"(?, ?)";
				
				ps = conn.prepareStatement(insertStatement);
				ps.setInt(1, userID);
				ps.setInt(2, currencyID);
				ps.addBatch();
				ps.executeBatch();
				conn.commit();
				
				added = true;
				
			} catch(Exception e){
				return added;
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
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
		} 
			
		return added;
		
	}
	/*
	public boolean removeFromWatchList(int userID, int currencyID) {
		
	}*/
}
