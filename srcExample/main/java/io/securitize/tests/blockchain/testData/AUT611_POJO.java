package io.securitize.tests.blockchain.testData;

import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;

public class AUT611_POJO {

    public String deploymentId = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut611_deploymentId.name());
    public String sellerSecId = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut611_sellerSecId.name());
    public String buyerSecId = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut611_buyerSecId.name());

    public String sellOrderAmount = "2";
    public String sellOrderId = "";

    public String buyOrderAmount = "2";
    public String buyOrderId = "";

    public AUT611_POJO() {
        // TODO
    }

/*
    // TEST INVESTORS.
    646f2e98a2d9e311d0418422
    https://cp.dev.securitize.io/securitize-id/646f2e98a2d9e311d0418422
    canada
    646f2fa6483f2d0003fd9e4e
    https://cp.dev.securitize.io/securitize-id/646f2fa6483f2d0003fd9e4e
    buyer usa
    646f30c2a2d9e311d04189a6
    https://cp.dev.securitize.io/securitize-id/646f30c2a2d9e311d04189a6
    seller usa
    646f3235a2d9e311d0418b4e
    https://cp.dev.securitize.io/securitize-id/646f3235a2d9e311d0418b4e
    buyer eu
    646f327aa2d9e311d0418c33
    https://cp.dev.securitize.io/securitize-id/646f327aa2d9e311d0418c33
    seller eu
    646f32bd483f2d0003fda4e1
    https://cp.dev.securitize.io/securitize-id/646f32bd483f2d0003fda4e1
 */

}