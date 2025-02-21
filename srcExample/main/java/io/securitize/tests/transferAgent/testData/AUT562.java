package io.securitize.tests.transferAgent.testData;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;

public class AUT562 {

    public String issuerId;
    public String tokenId;
    public String investorId;
    public String wallet;
    public String pk;
    public String transactionHash;
    public String investorEmail;
    public String tokenPrice;

    public AUT562() {

        issuerId = Users.getProperty(UsersProperty.ta_distribution_issuerId_aut365);
        tokenId = Users.getProperty(UsersProperty.ta_issuance_tokenId_aut562);
        investorId = Users.getProperty(UsersProperty.ta_issuance_investorId_aut562);
        wallet = Users.getProperty(UsersProperty.ta_issuance_wallet_address_aut562);
        pk = Users.getProperty(UsersProperty.ta_issuance_wallet_pk_aut562);
        investorEmail = Users.getProperty(UsersProperty.ta_issuance_investor_email_aut562);

    }

}