package io.securitize.tests.ats.sanity;

import io.securitize.infra.api.AtsGatewayAPI;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import io.securitize.tests.ats.constants.AtsGroup;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT687_ATS_MarketStatus extends AbstractATS {

    @Test(description = "Validates if the region status matches the region configuration status", groups = {AtsGroup.SANITY_ATS})
    public void AUT687_MarketStatus() {

        String investorMail = Users.getProperty(UsersProperty.ats_default_investor);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);

        startTestLevel("Login To SecuritizeId");
        loginToSecuritizeId(investorMail, password);
        endTestLevel();

        startTestLevel("Navigate To Secondary Market");
        navigateToSecondaryMarket();
        endTestLevel();

        startTestLevel("Close PreMarket Session PopUp If Visible");
        closePreMarketSessionPopUpIfVisible();
        endTestLevel();

        startTestLevel("Validate Market Status");
        AtsGatewayAPI atsGatewayAPI = new AtsGatewayAPI(investorMail, password);
        validateRegionStatus("us", atsGatewayAPI);
        endTestLevel();

    }

}