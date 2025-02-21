package io.securitize.pageObjects.controlPanel.cpIssuers.configuration.token;

import io.securitize.infra.exceptions.WalletNotReadyTimeoutException;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;
import java.util.function.Function;


public class CpTokenConfigurationPage extends AbstractPage {


    private static final ExtendedBy addTokenButton = new ExtendedBy("Add Token Button", By.xpath("//*[@title='Add Token']/span"));
    private static final ExtendedBy confirmAddTokenButton = new ExtendedBy("Confirm Add Token button", By.xpath("//*[@id='generalModal___BV_modal_footer_']/button[2]"));
    private static final ExtendedBy tokenNameField = new ExtendedBy("token field name", By.xpath("//*[@id='generalModal___BV_modal_body_']//input"));
    private static final ExtendedBy manageTokenButton = new ExtendedBy("Manage Token Button", By.xpath("//button[2]/span"));
    private static final ExtendedBy tokenStatusChipBadge = new ExtendedBy("Manage Token Button", By.xpath("//span[contains(text(),'Token Status:')]/following-sibling::span"));




    public CpTokenConfigurationPage(Browser browser) {
        super(browser);
    }

    public void clickAddToken() { browser.click(addTokenButton);}
    public void setTokenName(String tokenName) { browser.typeTextElement(tokenNameField , tokenName);}
    public void clickConfirmAddToken() { browser.click(confirmAddTokenButton);}

    public void clickManageToken() { browser.click(manageTokenButton);}

    public void waitForTokenToBeDeployed() {
        Function<String, Boolean> internalWaitForTokenToBeDeployed = t -> {
            Optional<WebElement> optionalElement = browser.findFirstVisibleElementQuick(tokenStatusChipBadge, 20);
            if (optionalElement.get().getText().equalsIgnoreCase("Deployed")) {
                return true;
            } else {
                MultiReporter.infoAndShowMessageInBrowser("Waiting for token status be 'Deployed'");
                browser.refreshPage(true);
            }
            return false;
        };

        String description = "waitingTokenToBeDeployed";
        //timeoutSeconds are 25 minutes until token deployment is complete
        //checkIntervalMilliseconds are 30 seconds for each browser refresh
        Browser.waitForExpressionToEqual(internalWaitForTokenToBeDeployed, null, true, description, 1500, 30000, new WalletNotReadyTimeoutException("Token Not deployed even after timeout"));
    }

    public boolean isTokenDeployed() {
        System.out.println("DEPLOYED TOKEN URL: ---- > " + browser.getCurrentUrl());
        return browser.findElement(tokenStatusChipBadge).getText().equalsIgnoreCase("Deployed");
    }


}
