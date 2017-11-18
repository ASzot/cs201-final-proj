package com.cv.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToDB {
	public ConnectToDB(){
		
	}
	
	public static Connection getDBConnection(){
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://107.180.58.50/csci201l_FinalProject?user=fp_admin&password=admin&useSSL=false");
			
				
			conn.setAutoCommit(false);
					
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} 
		return conn;
	}
}
		
		




	
	
	
	
	
	

