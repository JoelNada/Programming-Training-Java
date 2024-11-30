@order4
Feature: Testing of Excel api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

  Scenario: Testing Data Feed for Excel proper_upload
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/excel/upload'
    And multipart file file = {read: 'Excel_Proper_Upload (3).xlsx', filename: 'Excel_Proper_Upload (3).xlsx', Content-type: 'multipart/form-data'}
    When method POST
    Then status 200
    Then print response
    And match response == "Uploaded Successfully"

  Scenario: Testing Data Feed for Excel missing_upload
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/excel/upload'
    And  multipart file file = {read: 'Excel_Missing_Upload - Copy 1.xlsx', filename: 'Excel_Missing_Upload - Copy 1.xlsx', Content-type: 'multipart/form-data'}
    When method POST
    Then status 400
    Then print response
    And match response contains deep {"assignmentId": 11076456},{"missingFields": "[Grade missing]"}

  Scenario: Testing Data Feed for missing field Report
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/excel/missingFieldsReport'
    When method GET
    Then status 200
    Then print response

  Scenario: Testing Data Feed for excel download
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/excel/download/100002_Excel_Proper_Upload (3).xlsx'
    When method GET
    Then status 200
    Then print response

  Scenario: Testing Data Feed for all excels
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/excel/getall'
    When method GET
    Then status 200
    Then print response
    And match response contains deep {"createdBy": "dev_user@cognizant.com"}

#  Scenario: Testing Data Feed for uploadProgress
#    Given path 'auth/login'
#    And param role = 'PMO'
#    And request {"email":"dev_user@cognizant.com","password":"12345678"}
#    When method POST
#    Then status 200
#    And print response.jwtToken
#    Given header Authorization = 'Bearer '+response.jwtToken
#    Given path 'api/excel/uploadprogress'
#    When method GET
#    Then status 200
#    Then print response

  Scenario: Testing Data Feed for closeUploadProgress
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/excel/closeuploadprogress'
    When method GET
    Then status 200
    Then print response

