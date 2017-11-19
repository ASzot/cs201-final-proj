package com.cv.jdbc.get;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.cv.jdbc.ConnectToDB;

public class UserSearch {
	
	public UserSearch() {
		
	}
	
	public Vector<String> searchForSimilarUser(String name) {
		Connection conn = ConnectToDB.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Vector<String> similarUsers = new Vector<String>();
		
		
			try {
				
				String findSimilarUser = "SELECT * FROM User WHERE username like ?";
				
				ps = conn.prepareStatement(findSimilarUser);
				ps.setString(1, "%" + name + "%");
				rs = ps.executeQuery();
				
				while(rs.next()) {
					similarUsers.add(rs.getString("username"));
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
			return similarUsers;
	}
}
