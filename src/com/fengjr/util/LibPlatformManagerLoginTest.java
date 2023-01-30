package com.fengjr.util;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.util.Properties;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.*;
import org.testng.Assert;
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
public class LibPlatformManagerLoginTest {
	WebDriver driver;
	public static String LOGIN_USER_Manager = null;
	public static String LOGIN_USER_Manager_Password = null;
	private static final LibLogger logger = LibLogger
	.getLogger(LibPlatformManagerLoginTest.class);

	@BeforeMethod
	public void setUp() throws Exception {
		logger.log(" PlatformManagerLoginTest Action Start");
		this.driver = new FirefoxDriver();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		logger.log(" PlatformManagerLoginTest Action End");
		this.driver.quit();
	}

	public void PlatformManagerLoginTest() throws InterruptedException {
		logger.log("------  PlatformManager Login Start ----------");
		Properties testdataconfig = LibDataConfig.getInstance().getProperties();
		if (LOGIN_USER_Manager == null) {
			LOGIN_USER_Manager = testdataconfig
					.getProperty("testMURL.ManagerUser");
			logger.log(LOGIN_USER_Manager);
		}
		if (LOGIN_USER_Manager_Password == null) {
			LOGIN_USER_Manager_Password = testdataconfig
					.getProperty("testMURL.ManagerUserPassWD");
			logger.log(LOGIN_USER_Manager_Password);
		}

		LibPlatformManagerLogin PlatformManagerLogin = new LibPlatformManagerLogin(this.driver);
		boolean logintestresult = PlatformManagerLogin.loginAction(LOGIN_USER_Manager, LOGIN_USER_Manager_Password);


		String expectedTitle = "****";
		String actualTitle = driver.getTitle();
		AssertJUnit.assertEquals(actualTitle, expectedTitle);
		com.fengjr.util.LibSnapshot.snapshot((TakesScreenshot) this.driver,
				"PlatformManagerLogin.png");
		Thread.sleep(5000);
		logger.log("------  PlatformManager Login End ----------");
	}
}
