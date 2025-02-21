package io.securitize.tests;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.pageObjects.reportportal.ReportPortalLoginPage;
import io.securitize.tests.abstractClass.AbstractUiTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class AUT997_GrabReportPortalDashboardScreenshot extends AbstractUiTest {

    @Test
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    public void grabReportPortalDashboardScreenshotScript() throws IOException {

        // navigate to the dashboard
        getBrowser().navigateTo(System.getenv("reportPortalDashboardUri"));
        ReportPortalLoginPage reportPortalLoginPage = new ReportPortalLoginPage(getBrowser());
        reportPortalLoginPage
                .setUsername(Users.getProperty(UsersProperty.reportsPortalUsername))
                .setPassword(Users.getProperty(UsersProperty.reportsPortalPassword))
                .clickSubmit()
                .clickFullScreen();

        // save local screenshot
        getBrowser().sleep(30 * 1000); // give the dashboard time to load up
        String screenshot = Browser.getScreenshotAsBase64();
        assert screenshot != null;
        byte[] decodedImg = Base64.getDecoder()
                .decode(screenshot.getBytes(StandardCharsets.UTF_8));
        Path destinationFile = Paths.get( "dashboard_screenshot.png");
        Files.write(destinationFile, decodedImg);
    }
}
