
package entities;

import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Json serialization properties class to detect Devicetype.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Devicetype {

  @JsonProperty("name")
  private String name;
  @JsonProperty("bundlePath")
  private String bundlePath;
  @JsonProperty("identifier")
  private String identifier;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
   * Gets bundle path.
   *
   * @return the bundle path
   */
  @JsonProperty("bundlePath")
  public String getBundlePath() {
    return bundlePath;
  }

  /**
   * Sets bundle path.
   *
   * @param bundlePath the bundle path
   */
  @JsonProperty("bundlePath")
  public void setBundlePath(String bundlePath) {
    this.bundlePath = bundlePath;
  }

  /**
   * Gets identifier.
   *
   * @return the identifier
   */
  @JsonProperty("identifier")
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Sets identifier.
   *
   * @param identifier the identifier
   */
  @JsonProperty("identifier")
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
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
