package io.securitize.pageObjects.controlPanel.cpIssuers.vanityETHWalletGenerator;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class VanityETHHomePage extends AbstractPage {

    private static final ExtendedBy generateWalletButton = new ExtendedBy("Generate wallet button", By.xpath("//input[@type='button' and @value=\"Generate\" and not(@disabled)]"));
    private static final ExtendedBy walletAddressField = new ExtendedBy("Wallet address field", By.xpath("//div[@class='panel result']//span[@class='output']"));

    public VanityETHHomePage(Browser browser, ExtendedBy... elements) {
        super(browser, generateWalletButton);
    }

    public static String getNewWalletAddress(Browser browser) {
        // open vanityETH in a new tab
        browser.openNewTabAndSwitchToIt(MainConfig.getProperty(MainConfigProperty.vanityETHUrl));

        // generate new wallet address
        VanityETHHomePage vanityETHPage = new VanityETHHomePage(browser);
        vanityETHPage.clickGenerateWalletButton();

        // read new wallet address
        String walletAddress = vanityETHPage.getGeneratedWalletId();

        // close the tab and return to the previous one
        browser.closeLastTabAndSwitchToPreviousOne();

        return walletAddress;
    }

    public VanityETHHomePage clickGenerateWalletButton() {
        browser.click(generateWalletButton);
        return this;
    }

    public String getGeneratedWalletId() {
        return browser.getElementText(walletAddressField);
    }
}
