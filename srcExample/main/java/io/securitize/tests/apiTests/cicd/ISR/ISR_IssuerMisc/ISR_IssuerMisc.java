package io.securitize.tests.apiTests.cicd.ISR.ISR_IssuerMisc;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ISR_IssuerMisc extends BaseApiTest {

/*    @Test()
    public void getLogsTest503() {
        testRequest(Method.GET, "https://cp.rc.securitize.io/api/v2/issuers/{issuerId}/logs", "getLogs", LoginAs.OPERATOR, "ISR/main-api","{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"amount\":{\"format\":\"double\",\"type\":\"number\"},\"investor\":{\"type\":\"object\",\"properties\":{\"firstName\":{\"default\":\"\",\"type\":\"string\"},\"lastName\":{\"default\":\"\",\"type\":\"string\"},\"id\":{\"type\":\"number\"}}},\"registrationSource\":{\"type\":[\"string\",\"null\"]},\"currency\":{\"type\":\"object\",\"properties\":{\"identifier\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"new-investor-registered\",\"new-investor-from-rfe\",\"funds-received\",\"subscription-signed\",\"new-signature-required\",\"wallet-registered\",\"investor-requiring-manual-review\"]}}}");
    }*/


    @Test()
    public void getDashboardGeneralInformationTest701() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/dashboard", "getDashboardGeneralInformation", LoginAs.OPERATOR, "ISR/main-api","{\"type\":\"object\",\"properties\":{\"round\":{\"type\":\"object\",\"properties\":{\"amountRaised\":{\"type\":\"number\"},\"amountPledged\":{\"type\":\"number\"},\"status\":{\"type\":\"string\",\"enum\":[\"active\",\"inactive\"]}}},\"holders\":{\"type\":\"object\",\"properties\":{\"totalEUHolders\":{\"type\":\"number\"},\"totalUSHolders\":{\"type\":\"number\"},\"totalWorldHolders\":{\"type\":\"number\"},\"totalHolders\":{\"type\":\"number\"}}},\"onboarding\":{\"type\":\"object\",\"properties\":{\"totalRegistered\":{\"type\":\"number\"},\"totalFunded\":{\"type\":\"number\"},\"totalPledged\":{\"type\":\"number\"}}}}}");
    }

    @Test()
    public void getPartnersTest791() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/partners", "getPartners", LoginAs.OPERATOR, "ISR/main-api","{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"sourceType\":{\"type\":\"string\",\"enum\":[\"registrationSource\",\"brokerDealerGroup\"]},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"}}}}");
    }

    @Test()
    public void getTextsTest815() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/texts", "getTexts", LoginAs.OPERATOR,  "ISR/main-api","{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"issuerId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"system\",\"content\"]},\"value\":{\"type\":[\"string\",\"null\"]},\"updatedAt\":{\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void getPayoutDetailsTest525() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/payout-details", "getPayoutDetails", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"wallet\":{\"type\":[\"object\",\"null\"],\"properties\":{\"address\":{\"type\":\"string\"},\"currency\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"fiat\",\"crypto\"]}}},\"type\":{\"type\":\"string\",\"enum\":[\"automatic\",\"manual\"]}}}}}");
    }
}
