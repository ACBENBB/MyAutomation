package io.securitize.infra.enums;

public enum TestNetwork {
    ETHEREUM_SEPOLIA(true),
    POLYGON_MUMBAI(true),
    AVALANCHE_AVAX(false),
    QUORUM(true);

    private final boolean needsBypass;

    TestNetwork(final boolean needsBypass) {
        this.needsBypass = needsBypass;
    }

    public boolean needsBypass() {
        return needsBypass;
    }
}
