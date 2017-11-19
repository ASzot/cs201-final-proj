package com.cv.jdbc.get;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cv.jdbc.ConnectToDB;

public class WatchListGetters {
	public WatchListGetters(){
		
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
