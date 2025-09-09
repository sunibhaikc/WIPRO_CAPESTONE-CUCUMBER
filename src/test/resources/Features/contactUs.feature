Feature: Validate Contact Us Page

  Scenario: Verify Contact Us page displays contact information and icons
    Given the user opens the Contact Us page
    Then the contact section should contain "CONTACT US WITH EASE"
    And the address icon must be visible
    And the call us icon must be visible
    And the fax icon must be visible
    And the email icon must be visible
  Scenario: Verify Google Maps is embedded on Contact Us page
  Given the user opens the Contact Us page
  Then the Google Maps iframe should be visible