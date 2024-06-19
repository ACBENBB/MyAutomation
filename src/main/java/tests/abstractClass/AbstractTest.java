package tests.abstractClass;

import infra.config.MainConfig;
import infra.reporting.MultiReporter;
import infra.utils.RegexWrapper;
import net.minidev.json.JSONArray;
import org.jetbrains.annotations.NotNull;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static infra.reporting.MultiReporter.*;

public class AbstractTest implements IHookable {

    protected static final ThreadLocal<Method> currentTestMethod = new ThreadLocal<>();

    // This method initializes the suite before any tests are run.
    // Sets the thread count for the suite if specified in environment variables.
    // Logs the maximum number of threads to be used and the total number of tests.
    @BeforeSuite(alwaysRun = true)
    protected void initSuite(ITestContext testContext) {
        initializeSuite(testContext.getSuite().getName() + " => " + testContext.getName());

        String threadCountToSet = System.getenv("threadCount");
        if (threadCountToSet != null) {
            info("Setting thread count limit to: " + threadCountToSet);
            int threadCountAsInt = Integer.parseInt(threadCountToSet);
            testContext.getSuite().getXmlSuite().setThreadCount(threadCountAsInt);
        }
        info("Max threads to be used: " + getMaxThreadCount(testContext));
        info("On total of " + testContext.getSuite().getAllMethods().size() + " tests (ignoring data driven)");
    }

    // This method prepares the test method before it runs.
    // Sets the current test method, logs the method name, description, and domain.
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(final ITestContext testContext, Method method, Object[] parameters) throws Exception {
        currentTestMethod.set(method);
        String description = method.getAnnotation(Test.class).description();
        String testDescription = description.isEmpty() ? "Test description missing!!!" : description;

        String methodName = method.getName();
        if (parameters != null && parameters.length > 0) {
            String values = Arrays.stream(parameters)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            if (values.length() > 20) {
                methodName = methodName + "[" + parameters[0] + "...]";
            } else {
                methodName = methodName + "[" + values + "]";
            }
        }

        String currentTestDomain = RegexWrapper.getDomainNameFromClassName(method.getDeclaringClass().getName());
        if (currentTestDomain == null || currentTestDomain.isBlank()) {
            currentTestDomain = "default_category";
        }
        startTest(methodName, testDescription, currentTestDomain);
    }

    // This method cleans up after the test method has run.
    // Handles skipped tests and logs the reason if available.
    // Ends the test and removes the current test method.
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestContext context, ITestResult result) {
        // if test ended due to skip in its before method
        Set<ITestResult> skipsOfCurrentMethod = context.getSkippedTests().getResults(result.getMethod());
        if (skipsOfCurrentMethod.size() > 0) {
            Throwable throwable = skipsOfCurrentMethod.iterator().next().getThrowable();
            String skippedReason = "Can't extract skip reason, please check the logs";
            if (throwable != null) {
                skippedReason = throwable.getMessage();
            }
            endTest(result, skippedReason);
        } else {
            startTestLevelAsTopLevel("After method");
            endTest(result, null);
            endTestLevel(false);
        }
        currentTestMethod.remove();
    }

    // This method returns the current test method.
    public static Method getCurrentTestMethod() {
        return currentTestMethod.get();
    }

    // This method returns the name of the current test method.
    public static String getCurrentTestName() {
        if (currentTestMethod.get() == null) {
            return "Test name not set yet";
        }
        return currentTestMethod.get().getName();
    }

    // This method calculates the maximum thread count for the suite based on the XML configuration.
    private int getMaxThreadCount(ITestContext context) {
        int suiteThreadCount = context.getSuite().getXmlSuite().getThreadCount();
        // if suite runs internal 'test' tags in parallel the total sum of threads might be higher than
        // specified at the suite level, so we need to dive into each 'test' tag
        if (context.getSuite().getParallel().equals("tests")) {
            ArrayList<Integer> testsThreadCountList = new ArrayList<>();
            List<XmlTest> suiteTests = context.getSuite().getXmlSuite().getTests();
            for (XmlTest currentTest : suiteTests) {
                if (currentTest.getParallel() == XmlSuite.ParallelMode.TESTS) {
                    testsThreadCountList.add(1);
                } else {
                    testsThreadCountList.add(currentTest.getThreadCount());
                }
            }

            int result = 0;
            Collections.sort(testsThreadCountList);
            Collections.reverse(testsThreadCountList);
            for (int i = 0; i < suiteThreadCount && i < suiteTests.size(); i++) {
                result += testsThreadCountList.get(i);
            }
            return result;
        } else {
            return suiteThreadCount;
        }
    }

    // This method returns the fully qualified name of the current test method.
    public static String getTestFullyQualifiedName() {
        if (currentTestMethod.get() == null) {
            errorAndStop("Can't extract test full name", false);
        }
        String testMethodClass = currentTestMethod.get().getDeclaringClass().getName();
        String testMethodName = currentTestMethod.get().getName();
        return testMethodClass + "." + testMethodName;
    }

    // This method runs the test method and sets the test result status based on whether an error occurred.
    @Override
    public void run(IHookCallBack callBack, ITestResult result) {
        callBack.runTestMethod(result);
        if (MultiReporter.isErrorOccur()) {
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(MultiReporter.getErrorException());
        }
    }
}