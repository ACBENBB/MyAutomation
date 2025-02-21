
package io.securitize.tests.ats;

import io.securitize.infra.api.AtsGatewayAPI;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import org.testng.annotations.Test;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT553_ATS_AssetsAreDisplayedInTheCorrectMarket extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @Test(description = "AUT553_ATS_AssetsAreDisplayedInTheCorrectMarket")
    public void AUT553_ATS_AssetsAreDisplayedInTheCorrectMarket() {

        startTestLevel("Get US investor information");
        String usInvestorEmail = Users.getProperty(UsersProperty.ats_trade_investor_aut553_us);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        AtsGatewayAPI usInvestorGateway = new AtsGatewayAPI(usInvestorEmail, password);
        List<String> usAssetsList = usInvestorGateway.getAssetsByMarketAsList("us");
        endTestLevel();

        startTestLevel(String.format("SID investor - Login via WEB: %s", usInvestorEmail));
        loginToSecuritizeId(usInvestorEmail, password);
        endTestLevel();

        startTestLevel("SID investor - Navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("Validate EU market is not displayed");
        validateMarketsCount(1);
        endTestLevel();

        startTestLevel("Validate Spain Market information");
        validateMarketAssets(usAssetsList, "us");
        endTestLevel();

        startTestLevel("SID investor - Logout");
        logoutAts();
        endTestLevel();

        startTestLevel("Get Spain investor information");
        String spainInvestorEmail = Users.getProperty(UsersProperty.ats_trade_investor_aut553_spain);
        AtsGatewayAPI spainInvestorGateway = new AtsGatewayAPI(spainInvestorEmail, password);
        List<String> euAssetsList = spainInvestorGateway.getAssetsByMarketAsList("eu");
        endTestLevel();

        startTestLevel(String.format("SID investor - Login via WEB: %s", spainInvestorEmail));
        loginToSecuritizeId(spainInvestorEmail, password);
        endTestLevel();

        startTestLevel("SID investor - Navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("Validate US and EU markets are displayed");
        validateMarketsCount(2);
        endTestLevel();

        startTestLevel("Validate Spain Market information");
        validateMarketAssets(euAssetsList, "eu");
        endTestLevel();

        startTestLevel("SID investor - Logout");
        logoutAts();
        endTestLevel();

    }

}