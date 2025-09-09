package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContactUsPage {

    WebDriver driver;

    // Constructor
    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By contactHeader = By.xpath("//*[@id=\"post-127\"]/div/div[1]/div/div/div/div[1]/h3");
    private By addressIcon = By.xpath("//*[@id=\"post-127\"]/div/div[1]/div/div/div/div[7]/div[1]/div/div[1]/i");
    private By callIcon = By.xpath("//*[@id='post-127']/div/div[1]/div/div/div/div[7]/div[2]/div/div[1]/i");
    private By faxIcon = By.xpath("//*[@id='post-127']/div/div[1]/div/div/div/div[7]/div[3]/div/div[1]/i");
    private By emailIcon = By.xpath("//*[@id='post-127']/div/div[1]/div/div/div/div[7]/div[4]/div/div[1]/i");
    private By googleMapFrame = By.tagName("iframe");

    // Page actions
    public String getContactHeaderText() {
        return driver.findElement(contactHeader).getText();
    }

    public boolean isAddressIconVisible() {
        return driver.findElement(addressIcon).isDisplayed();
    }

    public boolean isCallIconVisible() {
        return driver.findElement(callIcon).isDisplayed();
    }

    public boolean isFaxIconVisible() {
        return driver.findElement(faxIcon).isDisplayed();
    }

    public boolean isEmailIconVisible() {
        return driver.findElement(emailIcon).isDisplayed();
    }

    public boolean isGoogleMapVisible() {
        return driver.findElement(googleMapFrame).isDisplayed();
    }
}
