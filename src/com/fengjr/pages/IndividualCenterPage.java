package com.fengjr.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IndividualCenterPage extends BasePage {

	public IndividualCenterPage(WebDriver driver) {
		super(driver);
	}
	
	By interestSumInt = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/ul[@class='user-index-count row']/li[1]/a[1]/h2[1]/strong");
	By interestSmallNumber = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/ul[@class='user-index-count row']/li[1]/a[1]/h2[1]/small");
	By availableFundsInt = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/ul[@class='user-index-count row']/li[2]/a[1]/h2[1]/strong");
	By availableFundsSmallNumber = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/ul[@class='user-index-count row']/li[2]/a[1]/h2[1]/small");
	By investSumInt = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/ul[1]/li[3]/a[1]/h2[1]/strong");
	By investSumSmallNumber = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/ul[1]/li[3]/a[1]/h2[1]/small");
	By reservationMoneySumInt = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/ul[1]/li[4]/a[1]/h2[1]/strong");
	By reservationMoneySumSmallNumber = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/ul[1]/li[4]/a[1]/h2[1]/small");
	By freezingFundInt = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/div[@class='row  income-charts-container']/div[@class='col-sm-12 col-md-6 income-charts-wrap']/div[@class='income-charts']/div[3]/p[2]/span[1]/strong");
	By freezingFundSmallNumber = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/div[@class='row  income-charts-container']/div[@class='col-sm-12 col-md-6 income-charts-wrap']/div[@class='income-charts']/div[3]/p[2]/span[1]/small");
	By assetsInt = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/div[@class='row  income-charts-container']/div[@class='col-sm-12 col-md-6 income-charts-wrap']/div[@class='income-charts']/div[3]/p[1]/span[2]/strong");
	By assetsSmallNumber = By.xpath("//div[@class='user-center']/div[@class='user-center-container']/div[@class='user-center-right']/div[@class='row  income-charts-container']/div[@class='col-sm-12 col-md-6 income-charts-wrap']/div[@class='income-charts']/div[3]/p[1]/span[2]/small");
	
	public WebElement interestSumInt(){
		return driver.findElement(interestSumInt);
	}
	
	public WebElement interestSmallNumber(){
		return driver.findElement(interestSmallNumber);
	}
	
	public WebElement availableFundsInt(){
		return driver.findElement(availableFundsInt);
	}
	
	public WebElement availableFundsSmallNumber(){
		return driver.findElement(availableFundsSmallNumber);
	}
	
	public WebElement investSumInt(){
		return driver.findElement(investSumInt);
	}
	
	public WebElement investSumSmallNumber(){
		return driver.findElement(investSumSmallNumber);
	}
	
	public WebElement reservationMoneySumInt(){
		return driver.findElement(reservationMoneySumInt);
	}
	
	public WebElement reservationMoneySumSmallNumber(){
		return driver.findElement(reservationMoneySumSmallNumber);
	}
	
	public WebElement freezingFundInt(){
		return driver.findElement(freezingFundInt);
	}
	
	public WebElement freezingFundSmallNumber(){
		return driver.findElement(freezingFundSmallNumber);
	}
	
	public WebElement assetsInt(){
		return driver.findElement(assetsInt);
	}
	
	public WebElement assetsSmallNumber(){
		return driver.findElement(assetsSmallNumber);
	}
}
