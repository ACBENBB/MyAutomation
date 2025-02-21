package io.securitize.tests.blockchain.testData;

import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.UsersProperty;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

public class AUT696_POJO {
    public static final String ISSUER_ID = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut696_issuerId.name());
    public static final String TOKEN_ID = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut696_tokenId.name());
    public static final String INVESTOR_ID = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut696_investorId.name());
    public static final String USER_FIRST_NAME = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut696_userFirstName.name());
    public static final String SIGNER_ADDRESS = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut696_signerAddress.name());
    public static final String PRIVATE_KEY = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut696_privateKey.name());
    public static final String TOKEN_AMOUNT = "1";
    public static final String INVESTOR_WALLET = DataManager.getInstance("bc").getAwsProperty(UsersProperty.aut696_investorWallet.name());


}
