package io.securitize.tests.apiTests.cicd.ST.ST_DsAlgorandProvider;

import io.restassured.http.*;
import io.securitize.infra.api.apicodegen.*;
import org.testng.annotations.*;

public class ST_DsAlgorandProvider extends BaseApiTest {

//    @Test()
//    public void removeLockTest226() {
//        testRequest(Method.DELETE, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}/{lockIndex}", "removeLock", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void providerDetailsTest403() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/details", "providerDetails", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"object\",\"required\":[\"name\",\"description\",\"underlying\"],\"properties\":{\"name\":{\"type\":\"string\"},\"description\":{\"type\":\"string\"},\"underlying\":{\"type\":\"string\"},\"version\":{\"type\":\"string\"},\"network\":{\"type\":\"string\"}}}");
    }


//    @Test()
//    public void changeOwnerTest121() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/deployments/{address}/owner", "changeOwner", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"description\":\"Success\"}");
//    }
//
//
//    @Test()
//    public void executeTransactionTest555() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/transactions", "executeTransaction", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"object\",\"properties\":{\"transactionId\":{\"type\":\"string\"}}}");
//    }


    @Test()
    public void getAddressLinkTest447() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/explorer/{address}/link", "getAddressLink", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"object\",\"properties\":{\"url\":{\"format\":\"url\",\"type\":\"object\"}}}");
    }


    @Test()
    public void getActiveComplianceCountersTest201() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/compliance_counters", "getActiveComplianceCounters", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/ComplianceCounters\"}");
    }


//    @Test()
//    public void cancelTransactionTest460() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/transactions/{id}/cancel", "cancelTransaction", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void getTBEOmnibusAddressTest606() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/omnibus/tbe-address", "getTBEOmnibusAddress", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/OmnibusTBEWalletAddress\"}");
    }


    @Test()
    public void getTransactionsTest963() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/transactions", "getTransactions", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/TransactionInfo\"}}");
    }


    @Test()
    public void getLatestBlockNumberTest145() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/latest_block_number", "getLatestBlockNumber", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"object\",\"properties\":{\"blockNumber\":{\"type\":\"integer\"}}}");
    }


//    @Test()
//    public void updateAttributeTest760() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "updateAttribute", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void getAttributeTest18() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "getAttribute", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}");
    }


//    @Test()
//    public void deleteAttributeTest831() {
//        testRequest(Method.DELETE, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "deleteAttribute", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }
//
//
//    @Test()
//    public void addWalletTest899() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/wallets", "addWallet", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }
//
//
//    @Test()
//    public void mintTest618() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "mint", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void totalSupplyTest902() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "totalSupply", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"integer\"}");
    }


//    @Test()
//    public void burnTest819() {
//        testRequest(Method.DELETE, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "burn", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void addressBalanceTest880() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/addresses/{address}/balance", "addressBalance", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"object\",\"properties\":{\"balance\":{\"type\":\"integer\"}}}");
    }


//    @Test()
//    public void createAccessTokensTest874() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/access_tokens", "createAccessTokens", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"object\",\"properties\":{\"secretAccessToken\":{\"type\":\"string\"},\"accessToken\":{\"type\":\"string\"}}}");
//    }


    @Test()
    public void getTokenDescriptionTest794() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/details", "getTokenDescription", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/TokenDescription\"}");
    }


    @Test()
    public void getHolderTest759() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "getHolder", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Holder\"}");
    }


//    @Test()
//    public void deleteHolderTest5() {
//        testRequest(Method.DELETE, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "deleteHolder", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }
//
//
//    @Test()
//    public void updateHolderTest531() {
//        testRequest(Method.PUT, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "updateHolder", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }
//
//
//    @Test()
//    public void setCountryStatusTest137() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/countries/{isocode}", "setCountryStatus", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void getCountryStatusTest278() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/countries/{isocode}", "getCountryStatus", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/CountryComplianceStatus\"}");
    }


    @Test()
    public void enabledTest651() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{account}/enabled", "enabled", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"object\"}");
    }


//    @Test()
//    public void approveTest495() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/allowance", "approve", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }
//
//
//    @Test()
//    public void createHolderTest537() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/holders", "createHolder", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }
//
//
//    @Test()
//    public void bulkTBEIssuanceTest614() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens", "bulkTBEIssuance", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/TransactionTBE\"}");
//    }
//
//
//    @Test()
//    public void bulkTBEBurnTest776() {
//        testRequest(Method.DELETE, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens", "bulkTBEBurn", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/TransactionTBE\"}");
//    }
//
//
//    @Test()
//    public void setTokenPauseStateTest573() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/pause", "setTokenPauseState", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void getHealthCheckTest917() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/health-check", "getHealthCheck", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"object\",\"required\":[\"status\"],\"properties\":{\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"ok\",\"alarm\"]}}}");
    }


    @Test()
    public void getEventsTest405() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/events", "getEvents", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"object\",\"properties\":{\"fromBlock\":{\"type\":\"number\"},\"toBlock\":{\"type\":\"number\"},\"events\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/Event\"}}}}");
    }


    @Test()
    public void getTransactionInformationTest922() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/transactions/{id}", "getTransactionInformation", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/TransactionInfo\"}");
    }


//    @Test()
//    public void adjustTBECountersTest882() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/counters", "adjustTBECounters", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/TransactionTBE\"}");
//    }
//
//
//    @Test()
//    public void internalTBETransferTest181() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/internal_tbe_transfer", "internalTBETransfer", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/TransactionTBE\"}");
//    }
//
//
//    @Test()
//    public void speedupTransactionTest780() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/transactions/{id}/speedup", "speedupTransaction", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void balanceOfTest2() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}", "balanceOf", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"integer\"}");
    }


//    @Test()
//    public void transferTest848() {
//        testRequest(Method.PUT, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}", "transfer", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }
//
//
//    @Test()
//    public void setActiveComplianceRulesTest374() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/compliance_rules", "setActiveComplianceRules", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void getActiveComplianceRulesTest669() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/compliance_rules", "getActiveComplianceRules", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/ComplianceRules\"}");
    }


    @Test()
    public void getHolderByWalletTest768() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/wallets/{walletId}", "getHolderByWallet", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Holder\"}");
    }


//    @Test()
//    public void removeWalletTest803() {
//        testRequest(Method.DELETE, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/wallets/{walletId}", "removeWallet", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }
//
//
//    @Test()
//    public void createAssetTest132() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/deployments/create-asset", "createAsset", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/TransactionData\"}");
//    }
//
//
//    @Test()
//    public void bulkTBETransferTest662() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens/transfer", "bulkTBETransfer", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/TransactionTBE\"}");
//    }


    @Test()
    public void isAddressTest337() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/addresses/{address}/check", "isAddress", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"boolean\"}");
    }


//    @Test()
//    public void unfreezeTest350() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/deployments/{address}/unfreeze", "unfreeze", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"description\":\"Success\"}");
//    }


    @Test()
    public void allowanceTest415() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/allowance/{spender}", "allowance", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"string\"}");
    }


    @Test()
    public void investorBalanceTest424() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/balance", "investorBalance", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"integer\"}");
    }


//    @Test()
//    public void addLockTest609() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}", "addLock", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void getLocksTest606() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}", "getLocks", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/ValueLock\"}}");
    }


//    @Test()
//    public void setRoleForWalletTest463() {
//        testRequest(Method.POST, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/role/{walletId}", "setRoleForWallet", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }


    @Test()
    public void getRoleForWalletTest433() {
        testRequest(Method.GET, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/role/{walletId}", "getRoleForWallet", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Role\"}");
    }


//    @Test()
//    public void seizeTest17() {
//        testRequest(Method.PUT, "https://algorand.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/seize", "seize", LoginAs.OPERATOR, "ST/ds-algorand-provider", "{\"$ref\":\"#/definitions/Transaction\"}");
//    }

}
