package com.fengjr.util;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *  JDBC   Version 1
 */
public class LibJDBC {
	private static final LibLogger logger = LibLogger
	.getLogger(LibJDBC.class);
	public static void release(ResultSet rs, 
			Statement statement, Connection conn) {
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 *   Statement Connection
	 * @param statement
	 * @param conn
	 */
	public static void release(Statement statement, Connection conn) {
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 1. 
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {

		Properties properties = new Properties();

//		InputStream in = LibJDBC.class.getClassLoader().getResourceAsStream("jdbc.properties");
        InputStream in = new FileInputStream("src//com//fengjr//config//jdbc.properties");
//        System.out.println("DB_Config InputStream is "+in);
//        logger.log("DB_Config InputStream is "+in);

		// 3).
		properties.load(in);

		// 4). 
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String jdbcUrl = properties.getProperty("jdbcUrl");
		String driver = properties.getProperty("driver");
		
//        logger.log("DB_Config  URL is :" +jdbcUrl);

		if(jdbcUrl.contains("10.255.0.108")){
	        logger.log("DB_Config  is  Test");
		}else {
	        logger.log("DB_Config  is  Beta");
		}

		Class.forName(driver);

		// 3. DriverManager   getConnection()
		return DriverManager.getConnection(jdbcUrl, user, password);
	}

}
