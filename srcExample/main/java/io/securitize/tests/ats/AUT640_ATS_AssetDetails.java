
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
import io.securitize.tests.ats.pojo.ATS_SellOrder;
import io.securitize.tests.ats.properties.Orders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT640_ATS_AssetDetails extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @AtsMarketDependent(expectedState = AtsMarketState.UP)
    @AtsRegionDependent(expectedState = AtsRegionState.US_OPEN)
    @Test(description = "AUT640_ATS_AssetDetails")
    public void AUT640_ATS_AssetDetails() {
        String buyerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_buyInvestor_aut128); // Buy investor
        String buyerInvestorId = Users.getProperty(UsersProperty.ats_trade_buyInvestorId_aut128); // Buy investor
        String sellerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_sellInvestor_aut128); // Sell investor
        String sellerInvestorId = Users.getProperty(UsersProperty.ats_trade_sellInvestorId_aut128); // Sell investor
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);

        Assert.assertTrue(isAssetEnabledForTrading(tokenName), String.format("The asset '%s' is not enabled for trading", tokenName));

        ATS_SellOrder ats_sellOrder = new ATS_SellOrder(1.00, 1.00);

        startTestLevel(String.format("Buy SID investor - Login via GW API: %s", buyerInvestorEmail));
        AtsGatewayAPI buyerInvestor = new AtsGatewayAPI(buyerInvestorEmail, password, buyerInvestorId);
        endTestLevel();

        startTestLevel(String.format("Sell SID investor - Login via GW API: %s", sellerInvestorEmail));
        AtsGatewayAPI sellerInvestor = new AtsGatewayAPI(buyerInvestorEmail, password, sellerInvestorId);
        endTestLevel();

        startTestLevel(String.format("Cancelling active orders of: %s", buyerInvestorEmail));
        buyerInvestor.cancelActiveOrders();
        endTestLevel();

        startTestLevel(String.format("Cancelling active orders of: %s", sellerInvestor));
        sellerInvestor.cancelActiveOrders();
        endTestLevel();

        startTestLevel(String.format("Create BUY order for: %s", buyerInvestorEmail));
        HashMap<String, String> buyOrder = buyerInvestor.createOrder(tokenName, Orders.SIDE.B.name(), "1", "1");
        endTestLevel();

        startTestLevel(String.format("Sell SID investor - Login via WEB API: %s", sellerInvestorEmail));
        loginToSecuritizeId(sellerInvestorEmail, password);
        endTestLevel();

        startTestLevel("Sell SID investor - navigate to Trading screen");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("Sell SID investor - Select Token");
        selectSecondaryMarketTokenByName(tokenName);
        endTestLevel();

        startTestLevel("Sell SID investor - Create sell order");
        createSellOrder(ats_sellOrder.getSellPrice(), ats_sellOrder.getSellQuantity());
        assertSellOrderStatusInHistoryTab(ats_sellOrder, Orders.FE_STATUS.DONE.getStatus());
        endTestLevel();

        //AUT640
        startTestLevel("Validate Last, open, close, high and low values");
        verifyAssetDetailsValues();
        endTestLevel();

        startTestLevel("Sell SID investor - Logout");
        logoutAts();
        endTestLevel();

    }

}