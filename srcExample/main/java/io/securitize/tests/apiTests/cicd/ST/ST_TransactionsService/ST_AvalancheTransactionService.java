package io.securitize.tests.apiTests.cicd.ST.ST_TransactionsService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_AvalancheTransactionService extends BaseApiTest {

    @Test()
    public void getRequiredConfirmationsTest607() {
        testRequest(Method.GET, "https://avalanche-transactions-service.internal.{environment}.securitize.io/v1/required_confirmations", "getRequiredConfirmations", LoginAs.NONE, "ST/transactions-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"requiredConfirmations\":{\"type\":\"integer\"}}}}}}");
    }


    @Test()
    public void getGasPriceTest898() {
        testRequest(Method.GET, "https://avalanche-transactions-service.internal.{environment}.securitize.io/v1/gas_price", "getGasPrice", LoginAs.NONE, "ST/transactions-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"cryptoCurrencySymbol\":{\"description\":\"Cryptocurrency symbol. Default: ETH\",\"type\":\"string\"},\"maxAllowedGasPrice\":{\"description\":\"Max gas price allowed expressed in gasPriceUnit\",\"type\":\"string\"},\"gasPriceUnit\":{\"description\":\"Gas price unit. Default: wei\",\"type\":\"string\"},\"gasPrice\":{\"description\":\"Current proposed gas price to use the network\",\"type\":\"string\"},\"minAllowedGasPrice\":{\"description\":\"Min gas price allowed expressed in gasPriceUnit\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getTransactionTest554() {
        testRequest(Method.GET, "https://avalanche-transactions-service.internal.{environment}.securitize.io/v1/transactions/{hash}", "getTransaction", LoginAs.NONE, "ST/transactions-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"string\"},\"index\":{\"type\":\"integer\"},\"nonce\":{\"type\":\"integer\"},\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"blockNumber\":{\"type\":\"integer\"},\"maxPriorityFeePerGas\":{\"type\":\"string\"},\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"maxFeePerGas\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"},\"hash\":{\"type\":\"string\"},\"timestamp\":{\"type\":\"integer\"},\"gasPrice\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getTransactionsTest416() {
        testRequest(Method.GET, "https://avalanche-transactions-service.internal.{environment}.securitize.io/v1/transactions", "getTransactions", LoginAs.NONE, "ST/transactions-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"string\"},\"index\":{\"type\":\"integer\"},\"nonce\":{\"type\":\"integer\"},\"finalized\":{\"type\":\"boolean\"},\"gasLimit\":{\"type\":\"integer\"},\"blockNumber\":{\"type\":\"integer\"},\"maxPriorityFeePerGas\":{\"type\":\"string\"},\"from\":{\"type\":\"string\"},\"to\":{\"type\":\"string\"},\"maxFeePerGas\":{\"type\":\"string\"},\"value\":{\"type\":\"string\"},\"hash\":{\"type\":\"string\"},\"timestamp\":{\"type\":\"integer\"},\"gasPrice\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}}}}");
    }


    @Test()
    public void getMultisigTransactionTest99() {
        testRequest(Method.GET, "https://avalanche-transactions-service.internal.{environment}.securitize.io/v1/multisig-wallets/{address}/transactions/{id}", "getMultisigTransaction", LoginAs.NONE, "ST/transactions-service/avalanche", "{\"description\":\"Transaction data\",\"content\":{\"application/json\":{\"schema\":{\"description\":\"Transaction data\",\"type\":\"object\",\"properties\":{\"requiredSignatures\":{\"type\":\"integer\"},\"currentSignatures\":{\"type\":\"integer\"},\"owners\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"confirmations\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}}}}");
    }


    @Test()
    public void getCheckHealthTest442() {
        testRequest(Method.GET, "https://avalanche-transactions-service.internal.{environment}.securitize.io/v1/health-check", "getCheckHealth", LoginAs.NONE, "ST/transactions-service/avalanche", "{\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"service\":{\"type\":\"string\"},\"error\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}}}");
    }




}

