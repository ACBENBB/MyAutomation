package io.securitize.tests.transferAgent.testData;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;

public class AUT365 {

    public String issuerId;
    public String tokenId;
    public DistributionsInvestorData investorData;
    public DistributionData distributionData;
    public String issuerIdCashAccountId;

    public AUT365() {

        this.issuerId = Users.getProperty(UsersProperty.ta_distribution_issuerId_aut365);
        this.tokenId = Users.getProperty(UsersProperty.ta_distribution_tokenId_aut365);
        this.issuerIdCashAccountId = Users.getProperty(UsersProperty.ta_distribution_issuer_CashAccount_Sid_aut365);

    }

}