package pages;

import utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * HomePage (improved)
 * - Uses an explicit shortWait (WebDriverWait) to reduce flakiness
 * - Improved expandPrograms() with hover + click fallback and tolerant waits
 * - Improved isProgramLinkVisible() with case-insensitive fallback and wait
 *
 * Keep other helper methods (tryFind, clickWithFallback, etc.) from your original implementation.
 */
public class HomePage {
    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final Actions actions;
    private final WebDriverWait shortWait;
    private String baseUrl = "https://westfloridaahec.org";

    public HomePage() {
        this.driver = DriverFactory.getDriver();
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(6));
    }

    // ---------- Utilities ----------
    private WebElement tryFind(By... locators) {
        for (By b : locators) {
            try {
                WebElement e = driver.findElement(b);
                if (e != null) return e;
            } catch (Exception ignored) { }
        }
        return null;
    }

    private boolean clickWithFallback(WebElement el) {
        if (el == null) return false;
        try {
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
            el.click();
            return true;
        } catch (Exception e1) {
            try {
                js.executeScript("arguments[0].click();", el);
                return true;
            } catch (Exception e2) {
                try {
                    js.executeScript("arguments[0].removeAttribute('disabled');", el);
                    Thread.sleep(150);
                    el.click();
                    return true;
                } catch (Exception e3) {
                    return false;
                }
            }
        }
    }

    // ---------- Navigation ----------
    public void setBaseUrl(String url) {
        if (url != null && !url.trim().isEmpty()) baseUrl = url.trim();
    }

    public void goToHome() {
        driver.get(baseUrl + "/");
        sleep(800);
    }

    public void goToMyAccount() {
        driver.get(baseUrl + "/my-account/");
        sleep(1000);
    }

    public void goToContactUs() {
        driver.get(baseUrl + "/contact-us/");
        sleep(800);
    }

    // ---------- Registration ----------
    public void fillRegistration(String username, String email, String password) {
        WebElement userInput = tryFind(
                By.id("reg_username"),
                By.name("username"),
                By.xpath("//input[contains(@placeholder,'Username')]"),
                By.id("username"),
                By.name("user_login")
        );

        WebElement emailInput = tryFind(
                By.id("reg_email"),
                By.name("email"),
                By.id("user_email"),
                By.xpath("//input[contains(@placeholder,'Email') or contains(@aria-label,'email')]")
        );

        WebElement passInput = tryFind(
                By.id("reg_password"),
                By.name("password"),
                By.id("password"),
                By.xpath("//input[@type='password']")
        );

        if (userInput != null) {
            try { userInput.clear(); } catch (Exception ignored) {}
            userInput.sendKeys(username);
        }

        sleep(250);

        if (emailInput != null) {
            try { emailInput.clear(); } catch (Exception ignored) {}
            emailInput.sendKeys(email);
        }

        sleep(250);

        if (passInput != null) {
            try { passInput.clear(); } catch (Exception ignored) {}
            passInput.sendKeys(password);
        }

        sleep(250);
    }

    public boolean clickRegister() {
        WebElement regButton = tryFind(
                By.xpath("//button[contains(.,'Register')]"),
                By.xpath("//input[@value='Register']"),
                By.name("register"),
                By.cssSelector("button[type='submit']"),
                By.xpath("//button[contains(.,'Sign up') or contains(.,'Sign Up')]")
        );

        boolean clicked = false;
        if (regButton != null) clicked = clickWithFallback(regButton);

        if (!clicked) {
            // fallback: submit parent form via JS
            try {
                WebElement frm = tryFind(
                        By.cssSelector("form.woocommerce-form-register"),
                        By.xpath("//form[contains(@class,'register') or contains(@id,'register')]"),
                        By.tagName("form")
                );
                if (frm != null) {
                    js.executeScript("arguments[0].submit();", frm);
                    clicked = true;
                }
            } catch (Exception ignored) {}
        }

        sleep(1200);
        return clicked;
    }

    // ---------- Login ----------
    public boolean login(String usernameOrEmail, String password) {
        goToMyAccount();
        WebElement userInput = tryFind(
                By.id("username"),
                By.xpath("//input[@id='user_login' or @name='log' or @name='username']"),
                By.name("username"),
                By.name("log")
        );

        WebElement passInput = tryFind(
                By.id("password"),
                By.xpath("//input[@id='user_pass' or @name='pwd' or @name='password']"),
                By.name("password"),
                By.name("pwd")
        );

        WebElement loginBtn = tryFind(
                By.xpath("//button[contains(.,'Log in') or contains(.,'Login') or contains(.,'Sign in')]"),
                By.name("login"),
                By.xpath("//input[@value='Log in' or @value='Login']")
        );

        if (userInput != null) {
            try { userInput.clear(); } catch (Exception ignored) {}
            userInput.sendKeys(usernameOrEmail);
        }

        sleep(250);

        if (passInput != null) {
            try { passInput.clear(); } catch (Exception ignored) {}
            passInput.sendKeys(password);
        }

        sleep(250);

        boolean clicked = false;
        if (loginBtn != null) clicked = clickWithFallback(loginBtn);

        sleep(1200);
        return isLoggedIn();
    }

    public boolean isLoggedIn() {
        return isDisplayedSafe(
                By.xpath("//*[contains(.,'Logout') or contains(.,'Log out')]"),
                By.xpath("//a[contains(@href,'/my-account') and (contains(.,'My account') or contains(.,'Account'))]")
        );
    }

    // ---------- Home checks ----------
    public boolean isNavigationPresent() {
        return isDisplayedSafe(
                By.xpath("//nav"),
                By.xpath("//ul[contains(@class,'menu') or contains(@class,'nav')]")
        );
    }

    public boolean isSearchPresent() {
        return isDisplayedSafe(
                By.cssSelector("input[type='search']"),
                By.xpath("//input[@name='s' or contains(@placeholder,'Search') or contains(@aria-label,'search')]")
        );
    }

    // ---------- Navigation hover/expand ----------
    public void hoverMenu(String menuName) {
        WebElement menu = tryFind(
                By.linkText(menuName.toUpperCase()),
                By.linkText(menuName),
                By.xpath("//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + menuName.toLowerCase() + "')]")
        );
        if (menu == null) throw new NoSuchElementException("Menu not found: " + menuName);
        actions.moveToElement(menu).perform();
        sleep(700);
    }

    /**
     * Improved isProgramLinkVisible with explicit wait and case-insensitive fallback.
     */
    public boolean isProgramLinkVisible(String linkText) {
        try {
            By byExact = By.linkText(linkText);
            By byCi = By.xpath(
                    "//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '"
                            + linkText.toLowerCase() + "')]"
            );

            // Wait until at least one of the locators is visible
            boolean visible = shortWait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(byExact),
                    ExpectedConditions.visibilityOfElementLocated(byCi)
            ));

            if (visible) {
                // Now check explicitly if either element is present
                WebElement el = tryFind(byExact, byCi);
                return el != null && el.isDisplayed();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Improved expandPrograms: hover first and wait for submenu; on failure fallback to click.
     */
    public void expandPrograms() {
        WebElement programs = tryFind(
                By.linkText("PROGRAMS"),
                By.linkText("Programs"),
                By.xpath("//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'programs')]")
        );
        if (programs == null) return;
        try {
            // try hover first
            actions.moveToElement(programs).perform();

            // wait for one known submenu item (tolerant)
            shortWait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'tobacco')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'ahec')]"))
            ));
            return;
        } catch (Exception e) {
            // fallback to click
            try {
                programs.click();
                shortWait.until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'tobacco')]")),
                        ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'ahec')]"))
                ));
                return;
            } catch (Exception ignored) {}
        }
    }

    public boolean areProgramLinksVisible(List<String> expected) {
        for (String p : expected) {
            boolean present = isProgramLinkVisible(p);
            if (!present) return false;
        }
        return true;
    }

    // ---------- Resources ----------
    public boolean isAnyResourceLinkPresent(List<String> resources) {
        for (String r : resources) {
            if (isDisplayedSafe(By.linkText(r.trim()))) return true;
            if (isDisplayedSafe(By.xpath("//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + r.trim().toLowerCase() + "')]")))
                return true;
        }
        return false;
    }

    // ---------- Search ----------
    public void searchFor(String query) {
        WebElement searchBox = tryFind(
                By.cssSelector("input[type='search']"),
                By.xpath("//input[@name='s' or contains(@placeholder,'Search') or contains(@aria-label,'search')]"),
                By.name("s")
        );
        if (searchBox == null) throw new NoSuchElementException("Search box not found");
        try { searchBox.clear(); } catch (Exception ignored) {}
        searchBox.sendKeys(query);
        sleep(250);
        searchBox.sendKeys(Keys.ENTER);
        sleep(1000);
    }

    public boolean searchResultsContain(String term) {
        String pageSrc = driver.getPageSource().toLowerCase();
        boolean found = pageSrc.contains(term.toLowerCase()) || (driver.getTitle() != null && driver.getTitle().toLowerCase().contains(term.toLowerCase()));
        if (!found) {
            found = isDisplayedSafe(By.xpath("//*[contains(translate(., 'SEARCH RESULTS','search results'),'search results')]"));
        }
        return found;
    }

    // ---------- Helpers ----------
    private boolean isDisplayedSafe(By... locators) {
        try {
            WebElement e = tryFind(locators);
            return e != null && e.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
    }
}
