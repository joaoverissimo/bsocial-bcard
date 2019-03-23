# bsocial-bcard

Well, when you have a free Saturday...

This project is the result of a Coding Task from the meetup [Codecademy London](https://www.meetup.com/pt-BR/Codecademy-London/events/257591450/).

## the challenge

The task was to process the following input. It's expenses from a group of friends, which only one pay the bill. 

| title Lunch   | Title Movie   |
| ------------- | ------------- |
| Spend:        | Spend:        |
| Kelly: 5.5    | Kelly: 10     |
| Sam: 10       | Sam: 10       |
| Ola: 15       | Ola: 10       |
| Tommen: 10    | Tommen: 10    |
| Sandy: 5      | Sandy: 10     |
| Total: 45.5   | Total: 50     |
| Payer: Tommen | Payer: Kelly  |

## the expected results

The result of processing need be able to answer the following questions:

1) In total, how much is Tommen owed by everyone else? R-> 25.50

2) How much does Ola owe Sam? R-> nothing

3) How much does Tommen owe Kelly? R-> 4.5

4) How can the group settle outstanding balances with minimum number of payments? R->
* Ola pays Kelly -> 25
* Sam pays Tommen -> 20
* Sandy pays Kelly -> 9.5
* Sandy pays Tommen -> 5.5

## my strategy

I've used Cucumber to write BDD scenarios. To get a detailed history, I stored the data in the objects `BalanceRecord`, which works like an account bank. One input will result in two items in this class.


```cucumber
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
    
```


![BDD](https://user-images.githubusercontent.com/2000159/54872025-8bb7ab00-4db5-11e9-8a14-1aec85b164f4.png "BDD")
