package com.fengjr.testcase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import net.sf.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.fengjr.util.*;

/**
 * 管理员登录后台进行标的结算
 * 
 * @param String
 * String env , 
 * 测试环境        
 * @author alvin
 * @version 1.0   
 * @since JDK 1.8
 */
@Test
public class TCA003ManagerSettleLoan_P2P {
	String test;
	WebDriver driver;
	
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger.getLogger(TCA003ManagerSettleLoan_P2P.class);

	@BeforeMethod
	public void setUp() throws Exception {
		logger.log(" PlatformManager Add Loan   Start");
	}

	public void SettleAuditResult() throws InterruptedException {
		LibPlatformManagerSettleAudit  SettleAudit = new LibPlatformManagerSettleAudit(driver);
		boolean Result = SettleAudit.platformManagerSettleAuditAction("TEST");
		if(Result){
			logger.log(" PlatformManager Audit Loan   Scuess");

		}else {
			logger.log(" PlatformManager Audit Loan   Failed");
		}
	}
}
