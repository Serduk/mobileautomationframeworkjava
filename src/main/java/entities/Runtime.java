
package entities;

import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Json serialization properties class to detect Runtime.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Runtime {

  @JsonProperty("version")
  private String version;
  @JsonProperty("bundlePath")
  private String bundlePath;
  @JsonProperty("isAvailable")
  private Boolean isAvailable;
  @JsonProperty("name")
  private String name;
  @JsonProperty("identifier")
  private String identifier;
  @JsonProperty("buildversion")
  private String buildversion;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * Gets version.
   *
   * @return the version
   */
  @JsonProperty("version")
  public String getVersion() {
    return version;
  }

  /**
   * Sets version.
   *
   * @param version the version
   */
  @JsonProperty("version")
  public void setVersion(String version) {
    this.version = version;
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
   * Gets buildversion.
   *
   * @return the buildversion
   */
  @JsonProperty("buildversion")
  public String getBuildversion() {
    return buildversion;
  }

  /**
   * Sets buildversion.
   *
   * @param buildversion the buildversion
   */
  @JsonProperty("buildversion")
  public void setBuildversion(String buildversion) {
    this.buildversion = buildversion;
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
