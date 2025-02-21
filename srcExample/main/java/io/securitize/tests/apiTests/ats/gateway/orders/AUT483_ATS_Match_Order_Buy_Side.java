
package io.securitize.tests.apiTests.ats.gateway.orders;

import io.securitize.infra.api.AtsGatewayAPI;
import io.securitize.infra.api.AtsOrderAPI;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.AtsMarketState;
import io.securitize.infra.utils.AtsMarketDependent;
import io.securitize.tests.ats.properties.Orders;
import io.securitize.tests.ats.queries.ATSOrdersQuery;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class AUT483_ATS_Match_Order_Buy_Side {

    @Test(description = "Creates a 'Buy' order and match it with a different user.", groups = {"apiSanityATS"})
    @AtsMarketDependent(expectedState = AtsMarketState.UP)
    public void AUT483_ATS_Match_Order_Buy_Side() throws InterruptedException {
        String buyerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_buyInvestor_aut128); // Buy investor
        String buyerInvestorId = Users.getProperty(UsersProperty.ats_trade_buyInvestorId_aut128); // Buy investor
        String sellerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_sellInvestor_aut128); // Sell investor
        String sellerInvestorId = Users.getProperty(UsersProperty.ats_trade_sellInvestorId_aut128); // Sell investor
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);

        AtsGatewayAPI user1 = new AtsGatewayAPI(buyerInvestorEmail, password);
        AtsGatewayAPI user2 = new AtsGatewayAPI(sellerInvestorEmail, password);

        AtsOrderAPI atsOrderAPI = new AtsOrderAPI();
        atsOrderAPI.cancelActiveOrders(tokenName);

        String price = "1";
        String quantity = "1";
        HashMap<String, String> buyOrder = user1.createOrder(tokenName, Orders.SIDE.B.name(), quantity, price);
        String actualBuyStatus = ATSOrdersQuery.findOrderStatusByOrderIdAndReferenceNumber(
                buyOrder.get(Orders.PROPERTIES.orderId.name()),
                buyOrder.get(Orders.PROPERTIES.referenceNumber.name()),
                Orders.STATUS.Open.name());

        HashMap<String, String> sellOrder = user2.createOrder(tokenName, Orders.SIDE.S.name(), quantity, price);
        String actualSellStatus = ATSOrdersQuery.findOrderStatusByOrderIdAndReferenceNumber(
                sellOrder.get(Orders.PROPERTIES.orderId.name()),
                sellOrder.get(Orders.PROPERTIES.referenceNumber.name()),
                Orders.STATUS.Executed.name());

        String sellOrderId = sellOrder.get(Orders.PROPERTIES.orderId.name());
        String buyOrderId = buyOrder.get(Orders.PROPERTIES.orderId.name());

        String afterMatchBuyStatus = ATSOrdersQuery.findOrderStatusByOrderIdAndReferenceNumber(
                buyOrder.get(Orders.PROPERTIES.orderId.name()),
                buyOrder.get(Orders.PROPERTIES.referenceNumber.name()));

        Assert.assertEquals(
                afterMatchBuyStatus,
                Orders.STATUS.Executed.name(),
                String.format("The database status is incorrect for order '%s'", buyOrderId));
        Assert.assertEquals(
                actualSellStatus,
                Orders.STATUS.Executed.name(),
                String.format("The database status is incorrect for order '%s'", sellOrderId));
    }

}