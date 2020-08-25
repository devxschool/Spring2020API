Feature:
  Scenario:
    Given DB connection is open
    And Product Info with "1952 Alpine Renault 1300" retreived using API
    And Product Info with "1952 Alpine Renault 1300" retreived from DB
    Then Objects should be equal
    And connection is closed