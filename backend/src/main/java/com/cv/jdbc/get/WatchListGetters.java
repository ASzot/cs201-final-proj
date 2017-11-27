package com.cv.jdbc.get;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.cv.jdbc.ConnectToDB;

public class WatchListGetters {
	public WatchListGetters(){
		
	}
	
	public Vector<String> getUserWatchListString(String username){
		GetCurrencyInformation getTicker = new GetCurrencyInformation();
		
		Vector<Integer> integerWatchList = getUserWatchListInt(username);
		Vector<String> stringWatchList = new Vector<String>();
		
		
		for (int i=0; i<integerWatchList.size(); i++) {
			stringWatchList.add(getTicker.getTickerFromID(integerWatchList.get(i)));
		}
		
		return stringWatchList;
	}
	
	public Vector<Integer> getUserWatchListInt(String username){
		Connection conn = ConnectToDB.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
	//	ValidateUser usernameValidation = new ValidateUser();
		
		Vector<Integer> myUsersWatchList = new Vector<Integer>();
		
		ValidateUser myUserValidation = new ValidateUser();
		int userID = myUserValidation.getUserID(username);
		//System.out.println(username);
		//System.out.println(userID);
		
	//	if(usernameValidation.isExistingUsername(username)) { Took out username validation for speeds sake
			try {
				
				String watchListStatement = "SELECT * FROM UserWatchList WHERE userID = ?";
				ps = conn.prepareStatement(watchListStatement);
				ps.setInt(1, userID);
				
				rs = ps.executeQuery();
				
			
				while(rs.next()) {
					
					int tempCurrencyID = rs.getInt("currencyID");
					myUsersWatchList.add(tempCurrencyID);
				
					
				}
			
			} catch(Exception e){
			
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
		//}
		return myUsersWatchList;
	}
	
	public boolean isAlreadyInWatchList(int userID, int currencyID ) {
		Connection conn = ConnectToDB.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		boolean isAlreadyInList = false;
		
			try {
				
				String getID = "SELECT * FROM UserWatchList WHERE userID = ?";
				
				ps = conn.prepareStatement(getID);
				ps.setInt(1, userID);
				
				rs = ps.executeQuery();
				
				while(rs.next()) {
					int tempUserID = rs.getInt("userID");
					int tempCurrencyID = rs.getInt("currencyID");
					
					if((userID == tempUserID)&&(currencyID == tempCurrencyID)) {
						isAlreadyInList = true;
					}	
				}
				
			} catch(Exception e){
				
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
		
		return isAlreadyInList;
	}

}
