package io.securitize.pageObjects.investorsOnboarding;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdInvestorAbstractPage<R> extends AbstractPage<R> {

    private static final ExtendedBy exitProcessButton = new ExtendedBy("Exit process button", By.xpath("//button[contains(@class, 'profile-verification-return-link')]/span[text() ='Return to' or 'Exit this process']"));

    public SecuritizeIdInvestorAbstractPage(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
    }

    public void clickExitProcess() {
        browser.click(exitProcessButton);
    }


}
