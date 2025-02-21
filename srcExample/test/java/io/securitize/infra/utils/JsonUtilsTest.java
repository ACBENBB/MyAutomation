package io.securitize.infra.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JsonUtilsTest {


   @Test
   public void searchJsonRecursivelyForKey_positiveScenario_valid(){
       //Arrange
       String key = "someKey";
       JsonElement jsonElement = JsonParser.parseString("{\"someKey\": \"someValue\"}");
       String expectedValue = "someValue";

       //Act
       String result = JsonUtils.searchJsonRecursivelyForKey(jsonElement, key);

       //Assert
       Assert.assertEquals(result, expectedValue);
   }

    @Test
    public void testSearchJsonRecursivelyForKey_EmptyJson_ReturnsNull() {
        String key = "someKey";
        JsonElement emptyJson = JsonParser.parseString("{}");

        String result = JsonUtils.searchJsonRecursivelyForKey(emptyJson, key);

        Assert.assertNull(result);
    }

    @Test
    public void testSearchJsonRecursivelyForKey_PrimitiveJson_ReturnsNull() {
        String key = "someKey";
        JsonElement primitiveJson = JsonParser.parseString("\"value\"");

        String result = JsonUtils.searchJsonRecursivelyForKey(primitiveJson, key);

        Assert.assertNull(result);
    }

    @Test
    public void testSearchJsonRecursivelyForKey_ArrayJson_KeyNotFound_ReturnsNull() {
        String key = "nonExistentKey";
        JsonElement jsonArray = JsonParser.parseString("[1, 2, 3]");

        String result = JsonUtils.searchJsonRecursivelyForKey(jsonArray, key);

        Assert.assertNull(result);
    }

    @Test
    public void testSearchJsonRecursivelyForKey_ArrayJson_KeyFound_ReturnsValue() {
        String key = "targetKey";
        JsonElement jsonArray = JsonParser.parseString("[{\"targetKey\": \"value\"}]");

        String result = JsonUtils.searchJsonRecursivelyForKey(jsonArray, key);

        Assert.assertEquals(result, "value");
    }

    @Test
    public void testSearchJsonRecursivelyForKey_ObjectJson_KeyNotFound_ReturnsNull() {
        String key = "nonExistentKey";
        JsonElement jsonObject = JsonParser.parseString("{\"otherKey\": \"value\"}");

        String result = JsonUtils.searchJsonRecursivelyForKey(jsonObject, key);

        Assert.assertNull(result);
    }

    @Test
    public void testSearchJsonRecursivelyForKey_ObjectJson_KeyFound_ReturnsValue() {
        String key = "targetKey";
        JsonElement jsonObject = JsonParser.parseString("{\"targetKey\": \"value\"}");

        String result = JsonUtils.searchJsonRecursivelyForKey(jsonObject, key);

        Assert.assertEquals(result, "value");
    }
}
