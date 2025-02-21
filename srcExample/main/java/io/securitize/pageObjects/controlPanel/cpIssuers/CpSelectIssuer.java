package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpSelectIssuer extends AbstractPage {

    private static final ExtendedBy viewIssuerButton = new ExtendedBy("View issuer button", By.xpath("//button[contains(text(), 'View Issuer')]"));
    private static final String viewIssuerButtonByIssuerTextTemplate = "//h3[text()=' %s ']/ancestor::div[contains(@class, 'issuer-card')]//button[contains(text(), 'View Issuer')]";
    private static final ExtendedBy searchIssuerTextBox = new ExtendedBy("search field in issuers list screen", By.xpath("//*[contains (@class, 'form-control form-control-m')]"));
    private static final ExtendedBy logoutButton = new ExtendedBy("Issuers list page - logout button", By.xpath("//*[@class = 'ion ion-ios-log-out navbar-icon align-middle']"));
    private static final ExtendedBy issuerTitle = new ExtendedBy("Issuers Title", By.xpath("//h4[text()=' Issuers ']"));

    public CpSelectIssuer(Browser browser) {
        super(browser, viewIssuerButton);
    }

    public void clickViewIssuerByName(String issuerName) {
        browser.typeTextElement(searchIssuerTextBox, issuerName);
        String fullXpathSelector = String.format(viewIssuerButtonByIssuerTextTemplate, issuerName);
        ExtendedBy valueSelector = new ExtendedBy("View issuer button for " + issuerName, By.xpath(fullXpathSelector));
        browser.click(valueSelector);
    }

    public void logoutFromCP(){
        browser.click(logoutButton);
    }

    public void waitForIssuerPageToLoad(){
        browser.waitForElementVisibility(issuerTitle);
        browser.waitForElementVisibility(searchIssuerTextBox);
    }
}