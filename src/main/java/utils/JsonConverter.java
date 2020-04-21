package utils;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * The Json converter class deserializes JSON from a string to any given class.
 */
public class JsonConverter {

  /**
   * Deserialize new class specified with "type" parameter from JSON.
   *
   * @param json the string representation of JSON
   * @param type the class type to be deserialized to
   * @return new instance of the object specified with "type" parameter
   * @throws IOException if the string is not a json string
   */
  public static <T> T deserializeToCustomClass(String json, Class<T> type) throws IOException {
    return new ObjectMapper().readValue(json, type);
  }
}
