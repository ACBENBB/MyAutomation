
package io.securitize.tests.ats;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT544_ATS_TokenTradeOnlyAvailableForAccreditedInvestors extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @Test(description = "AUT544_ATS_TokenTradeOnlyAvailableForAccreditedInvestors")
    public void AUT544_ATS_TokenTradeOnlyAvailableForAccreditedInvestors() {

        String buyerInvestorMail = Users.getProperty(UsersProperty.ats_trade_not_accredited_investor_aut544); // buy investor
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);

        startTestLevel("Buy SID investor - Login using email and password");
        loginToSecuritizeId(buyerInvestorMail, password);
        endTestLevel();

        startTestLevel("Buy SID investor - Navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("Buy SID investor - Select token");
        selectSecondaryMarketTokenByName(tokenName);
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Validate Buy button is disabled: %s", buyerInvestorMail));
        Assert.assertFalse(isBuyButtonEnabled());
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Validate Sell button is disabled: %s", buyerInvestorMail));
        Assert.assertFalse(isSellButtonEnabled());
        endTestLevel();

    }

}