package io.securitize.infra.api;

import com.jayway.jsonpath.JsonPath;
import io.restassured.builder.RequestSpecBuilder;
import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;
import net.minidev.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class AtsConfigAPI extends AbstractAtsAPI {
    private static final String ENDPOINT_GET_MARKET = DataManager.getInstance("ats").getTeamProperty("endpointGetMarket");

    public AtsConfigAPI() {
        String environment = DataManager.getInstance("ats").getMainConfigProperty("environment");
        String atsConfigApiKey = DataManager.getInstance("ats").getAwsProperty(UsersProperty.apiAtsConfigApiKey.name());
        BASE_URL = DataManager.getInstance("ats").getTeamProperty("atsConfigApiBaseUrl").replace(KEYWORD_ENVIRONMENT, environment);
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("x-api-key", atsConfigApiKey)
                .build();
    }

    public String getMarkets() {
        return get(ENDPOINT_GET_MARKET);
    }

}