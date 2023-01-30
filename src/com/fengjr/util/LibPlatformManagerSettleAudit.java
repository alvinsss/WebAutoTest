package com.fengjr.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.fengjr.pages.ManagerPage;
import com.sun.java.swing.plaf.windows.resources.windows;

/**
 * 管理员登录后台
 * 
 * @param String
 * String env , String loanId
 * 测试环境                
 */

public class LibPlatformManagerSettleAudit {

	WebDriver driver;
	public static String TestURL_Manager = null;
	public static String TestURL_ManagerAddLoanPage = null;
	public static String TestURL_ManagerAddLoanEditPage = null;
	public static String TestURL_ManagerAddLoanShePage = null;
	public static String LOGIN_USER_Manager = null;
	public static String LOGIN_USER_Manager_Password = null;
	private static final LibLogger logger = LibLogger
			.getLogger(LibPlatformManagerAddP2P.class);

	public LibPlatformManagerSettleAudit(WebDriver driver) {
		this.driver = new FirefoxDriver();
	}

	public boolean platformManagerSettleAuditAction(String env ) throws InterruptedException {

		Properties testdataconfig = LibDataConfig.getInstance().getProperties();
		String Loan_Title = null;

		int lonaAmountInt = new Random().nextInt(10) + 1; 

		String LoanAmount = "1";
		String LoanMonths = "3";
//		String LoanAmount =  String.valueOf(lonaAmountInt) ;

		logger.log("lonaAmount is " +LoanAmount);
		int flag_LoanAudit = 0;

		/********************************** 环境配置 *****************************************/

		logger.log("Current  Test ENV is : " + env);

		if (env.equals("BETA")) {

			if (TestURL_Manager == null) {
				TestURL_Manager = testdataconfig
						.getProperty("testMURL.beta_manager");
//				logger.log(" TestURL_Manager  BETA is : " + TestURL_Manager);
			}

			if (TestURL_ManagerAddLoanPage == null) {
				TestURL_ManagerAddLoanPage = testdataconfig
						.getProperty("testMURL.beta_managerAddLoanPage");
			}

			if (TestURL_ManagerAddLoanEditPage == null) {
				TestURL_ManagerAddLoanEditPage = testdataconfig
						.getProperty("testMURL.beta_managerAddLoanEditPage");
			}

//			logger.log(" TestURL_ManagerAddLoanShePage  aa  is : "+ TestURL_ManagerAddLoanShePage);
			if (TestURL_ManagerAddLoanShePage == null) {
				TestURL_ManagerAddLoanShePage = testdataconfig
						.getProperty("testMURL.beta_managerAddLoanEditPage");
			}

			if (LOGIN_USER_Manager == null) {
				LOGIN_USER_Manager = testdataconfig
						.getProperty("testMURL.beta_ManagerUserAdmin");
			}

			if (LOGIN_USER_Manager_Password == null) {
				LOGIN_USER_Manager_Password = testdataconfig
						.getProperty("testMURL.beta_ManagerUserPasswd");
			}

		} else {

			if (TestURL_Manager == null) {
				TestURL_Manager = testdataconfig
						.getProperty("testMURL.test_manager");
//				logger.log(" TestURL_Manager  is : " + TestURL_Manager);
			}

			if (TestURL_ManagerAddLoanPage == null) {
				TestURL_ManagerAddLoanPage = testdataconfig
						.getProperty("testMURL.test_managerAddLoanPage");
			}

			if (TestURL_ManagerAddLoanEditPage == null) {
				TestURL_ManagerAddLoanEditPage = testdataconfig
						.getProperty("testMURL.test_managerAddLoanEditPage");
			}

			if (TestURL_ManagerAddLoanShePage == null) {
				TestURL_ManagerAddLoanShePage = testdataconfig
						.getProperty("testMURL.test_managerAddLoanEditPage");

			}

			if (LOGIN_USER_Manager == null) {
				LOGIN_USER_Manager = testdataconfig
						.getProperty("testMURL.test_ManagerUserAdmin");
			}

			if (LOGIN_USER_Manager_Password == null) {
				LOGIN_USER_Manager_Password = testdataconfig
						.getProperty("testMURL.test_ManagerUserPasswd");
			}
		}

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-mmss");
		String dateNowStr = sdf.format(d);

		logger.log("  ---------------------------------- Manager Login  Start -------------------------------- ");
		ManagerPage ManagerPages = new ManagerPage(this.driver);
		this.driver.navigate().to(TestURL_Manager);
		this.driver.manage().window().maximize();
		Thread.sleep(1000);//
		ManagerPages.getUserTextField().clear();
		ManagerPages.getUserTextField().sendKeys(LOGIN_USER_Manager);
//		logger.log("LOGIN_USER_Manager is :" + LOGIN_USER_Manager);

		ManagerPages.getPassWordField().clear();
		ManagerPages.getPassWordField().sendKeys(LOGIN_USER_Manager_Password);
//		logger.log("LOGIN_USER_Manager_Password is :" +LOGIN_USER_Manager_Password);

		// 手动输入验证码
		Thread.sleep(7000);
		ManagerPages.getSubmitLoginButton().click();

		// 登录状态判断
		String expectedTitle = "CreditManager";
		String actualTitle = null;
		actualTitle = driver.getTitle();
		if (actualTitle.indexOf("系统登录") != -1) {
			logger.log("actualTitle is " + actualTitle);
			Assert.assertEquals(actualTitle, expectedTitle, "平台管理员登录失败！");
			com.fengjr.util.LibSnapshot.snapshot((TakesScreenshot) this.driver,"PlatformManagerLoginFailed.png");
//			logger.log(" Manager Login  Failed ");
			logger.log("  ---------------------------------- Manager Login  End -------------------------------- ");
//			return false;
		} else {
//			logger.log(" Manager Login  Success ");
			logger.log("  ---------------------------------- Manager Login  End -------------------------------- ");
//			return true;
		}
		this.driver.get(TestURL_Manager);
		
		// 选择贷款管理 - > 结算审核
		ManagerPages.getmanagerMainListLoanManageMenuFied().click();
		Thread.sleep(2000);
		this.driver.getPageSource();
		
		ManagerPages.getmanagerMainListSettledAuditBtnFied().click();
		Thread.sleep(3000);

		
		logger.log("标的结算开始");

		//满标列表的第1个标的进行结算
		logger.log("getText is :" +ManagerPages.getmanagerSettledAuditFirstLoanBtnFied().getText());
//		logger.log("getTagName is :" +ManagerPages.getmanagerSettledAuditFirstLoanBtnFied().getTagName());
//		logger.log("isEnabled is :" +ManagerPages.getmanagerSettledAuditFirstLoanBtnFied().isEnabled());
		logger.log("href is ：" +ManagerPages.getmanagerSettledAuditFirstLoanBtnFied().getAttribute("href"));
		ManagerPages.getmanagerSettledAuditFirstLoanBtnFied().click();
		
		Thread.sleep(3000);
		logger.log("标的结算结束");

		
		return true;
	}
}
