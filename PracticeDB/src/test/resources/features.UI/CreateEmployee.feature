Feature: Create Employee
  Scenario: Create Employee using UI Form
    Given User is on the main page
    When user fills up the form
    |employeeNumber|lastName|firstName|extension|email          |officeCode|reportsTo|jobTitle|
    |301           |Malan   |David    |300      |david@email.com|201       |101      |teacher |
    And usr submits the form
    And User navigates to show Employees page
    Then User should be able to see an employee record with provided info