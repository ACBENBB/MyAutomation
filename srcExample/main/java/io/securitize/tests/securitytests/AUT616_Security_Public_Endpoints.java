package io.securitize.tests.securitytests;

import org.testng.annotations.Test;

public class AUT616_Security_Public_Endpoints extends AbstractEndpointsSecurityTest {

    private static final String BASE_URL = "https://cp.%ssecuritize.io";

    public AUT616_Security_Public_Endpoints() {
        super(BASE_URL);
    }

    @Test(dataProvider = "testDataProviderForNegativeScenarios")
    public void AUT616_Security_Public_ForbiddenWithoutTokensTest(String testName, String method, String url) {
        forbiddenWithoutTokensTest(testName, method, url);
    }

    @Test(dataProvider = "testDataProviderForPositiveScenarios")
    public void AUT616_Security_Public_OkWithTokensTest(String testName, String method, String url) {
        okWithTokensTest(testName, method, url);
    }
}