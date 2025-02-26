package jsontree;

import java.util.ArrayList;
import java.util.List;

/**
 * JsonArray is concrete implementation of JSON array node.
 * JsonArray class extends IJsonArray.
 * JsonArray provides functionality to add array elements.
 * JsonArray overrides default implementation equal , hashcode.
 */
public class JsonArray extends IJsonArray {
  private List<JsonNode> arrayElements;

  /**
   * initialise an empty JsonArray.
   */
  public JsonArray() {
    arrayElements = new ArrayList<>();
  }

  /**
   * Implementation of prettyPrint() from the JsonNode Interface.
   * Generates a pretty-printed string representation of the JSON array.
   * Uses setLevel function to set the children level depth.
   *
   * @return A formatted string representation of the JSON array.
   */
  public String prettyPrint() {
    StringBuilder result = new StringBuilder("");
    String indentation = getSpace(this.level);
    result.append(indentation).append("[");

    for (int i = 0; i < arrayElements.size(); i++) {
      arrayElements.get(i).setLevel(this.level + 1);
      result.append("\n" + arrayElements.get(i).prettyPrint());
      if (i < arrayElements.size() - 1) {
        result.append(",");
      }
    }
    result.append("\n").append(indentation).append("]");
    return result.toString();
  }

  /**
   * Adds a JsonNode to the JSON array.
   * The JsonNode is appended at the end of the previous elements.
   *
   * @param value The JSON node to be added.
   */
  @Override
  public void add(JsonNode value) {
    arrayElements.add(value);
  }

  /**
   * Checks if this JsonArray is equal to another object.
   * Two JsonArray objects are considered equal if they have the same elements in the same order.
   *
   * @param otherObject The object to compare.
   * @return true if the objects are equal, else false.
   */
  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof IJsonArray)) {
      return false;
    }
    JsonArray thatObject = (JsonArray) otherObject;
    if (arrayElements.size() != thatObject.arrayElements.size()) {
      return false;
    }
    for (int i = 0; i < arrayElements.size(); i++) {
      if (!arrayElements.get(i).equals(thatObject.arrayElements.get(i))) {
        return false;
      }
    }
    return true;
  }


  /**
   * Computes the hash code for this JsonArray.
   * The function sums the has of all the individual nodes to compute the has for JsonArray.
   *
   * @return The hash code value.
   */
  @Override
  public int hashCode() {
    int hashValue = 1;
    for (JsonNode element : arrayElements) {
      hashValue = hashValue + (element.hashCode());
    }
    return hashValue;
  }

}
