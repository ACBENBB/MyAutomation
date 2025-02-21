package io.securitize.scripts;

import io.securitize.infra.api.anticaptcha.AntiCaptchaApi;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage2FA;
import io.securitize.tests.abstractClass.AbstractUiTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT236_UpdateCpBearerProduction extends AbstractUiTest {

    @Test
    @BypassRecaptcha
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    public void UpdateCpBearerProductionTest() {

        // this logic should only run in production environment
        List<String> allowedEnvironments = Arrays.asList("production", "apac");
        String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        if (allowedEnvironments.stream().noneMatch(currentEnvironment::equalsIgnoreCase)) {
            errorAndStop("This script it to be run only in these environments: " + allowedEnvironments, false);
        }

        startTestLevel("Login to control panel using email and password + 2FA ");
        String url = MainConfig.getCpUrl();
        getBrowser().navigateTo(url);
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        loginPage.setUsernameAndPassword(
                Users.getProperty(UsersProperty.automationCpApiEmail),
                Users.getProperty(UsersProperty.automationCpPassword));
        endTestLevel();


        startTestLevel("use Anti-Captcha to solve the reCaptcha");
        AntiCaptchaApi antiCaptchaApi = new AntiCaptchaApi();
        antiCaptchaApi.solveRecaptchaWithRetries(getBrowser().getCurrentUrl(), loginPage);
        endTestLevel();


        startTestLevel("Populate Login 2FA with the secret key");
        CpLoginPage2FA loginPage2Fa = new CpLoginPage2FA(getBrowser());
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI(false);
        endTestLevel();


        startTestLevel("Update production bearer in the vault");
        String bearer = getBrowser().getCookie("accessToken");
        Users.updateAutomationCpBearer(bearer);
        endTestLevel();
    }
}
