Feature: Exceptions testing
  Background:
    * configure driver = { type: 'chrome', addOptions: ["--remote-allow-origins=*"] }

  Scenario: testing the registration page with existing user
    Given driver 'http://localhost:3000/register'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/register')
    And input("//*[@id='reg-name']/input","PMO")
    And delay(1000)
    And input("//*[@id='reg-email']/input","Amexpmo@cognizant.com")
    And delay(1000)
    And input("//*[@id='reg-password']/input","12345678")
    And delay(1000)
    And input("//*[@id='reg-cpassword']/input","12345678")
    And delay(1000)
    When click("//*[@id='reg-submit']/button")
    And delay(1000)
    And def errorText = text("//*[@id='root']/div[1]/div[1]/div/div/div[2]")
    And print errorText
    And match errorText == "User email already exists! Choose another email"
    And delay(2000)
    And click("//*[@class='btn btn-primary']")
    Then waitForUrl("http://localhost:3000/register")
    And delay(2000)

  Scenario: testing the registration page with invalid email
    Given driver 'http://localhost:3000/register'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/register')
    And input("//*[@id='reg-name']/input","PMO")
    And delay(1000)
    And input("//*[@id='reg-email']/input","Amexpmocognizant.com")
    And delay(1000)
    And input("//*[@id='reg-password']/input","12345678")
    And delay(1000)
    And input("//*[@id='reg-cpassword']/input","12345678")
    And delay(1000)
    When click("//*[@id='reg-submit']/button")
    And def errorText = text("//*[@id='reg-email']/span/div")
    And print errorText
    And match errorText == "Invalid email"
    And delay(2000)

  Scenario: testing the registration page when password is short
    Given driver 'http://localhost:3000/register'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/register')
    And input("//*[@id='reg-name']/input","PMO")
    And delay(1000)
    And input("//*[@id='reg-email']/input","Amexpmo@cognizant.com")
    And delay(1000)
    And input("//*[@id='reg-password']/input","1234")
    And delay(1000)
    And input("//*[@id='reg-cpassword']/input","1234")
    And delay(1000)
    When click("//*[@id='reg-submit']/button")
    And def errorText = text("//*[@id='reg-password']/span/div")
    And print errorText
    And match errorText == "Too Short!"
    And delay(2000)

  Scenario: testing the registration page when conform password is invalid
    Given driver 'http://localhost:3000/register'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/register')
    And input("//*[@id='reg-name']/input","PMO")
    And delay(1000)
    And input("//*[@id='reg-email']/input","Amexpmo@cognizant.com")
    And delay(1000)
    And input("//*[@id='reg-password']/input","12345678")
    And delay(1000)
    And input("//*[@id='reg-cpassword']/input","123456789")
    And delay(1000)
    When click("//*[@id='reg-submit']/button")
    And def errorText = text("//*[@id='reg-cpassword']/span/div")
    And print errorText
    And match errorText == "Password did not match"
    And delay(2000)

  Scenario: testing the login page with invalid password
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And input("//*[@id='Login-username']/input",'Amexpmo@cognizant.com')
    And input("//*[@id='Login-password']/input",'123456789')
    And focus("//select[@class='login-field']")
    And delay(2000)
    And select("//select[@class='login-field']",'PMO').click()
    And delay(2000)
    When click("//*[@id='Login-button']")
    And delay(2000)
    And def errorText = text("//*[@id='root']/div/div[1]/div/div")
    And print errorText
    And match errorText == "Invalid Username or Password  !!"

  Scenario: testing the login page with invalid user name
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And input("//*[@id='Login-username']/input",'admin@demo.com')
    And input("//*[@id='Login-password']/input",'12345678')
    And focus("//select[@class='login-field']")
    And delay(2000)
    And select("//select[@class='login-field']",'PMO').click()
    And delay(2000)
    When click("//*[@id='Login-button']")
    And delay(2000)
    And def errorText = text("//*[@id='root']/div/div[1]/div/div")
    And print errorText
    And match errorText == "User Not Found"

  Scenario: Testing login API by logging new user
    Given driver 'http://localhost:3000/register'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/register')
    And input("//*[@id='reg-name']/input","test3")
    And delay(1000)
    And input("//*[@id='reg-email']/input","t3@demo.com")
    And delay(1000)
    And input("//*[@id='reg-password']/input","12345678")
    And delay(1000)
    And input("//*[@id='reg-cpassword']/input","12345678")
    And delay(1000)
    When click("//*[@id='reg-submit']/button")
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And delay(2000)
    And input("//*[@id='Login-username']/input",'t3@demo.com')
    And input("//*[@id='Login-password']/input",'12345678')
    When click("//*[@id='Login-button']")
    And delay(2000)
    And def errorText = text("//*[@id='root']/div/div[1]/div/div")
    And print errorText
    And match errorText == "ADMIN approval pending"

  Scenario: testing the excel Wrong_Template_upload
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And input("//*[@id='Login-username']/input",'Amexpmo@cognizant.com')
    And input("//*[@id='Login-password']/input",'12345678')
    When click("//*[@id='Login-button']")
    Then waitForUrl('http://localhost:3000/home')
    And delay(2000)
    And mouse().move("//*[@id='upload']").go()
    And delay(2000)
    And mouse().move("//div[@id='excel-upload']").click()
    And driver.inputFile("//*[@id='excel-upload-btn']",'Excel_Wrong_Template_Upload (1).xlsx')
    And delay(5000)
    And click("//*[@id='excel-sbt-btn']")
    And click("//*[@id='excel-submit']")
    And delay(2000)
    And def errorText = text("//*[@id='root']/div[3]/div/div/div[2]")
    And print errorText
    And match errorText == "Headers don't match with template, please upload file again"
    And delay(2000)
    And click("//*[@id='root']/div[3]/div/div/div[1]/button[1]")
    Then waitForUrl('http://localhost:3000/PMOupload')
    And delay(2000)

  Scenario: Testing the Reports Asset Release Requested after Asset Pickup Requested
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And input("//*[@id='Login-username']/input",'Amexpmo@cognizant.com')
    And input("//*[@id='Login-password']/input",'12345678')
    When click("//*[@id='Login-button']")
    And delay(2000)
    And click("/html/body/div/nav/div/div[2]/ul/li[2]/a")
    And delay(2000)
    And select("//*[@id='Dropdown-option-report']",'2').click()
    And delay(2000)
    When click("//*[@id='report-view-btn2']")
    And delay(2000)
    And def errorText = text("//*[@id='root']/div[1]/div[3]/div/div/div[2]")
    And print errorText
    And match errorText == "Asset Release Requested Report is Empty"
    When click("//*[@id='root']/div[1]/div[3]/div/div/div[1]/button[1]")
    And delay(2000)

  Scenario: Testing the Reports Asset Pickup Requested after Asset Pickup Completed
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And input("//*[@id='Login-username']/input",'Amexpmo@cognizant.com')
    And input("//*[@id='Login-password']/input",'12345678')
    When click("//*[@id='Login-button']")
    And delay(2000)
    And click("/html/body/div/nav/div/div[2]/ul/li[2]/a")
    And delay(2000)
    And select("//*[@id='Dropdown-option-report']",'3').click()
    And delay(2000)
    When click("//*[@id='report-view-btn2']")
    And delay(2000)
    And def errorText = text("//*[@id='root']/div[1]/div[3]/div/div/div[2]")
    And print errorText
    And match errorText == "Asset Pickup Requested Report is Empty"
    When click("//*[@id='root']/div[1]/div[3]/div/div/div[1]/button[1]")
    And delay(2000)

  Scenario: Testing the Reports invalid Asset Returned Per Month
    Given driver 'http://localhost:3000/'
    And driver.maximize()
    And delay(2000)
    Then waitForUrl('http://localhost:3000/')
    And input("//*[@id='Login-username']/input",'Amexpmo@cognizant.com')
    And input("//*[@id='Login-password']/input",'12345678')
    When click("//*[@id='Login-button']")
    And delay(2000)
    And click("/html/body/div/nav/div/div[2]/ul/li[2]/a")
    And delay(2000)
    And select("//*[@id='Dropdown-option-report']",'7').click()
    And delay(2000)
    And select("/html/body/div/div[1]/div[2]/div[1]/div/div/select",'{}JANUARY').click()
    And delay(2000)
    When click("//*[@id='report-view-btn2']")
    And delay(2000)
    And def errorText = text("//*[@id='root']/div[1]/div[3]/div/div/div[2]")
    And print errorText
    And match errorText == "There Are No Laptop Returns this: JANUARY 2023"
    When click("//*[@id='root']/div[1]/div[3]/div/div/div[1]/button[1]")
    And delay(2000)