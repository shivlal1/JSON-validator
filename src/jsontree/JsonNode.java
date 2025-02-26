package jsontree;

/**
 * An abstract representation of a JSON node in a tree structure.
 * This class provides common functionality for formatting and printing JSON data.
 * By default, the class sets the level of the node to 0.
 * Classes which extends JsonNode uses setLevel method to set their nested levels.
 */
public abstract class JsonNode {
  private static final int DEFAULT_LEVEL = 0;
  protected int level;

  /**
   * Constructs a Json Node with the default indentation level.
   */
  public JsonNode() {
    this.level = DEFAULT_LEVEL;
  }

  /**
   * Generates a pretty-printed string representation of the JSON node.
   *
   * @return A formatted string representation of the JSON node.
   */
  public abstract String prettyPrint();

  /**
   * Sets the indentation level of the node.
   *
   * @param level The new indentation level.
   */
  protected void setLevel(int level) {
    this.level = level;
  }

  /**
   * Generates a string containing spaces for indentation based on the given level.
   *
   * @param level The indentation level.
   * @return A string containing spaces for indentation.
   */

  protected String getSpace(int level) {
    StringBuilder indentation = new StringBuilder();
    for (int i = 0; i < level * 2; i++) {
      indentation.append(" ");
    }
    return indentation.toString();
  }
}
