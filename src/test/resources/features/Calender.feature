Feature: UI and API test

  @run
  Scenario: A happy holidaymaker
    Given I like to holiday in "Sydney,AU"
    And I only like to holiday on "Thursday"
    When I look up the weather forecast
    And the temperature is warmer than 10 degrees

  @run
  Scenario: Create a new meeting using Native Android Calendar App
    Given I have launched the Calendar App
    When It is not a public holiday
    And It is not a weekend
    Then I want to book a meeting with the title "Introduction"
    And Set Meeting duration as <30 Minutes> in the evening at "9"
    And I invite people "tarika.chandure@gmail.com"
    And I save the meeting
    Then I Check if the meeting is created as expected "9:00 PM – 9:30 PM: Introduction"

