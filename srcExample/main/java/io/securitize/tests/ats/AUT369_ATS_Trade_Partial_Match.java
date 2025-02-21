package io.securitize.tests.ats;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.AtsMarketState;
import io.securitize.infra.enums.AtsRegionState;
import io.securitize.infra.utils.AtsMarketDependent;
import io.securitize.infra.utils.AtsRegionDependent;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import io.securitize.tests.ats.pojo.ATS_BuyOrder;
import io.securitize.tests.ats.pojo.ATS_SellOrder;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT369_ATS_Trade_Partial_Match extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @AtsMarketDependent(expectedState = AtsMarketState.UP)
    @AtsRegionDependent(expectedState = AtsRegionState.US_OPEN)
    @Test(description = "AUT369 - Secondary Market - Perform and validate partial matches")
    public void AUT369_ATS_Perform_partial_matches() {

        String buyerInvestorMail = Users.getProperty(UsersProperty.ats_trade_buyInvestor_aut128);
        String sellerInvestorMail = Users.getProperty(UsersProperty.ats_trade_sellInvestor_aut128);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);
        ATS_BuyOrder atsBuyOrder = new ATS_BuyOrder(3.00, 1.00);
        ATS_SellOrder atsSellOrder = new ATS_SellOrder(3.00, 2.00);

        startTestLevel("Buy SID investor - Login using email and password");
        loginToSecuritizeId(buyerInvestorMail, password);
        endTestLevel();

        startTestLevel("Buy SID investor - Navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Select token: %s", tokenName));
        selectSecondaryMarketTokenByName(tokenName);
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Cancel all Open orders for the buyer investor: %s", buyerInvestorMail));
        cancelAllActiveOrders();
        endTestLevel();

        startTestLevel("Buy SID investor - Create buy order");
        createBuyOrder(atsBuyOrder.getBuyPrice(), atsBuyOrder.getBuyQuantity());
        assertBuyOrderCreated(atsBuyOrder);
        endTestLevel();

        startTestLevel("Buy SID investor - Logout");
        logoutAts();
        endTestLevel();

        startTestLevel("Sell SID investor - login");
        loginToSecuritizeId(sellerInvestorMail, password);
        endTestLevel();

        startTestLevel("Sell SID investor - navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Select token: %s", tokenName));
        selectSecondaryMarketTokenByName(tokenName);
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Cleaning up Open orders for the seller investor: %s", sellerInvestorMail));
        cancelAllActiveOrders();
        endTestLevel();

        startTestLevel("Sell SID investor - Create sell order");
        createSellOrder(atsSellOrder.getSellPrice(), atsSellOrder.getSellQuantity());
        assertOpenSellOrderCreated(atsSellOrder, 1.00);
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Cancel all Open orders for the seller investor: %s", sellerInvestorMail));
        cancelAllActiveOrders();
        endTestLevel();

        startTestLevel("Sell SID investor - Create sell order");
        assertCancelledSellOrderCreated(atsSellOrder, 1.00);
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Cleaning up Open orders for the next execution for the investor: %s", sellerInvestorMail));
        cancelAllActiveOrders();
        endTestLevel();

        startTestLevel("Sell SID investor - Logout");
        logoutAts();
        endTestLevel();

    }
}