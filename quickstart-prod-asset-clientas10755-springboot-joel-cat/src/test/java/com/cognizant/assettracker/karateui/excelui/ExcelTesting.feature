Feature: Excel testing
  Background:
    * configure driver = { type: 'chrome', addOptions: ["--remote-allow-origins=*"] }

  Scenario: testing the excel proper_upload
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And input("//*[@id='Login-username']/input",'Amexpmo@cognizant.com')
    And input("//*[@id='Login-password']/input",'12345678')
    And focus("//select[@class='login-field']")
    And delay(2000)
    And select("//select[@class='login-field']",'PMO').click()
    And delay(2000)
    When click("//*[@id='Login-button']")
    Then waitForUrl('http://localhost:3000/home')
    And delay(2000)
    And mouse().move("//*[@id='upload']").go()
    And delay(2000)
    And mouse().move("//div[@id='excel-upload']").click()
    And driver.inputFile("//*[@id='excel-upload-btn']",'Excel_Proper_Upload (2).xlsx')
    And delay(10000)
    And click("//*[@id='excel-sbt-btn']")
    And click("//*[@id='excel-submit']")
    And delay(2000)
    And def msgText = text("//*[@id='root']/div[3]/div/div/div[2]")
    And print msgText
    And match msgText == "File Submitted Successfully"
    And delay(2000)
    And click("//*[@id='root']/div[3]/div/div/div[1]/button[1]")
    And delay(2000)
    Then waitForUrl('http://localhost:3000/home')
    And delay(2000)

  Scenario: testing the excel Missing_upload
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And input("//*[@id='Login-username']/input",'Amexpmo@cognizant.com')
    And input("//*[@id='Login-password']/input",'12345678')
    And focus("//select[@class='login-field']")
    And delay(2000)
    And select("//select[@class='login-field']",'PMO').click()
    And delay(2000)
    When click("//*[@id='Login-button']")
    Then waitForUrl('http://localhost:3000/home')
    And delay(2000)
    And mouse().move("//*[@id='upload']").go()
    And delay(2000)
    And mouse().move("//div[@id='excel-upload']").click()
    And driver.inputFile("//*[@id='excel-upload-btn']",'Excel_Missing_Upload (1).xlsx')
    And delay(5000)
    And click("//*[@id='excel-sbt-btn']")
    And click("//*[@id='excel-submit']")
    And delay(5000)
    And click("/html/body/div[3]/div/div/div[3]/button")
    Then waitForUrl('http://localhost:3000/PMOupload')
    And delay(2000)

  Scenario: testing the excel Re_upload
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And input("//*[@id='Login-username']/input",'Amexpmo@cognizant.com')
    And input("//*[@id='Login-password']/input",'12345678')
    And focus("//select[@class='login-field']")
    And delay(2000)
    And select("//select[@class='login-field']",'PMO').click()
    And delay(2000)
    When click("//*[@id='Login-button']")
    Then waitForUrl('http://localhost:3000/home')
    And delay(2000)
    And mouse().move("//*[@id='upload']").go()
    And delay(2000)
    And mouse().move("//div[@id='excel-upload']").click()
    And driver.inputFile("//*[@id='excel-upload-btn']",'Excel_Re_Upload (1).xlsx')
    And delay(5000)
    And click("//*[@id='excel-sbt-btn']")
    And click("//*[@id='excel-submit']")
    And delay(2000)
    And def msgText = text("//*[@id='root']/div[3]/div/div/div[2]")
    And print msgText
    And match msgText == "File Submitted Successfully"
    And delay(2000)
    And click("//*[@id='root']/div[3]/div/div/div[1]/button[1]")
    Then waitForUrl('http://localhost:3000/home')
    And delay(2000)

  Scenario: Testing the Data Feed history
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And input("//*[@id='Login-username']/input",'Amexpmo@cognizant.com')
    And input("//*[@id='Login-password']/input",'12345678')
    And focus("//select[@class='login-field']")
    And delay(2000)
    And select("//select[@class='login-field']",'PMO').click()
    And delay(2000)
    When click("//*[@id='Login-button']")
    Then waitForUrl('http://localhost:3000/home')
    And delay(2000)
    And mouse().move("//*[@id='upload']").go()
    And delay(2000)
    And mouse().move("//*[@id='excel-upload-history']").click()
    And click("//*[@id='download-btn']")
    And delay(5000)




