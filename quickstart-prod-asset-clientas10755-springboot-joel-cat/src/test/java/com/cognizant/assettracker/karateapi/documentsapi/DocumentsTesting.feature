@order7
Feature: Testing of Documents api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

  Scenario: Testing the documents save
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/documents/save'
    And  multipart file files = {read: 'Testimage.png', filename: 'Testimage.png', Content-type: 'multipart/form-data'}
    And  multipart file loginId = '201007'
    And  multipart file assignmentId = '12671054'
    And  multipart file serialNumber = 'PHX5RB4TQ3.ads.aexp.com'
    And  multipart file reportEnum = 'PICKUPRECEIPT'
    When method POST
    Then status 200
    And print response
    And match response.message == "Uploaded the file successfully: Testimage.png"

  Scenario: Testing the documents with serialNumber
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/documents/get/PHX5RB4TQ3.ads.aexp.com/all'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"serialNumber": "PHX5RB4TQ3.ads.aexp.com"}

  Scenario: Testing the documents download with id
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/documents/download/1'
    When method GET
    Then status 200
    And print response

  Scenario: Testing Deleting the documents with id
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/documents/delete/1'
    When method DELETE
    Then status 200
    And print response
    And match response.message == "Deleted successfully"

