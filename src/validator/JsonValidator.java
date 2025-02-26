package validator;


import parser.AbstractJsonParser;
import parser.InvalidJsonException;
import parser.JsonParser;

/**
 * JsonValidator is the implementation of the JsonParser interface.
 * Represents a simple JSON parser that accepts input one character at a time.
 * Contains helper method to support the parsing logic.
 * Maintains a state Machine & changes to another state depending on char in input stream.
 * There are total eight states to handle key, value, arrays and nested objects.
 */
public class JsonValidator extends AbstractJsonParser<String> {

  private static final char OPEN_BRACE = '{';
  private static final char CLOSE_BRACE = '}';
  private static final char OPEN_BRACKET = '[';
  private static final char CLOSE_BRACKET = ']';
  private static final char QUOTE = '"';
  private static final char COLON = ':';
  private static final char COMMA = ',';


  /**
   * Accept a single character as input, and return the new parser object as a result.
   * Ignores all the whitespaces if the current state is not in key and value.
   * Sets state to Invalid & throw Exception if unexpected char appears in the current state.
   * Once in Invalid state, processing stops as it can't become valid again.
   *
   * @param c the input character
   * @return the parser after handling the provided character
   * @throws InvalidJsonException if the input causes the JSON to be invalid
   */
  @Override
  public JsonParser input(char c) throws InvalidJsonException {
    if (currentStatus.equals(String.valueOf(Status.INVALID)) || canSkipWhiteSpace(c)) {
      return this;
    }
    processStateMachine(c);
    return this;
  }

  /**
   * Provide the output of the parser, given all the inputs it has been provided.
   * so far. The function returns only the currentStatus set in the helper methods &
   * no specific separate implementation logic is used.
   *
   * @return currentStatus the output of the parser
   */
  @Override
  public String output() {
    return currentStatus;
  }

  /**
   * Processes the current character based on the validator's current state.
   * This method gives the input character to the appropriate current state
   * according to the current state of the JSON parsing process.If there's no
   * matching state with respect to the current character then it throws an
   * invalid json exception by default.
   *
   * @param c The character to be processed
   * @throws InvalidJsonException if the character is invalid for the current state
   */
  private void processStateMachine(char c) throws InvalidJsonException {
    switch (currentState) {
      case INITIALIZATION:
        handleInitialization(c);
        break;
      case EXPECT_KEY:
        handleExpectKey(c);
        break;
      case STARTING_KEY:
        handleKey(c);
        break;
      case END_KEY:
        handleKeyEnding(c);
        break;
      case EXPECT_VALUE_OR_NESTED_OBJECT:
        handleIntermediateStage(c);
        break;
      case EXPECT_VALUE:
        handleValue(c);
        break;
      case FINAL_STAGE:
        handleFinalStage(c);
        break;
      default:
        throwInvalidJson("error in state initialization");
        break;
    }
  }

  /**
   * Throws an InvalidJsonException with a custom messgage.
   * Updates the status to INVALID.
   * Used by helper methods in the class as a single point of contact to throw exception.
   *
   * @throws InvalidJsonException always throws an invalid json exception
   */
  protected void throwInvalidJson(String message) throws InvalidJsonException {
    currentStatus = String.valueOf(Status.INVALID);
    throw new InvalidJsonException("Error: " + message + "State :" + currentState.getDescription());
  }

  /**
   * Handles the initialization state, expecting an opening curly brace.
   * The Helper method will throw an Exception if open curly brace is not found.
   *
   * @param c The current character
   * @throws InvalidJsonException if the character is not '{'
   */
  private void handleInitialization(char c) throws InvalidJsonException {
    if (c != OPEN_BRACE) {
      throwInvalidJson("json should start with {");
    }
    characterStack.push(OPEN_BRACE);
    flowerBraces++;
    currentState = JsonStateMachine.EXPECT_KEY;
    currentStatus = String.valueOf(Status.INCOMPLETE);
  }

  /**
   * Validates the first character of a key, which must be a letter.
   * The Helper method will throw an Exception the first char is not a letter.
   *
   * @param c The character to validate
   * @throws InvalidJsonException if the character is not a letter
   */
  private void validateFirstKeyCharacter(char c) throws InvalidJsonException {
    if (!Character.isLetter(c)) {
      throwInvalidJson("json first char should be letter");
    }
    firstCharacter = false;
  }

  /**
   * Processes characters within a key string.
   * Uses helper method to validate first character.
   * Throws an Exception if the current char cannot be part of the key.
   *
   * @param c The current character
   * @throws InvalidJsonException if the character is invalid for a key
   */
  private void handleKey(char c) throws InvalidJsonException {
    if (firstCharacter) {
      validateFirstKeyCharacter(c);
      return;
    }
    if (c == QUOTE) {
      currentState = JsonStateMachine.END_KEY;
      firstCharacter = true;
      return;
    }
    if (!Character.isLetterOrDigit(c)) {
      throwInvalidJson("only letters & digits are allowed");
    }
  }

  /**
   * This helper method handles the state between key and value.
   * After the end of a key, expecting a colon and process to the next state.
   * Only a colon can separate key & value; an exception is thrown if it's missing.
   *
   * @param c The current character
   * @throws InvalidJsonException if the character is not ':'
   */
  private void handleKeyEnding(char c) throws InvalidJsonException {
    if (c != COLON) {
      throwInvalidJson("key should be followed by :");
    }
    currentState = JsonStateMachine.EXPECT_VALUE_OR_NESTED_OBJECT;
  }

  /**
   * After a key ends , the value can be either a string , or an array or an object.
   * This helper method redirects to the correct value state depending on the input.
   * Throws exception if state machine cannot be moved to any of the three states.
   *
   * @param c The current character
   * @throws InvalidJsonException if the character is invalid
   */
  private void handleIntermediateStage(char c) throws InvalidJsonException {
    switch (c) {
      case OPEN_BRACE:
      case QUOTE:
        processValueAsStringOrObject(c);
        break;
      case OPEN_BRACKET:
        currentState = JsonStateMachine.EXPECT_VALUE_OR_NESTED_OBJECT;
        characterStack.push(OPEN_BRACKET);
        break;
      case CLOSE_BRACKET:
        handleFinalStage(c);
        break;
      default:
        throwInvalidJson("Unexpected char");
    }
  }

  /**
   * Handles characters within a value string.
   * Since the value can contain any characters, no validation is applied.
   * The value state ends and moves to final stage upon encountering Quotes.
   *
   * @param c The current character
   */
  private void handleValue(char c) {
    if (c == QUOTE) {
      currentState = JsonStateMachine.FINAL_STAGE;
    }
  }

  /**
   * This helper method processes characters after value state ends.
   * The next expected character can be an array value or the start of an object.
   * This method redirects to the appropriate state based on the character.
   * If the expected character is missing for the next state, an Exception is thrown.
   *
   * @param c The current character
   * @throws InvalidJsonException if the character is invalid
   */
  private void handleFinalStage(char c) throws InvalidJsonException {
    switch (c) {
      case CLOSE_BRACE:
        handleFlowerClosingForJson(c);
        break;
      case COMMA:
        handleComma(c);
        break;
      case CLOSE_BRACKET:
        handleBracketClosingForJson(c);
        break;
      default:
        throwInvalidJson("json or array can end in only } or ]");
    }
  }

  /**
   * A value can be either a string or a nested object.
   * The helper method processes start of string or nested object based on input char c.
   *
   * @param c The current character
   * @throws InvalidJsonException if the character is invalid
   */
  private void processValueAsStringOrObject(char c) throws InvalidJsonException {
    if (c == QUOTE) {
      currentState = JsonStateMachine.EXPECT_VALUE;
    } else if (c == OPEN_BRACE) {
      characterStack.push(OPEN_BRACE);
      flowerBraces++;
      currentState = JsonStateMachine.EXPECT_KEY;
    }
  }

  /**
   * Handles the state where a key is expected, requiring a quote character.
   * A Key always starts with quotes, so exception is thrown if char c is not quotes.
   *
   * @param c The current character
   * @throws InvalidJsonException if the character is not '"'
   */
  private void handleExpectKey(char c) throws InvalidJsonException {
    if (c != QUOTE) {
      throwInvalidJson("error in key initialization");
    }
    currentState = JsonStateMachine.STARTING_KEY;
  }

  /**
   * Processes a closing curly brace, updating nested object tracking.
   * Before brace closing, the stack top is checked for { for validation
   *
   * @param c The current character
   * @throws InvalidJsonException if the closing brace is invalid
   */
  private void handleFlowerClosingForJson(char c) throws InvalidJsonException {
    if (characterStack.peek() == OPEN_BRACE) {
      characterStack.pop();
      flowerBraces--;
      if (flowerBraces == 0) {
        currentStatus = String.valueOf(Status.VALID);
        currentState = JsonStateMachine.END;
      } else {
        currentState = JsonStateMachine.FINAL_STAGE;
      }
    } else {
      throwInvalidJson("error in closing json with }");
    }
  }

  /**
   * Processes a closing square bracket in arrays.
   * Before brace closing, the stack top is checked for [ for validation
   *
   * @param c The current character
   * @throws InvalidJsonException if the closing bracket is invalid
   */
  private void handleBracketClosingForJson(char c) throws InvalidJsonException {
    if (characterStack.peek() == OPEN_BRACKET) {
      characterStack.pop();
      currentState = JsonStateMachine.FINAL_STAGE;
    } else {
      throwInvalidJson("error in closing array with ]");
    }
  }
}