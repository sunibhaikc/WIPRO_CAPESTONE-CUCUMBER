package StepDefinitions;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pages.ProgramsPage;

public class ProgramsSteps {

    WebDriver driver;
    ProgramsPage programsPage;

    // ===== First set =====
    @Given("user opens to homepage for programs")
    public void user_opens_to_homepage_for_programs() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        programsPage = new ProgramsPage(driver);
        programsPage.openHomePage();
    }

    @When("user navigate to {string}")
    public void user_navigate_to(String programName) {
        programsPage.navigateToProgram(programName);
    }

    @Then("{string} program content should be displayed")
    public void program_content_should_be_displayed(String expectedText) {
        Assert.assertTrue(
                programsPage.isProgramContentDisplayed(expectedText),
                "Program content not found for: " + expectedText
        );
        driver.quit();
    }

    // ===== Second set (hover + click explicitly) =====
    @Given("user opens the homepage for programs")
    public void user_opens_the_homepage_for_programs() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        programsPage = new ProgramsPage(driver);
        programsPage.openHomePage();
    }

    @When("user hovers on Programs and clicks {string}")
    public void user_hovers_on_programs_and_clicks(String programName) {
        programsPage.navigateToProgram(programName);
    }
}
