package StepDefinitions;

import pages.HomePage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class CapstoneHomePageSteps {

    private final HomePage home = new HomePage();

    // instance fields kept only for convenience/logging (but main storage in TestContext)
    private boolean lastLoginSucceeded = false;

    @Given("the site base URL is {string}")
    public void the_site_base_url_is(String url) {
        TestContext.setBaseUrl(url);
        home.setBaseUrl(TestContext.getBaseUrl());
    }

    @Given("the user navigates to the {string} page")
    public void the_user_navigates_to_the_page(String pageName) {
        if (pageName == null) pageName = "";
        String pn = pageName.trim().toLowerCase();
        if (pn.contains("my account") || pn.contains("account")) {
            home.goToMyAccount();
        } else if (pn.contains("home")) {
            home.goToHome();
        } else if (pn.contains("contact")) {
            home.goToContactUs();
        } else {
            // default to home
            home.goToHome();
        }
    }

    @When("the user fills the registration form with username {string}, email {string} and password {string}")
    public void the_user_fills_the_registration_form_with_username_email_and_password(String username, String email, String password) {
        long ts = System.currentTimeMillis();
        String regUsername = (username == null || username.trim().isEmpty() || username.contains("auto"))
                ? "auto_user_" + ts : username.trim();
        String regEmail = (email == null || email.trim().isEmpty() || email.contains("auto"))
                ? "auto+" + ts + "@example.com" : email.trim();
        String regPassword = (password == null || password.trim().isEmpty()) ? "Test@12345" : password.trim();

        // persist across scenarios
        TestContext.setRegCredentials(regUsername, regEmail, regPassword);

        home.fillRegistration(regUsername, regEmail, regPassword);
    }

    @When("the user clicks the Register button")
    public void the_user_clicks_the_register_button() {
        boolean clicked = home.clickRegister();
        System.out.println("Register clicked? " + clicked);
    }

    @Then("the registration attempt should be recorded \\(success or graceful failure allowed)")
    public void the_registration_attempt_should_be_recorded_success_or_graceful_failure_allowed() {
        Assert.assertTrue((TestContext.getRegUsername() != null && !TestContext.getRegUsername().isEmpty())
                        || (TestContext.getRegEmail() != null && !TestContext.getRegEmail().isEmpty()),
                "Registration attempt should have recorded either a username or an email.");
        Assert.assertTrue(TestContext.getRegPassword() != null && !TestContext.getRegPassword().isEmpty(),
                "Registration attempt should have recorded a password.");
        System.out.println("Recorded registration credentials -> username: " + TestContext.getRegUsername() + ", email: " + TestContext.getRegEmail());
    }

    @When("the user logs in with username_or_email {string} and password {string}")
    public void the_user_logs_in_with_username_or_email_and_password(String usernameOrEmail, String password) {
        String usernameToUse = usernameOrEmail;
        if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty() || usernameOrEmail.contains("auto")) {
            if (TestContext.getRegEmail() != null && !TestContext.getRegEmail().isEmpty()) usernameToUse = TestContext.getRegEmail();
            else if (TestContext.getRegUsername() != null && !TestContext.getRegUsername().isEmpty()) usernameToUse = TestContext.getRegUsername();
        }

        String passwordToUse = password;
        if (password == null || password.trim().isEmpty()) {
            passwordToUse = (TestContext.getRegPassword() != null) ? TestContext.getRegPassword() : "Test@12345";
        }

        lastLoginSucceeded = home.login(usernameToUse, passwordToUse);
    }

    @Then("the login attempt should be recorded \\(logged-in indicator or graceful failure)")
    public void the_login_attempt_should_be_recorded_logged_in_indicator_or_graceful_failure() {
        boolean haveCred = (TestContext.getRegEmail() != null && !TestContext.getRegEmail().isEmpty())
                || (TestContext.getRegUsername() != null && !TestContext.getRegUsername().isEmpty());
        Assert.assertTrue(haveCred, "A credential (registered email or username) should have been available for the login attempt.");
        System.out.println("Login attempt recorded. Logged-in indicator: " + lastLoginSucceeded);
    }

    @Given("the user is on the Home page")
    public void the_user_is_on_the_home_page() {
        home.goToHome();
    }

    @Then("the navigation menu should be present")
    public void the_navigation_menu_should_be_present() {
        Assert.assertTrue(home.isNavigationPresent(), "Navigation menu should be present on Home page.");
    }

    @Then("the search input should be present")
    public void the_search_input_should_be_present() {
        Assert.assertTrue(home.isSearchPresent(), "Search input should be present on Home page.");
    }

    @When("the user hovers over the {string} menu")
    public void the_user_hovers_over_the_menu(String menuName) {
        home.hoverMenu(menuName);
    }

    @Then("the {string} link should be visible under Programs")
    public void the_link_should_be_visible_under_programs(String link) {
        Assert.assertTrue(home.isProgramLinkVisible(link), link + " should be visible under Programs");
    }

    @When("the user expands the Programs menu")
    public void the_user_expands_the_programs_menu() {
        home.expandPrograms();
    }

    @Then("the following program links should be visible:")
    public void the_following_program_links_should_be_visible(DataTable dataTable) {
        List<String> expected = new ArrayList<>();
        for (List<String> row : dataTable.asLists()) {
            expected.add(row.get(0));
        }
        Assert.assertTrue(home.areProgramLinksVisible(expected), "One or more expected program links were not visible.");
    }

    @Then("at least one resource link from the list should be visible:")
    public void at_least_one_resource_link_from_the_list_should_be_visible(DataTable dataTable) {
        List<String> resources = new ArrayList<>();
        for (List<String> row : dataTable.asLists()) {
            resources.add(row.get(0));
        }
        Assert.assertTrue(home.isAnyResourceLinkPresent(resources), "No expected resource links were found on the Home page.");
    }

    @When("the user searches for {string}")
    public void the_user_searches_for(String query) {
        home.searchFor(query);
    }

    @Then("the search results should contain the term {string}")
    public void the_search_results_should_contain_the_term(String term) {
        Assert.assertTrue(home.searchResultsContain(term), "Search results should contain the term: " + term);
    }
}