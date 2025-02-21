package io.securitize.tests.apiTests.cicd.ST.ST_ContractsDataService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_Goerli_ContractDataService extends BaseApiTest {
/*

    @Test()
    public void updateAttributeTest59() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "updateAttribute", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getAttributeTest465() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "getAttribute", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}");
    }

/*

    @Test()
    public void deleteAttributeTest899() {
        testRequest(Method.DELETE, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "deleteAttribute", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void resetCounterTest694() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/counters/reset", "resetCounter", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"additionalProperties\":false,\"type\":\"object\"}");
    }
*/


    @Test()
    public void getHolderTest20() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "getHolder", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"minItems\":1,\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}");
    }

/*

    @Test()
    public void deleteHolderTest510() {
        testRequest(Method.DELETE, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "deleteHolder", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void updateHolderTest67() {
        testRequest(Method.PUT, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "updateHolder", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void createAccessTokensTest642() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/access_tokens", "createAccessTokens", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"secretAccessToken\":{\"type\":\"string\"},\"accessToken\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void addInvestorLockTest140() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}/investor_lock", "addInvestorLock", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getInvestorLockedTest949() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}/investor_lock", "getInvestorLocked", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"locked\":{\"type\":\"boolean\"}}}");
    }

/*

    @Test()
    public void adjustTBECountersTest878() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/counters", "adjustTBECounters", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void setTokenPauseStateTest58() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/pause", "setTokenPauseState", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void setCountryStatusTest297() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/countries/{isocode}", "setCountryStatus", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }

*/

    @Test()
    public void getCountryStatusTest501() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/countries/{isocode}", "getCountryStatus", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"string\",\"enum\":[\"none\",\"us\",\"eu\",\"jp\",\"forbidden\"]}");
    }


    @Test()
    public void retrieveMultisigWalletDeploymentTransactionTest214() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{multisigWalletDeploymentId}", "retrieveMultisigWalletDeploymentTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"deploymentId\":{\"description\":\"The deployment id\",\"type\":\"string\"},\"id\":{\"description\":\"The multisig wallet deployment id\",\"type\":\"string\"},\"walletAddress\":{\"description\":\"Multisig wallet address\",\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}");
    }

/*

    @Test()
    public void createTransactionTest347() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{address}/transactions", "createTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void retrieveSwapContractStatusTest991() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/swap-contracts/{swapContractId}", "retrieveSwapContractStatus", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"deploymentStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"swapContractAddress\":{\"type\":[\"string\",\"null\"]}}}");
    }

/*

    @Test()
    public void speedupTransactionTest356() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/transactions/{id}/speedup", "speedupTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }

*/

    @Test()
    public void getAddressLinkTest828() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/explorer/{address}/link", "getAddressLink", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"url\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getEventsTest808() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/events", "getEvents", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"fromBlock\":{\"type\":\"number\"},\"toBlock\":{\"type\":\"number\"},\"events\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"eventType\",\"blockNumber\"],\"properties\":{\"blockNumber\":{\"type\":\"number\"},\"blockTime\":{\"type\":\"number\"},\"id\":{\"type\":\"string\"},\"eventType\":{\"type\":\"string\",\"enum\":[\"Issue\",\"Transfer\",\"Burn\",\"Seize\",\"Approval\",\"DSRegistryServiceInvestorAdded\",\"DSRegistryServiceInvestorRemoved\",\"DSRegistryServiceInvestorCountryChanged\",\"DSRegistryServiceInvestorAttributeChanged\",\"DSRegistryServiceWalletAdded\",\"DSRegistryServiceWalletRemoved\",\"DSTrustServiceRoleAdded\",\"DSTrustServiceRoleRemoved\",\"HolderLocked\",\"HolderUnlocked\",\"InvestorFullyUnlocked\",\"InvestorFullyLocked\"]},\"parameters\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
    }

/*

    @Test()
    public void listenTokenEventsTest72() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/events/listen", "listenTokenEvents", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"listening\":{\"type\":\"boolean\"}}}");
    }
*/


    @Test()
    public void balanceOfTest368() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}", "balanceOf", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"string\"}");
    }

/*

    @Test()
    public void transferTest343() {
        testRequest(Method.PUT, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}", "transfer", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void createHolderTest777() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders", "createHolder", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void deployAnAlreadyExistingDeploymentByIdTest719() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/deployments/{id}", "deployAnAlreadyExistingDeploymentById", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"multiSigWalletDeploymentId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"swapContractDeploymentId\":{\"type\":\"string\"}}}");
    }
*/


    @Test()
    public void retrieveDeploymentInformationTest709() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/deployments/{id}", "retrieveDeploymentInformation", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"omnibusTBEAddress\":{\"type\":\"string\"},\"contractAddress\":{\"type\":\"string\"},\"started\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"lastMinedBlockNumber\":{\"type\":\"integer\"}}},\"redemptionAddress\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void investorBalanceTest16() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/balance", "investorBalance", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"string\"}");
    }

/*

    @Test()
    public void internalTBETransferTest840() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/internal_tbe_transfer", "internalTBETransfer", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void executeTransactionTest81() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/transactions", "executeTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionId\":{\"type\":\"string\"}}}");
    }
*/


    @Test()
    public void providerDetailsTest658() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/details", "providerDetails", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"description\":{\"type\":\"string\"},\"underlying\":{\"type\":\"string\"},\"network\":{\"type\":\"string\"}}}");
    }

/*

    @Test()
    public void addWalletTest380() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets", "addWallet", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getTokenDescriptionTest299() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/details", "getTokenDescription", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"symbol\":{\"type\":\"string\"},\"isPaused\":{\"type\":\"boolean\"},\"totalWallets\":{\"type\":\"integer\"},\"totalIssued\":{\"type\":\"string\"},\"decimals\":{\"type\":\"integer\"},\"name\":{\"type\":\"string\"}}}");
    }

/*

    @Test()
    public void bulkTBEIssuanceTest933() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens", "bulkTBEIssuance", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void bulkTBEBurnTest950() {
        testRequest(Method.DELETE, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens", "bulkTBEBurn", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void retrieveTheNonceOfAMultisigWalletTest376() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{address}/nonce", "retrieveTheNonceOfAMultisigWallet", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"nonce\":{\"description\":\"Multisig wallet nonce\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getGasPriceTest80() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/transactions/gas_price", "getGasPrice", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"cryptoCurrencySymbol\":{\"type\":\"string\"},\"gasPriceUnit\":{\"type\":\"string\"},\"gasPrice\":{\"type\":\"string\"}}}");
    }

/*

    @Test()
    public void retryADeploymentTest309() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/deployments/{id}/retry", "retryADeployment", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"multiSigWalletDeploymentId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"swapContractDeploymentId\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void bulkTBETransferTest647() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens/transfer", "bulkTBETransfer", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void approveTest565() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/allowance", "approve", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void addLockTest823() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}", "addLock", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getLocksTest459() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}", "getLocks", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"reason\":{\"type\":\"string\"},\"releaseTime\":{\"type\":\"integer\"},\"reasonCode\":{\"type\":\"integer\"},\"value\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"}},\"required\":[\"value\"]}}");
    }

/*

    @Test()
    public void decodeTransactionTest112() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/transactions/decode", "decodeTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}");
    }
*/


    @Test()
    public void retrieveAllContractsGeneralDataTest784() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/contracts_general_data", "retrieveAllContractsGeneralData", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"address\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"version\":{\"type\":[\"string\",\"null\"]},\"dependencies\":{\"type\":[\"array\",\"null\"],\"items\":{\"type\":\"object\",\"properties\":{\"contractAddress\":{\"type\":\"string\"},\"contractName\":{\"type\":\"string\"}}}}}}}");
    }

/*

    @Test()
    public void removeLockTest310() {
        testRequest(Method.DELETE, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}/{lockIndex}", "removeLock", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void reallocateTokensTest997() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/omnibus/tokens/reallocation", "reallocateTokens", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void setActiveComplianceRulesTest111() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_rules", "setActiveComplianceRules", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getActiveComplianceRulesTest250() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_rules", "getActiveComplianceRules", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"authorizedSecurities\":{\"type\":\"string\"},\"forceFullTransfer\":{\"type\":\"boolean\"},\"forceAccredited\":{\"type\":\"boolean\"},\"nonUSLockPeriod\":{\"type\":\"integer\"},\"nonAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"maxUSInvestorsPercentage\":{\"type\":\"integer\"},\"forceAccreditedUS\":{\"type\":\"boolean\"},\"usAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"worldWideForceFullTransfer\":{\"type\":\"boolean\"},\"minEUTokens\":{\"type\":\"string\"},\"usLockPeriod\":{\"type\":\"integer\"},\"jpInvestorsLimit\":{\"type\":\"integer\"},\"blockFlowbackEndTime\":{\"type\":\"integer\"},\"totalInvestorsLimit\":{\"type\":\"integer\"},\"euRetailInvestorsLimit\":{\"type\":\"integer\"},\"usInvestorsLimit\":{\"type\":\"integer\"},\"minimumHoldingsPerInvestor\":{\"type\":\"string\"},\"maximumHoldingsPerInvestor\":{\"type\":\"string\"},\"minimumTotalInvestors\":{\"type\":\"integer\"},\"minUSTokens\":{\"type\":\"string\"}}}");
    }

/*

    @Test()
    public void createANewDeploymentTaskTest808() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/deployments", "createANewDeploymentTask", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"multiSigWalletDeploymentId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"swapContractDeploymentId\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void cancelTransactionTest778() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/transactions/{id}/cancel", "cancelTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getHealthCheckTest724() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/health-check", "getHealthCheck", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"OK\",\"ALARM\"]}}}");
    }


    @Test()
    public void getAddressBalanceTest573() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/addresses/{address}/balance", "getAddressBalance", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"balance\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getActiveComplianceCountersTest665() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_counters", "getActiveComplianceCounters", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"usAccreditedInvestorsCount\":{\"type\":\"integer\"},\"euRetailInvestorsCounts\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"countryCode\":{\"type\":\"string\"},\"count\":{\"type\":\"integer\"}}}},\"accreditedInvestorsCount\":{\"type\":\"integer\"},\"jpInvestorsCount\":{\"type\":\"integer\"},\"totalInvestorsCount\":{\"type\":\"integer\"},\"usInvestorsCount\":{\"type\":\"integer\"}}}");
    }

/*

    @Test()
    public void setRoleForWalletTest186() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/role/{walletId}", "setRoleForWallet", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getRoleForWalletTest907() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/role/{walletId}", "getRoleForWallet", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"string\"}");
    }


    @Test()
    public void getTransactionsTest681() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/transactions", "getTransactions", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void providerInfoTest369() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/info", "providerInfo", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"deploymentsStrategy\":{\"type\":\"string\",\"enum\":[\"RC\",\"FD\"]},\"rpc\":{\"type\":\"string\"},\"chainId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"networkId\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void isAddressTest288() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/addresses/{address}/check", "isAddress", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"boolean\"}");
    }

/*

    @Test()
    public void mintTest124() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "mint", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void totalSupplyTest568() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "totalSupply", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"string\"}");
    }

/*

    @Test()
    public void burnTest488() {
        testRequest(Method.DELETE, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "burn", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getHolderByWalletTest465() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets/{walletId}", "getHolderByWallet", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"minItems\":1,\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}");
    }

/*

    @Test()
    public void removeWalletTest125() {
        testRequest(Method.DELETE, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets/{walletId}", "removeWallet", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void createHolderSwapTest660() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/swap", "createHolderSwap", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getSwapContractTest251() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/swap", "getSwapContract", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"description\":\"Contract Address\",\"type\":\"object\",\"properties\":{\"address\":{\"description\":\"The contract address\",\"type\":\"string\"}}}");
    }


    @Test()
    public void enabledTest619() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{account}/enabled", "enabled", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"isEnabled\":{\"type\":\"boolean\"}}}");
    }

/*

    @Test()
    public void deployMultisigWalletTest121() {
        testRequest(Method.POST, "https://goerli-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets", "deployMultisigWallet", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"deploymentId\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}");
    }
*/


    @Test()
    public void allowanceTest391() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/allowance/{spender}", "allowance", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"string\"}");
    }


    @Test()
    public void getLatestBlockNumberTest719() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/latest_block_number", "getLatestBlockNumber", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"blockNumber\":{\"type\":\"integer\"}}}");
    }

/*

    @Test()
    public void seizeTest295() {
        testRequest(Method.PUT, "https://goerli-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/seize", "seize", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }
*/


    @Test()
    public void getTransactionInformationTest969() {
        testRequest(Method.GET, "https://goerli-cds.internal.{environment}.securitize.io/v1/transactions/{id}", "getTransactionInformation", LoginAs.OPERATOR, "ST/contracts-data-service/goerli", "{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}");
    }




}

