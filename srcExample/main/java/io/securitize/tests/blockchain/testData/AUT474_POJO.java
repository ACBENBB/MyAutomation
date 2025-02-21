package io.securitize.tests.blockchain.testData;
import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;

public class AUT474_POJO {
    private String kyc;
    private String accredited;
    private String qualified;
    private String id;
    private String country;
    private String wallet;
    private String identity;
    private String purchaseAmount;
    private String documentHash;
    private String value;
    private String investorPays;
    private String deploymentId;

    public AUT474_POJO(){
        this.kyc="approved";
        this.accredited="pending";
        this.qualified="rejected";
        this.id = "787521da-9f10-11ec-b909-0242ac120002";
        this.country = "US";
        this.wallet = "0x7C33Ae400aEF6B0c8AbBB143f642b422d825a9Cd";
        this.identity = "0x7C33Ae400aEF6B0c8AbBB143f642b422d825a9Cd";
        this.purchaseAmount = "0";
        this.documentHash = "0x33027547537d35728a741470df1ccf65de10b454ca0def7c5c20b257b7b8d161";
        this.value = "1";
        this.investorPays = "true";
        this.deploymentId = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut474_deploymentId.name());
    }

    public String getKyc() {
        return kyc;
    }

    public String getAccredited() {
        return accredited;
    }

    public String getQualified() {
        return qualified;
    }

    public String getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getWallet() {
        return wallet;
    }

    public String getIdentity() {
        return identity;
    }

    public String getPurchaseAmount() {
        return purchaseAmount;
    }

    public String getDocumentHash() {
        return documentHash;
    }

    public String getValue() {
        return value;
    }

    public String getInvestorPays() {
        return investorPays;
    }

    public String getDeploymentId() {
        return deploymentId;
    }
}
