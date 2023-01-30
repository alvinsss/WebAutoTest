package com.fengjr.cookies;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;


public class DriverFactory {

    public static WebDriver create() {
        
        // TODO Auto-generated method stub
//        String chromdriver="E:\\chromedriver.exe";
//        System.setProperty("webdriver.chrome.driver", chromdriver);
        ChromeOptions options = new ChromeOptions();
     
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("firefox.switches",
                Arrays.asList("--start-maximized"));
        options.addArguments("--test-type", "--start-maximized");
        WebDriver driver=new ChromeDriver(options);
        return driver;
    }

}