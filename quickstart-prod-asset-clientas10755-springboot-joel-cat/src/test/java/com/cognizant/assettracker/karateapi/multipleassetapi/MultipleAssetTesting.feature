Feature: Testing of HomePage api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

  Scenario: Testing multiple asset with Excel proper_upload
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/excel/upload'
    And multipart file file = {read: 'Excel_Proper_Upload 2.xlsx', filename: 'Excel_Proper_Upload 2.xlsx', Content-type: 'multipart/form-data'}
    When method POST
    Then status 200
    Then print response
    And match response == "Uploaded Successfully"

  Scenario: Testing multiple asset withe projectId and associateId
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/search'
    And request [{"projectId": "1000383243","associateId": "105977"}]
    When method POST
    Then status 200
    And print response
    And match response contains deep {"asset": [{"serialNumber": "AssetTest1"},{"serialNumber": "AssetTest2"},{"serialNumber": "Asset3"}]}