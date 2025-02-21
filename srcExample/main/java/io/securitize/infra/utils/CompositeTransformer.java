package io.securitize.infra.utils;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

import static io.securitize.infra.reporting.MultiReporter.info;

/**
 * This one transformer class contains multiple logics which should have been split across multiple transformers,
 * but due to TestNG bug of <a href="https://github.com/cbeust/testng/issues/1894">...</a> we can only set one
 * transformer at a time. For this reason we load this one and run all the logics within
 */
public class CompositeTransformer implements IAnnotationTransformer {

    private static boolean isPrintMessage = true; // used to only print the message once and not per test

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        boolean shouldRun = skipTestOnEnvironmentsTransformer(annotation, testMethod);

        if (shouldRun) {
            shouldRun = skipTestOnAllowDomainTransformer(annotation, testMethod);
        }

        if (shouldRun) {
            shouldRun = skipTestOnIgnoreDomainTransformer(annotation, testMethod);
        }

        if (shouldRun) {
            retryAnnotationTransformer(annotation);
        }
    }

    private boolean skipTestOnEnvironmentsTransformer(ITestAnnotation annotation, Method testMethod) {
        SkipTestOnEnvironments annotationInMethod = testMethod.getAnnotation(SkipTestOnEnvironments.class);
        if (annotationInMethod != null) {
            String environment = MainConfig.getProperty(MainConfigProperty.environment);
            if (Arrays.stream(annotationInMethod.environments()).anyMatch(x -> x.equalsIgnoreCase(environment))) {
                String message = String.format("Test %s ignored as set to skip on environment: %s%s", testMethod.getName(), environment, System.lineSeparator());
                info(message);
                annotation.setEnabled(false);
                return false;
            }
        }
        return true;
    }


    private boolean skipTestOnAllowDomainTransformer(ITestAnnotation annotation, Method testMethod) {
        AllowTestOnEnvironments annotationInMethod = testMethod.getAnnotation(AllowTestOnEnvironments.class);
        if (annotationInMethod != null) {
            String environment = MainConfig.getProperty(MainConfigProperty.environment);
            if (Arrays.stream(annotationInMethod.environments()).noneMatch(x -> x.equalsIgnoreCase(environment))) {
                String message = String.format("Test '%s' is ignored as current environment '%s' is not in the list of allowed ones: %s%s", testMethod.getName(), environment, Arrays.toString(annotationInMethod.environments()), System.lineSeparator());
                info(message);
                annotation.setEnabled(false);
                return false;
            }
        }
        return true;
    }



    private boolean skipTestOnIgnoreDomainTransformer(ITestAnnotation annotation, Method testMethod) {
        String domainsToTestEnvironmentVariableName = "DOMAINS_TO_TEST";
        if (System.getenv().containsKey(domainsToTestEnvironmentVariableName)) {
            String currentTestDomain = RegexWrapper.getDomainNameFromClassName(testMethod.getDeclaringClass().getName());
            String rawDomainsToTest = System.getenv(domainsToTestEnvironmentVariableName);
            String[] domainsToTest = rawDomainsToTest.split(",");
            if (Arrays.stream(domainsToTest).anyMatch(x -> x.equalsIgnoreCase(currentTestDomain))) {
                info(String.format("Domain %s is allowed so test %s will be executed", currentTestDomain, testMethod.getName()));
                return true;
            }

            info(String.format("Domain %s is not allowed so test %s will NOT be executed", currentTestDomain, testMethod.getDeclaringClass().getCanonicalName()));
            annotation.setEnabled(false);
            return false;
        } else {
            if (isPrintMessage) {
                info("Environment variable " + domainsToTestEnvironmentVariableName + " not set - not skipping entire domains");
                isPrintMessage = false;
            }
        }
        return true;
    }


    private void retryAnnotationTransformer(ITestAnnotation annotation) {
        String allowTestRetryEnvironmentVariableName = "ALLOW_TEST_RETRY";
        if (System.getenv().containsKey(allowTestRetryEnvironmentVariableName)) {
            String allowTestRetryAsString = System.getenv(allowTestRetryEnvironmentVariableName);
            boolean allowTestRetry = Boolean.parseBoolean(allowTestRetryAsString);
            if (allowTestRetry) {
                annotation.setRetryAnalyzer(Retry.class);
            }
        }
    }
}