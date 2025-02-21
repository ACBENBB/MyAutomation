package io.securitize.infra.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Retry implements IRetryAnalyzer {
    private static final int MAX_RETRIES_PER_TEST = 1;
    private int counter = 0;

    private static final Map<String, Integer> testRetryCounter = new HashMap<>();

    @Override
    public boolean retry(ITestResult iTestResult) {
            if (counter < MAX_RETRIES_PER_TEST) {
                counter++;
                String name = iTestResult.getName();
                if (iTestResult.getParameters().length > 0) {
                    String values = Arrays.stream(iTestResult.getParameters())
                            .map(Object::toString)
                            .collect(Collectors.joining(","));
                    name = name + "[" + values + "]";
                }
                testRetryCounter.put(name, counter);
                return true;
            }
        return false;
    }

    public static int getTestRetryCounter(String testName) {
        if (testRetryCounter.containsKey(testName)) {
            return testRetryCounter.get(testName);
        }

        return -1;
    }
}