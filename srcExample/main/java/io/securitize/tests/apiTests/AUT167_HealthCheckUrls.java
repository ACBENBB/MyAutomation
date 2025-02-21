package io.securitize.tests.apiTests;

import io.securitize.infra.api.healthchecks.HealthCheckUrls;
import io.securitize.tests.abstractClass.AbstractHealthCheckTest;
import org.testng.annotations.Test;

public class AUT167_HealthCheckUrls extends AbstractHealthCheckTest {

    @Test(description = "AUT167 - Validate all mapped urls are healthy for the specified environment")
    public void AUT167ValidateHealthCheckUrls() {
        // validate urls health checks
        HealthCheckUrls healthCheckUrls = new HealthCheckUrls();
        healthCheckUrls.healthCheck("all");
    }
}