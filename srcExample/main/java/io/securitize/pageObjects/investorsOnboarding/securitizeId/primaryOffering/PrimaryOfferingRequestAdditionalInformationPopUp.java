package io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class PrimaryOfferingRequestAdditionalInformationPopUp extends AbstractPage {

    private static final ExtendedBy popUpTitle = new ExtendedBy("Pop Up Title", By.xpath("//h4[text()='Request aditional information']"));
    private static final ExtendedBy pupUpInfoText = new ExtendedBy("Pop Up Info Text", By.xpath("//P[text()='Due to regulations, we cannot market securities to users in your country.  However, we can send information about the issuer if you confirm your interest using the button below.']"));
    private static final ExtendedBy pupUpDisclaimer = new ExtendedBy("Pop Up Disclaimer Text", By.xpath("//P[text()='Information requested will come in the form of white papers, investor presentations, etc.  By confirming, you acknowledge that your request for this information comes without having been solicited or approached, directly or indirectly, by the issuer or any affiliate or other person acting as an agent on their behalf.']"));
    private static final ExtendedBy pupUpImInterestedBtn = new ExtendedBy("Im Interested Btn", By.xpath("//button[@id='reverse-solicitation-modal-accept']"));

    public PrimaryOfferingRequestAdditionalInformationPopUp(Browser browser) {
        super(browser);
    }

    public String getPopUpTitleText() {
        return browser.getElementText(popUpTitle);
    }

    public String getPupUpInfoTextText() {
        return browser.getElementText(pupUpInfoText);
    }

    public String getPopUpDisclaimerTextText() {
        return browser.getElementText(pupUpDisclaimer);
    }

    public void clickImInterestedBtn() {
        browser.click(pupUpImInterestedBtn, false);
    }

}
