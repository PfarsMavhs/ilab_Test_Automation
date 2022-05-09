package ilab.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class BrowserFactory {

	public static WebDriver launchWebBrowser(WebDriver driver, String browserName, String webURL)
	 {

		if (browserName.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", ".\\web_drivers\\geckodriver.exe");
			driver = new FirefoxDriver();

		} else if (browserName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", ".\\web_driver\\chromedriver.exe");
			driver = new ChromeDriver();
		} else {
			System.out.println("We did not cater for such browser");
		}

		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(webURL);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return driver;

	}

	public static void quitBrowser(WebDriver driver) {
		driver.quit();
	}

}
