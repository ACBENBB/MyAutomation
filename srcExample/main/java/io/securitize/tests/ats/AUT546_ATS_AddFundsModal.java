
package io.securitize.tests.ats;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT546_ATS_AddFundsModal extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @Test(description = "AUT546_ATS_AddFundsModal")
    public void AUT546_ATS_AddFundsModal() {

        String investorEmail = Users.getProperty(UsersProperty.ats_trade_buyInvestor_aut128);
        String investorId = Users.getProperty(UsersProperty.ats_trade_buyInvestorId_aut128);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);

        startTestLevel(String.format("SID investor - Get available deposit instructions for investor: %s", investorId));
        List<String> availableDepositOptions = cashAccountConfigAPI.getInvestorAvailableDepositOptions(investorId);
        Assert.assertFalse(availableDepositOptions.isEmpty(), "The there no deposit options available");
        endTestLevel();

        startTestLevel(String.format("SID investor - Login via WEB: %s", investorEmail));
        loginToSecuritizeId(investorEmail, password);
        endTestLevel();

        startTestLevel("SID investor - Navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("SID investor - Select Token");
        selectSecondaryMarketTokenByName(tokenName);
        endTestLevel();

        startTestLevel("SID investor - Open Buy Modal");
        clickBuyButton();
        endTestLevel();

        startTestLevel("SID investor - Open Add funds Modal");
        clickAddFundsButton();
        endTestLevel();

        startTestLevel("SID investor - Validate Deposit Modal");
        validateAddFundsModal(availableDepositOptions);
        endTestLevel();

        startTestLevel("SID investor - Logout");
        logoutAts();
        endTestLevel();

    }

}