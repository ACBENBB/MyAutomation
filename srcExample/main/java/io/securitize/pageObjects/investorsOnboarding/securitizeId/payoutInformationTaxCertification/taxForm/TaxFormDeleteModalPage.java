package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TaxFormDeleteModalPage extends AbstractPage {

    private static final ExtendedBy confirmBtn = new ExtendedBy("Delete Tax Form Btn", By.id("delete-tax-form"));

    public TaxFormDeleteModalPage(Browser browser) {
        super(browser);
    }

    public TaxFormPage clickDeleteConfirmBtn() {
        browser.clickAndWaitForElementToVanish(confirmBtn);
        return new TaxFormPage(browser);
    }

}