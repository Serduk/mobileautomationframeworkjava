package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LandingPage extends BasePage {

  @AndroidFindBy(id = "action_settings")
  @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeNavigationBar[$name == 'ParkSharing.ParkingLotView'$]/XCUIElementTypeButton[2]")
  MobileElement settingsButton;

  /**
   * Instantiates a new Base page.
   *
   * @param driver the Appium driver
   */
  public LandingPage(AppiumDriver driver) {
    super(driver);
    loadPage(getClass().getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void waitTillPageLoading() {
    waitForVisibilityOfElement(settingsButton);
  }

  public boolean isSettingsButtonVisible(){
    try {
      return settingsButton.isDisplayed();
    } catch (org.openqa.selenium.NoSuchElementException e) {
      return false;
    }
  }

  public SettingsPage clickSettingsButton(){
    settingsButton.click();
    return new SettingsPage(driver);
  }
}
