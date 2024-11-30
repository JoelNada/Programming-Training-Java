@order14
Feature: Testing of Exceptions api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

#    AuthTesting
  Scenario: Testing login_API with invalid email
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email": "jane111@demo.com","password": "12345678"}
    When method POST
    Then status 400
    And print response
    And match response.message == "User Not Found"

  Scenario: Testing login_API with invalid password
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email": "dev_user@cognizant.com","password": "123456789"}
    When method POST
    Then status 400
    And print response
    And match response.message == " Invalid Username or Password  !!"

  Scenario: Testing login_API with new user
    Given path 'auth/register'
    And request {"employeeId": "201005","email": "jane@demo.com","name": "jane","password": "12345678"}
    When method POST
    Then status 200
    And print response
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email": "jane@demo.com","password": "12345678"}
    When method POST
    Then status 401
    And print response
    And match response.message == "UNAUTHORIZED ROLE"

    #users
  Scenario: Testing of getting role API by invalid email
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/user/jane2@demo.com/role'
    And param action = 'remove'
    And request ["NEW_USER"]
    When method PUT
    Then status 400
    And match response.message == "User not found."

#    HomePage
  Scenario: Testing of getting list of employee with incorrect projectId
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/search'
    And request [{"projectId": "100038324"}]
    When method POST
    Then status 404
    And match response.message == "The search returned no results with the given parameters"

  Scenario: Testing of getting list of employee with incorrect projectId and associateId
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/search'
    And request [{"projectId": "1000383243","associateId": "1025531"}]
    When method POST
    Then status 404
    And match response.message == "The search returned no results with the given parameters"

  Scenario: Testing of getting list of employee with  projectId and projectName
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/search'
    And request [{"projectId": "1000383243","projectName": "Unbilled A/c Mgmt- Amex_NA"}]
    When method POST
    Then status 400
    And match response.message == "Either Project ID or Project Name must be provided or project id or name and employee id or name"

# Excel upload

  Scenario: Testing Data Feed for Excel Wrong Template_upload
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/excel/upload'
    And  multipart file file = {read: 'Excel_Wrong_Template_Upload (1).xlsx', filename: 'Excel_Wrong_Template_Upload (1).xlsx', Content-type: 'multipart/form-data'}
    When method POST
    Then status 417
    And match response.message == "Headers don't match with template, please upload file again"

  Scenario: Testing multiple asset
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/excel/upload'
    And  multipart file file = {read: 'Excel_Proper_Upload 2(1).xlsx', filename: 'Excel_Proper_Upload 2(1).xlsx', Content-type: 'multipart/form-data'}
    When method POST
    Then status 400
    And match response contains deep {"missingFields": "[Multiple Asset Model missing, Multiple Asset Make missing]"}


#    Documents
  Scenario: Testing the documents download with invalid id
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/documents/download/22'
    When method GET
    Then status 404
    And match response.message == "Document not found with id: 22"

#    Esa
  Scenario: Testing the esa list employees with invalid associate id
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/esa/list/employees'
    And param type = 'associateId'
    And param info = '1025531'
    When method GET
    Then status 400
    And match response.message == "Invalid project info type"

#    Reports--error
  Scenario: Testing the invalid Report generate 11
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":11,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 400
    And match response.message == "Wrong choice!"
 #--error
  Scenario: Testing the  Report generate 2 with empty
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":2,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 417
    And match response.message == "Asset Release Requested Report is Empty"

  Scenario: Testing the  Report generate 3 with empty
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":3,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 417
    And match response.message == "Asset Pickup Requested Report is Empty"

  Scenario: Testing the  Report generate 5 with invalid cidType
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":5,"cidType":"CIDTRE","month":"JULY","year":"2023","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 400
    And match response.message == "wrong choice, please choose again!"

  Scenario: Testing the  Report generate 7 with invalid month
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":7,"cidType":"CIDTRE","month":"JANUARY","year":"2023","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 417
    And match response.message == "There Are No Laptop Returns this: JANUARY 2023"

  Scenario: Testing the  Report generate 10 with invalid year
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":10,"cidType":"CIDTRE","month":"JANUARY","year":"2025","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 417
    And match response.message == "Laptop Return per Year Report is Empty"

  Scenario: Testing the  Report download with invalid downloadType
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/download'
    And request {"reportName":"Sample Report","columns":[1,2,3,4,5,29,30],"downloadType":"PDF's"}
    When method POST
    Then status 400
    And match response.message == "Wrong choice! Please select download type again"




