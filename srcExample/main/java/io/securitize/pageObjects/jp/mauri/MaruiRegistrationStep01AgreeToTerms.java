package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiRegistrationStep01AgreeToTerms extends AbstractJpPage {

    private static final ExtendedBy step01Header = new ExtendedBy("Step 01 Head Line", By.xpath("//h1[text()[contains(., '社債申込サービス利用の規約')]]"));
    private static final ExtendedBy agreeToTermsCheckbox = new ExtendedBy("Agree to Terms Checkbox", By.xpath("//*[contains(@class,'form-check-input')]"));
    private static final ExtendedBy nextStepButton = new ExtendedBy("Next Step Button", By.xpath("//button/*[text()[contains(.,'次のステップに進む')]]"));

    public MaruiRegistrationStep01AgreeToTerms(Browser browser) {
        super(browser, step01Header);
    }

    public MaruiRegistrationStep02InputInvestorInformation agreeToTermsAndClickNextButton() {
        if (!browser.findElement(agreeToTermsCheckbox).isSelected()) {
            browser.click(agreeToTermsCheckbox);
        }
        browser.click(nextStepButton);
        browser.waitForElementToVanish(step01Header);
        return new MaruiRegistrationStep02InputInvestorInformation(browser);
    }

    public MaruiRegistrationStep02InputInvestorInformation agreeToTermsAndClickNextButton(boolean retry) {
        return RetryOnExceptions.retryFunction(
                this::agreeToTermsAndClickNextButton,
                ()-> {
                    if (!browser.isElementVisible(agreeToTermsCheckbox)) {
                        browser.refreshPage();
                        String step02url = "https://oioi.sandbox.securitize.io/#/registration/profile";
                        browser.navigateTo(step02url);
                    }
                    return null;
                },
                retry
        );
    }

}
