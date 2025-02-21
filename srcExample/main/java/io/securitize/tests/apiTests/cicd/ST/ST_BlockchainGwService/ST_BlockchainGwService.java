package io.securitize.tests.apiTests.cicd.ST.ST_BlockchainGwService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_BlockchainGwService extends BaseApiTest {

    @Test()
    public void AbstractionLayerController_getErc20InfoTest601() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/providers/{provider}/erc20/{address}/info", "AbstractionLayerController_getErc20Info", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetErc20InfoResponseDTO\"}}}}");
    }


    @Test()
    public void AbstractionLayerController_getErc20AllowanceTest636() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/providers/{provider}/erc20/{address}/allowance/{wallet}", "AbstractionLayerController_getErc20Allowance", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetErc20AllowanceResponseDTO\"}}}}");
    }


    @Test()
    public void DeploymentsManagementController_getStableCoinsByProviderTest571() {
        testRequest(Method.GET, "https://blockchain-gw.internal..{environment}.securitize.io/v1/providers/{providerId}/stable-coins", "DeploymentsManagementController_getStableCoinsByProvider", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/StableCoinDto\"}}}}");
    }


    @Test()
    public void AttestationController_getPartnerTest743() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/partners/{partnerId}", "AttestationController_getPartner", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/PartnerDto\"}}}}");
    }


    @Test()
    public void getBlockchainGasServiceGasFeesTest903() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/providers/{provider}/gas-fees", "getBlockchainGasServiceGasFees", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GasFeesDto\"}}}}");
    }


    @Test()
    public void getBlockchainGasServiceGasPriceTest862() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/providers/{provider}/gas-price", "getBlockchainGasServiceGasPrice", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GasPriceDto\"}}}}");
    }


    @Test()
    public void ConnectDefiTransactionController_whitelistTest979() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/connect/v1/partners/{partnerId}/wallets/{walletAddress}/whitelist", "ConnectDefiTransactionController_whitelist", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DefiTransactionDto\"}}}}");
    }


    @Test()
    public void HealthController_checkTest13() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void AbstractionLayerController_getDeploymentLogsTest115() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/deployment-logs", "AbstractionLayerController_getDeploymentLogs", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/DeploymentLogsResponseDTO\"}}}}}");
    }


    @Test()
    public void AbstractionLayerController_getSwapContractTest819() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/swap", "AbstractionLayerController_getSwapContract", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/ContractSwapResponseDTO\"}}}}");
    }


    @Test()
    public void AbstractionLayerController_getErc20BalanceTest772() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/providers/{provider}/erc20/{address}/balance/{wallet}", "AbstractionLayerController_getErc20Balance", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetErc20BalanceResponseDTO\"}}}}");
    }


    @Test()
    public void ConnectPartnersController_attestationTest847() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/connect/v1/partners/{partnerId}/attestation", "ConnectPartnersController_attestation", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AttestationDto\"}}}}");
    }


    @Test()
    public void AbstractionLayerController_getTransferableTokensByWalletTest174() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/wallets/{address}/transferable-tokens", "AbstractionLayerController_getTransferableTokensByWallet", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TransferableTokensResponseDTO\"}}}}");
    }


    @Test()
    public void AbstractionLayerController_getDeployerAccountTest385() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/providers/{provider}/deployer-wallet", "AbstractionLayerController_getDeployerAccount", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetDeployerWalletResponseDTO\"}}}}");
    }


    @Test()
    public void DeploymentsManagementController_getIssuerDeploymentTest13() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/issuers/{issuerId}/deployments/{deploymentId}", "DeploymentsManagementController_getIssuerDeployment", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DeploymentInfoDto\"}}}}");
    }


    @Test()
    public void getTransactionSignaturesListTest320() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transaction-signatures", "getTransactionSignaturesList", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TransactionsSignatureListDTO\"}}}}");
    }


    @Test()
    public void AbstractionLayerController_getTransferableTokensByInvestorTest862() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/holders/{holderId}/transferable-tokens", "AbstractionLayerController_getTransferableTokensByInvestor", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TransferableTokensResponseDTO\"}}}}");
    }


    @Test()
    public void Web3AtsMatchServiceController_getMatchHandlerContractTest279() {
        testRequest(Method.GET, "https://blockchain-gw.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/match-handler", "Web3AtsMatchServiceController_getMatchHandlerContract", LoginAs.NONE, "ST/blockchain-gw-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/MatchHandlerResponseDto\"}}}}");
    }




}

