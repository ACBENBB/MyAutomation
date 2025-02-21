package io.securitize.tests.ats.pojo;

public class ATS_SellOrder {

    private Double sellPrice;
    private Double sellQuantity;

    public ATS_SellOrder(Double sellPrice, Double sellQuantity) {
        this.sellPrice = sellPrice;
        this.sellQuantity = sellQuantity;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public Double getSellQuantity() {
        return sellQuantity;
    }

}