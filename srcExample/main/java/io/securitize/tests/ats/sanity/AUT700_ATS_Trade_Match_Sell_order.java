
package io.securitize.tests.ats.sanity;

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
import io.securitize.tests.ats.pojo.ATSOrder;
import io.securitize.tests.ats.pojo.ATS_SellOrder;
import io.securitize.tests.ats.properties.Orders;
import io.securitize.tests.ats.queries.ATSOrdersQuery;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT700_ATS_Trade_Match_Sell_order extends AbstractATS {

    @SkipTestOnEnvironments(environments = {"production"})
    @AtsMarketDependent(expectedState = AtsMarketState.UP)
    @AtsRegionDependent(expectedState = AtsRegionState.US_OPEN)
    @Test(description = "The investor creates and matches a sell order", groups = {AtsGroup.SANITY_ATS})
    public void AUT700_ATS_Trade_Match_Sell_order() {


        String buyerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_buyInvestor_aut128); // Buy investor
        String buyerInvestorId = Users.getProperty(UsersProperty.ats_trade_buyInvestorId_aut128); // Buy investor
        String sellerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_sellInvestor_aut128); // Sell investor
        String sellerInvestorId = Users.getProperty(UsersProperty.ats_trade_sellInvestorId_aut128); // Sell investor
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);

        Assert.assertTrue(isAssetEnabledForTrading(tokenName), String.format("The asset '%s' is not enabled for trading", tokenName));

        ATS_SellOrder ats_sellOrder = new ATS_SellOrder(1.00, 1.00);

        startTestLevel(String.format("Buy SID investor - Login via GW API: %s", buyerInvestorEmail));
        AtsGatewayAPI buyerInvestorGw = new AtsGatewayAPI(buyerInvestorEmail, password, buyerInvestorId);
        endTestLevel();

        startTestLevel(String.format("Sell SID investor - Login via GW API: %s", sellerInvestorEmail));
        AtsGatewayAPI sellerInvestorGw = new AtsGatewayAPI(sellerInvestorEmail, password, sellerInvestorId);
        endTestLevel();

        startTestLevel(String.format("Cancelling active orders of: %s", buyerInvestorEmail));
        buyerInvestorGw.cancelActiveOrders();
        endTestLevel();

        startTestLevel(String.format("Cancelling active orders of: %s", sellerInvestorGw));
        sellerInvestorGw.cancelActiveOrders();
        endTestLevel();

        startTestLevel(String.format("Create BUY order for: %s", buyerInvestorEmail));
        String price = "1";
        String quantity = "1";
        String buyerFeePercentage = atsAssetAPI.getAssetFee(tokenName, "buyer");
        HashMap<String, String> buyOrder = buyerInvestorGw.createOrder(tokenName, Orders.SIDE.B.name(), quantity, price);
        ATSOrder atsOrder = new ATSOrder(buyOrder.get(Orders.PROPERTIES.orderId.name()), buyOrder.get(Orders.PROPERTIES.referenceNumber.name()));
        atsOrder = ATSOrdersQuery.findOrderByOrderIdAndReferenceNumber(atsOrder);
        Assert.assertEquals(price, atsOrder.getPrice(), "The price does not match the DB");
        Assert.assertEquals(quantity, atsOrder.getQuantity(), "The quantity does not match the DB");
        Assert.assertEquals(buyerFeePercentage, atsOrder.getFeePercentage(), "The fee percentage does not match the DB");
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

        String sellerFeePercentage = atsAssetAPI.getAssetFee(tokenName, "seller");
        String previousOrderId = getLastOrder(sellerInvestorGw, tokenName, "closed");
        createSellOrder(ats_sellOrder.getSellPrice(), ats_sellOrder.getSellQuantity());
        String latestOrderId = getLastOrder(sellerInvestorGw, tokenName, "closed");
        ATSOrder atsSellerOrder = ATSOrdersQuery.findOrderByOrderId(latestOrderId);

        Assert.assertEquals(price, atsSellerOrder.getPrice(), "The price does not match the DB");
        Assert.assertEquals(quantity, atsSellerOrder.getQuantity(), "The quantity does not match the DB");
        Assert.assertEquals(sellerFeePercentage, atsSellerOrder.getFeePercentage(), "The fee percentage does not match the DB");
        assertSellOrderStatusInHistoryTab(ats_sellOrder, Orders.FE_STATUS.DONE.getStatus(), previousOrderId, latestOrderId);
        endTestLevel();

    }
}