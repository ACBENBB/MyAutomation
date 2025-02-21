package io.securitize.tests.apiTests.cicd.SID.SID_PromotionService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class SID_PromotionService extends BaseApiTest {

//    @Test()
//    public void PromotionsController_createTest63() {
//        testRequest(Method.POST, "https://promotion-service.internal.{environment}.securitize.io/api/v1/promotions", "PromotionsController_create", LoginAs.NONE, "SID/promotion-service", "{\"description\":\"Create Promotion\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetPromotionDto\"}}}}");
//    }


    @Test()
    public void PromotionsController_findAllTest157() {
        testRequest(Method.GET, "https://promotion-service.internal.{environment}.securitize.io/api/v1/promotions", "PromotionsController_findAll", LoginAs.NONE, "SID/promotion-service", "{\"description\":\"Get Promotions\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetPromotionDto\"}}}}}");
    }


    @Test()
    public void HealthController_checkHealthTest223() {
        testRequest(Method.GET, "https://promotion-service.internal.{environment}.securitize.io/health", "HealthController_checkHealth", LoginAs.NONE, "SID/promotion-service", "{\"description\":\"Health Check\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/HealthDto\"}}}}");
    }


//    @Test()
//    public void PromotionsController_updateTest439() {
//        testRequest(Method.PATCH, "https://promotion-service.internal.{environment}.securitize.io/api/v1/promotions/{id}", "PromotionsController_update", LoginAs.NONE, "SID/promotion-service", "{\"description\":\"Edit Promotion By Id\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetPromotionDto\"}}}}");
//    }


//    @Test()
//    public void PromotionsController_removeTest496() {
//        testRequest(Method.DELETE, "https://promotion-service.internal.{environment}.securitize.io/api/v1/promotions/{id}", "PromotionsController_remove", LoginAs.NONE, "SID/promotion-service", "{\"description\":\"Delete Promotion By Id\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DeletePromotionDto\"}}}}");
//    }




}

