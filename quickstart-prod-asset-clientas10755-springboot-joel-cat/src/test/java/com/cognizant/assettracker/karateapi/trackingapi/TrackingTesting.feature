@order8
Feature: Testing of tracking api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

  Scenario: Testing getting the assetRelease with serialNumber
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/tracking/PHX5RB4TQ3.ads.aexp.com'
    When method GET
    Then status 200
    And print response
    And match response contains deep{"serialnumber": "PHX5RB4TQ3.ads.aexp.com"}

