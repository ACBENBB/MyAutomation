package io.securitize.infra.wallets;

public enum WalletExtension {
    NONE ("none"),
    METAMASK ("metaMask"),
    COINBASE ("coinBase");

    private final String name;

    WalletExtension(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
