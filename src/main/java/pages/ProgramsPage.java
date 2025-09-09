package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProgramsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ProgramsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Open homepage
    public void openHomePage() {
        driver.get("https://westfloridaahec.org/");
    }

    // Navigate to a specific program by submenu link
    public void navigateToProgram(String programName) {
        WebElement programsMenu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='menu-item-264']//span[@class='menu-text'][normalize-space()='PROGRAMS']"))
        );

        Actions actions = new Actions(driver);
        actions.moveToElement(programsMenu).perform();

        WebElement subMenuItem = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText(programName))
        );
        subMenuItem.click();

        switchToNewWindowIfOpened();
    }

    // Handle new tab/window if opened
    private void switchToNewWindowIfOpened() {
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    // Validation
    public boolean isProgramContentDisplayed(String expectedText) {
        return driver.getPageSource().toLowerCase().contains(expectedText.toLowerCase());
    }
}
