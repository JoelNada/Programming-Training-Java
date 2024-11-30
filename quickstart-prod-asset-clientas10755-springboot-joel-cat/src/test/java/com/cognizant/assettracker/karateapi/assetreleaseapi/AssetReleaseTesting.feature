@order9
Feature: Testing of Asset-release api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

  Scenario: Testing the assetRelease
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/assetrelease'
    And request {"requestCreationTime": "18-08-2023","assetReleaseReason": "expired","message": "string","serialNumber": "PHX5RB4TQ4.ads.aexp.com","pickupTime": "19-08-2023"}
    When method POST
    Then status 201
    And print response

  Scenario: Testing getting the assetRelease with serialNumber
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/assetrelease/PHX5RB4TQ3.ads.aexp.com'
    When method GET
    Then status 200
    And print response

