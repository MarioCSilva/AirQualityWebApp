Feature: Search Air Quality

  Background:
    When I navigate to 'http://localhost:3000/admin/search'

  Scenario: Search for Air Quality
    And I type the name of the city 'Aveiro'
    And I type the name of the country 'Portugal'
    And I press Search
    Then I see Air Details of 'Aveiro, PT'

  Scenario: Check 'Cities and Statistics'
    And I change tab to Cities and Statistics
    And I check Cache Statistics
    And I press the city card of 'Sintra, PT'
    Then I see a modal with Air Details of 'Sintra, PT'
    And I close Air Details modal
    Then I check if Cache Statistics changed
    And I press the city card of 'Sintra, PT'
    And I close Air Details modal
    Then I check if number of cache hits increased
