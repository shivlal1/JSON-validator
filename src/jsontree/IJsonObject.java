package jsontree;

/**
 * An abstract representation of a JSON object in a tree structure.
 * This class extends {@code JsonNode} and provides functionality for managing key-value pairs.
 */
public abstract class IJsonObject extends JsonNode {

  /**
   * Abstract function that Adds a key-value pair to the JSON object.
   *
   * @param key   key which is of type string.
   * @param value JSON node to be associated with the key of type JsonNode.
   */
  public abstract void add(String key, JsonNode value);
}