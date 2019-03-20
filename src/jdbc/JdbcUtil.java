package jdbc;

import java.sql.*;

/**
 * JDBC连接 工具类
 * @class JdbcUtils.java
 * @author fxb
 * @date 2019-3-19 上午10:19:17
 */
public class JdbcUtil {
	
	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	private static final String MYSQL_URL_PREFIX = "jdbc:mysql://";
	private static final String MYSQL_IP_DEFAULT = "127.0.0.1";
	private static final String MYSQL_PORT_DEFAULT = "3306";
	
	
	private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String ORACLE_URL_PREFIX = "jdbc:oracle:thin:@";
	private static final String ORACLE_IP_DEFAULT = "127.0.0.1";
	private static final String ORACLE_PORT_DEFAULT = "1521";
	
	private static Connection mysql_conn;
	private static Connection oracle_conn;
	
	private JdbcUtil() {
		
	}
	
	static{
		try {
			Class.forName(MYSQL_DRIVER);
			Class.forName(ORACLE_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getMysqlConnection(String ip,String port,String database,String username,String password){
		StringBuilder sb = new StringBuilder();
		if(ip == null || ip.equals(""))
			ip = MYSQL_IP_DEFAULT;
		if(port == null || port.equals("")){
			port = MYSQL_PORT_DEFAULT;
		}
		sb.append(MYSQL_URL_PREFIX).append(ip).append(":").append(port)
			.append("/").append(database).append("?characterEncoding=UTF-8");
		if(mysql_conn == null){
			mysql_conn = getConnection(sb.toString(), username, password);
		}
		sb = null;
		return mysql_conn;
	}
	
	public static Connection getOracleConnection(String ip,String port,String database,String username,String password){
		StringBuilder sb = new StringBuilder();
		if(ip == null || ip.equals(""))
			ip = ORACLE_IP_DEFAULT;
		if(port == null || port.equals("")){
			port = ORACLE_PORT_DEFAULT;
		}
		sb.append(ORACLE_URL_PREFIX).append(ip).append(":").append(port).append(":").append(database);
		if (oracle_conn == null) {
			oracle_conn = getConnection(sb.toString(), username, password);
		}
		sb = null;
		return oracle_conn;
	}

	public static Statement getStatement(Connection conn){
		if (conn != null)
			try {
				return  conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}

	public static PreparedStatement getPreparedStatement(Connection conn,String sql){
		if (conn != null)
			try {
				return  conn.prepareStatement(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return null;
	}

	public static CallableStatement getCallableStatement(Connection conn,String sql){
		if (conn != null)
			try {
				return  conn.prepareCall(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return null;
	}

	private static Connection getConnection(String url,String username,String password) {
		try {
			return	DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
