Feature: DB tests
  Scenario: truncate table
    Given DB connection is open to database "test"
    When table is truncated "employees"
    Then table should be empty "employees"
    And connection is closed