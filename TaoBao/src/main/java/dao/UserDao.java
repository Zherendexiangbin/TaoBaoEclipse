package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.User;
import util.DataBaseUtil;



public class UserDao {
	public static User getOne(String name) {
		Connection conn = DataBaseUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM user WHERE username=?");
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return new User(rs.getString("username"), rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();	
				}
				if (stmt != null) {
					stmt.close();	
				}
				if (conn != null) {
					conn.close();	
				}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
}
