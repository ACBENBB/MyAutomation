package io.securitize.infra.wallets;

public abstract class AbstractWallet {
    public abstract String getAddress();

    public abstract String getSecret();
}
