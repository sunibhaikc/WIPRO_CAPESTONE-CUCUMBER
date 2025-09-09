# WIPRO_CAPESTONE-CUCUMBER

## Overview

**WIPRO_CAPESTONE-CUCUMBER** is a comprehensive automation testing framework built using [Cucumber](https://cucumber.io/) and [Selenium WebDriver](https://www.selenium.dev/). It is designed to demonstrate behavior-driven development (BDD) practices for web application testing as part of the Wipro Capstone project. This repository helps teams write human-readable test scenarios, automate them, and generate rich reports for test execution.

## Purpose

The main goal of this project is to:
- Showcase BDD automation using Cucumber and Selenium.
- Enable easy collaboration between technical and non-technical stakeholders through readable Gherkin scenarios.
- Provide a scalable structure for adding and maintaining automated UI tests.
- Serve as a template for real-world enterprise automation projects.

## Directory Structure

```
WIPRO_CAPESTONE-CUCUMBER/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── ...              # Application source code (if any)
│   └── test/
│       └── java/
│           ├── stepdefinitions/ # Cucumber Step Definitions
│           ├── runners/         # Test Runner files
│           └── pages/           # Page Object Models
├── features/
│   └── ...                      # Cucumber feature files (Gherkin syntax)
├── pom.xml                      # Maven configuration file
├── README.md                    # Project documentation
├── reports/                     # Test execution reports
└── resources/
    └── ...                      # Test data, configurations, etc.
```

- **src/main/java/**: Core application code (if relevant).
- **src/test/java/stepdefinitions/**: Implementations of steps defined in feature files.
- **src/test/java/runners/**: Runner classes to execute Cucumber tests.
- **src/test/java/pages/**: Page Object Model classes for web elements and actions.
- **features/**: Contains `.feature` files written in Gherkin.
- **pom.xml**: Project configuration and dependency management via Maven.
- **reports/**: Stores generated test reports.
- **resources/**: Configuration files and test data.

## Setup Instructions

### Prerequisites

- [Java JDK 8+](https://adoptopenjdk.net/)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- A supported WebDriver (e.g., ChromeDriver or GeckoDriver)
- (Optional) An IDE such as IntelliJ IDEA or Eclipse

### Steps

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/sunibhaikc/WIPRO_CAPESTONE-CUCUMBER.git
   cd WIPRO_CAPESTONE-CUCUMBER
   ```

2. **Install Dependencies:**
   Maven will automatically download all required dependencies.
   ```bash
   mvn clean install
   ```

3. **Configure WebDriver:**
   - Download [ChromeDriver](https://sites.google.com/chromium.org/driver/) or [GeckoDriver](https://github.com/mozilla/geckodriver/releases).
   - Add the driver to your system `PATH` or specify its location in your test configuration.

4. **Update Configuration (Optional):**
   - Modify properties or test data in `src/test/resources/` as needed.

## Usage Examples

### Running Tests

You can run all Cucumber tests using Maven:

```bash
mvn test
```

Or run a specific test runner class:

```bash
mvn -Dtest=TestRunnerClassName test
```

### Adding a Test Scenario

1. Write a new `.feature` file in the `features/` directory using Gherkin syntax.
2. Implement corresponding step definitions in `src/test/java/stepdefinitions/`.
3. (If needed) Add page classes in `src/test/java/pages/`.

### Sample Feature File

```gherkin
Feature: User Login

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters valid username and password
    And clicks the login button
    Then the user should be redirected to the dashboard
```

### Sample Step Definition

```java
@Given("the user is on the login page")
public void userIsOnLoginPage() {
    driver.get("https://example.com/login");
}
```

## Test Reports

After execution, reports are generated in the `reports/` directory. These can include HTML, JSON, or other formats based on your runner configuration.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

---

For questions, issues, or suggestions, please open an [issue](https://github.com/sunibhaikc/WIPRO_CAPESTONE-CUCUMBER/issues).
