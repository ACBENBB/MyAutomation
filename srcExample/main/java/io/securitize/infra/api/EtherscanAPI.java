package io.securitize.infra.api;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.securitize.infra.enums.MetaMaskEthereumNetwork;
import org.testng.Assert;

import java.time.Duration;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class EtherscanAPI {

    private static final int RETRY_POLICY_MAX_ATTEMPTS = 6;
    private static final long RETRY_POLICY_DELAY_OF_SECONDS = 10;


    public static double getWalletBalance(String walletAddress, MetaMaskEthereumNetwork ethereumNetwork) {

        String url;
        switch (ethereumNetwork) {
            case Mainnet:
                url = "https://api.etherscan.io/api";
                break;
            case Sepolia:
                url = "https://api-sepolia.etherscan.io/api";
                break;
            case PolygonMumbai:
                url = "https://api-testnet.polygonscan.com/api";
                break;
            default:
                errorAndStop("Ethereum network not supported: " + ethereumNetwork, false);
                url = null;
        }

        // as Etherscan sometimes returns 403 errors, we add this retry mechanism
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .handle(AssertionError.class)
                .withDelay(Duration.ofSeconds(RETRY_POLICY_DELAY_OF_SECONDS))
                .withMaxRetries(RETRY_POLICY_MAX_ATTEMPTS)
                .onFailedAttempt(e -> info("API request failed, will try again. Error: " + e.getLastException()))
                .build();

        String balanceAsString = Failsafe.with(retryPolicy).get(() -> {
            String balanceAsStringInternal = given()
                    .when()
                    .queryParam("module", "account")
                    .queryParam("action", "balance")
                    .queryParam("address", walletAddress)
                    .queryParam("tag", "latest")
                    .log().all()
                    .get(url)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().path("result");
            // also handle rate limit errors by retrying
            Assert.assertFalse(balanceAsStringInternal.toLowerCase().contains("rate limit reached"), "Hit rate limit in Etherscan! Not good!");
            return balanceAsStringInternal;
        });
        return Double.parseDouble(balanceAsString) / Math.pow(10, 18);
    }
}
