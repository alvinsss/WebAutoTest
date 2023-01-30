package com.fengjr.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InvestSubjectPage extends BasePage{

	public InvestSubjectPage(WebDriver driver) {
		super(driver);
	}
	
	By amountOfInvestText = By.id("investm");
	By investBtn = By.id("wjs-invest2");

	public WebElement amountOfInvestText(){
		return this.driver.findElement(amountOfInvestText);
	}
	
	public WebElement investBtn(){
		return this.driver.findElement(investBtn);
	}
}
