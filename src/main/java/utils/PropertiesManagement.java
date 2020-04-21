package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * The Properties management class to manage test properties.
 */
public class PropertiesManagement extends LoggerBase{

  private static PropertiesManagement INSTANCE = null;
  private Properties props = null;

  private PropertiesManagement() {
  }

  /**
   * Loads project properties from a properties file.
   *
   * @param propsFileName the file name with properties
   * @return the {@link Properties} object
   */
  public synchronized Properties loadProps(String propsFileName) {
    FileInputStream fis = null;
    try {
      File propsFile = new File(propsFileName);
      props = new Properties();
      fis = new FileInputStream(propsFile);
      props.load(fis);
    } catch (IOException e) {
      try {
        props.load(Objects.requireNonNull(PropertiesManagement.class.getClassLoader().getResourceAsStream(propsFileName)));
      } catch (IOException ex) {
        log.error("Could not load properties from: ".concat(propsFileName));
        ex.printStackTrace();
      }
    } finally {
      try {
        if (fis != null) {
          fis.close();
        }
      } catch (IOException e) {
        log.error("Could not close: ".concat(propsFileName));
        e.printStackTrace();
      }
    }
    return props;
  }

  /**
   * Gets PropertiesManagement instance.
   *
   * @return the PropertiesManagement instance
   */
  public static PropertiesManagement getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new PropertiesManagement();
    }
    return INSTANCE;
  }

}
