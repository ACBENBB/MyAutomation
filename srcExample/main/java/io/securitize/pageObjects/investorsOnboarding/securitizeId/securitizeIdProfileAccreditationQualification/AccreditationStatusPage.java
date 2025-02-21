package io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import io.securitize.tests.transferAgent.abstractClass.AccreditationMethod;
import org.openqa.selenium.By;

public class AccreditationStatusPage extends AccreditationMethod {

    private static final ExtendedBy startAccreditationButton = new ExtendedBy("Start Accreditation Button", By.xpath("//button[@name='start-accreditation']"));
    private static final ExtendedBy accreditationStatusBadge = new ExtendedBy("Accreditation Status Badge", By.xpath("//span[contains(@class, 'text-white')]"));
    private static final ExtendedBy acceptCookiesBtn = new ExtendedBy("Accept Cookies Btn", By.xpath("//button[@type='button' and text()='Accept']"));
    private static final ExtendedBy acceptCookiesNewBtn = new ExtendedBy("Accept Cookies New Btn", By.xpath("//*[@id='hs-eu-confirmation-button']"));

    // Documents Submitted Modal
    private static final ExtendedBy understoodButton = new ExtendedBy("Understood Button", By.xpath("//button[contains(text(), 'Understood')]"));

    public AccreditationStatusPage(Browser browser) {
        super(browser);
    }

    public void acceptCookies() {
        browser.click(acceptCookiesBtn);
    }

    public void acceptCookiesNew() {
        browser.click(acceptCookiesNewBtn);
    }

    public String getAccreditationStatusBadge() {
        return browser.getElementAttribute(accreditationStatusBadge, "innerText");
    }

    public AccreditationMethodPage startAccreditationButton() {
        browser.waitForElementClickable(startAccreditationButton, 30);
        browser.click(startAccreditationButton, false);
        return new AccreditationMethodPage(browser);
    }

    public AccreditationStatusPage clickOnUnderstood() {
        browser.click(understoodButton, false);
        return this;
    }

    public void completeIndividualInvestorAccreditation(String accreditationMethod) {
        if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.IndividualAccreditationMethod.INCOME.name())) {
            individualAccreditationIncome();
        } else if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.IndividualAccreditationMethod.INCOME_PROD.name())) {
            individualAccreditationSanityProd();
        } else if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.IndividualAccreditationMethod.NET_WORTH.name())) {
            individualAccreditationNetWorth();
        } else if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.IndividualAccreditationMethod.PROFESSIONAL_LICENSE.name())) {
            individualAccreditationProfessionalLicense();
        } else if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.IndividualAccreditationMethod.THIRD_PARTY.name())) {
            individualAccreditationThirdParty();
        }
    }

    public void completeEntityInvestorAccreditation(String accreditationMethod) {
        if(accreditationMethod.equalsIgnoreCase(AbstractAccreditation.EntityAccreditationMethod.ASSETS_AND_INVESTMENTS.name())) {
            entityAccreditationAssetsAndInvestments();
        } else if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.EntityAccreditationMethod.OWNERS_OF_EQUITY_SECURITIES.name())) {
            entityAccreditationOwnersOfEquitySecurities();
        } else if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.IndividualAccreditationMethod.THIRD_PARTY.name())) {
            entityAccreditationThirdParty();
        }
    }

}