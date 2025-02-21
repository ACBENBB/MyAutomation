package io.securitize.infra.api.apicodegen.model;

@SuppressWarnings("unused")
public class OperationSkips {
    private static final String DEFAULT_TEST_SKIP_REASON = "Test marked to be skipped in the inputs json file";

    private boolean entireTest;
    private String skipReason;
    private boolean statusCodeCheck;
    private boolean bodySchemaCheck;
    private boolean bodyContentCheck;

    public boolean isSkipEntireTest() {
        return entireTest;
    }

    public String getSkipReason() {
        if (skipReason == null) return DEFAULT_TEST_SKIP_REASON;
        return skipReason;
    }

    public boolean isSkipStatusCodeCheck() {
        return statusCodeCheck;
    }

    public boolean isSkipBodySchemaCheck() {
        return bodySchemaCheck;
    }

    public boolean isSkipBodyContentCheck() {
        return bodyContentCheck;
    }
}
