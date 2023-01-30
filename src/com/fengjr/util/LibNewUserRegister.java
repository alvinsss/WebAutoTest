package com.fengjr.util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

@SuppressWarnings("unused")
public class LibNewUserRegister {
	
	WebDriver driver;
	private static String userRegisterURL = null; 
	public static String randomCodeValues = null; 
	private static String RegisterPhone_number = null; 
	private static String RegisterPasswd = null; 
	private static String role = null ;
	private static final LibLogger logger = LibLogger
	.getLogger(LibNewUserRegister.class);

	public LibNewUserRegister(WebDriver driver) {
		this.driver = driver;
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void NewUserRegisterNotVIPByPhone( String RegisterPhone_number, String role) throws InterruptedException {
		
		Properties testdataconfig = LibDataConfig.getInstance().getProperties();
		if (userRegisterURL == null) {
			userRegisterURL = testdataconfig.getProperty("testURL.userRegister");
			logger.log(userRegisterURL);
		}
		this.driver.get(userRegisterURL);		
		this.driver.findElement(By.name("username")).sendKeys("autotestui");
		this.driver.findElement(By.name("phone_number")).sendKeys(RegisterPhone_number);
		logger.log(RegisterPhone_number);

		this.driver.findElement(By.id("daojishi")).click();
		Thread.sleep(3000);
		if (RegisterPasswd == null) {
			RegisterPasswd = testdataconfig.getProperty("testURL.RegisterPasswd");
			logger.log(RegisterPasswd);
		}
		this.driver.findElement(By.id("tx")).sendKeys(RegisterPasswd);
	    WebElement checkbox = this.driver.findElement(By.id(role)) ; 
	    checkbox.click();	    
		
		if (randomCodeValues == null) {			
			Connection conn = null;
			Statement statement = null;
			ResultSet rs = null;			
			try {
				conn = LibJDBC.getConnection();
				statement = conn.createStatement();
				String sql = "SELECT smsContext from smsinfo where smsPhone='" + RegisterPhone_number + "' ORDER BY smsId desc LIMIT 1;";
				logger.log(sql);
				rs = statement.executeQuery(sql);
				
				while(rs.next()){
					randomCodeValues = rs.getString("smsContext");				
					logger.log("sql ResultSet randomCodeValues is :"+randomCodeValues);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				LibJDBC.release(rs, statement, conn);
			}
		}
		randomCodeValues = randomCodeValues.substring(4);
		logger.log("截取之后的randomCodeValues:"+randomCodeValues);
		this.driver.findElement(By.name("randomCode")).sendKeys(randomCodeValues);
		
		LibSnapshot LibSnapshot = new LibSnapshot();
		LibSnapshot.snapshot((TakesScreenshot)driver,"NewUserRegisterByPhone.png");	
		this.driver.findElement(By.xpath("//input[@type='submit']")).click();	
		Thread.sleep(20000);
	}	
}
