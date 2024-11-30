@order1
Feature: Testing of Auth_api's
  Background:
    * url 'http://localhost:8080/auth/'
    * header Accept = 'application/json'

  Scenario: Testing register_API
    Given path 'register'
    And request {"employeeId": "201004","email": "jane1@demo.com","name": "jane1","password": "12345678"}
    When method POST
    Then status 200

  Scenario: Testing login_API with PMO
    Given path 'login'
    And param role = 'PMO'
    And request {"email": "dev_user@cognizant.com","password": "12345678"}
    When method POST
    Then status 200
    And print response.jwtToken

  Scenario: Testing login_API ESA_PM
    Given path 'login'
    And param role = 'ESA_PM'
    And request {"email": "dev_user@cognizant.com","password": "12345678"}
    When method POST
    Then status 200
    And print response.jwtToken

  Scenario: Testing login_API with EPL
    Given path 'login'
    And param role = 'EPL'
    And request {"email": "dev_user@cognizant.com","password": "12345678"}
    When method POST
    Then status 200
    And print response.jwtToken

  Scenario: Testing the logout_API
    Given path 'logout'
    When method POST
    Then status 200
    And print response







