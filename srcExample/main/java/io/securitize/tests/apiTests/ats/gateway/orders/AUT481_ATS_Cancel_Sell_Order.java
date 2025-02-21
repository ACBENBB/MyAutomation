
package io.securitize.tests.apiTests.ats.gateway.orders;

import io.securitize.infra.api.AtsGatewayAPI;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.ats.properties.Orders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class AUT481_ATS_Cancel_Sell_Order {

    @Test(description = "Creates a 'Sell' order and then cancels the order.", groups = {"apiSanityATS"})
    public void AUT481_ATS_Cancel_Sell_Order() {
        String sellerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_sellInvestor_aut128); // Sell investor
        String sellerInvestorId = Users.getProperty(UsersProperty.ats_trade_sellInvestorId_aut128); // Sell investor
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);

        AtsGatewayAPI user1 = new AtsGatewayAPI(sellerInvestorEmail, password);
        String price = "1";
        String quantity = "1";
        HashMap<String, String> sellOrder = user1.createOrder(tokenName, Orders.SIDE.S.name(), quantity, price);
        String orderId = sellOrder.get(Orders.PROPERTIES.orderId.name());
        String actualStatusInDB = sellOrder.get(Orders.PROPERTIES.orderStatus.name());
        String expectedStatusInDB = Orders.STATUS.Open.name();
        Assert.assertEquals(actualStatusInDB, expectedStatusInDB,
                String.format("The database status is incorrect for order '%s'", orderId));
        HashMap<String, String> resultCancelOrder = user1.cancelOrder(sellOrder);
        actualStatusInDB = resultCancelOrder.get(Orders.PROPERTIES.orderStatus.name());
        expectedStatusInDB = Orders.STATUS.Canceled.name();
        Assert.assertEquals(actualStatusInDB, expectedStatusInDB,
                String.format("The database status is incorrect for order '%s'", orderId));
    }

}