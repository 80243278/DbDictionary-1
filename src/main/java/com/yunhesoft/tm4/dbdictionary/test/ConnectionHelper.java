package com.yunhesoft.tm4.dbdictionary.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.yunhesoft.tm4.dbdictionary.entity.domain.ConConfigDo;

/**
 * @author zhang.jt
 */
public class ConnectionHelper {

	static {
		try {
			// mssqlserver2005
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	   *得到某个数据库的连接
	 * @param url 不包含databaseName的url
	 * @param user 数据库用户
	 * @param pwd 密码
	 * @param dbName 数据库名
	 * */
	public static Connection getCon(String url, String user, String pwd, String dbName) throws SQLException {
		Connection con = DriverManager.getConnection(url + ";databaseName=" + dbName, user, pwd);
		return con;
	}

	/**
	   * 得到数据库的连接,不一定要指定数据库
	 * @param url url
	 * @param user 数据库用户
	 * @param pwd 密码
	 * */
	public static Connection getCon(ConConfigDo config) throws SQLException {
		Connection con = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPwd());
		return con;
	}

	/**
	   * 测试是否能连上数据库服务器
	 * */
	public static boolean testConnection(ConConfigDo config) {
		try {
			getCon(config);
			return true;
		} catch (Exception err) {
		}
		return false;
	}

	/**
	   * 关闭连接
	 * */
	public static void closeCon(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 执行sql
	 * */
	public static boolean execSql(String sql, Connection conn) throws Exception {
		PreparedStatement ps = conn.prepareStatement(sql);
		return ps.execute();
	}
}
