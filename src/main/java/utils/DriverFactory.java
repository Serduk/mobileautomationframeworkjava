package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * The Appium Driver factory.
 */
public class DriverFactory extends LoggerBase {

  private static final int IMPLICIT_WAIT_TIME = 3;

  /**
   * Creates driver instance depending on a platform you specify.
   *
   * @param platformType the mobile {@link PlatformType} (i.e. iOS, Android)
   * @param deviceName   the device name
   * @return the Appium driver object
   * @throws MalformedURLException if a malformed URL has occurred
   */
  public static AppiumDriver getDriver(PlatformType platformType, String deviceName) throws MalformedURLException {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    AppiumDriver driver = null;
    URL serverAddress = new URL("http://127.0.0.1:4723/wd/hub");

    switch (platformType) {
      case iOS: {
        List<String> appPaths = TestUtils.getAppPaths();
        if (!appPaths.isEmpty()) {
          String appPath = appPaths
              .stream()
              .filter(app -> app.contains(".app"))
              .collect(Collectors.toList())
              .iterator()
              .next();
          capabilities.setCapability(MobileCapabilityType.APP, appPath);
        } else {
          log.info(String.format("Didn't find any *.app at %s... Trying to launch existing application.", TestConstants.Constants.getLocalAppBasePath()));
          capabilities.setCapability("bundleId", TestConstants.Constants.getIosBundleId());
        }

        List<String> devices = TestUtils.getConnectedDevicesUDID(platformType);
        if (devices.iterator().hasNext()) {
          capabilities.setCapability(MobileCapabilityType.UDID, devices.iterator().next());
        } else {
          log.info(String.format("Didn't find any launched Simulators or connected devices. Trying to launch simulator with name: %s", deviceName));
          capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        }
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, TestConstants.Constants.getIosAutomationName());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, TestConstants.Constants.getIosPlatformName());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, TestConstants.Constants.getIosPlatformVersion());
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, TestConstants.Constants.getSessionStartTimeout());

        driver = new IOSDriver(serverAddress, capabilities);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
        break;
      }
      case ANDROID: {
        List<String> apkPaths = TestUtils.getAppPaths();
        if (!apkPaths.isEmpty()) {
          String apkPath = apkPaths
              .stream()
              .filter(apk -> apk.contains(".apk"))
              .collect(Collectors.toList())
              .iterator()
              .next();
          capabilities.setCapability(MobileCapabilityType.APP, apkPath);
        } else {
          log.info(String.format("Didn't find any *.apk at %s... Trying to launch existing application.", TestConstants.Constants.getLocalAppBasePath()));
          capabilities.setCapability("appActivity", TestConstants.Constants.getAppActivity());
          capabilities.setCapability("appPackage", TestConstants.Constants.getAppPackage());
        }

        List<String> devices = TestUtils.getConnectedDevicesUDID(platformType);
        if (devices.iterator().hasNext()) {
          capabilities.setCapability(MobileCapabilityType.UDID, devices.iterator().next());
        } else {
          log.info(String.format("Didn't find any launched ABDs or connected devices. Trying to launch emulator with name: %s", deviceName));
          capabilities.setCapability("avd", deviceName);
        }
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, TestConstants.Constants.getAndroidDeviceName());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, TestConstants.Constants.getAndroidPlatformName());
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, TestConstants.Constants.getSessionStartTimeout());
        capabilities.setCapability("autoDismissAlerts", true);
        capabilities.setCapability("androidInstallTimeout", 180000);
        capabilities.setCapability("adbExecTimeout", 40000);
        capabilities.setCapability("uiautomator2ServerInstallTimeout", 40000);

        driver = new AndroidDriver(serverAddress, capabilities);
        break;
      }
    }
    return driver;
  }
}