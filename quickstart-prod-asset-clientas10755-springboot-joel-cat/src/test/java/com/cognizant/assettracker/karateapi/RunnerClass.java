package com.cognizant.assettracker.karateapi;


import com.intuit.karate.junit5.Karate;

public class RunnerClass {

    @Karate.Test
    Karate authApiTest(){
        return Karate.run("authapi/AuthTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate userApiTest(){
        return Karate.run("usersapi/UserTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate eplApiTest(){
        return Karate.run("eplapi/EplTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate excelApiTest(){
        return Karate.run("excelapi/ExcelTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate esaApiTest(){
        return Karate.run("esaapi/EsaTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate homePageApiTest(){
        return Karate.run("homepageapi/HomePageTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate documentsApiTest(){
        return Karate.run("documentsapi/DocumentsTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate trackingApiTest(){
        return Karate.run("trackingapi/TrackingTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate assetReleaseApiTest(){
        return Karate.run("assetreleaseapi/AssetReleaseTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate templateApiTest(){
        return Karate.run("templateapi/TemplateTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate masterHistoryApiTest(){
        return Karate.run("masterhistoryapi/MasterHistoryTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate reportApiTest(){
        return Karate.run("reportsapi/ReportTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate multipleAssetApiTest(){
        return Karate.run("multipleassetapi/MultipleAssetTesting").relativeTo(getClass());
    }

    @Karate.Test
    Karate notificationApiTest(){
        return Karate.run("notificationsapi/NotificationTesting").relativeTo(getClass());
    }
    @Karate.Test
    Karate exceptionsApiTest(){
        return Karate.run("exceptionsapi/ExceptionsTesting").relativeTo(getClass());
    }



}
