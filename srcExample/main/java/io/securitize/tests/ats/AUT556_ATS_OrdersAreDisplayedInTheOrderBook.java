
package io.securitize.tests.ats;

import io.securitize.infra.api.AtsGatewayAPI;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.AtsMarketState;
import io.securitize.infra.enums.AtsRegionState;
import io.securitize.infra.utils.AtsMarketDependent;
import io.securitize.infra.utils.AtsRegionDependent;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import io.securitize.tests.ats.properties.Orders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT556_ATS_OrdersAreDisplayedInTheOrderBook extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @AtsMarketDependent(expectedState = AtsMarketState.UP)
    @AtsRegionDependent(expectedState = AtsRegionState.US_OPEN)
    @Test(description = "AUT556_ATS_OrdersAreDisplayedInTheOrderBook")
    public void AUT556_ATS_OrdersAreDisplayedInTheOrderBook() {


        String investorEmail = Users.getProperty(UsersProperty.ats_trade_investor_aut556);
        String investorId = Users.getProperty(UsersProperty.ats_trade_investorId_aut556);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut556);

        Assert.assertTrue(isAssetEnabledForTrading(tokenName), String.format("The asset '%s' is not enabled for trading", tokenName));

        startTestLevel(String.format(String.format("Cancel all active orders for: ", tokenName)));
        atsOrderAPI.cancelActiveOrders(tokenName);
        endTestLevel();

        startTestLevel(String.format("SID investor - Login via GW API: %s", investorEmail));
        AtsGatewayAPI buyerInvestor = new AtsGatewayAPI(investorEmail, password, investorId);
        endTestLevel();

        startTestLevel(String.format("SID investor - Login via WEB: %s", investorEmail));
        loginToSecuritizeId(investorEmail, password);
        endTestLevel();

        startTestLevel("SID investor - navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("SID investor - Select Token");
        selectSecondaryMarketTokenByName(tokenName);
        endTestLevel();

        startTestLevel("SID investor - Validate order book is empty");
        Assert.assertTrue(isOrderBookEmpty(), "The order book is not empty. The test cannot continue");
        endTestLevel();

        startTestLevel(String.format("Create BUY order for: %s", investorEmail));
        HashMap<String, String> buyOrder = buyerInvestor.createOrder(tokenName, Orders.SIDE.B.name(), "1", "1");
        endTestLevel();

        startTestLevel("SID investor - Validate BUY order is displayed in the order book");
        isButtonDisplayedInTheOrderBook("Buy");
        endTestLevel();

        startTestLevel("SID investor - Cancel BUY order");
        buyerInvestor.cancelOrder(buyOrder);
        endTestLevel();

        startTestLevel("SID investor - Validate order book is empty");
        Assert.assertTrue(isOrderBookEmpty(), "The order book is not empty. The test cannot continue");
        endTestLevel();

        startTestLevel(String.format("Create SELL order for: %s", investorEmail));
        HashMap<String, String> sellOrder = buyerInvestor.createOrder(tokenName, Orders.SIDE.S.name(), "1", "1");
        endTestLevel();

        startTestLevel("SID investor - Validate SELL order is displayed in the order book");
        isButtonDisplayedInTheOrderBook("Sell");
        endTestLevel();

        startTestLevel("SID investor - Cancel SELL order");
        buyerInvestor.cancelOrder(sellOrder);
        endTestLevel();

        startTestLevel("SID investor - Validate order book is empty");
        Assert.assertTrue(isOrderBookEmpty(), "The order book is not empty. The test cannot continue");
        endTestLevel();

        startTestLevel("Sell SID investor - Logout");
        logoutAts();
        endTestLevel();

    }

}