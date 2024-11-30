@order11
Feature: Testing of Master-History api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

  Scenario: Testing master with assignmentId
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/master/assignmentId/12579395'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"assignmentId": 12579395,"associateId": 112390}

  Scenario: Testing master with updatedExcelFile
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/master/updatedWithFile/1000002_Excel_Proper_Upload (3).xlsx'
    When method GET
    Then status 200
    And print response

  Scenario: Testing master search with name
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/master/search'
    And param name = 'Mukherjee,Debjit'
    When method GET
    Then status 200
    And print response

  Scenario: Testing master search with id
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/master/search'
    And param id = '105083'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"associateId": 105083,"asset":{"serialNumber": "PHX5RB4TQ2.ads.aexp.com"}}

  Scenario: Testing master all
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/master/all'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"projectId": 1000383243},{"projectId": 1000057622},{"projectId": 1919191991},{"projectId": 2323232323}