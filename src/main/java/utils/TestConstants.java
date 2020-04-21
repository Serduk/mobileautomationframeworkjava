package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import lombok.Getter;

/**
 * The Test constants class is used to get test properties from test.properties file.
 *
 * For example:
 *
 * {@code user_one} - username
 * {@code ser_one_password} - user password
 * {@code app_package} - package name of test app for the android platform (i.e. com.lohika.parksharing)
 * {@code ios_bundle_id} - package name of test app for the iOS platform (i.e. com.lohika.Parksharing)
 */
public class TestConstants {

  private static Properties prop = PropertiesManagement.getInstance().loadProps("test.properties");

  /**
   * The Users class to get properties of test users from test.properties file.
   */
  public static final class Users {
    @Getter
    private static final String userOne = prop.getProperty("user_one");

    @Getter
    private static final String userOnePassword = prop.getProperty("user_one_password");

  }

  /**
   * The Constants class to get test data from test.properties file.
   */
  public static final class Constants {

    @Getter
    private static final String appPackage = prop.getProperty("app_package");

    @Getter
    private static final String appActivity = prop.getProperty("app_activity");

    @Getter
    private static final String androidAppKeyword = prop.getProperty("android_app_keyword");

    @Getter
    private static final String iOSAppKeyword = prop.getProperty("ios_app_keyword");

    @Getter
    private static final String iosBundleId = prop.getProperty("ios_bundle_id");

    @Getter
    private static final String androidDeviceName = prop.getProperty("android_device_name");

    @Getter
    private static final String iosPlatformVersion = prop.getProperty("ios_platform_version");

    @Getter
    private static final String androidPlatformName = prop.getProperty("android_platform_name");

    @Getter
    private static final String iosPlatformName = prop.getProperty("ios_platform_name");

    @Getter
    private static final String androidAutomationName = prop.getProperty("android_automation_name");

    @Getter
    private static final String iosAutomationName = prop.getProperty("ios_automation_name");

    @Getter
    private static final int sessionStartTimeout = Integer.parseInt(prop.getProperty("session_start_timeout"));

    /**
     * Returns the base path of a directory with apps.
     *
     * @return the base path of a directory with apps
     */
    public static String getLocalAppBasePath() {
      return String.format("%sapp", File.separator);
    }

    /**
     * Returns the path of a directory with test videos.
     *
     * @param userDir the path to the project's root directory
     * @return the path to the directory with test videos
     */
    public static String getTestRecordingsDir(String userDir) {
      return String.format(
          "%s%stest_reports%stest_videos_and_screenshots%s%s%s",
          userDir,
          File.separator,
          File.separator,
          File.separator,
          new SimpleDateFormat("MM-dd-yyyy").format(new Date()),
          File.separator
      );
    }

    /**
     * Returns the path of test reports directory.
     *
     * @param userDir the path to the project's root directory
     * @return the path of test reports directory
     */
    public static String getTestReportsDir(String userDir) {
      return String.format(
          "%s%stest_reports%s",
          userDir,
          File.separator,
          File.separator
      );
    }

    /**
     * Returns the maximum amount of retires before test will be marked as failed. Defaults to 2 if not set
     *
     * @return the path of test reports directory
     */
    public static int getMaxRetries(){
      return ((System.getenv("MAX_RETRIES") == null) || (System.getenv("MAX_RETRIES").isEmpty())) ? 2 : Integer.parseInt(System.getenv("MAX_RETRIES"));
    }

    /**
     * Gets ios token from properties file
     *
     */
    @Getter
    public static String iosToken = prop.getProperty("ios_prod_token");

    /**
     * Gets android token from properties file
     *
     */
    @Getter
    public static String androidToken = prop.getProperty("android_prod_token");

    /**
     * Gets local iOS simulator path excluding /Users/<username>
     *
     * @return String, local iOS simulator path
     */
    public static String getLocalIOSSimulatorPath() {
      return String
          .format("%sLibrary/Developer/CoreSimulator/Devices", File.separator);
    }

    /**
     * Gets local iOS simulator keychain path excluding /Users/<username>
     *
     * @return String, local iOS simulator keychain path
     */
    public static String getLocalIOSSimulatorKeychainsPath() {
      return String
          .format("%sdata/Library/Keychains", File.separator);
    }
  }
}
