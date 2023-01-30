package com.fengjr.util;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author qa
 * @func use selenium login
 * @throws Exception
 * @throws InterruptedException
 */
@Test
public class LibUserLogin_Fun {
	WebDriver driver;
	public static String LOGIN_USER_URL = null;
	public static String LOGIN_USER_USERNAME = null;
	public static String LOGIN_USER_USERPasswd = null;
	private static final LibLogger logger = LibLogger.getLogger(LibUserLogin.class);
	
	@BeforeMethod
	public void setUp() throws Exception {
		logger.log(" LibUserLogin Action Start");
		this.driver = new FirefoxDriver();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		logger.log(" LibUserLogin Action End");
		this.driver.quit();
	}

	public void LibUserLoginAction() throws InterruptedException {
		
		Properties testdataconfig = LibDataConfig.getInstance().getProperties();
		if (LOGIN_USER_URL == null) {
			LOGIN_USER_URL = testdataconfig
					.getProperty("testURL.UserURL");
			logger.log(LOGIN_USER_URL);
		}

		if (LOGIN_USER_USERNAME == null) {
			LOGIN_USER_USERNAME = testdataconfig
					.getProperty("testURL.UserName");
			logger.log(LOGIN_USER_USERNAME);
		}
		if (LOGIN_USER_USERPasswd == null) {
			LOGIN_USER_USERPasswd = testdataconfig
					.getProperty("testURL.UserNamePassWD");
			logger.log(LOGIN_USER_USERPasswd);
		}

        this.driver.get(LOGIN_USER_URL);
        this.driver.findElement(By.id("user")).sendKeys(LOGIN_USER_USERNAME);
        Thread.sleep(5000);
        this.driver.findElement(By.id("tx")).sendKeys(LOGIN_USER_USERPasswd);
        Thread.sleep(5000);

        this.driver.findElement(By.xpath("//input[@type='submit']")).click();
        Thread.sleep(5000);
	}
}
