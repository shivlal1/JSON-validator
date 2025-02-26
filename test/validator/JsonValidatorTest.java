package validator;

import org.junit.Test;

import parser.InvalidJsonException;
import parser.JsonParser;

import static org.junit.Assert.assertEquals;

/**
 * Junit test class for JsonValidator class.
 * Testes Every aspect and combination of json such as Nested Json , Nested Array.
 * Tests cases are also included for special characters in between Key and Values.
 * Test cases also test the status of the json parser
 */
public class JsonValidatorTest {

  @Test
  public void invalidKey() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    try {
      String str = "{ \"1\" : \"h\"}";
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      String errorMessage;
      errorMessage = "Error: json first char should be letterState :Processing key characters";
      assertEquals(e.getMessage(), errorMessage);
      assertEquals("Status:Invalid", validator.output());
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKey2() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"1 \" : \"hello\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKey3() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \" 1 \" : \"helloHowAreYou\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void simpleJson() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"mission\" : \"impossible\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }


  @Test
  public void valueAsanObject() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"aforApple\" : {  \"bForBall\" : \"cForCat\"}  }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void nestedObjAsValue() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"peace\" : {  \"is\" :  {  \"the\" : \"mission\" }  } }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  /**
   * TEST CASES FOR JSON START and JSON KEY.
   */
  @Test
  public void testConstructor() {
    // status should be empty
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
  }

  @Test
  public void simpleJsonWithSpace() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"isThisValid\" : \"Yes! It is Valid\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonValidator) validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void simpleJsonWithNoSpace() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"zFor\":\"Zebra\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonValidator) validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyStartingWithNum() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"1\" : \"key cannot start with number\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonValidator) validator.input(ch);
    }
  }

  @Test
  public void invalidKeyStartingWithoutFlowerBraces() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    try {
      String str = " \"1\" : \"key should be preceed by { braces\"}";
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator = (JsonValidator) validator.input(ch);
      }
    } catch (Exception e) {
      validator = (JsonParser) validator.input('a');
      assertEquals("Status:Invalid", validator.output());
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyStartingWithSpecialCharacterAtBeginning() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"@1\" : \"Key cannot have special characters\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyStartingWithSpecialCharacterInBetween() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"a^1\" : \"key cannot have special characters in between also\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyStartingWithSpecialCharacterAtEnd() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"a1()\" : \"that's not a valid json\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyStartingWithSpace() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \" abc\" : \"efg\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithSpaceInBetween() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"ab c\" : \"is space allowed ? NOOO!!!\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithSpaceAtEnd() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"abc \" : \"can end with space ? NO !!!\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithSpaceAndSpecialCharacterAndNum() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"a&1 c \" : \"are nums allowed?\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithColon() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"key:\" : \"is clon allowed in key ? well! that's a no\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithComma() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{ \"key,\" : \"is comma allowed ? that's also no\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithFlowerBraces() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{ \"key{\" : \"flower braces atleast ?\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithArrayBraces() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{ \"key[\" : \"array braces atleast ? \"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyStartingWithoutQuotes() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{key\" : \"can i atleast start without quotes\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyEndWithoutQuotes() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key:\"end without quotes ?\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void jsonWithoutKey() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{:\"This json has no key. only value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test
  public void spaceBeforeJsonStart() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = " {\"key\" : \"Extra space\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());

  }

  @Test
  public void spaceAfterJsonEnd() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\" : \"extra space\"}  ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());

  }

  @Test
  public void jsonStartWithoutFlowerBraces() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "\"key\" : \"value\"}";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator = (JsonParser) validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals("Status:Invalid", validator.output());
    }
  }

  @Test
  public void jsonStartWithCharactersAfterException() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    String str = " xx{\"key\" : \"value\"}";
    assertEquals("Status:Empty", validator.output());
    char ch = 'a';
    try {
      for (int i = 0; i < str.length(); i++) {
        ch = str.charAt(i);
        validator = (JsonParser) validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals("Status:Invalid", validator.output());
    }

  }

  @Test
  public void jsonStartWithSpecialChars() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "@{ \"key\" : \"value\"}";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator = (JsonParser) validator.input(ch);
      }
    } catch (Exception e) {
      String message = "Error: json should start with {State :Initial state, expecting { brace";
      assertEquals(message, e.getMessage());
      assertEquals("Status:Invalid", validator.output());
    }
  }

  @Test
  public void jsonStartWithNumbers() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "@{\"key\" : \"value\"}";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator = (JsonParser) validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals("Status:Invalid", validator.output());
    }
  }

  /**
   * TEST CASES FOR CHAR INBETWEEN KEY AND VALUE.
   */
  @Test(expected = InvalidJsonException.class)
  public void keyValueNotSeparatedByColon() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\"\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void keyValueSeparatedByNumber() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\"12\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void keyValueeparatedSoecialChars() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\",@\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void keyValueeparatedByComma() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\",\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  /**
   * TEST CASES For Value String.
   */
  @Test
  public void oneSpaceBeforeValueStarts() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\": \"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void multipleSpaceBeforeValueStarts() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":    \"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void spaceAsFirstCharInValu() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\" value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void multipleSpacesAsFirstCharInValu() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"    value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void singleSpaceInBetweenValue() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"val ue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void newLineSpaceInBetweenValue() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"val\nue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void newLineSpaceInBetweenKey() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"ke\ny\":\"val\nue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void multipleSpaceInBetweenValue() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"val    ue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void multipleSpaceAndLinesBetweenValue() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"val   \n \n ue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueStartsWithNumber() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"1value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueWithNumberAndSpecialCharacter() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"val1@ue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueWithSpaceNumberAndSpecialCharacter() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"val 1@ue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueEndsWithNumber() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"value1\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueEndsWithSpecialChar() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"value@\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueWithAllCaps() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"afor\":\"APPLE\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueStartsWithCaps() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"cfor\":\"Catch\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueWithAllPossibilities() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"V{}1@5[]-:e\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void valueStartWithoutFlowerQuotes() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":IknowThisIsWrong\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void valueEndWithoutFlowerQuotes() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"thisIsIncomplete}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Incomplete", validator.output());
  }

  /**
   * TEST CASES FOR nested JSON.
   */

  @Test
  public void nestedJsonOneLevel() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":  {\"nestedKey1\":\"nestedValue1\"}  }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void nestedJsonTwoLevel() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key1\":  {\"key2\": {\"key3\":\"nestedValue567^8\"} }  }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  /**
   * Test Cases with Array as Values.
   */

  @Test
  public void valueAsArrayStartsWithNoSpace() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":[\"value1\",\"value2\"]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueAsArrayStartsWithSingleSpace() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\": [\"value1\",\"value2\"]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueAsArrayStartsWithMultipleSpace() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":   [\"value1\",\"value2\"]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueAsArrayEndsWithSingleSpace() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":[\"value1\",\"value2\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueAsArrayEndsWithMultipleSpace() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":[\"value1\",\"value2\"]   }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueAsArray() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":  [\"value1\",\"value2\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueAsArrayWithSpaces() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":  [\"value1\"  ,  \"value2\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueAsArrayWithOnlyString() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"fruits\":[\"apple\",\"orange\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void valueAsArrayWithOnlyComma() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"fruits\":[,] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void valueAsArrayWithOnlyNumbers() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"numbers\":[\"1\",\"2\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueAsArrayWithSpecialChars() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"numbersAndSpecialSymbols\":[\"1 @\",\"2 6 7\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());

  }

  @Test
  public void valueAsArrayWithSaces() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"SubjectList\":[\"CS 50\",\"CS 60\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueAsArrayContainsObject() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":[ {\"arrayKey\":\"arrayValue\"} ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueAsArrayContainsMultipleObjects() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    String str = "{\"multipleObjects\":[ {\"key1\":\"value1\"} , {\"key2\":\"value2\"} ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void arrayContainsAnotherArray() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":[ {\"fruit\":\"pineapple\"} , [\"pizza\"] ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void arrayContainsAnotherArrayWhichAgainContainsAnObject() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"complexJson\":[ {\"key1\":\"val1\"} , [\"hello\",{\"key1\":\"val1\"}] ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void valueInArrayIsNestedObject() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":[ {\"k1\":{\"k1\":\"v1\"}} , [\"hello\",{\"k1\":\"v1\"}] ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void multipleNestedArray() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":[ [ [\"hello\",{\"star\":\"gaze\"}]] ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void multipleNestedArrayWithMultipleNestedObjects() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"json\":[[[\"A\",{\"B\":{\"C\":{\"D\":\"E\"}}}]]]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void nestedObjectWithArrayValue() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":{\"Hello\":{\"giveMeNumberList\":[\"1\" , \"2\", \"3\"]}} }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void nestedObjectNestedArray() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":{\"k1\":{\"k1\":[\"1\" , [ \"2\"] ]}}}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void arrayWithNumbers() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"RealNumbers\":[\"1\",\"2.0\",\"-3.56\",\"+5.6\"]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void arrayWithNumbersWithMutltipleTypes() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"allTypes\":[\"+1\",{\"k\":\"v\"},[\"arrayVal\"] , \"string\"]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  /**
   * TEST CASES For Invalid Json.
   */

  @Test
  public void extraCharacAfterJsonEnd() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"value\"} ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void InvalidCharacAfterJsonEnd() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"value\"}xx";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    //assertEquals("Status:Valid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void jsonObjStartingAgain() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":\"value\"}{";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    //assertEquals("Status:Valid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void jsonObjWithouKeyAndValue() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    //assertEquals("Status:Valid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void jsonObjWithouKey() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{ : \"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    //assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void jsonContainsEmptyArray() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{ \"value\" : [] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void arrayEndingIsWrong() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    // should be } and then ]. but the order is reversed
    String str = "{ \"Key\" : [ \"name\"] , { \"time\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void wrongFlowerBracesClosing() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    // value in the array object is not present
    assertEquals("Status:Empty", validator.output());
    String str = "{ \"Key\" : [ \"name\"] , { \"time\"} }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonValidator) validator.input(ch);
    }
  }

  @Test
  public void checkStatusAfterInvalid() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{ \"Key\" :}}}";
    //Catch invalid exception & input one more character & see if the status is still invalid.
    char ch;
    try {
      for (int i = 0; i < str.length(); i++) {
        ch = str.charAt(i);
        validator = (JsonValidator) validator.input(ch);
      }
    } catch (Exception e) {
      str = " ";
      for (int i = 0; i < str.length(); i++) {
        ch = str.charAt(i);
        validator = (JsonValidator) validator.input(ch);
      }
      assertEquals("Status:Invalid", validator.output());
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void arrayNotClosedProperly() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{\"key\":[\"1\",\"2\", {\"Key\"} ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Invalid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void arrayisMissingComma() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    String str = "{\"key\":[\"1\" \"2\"]} ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Invalid", validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidFlowerBracesClosing() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{ \"html\": { \"head\": [\"This\",\"is\",\"a\",\"header\"} } }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidArrayBracketClosing() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{ \"html\": \"head\"]";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void onlyEmptyInput() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = " ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Empty", validator.output());
  }

  @Test
  public void statusInComplete() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"key\":\"va";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Incomplete", validator.output());
  }

  @Test
  public void keyAndValueWithMultipleSpaces() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{  \"key\"  :  \"va  lue\"   }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
      if (i != str.length() - 1) {
        assertEquals("Status:Incomplete", validator.output()); //check in between
      }
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void duplicateKyesCase() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"key\":\"value1\" , \"key\":\"value2\", \"key\":\"value3\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void duplicatevalues() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"key1\":\"value\" , \"key2\":\"value\", \"key3\":\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void duplicateKeyAndValues() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"key\":\"value\" , \"key\":\"value\", \"key\":\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void differentKeyValuePairs() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"key1\":\"value2\" , \"key3\":\"value4\", \"key5\":\"value6\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void nestedObjectAndASingleValue() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"course\": {\"name\":\"PDP\",\"Code\":\"5010\"} , \"term\" : \"Fall\" }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void arrayWithSimplevalues() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"course\": \"CS5010\" , \"sections\" : [\"A1\",\"A2\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void arryContainsObjAndArrys() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String arrajObject1 = "{ \"name\" : \"PDP\", \"sections\": [\"10\",\"20\"] }";
    String arrajObject2 = "{ \"name\" : \"Algo\", \"sections\": [\"20\",\"30\"] }";

    String str = "{ \"course\" : [" + arrajObject1 + "," + arrajObject2 + "] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void threeLevelnesting() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"outer\":{\"inner\":{\"deepest\":\"value\"}}}\n";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void allMixed() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"mixedArray\":[\"string1\",{\"key\":\"value\"},[\"nested\",\"array\"]]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }


  @Test
  public void twoDArray() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"matrix\":[[\"a\",\"b\"],[\"c\",\"d\"]]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void emptyValue() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"Helloe\":\"\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void emptyValueWithOnlySpaces() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());

    String str = "{\"Helloe\":\"   \"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void keyValuePairsMissingComma() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    String str = "{\"class1\":\"Algo\" , \"class2\":\"PDP\" \"class2\":\"PDP\"}\n";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals("Status:Invalid", validator.output());
    }

  }

  @Test
  public void checkStatusBeofreAndAfteInput() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    //string is only space
    String str = "     \n  ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Empty", validator.output());
  }

  @Test
  public void bracketMisMatch() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();

    String str = "{ \"key\" : [\"1\", \"2\" , \"3\"}";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals("Status:Invalid", validator.output());
    }
  }

  @Test
  public void bracketMisMatch2() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    String str = "{ \"key\" : [\"1\", \"2\" , \"3\"] ]";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals("Status:Invalid", validator.output());
    }
  }

  @Test
  public void missingArrayClosing() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    String str = "{ \"key\" : [\"1\", \"2\" , \"3\" }";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals("Status:Invalid", validator.output());
    }
  }

  @Test
  public void multipleKeysmultipleObjects() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    String str = "{\"key1\" : [\"1\"], \"key2\":\"string\",\"key3\":{ \"k\" : \"v\"} }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void arrayWithMultipleObj() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    String str = "{\"key1\" : [\"1\"], \"key2\":\"string\",\"key3\":{ \"k\" : \"v\"} }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void alternateObjAndArray() throws InvalidJsonException {
    JsonValidator validator = new JsonValidator();
    String str = "{\"key1\" : [ { \"k\" : [\"arrayString\"] }] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void spaceAfterValidInput() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str;
    str = "{ \"Key\" : \"value\" }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonValidator) validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());

    str = "   \n  ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonValidator) validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());
  }

  @Test
  public void checkIncompleteAndInvalidStatus() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str;
    str = "{ \"Key\" ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonValidator) validator.input(ch);
    }
    assertEquals("Status:Incomplete", validator.output());
    str = "x";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator = (JsonValidator) validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals("Status:Invalid", validator.output());
    }
  }

  @Test
  public void checkEmptyAndInvalidStatus() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "x";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator = (JsonValidator) validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals("Status:Invalid", validator.output());
    }
  }

  @Test
  public void checkValidAndInvalidStatus() throws InvalidJsonException {
    JsonParser validator = new JsonValidator();
    assertEquals("Status:Empty", validator.output());
    String str = "{ \"key\" : \"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonValidator) validator.input(ch);
    }
    assertEquals("Status:Valid", validator.output());

    try {
      validator = (JsonValidator) validator.input('x');
    } catch (Exception e) {
      assertEquals("Status:Invalid", validator.output());
    }
  }
}