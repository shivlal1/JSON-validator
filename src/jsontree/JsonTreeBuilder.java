package jsontree;

import java.util.Stack;

import parser.AbstractJsonParser;
import parser.InvalidJsonException;
import parser.JsonParser;
import validator.JsonStateMachine;
import validator.Status;

/**
 * JsonTreeBuilder is the implementation of the JsonParser interface.
 * Represents a simple JSON parser that accepts input one character at a time.
 * Contains helper method to support the parsing logic.
 * Maintains a state Machine & changes to another state depending on char in input stream.
 * There are total eight states to handle key, value, arrays and nested objects.
 */
public class JsonTreeBuilder extends AbstractJsonParser<JsonNode> {

  private JsonNode jsonRootNode;
  private Stack<Object> nodeStack;
  private StringBuilder currentStringValue;

  /**
   * Initializes a new JSON validator with default values.
   * Sets up two empty stack to track json objects and json parenthesis.
   * Initializes the state machine to state INITIALIZATION.
   * Sets the current state to EMPTY and jsonRootNode to NULL.
   */
  public JsonTreeBuilder() {
    nodeStack = new Stack<>();
    currentStringValue = new StringBuilder();
    jsonRootNode = null;
  }

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
  public JsonParser<JsonNode> input(char c) throws InvalidJsonException {
    if (currentStatus.equals(String.valueOf(Status.INVALID)) || canSkipWhiteSpace(c)) {
      return this;
    }
    processStateMachine(c);
    return this;
  }

  /**
   * Implements Output functionality from the JsonParser interface.
   * The method returns the jsonRootNode already computed within the parser helper functions.
   *
   * @return jsonRootNode the root of the JsonObject.
   */
  @Override
  public JsonNode output() {
    return jsonRootNode;
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
        throwInvalidJson("unexpected state");
        break;
    }
  }

  /**
   * Throws an InvalidJsonException with a custom message.
   * Updates the status to INVALID and sets root node to NULL.
   * Used by helper methods in the class as a single point of contact to throw exception.
   *
   * @throws InvalidJsonException always throws an invalid json exception
   */
  protected void throwInvalidJson(String message) throws InvalidJsonException {
    currentStatus = String.valueOf(Status.INVALID);
    jsonRootNode = null;
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
    nodeStack.push(new JsonObject());
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
  private void validateFirstKeyCharacterCheck(char c) throws InvalidJsonException {
    if (!Character.isLetter(c)) {
      throwInvalidJson("json first char should be letter");
    }
    currentStringValue.append(c);
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
      validateFirstKeyCharacterCheck(c);
      return;
    }
    if (c != QUOTE) {
      currentStringValue.append(c);
    } else {
      nodeStack.push(currentStringValue);
      currentStringValue = new StringBuilder("");
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
    currentStringValue = new StringBuilder("");
    currentState = JsonStateMachine.EXPECT_VALUE_OR_NESTED_OBJECT;
  }

  /**
   * Helper function to handles array when [ is encountered in the input stream.
   * Creates a JsonArray Object and pushes it into the nodeStack.
   */
  private void handleArrayOpening() {
    currentState = JsonStateMachine.EXPECT_VALUE_OR_NESTED_OBJECT;
    characterStack.push(OPEN_BRACKET);
    nodeStack.push(new JsonArray());
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
        handleArrayOpening();
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
   * The value state ends  upon encountering Quotes.
   * The stored value is send to a helper function for processing.
   *
   * @param c The current character
   */

  private void handleValue(char c) {
    if (c != QUOTE) {
      currentStringValue.append(c);
    }
    if (c == QUOTE) {
      addValueToParent(new JsonString(currentStringValue.toString()));
      currentStringValue = new StringBuilder("");
      currentState = JsonStateMachine.FINAL_STAGE;
    }
  }

  /**
   * This helper method processes characters after value state ends.
   * The next expected character can be an array value or the start of an object.
   * This method redirects to the appropriate state based on the character.
   * If the expected character is missing for the next state, an Exception is thrown.
   *
   * @param c The current character.
   * @throws InvalidJsonException if the character is invalid.
   */
  private void handleFinalStage(char c) throws InvalidJsonException {
    switch (c) {
      case CLOSE_BRACE:
        handleFlowerClosing(c);
        break;
      case COMMA:
        handleComma(c);
        break;
      case CLOSE_BRACKET:
        handleBracketClosing(c);
        break;
      default:
        throwInvalidJson("json or array can end in only } or ]");
    }
  }

  /**
   * A value can be either a string or a nested object.
   * The helper method processes start of string or nested object based on input char c.
   * Node object JsonObject is created & pushed into node stack when the char is {.
   *
   * @param c The current character.
   * @throws InvalidJsonException if the character is invalid.
   */
  private void processValueAsStringOrObject(char c) throws InvalidJsonException {
    if (c == QUOTE) {
      currentStringValue = new StringBuilder("");
      currentState = JsonStateMachine.EXPECT_VALUE;
    } else if (c == OPEN_BRACE) {
      nodeStack.push(new JsonObject());
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
   * Before brace closing, the stack top is checked for { for validation.
   *
   * @param c The current character.
   * @throws InvalidJsonException if the closing brace is invalid.
   */
  private void handleFlowerClosing(char c) throws InvalidJsonException {
    if (characterStack.peek() == OPEN_BRACE) {
      characterStack.pop();
      flowerBraces--;
      if (flowerBraces == 0) {
        currentStatus = String.valueOf(Status.VALID);
        currentState = JsonStateMachine.END;
        jsonRootNode = (JsonNode) nodeStack.peek();
        nodeStack.pop();
      } else {
        currentState = JsonStateMachine.FINAL_STAGE;
        handleNestedObjClose();
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
  private void handleBracketClosing(char c) throws InvalidJsonException {
    if (characterStack.peek() == OPEN_BRACKET) {
      characterStack.pop();
      currentState = JsonStateMachine.FINAL_STAGE;
      pushValuesIntoArray();
    } else {
      throwInvalidJson("error in closing array with ]");
    }
  }

  /**
   * Receives JsonNode and adds it to array or object depending on current state.
   *
   * @param value The recently processed JsonNode value
   */
  private void addValueToParent(JsonNode value) {
    Object parent = nodeStack.peek();
    if (parent instanceof StringBuilder) {
      String key = nodeStack.peek().toString();
      nodeStack.pop();
      IJsonObject obj = (IJsonObject) nodeStack.peek();
      obj.add(key, value);
    } else if (parent instanceof IJsonArray) {
      ((IJsonArray) parent).add(value);
    }
  }

  /**
   * Used to create Object with key value pair after encountering a }.
   * Uses the helper function addValueToParent to form the json object.
   */
  private void handleNestedObjClose() {
    JsonNode objectAsValue = (JsonNode) nodeStack.peek();
    nodeStack.pop();
    addValueToParent(objectAsValue);
  }

  /**
   * Used to create array value object after encountering a ].
   * Uses the helper function addValueToParent to form the json object.
   */
  private void pushValuesIntoArray() {
    JsonNode arrayAsValue = (JsonNode) nodeStack.peek();
    nodeStack.pop();
    addValueToParent(arrayAsValue);
  }

}
