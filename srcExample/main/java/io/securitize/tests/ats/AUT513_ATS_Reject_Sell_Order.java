
package io.securitize.tests.ats;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.AtsMarketState;
import io.securitize.infra.utils.AtsMarketDependent;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import io.securitize.tests.ats.pojo.ATS_BuyOrder;
import io.securitize.tests.ats.pojo.ATS_SellOrder;
import io.securitize.tests.ats.properties.Orders;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT513_ATS_Reject_Sell_Order extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @AtsMarketDependent(expectedState = AtsMarketState.UP)
    @Test(description = "AUT513_ATS_Reject_Sell_Order")
    public void AUT513_ATS_Reject_Sell_Order() {

        String buyerInvestorMail = Users.getProperty(UsersProperty.ats_trade_buyInvestor_aut128);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);
        ATS_BuyOrder atsBuyOrder = new ATS_BuyOrder(3.00, 1.00);
        ATS_SellOrder atsSellOrder = new ATS_SellOrder(3.00, 1.00);

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

        startTestLevel("Buy SID investor - Create sell order");
        createSellOrder(atsSellOrder.getSellPrice(), atsSellOrder.getSellQuantity());
        endTestLevel();

        startTestLevel("Sell SID investor - Create buy order");
        createBuyOrder(atsBuyOrder.getBuyPrice(), atsBuyOrder.getBuyQuantity());
        assertBuyOrderStatusInHistoryTab(atsBuyOrder, Orders.FE_STATUS.REJECTED.getStatus());
        endTestLevel();

        startTestLevel(String.format("Buy SID investor - Cancel all Open orders for the buyer investor: %s", buyerInvestorMail));
        cancelAllActiveOrders();
        endTestLevel();

        startTestLevel("Sell SID investor - Logout");
        logoutAts();
        endTestLevel();

    }
}
