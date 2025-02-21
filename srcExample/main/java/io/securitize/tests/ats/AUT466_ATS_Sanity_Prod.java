package io.securitize.tests.ats;

import io.securitize.infra.config.*;
import io.securitize.infra.utils.*;
import io.securitize.tests.ats.abstractClass.*;
import io.securitize.tests.ats.constants.AtsGroup;
import org.testng.annotations.*;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT466_ATS_Sanity_Prod extends AbstractATS {


    @SkipTestOnEnvironments(environments = {"rc", "sandbox"})
    @Test(description = "AUT466_ATS_Sanity_Prod", groups = {AtsGroup.SANITY_ATS})
    public void AUT466_ATS_Sanity_Prod() {

        String investorMail = Users.getProperty(UsersProperty.SecID_IntegrityCheckProdATS_Email);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = "SPiCE VC";
        String symbol = "Spice";

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
        searchAssetInAssetCatalogPage(tokenName);
        endTestLevel();

        startTestLevel("Validate Search Results Count");
        validateSearchResultsCount(1);
        endTestLevel();

        startTestLevel("Select Secondary Market Token By Name");
        selectSecondaryMarketTokenByName(tokenName);
        endTestLevel();

        /* This code is commented out until we get an investor in prod that owns tokens
        startTestLevel("Validate Asset BuySell Order Count Greater Than");
        validateAssetBuySellOrderCountGreaterThan(1);
        endTestLevel();
        */

        startTestLevel("Validate Chart is visible");
        assertChartIsVisible(symbol);
        endTestLevel();

        startTestLevel("Click Trades Tab");
        clickTradesTab();
        endTestLevel();

        startTestLevel("Validate trades are listed");
        assertTradesAreListed();
        endTestLevel();

        startTestLevel("Click My Orders Tab");
        clickMyOrdersTab();
        endTestLevel();

        // This validation must be updated once we get an investor in prod that owns tokens
        startTestLevel("Validate No Active Orders");
        validateNoActiveOrders();
        endTestLevel();

        startTestLevel("Click History Tab");
        clickHistoryTab();
        endTestLevel();

        startTestLevel("Click Asset Info Tab");
        clickAssetInfoTab();
        endTestLevel();

        startTestLevel("Validate Asset Info Tab");
        validateAssetInfoTab(tokenName);
        endTestLevel();

        /* Disabled due to buy button disabled.
        startTestLevel("Validate Buy Modal Loaded");
        validateBuyModalLoaded();
        endTestLevel();*/

        //Commented out due to the 'locked' balance issue.
        //startTestLevel("Validate Sell Modal Loaded");
        //validateSellModalLoaded();
        //endTestLevel();
    }

}