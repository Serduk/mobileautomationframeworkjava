package device.actions.android;

import device.actions.IDeviceActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;
import java.util.List;
import lombok.Getter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CommandExecutor;
import utils.LoggerBase;

/**
 * Device actions for Android devices.
 */
public class AndroidActions extends LoggerBase implements IDeviceActions {

  private AppiumDriver driver;
  @Getter
  private long duration = 800;
  private WebDriverWait wait;
  private static final int INTERNAL_TIMEOUT_IN_SECONDS = 5;

  /**
   * Invokes a keyevent via adb shell.
   * Android studio tools' directory should be added to a system PATH to make this work.
   * Refer to readme.md 'Environment Setup' section for more info
   */
  @Getter
  private String keyEventCommand = "adb shell input keyevent ";

  /**
   * Instantiates a new Android actions.
   *
   * @param driver the Appium driver
   */
  public AndroidActions(AppiumDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, INTERNAL_TIMEOUT_IN_SECONDS);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clickSearch() {
    executeKeyEventCommand(AndroidKey.SEARCH.getCode());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clickEnter() {
    executeKeyEventCommand(AndroidKey.ENTER.getCode());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void swipeUp() {
    Dimension size = driver.manage().window().getSize();

    int x = size.width / 2;

    int startY = (int) (size.height * 0.8);

    int endY = (int) (size.height * 0.3);

    driver.performTouchAction(new TouchAction(driver).press(PointOption.point(x, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(getDuration()))
    ).moveTo(PointOption.point(x, endY)).release().perform());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void swipeDown() {
    Dimension size = driver.manage().window().getSize();

    int x = size.width / 2;

    int startY = (int) (size.height * 0.3);

    int endY = (int) (size.height * 0.8);

    driver.performTouchAction(new TouchAction(driver).press(PointOption.point(x, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(getDuration()))
    ).moveTo(PointOption.point(x, endY)).release().perform());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void elementSwipeLeft(MobileElement element) {
    int cordX = element.getCenter().getX();
    int cordY = element.getCenter().getY();

    int startX = (int) (cordX * 1.95);
    int endX = (int) (cordX * 0.05);

    driver.performTouchAction(new TouchAction(driver).press(PointOption.point(startX, cordY))
        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(getDuration()))).moveTo(PointOption.point(endX, cordY)).release().perform());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void elementSwipeRight(MobileElement element) {
    int cordX = element.getCenter().getX();
    int cordY = element.getCenter().getY();

    int startX = (int) (cordX * 0.05);
    int endX = (int) (cordX * 1.95);

    driver.performTouchAction(new TouchAction(driver).press(PointOption.point(startX, cordY))
        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(getDuration()))).moveTo(PointOption.point(endX, cordY)).release().perform());
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

    //Do not change duration odf wait here. You might break Test ID: 24
    driver.performTouchAction(new TouchAction(driver).press(PointOption.point(startX, startY))
        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(getDuration() * 2))).moveTo(PointOption.point(startX, endY)).release().perform());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clickOpenButtonInNativeWebBrowser(){
    List<WebElement> openButton =  driver.findElements(MobileBy.xpath("//*[contains(@text,'Open')]"));
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
    String urlAddressLocator = "com.android.chrome:id/url_bar";
    wait.pollingEvery(Duration.ofSeconds(1)).until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(
            MobileBy.id(urlAddressLocator)
        )
    );
    List<WebElement> openButton = driver.findElements(MobileBy.id(urlAddressLocator));
    if(openButton.size() > 0){
      return openButton.iterator().next().getAttribute("text");
    }
    return currentUrl;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clickOnLinkInNativeWebBrowserWithRetries(String url, int retries) {
    String urlLinksLocator = String.format("//android.view.View[@text = '%s']", url);
    for(int i = 0; i < retries; retries++) {
      List<MobileElement> urlLinks = driver.findElements(MobileBy.xpath(urlLinksLocator));
      if(urlLinks.size() > 0) {
        MobileElement urlElement = urlLinks.iterator().next();
        centerElement(urlElement);
        urlElement.click();
        return;
      } else {
        swipeUp();
      }
    }
    throw new NoSuchElementException(String.format("Could not find following link: %s within %s retries", url, retries));
  }

  private void executeKeyEventCommand(int keyEventCode) {
    executeCommand(getKeyEventCommand() + keyEventCode);
  }

  private void executeCommand(String command) {
    CommandExecutor.executeCommand(command, false);
  }

}
