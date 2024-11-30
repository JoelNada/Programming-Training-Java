@order6
Feature: Testing of HomePage api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

  Scenario: Testing of Getting list of employee projectName and associateName
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/search'
    And request [{"projectName": "AMEXCO","associateName": "Rajan,Binesh "}]
    When method POST
    Then status 200
    And print response
    And match response contains deep {"assignmentId": 82671054},{"onboardingStatus": "Onboarded"}

  Scenario: Testing of getting list of employee with projectName and associateId
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/search'
    And request [{"projectName": "AMEXCO","associateId": "104268"}]
    When method POST
    Then status 200
    And print response
    And match response contains deep {"assignmentId": 82671054},{"onboardingStatus": "Onboarded"}


  Scenario: Testing of getting list of employee with projectId and associateId
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/search'
    And request [{"projectId": "1000057622","associateId": "104268"}]
    When method POST
    Then status 200
    And print response
    And match response contains deep {"assignmentId": 82671054},{"onboardingStatus": "Onboarded"}

  Scenario: Testing of getting list of employee with projectId and associateName
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/search'
    And request [{"projectId": "1000057622","associateName": "Rajan,Binesh "}]
    When method POST
    Then status 200
    And print response
    And match response contains deep {"assignmentId": 82671054},{"onboardingStatus": "Onboarded"}

  Scenario: Testing the Home page update asset details 1-Asset Assigned
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/update'
    And request [{"assignmentId": 82671054,"associateId": 104268,"associateName": "Rajan,Binesh E2","projectId": 1000057622,"projectName": "AMEXCO","assetType": "asset_update","asset": [{"serialNumber": "PHX5RB4TQ3.ads.aexp.com","allocated_date": "2023-08-01","status": "Asset Assigned","dw_pickup_date": "","assetMake": "Dell","assetModel": "Latitude","release_date": "","dw_pickup_requested": "","trackingNumber": "","dwpickupdoc": [],"dwpickupreceipt": []}]}]
    When method POST
    Then status 200
    And print response
    And match response == "Successfully modified assetdetails"

  Scenario: Testing the Home page update asset details 2-Asset release requested
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/update'
    And request [{"assignmentId": 82671054,"associateId": 104268,"associateName": "Rajan,Binesh E2","projectId": 1000057622,"projectName": "AMEXCO","assetType": "asset_update","asset": [{"serialNumber": "PHX5RB4TQ3.ads.aexp.com","allocated_date": "2023-08-01","status": "Asset Assigned","dw_pickup_date": "","assetMake": "Dell","assetModel": "Latitude","release_date": "2023-08-20","dw_pickup_requested": "","trackingNumber": "","dwpickupdoc": [],"dwpickupreceipt": []}]}]
    When method POST
    Then status 200
    And print response
    And match response == "Successfully modified assetdetails"

  Scenario: Testing the Home page update asset details 3-Asset pickup requested
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/update'
    And request [{"assignmentId": 82671054,"associateId": 104268,"associateName": "Rajan,Binesh E2","projectId": 1000057622,"projectName": "AMEXCO","assetType": "asset_update","asset": [{"serialNumber": "PHX5RB4TQ3.ads.aexp.com","allocated_date": "2023-08-01","status": "Asset Assigned","dw_pickup_date": "","assetMake": "Dell","assetModel": "Latitude","release_date": "2023-08-20","dw_pickup_requested": "2023-08-23","trackingNumber": "","dwpickupdoc": [],"dwpickupreceipt": []}]}]
    When method POST
    Then status 200
    And print response
    And match response == "Successfully modified assetdetails"

  Scenario: Testing the Home page update asset details 4-Asset pickup completed
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/update'
    And request [{"assignmentId": 82671054,"associateId": 104268,"associateName": "Rajan,Binesh E2","projectId": 1000057622,"projectName": "AMEXCO","assetType": "asset_update","asset": [{"serialNumber": "PHX5RB4TQ3.ads.aexp.com","allocated_date": "2023-08-01","status": "Asset Assigned","dw_pickup_date": "2023-08-28","assetMake": "Dell","assetModel": "Latitude","release_date": "2023-08-20","dw_pickup_requested": "2023-08-23","trackingNumber": "132576","dwpickupdoc": [],"dwpickupreceipt": []}]}]
    When method POST
    Then status 200
    And print response
    And match response == "Successfully modified assetdetails"


