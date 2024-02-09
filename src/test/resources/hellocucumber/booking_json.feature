Feature: This Feature is used to create booking Details using json 

  Scenario Outline: create booking Details using json
    Given user should create booking details
    When create booking details read data from json
    Then Booking details should be created with id