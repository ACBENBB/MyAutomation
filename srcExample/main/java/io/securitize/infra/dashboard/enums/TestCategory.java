package io.securitize.infra.dashboard.enums;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;

public enum TestCategory {
    UNDEFINED,
    JENKINS,
    NIGHTLY_E2E,
    NIGHTLY_API,
    MANUAL,
    MANUAL_STABILIZATION,
    CICD_E2E,
    CICD_API;

    public static TestCategory getTestCategory() {
        String category = MainConfig.getProperty(MainConfigProperty.testCategory);

        return TestCategory.valueOf(category.toUpperCase());
    }
}
