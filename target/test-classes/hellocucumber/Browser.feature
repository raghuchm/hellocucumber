Feature: This Feature is used to launch the browser and access the application

  Scenario Outline: Launch the Browser and access the application
    Given The Browser configuration is completed
    When Browser Launch is initiated with URL
    Then Browser should open with "<Title>"

    Examples:
  |  Title|
  |  Google|