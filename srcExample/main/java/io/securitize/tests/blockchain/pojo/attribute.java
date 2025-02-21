package io.securitize.tests.blockchain.pojo;

import io.securitize.infra.config.*;

public class attribute {

    private String key;
    private String value;

    public attribute(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}


