package jsontree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test Class for the class JsonObject.
 * Tests Object creation , pretty print , objects equals and hashcode functionality.
 * Test cases also tests if object key is valid or not.
 * Test cases also include cases to test constructors.
 * Uses setup() to initialise the required objects initially.
 */
public class JsonObjectTest {

  private JsonString stringValue;
  private IJsonArray arrayWith3Values;
  private IJsonObject object;
  private IJsonObject complexObj1;
  private IJsonObject complexObj2;
  private IJsonArray arrayOfaraaysAndObjects;

  @Before
  public void setup() {

    stringValue = new JsonString("String");

    arrayWith3Values = new JsonArray();
    arrayWith3Values.add(new JsonString("1"));
    arrayWith3Values.add(new JsonString("Hello"));
    arrayWith3Values.add(new JsonString("{}[]"));

    object = new JsonObject();
    object.add("KEY", stringValue);
    object.add("KEY", arrayWith3Values);

    arrayOfaraaysAndObjects = new JsonArray();
    IJsonArray arr1 = new JsonArray();
    arr1.add(object);
    arrayOfaraaysAndObjects.add(arr1);


    complexObj1 = new JsonObject();
    complexObj2 = new JsonObject();

    complexObj1.add("value", arrayOfaraaysAndObjects);
    complexObj2.add("value", arrayOfaraaysAndObjects);
  }

  @Test
  public void testConstructor() {
    JsonObject obj = new JsonObject();
    assertEquals("{\n}", obj.prettyPrint());
  }

  @Test
  public void emptyArrayObject() {
    JsonObject obj = new JsonObject();
    JsonArray arr = new JsonArray();
    obj.add("K", arr);
    String jsonString = "{\n"
            + "  \"K\":\n"
            + "  [\n"
            + "  ]\n"
            + "}";

    assertEquals(jsonString, obj.prettyPrint());
  }

  @Test
  public void simpleObjectCreateTest() {
    IJsonObject obj1 = new JsonObject();
    obj1.add("zfor", new JsonString("zebra"));
    String jsonString = "{\n"
            + "  \"zfor\":\"zebra\"\n"
            + "}";

    assertEquals(jsonString, obj1.prettyPrint());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidObjectCreate1() {
    IJsonObject obj1 = new JsonObject();
    obj1.add("1", new JsonString("zebra"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void invalidObjectCreate2() {
    IJsonObject obj1 = new JsonObject();
    obj1.add("A(", new JsonString("zebra"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidObjectCreate3() {
    IJsonObject obj1 = new JsonObject();
    obj1.add("()", new JsonString("zebra"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidObjectCreate4() {
    IJsonObject obj1 = new JsonObject();
    obj1.add("", new JsonString("zebra"));
  }

  @Test
  public void print() {
    String jsonString = "{\n"
            + "  \"value\":\n"
            + "  [\n"
            + "    [\n"
            + "      {\n"
            + "        \"KEY\":\"String\",\n"
            + "        \"KEY\":\n"
            + "        [\n"
            + "          \"1\",\n"
            + "          \"Hello\",\n"
            + "          \"{}[]\"\n"
            + "        ]\n"
            + "      }\n"
            + "    ]\n"
            + "  ]\n"
            + "}";

    String expected = complexObj2.prettyPrint();
    assertEquals(jsonString, expected);
  }

  @Test
  public void complexObjectPrint() {
    assertEquals(true, complexObj2.equals(complexObj1));
  }

  @Test
  public void addDuplicateKeyValuePair() {

    IJsonObject o1 = new JsonObject();
    //same key different values
    o1.add("key", new JsonString("value"));
    o1.add("key", new JsonString("value2"));
    //different key same values
    o1.add("key2", new JsonString("valueTest"));
    o1.add("key2", new JsonString("valueTest"));

    IJsonObject o2 = new JsonObject();
    //same key different values
    o2.add("key", new JsonString("value"));
    o2.add("key", new JsonString("value2"));
    //different key same values
    o2.add("key2", new JsonString("valueTest"));
    o2.add("key2", new JsonString("valueTest"));


    IJsonObject o3 = new JsonObject();
    //same key different values
    o3.add("key", new JsonString("value"));
    o3.add("key", new JsonString("value2"));
    //different key same values
    o3.add("key2", new JsonString("valueTest"));
    o3.add("key2", new JsonString("valueTest"));

    // Check Equality
    assertEquals(true, o1.equals(o1));
    assertEquals(true, o1.equals(o2));
    assertEquals(true, o2.equals(o1));
    assertEquals(o2.equals(o1), o2.equals(o3));

    // Check HashCode
    assertEquals(2058492349, o1.hashCode());
    assertEquals(o1.hashCode(), o1.hashCode());
    assertEquals(o1.hashCode(), o2.hashCode());
    assertEquals(o1.hashCode() == o2.hashCode(),
            o2.hashCode() == o3.hashCode());

    // Check Pretty Print
    assertEquals(o1.prettyPrint(), o1.prettyPrint());
    assertEquals(o1.prettyPrint(), o2.prettyPrint());
    assertEquals(o1.prettyPrint() == o2.prettyPrint(),
            o2.prettyPrint() == o3.prettyPrint());

  }

  @Test
  public void checkNotEqual() {

    assertEquals(false, stringValue.equals(complexObj1));
    assertEquals(false, arrayWith3Values.equals(complexObj1));


    assertEquals(false, complexObj1.equals(stringValue));
    assertEquals(false, complexObj1.equals(arrayWith3Values));

    JsonObject obj = new JsonObject();
    obj.add("key", new JsonString("value"));
    assertEquals(false, obj.equals(complexObj1));
    assertEquals(false, complexObj1.equals(obj));

  }

  @Test
  public void notEqualCheck() {

    IJsonObject o1 = new JsonObject();
    //same key different values
    o1.add("key", new JsonString("value1"));
    o1.add("key", new JsonString("value2"));

    IJsonObject o2 = new JsonObject();
    //same key different values
    o2.add("key", new JsonString("value3"));
    o2.add("key", new JsonString("value4"));


    IJsonObject o3 = new JsonObject();
    //same key different values
    o3.add("key", new JsonString("value"));
    o3.add("key", new JsonString("value2"));

    // Check Equality
    assertEquals(true, o1.equals(o1));
    assertEquals(false, o1.equals(o2));
    assertEquals(false, o2.equals(o1));
    assertEquals(o2.equals(o1), o2.equals(o3));

    // Check HashCode
    assertEquals(o1.hashCode(), o1.hashCode());
    assertNotEquals(o1.hashCode(), o2.hashCode());
    assertEquals(o1.hashCode() != o2.hashCode(),
            o2.hashCode() != o3.hashCode());

    // Check Pretty Print
    assertEquals(o1.prettyPrint(), o1.prettyPrint());
    assertNotEquals(o1.prettyPrint(), o2.prettyPrint());
    assertEquals(o1.prettyPrint() != o2.prettyPrint(),
            o2.prettyPrint() != o3.prettyPrint());

  }

  @Test
  public void sameValuesDifferentOrder() {

    IJsonObject o1 = new JsonObject();
    o1.add("key1", new JsonString("value1"));
    o1.add("key2", new JsonString("value2"));

    IJsonObject o2 = new JsonObject();
    o2.add("key2", new JsonString("value2"));
    o2.add("key1", new JsonString("value1"));

    assertEquals(true, o1.equals(o2));

  }

  @Test
  public void missingSomeKeyValuePair() {

    IJsonObject o1 = new JsonObject();
    o1.add("key1", new JsonString("value1"));
    o1.add("key2", new JsonString("value2"));
    o1.add("key3", new JsonString("value3"));

    IJsonObject o2 = new JsonObject();
    o2.add("key1", new JsonString("value1"));
    o2.add("key2", new JsonString("value2"));

    assertEquals(false, o1.equals(o2));
    assertEquals(false, o2.equals(o1));

  }

  @Test
  public void objectWithDifferentArrayOrder() {
    IJsonObject o1 = new JsonObject();
    o1.add("key1", new JsonString("value1"));
    o1.add("key2", new JsonString("value2"));
    o1.add("Array", arrayWith3Values);

    IJsonObject o2 = new JsonObject();
    o2.add("key1", new JsonString("value1"));
    o2.add("key2", new JsonString("value2"));

    JsonArray arrayWith3Values2 = new JsonArray();
    arrayWith3Values2 = new JsonArray();
    arrayWith3Values2.add(new JsonString("Hello"));
    arrayWith3Values2.add(new JsonString("1"));
    arrayWith3Values2.add(new JsonString("{}[]"));

    o2.add("Array", arrayWith3Values2);

    assertEquals(false, o1.equals(o2));
    assertEquals(false, o2.equals(o1));
  }

  @Test
  public void objectCompareWithDifferentNesting() {
    assertEquals(false, object.equals(complexObj1));
    assertEquals(false, complexObj1.equals(object));

    assertEquals(false, arrayOfaraaysAndObjects.equals(complexObj1));
    assertEquals(false, complexObj1.equals(arrayOfaraaysAndObjects));
  }

}