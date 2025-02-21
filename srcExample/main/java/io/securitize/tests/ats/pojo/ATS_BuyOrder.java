package io.securitize.tests.ats.pojo;

import java.text.*;

public class ATS_BuyOrder {

    private Double buyPrice;
    private Double buyQuantity;

    public ATS_BuyOrder(Double buyPrice, Double buyQuantity) {
        this.buyPrice = buyPrice;
        this.buyQuantity = buyQuantity;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public Double getBuyQuantity() {
        return buyQuantity;
    }

}
