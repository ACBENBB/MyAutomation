
package io.securitize.tests.apiTests.ats.gateway.orders;

import io.securitize.infra.api.AtsGatewayAPI;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.ats.properties.Orders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class AUT480_ATS_Cancel_Buy_Order {


    @Test(description = "Creates a 'Buy' order and then cancels the order.", groups = {"apiSanityATS"})
    public void AUT480_ATS_Cancel_Buy_Order() {
        String buyerInvestorEmail = Users.getProperty(UsersProperty.ats_trade_buyInvestor_aut128); // Buy investor
        String buyerInvestorId = Users.getProperty(UsersProperty.ats_trade_buyInvestorId_aut128); // Buy investor
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String tokenName = Users.getProperty(UsersProperty.ats_automationTokenName_aut128);

        AtsGatewayAPI buyerInvestor = new AtsGatewayAPI(buyerInvestorEmail, password);
        String price = "1";
        String quantity = "1";
        HashMap<String, String> buyOrder = buyerInvestor.createOrder(tokenName, Orders.SIDE.B.name(), quantity, price);
        String orderId = buyOrder.get(Orders.PROPERTIES.orderId.name());
        String actualStatusInDB = buyOrder.get(Orders.PROPERTIES.orderStatus.name());
        String expectedStatusInDB = Orders.STATUS.Open.name();
        Assert.assertEquals(actualStatusInDB, expectedStatusInDB,
                String.format("The database status is incorrect for order '%s'", orderId));
        HashMap<String, String> resultCancelOrder = buyerInvestor.cancelOrder(buyOrder);
        actualStatusInDB = resultCancelOrder.get(Orders.PROPERTIES.orderStatus.name());
        expectedStatusInDB = Orders.STATUS.Canceled.name();
        Assert.assertEquals(actualStatusInDB, expectedStatusInDB,
                String.format("The database status is incorrect for order '%s'", orderId));
    }

}