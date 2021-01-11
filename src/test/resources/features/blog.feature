Feature: Blog

  Scenario: Login from main page
    Given I am on the main page
    When Login with email "naxayo5421@wncnw.com" and password "123123qwe"
    And I go to blog page
    And I add article to favorites
    Then Article added to favorites section
    Then I remove article to favorites
