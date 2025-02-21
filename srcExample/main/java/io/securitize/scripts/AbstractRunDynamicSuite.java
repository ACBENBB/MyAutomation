package io.securitize.scripts;

import io.securitize.infra.dashboard.enums.TestCategory;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.utils.SkipTestOnStabilization;
import io.securitize.tests.abstractClass.AbstractTest;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.*;

import static io.securitize.infra.reporting.MultiReporter.info;

public abstract class AbstractRunDynamicSuite {

    protected static Map<String, List<String>> extractClassesFromTestMethodNames(List<String> testsToRun) {
        info("Extracting test classes from test names");
        Map<String, List<String>> result = new HashMap<>();

        Reflections reflections = new Reflections("io.securitize.tests");
        Set<Class<? extends AbstractTest>> allTestClasses = reflections.getSubTypesOf(AbstractTest.class);

        for (String currentTest : testsToRun) {
            for (Class<? extends AbstractTest> currentClass : allTestClasses) {
                String currentClassName = currentClass.getName();
                boolean isSkipTestOnStabilizationAnnotationExists = false;
                //skip test method which is annotated with @SkipTestOnStabilization
                try {
                    Method method = currentClass.getDeclaredMethod(currentTest);
                    isSkipTestOnStabilizationAnnotationExists = method.getDeclaredAnnotation(SkipTestOnStabilization.class) != null;
                    if (isSkipTestOnStabilizationAnnotationExists) {
                        info(currentTest + " is annotated with @SkipTestOnStabilization and will be skipped");
                    }
                } catch (NoSuchMethodException ignored) {
                }
                boolean finalIsSkipTestOnStabilizationAnnotationExists = isSkipTestOnStabilizationAnnotationExists;
                if (Arrays.stream(currentClass.getMethods()).anyMatch(x -> x.getName().equals(currentTest) && !(finalIsSkipTestOnStabilizationAnnotationExists))) {
                    if (result.containsKey(currentClassName)) {
                        result.get(currentClassName).add(currentTest);
                    } else {
                        List<String> singleTestForNow = new ArrayList<>();
                        singleTestForNow.add(currentTest);
                        result.put(currentClassName, singleTestForNow);
                    }
                    info("Will run: " + currentClassName + "." + currentTest);
                    break;
                }
            }
        }
        info("Found final " + result.size() + " tests to run");
        return result;
    }


    protected static int runTests(Map<String, List<String>> classesAndMethods, String description) {
        // create suite
        XmlSuite suite = new XmlSuite();
        suite.setName("Dynamic " + description + " suite");
        suite.setParallel(XmlSuite.ParallelMode.TESTS);
        suite.setThreadCount(20);

        // if this is not a local execution - add results to the dashboard db
        if (TestCategory.getTestCategory() != TestCategory.MANUAL) {
            suite.addListener("io.securitize.infra.dashboard.ExecutionListener");
        }
        suite.addListener("io.securitize.infra.utils.CompositeTransformer");
        List<XmlSuite> suites = new ArrayList<>();

        // key: domain name such as SID
        // value: list of xml classes to run in this domain
        Map<String, List<XmlClass>> xmlClassesPerDomain = new HashMap<>();

        // add listeners and parameters
        for (String currentClassName : classesAndMethods.keySet()) {
            String currentDomain = RegexWrapper.getDomainNameFromClassName(currentClassName);
            if (!xmlClassesPerDomain.containsKey(currentDomain)) {
                xmlClassesPerDomain.put(currentDomain, new ArrayList<>());
            }

            XmlClass currentClass = new XmlClass(currentClassName);
            List<XmlInclude> includeMethods = new ArrayList<>();
            for (String currentTestMethod : classesAndMethods.get(currentClassName)) {
                includeMethods.add(new XmlInclude(currentTestMethod));
            }
            currentClass.setIncludedMethods(includeMethods);
            xmlClassesPerDomain.get(currentDomain).add(currentClass);
        }

        // create an xmlTest object per domain so that we can filter tests by domain name if needed by using
        // environment variable of DOMAINS_TO_TEST
        for (String currentDomain : xmlClassesPerDomain.keySet()) {
            XmlTest test = new XmlTest(suite);
            test.setParallel(XmlSuite.ParallelMode.METHODS);
            test.setName("Dynamic " + description + " test_" + currentDomain + "_");
            test.setXmlClasses(xmlClassesPerDomain.get(currentDomain));
        }
        suites.add(suite);

        String isDryRun = System.getenv("isDryRun");
        if (StringUtils.isEmpty(isDryRun) || isDryRun.equalsIgnoreCase("false")) {
            // set the testNG runner
            TestNG testNG = new TestNG();
            testNG.setXmlSuites(suites);
            testNG.run();
            return testNG.getStatus();
        } else {
            dryRun(suites);
            return 0;
        }
    }

    private static void dryRun(List<XmlSuite> suites) {
        int testMethodsCounter = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (XmlSuite suite : suites) {
            stringBuilder.append("Dry running suite: ").append(suite.getName()).append(System.lineSeparator());
            for (XmlTest test : suite.getTests()) {
                stringBuilder.append("\t* Test: ").append(test.getName()).append(System.lineSeparator());
                for (XmlClass c : test.getClasses()) {
                    for (XmlInclude xmlInclude : c.getIncludedMethods()) {
                        stringBuilder.append("\t\t- ").append(c.getName()).append(".").append(xmlInclude.getName()).append(System.lineSeparator());
                        testMethodsCounter++;
                    }
                }
            }
        }
        stringBuilder.append("Total tests: ").append(testMethodsCounter).append(System.lineSeparator());
        info(stringBuilder.toString());
    }
}