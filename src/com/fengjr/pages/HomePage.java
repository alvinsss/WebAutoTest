package com.fengjr.pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver, String url) {
		super(driver);
		this.url = url;
		this.goTo();
	}
}
