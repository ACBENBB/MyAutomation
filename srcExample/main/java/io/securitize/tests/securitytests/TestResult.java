package io.securitize.tests.securitytests;

public enum TestResult {
    CREATED(-1),
    SUCCESS(1),
    FAILURE(2),
    SKIP(3),
    SUCCESS_PERCENTAGE_FAILURE(4),
    STARTED(16);

    public final int value;

    TestResult(int value) {
        this.value = value;
    }

    public static TestResult byValue(int value) {
        for (TestResult e : values()) {
            if (e.value == value) {
                return e;
            }
        }
        return null;
    }
}