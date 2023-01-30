package com.fengjr.util;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import org.testng.*;
import org.testng.annotations.Test;

public class LibJDBCTest {

	public <T> List<T> query(Class<T> clazz, String sql){
		return null;
	}
	
	/**

	 */
	@Test
	public void testResultSet(){
		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			//1. Connection
			conn = LibJDBC.getConnection();
			System.out.println(conn);
			
			//2. Statement
			statement = conn.createStatement();
			System.out.println(statement);
			
			//3.  SQL
			String sql = "SELECT id, name, email, birth " +
					"FROM customers";
			
			//4. ResultSet
			rs = statement.executeQuery(sql);
			System.out.println(rs);
			
			//5. ResultSet
			while(rs.next()){
				int id = rs.getInt(1);
				String name = rs.getString("name");
				String email = rs.getString(3);
				Date birth = rs.getDate(4);
				
				System.out.println(id);
				System.out.println(name);
				System.out.println(email);
				System.out.println(birth);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{

			LibJDBC.release(rs, statement, conn);
		}
		
	}
	
	/**

	 */
	public void update(String sql){
		Connection conn = null;
		Statement statement = null;
		
		try {
			conn = LibJDBC.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			LibJDBC.release(statement, conn);
		}
	}
	

	@Test
	public void testStatement() throws Exception{
		Connection conn = null;
		Statement statement = null;
		
		try {
			conn = getConnection2();
						String sql = null;
			
//			sql = "INSERT INTO customers (NAME, EMAIL, BIRTH) " +
//					"VALUES('XYZ', 'xyz@atguigu.com', '1990-12-12')";
//			sql = "DELETE FROM customers WHERE id = 1";
			sql = "UPDATE customers SET name = 'TOM' " +
					"WHERE id = 4";
			System.out.println(sql);
			

			statement = conn.createStatement();
			
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(statement != null)
					statement.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				if(conn != null)
					conn.close();							
			}
		}
		
	}
	
	@Test
	public void testGetConnection2() throws Exception{
		System.out.println(getConnection2()); 
	}
	
	public Connection getConnection2() throws Exception{

		Properties properties = new Properties();
		
//		InputStream in = 
//				this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
//		InputStream in = LibJDBC.class.getClassLoader().getResourceAsStream("com/mfexchange/config/jdbc.properties");
        InputStream in = new FileInputStream("src//com//mfexchange//config//jdbc.properties");

		System.out.println("DB_Config InputStream is "+in);

		properties.load(in);
		
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String jdbcUrl = properties.getProperty("jdbcUrl");
		String driver = properties.getProperty("driver");
		
		Class.forName(driver);
		
		return DriverManager.getConnection(jdbcUrl, user, password);
	}
	
	/**
	 * DriverManager 

	 * @throws Exception 
	 */
	@Test
	public void testDriverManager() throws Exception{

		String driverClass = "com.mysql.jdbc.Driver";
		//JDBC URL
		String jdbcUrl = "jdbc:mysql:///test";
		//user
		String user = "qa";
		//password
		String password = "qatest";
		
		Class.forName(driverClass);
		
		Connection connection = 
				DriverManager.getConnection(jdbcUrl, user, password);
		System.out.println(connection); 
		
	}
	
	/**

	 */
	@Test
	public void testDriver() throws SQLException {
		Driver driver = new com.mysql.jdbc.Driver();
	
		String url = "jdbc:mysql://localhost:3306/test";
		Properties info = new Properties();
		info.put("user", "qa");
		info.put("password", "qatest");
		
		Connection connection = driver.connect(url, info);
		System.out.println(connection);
	}
	
	/**
	 * @throws Exception 
	 */
	public Connection getConnection() throws Exception{
		String driverClass = null;
		String jdbcUrl = null;
		String user = null;
		String password = null;
		
		InputStream in = 
				getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		properties.load(in);
		driverClass = properties.getProperty("driver");
		jdbcUrl = properties.getProperty("jdbcUrl");
		user = properties.getProperty("user");
		password = properties.getProperty("password");
		
		Driver driver = 
				(Driver) Class.forName(driverClass).newInstance();
		
		Properties info = new Properties();
		info.put("user", user);
		info.put("password", password);
		
		Connection connection = driver.connect(jdbcUrl, info);
		
		return connection;
	}
	
	@Test
	public void testGetConnection() throws Exception{
		System.out.println(getConnection());
	}

}
