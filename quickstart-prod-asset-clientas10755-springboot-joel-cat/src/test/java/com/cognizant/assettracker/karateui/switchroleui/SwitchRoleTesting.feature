Feature: SwitchRole testing
  Background:
    * configure driver = { type: 'chrome', addOptions: ["--remote-allow-origins=*"] }

  Scenario: testing switch role project manager
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
    Then click("//*[@id='root']/nav/div/div[1]/div/span/span")
    And delay(1000)
    And select("//*[@id='Login-role']/select",'Project Manager').click()
    And delay(1000)
    And input("//*[@id='Login-password']/input",'12345678')
    And delay(1000)
    When click("//*[@id='Login-button']")
    And delay(1000)

  Scenario: testing switch role epl
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
    Then click("//*[@id='root']/nav/div/div[1]/div/span/span")
    And delay(1000)
    And select("//*[@id='Login-role']/select",'EPL').click()
    And delay(1000)
    And input("//*[@id='Login-password']/input",'12345678')
    And delay(1000)
    When click("//*[@id='Login-button']")
    And delay(1000)