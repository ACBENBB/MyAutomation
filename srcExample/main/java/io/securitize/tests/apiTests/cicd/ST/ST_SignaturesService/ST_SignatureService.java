package io.securitize.tests.apiTests.cicd.ST.ST_SignaturesService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_SignatureService extends BaseApiTest {

//    @Test()
//    public void cancelTransactionServiceTest44() {
//        testRequest(Method.POST, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions/{id}/cancel", "cancelTransactionService", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"transactionData\":{\"description\":\"Unsigned transaction\",\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}},\"transactionId\":{\"description\":\"The ID of AbstractionLayer transaction\",\"type\":\"string\"}}}}}}");
//    }
//
//
//    @Test()
//    public void createTransactionTest600() {
//        testRequest(Method.POST, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions", "createTransaction", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"transactionData\":{\"description\":\"The transactionData to be signed\",\"type\":\"string\"},\"externalId\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"additionalData\":{\"description\":\"Additional information to use during signing process\",\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"networkType\":{\"type\":\"string\"}}},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"sent\",\"success\",\"failure\",\"mempool\",\"canceled\"]}}}}}}");
//    }


    @Test()
    public void getTransactionsTest838() {
        testRequest(Method.GET, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions", "getTransactions", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"date\":{\"description\":\"Creation time in ISO Format\",\"type\":\"string\"},\"creationTime\":{\"description\":\"Creation date\",\"type\":\"number\"},\"deploymentId\":{\"type\":\"string\"},\"externalId\":{\"description\":\"The uuid of the transaction signature\",\"type\":\"string\"},\"description\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"type\":{\"description\":\"TransactionType of operation for example IssueToken or RegisterWallet\",\"type\":\"string\"},\"operatorName\":{\"description\":\"Name of operator who created the transaction\",\"type\":\"string\"},\"status\":{\"description\":\"Status of transaction\",\"type\":\"string\",\"enum\":[\"sent\",\"success\",\"failure\"]}}}}}}}}}");
    }


//    @Test()
//    public void updateTransactionStatusTest908() {
//        testRequest(Method.PUT, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions/{id}/status", "updateTransactionStatus", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"externalId\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"sent\",\"success\",\"failure\",\"mempool\",\"canceled\"]}}}}}}");
//    }
//
//
//    @Test()
//    public void createAssetTest723() {
//        testRequest(Method.POST, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/create-asset", "createAsset", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"transactionData\":{\"description\":\"The transactionData to be signed\",\"type\":\"string\"},\"externalId\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"additionalData\":{\"description\":\"Additional information to use during signing process\",\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"networkType\":{\"type\":\"string\"}}},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"sent\",\"success\",\"failure\",\"mempool\",\"canceled\"]}}}}}}");
//    }
//
//
//    @Test()
//    public void createBulkTransactionsSchemaTest800() {
//        testRequest(Method.POST, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions/bulk", "createBulkTransactionsSchema", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"transactionData\":{\"description\":\"The transactionData to be signed\",\"type\":\"string\"},\"externalId\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"additionalData\":{\"description\":\"Additional information to use during signing process\",\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"networkType\":{\"type\":\"string\"}}},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"sent\",\"success\",\"failure\",\"mempool\",\"canceled\"]}}}}}}}");
//    }


    @Test()
    public void getTransactionTest644() {
        testRequest(Method.GET, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions/{id}", "getTransaction", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"date\":{\"description\":\"Create date in ISO format\",\"type\":\"string\"},\"creationTime\":{\"description\":\"Creation date\",\"type\":\"number\"},\"signedAddress\":{\"description\":\"Address who signed the transaction\",\"type\":\"string\"},\"externalId\":{\"description\":\"uuid of the transaction signature. Used to send events in AL\",\"type\":\"string\"},\"description\":{\"type\":\"string\"},\"transactionProviderId\":{\"description\":\"transactionProviderId in AbstractionLayer\",\"type\":\"string\"},\"type\":{\"description\":\"TransactionType of operation for example IssueToken or RegisterWallet\",\"type\":\"string\"},\"error\":{\"description\":\"A field that details the last error with the transaction\",\"type\":\"string\"},\"operatorName\":{\"description\":\"Name of operator who created the transaction\",\"type\":\"string\"},\"transactionId\":{\"description\":\"Transaction ID in AbstractionLayer\",\"type\":\"string\"},\"signedTime\":{\"description\":\"Signed date\",\"type\":\"number\"},\"deploymentId\":{\"description\":\"id of abstraction-layer\",\"type\":\"string\"},\"blockNumber\":{\"description\":\"blockNumber of transaction\",\"type\":\"integer\"},\"blockTime\":{\"description\":\"blockTime of transaction\",\"type\":\"integer\"},\"id\":{\"type\":\"number\"},\"operatorId\":{\"description\":\"ID of the operator who created the transaction\",\"type\":\"integer\"},\"transaction\":{\"type\":\"object\",\"properties\":{\"apiMethod\":{\"type\":\"string\"},\"apiName\":{\"type\":\"string\"},\"txCreationBody\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"complianceRules\":{\"description\":\"Compliance rules\",\"type\":\"object\",\"properties\":{\"forceFullTransfer\":{\"type\":\"boolean\"},\"forceAccredited\":{\"type\":\"boolean\"},\"nonUSLockPeriod\":{\"type\":\"integer\"},\"nonAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"maxUSInvestorsPercentage\":{\"type\":\"integer\"},\"forceAccreditedUS\":{\"type\":\"boolean\"},\"usAccreditedInvestorsLimit\":{\"type\":\"integer\"},\"worldWideForceFullTransfer\":{\"type\":\"boolean\"},\"minEUTokens\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"},\"usLockPeriod\":{\"type\":\"integer\"},\"jpInvestorsLimit\":{\"type\":\"integer\"},\"blockFlowbackEndTime\":{\"type\":\"integer\"},\"totalInvestorsLimit\":{\"type\":\"integer\"},\"euRetailInvestorsLimit\":{\"type\":\"integer\"},\"usInvestorsLimit\":{\"type\":\"integer\"},\"minimumHoldingsPerInvestor\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"},\"maximumHoldingsPerInvestor\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"},\"minimumTotalInvestors\":{\"type\":\"integer\"},\"minUSTokens\":{\"pattern\":\"^0$|[1-9]$|[1-9][0-9]+$\",\"type\":\"string\"}}},\"holders\":{\"type\":\"array\",\"items\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"wallets\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"qualified\":{\"type\":\"string\"},\"createInvestor\":{\"type\":\"boolean\"},\"kyc\":{\"type\":\"string\"},\"countryCode\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"expiry\":{\"type\":\"integer\"},\"accredited\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}},\"id\":{\"type\":\"string\"}}}},\"lock\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"reason\":{\"type\":\"string\"},\"releaseTime\":{\"type\":\"integer\"},\"value\":{\"type\":\"string\"}}},\"holder\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"country\":{\"type\":\"string\"},\"wallets\":{\"type\":\"string\"},\"attributes\":{\"type\":\"array\",\"items\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"qualified\":{\"type\":\"string\"},\"createInvestor\":{\"type\":\"boolean\"},\"kyc\":{\"type\":\"string\"},\"countryCode\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"expiry\":{\"type\":\"integer\"},\"accredited\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}},\"id\":{\"type\":\"string\"}}}}},\"txCreationArguments\":{\"description\":\"Creation arguments\",\"type\":\"array\",\"items\":{\"type\":\"string\"}}}},\"status\":{\"description\":\"Status of transaction\",\"type\":\"string\",\"enum\":[\"sent\",\"success\",\"failure\"]}}}}}}");
    }


//    @Test()
//    public void deleteTransactionTest211() {
//        testRequest(Method.DELETE, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions/{id}", "deleteTransaction", LoginAs.OPERATOR, "ST/signatures-service", "{\"description\":\"The id and deploymentId of deleted transaction\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"The id and deploymentId of deleted transaction\",\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"number\"}}}}}}");
//    }
//
//
//    @Test()
//    public void updateTransactionTest63() {
//        testRequest(Method.PUT, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions/{id}", "updateTransaction", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"transactionData\":{\"description\":\"The transactionData to be signed\",\"type\":\"string\"},\"externalId\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"additionalData\":{\"description\":\"Additional information to use during signing process\",\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"networkType\":{\"type\":\"string\"}}},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"sent\",\"success\",\"failure\",\"mempool\",\"canceled\"]}}}}}}");
//    }


    @Test()
    public void getGasPriceTest115() {
        testRequest(Method.GET, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/gas-price", "getGasPrice", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"cryptoCurrencySymbol\":{\"type\":\"string\"},\"gasPriceUnit\":{\"type\":\"string\"},\"gasPrice\":{\"type\":\"string\"}}}}}}");
    }


//    @Test()
//    public void invokeDsProtocolServiceTest802() {
//        testRequest(Method.POST, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions/{id}/ds-protocol", "invokeDsProtocolService", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"transactionData\":{\"description\":\"Unsigned transaction\",\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}},\"transactionId\":{\"description\":\"The ID of AbstractionLayer transaction\",\"type\":\"string\"}}}}}}");
//    }
//
//
//    @Test()
//    public void sendTransactionSignatureTest370() {
//        testRequest(Method.POST, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions/{id}/signature", "sendTransactionSignature", LoginAs.OPERATOR, "ST/signatures-service", "{\"description\":\"Signed transaction sending result\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Signed transaction sending result\",\"type\":\"object\",\"properties\":{\"transactionData\":{\"description\":\"The transactionData to sign final multisig transactions\",\"type\":\"string\"},\"signatureId\":{\"description\":\"The ID of AbstractionLayer signatures\",\"type\":\"string\"},\"transactionProviderId\":{\"type\":\"string\"},\"additionalData\":{\"description\":\"The additionalData to sign final multisig transactions\",\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"multiSigTransactionExecution\":{\"type\":\"boolean\"},\"networkId\":{\"type\":\"integer\"},\"networkType\":{\"type\":\"string\"}}},\"transactionId\":{\"description\":\"The ID of AbstractionLayer transaction\",\"type\":\"string\"},\"status\":{\"description\":\"The status of the transaction in AbstractionLayer\",\"type\":\"string\"}}}}}}");
//    }


    @Test()
    public void getProviderTransactionDetailTest173() {
        testRequest(Method.GET, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions/{id}/provider-detail", "getProviderTransactionDetail", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"finalized\":{\"type\":\"boolean\"},\"blockNumber\":{\"type\":\"integer\"},\"transactionProviderId\":{\"description\":\"transactionProviderId in AbstractionLayer\",\"type\":\"string\"},\"signerAddress\":{\"description\":\"Address who signed the transaction\",\"type\":\"string\"},\"status\":{\"description\":\"Status of transaction\",\"type\":\"string\"},\"gasPrice\":{\"description\":\"Gas price of the transaction\",\"type\":\"string\"}}}}}}");
    }


//    @Test()
//    public void speedUpTransactionServiceTest961() {
//        testRequest(Method.POST, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/transactions/{id}/speed-up", "speedUpTransactionService", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"transactionData\":{\"description\":\"Unsigned transaction\",\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}},\"transactionId\":{\"description\":\"The ID of AbstractionLayer transaction\",\"type\":\"string\"}}}}}}");
//    }
//
//
//    @Test()
//    public void deleteMultiSigTransactionSignaturesTest675() {
//        testRequest(Method.DELETE, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/multisig-wallets/transactions/{id}/signatures", "deleteMultiSigTransactionSignatures", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"number\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"sent\",\"success\",\"failure\",\"mempool\",\"canceled\"]}}}}}}");
//    }


    @Test()
    public void getCheckHealthTest156() {
        testRequest(Method.GET, "https://signatures-service.internal.{environment}.securitize.io/v1/health", "getCheckHealth", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getMultisigWalletsTest987() {
        testRequest(Method.GET, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/multisig-wallets", "getMultisigWallets", LoginAs.OPERATOR, "ST/signatures-service", "{\"description\":\"List of Multisig wallets\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"List of Multisig wallets\",\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"address\":{\"pattern\":\"(0x)[0-9a-fA-F]*$\",\"type\":\"string\"}},\"required\":[\"address\"]}}}}}");
    }


    @Test()
    public void getMultiSigTransactionStatusSchemaTest232() {
        testRequest(Method.GET, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/multisig-wallets/transactions/{id}/status", "getMultiSigTransactionStatusSchema", LoginAs.OPERATOR, "ST/signatures-service", "{\"description\":\"The signatures of the off-chain transactions are saved until reaching the minimum amount required to execute it (threshold). This service returns the number of signatures so far\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"The signatures of the off-chain transactions are saved until reaching the minimum amount required to execute it (threshold). This service returns the number of signatures so far\",\"type\":\"object\",\"properties\":{\"multiSigWalletId\":{\"description\":\"The id of the multisig\",\"type\":\"string\"},\"transactionData\":{\"description\":\"The transactionData of the transaction\",\"type\":\"string\"},\"threshold\":{\"description\":\"The number of off-chain required signatures to sign the transaction\",\"type\":\"number\"},\"additionalData\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}},\"nonce\":{\"description\":\"The nonce of the multisig wallet when the trasaction is created\",\"type\":\"string\"},\"transactionId\":{\"description\":\"The id of the transaction\",\"type\":\"string\"},\"signatures\":{\"description\":\"The number of off-chain saved signatures of the transaction\",\"type\":\"number\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"sent\",\"success\",\"failure\",\"mempool\",\"canceled\"]}}}}}}");
    }


    @Test()
    public void getSingleMultiSigWalletTest401() {
        testRequest(Method.GET, "https://signatures-service.internal.{environment}.securitize.io/v1/deployments/{deploymentId}/multisig-wallets/{multisigWalletId}", "getSingleMultiSigWallet", LoginAs.OPERATOR, "ST/signatures-service", "{\"description\":\"MultiSig wallet\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"MultiSig wallet\",\"type\":\"object\",\"properties\":{\"deploymentStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"inProgress\",\"success\",\"failure\"]},\"walletName\":{\"type\":\"string\"},\"deploymentId\":{\"type\":\"string\"},\"threshold\":{\"type\":\"number\"},\"walletAddress\":{\"pattern\":\"(0x)[0-9a-fA-F]*$\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getCheckHealthTest793() {
        testRequest(Method.GET, "https://signatures-service.internal.{environment}.securitize.io/v1/health-check", "getCheckHealth", LoginAs.OPERATOR, "ST/signatures-service", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}}}");
    }

}