 Feature: This Feature is used to create booking Details

  Scenario Outline: create booking Details
    Given user should have access to create booking details
    When create booking details "<firstname>" "<lastname>" <totalprice> "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    Then Booking details should be created

    Examples:
  |firstname|lastname|totalprice|depositpaid|checkin	 |checkout  |additionalneeds|
  |  ram    |rahim   |1000			|true				|2018-01-01|2018-01-01| breakfast     |