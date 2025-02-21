package io.securitize.infra.visualtesting;

import io.securitize.infra.webdriver.Browser;

import static io.securitize.infra.reporting.MultiReporter.debug;
import static io.securitize.infra.reporting.MultiReporter.trace;

/**
 * Used as default VisualTesting engine - so tests could hold calls to VisualTesting even if the engine
 * hasn't been initialized without crashing the test
 */
public class VisualTestingDummy extends AbstractVisualTesting {

    public VisualTestingDummy(Browser browser) {
        super(browser);
        trace("Initiating dummy visual testing");
    }

    @Override
    public void snapshot(String name) {
        debug("VisualTesting not configured for this test by Annotation 'UseVisualTesting'. Doing nothing for step " + name);
    }
}