package com.fengjr.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 管理员登录后台
 * @param
 * 页面元素抽取
 * @author alvin
 * @version 1.0   
 * @since JDK 1.8
 */

public class UserPage extends BasePage {
	
	private WebElement userName=null;
	private WebElement passWord=null;
	
	public UserPage(WebDriver driver) {
		super(driver);
//		this.url = url;
//		this.goTo();
	}
	/*********************** 登录页面 *****************************************/
	
	By usernameLocator = By.xpath("/html/body/div/div/div[2]/form/div[1]/div/div[1]/input");
	By passwordLocator = By.xpath("/html/body/div/div/div[2]/form/div[2]/div/div[1]/input");
	By loginButtonLocator_admin = By.xpath("(//input[@value='登录'])[2]");
	
	
	/*********************** 增加loan页面 *****************************************/

	// 快捷增加按钮
	By shortCutAddBution = By.xpath("/html/body/div[3]/div[2]/div[1]/div[1]/a[2]/i");
	
	// 个人标的
	By managerPersonLoanBution = By.xpath("/html/body/div/div[1]/div/div[1]/button");
	
	// 企业标的
	By managerQiyeLoanBution = By.xpath("/html/body/div/div[1]/div/div[2]/button");
	
    // 标的借款人
	By managerLoanButionName =  By.name("regedUserLoginName");
	
	// 点击个人标的的查询按钮
	By managerLoanSearchBution = By.id("queryUserBtn");
	
	// 下一步
	By managerLoanNextBution = By.id("useOldUser");
	
	// 借款界面的借款唯一号
	By managerLoanUniqueCodeText = By.id("uniqueCode");
	
	// 担保公司
	By managerLoanCorporationList = By.id("corporation");
	
	// 担保公司保障方式
	By managerLoanCorporationTypeList = By.id("guarantyStyle");

	// 产品类型
	By managerLoanTypeList = By.id("product");

	// 产品标题
	By managerLoantitleText = By.id("title");
	
	// 产品金额
	By managerLoanAmountText = By.id("amount");
	
	// 产品最小金额
	By managerLoanMinAmountText = By.id("minAmount");
	
	// 产品最大金额
	By managerLoanMaxAmountText = By.id("maxAmount");
	
	// 产品增量金额
	By managerLoanMaxStepAmountText = By.id("stepAmount");
	
	// 产品期限
	By managerLoanMonthsText = By.id("months");
	
	//产品利率
	By managerLoanRateText = By.id("annualRate");
	
	//抵押品
	By managerLoanOptionsRadios2Text = By.id("optionsRadios2");
	
   //还款方式 
	By managerLoanPaymentMethodText = By.xpath("(//input[@name='paymentMethod'])[2]");
	
	//正常可转让
	By managerLoanAssignableSelect= By.name("assignable");
	
	//有罚息
	By managerLoanPrepayment_penaltyText = By.id("prepayment_penalty");
	
	//下一步
	By managerLoanNextStepBution = By.id("gotoStep3");
	
//	//有罚息
//	By managerLoanPrepayment_penaltyText = By.id("prepayment_penalty");
	
	//借款说明
	By managerLoanDescriptionText = By.id("description");
	
	//借款风险
	By managerLoanRiskInfoText = By.name("riskInfo");

	//提交按钮
	By managerLoanSubBution = By.id("submitForm");

	
	
	
	/*********************** Edit 调整页面 *****************************************/
	// Edit 页面的标的搜索框
	By managerLoanEditSearchText = By.xpath("/html/body/div[3]/div[3]/div[2]/div/div[1]/div[2]/div/label/input");
	
	// Edit 页面搜索之后的借款点击
	By managerLoanEditAutoSearch = By.xpath("/html/body/div[3]/div[3]/div[2]/div/table/tbody/tr/td[1]/a");

	//担保费率
	By managerLoanEdiGuaranteeFeeText = By.id("loanGuaranteeFee");
	
	//服务费率
	By managerLoanEditServiceFeeText = By.id("loanServiceFee");
	
	//区域位置
	By managerLoanEditLocationText = By.id("location");
	
	
    // 借款唯一号
	By managerLoanEditUniqueCodeText = By.id("serial");
	
	//保存修改
	By managerLoanEditsaveLoanBtn = By.id("saveLoanBtn");
	
	//保存确认
	By managerLoanEditsaveLoanConfirmBtn = By.xpath("/html/body/div[3]/div[6]/div[2]/div/div/div/button");

	
	/*********************** 左侧导航 *****************************************/
	//  贷款管理
	By managerMainListLoanManage = By.xpath("/html/body/div[3]/div[2]/ul/li[4]/a/span");

	//  借款申请列表
	By managerMainListRequestBtn = By.xpath("/html/body/div[3]/div[2]/ul/li[4]/ul/li[1]/a");
	
	
	/*********************** 审核页面 *****************************************/
	// 审核借款列表页面的标的搜索框
	By managerLoanAuditSearchText = By.xpath("/html/body/div[3]/div[3]/div[2]/div/div/div[2]/div[1]/div[2]/div/label/input");
	
	//点击产品链接
	By managerLoanAuditLoanLink = By.xpath("/html/body/div[3]/div[3]/div[2]/div/div/div[2]/table/tbody/tr/td[1]/a");

	// 批准审核
	By managerLoanAuditLoanConfirmBtn = By.xpath("/html/body/div[3]/div[3]/div[2]/div[2]/div[6]/div[3]/div/div/div[2]/form/div/div/button[1]");


	// 批准审核
	By managerLoanAuditLoanPublishNowcheckbox = By.name("publishNow");

	
	
	/***********************发布调度页面 *****************************************/

	// 借款标批量发布调度
	By managerLoanScheduleLoanBtn = By.xpath("/html/body/div[3]/div[2]/ul/li[4]/ul/li[6]/a");
	
		

	/****************************登录 *************************************/
	
	public WebElement getUserTextField() {
		return this.driver.findElement(usernameLocator);
	}
	
	

	public WebElement getPassWordField() {
		return this.driver.findElement(passwordLocator);
	}
	public WebElement getSubmitButton() {
		return this.driver.findElement(loginButtonLocator_admin);
	}
	
	/****************************增加Loand *************************************/
	public WebElement getshortCutAddButionField() {
		return this.driver.findElement(shortCutAddBution);
	}
	
	public WebElement getmanagerPersonLoanButionField() {
		return this.driver.findElement(managerPersonLoanBution);
	}
	
	
	public WebElement getmanagerQiyeLoanButionField() {
		return this.driver.findElement(managerQiyeLoanBution);
	}

	public WebElement getmanagerLoanButionNameTextField() {
		return this.driver.findElement(managerLoanButionName);
	}

	public WebElement getmanagerLoanSearchButionField() {
		return this.driver.findElement(managerLoanSearchBution);
	}
	
	public WebElement getmanagerLoanNextButionField() {
		return this.driver.findElement(managerLoanNextBution);
	}
	
	public WebElement getmanagerLoanUniqueCodeTextField() {
		return this.driver.findElement(managerLoanUniqueCodeText);
	}
	
	public WebElement getmanagerLoanCorporationListField() {
		return this.driver.findElement(managerLoanCorporationList);
	}
	
	public WebElement getmanagerLoanTypeListField() {
		return this.driver.findElement(managerLoanTypeList);
	}
	
	
	public WebElement getmanagerLoantitleTextField() {
		return this.driver.findElement(managerLoantitleText);
	}
	
	// 企业借款
	public WebElement getmanagerLoanCorporationTypeListField() {
		return this.driver.findElement(managerLoanCorporationTypeList);
	}
	
	
//	// 借款合同
//	public WebElement getmanagerLoanCorporationTypeListField() {
//		return this.driver.findElement(managerLoanCorporationTypeList);
//	}
//	
//	
//	// 借款居间
//	public WebElement getmanagerLoanCorporationTypeListField() {
//		return this.driver.findElement(managerLoanCorporationTypeList);
//	}
//	
	
	
	public WebElement getmanagerLoanAmountTextField() {
		return this.driver.findElement(managerLoanAmountText);
	}
	
	
	public WebElement getmanagerLoanMinAmountTextField() {
		return this.driver.findElement(managerLoanMinAmountText);
	}
	
	
	public WebElement getmanagerLoanMaxAmountTextField() {
		return this.driver.findElement(managerLoanMaxAmountText);
	}
	
	public WebElement getmanagerLoanMaxStepAmountTextFieldField() {
		return this.driver.findElement(managerLoanMaxStepAmountText);
	}
	
	public WebElement getmanagerLoanMonthsTextField() {
		return this.driver.findElement(managerLoanMonthsText);
	}
	
	public WebElement getmanagerLoanRateTextField() {
		return this.driver.findElement(managerLoanRateText);
	}
	
	public WebElement getmanagerLoanOptionsRadios2TextField() {
		return this.driver.findElement(managerLoanOptionsRadios2Text);
	}
	
	public WebElement getmanagerLoanPaymentMethodTextField() {
		return this.driver.findElement(managerLoanPaymentMethodText);
	}
	
	public WebElement getmanageroanAssignableSelectField() {
		return this.driver.findElement(managerLoanAssignableSelect);
	}
	
	public WebElement getmanagerLoanPrepayment_penaltyTextField() {
		return this.driver.findElement(managerLoanPrepayment_penaltyText);
	}
	
	public WebElement getmanagerLoanDescriptionTextField() {
		return this.driver.findElement(managerLoanDescriptionText);
	}
	
	public WebElement getmanagerLoanRiskInfoTextField() {
		return this.driver.findElement(managerLoanRiskInfoText);
	}
	
	public WebElement getmanagerLoanNextStepButionField() {
		return this.driver.findElement(managerLoanNextStepBution);
	}
	
	public WebElement getmanagerLoanSubButionField() {
		return this.driver.findElement(managerLoanSubBution);
	}	

	/****************************搜索       编辑*************************************/

	public WebElement getmanagerLoanEditSearchTextFied(){
		return this.driver.findElement(managerLoanEditSearchText);
	}
	
	public WebElement getmanagerLoanEditAutoSearchActionFied(){
		return this.driver.findElement(managerLoanEditAutoSearch);
	}
	
	public WebElement getmanagerLoanEditsaveLoanConfirmBtnFied(){
		return this.driver.findElement(managerLoanEditsaveLoanConfirmBtn);
	}
	
	public WebElement getmanagerLoanEdiGuaranteeFeeTextFied(){
		return this.driver.findElement(managerLoanEdiGuaranteeFeeText);
	}
	
	
	public WebElement getmanagerLoanEditServiceFeeTextFied(){
		return this.driver.findElement(managerLoanEditServiceFeeText);
	}
		
	public WebElement getmanagerLoanEditLocationTextFied(){
		return this.driver.findElement(managerLoanEditLocationText);
	}
	
	public WebElement getmanagerLoanEditsaveLoanButionFied(){
		return this.driver.findElement(managerLoanEditsaveLoanBtn);
	}
	
	public WebElement getmanagerLoanEditUniqueCodeTextFied(){
		return this.driver.findElement(managerLoanEditUniqueCodeText);
	}
	
	public WebElement getmanagerMainListLoanManageMenuFied(){
		return this.driver.findElement(managerMainListLoanManage);
	}
	
	public WebElement getmanagerMainListRequestBtnFied(){
		return this.driver.findElement(managerMainListRequestBtn);
	}
	
	
	/****************************审核*************************************/
	
	/*************借款申请列表的搜索输入框****************************/
	public WebElement getmanagerLoanAuditSearchTextFied(){
		return this.driver.findElement(managerLoanAuditSearchText);
	}
	
	public WebElement getmanagerLoanAuditLoanLinkFied(){
		return this.driver.findElement(managerLoanAuditLoanLink);
	}
	
	public WebElement getmanagerLoanAuditLoanConfirmBtnFied(){
		return this.driver.findElement(managerLoanAuditLoanConfirmBtn);
	}
	
	public WebElement getmanagerLoanAuditLoanPublishNowcheckboxFied(){
		return this.driver.findElement(managerLoanAuditLoanPublishNowcheckbox);
	}
	
	public WebElement getmanagerLoanScheduleLoanBtnFied(){
		return this.driver.findElement(managerLoanScheduleLoanBtn);
	}
	
	
}


