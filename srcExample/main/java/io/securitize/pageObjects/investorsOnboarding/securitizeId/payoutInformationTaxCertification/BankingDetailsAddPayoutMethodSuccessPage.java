package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class BankingDetailsAddPayoutMethodSuccessPage extends AbstractPage {
    // Actually this id is missing, ask dev to add it.
    private static final ExtendedBy goBackButton = new ExtendedBy("Go back to payout preference",
            By.id("go-back"));
    //we need to do this dirty thing here because we are interacting with an external web-hook, devs can't change ids.
    private static final ExtendedBy doneButton = new ExtendedBy("Done button",
            By.xpath("//*[@id=\"app\"]//button[.//span[text()='Done']]"));
    private static final ExtendedBy successMessage = new ExtendedBy("Success Mesaage",
            By.xpath("//div[contains(text(), 'Payout Method Submitted Successfully!')]"));


    public BankingDetailsAddPayoutMethodSuccessPage(Browser browser) {super(browser, doneButton);}

    public BankingDetailsAddPayoutMethodPage clickDoneBtn(){
        browser.click(doneButton, false);
        return new BankingDetailsAddPayoutMethodPage(browser);
    }





}
