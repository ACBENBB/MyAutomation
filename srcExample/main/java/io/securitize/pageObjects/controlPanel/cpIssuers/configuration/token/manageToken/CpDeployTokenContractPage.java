package io.securitize.pageObjects.controlPanel.cpIssuers.configuration.token.manageToken;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpDeployTokenContractPage extends AbstractPage {

    private static final ExtendedBy tokenTickerField = new ExtendedBy("Token Ticker field", By.xpath("//*[@name = 'Token Ticker']"));
    private static final ExtendedBy tokenDecimalField = new ExtendedBy("Token Decimal field", By.xpath("//*[@name = 'Token Decimals']"));
    private static final ExtendedBy blockchainDropdown = new ExtendedBy("Blockchain Network Dropdown", By.xpath("//*[@name = 'Blockchain']"));
    private static final ExtendedBy custodianAddressField = new ExtendedBy("Custodian Address field", By.xpath("//*[@name = 'custodianAddress']"));
    private static final ExtendedBy continueButton = new ExtendedBy("Continue Button", By.xpath("//*[@id='scroll-container']//button/span"));
    private static final ExtendedBy confirmationToast = new ExtendedBy("Confirmation Toast", By.xpath("//*[@role ='alert']"));
    private static final ExtendedBy tickerNameErrorToast = new ExtendedBy("Resource Already Exist Toast", By.xpath("//p[text()='Resource already exists']"));
    public CpDeployTokenContractPage(Browser browser) {
        super(browser);
    }

    public void addTokenTicker(String tokenTicker) { browser.typeTextElement(tokenTickerField, tokenTicker);}
    public void addTokenDecimals(String tokenDecimals) { browser.typeTextElement(tokenDecimalField, tokenDecimals);}
    public void selectNetwork(String networkName) {browser.selectElementByVisibleText(blockchainDropdown, networkName);}
    public void setCustodianAddressField(String custodianAddress) {browser.typeTextElement(custodianAddressField, custodianAddress);}
    public void clickContinueButton() {
        browser.click(continueButton);
    }
    public boolean isConfirmationToastVisible() {
        browser.waitForElementVisibility(confirmationToast);
        return browser.isElementVisible(confirmationToast);
    }

    public boolean isTickerNameErrorToastDisplayed() {
        return browser.isElementVisible(tickerNameErrorToast);
    }


}
