package io.securitize.infra.api;

import com.jayway.jsonpath.JsonPath;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.enums.AtsMarketState;
import net.minidev.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.infra.reporting.MultiReporter.infoAndShowMessageInBrowser;

public class MarketsAPI extends AbstractAPI {
    public void setMarketUSSuitability(String securitizeIdProfileId, String suitabilityStatus) {
        infoAndShowMessageInBrowser("Run api call: set Markets investor suitability to Verified ");

        JSONObject body = new JSONObject()
                .put("status", suitabilityStatus);

        patchAPI("/broker-dealer/investors/" + securitizeIdProfileId + "/suitability", body.toString(), 200, true);
    }

    public void setMarketAccreditation(String securitizeIdProfileId, String accreditationStatus) {
        infoAndShowMessageInBrowser("Run api call: set Markets investor Accreditation to Verified ");

        JSONObject body = new JSONObject()
                .put("status", accreditationStatus);

        patchAPI("/broker-dealer/investors/" + securitizeIdProfileId + "/accreditation", body.toString(), 200, true);
    }

    public void setMarketUSAccountStatus(String securitizeIdProfileId, String accountStatus, String signature) {
        infoAndShowMessageInBrowser("Run api call: set Markets investor Account to approved ");

        JSONObject body = new JSONObject()
                .put("status", accountStatus)
                .put("signature", signature);


        patchAPI("/broker-dealer/investors/" + securitizeIdProfileId + "/account-status", body.toString(), 200, true);
    }

    public AtsMarketState getMarketsState() {
        infoAndShowMessageInBrowser("Run api call: isMarketsOpen");

        String atsGatewayBaseUrl = MainConfig.getProperty(MainConfigProperty.atsGwUrl);
        String response = getAPI(getSecuritizeIdSpec(), atsGatewayBaseUrl + "config", null, new HashMap<>(), 200, false, true);

        String marketStatus = JsonPath.read(response, "status");
        info("Market status: " + marketStatus);

        return AtsMarketState.valueOf(marketStatus.toUpperCase());
    }

    public JSONArray getMarketsInformation() {
        infoAndShowMessageInBrowser("Run api call: isMarketsOpen");

        String atsGatewayBaseUrl = MainConfig.getProperty(MainConfigProperty.atsGwUrl);
        String response = getAPI(getSecuritizeIdSpec(), atsGatewayBaseUrl + "config", null, new HashMap<>(), 200, false, true);
        JSONArray regions =  JsonPath.read(response, "$.market.regions");
        return regions;
    }

}