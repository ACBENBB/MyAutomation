package io.securitize.infra.enums;

@SuppressWarnings("unused")
public enum CurrencyIds {
    unknown(0),
    USD(1),
    EUR(2),
    ETH(3);

    private final int id;


    CurrencyIds(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
