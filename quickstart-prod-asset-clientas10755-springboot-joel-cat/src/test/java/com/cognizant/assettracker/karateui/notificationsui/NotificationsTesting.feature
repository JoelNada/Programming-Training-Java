Feature: Notifications testing
  Background:
    * configure driver = { type: 'chrome', addOptions: ["--remote-allow-origins=*"] }

  Scenario: testing notifications unread
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
    And click("//*[@id='root']/div[4]/span/button")
    And delay(2000)
    And click("//*[@id="react-aria9511572215-:r7:-tabpane-profile"]/ul[1]/div/li[2]")
    And delay(2000)

  Scenario: testing notifications all
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
    And click("//*[@id='root']/div[4]/span/button")
    And click("//*[@id='react-aria9511572215-:r7:-tab-contact']")
    And delay(2000)