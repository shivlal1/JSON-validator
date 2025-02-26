package jsontree;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A concrete implementation of a IJsonObject.
 * Extends IJsonObject & provides functionality to store and print key-value pairs.
 * Overrides default behaviour of equals and hash code method.
 */
public class JsonObject extends IJsonObject {
  private List<Map.Entry<String, JsonNode>> objectElements;


  /**
   * Initialise an ArrayList to store json Objects.
   */
  public JsonObject() {
    this.objectElements = new ArrayList<>();
  }

  /**
   * Generates a pretty-printed string representation of the JSON object.
   * Uses setLevel to set the children indentation level.
   *
   * @return A formatted string representation of the JSON object.
   */
  public String prettyPrint() {
    StringBuilder result = new StringBuilder();
    String indentation = getSpace(this.level);
    String innerIndentation = getSpace(this.level + 1);
    int entrySize = objectElements.size();

    result.append(indentation).append("{\n");
    for (int i = 0; i < entrySize; i++) {
      Map.Entry<String, JsonNode> entry = objectElements.get(i);
      result.append(innerIndentation + "\"").append(entry.getKey()).append("\":");

      if (entry.getValue() instanceof IJsonObject || entry.getValue() instanceof IJsonArray) {
        entry.getValue().setLevel(this.level + 1);
        result.append("\n").append(entry.getValue().prettyPrint());
      } else {
        result.append(entry.getValue().prettyPrint());
      }

      if (i < entrySize - 1) {
        result.append(",");
      }
      result.append("\n");
    }
    result.append(indentation).append("}");
    return result.toString();
  }

  /**
   * Adds a key-value pair to the JSON object.
   *
   * @param key   The key associated with the value.
   * @param value The JSON node to be associated with the key.
   * @throws IllegalArgumentException If the key is not a valid .
   */
  @Override
  public void add(String key, JsonNode value) {
    if (isValidKey(key)) {
      objectElements.add(new AbstractMap.SimpleEntry<>(key, value));
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Checks if this JsonObject is equal to another object.
   * Two JsonObject are equal if they contain same key-value pairs, ignoring order
   *
   * @param otherObject The object to compare.
   * @return {@code true} if the objects are equal, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof JsonObject)) {
      return false;
    }
    JsonObject thatObject = (JsonObject) otherObject;
    if (objectElements.size() != thatObject.objectElements.size()) {
      return false;
    }
    return areAllObjectsEqual(thatObject.objectElements);
  }

  /**
   * Used to Computes the hash code for a  JsonObject.
   * Individual hash code for key and value for each object are summed to get the total hash.
   *
   * @return The computed hash code.
   */
  @Override
  public int hashCode() {
    int hashValue = 1;
    for (Map.Entry<String, JsonNode> thisEntry : objectElements) {
      int keyHash = thisEntry.getKey().hashCode();
      int valueHash = thisEntry.getValue().hashCode();
      hashValue = hashValue + (keyHash + valueHash);
    }
    return hashValue;
  }

  /**
   * Validates whether a given key satisfied the requiremnts to be a key.
   * The key must start with a letter and can only contain letters and digits.
   *
   * @param key The key to validate.
   * @return true if the key is valid,else false.
   */
  private boolean isValidKey(String key) {
    if (key.length() <= 0) {
      return false;
    }
    if (!Character.isLetter(key.charAt(0))) {
      return false;
    }
    for (int i = 1; i < key.length(); i++) {
      char ch = key.charAt(i);
      if (!Character.isLetterOrDigit(ch)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Checks whether two lists of key-value pairs contain same elements ignoring order.
   *
   * @param thatEntries list of key-value pairs to compare against.
   * @return true if both lists contain the same elements, else false.
   */
  private boolean areAllObjectsEqual(List<Map.Entry<String, JsonNode>> thatEntries) {
    int thatSize = thatEntries.size();
    boolean[] visArray = new boolean[thatSize];

    for (Map.Entry<String, JsonNode> thisEntry : objectElements) {
      boolean found = false;
      int index = 0;

      Iterator<Map.Entry<String, JsonNode>> iterator = thatEntries.iterator();

      while (iterator.hasNext()) {
        Map.Entry<String, JsonNode> thatEntry = iterator.next();
        if (thisEntry.getKey().equals(thatEntry.getKey())
                && thisEntry.getValue().equals(thatEntry.getValue())
                && !visArray[index]) {
          found = true;
          visArray[index] = true;
          break;
        }
        index++;
      }
      if (!found) {
        return false;
      }
    }
    return true;
  }
}
