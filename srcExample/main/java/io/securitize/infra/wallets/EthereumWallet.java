package io.securitize.infra.wallets;

public class EthereumWallet extends AbstractWallet {
    private final String walletAddress;
    private final String privateKey;

    public EthereumWallet(String walletAddress, String privateKey) {
        this.walletAddress = walletAddress;
        this.privateKey = privateKey;
    }

    public String getAddress() {
        return walletAddress;
    }

    public String getSecret() {
        return privateKey;
    }
}