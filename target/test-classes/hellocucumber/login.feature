Feature: To login to the website with credentials
  Scenario: Login data
    Given Load url open the website
    When enter the login credentials
    Then the website should load the product page
