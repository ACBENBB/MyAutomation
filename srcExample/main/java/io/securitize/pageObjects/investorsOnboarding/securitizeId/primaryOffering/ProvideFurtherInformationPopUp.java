package io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering;

import io.securitize.infra.webdriver.*;
import io.securitize.pageObjects.*;
import org.openqa.selenium.*;

public class ProvideFurtherInformationPopUp extends AbstractPage {

    private static final ExtendedBy furtherInfoPopUpTitle = new ExtendedBy("Further Info PopUp Title", By.xpath("//h4[text()='Please provide further information']"));
    private static final ExtendedBy furtherInfoPopUpText = new ExtendedBy("Further Info PopUp Text", By.xpath("//p[text()='In order to invest, we need you to provide more information about you.']"));
    private static final ExtendedBy furtherInfoPopUpContinueBtn = new ExtendedBy("Further Info PopUp Continue Btn", By.xpath("//span[text()='Continue']"));

    public ProvideFurtherInformationPopUp(Browser browser) {
        super(browser);
    }

    public String getPopUpTitleText() {
      return browser.getElementText(furtherInfoPopUpTitle);
    }

    public String getPopUpBodyText() {
       return browser.getElementText(furtherInfoPopUpText);
    }

    public void clickOnContinue() {
        browser.waitForElementClickable(furtherInfoPopUpContinueBtn, 20);
        browser.click(furtherInfoPopUpContinueBtn, true);
    }

}