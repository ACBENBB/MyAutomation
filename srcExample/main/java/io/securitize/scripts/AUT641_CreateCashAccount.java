package io.securitize.scripts;

import io.securitize.infra.api.CashAccountApi;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.tests.abstractClass.AbstractTest;
import org.openqa.selenium.InvalidArgumentException;
import org.testng.annotations.Test;
import static io.securitize.infra.reporting.MultiReporter.info;


public class AUT641_CreateCashAccount extends AbstractTest {

    static final Double FUNDING_AMOUNT = 10000.0;

    @Test(description = "AUT641_CreateCashAccount")
    public void AUT641_CreateCashAccount() {
        String securitizeId = getSecuritizeId();
        Double fundingAmount = getFundingAmount();
        CashAccountApi cashAccountApi = new CashAccountApi();

        String cashAccountId = cashAccountApi.createCashAccountWithFunds(securitizeId, fundingAmount);
        info("Successfully created CashAccountId: " + cashAccountId + " for securitizeId: " + securitizeId);

        String securitizeIdInvestorCpUrl = MainConfig.getProperty(MainConfigProperty.cpUrl) + "securitize-id/" + securitizeId;
        info(securitizeIdInvestorCpUrl);
        info("Available balance: USD " + cashAccountApi.getCashAccountBalance(securitizeId));
    }


    private static String getSecuritizeId() {
        String valueFromEnvironment = System.getenv("securitizeId");

        if (valueFromEnvironment == null) {
            throw new InvalidArgumentException("SecuritizeId can't be null.");
        }
        return valueFromEnvironment;
    }

    private Double getFundingAmount() {
        String valueFromEnvironment = System.getenv("fundingAmount");
        if (valueFromEnvironment == null) {
            return FUNDING_AMOUNT;
        }
        return Double.parseDouble(valueFromEnvironment);
    }

}
