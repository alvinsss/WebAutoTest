package com.fengjr.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


/**
 * @param args null
 */

public class ManagerLoginPage extends BasePage {
	public ManagerLoginPage(WebDriver driver , String url) {
		super(driver);
		this.url = url;
		this.goTo();
	}
// login Page
//	By usernameLocator = By.name("loginName");
//	By passwordLocator = By.name("password");
//	By loginButtonLocator_admin = By.xpath("//input[@type='submit']");

	By usernameLocator = By.xpath("/html/body/div/div/div[2]/form/div[1]/div/div[1]/input");
	By passwordLocator = By.xpath("/html/body/div/div/div[2]/form/div[2]/div/div[1]/input");
	By loginButtonLocator_admin = By.xpath("(//input[@value='登录'])[2]");
	
	public WebElement getUserTextField() {
		return this.driver.findElement(usernameLocator);
	}

	public WebElement getPassWordField() {
		return this.driver.findElement(passwordLocator);
	}
	public WebElement getSubmitButton() {
		return this.driver.findElement(loginButtonLocator_admin);
	}
	
}
