package io.securitize.infra.enums;

@SuppressWarnings("unused")
public enum AtsRegionState {
    US_OPEN("OPEN"),
    US_PRE_MARKET("PRE_MARKET"),
    US_NOT_AVAILABLE("NOT_AVAILABLE"),
    EU_OPEN("OPEN"),
    EU_PRE_MARKET("PRE_MARKET"),
    EU_NOT_AVAILABLE("NOT_AVAILABLE");

    private final String value;

    AtsRegionState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}