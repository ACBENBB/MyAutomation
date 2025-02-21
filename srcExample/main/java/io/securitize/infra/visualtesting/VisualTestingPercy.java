package io.securitize.infra.visualtesting;

import io.percy.selenium.Percy;
import io.securitize.infra.webdriver.Browser;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.info;

public class VisualTestingPercy extends AbstractVisualTesting {
    private final Percy percy;

    public VisualTestingPercy(Browser browser) {
        super(browser);
        info("Initiating percy visual testing");

        percy = new Percy(Browser.getDriver());
    }

    @Override
    public void snapshot(String name) {
        browser.waitForPageStable(Duration.ofSeconds(5));
        showRecordingMessage();

        List<Integer> resolutions = Arrays.asList(375, 1280, 1920);
        percy.snapshot(name, resolutions, 1080);
    }
}
