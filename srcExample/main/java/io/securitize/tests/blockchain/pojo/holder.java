package io.securitize.tests.blockchain.pojo;

import org.json.*;

import java.util.*;

public class holder {

    private String id;
    private String country;
    private List<attribute> attributes;
    private String wallet;
    private boolean isAffiliate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<attribute> attributes) {
        this.attributes = attributes;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public boolean isAffiliate() {
        return isAffiliate;
    }

    public void setAffiliate(boolean affiliate) {
        isAffiliate = affiliate;
    }
}

