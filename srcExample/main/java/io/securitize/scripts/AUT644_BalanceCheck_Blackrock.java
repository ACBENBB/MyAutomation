package io.securitize.scripts;

import io.securitize.infra.api.EtherscanAPI;
import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.*;
import io.securitize.infra.enums.MetaMaskEthereumNetwork;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.exceptions.InsufficientFundsException;
import io.securitize.infra.utils.DateTimeUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT644_BalanceCheck_Blackrock {
    private static final String LOG_FILE_NAME = "wallet-balance-results.log";
    private static final double MINIMUM_ISSUER_BALANCE_IN_WALLET = 0.001;
    private static final String ISSUER_NAME = "BlackRock_dataCreation";
    private static final TestNetwork TEST_NETWORK = TestNetwork.ETHEREUM_SEPOLIA;
    private static final StringBuilder goodWallets = new StringBuilder();
    private static final StringBuilder badWallets = new StringBuilder();

    public static void main(String[] args) throws IOException {
        removeOldLog();

        runLogic();

        writeLog();

        if (badWallets.length() > 0) {
            throw new InsufficientFundsException("At least one wallet doesn't have enough balance");
        }
    }

    private static void runLogic() {
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String fullPropertyName = TEST_NETWORK.toString().toLowerCase() + "TokenName";
        String tokenName = Users.getIssuerDetails(ISSUER_NAME.toLowerCase(), IssuerDetails.valueOf(fullPropertyName));
        String tokenTicker = investorsAPI.getTokenTicker(ISSUER_NAME, tokenName);
        JSONObject addressesAndBalance = investorsAPI.getTokenWalletsAndBalance(ISSUER_NAME, tokenName);

        // wallet master
        String walletMasterAddress = addressesAndBalance.getString("walletMasterAddress");
        double walletMasterBalance = EtherscanAPI.getWalletBalance(walletMasterAddress, MetaMaskEthereumNetwork.Sepolia);
        analyzeWalletBalance(tokenName, tokenTicker,"wallet MASTER", walletMasterAddress, walletMasterBalance);

        // wallet syncer
        String walletSyncerAddress = addressesAndBalance.getString("walletSyncerAddress");
        double walletSyncerBalance = addressesAndBalance.getDouble("balance");
        analyzeWalletBalance(tokenName, tokenTicker, "wallet syncer", walletSyncerAddress, walletSyncerBalance);
    }

        private static void analyzeWalletBalance(String tokenName, String tokenTicker, String walletType, String address, double balance) {
        if (balance >= MINIMUM_ISSUER_BALANCE_IN_WALLET) {
            String message = String.format("✓ Network '%s' Token Name '%s' (%s) %s balance %s >= %s (%s)%n", TEST_NETWORK, tokenName, tokenTicker, walletType, balance, MINIMUM_ISSUER_BALANCE_IN_WALLET, address);
            info(message);
            goodWallets.append(message);
        } else {
            String message = String.format("\uD800\uDD02 Network '%s' (%s) Token Name '%s' %s balance %s < %s (%s)%n", TEST_NETWORK, tokenName, tokenTicker, walletType, balance, MINIMUM_ISSUER_BALANCE_IN_WALLET, address);
            error(message, false);
            badWallets.append(message);
        }
    }

    private static void removeOldLog() {
        try {
            Files.deleteIfExists(Paths.get(LOG_FILE_NAME));
        } catch (IOException e) {
            errorAndStop("An error occur trying to delete old log file. Won't resume as it might cause confusion with previous runs", false, new RuntimeException(e));
        }
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
