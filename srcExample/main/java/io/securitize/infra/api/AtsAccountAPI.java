package io.securitize.infra.api;

import io.restassured.builder.RequestSpecBuilder;
import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;

import java.util.HashMap;
import java.util.Map;

public class AtsAccountAPI extends AbstractAtsAPI {
    private static final String ENDPOINT_POST_BALANCE_SYNC = DataManager.getInstance("ats").getTeamProperty("endpointPostBalanceSync");

    public AtsAccountAPI() {
        String environment = DataManager.getInstance("ats").getMainConfigProperty("environment");
        String atsAssetApiKey = DataManager.getInstance("ats").getAwsProperty(UsersProperty.apiAtsAccountApiKey.name());
        BASE_URL = DataManager.getInstance("ats").getTeamProperty("atsAccountApiBaseUrl").replace(KEYWORD_ENVIRONMENT, environment);
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("x-api-key", atsAssetApiKey)
                .build();
    }

    public String syncInvestorBalance(String investorId) {
        Map body = new HashMap();
        body.put("onlyTokens", false);
        body.put("includeInvestorInfo", false);
        String endpoint = ENDPOINT_POST_BALANCE_SYNC.replace(KEYWORD_INVESTOR_ID,investorId);
        return post(endpoint, body, 201);
    }

}