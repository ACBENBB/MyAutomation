package io.securitize.infra.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtils {

    public static String searchJsonRecursivelyForKey(JsonElement json, String key) {
        if (json.isJsonPrimitive()) return null;

        else if (json.isJsonNull()) return null;

        else if (json.isJsonArray()) {
            JsonArray jsonAsArray = json.getAsJsonArray();
            for (JsonElement currentElement: jsonAsArray) {
                String result = searchJsonRecursivelyForKey(currentElement, key);
                if (result != null) return result;
            }
        }

        else if (json.isJsonObject()) {
            JsonObject jsonAsObject = json.getAsJsonObject();
            for (String currentKey : jsonAsObject.keySet()) {
                if (currentKey.equalsIgnoreCase(key)) {
                    return jsonAsObject.get(currentKey).getAsString();
                } else {
                    String result = searchJsonRecursivelyForKey(jsonAsObject.get(currentKey), key);
                    if (result != null) return result;
                }
            }
        }

        return null;
    }
}
