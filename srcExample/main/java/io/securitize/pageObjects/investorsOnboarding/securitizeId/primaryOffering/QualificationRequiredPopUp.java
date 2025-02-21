package io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering;

import io.securitize.infra.webdriver.*;
import io.securitize.pageObjects.*;
import org.openqa.selenium.*;

public class QualificationRequiredPopUp extends AbstractPage {

    private static final ExtendedBy popUpTitle = new ExtendedBy("Investor Qualification Required PopUp Title", By.xpath("//h4[text()='Investor qualification required']"));
    private static final ExtendedBy popUpText = new ExtendedBy("Invetor Qualification Required PopUp Text", By.xpath("//p[contains(text(), 'must meet the accreditation/qualification standards')]"));
    private static final ExtendedBy goBackBtn = new ExtendedBy("Investor Qualification Required GoBack Btn", By.xpath("//a[text()='Go back']"));
    private static final ExtendedBy closeBtn = new ExtendedBy("Investor Qualification Required Close Btn", By.xpath("//button[@class='close']"));

    public QualificationRequiredPopUp(Browser browser) {
        super(browser);
    }

    public boolean isPopUpTitleVisible() {
        return browser.isElementVisible(popUpTitle);
    }

    public boolean isPopUpTextVisible() {
        return browser.isElementVisible(popUpText);
    }

    public void clickGoBackBtn() {
        browser.click(goBackBtn);
    }

}