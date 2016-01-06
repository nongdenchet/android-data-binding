Feature: Recycler feature

  Scenario: As a normal user I can use "Recycler Sample"
    When I press "Recycler Sample"
    Then I see "Hoang"
    Then I see "Petite Note"
    Then I see "Cà Phê Quỳ Đà Lạt"

  Scenario: I click sort
    When I press "Recycler Sample"
    Then I press view with id "action_sort"
    Then I press "Store"
    Then I see "Co.opMart Nhiêu Lộc"
    Then I press view with id "action_sort"
    Then I press "Theater"
    Then I see "Liều Cafe"