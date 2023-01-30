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
 * 管理员登录后台增加P2P产品
 * 
 * @param String
 * String env , String guaranteeCom , String loanType , String childType , String repmentType , String isVisible
 * 测试环境                 担保公司                                       产品类型                        子类型                                    还款方式                                       是否可转让
 * env：TEST 测试  ，   BETA BETA环境
 * guaranteeCom ：HH 瀚华，NO 没担保公司
 * loanType ：   RT 凤锐通    CX 凤保宝         YY 凤溢盈
 * childType ：默认无   收益权转让 0 ， SYQZY 收益权转让，  JYSCP 交易所产品
 * repmentType:默认值：按月付息到期还本,1 按月付息到期还本 ,2按月等额本息, 3按月等额本金 ,4一次性还本付息 ,5月平息 , 6按年付息到期还本  ,
 *
 *
 * @author alvin
 * @version 1.0   
 * @since JDK 1.7
 */


public class TCA001Test_ManagerAddLoan_P2P {
	String test;
	WebDriver driver;
	
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger.getLogger(TCA001Test_ManagerAddLoan_P2P.class);

	@BeforeMethod
	public void setUp() throws Exception {
		logger.log(" PlatformManager Add Loan   Start");
	}

	public String gettest() {
		return test;
	}
	


//	@Test
//	public void AddLoan_BETA_RT_zcAction() throws InterruptedException {
//		// 凤锐通 月付息到期还本 正常可转让
//		LibPlatformManagerAddP2P addLoan = new LibPlatformManagerAddP2P(driver);
//        //  guaranteeCom , String loanType , String childType , String repmentType , String isVisible
//		boolean addLoan_rtResult = addLoan.addLoanAction("TEST","HH","RT","00","1","zc");
//		logger.log("addLoan_rtResult is :" +addLoan_rtResult);
//		if (addLoan_rtResult) {
//			logger.log("add P2P Loan Result is  Success !");
//		}else{
//			logger.log("add P2P Loan Result is Failed !");
//		}
//		Assert.assertEquals(addLoan_rtResult, true, " add P2P Loan Result is Fail ！");
//	}
	
	
//	@Test
//	public void AddLoan_BETA_YY_zcAction() throws InterruptedException {
//		// 凤锐通 月付息到期还本 正常可转让
//		LibPlatformManagerAddP2P addLoan = new LibPlatformManagerAddP2P(driver);
//        //  guaranteeCom , String loanType , String childType , String repmentType , String isVisible
//		boolean addLoan_rtResult = addLoan.addLoanAction("TEST","HH","YY","SYQZY","2","zc");
//		logger.log("addLoan_rtResult is :" +addLoan_rtResult);
//		if (addLoan_rtResult) {
//			logger.log("add P2P Loan Result is  Success !");
//		}else{
//			logger.log("add P2P Loan Result is Failed !");
//		}
//		Assert.assertEquals(addLoan_rtResult, true, " add P2P Loan Result is Fail ！");
//	}
	
	@Test
	public void AddLoan_BETA_CX_zcAction() throws InterruptedException {
		// 凤锐通 月付息到期还本 正常可转让
		LibPlatformManagerAddP2P addLoan = new LibPlatformManagerAddP2P(driver);
        //  guaranteeCom , String loanType , String childType , String repmentType , String isVisible
		boolean addLoan_rtResult = addLoan.addLoanAction("TEST","HH","CX","SYQZY","4","zc");
		logger.log("addLoan_rtResult is :" +addLoan_rtResult);
		if (addLoan_rtResult) {
			logger.log("add P2P Loan Result is  Success !");
		}else{
			logger.log("add P2P Loan Result is Failed !");
		}
		Assert.assertEquals(addLoan_rtResult, true, " add P2P Loan Result is Fail ！");
	}

}
