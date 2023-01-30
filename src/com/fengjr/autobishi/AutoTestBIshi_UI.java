package com.fengjr.autobishi;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.fengjr.util.LibLogger;
/**
 * @author alvin
 * @func   p2p investment 
 * @throws InterruptedException
 * 使用maven或ANT相关的pom.xml + TestNG +  selenium + JENKINS 进行集成
 */
@Test
public class AutoTestBIshi_UI {
    public static WebDriver dr;  
	private String baseUrl;
	//login
	@FindBy(id="userName")
	private WebElement name;
	
	@FindBy(id="password")
	private WebElement pass;
	
	@FindBy(id="loginBt")
	private WebElement submit;
	
	@FindBy(id="investAmountInput")
	private WebElement amountText;
	
	private static final LibLogger logger = LibLogger.getLogger(AutoTestBIshi_UI.class);

	public AutoTestBIshi_UI(){
		dr = new FirefoxDriver();
		baseUrl = "https://beta.fengjr.com/";
		dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		dr.manage().timeouts().pageLoadTimeout(65, TimeUnit.SECONDS);
		dr.manage().window().maximize();
		PageFactory.initElements(dr, this);
	}
	
	public void locatLoan(){
		
		dr.get(baseUrl);
		
        String setscroll = "document.documentElement.scrollTop=" + 180;
        JavascriptExecutor jse=(JavascriptExecutor) dr;
        jse.executeScript(setscroll);
  
        WebElement loan_link = dr.findElement(By.linkText("凤溢盈-YHGJ-20150629-803"));
        loan_link.clear();
	}
	
	public void login() throws InterruptedException{
		//详情页的立即登录操作
		WebElement btn_loanDetailLogin = dr.findElement(By.xpath("//a[.='立即登录']"));
		name.clear();
		name.sendKeys("alvin");
		pass.clear();
		pass.sendKeys("123456");
		submit.click();
		Thread.sleep(2000);    
	}
	public void buy() throws InterruptedException{
		amountText.sendKeys("100");
		
		//等待手工输入验证码 或 开发程序写死验证码值
	    Thread.sleep(5000);
	    
		WebElement btn_loanDetailBuy = dr.findElement(By.xpath("//a[.='购买']"));
		btn_loanDetailBuy.clear();
	
		String currentWindow = dr.getWindowHandle();

		Set<String> handles = dr.getWindowHandles();

		Iterator<String> it = handles.iterator();
		while(it.hasNext()){
		String handle = it.next();
		    if(currentWindow.equals(handle)) continue;
		           WebDriver window = dr.switchTo().window(handle);
		   		String expectedTitle ="购买成功";
		   		String actualTitle = dr.getTitle();
		   		Assert.assertEquals(actualTitle,expectedTitle);
		   	    logger.log("Text is:"+dr.getTitle());
		    }
	}	
	
	public void tearDown(){
        dr.quit();
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		
		AutoTestBIshi_UI autoTest = new AutoTestBIshi_UI();
		autoTest.locatLoan();
		autoTest.login();
		autoTest.buy();
		}
}