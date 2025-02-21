package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TransactionRow {
    private String type;
    private String date;
    private String amount;
    private String fee;
    private String status;
    private String action;
    private String total;

    public TransactionRow(String type, String date, String amount, String fee, String status, String action, String total) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.fee = fee;
        this.status = status;
        this.action = action;
        this.total = total;
    }

    public TransactionRow(List<String> rowValues){
        this.type = rowValues.get(0);
        this.date = rowValues.get(1);
        this.amount = rowValues.get(2);
        this.fee = rowValues.get(3);
        this.status = rowValues.get(4);
        this.action = rowValues.get(5);
        this.total = rowValues.get(6);
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getFee() {
        return fee;
    }

    public String getStatus() {
        return status;
    }

    public String getAction() {
        return action;
    }

    public String getTotal() {
        return total;
    }

    public LocalDateTime getDateAsDateTime() {
        Locale originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
        var result = LocalDateTime.parse(getDate(), DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm a"));
        Locale.setDefault(originalLocale);
        return result;
    }

    public Double getAmountAsDouble() {
        return Double.parseDouble(getAmount().replaceAll("[^0-9.-]", ""));
    }

    public Double getFeeAsDouble() {
        return Double.parseDouble(getFee().replaceAll("[^0-9.-]", ""));
    }

    public Double getTotalAsDouble() {
        return Double.parseDouble(getTotal().replaceAll("[^0-9.-]", ""));
    }


}
