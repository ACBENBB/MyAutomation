package infra.visualtesting;

import infra.webdriver.Browser;

import static infra.reporting.MultiReporter.debug;
import static infra.reporting.MultiReporter.trace;

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
