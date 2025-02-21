package io.securitize.scripts;

import io.securitize.infra.api.IdologyAPI;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class AUT381_CheckIdologyStatus {
    public static void main(String[] args) {

        IdologyAPI idologyAPI = new IdologyAPI();
        try {
            boolean response = idologyAPI.isIdologyUp();
            if (response == true) {
                info("Idology service is up.");
            } else {
                info("IDoligy service is down.");
            }
        } catch (Exception e) {
            errorAndStop("Idology service is unavailable: " + e, false);
        }
    }
}