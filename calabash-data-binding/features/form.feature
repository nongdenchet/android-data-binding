Feature: Form feature

  Scenario: As a normal user I can use "Form Sample"
    When I press "Form Sample"
    Then I see "SUBMIT"

  Scenario: I enter invalid credit card
    When I press "Form Sample"
    Given I enter "5555" into input field number 1
    Then I see "Invalid Credit Card"
    Then I press "SUBMIT"
    Then I don't see the text "Success"

  Scenario: I enter invalid email
    When I press "Form Sample"
    Given I enter "nongdenchet" into input field number 2
    Then I see "Invalid Email"
    Then I press "SUBMIT"
    Then I don't see the text "Success"

  Scenario: I enter valid data
    When I press "Form Sample"
    Given I enter "555555555555" into input field number 1
    Given I enter "nongdenchet@gmail.com" into input field number 2
    Then I don't see "Invalid Email"
    Then I don't see "Invalid Credit Card"
    Then I press "SUBMIT"
    Then I see the text "Success"