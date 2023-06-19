package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import entity.Commodity;
import util.DataBaseUtil;

public class CommodityDao {
	// 获得所有商品
	public static List<Commodity> getAll() {
		Connection conn = DataBaseUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM product");
			rs = stmt.executeQuery();
			List<Commodity> res = new LinkedList<>();
			while (rs.next()) {
				res.add(new Commodity(rs.getString("image"), rs.getString("pro_name"), rs.getString("about"), rs.getString("price")));
			}
			return res;
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

	// 通过id获得单个商品
	public static Commodity getOne(String pro_name) {
		Connection conn = DataBaseUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM product WHERE pro_name = ?");
			stmt.setString(1, pro_name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return new Commodity(rs.getString("image"), rs.getString("pro_name"), rs.getString("about"), rs.getString("price"));
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
