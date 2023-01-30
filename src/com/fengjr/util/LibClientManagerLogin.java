package com.fengjr.util;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class LibClientManagerLogin {
	WebDriver driver;
	private static String ManagerURL = null;
	private static final LibLogger logger = LibLogger
	.getLogger(LibClientManagerLogin.class);

	public LibClientManagerLogin(WebDriver driver) {
		this.driver = driver;
	}

	public void login(String userName, String password)throws InterruptedException {
		Properties testdataconfig = LibDataConfig.getInstance().getProperties();
		if (ManagerURL == null) {
			ManagerURL = testdataconfig.getProperty("testURL.Manager");
			logger.log("ManagerURL is " + ManagerURL);
		}
		this.driver.get(ManagerURL);
		Thread.sleep(5000);

		try {
			Select selregType = new Select(this.driver.findElement(By.id("userType")));
			selregType.selectByValue("区域经理");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.out.println(" selregType catch exception ");
		}
		this.driver.findElement(By.name("userid")).sendKeys(userName);
		this.driver.findElement(By.name("password")).sendKeys(password);
		this.driver.findElement(By.xpath("//input[@type='submit']")).click();
	}
}
