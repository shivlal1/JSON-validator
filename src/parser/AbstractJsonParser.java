package parser;

import java.util.Stack;

import validator.JsonStateMachine;
import validator.Status;

/**
 * AbstractJsonParser is an abstract base class that implements common JSON parsing functionality.
 * Contains shared methods and fields used by both JsonValidator and JsonTreeBuilder.
 */
public abstract class AbstractJsonParser<T> implements JsonParser<T> {

  protected static final char OPEN_BRACE = '{';
  protected static final char CLOSE_BRACE = '}';
  protected static final char OPEN_BRACKET = '[';
  protected static final char CLOSE_BRACKET = ']';
  protected static final char QUOTE = '"';
  protected static final char COLON = ':';
  protected static final char COMMA = ',';

  protected Stack<Character> characterStack;
  protected boolean firstCharacter;
  protected JsonStateMachine currentState;
  protected int flowerBraces;
  protected String currentStatus;

  /**
   * Initializes a new JSON validator with default values.
   * Sets up an empty stack, initializes the state machine to state INITIALIZATION.
   * Sets the current state to EMPTY.
   */
  public AbstractJsonParser() {
    characterStack = new Stack<>();
    currentState = JsonStateMachine.INITIALIZATION;
    currentStatus = String.valueOf(Status.EMPTY);
    flowerBraces = 0;
    firstCharacter = true;
  }

  /**
   * Handles comma characters, determining the next expected state in the state machine.
   *
   * @param c The current character
   * @throws InvalidJsonException if the comma is invalid
   */
  protected void handleComma(char c) throws InvalidJsonException {
    currentState = (characterStack.peek() != OPEN_BRACKET)
            ? JsonStateMachine.EXPECT_KEY
            : JsonStateMachine.EXPECT_VALUE_OR_NESTED_OBJECT;
  }

  /**
   * Determines if a whitespace character can be skipped in the current state.
   * The whitespace will be skipped if the current state is not in key or value.
   *
   * @param c The character to check
   * @return true if the character can be skipped, false otherwise
   */
  protected boolean canSkipWhiteSpace(char c) {
    return Character.isWhitespace(c)
            && !currentState.isKeyProcessing()
            && !currentState.isValueProcessing();
  }

  /**
   * useful to thorw invalid json exception with a message.
   *
   * @param message received message.
   * @throws InvalidJsonException exception with message.
   */
  protected abstract void throwInvalidJson(String message) throws InvalidJsonException;


}
