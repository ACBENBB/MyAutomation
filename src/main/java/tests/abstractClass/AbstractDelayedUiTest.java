package tests.abstractClass;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

import static infra.reporting.MultiReporter.errorAndStop;

public class AbstractDelayedUiTest extends AbstractUiTest {

    private final ThreadLocal<Method> savedMethod = new ThreadLocal<>();
    private final ThreadLocal<Object[] > savedParameters = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(final ITestContext testContext, Method method, Object[] parameters) throws Exception {
        super.beforeMethod(testContext, method, parameters);

        savedMethod.set(method);
        savedParameters.set(parameters);
    }

    protected void openBrowser() {
        try {
            super.openBrowser(savedMethod.get(), savedParameters.get());
        } catch (Exception e) {
            errorAndStop("An error occur trying to open browser. Details: " + e, false);
        }
    }

}
