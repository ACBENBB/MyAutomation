package io.securitize.tests.ats.sanity;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT686_ATS_SearchAsset extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @Test(description = "AUT686_ATS_SearchAsset", groups = {AtsGroup.SANITY_ATS})
    public void AUT686_ATS_SearchAsset() {

        String investorMail = Users.getProperty(UsersProperty.ats_default_investor);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);

        startTestLevel("Login To SecuritizeId");
        loginToSecuritizeId(investorMail, password);
        endTestLevel();

        startTestLevel("Navigate To Secondary Market");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("Close PreMarket Session PopUp If Visible");
        closePreMarketSessionPopUpIfVisible();
        endTestLevel();

        startTestLevel("Search Asset In Asset Catalog Page");
        String assetName = getFirstAssetName();
        searchAssetInAssetCatalogPage(assetName);
        endTestLevel();

        startTestLevel("Validate Search Results Count");
        validateSearchResultsCount(1);
        endTestLevel();

    }

}