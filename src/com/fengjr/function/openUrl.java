package com.fengjr.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Xenu工具【下载链接：http://pan.baidu.com/s/1sjHqhY1 密码：64jb 】获取的全站URL，使用火狐浏览器打开并截图，测试域名分离
 * @param 
 * @author alvin
 * @version 1.0   
 * @since JDK 1.8
 */
public class openUrl {

	public static void main(String args[]) throws IOException,
			InterruptedException {
			File textUrlFile;
			
		try {
			// 指定源文件路径，Xenu工具扫描之后的URL文件，一个URL一行
			textUrlFile = new File("F://Workspaces//QA//FengJR_Fun//src//com//fengjr//testdata//url.txt");
			String strUrl = null;
			
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(textUrlFile)));
			WebDriver driver = new FirefoxDriver();
			// 登录
			driver.get("https://mybeta.fengjr.com/login?hint=undefined");
			driver.manage().window().maximize();
			driver.findElement(By.name("loginName")).clear();
			driver.findElement(By.name("loginName")).sendKeys("alvin");

			driver.findElement(By.id("input-password")).clear();
			driver.findElement(By.id("input-password")).sendKeys("psy025");
			
			//登录按钮
			driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/div/div[2]/form/div[3]/span")).click();
			Thread.sleep(3000);

			//增加一个name = "ccat",value="626eacc38d1d9723314c109a9d4530d1b3b7b775e782acf5cb0d9fa85ec910dd"的cookie
			Cookie cookie = new Cookie("ccat", "626eacc38d1d9723314c109a9d4530d1b3b7b775e782acf5cb0d9fa85ec910dd");
			Cookie cookie1 = new Cookie("Hm_lpvt_cca0837a014621d8d933a0b1b2cb0be5", "1437036515");
			Cookie cookie2 = new Cookie("Hm_lvt_cca0837a014621d8d933a0b1b2cb0be5", "1436346809,1436448287,1436756667,1437041689");

			//增加cookies到driver
			driver.manage().addCookie(cookie);
			driver.manage().addCookie(cookie1);
			driver.manage().addCookie(cookie2);

			//得到当前页面下所有的cookies,输出所在域 name value 有效日期和路径
			Set<Cookie> cookies = driver.manage().getCookies();
			System.out.println(String.format("Domain -> name -> value -> expiry -> path"));
			
			for(Cookie c : cookies)
				System.out.println(String.format("%s -> %s -> %s -> %s -> %s",
				c.getDomain(), c.getName(), c.getValue(),c.getExpiry(),c.getPath()));

			//删除cookie有三种方法
			
//			//第一种通过cookie的name
//			driver.manage().deleteCookieNamed("Hm_lpvt_cca0837a014621d8d933a0b1b2cb0be5");

//			//第二种通过Cookie对象 
//			driver.manage().deleteCookie(cookie2);

//			//第三种全部删除
//			driver.manage().deleteAllCookies();
			
			while ((strUrl = in.readLine()) != null) {
				driver.get(strUrl);
				driver.manage().window().maximize();
				Thread.sleep(1000);
				String getTitleStr = driver.getTitle();
				System.out.println("getTitleStr is " + getTitleStr);
//				System.out.println("herf is " + driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/div/div[2]/div[2]/p/a")).getText());
				// 截图，名称是当前打开的页面title
				com.fengjr.util.LibSnapshot.snapshot((TakesScreenshot) driver,getTitleStr + ".png");
				Thread.sleep(1000);
			}
			System.out.println("操作完成！");
			driver.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
