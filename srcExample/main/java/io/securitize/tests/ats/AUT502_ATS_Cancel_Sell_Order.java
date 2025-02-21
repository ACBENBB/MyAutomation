
package io.securitize.tests.ats;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.AtsMarketState;
import io.securitize.infra.utils.AtsMarketDependent;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import io.securitize.tests.ats.pojo.ATS_SellOrder;
import io.securitize.tests.ats.properties.Orders;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT502_ATS_Cancel_Sell_Order extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @AtsMarketDependent(expectedState = AtsMarketState.UP)
    @Test(description = "AUT502_ATS_Cancel_Sell_Order")
    public void AUT502_ATS_Cancel_Sell_Order() {

        String sellerInvestorMail = Users.getProperty(UsersProperty.ats_trade_sellInvestor_aut128); // buy investor
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);
        ATS_SellOrder atsSellOrder = new ATS_SellOrder(3.00, 1.00);

        startTestLevel("Buy SID investor - Login using email and password");
        loginToSecuritizeId(sellerInvestorMail, password);
        endTestLevel();

        startTestLevel("Buy SID investor - Navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("Buy SID investor - Select token");
        selectSecondaryMarketTokenByName(tokenName);
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Cancel all Open orders for the buyer investor: %s", sellerInvestorMail));
        cancelAllActiveOrders();
        endTestLevel();

        startTestLevel("Buy SID investor - Create buy order");
        createSellOrder(atsSellOrder.getSellPrice(), atsSellOrder.getSellQuantity());
        assertSellOrderCreated(atsSellOrder);
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Cancel all Open orders for the buyer investor: %s", sellerInvestorMail));
        Assert.assertTrue(cancelFirstActiveOrder(), "Unable to cancel a Buy order");
        endTestLevel();

        assertSellOrderStatusInHistoryTab(atsSellOrder, Orders.FE_STATUS.CANCELLED.getStatus());

        startTestLevel("Buy SID investor - Logout");
        logoutAts();
        endTestLevel();

    }
}
