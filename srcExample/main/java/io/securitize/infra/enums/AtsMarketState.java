package io.securitize.infra.enums;

@SuppressWarnings("unused")
public enum AtsMarketState {
    UP(0),
    MAINTENANCE(1);

    private final int value;

    AtsMarketState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}