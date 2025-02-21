package io.securitize.pageObjects.controlPanel.cpMarkets;
import io.securitize.infra.webdriver.*;
import io.securitize.pageObjects.*;
import org.openqa.selenium.*;
public class CpMarketsInvestorPage extends AbstractPage {

    private static final ExtendedBy verificationTab = new ExtendedBy("Verification tab", By.xpath("//a[text()='Verification']"));
    private static final ExtendedBy accreditationStatusText = new ExtendedBy("Accreditation status", By.xpath("//div[@id='accreditation-status-select']//span"));
    private static final ExtendedBy accreditationStatusEditBtn = new ExtendedBy("Accreditation Status Edit Btn", By.xpath("//div[@id='accreditation-status']//span[contains(@id, 'edit-investor')]//button"));
    private static final ExtendedBy accreditationStatusDropdown = new ExtendedBy("Accreditation Status Dropdown", By.xpath("//select[@class='custom-select']"));
    private static final ExtendedBy accreditationStatusSaveBtn = new ExtendedBy("Accreditation Status Save Btn", By.xpath("//button[contains(text(), 'Save changes')]"));

    public CpMarketsInvestorPage(Browser browser) {
        super(browser);
    }

    public void clickInVerificationTab(){
        browser.click(verificationTab);
    }

    public String getAccreditationStatus(){
        return browser.getElementText(accreditationStatusText);
    }

    public void setAccreditationStatusTo(String kycStatus) {
        String accreditationInitialStatus = getAccreditationStatus();
        if (!accreditationInitialStatus.equalsIgnoreCase(kycStatus)) {
            clickAccreditationStatusEditBtn();
            setAccreditationStatus(kycStatus);
        }
    }

    public void clickAccreditationStatusEditBtn() {
        browser.click(accreditationStatusEditBtn);
    }

    public void setAccreditationStatus(String accreditationStatus) {
        browser.waitForElementVisibility(accreditationStatusDropdown);
        browser.waitForElementClickable(accreditationStatusDropdown, 10);
        browser.selectElementByVisibleText(accreditationStatusDropdown, accreditationStatus);
    }

    public void clickAccreditationStatusSaveBtn() {
        WebElement element = browser.findElement(accreditationStatusSaveBtn);
        browser.click(accreditationStatusSaveBtn);
//        browser.waitForElementToVanish(element, "Save Changes Btn");
    }
}