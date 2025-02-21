package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class BankingDetailsAddPayoutMethodPage extends AbstractPage {

    private static final ExtendedBy goBackButton = new ExtendedBy("Go back to payout preference",
            By.id("go-back"));
    //we need to do this dirty thing here because we are interacting with an external web-hook, devs can't change ids.
    private static final ExtendedBy addPayoutMethodButton = new ExtendedBy("Add Payout Method - Button",
            By.xpath("//*[@id=\"app\"]//button[.//span[text()='Add Payout Method']]"));
    private static final ExtendedBy paymentRailsIframe = new ExtendedBy("Payment Rails Iframe",
            By.xpath("//*[contains(@class, 'PaymentRails_responsiveIframe')]"));
    private static final ExtendedBy editPayoutMethodBtn = new ExtendedBy("Payout Method Edit Buton",
            By.xpath("//i[contains(@class, 'pencil')]"));


    public BankingDetailsAddPayoutMethodPage(Browser browser) {
        super(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage clickAddPayoutMethodButton(){
        browser.switchToFrame(paymentRailsIframe);
        browser.click(addPayoutMethodButton, false);
        browser.switchBackToDefaultContent();
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public TaxCenterPage clickOnBackToPayoutPreference(){
        browser.switchBackToDefaultContent();
        browser.click(goBackButton, false);
        return new TaxCenterPage(browser);
    }


}
