package io.securitize.tests.apiTests.cicd.ST.ST_ContractsDataService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_Avalanche_ContractDataService extends BaseApiTest {
/*

    @Test()
    public void updateAttributeTest453() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "updateAttribute", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getAttributeTest239() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "getAttribute", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}");
    }

/*

    @Test()
    public void deleteAttributeTest917() {
        testRequest(Method.DELETE, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "deleteAttribute", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void resetCounterTest891() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/counters/reset", "resetCounter", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"additionalProperties\":false,\"type\":\"object\"}");
    }
*/


    @Test()
    public void getHolderTest279() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "getHolder", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"minItems\":1,\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}");
    }

/*

    @Test()
    public void deleteHolderTest804() {
        testRequest(Method.DELETE, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "deleteHolder", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void updateHolderTest985() {
        testRequest(Method.PUT, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "updateHolder", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void createAccessTokensTest531() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/access_tokens", "createAccessTokens", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"secretAccessToken\":{\"type\":\"string\"},\"accessToken\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void addInvestorLockTest403() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}/investor_lock", "addInvestorLock", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getInvestorLockedTest963() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}/investor_lock", "getInvestorLocked", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"locked\":{\"type\":\"boolean\"}}}");
    }

/*

    @Test()
    public void adjustTBECountersTest543() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/counters", "adjustTBECounters", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void setTokenPauseStateTest963() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/pause", "setTokenPauseState", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void setCountryStatusTest922() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/countries/{isocode}", "setCountryStatus", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getCountryStatusTest183() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/countries/{isocode}", "getCountryStatus", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"string\",\"enum\":[\"none\",\"us\",\"eu\",\"jp\",\"forbidden\"]}");
    }

/*

    @Test()
    public void createTransactionTest766() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{address}/transactions", "createTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void retrieveSwapContractStatusTest221() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/swap-contracts/{swapContractId}", "retrieveSwapContractStatus", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"deploymentStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"swapContractAddress\":{\"type\":[\"string\",\"null\"]}}}");
    }

/*

    @Test()
    public void speedupTransactionTest498() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/transactions/{id}/speedup", "speedupTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getAddressLinkTest539() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/explorer/{address}/link", "getAddressLink", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"url\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getEventsTest571() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/events", "getEvents", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"fromBlock\":{\"type\":\"number\"},\"toBlock\":{\"type\":\"number\"},\"events\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"eventType\",\"blockNumber\"],\"properties\":{\"blockNumber\":{\"type\":\"number\"},\"blockTime\":{\"type\":\"number\"},\"id\":{\"type\":\"string\"},\"eventType\":{\"type\":\"string\",\"enum\":[\"Issue\",\"Transfer\",\"Burn\",\"Seize\",\"Approval\",\"DSRegistryServiceInvestorAdded\",\"DSRegistryServiceInvestorRemoved\",\"DSRegistryServiceInvestorCountryChanged\",\"DSRegistryServiceInvestorAttributeChanged\",\"DSRegistryServiceWalletAdded\",\"DSRegistryServiceWalletRemoved\",\"DSTrustServiceRoleAdded\",\"DSTrustServiceRoleRemoved\",\"HolderLocked\",\"HolderUnlocked\",\"InvestorFullyUnlocked\",\"InvestorFullyLocked\"]},\"parameters\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
    }

/*

    @Test()
    public void listenTokenEventsTest731() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/events/listen", "listenTokenEvents", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"listening\":{\"type\":\"boolean\"}}}");
    }
*/


    @Test()
    public void balanceOfTest130() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}", "balanceOf", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"string\"}");
    }

/*

    @Test()
    public void transferTest187() {
        testRequest(Method.PUT, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}", "transfer", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void createHolderTest750() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders", "createHolder", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void deployAnAlreadyExistingDeploymentByIdTest147() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/deployments/{id}", "deployAnAlreadyExistingDeploymentById", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"multiSigWalletDeploymentId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"swapContractDeploymentId\":{\"type\":\"string\"}}}");
    }
*/


    @Test()
    public void retrieveDeploymentInformationTest694() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/deployments/{id}", "retrieveDeploymentInformation", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"omnibusTBEAddress\":{\"type\":\"string\"},\"contractAddress\":{\"type\":\"string\"},\"started\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"lastMinedBlockNumber\":{\"type\":\"integer\"}}},\"redemptionAddress\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void investorBalanceTest467() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/balance", "investorBalance", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"string\"}");
    }

/*

    @Test()
    public void internalTBETransferTest622() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/internal_tbe_transfer", "internalTBETransfer", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void executeTransactionTest457() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/transactions", "executeTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionId\":{\"type\":\"string\"}}}");
    }
*/


    @Test()
    public void providerDetailsTest656() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/details", "providerDetails", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"description\":{\"type\":\"string\"},\"underlying\":{\"type\":\"string\"},\"network\":{\"type\":\"string\"}}}");
    }

/*

    @Test()
    public void addWalletTest547() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets", "addWallet", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }

*/

    @Test()
    public void getTokenDescriptionTest581() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/details", "getTokenDescription", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"symbol\":{\"type\":\"string\"},\"isPaused\":{\"type\":\"boolean\"},\"totalWallets\":{\"type\":\"integer\"},\"totalIssued\":{\"type\":\"string\"},\"decimals\":{\"type\":\"integer\"},\"name\":{\"type\":\"string\"}}}");
    }

/*

    @Test()
    public void bulkTBEIssuanceTest368() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens", "bulkTBEIssuance", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void bulkTBEBurnTest767() {
        testRequest(Method.DELETE, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens", "bulkTBEBurn", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void retrieveTheNonceOfAMultisigWalletTest580() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{address}/nonce", "retrieveTheNonceOfAMultisigWallet", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"nonce\":{\"description\":\"Multisig wallet nonce\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getGasPriceTest207() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/transactions/gas_price", "getGasPrice", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"cryptoCurrencySymbol\":{\"type\":\"string\"},\"gasPriceUnit\":{\"type\":\"string\"},\"gasPrice\":{\"type\":\"string\"}}}");
    }

/*

    @Test()
    public void retryADeploymentTest979() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/deployments/{id}/retry", "retryADeployment", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"multiSigWalletDeploymentId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"swapContractDeploymentId\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void bulkTBETransferTest30() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens/transfer", "bulkTBETransfer", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void approveTest200() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/allowance", "approve", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void addLockTest886() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}", "addLock", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getLocksTest210() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}", "getLocks", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"reason\":{\"type\":\"string\"},\"releaseTime\":{\"type\":\"integer\"},\"reasonCode\":{\"type\":\"integer\"},\"value\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"}},\"required\":[\"value\"]}}");
    }

/*

    @Test()
    public void decodeTransactionTest627() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/transactions/decode", "decodeTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}");
    }
*/


    @Test()
    public void retrieveAllContractsGeneralDataTest364() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/contracts_general_data", "retrieveAllContractsGeneralData", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"address\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"version\":{\"type\":[\"string\",\"null\"]},\"dependencies\":{\"type\":[\"array\",\"null\"],\"items\":{\"type\":\"object\",\"properties\":{\"contractAddress\":{\"type\":\"string\"},\"contractName\":{\"type\":\"string\"}}}}}}}");
    }

/*

    @Test()
    public void removeLockTest411() {
        testRequest(Method.DELETE, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}/{lockIndex}", "removeLock", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void reallocateTokensTest665() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens/reallocation", "reallocateTokens", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void setActiveComplianceRulesTest868() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_rules", "setActiveComplianceRules", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getActiveComplianceRulesTest396() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_rules", "getActiveComplianceRules", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"authorizedSecurities\":{\"type\":\"string\"},\"forceFullTransfer\":{\"type\":\"boolean\"},\"forceAccredited\":{\"type\":\"boolean\"},\"nonUSLockPeriod\":{\"type\":\"integer\"},\"nonAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"maxUSInvestorsPercentage\":{\"type\":\"integer\"},\"forceAccreditedUS\":{\"type\":\"boolean\"},\"usAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"worldWideForceFullTransfer\":{\"type\":\"boolean\"},\"minEUTokens\":{\"type\":\"string\"},\"usLockPeriod\":{\"type\":\"integer\"},\"jpInvestorsLimit\":{\"type\":\"integer\"},\"blockFlowbackEndTime\":{\"type\":\"integer\"},\"totalInvestorsLimit\":{\"type\":\"integer\"},\"euRetailInvestorsLimit\":{\"type\":\"integer\"},\"usInvestorsLimit\":{\"type\":\"integer\"},\"minimumHoldingsPerInvestor\":{\"type\":\"string\"},\"maximumHoldingsPerInvestor\":{\"type\":\"string\"},\"minimumTotalInvestors\":{\"type\":\"integer\"},\"minUSTokens\":{\"type\":\"string\"}}}");
    }

/*

    @Test()
    public void createANewDeploymentTaskTest933() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/deployments", "createANewDeploymentTask", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"multiSigWalletDeploymentId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"swapContractDeploymentId\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void cancelTransactionTest454() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/transactions/{id}/cancel", "cancelTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getHealthCheckTest143() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/health-check", "getHealthCheck", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"OK\",\"ALARM\"]}}}");
    }


    @Test()
    public void getAddressBalanceTest939() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/addresses/{address}/balance", "getAddressBalance", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"balance\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getActiveComplianceCountersTest273() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_counters", "getActiveComplianceCounters", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"usAccreditedInvestorsCount\":{\"type\":\"integer\"},\"euRetailInvestorsCounts\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"countryCode\":{\"type\":\"string\"},\"count\":{\"type\":\"integer\"}}}},\"accreditedInvestorsCount\":{\"type\":\"integer\"},\"jpInvestorsCount\":{\"type\":\"integer\"},\"totalInvestorsCount\":{\"type\":\"integer\"},\"usInvestorsCount\":{\"type\":\"integer\"}}}");
    }

/*

    @Test()
    public void setRoleForWalletTest240() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/role/{walletId}", "setRoleForWallet", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getRoleForWalletTest0() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/role/{walletId}", "getRoleForWallet", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"string\"}");
    }


    @Test()
    public void getTransactionsTest318() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/transactions", "getTransactions", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void providerInfoTest5() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/info", "providerInfo", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"deploymentsStrategy\":{\"type\":\"string\",\"enum\":[\"RC\",\"FD\"]},\"rpc\":{\"type\":\"string\"},\"chainId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"networkId\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void isAddressTest640() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/addresses/{address}/check", "isAddress", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"boolean\"}");
    }

/*

    @Test()
    public void mintTest617() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "mint", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void totalSupplyTest576() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "totalSupply", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"string\"}");
    }

/*

    @Test()
    public void burnTest781() {
        testRequest(Method.DELETE, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "burn", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getHolderByWalletTest73() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets/{walletId}", "getHolderByWallet", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"minItems\":1,\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}");
    }

/*

    @Test()
    public void removeWalletTest893() {
        testRequest(Method.DELETE, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets/{walletId}", "removeWallet", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void createHolderSwapTest952() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/swap", "createHolderSwap", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getSwapContractTest867() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/swap", "getSwapContract", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"description\":\"Contract Address\",\"type\":\"object\",\"properties\":{\"address\":{\"description\":\"The contract address\",\"type\":\"string\"}}}");
    }


    @Test()
    public void enabledTest361() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{account}/enabled", "enabled", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"isEnabled\":{\"type\":\"boolean\"}}}");
    }

/*

    @Test()
    public void deployMultisigWalletTest646() {
        testRequest(Method.POST, "https://avalanche-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets", "deployMultisigWallet", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"deploymentId\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}");
    }

*/

    @Test()
    public void allowanceTest319() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/allowance/{spender}", "allowance", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"string\"}");
    }


    @Test()
    public void getLatestBlockNumberTest322() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/latest_block_number", "getLatestBlockNumber", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"blockNumber\":{\"type\":\"integer\"}}}");
    }

/*

    @Test()
    public void seizeTest461() {
        testRequest(Method.PUT, "https://avalanche-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/seize", "seize", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getTransactionInformationTest603() {
        testRequest(Method.GET, "https://avalanche-cds.internal.{environment}.securitize.io/v1/transactions/{id}", "getTransactionInformation", LoginAs.OPERATOR, "ST/contracts-data-service/avalanche", "{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}");
    }




}

