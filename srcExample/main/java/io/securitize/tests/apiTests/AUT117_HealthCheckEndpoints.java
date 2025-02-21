package io.securitize.tests.apiTests;

import io.securitize.infra.api.healthchecks.HealthCheckEndpoint;
import io.securitize.tests.abstractClass.AbstractHealthCheckTest;
import org.testng.annotations.Test;

public class AUT117_HealthCheckEndpoints extends AbstractHealthCheckTest {

    @Test(description = "Validate all endpoint services are healthy for the specified environment")
    public void AUT117ValidateHealthCheckEndpoints() {
        // validate endpoints health checks
        HealthCheckEndpoint healthCheckEndpoint = new HealthCheckEndpoint();
        healthCheckEndpoint.healthCheck("all");
    }
}