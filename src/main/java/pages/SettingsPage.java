package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingsPage extends BasePage {

  @AndroidFindBy(id = "settingsProfile")
  @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell/XCUIElementTypeStaticText[1]")
  MobileElement profileText;

  @AndroidFindBy(id = "settingsAbout")
  @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell/XCUIElementTypeStaticText[$name CONTAINS 'bout'$]")
  MobileElement aboutButton;

  @AndroidFindBy(id = "settingsFeedback")
  @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell/XCUIElementTypeStaticText[$name CONTAINS 'eedback'$]")
  MobileElement feedbackButton;

  @AndroidFindBy(id = "settingsLogout")
  @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[$name=='LOGOUT'$]")
  MobileElement logoutButton;

  @AndroidFindBy(id = "Navigate up")
  @iOSXCUITFindBy(accessibility = "Back")
  MobileElement backButton;

  /**
   * Instantiates a new Base page.
   *
   * @param driver the Appium driver
   */
  public SettingsPage(AppiumDriver driver) {
    super(driver);
    loadPage(getClass().getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void waitTillPageLoading() {
    waitForVisibilityOfElement(profileText);
  }

  public String getProfileText(){
    return profileText.getText();
  }

  public LandingPage clickBackButton(){
    backButton.click();
    return new LandingPage(driver);
  }

  public LoginPage clickLogoutButton(){
    logoutButton.click();
    return new LoginPage(driver);
  }
}
