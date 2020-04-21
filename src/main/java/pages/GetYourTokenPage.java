package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class GetYourTokenPage extends BasePage {

  @AndroidFindBy(className = "android.widget.Button")
  @iOSXCUITFindBy(className = "XCUIElementTypeButton")
  MobileElement nextButton;

  @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='userNameInput']")
  @iOSXCUITFindBy(className = "XCUIElementTypeStaticText")
  MobileElement loginInputField;

  @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='passwordInput']")
  @iOSXCUITFindBy(className = "XCUIElementTypeSecureTextField")
  MobileElement passwordInputField;

  @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='submitButton']")
  @iOSXCUITFindBy(accessibility = "Sign in")
  MobileElement submitButton;

  /**
   * Instantiates a new Base page.
   *
   * @param driver the Appium driver
   */
  public GetYourTokenPage(AppiumDriver driver) {
    super(driver);
    loadPage(getClass().getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void waitTillPageLoading() {
    waitForVisibilityOfElement(nextButton);
  }

  public YourTokenPage getToYouTokenPage(String user, String password){
   driver.getPageSource();

    setUserName(user);
    clickNextButton();
    setPassword(password);
    return clickSubmitButton();
  }

  public void setUserName(String userName){
    loginInputField.clear();
    loginInputField.setValue(userName);
  }

  public void clickNextButton(){
    nextButton.click();
  }

  public void setPassword(String password) {
    passwordInputField.clear();
    passwordInputField.setValue(password);
  }

  public YourTokenPage clickSubmitButton(){
    submitButton.click();
    return new YourTokenPage(driver);
  }

}
