Feature: Epl testing
  Background:
    * configure driver = { type: 'chrome', addOptions: ["--remote-allow-origins=*"] }

  Scenario: testing the EPL data for add
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And delay(1000)
    And input("//*[@id='Login-username']/input",'dev_user@cognizant.com')
    And input("//*[@id='Login-password']/input",'12345678')
    Then select("//*[@id='Login-role']/select",'PMO').click()
    When click("//*[@id='Login-button']")
    And delay(5000)
    Then waitForUrl('http://localhost:3000/home')
    And delay(1000)
    Then click("//*[@id='epldata']")
    Then waitForUrl('http://localhost:3000/epldata')
    And delay(1000)
    And click("//*[@id='root']/div[1]/div[1]/div/div/button")
    And input("/html/body/div[3]/div/div/div[2]/div[1]/div/form/div[1]/input",'223344')
    And input("/html/body/div[3]/div/div/div[2]/div[1]/div/form/div[2]/input",'Testing')
    When click("/html/body/div[3]/div/div/div[2]/div[1]/div/form/button")
    And click("/html/body/div[3]/div/div/div[2]/div[2]/div/div/div[1]/button[1]")
    And delay(3000)
    Then scroll("//*[@id='epltable']/table/tbody/tr[9]/td[3]")
    And delay(5000)


  Scenario: testing the EPL data for delete
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And delay(1000)
    And input("//*[@id='Login-username']/input",'dev_user@cognizant.com')
    And input("//*[@id='Login-password']/input",'12345678')
    Then select("//*[@id='Login-role']/select",'PMO').click()
    When click("//*[@id='Login-button']")
    And delay(5000)
    Then waitForUrl('http://localhost:3000/home')
    And delay(1000)
    Then click("//*[@id='epldata']")
    Then waitForUrl('http://localhost:3000/epldata')
    And delay(1000)
    Then scroll("//*[@id='epltable']/table/tbody/tr[7]/td[3]")
    And delay(5000)
    And click("//*[@id='epltable']/table/tbody/tr[8]/td[4]/button")
    And click("/html/body/div[3]/div/div/div[3]/div")
    And click("//*[@id='root']/div[3]/div/div/div[1]/button[1]")
    Then scroll("//*[@id='epltable']/table/tbody/tr[7]/td[3]")
    And delay(2000)