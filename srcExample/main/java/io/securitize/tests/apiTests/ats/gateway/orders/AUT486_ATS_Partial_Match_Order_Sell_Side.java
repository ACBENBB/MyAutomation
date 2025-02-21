
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

public class AUT486_ATS_Partial_Match_Order_Sell_Side {

    @Test(description = "Creates a 'Sell' order to partially match a buy order", groups = {"apiSanityATS"})
    @AtsMarketDependent(expectedState = AtsMarketState.UP)
    public void AUT486_ATS_Partial_Match_Order_Sell_Side() throws InterruptedException {
        String buyerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_buyInvestor_aut128); // Buy investor
        String buyerInvestorId = Users.getProperty(UsersProperty.ats_trade_buyInvestorId_aut128); // Buy investor
        String sellerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_sellInvestor_aut128); // Sell investor
        String sellerInvestorId = Users.getProperty(UsersProperty.ats_trade_sellInvestorId_aut128); // Sell investor
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);

        AtsGatewayAPI user1 = new AtsGatewayAPI(sellerInvestorEmail, password);
        AtsGatewayAPI user2 = new AtsGatewayAPI(buyerInvestorEmail, password);
        AtsOrderAPI atsOrderAPI = new AtsOrderAPI();
        atsOrderAPI.cancelActiveOrders(tokenName);

        HashMap<String, String> sellOrder = user1.createOrder(tokenName, Orders.SIDE.S.name(), "2", "1");
        String actualSellStatus = ATSOrdersQuery.findOrderStatusByOrderIdAndReferenceNumber(
                sellOrder.get(Orders.PROPERTIES.orderId.name()),
                sellOrder.get(Orders.PROPERTIES.referenceNumber.name()),
                Orders.STATUS.Open.name());

        HashMap<String, String> buyOrder = user2.createOrder(tokenName, Orders.SIDE.B.name(), "1", "1");
        String actualBuyStatus = ATSOrdersQuery.findOrderStatusByOrderIdAndReferenceNumber(
                buyOrder.get(Orders.PROPERTIES.orderId.name()),
                buyOrder.get(Orders.PROPERTIES.referenceNumber.name()),
                Orders.STATUS.Executed.name());

        String sellOrderId = sellOrder.get(Orders.PROPERTIES.orderId.name());
        String buyOrderId = buyOrder.get(Orders.PROPERTIES.orderId.name());
        
        Assert.assertEquals(
                actualBuyStatus,
                Orders.STATUS.Executed.name(),
                String.format("The database status is incorrect for order '%s'", buyOrderId));
        HashMap<String, String> canceledOrder = user1.cancelOrder(sellOrder);
        String actualCanceledOrderStatus = canceledOrder.get(Orders.PROPERTIES.orderStatus.name());
        Assert.assertEquals(
                actualCanceledOrderStatus,
                Orders.STATUS.Canceled.name(),
                String.format("The database status is incorrect for order '%s'", sellOrderId));
    }
    
}