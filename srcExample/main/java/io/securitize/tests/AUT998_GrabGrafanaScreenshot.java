package io.securitize.tests;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.grafana.GrafanaLoginPage;
import io.securitize.tests.abstractClass.AbstractUiTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class AUT998_GrabGrafanaScreenshot extends AbstractUiTest {

    @Test
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    public void grabGrafanaScreenshotScript() throws IOException {

        // navigate to the dashboard
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.grafanaUrl));
        GrafanaLoginPage grafanaLoginPage = new GrafanaLoginPage(getBrowser());
        grafanaLoginPage
                .setUsername(Users.getProperty(UsersProperty.grafanaUsername))
                .setPassword(Users.getProperty(UsersProperty.grafanaPassword))
                .clickSubmit();

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
