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



    public static Method getCurrentTestMethod() {
        return currentTestMethod.get();
    }

    public static String getCurrentTestName() {
        if (currentTestMethod.get() == null) {
            return "Test name not set yet";
        }
        return currentTestMethod.get().getName();
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
