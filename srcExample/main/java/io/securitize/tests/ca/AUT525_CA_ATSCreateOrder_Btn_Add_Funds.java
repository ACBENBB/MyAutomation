package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.TransferFundsModalPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket.TradingSecondaryMarketsTradeModal;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT525_CA_ATSCreateOrder_Btn_Add_Funds extends AbstractCashAccount {

    @SkipTestOnEnvironments(environments = {"production"})
    @Test(description = "ATS Create Order - Btn Add Funds: validate component elements via visible text.")
    public void AUT525_CA_ATSCreateOrder_Btn_Add_Funds() {

        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut525);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);

        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);

        TradingSecondaryMarketsTradeModal tradingSecondaryMarketsTradeModal = dashboard.clickTradingAssetsCatalog()
                .clickFirstAsset()
                .clickBuyButton();

        startTestLevel("Verity Add Funds button label");
        Assert.assertEquals(tradingSecondaryMarketsTradeModal.getAddFundsButtonText().trim(), "Add funds", "Add Funds button label should be correct.");
        endTestLevel();

        startTestLevel("Verify that clicking Add Funds button leads to Transfer Types modal");
        TransferFundsModalPage transferFundsModalPage = tradingSecondaryMarketsTradeModal.clickAddFundsBtn();
        Assert.assertTrue(transferFundsModalPage.isPageLoaded(), "Transfer Types modal should be loaded.");
        Assert.assertTrue(transferFundsModalPage.getDepositModalBodyText().contains("deposit"),
                "Deposit Modal is not displayed correctly.");
        transferFundsModalPage.clickCloseBtn();
        endTestLevel();

    }
}
