package io.securitize.infra.dashboard;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.dashboard.enums.TestCategory;
import io.securitize.infra.dashboard.enums.TestType;
import io.securitize.infra.database.MySqlDatabase;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.RegexWrapper;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class ExecutionListener implements ITestListener {

    private final ThreadLocal<TestStatus> testStatus = new ThreadLocal<>();
    private long suiteExecutionId = -1;

    public void onTestStart(ITestResult iTestResult) {
        this.testStatus.set(new TestStatus());
    }

    public void onTestSuccess(ITestResult iTestResult) {
        this.sendStatus(iTestResult,"PASS");
    }

    public void onTestFailure(ITestResult iTestResult) {
        this.sendStatus(iTestResult,"FAIL");
    }

    public void onTestSkipped(ITestResult iTestResult) {
        // if a test is skipped because it depends on another test method which failed, this is the only
        // way we have to run logging logic for the skipped test
        String message = null;
        if (iTestResult != null && iTestResult.getThrowable() != null) {
            message = iTestResult.getThrowable().getMessage();
        }
        if (message != null && message.contains("depends on not successfully finished method")) {
            String description = "";
            try {
                description = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).description();
            } catch (Exception e) {
                trace("An error occur trying to get skipped method description: " + ExceptionUtils.getStackTrace(e));
            }
            String category = RegexWrapper.getDomainNameFromClassName(iTestResult.getMethod().getRealClass().getDeclaringClass().getName());
            if (category == null || category.isBlank()) {
                category = "default_category";
            }
            startTest(iTestResult.getName(), description, category);
            skip(iTestResult.getThrowable().getMessage());
            endTest(iTestResult, null);
        }
        this.sendStatus(iTestResult,"SKIPPED");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        //skip
    }

    // suite level
    public void onStart(ITestContext iTestContext) {
        if (suiteExecutionId < 0) {
            suiteExecutionId = System.currentTimeMillis();
            info("Suite execution id: " + suiteExecutionId);
        }
        //skip
    }

    // suite level
    public void onFinish(ITestContext iTestContext) {
        //skip
    }

    private void sendStatus(ITestResult iTestResult, String status) {
        if (!iTestResult.wasRetried() && MainConfig.getBooleanProperty(MainConfigProperty.reportStatistics)) {
            try {
                testStatus.get().setTestClass(iTestResult.getTestClass().getName());
                String methodName = iTestResult.getMethod().getMethodName();
                if (iTestResult.getParameters() != null && iTestResult.getParameters().length > 0) {
                    methodName += " [" + iTestResult.getParameters()[0] + "]";
                }
                testStatus.get().setTestName(methodName);

                // description is limited to the first 128 characters.. We take only 120 just
                // to be on the safe side
                String description = iTestResult.getMethod().getDescription();
                if (description.length() > 120) {
                    description = description.substring(0, 120) + "...";
                }
                testStatus.get().setDescription(description);

                testStatus.get().setStatus(status);
                testStatus.get().setStartTime(DateTimeUtils.ticksToDateTime(iTestResult.getStartMillis()));
                testStatus.get().setEndTime(DateTimeUtils.ticksToDateTime(iTestResult.getEndMillis()));
                testStatus.get().setTestType(TestType.getTestType(iTestResult.getTestClass().getRealClass()));

                // add browser name only for UI tests
                if (testStatus.get().getTestType().equalsIgnoreCase(TestType.UI.name())) {
                    testStatus.get().setBrowser(MainConfig.getProperty(MainConfigProperty.browserType));
                } else {
                    testStatus.get().setBrowser("API");
                }

                testStatus.get().setEnvironment(MainConfig.getProperty(MainConfigProperty.environment));
                if (iTestResult.getThrowable() == null) {
                    testStatus.get().setFailureReason("pass!");
                } else {
                    testStatus.get().setFailureReason(iTestResult.getThrowable().getClass().getName());
                }

                testStatus.get().setCategory(TestCategory.getTestCategory());
                testStatus.get().setExecutionId(suiteExecutionId);
                testStatus.get().setLastStep(MultiReporter.getCurrentStepDescription());

                // send results to database - only if not marked to be skipped
                String skipStatistics = System.getenv("skipStatistics");
                if (skipStatistics != null && skipStatistics.equalsIgnoreCase("true")) {
                    info("Not reporting statistics as skipStatistics=true");
                } else {
                    MySqlDatabase.send(testStatus.get());
                    info("Statistics successfully sent to the dashboard DB!");
                }
            } catch (Exception e) {
                String errorMessage = String.format("An error occur trying to send statistics to dashboard DB. Status that was trying to send: %s%s%sError details: %s", System.lineSeparator(), new JSONObject(testStatus).toString(4), System.lineSeparator(), ExceptionUtils.getFullStackTrace(e));
                info(errorMessage);
            }
        } else {
            MultiReporter.debug("Not sending statistics as config reportStatistics is false");
        }
    }
}