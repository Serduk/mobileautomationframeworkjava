package tests.ios.login;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GetYourTokenPage;
import pages.LandingPage;
import pages.LoginPage;
import pages.SettingsPage;
import pages.YourTokenPage;
import tests.CustomRetryAnalyzer;
import tests.MobileTest;
import tests.TestBase;
import utils.TestConstants;

@MobileTest(name = "ios")
public class IOSLoginTests extends TestBase {

  @Test(retryAnalyzer = CustomRetryAnalyzer.class)
  public void iOSLoginTest(){
    logger = extent.createTest("ID: 1 IOS: Login test");
    logger.assignCategory("Login");
    logger.assignAuthor("Alexander Kostuchenko");

    LoginPage loginPage = new LoginPage(driver);
    if(IOS_TOKEN.isEmpty()) {
      log.info("There was no IOS_TOKEN from previous runs. Trying to get one.");
      GetYourTokenPage getYourTokenPage = loginPage.clickGetTokenLink();
      logger.log(Status.PASS, "Click 'go to site' link to navigate to 'Get your Token' page");

      YourTokenPage yourTokenPage = getYourTokenPage.getToYouTokenPage(TestConstants.Users.getUserOne(), TestConstants.Users.getUserOnePassword());
      logger.log(Status.PASS, "Enter user credentials a navigate to 'Your Token' page");

      IOS_TOKEN = yourTokenPage.getTokenValue();
      loginPage = yourTokenPage.goBackToLoginPage();
      logger.log(Status.PASS, "Get token and navigate to 'Login' page");
    }

    LandingPage landingPage = loginPage.setTokenAndLogin(IOS_TOKEN);
    Assert.assertTrue(landingPage.isSettingsButtonVisible(), "'Settings' button is not visible after a login attempt.");
    logger.log(Status.PASS, "You are navigated to 'Landing' page after successful login.");

    //you have to logout in ios, because driver.resetApp(); won't logout you from the app
    landingPage.clickSettingsButton().clickLogoutButton();
  }

  @Test(retryAnalyzer = CustomRetryAnalyzer.class)
  public void iOSLogoutTest(){
    logger = extent.createTest("ID: 2 IOS: Login test");
    logger.assignCategory("Logout");
    logger.assignAuthor("Alexander Kostuchenko");

    LoginPage loginPage = new LoginPage(driver);
    if(IOS_TOKEN.isEmpty()) {
      log.info("There was no IOS_TOKEN from previous runs. Trying to get one.");
      GetYourTokenPage getYourTokenPage = loginPage.clickGetTokenLink();
      logger.log(Status.PASS, "Click 'go to site' link to navigate to 'Get your Token' page");

      YourTokenPage yourTokenPage = getYourTokenPage.getToYouTokenPage(TestConstants.Users.getUserOne(), TestConstants.Users.getUserOnePassword());
      logger.log(Status.PASS, "Enter user credentials a navigate to 'Your Token' page");

      IOS_TOKEN = yourTokenPage.getTokenValue();
      loginPage = yourTokenPage.goBackToLoginPage();
      logger.log(Status.PASS, "Get token and navigate to 'Login' page");
    }
    LandingPage landingPage = loginPage.setTokenAndLogin(IOS_TOKEN);
    logger.log(Status.PASS, "You are navigated to 'Landing' page after successful login.");

    SettingsPage settingsPage = landingPage.clickSettingsButton();
    loginPage = settingsPage.clickLogoutButton();

    Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "'Login' button is not visible after a logout attempt.");
    logger.log(Status.PASS, "You are navigated to 'Login' page after successful logout.");
  }
}
