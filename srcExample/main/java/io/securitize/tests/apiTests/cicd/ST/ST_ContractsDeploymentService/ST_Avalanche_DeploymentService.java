package io.securitize.tests.apiTests.cicd.ST.ST_ContractsDeploymentService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_Avalanche_DeploymentService extends BaseApiTest {

    @Test()
    public void getDeploymentLogsTest763() {
        testRequest(Method.GET, "https://avalanche-deployment-service.internal.{environment}.securitize.io/v1/deployments/{id}/deployment-logs", "getDeploymentLogs", LoginAs.NONE, "ST/contracts-deployment-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"finalDate\":{\"description\":\"Final date in Millis. new Date().getTime()\",\"type\":\"number\",\"example\":1676063928403},\"gasUsed\":{\"description\":\"Gas used\",\"type\":\"string\",\"example\":\"700000\"},\"stepTitle\":{\"description\":\"Title of executed step\",\"type\":\"string\",\"example\":\"Deploy libraries\"},\"contractAddress\":{\"description\":\"Contract Address\",\"type\":\"string\",\"example\":\"0x1b3a8849a7e0f7786403b2490f387f93dc5d12bc\"},\"step\":{\"description\":\"Number of executed step\",\"type\":\"number\",\"example\":5},\"from\":{\"description\":\"Wallet who signed the transaction\",\"type\":\"string\",\"example\":\"0xcc3a8849a7e0f7786403b2490f387f93dc5d12ca\"},\"txHash\":{\"description\":\"Transaction Hash\",\"type\":\"string\",\"example\":\"0xa440bd1011ce2e49b2f295c62bf7b4cf764450b5b11c9be0d029b9f06994da60\"},\"initialDate\":{\"description\":\"Start date in Millis. new Date().getTime()\",\"type\":\"number\",\"example\":1676063928203},\"deploymentLogs\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"example\":\"Deploying DSToken Proxy Contract\"}},\"retry\":{\"description\":\"Retry number\",\"type\":\"number\",\"example\":1},\"status\":{\"default\":\"success\",\"description\":\"Status of deployment step\",\"type\":\"string\",\"enum\":[\"success\",\"failure\",\"skipped\"]}}}}}}}");
    }


    @Test()
    public void getInfoTest691() {
        testRequest(Method.GET, "https://avalanche-deployment-service.internal.{environment}.securitize.io/v1/info", "getInfo", LoginAs.NONE, "ST/contracts-deployment-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"deploymentsStrategy\":{\"type\":\"string\",\"enum\":[\"RC\",\"FD\"]}}}}}}");
    }


    @Test()
    public void getDeployerWalletTest954() {
        testRequest(Method.GET, "https://avalanche-deployment-service.internal.{environment}.securitize.io/v1/deployer-wallet", "getDeployerWallet", LoginAs.NONE, "ST/contracts-deployment-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"unit\":{\"type\":\"string\",\"example\":\"wei\"},\"address\":{\"type\":\"string\",\"example\":\"0x88435610e9EeBe656eDE516f265d274B1c1e666E\"},\"balance\":{\"type\":\"string\",\"example\":\"100005000000\"}}}}}}");
    }


    @Test()
    public void getDeploymentInfoTest429() {
        testRequest(Method.GET, "https://avalanche-deployment-service.internal.{environment}.securitize.io/v1/deployments/{id}", "getDeploymentInfo", LoginAs.NONE, "ST/contracts-deployment-service/avalanche", "{\"description\":\"Deployment detail\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Deployment detail\",\"type\":\"object\",\"properties\":{\"tokenSymbol\":{\"type\":\"string\"},\"tokenName\":{\"type\":\"string\"},\"swapContractAddress\":{\"type\":\"string\"},\"started\":{\"type\":\"integer\"},\"contracts\":{\"type\":\"array\",\"items\":{\"description\":\"Return a contract deployment\",\"type\":\"object\",\"properties\":{\"bytecode\":{\"type\":\"string\"},\"address\":{\"type\":\"string\"},\"deploymentId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"abi\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"deployedBlockNumber\":{\"type\":\"integer\"}}}},\"redemptionAddress\":{\"type\":\"string\"},\"tokenAddress\":{\"type\":\"string\"},\"omnibusTBEAddress\":{\"type\":\"string\"},\"tokenDecimals\":{\"type\":\"integer\"},\"swapId\":{\"type\":\"string\"},\"lastMinedBlockNumber\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}}}}");
    }


    @Test()
    public void getMultisigWalletDeploymentInfoTest698() {
        testRequest(Method.GET, "https://avalanche-deployment-service.internal.{environment}.securitize.io/v1/deployments/{id}/multisig_wallet/{multisigWalletDeploymentId}", "getMultisigWalletDeploymentInfo", LoginAs.NONE, "ST/contracts-deployment-service/avalanche", "{\"description\":\"Retrieve multisig wallet deployment information\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Retrieve multisig wallet deployment information\",\"type\":\"object\",\"properties\":{\"deploymentId\":{\"description\":\"The deployment id\",\"type\":\"string\"},\"id\":{\"description\":\"The multisig wallet deployment id\",\"type\":\"string\"},\"walletAddress\":{\"description\":\"Multisig wallet address\",\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}}}}");
    }


    @Test()
    public void getSwapContractStatusTest112() {
        testRequest(Method.GET, "https://avalanche-deployment-service.internal.{environment}.securitize.io/v1/swap-contracts/{swapContractId}", "getSwapContractStatus", LoginAs.NONE, "ST/contracts-deployment-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"deploymentStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"swapContractAddress\":{\"nullable\":true,\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getDeploymentStepLogsTest514() {
        testRequest(Method.GET, "https://avalanche-deployment-service.internal.{environment}.securitize.io/v1/deployments/{id}/deployment-logs/steps/{step}", "getDeploymentStepLogs", LoginAs.NONE, "ST/contracts-deployment-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"finalDate\":{\"description\":\"Final date in Millis. new Date().getTime()\",\"type\":\"number\",\"example\":1676063928403},\"gasUsed\":{\"description\":\"Gas used\",\"type\":\"string\",\"example\":\"700000\"},\"stepTitle\":{\"description\":\"Title of executed step\",\"type\":\"string\",\"example\":\"Deploy libraries\"},\"contractAddress\":{\"description\":\"Contract Address\",\"type\":\"string\",\"example\":\"0x1b3a8849a7e0f7786403b2490f387f93dc5d12bc\"},\"step\":{\"description\":\"Number of executed step\",\"type\":\"number\",\"example\":5},\"from\":{\"description\":\"Wallet who signed the transaction\",\"type\":\"string\",\"example\":\"0xcc3a8849a7e0f7786403b2490f387f93dc5d12ca\"},\"txHash\":{\"description\":\"Transaction Hash\",\"type\":\"string\",\"example\":\"0xa440bd1011ce2e49b2f295c62bf7b4cf764450b5b11c9be0d029b9f06994da60\"},\"initialDate\":{\"description\":\"Start date in Millis. new Date().getTime()\",\"type\":\"number\",\"example\":1676063928203},\"deploymentLogs\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"example\":\"Deploying DSToken Proxy Contract\"}},\"retry\":{\"description\":\"Retry number\",\"type\":\"number\",\"example\":1},\"status\":{\"default\":\"success\",\"description\":\"Status of deployment step\",\"type\":\"string\",\"enum\":[\"success\",\"failure\",\"skipped\"]}}}}}}}");
    }


    @Test()
    public void getDeploymentInfoFromTokenAddressTest418() {
        testRequest(Method.GET, "https://avalanche-deployment-service.internal.{environment}.securitize.io/v1/deployments/token/{address}", "getDeploymentInfoFromTokenAddress", LoginAs.NONE, "ST/contracts-deployment-service/avalanche", "{\"description\":\"Deployment detail\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Deployment detail\",\"type\":\"object\",\"properties\":{\"tokenSymbol\":{\"type\":\"string\"},\"tokenDecimals\":{\"type\":\"integer\"},\"tokenName\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"lockManagerType\":{\"type\":\"string\",\"enum\":[\"wallet\",\"investor\",\"partitioned\"]},\"tokenType\":{\"type\":\"string\",\"enum\":[\"standard\",\"partitioned\"]},\"complianceType\":{\"type\":\"string\",\"enum\":[\"regulated\",\"notRegulated\",\"whitelisted\",\"partitioned\"]}}}}}}");
    }


    @Test()
    public void getCheckHealthTest979() {
        testRequest(Method.GET, "https://avalanche-deployment-service.internal.{environment}.securitize.io/v1/health-check", "getCheckHealth", LoginAs.NONE, "ST/contracts-deployment-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"ok\",\"alarm\"]}}}}}}");
    }




}

