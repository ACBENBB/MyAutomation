package io.securitize.infra.api.apicodegen.model;

import java.util.HashMap;

@SuppressWarnings("unused")
public class OperationDetails extends AbstractOperation {

    private HashMap<String, Object> pathParams;
    private HashMap<String, Object> queryParams;
    private HashMap<String, Object> headers;
    private HashMap<String, Object> body;
    private OperationExpected expectedResponse;
    private OperationSkips skips;

    public HashMap<String, Object> getPathParams() {
        return trimHashMapKeys(pathParams);
    }

    public void setPathParams(HashMap<String, Object> pathParams) {
        this.pathParams = pathParams;
    }

    public HashMap<String, Object> getQueryParams() {
        return trimHashMapKeys(queryParams);
    }

    public HashMap<String, Object> getHeaders() {
        return trimHashMapKeys(headers);
    }

    public HashMap<String, Object> getBody() {
        return trimHashMapKeys(body);
    }

    public OperationExpected getExpectedResponse() {
        if (expectedResponse == null) return new OperationExpected();
        return expectedResponse;
    }

    public OperationSkips getSkips() {
        if (skips == null) return new OperationSkips();
        return skips;
    }
}