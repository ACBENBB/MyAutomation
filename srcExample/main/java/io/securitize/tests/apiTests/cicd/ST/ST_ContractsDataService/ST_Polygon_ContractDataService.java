package io.securitize.tests.apiTests.cicd.ST.ST_ContractsDataService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_Polygon_ContractDataService extends BaseApiTest {

    @Test()
    public void getDeploymentLogsTest563() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/deployments/{id}/deployment-logs", "getDeploymentLogs", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"finalDate\":{\"description\":\"Final date in Millis. new Date().getTime()\",\"type\":\"number\",\"example\":1676063928403},\"gasUsed\":{\"description\":\"Gas used\",\"type\":\"string\",\"example\":\"700000\"},\"stepTitle\":{\"description\":\"Title of executed step\",\"type\":\"string\",\"example\":\"Deploy libraries\"},\"contractAddress\":{\"description\":\"Contract Address\",\"type\":\"string\",\"example\":\"0x1b3a8849a7e0f7786403b2490f387f93dc5d12bc\"},\"step\":{\"description\":\"Number of executed step\",\"type\":\"number\",\"example\":5},\"from\":{\"description\":\"Wallet who signed the transaction\",\"type\":\"string\",\"example\":\"0xcc3a8849a7e0f7786403b2490f387f93dc5d12ca\"},\"txHash\":{\"description\":\"Transaction Hash\",\"type\":\"string\",\"example\":\"0xa440bd1011ce2e49b2f295c62bf7b4cf764450b5b11c9be0d029b9f06994da60\"},\"initialDate\":{\"description\":\"Start date in Millis. new Date().getTime()\",\"type\":\"number\",\"example\":1676063928203},\"deploymentLogs\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"example\":\"Deploying DSToken Proxy Contract\"}},\"status\":{\"default\":\"success\",\"description\":\"Status of deployment step\",\"type\":\"string\",\"enum\":[\"success\",\"failure\"]}}}}");
    }


    @Test()
    public void walletTransferableTokensTest693() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets/{walletId}/transferable-tokens", "walletTransferableTokens", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"string\"}");
    }


    @Test()
    public void getAttributeTest671() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/attribute/{attributeName}", "getAttribute", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}");
    }


    @Test()
    public void getHolderTest556() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}", "getHolder", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"minItems\":1,\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}");
    }


    @Test()
    public void getInvestorLockedTest129() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}/investor_lock", "getInvestorLocked", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"locked\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getCountryStatusTest835() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/countries/{isocode}", "getCountryStatus", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"string\",\"enum\":[\"none\",\"us\",\"eu\",\"jp\",\"forbidden\"]}");
    }


    @Test()
    public void getMultisigWalletDeploymentInfoTest385() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{multisigWalletDeploymentId}", "getMultisigWalletDeploymentInfo", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"deploymentId\":{\"description\":\"The deployment id\",\"type\":\"string\"},\"id\":{\"description\":\"The multisig wallet deployment id\",\"type\":\"string\"},\"walletAddress\":{\"description\":\"Multisig wallet address\",\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}");
    }


    @Test()
    public void getSwapContractStatusTest297() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/swap-contracts/{swapContractId}", "getSwapContractStatus", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"deploymentStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"swapContractAddress\":{\"type\":[\"string\",\"null\"]}}}");
    }


    @Test()
    public void getAddressLinkTest211() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/explorer/{address}/link", "getAddressLink", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"url\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getEventsTest4() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/events", "getEvents", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"fromBlock\":{\"type\":\"number\"},\"toBlock\":{\"type\":\"number\"},\"events\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"eventType\",\"blockNumber\"],\"properties\":{\"blockNumber\":{\"type\":\"number\"},\"blockTime\":{\"type\":\"number\"},\"id\":{\"type\":\"string\"},\"eventType\":{\"type\":\"string\",\"enum\":[\"Issue\",\"Transfer\",\"Burn\",\"Seize\",\"Approval\",\"DSRegistryServiceInvestorAdded\",\"DSRegistryServiceInvestorRemoved\",\"DSRegistryServiceInvestorCountryChanged\",\"DSRegistryServiceInvestorAttributeChanged\",\"DSRegistryServiceWalletAdded\",\"DSRegistryServiceWalletRemoved\",\"DSTrustServiceRoleAdded\",\"DSTrustServiceRoleRemoved\",\"HolderLocked\",\"HolderUnlocked\",\"InvestorFullyUnlocked\",\"InvestorFullyLocked\"]},\"parameters\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getErc20InfoTest671() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/erc20/{address}/info", "getErc20Info", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"symbol\":{\"type\":\"string\"},\"totalSupply\":{\"type\":\"string\"},\"decimals\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void balanceOfTest90() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}", "balanceOf", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"string\"}");
    }


    @Test()
    public void getHealthCheckTest527() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/health", "getHealthCheck", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"ok\",\"ALARM\"]}}}");
    }


    @Test()
    public void getDeploymentInfoTest681() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/deployments/{id}", "getDeploymentInfo", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"omnibusTBEAddress\":{\"type\":\"string\"},\"contractAddress\":{\"type\":\"string\"},\"started\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"lastMinedBlockNumber\":{\"type\":\"integer\"}}},\"redemptionAddress\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void investorBalanceTest512() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/balance", "investorBalance", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"string\"}");
    }


    @Test()
    public void providerDetailsTest452() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/details", "providerDetails", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"description\":{\"type\":\"string\"},\"underlying\":{\"type\":\"string\"},\"network\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getTokenDescriptionTest332() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/details", "getTokenDescription", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"symbol\":{\"type\":\"string\"},\"isPaused\":{\"type\":\"boolean\"},\"totalWallets\":{\"type\":\"integer\"},\"totalIssued\":{\"type\":\"string\"},\"decimals\":{\"type\":\"integer\"},\"name\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getMultisigWalletNonceTest83() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallets/{address}/nonce", "getMultisigWalletNonce", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"nonce\":{\"description\":\"Multisig wallet nonce\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getGasPriceTest867() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/transactions/gas_price", "getGasPrice", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"cryptoCurrencySymbol\":{\"type\":\"string\"},\"gasPriceUnit\":{\"type\":\"string\"},\"gasPrice\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void investorTransferableTokensTest181() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/{holderId}/transferable-tokens", "investorTransferableTokens", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"string\"}");
    }


    @Test()
    public void getLocksTest960() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/locks/{holderId}", "getLocks", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"reason\":{\"type\":\"string\"},\"releaseTime\":{\"type\":\"integer\"},\"reasonCode\":{\"type\":\"integer\"},\"value\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"}},\"required\":[\"value\"]}}");
    }


    @Test()
    public void getContractGeneralDataTest295() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/contracts_general_data", "getContractGeneralData", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"address\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"version\":{\"type\":[\"string\",\"null\"]},\"dependencies\":{\"type\":[\"array\",\"null\"],\"items\":{\"type\":\"object\",\"properties\":{\"contractAddress\":{\"type\":\"string\"},\"contractName\":{\"type\":\"string\"}}}}}}}");
    }


    @Test()
    public void getErc20AllowanceTest62() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/erc20/{address}/allowance/{wallet}", "getErc20Allowance", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"allowance\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getActiveComplianceRulesTest453() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_rules", "getActiveComplianceRules", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"authorizedSecurities\":{\"type\":\"string\"},\"forceFullTransfer\":{\"type\":\"boolean\"},\"forceAccredited\":{\"type\":\"boolean\"},\"nonUSLockPeriod\":{\"type\":\"integer\"},\"nonAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"maxUSInvestorsPercentage\":{\"type\":\"integer\"},\"forceAccreditedUS\":{\"type\":\"boolean\"},\"usAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"disallowBackDating\":{\"type\":\"boolean\"},\"worldWideForceFullTransfer\":{\"type\":\"boolean\"},\"minEUTokens\":{\"type\":\"string\"},\"usLockPeriod\":{\"type\":\"integer\"},\"jpInvestorsLimit\":{\"type\":\"integer\"},\"blockFlowbackEndTime\":{\"type\":\"integer\"},\"totalInvestorsLimit\":{\"type\":\"integer\"},\"euRetailInvestorsLimit\":{\"type\":\"integer\"},\"usInvestorsLimit\":{\"type\":\"integer\"},\"minimumHoldingsPerInvestor\":{\"type\":\"string\"},\"maximumHoldingsPerInvestor\":{\"type\":\"string\"},\"minimumTotalInvestors\":{\"type\":\"integer\"},\"minUSTokens\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getErc20BalanceTest100() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/erc20/{address}/balance/{wallet}", "getErc20Balance", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"balance\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getAddressBalanceTest80() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/addresses/{address}/balance", "getAddressBalance", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"balance\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getActiveComplianceCountersTest979() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/compliance_counters", "getActiveComplianceCounters", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"usAccreditedInvestorsCount\":{\"type\":\"integer\"},\"euRetailInvestorsCounts\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"countryCode\":{\"type\":\"string\"},\"count\":{\"type\":\"integer\"}}}},\"accreditedInvestorsCount\":{\"type\":\"integer\"},\"jpInvestorsCount\":{\"type\":\"integer\"},\"totalInvestorsCount\":{\"type\":\"integer\"},\"usInvestorsCount\":{\"type\":\"integer\"}}}");
    }


    @Test()
    public void getRoleForWalletTest110() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/role/{walletId}", "getRoleForWallet", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"string\"}");
    }


    @Test()
    public void getTransactionsTest300() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/transactions", "getTransactions", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void providerInfoTest134() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/info", "providerInfo", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"deploymentsStrategy\":{\"type\":\"string\",\"enum\":[\"RC\",\"FD\"]},\"rpc\":{\"type\":\"string\"},\"chainId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"networkId\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getDeployerWalletTest240() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/deployer-wallet", "getDeployerWallet", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"unit\":{\"type\":\"string\",\"example\":\"wei\"},\"address\":{\"type\":\"string\",\"example\":\"0xaBc9dfEd9300775a2DeF456c7BDa65310Ad21E6\"},\"balance\":{\"type\":\"string\",\"example\":\"100000299281\"}}}");
    }


    @Test()
    public void getEventTest155() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/contract/{contractAddress}/{contractName}/topic/{topic}/event", "getEvent", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"fromBlock\":{\"type\":\"number\"},\"toBlock\":{\"type\":\"number\"},\"event\":{\"type\":\"object\",\"required\":[\"id\",\"eventType\",\"blockNumber\"],\"properties\":{\"blockNumber\":{\"type\":\"number\"},\"blockTime\":{\"type\":\"number\"},\"id\":{\"type\":\"string\"},\"eventType\":{\"type\":\"string\",\"enum\":[\"Issue\",\"Transfer\",\"Burn\",\"Seize\",\"Approval\",\"DSRegistryServiceInvestorAdded\",\"DSRegistryServiceInvestorRemoved\",\"DSRegistryServiceInvestorCountryChanged\",\"DSRegistryServiceInvestorAttributeChanged\",\"DSRegistryServiceWalletAdded\",\"DSRegistryServiceWalletRemoved\",\"DSTrustServiceRoleAdded\",\"DSTrustServiceRoleRemoved\",\"HolderLocked\",\"HolderUnlocked\",\"InvestorFullyUnlocked\",\"InvestorFullyLocked\"]},\"parameters\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionId\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void isAddressTest407() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/addresses/{address}/check", "isAddress", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"boolean\"}");
    }


    @Test()
    public void totalSupplyTest142() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens", "totalSupply", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"string\"}");
    }


    @Test()
    public void getHolderByWalletTest993() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/wallets/{walletId}", "getHolderByWallet", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"proofHash\":{\"type\":\"string\"},\"name\":{\"type\":\"string\",\"enum\":[\"kyc\",\"accredited\",\"qualified\",\"professional\"]},\"expiry\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"approved\",\"rejected\"]}},\"required\":[\"name\",\"status\"]}},\"wallets\":{\"minItems\":1,\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"id\":{\"type\":\"string\"}},\"required\":[\"id\"]}");
    }


    @Test()
    public void getSwapContractTest661() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/holders/swap", "getSwapContract", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"description\":\"Contract Address\",\"type\":\"object\",\"properties\":{\"address\":{\"description\":\"The contract address\",\"type\":\"string\"}}}");
    }


    @Test()
    public void enabledTest222() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{account}/enabled", "enabled", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"isEnabled\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void allowanceTest907() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/tokens/{holder}/allowance/{spender}", "allowance", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"string\"}");
    }


    @Test()
    public void getLatestBlockNumberTest146() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/token_contracts/{address}/latest_block_number", "getLatestBlockNumber", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"blockNumber\":{\"type\":\"integer\"}}}");
    }


    @Test()
    public void getTransactionInformationTest294() {
        testRequest(Method.GET, "https://polygon-cds.internal.{environment}.securitize.io/v1/transactions/{id}", "getTransactionInformation", LoginAs.NONE, "ST/polygon-contract-data-service", "{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"inputData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"data\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"method\":{\"type\":\"string\",\"enum\":[\"transfer\",\"other\"]},\"sender\":{\"pattern\":\"(0x)[0-9a-fA-F]+$\",\"type\":\"string\"},\"params\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"}}}}},\"resultData\":{\"additionalProperties\":true,\"type\":\"object\"},\"targetAddress\":{\"type\":\"string\"},\"block\":{\"type\":\"integer\"},\"signerAddress\":{\"type\":\"string\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\"},\"transactionTime\":{\"type\":\"integer\"},\"transactionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"unsigned\",\"signed\",\"sent\",\"error\",\"success\",\"failure\"]},\"gasPrice\":{\"type\":\"string\"}}}");
    }




}

