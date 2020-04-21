package tests.android.login;

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

@MobileTest(name = "android")
public class AndroidLoginTests extends TestBase {

  @Test(retryAnalyzer = CustomRetryAnalyzer.class)
  public void androidLoginTest(){
    logger = extent.createTest("ID: 1 Android: Login test");
    logger.assignCategory("Login");
    logger.assignAuthor("Alexander Kostuchenko");

    LoginPage loginPage = new LoginPage(driver);
    if(ANDROID_TOKEN.isEmpty()) {
      log.info("There was no ANDROID_TOKEN from previous runs. Trying to get one.");
      GetYourTokenPage getYourTokenPage = loginPage.clickGetTokenLink();
      logger.log(Status.PASS, "Click 'go to site' link to navigate to 'Get your Token' page");

      YourTokenPage yourTokenPage = getYourTokenPage.getToYouTokenPage(TestConstants.Users.getUserOne(), TestConstants.Users.getUserOnePassword());
      logger.log(Status.PASS, "Enter user credentials a navigate to 'Your Token' page");

      ANDROID_TOKEN = yourTokenPage.getTokenValue();
      loginPage = yourTokenPage.goBackToLoginPage();
      logger.log(Status.PASS, "Get token and navigate to 'Login' page");
    }

    LandingPage landingPage = loginPage.setTokenAndLogin(ANDROID_TOKEN);
    Assert.assertTrue(landingPage.isSettingsButtonVisible(), "'Settings' button is not visible after a login attempt.");
    logger.log(Status.PASS, "You are navigated to 'Landing' page after successful login.");
  }

  @Test(retryAnalyzer = CustomRetryAnalyzer.class)
  public void androidLogoutTest(){
    logger = extent.createTest("ID: 2 Android: Login test");
    logger.assignCategory("Logout");
    logger.assignAuthor("Alexander Kostuchenko");

    LoginPage loginPage = new LoginPage(driver);
    if(ANDROID_TOKEN.isEmpty()) {
      log.info("There was no ANDROID_TOKEN from previous runs. Trying to get one.");
      GetYourTokenPage getYourTokenPage = loginPage.clickGetTokenLink();
      logger.log(Status.PASS, "Click 'go to site' link to navigate to 'Get your Token' page");

      YourTokenPage yourTokenPage = getYourTokenPage.getToYouTokenPage(TestConstants.Users.getUserOne(), TestConstants.Users.getUserOnePassword());
      logger.log(Status.PASS, "Enter user credentials a navigate to 'Your Token' page");

      ANDROID_TOKEN = yourTokenPage.getTokenValue();
      loginPage = yourTokenPage.goBackToLoginPage();
      logger.log(Status.PASS, "Get token and navigate to 'Login' page");
    }
    LandingPage landingPage = loginPage.setTokenAndLogin(ANDROID_TOKEN);
    logger.log(Status.PASS, "You are navigated to 'Landing' page after successful login.");

    SettingsPage settingsPage = landingPage.clickSettingsButton();
    loginPage = settingsPage.clickLogoutButton();

    Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "'Login' button is not visible after a logout attempt.");
    logger.log(Status.PASS, "You are navigated to 'Login' page after successful logout.");
  }

}
