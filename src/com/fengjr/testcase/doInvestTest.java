package com.fengjr.testcase;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.thoughtworks.selenium.Selenium;

public class doInvestTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-mmss");
		String dateNowStr = sdf.format(d);
		String baseUrl = "http://retump2.fengjr.inc:8088/admin";
		WebDriver driver = new FirefoxDriver();
		try {

			// System.setProperty("webdriver.firefox.bin",
			// "c:/Program Files/Mozilla Firefox/firefox.exe");		

			Selenium selenium = new WebDriverBackedSelenium(driver, baseUrl);

//			driver.get(baseUrl + "login");
			driver.get(baseUrl);

			driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div[1]/div/div[1]/input")).clear();
			driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div[1]/div/div[1]/input")).sendKeys("admin");
			driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div[2]/div/div[1]/input")).clear();
			driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div[2]/div/div[1]/input")).sendKeys("1qaz@WSX_0525");
			driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div[3]/div/div[1]/input")).clear();
			// driver.findElement(By.cssSelector("div.control-group.success > div.controls.controls-row > div.input-prepend.input-append > input[name=\"captcha\"]")).sendKeys("my6ew");

			Thread.sleep(9000); // 输入验证码
			driver.findElement(By.xpath("(//input[@value='登录'])[2]")).click();
			selenium.waitForPageToLoad("60000");
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[1]/div[1]/a[2]/i")).click();
			selenium.waitForPageToLoad("60000");

			String[] handles = new String[driver.getWindowHandles().size()];
			driver.getWindowHandles().toArray(handles);
			Thread.sleep(3000);

			driver.switchTo().window(handles[1]);
			driver.findElement(
					By.xpath("/html/body/div/div[1]/div/div[1]/button")).click();// 点击个人发标
			// driver.switchTo().window(handles[1]);
			selenium.waitForPageToLoad("60000");
			driver.findElement(By.name("regedUserLoginName")).clear();// 输入发标人
			driver.findElement(By.name("regedUserLoginName")).sendKeys("psy");
			driver.findElement(By.id("queryUserBtn")).click();// 查询
			Thread.sleep(6000);
			driver.findElement(By.id("useOldUser")).click();// 下一步
			selenium.waitForPageToLoad("60000");
			driver.findElement(By.id("uniqueCode")).clear();
			driver.findElement(By.id("uniqueCode")).sendKeys(
					"瀚华担保：" + dateNowStr);
			new Select(driver.findElement(By.id("corporation")))
					.selectByVisibleText("瀚华担保有限责任公司");
			new Select(driver.findElement(By.id("guarantyStyle")))
					.selectByVisibleText("抵押");
			new Select(driver.findElement(By.id("product")))
					.selectByVisibleText("凤锐通");
			driver.findElement(By.id("title")).clear();
			driver.findElement(By.id("title")).sendKeys(
					"凤锐通-HHDB-" + dateNowStr);// 标题
			driver.findElement(By.id("amount")).clear();
			driver.findElement(By.id("amount")).sendKeys("1");// 筹款金额
			driver.findElement(By.id("minAmount")).clear();
			driver.findElement(By.id("minAmount")).sendKeys("1");// 最小
			driver.findElement(By.id("maxAmount")).clear();
			driver.findElement(By.id("maxAmount")).sendKeys("1");// 最大
			driver.findElement(By.id("stepAmount")).clear();
			driver.findElement(By.id("stepAmount")).sendKeys("1");// 增量
			driver.findElement(By.id("months")).clear();
			driver.findElement(By.id("months")).sendKeys("1");// 气象
			driver.findElement(By.id("annualRate")).clear();
			driver.findElement(By.id("annualRate")).sendKeys("7.0");// 利率
			driver.findElement(By.id("optionsRadios2")).click();// 抵押品
			driver.findElement(By.xpath("(//input[@name='paymentMethod'])[2]"))
					.click();// 还款方式
			new Select(driver.findElement(By.name("assignable")))
					.selectByVisibleText("正常可转让");
			new Select(driver.findElement(By.id("prepayment_penalty")))
					.selectByVisibleText("有罚息");
			driver.findElement(By.id("gotoStep3")).click();// 下一步
			selenium.waitForPageToLoad("60000");
			driver.findElement(By.id("description")).clear();
			driver.findElement(By.id("description")).sendKeys("借款说明");// 借款说明
			driver.findElement(By.id("submitForm")).click();// 提交
			String success = "借款申请成功！";
			if (driver.switchTo().alert().getText().equals(success)) {
				driver.switchTo().alert().accept();
			}
			Thread.sleep(3000);
			driver.close();
			driver.quit();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			driver.close();
			driver.quit();
		}
	}
}
