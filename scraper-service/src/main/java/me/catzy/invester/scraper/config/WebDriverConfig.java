package me.catzy.invester.scraper.config;

import java.util.Collections;

import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebDriverConfig {
	@Value("${chrome.location}")
    private String chromeBinaryLocation;
	
	@SuppressWarnings("deprecation")
	@Bean 
	public ChromeOptions chromeOptions() {
		ChromeOptions options = new ChromeOptions();
	    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
	    options.setExperimentalOption("useAutomationExtension", false);
	    options.addArguments("--disable-blink-features=AutomationControlled");
	    options.setHeadless(false);
	    options.setBinary(chromeBinaryLocation);
	    String modernUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36";
	    options.addArguments("--user-agent=" + modernUserAgent);
	    return options;
	}
 }
