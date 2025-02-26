package jsontree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test Class for the class JsonArray.
 * Tests array creation , pretty print , array equals and has code functionality.
 * Test cases also include cases to test constructors.
 * Uses setup() to initialise the required objects initially.
 */
public class JsonArrayTest {

  private JsonString stringValue;
  private IJsonArray arrayWith3Values;
  private IJsonArray arrayWith4Values;
  private IJsonObject object;

  @Before
  public void setup() {
    stringValue = new JsonString("String");

    arrayWith3Values = new JsonArray();
    arrayWith3Values.add(new JsonString("1"));
    arrayWith3Values.add(new JsonString("Hello"));
    arrayWith3Values.add(new JsonString("{}[]"));

    arrayWith4Values = new JsonArray();
    arrayWith4Values.add(new JsonString("1"));
    arrayWith4Values.add(new JsonString("Hello"));
    arrayWith4Values.add(new JsonString("{}[]"));
    arrayWith4Values.add(new JsonString("{}[]()"));

    object = new JsonObject();
    object.add("KEY", stringValue);
    object.add("KEY", arrayWith3Values);

  }

  @Test
  public void testArrayConstructor1() {
    IJsonArray array = new JsonArray();
    assertEquals("[\n]", array.prettyPrint());
  }

  @Test
  public void testArrayConstructor2() {
    IJsonArray array = new JsonArray();
    array.add(new JsonString("value"));
    assertEquals("[\n  \"value\"\n]", array.prettyPrint());
  }

  @Test
  public void checkEmptyArray() {
    IJsonArray array1 = new JsonArray();
    IJsonArray array2 = new JsonArray();

    assertEquals(true, array1.equals(array2));
    assertEquals(array1.hashCode(), array2.hashCode());
    assertEquals("[\n]", array1.prettyPrint());
    assertEquals(array1.prettyPrint(), array2.prettyPrint());
  }

  @Test
  public void testArrayWithValues() {
    IJsonArray nestedArray = new JsonArray();
    nestedArray.add(new JsonString("V1"));

    JsonArray array = new JsonArray();
    array.add(new JsonString("value"));
    array.add(new JsonString("1"));
    array.add(nestedArray);

    String jsonString = "[\n"
            + "  \"value\",\n"
            + "  \"1\",\n"
            + "  [\n"
            + "    \"V1\"\n"
            + "  ]\n"
            + "]";

    assertEquals(jsonString, array.prettyPrint());
  }

  @Test
  public void testSameArray() {
    assertEquals(true, arrayWith3Values.equals(arrayWith3Values));
    assertEquals(arrayWith3Values.hashCode(), arrayWith3Values.hashCode());
  }

  @Test
  public void testDifferentArray() {

    //Equals method
    assertEquals(false, arrayWith3Values.equals(arrayWith4Values));
    assertEquals(false, arrayWith4Values.equals(arrayWith3Values));

    //pretty method
    String pp1 = arrayWith3Values.prettyPrint();
    String pp2 = arrayWith4Values.prettyPrint();

    assertEquals(false, pp1 == pp2);
  }

  @Test
  public void testNotEqualsHashCodeAllCombination() {
    //check string and array hashcode
    assertNotEquals(stringValue.hashCode(), arrayWith3Values.hashCode());
    // check array and array hashcode
    assertNotEquals(arrayWith3Values.hashCode(), arrayWith4Values.hashCode());
    // check array and object hashcode
    assertNotEquals(arrayWith3Values.hashCode(), object.hashCode());

  }

  @Test
  public void testNotEqualsAllCombination() {

    //check string and array
    assertEquals(false, stringValue.equals(arrayWith3Values));
    assertEquals(false, arrayWith3Values.equals(stringValue));

    // check array and array
    assertEquals(false, arrayWith4Values.equals(arrayWith3Values));
    assertEquals(false, arrayWith3Values.equals(arrayWith4Values));

    // check array and object
    assertEquals(false, arrayWith3Values.equals(object));
    assertEquals(false, arrayWith3Values.equals(object));
  }

  @Test
  public void arrayWithSameValuesDifferentOrder() {
    IJsonArray arrayWith3Values2;
    arrayWith3Values2 = new JsonArray();
    arrayWith3Values2.add(new JsonString("1"));
    arrayWith3Values2.add(new JsonString("{}[]"));
    arrayWith3Values2.add(new JsonString("Hello"));
    assertEquals(false, arrayWith3Values.equals(arrayWith3Values2));
  }

  @Test
  public void arrayWithSameValuesSameOrder() {
    IJsonArray arrayWith3Values2;
    arrayWith3Values2 = new JsonArray();
    arrayWith3Values2.add(new JsonString("1"));
    arrayWith3Values2.add(new JsonString("Hello"));
    arrayWith3Values2.add(new JsonString("{}[]"));
    assertEquals(true, arrayWith3Values.equals(arrayWith3Values2));
  }

  @Test
  public void arrayEuqalityTesting2() {
    IJsonArray a1 = new JsonArray();
    IJsonArray a2 = new JsonArray();
    IJsonArray a3 = new JsonArray();

    a1.add(new JsonString("Hello"));
    a2.add(new JsonString("Hello"));
    a3.add(new JsonString("Hello"));

    // Array Equality
    assertEquals(true, a1.equals(a1));
    assertEquals(true, a2.equals(a1));
    assertEquals(true, a1.equals(a2));
    assertEquals(a1.equals(a2), a2.equals(a3));

    // Array Hashcode
    assertEquals(69609651, a1.hashCode());
    assertEquals(a1.hashCode(), a1.hashCode());
    assertEquals(a1.hashCode(), a2.hashCode());
    assertEquals(a1.hashCode() == a2.hashCode(),
            a2.hashCode() == a3.hashCode());

    // String Equals
    assertEquals(a1.prettyPrint(), a1.prettyPrint());
    assertEquals(a1.prettyPrint(), a2.prettyPrint());
    assertEquals(a1.prettyPrint() == a2.prettyPrint(),
            a1.prettyPrint() == a2.prettyPrint());

  }

  @Test
  public void nestedarray() {
    IJsonArray level1 = new JsonArray();
    IJsonArray level2 = new JsonArray();
    IJsonArray level3 = new JsonArray();

    level3.add(new JsonString("value"));
    level2.add(level3);
    level1.add(level2);

    String jsonString = "[\n" +
            "  [\n" +
            "    [\n" +
            "      \"value\"\n" +
            "    ]\n" +
            "  ]\n" +
            "]";

    assertEquals(jsonString, level1.prettyPrint());
  }

  @Test
  public void arrayNotEuqalityTesting2() {
    IJsonArray a1 = new JsonArray();
    IJsonArray a2 = new JsonArray();
    IJsonArray a3 = new JsonArray();

    a1.add(new JsonString("Hello1"));
    a2.add(new JsonString("Hello2"));
    a3.add(new JsonString("Hello3"));

    // Array Equality
    assertEquals(true, a1.equals(a1));
    assertEquals(false, a2.equals(a1));
    assertEquals(false, a1.equals(a2));
    assertEquals(a1.equals(a2), a2.equals(a3));

    // Array Hashcode
    assertEquals(a1.hashCode(), a1.hashCode());
    assertNotEquals(a1.hashCode(), a2.hashCode());
    assertEquals(a1.hashCode() == a2.hashCode(),
            a2.hashCode() == a3.hashCode());

    // String Equals
    assertEquals(a1.prettyPrint(), a1.prettyPrint());
    assertNotEquals(a1.prettyPrint(), a2.prettyPrint());
    assertEquals(a1.prettyPrint() != a2.prettyPrint(),
            a1.prettyPrint() != a2.prettyPrint());

  }
}