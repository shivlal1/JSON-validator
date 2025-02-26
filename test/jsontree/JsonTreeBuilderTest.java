package jsontree;

import org.junit.Test;

import parser.InvalidJsonException;
import parser.JsonParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test case for JsonTreeBuilder class.
 * Uses the output() method to capture root of the jsonObject.
 * Tests functions of  pretty printing, equals, hashcode of jsonNode for the captured root.
 * Also includes test cases to test invalid key , invalid object , invalid arrays.
 * Test cases also includes detecting wrong opening and closing of both { and [ braces.
 */
public class JsonTreeBuilderTest {

  @Test
  public void invalidKey() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());
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
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKey2() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"1 \" : \"hello\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKey3() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \" 1 \" : \"helloHowAreYou\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void nestedObjAsValue() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"peace\" : {  \"is\" :  {  \"the\" : \"mission\" }  } }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    String jsonString = "{\n"
            + "  \"peace\":\n"
            + "  {\n"
            + "    \"is\":\n"
            + "    {\n"
            + "      \"the\":\"mission\"\n"
            + "    }\n"
            + "  }\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  /**
   * TEST CASES FOR JSON START and JSON KEY.
   */
  @Test
  public void testConstructor() {
    // root should be null
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());
  }

  @Test
  public void simpleJsonWithSpace() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"isThisValid\" : \"Yes! It is Valid\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    JsonNode root = (JsonNode) validator.output();

    String jsonString = "{\n"
            + "  \"isThisValid\":\"Yes! It is Valid\"\n"
            + "}";

    assertEquals(jsonString, root.prettyPrint());
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyStartingWithNum() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"1\" : \"key cannot start with number\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = validator.input(ch);
    }
  }

  @Test
  public void invalidKeyStartingWithoutFlowerBraces() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    try {
      String str = " \"1\" : \"key should be preceed by { braces\"}";
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      validator.input('a');
      assertEquals(null, validator.output());
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyStartingWithSpecialCharacterAtBeginning() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"@1\" : \"Key cannot have special characters\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }


  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithSpaceInBetween() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"ab c\" : \"is space allowed ? NOOO!!!\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithSpaceAtEnd() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"abc \" : \"can end with space ? NO !!!\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithSpaceAndSpecialCharacterAndNum() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"a&1 c \" : \"are nums allowed?\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithColon() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"key:\" : \"is clon allowed in key ? well! that's a no\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithComma() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"key,\" : \"is comma allowed ? that's also no\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithFlowerBraces() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"key{\" : \"flower braces atleast ?\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyWithArrayBraces() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"key[\" : \"array braces atleast ? \"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyStartingWithoutQuotes() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{key\" : \"can i atleast start without quotes\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidKeyEndWithoutQuotes() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key:\"end without quotes ?\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void jsonWithoutKey() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{:\"This json has no key. only value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void spaceBeforeJsonStart() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = " {\"key\" : \"Extra space\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    JsonNode root = (JsonNode) validator.output();
    String jsonString = "{\n"
            + "  \"key\":\"Extra space\"\n"
            + "}";

    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void spaceAfterJsonEnd() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\" : \"extra space\"}  ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }

    String jsonString = "{\n"
            + "  \"key\":\"extra space\"\n"
            + "}";
    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void jsonStartWithoutFlowerBraces() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "\"key\" : \"value\"}";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator = (JsonParser) validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals(null, validator.output());
    }
  }

  @Test
  public void jsonStartWithCharactersAfterException() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());
    String str = " xx{\"key\" : \"value\"}";
    char ch = 'a';
    try {
      for (int i = 0; i < str.length(); i++) {
        ch = str.charAt(i);
        validator = (JsonParser) validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals(null, validator.output());
    }

  }

  @Test
  public void jsonStartWithSpecialChars() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "@{ \"key\" : \"value\"}";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator = (JsonParser) validator.input(ch);
      }
    } catch (Exception e) {
      String message = "Error: json should start with {State :Initial state, expecting { brace";
      assertEquals(message, e.getMessage());
      assertEquals(null, validator.output());
    }
  }

  @Test
  public void jsonStartWithNumbers() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "1{\"key\" : \"value\"}";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator = (JsonParser) validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals(null, validator.output());
    }
  }

  /**
   * TEST CASES FOR CHAR INBETWEEN KEY AND VALUE.
   */
  @Test(expected = InvalidJsonException.class)
  public void keyValueNotSeparatedByColon() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\"\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void keyValueSeparatedByNumber() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\"12\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void keyValueeparatedSoecialChars() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\",@\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator = (JsonParser) validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void keyValueeparatedByComma() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

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
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\": \"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    String jsonString = "{\n" +
            "  \"key\":\"value\"\n" +
            "}";
    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void multipleSpaceBeforeValueStarts() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":    \"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n" +
            "  \"key\":\"value\"\n" +
            "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void spaceAsFirstCharInValu() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\" value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n" +
            "  \"key\":\" value\"\n" +
            "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void multipleSpacesAsFirstCharInValu() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"    value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }

    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key\":\"    value\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void singleSpaceInBetweenValue() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"val ue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());

    String jsonString = "{\n"
            + "  \"key\":\"val ue\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());

  }

  @Test
  public void newLineSpaceInBetweenValue() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"val\nue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n" +
            "  \"key\":\"val\nue\"\n" +
            "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test(expected = InvalidJsonException.class)
  public void newLineSpaceInBetweenKey() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"ke\ny\":\"val\nue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"ke\ny\":\"val\nue\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void multipleSpaceInBetweenValue() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"val    ue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key\":\"val    ue\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void multipleSpaceAndLinesBetweenValue() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"val   \n \n ue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }

    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key\":\"val   \n"
            + " \n"
            + " ue\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueStartsWithNumber() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"1value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key\":\"1value\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueWithNumberAndSpecialCharacter() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"val1@ue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key\":\"val1@ue\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueWithSpaceNumberAndSpecialCharacter() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"val 1@ue\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key\":\"val 1@ue\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueEndsWithNumber() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"value1\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key\":\"value1\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueEndsWithSpecialChar() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"value@\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key\":\"value@\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }


  @Test
  public void valueWithAllPossibilities() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"V{}1@5[]-:e\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());

    String jsonString = "{\n"
            + "  \"key\":\"V{}1@5[]-:e\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test(expected = InvalidJsonException.class)
  public void valueStartWithoutFlowerQuotes() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":IknowThisIsWrong\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void valueEndWithoutFlowerQuotes() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"thisIsIncomplete}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals(null, validator.output());

  }

  /**
   * TEST CASES FOR nested JSON.
   */

  @Test
  public void nestedJsonOneLevel() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":  {\"nestedKey1\":\"nestedValue1\"}  }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());

    String jsonString = "{\n"
            + "  \"key\":\n"
            + "  {\n"
            + "    \"nestedKey1\":\"nestedValue1\"\n"
            + "  }\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void nestedJsonTwoLevel() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key1\":  {\"key2\": {\"key3\":\"nestedValue567^8\"} }  }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    String jsonString = "{\n"
            + "  \"key1\":\n"
            + "  {\n"
            + "    \"key2\":\n"
            + "    {\n"
            + "      \"key3\":\"nestedValue567^8\"\n"
            + "    }\n"
            + "  }\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  /**
   * Test Cases with Array as Values.
   */

  @Test
  public void valueAsArrayStartsWithNoSpace() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":[\"value1\",\"value2\"]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }

    String jsonString = "{\n"
            + "  \"key\":\n"
            + "  [\n"
            + "    \"value1\",\n"
            + "    \"value2\"\n"
            + "  ]\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueAsArrayStartsWithSingleSpace() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\": [\"value1\",\"value2\"]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    String jsonString = "{\n"
            + "  \"key\":\n"
            + "  [\n"
            + "    \"value1\",\n"
            + "    \"value2\"\n"
            + "  ]\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueAsArrayStartsWithMultipleSpace() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":   [\"value1\",\"value2\"]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    String jsonString = "{\n"
            + "  \"key\":\n"
            + "  [\n"
            + "    \"value1\",\n"
            + "    \"value2\"\n"
            + "  ]\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }


  @Test
  public void valueAsArrayWithSpaces() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":  [\"value1\"  ,  \"value2\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());

    String jsonString = "{\n"
            + "  \"key\":\n"
            + "  [\n"
            + "    \"value1\",\n"
            + "    \"value2\"\n"
            + "  ]\n"
            + "}";


    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueAsArrayWithOnlyString() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"fruits\":[\"apple\",\"orange\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());

    String jsonString = "{\n"
            + "  \"fruits\":\n"
            + "  [\n"
            + "    \"apple\",\n"
            + "    \"orange\"\n"
            + "  ]\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test(expected = InvalidJsonException.class)
  public void valueAsArrayWithOnlyComma() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"fruits\":[,] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void valueAsArrayWithOnlyNumbers() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"numbers\":[\"1\",\"2\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"numbers\":\n"
            + "  [\n"
            + "    \"1\",\n"
            + "    \"2\"\n"
            + "  ]\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueAsArrayWithSpecialChars() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"numbersAndSpecialSymbols\":[\"1 @\",\"2 6 7\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());

    String jsonString = "{\n"
            + "  \"numbersAndSpecialSymbols\":\n"
            + "  [\n"
            + "    \"1 @\",\n"
            + "    \"2 6 7\"\n"
            + "  ]\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueAsArrayWithSaces() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"SubjectList\":[\"CS 50\",\"CS 60\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
  }

  @Test
  public void valueAsArrayContainsObject() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":[ {\"arrayKey\":\"arrayValue\"} ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    JsonNode root = (JsonNode) validator.output();

    String jsonString = "{\n"
            + "  \"key\":\n"
            + "  [\n"
            + "    {\n"
            + "      \"arrayKey\":\"arrayValue\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueAsArrayContainsMultipleObjects() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"multipleObjects\":[ {\"key1\":\"value1\"} , {\"key2\":\"value2\"} ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }

    String jsonString = "{\n"
            + "  \"multipleObjects\":\n"
            + "  [\n"
            + "    {\n"
            + "      \"key1\":\"value1\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"key2\":\"value2\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void arrayContainsAnotherArray() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":[ {\"fruit\":\"pineapple\"} , [\"pizza\"] ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }

    JsonNode root = (JsonNode) validator.output();

    String jsonString = "{\n"
            + "  \"key\":\n"
            + "  [\n"
            + "    {\n"
            + "      \"fruit\":\"pineapple\"\n"
            + "    },\n"
            + "    [\n"
            + "      \"pizza\"\n"
            + "    ]\n"
            + "  ]\n"
            + "}";

    assertEquals(jsonString, root.prettyPrint());

  }

  @Test
  public void arrayContainsAnotherArrayWhichAgainContainsAnObject() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();

    String str = "{\"complexJson\":[ {\"key1\":\"val1\"} , [\"hello\",{\"key1\":\"val1\"}] ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    String jsonString = "{\n"
            + "  \"complexJson\":\n"
            + "  [\n"
            + "    {\n"
            + "      \"key1\":\"val1\"\n"
            + "    },\n"
            + "    [\n"
            + "      \"hello\",\n"
            + "      {\n"
            + "        \"key1\":\"val1\"\n"
            + "      }\n"
            + "    ]\n"
            + "  ]\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void valueInArrayIsNestedObject() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();

    String str = "{\"key\":[ {\"k1\":{\"k1\":\"v1\"}} , [\"hello\",{\"k1\":\"v1\"}] ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    String jsonString = "{\n"
            + "  \"key\":\n"
            + "  [\n"
            + "    {\n"
            + "      \"k1\":\n"
            + "      {\n"
            + "        \"k1\":\"v1\"\n"
            + "      }\n"
            + "    },\n"
            + "    [\n"
            + "      \"hello\",\n"
            + "      {\n"
            + "        \"k1\":\"v1\"\n"
            + "      }\n"
            + "    ]\n"
            + "  ]\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void multipleNestedArray() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();

    String str = "{\"key\":[ [ [\"hello\",{\"star\":\"gaze\"}]] ] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    String jsonString = "{\n"
            + "  \"key\":\n"
            + "  [\n"
            + "    [\n"
            + "      [\n"
            + "        \"hello\",\n"
            + "        {\n"
            + "          \"star\":\"gaze\"\n"
            + "        }\n"
            + "      ]\n"
            + "    ]\n"
            + "  ]\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void multipleNestedArrayWithMultipleNestedObjects() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();

    String str = "{\"json\":[[[\"A\",{\"B\":{\"C\":{\"D\":\"E\"}}}]]]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }

    JsonNode root = (JsonNode) validator.output();
    String jsonString = "{\n"
            + "  \"json\":\n"
            + "  [\n"
            + "    [\n"
            + "      [\n"
            + "        \"A\",\n"
            + "        {\n"
            + "          \"B\":\n"
            + "          {\n"
            + "            \"C\":\n"
            + "            {\n"
            + "              \"D\":\"E\"\n"
            + "            }\n"
            + "          }\n"
            + "        }\n"
            + "      ]\n"
            + "    ]\n"
            + "  ]\n"
            + "}";

    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void nestedObjectWithArrayValue() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":{\"Hello\":{\"giveMeNumberList\":[\"1\" , \"2\", \"3\"]}} }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
  }

  @Test
  public void nestedObjectNestedArray() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":{\"k1\":{\"k1\":[\"1\" , [ \"2\"] ]}}}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
  }

  @Test
  public void arrayWithNumbers() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"RealNumbers\":[\"1\",\"2.0\",\"-3.56\",\"+5.6\"]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
  }

  @Test
  public void arrayWithNumbersWithMutltipleTypes() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"allTypes\":[\"+1\",{\"k\":\"v\"},[\"arrayVal\"] , \"string\"]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    JsonNode root = (JsonNode) validator.output();
    String jsonString = "{\n"
            + "  \"allTypes\":\n"
            + "  [\n"
            + "    \"+1\",\n"
            + "    {\n"
            + "      \"k\":\"v\"\n"
            + "    },\n"
            + "    [\n"
            + "      \"arrayVal\"\n"
            + "    ],\n"
            + "    \"string\"\n"
            + "  ]\n"
            + "}";
    assertEquals(root.prettyPrint(), jsonString);
    assertNotEquals(null, validator.output());
  }

  /**
   * TEST CASES For Invalid Json.
   */

  @Test
  public void extraCharacAfterJsonEnd() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"value\"} ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    JsonNode root = (JsonNode) validator.output();
    String jsonString = "{\n"
            + "  \"key\":\"value\"\n"
            + "}";

    assertEquals(root.prettyPrint(), jsonString);
  }

  @Test(expected = InvalidJsonException.class)
  public void InvalidCharacAfterJsonEnd() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"value\"}xx";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void jsonObjStartingAgain() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"value\"}{";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void jsonObjWithouKeyAndValue() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void jsonObjWithouKey() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ : \"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void jsonContainsEmptyArray() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"value\" : [] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    JsonNode root = (JsonNode) validator.output();
    String jsonString = "{\n"
            + "  \"value\":\n"
            + "  [\n"
            + "  ]\n"
            + "}";
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test(expected = InvalidJsonException.class)
  public void arrayEndingIsWrong() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();

    // should be } and then ]. but the order is reversed
    String str = "{ \"Key\" : [ \"name\"] , { \"time\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void wrongFlowerBracesClosing() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    // value in the array object is not present
    String str = "{ \"Key\" : [ \"name\"] , { \"time\"} }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void checkStatusAfterInvalid() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();

    String str = "{ \"Key\" :}}}";
    //Catch invalid exception & input one more character & see if the status is still invalid.
    char ch;
    try {
      for (int i = 0; i < str.length(); i++) {
        ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      str = " ";
      for (int i = 0; i < str.length(); i++) {
        ch = str.charAt(i);
        validator.input(ch);
        assertEquals(null, validator.output());
      }
      assertEquals(null, validator.output());
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void arrayNotClosedProperly() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();

    String str = "{\"key\":[\"1\",\"2\", {\"Key\"} ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals(null, validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void arrayisMissingComma() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    String str = "{\"key\":[\"1\" \"2\"]} ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals(null, validator.output());
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidFlowerBracesClosing() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();

    String str = "{ \"html\": { \"head\": [\"This\",\"is\",\"a\",\"header\"} } }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test(expected = InvalidJsonException.class)
  public void invalidArrayBracketClosing() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();

    String str = "{ \"html\": \"head\"]";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
  }

  @Test
  public void onlyEmptyInput() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = " ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals(null, validator.output());
  }

  @Test
  public void statusInComplete() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"va";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals(null, validator.output());
  }

  @Test
  public void keyAndValueWithMultipleSpaces() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{  \"key\"  :  \"va  lue\"   }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
      if (i != str.length() - 1) {
        assertEquals(null, validator.output());
        //check in between
      }
    }
    assertNotEquals(null, validator.output());
    JsonNode root = (JsonNode) validator.output();

    String jsonString = "{\n"
            + "  \"key\":\"va  lue\"\n"
            + "}";

    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void duplicateKyesCase() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"value1\" , \"key\":\"value2\", \"key\":\"value3\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    String jsonString = "{\n"
            + "  \"key\":\"value1\",\n"
            + "  \"key\":\"value2\",\n"
            + "  \"key\":\"value3\"\n"
            + "}";
    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void duplicatevalues() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key1\":\"value\" , \"key2\":\"value\", \"key3\":\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key1\":\"value\",\n"
            + "  \"key2\":\"value\",\n"
            + "  \"key3\":\"value\"\n"
            + "}";
    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void duplicateKeyAndValues() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key\":\"value\" , \"key\":\"value\", \"key\":\"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key\":\"value\",\n"
            + "  \"key\":\"value\",\n"
            + "  \"key\":\"value\"\n"
            + "}";
    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void differentKeyValuePairs() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"key1\":\"value2\" , \"key3\":\"value4\", \"key5\":\"value6\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key1\":\"value2\",\n"
            + "  \"key3\":\"value4\",\n"
            + "  \"key5\":\"value6\"\n"
            + "}";
    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void nestedObjectAndASingleValue() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"course\": {\"name\":\"PDP\",\"Code\":\"5010\"} , \"term\" : \"Fall\" }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());

    String jsonString = "{\n"
            + "  \"course\":\n"
            + "  {\n"
            + "    \"name\":\"PDP\",\n"
            + "    \"Code\":\"5010\"\n"
            + "  },\n"
            + "  \"term\":\"Fall\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void arrayWithSimplevalues() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"course\": \"CS5010\" , \"sections\" : [\"A1\",\"A2\"] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
  }

  @Test
  public void arryContainsObjAndArrys() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String arrajObject1 = "{ \"name\" : \"PDP\", \"sections\": [\"10\",\"20\"] }";
    String arrajObject2 = "{ \"name\" : \"Algo\", \"sections\": [\"20\",\"30\"] }";

    String str = "{ \"course\" : [" + arrajObject1 + "," + arrajObject2 + "] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    JsonNode root = (JsonNode) validator.output();
    String jsonString = "{\n"
            + "  \"course\":\n"
            + "  [\n"
            + "    {\n"
            + "      \"name\":\"PDP\",\n"
            + "      \"sections\":\n"
            + "      [\n"
            + "        \"10\",\n"
            + "        \"20\"\n"
            + "      ]\n"
            + "    },\n"
            + "    {\n"
            + "      \"name\":\"Algo\",\n"
            + "      \"sections\":\n"
            + "      [\n"
            + "        \"20\",\n"
            + "        \"30\"\n"
            + "      ]\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void threeLevelnesting() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"outer\":{\"inner\":{\"deepest\":\"value\"}}}\n";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
  }

  @Test
  public void allMixed() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"mixedArray\":[\"string1\",{\"key\":\"value\"},[\"nested\",\"array\"]]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
  }


  @Test
  public void twoDArray() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"matrix\":[[\"a\",\"b\"],[\"c\",\"d\"]]}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
  }

  @Test
  public void emptyValue() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"Helloe\":\"\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());

    String jsonString = "{\n"
            + "  \"Helloe\":\"\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void emptyValueWithOnlySpaces() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{\"Helloe\":\"   \"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"Helloe\":\"   \"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void keyValuePairsMissingComma() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());
    String str = "{\"class1\":\"Algo\" , \"class2\":\"PDP\" \"class2\":\"PDP\"}\n";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals(null, validator.output());
    }

  }

  @Test
  public void checkStatusBeofreAndAfteInput() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    //string is only space
    String str = "     \n  ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals(null, validator.output());
  }

  @Test
  public void bracketMisMatch() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();

    String str = "{ \"key\" : [\"1\", \"2\" , \"3\"}";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals(null, validator.output());
    }
  }

  @Test
  public void bracketMisMatch2() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());
    String str = "{ \"key\" : [\"1\", \"2\" , \"3\"] ]";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals(null, validator.output());
    }
  }

  @Test
  public void missingArrayClosing() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"key\" : [\"1\", \"2\" , \"3\" }";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals(null, validator.output());
    }
  }

  @Test
  public void multipleKeysmultipleObjects() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());
    String str = "{\"key1\" : [\"1\"], \"key2\":\"string\",\"key3\":{ \"k\" : \"v\"} }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key1\":\n"
            + "  [\n"
            + "    \"1\"\n"
            + "  ],\n"
            + "  \"key2\":\"string\",\n"
            + "  \"key3\":\n"
            + "  {\n"
            + "    \"k\":\"v\"\n"
            + "  }\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void arrayWithMultipleObj() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());
    String str = "{\"key1\" : [\"1\"], \"key2\":\"string\",\"key3\":{ \"k\" : \"v\"} }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
  }

  @Test
  public void alternateObjAndArray() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());
    String str = "{\"key1\" : [ { \"k\" : [\"arrayString\"] }] }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
  }

  @Test
  public void spaceAfterValidInput() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str;
    str = "{ \"Key\" : \"value\" }";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());

    str = "   \n  ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());

    String jsonString = "{\n"
            + "  \"Key\":\"value\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
  }

  @Test
  public void checkIncompleteAndInvalidStatus() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str;
    str = "{ \"Key\" ";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertEquals(null, validator.output());

    str = "x";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals(null, validator.output());
    }
  }

  @Test
  public void checkEmptyAndInvalidStatus() throws InvalidJsonException {
    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "x";
    try {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        validator.input(ch);
      }
    } catch (Exception e) {
      assertEquals(null, validator.output());
    }
  }

  @Test
  public void checkValidAndInvalidStatus() throws InvalidJsonException {

    JsonParser validator = new JsonTreeBuilder();
    assertEquals(null, validator.output());

    String str = "{ \"key\" : \"value\"}";
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      validator.input(ch);
    }
    assertNotEquals(null, validator.output());
    String jsonString = "{\n"
            + "  \"key\":\"value\"\n"
            + "}";

    JsonNode root = (JsonNode) validator.output();
    assertEquals(jsonString, root.prettyPrint());
    try {
      validator.input('x');
    } catch (Exception e) {
      assertEquals(null, validator.output());
    }
  }

  @Test
  public void testSameJson() throws InvalidJsonException {

    // Two Json with same string will result in same pretty print
    JsonParser validator1 = new JsonTreeBuilder();
    String str1 = "{ \"key\" : \"value\"}";
    for (int i = 0; i < str1.length(); i++) {
      char ch = str1.charAt(i);
      validator1.input(ch);
    }
    assertNotEquals(null, validator1.output());
    JsonNode root1 = (JsonNode) validator1.output();

    JsonParser validator2 = new JsonTreeBuilder();
    String str2 = "{ \"key\" : \"value\"}";
    for (int i = 0; i < str2.length(); i++) {
      char ch = str2.charAt(i);
      validator2.input(ch);
    }
    assertNotEquals(null, validator2.output());
    JsonNode root2 = (JsonNode) validator2.output();

    assertEquals(root1.prettyPrint(), root2.prettyPrint());
    assertEquals(true, root1.equals(root2));
    assertEquals(root1.hashCode(), root2.hashCode());
  }

  @Test
  public void testSameJsonWithSpaceDifference() throws InvalidJsonException {

    JsonParser validator1 = new JsonTreeBuilder();
    String str1 = "{ \"key\" : \n\"value\"}";
    for (int i = 0; i < str1.length(); i++) {
      char ch = str1.charAt(i);
      validator1.input(ch);
    }
    assertNotEquals(null, validator1.output());
    JsonNode root1 = (JsonNode) validator1.output();

    JsonParser validator2 = new JsonTreeBuilder();
    String str2 = "{ \"key\" \n : \"value\" \n } \n";
    for (int i = 0; i < str2.length(); i++) {
      char ch = str2.charAt(i);
      validator2.input(ch);
    }
    assertNotEquals(null, validator2.output());
    JsonNode root2 = (JsonNode) validator2.output();

    assertEquals(root1.prettyPrint(), root2.prettyPrint());
    assertEquals(true, root1.equals(root2));
    assertEquals(root1.hashCode(), root2.hashCode());
  }


  @Test
  public void testSameJsonWithDifferentOrder() throws InvalidJsonException {

    JsonParser validator1 = new JsonTreeBuilder();
    String str1 = "{ \"key\" : \n\"value\" , \"key2\" : \n\"value2\"}";
    for (int i = 0; i < str1.length(); i++) {
      char ch = str1.charAt(i);
      validator1.input(ch);
    }
    assertNotEquals(null, validator1.output());
    JsonNode root1 = (JsonNode) validator1.output();

    JsonParser validator2 = new JsonTreeBuilder();
    String str2 = "{ \"key2\" : \n\"value2\" , \"key\" : \n\"value\"}";
    for (int i = 0; i < str2.length(); i++) {
      char ch = str2.charAt(i);
      validator2.input(ch);
    }
    assertNotEquals(null, validator2.output());
    JsonNode root2 = (JsonNode) validator2.output();

    assertNotEquals(root1.prettyPrint(), root2.prettyPrint());
    assertEquals(true, root1.equals(root2));
    assertEquals(root1.hashCode(), root2.hashCode());
  }


  @Test
  public void testSameJsonWithDifferentOrder2() throws InvalidJsonException {

    JsonParser validator1 = new JsonTreeBuilder();
    String str1 = "{ \"key\" : \n\"value\" , \"key2\" : \n\"value2\"}";
    for (int i = 0; i < str1.length(); i++) {
      char ch = str1.charAt(i);
      validator1.input(ch);
    }
    assertNotEquals(null, validator1.output());
    JsonNode root1 = (JsonNode) validator1.output();

    JsonParser validator2 = new JsonTreeBuilder();
    String str2 = "{ \"key2\" : \n\"value2\" , \"key\" : \n\"value\"}";
    for (int i = 0; i < str2.length(); i++) {
      char ch = str2.charAt(i);
      validator2.input(ch);
    }
    assertNotEquals(null, validator2.output());
    JsonNode root2 = (JsonNode) validator2.output();

    assertNotEquals(root1.prettyPrint(), root2.prettyPrint());
    assertEquals(true, root1.equals(root2));
    assertEquals(root1.hashCode(), root2.hashCode());
  }

  @Test
  public void prettyPrintCheck() throws InvalidJsonException {

    IJsonArray arr = new JsonArray();
    arr.add(new JsonString("v1"));
    arr.add(new JsonString("v2"));

    IJsonObject nestedObj = new JsonObject();
    nestedObj.add("StringValue", new JsonString("val"));

    IJsonObject obj = new JsonObject();
    obj.add("StringValue", new JsonString("val"));
    obj.add("ArrValue", arr);
    obj.add("nestedObj", nestedObj);

    String nestObj = "{ \"StringValue\" :  \"val\"}";

    String str1 = "{\n"
            + "  \"StringValue\":\"val\","
            + "  \"ArrValue\":"
            + "  ["
            + "    \"v1\","
            + "    \"v2\""
            + "  ],"
            + "  \"nestedObj\":"
            + "  {"
            + "    \"StringValue\":\"val\"\n"
            + "  }\n"
            + "}";
    JsonParser validator1 = new JsonTreeBuilder();

    for (int i = 0; i < str1.length(); i++) {
      char ch = str1.charAt(i);
      validator1.input(ch);
    }

    JsonNode root = (JsonNode) validator1.output();

    assertEquals(root.prettyPrint(), obj.prettyPrint());
    assertEquals(true, root.equals(obj));
    assertEquals(obj.hashCode(), root.hashCode());
  }

  @Test
  public void checkObjectSame() throws InvalidJsonException {

    IJsonArray arr = new JsonArray();
    arr.add(new JsonString("v1"));
    arr.add(new JsonString("v2"));

    IJsonObject nestedObj = new JsonObject();
    nestedObj.add("StringValue", new JsonString("val"));

    IJsonObject obj = new JsonObject();
    obj.add("StringValue", new JsonString("val"));
    obj.add("ArrValue", arr);
    obj.add("nestedObj", nestedObj);

    String nestObj = "{ \"StringValue\" :  \"val\"}";

    String str1 = obj.prettyPrint();

    JsonParser validator1 = new JsonTreeBuilder();

    for (int i = 0; i < str1.length(); i++) {
      char ch = str1.charAt(i);
      validator1.input(ch);
    }

    JsonNode root = (JsonNode) validator1.output();

    assertEquals(root.prettyPrint(), obj.prettyPrint());
    assertEquals(true, root.equals(obj));
    assertEquals(obj.hashCode(), root.hashCode());
  }

  @Test
  public void checkObjectDifferent() throws InvalidJsonException {

    IJsonArray arr = new JsonArray();
    arr.add(new JsonString("v1"));
    arr.add(new JsonString("v2"));

    IJsonObject nestedObj = new JsonObject();
    nestedObj.add("StringValue", new JsonString("val"));

    IJsonObject obj = new JsonObject();
    obj.add("StringValue", new JsonString("val"));
    obj.add("ArrValue", arr);
    obj.add("nestedObj", nestedObj);

    String str1 = "{ \"Key\" :  \"val\"}";

    JsonParser validator1 = new JsonTreeBuilder();

    for (int i = 0; i < str1.length(); i++) {
      char ch = str1.charAt(i);
      validator1.input(ch);
    }

    JsonNode root = (JsonNode) validator1.output();

    assertNotEquals(root.prettyPrint(), obj.prettyPrint());
    assertNotEquals(true, root.equals(obj));
    assertNotEquals(obj.hashCode(), root.hashCode());
  }

}