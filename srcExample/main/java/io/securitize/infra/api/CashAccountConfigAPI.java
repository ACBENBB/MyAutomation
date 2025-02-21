package io.securitize.infra.api;

import com.jayway.jsonpath.JsonPath;
import io.restassured.builder.RequestSpecBuilder;
import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;
import net.minidev.json.JSONArray;

import java.util.*;
import java.util.stream.Collectors;

public class CashAccountConfigAPI extends AbstractAtsAPI {
    private static final String ENDPOINT_INFO = DataManager.getInstance("ca").getTeamProperty("endpointInfo");

    public CashAccountConfigAPI() {
        String environment = DataManager.getInstance("ca").getMainConfigProperty("environment");
        String caConfigApiKey = DataManager.getInstance("ca").getAwsProperty(UsersProperty.ca_config_xApiKey.name());
        BASE_URL = DataManager.getInstance("ca").getTeamProperty("cashAccountConfigApiBaseUrl").replace(KEYWORD_ENVIRONMENT, environment);
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("x-api-key", caConfigApiKey)
                .build();
    }

    public String getInfo() {
        return get(ENDPOINT_INFO);
    }

    public String getInfo(String investorId) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("investorId", investorId);
        return get(ENDPOINT_INFO, queryParams);
    }

    public List<String> getInvestorAvailableDepositOptions(String investorId) {

        // Mapping feature flags to ui deposit options
        Map<String, String> flagMapping = new HashMap<>();
        flagMapping.put("ACH_ENABLED", "Bank Account - ACH");
        flagMapping.put("DEPOSIT_INTERNATIONAL", "United States Wire Transfer");
        flagMapping.put("DEPOSITS_ENABLED", "International Wire Transfer");
        flagMapping.put("CREDIT_CARD_FUNDING", "Credit/Debit Card");

        JSONArray array = JsonPath.read(this.getInfo(investorId), "featureFlags");

        return Arrays.stream(array.toArray())
                .map(Object::toString)
                .filter(flagMapping::containsKey)
                .map(flagMapping::get)
                .collect(Collectors.toList());

    }

}