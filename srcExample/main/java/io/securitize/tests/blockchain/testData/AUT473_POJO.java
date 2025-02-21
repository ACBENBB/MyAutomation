package io.securitize.tests.blockchain.testData;
import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;

public class AUT473_POJO {
    private String id;
    private String country;
    private String wallet;
    private String identity;
    private String value;
    private String investorPays;
    private String deploymentId;

    public AUT473_POJO(){
        this.id = "123123123";
        this.country = "US";
        this.wallet = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut473_wallet.name());
        this.identity = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut473_identity.name());
        this.value = "1";
        this.investorPays = "true";
        this.deploymentId = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut473_deploymentId.name());
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getCountry() {return country;}

    public void setCountry(String country) {this.country = country;}

    public String getWallet() {return wallet;}

    public void setWallet(String wallet) {this.wallet = wallet;}

    public String getIdentity() {return identity;}

    public void setIdentity(String identity) {this.identity = identity;}

    public String getValue() {return value;}

    public void setValue(String value) {this.value = value;}

    public String getInvestorPays() {return investorPays;}

    public void setInvestorPays(String investorPays) {this.investorPays = investorPays;}

    public String getDeploymentId() {return deploymentId;}

    public void setDeploymentId(String deploymentId) {this.deploymentId = deploymentId;}
}
