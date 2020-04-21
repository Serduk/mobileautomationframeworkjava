
package entities;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Json serialization properties class to detect Devices.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Devices {

  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-13-4")
  private List<ComAppleCoreSimulatorSimRuntimeIOS134> comAppleCoreSimulatorSimRuntimeIOS134 = null;
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-13-3")
  private List<ComAppleCoreSimulatorSimRuntimeIOS133> comAppleCoreSimulatorSimRuntimeIOS133 = null;
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-13-2")
  private List<ComAppleCoreSimulatorSimRuntimeIOS132> comAppleCoreSimulatorSimRuntimeIOS132 = null;
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-13-1")
  private List<ComAppleCoreSimulatorSimRuntimeIOS131> comAppleCoreSimulatorSimRuntimeIOS131 = null;
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-12-4")
  private List<ComAppleCoreSimulatorSimRuntimeIOS124> comAppleCoreSimulatorSimRuntimeIOS124 = null;

  /**
   * Gets com apple core simulator sim runtime ios 132.
   *
   * @return the com apple core simulator sim runtime ios 132
   */
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-13-2")
  public List<ComAppleCoreSimulatorSimRuntimeIOS132> getComAppleCoreSimulatorSimRuntimeIOS132() {
    return comAppleCoreSimulatorSimRuntimeIOS132;
  }

  /**
   * Gets com apple core simulator sim runtime ios 133.
   *
   * @return the com apple core simulator sim runtime ios 133
   */
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-13-3")
  public List<ComAppleCoreSimulatorSimRuntimeIOS133> getComAppleCoreSimulatorSimRuntimeIOS133() {
    return comAppleCoreSimulatorSimRuntimeIOS133;
  }

  /**
   * Gets com apple core simulator sim runtime ios 134.
   *
   * @return the com apple core simulator sim runtime ios 134
   */
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-13-4")
  public List<ComAppleCoreSimulatorSimRuntimeIOS134> getComAppleCoreSimulatorSimRuntimeIOS134() {
    return comAppleCoreSimulatorSimRuntimeIOS134;
  }

  /**
   * Sets com apple core simulator sim runtime ios 132.
   *
   * @param comAppleCoreSimulatorSimRuntimeIOS132 the com apple core simulator sim runtime ios 132
   */
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-13-2")
  public void setComAppleCoreSimulatorSimRuntimeIOS132(
      List<ComAppleCoreSimulatorSimRuntimeIOS132> comAppleCoreSimulatorSimRuntimeIOS132) {
    this.comAppleCoreSimulatorSimRuntimeIOS132 = comAppleCoreSimulatorSimRuntimeIOS132;
  }

  /**
   * Gets com apple core simulator sim runtime ios 131.
   *
   * @return the com apple core simulator sim runtime ios 131
   */
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-13-1")
  public List<ComAppleCoreSimulatorSimRuntimeIOS131> getComAppleCoreSimulatorSimRuntimeIOS131() {
    return comAppleCoreSimulatorSimRuntimeIOS131;
  }

  /**
   * Sets com apple core simulator sim runtime ios 131.
   *
   * @param comAppleCoreSimulatorSimRuntimeIOS131 the com apple core simulator sim runtime ios 131
   */
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-13-1")
  public void setComAppleCoreSimulatorSimRuntimeIOS131(
      List<ComAppleCoreSimulatorSimRuntimeIOS131> comAppleCoreSimulatorSimRuntimeIOS131) {
    this.comAppleCoreSimulatorSimRuntimeIOS131 = comAppleCoreSimulatorSimRuntimeIOS131;
  }

  /**
   * Gets com apple core simulator sim runtime ios 124.
   *
   * @return the com apple core simulator sim runtime ios 124
   */
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-12-4")
  public List<ComAppleCoreSimulatorSimRuntimeIOS124> getComAppleCoreSimulatorSimRuntimeIOS124() {
    return comAppleCoreSimulatorSimRuntimeIOS124;
  }

  /**
   * Sets com apple core simulator sim runtime ios 124.
   *
   * @param comAppleCoreSimulatorSimRuntimeIOS124 the com apple core simulator sim runtime ios 124
   */
  @JsonProperty("com.apple.CoreSimulator.SimRuntime.iOS-12-4")
  public void setComAppleCoreSimulatorSimRuntimeIOS124(
      List<ComAppleCoreSimulatorSimRuntimeIOS124> comAppleCoreSimulatorSimRuntimeIOS124) {
    this.comAppleCoreSimulatorSimRuntimeIOS124 = comAppleCoreSimulatorSimRuntimeIOS124;
  }
}
