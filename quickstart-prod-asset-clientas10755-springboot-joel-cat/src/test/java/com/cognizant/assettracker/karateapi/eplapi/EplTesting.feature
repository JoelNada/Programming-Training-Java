@order3
Feature: Testing of epl api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

  Scenario: Testing adding the epl data
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/epl/add/data'
    And request {"eplID": "13645","eplName": "Rohith"}
    When method POST
    Then status 201
    And print response
    And match response == "EPL ADDED"

  Scenario: getting epl data
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/epl/get/all'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"eplName": "Rohith"}

  Scenario: deleting epl data by eplId
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/epl/delete/13645'
    When method DELETE
    Then status 200
    And print response
    And match response == "EPL DELETED"



