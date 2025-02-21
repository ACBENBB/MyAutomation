package io.securitize.tests.abstractClass;

import io.securitize.infra.api.IdologyAPI;
import io.securitize.infra.api.MarketsAPI;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.healthchecks.HealthCheckEndpoint;
import io.securitize.infra.api.healthchecks.HealthCheckUrls;
import io.securitize.infra.config.*;
import io.securitize.infra.enums.AtsMarketState;
import io.securitize.infra.enums.AtsRegionState;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.googleapi.GoogleSheets;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.infra.utils.*;
import net.minidev.json.JSONArray;
import org.jetbrains.annotations.NotNull;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractTest implements IHookable {
    protected static final ThreadLocal<Method> currentTestMethod = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    protected void initSuite(ITestContext testContext) {
        initializeSuite( testContext.getSuite().getName() + " => " + testContext.getName());

        String threadCountToSet = System.getenv("threadCount");
        if (threadCountToSet != null) {
            info("Setting thread count limit to: " + threadCountToSet);
            int threadCountAsInt = Integer.parseInt(threadCountToSet);
            testContext.getSuite().getXmlSuite().setThreadCount(threadCountAsInt);
        }
        info("Max threads to be used: " + getMaxThreadCount(testContext));
        info("On total of " + testContext.getSuite().getAllMethods().size()  + " tests (ignoring data driven)");

        // validate endpoints health checks - if defined to do so
        if (!(this instanceof BaseApiTest)) {
            if (MainConfig.getBooleanProperty(MainConfigProperty.runHealthChecksEndpointsBeforeTests)) {
                HealthCheckEndpoint healthCheckEndpoint = new HealthCheckEndpoint();
                healthCheckEndpoint.healthCheck(MainConfig.getProperty(MainConfigProperty.environment));
            }

            // validate urls health checks - if defined to do so
            if (MainConfig.getBooleanProperty(MainConfigProperty.runHealthChecksUrlsBeforeTests)) {
                HealthCheckUrls healthCheckUrls = new HealthCheckUrls();
                healthCheckUrls.healthCheck(MainConfig.getProperty(MainConfigProperty.environment));
            }
        }

        // load Google sheet list of tests to skip if needed
        if ((MainConfig.getBooleanProperty(MainConfigProperty.skipTestsListedInGoogleSheet))) {
            GoogleSheets.initTestsToSkip();
        }
    }


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

        if ((MainConfig.getBooleanProperty(MainConfigProperty.skipTestsListedInGoogleSheet))) {
            GoogleSheets.skipTestIfNeeded(method.getName());
        }
        validateAtsMarketState(method);
        validateAtsRegionState(method);
        validateIdology(method);
    }

    protected void validateAtsMarketState(Method method) {
        AtsMarketDependent atsMarketDependent = method.getDeclaredAnnotation(AtsMarketDependent.class);

        // if current test is market dependent
        if (atsMarketDependent != null) {
            AtsMarketState expectedState = atsMarketDependent.expectedState();

            MarketsAPI marketsAPI = new MarketsAPI();
            AtsMarketState actualState = marketsAPI.getMarketsState();

            if (expectedState.getValue() != actualState.getValue()) {
                throw new SkipException(String.format("Current market state: %S but expected state for test %s is: %s", actualState, method.getName(), expectedState));
            }
        }
    }

    protected void validateAtsRegionState(Method method) {
        AtsRegionDependent atsRegionDependent = method.getDeclaredAnnotation(AtsRegionDependent.class);

        if (atsRegionDependent != null) {
            AtsRegionState expectedState = atsRegionDependent.expectedState();

            MarketsAPI marketsAPI = new MarketsAPI();
            JSONArray marketsInformation = marketsAPI.getMarketsInformation();
            AtsRegionState actualState = findActualState(marketsInformation, expectedState);

            if (!actualState.getValue().equals(expectedState.getValue())) {
                throw new SkipException(String.format("Current market state: %S but expected state for test %s is: %s",
                        actualState, method.getName(), expectedState));

            }
        }
    }

    private AtsRegionState findActualState(@NotNull JSONArray marketsInformation, AtsRegionState expectedState) {
        for (Object marketInfo : marketsInformation) {
            String status = ((LinkedHashMap<?, ?>) marketInfo).get("status").toString().toUpperCase();
            String identifier = ((LinkedHashMap<?, ?>) marketInfo).get("identifier").toString().toUpperCase();
            AtsRegionState actualState = AtsRegionState.valueOf(String.format("%s_%s", identifier, status));

            // Check if the expected state contains the identifier and actual state matches
            if (expectedState.name().contains(identifier)) {
                return actualState;
            }
        }
        // If the loop completes it means the region does not exist.
        throw new SkipException(String.format("The region %s does not exist", expectedState.name()));
    }

    protected void validateIdology(Method method) {
        try {
            // if this test is dependent with Idology - make sure it is up before even startint it
            boolean isIdologyDependent = method.getDeclaredAnnotation(IdologyDependent.class) != null;
            if (isIdologyDependent) {
                info("This test is Idology dependent. Validing Idology is up...");
                IdologyAPI idologyAPI = new IdologyAPI();
                boolean result = idologyAPI.isIdologyUp();
                info("Is idology up? " + result);
                if (!result) {
                    errorAndStop("This test depends on Idology and it is down... Won't resume!", false);
                }
            }
        } catch (Exception e) {
            errorAndStop("An error occur trying to validate Idology is up. Can't resume. Details: " + e.getMessage(), false);
        }
    }

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


    @AfterSuite(alwaysRun = true)
    protected void tearDown(ITestContext testContext) {
        EmailWrapper.closeAllEmailConnections();

        // make sure to add skipped tests to the report
        for (ITestNGMethod currentSkippedTest : testContext.getSkippedTests().getAllMethods()) {
            // avoid cases where we failed in BeforeMethod of some test and then we once add failure in the log and also a skip on the test itself as well
            if (testContext.getFailedConfigurations().getAllMethods().stream().noneMatch(x -> x.getTestClass().getName().contains(currentSkippedTest.getMethodName()))) {
                addSkippedTest(currentSkippedTest.getMethodName(), null);
            }
        }

        terminateSuite();
    }

    public static Method getCurrentTestMethod() {
        return currentTestMethod.get();
    }

    public static String getCurrentTestName() {
        if (currentTestMethod.get() == null) {
            return "Test name not set yet";
        }
        return currentTestMethod.get().getName();
    }

    public static TestNetwork getTestNetwork() {
        TestNetwork testNetwork;

        // if current test has the DefaultToken annotation - take the value from there
        if (currentTestMethod.get().getAnnotation(DefaultTestNetwork.class) != null) {
            testNetwork = currentTestMethod.get().getAnnotation(DefaultTestNetwork.class).value();
        } else { // return default value from config (quorum)
            String testNetworkAsString = MainConfig.getProperty(MainConfigProperty.testNetwork);
            testNetwork = TestNetwork.valueOf(testNetworkAsString);
        }
        return testNetwork;
    }

    public String getTokenName(String issuerName) {
        TestNetwork testNetwork = getTestNetwork();
        String fullPropertyName = testNetwork.toString().toLowerCase() + "TokenName";
        return Users.getIssuerDetails(issuerName.toLowerCase(), IssuerDetails.valueOf(fullPropertyName));
    }


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
            for (int i = 0; i < suiteThreadCount && i < suiteTests.size() ; i++) {
                result += testsThreadCountList.get(i);
            }
            return result;
        } else {
            return suiteThreadCount;
        }
    }

    public static String getTestFullyQualifiedName() {
        if (currentTestMethod.get() == null) {
            errorAndStop("Can't extract test full name", false);
        }
        String testMethodClass = currentTestMethod.get().getDeclaringClass().getName();
        String testMethodName = currentTestMethod.get().getName();
        return testMethodClass + "." + testMethodName;
    }

    public static boolean isUiTest() {
        StackTraceElement[] currentStackFrame = Thread.currentThread().getStackTrace();
        return Arrays.stream(currentStackFrame).anyMatch(x -> {
            try {
                return AbstractUiTest.class.isAssignableFrom(Class.forName(x.getClassName()));
            } catch (ClassNotFoundException e) {
                // we don't care if any error happened here
                return false;
            }
        });
    }

    /**
     * Used to fail a test if marked as error (fails at the end)
     * @param callBack test method to invoke
     * @param result result of test method - which might change if non-fatal error occur
     */
    @Override
    public void run(IHookCallBack callBack, ITestResult result) {
        callBack.runTestMethod(result);
        if (MultiReporter.isErrorOccur()) {
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(MultiReporter.getErrorException());
        }
    }
}