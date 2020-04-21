
package entities;

import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Json serialization properties class for ios 12.4.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "state",
    "isAvailable",
    "name",
    "udid"
})
public class ComAppleCoreSimulatorSimRuntimeIOS124 {

  @JsonProperty("state")
  private String state;
  @JsonProperty("isAvailable")
  private Boolean isAvailable;
  @JsonProperty("name")
  private String name;
  @JsonProperty("udid")
  private String udid;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * Gets state.
   *
   * @return the state
   */
  @JsonProperty("state")
  public String getState() {
    return state;
  }

  /**
   * Sets state.
   *
   * @param state the state
   */
  @JsonProperty("state")
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Gets is available.
   *
   * @return the is available
   */
  @JsonProperty("isAvailable")
  public Boolean getIsAvailable() {
    return isAvailable;
  }

  /**
   * Sets is available.
   *
   * @param isAvailable the is available
   */
  @JsonProperty("isAvailable")
  public void setIsAvailable(Boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets udid.
   *
   * @return the udid
   */
  @JsonProperty("udid")
  public String getUdid() {
    return udid;
  }

  /**
   * Sets udid.
   *
   * @param udid the udid
   */
  @JsonProperty("udid")
  public void setUdid(String udid) {
    this.udid = udid;
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
