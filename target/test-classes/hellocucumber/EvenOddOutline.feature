Feature: Even Odd using ScenarioOutline

  Scenario Outline: Testing of Even Odd using Outline
    Given User passes the number as input  <num>
    When  number is validated for the Even or Odd
    Then  Output is asserted if it Even or Odd "<output>"
    Examples:
      | num | output |
      |  10  |   Even |
      | 3    | Odd    |

