@E2E
Feature: Karate Test

Scenario: 
   Given url 'https://postman-echo.com'
    And path '/get'
    And param test = '123'
    When method get
    Then status 200
    And def result = response
    * match result.args.test == '124'