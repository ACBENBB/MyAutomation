package io.securitize.tests.blockchain.pojo;

import org.json.*;

public class body {

    private String identity;
    private holder holder;
    private String value;
    private boolean investorPays;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public io.securitize.tests.blockchain.pojo.holder getHolder() {
        return holder;
    }

    public void setHolder(io.securitize.tests.blockchain.pojo.holder holder) {
        this.holder = holder;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getInvestorPays() {
        return investorPays;
    }

    public void setInvestorPays(boolean investorPays) {
        this.investorPays = investorPays;
    }
}
