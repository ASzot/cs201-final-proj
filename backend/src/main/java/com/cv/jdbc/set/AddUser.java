package com.cv.jdbc.set;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cv.jdbc.get.ValidateUser;
import com.cv.jdbc.ConnectToDB;

public class AddUser {
	PreparedStatement ps = null;
	
	public AddUser() {
		
	}
	
	public boolean addUser(String Username, String Password) {
		Connection conn = ConnectToDB.getDBConnection();
		boolean added = true;
		ValidateUser checkUsername = new ValidateUser();
		
		if(checkUsername.isExistingUsername(Username)) {
			added = false;
			return added;
		} 
    else {
			try {
				String addUser = "INSERT INTO User" 
												+"(username, hashed_password) VALUES"
												+"(?, ?)";
				
				ps = conn.prepareStatement(addUser);
				ps.setString(1, Username);
				ps.setString(2, Password);
				int result = ps.executeUpdate();
				
				if (result > 0) {  //Redundant code due to .isValidUSername() check, but worthwile to know about
					added = true;
				} else {
					added=false;
				}

				conn.commit();
			} 
      catch (SQLException sqle) {
				System.out.println ("SQLException: " + sqle.getMessage());
				added = false;
			} 
      finally {
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
			return added;
		}
	}
}
