package device.actions;

import io.appium.java_client.MobileElement;

/**
 * The interface Device actions strategy.
 * Interface defined structure of Device actions for polymorphic usage of device actions like swiping, scrolling. etc.
 */
public interface IDeviceActions {

  /**
   * Click search on the virtual keyboard.
   */
  public void clickSearch();

  /**
   * Click enter on the virtual keyboard.
   */
  public void clickEnter();

  /**
   * Perform swipe up action.
   */
  public void swipeUp();

  /**
   * Perform swipe down action.
   */
  public void swipeDown();

  /**
   * Perform swipe left action at element's coordinates.
   *
   * @param element the {@link MobileElement} where swipe action should be performed.
   */
  public void elementSwipeLeft(MobileElement element);

  /**
   * Perform swipe right action at element's coordinates.
   *
   * @param element the {@link MobileElement} where swipe action should be performed.
   */
  public void elementSwipeRight(MobileElement element);

  /**
   * Scroll down to the element.
   * Method swipes down till element becomes visible at screen
   *
   * @param xpath the xpath of element
   * @return the {@link MobileElement} of searched element
   */
  public MobileElement scrollDownToElement(String xpath);

  /**
   * Center element.
   * Method scrolls view vertically in order to make the element located in the center of the screen
   *
   * @param element the {@link MobileElement} which should be moved to the center of the screen.
   */
  public void centerElement(MobileElement element);

  /**
   * Click open button in native web browser.
   */
  public void clickOpenButtonInNativeWebBrowser();

  /**
   * Gets current browser url.
   *
   * @return string with current browser url
   */
  public String getCurrentBrowserUrl();

  /**
   * Click on link in native web browser with some count of retries.
   *
   * @param url     the url
   * @param retries the count of retries
   * @throws InterruptedException the interrupted exception
   */
  public void clickOnLinkInNativeWebBrowserWithRetries(String url, int retries) throws InterruptedException;

}
