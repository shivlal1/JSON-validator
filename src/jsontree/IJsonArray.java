package jsontree;

/**
 * An abstract representation of a JSON array in a tree structure.
 * IJsonArray class extends  JsonNode.
 * IJsonArray provides add functionality for adding JsonNodes in an array.
 */
public abstract class IJsonArray extends JsonNode {

  /**
   * Adds a  JsonNode to the JSON array.
   *
   * @param value The JSON node to be added.
   */
  public abstract void add(JsonNode value);
}
