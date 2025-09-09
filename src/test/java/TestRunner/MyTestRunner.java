package TestRunner;
 
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = {
        "src/test/resources/Features/contactUs.feature",
        "src/test/resources/Features/home.feature",
        "src/test/resources/Features/programs.feature"
    },
    glue = {"StepDefinitions", "Hooks"},   // put your step definitions + hooks package here
    plugin = {
        "pretty",
        "html:target/cucumber-report.html",
        "json:target/cucumber-report.json",
        "rerun:target/rerun.txt"
    },
    monochrome = true
)
public class MyTestRunner extends AbstractTestNGCucumberTests {
    // No code needed, execution handled by Cucumber + TestNG
}