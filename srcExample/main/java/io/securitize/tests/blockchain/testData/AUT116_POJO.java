package io.securitize.tests.blockchain.testData;

import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;

public class AUT116_POJO {

    public final String deploymentId;
    public final String sellerSecId;
    public final String buyerSecId;
    public final int toSellAmount;
    public final int toBuyAmount;
    public final int timeout;

    public AUT116_POJO() {

        // Partial Match
        this.toSellAmount = 2;
        this.toBuyAmount = 1;
        this.timeout = 60;
        deploymentId = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut116_deploymentId.name());
        sellerSecId = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut116_sellerSecId.name());
        buyerSecId = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut116_buyerSecId.name());

    }

}