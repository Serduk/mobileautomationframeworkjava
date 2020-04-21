package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.offset.PointOption;
import utils.TestUtils;

public class LoginPage extends BasePage {

  @AndroidFindBy(id = "subtitle")
  @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[$name CONTAINS 'the site'$]")
  MobileElement getTokenLink;

  @AndroidFindBy(id = "loginButton")
  @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[$name == 'LOGIN'$]")
  MobileElement loginButton;

  @AndroidFindBy(id = "authTokenEditText")
  @iOSXCUITFindBy(className = "XCUIElementTypeTextField")
  MobileElement tokenInputField;

  /**
   * Instantiates a new Login page.
   *
   * @param driver the Appium driver
   */
  public LoginPage(AppiumDriver driver) {
    super(driver);
    loadPage(getClass().getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void waitTillPageLoading() {
    waitForVisibilityOfElement(loginButton);
  }

  /**
   * Click login button.
   */
  public GetYourTokenPage clickGetTokenLink(){
    if(TestUtils.isIOSPlatform(driver)){
      getTokenLink.click();
    } else {
      int centerX = getTokenLink.getCenter().getX();
      int centerY = getTokenLink.getCenter().getY();
      TouchAction action = new TouchAction(driver);
      action.press(PointOption.point(centerX, centerY)).release().perform();
      TestUtils.clickAcceptAndContinueButtonInGoogleChromeIfPresent(driver);
      TestUtils.clickNoThanksButtonGoogleChromeIfPresent(driver);
    }

    return new GetYourTokenPage(driver);
  }

  /**
   * Set token and click login button.
   * @param token token value
   * @return {@link LandingPage} returns new instance of Landing page class
   */
  public LandingPage setTokenAndLogin(String token) {
    tokenInputField.clear();
    tokenInputField.sendKeys(token);
    loginButton.click();
    waitAlertAndAccept();
    return new LandingPage(driver);
  }

  /**
   * Verify if loginButton is displayed
   * @return boolean "true" if loginButton is displayed
   */
  public boolean isLoginButtonDisplayed(){
    try {
      return loginButton.isDisplayed();
    } catch (org.openqa.selenium.NoSuchElementException e) {
      return false;
    }
  }

}
