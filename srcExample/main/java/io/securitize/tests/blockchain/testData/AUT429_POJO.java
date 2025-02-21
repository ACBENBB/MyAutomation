package io.securitize.tests.blockchain.testData;

import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;

public class AUT429_POJO {
    private String securitizeId;
    private String partnersId;
    private String wallet;

    public AUT429_POJO(){
        this.securitizeId   = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut429_securitizeId.name());
        this.partnersId     = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut429_partnersId.name());
        this.wallet         = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut429_wallet.name());
    }

    public String getSecuritizeId() {
        return securitizeId;
    }

    public String getPartnersId() {
        return partnersId;
    }

    public String getWallet() {
        return wallet;
    }
}
