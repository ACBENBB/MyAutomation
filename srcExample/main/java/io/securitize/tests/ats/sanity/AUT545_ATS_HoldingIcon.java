
package io.securitize.tests.ats.sanity;

import io.securitize.infra.api.AtsGatewayAPI;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.AtsMarketState;
import io.securitize.infra.utils.AtsMarketDependent;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT545_ATS_HoldingIcon extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @AtsMarketDependent(expectedState = AtsMarketState.UP)
    @Test(description = "Validates a holding icon is displayed if the investor holds a token", groups = {AtsGroup.SANITY_ATS})
    public void AUT545_ATS_HoldingIcon() {

        String investorEmail = Users.getProperty(UsersProperty.ats_trade_holding_investor_aut545);
        String investorId = Users.getProperty(UsersProperty.ats_trade_holding_investorId_aut545);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);

        startTestLevel(String.format("SID investor - Login via GW API: %s", investorEmail));
        AtsGatewayAPI buyerInvestor = new AtsGatewayAPI(investorEmail, password, investorId);
        endTestLevel();

        startTestLevel(String.format("SID investor - Validate the investor has assets in the balance", investorEmail));
        List<String> listFromGateway = buyerInvestor.getHoldingAssets();
        Assert.assertTrue(listFromGateway.size() > 0, "The investor has no assets in order to display holding icons");
        endTestLevel();

        startTestLevel(String.format("SID investor - Login via WEB: %s", investorEmail));
        loginToSecuritizeId(investorEmail, password);
        endTestLevel();

        startTestLevel("SID investor - Navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("SID investor - Validate the holding icons are displayed");
        validateHoldingIconsAreDisplayed(listFromGateway);
        endTestLevel();

    }
}