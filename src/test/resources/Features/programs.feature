Feature: Verify Health Programs navigation

  Scenario: Navigate to Tobacco Program
  Given user opens to homepage for programs
  When user navigate to "Tobacco"
  Then "Tobacco" program content should be displayed



  Scenario: Navigate to AHEC Scholars Program
    Given user opens the homepage for programs
    When user hovers on Programs and clicks "AHEC Scholars"
    Then "AHEC Scholars" program content should be displayed

  Scenario: Navigate to Healthy Aging Program
    Given user opens the homepage for programs
    When user hovers on Programs and clicks "Healthy Aging"
    Then "Healthy Aging" program content should be displayed

  Scenario: Navigate to Covering Florida Program
    Given user opens the homepage for programs
    When user hovers on Programs and clicks "Covering Florida"
    Then "Covering Florida" program content should be displayed