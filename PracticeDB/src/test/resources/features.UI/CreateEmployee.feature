Feature: Create Employee
  Scenario: Create Employee using UI Form
    Given User is on the main page
    When user fills up the form
    |employeeNumber|lastName|firstName|extension|email          |officeCode|reportsTo|jobTitle|
    |301           |Malan   |David    |300      |david@email.com|201       |101      |teacher |
    And usr submits the form
    And User navigates to show Employees page
    Then User should be able to see an employee record with provided info


  Scenario: Create Employee using UI Form verify in DB
    Given User is on the main page
    And DB connection is open to database "test"
#    And table is truncated "employees"
    When user fills up the form
      |employeeNumber|lastName|firstName|extension|email          |officeCode|reportsTo|jobTitle|
      |301           |John    |Doe    |300         |david@email.com|201       |101      |teacher |
    And usr submits the form
    And Employee info is retreived with firstName "John" and lastName "Doe"
    Then The record should be present in the data base


    Scenario: Insert record directly to DB and verify on UI
      Given DB connection is open to database "test"
      And record is inserted using query
        |employeeNumber|lastName|firstName|extension|email          |officeCode|reportsTo|jobTitle|
        |0             |Daug    |Mar   |339       |DM@email.com|2011       |104      |teacher |
      When User is on the main page
      And User navigates to show Employees page
      And records are parsed
      Then created record should be present in the list
      And connection is closed

    Scenario: Records comparison UI to DB
      Given User navigates to show Employees page
      And records are parsed
      And DB connection is open to database "test"
      When all records retrieved from the table "employees"
      And records are mapped to beans
      Then lists should be equal
      And connection is closed

