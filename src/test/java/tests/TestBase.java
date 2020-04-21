package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.screenrecording.CanRecordScreen;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.DriverFactory;
import utils.LoggerBase;
import utils.PlatformType;
import utils.TestConstants;
import utils.TestUtils;

/**
 * Base class for all tests to extend.
 * Contains setup and cleanup actions as well as gets most of environment variables that you can pass by -D when running from command line.
 * ANDROID_DEVICE - sets Android emulator name that will be used to run tests. Defaults to "Pixel_2_API_28" if not set
 * IOS_DEVICE - sets iOS simulator name that will be used to run tests. Defaults to "iPhone X" if not set
 * RECORD_ALL_RUNS - if set to "true" all test run videos will be saved to <git_root>/test_reports.
 * If RECORD_ALL_RUNS is set to "false" only videos and screenshots of failed tests will be saved. Defaults to "false"
 */
public class TestBase extends LoggerBase {

  private static String testName = "DEFAULT";
  public static AppiumDriver driver;
  private static final String userDir = System.getProperty("user.dir");
  private static final String testReportsDir = TestConstants.Constants.getTestReportsDir(userDir);
  static final String testVideoRecordingsDir = TestConstants.Constants.getTestRecordingsDir(userDir);
  protected static String ANDROID_DEVICE = System.getenv("ANDROID_DEVICE");
  protected static String IOS_DEVICE = System.getenv("IOS_DEVICE");
  protected static boolean RECORD_ALL_RUNS = Boolean.parseBoolean(System.getenv("RECORD_ALL_RUNS"));
  protected static String ANDROID_TOKEN = "";
  protected static String IOS_TOKEN = TestConstants.Constants.getIosToken();
  public static ExtentReports extent;
  public static ExtentTest logger;

  /**
   * Executes before test suite.
   * Initializes HTML report and setups Android and iOS devices.
   *
   * @throws IOException the io exception if test report folder is not found
   */
  @BeforeSuite
  public void beforeSuite() throws IOException {
    log.info("ANDROID_DEVICE: ".concat(ANDROID_DEVICE));
    log.info("IOS_DEVICE: ".concat(IOS_DEVICE));
    log.info(String.format("RECORD_ALL_RUNS: %s", RECORD_ALL_RUNS));
    log.info(String.format("MAX_RETRIES: %s", TestConstants.Constants.getMaxRetries()));

    if (ANDROID_DEVICE == null || ANDROID_DEVICE.isEmpty()) {
      ANDROID_DEVICE = "Pixel_2_API_28";
    }
    if (IOS_DEVICE == null || IOS_DEVICE.isEmpty()) {
      IOS_DEVICE = "iPhone X";
    }

    File reportDir = new File(testReportsDir);
    if (!reportDir.exists()) {
      reportDir.mkdirs();
    } else if (!reportDir.isDirectory()) {
      throw new IOException("\"" + testReportsDir + "\" is not a directory.");
    }

    ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(new File(testReportsDir, "MobileAutomationTestReport.html"));
    extent = new ExtentReports();
    extent.attachReporter(htmlReporter);

    htmlReporter.config().setTheme(Theme.DARK);
    htmlReporter.config().setDocumentTitle("Automated Tests Run Report");
    htmlReporter.config().setReportName("Test Reporter");

    if (this.getClass().getAnnotation(MobileTest.class).name().toLowerCase().contains("android")) {
        TestUtils.setupAndroidEmulator(ANDROID_DEVICE, true);
    }
    if (this.getClass().getAnnotation(MobileTest.class).name().toLowerCase().contains("ios")) {
      TestUtils.setupIOSSimulator(IOS_DEVICE, true);
    }
  }

  /**
   * Executes before each test class.
   * Initializes the Appium driver.
   * @see DriverFactory#getDriver(PlatformType, String)
   *
   * @throws MalformedURLException the malformed url exception
   */
  @BeforeClass
  public void beforeClass() throws MalformedURLException {
    if (this.getClass().getAnnotation(MobileTest.class).name().toLowerCase().contains("ios")) {
      driver = DriverFactory.getDriver(PlatformType.iOS, IOS_DEVICE);

    } else if (this.getClass().getAnnotation(MobileTest.class).name().toLowerCase().contains("android")) {
      driver = DriverFactory.getDriver(PlatformType.ANDROID, ANDROID_DEVICE);
    }
  }

  /**
   * Executes before each test method.
   * Starts test execution video recording.
   * Resets the currently running app along with the session.
   *
   * @param method the test method from TestNG context. Parameter is retrieved by java.lang.reflect.
   */
  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method method) {
    testName = method.getName();
    log.info(String.format("Starting test: %s", testName));

    if (this.getClass().getAnnotation(MobileTest.class).name().toLowerCase().contains("ios") || this.getClass().getAnnotation(MobileTest.class).name().toLowerCase().contains("android")) {
      ((CanRecordScreen)driver).startRecordingScreen();
      driver.resetApp();
    }
  }

  /**
   * Executes after each test method.
   * Stops screen recording and saves test execution status(Pass, Fail or Skip) to the extent report
   *
   * @param method the method from TestNG context. Parameter is retrieved by java.lang.reflect.
   * @param result the Test execution results from TestNG context. Parameter is passed by TestNG.
   * @throws IOException the io exception
   */
  @AfterMethod
  public void afterMethod(Method method, ITestResult result) throws IOException {
    testName = method.getName();
    if (this.getClass().getAnnotation(MobileTest.class).name().toLowerCase().contains("ios") || this.getClass().getAnnotation(MobileTest.class).name().toLowerCase().contains("android")) {
      String filenameDateFormat = "yyyy-MM-dd_hh.mm";
      if(RECORD_ALL_RUNS) {
        TestUtils.stopRecording(driver, testVideoRecordingsDir, testName, filenameDateFormat);
      }
      try {
        if (result.getStatus() == ITestResult.FAILURE) {
          File file = driver.getScreenshotAs(OutputType.FILE);
          FileUtils.copyFile(
              file,
              new File(String.format(
                  "%s%s%s_%s.png",
                  testVideoRecordingsDir,
                  File.separator,
                  result.getName(),
                  LocalDateTime.now().format(DateTimeFormatter.ofPattern(filenameDateFormat)))
              )
          );
          logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to following issues:", ExtentColor.RED));
          logger.fail(result.getThrowable());

          if(!RECORD_ALL_RUNS) {
            TestUtils.stopRecording(driver, testVideoRecordingsDir, testName, filenameDateFormat);
          }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
          logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
        } else if (result.getStatus() == ITestResult.SKIP) {
          logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.BLUE));
        }
      } catch (NullPointerException e) {
        log.info("You should call 'extent.createTest()' in the begging of a test to save report.");
        e.printStackTrace();
      }
    }
  }

  /**
   * Runs after test suite.
   * Quits Appium driver if it's not null and forms test report.
   */
  @AfterSuite
  public void afterSuite() {
    extent.flush();
    try {
      if (driver != null) {
        driver.quit();
      }
    } catch (org.openqa.selenium.NoSuchSessionException e) {
      e.printStackTrace();
    }
  }
}