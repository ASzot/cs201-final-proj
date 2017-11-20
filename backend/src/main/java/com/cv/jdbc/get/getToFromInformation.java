package com.cv.jdbc.get;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cv.jdbc.ConnectToDB;
import com.cv.model.*;

public class getToFromInformation {
	public getToFromInformation(){
		
	}
	
	public List<Market> getAllMarkets(){
		Connection conn = ConnectToDB.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Market> myMarkets = new ArrayList<Market>();
		
		
		try {
			
			String validationStatement = "SELECT * FROM CurrencyToFromExchange";
			
			ps = conn.prepareStatement(validationStatement);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Market myTempMarket = new Market(new CurrencyTicker(-1, rs.getString("fromTicker")), new CurrencyTicker(-1, rs.getString("toTicker")), rs.getString("exchangeName"));
				myMarkets.add(myTempMarket);
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
		return myMarkets;
	
	
	}
}
