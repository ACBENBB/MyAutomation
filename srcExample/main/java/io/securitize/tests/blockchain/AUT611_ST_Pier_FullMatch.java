package io.securitize.tests.blockchain;

import io.securitize.infra.api.PierAPI;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.blockchain.testData.AUT611_POJO;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.info;

public class AUT611_ST_Pier_FullMatch extends AbstractTest {

    // JIRA TICKET https://securitize.atlassian.net/jira/software/projects/ST2/boards/80/backlog?selectedIssue=ST2-525

    @Test(description = "AUT611_ST_Pier_FullMatch")
    public void AUT611_ST_Pier_FullMatch() {

        AUT611_POJO aut611_pojo = new AUT611_POJO();

        info("Start Test Level: Assert PIER Required Services Health Status");
        PierAPI pierAPI = new PierAPI();
        pierAPI.assertHealthStatus();

        info("Start Test Level: Validate Seller Available Tokens");
        Assert.assertTrue(pierAPI.getAvailableTokens(pierAPI.getInvestorBalanceForDeploymentID(aut611_pojo.sellerSecId, aut611_pojo.deploymentId)) > Integer.parseInt(aut611_pojo.sellOrderAmount),
                "Seller hasn't the amount of tokens necessary to perform the test");

        info("Start Test Level: Post Buy and Sell orders");
        aut611_pojo.sellOrderId = pierAPI.postSellOrder(aut611_pojo.sellerSecId, aut611_pojo.sellOrderAmount, aut611_pojo.deploymentId);
        aut611_pojo.buyOrderId = pierAPI.postBuyOrder(aut611_pojo.buyerSecId, aut611_pojo.buyOrderAmount, aut611_pojo.deploymentId);

        info("Start Test Level: Get Orders Status equal IN PROGRESS");
        Assert.assertEquals(pierAPI.getOrderStatus(aut611_pojo.sellOrderId, aut611_pojo.sellerSecId), "inProgress",
                "Order status should be inProgress");
        Assert.assertEquals(pierAPI.getOrderStatus(aut611_pojo.buyOrderId, aut611_pojo.buyerSecId), "inProgress",
                "Order status should be inProgress");

        info("Start Test Level: Post Match orders");
        info(pierAPI.postMatchOrders(aut611_pojo.sellOrderId, aut611_pojo.buyOrderId, aut611_pojo.buyOrderAmount));

        info("Start Test Level: Get Orders Status equal DONE");
        Assert.assertEquals(pierAPI.waitOrderStatus(aut611_pojo.sellOrderId, aut611_pojo.sellerSecId, "done", 120), "done",
                "Order status should be done");
        Assert.assertEquals(pierAPI.waitOrderStatus(aut611_pojo.buyOrderId, aut611_pojo.buyerSecId, "done", 120), "done",
                "Order status should be done");

    }

}