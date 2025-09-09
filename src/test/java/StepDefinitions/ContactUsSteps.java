package StepDefinitions;

import pages.ContactUsPage;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import Hooks.Hooks;

public class ContactUsSteps {

    WebDriver driver;
    ContactUsPage contactUsPage;

    @Given("the user opens the Contact Us page")
    public void the_user_opens_the_contact_us_page() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://westfloridaahec.org/contact-us/");
        contactUsPage = new ContactUsPage(driver);
        System.out.println("Opened Contact Us page ✅");
    }

    @Then("the contact section should contain {string}")
    public void the_contact_section_should_contain(String expectedText) {
        String actualText = contactUsPage.getContactHeaderText();
        if (actualText.contains(expectedText)) {
            System.out.println("Contact section contains expected text ✅");
        } else {
            System.out.println("Contact section does NOT contain expected text ");
        }
        Assert.assertTrue(actualText.contains(expectedText));
    }

    @Then("the address icon must be visible")
    public void the_address_icon_must_be_visible() {
        if (contactUsPage.isAddressIconVisible()) {
            System.out.println("Address icon is visible");
        } else {
            System.out.println("Address icon is NOT visible ");
        }
        Assert.assertTrue(contactUsPage.isAddressIconVisible());
    }

    @Then("the call us icon must be visible")
    public void the_call_us_icon_must_be_visible() {
        if (contactUsPage.isCallIconVisible()) {
            System.out.println("Call Us icon is visible ");
        } else {
            System.out.println("Call Us icon is NOT visible");
        }
        Assert.assertTrue(contactUsPage.isCallIconVisible());
    }

    @Then("the fax icon must be visible")
    public void the_fax_icon_must_be_visible() {
        if (contactUsPage.isFaxIconVisible()) {
            System.out.println("Fax icon is visible ✅");
        } else {
            System.out.println("Fax icon is NOT visible");
        }
        Assert.assertTrue(contactUsPage.isFaxIconVisible());
    }

    @Then("the email icon must be visible")
    public void the_email_icon_must_be_visible() {
        if (contactUsPage.isEmailIconVisible()) {
            System.out.println("Email icon is visible");
        } else {
            System.out.println("Email icon is NOT visible");
        }
        Assert.assertTrue(contactUsPage.isEmailIconVisible());
    }

    @Then("the Google Maps iframe should be visible")
    public void the_google_maps_iframe_should_be_visible() {
        if (contactUsPage.isGoogleMapVisible()) {
            System.out.println("Google Maps iframe is visible");
        } else {
            System.out.println("Google Maps iframe is NOT visible");
        }
        Assert.assertTrue(contactUsPage.isGoogleMapVisible());

        System.out.println("All Contact Us validations successfully done");
        driver.quit();
    }
}
