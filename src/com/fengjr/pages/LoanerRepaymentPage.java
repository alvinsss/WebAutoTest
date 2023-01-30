package com.fengjr.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoanerRepaymentPage extends BasePage{

	public LoanerRepaymentPage(WebDriver driver) {
		super(driver);
	}
	
	By goPayBtn = By.id("goPay");
	By fir = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/div[@class='user-block']/div[@id='repay-normal']/table[@id='repay-normal-table']/tbody[1]/tr[1]/td[3]");
	By sec = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/div[@class='user-block']/div[@id='repay-normal']/table[@id='repay-normal-table']/tbody[1]/tr[1]/td[4]");
	
	
	public WebElement goPayBtn(){
		return this.driver.findElement(goPayBtn);
	}
	
	public WebElement fir(){
		return this.driver.findElement(fir);
	}
	
	public WebElement sec(){
		return this.driver.findElement(sec);
	}
}
