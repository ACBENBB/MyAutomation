package io.securitize.tests.apiTests.cicd.ISR.ISR_PrivacyControl;

import io.restassured.http.*;
import io.securitize.infra.api.apicodegen.*;
import org.testng.annotations.*;

public class ISR_PrivacyControl extends BaseApiTest {


//    @Test()
//    public void addPrivacyControlOperatorTest() {
//        testRequest(Method.POST, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/privacy-control", "addPrivacyControlOperator", LoginAs.OPERATOR,  "ISR/main-api","{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"operatorId\":{\"type\":\"number\"},\"email\":{\"format\":\"email\",\"type\":\"string\"}}}");
//    }


    @Test()
    public void getPrivacyControlOperatorsTest() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/privacy-control", "getPrivacyControlOperators", LoginAs.OPERATOR,  "ISR/main-api","{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"externalId\":{\"type\":\"number\"},\"operatorId\":{\"type\":\"number\"},\"email\":{\"format\":\"email\",\"type\":\"string\"}}}}}}");
   }


//    @Test(dependsOnMethods = "getPrivacyControlOperatorsTest")
//    public void deletePrivacyControlOperatorTest() {
//        testRequest(Method.DELETE, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/privacy-control/{operatorId}", "deletePrivacyControlOperator", LoginAs.OPERATOR,  "ISR/main-api","{\"description\":\"Success\"}");
//    }
}
