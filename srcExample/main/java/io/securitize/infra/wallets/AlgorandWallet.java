package io.securitize.infra.wallets;

import io.securitize.infra.api.algorand.AlgorandApi;

public class AlgorandWallet extends AbstractWallet {
    private final String address;
    private final String passphrase;

    public AlgorandWallet(String address, String passphrase) {
        this.address = address;
        this.passphrase = passphrase;
    }

    public static AlgorandWallet GenerateWallet() {
        AlgorandApi api = new AlgorandApi();
        return api.createWallet();
    }

    public String getAddress() {
        return address;
    }

    public String getSecret() {
        return passphrase;
    }
}