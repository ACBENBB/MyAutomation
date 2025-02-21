package io.securitize.tests.apiTests.cicd.ST.ST_PierApi;

import io.restassured.http.*;
import io.securitize.infra.api.apicodegen.*;
import org.testng.annotations.*;

public class ST_PierApi extends BaseApiTest {

//    @Test()
//    public void AtsController_createAccessTokensTest945() {
//        testRequest(Method.POST, "http://pier.{environment}.securitize.io/v1/ats/{atsId}/access-tokens", "AtsController_createAccessTokens", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\"}");
//    }


    @Test()
    public void DeploymentsController_getDeploymentCountersTest615() {
        testRequest(Method.GET, "http://pier.{environment}.securitize.io/v1/deployments/{deploymentId}/counters", "DeploymentsController_getDeploymentCounters", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/CountersDto\"}}}}");
    }


    @Test()
    public void OrdersController_canBuyTest324() {
        testRequest(Method.GET, "http://pier.{environment}.securitize.io/v1/users/{securitizeId}/orders/canBuy", "OrdersController_canBuy", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OrderAuthorizationDto\"}}}}");
    }


    @Test()
    public void BalancesController_getBalancesTest485() {
        testRequest(Method.GET, "http://pier.{environment}.securitize.io/v1/users/{securitizeId}/balances", "BalancesController_getBalances", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/BalanceDto\"}}}}");
    }


//    @Test()
//    public void MatchesController_matchOrderTest183() {
//        testRequest(Method.POST, "http://pier.{environment}.securitize.io/v1/matches", "MatchesController_matchOrder", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OrderMatchResponseDto\"}}}}");
//    }
//
//
//    @Test()
//    public void OrdersController_removeSellOrderTest735() {
//        testRequest(Method.POST, "http://pier.{environment}.securitize.io/v1/users/{securitizeId}/orders/{orderId}/cancel", "OrdersController_removeSellOrder", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OrderDto\"}}}}");
//    }


    @Test()
    public void AssetsController_getAssetRulesTest631() {
        testRequest(Method.GET, "http://pier.{environment}.securitize.io/v1/assets/{deploymentId}", "AssetsController_getAssetRules", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AssetsDto\"}}}}");
    }


//    @Test()
//    public void AtsController_registerAtsTest492() {
//        testRequest(Method.POST, "http://pier.{environment}.securitize.io/v1/ats", "AtsController_registerAts", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\"}");
//    }


    @Test()
    public void HealthController_checkTest936() {
        testRequest(Method.GET, "http://pier.{environment}.securitize.io/health", "HealthController_check", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


//    @Test()
//    public void OrdersController_addOrderTest883() {
//        testRequest(Method.POST, "http://pier.{environment}.securitize.io/v1/users/{securitizeId}/orders", "OrdersController_addOrder", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OrderDto\"}}}}");
//    }


    @Test()
    public void OrdersController_orderStatusTest766() {
        testRequest(Method.GET, "http://pier.{environment}.securitize.io/v1/users/{securitizeId}/orders/{orderId}/status", "OrdersController_orderStatus", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OrderDto\"}}}}");
    }


    @Test()
    public void DeploymentsController_getDeploymentTBEAddressTest774() {
        testRequest(Method.GET, "http://pier.{environment}.securitize.io/v1/deployments/{deploymentId}/tbe-address", "DeploymentsController_getDeploymentTBEAddress", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TbeAddressDto\"}}}}");
    }


    @Test()
    public void AtsController_getAtsDetailsTest7() {
        testRequest(Method.GET, "http://pier.{environment}.securitize.io/v1/ats/{atsId}", "AtsController_getAtsDetails", LoginAs.OPERATOR, "ST/pier-api", "{\"description\":\"\"}");
    }



}
