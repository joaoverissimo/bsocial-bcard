Feature: Spend
  Spend Service Test

  Scenario: Insert Spend
    Given the spend "Lunch" which payer was "Tommen" and the spends:
      | name   | value |
      | Kelly  | 5.5   |
      | Sam    | 10    |
      | Ola    | 15    |
      | Tommen | 10    |
      | Sandy  | 5     |
    When Insert a Spend
    Then the spend was saved

    
  Scenario: Block to insert two Spends with same title
    Given the spend "Lets go Error" which payer was "Kelly" and the spends:
      | name   | value |
      | Kelly  | 5.5   |
    Given the spend "Lets go Error" which payer was "Tommen" and the spends:
      | name   | value |
      | Tommen | 10    |
    When Insert a Spend
    Then an exception is throw
    