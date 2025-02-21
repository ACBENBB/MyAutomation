package io.securitize.tests.securitytests;

import org.testng.annotations.Test;

public class AUT615_Security_Internal_Endpoints extends AbstractEndpointsSecurityTest {

    private static final String BASE_URL = "https://sid-cp-gw.internal.rc.securitize.io";

    public AUT615_Security_Internal_Endpoints() {
        super(BASE_URL);
    }

    @Test(dataProvider = "testDataProviderForNegativeScenarios")
    public void AUT615_Security_Internal_ForbiddenWithoutTokensTest(String testName, String method, String url) {
        forbiddenWithoutTokensTest(testName, method, url);
    }

    @Test(dataProvider = "testDataProviderForPositiveScenarios")
    public void AUT615_Security_Internal_OkWithTokensTest(String testName, String method, String url) {
        okWithTokensTest(testName, method, url);
    }
}