package com.cv.jdbc.get;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cv.jdbc.ConnectToDB;

public class ValidateUser {
	
	public ValidateUser(){
		
	}
	
	public int getUserID (String username) {
		Connection conn = ConnectToDB.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int id = -1;
		
		if(isExistingUsername(username)) {
			try {
				
				String getID = "SELECT * FROM User WHERE username=?";
				
				ps = conn.prepareStatement(getID);
				ps.setString(2, username);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					id = rs.getInt("userID");

					return id;
				}
				
			} catch(Exception e){
				return id;
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
		
		return id;
	}
	
	public boolean isValidUserID(int userID) {
		Connection conn = ConnectToDB.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean validUserID = false;
		
		try {
			
			String validationStatement = "SELECT * FROM User WHERE userID=?";
			
			ps = conn.prepareStatement(validationStatement);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("userID");

				if(id == userID) {
					validUserID = true;
					return validUserID;
				}
			}
			
		} catch(Exception e){
			return validUserID;
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
		return validUserID;
	}
		
		
	
	
	public boolean isExistingUsername(String username) {
		Connection conn = ConnectToDB.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean usernameExists = false;
		
		try {
			
			String validationStatement = "SELECT * FROM User WHERE username=?";
			
			ps = conn.prepareStatement(validationStatement);
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String un = rs.getString("username");

				if(username.equals(un)) {
					usernameExists = true;
					return usernameExists;
				}
			}
			
		} catch(Exception e){
			return usernameExists;
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
		return usernameExists;
	}
	
	
	public boolean validateUsernameAndPassword(String Username, String Password) {
		Connection conn = ConnectToDB.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean IsValidUser = false;
		
		try {
			String validationStatement = "SELECT * FROM User WHERE username=?";
			
			ps = conn.prepareStatement(validationStatement);
			ps.setString(1, Username);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String un = rs.getString("username");
				String pass = rs.getString("hashed_password");
				System.out.println("UN: "+un);
				System.out.println("Pass: "+pass);
				if(Username.equals(un) && Password.equals(pass)) {
					IsValidUser = true;
					return IsValidUser;
				}
			}
			
		} catch(Exception e){
			return IsValidUser;
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
		return IsValidUser;
	}
	
}


