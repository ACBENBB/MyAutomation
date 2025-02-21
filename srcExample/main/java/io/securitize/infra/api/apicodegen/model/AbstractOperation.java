package io.securitize.infra.api.apicodegen.model;

import io.restassured.http.Headers;

import java.util.ArrayList;
import java.util.HashMap;

abstract class AbstractOperation {
    HashMap<String, Object> trimHashMapKeys(HashMap<String, Object> hashMap){
        ArrayList<String> keysToRemove = new ArrayList<>();
        if(hashMap != null) {
            for (String key : hashMap.keySet()) {
                if (!key.trim().equals(key)) {
                    hashMap.put(key.trim(), hashMap.get(key));
                    keysToRemove.add(key);
                }
            }
            for(String key : keysToRemove){
                hashMap.remove(key);
            }
        }
        return hashMap;
    }


    protected HashMap<String, Headers> trimHashMapKeysHeaders(HashMap<String, Headers> headers) {

        ArrayList<String> keysToRemove = new ArrayList<>();
        if(headers != null) {
            for (String key : headers.keySet()) {
                if (!key.trim().equals(key)) {
                    headers.put(key.trim(), headers.get(key));
                    keysToRemove.add(key);
                }
            }
            for(String key : keysToRemove){
                headers.remove(key);
            }
        }
        return headers;

    }

}