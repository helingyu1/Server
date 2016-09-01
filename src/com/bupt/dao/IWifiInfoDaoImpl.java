package com.bupt.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bupt.connection.ConnectionPool;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class IWifiInfoDaoImpl implements IWifiInfoDao{
	
	private static ConnectionPool connPool;
	
	static{
		connPool = new ConnectionPool();
		try {
			//创建数据库连接池
			connPool.createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveWifiInfoToDB() {
		String sql = "insert into record(wifi_id,wifi_ipv4,wifi_ipv4_port,time) values(?,?,?,?)";
	}

	@Override
	public void getInfoInDB() {
		String sql = "select wifi_ipv4,wifi_ipv4_port from record where wifi_id=?";
	}

	@Override
	public void saveHeartInfoToDB() {
	}
	//test
	public static void main(String[] orgs) throws SQLException{
		Connection conn = (Connection) connPool.getConnection();
		String sql = "select * from user_info";
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		System.out.println(rs.wasNull());
		int i = 0;
		while(rs.next())
			i++;
		System.out.println(i);
	}

}
