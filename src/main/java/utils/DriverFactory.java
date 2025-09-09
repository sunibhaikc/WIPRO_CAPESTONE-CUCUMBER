package utils;
 
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
 
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class DriverFactory {

	public  static WebDriver driver;

	public static WebDriver getDriver() {

		if (driver == null ) {

			ChromeOptions chromeOptions = new ChromeOptions();
 
			WebDriverManager.chromedriver().setup();
 
			driver = new ChromeDriver(chromeOptions);

	        driver.manage().window().maximize();

		}

		return driver;

	}

	public static void quitDriver() {


		if(driver != null) {


			driver.quit();

			driver = null;

		}

	}


 
}
