package jsontree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Test Class for the class JsonString.
 * Tests string node creation , pretty print , string node equals and hashcode functionality.
 * Test cases also include cases to test constructors.
 */
public class JsonStringTest {

  @Test
  public void testStringConstructor() {
    JsonNode js = new JsonString("constructor");
    assertEquals("\"constructor\"", js.prettyPrint());
  }

  @Test
  public void testObjUsingStringObject() {
    JsonString str = new JsonString("");
    assertEquals("\"\"", str.prettyPrint());
  }

  @Test
  public void simpleStringTest() {
    JsonNode js = new JsonString("hello");
    assertEquals("\"hello\"", js.prettyPrint());
  }

  @Test
  public void emptyStringTest() {
    JsonNode js = new JsonString("");
    assertEquals("\"\"", js.prettyPrint());

    assertEquals(new JsonString("").hashCode(), new JsonString("").hashCode());
  }

  @Test
  public void stringWithSpecialCharacters() {
    JsonString js = new JsonString("Hello how are you ?");
    assertEquals("\"Hello how are you ?\"", js.prettyPrint());
  }

  @Test
  public void checkEqualString() {
    JsonNode string1 = new JsonString("Hello");
    JsonNode string2 = new JsonString("Hello");
    assertEquals(true, string1.equals(string2));
    assertEquals(string1.hashCode(), string2.hashCode());

  }

  @Test
  public void checkNotEqualString() {
    JsonNode string1 = new JsonString("Hello");
    JsonNode string2 = new JsonString("Hello 123");
    assertEquals(false, string1.equals(string2));
  }

  @Test
  public void checkEuqalForBigString() {
    JsonNode string1 = new JsonString("Hello, How are \n you ? ");
    JsonNode string2 = new JsonString("Hello, How are \n you ? ");
    assertEquals(true, string1.equals(string2));
  }

  @Test
  public void checkEuqalForEmptyString() {
    JsonNode string1 = new JsonString("");
    JsonNode string2 = new JsonString("");
    assertEquals(true, string1.equals(string2));
  }

  @Test
  public void checkEqualForEmptyWithDifferentObjectString() {
    JsonNode string1 = new JsonString("Hello");
    String string2 = "Hello";
    assertEquals(false, string1.equals(string2));
  }

  @Test
  public void checkEqualWithArray() {
    JsonNode string1 = new JsonString("Hello");
    IJsonArray array = new JsonArray();
    array.add(new JsonString("Hello"));
    assertEquals(false, string1.equals(array));
  }

  @Test
  public void checkEqualWithObject() {
    JsonNode string1 = new JsonString("Hello");

    IJsonArray array = new JsonArray();
    array.add(new JsonString("Hello"));
    IJsonObject object = new JsonObject();
    object.add("Key", array);
    assertEquals(false, string1.equals(object));
  }

  @Test
  public void checkHashCodeEqual() {
    JsonNode str1 = new JsonString("This is a string object");
    JsonNode str2 = new JsonString("This is a string object");
    assertEquals(str1.hashCode(), str2.hashCode());
  }


  @Test
  public void checkHashCodeNotEqual() {
    JsonNode str1 = new JsonString("This is a string object");
    JsonNode str2 = new JsonString("This is a string");
    assertNotEquals(str1.hashCode(), str2.hashCode());
  }

  @Test
  public void checkHashCodeEqualWithArray() {
    JsonNode string1 = new JsonString("Hello");

    IJsonArray array = new JsonArray();
    array.add(new JsonString("Hello"));
    assertEquals(false, string1.hashCode() == array.hashCode());
  }

  @Test
  public void checkHashCodeEqualWithObject() {
    JsonNode string1 = new JsonString("Hello");

    IJsonArray array = new JsonArray();
    array.add(new JsonString("Hello"));
    IJsonObject object = new JsonObject();
    object.add("Key", array);
    assertEquals(false, string1.hashCode() == object.hashCode());
  }

  @Test
  public void stringEqulityTest() {
    JsonNode s1 = new JsonString("aForApple");
    JsonNode s2 = new JsonString("aForApple");
    JsonNode s3 = new JsonString("aForApple");

    //Equality
    assertEquals(true, s1.equals(s1));
    assertEquals(true, s2.equals(s1));
    assertEquals(true, s1.equals(s2));
    assertEquals(s1.equals(s2), s2.equals(s3));

    //HashCode
    assertEquals(s1.hashCode(), s1.hashCode());
    assertEquals(s1.hashCode(), s2.hashCode());
    assertEquals(s1.hashCode() == s2.hashCode(), s2.hashCode() == s3.hashCode());

    //PrettyPrint
    assertEquals(s1.prettyPrint(), s1.prettyPrint());
    assertEquals(s1.prettyPrint(), s2.prettyPrint());
    assertEquals(s1.prettyPrint() == s2.prettyPrint(),
            s2.prettyPrint() == s3.prettyPrint());
  }

  @Test
  public void stringNotEqulityTest() {
    JsonNode s1 = new JsonString("aForApple1");
    JsonNode s2 = new JsonString("aForApple2");
    JsonNode s3 = new JsonString("aForApple3");

    //Equality
    assertEquals(true, s1.equals(s1));
    assertEquals(false, s2.equals(s1));
    assertEquals(false, s1.equals(s2));
    assertEquals(s1.equals(s2), s2.equals(s3));

    //HashCode
    assertEquals(s1.hashCode(), s1.hashCode());
    assertNotEquals(s1.hashCode(), s2.hashCode());
    assertEquals(s1.hashCode() == s2.hashCode(), s2.hashCode() == s3.hashCode());

    //PrettyPrint
    assertEquals(s1.prettyPrint(), s1.prettyPrint());
    assertNotEquals(s1.prettyPrint(), s2.prettyPrint());
    assertEquals(s1.prettyPrint() != s2.prettyPrint(),
            s2.prettyPrint() != s3.prettyPrint());

  }


}