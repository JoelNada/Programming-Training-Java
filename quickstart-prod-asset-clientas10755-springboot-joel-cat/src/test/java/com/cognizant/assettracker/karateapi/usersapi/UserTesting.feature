@order2
Feature: Testing of User api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

  Scenario: Testing of getting role API by remove
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/user/jane1@demo.com/role'
    And param action = 'remove'
    And request ["NEW_USER"]
    When method PUT
    Then status 200
    And print response
    And match response == "Role Updated Successfully"

  Scenario: Testing of getting role API by add
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/user/jane1@demo.com/role'
    And param action = 'add'
    And request ["NEW_USER"]
    When method PUT
    Then status 200
    And print response
    And match response == "Role Updated Successfully"

  Scenario: Testing the users
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/user/get/all'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"email": "dev_user@cognizant.com"},{"email": "jane1@demo.com"}

  Scenario: Testing of getting newusers API
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/user/role/newusers'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"name": "jane1"}

  Scenario: Testing of delete user
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/user/deleteuser/201004'
    When method DELETE
    Then status 200
    And print response
    And match response == "Deleted Successfully"