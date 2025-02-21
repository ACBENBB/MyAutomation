package io.securitize.infra.api;

import io.restassured.builder.RequestSpecBuilder;
import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.ats.pojo.ATSOrder;
import io.securitize.tests.ats.properties.Orders;
import io.securitize.tests.ats.queries.ATSOrdersQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.securitize.infra.reporting.MultiReporter.info;

public class AtsOrderAPI extends AbstractAtsAPI {
    private static final String ENDPOINT_POST_ORDER_CANCEL = DataManager.getInstance("ats").getTeamProperty("endpointPostOrderCancel");

    public AtsOrderAPI() {
        String environment = DataManager.getInstance("ats").getMainConfigProperty("environment");
        String atsAssetApiKey = DataManager.getInstance("ats").getAwsProperty(UsersProperty.apiAtsOrderApiKey.name());
        BASE_URL = DataManager.getInstance("ats").getTeamProperty("atsOrderApiBaseUrl").replace(KEYWORD_ENVIRONMENT, environment);
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("x-api-key", atsAssetApiKey)
                .build();
    }

    private String callCancelOrderEndpoint(String orderId, Map<String, String> body) {
        return post(ENDPOINT_POST_ORDER_CANCEL.replace(KEYWORD_ORDER_ID, orderId), body, 201);
    }

    public void cancelActiveOrders(String security) {
        List<ATSOrder> orders = ATSOrdersQuery.findOrdersBySecurity(security, Orders.DB_STATUS.OPEN.name());
        info(String.format("Orders to cancel: %d", orders.size()));
        for (ATSOrder atsOrder : orders) {
            info(String.format("Cancelling order: %s", atsOrder.getOrderId()));
            Map<String, String> body = new HashMap<>();
            body.put(Orders.PROPERTIES.referenceNumber.name(), atsOrder.getReferenceNumber());
            body.put(Orders.PROPERTIES.previousStatus.name(), Orders.DB_STATUS.OPEN.name());
            body.put(Orders.PROPERTIES.orderId.name(), atsOrder.getOrderId());
            callCancelOrderEndpoint(atsOrder.getOrderId(), body);
        }
    }
}