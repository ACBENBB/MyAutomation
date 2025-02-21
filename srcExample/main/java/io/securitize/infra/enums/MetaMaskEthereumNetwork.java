package io.securitize.infra.enums;

public enum MetaMaskEthereumNetwork {
    Mainnet("Ethereum Mainnet"),
    Sepolia("Sepolia"),
    PolygonMumbai("Mumbai"),
    AvalancheAvax("Avalance");

    private final String textDescription;

    MetaMaskEthereumNetwork(final String textDescription) {
        this.textDescription = textDescription;
    }

    @Override
    public String toString() {
        return textDescription;
    }
}
