package io.securitize.tests.apiTests.cicd.ST.ST_AlgorandService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_AlgorandService extends BaseApiTest {

    @Test()
    public void getTokenDescriptionTest263() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/details", "getTokenDescription", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TokenDescriptionDto\"}}}}");
    }


    @Test()
    public void getAttributeTest408() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "getAttribute", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/HolderAttributeDto\"}}}}");
    }


    @Test()
    public void getTBEOmnibusAddressTest309() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/omnibus/tbe-address", "getTBEOmnibusAddress", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OmnibusTbeAddressDto\"}}}}");
    }


    @Test()
    public void getHolderTest574() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "getHolder", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/HolderDto\"}}}}");
    }


    @Test()
    public void getInvestorLockedTest348() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}/investor_lock", "getInvestorLocked", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/InvestorLockedDto\"}}}}");
    }


    @Test()
    public void getLocksTest12() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}", "getLocks", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/ValueLockDto\"}}}}}");
    }


    @Test()
    public void getCountryStatusTest395() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/countries/{isocode}", "getCountryStatus", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"default\":\"none\",\"type\":\"string\",\"enum\":[\"eu\",\"us\",\"forbidden\",\"none\"]}}}}");
    }


    @Test()
    public void getActiveComplianceRulesTest758() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_rules", "getActiveComplianceRules", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/ComplianceRulesDto\"}}}}");
    }


    @Test()
    public void HealthController_checkTest143() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/health-check", "HealthController_check", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void ProviderController_getAddressLinkTest499() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/explorer/{address}/link", "ProviderController_getAddressLink", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AddressLinkDto\"}}}}");
    }


    @Test()
    public void ProviderController_getAddressBalanceTest171() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/addresses/{address}/balance", "ProviderController_getAddressBalance", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AddressBalanceDto\"}}}}");
    }


    @Test()
    public void getActiveComplianceCountersTest437() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_counters", "getActiveComplianceCounters", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/ComplianceCountersDto\"}}}}");
    }


    @Test()
    public void getEventsTest24() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/events", "getEvents", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/EventsDto\"}}}}");
    }


    @Test()
    public void getRoleForWalletTest938() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/role/{walletId}", "getRoleForWallet", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/\"}}}}");
    }


    @Test()
    public void getTransactionsTest617() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/transactions", "getTransactions", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/TransactionInformationDto\"}}}}}");
    }


    @Test()
    public void totalSupplyTest930() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "totalSupply", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/\"}}}}");
    }


    @Test()
    public void getHolderByWalletTest972() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets/{walletId}", "getHolderByWallet", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/HolderDto\"}}}}");
    }


    @Test()
    public void balanceOfTest540() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}", "balanceOf", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/\"}}}}");
    }


    @Test()
    public void ProviderController_isAddressTest930() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/address/{address}/check", "ProviderController_isAddress", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"boolean\"}}}}");
    }


    @Test()
    public void enabledTest587() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{account}/enabled", "enabled", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/\"}}}}");
    }


    @Test()
    public void investorBalanceTest914() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/balance", "investorBalance", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/\"}}}}");
    }


    @Test()
    public void ProviderController_getLatestBlockNumberTest921() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/latest_block_number", "ProviderController_getLatestBlockNumber", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AddressBalanceDto\"}}}}");
    }


    @Test()
    public void allowanceTest378() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/allowance/{spender}", "allowance", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/\"}}}}");
    }


    @Test()
    public void providerDetailsTest679() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/details", "providerDetails", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DetailsDto\"}}}}");
    }


    @Test()
    public void getTransactionInformationTest111() {
        testRequest(Method.GET, "https://algorand-service.internal.{environment}.securitize.io/v1/transactions/{id}", "getTransactionInformation", LoginAs.NONE, "ST/algorand-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TransactionInformationDto\"}}}}");
    }




}

