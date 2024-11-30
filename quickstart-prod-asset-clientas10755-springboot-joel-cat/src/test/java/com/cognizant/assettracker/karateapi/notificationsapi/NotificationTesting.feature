@order13
Feature: Testing of Report api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

#  Scenario: Testing the notifications get report
#    Given path 'auth/login'
#    And param role = 'PMO'
#    And header Accept = 'application/json'
#    And request {"email":"dev_user@cognizant.com","password":"12345678"}
#    When method POST
#    Then status 200
#    And print response.jwtToken
#    And header Content-Type = 'Text'
#    Given header Authorization = 'Bearer '+response.jwtToken
#    Given path 'api/notifications/getreport'
#    And request "188"
##    And def dynamicValue = 188
##    And request {"dynamicKey":"#(dynamicValue)"}
#    When method POST
#    Then status 200
#    And print response
#
#  Scenario: Testing Notifications with properUpload
#    Given path 'auth/login'
#    And param role = 'PMO'
#    And request {"email":"dev_user@cognizant.com","password":"12345678"}
#    When method POST
#    Then status 200
#    And print response.jwtToken
#    Given header Authorization = 'Bearer '+response.jwtToken
#    Given path 'api/excel/upload'
#    And  multipart file file = {read: 'Excel_Proper_Upload (3).xlsx', filename: 'Excel_Proper_Upload (3).xlsx', Content-type: 'multipart/form-data'}
#    When method POST
#    Then status 200
#    Then print response
#    Given path 'auth/login'
#    And param role = 'PMO'
#    And request {"email":"dev_user@cognizant.com","password":"12345678"}
#    When method POST
#    Then status 200
#    And print response.jwtToken
#    Given header Authorization = 'Bearer '+response.jwtToken
#    Given path 'api/notifications/addressed'
#    And request 1
##    And param notificationId = 1
#    When method POST
#    Then status 200
#    And print response
#
#  Scenario: Testing Notifications with inProperUpload
#    Given path 'auth/login'
#    And param role = 'PMO'
#    And request {"email":"dev_user@cognizant.com","password":"12345678"}
#    When method POST
#    Then status 200
#    And print response.jwtToken
#    Given header Authorization = 'Bearer '+response.jwtToken
#    Given path 'api/excel/upload'
#    And  multipart file file = {read: 'Excel_Missing_Upload - Copy 1.xlsx', filename: 'Excel_Missing_Upload - Copy 1.xlsx', Content-type: 'multipart/form-data'}
#    When method POST
#    Then status 400
#    Then print response
#    Given path 'auth/login'
#    And param role = 'PMO'
#    And request {"email":"dev_user@cognizant.com","password":"12345678"}
#    When method POST
#    Then status 200
#    And print response.jwtToken
#    Given header Authorization = 'Bearer '+response.jwtToken
#    Given path 'api/notifications/addressed'
#    And request 1
##    And param notificationId = 1
#    When method POST
#    Then status 200
#    And print response

  Scenario: Testing Notifications unread
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/notifications/unread'
    When method GET
    Then status 200
    And print response

  Scenario: Testing Notifications close
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/notifications/close'
    When method GET
    Then status 200
    And print response

  Scenario: Testing Notifications all
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/notifications/all'
    When method GET
    Then status 200
    And print response





#  ---

#  Scenario: Testing Notifications all
#    Given path 'auth/login'
#    And param role = 'PMO'
#    And request {"email":"dev_user@cognizant.com","password":"12345678"}
#    When method POST
#    Then status 200
#    And print response.jwtToken
#    Given header Authorization = 'Bearer '+response.jwtToken
#    Given path 'api/notifications/all'
#    When method GET
#    And waitFor 5000
##    Then status 200
##    And print response
#
##    And def notificationMessage = notificationSocket.receive()
#
#    Given path 'auth/login'
#    And param role = 'PMO'
#    And request {"email":"dev_user@cognizant.com","password":"12345678"}
#    When method POST
#    Then status 200
#    And print response.jwtToken
#    Given header Authorization = 'Bearer '+response.jwtToken
#    Given path 'api/notifications/loadall'
#    When method GET
#    Then status 200
#    And print response
#
#    Given path 'auth/login'
#    And param role = 'PMO'
#    And request {"email":"dev_user@cognizant.com","password":"12345678"}
#    When method POST
#    Then status 200
#    And print response.jwtToken
#    Given header Authorization = 'Bearer '+response.jwtToken
#    Given path 'api/notifications/all'
#    When method GET
#    Then status 200
#    And print response
#
#
##    And notificationSocket.close()





