package io.securitize.infra.utils;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.securitize.infra.exceptions.PageNotLoadedException;
import io.securitize.infra.webdriver.Browser;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import java.time.Duration;
import java.util.function.Supplier;

import static io.securitize.infra.reporting.MultiReporter.*;

public class RetryOnExceptions {
    protected Browser browser;

    private static RetryPolicy<Object> retryPolicy = null;
    private static RetryPolicy<Object> retryPolicyOnStaleElement = null;
    private static final long DEFAULT_RETRY_DELAY_SECONDS = 30L;
    private static final int DEFAULT_MAX_RETRIES = 3;

    private RetryOnExceptions() {
        throw new IllegalStateException("Utility class");
    }

    private static void createRetryPolicyOnTimeoutAndPageNotLoadedAndNoSuchElement() {
        if (retryPolicy == null) {
            retryPolicy = RetryPolicy.builder()
                    .handle(TimeoutException.class, PageNotLoadedException.class, NoSuchElementException.class)
                    .withDelay(Duration.ofSeconds(DEFAULT_RETRY_DELAY_SECONDS))
                    .withMaxRetries(DEFAULT_MAX_RETRIES)
                    .onRetry(e -> warning("Retry because of exception: " + e.getLastException(), true))
                    .onRetriesExceeded(e -> error("Max retries exceeded: ", true))
                    .build();
        }
    }

    /**
     * retry function.
     * first, func is executed.
     * if an exception (Timeout, PageNotLoaded, or NoSuchElement) happens, runOnException is executed. and func is
     * executed again with failsafe. runOnException can be reloading page or doing nothing, for example.
     * if such exception does not happen, func is not retried.
     * when retry should not be executed, set false to performRetry. both retry and no-retry are covered.
     */
    public static <T> T retryFunction(Supplier<T> func, Supplier<T> runOnException, boolean performRetry) {
        if (performRetry) {
            createRetryPolicyOnTimeoutAndPageNotLoadedAndNoSuchElement();
            return Failsafe.with(retryPolicy).get(() -> {
                T type;
                try {
                    type = func.get();
                } catch (TimeoutException | PageNotLoadedException | NoSuchElementException exception) {
                    warning("Execute runOnExecution because: " + exception.getMessage());
                    runOnException.get();
                    throw exception;
                }
                return type;
            });
        } else {
            return func.get();
        }
    }

    private static void createRetryPolicyOnStaleElementReference() {
        if (retryPolicyOnStaleElement == null) {
            retryPolicyOnStaleElement = RetryPolicy.builder()
                    .handle(StaleElementReferenceException.class)
                    .withDelay(Duration.ofSeconds(5))
                    .withMaxRetries(DEFAULT_MAX_RETRIES)
                    .onRetry(e -> warning("Retry because of exception: " + e.getLastException(), true))
                    .onRetriesExceeded(e -> error("Max retries exceeded: ", true))
                    .build();
        }
    }

    public static <T> T retryOnStaleElement(Supplier<T> func, boolean performRetry) {
        if (performRetry) {
            createRetryPolicyOnStaleElementReference();
            return Failsafe.with(retryPolicyOnStaleElement).get(() -> {
                T type;
                try {
                    type = func.get();
                } catch (StaleElementReferenceException exception) {
                    warning("Stale Element Exception. Message: " + exception.getMessage());
                    throw exception;
                }
                return type;
            });
        } else {
            return func.get();
        }
    }

}
