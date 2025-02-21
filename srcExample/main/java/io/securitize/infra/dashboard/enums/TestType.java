package io.securitize.infra.dashboard.enums;

import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.tests.abstractClass.AbstractHealthCheckTest;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.abstractClass.AbstractUiTest;

public enum TestType {
    UNDEFINED,
    UI,
    API,
    HEALTH_CHECK;

    public static TestType getTestType(Class<?> classToAnalyze) {
        if ((!AbstractTest.class.isAssignableFrom(classToAnalyze)) &&
            (!BaseApiTest.class.isAssignableFrom(classToAnalyze)) &&
            (!AbstractHealthCheckTest.class.isAssignableFrom(classToAnalyze))) {
            return UNDEFINED;
        }

        if (AbstractUiTest.class.isAssignableFrom(classToAnalyze)) {
            return UI;
        }

        if (AbstractHealthCheckTest.class.isAssignableFrom(classToAnalyze)) {
            return HEALTH_CHECK;
        }

        return API;
    }
}
