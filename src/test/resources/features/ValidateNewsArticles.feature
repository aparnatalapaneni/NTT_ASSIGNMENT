Feature: ESPN Cricinfo Website Functional Testing

  Scenario: Verify that the 'News' section displays the latest cricket-related articles
    Given I am on the ESPN Cricinfo homepage
    When I navigate to the 'News' section
    Then I should see the latest cricket-related articles