package io.securitize.tests.apiTests.controlPanel;

import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.Users;
import io.securitize.infra.enums.BlockchainType;
import io.securitize.tests.apiTests.abstractClass.AbstractCpApiRegistration;
import org.testng.annotations.Test;

public class AUT169_ISR_API_Registration_Flow_Algorand extends AbstractCpApiRegistration {

    @Test(description = "AUT169 - Validate control panel registration apis - Algorand")
    public void AUT169_ISR_API_Registration_Flow_Algorand_test() {

        final int ISSUED_REGULAR_TOKENS_AMOUNT = 5;
        String issuerName = "NieAlgo";
        String tokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);

        controlPanelRegistrationFlow(BlockchainType.ALGORAND, issuerName, tokenName, ISSUED_REGULAR_TOKENS_AMOUNT, 0);
    }
}