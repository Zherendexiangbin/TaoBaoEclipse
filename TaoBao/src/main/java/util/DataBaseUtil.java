package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtil {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/taobao?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8", "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
