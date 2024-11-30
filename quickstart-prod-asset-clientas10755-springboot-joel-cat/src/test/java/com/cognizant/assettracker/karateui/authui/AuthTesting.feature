Feature: Auth testing
  Background:
    * configure driver = { type: 'chrome', addOptions: ["--remote-allow-origins=*"] }

#  Scenario: Testing the Registration for user 1
#    Given driver 'http://localhost:3000/register'
#    And driver.maximize()
#    And delay(2000)
#    Then waitForUrl('http://localhost:3000/register')
#    And input("//*[@id='reg-name']/input","test1")
#    And delay(1000)
#    And input("/html/body/div/div[1]/div[2]/div/div[4]/form/div[1]/div[2]/input","215004")
#    And delay(1000)
#    And input("/html/body/div/div[1]/div[2]/div/div[4]/form/div[1]/div[3]/input","t1@demo.com")
#    And delay(1000)
#    And input("//*[@id='reg-password']/input","Testing@1")
#    And delay(1000)
#    And input("//*[@id='reg-cpassword']/input","Testing@1")
#    And delay(1000)
#    When click("//*[@id='reg-submit']/button")
#    And delay(2000)
#    Then waitForUrl('http://localhost:3000/')
#    And delay(2000)
#
#  Scenario: Testing the Registration for user 2
#    Given driver 'http://localhost:3000/register'
#    And driver.maximize()
#    And delay(2000)
#    Then waitForUrl('http://localhost:3000/register')
#    And input("//*[@id='reg-name']/input","test2")
#    And delay(1000)
#    And input("/html/body/div/div[1]/div[2]/div/div[4]/form/div[1]/div[2]/input","215005")
#    And delay(1000)
#    And input("/html/body/div/div[1]/div[2]/div/div[4]/form/div[1]/div[3]/input","t2@demo.com")
#    And delay(1000)
#    And input("//*[@id='reg-password']/input","Testing@2")
#    And delay(1000)
#    And input("//*[@id='reg-cpassword']/input","Testing@2")
#    And delay(1000)
#    When click("//*[@id='reg-submit']/button")
#    And delay(2000)
#    Then waitForUrl('http://localhost:3000/')
#    And delay(2000)
#
#  Scenario: Testing the Registration from login page
#    Given driver 'http://localhost:3000/'
#    And driver.maximize()
#    And delay(2000)
#    Then waitForUrl('http://localhost:3000/')
#    And click("//*[@id='Register']")
#    And delay(2000)
#    And input("//*[@id='reg-name']/input","test3")
#    And delay(1000)
#    And input("/html/body/div/div[1]/div[2]/div/div[4]/form/div[1]/div[2]/input","215006")
#    And delay(1000)
#    And input("/html/body/div/div[1]/div[2]/div/div[4]/form/div[1]/div[3]/input","t3@demo.com")
#    And delay(1000)
#    And input("//*[@id='reg-password']/input","Testing@3")
#    And delay(1000)
#    And input("//*[@id='reg-cpassword']/input","Testing@3")
#    And delay(1000)
#    When click("//*[@id='reg-submit']/button")
#    And delay(2000)
#    Then waitForUrl('http://localhost:3000/')
#    And delay(2000)


  Scenario: testing the login and logout
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(5000)
    Then waitForUrl('http://localhost:3000/')
    And delay(5000)
    And input("//input[@placeholder='username or email']",'dev_user@cognizant.com')
    And input("//input[@placeholder='Password']",'12345678')
    And delay(2000)
#    And focus("//*[@id='role-field']")
    And delay(2000)
    And select("//*[@data-testid='role-field']",'EPL').click()
#    And click("//*[@id='role-field']")
#    And select("//select[@class='login-field']",'PMO').click()
    And delay(5000)
    And click("//*[@id='Login-button']")
    And delay(5000)
    And click("//*[@id='complete-logout']")
    And delay(5000)

