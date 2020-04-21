package device.actions.ios;

import device.actions.IDeviceActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerBase;

/**
 * Device actions for iOS devices.
 */
public class IOSActions extends LoggerBase implements IDeviceActions {

  private AppiumDriver driver;
  private WebDriverWait wait;

  @Getter
  private long horizontalDuration = 900;

  @Getter
  private long verticalDuration = 2700;

  /**
   * Instantiates a new Ios actions.
   *
   * @param driver the Appium driver
   */
  public IOSActions(AppiumDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, 5);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clickSearch() {
    driver.findElement(MobileBy.iOSClassChain("**/XCUIElementTypeButton[$name == 'Search'$]")).click();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clickEnter() {
    clickSearch();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void swipeUp() {
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int startY = (int) (size.height * 0.85);
    int endY = (int) (size.height * 0.15);
    //using try catch here because WebDriverException is always thrown, when you invoke new TouchAction() in IOS
    try {
      driver.performTouchAction(new TouchAction(driver).press(PointOption.point(x, startY))
          .waitAction(WaitOptions.waitOptions(Duration.ofMillis(getVerticalDuration()))).moveTo(PointOption.point(x, endY)).release().perform());
    } catch (WebDriverException ignored) {
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void swipeDown() {
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int startY = (int) (size.height * 0.15);
    int endY = (int) (size.height * 0.85);
    //using try catch here because WebDriverException is always thrown, when you invoke new TouchAction() in IOS
    try {
      driver.performTouchAction(new TouchAction(driver).press(PointOption.point(x, startY))
          .waitAction(WaitOptions.waitOptions(Duration.ofMillis(getVerticalDuration()))).moveTo(PointOption.point(x, endY)).release().perform());
    } catch (WebDriverException ignored) {
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void elementSwipeLeft(MobileElement element) {
    int cordX = element.getCenter().getX();
    int cordY = element.getCenter().getY();

    int startX = (int) (cordX * 1.55);
    int endX = (int) (cordX * 0.05);
    //using try catch here because WebDriverException is always thrown, when you invoke new TouchAction() in IOS
    try {
      driver.performTouchAction(new TouchAction(driver).press(PointOption.point(startX, cordY))
          .waitAction(WaitOptions.waitOptions(Duration.ofMillis(getHorizontalDuration())))
          .moveTo(PointOption.point(endX, cordY)).release().perform());
    } catch (WebDriverException ignored) {
      //ignoring WebDriverException that is always thrown, when you invoke new TouchAction() in IOS
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void elementSwipeRight(MobileElement element) {
    int cordX = element.getCenter().getX();
    int cordY = element.getCenter().getY();

    int startX = (int) (cordX * 0.05);
    int endX = (int) (cordX * 1.55);
    //using try catch here because WebDriverException is always thrown, when you invoke new TouchAction() in IOS
    try {
      driver.performTouchAction(new TouchAction(driver).press(PointOption.point(startX, cordY))
          .waitAction(WaitOptions.waitOptions(Duration.ofMillis(getHorizontalDuration())))
          .moveTo(PointOption.point(endX, cordY)).release().perform());
    } catch (WebDriverException ignored) {
      //ignoring WebDriverException that is always thrown, when you invoke new TouchAction() in IOS
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MobileElement scrollDownToElement(String xpath) {
    int maxTimesToScroll = 25;
    List<MobileElement> searchList = driver.findElements(MobileBy.xpath(xpath));
    if (searchList.size() > 0) {
      return searchList.stream().findFirst().get();
    }

    for (int i = 0; i < maxTimesToScroll; i++) {
      try {
        swipeUp();
        searchList = driver.findElements(MobileBy.xpath(xpath));
        if (searchList.size() > 0) {
          return searchList.stream().findFirst().get();
        }
      } catch (NoSuchElementException ex) {
        log.info("Could not find element with following locator: ".concat(xpath));
        ex.printStackTrace();
      }
    }
    throw new NotFoundException("Could not find element with following locator: ".concat(xpath));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void centerElement(MobileElement element) {
    Dimension size = driver.manage().window().getSize();
    int endY = size.height / 2;

    int startX = element.getCenter().getX();
    int startY = element.getCenter().getY();

    //using try catch here because WebDriverException is always thrown, when you invoke new TouchAction() in IOS
    try {
      driver.performTouchAction(new TouchAction(driver).press(PointOption.point(startX, startY))
          .waitAction(WaitOptions.waitOptions(Duration.ofMillis(verticalDuration))).moveTo(PointOption.point(startX, endY)).release().perform());
    } catch (WebDriverException ignored) {
      //ignoring WebDriverException that is always thrown, when you invoke new TouchAction() in IOS
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clickOpenButtonInNativeWebBrowser(){
    List<WebElement> openButton = driver.findElements(MobileBy.iOSClassChain("**/XCUIElementTypeButton[$name == 'Open'$]"));
    if(openButton.size() > 0){
      openButton.iterator().next().click();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCurrentBrowserUrl(){
    String currentUrl = "";
    String urlAddressLocator = "**/XCUIElementTypeOther/XCUIElementTypeButton[$name == 'URL'$]";
    wait.pollingEvery(Duration.ofSeconds(1)).until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(
            MobileBy.iOSClassChain(urlAddressLocator)
        )
    );
    List<WebElement> openButton = driver.findElements(MobileBy.iOSClassChain(urlAddressLocator));
    if(openButton.size() > 0){
      return openButton.iterator().next().getAttribute("value");
    }
    return currentUrl;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clickOnLinkInNativeWebBrowserWithRetries(String url, int retries){
    throw new NotImplementedException("Not implemented. Use driver.get('url') for IOS instead.");
  }

}
