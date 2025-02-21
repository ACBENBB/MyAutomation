package io.securitize.infra.api;

import com.jayway.jsonpath.JsonPath;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.securitize.tests.ats.pojo.ATSOrder;
import io.securitize.tests.ats.properties.Orders;
import io.securitize.tests.ats.queries.ATSOrdersQuery;
import net.minidev.json.JSONArray;
import org.testng.Assert;
import io.securitize.infra.config.DataManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.info;

public class AtsGatewayAPI extends AbstractAtsAPI {
    private static final String ENDPOINT_GW_GET_ASSETS = DataManager.getInstance("ats").getTeamProperty("endpointGwGetAssets");
    private static final String ENDPOINT_GW_GET_ASSETS_CATALOG = DataManager.getInstance("ats").getTeamProperty("endpointGwGetAssetsCatalog");
    private static final String ENDPOINT_GW_GET_ACCOUNT_ASSETS = DataManager.getInstance("ats").getTeamProperty("endpointGwGetAccountAssets");

    private static final String ENDPOINT_GW_GET_ORDERS = DataManager.getInstance("ats").getTeamProperty("endpointGwGetOrders");
    private static final String ENDPOINT_GW_POST_ORDERS = DataManager.getInstance("ats").getTeamProperty("endpointGwPostOrders");
    private static final String ENDPOINT_GW_POST_ORDERS_CANCEL = DataManager.getInstance("ats").getTeamProperty("endpointGwPostOrdersCancel");
    private static final String ENDPOINT_GW_GET_CONFIG = DataManager.getInstance("ats").getTeamProperty("endpointGwConfig");

    private final String user;
    private final String password;
    private final String investorId;
    private final String bearerToken;
    private Map<String, String> cookies = new HashMap<>();

    public AtsGatewayAPI(String user, String password, String investorId) {
        this.user = user;
        this.password = password;
        this.investorId = investorId;
        Response response = getSecuritizeIdSpec(user, password);
        this.bearerToken = JsonPath.read(response.body().asString(), "$.token");
        this.cookies.putAll(response.getCookies());
        String environment = DataManager.getInstance("ats").getMainConfigProperty("environment");
        BASE_URL = DataManager.getInstance("ats").getTeamProperty("atsGwApiBaseUrl").replace(KEYWORD_ENVIRONMENT, environment).replace(".production.", ".");
        this.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("Authorization", bearerToken)
                .addHeader("accept", "application/json")
                .addCookies(response.getCookies())
                .build();
    }

    public AtsGatewayAPI(String user, String password) {
        this(user, password, null);
    }

    public String getAssets() {
        return get(ENDPOINT_GW_GET_ASSETS);
    }

    public String getAssetsCatalog() {
        return get(ENDPOINT_GW_GET_ASSETS_CATALOG);
    }

    public List<String> getAssetsByMarketAsList(String market) {
        String jsonPathExpression = String.format("$.markets.%s.assets[*].ticker", market);
        JSONArray array = JsonPath.read(getAssetsCatalog(), jsonPathExpression);
        return array.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public String getAccountAssets() {
        return get(ENDPOINT_GW_GET_ACCOUNT_ASSETS);
    }

    private String callCreateOrderEndpoint(Map body) {
        return post(ENDPOINT_GW_POST_ORDERS, body, 201);
    }

    private String callCancelOrderEndpoint(String orderId) {
        return post(ENDPOINT_GW_POST_ORDERS_CANCEL.replace(KEYWORD_ORDER_ID, orderId), new HashMap<>(), 201);
    }

    public HashMap<String, String> createOrder(String security, String side, String amount, String price) {
        HashMap<String, Serializable> body = new HashMap<>();
        body.put(Orders.PROPERTIES.security.name(), security);
        body.put(Orders.PROPERTIES.side.name(), side);
        body.put(Orders.PROPERTIES.amount.name(), amount);
        body.put(Orders.PROPERTIES.price.name(), price);
        body.put(Orders.PROPERTIES.type.name(), "LMT");
        body.put(Orders.PROPERTIES.expirationDays.name(), 365);
        String assetsResponse = callCreateOrderEndpoint(body);
        String responseOrderId = JsonPath.read(assetsResponse, "$.orderId");
        String responseReferenceNumber = JsonPath.read(assetsResponse, "$.referenceNumber");
        String actualOrderStatus = ATSOrdersQuery.findOrderStatusByOrderIdAndReferenceNumber(responseOrderId, responseReferenceNumber);
        HashMap<String, String> result = new HashMap<>();
        result.put(Orders.PROPERTIES.orderId.name(), responseOrderId);
        result.put(Orders.PROPERTIES.referenceNumber.name(), responseReferenceNumber);
        result.put(Orders.PROPERTIES.orderStatus.name(), actualOrderStatus);
        return result;
    }

    public HashMap<String, String> cancelOrder(Map<String, String> values) {
        String orderId = values.get(Orders.PROPERTIES.orderId.name());
        String referenceNumber = values.get(Orders.PROPERTIES.referenceNumber.name());
        String previousOrderStatus = ATSOrdersQuery.findOrderStatusByOrderIdAndReferenceNumber(orderId, referenceNumber);
        String response = callCancelOrderEndpoint(orderId);
        Assert.assertEquals(JsonPath.read(response, "$.orderId"), orderId);
        Assert.assertEquals(JsonPath.read(response, "$.referenceNumber"), referenceNumber);
        String actualOrderStatus = ATSOrdersQuery.findOrderStatusByOrderIdAndReferenceNumber(orderId, referenceNumber);
        HashMap<String, String> result = new HashMap<>();
        result.put(Orders.PROPERTIES.orderId.name(), orderId);
        result.put(Orders.PROPERTIES.referenceNumber.name(), referenceNumber);
        result.put(Orders.PROPERTIES.previousOrderStatus.name(), previousOrderStatus);
        result.put(Orders.PROPERTIES.orderStatus.name(), actualOrderStatus);
        return result;
    }

    public void cancelActiveOrders() {
        List<ATSOrder> orders = ATSOrdersQuery.findOrders(this.investorId, Orders.DB_STATUS.OPEN.name());
        info(String.format("Orders to cancel: %d", orders.size()));
        for (ATSOrder atsOrder : orders) {
            info(String.format("Cancelling order: %s", atsOrder.getOrderId()));
            callCancelOrderEndpoint(atsOrder.getOrderId());
        }
    }

    public List<String> getHoldingAssets() {
        JSONArray array = JsonPath.read(getAccountAssets(), "$[*].asset.security");
        return array.stream().map(Object::toString)
                .collect(Collectors.toList());
    }

    public String getConfig() {
        return get(ENDPOINT_GW_GET_CONFIG);
    }

    public String getOrders(Map<String, String> queryParams){
        return get(ENDPOINT_GW_GET_ORDERS, queryParams);
    }

}