package com.fengjr.util;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.fengjr.pages.ManagerLoginPage;



/**
 * 管理员登录后台
 * @param String LOGIN_USER_Manager, String LOGIN_USER_Manager_Password
 */

public class LibPlatformManagerLogin {
	
	WebDriver driver;
	public  static  String TestURL_Manager = null;
	public  static  String LOGIN_USER_Manager = null;
	public  static  String LOGIN_USER_Manager_Password = null;
	private static final LibLogger logger = LibLogger
	.getLogger(LibPlatformManagerLogin.class);

	public LibPlatformManagerLogin(WebDriver driver) {
		this.driver = new FirefoxDriver();
	}

	public  boolean loginAction(String LOGIN_USER_Manager, String LOGIN_USER_Manager_Password) throws InterruptedException {

		Properties testdataconfig = LibDataConfig.getInstance().getProperties();

		if (TestURL_Manager == null) {
			TestURL_Manager = testdataconfig.getProperty("testMURL.test_manager");
		}
		
		if ( LOGIN_USER_Manager == null ) {
			LOGIN_USER_Manager = testdataconfig.getProperty("testMURL.test_ManagerUserAdmin");
		}
		
		if (LOGIN_USER_Manager_Password == null) {
			LOGIN_USER_Manager_Password = testdataconfig.getProperty("testMURL.test_ManagerUserPasswd");
		}


		ManagerLoginPage managerloginPage = new ManagerLoginPage(driver, TestURL_Manager);
		this.driver.manage().window().maximize();
		Thread.sleep(4000);//
		managerloginPage.getUserTextField().clear();
		managerloginPage.getUserTextField().sendKeys(LOGIN_USER_Manager);
		
		managerloginPage.getPassWordField().clear();
		managerloginPage.getPassWordField().sendKeys(LOGIN_USER_Manager_Password);
		
		// 手动输入验证码
     	Thread.sleep(7000);
 
     	managerloginPage.getSubmitButton().click();
     	
		// 登录状态判断
		String expectedTitle = "CreditManager";
		String actualTitle = null;
		actualTitle = driver.getTitle();
		logger.log("actualTitle is " +actualTitle);
		Assert.assertEquals(actualTitle , expectedTitle ,"平台管理员登录失败！" );
		com.fengjr.util.LibSnapshot.snapshot((TakesScreenshot) this.driver,"PlatformManagerLoginFailed.png");
		Thread.sleep(3000);
		
		logger.log("PlatformManager Login End ");
			   
	   if (actualTitle  != null ){
		    return true;
	   }else{
		   return false;
	   }

	}
}
