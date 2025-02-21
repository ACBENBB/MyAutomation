package io.securitize.infra.api;

import com.jayway.jsonpath.JsonPath;
import io.restassured.builder.RequestSpecBuilder;
import io.securitize.infra.config.*;
import io.securitize.tests.ats.pojo.ATSOrder;
import io.securitize.tests.ats.properties.Orders;
import io.securitize.tests.ats.queries.ATSOrdersQuery;
import net.minidev.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.infra.reporting.MultiReporter.warning;

public class AtsAssetAPI extends AbstractAtsAPI {
    private static final String ENDPOINT_GET_ASSETS = DataManager.getInstance("ats").getTeamProperty("endpointGetAssets");
    private static final String ENDPOINT_PATCH_ASSETS = DataManager.getInstance("ats").getTeamProperty("endpointPatchAssets");

    public AtsAssetAPI() {
        String environment = DataManager.getInstance("ats").getMainConfigProperty("environment");
        String atsAssetApiKey = DataManager.getInstance("ats").getAwsProperty(UsersProperty.apiAtsAssetApiKey.name());
        BASE_URL = DataManager.getInstance("ats").getTeamProperty("atsAssetsApiBaseUrl").replace(KEYWORD_ENVIRONMENT, environment);
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("x-api-key", atsAssetApiKey)
                .build();
    }

    public String getAssets() {
        return get(ENDPOINT_GET_ASSETS);
    }

    public String getAssetFee(String security, String side) {
        String response =  get(ENDPOINT_GET_ASSETS);
        String jsonPathExpression = String.format("$[?(@.security == '%s')].feePercentage.%s", security, side);
        JSONArray array = JsonPath.read(response, jsonPathExpression);
        return String.valueOf(array.get(0));
    }

    public String setExchangeType(String security, String exchangeType) {
        Map body = new HashMap();
        body.put("exchangeType", exchangeType);
        String endpoint = ENDPOINT_PATCH_ASSETS.replace(KEYWORD_SECURITY, security);
        return patch(endpoint, body);
    }

    public String setTradingType(String security, String tradingType) {
        Map body = new HashMap();
        body.put("tradingType", tradingType);
        String endpoint = ENDPOINT_PATCH_ASSETS.replace(KEYWORD_SECURITY, security);
        return patch(endpoint, body);
    }

    public String setTradingType(String security, Map body) {
        String endpoint = ENDPOINT_PATCH_ASSETS.replace(KEYWORD_SECURITY, security);
        return patch(endpoint, body);
    }

}