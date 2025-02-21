package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm.TaxFormPage;
import org.openqa.selenium.By;
public class TaxCenterPage extends AbstractPage {

    private static final ExtendedBy taxFormCard = new ExtendedBy("Tax Form Card", By.xpath("//h6[text()='Tax Forms']"));

    public TaxCenterPage(Browser browser) {
        super(browser);
    }

    public TaxFormPage clickTaxFormCard(){
        browser.click(taxFormCard, false);
        return new TaxFormPage(browser);
    }

}