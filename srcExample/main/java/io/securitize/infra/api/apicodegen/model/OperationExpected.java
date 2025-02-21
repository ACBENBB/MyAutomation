package io.securitize.infra.api.apicodegen.model;

import java.util.HashMap;

@SuppressWarnings("unused")
public class OperationExpected extends AbstractOperation{
    private int statusCode;
    private HashMap<String, Object> body;

    public int getStatusCode() {
        return statusCode;
    }

    public HashMap<String, Object> getBody() {
        if (body == null) return new HashMap<>();
        return trimHashMapKeys(body);
    }
}
