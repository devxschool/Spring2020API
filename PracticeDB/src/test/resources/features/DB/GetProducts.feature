# new feature
# Tags: optional

Feature: Display products

  Scenario: Compare UI to DB
    Given user is on display products page
    When  values are parsed
    And DB connection is open to database "test"
    And product records are retrieved
    Then Values should be equal
