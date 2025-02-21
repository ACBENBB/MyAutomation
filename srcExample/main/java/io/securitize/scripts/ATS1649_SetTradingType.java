package io.securitize.scripts;

import com.jayway.jsonpath.JsonPath;
import io.securitize.infra.api.AtsAssetAPI;
import io.securitize.infra.config.AwsParametersLoader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ATS1649_SetTradingType {

    @Test(description = "Validates that the tradingType can be modified to REGULAR or LIMITED_TRADING")
    public void setTradingType() {
        String tradingType = "REGULAR"; //[REGULAR, LIMITED_TRADING]
        String security = "TTk1";
        AtsAssetAPI atsAssetAPI = new AtsAssetAPI();
        String responseAsString = atsAssetAPI.setTradingType(security, tradingType);
        String actualTradingType = JsonPath.read(responseAsString, "$.tradingType");
        Assert.assertEquals(actualTradingType, tradingType, "There was a problem updating the tradingType");
    }

}