package io.securitize.tests.blockchain.testData;

import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;

public class AUT404_POJO {

    private String identity;
    private String holderId;
    private String wallet;
    private String investorPays;
    private String deploymentId;

    public AUT404_POJO() {

        this.identity = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut404_identity.name());
        this.holderId = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut404_holderId.name());
        this.wallet = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut404_wallet.name());
        this.investorPays = "true";
        this.deploymentId = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut404_deploymentId.name());


    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getHolderId() {
        return holderId;
    }

    public void setHolderId(String holderId) {
        this.holderId = holderId;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getInvestorPays() {
        return investorPays;
    }

    public void setInvestorPays(String investorPays) {
        this.investorPays = investorPays;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }
}
