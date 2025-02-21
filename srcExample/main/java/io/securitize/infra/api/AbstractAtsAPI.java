package io.securitize.infra.api;

import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class AbstractAtsAPI extends AbstractAPI {
    protected static final String KEYWORD_ENVIRONMENT = "{environment}";
    protected static final String KEYWORD_ORDER_ID = "{orderId}";
    protected static final String KEYWORD_SECURITY = "{security}";
    protected static final String KEYWORD_INVESTOR_ID = "{investorId}";
    protected static String BASE_URL;
    protected RequestSpecification requestSpecification;

    public String get(String endpoint) {
        return getAPI(requestSpecification, endpoint);
    }

    public String get(String endpoint, Map<String, String> queryParams) {
        return getAPI(requestSpecification, endpoint, queryParams);
    }

    public String post(String endpoint, Map body) {
        return postAPI(requestSpecification, endpoint, body);
    }

    public String post(String endpoint, Map body, int statusCode) {
        return postAPI(requestSpecification, endpoint, body, statusCode);
    }

    public String patch(String endpoint, Map body) {
        return patchAPI(requestSpecification, endpoint, body);
    }

}