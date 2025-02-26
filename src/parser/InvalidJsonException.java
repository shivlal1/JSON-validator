package parser;

/**
 * This class represents a checked exception. This is used by JsonParser
 * implementations to signal invalid JSON.
 */

public class InvalidJsonException extends Exception {
  /**
   * serves the purpouse of passing the message to the parent constructor.
   *
   * @param message text message describing the exception.
   */
  public InvalidJsonException(String message) {
    super(message);
  }
}
