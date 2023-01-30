package com.fengjr.testcase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.thoughtworks.selenium.Selenium;

public class submitReqLoanTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-mmss");
		String dateNowStr = sdf.format(d);
		String baseUrl = "http://retump.fengjr.inc:8088/admin/";
		WebDriver driver = new FirefoxDriver();
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\lichun.wu\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");   
//		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		try {
			Selenium selenium = new WebDriverBackedSelenium(driver, baseUrl);

			driver.get(baseUrl + "login");
			driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div[1]/div/div[1]/input")).clear();
			driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div[1]/div/div[1]/input")).sendKeys("admin");
			driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div[2]/div/div[1]/input")).clear();
			driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div[2]/div/div[1]/input")).sendKeys("1qaz@WSX_0525");
			driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div[3]/div/div[1]/input")).clear();
			// driver.findElement(By.cssSelector("div.control-group.success > div.controls.controls-row > div.input-prepend.input-append > input[name=\"captcha\"]")).sendKeys("my6ew");

			Thread.sleep(12000); // 输入验证码
			driver.findElement(By.xpath("(//input[@value='登录'])[2]")).click();
			selenium.waitForPageToLoad("60000");
			driver.get(baseUrl+"loan/request/list");//打开借款申请页面
			selenium.waitForPageToLoad("60000");
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("/html/body/div[3]/div[3]/div[2]/div/div/div[2]/div[1]/div[2]/div/label/input")).sendKeys("凤保宝-SDZDB-20150617-001");//搜索框
			selenium.waitForPageToLoad("60000");
			
			driver.findElement(By.xpath("/html/body/div[3]/div[3]/div[2]/div/div/div[2]/table/tbody/tr/td[1]/a")).click();//点击标题
			selenium.waitForPageToLoad("60000");
			Thread.sleep(9000);
			// 新打开的浏览器处理
			String currentWindow = driver.getWindowHandle();// 获取当前窗口句柄
			Set<String> handles = driver.getWindowHandles();// 获取所有窗口句柄
			Iterator<String> it = handles.iterator();
			while (it.hasNext()) {
				if (currentWindow == it.next()) {
					continue;
				}
			System.out.println(driver.getCurrentUrl());
			WebDriver window =driver.switchTo().window(it.next());// 切换到新窗口


			// 详情页面下拉滚动条
			String setscroll = "document.documentElement.scrollTop=" + 160;
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript(setscroll);
	        
			driver.findElement(By.id("submitReqBtn")).click();// 批准申请
			}
			Thread.sleep(3000);
			driver.close();
			driver.quit();
			
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
			driver.quit();
		}
	}
}
