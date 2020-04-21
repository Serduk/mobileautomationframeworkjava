
package entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Json serialization properties class to detect Simctl list output.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimctlListOutput {

  @JsonProperty("devicetypes")
  private List<Devicetype> devicetypes = null;
  @JsonProperty("runtimes")
  private List<Runtime> runtimes = null;
  @JsonProperty("devices")
  private Devices devices;
  @JsonProperty("pairs")
  private Pairs pairs;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * Gets devicetypes.
   *
   * @return the devicetypes
   */
  @JsonProperty("devicetypes")
  public List<Devicetype> getDevicetypes() {
    return devicetypes;
  }

  /**
   * Sets devicetypes.
   *
   * @param devicetypes the devicetypes
   */
  @JsonProperty("devicetypes")
  public void setDevicetypes(List<Devicetype> devicetypes) {
    this.devicetypes = devicetypes;
  }

  /**
   * Gets runtimes.
   *
   * @return the runtimes
   */
  @JsonProperty("runtimes")
  public List<Runtime> getRuntimes() {
    return runtimes;
  }

  /**
   * Sets runtimes.
   *
   * @param runtimes the runtimes
   */
  @JsonProperty("runtimes")
  public void setRuntimes(List<Runtime> runtimes) {
    this.runtimes = runtimes;
  }

  /**
   * Gets devices.
   *
   * @return the devices
   */
  @JsonProperty("devices")
  public Devices getDevices() {
    return devices;
  }

  /**
   * Sets devices.
   *
   * @param devices the devices
   */
  @JsonProperty("devices")
  public void setDevices(Devices devices) {
    this.devices = devices;
  }

  /**
   * Gets pairs.
   *
   * @return the pairs
   */
  @JsonProperty("pairs")
  public Pairs getPairs() {
    return pairs;
  }

  /**
   * Sets pairs.
   *
   * @param pairs the pairs
   */
  @JsonProperty("pairs")
  public void setPairs(Pairs pairs) {
    this.pairs = pairs;
  }

  /**
   * Gets additional properties.
   *
   * @return the additional properties
   */
  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  /**
   * Sets additional property.
   *
   * @param name  the name
   * @param value the value
   */
  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
