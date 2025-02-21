package io.securitize.tests.ats.pojo;

public class ATSOrder {
    private String orderId;
    private String referenceNumber;
    private String feePercentage;
    private String quantity;
    private String price;

    public ATSOrder() {

    }

    public ATSOrder(String orderId, String referenceNumber) {
        this.orderId = orderId;
        this.referenceNumber = referenceNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getFeePercentage() {
        return feePercentage;
    }

    public void setFeePercentage(String feePercentage) {
        this.feePercentage = feePercentage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
