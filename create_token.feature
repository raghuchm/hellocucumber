Feature: This is to demonstrate the toen creation

  Scenario: Get the token details 
    Given Get the token_id
    When login details "<username>" and "<password>" passed
    Then Get the token_id and save to file

    Examples:
  |username|password|
  |admin|password123|