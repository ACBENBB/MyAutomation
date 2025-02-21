package io.securitize.tests.apiTests.controlPanel;

import io.securitize.infra.enums.BlockchainType;
import io.securitize.tests.apiTests.abstractClass.AbstractCpApiRegistration;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class AUT150_ISR_API_Registration_Flow_And_Control_Flow extends AbstractCpApiRegistration {

    @Test(description = "AUT150 - Validate control panel registration apis - Eth")
    public void AUT150_ISR_API_Registration_Flow_And_Control_Flow_test() {

        final int ISSUED_REGULAR_TOKENS_AMOUNT = 5;
        final int ISSUED_TBE_TOKENS_AMOUNT = 6;
        String issuerName = "Nie";
        String tokenName = getTokenName(issuerName);

        final int initialIssuedSecurities = cpGetControlBookInfo(issuerName, tokenName).getInt("issuedSecurities");
        final int initialTotalIssuedToBlockchain = cpGetControlBookInfo(issuerName, tokenName).getInt("totalIssuedToBlockchain");
        final int initialTotalIssuedToDRSTokens = cpGetControlBookInfo(issuerName, tokenName).getInt("totalIssuedToDRSTokens");
        final int totalIssuedTokens = ISSUED_REGULAR_TOKENS_AMOUNT + ISSUED_TBE_TOKENS_AMOUNT;

        controlPanelRegistrationFlow(BlockchainType.ETHEREUM, issuerName, tokenName, ISSUED_REGULAR_TOKENS_AMOUNT, ISSUED_TBE_TOKENS_AMOUNT);
        validateControlBookTokenAmount(issuerName, tokenName, initialTotalIssuedToBlockchain, ISSUED_REGULAR_TOKENS_AMOUNT, "totalIssuedToBlockchain");
        validateControlBookTokenAmount(issuerName, tokenName, initialTotalIssuedToDRSTokens, ISSUED_TBE_TOKENS_AMOUNT, "totalIssuedToDRSTokens");
        validateControlBookTokenAmount(issuerName, tokenName, initialIssuedSecurities, totalIssuedTokens, "issuedSecurities");

        //validate authorized securities amount is bigger than total issued securities
        String authorizedSecuritiesAsString = cpGetControlBookInfo(issuerName, tokenName).getString("authorizedSecurities");
        long authorizedSecurities = Long.parseLong(authorizedSecuritiesAsString);
        assertTrue("Total Issued Tokens cannot be greater than Authorized Tokens", authorizedSecurities > totalIssuedTokens);
    }
}