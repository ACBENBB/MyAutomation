package io.securitize.scripts;

import io.securitize.infra.api.LoginAPI;
import io.securitize.infra.config.Users;

import static io.securitize.infra.reporting.MultiReporter.warning;

public class AUT236_UpdateCpBearer {

    public static void main(String[] args) {
        LoginAPI loginAPI = new LoginAPI();
        String bearer;

        try {
            bearer = loginAPI.postLoginControlPanel();
        } catch (AssertionError e) {
            // if OTP code expired until we got a chance to send it - try once more
            if (e.getMessage().contains("401")) {
                warning("OTP failed with 401.. Trying once more...");
                bearer = loginAPI.postLoginControlPanel();
            } else {
                throw e;
            }
        }

        Users.updateAutomationCpBearer(bearer);
    }
}
