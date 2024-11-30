Feature: Users testing
  Background:
    * configure driver = { type: 'chrome', addOptions: ["--remote-allow-origins=*"] }

  Scenario: testing the PMO Approvals
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
    And mouse().move("//*[@id='Users']").click()
    And delay(2000)
    And mouse().move("//*[@id='basic-search']").click()
    Then waitForUrl('http://localhost:3000/pendinglistapproval')
    And delay(2000)
    And click("//*[@id='approval-submit']")
    And delay(1000)
    #PMO
    And click("/html/body/div[5]/div/div/div[2]/center/div[1]/input[1]")
    #Add
    When click("/html/body/div[3]/div/div/div[2]/center/div[2]")
    And def msgText = text("/html/body/div[3]/div/div/div[2]/div/div/div/div[2]")
    And print msgText
    And match msgText == "Role Updated Successfully"
    #close
    And click("//*[@class='btn btn-primary']")
    And waitForUrl("http://localhost:3000/home")

  Scenario: testing the Project Manager Approvals
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
    And mouse().move("//*[@id='Users']").click()
    And delay(2000)
    And mouse().move("//*[@id='basic-search']").click()
    Then waitForUrl('http://localhost:3000/pendinglistapproval')
    And delay(2000)
    And click("//*[@id='approval-submit']")
    And delay(1000)
    #ESA_PM
    And click("/html/body/div[5]/div/div/div[2]/center/div[1]/input[2]")
    #Add
    When click("/html/body/div[3]/div/div/div[2]/center/div[2]")
    And def msgText = text("/html/body/div[3]/div/div/div[2]/div/div/div/div[2]")
    And print msgText
    And match msgText == "Role Updated Successfully"
    #close
    And click("//*[@class='btn btn-primary']")
    And waitForUrl("http://localhost:3000/home")

  Scenario: testing the Epl Approvals
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
    And mouse().move("//*[@id='Users']").click()
    And delay(2000)
    And mouse().move("//*[@id='basic-search']").click()
    Then waitForUrl('http://localhost:3000/pendinglistapproval')
    And delay(2000)
    And click("//*[@id='approval-submit']")
    And delay(1000)
    #EPL
    And click("/html/body/div[5]/div/div/div[2]/center/div[1]/input[3]")
    #Add
    When click("/html/body/div[3]/div/div/div[2]/center/div[2]")
    And def msgText = text("/html/body/div[3]/div/div/div[2]/div/div/div/div[2]")
    And print msgText
    And match msgText == "Role Updated Successfully"
    #close
    And click("//*[@class='btn btn-primary']")
    And waitForUrl("http://localhost:3000/home")

  Scenario: testing the list of all users for Adding role
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
    And mouse().move("//*[@id='Users']").click()
    And delay(2000)
    And mouse().move("//*[@id='advanced-search']").click()
    Then waitForUrl('http://localhost:3000/all-users')
    And delay(2000)
    And click("//*[@id='root']/div[1]/table/tbody/tr[2]/td[4]/button[1]")
    And delay(1000)
    #Add
    And click("/html/body/div[7]/div/div/div[2]/ul/li[1]")
    And delay(1000)
    #ESA_PM
    And click("/html/body/div[7]/div/div/div[2]/center/div[1]/input[2]")
    And delay(1000)
    #add
    When click("/html/body/div[7]/div/div/div[2]/center/div[2]")
    And def msgText = text("/html/body/div[7]/div/div/div[2]/div/div/div/div[2]")
    And print msgText
    And match msgText == "Role Updated Successfully"
    And delay(2000)
    #close
    And click("//*[@class='btn btn-primary']")
    And delay(2000)

  Scenario: testing the list of all users for Removing role
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
    And mouse().move("//*[@id='Users']").click()
    And delay(2000)
    And mouse().move("//*[@id='advanced-search']").click()
    Then waitForUrl('http://localhost:3000/all-users')
    And delay(2000)
    And click("//*[@id='root']/div[1]/table/tbody/tr[2]/td[4]/button[1]")
    And delay(1000)
    #Remove
    And click("/html/body/div[7]/div/div/div[2]/ul/li[2]")
    And delay(1000)
    #ESA_PM
    And click("/html/body/div[7]/div/div/div[2]/center/div[1]/input[2]")
    And delay(1000)
    #remove
    When click("/html/body/div[7]/div/div/div[2]/center/div[2]")
    And def msgText = text("/html/body/div[7]/div/div/div[2]/div/div/div/div[2]")
    And print msgText
    And match msgText == "Role Updated Successfully"
    And delay(2000)
    #close
    And click("//*[@class='btn btn-primary']")
    And delay(2000)

  Scenario: testing the delete option for list of all users
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
    And mouse().move("//*[@id='Users']").click()
    And delay(2000)
    And mouse().move("//*[@id='advanced-search']").click()
    Then waitForUrl('http://localhost:3000/all-users')
    And delay(2000)
    #Delete:
    And click("//*[@id="root"]/div[1]/table/tbody/tr[2]/td[4]/button[2]")
    And delay(1000)
    #confirm
    And click("//div[@class='btn btn-primary']")
    And delay(1000)
    And def msgText = text("//*[@id="root"]/div[1]/table/tbody/tr[3]/td[4]/div[1]/div/div/div[2]")
    And print msgText
    And match msgText == "Deleted Successfully"
    And delay(2000)
    #close
    And click("//*[@id='root']/div[1]/table/tbody/tr[3]/td[4]/div[1]/div/div/div[1]/button[1]")
    And delay(2000)









