package utils;

import entities.ComAppleCoreSimulatorSimRuntimeIOS124;
import entities.ComAppleCoreSimulatorSimRuntimeIOS131;
import entities.ComAppleCoreSimulatorSimRuntimeIOS132;
import entities.ComAppleCoreSimulatorSimRuntimeIOS133;
import entities.ComAppleCoreSimulatorSimRuntimeIOS134;
import entities.SimctlListOutput;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.screenrecording.CanRecordScreen;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Finder;
import utils.TestConstants.Constants;

/**
 * Class TestUtils contains utility methods that you can use in Mobile UI tests or pretty much anywhere in project.
 * All methods are static, so you can call any method from TestUtils directly.
 *
 * Short list of such methods:
 * {@link #setupAndroidEmulator(String, boolean)} - Launches an android emulator in the writable mode and copies the SSL cert and the hosts file to a testing device.
 * {@link #setupIOSSimulator(String, boolean)} - Launches an iOS simulator and sets the SSL cert and the hosts file.
 * {@link #takeScreenshotOfElement(MobileElement)} - Takes screenshot of the mobile element.
 * {@link #stopRecording(AppiumDriver, String, String, String)} - Stops screen recording of an emulator or a simulator,
 * creates directory of given name if it doesn't exist and stores screen recordings there.
 */
public class TestUtils extends LoggerBase {

  private static String OS = System.getProperty("os.name").toLowerCase();

  /**
   * Method to get udid of connected real device, simulator or emulator.
   *
   * @param platformType the platform type - iOS, Android
   * @see PlatformType
   * @return the udid of connected device
   */
  static List<String> getConnectedDevicesUDID(PlatformType platformType) {
    List<String> connectedDevices = new ArrayList<>();
    switch (platformType) {
      case iOS: {
        connectedDevices.addAll(getConnectedRealIOSDevicesAndSimulatorsUDID());
        break;
      }
      case ANDROID: {
        connectedDevices.addAll(getConnectedRealAndroidDevicesAndEmulatorsUDID());
        break;
      }
    }

    return connectedDevices;
  }

  /**
   * Method to get a list of app paths(.app, .apk).
   *
   * @return the list with app paths
   */
  public static List<String> getAppPaths() {
    List<String> appPaths = new ArrayList<>();
    final File appsRootFolder = Paths
        .get(System.getProperty("user.dir"), TestConstants.Constants.getLocalAppBasePath())
        .toAbsolutePath().toFile();
    List<File> appFiles = new ArrayList<>();
    FileManagement.searchForAppFiles(appsRootFolder, appFiles);
    String apkName = TestConstants.Constants.getAndroidAppKeyword();
    String appName = TestConstants.Constants.getIOSAppKeyword();

    for (File fileEntry : appFiles) {
      if (fileEntry.getAbsolutePath().contains(apkName) || fileEntry.getAbsolutePath().contains(appName)) {
        String appFullPath = fileEntry.getAbsolutePath();
        appPaths.add(appFullPath);
        log.info(String.format("app is located at: %s", appFullPath));
      }
    }

    return appPaths;
  }


  private static String getFileExtension(String fileNameOfAbsolutePath){
    String[] name = fileNameOfAbsolutePath.split("\\.");
    return  name[name.length - 1];
  }

  private static List<String> getConnectedRealAndroidDevicesAndEmulatorsUDID() {
    List<String> connectedDevices = new ArrayList<>();
    String commandToExecute;
    if (OS.contains("win")) {
      commandToExecute = "cmd /c adb devices";
    } else {
      commandToExecute = "adb devices";
    }
    String[] commandResult = CommandExecutor.executeCommand(commandToExecute, true).split("\n");
    log.info(Arrays.toString(commandResult));
    for (String device : commandResult) {
      if (device.contains("\tdevice")) {
        connectedDevices.add(device.substring(0, device.indexOf("\t")));
      }
    }
    if (connectedDevices.size() == 0) {
      System.out
          .println("There are no connected devices. \n Check the 'adb devices' command output");
    }

    return connectedDevices;
  }

  private static List<String> getConnectedRealIOSDevicesAndSimulatorsUDID() {
    List<String> bootedDevices = new ArrayList<>();

    List<String> realIOSDevicesUDID = getListOfRealIOSDevicesUDID();
    if (!realIOSDevicesUDID.isEmpty()) {
      bootedDevices.addAll(getListOfRealIOSDevicesUDID());
      return bootedDevices;
    }

    List<String> iOSSimulatorsUDID = getConnectedSimulatorsUDID();
    if (!iOSSimulatorsUDID.isEmpty()) {
      bootedDevices.addAll(getConnectedSimulatorsUDID());
    }
    return bootedDevices;
  }

  private static List<String> getConnectedSimulatorsUDID() {
    List<String> bootedDevices = new ArrayList<>();

    try {
      String commandToExecute = "xcrun simctl list --json";
      String commandResult = CommandExecutor.executeCommand(commandToExecute, true);
      SimctlListOutput result = JsonConverter
          .deserializeToCustomClass(commandResult, SimctlListOutput.class);

      List<ComAppleCoreSimulatorSimRuntimeIOS132> bootedIOS132Devices = result.getDevices()
          .getComAppleCoreSimulatorSimRuntimeIOS132()
          .stream()
          .filter(device -> device.getState().equalsIgnoreCase("Booted"))
          .collect(Collectors.toList());
      List<ComAppleCoreSimulatorSimRuntimeIOS131> bootedIOS131Devices = result.getDevices()
          .getComAppleCoreSimulatorSimRuntimeIOS131()
          .stream()
          .filter(device -> device.getState().equalsIgnoreCase("Booted"))
          .collect(Collectors.toList());
      List<ComAppleCoreSimulatorSimRuntimeIOS124> bootedIOS124Devices = result.getDevices()
          .getComAppleCoreSimulatorSimRuntimeIOS124()
          .stream()
          .filter(device -> device.getState().equalsIgnoreCase("Booted"))
          .collect(Collectors.toList());
      List<ComAppleCoreSimulatorSimRuntimeIOS133> bootedIOS133Devices = result.getDevices()
          .getComAppleCoreSimulatorSimRuntimeIOS133()
          .stream()
          .filter(device -> device.getState().equalsIgnoreCase("Booted"))
          .collect(Collectors.toList());
      List<ComAppleCoreSimulatorSimRuntimeIOS134> bootedIOS134Devices = result.getDevices()
          .getComAppleCoreSimulatorSimRuntimeIOS134()
          .stream()
          .filter(device -> device.getState().equalsIgnoreCase("Booted"))
          .collect(Collectors.toList());

      bootedDevices.addAll(
          bootedIOS133Devices.stream().map(ComAppleCoreSimulatorSimRuntimeIOS133::getUdid)
              .collect(Collectors.toList())
      );
      bootedDevices.addAll(
          bootedIOS132Devices.stream().map(ComAppleCoreSimulatorSimRuntimeIOS132::getUdid)
              .collect(Collectors.toList())
      );
      bootedDevices.addAll(
          bootedIOS131Devices.stream().map(ComAppleCoreSimulatorSimRuntimeIOS131::getUdid)
              .collect(Collectors.toList())
      );
      bootedDevices.addAll(
          bootedIOS124Devices.stream().map(ComAppleCoreSimulatorSimRuntimeIOS124::getUdid)
              .collect(Collectors.toList())
      );
      bootedDevices.addAll(
      bootedIOS134Devices.stream().map(ComAppleCoreSimulatorSimRuntimeIOS134::getUdid)
          .collect(Collectors.toList())
      );

    } catch (IOException | NullPointerException e) {
      log.error("There was a problem while getting list of real iOS devices. Make sure you have all the entities for deserialization of 'xcrun simctl list --json' command result");
    }

    if (bootedDevices.size() == 0) {
      log.info(
          "There are no booted ios devices. Check the 'xcrun simctl list' command output");
    }
    return bootedDevices;
  }

  private static List<String> getListOfRealIOSDevicesUDID() {
    List<String> connectedDevices = new ArrayList<>();
    String commandToExecute = "idevice_id -l";
    String commandResult = CommandExecutor.executeCommand(commandToExecute, true);
    if (!commandResult.isEmpty()) {
      String[] commandResults = commandResult.split("\n");
      connectedDevices = Arrays.asList(commandResults);
    }

    if (connectedDevices.size() == 0) {
      log.info(
          "There are no connected ios devices. Check the 'idevice_id -l' command output");
    }
    return connectedDevices;
  }

  /**
   * Method to get the local IP address.
   *
   * @return the local ip address
   */
  public static String getLocalIpAddress() {
    String localIpAddress;
    if (OS.contains("win")) {
      String IP_ADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

      String defaultGateway = Arrays
          .stream(
              CommandExecutor
                  .executeCommand("cmd /c ipconfig | findstr \"Default Gateway\"", true)
                  .split("\n")
          )
          .filter(g -> !g.isEmpty())
          .collect(Collectors.joining());

      Pattern pattern = Pattern.compile(IP_ADDRESS_PATTERN);
      Matcher matcher = pattern.matcher(defaultGateway);
      final String defaultGatewayIP = matcher.find()
          ? matcher.group()
          : "172.0.0.0";

      String localIpAddressResult = Arrays.stream(
          CommandExecutor
              .executeCommand("cmd /c ipconfig | findstr \"Ipv4 Address\"", true)
              .split("\n")
      )
          .filter(ip -> ip.contains(defaultGatewayIP.substring(0, 3)))
          .collect(Collectors.joining());

      pattern = Pattern.compile(IP_ADDRESS_PATTERN);
      matcher = pattern.matcher(localIpAddressResult);
      localIpAddress = matcher.find()
          ? matcher.group()
          : "127.0.0.1";
    } else if (OS.contains("mac")) {
      localIpAddress = CommandExecutor.executeCommand("ipconfig getifaddr en0", true).replace("\n", "");
      if(localIpAddress.isEmpty()) {
        localIpAddress = "127.0.0.1";
      }
    } else {
      localIpAddress = CommandExecutor.executeCommand("hostname -i", true).replace("\n", "");
    }

    return localIpAddress;
  }

  private static void startAndroidEmulatorInWritableState(String avdName) {
    try {
      log.info(
          CommandExecutor.executeCommand(
              String.format("emulator -writable-system -netdelay none -netspeed full -avd %s", avdName),
              false)
      );
      log.info("Waiting 10 seconds for emulator to start");
      TimeUnit.SECONDS.sleep(10);
      String emulatorBootStatus = CommandExecutor.executeCommand("adb shell getprop sys.boot_completed", true);
      if(!emulatorBootStatus.contains("1")){
        log.info("Waiting another 20 seconds for emulator to start");
        TimeUnit.SECONDS.sleep(20);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Copies file from local to android device.
   *
   * @param source String, the relative path to the file you want to copy
   * @param destination String, the destination path on the device
   * For example to copy 'someFile.txt' from project's root to '/etc/' on the device you' have to set source = 'someFile.txt' and  destination = '/etc/someFile.txt'
   * Note: emulator hast to be started in 'writable state' by 'startAndroidEmulatorInWritableState' method to be able to copy to the /etc/
   */
  public static void copyGivenFileToRunningAndroidEmulator(String source, String destination) {
    CommandExecutor.executeCommand("adb root", true);
    CommandExecutor.executeCommand("adb remount", true);
    CommandExecutor.executeCommand(String.format("adb push %s %s", source, destination), true);
  }

  /**
   * Launches an android emulator in the writable mode and clean device if cleanDevice is set to "true"
   *
   * @param avdName          the avd name(i.e. Pixel_2_API_28)
   * @param cleanDevice boolean, if true, device will be wiped.
   */
  public static void setupAndroidEmulator(String avdName, boolean cleanDevice) {
    if(cleanDevice){
      shutdownBootedEmulators();
      cleanEmulatorAndStartInWritableState(avdName);
    } else {
      startAndroidEmulatorInWritableState(avdName);
    }
  }

  /**
   * Check if the Appium driver is for iOS platform
   *
   * @param driver the Appium driver
   * @return true if iOS platform, false if Android platform
   */
  public static boolean isIOSPlatform(AppiumDriver driver){
    return Objects.requireNonNull(driver.getPlatformName()).toLowerCase().contains("ios");
  }

  /**
   * Takes screenshot of the mobile element.
   *
   * @param element the mobile element to get a screenshot of
   * @return the File object with the screenshot file of the mobile element
   * @see File
   */
  public static File takeScreenshotOfElement(MobileElement element){
    return element.getScreenshotAs(OutputType.FILE);
  }

  /**
   * Method to compare actual image of a mobile element on a screen with expected image.
   *
   * @param elementScreenshot       the image file of the actual mobile element
   * @param pathToExpectedImage     the path to expected image file
   * @param expectedMatchPercentage the expected match percentage(i.e. 100, 90, 75 etc. Any digit from 1 to 100)
   * @return true if is matched or false if not matched
   */
  public static boolean isImageMatch(File elementScreenshot, String pathToExpectedImage, Integer expectedMatchPercentage){
    resizeImageTo1000x1000pixels(elementScreenshot);
    resizeImageTo1000x1000pixels(new File(pathToExpectedImage));
    float percentage = (float)(expectedMatchPercentage)/100;
    org.sikuli.script.Pattern searchImage = new org.sikuli.script.Pattern(elementScreenshot.getAbsolutePath()).similar(percentage);
    Finder objFinder = new Finder(pathToExpectedImage);
    objFinder.find(searchImage); //searchImage is the image you want to search within ScreenImage
    return objFinder.hasNext();
  }

  private static File resizeImageTo1000x1000pixels(File originalImage){
    return resizeImage(originalImage,new Dimension(1000,1000));
  }

  private static File resizeImage(File originalImage, Dimension dimension){
    log.info("File location ".concat(originalImage.getAbsolutePath()));
    try{
      BufferedImage originalBufferedImage = ImageIO.read(originalImage);
      BufferedImage resizedImage = new BufferedImage(dimension.getWidth(), dimension.getHeight(), originalBufferedImage.getType());
      Graphics2D g = resizedImage.createGraphics();
      g.drawImage(originalBufferedImage, 0, 0, dimension.getWidth(), dimension.getHeight(), null);
      ImageIO.write(resizedImage,getFileExtension(originalImage.getAbsolutePath()),originalImage);
      g.dispose();
    }catch(IOException e){
      System.out.println(e.getMessage());
    }
    return originalImage;
  }

  /**
   * Shuts down and wipes data on all running simulators
   * Launches an iOS simulator and performs clean up if cleanDevice is set to "true"
   *
   * @param iphoneName       the iphone name(i.e. iPhone 11, iPhone 11 Pro Max, etc.)
   * @param cleanDevice boolean, if true device will shut down, erased and rebooted
   */
  public static void setupIOSSimulator(String iphoneName, boolean cleanDevice) {
    if(cleanDevice) {
      shutdownAndEraseBootedSimulators();
    }
    startIOSSimulator(iphoneName);
  }

  private static void shutdownAndEraseBootedSimulators(){
    CommandExecutor.executeCommand("xcrun simctl shutdown booted", true);
    CommandExecutor.executeCommand("xcrun simctl erase all", true);
  }

  private static void shutdownBootedEmulators(){
    getConnectedRealAndroidDevicesAndEmulatorsUDID().forEach(emulator -> {
      CommandExecutor.executeCommand(String.format("adb -s %s emu kill", emulator),false);
    });
  }

  private static void cleanEmulatorAndStartInWritableState(String avdName){
    try {
      log.info(
          CommandExecutor.executeCommand(String.format("emulator -avd %s -wipe-data -writable-system -netdelay none -netspeed full", avdName), false)
      );
      log.info("Waiting 30 seconds for emulator to start");
      TimeUnit.SECONDS.sleep(30);
      String emulatorBootStatus = CommandExecutor.executeCommand("adb shell getprop sys.boot_completed", true);
      if(!emulatorBootStatus.contains("1")){
        log.info("Waiting another 30 seconds for emulator to start");
        TimeUnit.SECONDS.sleep(30);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static String startIOSSimulator(String iphoneName) {
    String firstIphoneDevice = getAllXcodeDevices().stream().filter(device -> device.contains(iphoneName)).findFirst().get();
    String firstIphoneDeviceUDID = firstIphoneDevice.substring(firstIphoneDevice.indexOf("[") + 1, firstIphoneDevice.indexOf("]"));
    CommandExecutor.executeCommand(String.format("xcrun instruments -w %s", firstIphoneDeviceUDID), true);

    return firstIphoneDeviceUDID;
  }

  private static List<String> getAllXcodeDevices() {
    return Arrays.asList(CommandExecutor.executeCommand("instruments -s devices", true).split("\n"));
  }

  /**
   * Stops screen recording of an emulator or a simulator. Creates directory with given name if it doesn't exist and stores screen recordings there.
   *
   * @param driver                 the Appium driver
   * @param testVideoRecordingsDir the path of folder to store screen recording files
   * @param testName               the test name
   * @param filenameDateFormat     the filename date format(i.e. yyyy-MM-dd_hh.mm)
   * @throws IOException the io exception if can't create the folder to store screen recording files
   */
  public static void stopRecording(AppiumDriver driver, String testVideoRecordingsDir, String testName, String filenameDateFormat) throws IOException {
    String video = ((CanRecordScreen) driver).stopRecordingScreen();
    byte[] decodedVideo = Base64.getMimeDecoder().decode(video);
    File file = new File(testVideoRecordingsDir);
    if (!file.exists()) {
      Files.createDirectories(Paths.get(file.getAbsolutePath()));
    }
    Path testVideoFile = Paths.get(
        testVideoRecordingsDir,
        String.format("%s-%s.%s", testName, LocalDateTime.now().format(DateTimeFormatter.ofPattern(filenameDateFormat)), "mp4")
    );
    Files.write(testVideoFile, decodedVideo);
    log.info(String.format("New test recording created: %s", testVideoFile));
  }

  /**
   * Start google chrome app and open url on android device or emulator.
   *
   * @param url the url to open
   */
  public static void startGoogleChromeAndOpenUrlOnAndroidDevice(String url){
    CommandExecutor.executeCommand(
        String.format("adb shell am start -n com.android.chrome/com.google.android.apps.chrome.Main -d \"%s\"", url),
        false
    );
  }

  /**
   * Performs click the accept and continue button in google chrome if present.
   *
   * @param driver the Appium driver
   */
  public static void clickAcceptAndContinueButtonInGoogleChromeIfPresent(AppiumDriver driver) {
    WebDriverWait internalWait = new WebDriverWait(driver, 5);
    String acceptButtonLocator =  "com.android.chrome:id/terms_accept";
    try{
      internalWait.until(ExpectedConditions.elementToBeClickable(MobileBy.id(acceptButtonLocator)));
      driver.findElement(MobileBy.id(acceptButtonLocator)).click();
    } catch (TimeoutException e){
      log.info("'Accept & Continue' button was not present in Google Chrome web browser");
    }
  }

  /**
   * Performs click the no thanks button in google chrome if present.
   *
   * @param driver the Appium driver
   */
  public static void clickNoThanksButtonGoogleChromeIfPresent(AppiumDriver driver) {
    WebDriverWait internalWait = new WebDriverWait(driver, 5);
    String noThanksButtonLocator =  "com.android.chrome:id/negative_button";
    try{
      internalWait.until(ExpectedConditions.elementToBeClickable(MobileBy.id(noThanksButtonLocator)));
      driver.findElement(MobileBy.id(noThanksButtonLocator)).click();
    } catch (TimeoutException e){
      log.info("'No Thanks' button was not present in Google Chrome web browser");
    }
  }

  /**
   * Launches installed up on Android device.
   *
   * @param appPackage pacakge name of the app to launch
   * @param appActivity activity name of the launched app to start
   */
  public static void launchAppOnAndroid(AndroidDriver driver, String appPackage, String appActivity) {
    Activity activity = new Activity(appPackage, appActivity);
    activity.setStopApp(false);
    driver.startActivity(activity);
  }
}