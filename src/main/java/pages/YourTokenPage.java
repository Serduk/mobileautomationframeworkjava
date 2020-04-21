package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.TestConstants;
import utils.TestUtils;

public class YourTokenPage extends BasePage {

  @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[$name == 'Parking admin'$]")
  @AndroidFindBy(className = "android.webkit.WebView")
  MobileElement webViewContainer;

  @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[$name == 'Parking admin'$]/XCUIElementTypeOther/XCUIElementTypeStaticText[2]")
  @AndroidFindBy(xpath = "//android.webkit.WebView/android.view.View[3]")
  MobileElement tokenValue;

  @iOSXCUITFindBy(accessibility = "Back")
  MobileElement iOSBackButton;

  /**
   * Instantiates a new Base page.
   *
   * @param driver the Appium driver
   */
  public YourTokenPage(AppiumDriver driver) {
    super(driver);
    loadPage(getClass().getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void waitTillPageLoading() {
    waitForVisibilityOfElement(webViewContainer);
  }

  public String getTokenValue(){
    return tokenValue.getText();
  }

  public LoginPage goBackToLoginPage(){
    if(TestUtils.isIOSPlatform(driver)){
      iOSBackButton.click();
    } else {
      TestUtils.launchAppOnAndroid((AndroidDriver) driver, TestConstants.Constants.getAppPackage(), TestConstants.Constants.getAppActivity());
    }

    return new LoginPage(driver);
  }
}
