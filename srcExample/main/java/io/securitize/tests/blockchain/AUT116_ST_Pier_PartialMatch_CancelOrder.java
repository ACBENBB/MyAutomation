package io.securitize.tests.blockchain;

import io.securitize.infra.api.PierAPI;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.blockchain.testData.AUT116_POJO;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.securitize.infra.reporting.MultiReporter.info;


public class AUT116_ST_Pier_PartialMatch_CancelOrder extends AbstractTest {

    @Test(description = "PIER - Partial Match - Cancel Order")
    public void AUT116_ST_Pier_PartialMatch_CancelOrder_test() {

        info("Start Test Level: Create Test Object");
        AUT116_POJO aut116_pojo = new AUT116_POJO();

        info("Start Test Level: Assert PIER Services Health");
        PierAPI pierAPI = new PierAPI();
        pierAPI.assertHealthStatus();

        info("Start Test Level: Get Buyer Balance");
        JSONObject beforeMatchBuyerBalance =  pierAPI.waitUntilBalanceNotEmpty(aut116_pojo.buyerSecId, aut116_pojo.deploymentId, aut116_pojo.timeout);
        int beforeMatchBuyerTotalTokens = Integer.parseInt(beforeMatchBuyerBalance.getString("total"));

        info("Start Test Level: Get Seller Available and Total Tokens");
        JSONObject sellerBalance =  pierAPI.getInvestorBalanceForDeploymentID(aut116_pojo.sellerSecId, aut116_pojo.deploymentId);
        Assert.assertNotNull(sellerBalance, String.format("Seller %s balance came back as null. This shouldn't happen", aut116_pojo.sellerSecId));
        int sellerAvailableTokens = Integer.parseInt(sellerBalance.getString("available"));
        int sellerTotalTokens = Integer.parseInt(sellerBalance.getString("total"));
        Assert.assertTrue(sellerAvailableTokens > aut116_pojo.toSellAmount, "Seller Balance is not enough to create the order");

        info("Start Test Level: Create Sell Order");
        String sellOrderID =  pierAPI.postSellOrder(aut116_pojo.sellerSecId, String.valueOf(aut116_pojo.toSellAmount), aut116_pojo.deploymentId);

        info("Start Test Level: Get Seller Balance After Match");
        JSONObject newSellerBalance =  pierAPI.getInvestorBalanceForDeploymentID(aut116_pojo.sellerSecId, aut116_pojo.deploymentId);
        Assert.assertNotNull(sellerBalance, String.format("Seller %s new balance came back as null. This shouldn't happen", aut116_pojo.sellerSecId));
        int newSellerAvailableTokens = Integer.parseInt(newSellerBalance.getString("available"));
        int newSellerTotalTokens = Integer.parseInt(newSellerBalance.getString("total"));

        SoftAssert softAssertion = new SoftAssert();
        softAssertion.assertTrue(newSellerAvailableTokens == ( sellerAvailableTokens - aut116_pojo.toSellAmount),
                "Available tokens should be less than before by same amount ot sell");
        softAssertion.assertTrue(newSellerTotalTokens == sellerTotalTokens,
                "Total tokens should be the same as before creating the sellS order");

        info("Start Test Level: Check if Buyer Can Buy");
        softAssertion.assertTrue(pierAPI.getCanBuy(aut116_pojo.buyerSecId, aut116_pojo.deploymentId));

        info("Start Test Level: Create a Buy Order");
        int toBuyAmount = aut116_pojo.toBuyAmount;
        String buyOrderID =  pierAPI.postBuyOrder(aut116_pojo.buyerSecId, String.valueOf(toBuyAmount), aut116_pojo.deploymentId);

        info("Start Test Level: Match Order IDs");
        pierAPI.postMatchOrders(sellOrderID, buyOrderID, String.valueOf(toBuyAmount));
        JSONObject afterMatchSellerBalance =  pierAPI.waitForNewBalanceAfterMatch(aut116_pojo.sellerSecId, aut116_pojo.deploymentId,
                newSellerAvailableTokens, (sellerTotalTokens -toBuyAmount), aut116_pojo.timeout);
        int afterMatchSellerAvailableTokens = Integer.parseInt(afterMatchSellerBalance.getString("available"));
        int afterMatchSellerTotalTokens = Integer.parseInt(afterMatchSellerBalance.getString("total"));

        softAssertion.assertTrue(afterMatchSellerTotalTokens ==  (sellerTotalTokens -toBuyAmount),
                "Total tokens after match should be less than before by same amount that was matched." );
        softAssertion.assertTrue(afterMatchSellerAvailableTokens == newSellerAvailableTokens,
                "Available tokens after match should be the same as before the match order");

        JSONObject afterMatchBuyerBalance =  pierAPI.waitUntilBalanceNotEmpty(aut116_pojo.buyerSecId, aut116_pojo.deploymentId, aut116_pojo.timeout);
        int afterMatchBuyerTotalTokens = Integer.parseInt(afterMatchBuyerBalance.getString("total"));
        softAssertion.assertTrue(afterMatchBuyerTotalTokens == (beforeMatchBuyerTotalTokens + toBuyAmount),
                "Buyer should have same total tokens as bought");

        info("Start Test Level: Check if order still open - partial match");
        String orderStatus = pierAPI.getOrderStatus(sellOrderID, aut116_pojo.sellerSecId);
        softAssertion.assertTrue(orderStatus.equals("inProgress") ,
                "Sell order should still be in progress. Found: " + orderStatus);

        info("Start Test Level: Cancel the Order");
        // Let's cancel the order and verify available tokens after
        pierAPI.postCancelOrder(sellOrderID, aut116_pojo.sellerSecId, "expired");
        String newOrderStatus = pierAPI.getOrderStatus(sellOrderID, aut116_pojo.sellerSecId);
        softAssertion.assertTrue(newOrderStatus.equals("expired"),
                "Sell order should be expired. Found: " + newOrderStatus);

        JSONObject afterCancelSellerBalance =  pierAPI.getInvestorBalanceForDeploymentID(aut116_pojo.sellerSecId, aut116_pojo.deploymentId);
        int afterCancelSellerAvailableTokens = Integer.parseInt(afterCancelSellerBalance.getString("available"));
        int afterCancelSellerTotalTokens = Integer.parseInt(afterCancelSellerBalance.getString("total"));

        softAssertion.assertTrue(afterCancelSellerTotalTokens == afterMatchSellerTotalTokens,
                "Total tokens should be same as before");
        softAssertion.assertTrue(afterCancelSellerAvailableTokens == (sellerAvailableTokens - toBuyAmount),
                "Available tokens should be the first available tokens amount minus the matched amount");
        softAssertion.assertAll();

        // TODO: Now let's verify that these changes are reflected in CP -> Holders page
        // TODO: and in CP -> Transfer Agent -> Securities Transactions

    }

}
