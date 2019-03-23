@balance
Feature: Balance
  One spend will manage balances, likes a cash flow

  Background:
    Given the spend "Lunch" which payer was "Tommen" and the spends:
      | name   | value |
      | Kelly  | 5.5   |
      | Sam    | 10    |
      | Ola    | 15    |
      | Tommen | 10    |
      | Sandy  | 5     |
    And the spend "Movie" which payer was "Kelly" and the spends:
      | name   | value |
      | Kelly  | 10    |
      | Sam    | 10    |
      | Ola    | 10    |
      | Tommen | 10    |
      | Sandy  | 10    |
      
  Scenario: How much is Tommen owed by everyone else
    When find the total of "Tommen" owed
    Then the result is £ 25.5

  Scenario: How much does Ola owe Sam?
    When find how much "Ola" owe "Sam"
    Then the result is £ 0.0
    
  Scenario: How much does Tommen owe Kelly?
    When find how much "Tommen" owe "Kelly"
    Then the result is £ 4.5
    
  Scenario: How much does Kelly owe Tommen?
    When find how much "Kelly" owe "Tommen"
    Then the result is £ 0.0
    
  Scenario: Outstanding balances with minimum number of payments
    When try outstanding balances
    Then the result of payments:
      | name  | pays   | value | 
      | Ola   | Kelly  | 25.0  |
      | Sam   | Tommen | 20.0  |
      | Sandy | Kelly  |  9.5  |
      | Sandy | Tommen |  5.5  | 
    