package Hooks;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
 
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverFactory;

public class Hooks {
	// Inject WebDriver from DriverFactory
		 @Before
		 public void setUp() {
			 DriverFactory.getDriver();
			 System.out.println("Browser launched before scenario");
		 }
		 @After
		 public void tearDown(Scenario scenario) {
			 WebDriver driver = DriverFactory.getDriver();
			 if(scenario.isFailed()) {
				  byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		          scenario.attach(screenshot, "image/png", "Failed Screenshot"); 
			 }
			 DriverFactory.quitDriver();
			 System.out.println("Browser closed after the scenario");



		 }


}
