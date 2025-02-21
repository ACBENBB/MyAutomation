package io.securitize.infra.api.healthchecks;

import org.json.JSONObject;

/**
 * Holds response data of a specific endpoint
 */
public class RequestResponse {
    int statusCode;
    JSONObject body;
    String bodyAsString; // if the body can't be parsed to json, we still return the raw value of it

    public RequestResponse(int statusCode, JSONObject body, String bodyAsString) {
        this.statusCode = statusCode;
        this.body = body;
        this.bodyAsString = bodyAsString;
    }
}
