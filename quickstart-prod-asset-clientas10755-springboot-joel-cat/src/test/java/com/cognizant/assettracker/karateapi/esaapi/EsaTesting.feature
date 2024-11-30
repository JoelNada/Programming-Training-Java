@order5
Feature: Testing of Esa api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

  Scenario: Testing esa list employees with projectName
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/esa/list/employees'
    And param type = 'projectName'
    And param info = 'AMEXCO'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"associateId": 104268},{"associateName": "Rajan,Binesh "}

  Scenario: Testing esa list employees with projectId
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/esa/list/employees'
    And param type = 'projectId'
    And param info = '1000383243'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"associateId": 102553},{"associateId": 893675}

  Scenario: Getting the esa list
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/esa/list/all'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"projectId": 1000057622},{"projectName": "AMEXCO"}
