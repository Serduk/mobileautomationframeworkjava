package device.actions;

import device.actions.android.AndroidActions;
import io.appium.java_client.AppiumDriver;
import device.actions.ios.IOSActions;
import utils.TestUtils;

/**
 * Class container for DeviceActions.
 * Class creates implementation of IDeviceActions depending on platform.
 * Platform is set automatically depending on which implementation of AppiumDriver is passed.
 */
public class DeviceActions {

  /**
   * Get device actions.
   *
   * @return the device actions strategy.
   * @see IDeviceActions
   */
  public static IDeviceActions getDeviceActions(AppiumDriver driver){
    if (TestUtils.isIOSPlatform(driver))
     return new IOSActions(driver);
    else {
      return new AndroidActions(driver);
    }
  }
}
