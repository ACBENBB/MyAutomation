package io.securitize.tests.apiTests.cicd.ST.ST_AbstractionLayer;

import io.restassured.http.*;
import io.securitize.infra.api.apicodegen.*;
import org.testng.annotations.*;

public class ST_AbstractionLayer extends BaseApiTest {

    //    @Test()
//    public void postAccessTokenPairTest207() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/access_tokens", "postAccessTokenPair", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"'accessToken': identifies the user 'secretAccessToken': proves the identity of the user\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"'accessToken': identifies the user 'secretAccessToken': proves the identity of the user\",\"type\":\"object\",\"properties\":{\"secretAccessToken\":{\"type\":\"string\"},\"accessToken\":{\"type\":\"string\"}}}}}}");
//    }

    //    @Test()
//    public void resetCounterTest729() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/counters/reset", "resetCounter", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"reset counters response\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"reset counters response\",\"additionalProperties\":false,\"type\":\"object\"}}}}");
//    }

    @Test()
    public void getEventsTest645() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/events", "getEvents", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"List of events\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"List of events\",\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"blockNumber\":{\"type\":\"integer\"},\"blockTime\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"eventType\":{\"type\":\"string\"},\"parameters\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}}");
    }

    @Test()
    public void getAccountEnabledTest800() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/account/{account}/enabled", "getAccountEnabled", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Account status response\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Account status response\",\"type\":\"object\",\"properties\":{\"isEnabled\":{\"type\":\"boolean\"}}}}}}");
    }

//    @Test()
//    public void postAlgorandAssetTest812() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/create-asset", "postAlgorandAsset", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void resumeDeploymentTest859() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}", "resumeDeployment", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Deployment data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Deployment data\",\"type\":\"object\",\"properties\":{\"multiSigWalletId\":{\"type\":\"string\"},\"provider\":{\"type\":\"string\"},\"contractAddress\":{\"type\":\"string\"},\"started\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"lastMinedBlockNumber\":{\"type\":\"integer\"}}},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}}}}");
//    }

    @Test()
    public void getDeploymentInfoTest216() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}", "getDeploymentInfo", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Deployment data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Deployment data\",\"type\":\"object\",\"properties\":{\"multiSigWalletId\":{\"type\":\"string\"},\"provider\":{\"type\":\"string\"},\"contractAddress\":{\"type\":\"string\"},\"started\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"lastMinedBlockNumber\":{\"type\":\"integer\"}}},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}}}}");
    }

//    @Test()
//    public void postPauseTest895() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/pause", "postPause", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void postAccessTokensTest98() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/access_tokens", "postAccessTokens", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Token access\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Token access\",\"type\":\"object\",\"properties\":{\"secretAccessToken\":{\"type\":\"string\"},\"accessToken\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void postHSMKeyPairTest570() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/key-pair", "postHSMKeyPair", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"HSM KeyPair\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"HSM KeyPair\",\"type\":\"object\",\"properties\":{\"pkLabel\":{\"type\":\"string\"},\"Address\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getCountriesStatusesTest470() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/countries", "getCountriesStatuses", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Compliance statuses of all countries\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Compliance statuses of all countries\",\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"deploymentId\":{\"type\":\"string\"},\"complianceStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"us\",\"eu\",\"jp\",\"forbidden\"]},\"id\":{\"type\":\"string\"},\"countryName\":{\"type\":\"string\"}}}}}}}");
    }

//    @Test()
//    public void postAlgorandAssetTest535() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/asset", "postAlgorandAsset", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"deploymentId\":{\"type\":\"string\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void retryDeploymentTest297() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/retry", "retryDeployment", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Retry deployment\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Retry deployment\",\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void updateHSMTest117() {
//        testRequest(Method.PUT, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/hsm", "updateHSM", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"HSM Deployment and AbstractionLayer Deployment Ids\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"HSM Deployment and AbstractionLayer Deployment Ids\",\"type\":\"object\",\"properties\":{\"deploymentId\":{\"type\":\"string\"},\"abstractionLayerDeploymentId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void postDeploymentTest664() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments", "postDeployment", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Deployment data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Deployment data\",\"type\":\"object\",\"properties\":{\"multiSigWalletId\":{\"type\":\"string\"},\"provider\":{\"type\":\"string\"},\"contractAddress\":{\"type\":\"string\"},\"started\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"lastMinedBlockNumber\":{\"type\":\"integer\"}}},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}}}}");
//    }

    @Test()
    public void getDeploymentsTest233() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments", "getDeployments", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Deployments list\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Deployments list\",\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"deployments\":{\"type\":\"array\",\"items\":{\"description\":\"Deployment data\",\"type\":\"object\",\"properties\":{\"multiSigWalletId\":{\"type\":\"string\"},\"provider\":{\"type\":\"string\"},\"contractAddress\":{\"type\":\"string\"},\"started\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"lastMinedBlockNumber\":{\"type\":\"integer\"}}},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}},\"provider\":{\"type\":\"string\"}}}}}}}");
    }

    @Test()
    public void getDeploymentGasPriceTest177() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/gas-price", "getDeploymentGasPrice", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"cryptoCurrencySymbol\":{\"type\":\"string\"},\"gasPriceUnit\":{\"type\":\"string\"},\"gasPrice\":{\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void isAddressDeploymentsByDeploymentIdTest240() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/addresses/{address}/check", "isAddressDeploymentsByDeploymentId", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"is address\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"is address\",\"type\":\"object\",\"properties\":{\"valid\":{\"type\":\"boolean\"}}}}}}");
    }

    @Test()
    public void getAddressBalanceByDeploymentIdTest861() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/addresses/{address}/balance", "getAddressBalanceByDeploymentId", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"balance\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"balance\",\"type\":\"object\",\"properties\":{\"balance\":{\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void getComplianceCountersTest307() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/compliance_counters", "getComplianceCounters", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Compliance counters\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Compliance counters\",\"type\":\"object\",\"properties\":{\"usAccreditedInvestorsCount\":{\"type\":\"integer\"},\"euRetailInvestorsCounts\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"countryCode\":{\"type\":\"string\"},\"count\":{\"type\":\"integer\"}}}},\"accreditedInvestorsCount\":{\"type\":\"integer\"},\"jpInvestorsCount\":{\"type\":\"integer\"},\"totalInvestorsCount\":{\"type\":\"integer\"},\"usInvestorsCount\":{\"type\":\"integer\"}}}}}}");
    }

    //    @Test()
//    public void setComplianceRulesTest157() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/compliance_rules", "setComplianceRules", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getComplianceRulesTest20() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/compliance_rules", "getComplianceRules", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Compliance rules\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Compliance rules\",\"type\":\"object\",\"properties\":{\"forceFullTransfer\":{\"type\":\"boolean\"},\"forceAccredited\":{\"type\":\"boolean\"},\"nonUSLockPeriod\":{\"type\":\"integer\"},\"nonAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"maxUSInvestorsPercentage\":{\"type\":\"integer\"},\"forceAccreditedUS\":{\"type\":\"boolean\"},\"usAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"worldWideForceFullTransfer\":{\"type\":\"boolean\"},\"minEUTokens\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"},\"usLockPeriod\":{\"type\":\"integer\"},\"jpInvestorsLimit\":{\"type\":\"integer\"},\"blockFlowbackEndTime\":{\"type\":\"integer\"},\"totalInvestorsLimit\":{\"type\":\"integer\"},\"euRetailInvestorsLimit\":{\"type\":\"integer\"},\"usInvestorsLimit\":{\"type\":\"integer\"},\"minimumHoldingsPerInvestor\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"},\"maximumHoldingsPerInvestor\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"},\"minimumTotalInvestors\":{\"type\":\"integer\"},\"minUSTokens\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"}}}}}}");
    }

    //    @Test()
//    public void setCountryStatusTest83() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/countries/{isocode}", "setCountryStatus", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getCountryStatusTest791() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/countries/{isocode}", "getCountryStatus", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Country Status\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Country Status\",\"type\":\"object\",\"properties\":{\"complianceStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"us\",\"eu\",\"jp\",\"forbidden\"]}}}}}}");
    }

    @Test()
    public void getAddressLinkTest0() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/providers/{provider}/explorer/{address}/link", "getAddressLink", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Link\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Link\",\"type\":\"object\",\"properties\":{\"url\":{\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void getAddressLinkByDeploymentIdTest641() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/explorer/{address}/link", "getAddressLinkByDeploymentId", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Link\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Link\",\"type\":\"object\",\"properties\":{\"url\":{\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void getHolderAttributeValueTest744() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/holders/{holderId}/attributes/{name}", "getHolderAttributeValue", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"attribute\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"attribute\",\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}}}}");
    }

//    @Test()
//    public void deleteHolderAttributeTest249() {
//        testRequest(Method.DELETE, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/holders/{holderId}/attributes/{name}", "deleteHolderAttribute", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void updateAttributeTest722() {
//        testRequest(Method.PUT, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/holders/{holderId}/attributes/{name}", "updateAttribute", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getHolderTest576() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/holders/{holderId}", "getHolder", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Holder\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Holder\",\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"description\":\"attribute\",\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}}}}");
    }

//    @Test()
//    public void deleteTokenHolderTest902() {
//        testRequest(Method.DELETE, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/holders/{holderId}", "deleteTokenHolder", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void updateTokenHolderTest60() {
//        testRequest(Method.PUT, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/holders/{holderId}", "updateTokenHolder", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void postHolderRecordTest836() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/holders", "postHolderRecord", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getHoldersTest159() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/holders", "getHolders", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"holders list\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"holders list\",\"type\":\"array\",\"items\":{\"description\":\"Holder\",\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"description\":\"attribute\",\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}}}}}");
    }

    @Test()
    public void getHolderBalanceTest666() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/holders/{holderId}/balance", "getHolderBalance", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Holders balance response\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Holders balance response\",\"type\":\"object\",\"properties\":{\"balance\":{\"type\":\"string\"}}}}}}");
    }

    //    @Test()
//    public void removeLockTest90() {
//        testRequest(Method.DELETE, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/locks/{holderId}/{lockIndex}", "removeLock", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void addLockTest46() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/locks/{holderId}", "addLock", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getLocksTest715() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/locks/{holderId}", "getLocks", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"List of value-locks associated with a holder\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"List of value-locks associated with a holder\",\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"value\"],\"properties\":{\"reason\":{\"type\":\"string\"},\"releaseTime\":{\"type\":\"integer\"},\"reasonCode\":{\"default\":0,\"type\":\"integer\"},\"value\":{\"type\":\"string\"}}}}}}}");
    }

//    @Test()
//    public void lockMultiSigWalletTest821() {
//        testRequest(Method.PUT, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{walletId}/locking/{lockType}", "lockMultiSigWallet", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"MultiSig wallet Lock\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"MultiSig wallet Lock\",\"type\":\"object\",\"properties\":{\"deploymentStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"deploymentId\":{\"type\":\"string\"},\"signingLock\":{\"default\":false,\"description\":\"True if multi-sig wallet can not create new transactions because of pending transactions\",\"type\":\"boolean\"}}}}}}");
//    }

//    @Test()
//    public void addInvestorLockTest735() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/locks/{holderId}/investor_lock", "addInvestorLock", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getInvestorLockTest849() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/locks/{holderId}/investor_lock", "getInvestorLock", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Checks if an investor is locked\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Checks if an investor is locked\",\"type\":\"object\",\"properties\":{\"locked\":{\"type\":\"boolean\"}}}}}}");
    }

//    @Test()
//    public void postSeizeTest297() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/tokens/seize", "postSeize", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void postTransferTokensTest414() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/tokens/transfer", "postTransferTokens", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    //    @Test()
//    public void deployMultisigWalletTest595() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets", "deployMultisigWallet", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Multisig wallet\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Multisig wallet\",\"type\":\"object\",\"properties\":{\"deploymentStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"id\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getMultisigWalletTest860() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets", "getMultisigWallet", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"List of Multisig wallets\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"List of Multisig wallets\",\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"deploymentStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"walletName\":{\"type\":\"string\"},\"signingLock\":{\"default\":false,\"type\":\"boolean\"},\"threshold\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"walletAddress\":{\"type\":\"string\"},\"nonce\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"}},\"required\":[\"address\"]}}}}}");
    }

    @Test()
    public void getSingleMultiSigWalletTest838() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{walletId}", "getSingleMultiSigWallet", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"MultiSig wallet\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"MultiSig wallet\",\"type\":\"object\",\"properties\":{\"deploymentStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"walletName\":{\"type\":\"string\"},\"deploymentId\":{\"type\":\"number\"},\"signingLock\":{\"default\":false,\"description\":\"True if multi-sig wallet can not create new transactions because of pending transactions\",\"type\":\"boolean\"},\"threshold\":{\"type\":\"number\"},\"walletAddress\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"}}}}}}");
    }

    //    public void postWithdrawalTest664() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/omnibus/tokens/transfer", "postWithdrawal", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getAlgorandTBEAddressTest773() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/omnibus/tokens/tbe-address", "getAlgorandTBEAddress", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Return Omnibus TBE Wallet Address\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Return Omnibus TBE Wallet Address\",\"type\":\"object\",\"properties\":{\"OmnibusWalletAddress\":{\"type\":\"string\"}}}}}}");
    }

//    @Test()
//    public void updateCountersInternalTransferTest174() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/omnibus/tokens/internal_transfer", "updateCountersInternalTransfer", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    //    @Test()
//    public void postBulkIssuanceTBETest870() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/omnibus/tokens", "postBulkIssuanceTBE", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void deleteTokensTBETest658() {
//        testRequest(Method.DELETE, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/omnibus/tokens", "deleteTokensTBE", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    //    @Test()
//    public void setRoleForWalletTest261() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/role/{address}", "setRoleForWallet", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getRoleForWalletTest777() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/role/{address}", "getRoleForWallet", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"roles\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"roles\",\"type\":\"object\",\"properties\":{\"role\":{\"type\":\"string\",\"enum\":[\"none\",\"master\",\"issuer\",\"exchange\"]}}}}}}");
    }

    //    @Test()
//    public void postTokensTest855() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/tokens", "postTokens", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getMintedTokensTest808() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/tokens", "getMintedTokens", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Total supply response\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Total supply response\",\"type\":\"object\",\"properties\":{\"totalSupply\":{\"type\":\"string\"}}}}}}");
    }

//    @Test()
//    public void deleteTokensTest206() {
//        testRequest(Method.DELETE, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/tokens", "deleteTokens", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getDeploymentTokenDetailsTest233() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/token_details", "getDeploymentTokenDetails", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Token detail\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Token detail\",\"type\":\"object\",\"properties\":{\"symbol\":{\"type\":\"string\"},\"isPaused\":{\"type\":\"boolean\"},\"totalWallets\":{\"type\":\"integer\"},\"totalAssets\":{\"type\":\"string\"},\"totalIssued\":{\"type\":\"string\"},\"decimals\":{\"type\":\"integer\"},\"name\":{\"type\":\"string\"},\"type\":{\"type\":\"string\",\"enum\":[\"standard\",\"partitioned\"]},\"metadataHash\":{\"type\":\"string\"},\"url\":{\"type\":\"string\"}}}}}}");
    }

//    @Test()
//    public void postAllowanceTest907() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/tokens/allowance", "postAllowance", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getAllowanceTest36() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/tokens/allowance?owner={owner}&spender={spender}", "getAllowance", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Check allowance response\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Check allowance response\",\"type\":\"object\",\"properties\":{\"allowance\":{\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void getWalletBalanceTest72() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/wallets/{address}/balance", "getWalletBalance", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Wallet token balance response\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Wallet token balance response\",\"type\":\"object\",\"properties\":{\"balance\":{\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void getHolderByWalletIDTest293() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/wallets/{address}/holder", "getHolderByWalletID", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Holder\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Holder\",\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"description\":\"attribute\",\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}}}}");
    }

//    @Test()
//    public void postWalletTest331() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/wallets", "postWallet", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getWalletsTest533() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/wallets", "getWallets", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"wallets list\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"wallets list\",\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"address\":{\"pattern\":\".+\",\"type\":\"string\"},\"balance\":{\"type\":\"string\"},\"holderId\":{\"type\":\"string\"}},\"required\":[\"holderId\",\"address\",\"balance\"]}}}}}");
    }

//    @Test()
//    public void deleteWalletTest807() {
//        testRequest(Method.DELETE, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/wallets/{address}", "deleteWallet", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getHealthCheckTest80() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/health-check", "getHealthCheck", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"health check\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"health check\",\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"OK\",\"ALARM\"]}}}}}}");
    }

    //    @Test()
//    public void createQueueTest693() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/message_queues", "createQueue", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Created queue\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Created queue\",\"type\":\"object\",\"properties\":{\"synced\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"url\":{\"type\":\"string\"}}}}}}");
//    }

        @Test()
    public void isAddressTest596() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/providers/{provider}/addresses/{address}/check", "isAddress", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"is address\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"is address\",\"type\":\"object\",\"properties\":{\"valid\":{\"type\":\"boolean\"}}}}}}");
    }

    @Test()
    public void getProviderInfoTest180() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/providers/{provider}/info", "getProviderInfo", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"rpc\":{\"type\":\"string\"},\"chainId\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"},\"networkId\":{\"type\":\"number\"}}}}}}");
    }

    @Test()
    public void getAddressBalanceTest649() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/providers/{provider}/addresses/{address}/balance", "getAddressBalance", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"balance\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"balance\",\"type\":\"object\",\"properties\":{\"balance\":{\"type\":\"string\"}}}}}}");
    }

//    @Test()
//    public void createHSMKeyPairProviderTest405() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/providers/{providerId}/key-pair", "createHSMKeyPairProvider", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"HSM KeyPair\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"HSM KeyPair\",\"type\":\"object\",\"properties\":{\"pkLabel\":{\"type\":\"string\"},\"Address\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getTokenDetailsTest488() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/tokens/{tokenId}", "getTokenDetails", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Token details\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Token details\",\"type\":\"object\",\"properties\":{\"tokenAddress\":{\"type\":\"string\"},\"tokenId\":{\"type\":\"string\"},\"deploymentId\":{\"type\":\"string\"},\"network\":{\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void getProviderTransactionTest846() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/providers/{provider}/transactions/{id}", "getProviderTransaction", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"transaction\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"transaction\",\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"blockNumber\":{\"type\":\"integer\"},\"transactionProviderId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"sent\",\"error\",\"success\",\"failure\",\"signed\",\"canceled\",\"speed-up\"]}}}}}}");
    }

    @Test()
    public void getProviderTransactionByDeploymentIdTest561() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/transactions/{txId}", "getProviderTransactionByDeploymentId", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"transaction\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"transaction\",\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"blockNumber\":{\"type\":\"integer\"},\"transactionProviderId\":{\"type\":\"string\"},\"signerAddress\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"sent\",\"error\",\"success\",\"failure\",\"signed\",\"canceled\",\"speed-up\"]},\"gasPrice\":{\"type\":\"string\"}}}}}}");
    }

//    @Test()
//    public void speedupTransactionTest999() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/transactions/{id}/speedup", "speedupTransaction", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getTransactionInformationTest624() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/transactions/{id}", "getTransactionInformation", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Transaction info\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Transaction info\",\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"inputData\":{\"type\":\"string\"},\"blockNumber\":{\"nullable\":true,\"type\":\"integer\"},\"targetAddress\":{\"type\":\"string\"},\"externalId\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"transactionProviderId\":{\"nullable\":true,\"type\":\"string\"},\"additionalData\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"sent\",\"error\",\"success\",\"failure\",\"signed\",\"canceled\",\"speed-up\"]},\"timestamp\":{\"nullable\":true,\"type\":\"integer\"}}}}}}");
    }

    @Test()
    public void decodeTransactionTest111() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/transactions/{id}/decode", "decodeTransaction", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"transaction\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"transaction\",\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"blockNumber\":{\"type\":\"integer\"},\"transactionProviderId\":{\"type\":\"string\"},\"signerAddress\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"sent\",\"error\",\"success\",\"failure\",\"signed\",\"canceled\",\"speed-up\"]},\"gasPrice\":{\"type\":\"string\"}}}}}}");
    }

//    @Test()
//    public void executeTransactionTest98() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/transactions/{id}", "executeTransaction", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Transaction execution result\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Transaction execution result\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"description\":\"The transactionData to sign final multisig transactions\",\"type\":\"string\"},\"signatureId\":{\"type\":\"string\"},\"transactionProviderId\":{\"type\":\"string\"},\"additionalData\":{\"description\":\"The additionalData to sign final multisig transactions\",\"type\":\"object\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"sent\",\"error\",\"success\",\"failure\",\"signed\",\"canceled\",\"speed-up\"]}}}}}}");
//    }

    @Test()
    public void getGasPriceTest362() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/providers/{provider}/transactions/gas_price", "getGasPrice", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"cryptoCurrencySymbol\":{\"type\":\"string\"},\"gasPriceUnit\":{\"type\":\"string\"},\"gasPrice\":{\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void getTransactionsInformationTest474() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/transactions", "getTransactionsInformation", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Transaction different statuses explanation :Unsigned - Transaction was created but not yet signed ,Sent - Transaction was executed pending results, Success - Transaction was executed with successfull results, Failure - Transaction was executed with running errors, Error - There was an error when trying to execute the transaction\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Transaction different statuses explanation :Unsigned - Transaction was created but not yet signed ,Sent - Transaction was executed pending results, Success - Transaction was executed with successfull results, Failure - Transaction was executed with running errors, Error - There was an error when trying to execute the transaction\",\"type\":\"array\",\"items\":{\"description\":\"Transaction info\",\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"inputData\":{\"type\":\"string\"},\"blockNumber\":{\"nullable\":true,\"type\":\"integer\"},\"targetAddress\":{\"type\":\"string\"},\"externalId\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"transactionProviderId\":{\"nullable\":true,\"type\":\"string\"},\"additionalData\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"sent\",\"error\",\"success\",\"failure\",\"signed\",\"canceled\",\"speed-up\"]},\"timestamp\":{\"nullable\":true,\"type\":\"integer\"}}}}}}}");
    }

    @Test()
    public void getMultiSigTransactionStatusSchemaTest708() {
        testRequest(Method.GET, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/transactions/status", "getMultiSigTransactionStatusSchema", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"The signatures of the off-chain transactions are saved until reaching the minimum amount required to execute it (threshold). This service returns the number of signatures so far\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"The signatures of the off-chain transactions are saved until reaching the minimum amount required to execute it (threshold). This service returns the number of signatures so far\",\"type\":\"object\",\"properties\":{\"multiSigWalletId\":{\"description\":\"The id of the multisig\",\"type\":\"string\"},\"transactionData\":{\"description\":\"The transactionData of the transaction\",\"type\":\"string\"},\"signingLock\":{\"default\":false,\"description\":\"True if multi-sig wallet can not create new transactions because of pending transactions\",\"type\":\"boolean\"},\"threshold\":{\"description\":\"The number of off-chain required signatures to sign the transaction\",\"type\":\"number\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"networkId\":{\"type\":\"integer\"},\"networkType\":{\"type\":\"string\"}}},\"nonce\":{\"description\":\"The nonce of the multisig wallet when the transaction is created\",\"type\":\"string\"},\"transactionId\":{\"description\":\"The id of the transaction\",\"type\":\"string\"},\"signatures\":{\"description\":\"The number of off-chain saved signatures of the transaction\",\"type\":\"number\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"sent\",\"error\",\"success\",\"failure\",\"signed\",\"canceled\",\"speed-up\"]}}}}}}");
    }

//    @Test()
//    public void cancelTransactionTest930() {
//        testRequest(Method.POST, "http://abstraction-layer.internal.{environment}.securitize.io/v1/transactions/{id}/cancel", "cancelTransaction", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"Prepared transaction to sign\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Prepared transaction to sign\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
//    }

//    @Test()
//    public void deleteMultiSigTransactionSignaturesTest39() {
//        testRequest(Method.DELETE, "http://abstraction-layer.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/transactions/{transactionId}/signatures", "deleteMultiSigTransactionSignatures", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"multiSigWalletId\":{\"description\":\"The id of the multisig\",\"type\":\"string\"},\"transactionId\":{\"description\":\"The id of the transaction\",\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"sent\",\"error\",\"success\",\"failure\",\"signed\",\"canceled\",\"speed-up\"]}}}}}}");
//    }

//    @Test()
//    public void updateTransactionTest27() {
//        testRequest(Method.PUT, "http://abstraction-layer.internal.{environment}.securitize.io/v1/transactions/{id}/status", "updateTransaction", LoginAs.OPERATOR, "ST/abstraction-layer", "{\"description\":\"update transaction response\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"update transaction response\",\"additionalProperties\":false,\"type\":\"object\"}}}}");
//    }

}
