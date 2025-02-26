package validator;

/**
 * Represents the possible states of a JSON validator during parsing.
 * Each state corresponds to a specific state in the JSON state machine validation process.
 */
public enum JsonStateMachine {
  INITIALIZATION("Initial state, expecting { brace"),
  EXPECT_KEY("Expecting a key to start with quotes"),
  STARTING_KEY("Processing key characters"),
  END_KEY("Key completed, expecting colon"),
  EXPECT_VALUE_OR_NESTED_OBJECT("Expecting value, array, or nested object"),
  EXPECT_VALUE("Processing value characters"),
  FINAL_STAGE("Value completed, expecting comma, closing brace, or bracket"),
  END("JSON validation completed");

  private final String description;

  /**
   * Constructs a new JsonValidatorState with a description.
   *
   * @param description A human-readable description of the state
   */
  JsonStateMachine(String description) {
    this.description = description;
  }

  /**
   * Gets the description of the state.
   *
   * @return The state description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Checks if the state is processing a key.
   *
   * @return true if the state is related to key processing
   */
  public boolean isKeyProcessing() {
    return this == STARTING_KEY;
  }

  /**
   * Checks if the state is processing a value.
   *
   * @return true if the state is related to value processing
   */
  public boolean isValueProcessing() {
    return this == EXPECT_VALUE;
  }
}