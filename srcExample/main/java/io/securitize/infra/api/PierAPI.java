package io.securitize.infra.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.config.*;
import io.securitize.infra.utils.RandomGenerator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.*;
import java.util.function.Function;

public class PierAPI extends AbstractAPI {

    private static String BASE_URL;

    private static final String ENDPOINT_GET_HEALTH = DataManager.getInstance("bc").getTeamProperty("getHealth");
    private static final String ENDPOINT_GET_BALANCE = DataManager.getInstance("bc").getTeamProperty("getBalance");
    private static final String ENDPOINT_GET_ORDER_STATUS = DataManager.getInstance("bc").getTeamProperty("getOrderStatus");
    private static final String ENDPOINT_GET_DEPLOYMENT_ASSETS = DataManager.getInstance("bc").getTeamProperty("getDeploymentAssets");
    private static final String ENDPOINT_GET_DEPLOYMENT_TBE_ADDRESS = DataManager.getInstance("bc").getTeamProperty("getDeploymentTbeAddress");
    private static final String ENDPOINT_GET_USER_CAN_BUY = DataManager.getInstance("bc").getTeamProperty("getUsersCanBuy");
    private static final String ENDPOINT_POST_ORDER = DataManager.getInstance("bc").getTeamProperty("postOrder");
    private static final String ENDPOINT_POST_ORDER_CANCEL = DataManager.getInstance("bc").getTeamProperty("postCancelOrders");
    private static final String ENDPOINT_POST_MATCH = DataManager.getInstance("bc").getTeamProperty("postMatches");

    private static final String KEYWORD_ENVIRONMENT = "{environment}";
    private static final String KEYWORD_SEC_ID = "{securitizeId}";
    private static final String KEYWORD_ORDER_ID = "{orderId}";
    private static final String KEYWORD_DEPLOYMENT_ID = "{deploymentId}";

    private final RequestSpecification requestSpecification;
    
    public PierAPI() {
        String environment = DataManager.getInstance("bc").getMainConfigProperty("environment");
        BASE_URL = DataManager.getInstance("bc").getTeamProperty("pierApiBaseUrl").replace(KEYWORD_ENVIRONMENT, environment);
        String pierApiKey = DataManager.getInstance("bc").getAwsProperty(UsersProperty.pierXApiKey.name());
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("x-api-key", pierApiKey)
                .addHeader("Content-Type", String.valueOf(ContentType.JSON))
                .addHeader("Accept", String.valueOf(ContentType.JSON))
                .build();
    }

    public void assertHealthStatus(){
        String  resultAsString = getAPI(requestSpecification, BASE_URL + ENDPOINT_GET_HEALTH , null, null, 200,
                false, true);
        JSONObject resultAsJSONObject = new JSONObject(resultAsString);

        JSONObject abstractionLayerStatusObject = resultAsJSONObject.getJSONObject("abstractionLayer");
        JSONObject investorApiStatusObject = resultAsJSONObject.getJSONObject("investorApi");
        JSONObject fundraiseApiStatusObject = resultAsJSONObject.getJSONObject("fundraiseApi");
        JSONObject investorPermissionApiStatusObject = resultAsJSONObject.getJSONObject("investorPermissionApi");

        String abstractionLayerStatus = abstractionLayerStatusObject.getString("status");
        String investorApiStatus = investorApiStatusObject.getString("status");
        String fundraiseApiStatus = fundraiseApiStatusObject.getString("status");
        String investorPermissionApiStatus = investorPermissionApiStatusObject.getString("status");

        String upStatus = "up";
        Assert.assertEquals(abstractionLayerStatus, upStatus, "abstractionLayerStatus is not up");
        Assert.assertEquals(investorApiStatus, upStatus, "investorApiStatus is not up");
        Assert.assertEquals(fundraiseApiStatus, upStatus, "fundraiseApiStatus is not up");
        Assert.assertEquals(investorPermissionApiStatus, upStatus, "investorPermissionApiStatus is not up");
    }

    public JSONArray getInvestorBalances(String secId){
        String resultAsString =  getAPI(requestSpecification,PierAPI.BASE_URL + ENDPOINT_GET_BALANCE.replace(KEYWORD_SEC_ID, secId), null, null,
                200, true, true);
        return new JSONArray(resultAsString);
    }

    public JSONObject getInvestorBalanceForDeploymentID(String secId, String deploymentId){
        JSONArray balancesArray = getInvestorBalances(secId);
        for (int i = 0; i < balancesArray.length(); i++) {
            JSONObject explrObject = balancesArray.getJSONObject(i);
            if (explrObject.getString("deploymentId").equals(deploymentId)){
                return explrObject;
            }
        }
        return null;
    }

    public int getAvailableTokens(JSONObject balanceResponse) {
        return Integer.parseInt(balanceResponse.getString("available"));
    }

    public int getTotalTokens(JSONObject balanceResponse) {
        return Integer.parseInt(balanceResponse.getString("total"));
    }

    protected String  postOrder(String secId, String amount, String orderType, String deploymentId) {
        String body = new JSONObject()
                .put("deploymentId", deploymentId)
                .put("type", orderType)
                .put("amount", amount).toString();

        String  resultAsString = postAPI(requestSpecification, PierAPI.BASE_URL +ENDPOINT_POST_ORDER.replace(KEYWORD_SEC_ID, secId), body, null,
                201,false, false, true);
        JSONObject resultAsJSONObject = new JSONObject(resultAsString);
        return resultAsJSONObject.getString("orderId");
    }

    public String  postBuyOrder(String buyerId, String amount, String deploymentId){
        String orderType = "buyOrder";
        return postOrder(buyerId, amount, orderType, deploymentId);
    }

    public String  postSellOrder(String sellerId, String amount, String deploymentId){
        String orderType = "sellOrder";
        return postOrder(sellerId, amount, orderType, deploymentId);
    }

    public String  postMatchOrders(String sellOrderId, String buyOrderId, String amount){
        String matchId = "automationMatch" + RandomGenerator.randInt(100, 100000);
        String body = new JSONObject()
                .put("sellOrderId", sellOrderId)
                .put("buyOrderId", buyOrderId)
                .put("amount", amount)
                .put("matchId", matchId).toString();

        return postAPI(requestSpecification, BASE_URL + ENDPOINT_POST_MATCH, body, null, 201,
                false, false, true);
    }

    public String getOrderStatus(String orderId, String secId){
        String  resultAsString = getAPI(requestSpecification, BASE_URL +
                        ENDPOINT_GET_ORDER_STATUS.replace(KEYWORD_ORDER_ID, orderId).replace(KEYWORD_SEC_ID, secId),
                null, null, 200, false, true);
        JSONObject resultAsJSONObject = new JSONObject(resultAsString);
        return resultAsJSONObject.getString("orderStatus");
    }

    public String waitOrderStatus(String orderId, String secId, String orderStatusToWait, int timeoutSeconds) {
        Function<String, String> internalWaitOrderStatus = t -> {
            String orderStatus = getOrderStatus(orderId, secId);
            if (orderStatus.equalsIgnoreCase(orderStatusToWait)) {
                return orderStatus;
            } else {
                return null;
            }
        };

        String description = "Wait for Order status to be " + orderStatusToWait;
        return Browser.waitForExpressionNotNull(internalWaitOrderStatus, null, description, timeoutSeconds, 1000);
    }

    // TODO all methods above are reviewed.

    private JSONObject getDeploymentAssets(String deploymentId){
        String  resultAsString = getAPI(requestSpecification, BASE_URL + ENDPOINT_GET_DEPLOYMENT_ASSETS + deploymentId,
                null, null, 200, false, true);
        return new JSONObject(resultAsString);
    }

    public String postCancelOrder(String orderId, String secId, String reason){
        String body = new JSONObject().put("reason", reason).toString();
        return postAPI(requestSpecification,BASE_URL +
                        ENDPOINT_POST_ORDER_CANCEL.replace(KEYWORD_SEC_ID, secId).replace(KEYWORD_ORDER_ID, orderId),
                body,null, 201, false, false, true);
    }

    public boolean getCanBuy(String buyerId, String deploymentId){
        return getCanBuy(buyerId, 200, deploymentId);
    }

    public boolean getCanBuy(String buyerSecId, int expectedResponseCode, String deploymentId){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("deploymentId", deploymentId);

        String resultAsString =  getAPI(requestSpecification, BASE_URL + ENDPOINT_GET_USER_CAN_BUY.replace(KEYWORD_SEC_ID, buyerSecId), queryParams, null,
                expectedResponseCode, false, true);
        JSONObject resultAsJSONObject = new JSONObject(resultAsString);
        return resultAsJSONObject.getBoolean("isAuthorized");
    }

    public JSONObject waitUntilBalanceNotEmpty(String secId, String deploymentId, int timeoutSeconds) {
        Function<String, JSONObject> internalWaitUntilBalanceNotEmpty = t -> getInvestorBalanceForDeploymentID(secId, deploymentId);

        String description = "Wait until balance api result is not empty";
        return Browser.waitForExpressionNotNull(internalWaitUntilBalanceNotEmpty, null, description, timeoutSeconds, 1000);
    }

    public JSONObject waitForNewBalanceAfterMatch(String secId, String deploymentId, int availableValueToWait, int totalValueToWait, int timeoutSeconds) {
        Function<String, JSONObject> internalWaitForNewBalanceAfterMatch = t -> {
            JSONObject rsp = getInvestorBalanceForDeploymentID(secId, deploymentId);
            int available = Integer.parseInt(rsp.getString("available"));
            int total = Integer.parseInt(rsp.getString("total"));
            if ((available == availableValueToWait) && (total == totalValueToWait)) {
                return rsp;
            } else {
                return null;
            }
        };

        String description = "Wait for new balance after match";
        return Browser.waitForExpressionNotNull(internalWaitForNewBalanceAfterMatch, null, description, timeoutSeconds, 1000);
    }

    // TODO review this method relevance
//    public JSONObject getDeploymentCounters(String deploymentId){
//        HashMap<String, Object> modHeaders = (HashMap<String, Object>) PierAPI.Headers.clone();
////        modHeaders.put("x-api-key", PierAPI.AdminSecret);
//        String  resultAsString = getAPI(null, BASE_URL + "v1/deployments/" + deploymentId + "/counters",
//                null, modHeaders, 200, false, true);
//        return new JSONObject(resultAsString);
//
//    }

    public String getDeploymentTBEAddress(String deploymentId){
        String  resultAsString = getAPI(requestSpecification, BASE_URL + ENDPOINT_GET_DEPLOYMENT_TBE_ADDRESS.replace(KEYWORD_DEPLOYMENT_ID, deploymentId),
                null, null, 200, false, true);
        JSONObject resultAsJSONObject = new JSONObject(resultAsString);
        return resultAsJSONObject.getString("omnibusTBEAddress");
    }

    public JSONArray getDeploymentRules(String deploymentId){
        // Response format
        // [
        //        {
        //            "name": "US_FORCE_ACCREDITED"
        //        },
        //    ]
        JSONObject res = getDeploymentAssets(deploymentId);
        return res.getJSONArray("rules");
    }

    public JSONArray getDeploymentForbiddenStates(String deploymentId){
        // Response format
        // [
        //        "SC",
        //        "AZ"
        //    ]
        JSONObject res = getDeploymentAssets(deploymentId);
        return res.getJSONArray("usForbiddenStates");
    }
}