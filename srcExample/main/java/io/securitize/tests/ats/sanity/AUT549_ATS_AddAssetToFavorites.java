
package io.securitize.tests.ats.sanity;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.AtsMarketState;
import io.securitize.infra.utils.AtsMarketDependent;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT549_ATS_AddAssetToFavorites extends AbstractATS {

    @Test(description = "Adds an asset to favorites list", groups = {AtsGroup.SANITY_ATS})
    public void AUT549_ATS_AddAssetToFavorites() {

        String investorEmail = Users.getProperty(UsersProperty.ats_default_investor);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_default_tokenSymbol);

        startTestLevel(String.format("SID investor - Login via WEB: %s", investorEmail));
        loginToSecuritizeId(investorEmail, password);
        endTestLevel();

        startTestLevel("SID investor - Navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("SID investor - Add token to favorites");
        addAssetToWatchlist(tokenName);
        endTestLevel();

        startTestLevel("SID investor - Go to asset details page");
        selectSecondaryMarketTokenByName(tokenName);
        endTestLevel();

        startTestLevel("SID investor - Validate asset is in watchlist");
        validateAssetIsInWatchlist();
        endTestLevel();

    }

}