package io.securitize.scripts;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.securitize.infra.api.AvalancheAPI;
import io.securitize.infra.api.EtherscanAPI;
import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.enums.MetaMaskEthereumNetwork;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.exceptions.InsufficientFundsException;
import io.securitize.infra.utils.DateTimeUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT683_BalanceCheck_TestNetworks {

    private static final String ISSUER_NAME = "Nie";
    private static final String LOG_FILE_NAME = "wallet-balance-results.log";
    private static final int MAX_API_CALL_ATTEMPTS = 10;
    private static final Map<TestNetwork, Double> minimalAllowedBalance = Map.of(
            TestNetwork.ETHEREUM_SEPOLIA, 0.6d,
            TestNetwork.POLYGON_MUMBAI, 1d,
            TestNetwork.AVALANCHE_AVAX, 1d);

    private static final Map<String, List<TestNetwork>> networksToAnalyze = new HashMap<>();

    static {
        networksToAnalyze.put("rc", List.of(TestNetwork.ETHEREUM_SEPOLIA, TestNetwork.POLYGON_MUMBAI));
        networksToAnalyze.put("sandbox", List.of(TestNetwork.ETHEREUM_SEPOLIA, TestNetwork.POLYGON_MUMBAI));
        networksToAnalyze.put("production", List.of(TestNetwork.AVALANCHE_AVAX));
    }

    private static RetryPolicy<Object> retryPolicy;
    private static final InvestorsAPI investorsAPI = new InvestorsAPI();
    private static final StringBuilder goodWallets = new StringBuilder();
    private static final StringBuilder badWallets = new StringBuilder();
    private static int retryCounter = 1;


    public static void main(String[] args) throws IOException {
        removeOldLog();

        var scenarios = networksToAnalyze.get(MainConfig.getProperty(MainConfigProperty.environment));

        initRetryPolicy();
        for (TestNetwork currentNetwork: scenarios) {
            retryCounter = 1;
            runLogic(currentNetwork);
        }

        writeLog();

        if (badWallets.length() > 0) {
            throw new InsufficientFundsException("At least one wallet doesn't have enough balance");
        }
    }

    private static void initRetryPolicy() {
        retryPolicy = RetryPolicy.builder()
                .handle(java.lang.AssertionError.class)
                .withDelay(Duration.ofSeconds(5))
                .onRetry(e -> {
                    info(String.format("API request attempt failed (%s/%s). Error: %s", retryCounter, MAX_API_CALL_ATTEMPTS, e.getLastException()));
                    retryCounter++;
                })
                .withMaxRetries(MAX_API_CALL_ATTEMPTS)
                .build();
    }

    private static void removeOldLog() {
        try {
            Files.deleteIfExists(Paths.get(LOG_FILE_NAME));
        } catch (IOException e) {
            errorAndStop("An error occur trying to delete old log file. Won't resume as it might cause confusion with previous runs", false, new RuntimeException(e));
        }
    }


    private static void runLogic(TestNetwork testNetwork) {
        info(String.format("Checking network:%s%n", testNetwork));
        String fullPropertyName = testNetwork.toString().toLowerCase() + "TokenName";
        String tokenName = Users.getIssuerDetails(ISSUER_NAME.toLowerCase(), IssuerDetails.valueOf(fullPropertyName));
        JSONObject addressesAndBalance = Failsafe.with(retryPolicy).get(() -> investorsAPI.getTokenWalletsAndBalance(ISSUER_NAME, tokenName));

        // wallet master
        String walletMasterAddress = addressesAndBalance.getString("walletMasterAddress");
        double walletMasterBalance = Failsafe.with(retryPolicy).get(() -> getWalletBalance(testNetwork, walletMasterAddress));
        analyzeWalletBalance(testNetwork, tokenName, "wallet MASTER", walletMasterAddress, walletMasterBalance);

        // wallet syncer
        String walletSyncerAddress = addressesAndBalance.getString("walletSyncerAddress");
        double walletSyncerBalance = addressesAndBalance.getDouble("balance");
        analyzeWalletBalance(testNetwork, tokenName, "wallet syncer", walletSyncerAddress, walletSyncerBalance);
    }

    private static void analyzeWalletBalance(TestNetwork testNetwork, String tokenName, String walletType, String address, double balance) {
        double requiredBalance = minimalAllowedBalance.get(testNetwork);
        if (balance >= requiredBalance) {
            String message = String.format("✓ Network '%s' Token Name '%s' %s balance %s >= %s (%s)%n", testNetwork, tokenName, walletType, balance, requiredBalance, address);
            info(message);
            goodWallets.append(message);
        } else {
            String message = String.format("\uD800\uDD02 Network '%s' Token Name '%s' %s balance %s < %s (%s)%n", testNetwork, tokenName, walletType, balance, requiredBalance, address);
            error(message, false);
            badWallets.append(message);
        }
    }

    private static double getWalletBalance(TestNetwork testNetwork, String address) {
        double balance;
        switch (testNetwork) {
            case ETHEREUM_SEPOLIA:
                balance = EtherscanAPI.getWalletBalance(address, MetaMaskEthereumNetwork.Sepolia);
                break;
            case POLYGON_MUMBAI:
                balance = EtherscanAPI.getWalletBalance(address, MetaMaskEthereumNetwork.PolygonMumbai);
                break;
            case AVALANCHE_AVAX:
                balance = AvalancheAPI.getWalletBalance(address);
                break;
            default:
                errorAndStop("Can't find balance for wallet of test network: " + testNetwork, false);
                return -1d;
        }
        return BigDecimal.valueOf(balance).setScale(5, RoundingMode.CEILING).doubleValue();
    }

    private static void writeLog() throws IOException {
        String currentTimeStamp = DateTimeUtils.currentDateTimeUTC();
        String issuerFullName = Users.getIssuerDetails(ISSUER_NAME, IssuerDetails.issuerFullName);
        String fullText = String.format("Wallets balance summary on **%s** for issuer %s (%s):%n(%s)%n", MainConfig.getProperty(MainConfigProperty.environment).toUpperCase(), ISSUER_NAME, issuerFullName, currentTimeStamp);
        if (badWallets.length() > 0) {
            fullText += String.format("%nWallets with insufficient funds:%n%s", badWallets);
        }
        if (goodWallets.length() > 0) {
            fullText += String.format("%nWallets with sufficient funds: %n%s", goodWallets);
        }
        Files.write(Paths.get(LOG_FILE_NAME), fullText.getBytes());
    }
}