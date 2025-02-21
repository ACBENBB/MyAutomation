
package io.securitize.tests.ats;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket.TradingSecondaryMarketsAssetDetail;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT655_ATS_LimitedTrading extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @Test(description = "AUT655_ATS_LimitedTrading")
    public void AUT655_ATS_LimitedTrading() {

        String investorEmail = Users.getProperty(UsersProperty.ats_trade_sellInvestor_aut128); // Sell investor
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut655);

        startTestLevel(String.format("SID investor - Login via WEB API: %s", investorEmail));
        loginToSecuritizeId(investorEmail, password);
        endTestLevel();

        startTestLevel("SID investor - navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("SID investor - Set 'REGULAR' trading");
        setRegularTrading(tokenName);
        endTestLevel();

        startTestLevel("SID investor - Select Token");
        selectSecondaryMarketTokenByName(tokenName);
        endTestLevel();

        startTestLevel("SID investor - Is buy button enabled");
        TradingSecondaryMarketsAssetDetail secondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        Assert.assertTrue(secondaryMarketsAssetDetail.isBuyButtonEnabled(), "The 'Buy' button is not enabled");
        endTestLevel();

        startTestLevel("SID investor - Set 'LIMITED_TRADING' trading to end in five minutes from now");
        long currentTimeMillis = System.currentTimeMillis();
        long fiveMinutesInMillis = DateTimeUtils.getMinutesToMillis(currentTimeMillis, 5);
        setLimitedTrading(tokenName, System.currentTimeMillis(), fiveMinutesInMillis);
        endTestLevel();

        startTestLevel("SID investor - Refresh page to see limited trading banner");
        getBrowser().refreshPage(true, false);
        endTestLevel();

        startTestLevel("SID investor - Validate 'Limited trading' banner is visible ");
        Assert.assertTrue(secondaryMarketsAssetDetail.isLimitedTradingBannerVisible(), "The 'Limited trading banner' is not visible");
        endTestLevel();

        startTestLevel("SID investor - Set 'REGULAR' trading");
        setRegularTrading(tokenName);
        endTestLevel();

        startTestLevel("SID investor - Refresh page to check 'Limited trading' banner is not visible");
        getBrowser().refreshPage();
        endTestLevel();

        startTestLevel("SID investor - Validate 'Limited trading' banner is not visible");
        Assert.assertFalse(secondaryMarketsAssetDetail.isLimitedTradingBannerVisible(), "The 'Limited trading banner' is not visible");
        endTestLevel();

        startTestLevel("SID investor - Logout");
        logoutAts();
        endTestLevel();

    }

}