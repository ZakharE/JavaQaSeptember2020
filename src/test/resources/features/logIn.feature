Feature: Log in

  Scenario: Login from main page
    Given I am on the main page
    When Login with email "naxayo5421@wncnw.com" and password "123123qwe"
    Then I logged in

  @negative
  Scenario: Login with invalid credentials
    Given I am on the main page
    When Login with email "dasd@mail.com" and password "askjnd"
    Then I am not logged