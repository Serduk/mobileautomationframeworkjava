package pages;

import device.actions.DeviceActions;
import device.actions.IDeviceActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerBase;
import utils.TestUtils;

/**
 * Base class for all PageObjects of application.
 * Class implements all the methods which all PageObjects should have
 */
public abstract class BasePage extends LoggerBase {

  public AppiumDriver driver;
  protected WebDriverWait wait;
  protected static int GLOBAL_WAIT_MODIFIER = 10;
  /**
   * Instantiates a new Base page.
   *
   * @param driver the Appium driver
   */
  public BasePage(AppiumDriver driver) {
    this.driver = driver;
    // global waits
    wait = new WebDriverWait(driver, GLOBAL_WAIT_MODIFIER);
  }

  /**
   * Initializes all fields of PageObject marked with annotations {@code @AndroidFindBy and @iOSXCUITFindBy} using PageFactory
   * with default timeout value stored in {@link BasePage#GLOBAL_WAIT_MODIFIER}
   *
   * @param pageName the name of page which is instantiating to put it in the logs
   */
  public void loadPage(String pageName) {
    log.info(String.format("Loading: %s", pageName));
    PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(GLOBAL_WAIT_MODIFIER)), this);
    this.waitTillPageLoading();
  }

  /**
   * Initializes all fields of PageObject marked with annotations {@code @AndroidFindBy and @iOSXCUITFindBy} using PageFactory
   * with the timeout passed in parameters.
   *
   * @param pageName the name of page which is instantiating to put it in the logs
   * @param implicitWaitLoadTime the implicit wait load time
   */
  public void loadPage(String pageName, Integer implicitWaitLoadTime) {
    log.info(String.format("Loading: %s", pageName));
    PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(implicitWaitLoadTime)), this);
    this.waitTillPageLoading();
  }

  /**
   * Method is waiting for visibility of specific element as detection that page is successfully loaded.
   * If element is not visible after the expected time or we caught an exception then
   * we reinitialize PageObject with posting message about Page loading timeout to the logs
   *
   * @param mobileElement the MobileElement which expected to have visible status
   */
  public void waitForVisibilityOfElement(MobileElement mobileElement) {
    try {
      wait.until(ExpectedConditions.visibilityOf(mobileElement));
    } catch (org.openqa.selenium.NoSuchElementException | NullPointerException | TimeoutException | StaleElementReferenceException e) {
      log.info(String.format(
          "Page loading timeout. Page \"%s\" was not detected as successfully loaded after %d seconds", this.getClass().getName(), GLOBAL_WAIT_MODIFIER)
      );
      PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(GLOBAL_WAIT_MODIFIER)), this);
    }
  }

  /**
   * Gets device actions.
   *
   * @return the device actions strategy
   * @see IDeviceActions
   */
  public IDeviceActions deviceActions() {
    return DeviceActions.getDeviceActions(driver);
  }

  /**
   * Get {@link By} with xpath or iosClassChain type depends on platform.
   * Method takes string with selector and returns {@link By} object with iOSClassChain selector for iOS platform and xpath for Android platform.
   * String with selector must have appropriate format of selector.
   *
   * @param selector string with the selector in appropriate format: iOSClassChain format for iOS platform and xpath format for Android.
   * @return {@link By} object with iOSClassChain selector for iOS platform and xpath for Android platform.
   */
  protected By getXpathOrIOSSelectorDependingOnCurrentOS(String selector){
    if (TestUtils.isIOSPlatform(driver)) {
      return MobileBy.iOSClassChain(selector);
    }
    else return MobileBy.xpath(selector);
  }

  /**
   * Abstract method to define condition of every child PageObject that page is loaded.
   * Example of definition in child classes:
   * {@code
   * public void waitTillPageLoading() {
   *     waitForVisibilityOfElement(loginButton);
   * }}
   * where {@code loginButton} is a MobileElement which is a part of page that child PageObject representing
   * @see BasePage#waitForVisibilityOfElement(MobileElement)
   */
  public abstract void waitTillPageLoading();

  /**
   * Wait the time from {@link BasePage#GLOBAL_WAIT_MODIFIER} till alert appears and accept it.
   */
  public void waitAlertAndAccept() {
    try {
      wait.until(ExpectedConditions.alertIsPresent());
      driver.switchTo().alert().accept();
    } catch (org.openqa.selenium.NoSuchElementException | NullPointerException | TimeoutException ignored) {
    }
  }
}
