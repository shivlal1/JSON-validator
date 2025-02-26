package jsontree;

/**
 * A concrete implementation of a JSON string node.
 * JsonString class extends JsonNode and represents a string value in a JSON structure.
 * It also overrides default behaviour of equals and hash code functionality.
 */
public class JsonString extends JsonNode {
  private String value;

  /**
   * Constructs a JsonString with the specified value.
   *
   * @param value The string value to be stored in this node.
   */
  public JsonString(String value) {
    this.value = value;
  }

  /**
   * Generates a pretty-printed string representation of the JSON string.
   * Uses helpther function getSpace to add required spaces.
   *
   * @return A formatted string representation of the JSON string.
   */
  @Override
  public String prettyPrint() {
    return getSpace(this.level) + "\"" + value + "\"";
  }

  /**
   * Checks if this JsonString is equal to another object.
   * Two JsonString instances are considered equal if they contain the same string value.
   *
   * @param otherObject The object to compare.
   * @return {@code true} if the objects are equal, {@code false} otherwise.
   */

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof JsonString)) {
      return false;
    }
    JsonString thatObject = (JsonString) otherObject;
    return (value.equals(thatObject.value));
  }

  /**
   * Computes the hash code for this JsonString.
   * Uses the function of hashCode in String class to comput the hash.
   *
   * @return The computed hash code.
   */
  @Override
  public int hashCode() {
    return value.hashCode();
  }

}