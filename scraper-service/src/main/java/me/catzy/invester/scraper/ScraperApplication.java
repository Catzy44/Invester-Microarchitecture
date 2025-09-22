package me.catzy.invester.scraper;

import java.io.File;
import java.io.FileWriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
@EnableScheduling
//@EnableCaching
public class ScraperApplication {
	public static final boolean SMS_CACHE_PRELOAD_DISABLED = false;

	public static void main(String[] args) {
		try {
			long pid = ProcessHandle.current().pid();
			File pidF = new File("proc.pid");
			FileWriter fw = new FileWriter(pidF);
			fw.write("" + pid);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication application = new SpringApplication(new Class[] { ScraperApplication.class });
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}
}
