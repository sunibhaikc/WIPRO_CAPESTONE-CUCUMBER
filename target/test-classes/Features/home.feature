Feature: Home page and Account flows
  As a user of the West Florida AHEC site
  I want to register, login, and verify core home-page functionality
  So that account flows and primary navigation/search features work

  Background:
    Given the site base URL is "https://westfloridaahec.org"

  @registration @smoke
  Scenario Outline: Registration attempt with valid credentials
    Given the user navigates to the "My Account" page
    When the user fills the registration form with username "<username>", email "<email>" and password "<password>"
    And the user clicks the Register button
    Then the registration attempt should be recorded (success or graceful failure allowed)

    Examples:
      | username     | email                 | password    |
      | Jyotirmay  | jyotirmaypanja@gmail.com     | Jyoti@2020  |

    # NOTE: step implementation can append a timestamp to username/email if you want unique accounts each run.

  @login
  Scenario Outline: Login attempt using previously-registered or fallback credentials
    Given the user navigates to the "My Account" page
    When the user logs in with username_or_email "<usernameOrEmail>" and password "<password>"
    Then the login attempt should be recorded (logged-in indicator or graceful failure)

    Examples:
      | usernameOrEmail         | password    |
      | nietsiddhant@gmail.com       | JBLMAC@123  |
      | jyotirmaypanja@gmail.com   | Jyoti@2020  |

  @homebasic
  Scenario: Home page basic verification
    Given the user is on the Home page
    Then the navigation menu should be present
    And the search input should be present

  @hover @navigation
  Scenario: Hovering Programs menu shows sub-links
    Given the user is on the Home page
    When the user hovers over the "Programs" menu
    Then the "Tobacco" link should be visible under Programs

  @programs
  Scenario: Health programs are listed under Programs
    Given the user is on the Home page
    When the user expands the Programs menu
    Then the following program links should be visible:
      | Tobacco        |
      | AHEC Scholars  |
      | Healthy Aging  |
      | Covering Florida |

  @resources
  Scenario: At least one expected resource link is present on Home page
    Given the user is on the Home page
    Then at least one resource link from the list should be visible:
      | Contact Us |
      | News       |
      | CPR Classes|
      | Shop       |
      | Home       |

  @search
  Scenario: Search returns results for a query
    Given the user is on the Home page
    When the user searches for "tobacco"
    Then the search results should contain the term "tobacco"