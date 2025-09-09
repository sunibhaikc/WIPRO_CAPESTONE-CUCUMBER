package StepDefinitions;

/**
 * Minimal shared test context for small suites.
 * Stores baseUrl + registered credentials across scenarios.
 *
 * (For larger suites use dependency injection like PicoContainer).
 */
public final class TestContext {
    private static String baseUrl = "https://westfloridaahec.org";
    private static String regUsername;
    private static String regEmail;
    private static String regPassword;

    private TestContext() {}

    public static void setBaseUrl(String url) { if (url != null && !url.trim().isEmpty()) baseUrl = url.trim(); }
    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setRegCredentials(String username, String email, String password) {
        regUsername = username;
        regEmail = email;
        regPassword = password;
    }
    public static String getRegUsername() {
        return regUsername;
    }
    public static String getRegEmail() {
        return regEmail;
    }
    public static String getRegPassword() {
        return regPassword;
    }
}
