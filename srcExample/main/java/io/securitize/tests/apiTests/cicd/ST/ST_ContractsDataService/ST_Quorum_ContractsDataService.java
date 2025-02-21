package io.securitize.tests.apiTests.cicd.ST.ST_ContractsDataService;

import io.restassured.http.*;
import io.securitize.infra.api.apicodegen.*;
import org.testng.annotations.*;

public class ST_Quorum_ContractsDataService extends BaseApiTest {

    //    @Test()
//    public void createAccessTokensTest783() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/access_tokens", "createAccessTokens", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"secretAccessToken\":{\"type\":\"string\"},\"accessToken\":{\"type\":\"string\"}}}");
//    }

        @Test()
    public void getAddressBalanceTest284() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/addresses/{address}/balance", "getAddressBalance", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"balance\":{\"type\":\"string\"}}}");
    }

    @Test()
    public void isAddressTest460() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/addresses/{address}/check", "isAddress", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"boolean\"}");
    }

    //    @Test()
//    public void updateAttributeTest231() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "updateAttribute", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getAttributeTest351() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "getAttribute", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}");
    }

//    @Test()
//    public void deleteAttributeTest194() {
//        testRequest(Method.DELETE, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "deleteAttribute", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    //    @Test()
//    public void resetCounterTest436() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/counters/reset", "resetCounter", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"additionalProperties\":false,\"type\":\"object\"}");
//    }

    //    @Test()
//    public void deployDeploymentTest628() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/deployments/{id}", "deployDeployment", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"multiSigWalletDeploymentId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}");
//    }

    @Test()
    public void getDeploymentTest971() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/deployments/{id}", "getDeployment", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"omnibusTBEAddress\":{\"type\":\"string\"},\"contractAddress\":{\"type\":\"string\"},\"started\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"lastMinedBlockNumber\":{\"type\":\"integer\"}}},\"status\":{\"type\":\"string\"}}}");
    }

//    @Test()
//    public void retryDeploymentTest451() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/deployments/{id}/retry", "retryDeployment", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"multiSigWalletDeploymentId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}");
//    }

//    @Test()
//    public void createNewDeploymentTaskTest412() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/deployments", "createNewDeploymentTask", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"multiSigWalletDeploymentId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}");
//    }

    @Test()
    public void getAddressLinkTest691() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/explorer/{address}/link", "getAddressLink", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"url\":{\"type\":\"string\"}}}");
    }

    @Test()
    public void getHolderTest396() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "getHolder", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"minItems\":1,\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}");
    }

//    @Test()
//    public void deleteHolderTest167() {
//        testRequest(Method.DELETE, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/holders/{holderId}", "deleteHolder", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void updateHolderTest385() {
//        testRequest(Method.PUT, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/holders/{holderId}", "updateHolder", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getInvestorBalanceTest475() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/balance", "getInvestorBalance", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"string\"}");
    }

//    @Test()
//    public void createHolderTest747() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/holders", "createHolder", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getMultisigWalletTest858() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{multisigWalletDeploymentId}", "getMultisigWallet", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"deploymentId\":{\"description\":\"The deployment id\",\"type\":\"string\"},\"id\":{\"description\":\"The multisig wallet deployment id\",\"type\":\"string\"},\"walletAddress\":{\"description\":\"Multisig wallet address\",\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}");
    }

//    @Test()
//    public void createTransactionTest5() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/deployments/{id}/multisig_wallets/{address}/transactions", "createTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getWalletNonceTest656() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{address}/nonce", "getWalletNonce", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"nonce\":{\"description\":\"Multisig wallet nonce\",\"type\":\"string\"}}}");
    }

//    @Test()
//    public void deployMultisigWalletTest533() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/deployments/{id}/multisig_wallets", "deployMultisigWallet", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"deploymentId\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}");
//    }

        @Test()
    public void providerDetailsTest726() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/details", "providerDetails", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"description\":{\"type\":\"string\"},\"underlying\":{\"type\":\"string\"},\"network\":{\"type\":\"string\"}}}");
    }

    @Test()
    public void getHealthCheckTest487() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/health-check", "getHealthCheck", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"ok\",\"alarm\"]}}}");
    }

    @Test()
    public void providerInfoTest222() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/info", "providerInfo", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"rpc\":{\"type\":\"string\"},\"chainId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"networkId\":{\"type\":\"string\"}}}");
    }

    //    @Test()
//    public void bulkTBETransferTest293() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/omnibus/tokens/transfer", "bulkTBETransfer", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void approveTest445() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/tokens/{holder}/allowance", "approve", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void addLockTest413() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/locks/{holderId}", "addLock", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getLocksTest211() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}", "getLocks", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"reason\":{\"type\":\"string\"},\"releaseTime\":{\"type\":\"integer\"},\"reasonCode\":{\"type\":\"integer\"},\"value\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"}},\"required\":[\"value\"]}}");
    }

    @Test()
    public void getContractsGeneralDataTest203() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/contracts_general_data", "getContractsGeneralData", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"address\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"version\":{\"type\":[\"string\",\"null\"]},\"dependencies\":{\"type\":[\"array\",\"null\"],\"items\":{\"type\":\"object\",\"properties\":{\"contractAddress\":{\"type\":\"string\"},\"contractName\":{\"type\":\"string\"}}}}}}}");
    }

//    @Test()
//    public void removeLockTest454() {
//        testRequest(Method.DELETE, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/locks/{holderId}/{lockIndex}", "removeLock", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void setActiveComplianceRulesTest405() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/compliance_rules", "setActiveComplianceRules", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getActiveComplianceRulesTest646() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_rules", "getActiveComplianceRules", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"authorizedSecurities\":{\"type\":\"string\"},\"forceFullTransfer\":{\"type\":\"boolean\"},\"forceAccredited\":{\"type\":\"boolean\"},\"nonUSLockPeriod\":{\"type\":\"integer\"},\"nonAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"maxUSInvestorsPercentage\":{\"type\":\"integer\"},\"forceAccreditedUS\":{\"type\":\"boolean\"},\"usAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"worldWideForceFullTransfer\":{\"type\":\"boolean\"},\"minEUTokens\":{\"type\":\"string\"},\"usLockPeriod\":{\"type\":\"integer\"},\"jpInvestorsLimit\":{\"type\":\"integer\"},\"blockFlowbackEndTime\":{\"type\":\"integer\"},\"totalInvestorsLimit\":{\"type\":\"integer\"},\"euRetailInvestorsLimit\":{\"type\":\"integer\"},\"usInvestorsLimit\":{\"type\":\"integer\"},\"minimumHoldingsPerInvestor\":{\"type\":\"string\"},\"maximumHoldingsPerInvestor\":{\"type\":\"string\"},\"minimumTotalInvestors\":{\"type\":\"integer\"},\"minUSTokens\":{\"type\":\"string\"}}}");
    }

    @Test()
    public void getActiveComplianceCountersTest614() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_counters", "getActiveComplianceCounters", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"usAccreditedInvestorsCount\":{\"type\":\"integer\"},\"euRetailInvestorsCounts\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"countryCode\":{\"type\":\"string\"},\"count\":{\"type\":\"integer\"}}}},\"accreditedInvestorsCount\":{\"type\":\"integer\"},\"jpInvestorsCount\":{\"type\":\"integer\"},\"totalInvestorsCount\":{\"type\":\"integer\"},\"usInvestorsCount\":{\"type\":\"integer\"}}}");
    }

//    @Test()
//    public void setRoleForWalletTest470() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/role/{walletId}", "setRoleForWallet", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getRoleForWalletTest50() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/role/{walletId}", "getRoleForWallet", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"string\"}");
    }

    @Test()
    public void getTransactionsTest56() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/transactions", "getTransactions", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}}");
    }

//    @Test()
//    public void mintTest89() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/tokens", "mint", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void totalSupplyTest826() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "totalSupply", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"string\"}");
    }

//    @Test()
//    public void burnTest356() {
//        testRequest(Method.DELETE, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/tokens", "burn", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getHolderByWalletTest113() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets/{walletId}", "getHolderByWallet", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"minItems\":1,\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}");
    }

//    @Test()
//    public void removeWalletTest64() {
//        testRequest(Method.DELETE, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/wallets/{walletId}", "removeWallet", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void enabledTest590() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{account}/enabled", "enabled", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"isEnabled\":{\"type\":\"boolean\"}}}");
    }

    @Test()
    public void allowanceTest314() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/allowance/{spender}", "allowance", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"string\"}");
    }

    @Test()
    public void getLatestBlockNumberTest762() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/latest_block_number", "getLatestBlockNumber", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"blockNumber\":{\"type\":\"integer\"}}}");
    }

//    @Test()
//    public void seizeTest717() {
//        testRequest(Method.PUT, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/tokens/{holder}/seize", "seize", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void addInvestorLockTest933() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/locks/{holderId}/investor_lock", "addInvestorLock", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getInvestorLockedTest109() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}/investor_lock", "getInvestorLocked", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"locked\":{\"type\":\"boolean\"}}}");
    }

//    @Test()
//    public void adjustTBECountersTest623() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/omnibus/counters", "adjustTBECounters", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void setTokenPauseStateTest546() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/pause", "setTokenPauseState", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void setCountryStatusTest195() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/countries/{isocode}", "setCountryStatus", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getCountryStatusTest12() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/countries/{isocode}", "getCountryStatus", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"string\",\"enum\":[\"none\",\"us\",\"eu\",\"jp\",\"forbidden\"]}");
    }

    @Test()
    public void getEventsTest51() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/events", "getEvents", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"fromBlock\":{\"type\":\"number\"},\"toBlock\":{\"type\":\"number\"},\"events\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"eventType\",\"blockNumber\"],\"properties\":{\"blockNumber\":{\"type\":\"number\"},\"blockTime\":{\"type\":\"number\"},\"id\":{\"type\":\"string\"},\"eventType\":{\"type\":\"string\",\"enum\":[\"Issue\",\"Transfer\",\"Burn\",\"Seize\",\"Approval\",\"DSRegistryServiceInvestorAdded\",\"DSRegistryServiceInvestorRemoved\",\"DSRegistryServiceInvestorCountryChanged\",\"DSRegistryServiceInvestorAttributeChanged\",\"DSRegistryServiceWalletAdded\",\"DSRegistryServiceWalletRemoved\",\"DSTrustServiceRoleAdded\",\"DSTrustServiceRoleRemoved\",\"HolderLocked\",\"HolderUnlocked\",\"OmnibusTBEOperation\"]},\"parameters\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void balanceOfTest57() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}", "balanceOf", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"string\"}");
    }

//    @Test()
//    public void transferTest462() {
//        testRequest(Method.PUT, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/tokens/{holder}", "transfer", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void internalTBETransferTest751() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/omnibus/internal_tbe_transfer", "internalTBETransfer", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void addWalletTest96() {
//        testRequest(Method.POST, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets", "addWallet", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getTokenDescriptionTest117() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/details", "getTokenDescription", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"symbol\":{\"type\":\"string\"},\"isPaused\":{\"type\":\"boolean\"},\"totalWallets\":{\"type\":\"integer\"},\"totalIssued\":{\"type\":\"string\"},\"decimals\":{\"type\":\"integer\"},\"name\":{\"type\":\"string\"}}}");
    }

//    @Test()
//    public void bulkTBEIssuanceTest601() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/omnibus/tokens", "bulkTBEIssuance", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void bulkTBEBurnTest485() {
//        testRequest(Method.DELETE, "http://cds.{environment}.securitize.io:3010/v1/token_contracts/{address}/omnibus/tokens", "bulkTBEBurn", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    //    @Test()
//    public void speedupTransactionTest946() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/transactions/{id}/speedup", "speedupTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

//    @Test()
//    public void executeTransactionTest208() {
//        testRequest(Method.POST, "http://quorum-cds.internal.{environment}.securitize.io/v1/transactions", "executeTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionId\":{\"type\":\"string\"}}}");
//    }

    @Test()
    public void getGasPriceTest677() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/transactions/gas_price", "getGasPrice", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"cryptoCurrencySymbol\":{\"type\":\"string\"},\"gasPriceUnit\":{\"type\":\"string\"},\"gasPrice\":{\"type\":\"string\"}}}");
    }

//    @Test()
//    public void decodeTransactionTest930() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/transactions/decode", "decodeTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}");
//    }

//    @Test()
//    public void cancelTransactionTest268() {
//        testRequest(Method.POST, "http://cds.{environment}.securitize.io:3010/v1/transactions/{id}/cancel", "cancelTransaction", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}");
//    }

    @Test()
    public void getTransactionInformationTest555() {
        testRequest(Method.GET, "http://quorum-cds.internal.{environment}.securitize.io/v1/transactions/{id}", "getTransactionInformation", LoginAs.OPERATOR, "ST/contracts-data-service/quorum", "{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}");
    }
}