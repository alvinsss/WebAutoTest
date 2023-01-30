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
 * String env , String guaranteeCom , String loanType , String childType , String repmentType , String isVisible
 * 测试环境                 担保公司                                       产品类型                        子类型                                    还款方式                                       是否可转让
 */

public class LibPlatformManagerAddP2P {

	WebDriver driver;
	public static String TestURL_Manager = null;
	public static String TestURL_ManagerManagerPages = null;
	public static String TestURL_ManagerAddLoanEditPage = null;
	public static String TestURL_ManagerAddLoanShePage = null;
	public static String LOGIN_USER_Manager = null;
	public static String LOGIN_USER_Manager_Password = null;
	private static final LibLogger logger = LibLogger
			.getLogger(LibPlatformManagerAddP2P.class);

	public LibPlatformManagerAddP2P(WebDriver driver) {
		this.driver = new FirefoxDriver();
	}

	public boolean addLoanAction(String env, String guaranteeCom,String loanType, String childType, String repmentType,String isVisible) throws InterruptedException {

		Properties testdataconfig = LibDataConfig.getInstance().getProperties();
		String Loan_Title = null;

		int lonaAmountInt = new Random().nextInt(10) + 1; 

		String LoanAmount = "10";
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

			if (TestURL_ManagerManagerPages == null) {
				TestURL_ManagerManagerPages = testdataconfig
						.getProperty("testMURL.beta_managerManagerPages");
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

			if (TestURL_ManagerManagerPages == null) {
				TestURL_ManagerManagerPages = testdataconfig
						.getProperty("testMURL.test_managerManagerPages");
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

		} else {
//			logger.log(" Manager Login  Success ");
			logger.log("  ---------------------------------- Manager Login  End -------------------------------- ");
		}

		
		
		
		

		
		
		// 增加P2P产品
		logger.log(" ---------------------------------- Manager add Loan   Start ---------------------------------- ");

		// navigate 取消点击跳转操作
		this.driver.navigate().to(TestURL_ManagerManagerPages);
		Thread.sleep(1000);//
		// ManagerPages.getshortCutAddButionField().click();

		// 增加类型 非RT类型, 均是企业借款
		if (loanType.equals("RT")) {
			ManagerPages.getmanagerPersonLoanButionField().click();
			Thread.sleep(1000);
			ManagerPages.getmanagerLoanButionNameTextField().sendKeys("psy");
		} else {
			ManagerPages.getmanagerQiyeLoanButionField().click();
			Thread.sleep(1000);
			ManagerPages.getmanagerLoanButionNameTextField().sendKeys("银河国际");
		}

		ManagerPages.getmanagerLoanSearchButionField().click();
		Thread.sleep(1000);
		ManagerPages.getmanagerLoanNextButionField().click();
		Thread.sleep(1000);

		// 借款惟一编号
		if (guaranteeCom.equals("HH")) {
			ManagerPages.getmanagerLoanUniqueCodeTextField().sendKeys(
					"瀚华担保：" + dateNowStr);
		} else {
			ManagerPages.getmanagerLoanUniqueCodeTextField().sendKeys(guaranteeCom + dateNowStr);
		}

		
		// 担保公司
		if (guaranteeCom.equals("HH")){
			
			try {
			
			new Select(this.driver.findElement(By.id("corporation"))).selectByVisibleText("瀚华担保有限责任公司");
			
				}catch (Exception e) {
			logger.log("   selcect   corporation exception ");
			e.printStackTrace(System.err);
			this.driver.close();
			}
		
		}else {
			
			try {
				new Select(this.driver.findElement(By.id("corporation"))).selectByVisibleText("无");
				
				}catch (Exception e) {
				logger.log("   selcect   corporation exception ");
				e.printStackTrace(System.err);
				this.driver.close();
				}		
		}
		
		
		//担保方式 -- 抵押
		new Select(driver.findElement(By.id("guarantyStyle"))).selectByVisibleText("抵押");
		
		
	    //产品  ，产品子类型 ，借款标题，借款合同模板，居间合同模板，债权转让合同模板 借款用途
		
		//凤锐通
		if(loanType.equals("RT") && guaranteeCom.equals("HH")){
			try{
				// 产品 凤锐通 ，子类型不需要选择
				new Select(this.driver.findElement(By.name("product")))
				.selectByVisibleText("凤锐通");
				
				//借款标题
				Loan_Title = "凤锐通-HHDB-" + dateNowStr;
				ManagerPages.getmanagerLoantitleTextField().sendKeys(Loan_Title);
				
				//借款合同模板
				new Select(this.driver.findElement(By.name("loanContractTemplate")))
				.selectByVisibleText("瀚华湖北-三方借款协议-个人借款-Auto");
				
				// 居间合同模板
				new Select(this.driver.findElement(By.name("brokerageContractTemplate")))
				.selectByVisibleText("瀚华湖南（个人借款）2015-居间服务协议-Auto");
				
				
				
				// 债权转让合同模板,分环境
				if (env.equals("BETA")){
					new Select(this.driver.findElement(By.name("creditassignContractTemplate"))).selectByVisibleText("债权－有担保－凤锐通");
				}else{
					new Select(this.driver.findElement(By.name("creditassignContractTemplate"))).selectByVisibleText("债权转让及受让协议-凤锐通");
				}
				
				// 借款用途
				new Select(this.driver.findElement(By.name("usage"))).selectByVisibleText("短期周转");

			} catch (Exception e) {
				logger.log("   selcect 模版  exception ");
				e.printStackTrace(System.err);
				this.driver.close();
			}
			
			//凤溢盈
		}else if(loanType.equals("YY")) {
			
			// 产品 凤锐通 ，子类型不需要选择
			new Select(this.driver.findElement(By.name("product"))).selectByVisibleText("凤溢盈");
			
			//借款标题
			Loan_Title = "凤溢盈-HHDB-" + dateNowStr;
			ManagerPages.getmanagerLoantitleTextField().sendKeys(Loan_Title);
			
			if (childType.equals("SYQZY")){
				
				//产品子类型
				new Select(driver.findElement(By.id("subProductType"))).selectByVisibleText("收益权转让");
				
				//借款合同模板
				new Select(this.driver.findElement(By.name("loanContractTemplate"))).selectByVisibleText("瀚华湖北2015-三方借款协议-企业借款-Auto");
			
				// 居间合同模板
				new Select(this.driver.findElement(By.name("brokerageContractTemplate"))).selectByVisibleText("瀚华湖北-居间服务协议-企业借款-Auto");
				
				// 借款用途
				new Select(this.driver.findElement(By.name("usage"))).selectByVisibleText("短期周转");
			}
		
			else {
				
				new Select(driver.findElement(By.id("subProductType"))).selectByVisibleText("交易所产品");
				//借款合同模板
				new Select(this.driver.findElement(By.name("loanContractTemplate"))).selectByVisibleText("瀚瀚华湖北2015-三方借款协议-企业借款-Auto");
				// 居间合同模板
				new Select(this.driver.findElement(By.name("brokerageContractTemplate"))).selectByVisibleText("瀚华湖北-居间服务协议-企业借款-Auto");
			
			}

			
			// 债权转让合同模板
			if(env.equals("BETA")){
		
				if (guaranteeCom.equals("HH")){
				new Select(this.driver.findElement(By.name("creditassignContractTemplate"))).selectByVisibleText("债权－有担保－凤溢盈");
				}else{
				new Select(this.driver.findElement(By.name("creditassignContractTemplate"))).selectByVisibleText("债权－无担保－凤溢盈");
				}

			}else{
				if (guaranteeCom.equals("HH")){
					new Select(this.driver.findElement(By.name("creditassignContractTemplate"))).selectByVisibleText("债权转让及受让协议-凤溢盈");
					}else{
					new Select(this.driver.findElement(By.name("creditassignContractTemplate"))).selectByVisibleText("债权转让及受让协议-无担保版-凤溢盈");
				}
			}

			
		}else{
			
			try{
				// 产品 凤保宝 ，子类型不需要选择
				new Select(this.driver.findElement(By.name("product"))).selectByVisibleText("凤保宝");
				
				//借款标题
				Loan_Title = "凤保宝-HHDB-" + dateNowStr;
				ManagerPages.getmanagerLoantitleTextField().sendKeys(Loan_Title);
				
				if (childType.equals("SYQZY")){
					
					//产品子类型
					new Select(driver.findElement(By.id("subProductType"))).selectByVisibleText("收益权转让");
					
					//借款合同模板
					new Select(this.driver.findElement(By.name("loanContractTemplate"))).selectByVisibleText("瀚华湖北2015-三方借款协议-企业借款-Auto");
				
					// 居间合同模板
					new Select(this.driver.findElement(By.name("brokerageContractTemplate"))).selectByVisibleText("瀚华湖北-居间服务协议-企业借款-Auto");
				}
				
				else {
					
					//产品子类型 
					new Select(driver.findElement(By.id("subProductType"))).selectByVisibleText("收益权转让");

					//借款合同模板
					new Select(this.driver.findElement(By.name("loanContractTemplate"))).selectByVisibleText("瀚瀚华湖北2015-三方借款协议-企业借款-Auto");
					// 居间合同模板
					new Select(this.driver.findElement(By.name("brokerageContractTemplate"))).selectByVisibleText("瀚华湖北-居间服务协议-企业借款-Auto");
				
				}
				
				// 债权转让合同模板
				if(env.equals("BETA") ){
					if (guaranteeCom.equals("HH")){
						new Select(this.driver.findElement(By.name("creditassignContractTemplate"))).selectByVisibleText("债权－有担保－凤溢盈");

					}else{
						new Select(this.driver.findElement(By.name("creditassignContractTemplate"))).selectByVisibleText("债权－无担保－凤溢盈");
					}
					
				}else{
					if (guaranteeCom.equals("HH")){
						new Select(this.driver.findElement(By.name("creditassignContractTemplate"))).selectByVisibleText("债权转让及受让协议-凤保宝");

					}else{
						new Select(this.driver.findElement(By.name("creditassignContractTemplate"))).selectByVisibleText("债权转让及受让协议-无担保版-凤保宝");
					}
					
				}
				
				// 借款用途
				new Select(this.driver.findElement(By.name("usage"))).selectByVisibleText("短期周转");
				
			} catch (Exception e) {
				logger.log("   selcect 模版  exception ");
				e.printStackTrace(System.err);
				this.driver.close();
			}
			
		}
		
		
	
				
		// 设置借款关键数据
		ManagerPages.getmanagerLoanAmountTextField().sendKeys(LoanAmount);
		ManagerPages.getmanagerLoanMinAmountTextField().clear();
		ManagerPages.getmanagerLoanMinAmountTextField().sendKeys("1");
		ManagerPages.getmanagerLoanMaxAmountTextField().clear();
		ManagerPages.getmanagerLoanMaxAmountTextField().sendKeys(LoanAmount);
		ManagerPages.getmanagerLoanMaxStepAmountTextFieldField().clear();
		ManagerPages.getmanagerLoanMaxStepAmountTextFieldField().sendKeys("1");
		ManagerPages.getmanagerLoanMonthsTextField().sendKeys(LoanMonths);
		ManagerPages.getmanagerLoanRateTextField().clear();
		// 年化
		ManagerPages.getmanagerLoanRateTextField().sendKeys("7");
		ManagerPages.getmanagerLoanOptionsRadios2TextField().click();
		new Select(driver.findElement(By.id("guarantyStyle")))
				.selectByVisibleText("抵押");

		
		
		
		// 还款方式 默认 按月付息到期还本
		ManagerPages.getmanagerLoanPaymentMethodTextField().click();

		if (isVisible.equals("zc")) {
			new Select(driver.findElement(By.name("assignable")))
					.selectByVisibleText("正常可转让");
		} else if (isVisible.equals("one")) {
			new Select(driver.findElement(By.name("assignable")))
					.selectByVisibleText("一对一转让");
		} else {
			new Select(driver.findElement(By.name("assignable")))
					.selectByVisibleText("不可转让");
		}

		new Select(driver.findElement(By.id("prepayment_penalty")))
				.selectByVisibleText("有罚息");

		logger.log("++++++++++++++++++++++++++++++++==有罚息");
		//  下一步
		ManagerPages.getmanagerLoanNextStepButionField().click();
		
		
		// 文本区域
		ManagerPages.getmanagerLoanDescriptionTextField().sendKeys("auto ui test ");
		ManagerPages.getmanagerLoanRiskInfoTextField().sendKeys(Loan_Title);
		ManagerPages.getmanagerLoanSubButionField().click();
		Thread.sleep(1000);

		String success = "借款申请成功！";
		if (driver.switchTo().alert().getText().equals(success)) {
			driver.switchTo().alert().accept();
			// this.driver.quit();
			logger.log(" ---------------------------------- Manager add Loan   End ---------------------------------- ");
			// return true;
		} else {
			logger.log(" ---------------------------------- Manager add Loan   End ---------------------------------- ");
			// this.driver.quit();
			// return false;
		}

//		logger.log(" ---------------------------------- Manager Edit Loan   Start ---------------------------------- ");
		//取消调整操作
//		logger.log(" ---------------------------------- Manager Edit Loan   End ---------------------------------- ");

		logger.log(" ---------------------------------- Manager Audit Loan   Start ---------------------------------- ");

		this.driver.get(TestURL_Manager);

		// 选择贷款管理 - > 借款申请列表
		ManagerPages.getmanagerMainListLoanManageMenuFied().click();
		// List<WebElement> buttons =
		// this.driver.findElements(By.tagName("button"));
		// System.out.println(buttons.size()); //打印出button的个数
		ManagerPages.getmanagerMainListRequestBtnFied().click();
		
		try {
			// 审核借款列表页面的标的搜索框    输入搜索title 
			Thread.sleep(6000);
			logger.log("Loan_Title is:"+Loan_Title);
			ManagerPages.getmanagerLoanAuditSearchTextFied().sendKeys(Loan_Title);
			
			Thread.sleep(3000);
			// 点击查询结果
			logger.log("ManagerPages.getmanagerLoanAuditLoanLinkFied().getText() is :"+ ManagerPages.getmanagerLoanAuditLoanLinkFied().getText());
			
			if (ManagerPages.getmanagerLoanAuditLoanLinkFied().getText().equals(Loan_Title)){
				ManagerPages.getmanagerLoanAuditLoanLinkFied().click();
			}else{
				logger.log("Not Find "+Loan_Title+"Data" );
				this.driver.close();
			}

		} catch (Exception e) {
			logger.log("   search    exception ");
			e.printStackTrace(System.err);
//			this.driver.close();
		}
		Thread.sleep(1000);

		// 新打开的浏览器处理
		String currentWindow = this.driver.getWindowHandle();// 获取当前窗口句柄
		Set<String> handles = this.driver.getWindowHandles();// 获取所有窗口句柄
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {
			if (currentWindow == it.next()) {
				continue;
			}
			WebDriver window = this.driver.switchTo().window(it.next());// 切换到新窗口
			logger.log("New URL is :" + window.getCurrentUrl()
					+ ",  New getTitle is : " + window.getTitle());

			// 详情页面下拉滚动条
			String setscroll = "document.documentElement.scrollTop=" + 260;
			JavascriptExecutor jse = (JavascriptExecutor) this.driver;
			jse.executeScript(setscroll);

			//审核之前截图
			com.fengjr.util.LibSnapshot.snapshot((TakesScreenshot) this.driver,
			"PlatformManagerAddLoanTitle"+Loan_Title+".png");
			
			//立即发布
			ManagerPages.getmanagerLoanAuditLoanPublishNowcheckboxFied().click();
			
			// 点击批准审核 按钮
			ManagerPages.getmanagerLoanAuditLoanConfirmBtnFied().click();
			Thread.sleep(2000);
			flag_LoanAudit = 1;
//			logger.log("flag_LoanAdd Action is :" +flag_LoanAudit);
			window.close();// 关闭新窗口
		}
		this.driver.switchTo().window(currentWindow);// 回到原来页面
		Thread.sleep(1000);
		// this.driver.quit();

		logger.log(" ---------------------------------- Manager ShenhHe Loan   End ---------------------------------- ");

		logger.log(" ---------------------------------- Manager Schedule Loan   Start ---------------------------------- ");
		this.driver.navigate().refresh();
		Thread.sleep(1000);
//		ManagerPages.getmanagerMainListLoanManageMenuFied().click();
//		ManagerPages.getmanagerLoanScheduleLoanBtnFied().click();
		this.driver.quit();

		logger.log(" ---------------------------------- Manager Schedule Loan   End ---------------------------------- ");

		logger.log(" ---------------------------------- Manager Add Action Result    ---------------------------------- ");
		if( (flag_LoanAudit == 1) ){
			return true;
		}else{
			return false;
		}
	}
}
