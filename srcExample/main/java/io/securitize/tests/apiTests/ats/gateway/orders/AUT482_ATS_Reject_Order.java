
package io.securitize.tests.apiTests.ats.gateway.orders;

import io.securitize.infra.api.AtsGatewayAPI;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.AtsMarketState;
import io.securitize.infra.utils.AtsMarketDependent;
import io.securitize.tests.ats.properties.Orders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class AUT482_ATS_Reject_Order {

    @Test(description = "Creates a 'Buy' order and tries to match the order with the same user", groups = {"apiSanityATS"})
    @AtsMarketDependent(expectedState = AtsMarketState.UP)
    public void AUT482_ATS_Reject_Order() {
        String buyerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_buyInvestor_aut128); // Buy investor
        String buyerInvestorId = Users.getProperty(UsersProperty.ats_trade_buyInvestorId_aut128); // Buy investor
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);

        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);
        AtsGatewayAPI user1 = new AtsGatewayAPI(buyerInvestorEmail, password);
        String price = "1";
        String quantity = "1";
        HashMap<String, String> buyOrder = user1.createOrder(tokenName, Orders.SIDE.B.name(), quantity, price);
        HashMap<String, String> sellOrder = user1.createOrder(tokenName, Orders.SIDE.S.name(), quantity, price);
        String orderId = sellOrder.get(Orders.PROPERTIES.orderId.name());
        String actualStatusInDB = sellOrder.get(Orders.PROPERTIES.orderStatus.name());
        String expectedStatusInDB = Orders.STATUS.Rejected.name();
        Assert.assertEquals(actualStatusInDB, expectedStatusInDB,
                String.format("The database status is incorrect for order '%s'", orderId));
        HashMap<String, String> canceledOrder = user1.cancelOrder(buyOrder);
        String actualPreviousStatus = canceledOrder.get(Orders.PROPERTIES.previousOrderStatus.name());
        String actualStatus = canceledOrder.get(Orders.PROPERTIES.orderStatus.name());
        String expectedPreviousStatus = Orders.STATUS.Open.name();
        String expectedStatus = Orders.STATUS.Canceled.name();
        Assert.assertEquals(actualPreviousStatus, expectedPreviousStatus,
                String.format("The database status is incorrect for order '%s'", orderId));
        Assert.assertEquals(actualStatus, expectedStatus,
                String.format("The database status is incorrect for order '%s'", orderId));
    }
}