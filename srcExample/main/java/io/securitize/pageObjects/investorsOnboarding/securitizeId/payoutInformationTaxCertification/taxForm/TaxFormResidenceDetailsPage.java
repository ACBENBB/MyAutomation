package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TaxFormResidenceDetailsPage extends AbstractPage {

    private static final ExtendedBy continueBtn = new ExtendedBy("Continue Button", By.xpath("//button[not(contains(@id, 'addInvestor'))][text()='Continue']"));
    private static final ExtendedBy IamAUSPersonOrEntity = new ExtendedBy("I am a US Person or US Entity Checkbox", By.xpath("//input[@id='us']"));
    private static final ExtendedBy IamNotAUSPersonOrEntity = new ExtendedBy("I am NOT a US Person or US Entity Checkbox", By.xpath("//input[@id='not-us']"));
    private static final ExtendedBy IamAnIndividualCheckBox = new ExtendedBy("I am NON US Individual Checkbox", By.xpath("//label[@for='individual']"));
    private static final ExtendedBy IamACorporationCheckBox = new ExtendedBy("I am NON US Individual Checkbox", By.xpath("//label[@for='corporation']"));

    public TaxFormResidenceDetailsPage(Browser browser) {
        super(browser, continueBtn);
    }

    public boolean isIamAUSPersonOrUSEntityCheckboxSelected() {
        return browser.findElement(IamAUSPersonOrEntity).isSelected();
    }

    public boolean isIamNotAUSPersonOrUSEntityCheckboxSelected() {
        return browser.findElement(IamNotAUSPersonOrEntity).isSelected();
    }

    public void selectIamAnIndividualCheckBox() {
        browser.click(IamAnIndividualCheckBox);
    }

    public TaxFormResidenceDetailsPage selectIamACorporationCheckBox() {
        browser.click(IamACorporationCheckBox);
        return new TaxFormResidenceDetailsPage(browser);
    }

    public void clickContinue() {
        browser.click(continueBtn, true);
    }

}