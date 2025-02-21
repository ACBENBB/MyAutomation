package io.securitize.tests.apiTests.cicd.ST.ST_ContractsDeploymentService;

import io.restassured.http.*;
import io.securitize.infra.api.apicodegen.*;
import org.testng.annotations.*;

public class ST_ContractsDeploymentService extends BaseApiTest {

    @Test()
    public void getCheckHealthTest297() {
        testRequest(Method.GET, "http://ds.{environment}.securitize.io/v1/health-check", "getCheckHealth", LoginAs.OPERATOR, "ST/contracts-deployment-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"ok\",\"alarm\"]}}}}}}");
    }

    @Test()
    public void getDeploymentInfoByTokenAddressTest21() {
        testRequest(Method.GET, "http://ds.{environment}.securitize.io/v1/deployments/token/{address}", "getDeploymentInfoByTokenAddress", LoginAs.OPERATOR, "ST/contracts-deployment-service", "{\"description\":\"Deployment detail\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Deployment detail\",\"type\":\"object\",\"properties\":{\"tokenSymbol\":{\"type\":\"string\"},\"tokenDecimals\":{\"type\":\"integer\"},\"tokenName\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"lockManagerType\":{\"type\":\"string\",\"enum\":[\"wallet\",\"investor\",\"partitioned\"]},\"tokenType\":{\"type\":\"string\",\"enum\":[\"standard\",\"partitioned\"]},\"complianceType\":{\"type\":\"string\",\"enum\":[\"regulated\",\"notRegulated\",\"whitelisted\",\"partitioned\"]}}}}}}");
    }

    @Test()
    public void getDeploymentsTest517() {
        testRequest(Method.GET, "http://ds.{environment}.securitize.io/v1/deployments/{id}", "getDeployments", LoginAs.OPERATOR, "ST/contracts-deployment-service", "{\"description\":\"Deployment detail\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Deployment detail\",\"type\":\"object\",\"properties\":{\"tokenAddress\":{\"type\":\"string\"},\"omnibusTBEAddress\":{\"type\":\"string\"},\"tokenSymbol\":{\"type\":\"string\"},\"tokenDecimals\":{\"type\":\"integer\"},\"tokenName\":{\"type\":\"string\"},\"lastMinedBlockNumber\":{\"type\":\"integer\"},\"started\":{\"type\":\"integer\"},\"id\":{\"type\":\"string\"},\"contracts\":{\"type\":\"array\",\"items\":{\"description\":\"Return a contract deployment\",\"type\":\"object\",\"properties\":{\"bytecode\":{\"type\":\"string\"},\"address\":{\"type\":\"string\"},\"deploymentId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"abi\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"deployedBlockNumber\":{\"type\":\"integer\"}}}},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}}}}");
    }

    @Test()
    public void getMultisigDeploymentInfoTest982() {
        testRequest(Method.GET, "http://ds.{environment}.securitize.io/v1/deployments/{id}/multisig_wallet/{multisigWalletDeploymentId}", "getMultisigDeploymentInfo", LoginAs.OPERATOR, "ST/contracts-deployment-service", "{\"description\":\"Retrieve multisig wallet deployment information\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Retrieve multisig wallet deployment information\",\"type\":\"object\",\"properties\":{\"deploymentId\":{\"description\":\"The deployment id\",\"type\":\"string\"},\"id\":{\"description\":\"The multisig wallet deployment id\",\"type\":\"string\"},\"walletAddress\":{\"description\":\"Multisig wallet address\",\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]}}}}}}");
    }

}