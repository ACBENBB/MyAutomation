package io.securitize.tests.apiTests.cicd.ST.ST_TransactionsService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_Polygon_TransactionsService extends BaseApiTest {

    @Test()
    public void getRequiredConfirmationsTest260() {
        testRequest(Method.GET, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/required_confirmations", "getRequiredConfirmations", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"requiredConfirmations\":{\"type\":\"integer\"}}}}}}");
    }

    @Test()
    public void getGasPriceTest678() {
        testRequest(Method.GET, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/gas_price", "getGasPrice", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"cryptoCurrencySymbol\":{\"description\":\"Cryptocurrency symbol. Default: ETH\",\"type\":\"string\"},\"maxAllowedGasPrice\":{\"description\":\"Max gas price allowed expressed in gasPriceUnit\",\"type\":\"string\"},\"gasPriceUnit\":{\"description\":\"Gas price unit. Default: wei\",\"type\":\"string\"},\"gasPrice\":{\"description\":\"Current proposed gas price to use the network\",\"type\":\"string\"},\"minAllowedGasPrice\":{\"description\":\"Min gas price allowed expressed in gasPriceUnit\",\"type\":\"string\"}}}}}}");
    }

    @Test()
    public void getCheckHealthTest798() {
        testRequest(Method.GET, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/health-check", "getCheckHealth", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}}}");
    }


//    @Test()
//    public void signHsmTest596() {
//        testRequest(Method.POST, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/hsm-signature", "signHsm", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"description\":\"Signature vectors and data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Signature vectors and data\",\"type\":\"object\",\"properties\":{\"r\":{\"type\":\"string\"},\"s\":{\"type\":\"string\"},\"data\":{\"type\":\"string\"},\"v\":{\"type\":\"number\"}}}}}}");
//    }

    //    @Test()
//    public void createMultisigTransactionTest399() {
//        testRequest(Method.POST, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/multisig-wallets/{address}/transactions/new", "createMultisigTransaction", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"description\":\"The created transaction object 'multisigId' representing \\n    the created multisig transaction in the contract \\n    and 'transactionData' is the created transaction data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"The created transaction object 'multisigId' representing \\n    the created multisig transaction in the contract \\n    and 'transactionData' is the created transaction data\",\"type\":\"object\",\"properties\":{\"multisigId\":{\"type\":\"string\"},\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}}}}");
//    }

//    @Test()
//    public void removeOwnerTest895() {
//        testRequest(Method.DELETE, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/multisig-wallets/{address}/owners/{owner}", "removeOwner", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"description\":\"The created transaction object 'multisigId' representing \\n    the created multisig transaction in the contract \\n    and 'transactionData' is the created transaction data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"The created transaction object 'multisigId' representing \\n    the created multisig transaction in the contract \\n    and 'transactionData' is the created transaction data\",\"type\":\"object\",\"properties\":{\"multisigId\":{\"type\":\"string\"},\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}}}}");
//    }

//    @Test()
//    public void addOwnerTest448() {
//        testRequest(Method.POST, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/multisig-wallets/{address}/owners", "addOwner", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"description\":\"The created transaction object 'multisigId' representing \\n    the created multisig transaction in the contract \\n    and 'transactionData' is the created transaction data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"The created transaction object 'multisigId' representing \\n    the created multisig transaction in the contract \\n    and 'transactionData' is the created transaction data\",\"type\":\"object\",\"properties\":{\"multisigId\":{\"type\":\"string\"},\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}}}}");
//    }

//    @Test()
//    public void replaceOwnerTest803() {
//        testRequest(Method.PUT, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/multisig-wallets/{address}/owners", "replaceOwner", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"description\":\"The created transaction object 'multisigId' representing \\n    the created multisig transaction in the contract \\n    and 'transactionData' is the created transaction data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"The created transaction object 'multisigId' representing \\n    the created multisig transaction in the contract \\n    and 'transactionData' is the created transaction data\",\"type\":\"object\",\"properties\":{\"multisigId\":{\"type\":\"string\"},\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}}}}");
//    }

//    @Test()
//    public void confirmMultisigTransactionTest84() {
//        testRequest(Method.POST, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/multisig-wallets/{address}/transactions/{id}/signatures", "confirmMultisigTransaction", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"description\":\"The created transaction object 'multisigId' representing \\n    the created multisig transaction in the contract \\n    and 'transactionData' is the created transaction data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"The created transaction object 'multisigId' representing \\n    the created multisig transaction in the contract \\n    and 'transactionData' is the created transaction data\",\"type\":\"object\",\"properties\":{\"multisigId\":{\"type\":\"string\"},\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}}}}");
//    }

    @Test()
    public void getMultisigTransactionTest252() {
        testRequest(Method.GET, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/multisig-wallets/{address}/transactions/{id}", "getMultisigTransaction", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"description\":\"Transaction data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Transaction data\",\"type\":\"object\",\"properties\":{\"requiredSignatures\":{\"type\":\"integer\"},\"currentSignatures\":{\"type\":\"integer\"},\"owners\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"confirmations\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}}}}");
    }

    //    @Test()
//    public void createTransactionTest163() {
//        testRequest(Method.POST, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/transactions/new", "createTransaction", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}}}}");
//    }

//    @Test()
//    public void decodeTransactionTest843() {
//        testRequest(Method.POST, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/transactions/decode", "decodeTransaction", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"string\"},\"index\":{\"type\":\"integer\"},\"nonce\":{\"type\":\"integer\"},\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"blockNumber\":{\"type\":\"integer\"},\"maxPriorityFeePerGas\":{\"type\":\"string\"},\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"maxFeePerGas\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"},\"hash\":{\"type\":\"string\"},\"timestamp\":{\"type\":\"integer\"},\"gasPrice\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getTransactionTest338() {
        testRequest(Method.GET, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/transactions/{hash}", "getTransaction", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"string\"},\"index\":{\"type\":\"integer\"},\"nonce\":{\"type\":\"integer\"},\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"blockNumber\":{\"type\":\"integer\"},\"maxPriorityFeePerGas\":{\"type\":\"string\"},\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"maxFeePerGas\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"},\"hash\":{\"type\":\"string\"},\"timestamp\":{\"type\":\"integer\"},\"gasPrice\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}}}");
    }

//    @Test()
//    public void cancelTransactionTest451() {
//        testRequest(Method.POST, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/transactions/{hash}/cancel", "cancelTransaction", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}}}}");
//    }

//    @Test()
//    public void executeTransactionTest646() {
//        testRequest(Method.POST, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/transactions", "executeTransaction", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"description\":\"The transaction hash\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"The transaction hash\",\"type\":\"object\",\"properties\":{\"hash\":{\"type\":\"string\"}}}}}}");
//    }

    @Test()
    public void getTransactionsTest378() {
        testRequest(Method.GET, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/transactions?address={address}&fromBlock={fromBlock}&toBlock={toBlock}&status={status}", "getTransactions", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"string\"},\"index\":{\"type\":\"integer\"},\"nonce\":{\"type\":\"integer\"},\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"blockNumber\":{\"type\":\"integer\"},\"maxPriorityFeePerGas\":{\"type\":\"string\"},\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"maxFeePerGas\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"},\"hash\":{\"type\":\"string\"},\"timestamp\":{\"type\":\"integer\"},\"gasPrice\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}}}}");
    }

//    @Test()
//    public void speedupTransactionTest525() {
//        testRequest(Method.POST, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/transactions/{hash}/speedup", "speedupTransaction", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"chainId\":{\"type\":\"integer\"},\"networkId\":{\"type\":\"integer\"},\"txType\":{\"type\":\"string\"},\"networkType\":{\"type\":\"string\"}}}}}}}}");
//    }

//    @Test()
//    public void resetCounterTest837() {
//        testRequest(Method.POST, "https://polygon-transactions-service.internal.{environment}.securitize.io/v1/reset_nonce", "resetCounter", LoginAs.OPERATOR, "ST/transactions-service/polygon", "{\"content\":{\"application/json\":{\"schema\":{\"additionalProperties\":false,\"type\":\"object\"}}}}");
//    }


}
