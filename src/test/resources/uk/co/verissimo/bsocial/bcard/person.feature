Feature: Person
  Service Test for Entity person

  Scenario: Insert Person
    Given the person name "Verissimo"
    When Insert person
    Then the person saved
    
