package SetData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import GetData.GetCurrencyInformation;
import GetData.ValidateUser;
import JDBCDriver.ConnectToDB;

public class WatchList {
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public WatchList(){
		
	}
	
	public boolean addToWatchList(int userID, int currencyID) {
		Connection conn = ConnectToDB.getDBConnection();
		boolean added = false;
		ValidateUser validateUserID = new ValidateUser();
		GetCurrencyInformation validateCurrencyID = new GetCurrencyInformation();
		
		if((validateUserID.isValidUserID(userID))&&(validateCurrencyID.isValidCurrencyID(currencyID))) {
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
