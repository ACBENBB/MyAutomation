package io.securitize.infra.config;

/**
 * Used to easily store details we need to sign transactions - address of signer wallet and relevant private key
 */
public class WalletDetails {

    private final String walletAddress;
    private final String walletPrivateKey;

    public WalletDetails(String walletAddress, String walletPrivateKey) {
        this.walletAddress = walletAddress;
        this.walletPrivateKey = walletPrivateKey;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public String getWalletPrivateKey() {
        return walletPrivateKey;
    }
}