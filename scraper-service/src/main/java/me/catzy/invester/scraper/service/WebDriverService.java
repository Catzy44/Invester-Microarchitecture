package me.catzy.invester.scraper.service;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WebDriverService {
	private WebDriver driver;
	@Autowired ChromeOptions options;
	
	public WebDriver get() {
		if(driver == null) {
			driver = loadDriverB();
		}
		return driver;
	}
	
	public void close() {
		if(driver != null) {
			driver.close();
		}
		driver = null;
	}
	
	private WebDriver loadDriverB() {
		String driverPath = System.getProperty("user.dir") + File.separator + "undetected_chromedriver.exe";
	    System.setProperty("webdriver.chrome.driver", driverPath);
	    WebDriver driver = new ChromeDriver(options);
	    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
	    return driver;
	}
}
