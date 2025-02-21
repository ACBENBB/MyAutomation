package io.securitize.tests.apiTests.cicd.ATS.ATS_AtsOrderApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ATS_AtsOrderApi extends BaseApiTest {

    @Test()
    public void OrdersController_getOrderByIdTest459() {
        testRequest(Method.GET, "https://ats-order-api.internal.{environment}.securitize.io/v1/orders/{orderId}", "OrdersController_getOrderById", LoginAs.NONE, "ATS/ats-order-api", "{\"description\":\"Get order by id\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetOrderDto\"}}}}");
    }


    @Test()
    public void HealthController_checkTest992() {
        testRequest(Method.GET, "https://ats-order-api.internal.{environment}.securitize.io/api/health", "HealthController_check", LoginAs.NONE, "ATS/ats-order-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void BooksController_getBooksTest922() {
        testRequest(Method.GET, "https://ats-order-api.internal.{environment}.securitize.io/v2/books", "BooksController_getBooks", LoginAs.NONE, "ATS/ats-order-api", "{\"description\":\"Get all books\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/Book\"}}}}}");
    }


    @Test()
    public void SyncController_getOrderSummaryTest515() {
        testRequest(Method.GET, "https://ats-order-api.internal.{environment}.securitize.io/v1/orders-summary", "SyncController_getOrderSummary", LoginAs.NONE, "ATS/ats-order-api", "{\"description\":\"Get order summary\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetOrderSummaryByFiltersDto\"}}}}}");
    }


    @Test()
    public void PrometheusController_indexTest55() {
        testRequest(Method.GET, "https://ats-order-api.internal.{environment}.securitize.io/metrics", "PrometheusController_index", LoginAs.NONE, "ATS/ats-order-api", "{\"description\":\"\"}");
    }


    @Test()
    public void BooksController_getBooksOldTest100() {
        testRequest(Method.GET, "https://ats-order-api.internal.{environment}.securitize.io/v1/books", "BooksController_getBooksOld", LoginAs.NONE, "ATS/ats-order-api", "{\"description\":\"Get all books\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/Book\"}}}}}");
    }


    @Test()
    public void OrdersController_getOrdersTest299() {
        testRequest(Method.GET, "https://ats-order-api.internal.{environment}.securitize.io/v1/orders", "OrdersController_getOrders", LoginAs.NONE, "ATS/ats-order-api", "{\"description\":\"Get all orders\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetOrdersDto\"}}}}}");
    }




}

