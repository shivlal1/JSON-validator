package validator;

/**
 * Represents the different states that can be associated with a validation process.
 * Each status has an associated string value prefixed with "Status:".
 */
public enum Status {
  EMPTY("Status:Empty"),
  INCOMPLETE("Status:Incomplete"),
  VALID("Status:Valid"),
  INVALID("Status:Invalid");

  private final String value;

  /**
   * Constructor for Status enum.
   *
   * @param value The string representation of the status, prefixed with "Status:"
   */
  Status(String value) {
    this.value = value;
  }

  /**
   * Returns the string representation of the status.
   *
   * @return The formatted status string (e.g., "Status:Valid")
   */
  @Override
  public String toString() {
    return value;
  }
}