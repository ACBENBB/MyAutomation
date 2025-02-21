package io.securitize.scripts;

import io.securitize.infra.api.algorand.AlgorandApi;
import io.securitize.infra.wallets.AlgorandWallet;

public class AUT166_AlgorandNewWalletCreation {
    public static void main(String[] args) {
        AlgorandApi algorandApi = new AlgorandApi();
        long assetId = 214907; // algo-qa. Details at: https://testnet.algoexplorer.io/asset/214907

        // create new account
        AlgorandWallet wallet = AlgorandWallet.GenerateWallet();

        // check initial balance
        long initialBalance = algorandApi.getWalletBalance(wallet);

        // send initial funds to wallet
        String txId = algorandApi.loadWalletWithFundsFromInternalFaucet(wallet, 1000000);

        // wait for funds to arrive
        algorandApi.waitForConfirmation(txId, 20);

        // print updated balance in new wallet
        long currentBalance = algorandApi.getWalletBalance(wallet);

        // enable token for wallet
        algorandApi.optInAsset(wallet, assetId);

        // check wallet for tokens
        int tokensAmount = algorandApi.getAssetHolding(wallet, assetId);
        System.out.println("**  Initial tokens held amount: " + tokensAmount);
    }
}
