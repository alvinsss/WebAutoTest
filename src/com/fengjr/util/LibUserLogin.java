package com.fengjr.util;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class LibUserLogin {
	
	WebDriver driver;
	private static String userLoginURL = null; 
	public static String randomCodeValues = null; 
	private static String RegisterPhone_number = null; 
	private static String RegisterPasswd = null; 
	private static String role = null ;
	private static final LibLogger logger = LibLogger
	.getLogger(LibUserLogin.class);

	public LibUserLogin(WebDriver driver) {
		this.driver = driver;
	}

	@SuppressWarnings("static-access")
	@Test
	public void LibUserLoginAction( ) throws InterruptedException {
		
		Properties testdataconfig = LibDataConfig.getInstance().getProperties();
		if (userLoginURL == null) {
			userLoginURL = testdataconfig.getProperty("testURL.UserLoginURL");
			logger.log(userLoginURL);
		}
		this.driver.get(userLoginURL);
		
		this.driver.findElement(By.name("username")).sendKeys("autotestui");
//		
//		if (RegisterPhone_number == null) {
//			RegisterPhone_number = testdataconfig.getProperty("testURL.RegisterPhone_number");
//		}
		this.driver.findElement(By.name("phone_number")).sendKeys(RegisterPhone_number);
		logger.log(RegisterPhone_number);

	
		if (RegisterPasswd == null) {
			RegisterPasswd = testdataconfig.getProperty("testURL.RegisterPasswd");
			logger.log(RegisterPasswd);
		}
		this.driver.findElement(By.id("tx")).sendKeys(RegisterPasswd);

	    WebElement checkbox = this.driver.findElement(By.id(role)) ; 
	    checkbox.click();	    

		randomCodeValues = randomCodeValues.substring(4);
		logger.log("截取之后的randomCodeValues:"+randomCodeValues);
		this.driver.findElement(By.name("randomCode")).sendKeys(randomCodeValues);
		
		LibSnapshot LibSnapshot = new LibSnapshot();
		LibSnapshot.snapshot((TakesScreenshot)driver,"NewUserRegisterByPhone.png");	
	
		this.driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		Thread.sleep(20000);

	}	

}
