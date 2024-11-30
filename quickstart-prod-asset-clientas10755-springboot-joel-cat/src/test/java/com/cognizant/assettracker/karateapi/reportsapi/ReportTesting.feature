@order12
Feature: Testing of Report api's
  Background:
    * url 'http://localhost:8080/'
    * header Accept = 'application/json'

#  Scenario: Testing the Report Headers
#    Given path 'auth/login'
#    And param role = 'PMO'
#    And request {"email":"dev_user@cognizant.com","password":"12345678"}
#    When method POST
#    Then status 200
#    And print response.jwtToken
#    Given header Authorization = 'Bearer '+response.jwtToken
#    Given path 'api/report/headers'
#    And request 10
#    When method POST
#    Then status 200
#    And print response

  Scenario: Testing the Report List
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/list'
    When method GET
    Then status 200
    And print response
    And match response contains deep {"no": 6,"option": "Client Asset Assignment List","description": "List of all the Client Assets"}

  Scenario: Testing the Report generate 1 Asset Issued-Onboarded
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":1,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 200
    And print response
    And match response contains deep {"associateCTSEmailId": "102553@cognizant.com","associateAmexEmailId": "jaydip@aexp.com"}

  Scenario: Testing the Report download 1 Asset Issued-Onboarded
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/download'
    And request {"reportName":"Sample Report","columns":[1,2,3,4,5,29,30],"downloadType":"PDF"}
    When method POST
    Then status 200

#    --------------------------------------------------

  Scenario: Testing the Report generate 2 Asset Release Requested
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/update'
    And request [{"assignmentId": 99999999,"associateId": 105083,"associateName": "Mukherjee,Debjit E2","projectId": 1919191991,"projectName": "Amex-TD&E Team T&M","assetType": "asset_update","asset": [{"serialNumber": "PHX5RB4TQ2.ads.aexp.com","allocated_date": "2023-07-05","status": "Asset release requested","dw_pickup_date": "","assetMake": "Dell","assetModel": "Latitude","release_date": "2023-07-08","dw_pickup_requested": "","trackingNumber": "","dwpickupdoc": [],"dwpickupreceipt": []}]}]
    When method POST
    Then status 200
    And print response
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
    Then status 200
    And print response
    And match response contains deep {"associateId": "105083","releaseRequestedDate": "2023-07-08"}

  Scenario: Testing the Report download 2 Asset Release Requested
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/download'
    And request {"reportName":"Sample Report","columns":[1,2,3,4,5,29,30],"downloadType":"PDF"}
    When method POST
    Then status 200

  Scenario: Testing the Report generate 3 Asset Pickup Requested
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/update'
    And request [{"assignmentId": 99999999,"associateId": 105083,"associateName": "Mukherjee,Debjit E2","projectId": 1919191991,"projectName": "Amex-TD&E Team T&M","assetType": "asset_update","asset": [{"serialNumber": "PHX5RB4TQ2.ads.aexp.com","allocated_date": "2023-07-05","status": "Asset release requested","dw_pickup_date": "","assetMake": "Dell","assetModel": "Latitude","release_date": "2023-07-08","dw_pickup_requested": "2023-07-09","trackingNumber": "","dwpickupdoc": [],"dwpickupreceipt": []}]}]
    When method POST
    Then status 200
    And print response
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
    Then status 200
    And print response
    And match response contains deep {"associateId": "105083","dwpickupRequestedDate": "2023-07-09"}

  Scenario: Testing the Report download 3 Asset Pickup Requested
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/download'
    And request {"reportName":"Sample Report","columns":[1,2,3,4,5,29,30],"downloadType":"PDF"}
    When method POST
    Then status 200

  Scenario: Testing the Report generate 4 Asset Pickup Completed
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/home/update'
    And request [{"assignmentId": 99999999,"associateId": 105083,"associateName": "Mukherjee,Debjit E2","projectId": 1919191991,"projectName": "Amex-TD&E Team T&M","assetType": "asset_update","asset": [{"serialNumber": "PHX5RB4TQ2.ads.aexp.com","allocated_date": "2023-07-05","status": "Asset release requested","dw_pickup_date": "2023-07-11","assetMake": "Dell","assetModel": "Latitude","release_date": "2023-07-08","dw_pickup_requested": "2023-07-09","trackingNumber": "134976","dwpickupdoc": [],"dwpickupreceipt": []}]}]
    When method POST
    Then status 200
    And print response
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":4,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 200
    And print response
    And match response contains deep {"associateId": "105083","releaseRequestedDate": "2023-07-08","dwpickupRequestedDate": "2023-07-09"}

  Scenario: Testing the Report download 4 Asset Pickup Completed
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/download'
    And request {"reportName":"Sample Report","columns":[1,2,3,4,5,29,30],"downloadType":"PDF"}
    When method POST
    Then status 200


#   --------------------------------------------------

  Scenario: Testing the Report generate 5 Onboarded to Client Network
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":5,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[1,2,6,3,5,29,30]}
    When method POST
    Then status 200
    And print response
    And match response contains deep {"associateId": "105083"},{"associateId": "102553"},{"associateId": "112390"},{"associateId": "914924"}

  Scenario: Testing the Report download 5 Onboarded to Client Network
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/download'
    And request {"reportName":"Sample Report","columns":[1,2,3,6,5,29,30],"downloadType":"PDF"}
    When method POST
    Then status 200

  Scenario: Testing the Report generate 6 Client Asset Assignment List
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":6,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 200
    And print response
    And match response contains deep {"associateId": "102553","releaseRequestedDate": "N/A","dwpickupRequestedDate": "N/A"}

  Scenario: Testing the Report download 6 Client Asset Assignment List
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/download'
    And request {"reportName":"Sample Report","columns":[1,2,3,4,5,29,30],"downloadType":"PDF"}
    When method POST
    Then status 200

  Scenario: Testing the Report generate 7 Assets Returned Per Month
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":7,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 200
    And print response
    And match response contains deep {"associateId": "105083","associateCTSEmailId": "105083@cognizant.com","associateAmexEmailId": "debjit.mukherjee@aexp.com"}

  Scenario: Testing the Report download 7 Assets Returned Per Month
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/download'
    And request {"reportName":"Sample Report","columns":[1,2,3,4,5,29,30],"downloadType":"PDF"}
    When method POST
    Then status 200


  Scenario: Testing the Report generate 8 Incomplete Associate Asset Details
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":8,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[1,2,4,3,7,29,30]}
    When method POST
    Then status 200
    And print response
    And match response contains deep {"associateId": "102553","releaseRequestedDate": "N/A","dwpickupRequestedDate": "N/A"}

  Scenario: Testing the Report download 8 Incomplete Associate Asset Details
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/download'
    And request {"reportName":"Sample Report","columns":[1,2,3,6,7,29,30],"downloadType":"PDF"}
    When method POST
    Then status 200

  Scenario: Testing the Report generate 9 Onboarded to Client Network without Client Asset
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":9,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[1,2,4,3,5,29,30]}
    When method POST
    Then status 200
    And print response
    And match response contains deep {"associateId": "112390","associateCTSEmailId": "112390@cognizant.com","associateAmexEmailId": "saravanakumar.krishnan@aexp.com"}

  Scenario: Testing the Report download 9 Onboarded to Client Network without Client Asset
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/download'
    And request {"reportName":"Sample Report","columns":[1,2,4,3,5,29,30],"downloadType":"PDF"}
    When method POST
    Then status 200

  Scenario: Testing the Report generate 10 Assets Returned Per Year
    Given path 'auth/login'
    And param role = 'PMO'
    And request {"email":"dev_user@cognizant.com","password":"12345678"}
    When method POST
    Then status 200
    And print response.jwtToken
    Given header Authorization = 'Bearer '+response.jwtToken
    Given path 'api/report/generate'
    And request {"type":10,"cidType":"CIDTRUE","month":"JULY","year":"2023","columns":[32]}
    When method POST
    Then status 200
    And print response
    And match response contains deep {"JULY": 1}





